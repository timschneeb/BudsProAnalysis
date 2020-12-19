package com.samsung.accessory.hearablemgr.core.service.message;

import android.os.Build;
import com.samsung.accessory.hearablemgr.common.util.Util;

public class MsgManagerInfo extends Msg {
    public static final byte MANAGER_TYPE_ANDROID_GEAR_MANAGER = 1;
    public static final byte MANUFACTURER_TYPE_NONSAMSUNG = 2;
    public static final byte MANUFACTURER_TYPE_SAMSUNG = 1;
    public byte manufacturer;
    public int osVersion;
    public byte result;
    public byte type = 1;

    public MsgManagerInfo(byte[] bArr) {
        super(bArr);
        this.result = bArr[getDataStartIndex()];
    }

    public MsgManagerInfo() {
        super((byte) MsgID.MANAGER_INFO);
        byte b = 1;
        this.manufacturer = !Util.isSamsungDevice() ? 2 : b;
        this.osVersion = Build.VERSION.SDK_INT;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{this.type, this.manufacturer, (byte) this.osVersion};
    }
}
