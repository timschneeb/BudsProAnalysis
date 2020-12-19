package com.samsung.accessory.hearablemgr.core.bixbyroutine;

import android.content.Intent;
import android.os.Bundle;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;

public class RoutineBixbyVoiceWakeUpConfigActivity extends RoutinePresetOnOffConfigActivity {
    /* access modifiers changed from: protected */
    @Override // com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutinePresetOnOffConfigActivity
    public void onCreate(Bundle bundle) {
        if (!Application.getAomManager().isSupportAOM()) {
            setIntent(new Intent().putExtra("routine_extra_option", 101));
        }
        setRoutineTitle(R.string.settings_voice_wakeup_title);
        super.onCreate(bundle);
    }
}
