package com.samsung.accessory.hearablemgr.core.service.message;

import seccompat.android.util.Log;

public class MsgFotaDeviceInfoSwVersion extends Msg {
    private static final String TAG = "Attic_MsgFotaDeviceInfoSwVersion";
    public String version;

    MsgFotaDeviceInfoSwVersion(byte[] bArr) {
        super(bArr);
        byte[] bArr2 = new byte[(((bArr.length - getDataStartIndex()) - 2) - 1)];
        System.arraycopy(bArr, getDataStartIndex(), bArr2, 0, bArr2.length);
        this.version = new String(bArr2);
        Log.d(TAG, "version : " + this.version);
    }
}
