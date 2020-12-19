package com.accessorydm.eng.core;

import android.os.Message;
import com.accessorydm.agent.XDMTask;
import com.accessorydm.agent.XDMUITask;
import com.accessorydm.interfaces.XEventInterface;
import com.samsung.android.fotaprovider.log.Log;

public class XDMMsg {
    private static final Object syncMsgQueueObj = new Object();
    private static final Object syncUIMsgQueueObj = new Object();
    public XDMMsgItem msgItem = new XDMMsgItem();

    public static class XDMMsgItem {
        public XDMMsgParam param;
        public Object type;
    }

    public static class XDMMsgParam {
        public Object param;
        public Object paramFree;
    }

    public static void xdmSendMessage(XEventInterface.XEVENT xevent, Object obj, Object obj2) {
        XDMMsgParam xDMMsgParam = null;
        if (obj != null) {
            XDMMsgParam xDMMsgParam2 = new XDMMsgParam();
            xDMMsgParam2.param = obj;
            xDMMsgParam2.paramFree = null;
            if (obj2 != null) {
                xDMMsgParam2.paramFree = obj2;
            }
            xDMMsgParam = xDMMsgParam2;
        }
        synchronized (syncMsgQueueObj) {
            XDMMsgItem xDMMsgItem = new XDMMsgItem();
            if (XDMTask.g_hDmTask == null) {
                for (int i = 0; i < 2; i++) {
                    try {
                        Log.I("waiting for DM_TaskHandler create");
                        syncMsgQueueObj.wait(300);
                    } catch (InterruptedException e) {
                        Log.E(e.toString());
                    }
                    if (XDMTask.g_hDmTask == null) {
                    }
                }
            }
            try {
                xDMMsgItem.type = xevent;
                xDMMsgItem.param = xDMMsgParam;
                Message obtainMessage = XDMTask.g_hDmTask.obtainMessage();
                obtainMessage.what = xevent.ordinal();
                obtainMessage.obj = xDMMsgItem;
                XDMTask.g_hDmTask.sendMessage(obtainMessage);
            } catch (Exception unused) {
                Log.E("Can't send message");
            }
        }
    }

    public static void xdmSendUIMessage(Object obj, Object obj2, Object obj3) {
        XDMMsgParam xDMMsgParam = null;
        if (obj2 != null) {
            XDMMsgParam xDMMsgParam2 = new XDMMsgParam();
            xDMMsgParam2.param = obj2;
            xDMMsgParam2.paramFree = null;
            if (obj3 != null) {
                xDMMsgParam2.paramFree = obj3;
            }
            xDMMsgParam = xDMMsgParam2;
        }
        synchronized (syncUIMsgQueueObj) {
            XDMMsgItem xDMMsgItem = new XDMMsgItem();
            if (XDMUITask.g_hDmUiTask == null) {
                for (int i = 0; i < 2; i++) {
                    try {
                        Log.I("waiting for hUITask create");
                        syncUIMsgQueueObj.wait(300);
                    } catch (InterruptedException e) {
                        Log.E(e.toString());
                    }
                    if (XDMUITask.g_hDmUiTask == null) {
                    }
                }
            }
            try {
                xDMMsgItem.type = obj;
                xDMMsgItem.param = xDMMsgParam;
                Message obtainMessage = XDMUITask.g_hDmUiTask.obtainMessage();
                obtainMessage.obj = xDMMsgItem;
                XDMUITask.g_hDmUiTask.sendMessage(obtainMessage);
            } catch (Exception unused) {
                Log.E("Can't send UI message");
            }
        }
    }

    public static XDMAbortMsgParam xdmCreateAbortMessage(int i, boolean z) {
        XDMAbortMsgParam xDMAbortMsgParam = new XDMAbortMsgParam();
        xDMAbortMsgParam.abortCode = i;
        xDMAbortMsgParam.userReq = z;
        return xDMAbortMsgParam;
    }
}
