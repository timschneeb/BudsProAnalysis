package com.samsung.android.fotaprovider.util;

import android.text.TextUtils;
import com.accessorydm.db.file.AccessoryInfoAdapter;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.deviceinfo.ProviderInfo;

public class OperatorUtil {
    private static final String CHINA_CSC = "CTC/CHN/CHM/CHU/CHC/CHZ";
    private static final String CHINA_MCC = "460";
    private static final String CHINA_OSP_SERVER_URL = "https://chn-fota-dms.samsungdm.com/v1/device/magicsync/mdm";
    private static final String CHINA_PRE_URL = "chn";
    private static final String DEFAULT_PRE_URL = "www";

    public static String replaceToWLAN(int i) {
        if (i == 0 || !isChinaProvider()) {
            return FotaProviderInitializer.getContext().getString(i);
        }
        return FotaProviderInitializer.getContext().getString(i).replace("Wi-Fi", "WLAN");
    }

    public static boolean isSPP() {
        return isChinaProvider();
    }

    public static String getUrlPrefix() {
        return isChinaConsumer() ? CHINA_PRE_URL : DEFAULT_PRE_URL;
    }

    public static String getOspServerUrl(String str) {
        return isChinaConsumer() ? CHINA_OSP_SERVER_URL : str;
    }

    private static boolean isChinaProvider() {
        ProviderInfo providerInfo = new ProviderInfo();
        if (providerInfo.isSamsungDevice()) {
            String salesCode = providerInfo.getSalesCode();
            if (!TextUtils.isEmpty(salesCode) && CHINA_CSC.contains(salesCode)) {
                return true;
            }
        }
        if (CHINA_MCC.equals(providerInfo.getSimMCC()) || CHINA_MCC.equals(providerInfo.getNetworkMCC())) {
            return true;
        }
        return false;
    }

    public static boolean isChinaConsumer() {
        String salesCode = new AccessoryInfoAdapter().getSalesCode();
        return !TextUtils.isEmpty(salesCode) && CHINA_CSC.contains(salesCode);
    }
}
