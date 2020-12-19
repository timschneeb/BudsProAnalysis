package com.samsung.android.app.twatchmanager.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.samsung.android.app.twatchmanager.log.Log;
import java.lang.ref.WeakReference;

public class SafeRemoveTaskHandler extends Handler {
    private static final int CHECK_INTERVAL = 1000;
    private static final int MESSAGE_FOREGROUND_CHECK = 0;
    private static final int MESSAGE_REMOVE_TASK = 1;
    private static final int REMOVE_TASK_DELAY = 300;
    public static final String TAG = ("tUHM:" + SafeRemoveTaskHandler.class.getSimpleName());
    private static final int TIME_LIMIT = 5000;
    WeakReference<Activity> mActivity;
    private int mProgressSec;

    /* access modifiers changed from: private */
    public static class LazyHolder {
        private static final SafeRemoveTaskHandler INSTANCE = new SafeRemoveTaskHandler(Looper.getMainLooper());

        private LazyHolder() {
        }
    }

    private SafeRemoveTaskHandler(Looper looper) {
        super(looper);
        this.mProgressSec = -1;
    }

    public static SafeRemoveTaskHandler getInstance() {
        Log.d(TAG, "getInstance()");
        return LazyHolder.INSTANCE;
    }

    private void removeTask() {
        Log.d(TAG, "removeTask() starts...");
        Activity activity = this.mActivity.get();
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
        HostManagerUtils.handleTaskInternal();
    }

    public void check() {
        long j;
        int i;
        boolean isBackground = Foreground.get().isBackground();
        String str = TAG;
        Log.d(str, "check() getAppStatus : " + Foreground.get().getAppStatus() + " isBackground : " + isBackground);
        if (!isBackground) {
            i = 0;
            j = 1000;
        } else {
            i = 1;
            j = 300;
        }
        sendEmptyMessageDelayed(i, j);
    }

    public void handleMessage(Message message) {
        if (message.what == 1) {
            removeTask();
            stop();
            return;
        }
        this.mProgressSec += 1000;
        if (this.mProgressSec == TIME_LIMIT) {
            sendEmptyMessageDelayed(1, 300);
        } else {
            check();
        }
    }

    public void start(Activity activity) {
        this.mProgressSec = 0;
        this.mActivity = new WeakReference<>(activity);
        check();
    }

    public void stop() {
        removeMessages(1);
        removeMessages(0);
        this.mProgressSec = 0;
        this.mActivity = null;
    }
}
