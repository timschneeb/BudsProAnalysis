package com.samsung.android.app.watchmanager.sdllibrary;

import android.util.Log;
import com.samsung.android.app.watchmanager.libinterface.FloatingFeatureInterface;

public class FloatingFeature implements FloatingFeatureInterface {
    private static final String TAG = "FloatingFeature";

    @Override // com.samsung.android.app.watchmanager.libinterface.FloatingFeatureInterface
    public boolean getEnableStatus(String str) {
        try {
            return com.samsung.android.feature.FloatingFeature.getInstance().getEnableStatus(str);
        } catch (NoClassDefFoundError e) {
            Log.w(TAG, "FloatingFeature()", e);
            return false;
        }
    }
}
