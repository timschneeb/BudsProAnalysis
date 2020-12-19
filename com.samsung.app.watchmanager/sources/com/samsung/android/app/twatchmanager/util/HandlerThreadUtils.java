package com.samsung.android.app.twatchmanager.util;

import android.os.Build;
import android.os.HandlerThread;

public abstract class HandlerThreadUtils {
    public static void close(HandlerThread handlerThread) {
        if (handlerThread != null && handlerThread.isAlive()) {
            if (Build.VERSION.SDK_INT >= 18) {
                handlerThread.quitSafely();
            } else {
                handlerThread.quit();
            }
        }
    }
}
