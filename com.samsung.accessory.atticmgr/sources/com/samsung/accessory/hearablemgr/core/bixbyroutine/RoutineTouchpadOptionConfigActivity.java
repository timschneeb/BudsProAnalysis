package com.samsung.accessory.hearablemgr.core.bixbyroutine;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.gson.JsonObject;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldActivity;
import com.samsung.android.SDK.routine.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import seccompat.android.util.Log;

public class RoutineTouchpadOptionConfigActivity extends Activity {
    private static final String TAG = (Application.TAG_ + RoutineTouchpadOptionConfigActivity.class.getSimpleName());
    private TextView cancelButton;
    private RadioButton optionOthers;
    private RadioGroup optionOthersLeft;
    private RadioGroup optionOthersRight;
    private RadioButton optionVolumeControl;
    private LinearLayout optionVolumeControlLayout;
    private TextView positiveButton;
    private int validState;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.validState = getIntent().getIntExtra(Constants.EXTRA_VALID_STATE, 0);
        String str = TAG;
        Log.d(str, "onCreate() validState : " + this.validState);
        int i = this.validState;
        if (i < 0) {
            RoutineUtils.showErrorDialog(this, i);
            return;
        }
        init();
        initListener();
        initOthersView();
        initConfiguration();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.validState >= 0) {
            setPrevParam();
        }
    }

    private void init() {
        setContentView(R.layout.activity_routine_config_touchpad_option);
        getWindow().setGravity(80);
        setTitle(R.string.settings_touchpad_option_menu);
        this.optionVolumeControlLayout = (LinearLayout) findViewById(R.id.radio_group_option_volume_control);
        this.optionVolumeControl = (RadioButton) findViewById(R.id.btn_option_volume_control);
        this.optionOthers = (RadioButton) findViewById(R.id.btn_option_others);
        this.optionOthersLeft = (RadioGroup) findViewById(R.id.routine_touchpad_option_left_group);
        this.optionOthersRight = (RadioGroup) findViewById(R.id.routine_touchpad_option_right_group);
        this.positiveButton = (TextView) findViewById(R.id.btn_ok);
        this.cancelButton = (TextView) findViewById(R.id.btn_cancel);
    }

    private void initListener() {
        this.optionVolumeControlLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.$$Lambda$RoutineTouchpadOptionConfigActivity$zM8VcC9etqqnEck7XiFe5McWfio */

            public final void onClick(View view) {
                RoutineTouchpadOptionConfigActivity.this.lambda$initListener$0$RoutineTouchpadOptionConfigActivity(view);
            }
        });
        this.optionOthers.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.$$Lambda$RoutineTouchpadOptionConfigActivity$YeUvVLzBL_NsQOwfY5Yo5mvd9FM */

            public final void onClick(View view) {
                RoutineTouchpadOptionConfigActivity.this.lambda$initListener$1$RoutineTouchpadOptionConfigActivity(view);
            }
        });
        this.positiveButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.$$Lambda$RoutineTouchpadOptionConfigActivity$2CYqirhMri9yFn6PD__oSkVCx80 */

            public final void onClick(View view) {
                RoutineTouchpadOptionConfigActivity.this.lambda$initListener$2$RoutineTouchpadOptionConfigActivity(view);
            }
        });
        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.$$Lambda$RoutineTouchpadOptionConfigActivity$dXDUkz4X7tPGAjKBfWN3DglYCsE */

            public final void onClick(View view) {
                RoutineTouchpadOptionConfigActivity.this.lambda$initListener$3$RoutineTouchpadOptionConfigActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$RoutineTouchpadOptionConfigActivity(View view) {
        setOptionVolumeControl();
    }

    public /* synthetic */ void lambda$initListener$1$RoutineTouchpadOptionConfigActivity(View view) {
        setOptionOthers();
    }

    public /* synthetic */ void lambda$initListener$2$RoutineTouchpadOptionConfigActivity(View view) {
        saveAction();
    }

    public /* synthetic */ void lambda$initListener$3$RoutineTouchpadOptionConfigActivity(View view) {
        finish();
    }

    private void initOthersView() {
        this.optionOthersLeft.removeAllViews();
        this.optionOthersRight.removeAllViews();
        initDefaultView();
        initAppToAppView();
    }

    private void initDefaultView() {
        addOthersOption(this.optionOthersLeft, getString(getVoiceRecognitionText()), 1).setChecked(true);
        addOthersOption(this.optionOthersRight, getString(getVoiceRecognitionText()), 1).setChecked(true);
        addOthersOption(this.optionOthersLeft, getString(R.string.noise_controls), 2);
        addOthersOption(this.optionOthersRight, getString(R.string.noise_controls), 2);
        if (TouchAndHoldActivity.isReadySpotify()) {
            addOthersOption(this.optionOthersLeft, getString(R.string.settings_touchpad_popup_txt4), 4);
            addOthersOption(this.optionOthersRight, getString(R.string.settings_touchpad_popup_txt4), 4);
        }
    }

    private void initAppToAppView() {
        ArrayList appToAppList = RoutineUtils.getAppToAppList(this);
        if (appToAppList.isEmpty()) {
            Log.d(TAG, "app to app list is empty");
            return;
        }
        Iterator it = appToAppList.iterator();
        while (it.hasNext()) {
            HashMap hashMap = (HashMap) it.next();
            addOthersOption(this.optionOthersLeft, (String) hashMap.get(RoutineConstants.APP_TO_APP_KEY_MENU_NAME), 5, (String) hashMap.get("autho_key"));
            addOthersOption(this.optionOthersRight, (String) hashMap.get(RoutineConstants.APP_TO_APP_KEY_MENU_NAME), 6, (String) hashMap.get("autho_key"));
        }
    }

    private RadioButton addOthersOption(RadioGroup radioGroup, String str, int i, String str2) {
        RadioButton radioButton = (RadioButton) LayoutInflater.from(this).inflate(R.layout.view_radio_routine_touchpad, (ViewGroup) null);
        radioButton.setLayoutParams(new RadioGroup.LayoutParams(-1, -2));
        radioButton.setText(str);
        radioButton.setTag(RoutineConstants.KEY_OPTION_NAME, Integer.valueOf(i));
        radioButton.setTag(RoutineConstants.KEY_OPTION_OTHERS_PACKAGE_NAME, str2);
        radioGroup.addView(radioButton);
        return radioButton;
    }

    private RadioButton addOthersOption(RadioGroup radioGroup, String str, int i) {
        return addOthersOption(radioGroup, str, i, null);
    }

    private void initConfiguration() {
        if (getResources().getConfiguration().getLayoutDirection() == 1) {
            RoutineUtils.setRTLConfigurationWithChildren(this.optionOthersLeft, 0);
            RoutineUtils.setRTLConfigurationWithChildren(this.optionOthersRight, 0);
        }
    }

    private void setPrevParam() {
        String stringExtra = getIntent().getStringExtra(Constants.EXTRA_CONFIG_PARAMS);
        String str = TAG;
        Log.d(str, "prevParam : " + stringExtra);
        if (stringExtra == null) {
            setOptionValue(Preferences.getInt(PreferenceKey.TOUCHPAD_OPTION_LEFT, 1), Preferences.getInt(PreferenceKey.TOUCHPAD_OPTION_RIGHT, 1), Preferences.getString(PreferenceKey.LEFT_OTHER_OPTION_PACKAGE_NAME, ""), Preferences.getString(PreferenceKey.RIGHT_OTHER_OPTION_PACKAGE_NAME, ""));
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(stringExtra);
            int i = jSONObject.getInt("touch_option_left");
            int i2 = jSONObject.getInt("touch_option_right");
            String str2 = null;
            String string = i == 5 ? jSONObject.getString("package_name_left") : null;
            if (i2 == 6) {
                str2 = jSONObject.getString("package_name_right");
            }
            setOptionValue(i, i2, string, str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setOptionValue(int i, int i2, String str, String str2) {
        String str3 = TAG;
        Log.d(str3, "setOptionValue() leftOption : " + i + ", rightOption : " + i2 + ", leftPackageName : " + str + ", rightPackageName : " + str2);
        if (i == 3) {
            setOptionVolumeControl();
            return;
        }
        setOptionOthers();
        RadioButton findViewByOption = findViewByOption(this.optionOthersLeft, i, str);
        if (findViewByOption != null) {
            findViewByOption.setChecked(true);
        }
        RadioButton findViewByOption2 = findViewByOption(this.optionOthersRight, i2, str2);
        if (findViewByOption2 != null) {
            findViewByOption2.setChecked(true);
        }
    }

    private void setOptionVolumeControl() {
        this.optionVolumeControl.setChecked(true);
        this.optionOthers.setChecked(false);
        UiUtil.setEnabledWithChildren(this.optionOthersLeft, false);
        UiUtil.setEnabledWithChildren(this.optionOthersRight, false);
    }

    private void setOptionOthers() {
        this.optionVolumeControl.setChecked(false);
        this.optionOthers.setChecked(true);
        UiUtil.setEnabledWithChildren(this.optionOthersLeft, true);
        UiUtil.setEnabledWithChildren(this.optionOthersRight, true);
    }

    private RadioButton findViewByOption(ViewGroup viewGroup, int i, String str) {
        if (i == 1 || i == 2 || i == 4) {
            return (RadioButton) RoutineUtils.findViewByTag(viewGroup, RoutineConstants.KEY_OPTION_NAME, Integer.valueOf(i));
        }
        if ((i == 5 || i == 6) && str != null) {
            return (RadioButton) RoutineUtils.findViewByTag(viewGroup, RoutineConstants.KEY_OPTION_OTHERS_PACKAGE_NAME, str);
        }
        return null;
    }

    private void saveAction() {
        String str;
        JsonObject jsonObject;
        if (this.optionVolumeControl.isChecked()) {
            jsonObject = RoutineUtils.makeTouchpadOptionToJson(3, 3, null, null);
            str = getString(R.string.routine_volume_control);
        } else {
            RadioButton currentCheckedLeftButton = getCurrentCheckedLeftButton();
            RadioButton currentCheckedRightButton = getCurrentCheckedRightButton();
            JsonObject makeTouchpadOptionToJson = RoutineUtils.makeTouchpadOptionToJson(((Integer) currentCheckedLeftButton.getTag(RoutineConstants.KEY_OPTION_NAME)).intValue(), ((Integer) currentCheckedRightButton.getTag(RoutineConstants.KEY_OPTION_NAME)).intValue(), (String) currentCheckedLeftButton.getTag(RoutineConstants.KEY_OPTION_OTHERS_PACKAGE_NAME), (String) currentCheckedRightButton.getTag(RoutineConstants.KEY_OPTION_OTHERS_PACKAGE_NAME));
            str = getString(R.string.routine_label_touchpad_left, new Object[]{currentCheckedLeftButton.getText()}) + "\n" + getString(R.string.routine_label_touchpad_right, new Object[]{currentCheckedRightButton.getText()});
            jsonObject = makeTouchpadOptionToJson;
        }
        RoutineUtils.save(this, str, jsonObject.toString());
    }

    private RadioButton getCurrentCheckedLeftButton() {
        RadioGroup radioGroup = this.optionOthersLeft;
        return (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
    }

    private RadioButton getCurrentCheckedRightButton() {
        RadioGroup radioGroup = this.optionOthersRight;
        return (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
    }

    private int getVoiceRecognitionText() {
        return Util.isBixbyDefaultVoiceCommandAgent() ? R.string.settings_touchpad_popup_txt1_bixby : R.string.settings_touchpad_popup_txt1_normal;
    }
}
