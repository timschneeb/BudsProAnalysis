package com.samsung.android.sdk.cover;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.samsung.android.cover.INfcLedCoverTouchListenerCallback;
import com.samsung.android.sdk.cover.ScoverManager;
import java.lang.ref.WeakReference;

/* access modifiers changed from: package-private */
public class LedSystemEventListenerDelegate extends INfcLedCoverTouchListenerCallback.Stub {
    private static final int MSG_SYSTEM_COVER_EVENT = 0;
    private ListenerDelegateHandler mHandler;
    private ScoverManager.LedSystemEventListener mListener;

    public void onCoverTapLeft() throws RemoteException {
    }

    public void onCoverTapMid() throws RemoteException {
    }

    public void onCoverTapRight() throws RemoteException {
    }

    public void onCoverTouchAccept() throws RemoteException {
    }

    public void onCoverTouchReject() throws RemoteException {
    }

    LedSystemEventListenerDelegate(ScoverManager.LedSystemEventListener ledSystemEventListener, Handler handler, Context context) {
        Looper looper;
        this.mListener = ledSystemEventListener;
        if (handler == null) {
            looper = context.getMainLooper();
        } else {
            looper = handler.getLooper();
        }
        this.mHandler = new ListenerDelegateHandler(looper, this.mListener);
    }

    public Object getListener() {
        return this.mListener;
    }

    public void onSystemCoverEvent(int i, Bundle bundle) throws RemoteException {
        Message obtainMessage = this.mHandler.obtainMessage(0);
        obtainMessage.arg1 = i;
        obtainMessage.obj = bundle;
        obtainMessage.sendToTarget();
    }

    private static class ListenerDelegateHandler extends Handler {
        private final WeakReference<ScoverManager.LedSystemEventListener> mListenerRef;

        public ListenerDelegateHandler(Looper looper, ScoverManager.LedSystemEventListener ledSystemEventListener) {
            super(looper);
            this.mListenerRef = new WeakReference<>(ledSystemEventListener);
        }

        public void handleMessage(Message message) {
            ScoverManager.LedSystemEventListener ledSystemEventListener = this.mListenerRef.get();
            if (ledSystemEventListener != null && message.what == 0) {
                ledSystemEventListener.onSystemCoverEvent(message.arg1, (Bundle) message.obj);
            }
        }
    }
}
