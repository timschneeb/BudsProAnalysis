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
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;
import seccompat.android.util.Log;

public class GamingModeActivity extends ConnectionActivity {
    private static final String TAG = (Application.TAG_ + GamingModeActivity.class.getSimpleName());
    private EarBudsInfo mEarBudsInfo;
    private SeslSwitchBar mSwitchBar;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_gaming_mode);
        this.mEarBudsInfo = Application.getCoreService().getEarBudsInfo();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initGamingMode();
    }

    private void initGamingMode() {
        this.mSwitchBar = (SeslSwitchBar) findViewById(R.id.switch_bar);
        ((TextView) findViewById(R.id.layout_gaming_desc)).setText(Util.isTablet() ? R.string.settings_game_mode_desc1_tablet : R.string.settings_game_mode_desc1);
        this.mSwitchBar.setChecked(this.mEarBudsInfo.adjustSoundSync);
        setColorBackground(this.mEarBudsInfo.adjustSoundSync);
        this.mSwitchBar.addOnSwitchChangeListener(new SeslSwitchBar.OnSwitchChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.$$Lambda$GamingModeActivity$H1DcGXpl6gOW6xj8YI_j3RkJEo */

            @Override // androidx.appcompat.widget.SeslSwitchBar.OnSwitchChangeListener
            public final void onSwitchChanged(SwitchCompat switchCompat, boolean z) {
                GamingModeActivity.this.lambda$initGamingMode$0$GamingModeActivity(switchCompat, z);
            }
        });
    }

    public /* synthetic */ void lambda$initGamingMode$0$GamingModeActivity(SwitchCompat switchCompat, boolean z) {
        EarBudsInfo earBudsInfo = this.mEarBudsInfo;
        earBudsInfo.adjustSoundSync = z;
        SamsungAnalyticsUtil.setStatusString(SA.Status.GAME_MODE, earBudsInfo.adjustSoundSync ? "1" : "0");
        Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.ADJUST_SOUND_SYNC, this.mEarBudsInfo.adjustSoundSync ? (byte) 1 : 0));
        setColorBackground(this.mEarBudsInfo.adjustSoundSync);
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
