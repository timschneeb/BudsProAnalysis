package com.samsung.accessory.hearablemgr.core.service.redirectMessage;

import android.content.Intent;
import android.util.Log;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.service.CoreService;

public class RedirectMsgSetAmbientMode extends RedirectMsg {
    private static final String TAG = "Attic_RedirectMsgSetAmbientMode";
    boolean ambient_sound_config = false;

    public RedirectMsgSetAmbientMode(byte[] bArr) {
        super(bArr);
        boolean z = false;
        this.ambient_sound_config = getRecvDataByteBuffer().get() == 1 ? true : z;
    }

    public void applyTo() {
        Log.d(TAG, "applyTo() : ambient_sound_config : " + this.ambient_sound_config);
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        boolean z = this.ambient_sound_config;
        earBudsInfo.ambientSound = z;
        Preferences.putBoolean(PreferenceKey.AMBIENT_SOUND_STATE, Boolean.valueOf(z));
        Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_AMBIENT_SOUND_UPDATED));
    }
}
