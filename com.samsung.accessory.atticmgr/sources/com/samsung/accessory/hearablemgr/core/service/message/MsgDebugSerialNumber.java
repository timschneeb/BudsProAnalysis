package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class MsgDebugSerialNumber extends Msg {
    private static final String TAG = "Attic_MsgDebugSerialNumber";
    public String SerialNumberLeft = "None";
    public String SerialNumberRight = "None";

    public MsgDebugSerialNumber() {
        super((byte) MsgID.DEBUG_SERIAL_NUMBER);
    }

    public MsgDebugSerialNumber(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        byte[] bArr2 = new byte[11];
        boolean z = false;
        for (int i = 0; i < 11; i++) {
            bArr2[i] = recvDataByteBuffer.get();
            if (bArr2[i] != 0) {
                z = true;
            }
        }
        String str = null;
        String str2 = z ? new String(bArr2) : null;
        byte[] bArr3 = new byte[11];
        boolean z2 = false;
        for (int i2 = 0; i2 < 11; i2++) {
            bArr3[i2] = recvDataByteBuffer.get();
            if (bArr3[i2] != 0) {
                z2 = true;
            }
        }
        str = z2 ? new String(bArr3) : str;
        Log.d(TAG, "spp_serialnumber_res - Left : " + str2 + " Right : " + str);
        this.SerialNumberLeft = str2;
        this.SerialNumberRight = str;
        Preferences.putString(PreferenceKey.LEFT_INFO_SN, this.SerialNumberLeft);
        Preferences.putString(PreferenceKey.RIGHT_INFO_SN, this.SerialNumberRight);
    }
}
