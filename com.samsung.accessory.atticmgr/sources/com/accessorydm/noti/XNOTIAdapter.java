package com.accessorydm.noti;

import android.text.TextUtils;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.XDMServiceManager;
import com.accessorydm.adapter.XDMInitAdapter;
import com.accessorydm.agent.XDMAgent;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBNoti;
import com.accessorydm.db.file.XDBPostPoneAdp;
import com.accessorydm.db.file.XDBProfileAdp;
import com.accessorydm.db.file.XDBProfileListAdp;
import com.accessorydm.db.file.XDBSessionSaveInfo;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XNOTIInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.postpone.PostponeType;
import com.accessorydm.ui.XUIAdapter;
import com.accessorydm.ui.dialog.XUIDialog;
import com.accessorydm.ui.dialog.XUIDialogActivity;
import com.accessorydm.ui.notification.XUINotificationManager;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.NetworkUtil;
import java.io.Serializable;
import java.util.LinkedList;

public class XNOTIAdapter extends XDMAgent implements Serializable, XNOTIInterface {
    private static final int XNOTI_RETRY_COUNT_MAX = 5;
    private static boolean m_NotiProcessing = false;
    private static LinkedList<XNOTIData> m_PushDataQueue = new LinkedList<>();
    private static final long serialVersionUID = 1;

    public void xnotiPushAdpReceiveMsg(byte[] bArr, int i) {
        XNOTIWapPush xnotiPushHdlrParsingWSPHeader = XNOTIHandler.xnotiPushHdlrParsingWSPHeader(bArr, i);
        if (xnotiPushHdlrParsingWSPHeader == null) {
            Log.E("xnotiPushAdpReceiveMsg Error");
        } else if (xnotiPushHdlrParsingWSPHeader.tWapPushInfo.nContentType != 68) {
            Log.H("Not Support Content Type :" + String.format("0x%x", Integer.valueOf(xnotiPushHdlrParsingWSPHeader.tWapPushInfo.nContentType)));
        } else {
            XNOTIMessage xNOTIMessage = new XNOTIMessage();
            xNOTIMessage.push_type = 1;
            xNOTIMessage.pData = new byte[i];
            xNOTIMessage.dataSize = i;
            xNOTIMessage.pData = bArr;
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_NOTI_RECEIVED, xNOTIMessage, null);
        }
    }

    private void xnotiIpPushAdpReceiveMsg(byte[] bArr, int i) {
        XNOTIMessage xNOTIMessage = new XNOTIMessage();
        xNOTIMessage.push_type = 1;
        xNOTIMessage.pBody = bArr;
        xNOTIMessage.bodySize = i;
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_NOTI_RECEIVED, xNOTIMessage, null);
    }

    public static boolean xnotiGetNotiProcessing() {
        return m_NotiProcessing;
    }

    private static void xnotiSetNotiProcessing(boolean z) {
        m_NotiProcessing = z;
        Log.I("notiProcessing : " + z);
    }

    public static void xnotiAddPushDataQueue(int i, byte[] bArr) {
        if (bArr == null) {
            Log.E("gPushData  Uri is null");
            return;
        }
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        XNOTIData xNOTIData = new XNOTIData(i, bArr2);
        synchronized (m_PushDataQueue) {
            m_PushDataQueue.add(xNOTIData);
            Log.I("mPushDataQueue add");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0024, code lost:
        if (r1 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0026, code lost:
        r0 = r1.type;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0029, code lost:
        if (r0 == 1) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        if (r0 == 2) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002e, code lost:
        xnotiSetNotiProcessing(false);
        com.samsung.android.fotaprovider.log.Log.I("PUSH_TYPE is not exist");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0037, code lost:
        xnotiIPPushDataReceive(r1.pushData);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003d, code lost:
        xnotiPushDataReceive(r1.pushData);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void xnotiPushDataHandling() {
        /*
            java.util.LinkedList<com.accessorydm.noti.XNOTIData> r0 = com.accessorydm.noti.XNOTIAdapter.m_PushDataQueue
            monitor-enter(r0)
            java.util.LinkedList<com.accessorydm.noti.XNOTIData> r1 = com.accessorydm.noti.XNOTIAdapter.m_PushDataQueue     // Catch:{ all -> 0x0043 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0043 }
            r2 = 0
            if (r1 == 0) goto L_0x0016
            xnotiSetNotiProcessing(r2)     // Catch:{ all -> 0x0043 }
            java.lang.String r1 = "mPushDataQueue is empty. return"
            com.samsung.android.fotaprovider.log.Log.I(r1)     // Catch:{ all -> 0x0043 }
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            return
        L_0x0016:
            java.util.LinkedList<com.accessorydm.noti.XNOTIData> r1 = com.accessorydm.noti.XNOTIAdapter.m_PushDataQueue     // Catch:{ all -> 0x0043 }
            java.lang.Object r1 = r1.poll()     // Catch:{ all -> 0x0043 }
            com.accessorydm.noti.XNOTIData r1 = (com.accessorydm.noti.XNOTIData) r1     // Catch:{ all -> 0x0043 }
            java.lang.String r3 = "mPushDataQueue poll"
            com.samsung.android.fotaprovider.log.Log.I(r3)     // Catch:{ all -> 0x0043 }
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            if (r1 == 0) goto L_0x0042
            int r0 = r1.type
            r3 = 1
            if (r0 == r3) goto L_0x003d
            r3 = 2
            if (r0 == r3) goto L_0x0037
            xnotiSetNotiProcessing(r2)
            java.lang.String r0 = "PUSH_TYPE is not exist"
            com.samsung.android.fotaprovider.log.Log.I(r0)
            goto L_0x0042
        L_0x0037:
            byte[] r0 = r1.pushData
            xnotiIPPushDataReceive(r0)
            goto L_0x0042
        L_0x003d:
            byte[] r0 = r1.pushData
            xnotiPushDataReceive(r0)
        L_0x0042:
            return
        L_0x0043:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.noti.XNOTIAdapter.xnotiPushDataHandling():void");
    }

    private static void xnotiPushDataReceive(byte[] bArr) {
        if (bArr == null) {
            Log.I("pushData is null");
            return;
        }
        xnotiSetNotiProcessing(true);
        new XNOTIAdapter().xnotiPushAdpReceiveMsg(bArr, bArr.length);
    }

    private static void xnotiIPPushDataReceive(byte[] bArr) {
        if (bArr == null) {
            Log.I("pushData is null");
            return;
        }
        xnotiSetNotiProcessing(true);
        new XNOTIAdapter().xnotiIpPushAdpReceiveMsg(bArr, bArr.length);
    }

    public static void xnotiPushAdpClearSessionStatus() {
        Log.I("");
        try {
            XUINotificationManager.getInstance().xuiRemoveAllNotification();
            xnotiPushAdpResetSessionSaveState();
            XDBProfileListAdp.xdbSetNotiEvent(0);
            XDBProfileAdp.xdbSetBackUpServerUrl();
            XDBFumoAdp.xdbSetFUMOStatus(0);
            XDBFumoAdp.xdbSetFUMOUpdateMechanism(0);
            int xdbGetFileIdFirmwareData = XDB.xdbGetFileIdFirmwareData();
            XDMDmUtils.getInstance().xdmSetResumeStatus(0);
            XDBFumoAdp.xdbSetFUMOInitiatedType(0);
            XDBPostPoneAdp.xdbSetPostponeType(PostponeType.NONE);
            if (XDB.xdbAdpFileExists(null, xdbGetFileIdFirmwareData) == 0) {
                XDB.xdbAdpFileDelete(null, xdbGetFileIdFirmwareData);
            }
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    private static XUIEventInterface.DM_UIEVENT xnotiPushAdpSelectNotiEvt(int i) {
        if (i == 1) {
            return XUIEventInterface.DM_UIEVENT.XUI_DM_NOTI_NOT_SPECIFIED;
        }
        if (i == 2) {
            return XUIEventInterface.DM_UIEVENT.XUI_DM_NOTI_BACKGROUND;
        }
        if (i == 3) {
            return XUIEventInterface.DM_UIEVENT.XUI_DM_NOTI_INFORMATIVE;
        }
        if (i != 4) {
            return XUIEventInterface.DM_UIEVENT.XUI_DM_NOTI_NOT_SPECIFIED;
        }
        return XUIEventInterface.DM_UIEVENT.XUI_DM_NOTI_INTERACTIVE;
    }

    private static void xnotiPushAdpSuspendNotiAction(int i) {
        Log.I("");
        XDBProfileListAdp.xdbSetSessionSaveStatus(1, i, 0);
        XDBProfileListAdp.xdbSetNotiEvent(0);
        XDMDmUtils.getInstance().xdmSetResumeStatus(1);
    }

    public static void xnotiPushAdpHandleNotiQueue() {
        String xdbGetNotiSessionID = XDBProfileListAdp.xdbGetNotiSessionID();
        if (!TextUtils.isEmpty(xdbGetNotiSessionID)) {
            Log.H("Delete Noti Msg sessionId=" + xdbGetNotiSessionID);
            XDBNoti.xdbNotiDeleteSessionId(xdbGetNotiSessionID);
        }
        if (XDBNoti.xdbNotiExistInfo()) {
            Log.I("Next Noti Msg Execute");
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_NOTI_EXECUTE, XDBNoti.xdbNotiGetInfo(), null);
        } else if (!XUIDialogActivity.xuiCheckDialog(XUIDialog.DM_ACCESSORY_BLOCKED_BY_POLICY_FAILED.ordinal()) && !XUIDialogActivity.xuiCheckDialog(XUIDialog.DL_MEMORY_FULL.ordinal())) {
            XDMServiceManager.getInstance().xdmStopService();
        }
    }

    private static void xnotiPushAdpExcuteResumeNoti() {
        XDBSessionSaveInfo xdbGetSessionSaveInfo = XDBProfileListAdp.xdbGetSessionSaveInfo();
        if (xdbGetSessionSaveInfo == null) {
            Log.E("Get Noti Info File Read Error");
            return;
        }
        Log.I("nSessionSaveState:" + xdbGetSessionSaveInfo.nSessionSaveState);
        Log.I("nNotiUiEvent:" + xdbGetSessionSaveInfo.nNotiUiEvent);
        Log.I("nNotiRetryCount:" + xdbGetSessionSaveInfo.nNotiRetryCount);
        if (xdbGetSessionSaveInfo.nSessionSaveState != 1 && xdbGetSessionSaveInfo.nSessionSaveState != 2) {
            Log.I("Current NOTI NOT SAVED State. EXIT.");
            xnotiPushAdpClearSessionStatus();
            xnotiPushAdpHandleNotiQueue();
        } else if (xdbGetSessionSaveInfo.nNotiRetryCount >= 5) {
            Log.I("Noti Retry Count MAX. All Clear");
            XDMEvent.XDMSetEvent(null, XUIEventInterface.DM_UIEVENT.XUI_DM_NOTI_RESUME_MAX);
            xnotiPushAdpClearSessionStatus();
            xnotiPushAdpHandleNotiQueue();
        } else {
            Log.I("Current NOTI SAVED State");
            if (xdbGetSessionSaveInfo.nNotiUiEvent != 4 && xdbGetSessionSaveInfo.nNotiUiEvent != 3) {
                XDBProfileListAdp.xdbSetNotiEvent(xdbGetSessionSaveInfo.nNotiUiEvent);
                XDMEvent.XDMSetEvent(null, xnotiPushAdpSelectNotiEvt(xdbGetSessionSaveInfo.nNotiUiEvent));
            } else if (XUIAdapter.xuiAdpStartSession()) {
                if (NetworkUtil.isWiFiNetworkConnected(XDMDmUtils.getContext())) {
                    XDBFumoAdp.xdbSetUiMode(2);
                } else {
                    XDBFumoAdp.xdbSetUiMode(1);
                }
            }
            XDBProfileListAdp.xdbSetSessionSaveStatus(xdbGetSessionSaveInfo.nSessionSaveState, xdbGetSessionSaveInfo.nNotiUiEvent, xdbGetSessionSaveInfo.nNotiRetryCount + 1);
        }
    }

    public static void xnotiPushAdpResetSessionSaveState() {
        XDBSessionSaveInfo xdbGetSessionSaveStatus = XDBProfileListAdp.xdbGetSessionSaveStatus();
        if (xdbGetSessionSaveStatus != null && xdbGetSessionSaveStatus.nSessionSaveState != 0) {
            XDBProfileListAdp.xdbSetSessionSaveStatus(0, 0, 0);
        }
    }

    public static void xnotiPushAdpProcessNotiMessage(int i) {
        int xdmInitAdpCheckNetworkReady = XDMInitAdapter.xdmInitAdpCheckNetworkReady();
        XDBProfileListAdp.xdbSetSessionSaveStatus(1, i, 0);
        if (xdmInitAdpCheckNetworkReady == 0) {
            XDMEvent.XDMSetEvent(null, xnotiPushAdpSelectNotiEvt(i));
            return;
        }
        Log.I("nNetworkState is Used." + xdmInitAdpCheckNetworkReady);
        xnotiPushAdpSuspendNotiAction(i);
    }

    public static void xnotiPushAdpResumeNotiAction() {
        if (XDMInitAdapter.xdmInitAdpCheckNetworkReady() != 0) {
            int xdbGetNotiEvent = XDBProfileListAdp.xdbGetNotiEvent();
            if (xdbGetNotiEvent != 1 && xdbGetNotiEvent != 2 && xdbGetNotiEvent != 3 && xdbGetNotiEvent != 4) {
                Log.E("CAN NOT EXCUTE Noti Resume. EXIT");
                return;
            }
            return;
        }
        xnotiPushAdpExcuteResumeNoti();
    }
}
