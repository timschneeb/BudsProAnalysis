package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.common.util.ByteUtil;
import java.nio.ByteBuffer;

public class MsgMeteringReport extends Msg {
    private static final String TAG = "Attic_MsgMeteringReport";
    public int[] a2dpUsingTime;
    public int[] ambientOnTime;
    public int[] ancOnTime;
    public int[] battery;
    public byte connectedSide;
    public int[] escoOpenTime;
    public int totalBatteryCapacity;

    public MsgMeteringReport() {
        super((byte) MsgID.METERING_REPORT);
        this.battery = new int[2];
        this.a2dpUsingTime = new int[2];
        this.escoOpenTime = new int[2];
        this.ancOnTime = new int[2];
        this.ambientOnTime = new int[2];
    }

    public MsgMeteringReport(byte[] bArr) {
        super(bArr);
        this.battery = new int[2];
        this.a2dpUsingTime = new int[2];
        this.escoOpenTime = new int[2];
        this.ancOnTime = new int[2];
        this.ambientOnTime = new int[2];
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        byte b = recvDataByteBuffer.get();
        this.connectedSide = recvDataByteBuffer.get();
        boolean z = ByteUtil.valueOfLeft(this.connectedSide) == 1;
        boolean z2 = ByteUtil.valueOfRight(this.connectedSide) == 1;
        if (b >= 2) {
            this.totalBatteryCapacity = recvDataByteBuffer.getShort();
        }
        if (z) {
            this.battery[0] = recvDataByteBuffer.get();
            this.a2dpUsingTime[0] = recvDataByteBuffer.getInt();
            this.escoOpenTime[0] = recvDataByteBuffer.getInt();
            this.ancOnTime[0] = recvDataByteBuffer.getInt();
            this.ambientOnTime[0] = recvDataByteBuffer.getInt();
        }
        if (z2) {
            this.battery[1] = recvDataByteBuffer.get();
            this.a2dpUsingTime[1] = recvDataByteBuffer.getInt();
            this.escoOpenTime[1] = recvDataByteBuffer.getInt();
            this.ancOnTime[1] = recvDataByteBuffer.getInt();
            this.ambientOnTime[1] = recvDataByteBuffer.getInt();
        }
    }
}
