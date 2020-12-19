package com.samsung.accessory.hearablemgr.common.util;

public abstract class ResponseCallback {
    private Object mExtraObject;

    public abstract void onResponse(String str);

    public void setExtraObject(Object obj) {
        this.mExtraObject = obj;
    }

    /* access modifiers changed from: protected */
    public Object getExtraObject() {
        return this.mExtraObject;
    }
}
