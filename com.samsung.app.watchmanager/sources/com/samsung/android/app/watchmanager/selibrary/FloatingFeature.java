package com.samsung.android.app.watchmanager.selibrary;

import com.samsung.android.app.watchmanager.libinterface.FloatingFeatureInterface;

public class FloatingFeature implements FloatingFeatureInterface {
    @Override // com.samsung.android.app.watchmanager.libinterface.FloatingFeatureInterface
    public boolean getEnableStatus(String str) {
        return false;
    }
}
