package com.samsung.android.app.twatchmanager.factory;

import com.samsung.android.app.twatchmanager.util.FeatureUtil;
import com.samsung.android.app.watchmanager.libinterface.FloatingFeatureInterface;
import com.samsung.android.app.watchmanager.sdllibrary.FloatingFeature;

public class FloatingFeatureFactory {
    private static FloatingFeatureInterface mInterface;

    public static FloatingFeatureInterface get() {
        if (mInterface == null) {
            mInterface = !FeatureUtil.supportSem() ? new FloatingFeature() : new com.samsung.android.app.watchmanager.selibrary.FloatingFeature();
        }
        return mInterface;
    }
}
