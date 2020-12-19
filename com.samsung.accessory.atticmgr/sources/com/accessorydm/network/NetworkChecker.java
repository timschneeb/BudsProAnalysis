package com.accessorydm.network;

import android.content.Context;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.adapter.XDMDevinfAdapter;
import com.accessorydm.adapter.XDMFeature;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBFumoAdp;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.NetworkUtil;
import com.samsung.android.fotaprovider.util.type.DeviceType;

public enum NetworkChecker {
    DM {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.network.NetworkChecker
        public boolean downloadViaWifiOnly() {
            return false;
        }
    },
    DL {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.network.NetworkChecker
        public boolean downloadViaWifiOnly() {
            if (DeviceType.get().isSupportWifiOnlyFlagByServer()) {
                return XDBFumoAdp.xdbGetFUMOWifiOnlyDownload();
            }
            return false;
        }
    };

    /* access modifiers changed from: package-private */
    public abstract boolean downloadViaWifiOnly();

    public static NetworkChecker get() {
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        if (xdbGetFUMOStatus == 30 || xdbGetFUMOStatus == 200) {
            Log.I("get DL network checker");
            return DL;
        }
        Log.I("get DM network checker");
        return DM;
    }

    public NetworkBlockedType getNetworkBlockType() {
        Log.I("");
        Context context = XDMDmUtils.getContext();
        if (NetworkUtil.isWiFiNetworkConnected(context)) {
            Log.I("Wi-Fi connected: no block");
            return NetworkBlockedType.NO_BLOCKING;
        } else if (XDMFeature.XDM_FEATURE_WIFI_ONLY_MODEL || XDB.xdbGetWifiOnlyFlag() || downloadViaWifiOnly()) {
            Log.I("Wi-Fi disconnected: Wi-Fi only model / checked Wi-Fi only menu / Wi-Fi only flag");
            return NetworkBlockedType.WIFI_DISCONNECTED;
        } else if (!NetworkUtil.isMobileNetworkConnected(context)) {
            Log.I("Wi-Fi and mobile network disconnected");
            return NetworkBlockedType.NETWORK_DISCONNECTED;
        } else if (!XDMDevinfAdapter.xdmBlocksDueToRoamingNetwork()) {
            return NetworkBlockedType.NO_BLOCKING;
        } else {
            Log.I("Wi-Fi disconnected: roaming network");
            return NetworkBlockedType.ROAMING;
        }
    }
}
