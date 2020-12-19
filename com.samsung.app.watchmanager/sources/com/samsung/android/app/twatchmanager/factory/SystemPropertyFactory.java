package com.samsung.android.app.twatchmanager.factory;

import com.samsung.android.app.twatchmanager.util.FeatureUtil;
import com.samsung.android.app.watchmanager.libinterface.AndroidSystemInterface;
import com.samsung.android.app.watchmanager.sdllibrary.AndroidSystemProperties;

public class SystemPropertyFactory {
    private static AndroidSystemInterface mInterface;

    public static AndroidSystemInterface getAndroidSystemProperty() {
        if (mInterface == null) {
            mInterface = !FeatureUtil.supportSem() ? new AndroidSystemProperties() : new com.samsung.android.app.watchmanager.selibrary.AndroidSystemProperties();
        }
        return mInterface;
    }
}
