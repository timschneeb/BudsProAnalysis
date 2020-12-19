package com.samsung.accessory.hearablemgr.core.bixbyroutine;

import android.os.Bundle;
import com.samsung.accessory.atticmgr.R;

public class RoutineNotificationConfigActivity extends RoutinePresetOnOffConfigActivity {
    /* access modifiers changed from: protected */
    @Override // com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutinePresetOnOffConfigActivity
    public void onCreate(Bundle bundle) {
        setRoutineTitle(R.string.read_notifications_aloud);
        super.onCreate(bundle);
    }
}
