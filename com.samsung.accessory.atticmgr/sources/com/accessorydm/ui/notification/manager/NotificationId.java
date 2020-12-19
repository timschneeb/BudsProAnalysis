package com.accessorydm.ui.notification.manager;

import android.content.Intent;
import android.os.IBinder;

public enum NotificationId {
    XDM_NOTIFICATION_ID_NONE {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.notification.manager.NotificationId
        public Class<? extends NotificationTypeManagerService> getNotificationTypeManagerServiceClass() {
            return StubNotificationTypeManagerService.class;
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.notification.manager.NotificationId
        public NotificationType getNotificationTypePlaceHolder() {
            return NotificationType.XUI_INDICATOR_STUB;
        }

        @Override // com.accessorydm.ui.notification.manager.NotificationId
        public NotificationType getNotificationType() {
            return NotificationType.XUI_INDICATOR_STUB;
        }
    },
    XDM_NOTIFICATION_ID_PRIMARY {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.notification.manager.NotificationId
        public Class<? extends NotificationTypeManagerService> getNotificationTypeManagerServiceClass() {
            return Common.class;
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.notification.manager.NotificationId
        public NotificationType getNotificationTypePlaceHolder() {
            return NotificationType.XUI_INDICATOR_NONE;
        }
    },
    XDM_NOTIFICATION_ID_SECONDARY {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.notification.manager.NotificationId
        public Class<? extends NotificationTypeManagerService> getNotificationTypeManagerServiceClass() {
            return UpdateReport.class;
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.notification.manager.NotificationId
        public NotificationType getNotificationTypePlaceHolder() {
            return NotificationType.XUI_INDICATOR_UPDATE_RESULT_NONE;
        }
    };

    /* access modifiers changed from: package-private */
    public abstract Class<? extends NotificationTypeManagerService> getNotificationTypeManagerServiceClass();

    /* access modifiers changed from: package-private */
    public abstract NotificationType getNotificationTypePlaceHolder();

    /* access modifiers changed from: package-private */
    public int getId() {
        return ordinal() + 1;
    }

    /* access modifiers changed from: package-private */
    public void setNotificationType(NotificationType notificationType) {
        NotificationStorage.getInstance().putNotification(this, notificationType);
    }

    public NotificationType getNotificationType() {
        return NotificationStorage.getInstance().getNotification(this);
    }

    public void clearNotificationType() {
        NotificationStorage.getInstance().clearNotification(this);
    }

    public static class Common extends RealNotificationTypeManagerService {
        @Override // com.accessorydm.ui.notification.manager.RealNotificationTypeManagerService
        public /* bridge */ /* synthetic */ IBinder onBind(Intent intent) {
            return super.onBind(intent);
        }

        @Override // com.accessorydm.ui.notification.manager.RealNotificationTypeManagerService
        public /* bridge */ /* synthetic */ void onCreate() {
            super.onCreate();
        }

        @Override // com.accessorydm.ui.notification.manager.RealNotificationTypeManagerService
        public /* bridge */ /* synthetic */ void onDestroy() {
            super.onDestroy();
        }

        @Override // com.accessorydm.ui.notification.manager.RealNotificationTypeManagerService
        public /* bridge */ /* synthetic */ int onStartCommand(Intent intent, int i, int i2) {
            return super.onStartCommand(intent, i, i2);
        }
    }

    public static class UpdateReport extends RealNotificationTypeManagerService {
        @Override // com.accessorydm.ui.notification.manager.RealNotificationTypeManagerService
        public /* bridge */ /* synthetic */ IBinder onBind(Intent intent) {
            return super.onBind(intent);
        }

        @Override // com.accessorydm.ui.notification.manager.RealNotificationTypeManagerService
        public /* bridge */ /* synthetic */ void onCreate() {
            super.onCreate();
        }

        @Override // com.accessorydm.ui.notification.manager.RealNotificationTypeManagerService
        public /* bridge */ /* synthetic */ void onDestroy() {
            super.onDestroy();
        }

        @Override // com.accessorydm.ui.notification.manager.RealNotificationTypeManagerService
        public /* bridge */ /* synthetic */ int onStartCommand(Intent intent, int i, int i2) {
            return super.onStartCommand(intent, i, i2);
        }
    }
}
