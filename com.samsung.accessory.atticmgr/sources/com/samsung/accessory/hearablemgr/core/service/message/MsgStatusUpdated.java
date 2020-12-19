package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.common.util.ByteUtil;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class MsgStatusUpdated extends Msg {
    public static final int EARBUD_PLACEMENT_IN_CLOSE_CASE = 4;
    public static final int EARBUD_PLACEMENT_IN_EAR = 1;
    public static final int EARBUD_PLACEMENT_IN_OPEN_CASE = 3;
    public static final int EARBUD_PLACEMENT_OUTSIDE = 2;
    public static final int EARBUD_PLACEMENT_UNKNOWN = 0;
    public static final int PRIMARY_EARBUD_LEFT = 1;
    public static final int PRIMARY_EARBUD_RIGHT = 0;
    public static final int RESPONSE_RECEIVED = 0;
    public static final int RESPONSE_REQUEST = 1;
    public static final int STATUS_COUPLE = 1;
    public static final int STATUS_SINGLE = 0;
    private static final String TAG = "Attic_MsgStatusUpdated";
    public static final int WEARING_BOTH = 17;
    public static final int WEARING_L = 16;
    public static final int WEARING_NONE = 0;
    public static final int WEARING_R = 1;
    public int batteryCase;
    public int batteryLeft;
    public int batteryRight;
    public boolean coupled;
    public int placementL;
    public int placementR;
    public byte primaryEarbud;
    public byte revision;
    public boolean wearingL;
    public boolean wearingR;

    MsgStatusUpdated(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.revision = recvDataByteBuffer.get();
        this.batteryLeft = recvDataByteBuffer.get();
        this.batteryRight = recvDataByteBuffer.get();
        boolean z = false;
        this.coupled = recvDataByteBuffer.get() == 1;
        this.primaryEarbud = recvDataByteBuffer.get();
        byte b = recvDataByteBuffer.get();
        this.placementL = ByteUtil.valueOfLeft(b);
        this.placementR = ByteUtil.valueOfRight(b);
        this.wearingL = this.placementL == 1;
        this.wearingR = this.placementR == 1 ? true : z;
        this.batteryCase = recvDataByteBuffer.get();
        Log.d(TAG, "revision=" + ((int) this.revision) + ", batteryLeft=" + this.batteryLeft + ", batteryRight=" + this.batteryRight + ", batteryCase=" + this.batteryCase);
    }

    public void applyTo(EarBudsInfo earBudsInfo) {
        earBudsInfo.batteryL = this.batteryLeft;
        earBudsInfo.batteryR = this.batteryRight;
        earBudsInfo.batteryCase = this.batteryCase;
        earBudsInfo.coupled = this.coupled;
        earBudsInfo.wearingL = this.wearingL;
        earBudsInfo.wearingR = this.wearingR;
        earBudsInfo.placementL = this.placementL;
        earBudsInfo.placementR = this.placementR;
        earBudsInfo.calcBatteryIntegrated();
    }
}
