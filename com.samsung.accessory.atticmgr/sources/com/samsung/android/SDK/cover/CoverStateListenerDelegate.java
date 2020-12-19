package com.samsung.android.sdk.cover;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.samsung.android.cover.ICoverStateListenerCallback;
import com.samsung.android.sdk.cover.ScoverManager;
import java.lang.ref.WeakReference;

/* access modifiers changed from: package-private */
public class CoverStateListenerDelegate extends ICoverStateListenerCallback.Stub {
    private static final int MSG_LISTEN_COVER_ATTACH_STATE_CHANGE = 1;
    private static final int MSG_LISTEN_COVER_SWITCH_STATE_CHANGE = 0;
    public static final int TYPE_COVER_STATE_LISTENER = 2;
    private ListenerDelegateHandler mHandler;
    private final ScoverManager.CoverStateListener mListener;

    CoverStateListenerDelegate(ScoverManager.CoverStateListener coverStateListener, Handler handler, Context context) {
        Looper looper;
        this.mListener = coverStateListener;
        if (handler == null) {
            looper = context.getMainLooper();
        } else {
            looper = handler.getLooper();
        }
        this.mHandler = new ListenerDelegateHandler(looper, this.mListener);
    }

    public ScoverManager.CoverStateListener getListener() {
        return this.mListener;
    }

    public void onCoverSwitchStateChanged(boolean z) throws RemoteException {
        Message.obtain(this.mHandler, 0, z ? 1 : 0, 0).sendToTarget();
    }

    public void onCoverAttachStateChanged(boolean z) throws RemoteException {
        Message.obtain(this.mHandler, 1, z ? 1 : 0, 0).sendToTarget();
    }

    public String getListenerInfo() throws RemoteException {
        return this.mListener.toString();
    }

    private static class ListenerDelegateHandler extends Handler {
        private final WeakReference<ScoverManager.CoverStateListener> mListenerRef;

        public ListenerDelegateHandler(Looper looper, ScoverManager.CoverStateListener coverStateListener) {
            super(looper);
            this.mListenerRef = new WeakReference<>(coverStateListener);
        }

        public void handleMessage(Message message) {
            ScoverManager.CoverStateListener coverStateListener = this.mListenerRef.get();
            if (coverStateListener != null) {
                int i = message.what;
                boolean z = false;
                if (i == 0) {
                    if (message.arg1 == 1) {
                        z = true;
                    }
                    coverStateListener.onCoverSwitchStateChanged(z);
                } else if (i == 1) {
                    if (message.arg1 == 1) {
                        z = true;
                    }
                    coverStateListener.onCoverAttachStateChanged(z);
                }
            }
        }
    }
}
