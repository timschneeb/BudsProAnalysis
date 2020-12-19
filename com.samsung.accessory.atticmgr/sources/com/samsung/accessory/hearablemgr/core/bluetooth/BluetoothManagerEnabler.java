package com.samsung.accessory.hearablemgr.core.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import com.samsung.accessory.hearablemgr.common.util.ResponseCallback;
import seccompat.android.util.Log;

public class BluetoothManagerEnabler {
    public static final String REASON_ERROR = "error";
    public static final String REASON_FAILED = "failed";
    public static final String REASON_TIMEOUT = "timeout";
    private static final String TAG = "Attic_BluetoothManagerEnabler";
    private static final long TIMEOUT_MILLIS = 5000;
    private ResponseCallback mCallback;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.bluetooth.BluetoothManagerEnabler.AnonymousClass2 */

        public void onReceive(Context context, Intent intent) {
            Log.d(BluetoothManagerEnabler.TAG, "onReceive()");
            BluetoothManagerEnabler.this.response(null);
        }
    };
    private boolean mReceiverRegistered = false;

    public BluetoothManagerEnabler(ResponseCallback responseCallback) {
        this.mCallback = responseCallback;
    }

    public void execute() {
        Log.d(TAG, "execute()");
        if (Application.getBluetoothManager().isReady()) {
            response(null);
            return;
        }
        registerReceiver();
        if (BluetoothUtil.getAdapter().enable()) {
            this.mHandler.postDelayed(new Runnable() {
                /* class com.samsung.accessory.hearablemgr.core.bluetooth.BluetoothManagerEnabler.AnonymousClass1 */

                public void run() {
                    BluetoothManagerEnabler.this.response(BluetoothManagerEnabler.REASON_TIMEOUT);
                }
            }, 5000);
        } else {
            response(REASON_ERROR);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void response(String str) {
        unregisterReceiver();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mCallback.onResponse(str);
    }

    private void registerReceiver() {
        Application.getContext().registerReceiver(this.mReceiver, getIntentFilter());
        this.mReceiverRegistered = true;
    }

    private void unregisterReceiver() {
        if (this.mReceiverRegistered) {
            Application.getContext().unregisterReceiver(this.mReceiver);
        }
    }

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothManager.ACTION_READY);
        return intentFilter;
    }
}
