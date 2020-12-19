package com.accessorydm.filetransfer.action;

import com.accessorydm.XDMSecReceiverApiCall;
import com.accessorydm.adapter.XDMInitAdapter;
import com.accessorydm.agent.fota.XFOTADl;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.filetransfer.XDMFileTransferManager;
import com.accessorydm.interfaces.XDMAccessoryInterface;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.ui.UIManager;
import com.accessorydm.ui.notification.XUINotificationManager;
import com.accessorydm.ui.notification.manager.NotificationId;
import com.accessorydm.ui.notification.manager.NotificationType;
import com.accessorydm.ui.progress.XUIProgressModel;
import com.samsung.accessory.fotaprovider.controller.RequestError;
import com.samsung.android.fotaprovider.log.Log;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileTransferFailure {
    public static void handleChangedDevice() {
        Log.I("");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_ACCESSORY_DIFFERENT_DEVICE, null, null);
        XDMFileTransferManager.resetDevice();
    }

    static void handleAccessoryConnectionFailure(RequestError requestError) {
        Log.I("");
        if (requestError != null) {
            Log.I("Check error state : " + requestError);
            int i = AnonymousClass1.$SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError[requestError.ordinal()];
            if (i == 1) {
                XDMSecReceiverApiCall.getInstance().setSysScopeScanned(true);
                XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_SYSSCOPE_SCANNING);
                Executors.newSingleThreadScheduledExecutor().schedule($$Lambda$FileTransferFailure$LOFNuKnUGfyYBEVQVIIhp4Fu6c.INSTANCE, 10, TimeUnit.SECONDS);
                return;
            } else if (i == 2) {
                XDMSecReceiverApiCall.getInstance().setSysScopeScanned(false);
                handleInstallFailure(XFOTAInterface.XFOTA_GENERIC_ROOTING_UPDATE_FAILED);
                return;
            } else if (i == 3) {
                handleInstallFailure(XFOTAInterface.XFOTA_GENERIC_BLOCKED_MDM_UPDATE_FAILED);
                return;
            }
        }
        if (XDBFumoAdp.xdbGetFUMOStatus() == 200) {
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_USER_CANCEL_DOWNLOAD, null, null);
        }
        XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_CONNECTION_FAILED);
    }

    static /* synthetic */ void lambda$handleAccessoryConnectionFailure$0() {
        if (XDMSecReceiverApiCall.getInstance().isSysScopeScanned()) {
            handleAccessoryConnectionFailure(RequestError.ERROR_CONSUMER_MODIFIED);
        }
    }

    static void handleInstallFailure(String str) {
        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
        Log.I("FUMO status: " + xdbGetFUMOStatus + ", failReason: " + str);
        if (xdbGetFUMOStatus == 0) {
            Log.I("Do not report because FUMO status is none");
        } else if (xdbGetFUMOStatus == 30 || xdbGetFUMOStatus == 200) {
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_DEVICE_FAIL_DOWNLOAD, str, null);
        } else {
            XDMInitAdapter.xdmAccessoryUpdateResultSetAndReport(str);
        }
        UIManager.getInstance().finishAllActivities();
        XDMFileTransferManager.resetDevice();
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 51663) {
            if (hashCode == 51696 && str.equals(XFOTAInterface.XFOTA_GENERIC_BLOCKED_MDM_UPDATE_FAILED)) {
                c = 1;
            }
        } else if (str.equals(XFOTAInterface.XFOTA_GENERIC_ROOTING_UPDATE_FAILED)) {
            c = 0;
        }
        if (c == 0) {
            XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_SYSSCOPE_MODIFIED);
        } else if (c != 1) {
            XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_INSTALL_FAILED);
        } else {
            XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_BLOCKED_BY_POLICY_FAILED);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: com.accessorydm.filetransfer.action.FileTransferFailure$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$accessorydm$interfaces$XDMAccessoryInterface$XDMAccessoryCheckState = new int[XDMAccessoryInterface.XDMAccessoryCheckState.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError = new int[RequestError.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|13|14|15|16|17|18|20) */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x003d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0047 */
        static {
            /*
                com.accessorydm.interfaces.XDMAccessoryInterface$XDMAccessoryCheckState[] r0 = com.accessorydm.interfaces.XDMAccessoryInterface.XDMAccessoryCheckState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.accessorydm.filetransfer.action.FileTransferFailure.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMAccessoryInterface$XDMAccessoryCheckState = r0
                r0 = 1
                int[] r1 = com.accessorydm.filetransfer.action.FileTransferFailure.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMAccessoryInterface$XDMAccessoryCheckState     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.accessorydm.interfaces.XDMAccessoryInterface$XDMAccessoryCheckState r2 = com.accessorydm.interfaces.XDMAccessoryInterface.XDMAccessoryCheckState.XDM_ACCESSORY_CHECK_DONWLOAD     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = com.accessorydm.filetransfer.action.FileTransferFailure.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMAccessoryInterface$XDMAccessoryCheckState     // Catch:{ NoSuchFieldError -> 0x001f }
                com.accessorydm.interfaces.XDMAccessoryInterface$XDMAccessoryCheckState r3 = com.accessorydm.interfaces.XDMAccessoryInterface.XDMAccessoryCheckState.XDM_ACCESSORY_CHECK_COPY     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = com.accessorydm.filetransfer.action.FileTransferFailure.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMAccessoryInterface$XDMAccessoryCheckState     // Catch:{ NoSuchFieldError -> 0x002a }
                com.accessorydm.interfaces.XDMAccessoryInterface$XDMAccessoryCheckState r4 = com.accessorydm.interfaces.XDMAccessoryInterface.XDMAccessoryCheckState.XDM_ACCESSORY_CHECK_INSTALL     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                com.samsung.accessory.fotaprovider.controller.RequestError[] r3 = com.samsung.accessory.fotaprovider.controller.RequestError.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                com.accessorydm.filetransfer.action.FileTransferFailure.AnonymousClass1.$SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError = r3
                int[] r3 = com.accessorydm.filetransfer.action.FileTransferFailure.AnonymousClass1.$SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError     // Catch:{ NoSuchFieldError -> 0x003d }
                com.samsung.accessory.fotaprovider.controller.RequestError r4 = com.samsung.accessory.fotaprovider.controller.RequestError.ERROR_CONSUMER_SCANNING     // Catch:{ NoSuchFieldError -> 0x003d }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x003d }
                r3[r4] = r0     // Catch:{ NoSuchFieldError -> 0x003d }
            L_0x003d:
                int[] r0 = com.accessorydm.filetransfer.action.FileTransferFailure.AnonymousClass1.$SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError     // Catch:{ NoSuchFieldError -> 0x0047 }
                com.samsung.accessory.fotaprovider.controller.RequestError r3 = com.samsung.accessory.fotaprovider.controller.RequestError.ERROR_CONSUMER_MODIFIED     // Catch:{ NoSuchFieldError -> 0x0047 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0047 }
                r0[r3] = r1     // Catch:{ NoSuchFieldError -> 0x0047 }
            L_0x0047:
                int[] r0 = com.accessorydm.filetransfer.action.FileTransferFailure.AnonymousClass1.$SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError     // Catch:{ NoSuchFieldError -> 0x0051 }
                com.samsung.accessory.fotaprovider.controller.RequestError r1 = com.samsung.accessory.fotaprovider.controller.RequestError.ERROR_CONSUMER_MDM_BLOCKED     // Catch:{ NoSuchFieldError -> 0x0051 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0051 }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0051 }
            L_0x0051:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.filetransfer.action.FileTransferFailure.AnonymousClass1.<clinit>():void");
        }
    }

    static void handleLowMemory(XDMAccessoryInterface.XDMAccessoryCheckState xDMAccessoryCheckState) {
        Log.I("");
        int i = AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMAccessoryInterface$XDMAccessoryCheckState[xDMAccessoryCheckState.ordinal()];
        if (i == 1) {
            XDB.xdbAdpDeltaAllClear();
            XDMInitAdapter.xdmAccessoryUpdateResultSetAndReport(XFOTAInterface.XFOTA_GENERIC_SAP_FAILED_OUT_MEMORY);
            XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_LOW_MEMORY_DOWNLOAD_WATCH);
            XDMFileTransferManager.resetDevice();
        } else if (i == 2) {
            XDB.xdbAdpDeltaAllClear();
            XDMInitAdapter.xdmAccessoryUpdateResultSetAndReport(XFOTAInterface.XFOTA_GENERIC_SAP_FAILED_OUT_MEMORY);
            XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_LOW_MEMORY_COPY_WATCH);
            XDMFileTransferManager.resetDevice();
        } else if (i == 3) {
            XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_LOW_MEMORY_INSTALL_WATCH);
        }
    }

    static void handleLowBattery(XDMAccessoryInterface.XDMAccessoryCheckState xDMAccessoryCheckState) {
        Log.I("");
        int i = AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XDMAccessoryInterface$XDMAccessoryCheckState[xDMAccessoryCheckState.ordinal()];
        if (i == 2) {
            XFOTADl.xfotaCopySetDrawingPercentage(false);
            if (XDBFumoAdp.xdbGetFUMOInitiatedType() == 2) {
                XDBFumoAdp.xdbSetFUMOLowBatteryRetryCount(XDBFumoAdp.xdbGetFUMOLowBatteryRetryCount() + 1);
            }
            if (XDBFumoAdp.xdbGetFUMOLowBatteryRetryCount() >= 3) {
                XUIProgressModel.getInstance().initializeProgress();
                XDBFumoAdp.xdbSetFUMOLowBatteryRetryCount(0);
                XDB.xdbAdpDeltaAllClear();
                XUINotificationManager.getInstance().xuiRemoveNotification(NotificationId.XDM_NOTIFICATION_ID_PRIMARY);
                XDMInitAdapter.xdmAccessoryUpdateResultSetAndReport(XFOTAInterface.XFOTA_GENERIC_SAP_COPY_FAILED);
                XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_COPY_FAILED);
                XDMFileTransferManager.resetDevice();
                return;
            }
            XUIProgressModel.getInstance().initializeProgress();
            XDBFumoAdp.xdbSetFUMOStatus(250);
            XUINotificationManager.getInstance().xuiSetIndicator(NotificationType.XUI_INDICATOR_COPY_FAILED);
            if (XDBFumoAdp.xdbGetUiMode() == 1) {
                XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_LOW_BATTERY_WATCH);
            }
        } else if (i == 3) {
            XUINotificationManager.getInstance().xuiSetIndicator(NotificationType.XUI_INDICATOR_FOTA_UPDATE);
            XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_LOW_BATTERY_WATCH);
        }
    }
}
