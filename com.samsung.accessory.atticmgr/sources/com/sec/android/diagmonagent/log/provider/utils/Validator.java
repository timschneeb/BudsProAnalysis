package com.sec.android.diagmonagent.log.provider.utils;

import android.os.Bundle;
import android.text.TextUtils;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.provider.DiagMonConfig;
import com.sec.android.diagmonagent.log.provider.EventBuilder;
import java.io.File;

public class Validator {
    public static boolean isValidLegacyConfig(DiagMonConfig diagMonConfig) {
        if (TextUtils.isEmpty(diagMonConfig.getServiceId())) {
            AppLog.w("Service ID has to be set");
            return false;
        } else if (diagMonConfig.getAgree()) {
            return true;
        } else {
            AppLog.w("You have to agree to terms and conditions");
            return false;
        }
    }

    public static boolean isValidMandatoryFields(Bundle bundle) {
        if (bundle.getString("serviceId", "").isEmpty()) {
            AppLog.w("Service ID has to be set");
            return false;
        } else if (bundle.getString("serviceVersion", "").isEmpty()) {
            AppLog.w("No service version");
            return false;
        } else if (bundle.getString("sdkVersion", "").isEmpty()) {
            AppLog.w("No SDK version");
            return false;
        } else if (bundle.getString("sdkType", "").isEmpty()) {
            AppLog.w("No SDK type");
            return false;
        } else if (bundle.getString("serviceAgreeType", "").isEmpty()) {
            AppLog.w("You have to agree to terms and conditions");
            return false;
        } else {
            String string = bundle.getString("serviceAgreeType", "");
            AppLog.i("Agreement value: " + string);
            if (!DiagMonUtil.AGREE_TYPE_DIAGNOSTIC.equals(string) && !"S".equals(string) && !"G".equals(string)) {
                AppLog.w("Undefined agreement: " + string);
                return false;
            } else if (!DiagMonUtil.AGREE_TYPE_DIAGNOSTIC.equals(string) || bundle.getString("deviceId", "").isEmpty()) {
                return true;
            } else {
                AppLog.w("You can't use setDeviceId API if you used setAgree as Diagnostic agreement");
                return false;
            }
        }
    }

    public static boolean isValidLegacyEventBuilder(EventBuilder eventBuilder) {
        if (!TextUtils.isEmpty(eventBuilder.getErrorCode())) {
            return true;
        }
        AppLog.w("No Result code - you have to set");
        return false;
    }

    public static boolean isValidLogPath(String str) {
        File[] listFiles;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (!file.isDirectory() || (listFiles = file.listFiles()) == null) {
            return false;
        }
        if (listFiles.length >= 1) {
            return true;
        }
        return false;
    }
}
