package com.samsung.android.app.watchmanager.libinterface;

import android.app.Activity;
import android.app.ActivityManager;

public interface ActivityManagerInterface {
    void convertFromTranslucent(Activity activity);

    void removeTask(ActivityManager activityManager, int i);
}
