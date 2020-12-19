package com.samsung.accessory.hearablemgr.common.util;

import android.os.Handler;
import seccompat.android.util.Log;

public class WaitTimer {
    private static final String TAG = "Attic_WaitTimer";
    private Handler mHandler = new Handler();

    public WaitTimer(Handler handler) {
        this.mHandler = handler;
    }

    public void start(int i, long j) {
        Log.d(TAG, "start() : " + Integer.toHexString(i & 255));
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(i), j);
    }

    public void start(int i, long j, long j2) {
        Log.d(TAG, "start() : " + Integer.toHexString(i & 255));
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(i, Long.valueOf(j)), j2);
    }

    public void start(int i, byte b, long j) {
        Log.d(TAG, "start() : " + Integer.toHexString(i & 255));
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(i, Byte.valueOf(b)), j);
    }

    public void start(int i, Object obj, long j) {
        Log.d(TAG, "start() : " + Integer.toHexString(i & 255));
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(i, obj), j);
    }

    public boolean check(int i) {
        Log.d(TAG, "check() : " + i);
        if (!this.mHandler.hasMessages(i)) {
            return false;
        }
        Log.d(TAG, "true");
        return true;
    }

    public boolean check(int i, long j) {
        Log.d(TAG, "check() : " + Integer.toHexString(i & 255));
        if (!this.mHandler.hasMessages(i, Long.valueOf(j))) {
            return false;
        }
        Log.d(TAG, "true");
        return true;
    }

    public boolean check(int i, Object obj) {
        Log.d(TAG, "check() : " + i);
        if (!this.mHandler.hasMessages(i, obj)) {
            return false;
        }
        Log.d(TAG, "true");
        return true;
    }

    public void remove(int i) {
        Log.d(TAG, "remove() : " + Integer.toHexString(i & 255));
        this.mHandler.removeMessages(i);
    }

    public void reset() {
        Log.d(TAG, "reset() : " + this);
        this.mHandler.removeCallbacksAndMessages(null);
    }
}
