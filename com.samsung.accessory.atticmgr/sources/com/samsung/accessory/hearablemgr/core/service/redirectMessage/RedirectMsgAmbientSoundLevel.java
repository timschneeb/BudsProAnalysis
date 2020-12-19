package com.samsung.accessory.hearablemgr.core.service.redirectMessage;

import android.util.Log;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;

public class RedirectMsgAmbientSoundLevel extends RedirectMsg {
    private static final String TAG = "Attic_RedirectMsgAmbientSoundLevel";
    byte ambient_sound_level = getRecvDataByteBuffer().get();

    public RedirectMsgAmbientSoundLevel(byte[] bArr) {
        super(bArr);
    }

    public void applyTo() {
        Log.d(TAG, "applyTo() : ambient_sound_level : " + ((int) this.ambient_sound_level));
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        byte b = this.ambient_sound_level;
        earBudsInfo.ambientSoundLevel = b;
        SamsungAnalyticsUtil.setStatusInt(SA.Status.AMBIENT_SOUND_VOLUME_STATUS, b);
    }
}
