package com.samsung.accessory.hearablemgr.common.util;

import android.os.Handler;
import android.os.SystemClock;
import seccompat.android.util.Log;

public class ActionBuffer {
    private static final long BUFFER_MILLIS = 5000;
    private static final String TAG = "Attic_ActionBuffer";
    private final Runnable mActionRunnable = new Runnable() {
        /* class com.samsung.accessory.hearablemgr.common.util.ActionBuffer.AnonymousClass1 */

        public void run() {
            ActionBuffer.this.mLastActionUptimeMillis = SystemClock.uptimeMillis();
            Log.d(ActionBuffer.TAG, "mActionRunnable.run() : " + ActionBuffer.this.mLastActionUptimeMillis);
            ActionBuffer.this.mUserAction.run();
        }
    };
    private final long mBufferMillis;
    private final Handler mHandler = new Handler();
    private long mLastActionUptimeMillis;
    private final Runnable mUserAction;

    public ActionBuffer(Runnable runnable) {
        this.mUserAction = runnable;
        this.mBufferMillis = 5000;
    }

    public ActionBuffer(Runnable runnable, long j) {
        this.mUserAction = runnable;
        this.mBufferMillis = j;
    }

    public void destroy() {
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public void action() {
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = this.mLastActionUptimeMillis;
        long j2 = this.mBufferMillis;
        if (uptimeMillis - j > j2) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mActionRunnable.run();
            Log.d(TAG, "action() : action");
            return;
        }
        long j3 = uptimeMillis + (j2 - (uptimeMillis - j));
        Log.d(TAG, "action() : atUptimeMillis=" + j3 + " (now=" + SystemClock.uptimeMillis() + ")");
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.postAtTime(this.mActionRunnable, j3);
    }
}
