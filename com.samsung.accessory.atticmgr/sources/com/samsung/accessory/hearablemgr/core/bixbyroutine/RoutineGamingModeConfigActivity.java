package com.samsung.accessory.hearablemgr.core.bixbyroutine;

import android.os.Bundle;
import com.samsung.accessory.atticmgr.R;

public class RoutineGamingModeConfigActivity extends RoutinePresetOnOffConfigActivity {
    /* access modifiers changed from: protected */
    @Override // com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutinePresetOnOffConfigActivity
    public void onCreate(Bundle bundle) {
        setRoutineTitle(R.string.settings_game_mode_title);
        super.onCreate(bundle);
    }
}
