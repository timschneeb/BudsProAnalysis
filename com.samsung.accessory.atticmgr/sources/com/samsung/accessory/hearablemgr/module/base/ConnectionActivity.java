package com.samsung.accessory.hearablemgr.module.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import seccompat.android.util.Log;

public abstract class ConnectionActivity extends PermissionCheckActivity {
    private static final String TAG = "Attic_ConnectionActivity";
    private final BroadcastReceiver mDisconnectedReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.module.base.ConnectionActivity.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            Log.w(ConnectionActivity.TAG, "CoreService.ACTION_DEVICE_DISCONNECTED -> finish()");
            ConnectionActivity.this.finish();
        }
    };

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onStart() {
        super.onStart();
        registerReceiver(this.mDisconnectedReceiver, getDisconnectedIntentFilter());
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onResume() {
        super.onResume();
        if (!Application.getCoreService().isConnected()) {
            Log.w(TAG, "isConnected() == false -> finish()");
            finish();
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onStop() {
        super.onStop();
        unregisterReceiver(this.mDisconnectedReceiver);
    }

    private final IntentFilter getDisconnectedIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_DEVICE_DISCONNECTED);
        return intentFilter;
    }
}
