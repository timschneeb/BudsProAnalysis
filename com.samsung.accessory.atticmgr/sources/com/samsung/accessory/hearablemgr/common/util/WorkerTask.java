package com.samsung.accessory.hearablemgr.common.util;

import com.samsung.accessory.hearablemgr.Application;
import seccompat.android.util.Log;

public abstract class WorkerTask implements Runnable {
    protected final String TAG = (Application.TAG_ + getClass().getSimpleName());

    public abstract void execute();

    public void run() {
        Log.d(this.TAG, "execute()");
        execute();
        Log.d(this.TAG, "execute()_end");
    }
}
