package com.samsung.accessory.hearablemgr.module.setupwizard;

import com.samsung.accessory.atticmgr.R;

public class NoticePrivacyPolicyActivity extends NoticeActivity {
    private static final String TAG = "Attic_NoticePrivacyPolicyActivity";

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
        return getString(R.string.privacy_policy);
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.accessory.hearablemgr.module.setupwizard.NoticeActivity
    public String getNoticeDescription() {
        return PrivacyNotice.getString();
    }
}
