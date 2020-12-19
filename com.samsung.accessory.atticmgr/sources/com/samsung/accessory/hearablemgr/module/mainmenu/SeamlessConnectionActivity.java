package com.samsung.accessory.hearablemgr.module.mainmenu;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;
import seccompat.android.util.Log;

public class SeamlessConnectionActivity extends ConnectionActivity {
    public static final byte SC_PARAM_OFF = 1;
    public static final byte SC_PARAM_ON = 0;
    private static final String TAG = (Application.TAG_ + SeamlessConnectionActivity.class.getSimpleName());
    private EarBudsInfo mEarBudsInfo;
    private SeslSwitchBar mSwitchBar;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_seamless_connection);
        this.mEarBudsInfo = Application.getCoreService().getEarBudsInfo();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initSeamlessConnection();
    }

    private void initSeamlessConnection() {
        this.mSwitchBar = (SeslSwitchBar) findViewById(R.id.switch_bar);
        this.mSwitchBar.setChecked(this.mEarBudsInfo.seamlessConnection);
        setColorBackground(this.mEarBudsInfo.seamlessConnection);
        this.mSwitchBar.addOnSwitchChangeListener(new SeslSwitchBar.OnSwitchChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.$$Lambda$SeamlessConnectionActivity$TdCK39YH4mt6ALqdrd40X0zF32k */

            @Override // androidx.appcompat.widget.SeslSwitchBar.OnSwitchChangeListener
            public final void onSwitchChanged(SwitchCompat switchCompat, boolean z) {
                SeamlessConnectionActivity.this.lambda$initSeamlessConnection$0$SeamlessConnectionActivity(switchCompat, z);
            }
        });
        if (Util.isJapanModel()) {
            ((TextView) findViewById(R.id.text_seamless_connection_desc)).setText(R.string.settings_seamless_connection_desc_jpn);
        }
    }

    public /* synthetic */ void lambda$initSeamlessConnection$0$SeamlessConnectionActivity(SwitchCompat switchCompat, boolean z) {
        this.mEarBudsInfo.seamlessConnection = z;
        Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_SEAMLESS_CONNECTION, !this.mEarBudsInfo.seamlessConnection ? 1 : 0 ? (byte) 1 : 0));
        setColorBackground(this.mEarBudsInfo.seamlessConnection);
        SamsungAnalyticsUtil.sendEvent(SA.Event.SEAMLESS_EARBUD_CONNECTION, (String) null, z ? "b" : "a");
        SamsungAnalyticsUtil.setStatusInt(SA.Status.SEAMLESS_EARBUD_CONNECTION_STATUS, z ? 1 : 0);
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
        Preferences.putBoolean(PreferenceKey.SEAMLESS_CONNECTION_CARD_SHOW_AGAIN, false, Preferences.MODE_MANAGER);
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
