package com.samsung.accessory.hearablemgr.module;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.module.home.HomeActivity;
import com.samsung.accessory.hearablemgr.module.setupwizard.TermsAndConditionsActivity;
import seccompat.android.util.Log;

public class LaunchActivity extends AppCompatActivity {
    private static final String TAG = "Attic_LaunchActivity";
    private static Integer sLaunchMode;
    private String mDeviceId = null;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        int intValue;
        Log.d(TAG, "onCreate() : versionCode=2020121151");
        super.onCreate(bundle);
        printIntentExtras();
        int intExtra = getIntent().getIntExtra(UhmFwUtil.EXTRA_LAUNCH_DATA_LAUNCH_MODE, -1);
        sLaunchMode = intExtra != -1 ? Integer.valueOf(intExtra) : null;
        this.mDeviceId = getIntent().getStringExtra("deviceid");
        if (Util.isEmulator()) {
            this.mDeviceId = "00:00:00:00:00:00";
        }
        if (TextUtils.isEmpty(this.mDeviceId)) {
            Log.e(TAG, "mDeviceId == null");
        } else {
            UhmFwUtil.setLastLaunchDeviceId(this.mDeviceId);
            Application.getCoreService().disconnectOtherDevice(this.mDeviceId);
        }
        if (UhmFwUtil.getLastLaunchDeviceId() == null) {
            UhmFwUtil.startNewDeviceActivity(this, false);
        } else if (!Preferences.getBoolean(PreferenceKey.SETUP_WIZARD_DONE, false)) {
            startActivity(new Intent(this, TermsAndConditionsActivity.class));
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            if (getLaunchMode() != null && ((intValue = getLaunchMode().intValue()) == 1002 || intValue == 1003 || intValue == 1006 || intValue == 1009)) {
                intent.putExtra(HomeActivity.EXTRA_AUTO_CONNECT, true);
            }
            startActivity(intent);
        }
        finish();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    private static Integer getLaunchMode() {
        return sLaunchMode;
    }

    private void printIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.i(TAG, "printIntentExtras() : bundle == null");
            return;
        }
        Log.i(TAG, "printIntentExtras() :");
        for (String str : extras.keySet()) {
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 192752574) {
                if (hashCode != 831630091) {
                    if (hashCode == 1109192177 && str.equals("deviceid")) {
                        c = 0;
                    }
                } else if (str.equals("device_address")) {
                    c = 1;
                }
            } else if (str.equals(UhmFwUtil.EXTRA_LAUNCH_DATA_BT_ADDRESS)) {
                c = 2;
            }
            if (c == 0 || c == 1 || c == 2) {
                StringBuilder sb = new StringBuilder();
                sb.append("    ");
                sb.append(str);
                sb.append(" = ");
                sb.append(BluetoothUtil.privateAddress(extras.get(str) != null ? extras.get(str).toString() : null));
                Log.i(TAG, sb.toString());
            } else {
                Log.i(TAG, "    " + str + " = " + extras.get(str));
            }
        }
    }
}
