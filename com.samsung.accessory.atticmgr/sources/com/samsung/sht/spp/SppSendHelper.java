package com.samsung.sht.spp;

import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.library.SpatialSensorInterface;

public class SppSendHelper {
    public void sendControlMessage(SpatialSensorInterface.SupportApi supportApi, byte b) {
        supportApi.sendSppMessage(MsgID.SPATIAL_AUDIO_CONTROL, new byte[]{b});
    }

    public void sendAddMsg(SpatialSensorInterface.SupportApi supportApi) {
        sendControlMessage(supportApi, (byte) 0);
    }

    public void sendRemoveMsg(SpatialSensorInterface.SupportApi supportApi) {
        sendControlMessage(supportApi, (byte) 1);
    }

    public void sendAliveMsg(SpatialSensorInterface.SupportApi supportApi) {
        sendControlMessage(supportApi, (byte) 4);
    }

    public void sendRequestWearOnOffMsg(SpatialSensorInterface.SupportApi supportApi) {
        sendControlMessage(supportApi, (byte) 5);
    }
}
