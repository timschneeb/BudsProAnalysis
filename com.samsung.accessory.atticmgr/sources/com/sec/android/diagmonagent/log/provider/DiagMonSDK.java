package com.sec.android.diagmonagent.log.provider;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.db.DataController;
import com.sec.android.diagmonagent.log.provider.threadExecutor.ANRExecutor;
import com.sec.android.diagmonagent.log.provider.threadExecutor.EventReportExecutor;
import com.sec.android.diagmonagent.log.provider.threadExecutor.ServiceRegistrationExecutor;
import com.sec.android.diagmonagent.log.provider.utils.BundleContract;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;
import java.lang.Thread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiagMonSDK {
    private static final int MIN_SDK = 24;
    private static CONFIGURATION_TYPE configurationType = CONFIGURATION_TYPE.NONE;
    private static ExecutorService es = Executors.newSingleThreadExecutor();
    private static boolean isEnableUncaughtExceptionLogging = false;
    private static DiagMonConfig mConfig;
    private static Thread.UncaughtExceptionHandler originUncaughtExceptionHandler;
    private static Bundle srObj;

    /* access modifiers changed from: private */
    public enum CONFIGURATION_TYPE {
        NONE,
        DEFAULT,
        CUSTOM
    }

    public static void setDefaultConfiguration(Context context, String str) {
        if (Build.VERSION.SDK_INT < 24) {
            Log.w(DiagMonUtil.TAG, "SDK is required at least version 24");
            return;
        }
        AppLog.initLogger(context, str);
        if (getConfigurationType() == CONFIGURATION_TYPE.CUSTOM) {
            AppLog.w("setDefaultConfiguration can't be used because CustomLogging is using");
        } else if (mConfig != null) {
            AppLog.w("setDefaultConfiguration is already set");
        } else if (DiagMonUtil.checkDMA(context) == 3) {
            AppLog.w("setDefaultConfiguration is not supported for GED devices");
        } else {
            mConfig = new DiagMonConfig(context).setServiceId(str).setAgree(DiagMonUtil.AGREE_TYPE_DIAGNOSTIC);
            setConfigurationType(CONFIGURATION_TYPE.DEFAULT);
            registerService();
        }
    }

    public static void setConfiguration(DiagMonConfig diagMonConfig) {
        if (Build.VERSION.SDK_INT < 24) {
            Log.w(DiagMonUtil.TAG, "SDK is required at least version 24");
        } else if (diagMonConfig == null) {
            Log.w(DiagMonUtil.TAG, "DiagMonConfiguration is null");
        } else {
            AppLog.initLogger(diagMonConfig.getContext(), diagMonConfig.getServiceId());
            if (getConfigurationType() == CONFIGURATION_TYPE.DEFAULT) {
                AppLog.w("You can't use setConfiguration with enableDefaultConfiguration");
                return;
            }
            mConfig = diagMonConfig;
            setConfigurationType(CONFIGURATION_TYPE.CUSTOM);
            registerService();
        }
    }

    private static void registerService() {
        try {
            synchronized (DiagMonSDK.class) {
                srObj = makeSRObj(mConfig);
                es.submit(new ServiceRegistrationExecutor(mConfig, srObj));
            }
        } catch (Exception e) {
            AppLog.e("failed to setConfiguration" + e);
        }
    }

    private static Bundle makeSRObj(DiagMonConfig diagMonConfig) {
        Bundle bundle = new Bundle();
        bundle.putString("serviceId", diagMonConfig.getServiceId());
        bundle.putString("serviceVersion", DiagMonUtil.getPackageVersion(diagMonConfig.getContext()));
        bundle.putString("serviceAgreeType", diagMonConfig.getAgreeAsString());
        bundle.putString("deviceId", diagMonConfig.getDeviceId());
        bundle.putString("trackingId", diagMonConfig.getTrackingId());
        bundle.putString("sdkVersion", DiagMonUtil.getSdkVersion());
        bundle.putString("sdkType", DiagMonUtil.getSdkType(diagMonConfig.getContext()));
        bundle.putString(BundleContract.PKG_NAME, diagMonConfig.getContext().getPackageName());
        AppLog.i("generated SR object");
        return bundle;
    }

    public static boolean customEventReport(Context context, EventBuilder eventBuilder) {
        if (Build.VERSION.SDK_INT < 24) {
            Log.w(DiagMonUtil.TAG, "SDK is required at least version 24");
            return false;
        }
        Log.i(DiagMonUtil.TAG, "Request CustomEventReport");
        DiagMonConfig diagMonConfig = mConfig;
        if (diagMonConfig == null) {
            Log.w(DiagMonUtil.TAG, "You first have to create DiagMonConfiguration");
            Log.w(DiagMonUtil.TAG, "CustomEventReport is aborted");
            return false;
        }
        AppLog.initLogger(diagMonConfig.getContext(), mConfig.getServiceId());
        if (getConfigurationType() == CONFIGURATION_TYPE.DEFAULT) {
            AppLog.w("You can't use customEventReport with enableDefaultConfiguration");
            return false;
        }
        sendEventReport(eventBuilder);
        return true;
    }

    private static void sendEventReport(EventBuilder eventBuilder) {
        es.submit(new EventReportExecutor(mConfig, srObj, eventBuilder));
    }

    public static void enableUncaughtExceptionLogging(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 24) {
                Log.w(DiagMonUtil.TAG, "SDK is required at least version 24");
            } else if (mConfig == null) {
                Log.w(DiagMonUtil.TAG, "UncaughtExceptionLogging can't be enabled because Configuration is null");
            } else {
                AppLog.initLogger(mConfig.getContext(), mConfig.getServiceId());
                if (getConfigurationType() == CONFIGURATION_TYPE.NONE) {
                    AppLog.w("You first have to call configuration method");
                } else if (isEnableUncaughtExceptionLogging) {
                    AppLog.w("UncaughtExceptionLogging is already enabled");
                } else {
                    isEnableUncaughtExceptionLogging = true;
                    originUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
                    Thread.setDefaultUncaughtExceptionHandler(new DiagMonLogger(context, originUncaughtExceptionHandler, mConfig));
                }
            }
        } catch (Exception e) {
            AppLog.e("failed to enableUncaughtExceptionLogging" + e);
        }
    }

    public static void disableUncaughtExceptionLogging() {
        try {
            if (Build.VERSION.SDK_INT < 24) {
                Log.w(DiagMonUtil.TAG, "SDK is required at least version 24");
            } else if (originUncaughtExceptionHandler != null) {
                isEnableUncaughtExceptionLogging = false;
                Thread.setDefaultUncaughtExceptionHandler(originUncaughtExceptionHandler);
            }
        } catch (NullPointerException e) {
            AppLog.e(e.getMessage());
        } catch (Exception e2) {
            AppLog.e(e2.getMessage());
        }
    }

    public boolean isEnableUncaughtExceptionLogging() {
        if (Build.VERSION.SDK_INT >= 24) {
            return isEnableUncaughtExceptionLogging;
        }
        Log.w(DiagMonUtil.TAG, "SDK is required at least version 24");
        return false;
    }

    public static void enableANRLogging() {
        if (Build.VERSION.SDK_INT < 24) {
            Log.w(DiagMonUtil.TAG, "SDK is required at least version 24");
        } else if (DiagMonUtil.isGED()) {
            Log.w(DiagMonUtil.TAG, "This API isn't supported on this device");
        } else if (DiagMonUtil.underPalette()) {
            Log.w(DiagMonUtil.TAG, "This API isn't supported on this device");
        } else {
            DiagMonConfig diagMonConfig = mConfig;
            if (diagMonConfig == null) {
                Log.w(DiagMonUtil.TAG, "ANR Logging can't be enabled because Configuration is null");
                return;
            }
            AppLog.initLogger(diagMonConfig.getContext(), mConfig.getServiceId());
            es.submit(new ANRExecutor(mConfig.getContext(), mConfig.getServiceId(), true));
        }
    }

    public static void disableANRLogging() {
        if (Build.VERSION.SDK_INT < 24) {
            Log.w(DiagMonUtil.TAG, "SDK is required at least version 24");
        } else if (DiagMonUtil.isGED()) {
            Log.w(DiagMonUtil.TAG, "This API isn't supported on this device");
        } else if (DiagMonUtil.underPalette()) {
            Log.w(DiagMonUtil.TAG, "This API isn't supported on this device");
        } else {
            DiagMonConfig diagMonConfig = mConfig;
            if (diagMonConfig == null) {
                Log.w(DiagMonUtil.TAG, "ANR Logging can't be disabled because Configuration is null");
                return;
            }
            AppLog.initLogger(diagMonConfig.getContext(), mConfig.getServiceId());
            es.submit(new ANRExecutor(mConfig.getContext(), mConfig.getServiceId(), false));
        }
    }

    public static String getDeviceId(Context context, String str) {
        if (Build.VERSION.SDK_INT < 24) {
            Log.w(DiagMonUtil.TAG, "SDK is required at least version 24");
            return "";
        }
        DiagMonConfig diagMonConfig = mConfig;
        if (diagMonConfig == null) {
            return "";
        }
        AppLog.initLogger(diagMonConfig.getContext(), mConfig.getServiceId());
        int checkDMA = DiagMonUtil.checkDMA(context);
        if (checkDMA == 2) {
            Bundle bundle = new Bundle();
            bundle.putString("serviceId", str);
            Bundle call = context.getContentResolver().call(DiagMonUtil.URI, "request_deviceid", "request_deviceid", bundle);
            if (call.getString("result") == null) {
                AppLog.i("Can't find deviceId from DMA");
                return "";
            }
            DiagMonUtil.printResultfromDMA(call);
            return call.getString("result");
        } else if (checkDMA == 3) {
            return DataController.getDeviceId(context);
        } else {
            try {
                AppLog.w("It is not supported because DiagMonAgent is an old version");
                return "";
            } catch (Exception e) {
                AppLog.e(e.getMessage());
                return "";
            }
        }
    }

    static void sendUncaughtExceptionEvent(EventBuilder eventBuilder) {
        registerService();
        sendEventReport(eventBuilder);
    }

    private static CONFIGURATION_TYPE getConfigurationType() {
        return configurationType;
    }

    private static void setConfigurationType(CONFIGURATION_TYPE configuration_type) {
        configurationType = configuration_type;
        AppLog.d("setConfiguration type : " + configurationType);
    }
}
