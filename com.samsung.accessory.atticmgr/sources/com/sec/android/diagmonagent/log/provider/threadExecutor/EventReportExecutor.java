package com.sec.android.diagmonagent.log.provider.threadExecutor;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import com.accessorydm.interfaces.XDBInterface;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.db.DataController;
import com.sec.android.diagmonagent.log.provider.DiagMonConfig;
import com.sec.android.diagmonagent.log.provider.EventBuilder;
import com.sec.android.diagmonagent.log.provider.utils.BundleContract;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;
import com.sec.android.diagmonagent.log.provider.utils.Validator;
import com.sec.android.diagmonagent.log.provider.utils.ZipHelper;
import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class EventReportExecutor implements Runnable {
    private static final String intentNameApp = "com.sec.android.diagmonagent.intent.REPORT_ERROR_APP";
    private static final String intentNameSystem = "com.sec.android.diagmonagent.intent.REPORT_ERROR_V2";
    private Context context;
    private EventBuilder eventBuilder;
    private DiagMonConfig mConfig;
    private Bundle srObj;

    public EventReportExecutor(DiagMonConfig diagMonConfig, Bundle bundle, EventBuilder eventBuilder2) {
        this.context = diagMonConfig.getContext();
        this.mConfig = diagMonConfig;
        this.srObj = bundle;
        this.eventBuilder = eventBuilder2;
    }

    public void run() {
        try {
            if (!DiagMonUtil.insufficientStorage()) {
                if (!Validator.isValidLogPath(this.eventBuilder.getLogPath())) {
                    AppLog.w("You have to properly set LogPath");
                    return;
                }
                if (!this.eventBuilder.mIsCalledNetworkMode && this.mConfig.isEnabledDefaultNetwork()) {
                    AppLog.d("NetworkMode is applied as DefaultNetwork");
                    this.eventBuilder.setNetworkMode(this.mConfig.getDefaultNetworkMode());
                }
                int checkDMA = DiagMonUtil.checkDMA(this.context);
                boolean z = false;
                if (checkDMA == 0) {
                    AppLog.w("not installed");
                } else if (checkDMA == 1) {
                    AppLog.d("LEGACY DMA");
                    z = eventReportViaBR(this.context, this.mConfig, this.eventBuilder);
                } else if (checkDMA == 2) {
                    z = eventReportViaCP(this.context, this.mConfig, this.eventBuilder, this.srObj);
                } else if (checkDMA != 3) {
                    AppLog.w("Exceptional case");
                    AppLog.w("customEventReport is aborted");
                } else {
                    Bundle makeEventObjAsBundle = makeEventObjAsBundle(this.context, this.mConfig, this.eventBuilder);
                    if (Validator.isValidMandatoryFields(makeEventObjAsBundle)) {
                        String collectLogs = collectLogs(this.context, this.eventBuilder.getLogPath());
                        boolean eventReport = DataController.eventReport(this.context, makeEventObjAsBundle, collectLogs);
                        if (!collectLogs.isEmpty()) {
                            removeZipFile(collectLogs);
                        }
                        z = eventReport;
                    }
                }
                if (!z) {
                    AppLog.w("failed to customEventReport");
                }
            }
        } catch (Exception e) {
            AppLog.w("failed to customEventReport" + e);
        }
    }

    private boolean eventReportViaBR(Context context2, DiagMonConfig diagMonConfig, EventBuilder eventBuilder2) {
        try {
            if (!Validator.isValidLegacyConfig(diagMonConfig)) {
                AppLog.w("Invalid DiagMonConfiguration");
                return false;
            } else if (!Validator.isValidLegacyEventBuilder(eventBuilder2)) {
                AppLog.w("Invalid EventBuilder");
                return false;
            } else {
                AppLog.d("Valid EventBuilder");
                sendLogPath();
                context2.sendBroadcast(makeEventobjAsIntent(context2, diagMonConfig, eventBuilder2));
                AppLog.d("Report your logs");
                return true;
            }
        } catch (Exception | NullPointerException unused) {
            return false;
        }
    }

    private Intent makeEventobjAsIntent(Context context2, DiagMonConfig diagMonConfig, EventBuilder eventBuilder2) {
        Intent intent;
        JSONObject jSONObject = new JSONObject();
        if (getUid(context2) == 1000) {
            intent = new Intent(intentNameSystem);
        } else {
            intent = new Intent(intentNameApp);
        }
        Bundle bundle = new Bundle();
        intent.addFlags(32);
        bundle.putBundle("DiagMon", new Bundle());
        bundle.getBundle("DiagMon").putBundle("CFailLogUpload", new Bundle());
        bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putString("ServiceID", diagMonConfig.getServiceId());
        bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putBundle("Ext", new Bundle());
        bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("ClientV", DiagMonUtil.getPackageVersion(context2));
        if (!TextUtils.isEmpty(eventBuilder2.getRelayClientType())) {
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("RelayClient", eventBuilder2.getRelayClientType());
        }
        if (!TextUtils.isEmpty(eventBuilder2.getRelayClientVer())) {
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("RelayClientV", eventBuilder2.getRelayClientVer());
        }
        bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("UiMode", "0");
        bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString(XDBInterface.XDM_SQL_LAST_UPDATE_RESULTCODE, eventBuilder2.getErrorCode());
        if (!TextUtils.isEmpty(eventBuilder2.getServiceDefinedKey())) {
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("EventID", eventBuilder2.getServiceDefinedKey());
        }
        try {
            jSONObject.put("SasdkV", "6.05.033");
            jSONObject.put("SdkV", DiagMonUtil.getSdkVersion());
            jSONObject.put("TrackingID", diagMonConfig.getTrackingId());
            jSONObject.put(XDBInterface.XDM_SQL_LAST_UPDATE_DESCRIPTION, eventBuilder2.getDescription());
        } catch (JSONException e) {
            AppLog.e(e.getMessage());
        }
        bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString(XDBInterface.XDM_SQL_LAST_UPDATE_DESCRIPTION, jSONObject.toString());
        if (eventBuilder2.getNetworkMode()) {
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("WifiOnlyFeature", "1");
        } else {
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("WifiOnlyFeature", "0");
        }
        intent.putExtra("uploadMO", bundle);
        intent.setFlags(32);
        AppLog.i("EventObject is generated");
        return intent;
    }

    private int getUid(Context context2) {
        return context2.getApplicationInfo().uid;
    }

    private void sendLogPath() {
        try {
            String legacyAuthority = DiagMonUtil.getLegacyAuthority(this.mConfig.getServiceId());
            ContentResolver contentResolver = this.context.getContentResolver();
            contentResolver.call(Uri.parse("content://" + legacyAuthority), "update_path", this.eventBuilder.getLogPath(), (Bundle) null);
        } catch (Exception e) {
            AppLog.w("fail to send log path: " + e.getMessage());
        }
    }

    private boolean eventReportViaCP(Context context2, DiagMonConfig diagMonConfig, EventBuilder eventBuilder2, Bundle bundle) {
        if (diagMonConfig == null) {
            try {
                AppLog.w("No Configuration");
                AppLog.w("You have to set DiagMonConfiguration");
                return false;
            } catch (Exception | NullPointerException unused) {
                return false;
            }
        } else {
            Bundle makeEventObjAsBundle = makeEventObjAsBundle(context2, diagMonConfig, eventBuilder2);
            if (makeEventObjAsBundle == null) {
                AppLog.w("No EventObject");
                return false;
            } else if (!Validator.isValidMandatoryFields(bundle)) {
                AppLog.w("Invalid SR object");
                return false;
            } else if (!Validator.isValidMandatoryFields(makeEventObjAsBundle)) {
                AppLog.w("Invalid ER object");
                return false;
            } else {
                AppLog.i("Valid SR, ER object");
                AppLog.i("Report your logs");
                AppLog.i("networkMode : " + eventBuilder2.getNetworkMode());
                String collectLogs = collectLogs(context2, eventBuilder2.getLogPath());
                Bundle addParcelFileDescriptor = addParcelFileDescriptor(makeEventObjAsBundle, collectLogs);
                if ("G".equals(addParcelFileDescriptor.getString("serviceAgreeType"))) {
                    bundle.putString("serviceAgreeType", "S");
                    addParcelFileDescriptor.putString("serviceAgreeType", "S");
                }
                DiagMonUtil.printResultfromDMA(context2.getContentResolver().call(DiagMonUtil.URI, "event_report", "eventReport", addParcelFileDescriptor));
                if (collectLogs.isEmpty()) {
                    return true;
                }
                removeZipFile(collectLogs);
                return true;
            }
        }
    }

    private Bundle makeEventObjAsBundle(Context context2, DiagMonConfig diagMonConfig, EventBuilder eventBuilder2) {
        Bundle bundle = new Bundle();
        try {
            bundle.putString("serviceId", diagMonConfig.getServiceId());
            bundle.putString("serviceVersion", diagMonConfig.getServiceVer());
            bundle.putString("serviceDefinedKey", eventBuilder2.getServiceDefinedKey());
            bundle.putString("errorCode", eventBuilder2.getErrorCode());
            bundle.putBoolean(BundleContract.NETWORK_MODE, eventBuilder2.getNetworkMode());
            bundle.putString(BundleContract.DESCRIPTION, eventBuilder2.getDescription());
            bundle.putString("relayClientVersion", eventBuilder2.getRelayClientVer());
            bundle.putString("relayClientType", eventBuilder2.getRelayClientType());
            bundle.putString("extension", eventBuilder2.getExtData());
            bundle.putString("deviceId", diagMonConfig.getDeviceId());
            bundle.putString("serviceAgreeType", diagMonConfig.getAgreeAsString());
            bundle.putString("sdkVersion", DiagMonUtil.getSdkVersion());
            bundle.putString("sdkType", DiagMonUtil.getSdkType(context2));
            bundle.putString("memory", eventBuilder2.getMemory().toString());
            bundle.putString("storage", eventBuilder2.getInternalStorageSize().toString());
            AppLog.d("Generated EventObject");
            return bundle;
        } catch (Exception unused) {
            return null;
        }
    }

    private String collectLogs(Context context2, String str) throws Exception {
        if (!TextUtils.isEmpty(str)) {
            try {
                String str2 = context2.getFilesDir().getAbsolutePath() + "/zip";
                new File(str2).mkdir();
                return ZipHelper.zip(str, str2 + "/" + (System.currentTimeMillis() + ".zip"));
            } catch (Exception e) {
                AppLog.w("Zipping failure");
                AppLog.w("Exception : " + e.getMessage());
                throw e;
            }
        } else {
            AppLog.w("No Log Path, You have to set LogPath to report logs");
            throw new IOException("Not found");
        }
    }

    private Bundle addParcelFileDescriptor(Bundle bundle, String str) {
        ParcelFileDescriptor parcelFileDescriptor;
        try {
            parcelFileDescriptor = ParcelFileDescriptor.open(new File(str), 268435456);
            AppLog.d("Zipping logs is completed");
            AppLog.d("Zipped file size : " + parcelFileDescriptor.getStatSize());
        } catch (Exception e) {
            AppLog.e(e.getMessage());
            parcelFileDescriptor = null;
        }
        bundle.putParcelable(BundleContract.FILE_DESCRIPTOR, parcelFileDescriptor);
        return bundle;
    }

    private void removeZipFile(String str) {
        File file = new File(str);
        if (!file.exists()) {
            return;
        }
        if (file.delete()) {
            AppLog.d("Removed zipFile : " + str);
            return;
        }
        AppLog.d("Couldn't removed zipFile : " + str);
    }
}
