package com.accessorydm.ui.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.ui.notification.manager.NotificationId;
import com.accessorydm.ui.notification.manager.NotificationType;
import com.accessorydm.ui.notification.manager.NotificationTypeManager;
import com.accessorydm.ui.notification.manager.NotificationTypeManagerService;
import com.accessorydm.ui.progress.XUIProgressModel;
import com.accessorydm.ui.progress.listener.XUIProgressNotificationListener;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.type.DeviceType;

public class XUINotificationManager {
    private static XUINotificationManager instance = new XUINotificationManager();

    private XUINotificationManager() {
        XUIProgressModel.getInstance().addProgressListener(new XUIProgressNotificationListener());
    }

    public static XUINotificationManager getInstance() {
        return instance;
    }

    public void xuiSetIndicator(NotificationType notificationType) {
        NotificationTypeManager.notify(notificationType);
        if (!NotificationType.XUI_INDICATOR_UPDATE_RESULT_SUCCESS.isSet() && !NotificationType.XUI_INDICATOR_UPDATE_RESULT_FAILURE.isSet()) {
            return;
        }
        if (notificationType == NotificationType.XUI_INDICATOR_DOWNLOAD_START_CONFIRM || notificationType == NotificationType.XUI_INDICATOR_DOWNLOAD_PROGRESS) {
            NotificationTypeManager.cancel(NotificationId.XDM_NOTIFICATION_ID_SECONDARY);
        }
    }

    public void xuiReNotifyAllNotification() {
        Log.I("");
        NotificationTypeManager.reNotifyAll();
    }

    public void xuiRemoveAllNotification() {
        Log.I("");
        NotificationTypeManager.cancelAll();
    }

    public void xuiRemoveAllNotificationExceptTo(NotificationType notificationType) {
        Log.I("");
        NotificationTypeManager.cancelAllExceptTo(notificationType);
    }

    public void xuiRemoveNotification(NotificationType notificationType) {
        NotificationTypeManager.cancel(notificationType);
    }

    public void xuiRemoveNotification(NotificationId notificationId) {
        NotificationTypeManager.cancel(notificationId);
    }

    public void xuiUpdateNotificationChannel() {
        NotificationChannel notificationChannel = getNotificationManager().getNotificationChannel(NotificationTypeManagerService.NOTIFICATION_CHANNELID);
        if (notificationChannel != null) {
            Log.I("NotificationChannel Name is modified - " + ((Object) notificationChannel.getName()));
            notificationChannel.setName(XDMDmUtils.getContext().getString(DeviceType.get().getTextType().getTitleId()));
            getNotificationManager().createNotificationChannel(notificationChannel);
            return;
        }
        Log.I("NotificationChannel is null");
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) XDMDmUtils.getInstance().xdmGetServiceManager("notification");
    }
}
