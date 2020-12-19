package com.samsung.accessory.hearablemgr.module.home;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.ResultReceiver;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import com.samsung.accessory.hearablemgr.common.util.ResponseCallback;
import seccompat.android.util.Log;

public class SmartThingsUtil {
    private static final String SMART_THINGS_FME_HELPER_SERVICE = "com.samsung.android.oneconnect.support.fme.helper.FmeHelperService";
    private static final int SMART_THINGS_MIN_VERSION_CODE = 175116010;
    private static final String SMART_THINGS_PACKAGE_NAME = "com.samsung.android.oneconnect";
    private static final String TAG = "Attic_SmartThingsUtil";
    private static boolean sFmeServiceReady = false;

    public static void startSmartThingsFind(Activity activity, String str) {
        Log.d(TAG, "startSmartThingsFind() :" + BluetoothUtil.privateAddress(str));
        if (str == null) {
            str = UhmFwUtil.getLastLaunchDeviceId();
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("scapp://launch?action=service&service_code=FME&target_id=" + str));
        intent.addFlags(268435456);
        intent.addFlags(67108864);
        intent.addFlags(536870912);
        try {
            activity.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "startSmartThingsFind() : Exception : " + e);
        }
    }

    public static boolean isServiceCheckSupport() {
        boolean z = false;
        try {
            PackageInfo packageInfo = Application.getContext().getPackageManager().getPackageInfo(SMART_THINGS_PACKAGE_NAME, 0);
            if (packageInfo != null && packageInfo.versionCode >= SMART_THINGS_MIN_VERSION_CODE) {
                z = true;
            }
        } catch (Exception e) {
            Log.w(TAG, "isServiceCheckSupport() : Exception : " + e);
        }
        Log.d(TAG, "isServiceCheckSupport() : " + z);
        return z;
    }

    private static ResultReceiver toResultReceiverParcelable(ResultReceiver resultReceiver) {
        Parcel obtain = Parcel.obtain();
        try {
            resultReceiver.writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            return (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(obtain);
        } finally {
            obtain.recycle();
        }
    }

    public static void checkFmeServiceReady(String str, final ResponseCallback responseCallback) {
        Log.d(TAG, "checkFmeServiceReady() :" + BluetoothUtil.privateAddress(str));
        if (str == null) {
            str = UhmFwUtil.getLastLaunchDeviceId();
        }
        AnonymousClass1 r0 = new ResultReceiver(new Handler(Looper.getMainLooper())) {
            /* class com.samsung.accessory.hearablemgr.module.home.SmartThingsUtil.AnonymousClass1 */

            /* access modifiers changed from: protected */
            public void onReceiveResult(int i, Bundle bundle) {
                super.onReceiveResult(i, bundle);
                String str = null;
                String string = bundle != null ? bundle.getString("result_response") : null;
                Log.d(SmartThingsUtil.TAG, "onReceiveResult() : resultCode=" + i + ", resultData=" + bundle + ", result=" + string);
                responseCallback.setExtraObject(string);
                ResponseCallback responseCallback = responseCallback;
                if (string == null) {
                    str = "null";
                }
                responseCallback.onResponse(str);
            }
        };
        Intent intent = new Intent();
        intent.setClassName(SMART_THINGS_PACKAGE_NAME, SMART_THINGS_FME_HELPER_SERVICE);
        intent.putExtra("launch_type", "SERVICE_CHECK");
        intent.putExtra("target_service", "FME");
        intent.putExtra("target_id", str);
        intent.putExtra("receiver", toResultReceiverParcelable(r0));
        try {
            Application.getContext().startService(intent);
        } catch (Exception e) {
            Log.w(TAG, "checkFmeServiceReady() : Exception : " + e);
        }
    }

    public static boolean getFmeServiceReady(boolean z) {
        if (!isServiceCheckSupport()) {
            sFmeServiceReady = false;
        } else if (z) {
            checkFmeServiceReady(null, new ResponseCallback() {
                /* class com.samsung.accessory.hearablemgr.module.home.SmartThingsUtil.AnonymousClass2 */

                @Override // com.samsung.accessory.hearablemgr.common.util.ResponseCallback
                public void onResponse(String str) {
                    if (str == null) {
                        String str2 = (String) getExtraObject();
                        Log.d(SmartThingsUtil.TAG, "getFmeServiceReady().onResponse() : result=" + str2);
                        boolean unused = SmartThingsUtil.sFmeServiceReady = "true".equalsIgnoreCase(str2);
                        return;
                    }
                    Log.d(SmartThingsUtil.TAG, "getFmeServiceReady().onResponse() : " + str);
                    boolean unused2 = SmartThingsUtil.sFmeServiceReady = false;
                }
            });
        }
        Log.d(TAG, "getFmeServiceReady() : " + sFmeServiceReady);
        return sFmeServiceReady;
    }
}
