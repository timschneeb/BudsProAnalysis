package com.accessorydm.ui.notification.manager;

public interface NotificationStrategy {
    public static final NotificationStrategy COMMON_BACKGROUND = new NotificationStrategy() {
        /* class com.accessorydm.ui.notification.manager.NotificationStrategy.AnonymousClass2 */

        @Override // com.accessorydm.ui.notification.manager.NotificationStrategy
        public boolean isForegroundService() {
            return false;
        }

        @Override // com.accessorydm.ui.notification.manager.NotificationStrategy
        public NotificationId getNotificationId() {
            return NotificationId.XDM_NOTIFICATION_ID_PRIMARY;
        }
    };
    public static final NotificationStrategy COMMON_FOREGROUND = new NotificationStrategy() {
        /* class com.accessorydm.ui.notification.manager.NotificationStrategy.AnonymousClass3 */

        @Override // com.accessorydm.ui.notification.manager.NotificationStrategy
        public boolean isForegroundService() {
            return true;
        }

        @Override // com.accessorydm.ui.notification.manager.NotificationStrategy
        public NotificationId getNotificationId() {
            return NotificationId.XDM_NOTIFICATION_ID_PRIMARY;
        }
    };
    public static final NotificationStrategy STUB = new NotificationStrategy() {
        /* class com.accessorydm.ui.notification.manager.NotificationStrategy.AnonymousClass1 */

        @Override // com.accessorydm.ui.notification.manager.NotificationStrategy
        public boolean isForegroundService() {
            return false;
        }

        @Override // com.accessorydm.ui.notification.manager.NotificationStrategy
        public NotificationId getNotificationId() {
            return NotificationId.XDM_NOTIFICATION_ID_NONE;
        }
    };
    public static final NotificationStrategy UPDATE_REPORT = new NotificationStrategy() {
        /* class com.accessorydm.ui.notification.manager.NotificationStrategy.AnonymousClass4 */

        @Override // com.accessorydm.ui.notification.manager.NotificationStrategy
        public boolean isForegroundService() {
            return false;
        }

        @Override // com.accessorydm.ui.notification.manager.NotificationStrategy
        public NotificationId getNotificationId() {
            return NotificationId.XDM_NOTIFICATION_ID_SECONDARY;
        }
    };

    NotificationId getNotificationId();

    boolean isForegroundService();
}
