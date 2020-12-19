package com.samsung.android.sdk.cover;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverManagerCallback;
import com.samsung.android.sdk.cover.ScoverManager;
import java.lang.ref.WeakReference;

/* access modifiers changed from: package-private */
public class CoverListenerDelegate extends ICoverManagerCallback.Stub {
    private static final String TAG = ScoverManager.class.getSimpleName();
    static final int hasAttachFieldVersion = 16842752;
    static final int hasModelFieldVersion = 16908288;
    private ListenerDelegateHandler mHandler;
    private final ScoverManager.StateListener mListener;

    CoverListenerDelegate(ScoverManager.StateListener stateListener, Handler handler, Context context) {
        Looper looper;
        this.mListener = stateListener;
        if (handler == null) {
            looper = context.getMainLooper();
        } else {
            looper = handler.getLooper();
        }
        this.mHandler = new ListenerDelegateHandler(looper, this.mListener);
    }

    public ScoverManager.StateListener getListener() {
        return this.mListener;
    }

    public void coverCallback(CoverState coverState) throws RemoteException {
        Message obtain = Message.obtain();
        obtain.what = 0;
        obtain.obj = coverState;
        this.mHandler.sendMessage(obtain);
    }

    public String getListenerInfo() throws RemoteException {
        return this.mListener.toString();
    }

    private static class ListenerDelegateHandler extends Handler {
        private final WeakReference<ScoverManager.StateListener> mListenerRef;

        public ListenerDelegateHandler(Looper looper, ScoverManager.StateListener stateListener) {
            super(looper);
            this.mListenerRef = new WeakReference<>(stateListener);
        }

        public void handleMessage(Message message) {
            ScoverState scoverState;
            ScoverManager.StateListener stateListener = this.mListenerRef.get();
            if (stateListener != null) {
                CoverState coverState = (CoverState) message.obj;
                if (coverState != null) {
                    if (ScoverManager.isSupportableVersion(CoverListenerDelegate.hasModelFieldVersion)) {
                        scoverState = new ScoverState(coverState.switchState, coverState.type, coverState.color, coverState.widthPixel, coverState.heightPixel, coverState.attached, coverState.model);
                    } else if (ScoverManager.isSupportableVersion(CoverListenerDelegate.hasAttachFieldVersion)) {
                        scoverState = new ScoverState(coverState.switchState, coverState.type, coverState.color, coverState.widthPixel, coverState.heightPixel, coverState.attached);
                    } else {
                        scoverState = new ScoverState(coverState.switchState, coverState.type, coverState.color, coverState.widthPixel, coverState.heightPixel);
                    }
                    stateListener.onCoverStateChanged(scoverState);
                    return;
                }
                Log.e(CoverListenerDelegate.TAG, "coverState : null");
            }
        }
    }
}
