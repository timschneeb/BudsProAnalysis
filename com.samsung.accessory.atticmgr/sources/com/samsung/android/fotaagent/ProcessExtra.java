package com.samsung.android.fotaagent;

import com.accessorydm.XDMSecReceiverApiCall;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBProfileListAdp;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.postpone.PostponeManager;
import com.accessorydm.ui.UIManager;
import com.accessorydm.ui.notification.XUINotificationManager;
import com.accessorydm.ui.notification.manager.NotificationType;
import com.accessorydm.ui.progress.XUIProgressActivity;
import com.samsung.android.fotaagent.polling.Polling;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.type.DeviceType;

public class ProcessExtra {
    public void notifyConnectionToAccessorydm() {
        Log.I("");
        XDMSecReceiverApiCall.getInstance().xdmDeviceConnected();
    }

    public void startPollingAlarm() {
        Log.I("");
        if (DeviceType.get().isPollingSupported()) {
            Polling.startPollingTimer(FotaProviderInitializer.getContext());
        }
    }

    public void stopPollingAlarm() {
        Log.I("");
        if (DeviceType.get().isPollingSupported()) {
            Polling.stopPollingTimer(FotaProviderInitializer.getContext());
        }
    }

    public void stopInstallConfirmPostponeAlarm() {
        Log.I("");
        PostponeManager.stopAlarmExceptScheduleInstall();
    }

    public void removeUI() {
        Log.I("");
        FotaProviderState.setFotaBadgeCount(0);
        UIManager.getInstance().finishAllActivitiesExcept(XUIProgressActivity.class.getName());
        if (XDBFumoAdp.xdbGetFUMOStatus() <= 10) {
            XDBProfileListAdp.xdbSetNotiEvent(0);
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_ABORT, XDMMsg.xdmCreateAbortMessage(241, true), null);
            return;
        }
        XUINotificationManager.getInstance().xuiRemoveAllNotificationExceptTo(NotificationType.XUI_INDICATOR_DOWNLOAD_PROGRESS);
    }

    public void changeDeviceInfo() {
        Log.I("Device is changed. Clear previous device info and register new device");
        int xdbGetUiMode = XDBFumoAdp.xdbGetUiMode();
        removeUI();
        FotaProviderState.resetDataAndStopAlarms(FotaProviderInitializer.getContext());
        FotaProviderState.resetChangedDeviceProcess();
        if (xdbGetUiMode == 1) {
            new ProcessRegister().registerDeviceOnForeground();
        } else {
            new ProcessRegister().registerDeviceOnBackground();
        }
    }
}
