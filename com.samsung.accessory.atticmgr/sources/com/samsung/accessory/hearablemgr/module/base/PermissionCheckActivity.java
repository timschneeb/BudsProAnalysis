package com.samsung.accessory.hearablemgr.module.base;

import android.os.Bundle;
import com.samsung.accessory.hearablemgr.Application;
import seccompat.android.util.Log;

public abstract class PermissionCheckActivity extends OrientationPolicyActivity {
    private static final String TAG = (Application.TAG_ + PermissionCheckActivity.class.getSimpleName());
    private PermissionCheckImpl mImpl;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i(TAG, "onCreate");
        this.mImpl = new PermissionCheckImpl(this);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        this.mImpl.checkPermission();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onPause() {
        super.onPause();
        this.mImpl.dismissDialog();
    }

    @Override // androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback, androidx.fragment.app.FragmentActivity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        Log.d(TAG, "onRequestPermissionsResult()");
        this.mImpl.onRequestPermissionsResult(i, strArr, iArr);
    }
}
