package com.samsung.accessory.hearablemgr.core.service.message;

import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class MsgDebugSKU extends Msg {
    private static final String TAG = "Attic_MsgDebugSerialNumber";
    public String LeftSKU = "None";
    public String RightSKU = "None";

    public MsgDebugSKU() {
        super((byte) MsgID.DEBUG_SKU);
    }

    public MsgDebugSKU(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        byte[] bArr2 = new byte[14];
        boolean z = false;
        for (int i = 0; i < 14; i++) {
            bArr2[i] = recvDataByteBuffer.get();
            if (bArr2[i] != 0) {
                z = true;
            }
        }
        String str = null;
        String str2 = z ? new String(bArr2) : null;
        byte[] bArr3 = new byte[14];
        boolean z2 = false;
        for (int i2 = 0; i2 < 14; i2++) {
            bArr3[i2] = recvDataByteBuffer.get();
            if (bArr3[i2] != 0) {
                z2 = true;
            }
        }
        str = z2 ? new String(bArr3) : str;
        Log.d(TAG, "spp_SKU- Left : " + str2 + " Right : " + str);
        this.LeftSKU = str2;
        this.RightSKU = str;
    }
}
