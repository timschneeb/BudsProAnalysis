package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.common.util.BufferBuilder;
import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class MsgFotaControl extends Msg {
    public static final int CONTROL_ID_READY_TO_DOWNLOAD = 1;
    public static final int CONTROL_ID_SEND_MTU = 0;
    private static final String TAG = "Attic_MsgFotaControl";
    public int mControlID;
    public int mId;
    public int mMtuSize;

    public MsgFotaControl(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.mControlID = recvDataByteBuffer.get();
        int i = this.mControlID;
        if (i == 0) {
            this.mMtuSize = recvDataByteBuffer.getShort();
            int i2 = this.mMtuSize;
            this.mMtuSize = i2 > 650 ? 650 : i2;
        } else if (i == 1) {
            this.mId = recvDataByteBuffer.getShort();
        } else {
            Log.d(TAG, "ControlID is : " + this.mControlID);
        }
    }

    public MsgFotaControl(int i, int i2) {
        super(MsgID.FOTA_CONTROL, true);
        this.mControlID = i;
        int i3 = this.mControlID;
        if (i3 == 0) {
            this.mMtuSize = i2;
            this.mMtuSize = this.mMtuSize > 650 ? 650 : i2;
        } else if (i3 == 1) {
            this.mId = i2;
        } else {
            Log.d(TAG, "ControlID is : " + this.mControlID);
        }
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        BufferBuilder bufferBuilder = new BufferBuilder();
        bufferBuilder.put((byte) this.mControlID);
        int i = this.mControlID;
        if (i == 0) {
            bufferBuilder.putShort((short) this.mMtuSize);
        } else if (i == 1) {
            bufferBuilder.putShort((short) this.mId);
        } else {
            Log.d(TAG, "ControlID is : " + this.mControlID);
        }
        return bufferBuilder.array();
    }
}
