package com.samsung.accessory.hearablemgr.module.mainmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
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

public class AdvancedActivity extends ConnectionActivity {
    private static final String TAG = (Application.TAG_ + AdvancedActivity.class.getSimpleName());
    private EarBudsInfo mEarBudsInfo;
    private SwitchCompat mSeamlessConnectionSwitch;
    private View mSpatialAudioLinearLayout;
    private SwitchCompat mSpatialAudioSwitch;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_advanced);
        this.mEarBudsInfo = Application.getCoreService().getEarBudsInfo();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initVoiceWakeUp();
        initSeamlessConnection();
        initSpatialAudio();
        initHearingEnhancements();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onResume() {
        super.onResume();
        updateVoiceAssistant();
        updateSeamlessConnection();
        updateSpatialAudio();
        SamsungAnalyticsUtil.sendPage(SA.Screen.ADVANCED_265);
    }

    private void initVoiceWakeUp() {
        findViewById(R.id.linear_layout_voice_wakeup).setVisibility(Application.getAomManager().checkEnabledBixby() ? 0 : 8);
        findViewById(R.id.layout_voice_wakeup).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.AdvancedActivity.AnonymousClass1 */

            public void onClick(View view) {
                AdvancedActivity advancedActivity = AdvancedActivity.this;
                advancedActivity.startActivity(new Intent(advancedActivity, BixbyVoiceWakeUpActivity.class));
                SamsungAnalyticsUtil.sendEvent(SA.Event.VOICE_WAKE_UP_SWITCH, SA.Screen.ADVANCED_265);
            }
        });
    }

    private void initSeamlessConnection() {
        this.mSeamlessConnectionSwitch = (SwitchCompat) findViewById(R.id.switch_seamless_connection);
        this.mSeamlessConnectionSwitch.setChecked(this.mEarBudsInfo.seamlessConnection);
        findViewById(R.id.layout_seamless_connection_switch).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.AdvancedActivity.AnonymousClass2 */

            public void onClick(View view) {
                AdvancedActivity advancedActivity = AdvancedActivity.this;
                advancedActivity.startActivity(new Intent(advancedActivity, SeamlessConnectionActivity.class));
                SamsungAnalyticsUtil.sendEvent(SA.Event.SEAMLESS_EARBUDS_CONNECTION_SWITCH, SA.Screen.ADVANCED_265);
            }
        });
        this.mSeamlessConnectionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.AdvancedActivity.AnonymousClass3 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_SEAMLESS_CONNECTION, !z ? 1 : 0 ? (byte) 1 : 0));
                AdvancedActivity.this.mEarBudsInfo.seamlessConnection = z;
                SamsungAnalyticsUtil.setStatusInt(SA.Status.SEAMLESS_EARBUD_CONNECTION_STATUS, z ? 1 : 0);
            }
        });
        findViewById(R.id.switch_layout_seamless_connection).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.AdvancedActivity.AnonymousClass4 */

            public void onClick(View view) {
                AdvancedActivity.this.mSeamlessConnectionSwitch.setChecked(!AdvancedActivity.this.mSeamlessConnectionSwitch.isChecked());
                SamsungAnalyticsUtil.sendEvent(SA.Event.SEAMLESS_EARBUDS_CONNECTION_SWITCH, SA.Screen.ADVANCED_265);
            }
        });
    }

    private void initSpatialAudio() {
        this.mSpatialAudioLinearLayout = findViewById(R.id.layout_spatial_audio);
        this.mSpatialAudioLinearLayout.setVisibility((!SpatialSensorManager.isSupported(this) || this.mEarBudsInfo.extendedRevision <= 1) ? 8 : 0);
        this.mSpatialAudioSwitch = (SwitchCompat) findViewById(R.id.switch_spatial_audio);
        this.mSpatialAudioSwitch.setChecked(this.mEarBudsInfo.spatialAudio);
        this.mSpatialAudioLinearLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.AdvancedActivity.AnonymousClass5 */

            public void onClick(View view) {
                AdvancedActivity advancedActivity = AdvancedActivity.this;
                advancedActivity.startActivity(new Intent(advancedActivity, SpatialAudioActivity.class));
                SamsungAnalyticsUtil.sendEvent(SA.Event._3D_AUDIO_FOR_VIDEOS_SWITCH, SA.Screen.ADVANCED_265);
            }
        });
        this.mSpatialAudioSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.AdvancedActivity.AnonymousClass6 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_SPATIAL_AUDIO, z ? (byte) 1 : 0));
                AdvancedActivity.this.mEarBudsInfo.spatialAudio = z;
                SpatialSensorManager.notifySpatialAudioSettingUpdated(z);
                SamsungAnalyticsUtil.setStatusInt(SA.Status._3D_AUDIO_FOR_VIDEOS_STATUS, z ? 1 : 0);
            }
        });
        findViewById(R.id.switch_layout_spatial_audio).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.AdvancedActivity.AnonymousClass7 */

            public void onClick(View view) {
                AdvancedActivity.this.mSpatialAudioSwitch.setChecked(!AdvancedActivity.this.mSpatialAudioSwitch.isChecked());
                SamsungAnalyticsUtil.sendEvent(SA.Event._3D_AUDIO_FOR_VIDEOS_SWITCH, SA.Screen.ADVANCED_265);
            }
        });
    }

    private void initHearingEnhancements() {
        findViewById(R.id.linear_layout_hearing_enhancements).setVisibility(8);
        findViewById(R.id.layout_hearing_enhancements).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.AdvancedActivity.AnonymousClass8 */

            public void onClick(View view) {
                AdvancedActivity advancedActivity = AdvancedActivity.this;
                advancedActivity.startActivity(new Intent(advancedActivity, HearingEnhancementsActivity.class));
                SamsungAnalyticsUtil.sendEvent("2376", SA.Screen.ADVANCED_265);
            }
        });
    }

    private void updateVoiceAssistant() {
        if (Util.isTalkBackEnabled()) {
            this.mSeamlessConnectionSwitch.setFocusable(false);
            this.mSeamlessConnectionSwitch.setClickable(false);
            this.mSpatialAudioSwitch.setFocusable(false);
            this.mSpatialAudioSwitch.setClickable(false);
            return;
        }
        this.mSeamlessConnectionSwitch.setFocusable(true);
        this.mSeamlessConnectionSwitch.setClickable(true);
        this.mSpatialAudioSwitch.setFocusable(true);
        this.mSpatialAudioSwitch.setClickable(true);
    }

    private void updateSeamlessConnection() {
        this.mSeamlessConnectionSwitch.setChecked(this.mEarBudsInfo.seamlessConnection);
    }

    private void updateSpatialAudio() {
        this.mSpatialAudioSwitch.setChecked(this.mEarBudsInfo.spatialAudio);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        SamsungAnalyticsUtil.sendEvent(SA.Event.UP_BUTTON, SA.Screen.ADVANCED_265);
        finish();
        return super.onSupportNavigateUp();
    }
}
