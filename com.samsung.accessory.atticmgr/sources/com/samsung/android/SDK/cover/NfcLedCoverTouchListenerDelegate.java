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
public class NfcLedCoverTouchListenerDelegate extends INfcLedCoverTouchListenerCallback.Stub {
    private static final int MSG_LISTEN_COVER_TOUCH_ACCEPT = 0;
    private static final int MSG_LISTEN_COVER_TOUCH_REJECT = 1;
    private static final int MSG_LISTEN_COVER_TOUCH_REJECT_TAP_LEFT = 2;
    private static final int MSG_LISTEN_COVER_TOUCH_REJECT_TAP_MID = 3;
    private static final int MSG_LISTEN_COVER_TOUCH_REJECT_TAP_RIGHT = 4;
    private ListenerDelegateHandler mHandler;
    private ScoverManager.NfcLedCoverTouchListener mListener;

    public void onSystemCoverEvent(int i, Bundle bundle) throws RemoteException {
    }

    NfcLedCoverTouchListenerDelegate(ScoverManager.NfcLedCoverTouchListener nfcLedCoverTouchListener, Handler handler, Context context) {
        Looper looper;
        this.mListener = nfcLedCoverTouchListener;
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

    public void onCoverTouchAccept() throws RemoteException {
        this.mHandler.obtainMessage(0).sendToTarget();
    }

    public void onCoverTouchReject() throws RemoteException {
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    public void onCoverTapLeft() throws RemoteException {
        this.mHandler.obtainMessage(2).sendToTarget();
    }

    public void onCoverTapMid() throws RemoteException {
        this.mHandler.obtainMessage(3).sendToTarget();
    }

    public void onCoverTapRight() throws RemoteException {
        this.mHandler.obtainMessage(4).sendToTarget();
    }

    private static class ListenerDelegateHandler extends Handler {
        private final WeakReference<ScoverManager.NfcLedCoverTouchListener> mListenerRef;

        public ListenerDelegateHandler(Looper looper, ScoverManager.NfcLedCoverTouchListener nfcLedCoverTouchListener) {
            super(looper);
            this.mListenerRef = new WeakReference<>(nfcLedCoverTouchListener);
        }

        public void handleMessage(Message message) {
            ScoverManager.NfcLedCoverTouchListener nfcLedCoverTouchListener = this.mListenerRef.get();
            if (nfcLedCoverTouchListener != null) {
                int i = message.what;
                if (i == 0) {
                    nfcLedCoverTouchListener.onCoverTouchAccept();
                } else if (i == 1) {
                    nfcLedCoverTouchListener.onCoverTouchReject();
                } else if (i == 2) {
                    nfcLedCoverTouchListener.onCoverTapLeft();
                } else if (i == 3) {
                    nfcLedCoverTouchListener.onCoverTapMid();
                } else if (i == 4) {
                    nfcLedCoverTouchListener.onCoverTapRight();
                }
            }
        }
    }
}
