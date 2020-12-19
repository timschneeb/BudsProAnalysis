package com.samsung.android.app.twatchmanager.factory;

import com.samsung.android.app.twatchmanager.util.FeatureUtil;
import com.samsung.android.app.watchmanager.libinterface.ActivityManagerInterface;
import com.samsung.android.app.watchmanager.sdllibrary.ActivityManager;

public class ActivityManagerFactory {
    private static ActivityManagerInterface mInterface;

    public static ActivityManagerInterface get() {
        if (mInterface == null) {
            mInterface = !FeatureUtil.supportSem() ? new ActivityManager() : new com.samsung.android.app.watchmanager.selibrary.ActivityManager();
        }
        return mInterface;
    }
}
