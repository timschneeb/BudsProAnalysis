package com.accessorydm.adapter;

import android.text.TextUtils;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.db.file.AccessoryInfoAdapter;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.android.fotaprovider.deviceinfo.ProviderInfo;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.NetworkUtil;

public class XDMDevinfAdapter implements XDMDefInterface, XDMInterface, XFOTAInterface {
    private static final String DEFAULT_DEVID_VALUE = "Default";
    private static final String DEFAULT_NULL_DEVID_VALUE = "000000000000000";
    private static final String DEFAULT_NULL_DEVID_VALUE2 = "B0000000";

    public static String xdmDevAdpGetManufacturer() {
        return "Samsung";
    }

    public static String xdmDevAdpGetDeviceID() {
        String deviceId = new AccessoryInfoAdapter().getDeviceId();
        if (deviceId.contains("TWID:") || deviceId.contains("MEID:") || deviceId.contains("IMEI:")) {
            return deviceId.substring(5);
        }
        return "";
    }

    public static String xdmDevAdpGetFullDeviceID() {
        return new AccessoryInfoAdapter().getDeviceId();
    }

    public static String xdmDevAdpGetModel() {
        return new AccessoryInfoAdapter().getModelNumber();
    }

    public static String xdmDevAdpGetLanguage() {
        String xdmGetTargetLanguage = XDMTargetAdapter.xdmGetTargetLanguage();
        return TextUtils.isEmpty(xdmGetTargetLanguage) ? XDMInterface.XDM_DEVINFO_DEFAULT_LANG : xdmGetTargetLanguage;
    }

    public static String xdmDevAdpGetTelephonyMcc() {
        if (XDMFeature.XDM_FEATURE_WIFI_ONLY_MODEL) {
            return "";
        }
        String networkMCC = new ProviderInfo().getNetworkMCC();
        Log.H("Network MCC: " + networkMCC);
        return networkMCC;
    }

    public static String xdmDevAdpGetTelephonyMnc() {
        if (XDMFeature.XDM_FEATURE_WIFI_ONLY_MODEL) {
            return "";
        }
        String networkMNC = new ProviderInfo().getNetworkMNC();
        Log.H("Network MNC: " + networkMNC);
        return networkMNC;
    }

    public static String xdmDevAdpGetAppVersion() {
        return new ProviderInfo().getAppVersion();
    }

    public static String xdmDevAdpGetFirmwareVersion() {
        return new AccessoryInfoAdapter().getFirmwareVersion();
    }

    public static String xdmDevAdpGetSalesCode() {
        return new ProviderInfo().getSalesCode();
    }

    public static String xdmDevAdpGetHttpUserAgent() {
        return String.format("%s %s %s", xdmDevAdpGetManufacturer(), xdmDevAdpGetModel(), HttpNetworkInterface.XTP_HTTP_DM_USER_AGENT);
    }

    public static boolean xdmDevAdpBatteryLifeCheck() {
        int xdmGetIntFromFile = xdmGetIntFromFile("/sys/class/power_supply/battery/capacity");
        int minimumBatteryLevel = AccessoryController.getInstance().getAccessoryUtil().getMinimumBatteryLevel();
        Log.I("battery level [" + xdmGetIntFromFile + "], Minimum Level [" + minimumBatteryLevel + "]\n");
        return xdmGetIntFromFile >= minimumBatteryLevel;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0059 A[SYNTHETIC, Splitter:B:25:0x0059] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int xdmGetIntFromFile(java.lang.String r6) {
        /*
        // Method dump skipped, instructions count: 102
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.adapter.XDMDevinfAdapter.xdmGetIntFromFile(java.lang.String):int");
    }

    public static boolean xdmDevAdpVerifyDevID() {
        String xdmDevAdpGetDeviceID = xdmDevAdpGetDeviceID();
        return !TextUtils.isEmpty(xdmDevAdpGetDeviceID) && !DEFAULT_NULL_DEVID_VALUE.equals(xdmDevAdpGetDeviceID) && !DEFAULT_NULL_DEVID_VALUE2.equals(xdmDevAdpGetDeviceID) && !DEFAULT_DEVID_VALUE.equals(xdmDevAdpGetDeviceID);
    }

    public static boolean xdmBlocksDueToRoamingNetwork() {
        if (!XDMDmUtils.getInstance().XDM_ROAMING_CHECK || !NetworkUtil.isRoamingNetworkConnected(XDMDmUtils.getContext()) || NetworkUtil.isWiFiNetworkConnected(XDMDmUtils.getContext())) {
            return false;
        }
        Log.I("Roaming & WiFi-Disconnected. return true");
        return true;
    }
}
