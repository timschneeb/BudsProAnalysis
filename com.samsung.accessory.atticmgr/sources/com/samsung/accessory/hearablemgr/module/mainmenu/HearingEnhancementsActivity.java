package com.samsung.accessory.hearablemgr.module.mainmenu;

import android.os.Bundle;
import android.widget.SeekBar;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;
import seccompat.android.util.Log;

public class HearingEnhancementsActivity extends ConnectionActivity {
    private static final String TAG = (Application.TAG_ + HearingEnhancementsActivity.class.getSimpleName());
    private EarBudsInfo mEarBudsInfo;
    private long preTime = 0;
    SeekBar seekBar;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_hearing_enhancements);
        this.mEarBudsInfo = Application.getCoreService().getEarBudsInfo();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initHearingEnhancements();
    }

    private void initHearingEnhancements() {
        this.mEarBudsInfo = Application.getCoreService().getEarBudsInfo();
        this.seekBar = (SeekBar) findViewById(R.id.sound_balance_seekbar);
        this.seekBar.setProgress(this.mEarBudsInfo.hearingEnhancements);
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.HearingEnhancementsActivity.AnonymousClass1 */

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (System.currentTimeMillis() - HearingEnhancementsActivity.this.preTime > 100) {
                    Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_HEARING_ENHANCEMENTS, (byte) i));
                    HearingEnhancementsActivity.this.mEarBudsInfo.hearingEnhancements = i;
                }
                HearingEnhancementsActivity.this.preTime = System.currentTimeMillis();
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() != HearingEnhancementsActivity.this.mEarBudsInfo.hearingEnhancements) {
                    Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_HEARING_ENHANCEMENTS, (byte) seekBar.getProgress()));
                    HearingEnhancementsActivity.this.mEarBudsInfo.hearingEnhancements = seekBar.getProgress();
                    SamsungAnalyticsUtil.setStatusString(SA.Status.SOUND_BALANCE, SamsungAnalyticsUtil.makeSoundBalanceDetail(HearingEnhancementsActivity.this.mEarBudsInfo.hearingEnhancements));
                }
                SamsungAnalyticsUtil.sendEvent(SA.Event.SOUND_BALANCE);
            }
        });
    }

    private void updateHearingEnhancements() {
        this.seekBar.setProgress(this.mEarBudsInfo.hearingEnhancements);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onResume() {
        super.onResume();
        updateHearingEnhancements();
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
}
