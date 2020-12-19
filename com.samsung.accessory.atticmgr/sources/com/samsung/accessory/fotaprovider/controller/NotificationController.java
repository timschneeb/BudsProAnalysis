package com.samsung.accessory.fotaprovider.controller;

import com.accessorydm.ui.notification.manager.NotificationType;

public class NotificationController {
    protected NotificationCallback notificationCallback;

    public interface NotificationCallback {
        void onResponse();
    }

    public void sendAccessoryNotification(NotificationCallback notificationCallback2, NotificationType notificationType) {
        if (notificationCallback2 != null) {
            notificationCallback2.onResponse();
        }
    }

    public void removeAccessoryNotification(NotificationCallback notificationCallback2) {
        if (notificationCallback2 != null) {
            notificationCallback2.onResponse();
        }
    }
}
