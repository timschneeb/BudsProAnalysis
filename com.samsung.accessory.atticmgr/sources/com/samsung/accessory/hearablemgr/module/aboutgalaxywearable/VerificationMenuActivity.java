package com.samsung.accessory.hearablemgr.module.aboutgalaxywearable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;

public class VerificationMenuActivity extends ConnectionActivity {
    private static final String TAG = (Application.TAG_ + VerificationMenuActivity.class.getSimpleName());

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_verification_menu);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Verification");
        findViewById(R.id.layout_debug).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutgalaxywearable.VerificationMenuActivity.AnonymousClass1 */

            public void onClick(View view) {
                VerificationMenuActivity verificationMenuActivity = VerificationMenuActivity.this;
                verificationMenuActivity.startActivity(new Intent(verificationMenuActivity, VerificationDeviceInfoActivity.class));
            }
        });
        findViewById(R.id.layout_dumplog).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutgalaxywearable.VerificationMenuActivity.AnonymousClass2 */

            public void onClick(View view) {
                VerificationMenuActivity verificationMenuActivity = VerificationMenuActivity.this;
                verificationMenuActivity.startActivity(new Intent(verificationMenuActivity, VerificationDumpLogActivity.class));
            }
        });
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
