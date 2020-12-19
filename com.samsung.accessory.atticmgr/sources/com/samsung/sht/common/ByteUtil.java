package com.samsung.sht.common;

import com.samsung.sht.log.ShtLog;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteUtil {
    private static final String TAG = ByteUtil.class.getSimpleName();
    private static final Boolean TRACE_DEBUG = false;

    public static byte[] intToBytes(int i) {
        return new byte[]{(byte) i, (byte) (i >> 8)};
    }

    public static byte[] shortToBytes(short s) {
        return new byte[]{(byte) s, (byte) (s >> 8)};
    }

    public static int byteBufferToInt(byte[] bArr, int i, int i2, boolean z) {
        int i3;
        if (bArr == null) {
            if (!TRACE_DEBUG.booleanValue()) {
                return -1;
            }
            ShtLog.d(TAG, "byteBufferToInt : null");
            return -1;
        } else if (bArr.length >= i + i2) {
            int i4 = 0;
            if (z) {
                i3 = 0;
                while (i4 < i2) {
                    i3 += (bArr[i + i4] & 255) << (((i2 - 1) - i4) * 8);
                    i4++;
                }
            } else {
                i3 = 0;
                while (i4 < i2) {
                    i3 += (bArr[i + i4] & 255) << (i4 * 8);
                    i4++;
                }
            }
            return i3;
        } else if (!TRACE_DEBUG.booleanValue()) {
            return -2;
        } else {
            ShtLog.d(TAG, "byteBufferToInt : invalid Length " + bArr.length + " / " + (i + 4));
            return -2;
        }
    }

    public static long byteBufferToLong(byte[] bArr, int i, int i2, boolean z) {
        if (bArr == null) {
            if (!TRACE_DEBUG.booleanValue()) {
                return -1;
            }
            ShtLog.d(TAG, "byteBufferToInt : null");
            return -1;
        } else if (bArr.length >= i + i2) {
            long j = 0;
            int i3 = 0;
            if (z) {
                while (i3 < i2) {
                    j += (((long) bArr[i + i3]) & 255) << (((i2 - 1) - i3) * 8);
                    i3++;
                }
            } else {
                while (i3 < i2) {
                    j += (((long) bArr[i + i3]) & 255) << (i3 * 8);
                    i3++;
                }
            }
            return j;
        } else if (!TRACE_DEBUG.booleanValue()) {
            return -2;
        } else {
            String str = TAG;
            ShtLog.d(str, "byteBufferToInt : invalid Length " + bArr.length + " / " + (i + 4));
            return -2;
        }
    }

    public static String byteBufferToCharString(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return new String(bArr2);
    }

    public static String byteBufferToString(byte[] bArr, int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = i; i3 < i + i2; i3++) {
            stringBuffer.append(String.format("%02x", Byte.valueOf(bArr[i3])));
        }
        return stringBuffer.toString();
    }

    public static String byteBufferToString(byte[] bArr, int i) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append(String.format("%02x", Byte.valueOf(bArr[i2])));
        }
        return stringBuffer.toString();
    }

    public static String byteToString(byte b) {
        StringBuilder sb = new StringBuilder(2);
        sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        return sb.toString();
    }

    public static byte[] hexStrToByteBuffer(String str) {
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public static byte[] longToBytebuffer(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) ((int) (255 & j));
            j >>= 8;
        }
        return bArr;
    }

    public static byte[] intToBytebuffer(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) (i & 255);
            i >>= 8;
        }
        return bArr;
    }

    public static byte[] floatToBytes(float f) {
        byte[] bArr = new byte[4];
        int floatToIntBits = Float.floatToIntBits(f);
        for (int i = 0; i < 4; i++) {
            bArr[i] = (byte) ((floatToIntBits >> (i * 8)) & 255);
        }
        return bArr;
    }

    public static byte[] intToBytes_LE(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            bArr[i2] = (byte) (i % 256);
            i >>= 8;
        }
        return bArr;
    }

    public static byte[] longToBytes(long j) {
        byte[] bArr = new byte[4];
        for (int i = 0; i < 4; i++) {
            bArr[i] = (byte) ((int) (j % 256));
            j >>= 8;
        }
        return bArr;
    }

    public static float bytesToFloat(byte[] bArr, int i, int i2, ByteOrder byteOrder) {
        if (i2 > 4) {
            ShtLog.e(TAG, "bytesToFloat> INVALID LENGTH!.");
            return 0.0f;
        }
        byte[] bArr2 = {0, 0, 0, 0};
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bytesToFloat(bArr2, byteOrder);
    }

    public static float bytesToFloat(byte[] bArr, ByteOrder byteOrder) {
        if (bArr != null) {
            return ByteBuffer.wrap(bArr).order(byteOrder).getFloat();
        }
        return 0.0f;
    }

    public static double bytesToDouble(byte[] bArr, int i, int i2, ByteOrder byteOrder) {
        if (i2 > 8) {
            ShtLog.e(TAG, "bytesToFloat> INVALID LENGTH!.");
            return 0.0d;
        }
        byte[] bArr2 = {0, 0, 0, 0, 0, 0, 0, 0};
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bytesToDouble(bArr2, byteOrder);
    }

    public static double bytesToDouble(byte[] bArr, ByteOrder byteOrder) {
        if (bArr != null) {
            return ByteBuffer.wrap(bArr).order(byteOrder).getDouble();
        }
        return 0.0d;
    }

    public static void reverseBytes(byte[] bArr) {
        int length = bArr.length;
        for (int i = 0; i < length / 2; i++) {
            byte b = bArr[i];
            int i2 = (length - 1) - i;
            bArr[i] = bArr[i2];
            bArr[i2] = b;
        }
    }

    public static void convertBTAddrFromSnoopFormat(byte[] bArr) {
        if (bArr == null || bArr.length != 6) {
            ShtLog.w(TAG, "convertBTAddrFromSnoopFormat. INVALID PARAMS.");
            return;
        }
        int length = bArr.length / 2;
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            byte b = bArr[i2];
            int i3 = i2 + 1;
            bArr[i2] = bArr[i3];
            bArr[i3] = b;
        }
    }

    public static boolean compareBytes(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        if (bArr == null || bArr2 == null || i3 == 0) {
            ShtLog.w(TAG, "ERROR: input params were invalid!" + bArr + ", " + bArr2 + ", " + i3);
            return false;
        } else if (bArr.length < i + i3) {
            ShtLog.w(TAG, "ERROR: length of src1 were invalid! (" + bArr.length + ", " + i + ", " + i3 + ")");
            return false;
        } else if (bArr2.length < i2 + i3) {
            ShtLog.w(TAG, "ERROR: length of src2 were invalid! (" + bArr2.length + ", " + i2 + ", " + i3 + ")");
            return false;
        } else {
            for (int i4 = 0; i4 < i3; i4++) {
                int i5 = i + i4;
                int i6 = i2 + i4;
                if (bArr[i5] != bArr2[i6]) {
                    ShtLog.w(TAG, "ERROR: mismatch(" + i4 + "). src1(" + ((int) bArr[i5]) + ") src2(" + ((int) bArr2[i6]) + ")");
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean compareString(String str, int i, String str2, int i2, int i3) {
        if (str == null || str2 == null || i3 == 0) {
            ShtLog.w(TAG, "ERROR: input params were invalid!" + str + ", " + str2 + ", " + i3);
            return false;
        } else if (str.length() < i + i3) {
            ShtLog.w(TAG, "ERROR: length of src were invalid! (" + str.length() + ", " + i + ", " + i3 + ")");
            return false;
        } else if (str2.length() < i2 + i3) {
            ShtLog.w(TAG, "ERROR: length of des were invalid! (" + str2.length() + ", " + i2 + ", " + i3 + ")");
            return false;
        } else {
            for (int i4 = 0; i4 < i3; i4++) {
                int i5 = i + i4;
                int i6 = i2 + i4;
                if (str.charAt(i5) != str2.charAt(i6)) {
                    ShtLog.w(TAG, "ERROR: mismatch(" + i4 + "). src1(" + str.charAt(i5) + ") src2(" + str2.charAt(i6) + ")");
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean compareStringIgnoreCase(String str, int i, String str2, int i2, int i3) {
        if (str == null || str2 == null || i3 == 0) {
            String str3 = TAG;
            ShtLog.w(str3, "ERROR: input params were invalid!" + str + ", " + str2 + ", " + i3);
            return false;
        } else if (str.length() < i + i3) {
            String str4 = TAG;
            ShtLog.w(str4, "ERROR: length of src were invalid! (" + str.length() + ", " + i + ", " + i3 + ")");
            return false;
        } else if (str2.length() < i2 + i3) {
            String str5 = TAG;
            ShtLog.w(str5, "ERROR: length of des were invalid! (" + str2.length() + ", " + i2 + ", " + i3 + ")");
            return false;
        } else {
            StringBuilder sb = new StringBuilder(str);
            StringBuilder sb2 = new StringBuilder(str2);
            String substring = sb.substring(i, i3);
            String substring2 = sb2.substring(i2, i3);
            if (substring == null || substring2 == null) {
                String str6 = TAG;
                ShtLog.w(str6, "ERROR: newSrc/Des is null! (" + substring + ", " + substring2 + ")");
                return false;
            } else if (substring.equalsIgnoreCase(substring2)) {
                return true;
            } else {
                String str7 = TAG;
                ShtLog.w(str7, "ERROR: mismatch. newSrc(" + substring + ") newDes(" + substring2 + ")");
                return false;
            }
        }
    }

    public static byte[] xorBytes(byte[] bArr, byte[] bArr2, int i) {
        if (bArr == null || bArr2 == null || i == 0 || bArr.length > i || bArr2.length > i) {
            ShtLog.w(TAG, "INVALID DATA!");
            return null;
        }
        byte[] bArr3 = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr3[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
        return bArr3;
    }

    public static boolean checkHexStr(String str, int i) {
        if (i == 0 || str == null || str.length() < i) {
            return false;
        }
        for (int i2 = 0; i2 < i; i2++) {
            char charAt = str.charAt(i2);
            if ((charAt < '0' || charAt > '9') && ((charAt < 'A' || charAt > 'F') && (charAt < 'a' || charAt > 'f'))) {
                return false;
            }
        }
        return true;
    }
}
