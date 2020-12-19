package com.accessorydm.ui.notification.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* access modifiers changed from: package-private */
public class NotificationStorage {
    private static final NotificationStorage instance = new NotificationStorage();
    private final Map<NotificationId, NotificationType> notificationMap = new ConcurrentHashMap<NotificationId, NotificationType>() {
        /* class com.accessorydm.ui.notification.manager.NotificationStorage.AnonymousClass1 */

        {
            put(NotificationId.XDM_NOTIFICATION_ID_PRIMARY, NotificationType.XUI_INDICATOR_NONE);
            put(NotificationId.XDM_NOTIFICATION_ID_SECONDARY, NotificationType.XUI_INDICATOR_UPDATE_RESULT_NONE);
        }
    };

    private NotificationStorage() {
    }

    public static NotificationStorage getInstance() {
        return instance;
    }

    /* access modifiers changed from: package-private */
    public void putNotification(NotificationId notificationId, NotificationType notificationType) {
        this.notificationMap.put(notificationId, notificationType);
    }

    /* access modifiers changed from: package-private */
    public void clearNotification(NotificationId notificationId) {
        putNotification(notificationId, notificationId.getNotificationTypePlaceHolder());
    }

    /* access modifiers changed from: package-private */
    public NotificationType getNotification(NotificationId notificationId) {
        return this.notificationMap.get(notificationId);
    }
}
