package com.samsung.android.app.watchmanager.selibrary;

import com.samsung.android.app.watchmanager.libinterface.AndroidSystemInterface;

public class AndroidSystemProperties implements AndroidSystemInterface {
    @Override // com.samsung.android.app.watchmanager.libinterface.AndroidSystemInterface
    public String ULTRA_POWERSAVING_MODE() {
        return "ultra_powersaving_mode";
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.AndroidSystemInterface
    public String getSystemProperty(String str) {
        Class cls = ReflectUtil.getClass("android.os.SemSystemProperties");
        Object callMethod = cls != null ? ReflectUtil.callMethod(cls, "get", str) : null;
        return callMethod == null ? "" : (String) callMethod;
    }
}
