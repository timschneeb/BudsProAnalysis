package com.accessorydm.adapter;

import com.accessorydm.XDMDmUtils;
import com.accessorydm.tp.urlconnect.HttpNetworkInfo;
import com.samsung.android.fotaprovider.log.Log;

public class XDMFeature {
    public static boolean XDM_FEATURE_WIFI_ONLY_MODEL = false;

    public static void xdmInitialize() {
        if (XDMTargetAdapter.xdmGetCheckWifiOnlyModel()) {
            XDM_FEATURE_WIFI_ONLY_MODEL = true;
        }
        Log.H("XDM_FEATURE_WIFI_ONLY_MODEL : " + XDM_FEATURE_WIFI_ONLY_MODEL);
    }

    public static void xdmSetPrefConfig() {
        XDMDmUtils.getInstance().XDM_ROAMING_CHECK = xdmGetRoamingCheckFeature();
        HttpNetworkInfo.getInstance().setSSLCheck(xdmGetSSLCheckFeature());
    }

    public static boolean xdmGetRoamingCheckFeature() {
        boolean z = XDMCommonUtils.xdmGetRoamingPrefValue() == 0;
        Log.I("Roaming Check : " + z);
        return z;
    }

    public static boolean xdmGetSSLCheckFeature() {
        if (XDMCommonUtils.xdmGetSSLPrefValue() == 0) {
            Log.I("SSL Check On");
            return true;
        }
        Log.I("SSL Check Off");
        return false;
    }

    public static boolean xdmGetValidationCheckFeature() {
        if (XDMCommonUtils.xdmGetValidationPrefValue() == 0) {
            Log.I("validation Check On");
            return true;
        }
        Log.I("validation Check Off");
        return false;
    }
}
