package com.samsung.sht.spp;

import com.samsung.sht.common.ByteUtil;
import com.samsung.sht.log.ShtLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SppRecvHelper {
    private Callback mCallback = null;

    public interface Callback {
        void onAddSuccess();

        void onBudGrvUpdated(List<float[]> list);

        void onRemoveSuccess();

        void onWearOnOffUpdated(boolean z, boolean z2);
    }

    public SppRecvHelper(Callback callback) {
        this.mCallback = callback;
    }

    public void parse(byte b, byte[] bArr) {
        if (b == -62) {
            parseData(bArr);
        } else if (b != -61) {
            ShtLog.e("Unknown message received=" + ((int) b));
        } else {
            parseControl(bArr);
        }
    }

    private void parseControl(byte[] bArr) {
        Callback callback;
        byte b = bArr[0];
        if (b == 2) {
            Callback callback2 = this.mCallback;
            if (callback2 != null) {
                callback2.onAddSuccess();
            }
        } else if (b == 3 && (callback = this.mCallback) != null) {
            callback.onRemoveSuccess();
        }
    }

    private void parseData(byte[] bArr) {
        boolean z = false;
        boolean z2 = true;
        switch (bArr[0]) {
            case 32:
                parseGrv(Arrays.copyOfRange(bArr, 1, bArr.length));
                return;
            case 33:
                if (bArr.length >= 2) {
                    Callback callback = this.mCallback;
                    if (bArr[1] == 1) {
                        z = true;
                    }
                    callback.onWearOnOffUpdated(true, z);
                    return;
                }
                ShtLog.e("Wear on updated, but wrong message format");
                return;
            case 34:
                Callback callback2 = this.mCallback;
                if (callback2 == null) {
                    return;
                }
                if (bArr.length >= 2) {
                    if (bArr[1] != 1) {
                        z2 = false;
                    }
                    callback2.onWearOnOffUpdated(false, z2);
                    return;
                }
                ShtLog.e("Wear off updated, but wrong message format");
                return;
            case 35:
                parseGyroBias(Arrays.copyOfRange(bArr, 1, bArr.length));
                return;
            case 36:
                parseStuckInfo(Arrays.copyOfRange(bArr, 1, bArr.length));
                return;
            default:
                ShtLog.e("Unknown data message=" + ((int) bArr[0]));
                return;
        }
    }

    private void parseGrv(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        int length = bArr.length / 8;
        float[] fArr = new float[4];
        for (int i = 0; i < length; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                fArr[i2] = ((float) ((short) ByteUtil.byteBufferToInt(bArr, (i * 8) + (i2 * 2), 2, false))) / 10000.0f;
            }
            arrayList.add(Arrays.copyOf(fArr, fArr.length));
        }
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onBudGrvUpdated(arrayList);
        }
    }

    private void parseGyroBias(byte[] bArr) {
        if (bArr.length < 6) {
            ShtLog.e("Invalid Gyro Bias Info received");
            return;
        }
        int[] iArr = {(short) ByteUtil.byteBufferToInt(bArr, 0, 2, false), (short) ByteUtil.byteBufferToInt(bArr, 2, 2, false), (short) ByteUtil.byteBufferToInt(bArr, 4, 2, false)};
        if (bArr.length >= 9) {
            byte b = bArr[6];
            ShtLog.d("ShtCore", "Gyro bias Info :" + Arrays.toString(iArr) + "," + ((int) b) + "," + ((int) ((short) ByteUtil.byteBufferToInt(bArr, 7, 2, false))));
            return;
        }
        ShtLog.d("ShtCore", "Gyro bias Info :" + Arrays.toString(iArr));
    }

    private void parseStuckInfo(byte[] bArr) {
        ShtLog.d("ShtCore", "Stuck Info :" + ((int) ((short) ByteUtil.byteBufferToInt(bArr, 0, 1, false))));
    }
}
