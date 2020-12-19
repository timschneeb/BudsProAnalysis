package com.samsung.accessory.hearablemgr.module.mainmenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.aom.AomManager;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;
import seccompat.android.util.Log;

public class BixbyVoiceWakeUpActivity extends ConnectionActivity {
    private static final String TAG = (Application.TAG_ + BixbyVoiceWakeUpActivity.class.getSimpleName());
    private EarBudsInfo mEarBudsInfo;
    private ConstraintLayout mSpeakSeamlesslyLayout;
    private SwitchCompat mSpeakSeamlesslySwitch;
    private SeslSwitchBar mSwitchBar;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_bixby_voice_wake_up);
        this.mEarBudsInfo = Application.getCoreService().getEarBudsInfo();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initBixbyVoiceWakeUp();
        initSpeakSeamlessly();
    }

    private void initBixbyVoiceWakeUp() {
        this.mSwitchBar = (SeslSwitchBar) findViewById(R.id.switch_bar);
        this.mSwitchBar.addOnSwitchChangeListener(new SeslSwitchBar.OnSwitchChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.$$Lambda$BixbyVoiceWakeUpActivity$ElI4RmurQNk1iQF27FmbrVk50YQ */

            @Override // androidx.appcompat.widget.SeslSwitchBar.OnSwitchChangeListener
            public final void onSwitchChanged(SwitchCompat switchCompat, boolean z) {
                BixbyVoiceWakeUpActivity.this.lambda$initBixbyVoiceWakeUp$0$BixbyVoiceWakeUpActivity(switchCompat, z);
            }
        });
    }

    public /* synthetic */ void lambda$initBixbyVoiceWakeUp$0$BixbyVoiceWakeUpActivity(SwitchCompat switchCompat, boolean z) {
        if (Application.getAomManager().isSupportAOM()) {
            setbixbyVoiceWakeUp(z);
        } else if (z) {
            executeBixby();
            this.mSwitchBar.setChecked(false);
            setColorBackground(false);
        } else {
            setbixbyVoiceWakeUp(false);
        }
        UiUtil.setEnabledWithChildren(this.mSpeakSeamlesslyLayout, this.mEarBudsInfo.voiceWakeUp);
        SamsungAnalyticsUtil.sendEvent(SA.Event.BIXBY_VOICE_WEAK_UP, (String) null, z ? "b" : "a");
    }

    private void initSpeakSeamlessly() {
        this.mSpeakSeamlesslyLayout = (ConstraintLayout) findViewById(R.id.layout_speak_seamlessly);
        if (Application.getAomManager().isSeamlessSupported()) {
            UiUtil.setEnabledWithChildren(this.mSpeakSeamlesslyLayout, this.mEarBudsInfo.voiceWakeUp);
        } else {
            this.mSpeakSeamlesslyLayout.setVisibility(8);
        }
        this.mSpeakSeamlesslySwitch = (SwitchCompat) findViewById(R.id.switch_speak_seamlessly);
        this.mSpeakSeamlesslySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.BixbyVoiceWakeUpActivity.AnonymousClass1 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                BixbyVoiceWakeUpActivity.this.mEarBudsInfo.speakSeamlessly = z;
                Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_SPEAK_SEAMLESSLY, BixbyVoiceWakeUpActivity.this.mEarBudsInfo.speakSeamlessly ? (byte) 1 : 0));
                SamsungAnalyticsUtil.setStatusInt(SA.Status.SPEAK_SEAMLESSLY, z ? 1 : 0);
                SamsungAnalyticsUtil.sendEvent(SA.Event.SPEAK_SEAMLESSLY, (String) null, z ? "b" : "a");
            }
        });
        findViewById(R.id.layout_speak_seamlessly).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.BixbyVoiceWakeUpActivity.AnonymousClass2 */

            public void onClick(View view) {
                BixbyVoiceWakeUpActivity.this.mSpeakSeamlesslySwitch.setChecked(!BixbyVoiceWakeUpActivity.this.mSpeakSeamlesslySwitch.isChecked());
            }
        });
    }

    private void setColorBackground(boolean z) {
        if (this.mSwitchBar.isChecked() != z) {
            this.mSwitchBar.setCheckedInternal(z);
        }
    }

    private void updateVoiceWakeUp() {
        if (Application.getAomManager().isSupportAOM()) {
            if (Preferences.getBoolean(PreferenceKey.PREFERENCE_AOM_FIRST_SETTINGS, false)) {
                Preferences.putBoolean(PreferenceKey.PREFERENCE_AOM_FIRST_SETTINGS, false);
                this.mEarBudsInfo.voiceWakeUp = true;
            }
            this.mSwitchBar.setChecked(this.mEarBudsInfo.voiceWakeUp);
            setColorBackground(this.mEarBudsInfo.voiceWakeUp);
            ((TextView) findViewById(R.id.text_bixby_voice_wake_up_desc)).setText(getString(R.string.settings_voice_wakeup_desc, new Object[]{getString(R.string.hi_bixby)}) + " " + getString(R.string.settings_voice_wakeup_desc1));
        } else {
            this.mSwitchBar.setChecked(false);
            setColorBackground(false);
            Application.getAomManager();
            if (AomManager.isCompleteBixbyOOB() || !Application.getAomManager().isCompleteUpdate()) {
                ((TextView) findViewById(R.id.text_bixby_voice_wake_up_desc)).setText(R.string.settings_voice_wakeup_update_incomplete_desc);
            } else {
                ((TextView) findViewById(R.id.text_bixby_voice_wake_up_desc)).setText(R.string.settings_voice_wakeup_oobe_incomplete_desc);
            }
        }
        Preferences.putBoolean(PreferenceKey.PREFERENCE_AOM_FIRST_SETTINGS, false);
    }

    private void executeBixby() {
        Preferences.putBoolean(PreferenceKey.PREFERENCE_AOM_FIRST_SETTINGS, true);
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("bixbyvoice://com.samsung.android.bixby.agent/GoToFeature?featureName=AssistantHome")));
    }

    private void setbixbyVoiceWakeUp(boolean z) {
        EarBudsInfo earBudsInfo = this.mEarBudsInfo;
        earBudsInfo.voiceWakeUp = z;
        setColorBackground(earBudsInfo.voiceWakeUp);
        SamsungAnalyticsUtil.setStatusString(SA.Status.VOICE_WAKE_UP, this.mEarBudsInfo.voiceWakeUp ? "1" : "0");
        Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_VOICE_WAKE_UP, this.mEarBudsInfo.voiceWakeUp ? (byte) 1 : 0));
        if (!this.mEarBudsInfo.voiceWakeUp) {
            Application.getAomManager().setBixbyMic(false);
        }
    }

    private void updateSpeakSeamlessly() {
        this.mSpeakSeamlesslySwitch.setChecked(this.mEarBudsInfo.speakSeamlessly);
        if (Application.getAomManager().isSeamlessSupported()) {
            UiUtil.setEnabledWithChildren(this.mSpeakSeamlesslyLayout, this.mEarBudsInfo.voiceWakeUp);
        } else {
            this.mSpeakSeamlesslyLayout.setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onResume() {
        super.onResume();
        updateVoiceAssistant();
        updateVoiceWakeUp();
        updateSpeakSeamlessly();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        SamsungAnalyticsUtil.sendEvent(SA.Event.UP_BUTTON);
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
