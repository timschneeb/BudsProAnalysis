package com.samsung.accessory.hearablemgr.core.service.redirectMessage;

import android.content.Intent;
import android.util.Log;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;

public class RedirectMsgNoiseControls extends RedirectMsg {
    private static final String TAG = "Attic_RedirectMsgNoiseControls";
    byte noise_controls = getRecvDataByteBuffer().get();

    public RedirectMsgNoiseControls(byte[] bArr) {
        super(bArr);
    }

    public void applyTo() {
        Log.d(TAG, "applyTo() : noise_controls : " + ((int) this.noise_controls));
        Application.getCoreService().getEarBudsInfo().noiseControls = this.noise_controls;
        Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_NOISE_CONTROLS_UPDATE));
        Preferences.putInt(PreferenceKey.NOISE_CONTROL_STATE, Integer.valueOf(this.noise_controls));
        SamsungAnalyticsUtil.setNoiseControlsStatus(this.noise_controls);
    }
}
