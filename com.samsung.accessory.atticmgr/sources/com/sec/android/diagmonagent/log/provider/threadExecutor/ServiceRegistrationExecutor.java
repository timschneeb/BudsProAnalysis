package com.sec.android.diagmonagent.log.provider.threadExecutor;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.db.DataController;
import com.sec.android.diagmonagent.log.provider.DiagMonConfig;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;
import com.sec.android.diagmonagent.log.provider.utils.Validator;
import java.util.concurrent.TimeUnit;

public class ServiceRegistrationExecutor implements Runnable {
    private final long MIN_WAITING_TIME = TimeUnit.HOURS.toMillis(6);
    private final String PREF_DIAGMON_NAME = "diagmon_pref";
    private final String PREF_DIAGMON_TIMESTAMP = "diagmon_timestamp";
    private Context context;
    private DiagMonConfig mConfig;
    private Bundle srObj;

    public ServiceRegistrationExecutor(DiagMonConfig diagMonConfig, Bundle bundle) {
        this.context = diagMonConfig.getContext();
        this.mConfig = diagMonConfig;
        this.srObj = bundle;
    }

    public void run() {
        int checkDMA = DiagMonUtil.checkDMA(this.context);
        if (checkDMA == 0) {
            AppLog.w("Not installed DMA");
            AppLog.w("SetConfiguration is aborted");
        } else if (checkDMA != 1) {
            if (checkDMA == 2) {
                long currentTimeMillis = System.currentTimeMillis();
                long prefDiagmonTimestamp = getPrefDiagmonTimestamp();
                if (!isTestMode() && currentTimeMillis <= prefDiagmonTimestamp + this.MIN_WAITING_TIME) {
                    return;
                }
                if (checkAuthority(this.mConfig.getServiceId(), checkDMA)) {
                    setPrefDiagmonTimestamp(currentTimeMillis);
                    if (Validator.isValidMandatoryFields(this.srObj)) {
                        if ("G".equals(this.srObj.getString("serviceAgreeType"))) {
                            this.srObj.putString("serviceAgreeType", "S");
                        }
                        sendSRObj();
                        return;
                    }
                    Log.w(DiagMonUtil.TAG, "Invalid SR object");
                    return;
                }
                AppLog.w("Authority check got failed");
            } else if (checkDMA != 3) {
                AppLog.w("Exceptional case");
                AppLog.w("SetConfiguration is aborted");
            } else if (Validator.isValidMandatoryFields(this.srObj)) {
                DataController.sendSRObj(this.context, this.srObj);
            } else {
                Log.w(DiagMonUtil.TAG, "Invalid SR object");
            }
        } else if (!Validator.isValidLegacyConfig(this.mConfig)) {
            AppLog.w("Invalid DiagMonConfiguration");
            AppLog.w("SetConfiguration is aborted");
        } else {
            sendLegacySRObj();
            AppLog.i("Valid DiagMonConfiguration");
        }
    }

    private long getPrefDiagmonTimestamp() {
        return this.context.getSharedPreferences("diagmon_pref", 0).getLong("diagmon_timestamp", 0);
    }

    private void setPrefDiagmonTimestamp(long j) {
        SharedPreferences.Editor edit = this.context.getSharedPreferences("diagmon_pref", 0).edit();
        edit.putLong("diagmon_timestamp", j);
        edit.apply();
    }

    private boolean checkAuthority(String str, int i) {
        if (i != 2) {
            return true;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putString("serviceId", str);
            this.context.getContentResolver().call(DiagMonUtil.URI, "request_deviceid", "request_deviceid", bundle);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private void sendSRObj() {
        try {
            AppLog.i("Request Service Registration");
            DiagMonUtil.printResultfromDMA(this.context.getContentResolver().call(DiagMonUtil.URI, "register_service", "registration", this.srObj));
        } catch (Exception unused) {
            AppLog.w("fail to send SR obj");
        }
    }

    private void sendLegacySRObj() {
        try {
            String legacyAuthority = DiagMonUtil.getLegacyAuthority(this.mConfig.getServiceId());
            Bundle bundle = new Bundle();
            bundle.putString("deviceId", this.mConfig.getDeviceId());
            bundle.putBoolean("serviceAgreeType", this.mConfig.getAgree());
            bundle.putString("serviceId", legacyAuthority);
            ContentResolver contentResolver = this.context.getContentResolver();
            contentResolver.call(Uri.parse("content://" + legacyAuthority), "service_registration", (String) null, bundle);
        } catch (Exception e) {
            AppLog.w("fail to send SR obj: " + e.getMessage());
        }
    }

    private boolean isTestMode() {
        return this.mConfig.getServiceId().equals("on6ox5scyf") && Build.TYPE.equals("eng");
    }
}
