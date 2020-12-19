package com.samsung.accessory.hearablemgr.core.bixbyroutine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.android.SDK.routine.Constants;
import seccompat.android.util.Log;

public class RoutineNoiseControlConfigActivity extends Activity {
    private static final String TAG = (Application.TAG_ + RoutineNoiseControlConfigActivity.class.getSimpleName());
    private RadioGroup radioGroup;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int intExtra = getIntent().getIntExtra(Constants.EXTRA_VALID_STATE, 0);
        String str = TAG;
        Log.d(str, "validState  : " + intExtra);
        if (intExtra < 0) {
            RoutineUtils.showErrorDialog(this, intExtra);
            return;
        }
        init();
        initPrevParam();
        initButtonView();
    }

    private void init() {
        setContentView(R.layout.activity_routine_config_noise_controls);
        getWindow().setGravity(80);
        setTitle(R.string.noise_controls);
        this.radioGroup = (RadioGroup) findViewById(R.id.radio_group);
    }

    private void initPrevParam() {
        String stringExtra = getIntent().getStringExtra(Constants.EXTRA_CONFIG_PARAMS);
        if (stringExtra == null) {
            stringExtra = String.valueOf(Preferences.getInt(PreferenceKey.NOISE_CONTROL_STATE, 0));
        }
        try {
            ((RadioButton) this.radioGroup.getChildAt(Integer.valueOf(stringExtra).intValue())).setChecked(true);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    private void initButtonView() {
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.$$Lambda$RoutineNoiseControlConfigActivity$CrQnBTInJKVMVpr_jqWIwYMCG0Q */

            public final void onClick(View view) {
                RoutineNoiseControlConfigActivity.this.lambda$initButtonView$0$RoutineNoiseControlConfigActivity(view);
            }
        });
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.$$Lambda$RoutineNoiseControlConfigActivity$Ldb9Yzeyom116P61PL6YDQ7X4w */

            public final void onClick(View view) {
                RoutineNoiseControlConfigActivity.this.lambda$initButtonView$1$RoutineNoiseControlConfigActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initButtonView$0$RoutineNoiseControlConfigActivity(View view) {
        saveCurrentParam();
    }

    public /* synthetic */ void lambda$initButtonView$1$RoutineNoiseControlConfigActivity(View view) {
        finish();
    }

    private void saveCurrentParam() {
        RadioGroup radioGroup2 = this.radioGroup;
        RadioButton radioButton = (RadioButton) radioGroup2.findViewById(radioGroup2.getCheckedRadioButtonId());
        RoutineUtils.save(this, radioButton.getText().toString(), String.valueOf(this.radioGroup.indexOfChild(radioButton)));
    }
}
