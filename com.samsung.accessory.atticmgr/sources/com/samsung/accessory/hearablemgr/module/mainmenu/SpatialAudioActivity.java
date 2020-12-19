package com.samsung.accessory.hearablemgr.module.mainmenu;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.SpatialSensorManager;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;
import seccompat.android.util.Log;

public class SpatialAudioActivity extends ConnectionActivity {
    private static final String TAG = (Application.TAG_ + SpatialAudioActivity.class.getSimpleName());
    private EarBudsInfo mEarBudsInfo;
    private SeslSwitchBar mSwitchBar;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_spatial_audio);
        this.mEarBudsInfo = Application.getCoreService().getEarBudsInfo();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initSpatialAudio();
    }

    private void initSpatialAudio() {
        this.mSwitchBar = (SeslSwitchBar) findViewById(R.id.switch_bar);
        this.mSwitchBar.setChecked(this.mEarBudsInfo.spatialAudio);
        setColorBackground(this.mEarBudsInfo.spatialAudio);
        this.mSwitchBar.addOnSwitchChangeListener(new SeslSwitchBar.OnSwitchChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.$$Lambda$SpatialAudioActivity$Vez52oyqBd9qy7a94BOJtandM */

            @Override // androidx.appcompat.widget.SeslSwitchBar.OnSwitchChangeListener
            public final void onSwitchChanged(SwitchCompat switchCompat, boolean z) {
                SpatialAudioActivity.this.lambda$initSpatialAudio$0$SpatialAudioActivity(switchCompat, z);
            }
        });
        ((TextView) findViewById(R.id.text_spatial_audio_disclaimer)).setText(Util.isTablet() ? R.string.advanced_spatial_audio_disclaimer_tablet : R.string.advanced_spatial_audio_disclaimer_phone);
    }

    public /* synthetic */ void lambda$initSpatialAudio$0$SpatialAudioActivity(SwitchCompat switchCompat, boolean z) {
        this.mEarBudsInfo.spatialAudio = z;
        Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_SPATIAL_AUDIO, this.mEarBudsInfo.spatialAudio ? (byte) 1 : 0));
        setColorBackground(this.mEarBudsInfo.spatialAudio);
        SpatialSensorManager.notifySpatialAudioSettingUpdated(z);
        SamsungAnalyticsUtil.setStatusInt(SA.Status._3D_AUDIO_FOR_VIDEOS_STATUS, z ? 1 : 0);
    }

    private void setColorBackground(boolean z) {
        if (this.mSwitchBar.isChecked() != z) {
            this.mSwitchBar.setCheckedInternal(z);
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onResume() {
        super.onResume();
        updateVoiceAssistant();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void updateVoiceAssistant() {
        if (Util.isTalkBackEnabled()) {
            this.mSwitchBar.setFocusable(false);
            this.mSwitchBar.setClickable(false);
            return;
        }
        this.mSwitchBar.setFocusable(true);
        this.mSwitchBar.setClickable(true);
    }
}
