package com.samsung.accessory.hearablemgr.module.aboutmenu;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity;

public class BatteryInformationActivity extends PermissionCheckActivity {
    private static final String TAG = "Attic_BatteryInformationActivity";
    private Context mContext;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        setContentView(R.layout.activity_battery_information);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(this.mContext.getString(R.string.about_earbuds_battery_information));
        int integer = getResources().getInteger(R.integer.earbuds_battery_capacity);
        int integer2 = getResources().getInteger(R.integer.cradle_battery_capacity);
        ((TextView) findViewById(R.id.earbuds_battery_rated)).setText(getString(R.string.earbuds_battery_rated, new Object[]{5, Float.valueOf(0.2f)}));
        ((TextView) findViewById(R.id.earbuds_battery_capacity)).setText(getString(R.string.earbuds_battery_capacity, new Object[]{Integer.valueOf(integer)}));
        ((TextView) findViewById(R.id.charging_case_rated)).setText(getString(R.string.case_battery_rated, new Object[]{5, Float.valueOf(0.6f)}));
        ((TextView) findViewById(R.id.charging_case_capacity)).setText(getString(R.string.case_battery_capacity, new Object[]{Integer.valueOf(integer2)}));
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
