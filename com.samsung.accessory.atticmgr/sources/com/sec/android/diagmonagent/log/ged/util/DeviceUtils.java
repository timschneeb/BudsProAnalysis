package com.sec.android.diagmonagent.log.ged.util;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.accessorydm.interfaces.XDBInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConstants;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;
import com.sec.android.diagmonagent.log.provider.utils.ZipHelper;
import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceUtils {
    private static final String EXCEPTION_MCC_LIST = "001,002,999,@65";
    public static final String TAG = ("GED_DIAGMON_SDK[" + DiagMonUtil.getSdkVersion() + "]");

    public static String getTmcc(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE);
        if (telephonyManager == null) {
            return null;
        }
        String networkOperator = telephonyManager.getNetworkOperator();
        if (networkOperator.length() > 3) {
            networkOperator = networkOperator.substring(0, 3);
        }
        if (EXCEPTION_MCC_LIST.contains(networkOperator)) {
            return null;
        }
        return networkOperator;
    }

    public static String getSmcc(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE);
        if (telephonyManager == null) {
            return null;
        }
        String simOperator = telephonyManager.getSimOperator();
        if (simOperator.length() > 3) {
            simOperator = simOperator.substring(0, 3);
        }
        if (EXCEPTION_MCC_LIST.contains(simOperator)) {
            return null;
        }
        return simOperator;
    }

    public static JSONObject getDeviceInfo(Context context) {
        try {
            JSONObject simpleDeviceInfo = getSimpleDeviceInfo(context);
            simpleDeviceInfo.put("diagnosticsAgree", FmmConstants.NOT_SUPPORT);
            return simpleDeviceInfo;
        } catch (JSONException unused) {
            return null;
        }
    }

    private static JSONObject getSimpleDeviceInfo(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(Build.MODEL)) {
                jSONObject.put(XDBInterface.XDM_SQL_ACCESSORY_MODEL, Build.MODEL);
            }
            if (!TextUtils.isEmpty(Build.TYPE)) {
                jSONObject.put("binaryType", Build.TYPE);
            }
            if (!TextUtils.isEmpty(Build.FINGERPRINT)) {
                jSONObject.put("fingerprint", Build.FINGERPRINT);
            }
            if (!TextUtils.isEmpty(getTmcc(context))) {
                jSONObject.put("tmcc", getTmcc(context));
            }
            if (!TextUtils.isEmpty(getSmcc(context))) {
                jSONObject.put("smcc", getSmcc(context));
            }
        } catch (JSONException e) {
            AppLog.e("Failed to get device info : " + e.getMessage());
        }
        return jSONObject;
    }

    public static String generateRandomDeviceId(String str) {
        if (!TextUtils.isEmpty(str)) {
            AppLog.d("already set device id by srObj");
            return str;
        }
        SecureRandom secureRandom = new SecureRandom();
        byte[] bArr = new byte[16];
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < 32; i++) {
            secureRandom.nextBytes(bArr);
            try {
                sb.append("0123456789abcdefghijklmjopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int) (Math.abs(new BigInteger(bArr).longValue()) % ((long) 62))));
            } catch (Exception e) {
                AppLog.e("Failed to generate RandomDeviceId : " + e.getMessage());
                return "";
            }
        }
        return sb.toString();
    }

    public static String zipWithMetaFile(Context context, String str, String str2, String str3, String str4, String str5) {
        AppLog.i("zip with meta file");
        String str6 = context.getFilesDir().getAbsoluteFile() + "/" + System.currentTimeMillis();
        try {
            new File(str6).mkdir();
            new File(str).renameTo(new File(str6 + "/logs.zip"));
            makeMetaFile(context, str6, str3, str2, str4, str5);
            String str7 = str6 + ".zip";
            ZipHelper.zip(str6, str7);
            AppLog.d("logPath: " + str7);
            deleteFiles(str6);
            return new File(str7).getName();
        } catch (Exception e) {
            AppLog.e("Fail to zip with meta file : " + e.getMessage());
            deleteFiles(str);
            deleteFiles(str6);
            return "";
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006c, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0071, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0072, code lost:
        r2.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0075, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void makeMetaFile(android.content.Context r2, java.lang.String r3, java.lang.String r4, java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
        // Method dump skipped, instructions count: 129
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.ged.util.DeviceUtils.makeMetaFile(android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public static void deleteFiles(String str) {
        File[] listFiles;
        File file = new File(str);
        if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
            for (File file2 : listFiles) {
                file2.delete();
            }
        }
        file.delete();
    }
}
