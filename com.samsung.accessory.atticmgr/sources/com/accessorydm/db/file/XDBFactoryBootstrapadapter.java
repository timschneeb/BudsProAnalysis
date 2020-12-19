package com.accessorydm.db.file;

import android.text.TextUtils;
import com.accessorydm.eng.core.XDMMem;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.android.fotaprovider.log.Log;
import java.nio.charset.Charset;
import java.security.MessageDigest;

public class XDBFactoryBootstrapadapter {
    private static byte[] szDict = {1, 15, 5, 11, 19, 28, 23, 47, 35, 44, 2, 14, 6, 10, 18, 13, 22, 26, 32, 47, 3, 13, 7, 9, 17, 30, 21, 25, 33, 45, 4, 12, 8, 63, 16, 31, 20, 24, MsgID.DEBUG_SKU, 46};
    private static final char[] xdb_hexTable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* JADX WARNING: Code restructure failed: missing block: B:1:0x0008, code lost:
        if (r1 == 0) goto L_0x000a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        if (r2 >= r0) goto L_0x001f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000c, code lost:
        r3 = r5.charAt(r2);
        r5.deleteCharAt(r2);
        r4 = r0 - r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0015, code lost:
        if (r1 != 0) goto L_0x0019;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0017, code lost:
        r4 = r4 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0019, code lost:
        r5.insert(r4, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001c, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001f, code lost:
        return r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.StringBuffer xdbFBAdpShuffle(java.lang.StringBuffer r5) {
        /*
            int r0 = r5.length()
            int r1 = r0 % 2
            int r2 = r0 / 2
            if (r1 != 0) goto L_0x001c
        L_0x000a:
            if (r2 >= r0) goto L_0x001f
            char r3 = r5.charAt(r2)
            r5.deleteCharAt(r2)
            int r4 = r0 - r2
            if (r1 != 0) goto L_0x0019
            int r4 = r4 + -1
        L_0x0019:
            r5.insert(r4, r3)
        L_0x001c:
            int r2 = r2 + 1
            goto L_0x000a
        L_0x001f:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.file.XDBFactoryBootstrapadapter.xdbFBAdpShuffle(java.lang.StringBuffer):java.lang.StringBuffer");
    }

    public static char[] xdbFBAdpEncodeHex(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            char[] cArr2 = xdb_hexTable;
            cArr[i] = cArr2[b & 15];
            i = i2 + 1;
            cArr[i2] = cArr2[(b >>> 4) & 15];
        }
        return cArr;
    }

    private static String xdbFBAdpOspGenerateDevPwdKey(String str) {
        char[] cArr = new char[64];
        String substring = str.substring(str.indexOf(58) + 1);
        int length = substring.length();
        if (length == 0) {
            return null;
        }
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (Character.isLetterOrDigit(substring.charAt(i2))) {
                cArr[i] = substring.charAt(i2);
                i++;
            }
        }
        long j = 0;
        long j2 = 0;
        for (int i3 = 0; i3 < i - 1; i3++) {
            long j3 = (long) cArr[i3];
            byte[] bArr = szDict;
            j += j3 * ((long) bArr[i3]);
            j2 += ((long) cArr[i3]) * ((long) cArr[(i - i3) - 1]) * ((long) bArr[i3]);
        }
        return "".concat(String.valueOf(j)).concat(String.valueOf(j2));
    }

    public static String xdbFBAdpOspGenerateDevicePwd(String str, String str2) {
        String xdbFBAdpOspGenerateDevPwdKey = xdbFBAdpOspGenerateDevPwdKey(str);
        if (TextUtils.isEmpty(xdbFBAdpOspGenerateDevPwdKey)) {
            return null;
        }
        byte[] bytes = (str2 + xdbFBAdpOspGenerateDevPwdKey + str).getBytes(Charset.defaultCharset());
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.reset();
            char[] xdbFBAdpEncodeHex = xdbFBAdpEncodeHex(instance.digest(bytes));
            int length = str.length();
            bytes[0] = str.getBytes(Charset.defaultCharset())[length - 2];
            bytes[1] = str.getBytes(Charset.defaultCharset())[length - 1];
            String concat = String.valueOf(new char[]{xdbFBAdpEncodeHex[1], xdbFBAdpEncodeHex[4], xdbFBAdpEncodeHex[5], xdbFBAdpEncodeHex[7]}).concat(XDMMem.xdmLibCharToString(new XDBCrypt().xdbCryptGenerate(str, bytes).toCharArray()));
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(concat);
            for (int i = 0; i < 3; i++) {
                stringBuffer = xdbFBAdpShuffle(stringBuffer);
            }
            return new String(stringBuffer);
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }
}
