package com.samsung.android.app.watchmanager.sdllibrary;

import android.os.SystemProperties;
import com.samsung.android.app.watchmanager.libinterface.AndroidSystemInterface;

public class AndroidSystemProperties implements AndroidSystemInterface {
    @Override // com.samsung.android.app.watchmanager.libinterface.AndroidSystemInterface
    public String ULTRA_POWERSAVING_MODE() {
        return "ultra_powersaving_mode";
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.AndroidSystemInterface
    public String getSystemProperty(String str) {
        return SystemProperties.get(str);
    }
}
