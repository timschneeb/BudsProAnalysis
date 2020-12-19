package com.accessorydm.ui.notification.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;

public class NotificationTypeManager {
    public static void notify(NotificationType notificationType) {
        NotificationId notificationId = notificationType.getNotificationStrategy().getNotificationId();
        if (isValidNotification(notificationId, notificationType)) {
            Log.I("Notification notify - " + notificationId + " : " + notificationType);
            Intent intent = new Intent(getContext(), notificationId.getNotificationTypeManagerServiceClass());
            intent.putExtra("OPERATION_KEY", "OPERATION_NOTIFY");
            intent.putExtra(NotificationTypeManagerService.NOTIFICATION_TYPE_KEY, notificationType);
            startService(intent);
        }
    }

    public static void reNotifyAll() {
        Log.I("");
        NotificationId[] values = NotificationId.values();
        for (NotificationId notificationId : values) {
            if (notificationId != NotificationId.XDM_NOTIFICATION_ID_NONE) {
                notify(notificationId.getNotificationType());
            }
        }
    }

    public static void cancel(NotificationType notificationType) {
        Log.E("cancel " + notificationType);
        NotificationId notificationId = notificationType.getNotificationStrategy().getNotificationId();
        if (!notificationType.isSet()) {
            Log.W("Existing notificationType should be the same as that to be cancelled - existing[" + notificationId + ":" + notificationId.getNotificationType() + "], to be cancelled [" + notificationType + "]");
            return;
        }
        cancel(notificationId);
    }

    public static void cancel(NotificationId notificationId) {
        NotificationType notificationType = notificationId.getNotificationType();
        if (isValidNotification(notificationId, notificationType) && notificationId == notificationType.getNotificationStrategy().getNotificationId()) {
            Log.I("Notification cancel - " + notificationId + " : " + notificationType);
            Intent intent = new Intent(getContext(), notificationId.getNotificationTypeManagerServiceClass());
            intent.putExtra("OPERATION_KEY", "OPERATION_CANCEL");
            intent.putExtra(NotificationTypeManagerService.NOTIFICATION_ID_KEY, notificationId);
            startService(intent);
        }
    }

    public static void cancelAll() {
        Log.I("");
        NotificationId[] values = NotificationId.values();
        for (NotificationId notificationId : values) {
            if (notificationId != NotificationId.XDM_NOTIFICATION_ID_NONE) {
                cancel(notificationId);
            }
        }
    }

    public static void cancelAllExceptTo(NotificationType notificationType) {
        Log.I("");
        NotificationId[] values = NotificationId.values();
        for (NotificationId notificationId : values) {
            if (!(notificationId == NotificationId.XDM_NOTIFICATION_ID_NONE || notificationType == notificationId.getNotificationType())) {
                cancel(notificationId);
            }
        }
    }

    private static boolean isValidNotification(NotificationId notificationId, NotificationType notificationType) {
        if (notificationId == NotificationId.XDM_NOTIFICATION_ID_NONE) {
            Log.W("Do not use id [" + notificationId + "], which is just a placeholder");
            return false;
        } else if (notificationType != NotificationType.XUI_INDICATOR_NONE && notificationType != NotificationType.XUI_INDICATOR_UPDATE_RESULT_NONE) {
            return true;
        } else {
            Log.W("Not available to notify [" + notificationType + "]");
            return false;
        }
    }

    private static Context getContext() {
        return FotaProviderInitializer.getContext();
    }

    private static void startService(Intent intent) {
        if (Build.VERSION.SDK_INT < 26) {
            getContext().startService(intent);
        } else {
            getContext().startForegroundService(intent);
        }
    }
}
