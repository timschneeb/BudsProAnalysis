package com.samsung.android.app.twatchmanager.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.samsung.android.app.twatchmanager.log.Log;

public class Foreground implements Application.ActivityLifecycleCallbacks {
    public static final String TAG = "Foreground";
    private static Foreground instance;
    private AppStatus mAppStatus;
    private int running = 0;

    public enum AppStatus {
        BACKGROUND,
        RETURNED_TO_FOREGROUND,
        FOREGROUND
    }

    private Foreground() {
    }

    public static Foreground get() {
        return instance;
    }

    public static void init(Application application) {
        Log.d(TAG, "init() starts...");
        if (instance == null) {
            instance = new Foreground();
            application.registerActivityLifecycleCallbacks(instance);
        }
    }

    public AppStatus getAppStatus() {
        return this.mAppStatus;
    }

    public boolean isBackground() {
        AppStatus appStatus = this.mAppStatus;
        return appStatus != null && appStatus.ordinal() == AppStatus.BACKGROUND.ordinal();
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
        AppStatus appStatus;
        String str = TAG;
        Log.d(str, "onActivityStarted() starts... running : " + this.running);
        int i = this.running + 1;
        this.running = i;
        if (i == 1) {
            appStatus = AppStatus.RETURNED_TO_FOREGROUND;
        } else if (this.running > 1) {
            appStatus = AppStatus.FOREGROUND;
        } else {
            return;
        }
        this.mAppStatus = appStatus;
    }

    public void onActivityStopped(Activity activity) {
        String str = TAG;
        Log.d(str, "onActivityStopped() starts... running : " + this.running);
        int i = this.running + -1;
        this.running = i;
        if (i == 0) {
            this.mAppStatus = AppStatus.BACKGROUND;
        }
    }
}
