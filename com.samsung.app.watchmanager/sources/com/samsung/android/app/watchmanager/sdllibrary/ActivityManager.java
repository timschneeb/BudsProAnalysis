package com.samsung.android.app.watchmanager.sdllibrary;

import android.app.Activity;
import com.samsung.android.app.watchmanager.libinterface.ActivityManagerInterface;

public class ActivityManager implements ActivityManagerInterface {
    @Override // com.samsung.android.app.watchmanager.libinterface.ActivityManagerInterface
    public void convertFromTranslucent(Activity activity) {
        activity.convertFromTranslucent(false);
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.ActivityManagerInterface
    public void removeTask(android.app.ActivityManager activityManager, int i) {
        activityManager.removeTask(i, 4);
    }
}
