package com.samsung.android.app.twatchmanager.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.samsung.android.app.twatchmanager.log.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HostManagerUtilsNetwork {
    private static final String CSC_PATH = "/system/csc/sales_code.dat";
    private static final String TAG = ("tUHM:" + HostManagerUtilsNetwork.class.getSimpleName());

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0044, code lost:
        if (android.text.TextUtils.isEmpty(r0) != false) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006c, code lost:
        if (android.text.TextUtils.isEmpty(r0) != false) goto L_0x0046;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getCSC() {
        /*
        // Method dump skipped, instructions count: 205
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork.getCSC():java.lang.String");
    }

    private static String getCSCFromCSCPath() {
        File file = new File(CSC_PATH);
        String str = "NONE";
        if (!file.exists()) {
            return str;
        }
        FileInputStream fileInputStream = null;
        try {
            byte[] bArr = new byte[20];
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                if (fileInputStream2.read(bArr) > 0) {
                    str = new String(bArr, 0, 3);
                }
                try {
                    fileInputStream2.close();
                    return str;
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                    return str;
                }
            } catch (Exception unused) {
                fileInputStream = fileInputStream2;
                fileInputStream.close();
                return "FAIL";
            } catch (Throwable th) {
                try {
                    fileInputStream2.close();
                } catch (IOException | NullPointerException e2) {
                    e2.printStackTrace();
                }
                throw th;
            }
        } catch (Exception unused2) {
            fileInputStream.close();
            return "FAIL";
        }
    }

    public static String getMCC(Context context) {
        String simOperator;
        TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService("phone");
        String substring = (telephonyManager == null || (simOperator = telephonyManager.getSimOperator()) == null || simOperator.length() <= 3) ? "" : simOperator.substring(0, 3);
        String str = TAG;
        Log.d(str, "getMCC() mcc : " + substring);
        return substring;
    }

    public static String getMNC(Context context) {
        String simOperator;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        String substring = (telephonyManager == null || (simOperator = telephonyManager.getSimOperator()) == null || simOperator.length() <= 3) ? "" : simOperator.substring(3);
        String str = TAG;
        Log.d(str, "getMNC() mnc : " + substring);
        return substring;
    }

    public static boolean isDataNetworkConnected(Context context) {
        boolean z;
        try {
            z = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo().isConnected();
        } catch (RuntimeException e) {
            e.printStackTrace();
            z = false;
        }
        String str = TAG;
        Log.d(str, "isDataNetworkConnected() isConnected : " + z);
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0050, code lost:
        if (r7.isConnectedOrConnecting() != false) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0058, code lost:
        if (r4.isConnectedOrConnecting() != false) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0048, code lost:
        if (r3.isConnectedOrConnecting() != false) goto L_0x0061;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isNetworkAvailable(android.content.Context r7) {
        /*
        // Method dump skipped, instructions count: 123
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork.isNetworkAvailable(android.content.Context):boolean");
    }

    public static boolean isWifiNetworkConnected(Context context) {
        boolean z = false;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo.getType() == 1 && activeNetworkInfo.isConnected()) {
                z = true;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        String str = TAG;
        Log.d(str, "isWifiNetworkConnected() isConnected : " + z);
        return z;
    }
}
