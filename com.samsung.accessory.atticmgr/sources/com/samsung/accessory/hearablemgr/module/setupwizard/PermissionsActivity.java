package com.samsung.accessory.hearablemgr.module.setupwizard;

import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity;

public class PermissionsActivity extends OrientationPolicyActivity {
    private static final String TAG = "Attic_PermissionsActivity";
    private StringBuffer mDescription;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_permissions);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (Build.VERSION.SDK_INT < 23) {
            findViewById(R.id.text_guide_for_legacy).setVisibility(0);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
