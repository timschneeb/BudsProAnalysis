package com.samsung.accessory.hearablemgr.common.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import seccompat.android.util.Log;

public class WorkerHandler extends Handler {
    private static final String TAG = "Attic_WorkerHandler";
    private final HandlerThread mHandlerThread;
    private Handler.Callback mMessageHandler;

    private WorkerHandler(HandlerThread handlerThread) {
        super(handlerThread.getLooper());
        this.mHandlerThread = handlerThread;
        post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.common.util.WorkerHandler.AnonymousClass1 */

            public void run() {
                Log.d(WorkerHandler.TAG, "WorkerHandler() : " + WorkerHandler.this.mHandlerThread.getName());
            }
        });
    }

    public void quit() {
        removeCallbacksAndMessages(null);
        post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.common.util.WorkerHandler.AnonymousClass2 */

            public void run() {
                Log.d(WorkerHandler.TAG, "WorkerHandler().quit() : " + WorkerHandler.this.mHandlerThread.getName());
                try {
                    Looper.myLooper().quit();
                } catch (NullPointerException e) {
                    Log.e(WorkerHandler.TAG, e.toString());
                }
            }
        });
    }

    public void setMessageHandler(Handler.Callback callback) {
        this.mMessageHandler = callback;
    }

    public void handleMessage(Message message) {
        Handler.Callback callback = this.mMessageHandler;
        if (callback != null) {
            callback.handleMessage(message);
        }
    }

    public static WorkerHandler createWorkerHandler(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        return new WorkerHandler(handlerThread);
    }
}
