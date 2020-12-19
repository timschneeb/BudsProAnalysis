package com.samsung.accessory.hearablemgr.core.service.message;

import android.content.Intent;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;

public class MsgNoiseControls extends Msg {
    public byte noiseControls;

    public MsgNoiseControls(byte b) {
        super((byte) MsgID.NOISE_CONTROLS);
        this.noiseControls = b;
        Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_NOISE_CONTROLS_UPDATE));
        Preferences.putInt(PreferenceKey.NOISE_CONTROL_STATE, Integer.valueOf(b));
        SamsungAnalyticsUtil.setNoiseControlsStatus(b);
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{this.noiseControls};
    }
}
