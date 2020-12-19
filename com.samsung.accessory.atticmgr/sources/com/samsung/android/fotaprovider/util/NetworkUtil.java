package com.samsung.android.fotaprovider.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.android.fotaprovider.log.Log;

public class NetworkUtil {
    private static boolean isNetworkConnected(Context context, int i) {
        if (context == null) {
            Log.W("Context is null");
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            Log.W("ConnectivityManager is null");
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            Log.W("ActiveNetworkInfo is null");
            return false;
        } else if (activeNetworkInfo.getType() == i) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isWiFiNetworkConnected(Context context) {
        if (isNetworkConnected(context, 1)) {
            Log.I("WiFi Network is connected");
            return true;
        }
        Log.I("WiFi Network is disconnected");
        return false;
    }

    public static boolean isMobileNetworkConnected(Context context) {
        if (isNetworkConnected(context, 0)) {
            Log.I("Mobile Network is connected");
            return true;
        }
        Log.I("Mobile Network is disconnected");
        return false;
    }

    public static boolean isAnyNetworkConnected(Context context) {
        return isWiFiNetworkConnected(context) || isMobileNetworkConnected(context);
    }

    public static boolean isRoamingNetworkConnected(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE);
        return telephonyManager != null && telephonyManager.isNetworkRoaming();
    }
}
