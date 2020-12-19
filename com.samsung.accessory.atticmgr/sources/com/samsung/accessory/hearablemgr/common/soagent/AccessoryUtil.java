package com.samsung.accessory.hearablemgr.common.soagent;

import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import seccompat.android.util.Log;

public class AccessoryUtil {
    public static final String API_SERVER = "https://dir-apis.samsungdm.com";
    public static final String API_TEST_SERVER = "http://dir-stg-apis.samsungdiroute.net:8080";
    private static final String TAG = (Application.TAG_ + AccessoryUtil.class.getSimpleName());
    private static byte[] clientPasswordDict = {1, 15, 5, 11, 19, 28, 23, 47, 35, 44, 2, 14, 6, 10, 18, 13, 22, 26, 32, 47, 3, 13, 7, 9, 17, 30, 21, 25, 33, 45, 4, 12, 8, 63, 16, 31, 20, 24, MsgID.DEBUG_SKU, 46};

    public static String getServerID() {
        return "fscw5mpc23";
    }

    public static String getServerPassword() {
        return "99Q2ang60Wqx83cc2";
    }

    public static String convertId(String str) {
        if (str == null) {
            Log.d(TAG, "null ID");
            return "";
        }
        int length = str.length();
        byte[] bytes = str.getBytes(Charset.defaultCharset());
        long j = 0;
        long j2 = 0;
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (i >= i2) {
                break;
            }
            byte[] bArr = clientPasswordDict;
            j += ((long) bytes[i]) * ((long) bArr[i]);
            j2 += ((long) bytes[i]) * ((long) bytes[i2 - i]) * ((long) bArr[i]);
            i++;
        }
        String str2 = String.valueOf(j) + String.valueOf(j2);
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.reset();
            String str3 = getServerID() + str2 + str;
            byte[] digest = instance.digest(str3.getBytes(Charset.defaultCharset()));
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(Integer.toHexString((digest[0] >> 4) & 15));
            stringBuffer.append(Integer.toHexString(digest[2] & 15));
            stringBuffer.append(Integer.toHexString((digest[2] >> 4) & 15));
            stringBuffer.append(Integer.toHexString((digest[3] >> 4) & 15));
            stringBuffer.append(UnixCrypt.crypt(str.getBytes(Charset.defaultCharset()), str.substring(length - 2) + str3.substring(2)));
            int length2 = stringBuffer.length();
            int i3 = length2 % 2;
            int i4 = (length2 / 2) + i3;
            for (int i5 = 0; i5 < 3; i5++) {
                for (int i6 = i4; i6 < length2; i6++) {
                    char charAt = stringBuffer.charAt(i6);
                    int i7 = ((length2 - i6) - 1) + i3;
                    for (int i8 = i6; i8 > i7; i8--) {
                        stringBuffer.setCharAt(i8, stringBuffer.charAt(i8 - 1));
                    }
                    stringBuffer.setCharAt(i7, charAt);
                }
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.d(TAG, "convert fail : " + str);
            return "";
        }
    }
}
