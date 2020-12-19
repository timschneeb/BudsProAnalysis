package com.accessorydm.ui.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.sec.android.fotaprovider.R;

public class XDMToastHandler {
    private static final int XDM_EVENT_SHOW_TOAST = 1;
    private static final XDMToastHandler dmToastHandler = new XDMToastHandler();
    private static Toast m_ToastAlreadyAdded = null;
    private final Handler toastHandler;

    private XDMToastHandler() {
        HandlerThread handlerThread = new HandlerThread("XDMToastHandler");
        handlerThread.setDaemon(true);
        handlerThread.start();
        this.toastHandler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            /* class com.accessorydm.ui.handler.$$Lambda$XDMToastHandler$TEzJg1hM7_oM5Lc4PTa032wURyM */

            public final boolean handleMessage(Message message) {
                return XDMToastHandler.this.lambda$new$0$XDMToastHandler(message);
            }
        });
    }

    public /* synthetic */ boolean lambda$new$0$XDMToastHandler(Message message) {
        xdmToastHandlerMessage(message);
        return true;
    }

    private void xdmToastHandlerMessage(Message message) {
        try {
            String str = (String) message.obj;
            int i = message.arg1;
            Log.I("xdmToastHandlerMessage : " + str);
            if (message.what != 1) {
                Log.I("Don't know msg.what..");
            } else {
                if (m_ToastAlreadyAdded == null) {
                    m_ToastAlreadyAdded = Toast.makeText(new ContextThemeWrapper(FotaProviderInitializer.getContext(), R.style.FotaProviderTheme_Activity), (CharSequence) null, i);
                } else if (i == 1) {
                    m_ToastAlreadyAdded.setDuration(1);
                } else {
                    m_ToastAlreadyAdded.setDuration(0);
                }
                m_ToastAlreadyAdded.setText(str);
                m_ToastAlreadyAdded.show();
            }
            this.toastHandler.removeMessages(message.what);
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static void xdmShowToastSendMessage(int i, String str, int i2) {
        try {
            if (dmToastHandler.toastHandler != null) {
                Message obtainMessage = dmToastHandler.toastHandler.obtainMessage();
                obtainMessage.what = i;
                obtainMessage.arg1 = i2;
                obtainMessage.obj = str;
                dmToastHandler.toastHandler.sendMessage(obtainMessage);
                return;
            }
            Log.I("m_hShowToast is null!!");
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public static void xdmShowToast(String str, int i) {
        xdmShowToastSendMessage(1, str, i);
    }
}
