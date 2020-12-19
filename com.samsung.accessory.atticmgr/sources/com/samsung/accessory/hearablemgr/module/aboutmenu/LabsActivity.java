package com.samsung.accessory.hearablemgr.module.aboutmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.gamemode.GameModeManager;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;
import com.samsung.accessory.hearablemgr.module.mainmenu.GamingModeActivity;
import seccompat.android.util.Log;

public class LabsActivity extends ConnectionActivity {
    private static final String TAG = (Application.TAG_ + LabsActivity.class.getSimpleName());
    private View gamingModeLayout;
    private SwitchCompat gamingModeSwitch;
    private EarBudsInfo mEarBudsInfo;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_labs);
        this.mEarBudsInfo = Application.getCoreService().getEarBudsInfo();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initGameModeCard();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onResume() {
        super.onResume();
        SamsungAnalyticsUtil.sendPage(SA.Screen.LABS);
        updateUI();
        updateVoiceAssistant();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        super.onDestroy();
    }

    private void initGameModeCard() {
        this.gamingModeLayout = findViewById(R.id.layout_gaming_mode);
        this.gamingModeLayout.setVisibility(GameModeManager.isSupportDevice() ? 0 : 8);
        this.gamingModeSwitch = (SwitchCompat) findViewById(R.id.switch_gaming_mode);
        this.gamingModeSwitch.setChecked(this.mEarBudsInfo.adjustSoundSync);
        findViewById(R.id.layout_game_mode_switch).setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LabsActivity.AnonymousClass1 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                SamsungAnalyticsUtil.sendEvent(SA.Event.GAME_MODE_DETAIL, SA.Screen.LABS);
                LabsActivity labsActivity = LabsActivity.this;
                labsActivity.startActivity(new Intent(labsActivity, GamingModeActivity.class));
            }
        });
        this.gamingModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LabsActivity.AnonymousClass2 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.ADJUST_SOUND_SYNC, z ? (byte) 1 : 0));
                LabsActivity.this.mEarBudsInfo.adjustSoundSync = z;
                SamsungAnalyticsUtil.sendEvent(SA.Event.GAME_MODE_SWITCH, SA.Screen.LABS, LabsActivity.this.mEarBudsInfo.adjustSoundSync ? "b" : "a");
                SamsungAnalyticsUtil.setStatusString(SA.Status.GAME_MODE, LabsActivity.this.mEarBudsInfo.adjustSoundSync ? "1" : "0");
            }
        });
        findViewById(R.id.switch_layout_gaming_mode).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutmenu.LabsActivity.AnonymousClass3 */

            public void onClick(View view) {
                LabsActivity.this.gamingModeSwitch.setChecked(!LabsActivity.this.gamingModeSwitch.isChecked());
            }
        });
    }

    private void updateUI() {
        initGameModeCard();
    }

    private void updateVoiceAssistant() {
        if (Util.isTalkBackEnabled()) {
            this.gamingModeSwitch.setFocusable(false);
            this.gamingModeSwitch.setClickable(false);
            return;
        }
        this.gamingModeSwitch.setFocusable(true);
        this.gamingModeSwitch.setClickable(true);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        SamsungAnalyticsUtil.sendEvent(SA.Event.UP_BUTTON, SA.Screen.LABS);
        finish();
        return super.onSupportNavigateUp();
    }
}
