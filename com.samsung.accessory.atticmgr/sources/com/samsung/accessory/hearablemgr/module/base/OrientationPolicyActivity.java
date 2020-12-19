package com.samsung.accessory.hearablemgr.module.base;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.samsung.accessory.hearablemgr.common.util.Util;

public abstract class OrientationPolicyActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRequestedOrientationByPolicy(this);
    }

    public static void setRequestedOrientationByPolicy(Activity activity) {
        if (Util.isTablet()) {
            activity.setRequestedOrientation(2);
        } else {
            activity.setRequestedOrientation(1);
        }
    }
}
