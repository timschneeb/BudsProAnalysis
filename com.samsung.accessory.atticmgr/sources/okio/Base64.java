package okio;

import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import java.io.UnsupportedEncodingException;

/* access modifiers changed from: package-private */
public final class Base64 {
    private static final byte[] MAP = {MsgID.METERING_REPORT, MsgID.UNIVERSAL_MSG_ID_ACKNOWLEDGEMENT, 67, 68, 69, 70, 71, 72, 73, MsgID.AGING_TEST_REPORT, 75, 76, 77, 78, 79, MsgID.RESET, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, MsgID.EXTENDED_STATUS_UPDATED, 98, MsgID.VERSION_INFO, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, MsgID.AUTO_SWITCH_AUDIO_OUPUT, 116, 117, 118, MsgID.MSG_ID_NOISE_CONTROLS_UPDATE, MsgID.NOISE_CONTROLS, MsgID.SET_TOUCH_AND_HOLD_NOISE_CONTROLS, MsgID.SET_DETECT_CONVERSATIONS, 48, MsgID.LOG_COREDUMP_DATA_SIZE, MsgID.LOG_COREDUMP_DATA, MsgID.LOG_COREDUMP_COMPLETE, MsgID.LOG_TRACE_START, MsgID.LOG_TRACE_DATA, MsgID.LOG_TRACE_COMPLETE, MsgID.LOG_TRACE_ROLE_SWITCH, MsgID.LOG_COREDUMP_DATA_DONE, MsgID.LOG_TRACE_DATA_DONE, 43, 47};
    private static final byte[] URL_MAP = {MsgID.METERING_REPORT, MsgID.UNIVERSAL_MSG_ID_ACKNOWLEDGEMENT, 67, 68, 69, 70, 71, 72, 73, MsgID.AGING_TEST_REPORT, 75, 76, 77, 78, 79, MsgID.RESET, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, MsgID.EXTENDED_STATUS_UPDATED, 98, MsgID.VERSION_INFO, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, MsgID.AUTO_SWITCH_AUDIO_OUPUT, 116, 117, 118, MsgID.MSG_ID_NOISE_CONTROLS_UPDATE, MsgID.NOISE_CONTROLS, MsgID.SET_TOUCH_AND_HOLD_NOISE_CONTROLS, MsgID.SET_DETECT_CONVERSATIONS, 48, MsgID.LOG_COREDUMP_DATA_SIZE, MsgID.LOG_COREDUMP_DATA, MsgID.LOG_COREDUMP_COMPLETE, MsgID.LOG_TRACE_START, MsgID.LOG_TRACE_DATA, MsgID.LOG_TRACE_COMPLETE, MsgID.LOG_TRACE_ROLE_SWITCH, MsgID.LOG_COREDUMP_DATA_DONE, MsgID.LOG_TRACE_DATA_DONE, 45, 95};

    private Base64() {
    }

    public static byte[] decode(String str) {
        int i;
        int length = str.length();
        while (length > 0 && ((r5 = str.charAt(length - 1)) == '=' || r5 == '\n' || r5 == '\r' || r5 == ' ' || r5 == '\t')) {
            length--;
        }
        byte[] bArr = new byte[((int) ((((long) length) * 6) / 8))];
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            char charAt = str.charAt(i5);
            if (charAt >= 'A' && charAt <= 'Z') {
                i = charAt - 'A';
            } else if (charAt >= 'a' && charAt <= 'z') {
                i = charAt - 'G';
            } else if (charAt >= '0' && charAt <= '9') {
                i = charAt + 4;
            } else if (charAt == '+' || charAt == '-') {
                i = 62;
            } else if (charAt == '/' || charAt == '_') {
                i = 63;
            } else {
                if (!(charAt == '\n' || charAt == '\r' || charAt == ' ' || charAt == '\t')) {
                    return null;
                }
            }
            i3 = (i3 << 6) | ((byte) i);
            i2++;
            if (i2 % 4 == 0) {
                int i6 = i4 + 1;
                bArr[i4] = (byte) (i3 >> 16);
                int i7 = i6 + 1;
                bArr[i6] = (byte) (i3 >> 8);
                bArr[i7] = (byte) i3;
                i4 = i7 + 1;
            }
        }
        int i8 = i2 % 4;
        if (i8 == 1) {
            return null;
        }
        if (i8 == 2) {
            bArr[i4] = (byte) ((i3 << 12) >> 16);
            i4++;
        } else if (i8 == 3) {
            int i9 = i3 << 6;
            int i10 = i4 + 1;
            bArr[i4] = (byte) (i9 >> 16);
            i4 = i10 + 1;
            bArr[i10] = (byte) (i9 >> 8);
        }
        if (i4 == bArr.length) {
            return bArr;
        }
        byte[] bArr2 = new byte[i4];
        System.arraycopy(bArr, 0, bArr2, 0, i4);
        return bArr2;
    }

    public static String encode(byte[] bArr) {
        return encode(bArr, MAP);
    }

    public static String encodeUrl(byte[] bArr) {
        return encode(bArr, URL_MAP);
    }

    private static String encode(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[(((bArr.length + 2) / 3) * 4)];
        int length = bArr.length - (bArr.length % 3);
        int i = 0;
        for (int i2 = 0; i2 < length; i2 += 3) {
            int i3 = i + 1;
            bArr3[i] = bArr2[(bArr[i2] & 255) >> 2];
            int i4 = i3 + 1;
            int i5 = i2 + 1;
            bArr3[i3] = bArr2[((bArr[i2] & 3) << 4) | ((bArr[i5] & 255) >> 4)];
            int i6 = i4 + 1;
            int i7 = i2 + 2;
            bArr3[i4] = bArr2[((bArr[i5] & 15) << 2) | ((bArr[i7] & 255) >> 6)];
            i = i6 + 1;
            bArr3[i6] = bArr2[bArr[i7] & 63];
        }
        int length2 = bArr.length % 3;
        if (length2 == 1) {
            int i8 = i + 1;
            bArr3[i] = bArr2[(bArr[length] & 255) >> 2];
            int i9 = i8 + 1;
            bArr3[i8] = bArr2[(bArr[length] & 3) << 4];
            bArr3[i9] = 61;
            bArr3[i9 + 1] = 61;
        } else if (length2 == 2) {
            int i10 = i + 1;
            bArr3[i] = bArr2[(bArr[length] & 255) >> 2];
            int i11 = i10 + 1;
            int i12 = length + 1;
            bArr3[i10] = bArr2[((bArr[i12] & 255) >> 4) | ((bArr[length] & 3) << 4)];
            bArr3[i11] = bArr2[(bArr[i12] & 15) << 2];
            bArr3[i11 + 1] = 61;
        }
        try {
            return new String(bArr3, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
}
