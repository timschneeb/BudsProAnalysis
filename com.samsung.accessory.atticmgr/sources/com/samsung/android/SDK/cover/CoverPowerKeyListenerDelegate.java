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
public class CoverPowerKeyListenerDelegate extends INfcLedCoverTouchListenerCallback.Stub {
    private static final int MSG_SYSTEM_COVER_EVENT = 0;
    private ListenerDelegateHandler mHandler;
    private ScoverManager.CoverPowerKeyListener mListener;

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

    private static final class SystemEvents {
        private static final String KEY_DISABLE_LCD_OFF_BY_COVER = "lcd_off_disabled_by_cover";
        private static final int LCD_OFF_DISABLED_BY_COVER = 4;
        private static final int LED_OFF = 0;
        private static final int NOTIFICATION_ADD = 2;
        private static final int NOTIFICATION_REMOVE = 3;
        private static final int POWER_BUTTON = 1;

        private SystemEvents() {
        }
    }

    CoverPowerKeyListenerDelegate(ScoverManager.CoverPowerKeyListener coverPowerKeyListener, Handler handler, Context context) {
        this.mListener = coverPowerKeyListener;
        this.mHandler = new ListenerDelegateHandler(handler == null ? context.getMainLooper() : handler.getLooper(), this.mListener);
    }

    public Object getListener() {
        return this.mListener;
    }

    public void onSystemCoverEvent(int i, Bundle bundle) throws RemoteException {
        if (i == 1) {
            this.mHandler.sendEmptyMessage(0);
        }
    }

    private static class ListenerDelegateHandler extends Handler {
        private final WeakReference<ScoverManager.CoverPowerKeyListener> mListenerRef;

        public ListenerDelegateHandler(Looper looper, ScoverManager.CoverPowerKeyListener coverPowerKeyListener) {
            super(looper);
            this.mListenerRef = new WeakReference<>(coverPowerKeyListener);
        }

        public void handleMessage(Message message) {
            ScoverManager.CoverPowerKeyListener coverPowerKeyListener = this.mListenerRef.get();
            if (message.what == 0 && coverPowerKeyListener != null) {
                coverPowerKeyListener.onPowerKeyPress();
            }
        }
    }
}
