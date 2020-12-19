package com.samsung.accessory.hearablemgr.core.service.redirectMessage;

import android.content.Intent;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.SpatialSensorManager;
import com.samsung.accessory.hearablemgr.core.service.message.Msg;
import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class RedirectMsgSimple extends Msg {
    private static final String TAG = "Attic_RedirectMsgSimple";
    private ByteBuffer getBuffer;
    public int reDirectID;

    public RedirectMsgSimple(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.reDirectID = recvDataByteBuffer.get();
        this.getBuffer = recvDataByteBuffer;
    }

    public void applyTo() {
        Log.d(TAG, "applyTo()");
        int i = this.reDirectID;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        if (i == -123) {
            Log.d(TAG, "ADJUST_SOUND_SYNC");
            if (this.getBuffer.get() == 1) {
                z = true;
            }
            Application.getCoreService().getEarBudsInfo().adjustSoundSync = z;
        } else if (i == -81) {
            Log.d(TAG, "SET_SEAMLESS_CONNECTION");
            if (this.getBuffer.get() == 0) {
                z7 = true;
            }
            Application.getCoreService().getEarBudsInfo().seamlessConnection = z7;
            int i2 = z7 ? 1 : 0;
            int i3 = z7 ? 1 : 0;
            int i4 = z7 ? 1 : 0;
            SamsungAnalyticsUtil.setStatusInt(SA.Status.SEAMLESS_EARBUD_CONNECTION_STATUS, i2);
        } else if (i == 122) {
            Log.d(TAG, "SET_DETECT_CONVERSATIONS");
            if (this.getBuffer.get() == 1) {
                z6 = true;
            }
            Application.getCoreService().getEarBudsInfo().detectConversations = z6;
            int i5 = z6 ? 1 : 0;
            int i6 = z6 ? 1 : 0;
            int i7 = z6 ? 1 : 0;
            SamsungAnalyticsUtil.setStatusInt(SA.Status.VOICE_DETECT_STATUS, i5);
        } else if (i == 124) {
            Log.d(TAG, "SET_SPATIAL_AUDIO");
            if (this.getBuffer.get() == 1) {
                z5 = true;
            }
            if (Application.getCoreService().getEarBudsInfo().spatialAudio != z5) {
                Application.getCoreService().getEarBudsInfo().spatialAudio = z5;
                SpatialSensorManager.notifySpatialAudioSettingUpdated(z5);
                int i8 = z5 ? 1 : 0;
                int i9 = z5 ? 1 : 0;
                int i10 = z5 ? 1 : 0;
                SamsungAnalyticsUtil.setStatusInt(SA.Status._3D_AUDIO_FOR_VIDEOS_STATUS, i8);
            }
        } else if (i == -106) {
            Log.d(TAG, "EXTRA_HIGH_AMBIENT");
            if (this.getBuffer.get() == 1) {
                z4 = true;
            }
            Application.getCoreService().getEarBudsInfo().extraHighAmbient = z4;
        } else if (i != -105) {
            switch (i) {
                case -97:
                    Log.d(TAG, "PASS_THROUGH");
                    if (this.getBuffer.get() == 1) {
                        z2 = true;
                    }
                    Application.getCoreService().getEarBudsInfo().passThrough = z2;
                    return;
                case -96:
                    Log.d(TAG, "FIND_MY_EARBUDS_START");
                    Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STATUS_UPDATED));
                    return;
                case -95:
                    Log.d(TAG, "FIND_MY_EARBUDS_STOP");
                    Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STOP));
                    return;
                default:
                    return;
            }
        } else {
            Log.d(TAG, "SET_VOICE_WAKE_UP");
            if (this.getBuffer.get() == 1) {
                z3 = true;
            }
            Application.getCoreService().getEarBudsInfo().voiceWakeUp = z3;
        }
    }
}
