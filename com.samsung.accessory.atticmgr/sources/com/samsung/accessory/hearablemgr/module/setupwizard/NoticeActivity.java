package com.samsung.accessory.hearablemgr.module.setupwizard;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity;
import seccompat.android.util.Log;

abstract class NoticeActivity extends OrientationPolicyActivity {
    private static final String TAG = "Attic_NoticeActivity";

    /* access modifiers changed from: protected */
    public abstract String getNoticeDescription();

    /* access modifiers changed from: protected */
    public abstract String getNoticeTile();

    /* access modifiers changed from: protected */
    public abstract String getTAG();

    NoticeActivity() {
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        Log.d(getTAG(), "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_notice);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(getNoticeTile());
        ((TextView) findViewById(R.id.text_description)).setText(getNoticeDescription());
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
