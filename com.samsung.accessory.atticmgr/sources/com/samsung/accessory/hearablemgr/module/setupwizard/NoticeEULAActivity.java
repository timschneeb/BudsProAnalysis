package com.samsung.accessory.hearablemgr.module.setupwizard;

import com.samsung.accessory.atticmgr.R;

public class NoticeEULAActivity extends NoticeActivity {
    private static final String TAG = "Attic_NoticeEULAActivity";

    /* access modifiers changed from: protected */
    @Override // com.samsung.accessory.hearablemgr.module.setupwizard.NoticeActivity
    public String getTAG() {
        return TAG;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, com.samsung.accessory.hearablemgr.module.setupwizard.NoticeActivity
    public /* bridge */ /* synthetic */ boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.accessory.hearablemgr.module.setupwizard.NoticeActivity
    public String getNoticeTile() {
        return getString(R.string.end_user_license_agreement);
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.accessory.hearablemgr.module.setupwizard.NoticeActivity
    public String getNoticeDescription() {
        return AssetString.getStringEULA();
    }
}
