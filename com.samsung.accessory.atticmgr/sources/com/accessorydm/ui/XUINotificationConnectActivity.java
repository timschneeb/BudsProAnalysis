package com.accessorydm.ui;

import android.app.Activity;
import android.os.Bundle;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.ui.dialog.XUIDialog;
import com.accessorydm.ui.handler.XDMServiceHandler;
import com.accessorydm.ui.notification.manager.NotificationId;
import com.accessorydm.ui.notification.manager.NotificationType;
import com.accessorydm.ui.notification.manager.NotificationTypeManagerService;
import com.samsung.android.fotaprovider.log.Log;

public class XUINotificationConnectActivity extends Activity implements XDMInterface, XDMDefInterface {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        NotificationType notificationType = (NotificationType) getIntent().getSerializableExtra(NotificationTypeManagerService.NOTIFICATION_TYPE_KEY);
        Log.I("Select Notification: " + notificationType);
        if (notificationType == null) {
            Log.I("notificationType must not be null, so get primary notification");
            notificationType = NotificationId.XDM_NOTIFICATION_ID_PRIMARY.getNotificationType();
        }
        if (notificationType != NotificationType.XUI_INDICATOR_UPDATE_RESULT_SUCCESS) {
            XDBFumoAdp.xdbSetUiMode(1);
        }
        switch (notificationType) {
            case XUI_INDICATOR_UPDATE_BACK_KEY_POSTPONE:
            case XUI_INDICATOR_UPDATE_SCHEDULE_INSTALL:
            case XUI_INDICATOR_UPDATE_POSTPONE_SCHEDULE_INSTALL:
                XUIAdapter.xuiAdpSetUserClick(true);
                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_UPDATE_CONFIRM);
                break;
            case XUI_INDICATOR_FOTA_UPDATE:
            case XUI_INDICATOR_FOTA_UPDATE_COUNTDOWN:
                XUIAdapter.xuiAdpSetUserClick(true);
                XDMServiceHandler.xdmSendMessageDmHandler(XUIEventInterface.DL_UIEVENT.XUI_DL_UPDATE_CONFIRM);
                break;
            case XUI_INDICATOR_DOWNLOAD_START_CONFIRM:
                XUIAdapter.xuiAdpSetUserClick(true);
                XDMServiceHandler.xdmSendMessageDmHandler(XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_START_CONFIRM);
                break;
            case XUI_INDICATOR_DOWNLOAD_PROGRESS:
                XDMServiceHandler.xdmSendMessageDmHandler(XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_IN_PROGRESS);
                break;
            case XUI_INDICATOR_DOWNLOAD_RETRY_CONFIRM:
                XDMServiceHandler.xdmSendMessageDmHandler(XUIDialog.DL_DOWNLOAD_RETRY_CONFIRM);
                break;
            case XUI_INDICATOR_DOWNLOAD_FAILED_NETWORK_DISCONNECTED:
                XDMServiceHandler.xdmSendMessageDmHandler(XUIDialog.DL_DOWNLOAD_FAILED_NETWORK_DISCONNECTED);
                break;
            case XUI_INDICATOR_DOWNLOAD_FAILED_WIFI_DISCONNECTED:
                XDMServiceHandler.xdmSendMessageDmHandler(XUIDialog.DL_DOWNLOAD_FAILED_WIFI_DISCONNECTED);
                break;
            case XUI_INDICATOR_COPY_FAILED:
                if (XDBFumoAdp.xdbGetFUMOLowBatteryRetryCount() != 0) {
                    XDBFumoAdp.xdbSetFUMOLowBatteryRetryCount(0);
                }
                XDMServiceHandler.xdmSendMessageDmHandler(XUIDialog.DM_ACCESSORY_COPY_RETRY_CONFIRM);
                break;
            case XUI_INDICATOR_COPY_PROGRESS:
                XDMServiceHandler.xdmSendMessageDmHandler(XUIEventInterface.DL_UIEVENT.XUI_DL_COPY_IN_PROGRESS);
                break;
            case XUI_INDICATOR_UPDATE_RESULT_SUCCESS:
            case XUI_INDICATOR_UPDATE_RESULT_FAILURE:
                notificationType.getNotificationStrategy().getNotificationId().clearNotificationType();
                break;
        }
        finish();
    }
}
