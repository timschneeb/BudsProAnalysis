package com.accessorydm.eng.core;

import android.text.TextUtils;

public class XDMMem {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static int xdmLibHexToChar(int i) {
        if (i >= 0 && i <= 9) {
            return i + 48;
        }
        if (10 > i || i > 15) {
            return 63;
        }
        return (i + 65) - 10;
    }

    public static boolean xdmLibIsSpace(char c) {
        return c == ' ' || c == '\f' || c == '\n' || c == '\r' || c == '\t';
    }

    public static String xdmLibStrsplit(char[] cArr, char c, char[] cArr2) {
        if (cArr == null || cArr.length == 0) {
            return null;
        }
        for (int i = 0; i < cArr.length; i++) {
            if (cArr[i] == c) {
                cArr2[i] = 0;
                int i2 = i + 1;
                char[] cArr3 = new char[(cArr.length - i2)];
                for (int i3 = 0; i3 < cArr.length - i2; i3++) {
                    cArr3[i3] = cArr[i3 + i + 1];
                }
                return String.valueOf(cArr3);
            }
            cArr2[i] = cArr[i];
        }
        return null;
    }

    public static String xdmLibStrstr(String str, String str2) {
        int indexOf;
        if (!TextUtils.isEmpty(str) && (indexOf = str.indexOf(str2)) != -1) {
            return str.substring(indexOf);
        }
        return null;
    }

    public static int xdmLibStrncmp(String str, String str2, int i) {
        if (i < str.length()) {
            str = str.substring(0, str2.length());
        }
        if (str.compareTo(str2) == 0) {
            return 0;
        }
        return 1;
    }

    public static String xdmLibStrrchr(String str, char c) {
        return str.substring(0, str.lastIndexOf(c));
    }

    public static String xdmLibCharToString(char[] cArr) {
        if (cArr.length <= 0) {
            return null;
        }
        int i = 0;
        while (cArr[i] != 0 && cArr.length > i) {
            i++;
        }
        char[] cArr2 = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr2[i2] = cArr[i2];
        }
        return String.valueOf(cArr2);
    }

    public static String xdmLibBytesToHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            sb.append("0123456789abcdef".charAt((bArr[i] >> 4) & 15));
            sb.append("0123456789abcdef".charAt(bArr[i] & 15));
        }
        return sb.toString();
    }

    public static String xdmLibToHexString(byte[] bArr, int i, int i2) {
        char[] cArr = new char[(i2 * 2)];
        int i3 = 0;
        int i4 = 0;
        for (int i5 = i; i5 < i + i2; i5++) {
            byte b = bArr[i5];
            int i6 = i4 + 1;
            char[] cArr2 = HEX_DIGITS;
            cArr[i4] = cArr2[(b >>> 4) & 15];
            i4 = i6 + 1;
            cArr[i6] = cArr2[b & 15];
        }
        while (i3 < cArr.length && cArr[i3] <= '0') {
            i3++;
        }
        if (i3 == cArr.length) {
            return new String("0");
        }
        return String.valueOf(cArr, i3, cArr.length - i3);
    }
}
