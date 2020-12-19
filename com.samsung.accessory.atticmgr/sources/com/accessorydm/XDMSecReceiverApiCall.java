package com.accessorydm;

import android.os.SystemClock;
import com.accessorydm.adapter.XDMCommonUtils;
import com.accessorydm.adapter.XDMInitAdapter;
import com.accessorydm.agent.XDMAgent;
import com.accessorydm.agent.XDMDebug;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBPostPoneAdp;
import com.accessorydm.db.file.XDBProfileListAdp;
import com.accessorydm.dmstarter.XDMSessionStarter;
import com.accessorydm.eng.core.XDMBase64;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.interfaces.XCommonInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.noti.XNOTIAdapter;
import com.accessorydm.postpone.PostponeType;
import com.accessorydm.ui.handler.XDMToastHandler;
import com.accessorydm.ui.notification.XUINotificationManager;
import com.accessorydm.ui.notification.manager.NotificationType;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.NetworkUtil;
import com.samsung.android.sdk.mobileservice.common.ErrorCodeConvertor;
import com.sec.android.fotaprovider.R;

public class XDMSecReceiverApiCall {
    private static final long MIN_PULL_INIT_INTERVAL = 1000;
    private static final XDMSecReceiverApiCall instance = new XDMSecReceiverApiCall();
    private XCommonInterface.INIT_TYPE deviceCheckinitType = XCommonInterface.INIT_TYPE.INIT_TYPE_NONE;
    private long lastPullActionTime = (SystemClock.uptimeMillis() - ErrorCodeConvertor.AGENT_DEVICE_NOT_AUTHENTICATE);
    private int nRegistrationMode = 0;
    private String pdus;
    private boolean pushNotiSaved = false;
    private boolean sysScopeScanned = false;

    public static XDMSecReceiverApiCall getInstance() {
        return instance;
    }

    private XDMSecReceiverApiCall() {
    }

    public void xdmDeviceRegistration(int i) {
        Log.I("");
        xdmAccessorySetRegistrationMode(i);
        XDB.xdbAdpDeltaAllClear();
        if (xdmAccessoryGetRegistrationMode() != 0) {
            XDMSessionStarter.forInitiateType(i == 1 ? XCommonInterface.INIT_TYPE.INIT_TYPE_PULL : XCommonInterface.INIT_TYPE.INIT_TYPE_POLLING).dmInitExecute();
        }
    }

    public void xdmPull() {
        Log.I("");
        if (!isDuplicatePullAction()) {
            XDMSessionStarter.forInitiateType(XCommonInterface.INIT_TYPE.INIT_TYPE_PULL).dmInitExecute();
        }
    }

    public void xdmPolling() {
        Log.I("");
        XDMSessionStarter.forInitiateType(XCommonInterface.INIT_TYPE.INIT_TYPE_POLLING).dmInitExecute();
    }

    public void xdmPush(String str) {
        Log.I("");
        this.pdus = str;
        XDMSessionStarter.forInitiateType(XCommonInterface.INIT_TYPE.INIT_TYPE_PUSH).dmInitExecute();
    }

    public void xdmDeviceConnected() {
        Log.I("");
        if (isNeedToClearSession()) {
            clearSession();
        } else if (XDBFumoAdp.xdbGetFUMOInitiatedType() == 2 && XDBFumoAdp.xdbGetFUMOLowBatteryRetryCount() != 0) {
            Log.I("Low Battery Retry - File check");
            XDMSessionStarter.forInitiateType(XCommonInterface.INIT_TYPE.INIT_TYPE_PULL).dmInitExecute();
        }
    }

    public void sysScopeCheckProcess(int i, int i2) {
        Log.I("SysScope check results : " + i + ", status : " + i2);
        XDBFumoAdp.xdbSetFUMOCheckRooting(i == 2);
        if (isNeedToClearSession()) {
            clearSession();
        } else if (isSysScopeScanned()) {
            setSysScopeScanned(false);
            if (i == 2) {
                XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_SYSSCOPE_MODIFIED);
                return;
            }
            Log.I("XUI_DM_ACCESSORY_CHECKING_FOR_UPDATE");
            XDMSessionStarter.forInitiateType(XCommonInterface.INIT_TYPE.INIT_TYPE_PULL).dmInitExecute();
        }
    }

    public void xdmUpdateResults(int i, int i2) {
        Log.I("update result: " + i + ", consumer status: " + i2);
        if (i2 != 65) {
            Log.E("consumerStatus is not reporting status: " + i2);
            return;
        }
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        if (xdbGetFUMOStatus == 60 || xdbGetFUMOStatus == 252) {
            XDBFumoAdp.xdbSetFUMOStatus(65);
            XDBFumoAdp.xdbSetUiMode(2);
            XDBFumoAdp.xdbSetFUMOInitiatedType(0);
            if (xdbGetFUMOStatus != 60) {
                XDMInitAdapter.xdmAccessoryUpdateResultSetAndReport(XFOTAInterface.XFOTA_GENERIC_SAP_NO_RESPONSE_UPDATE_RESULT);
            } else if (i == -1) {
                XDMInitAdapter.xdmAccessoryUpdateResultSetAndReport(XFOTAInterface.XFOTA_GENERIC_UPDATE_FAILED);
            } else if (i == 0) {
                XDMInitAdapter.xdmAccessoryUpdateResultSetAndReport(XFOTAInterface.XFOTA_GENERIC_UNDEFINED_ERROR);
            } else if (i != 200) {
                XDMInitAdapter.xdmAccessoryUpdateResultSetAndReport(Integer.toString(i));
            } else {
                XDMInitAdapter.xdmAccessoryUpdateResultSetAndReport("200");
            }
            if ("200".equals(XDBFumoAdp.xdbGetFUMOResultCode())) {
                XUINotificationManager.getInstance().xuiSetIndicator(NotificationType.XUI_INDICATOR_UPDATE_RESULT_SUCCESS);
            } else {
                XUINotificationManager.getInstance().xuiSetIndicator(NotificationType.XUI_INDICATOR_UPDATE_RESULT_FAILURE);
            }
        } else {
            Log.E("fumoStatus is not reporting status: " + xdbGetFUMOStatus);
        }
    }

    public void initializeXdmService() {
        Log.I("");
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        boolean z = xdbGetFUMOStatus == 60 || xdbGetFUMOStatus == 65 || xdbGetFUMOStatus == 80 || xdbGetFUMOStatus == 100;
        if (XDMAgent.xdmAgentGetSyncMode() != 0 || z) {
            Log.I("sync ing...");
        } else {
            XDMDebug.xdmSetSessionRuning(true);
        }
        if (FotaProviderState.isDeviceRegisteredDB()) {
            Log.I("Device is registered");
            if (!XDMCommonUtils.xdmServiceRunningCheck()) {
                Log.I(">>>>>>>>>>   Service starts   <<<<<<<<<<");
                XDMServiceManager.getInstance().xdmDoStartInitService();
            }
        }
    }

    private void clearSession() {
        XNOTIAdapter.xnotiPushAdpResetSessionSaveState();
        XNOTIAdapter.xnotiPushAdpHandleNotiQueue();
        XDBProfileListAdp.xdbSetNotiEvent(0);
    }

    /* renamed from: com.accessorydm.XDMSecReceiverApiCall$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$accessorydm$interfaces$XCommonInterface$INIT_TYPE = new int[XCommonInterface.INIT_TYPE.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.accessorydm.interfaces.XCommonInterface$INIT_TYPE[] r0 = com.accessorydm.interfaces.XCommonInterface.INIT_TYPE.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.accessorydm.XDMSecReceiverApiCall.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XCommonInterface$INIT_TYPE = r0
                int[] r0 = com.accessorydm.XDMSecReceiverApiCall.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XCommonInterface$INIT_TYPE     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.accessorydm.interfaces.XCommonInterface$INIT_TYPE r1 = com.accessorydm.interfaces.XCommonInterface.INIT_TYPE.INIT_TYPE_POLLING     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = com.accessorydm.XDMSecReceiverApiCall.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XCommonInterface$INIT_TYPE     // Catch:{ NoSuchFieldError -> 0x001f }
                com.accessorydm.interfaces.XCommonInterface$INIT_TYPE r1 = com.accessorydm.interfaces.XCommonInterface.INIT_TYPE.INIT_TYPE_PULL     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = com.accessorydm.XDMSecReceiverApiCall.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XCommonInterface$INIT_TYPE     // Catch:{ NoSuchFieldError -> 0x002a }
                com.accessorydm.interfaces.XCommonInterface$INIT_TYPE r1 = com.accessorydm.interfaces.XCommonInterface.INIT_TYPE.INIT_TYPE_PUSH     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.XDMSecReceiverApiCall.AnonymousClass1.<clinit>():void");
        }
    }

    public void xdmAccessoryCheckDeviceCallback(int i) {
        Log.I("");
        int i2 = AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XCommonInterface$INIT_TYPE[xdmGetDeviceCheckInfoState().ordinal()];
        if (i2 == 1 || i2 == 2) {
            startDMSession(i);
        } else if (i2 != 3) {
            Log.I("nothing happened");
        } else {
            String str = this.pdus;
            if (str != null) {
                startIPPushSession(str, i);
            }
        }
    }

    private void startDMSession(int i) {
        Log.I("");
        if (i == 0) {
            xdmAccessorySetRegistrationMode(0);
            Log.I("CheckDevice Fail");
            return;
        }
        XDMSessionStarter.onStartSession();
    }

    private void startIPPushSession(String str, int i) {
        Log.I("");
        if (i == 0) {
            setPushNotiSaved(true);
            Log.E("Push noti should be saved. In session or Not Connected OR Checking SYSSCOPE");
        }
        byte[] convertPushMessageToByteArray = convertPushMessageToByteArray(str);
        if (convertPushMessageToByteArray == null) {
            Log.E("PushData is null.");
            return;
        }
        byte[] xdmBase64Decode = XDMBase64.xdmBase64Decode(convertPushMessageToByteArray);
        if (xdmBase64Decode != null) {
            XNOTIAdapter.xnotiAddPushDataQueue(2, xdmBase64Decode);
            if (XNOTIAdapter.xnotiGetNotiProcessing()) {
                Log.I("Noti Processing...");
            } else {
                XNOTIAdapter.xnotiPushDataHandling();
            }
        }
    }

    private byte[] convertPushMessageToByteArray(String str) {
        try {
            return str.getBytes("UTF8");
        } catch (Exception e) {
            Log.E(e.toString());
            return null;
        }
    }

    public boolean isPushNotiSaved() {
        return this.pushNotiSaved;
    }

    public void setPushNotiSaved(boolean z) {
        this.pushNotiSaved = z;
    }

    public boolean isForegroundAvailable() {
        if (XDMInitAdapter.xdmInitAdpCheckNetworkReady() == 0 && XDMAgent.xdmAgentGetSyncMode() == 0) {
            return true;
        }
        getInstance().showToastWithTitle(R.string.STR_DM_CONNECTING_SERVER);
        return false;
    }

    public boolean isBackgroundAvailable() {
        if (NetworkUtil.isAnyNetworkConnected(FotaProviderInitializer.getContext()) || !isUpdateInProgress()) {
            Log.W("process is available in background");
            return true;
        }
        Log.W("process is not available in background");
        return false;
    }

    private boolean isUpdateInProgress() {
        if (60 == XDBFumoAdp.xdbGetFUMOStatus()) {
            Log.I("it is update in progress");
            return true;
        }
        Log.I("it is not update in progress");
        return false;
    }

    private boolean isNeedToClearSession() {
        return XDBFumoAdp.xdbGetFUMOInitiatedType() == 0 && XDBFumoAdp.xdbGetFUMOStatus() == 0 && XDBPostPoneAdp.xdbGetPostponeType() == PostponeType.NONE;
    }

    private int xdmAccessoryGetRegistrationMode() {
        Log.I("nRegistrationMode : " + this.nRegistrationMode);
        return this.nRegistrationMode;
    }

    public void xdmAccessorySetRegistrationMode(int i) {
        this.nRegistrationMode = i;
        Log.I("nRegistrationMode : " + this.nRegistrationMode);
    }

    public void xdmSetDeviceCheckInfoState(XCommonInterface.INIT_TYPE init_type) {
        this.deviceCheckinitType = init_type;
    }

    public XCommonInterface.INIT_TYPE xdmGetDeviceCheckInfoState() {
        return this.deviceCheckinitType;
    }

    public void setSysScopeScanned(boolean z) {
        this.sysScopeScanned = z;
    }

    public boolean isSysScopeScanned() {
        return this.sysScopeScanned;
    }

    public void showToastWithTitle(int i) {
        XDMToastHandler.xdmShowToast(String.format("%s%n%s", FotaProviderInitializer.getContext().getString(i), FotaProviderInitializer.getContext().getString(R.string.STR_COMMON_PLEASE_WAIT)), 0);
    }

    private boolean isDuplicatePullAction() {
        long uptimeMillis = SystemClock.uptimeMillis();
        Log.I("Current pull time [" + uptimeMillis + "] Last pull time [" + getLastPullActionTime() + "]");
        if (uptimeMillis <= getLastPullActionTime() + 1000) {
            Log.W("Duplicate Pull..Return!!");
            return true;
        }
        setLastPullActionTime(uptimeMillis);
        return false;
    }

    private void setLastPullActionTime(long j) {
        Log.I("setLastPullActionTime [" + j + "]");
        this.lastPullActionTime = j;
    }

    private long getLastPullActionTime() {
        return this.lastPullActionTime;
    }
}
