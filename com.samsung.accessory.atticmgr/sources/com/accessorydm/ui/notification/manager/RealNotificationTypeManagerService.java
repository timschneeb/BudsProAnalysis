package com.accessorydm.ui.notification.manager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.accessorydm.XDMServiceManager;
import com.samsung.android.fotaprovider.log.Log;

abstract class RealNotificationTypeManagerService extends Service implements NotificationTypeManagerService {
    public IBinder onBind(Intent intent) {
        return null;
    }

    RealNotificationTypeManagerService() {
    }

    public void onCreate() {
        super.onCreate();
        Log.I(getClass().getSimpleName());
        XDMServiceManager.getInstance().xdmNotifyObserver(1);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        try {
            String stringExtra = intent.getStringExtra("OPERATION_KEY");
            NotificationType notificationType = (NotificationType) intent.getSerializableExtra(NotificationTypeManagerService.NOTIFICATION_TYPE_KEY);
            NotificationId notificationId = (NotificationId) intent.getSerializableExtra(NotificationTypeManagerService.NOTIFICATION_ID_KEY);
            verifyNotification(stringExtra, notificationType, notificationId);
            char c = 65535;
            int hashCode = stringExtra.hashCode();
            if (hashCode != -291369870) {
                if (hashCode == 36664641) {
                    if (stringExtra.equals("OPERATION_NOTIFY")) {
                        c = 0;
                    }
                }
            } else if (stringExtra.equals("OPERATION_CANCEL")) {
                c = 1;
            }
            if (c == 0) {
                notify(notificationType);
                return 2;
            } else if (c != 1) {
                return 2;
            } else {
                cancel(notificationId);
                return 2;
            }
        } catch (UnexpectedNotificationException | NullPointerException e) {
            Log.W(getClass().getSimpleName() + " - unexpected notification : " + e.getMessage());
            avoidANR();
            return 2;
        }
    }

    private void verifyNotification(String str, NotificationType notificationType, NotificationId notificationId) throws UnexpectedNotificationException {
        if (str == null) {
            throw new UnexpectedNotificationException("operation is null");
        } else if (!"OPERATION_NOTIFY".equals(str) && !"OPERATION_CANCEL".equals(str)) {
            throw new UnexpectedNotificationException("operation is wrong");
        } else if ("OPERATION_NOTIFY".equals(str) && notificationType == null) {
            throw new UnexpectedNotificationException("notificationType is null for notifying");
        } else if ("OPERATION_CANCEL".equals(str) && notificationId == null) {
            throw new UnexpectedNotificationException("notificationId is null for cancelling");
        }
    }

    private void notify(NotificationType notificationType) {
        Log.I(getClass().getSimpleName() + " - NotificationType: " + notificationType + "[" + ((Object) notificationType.xdmGetNotiContentTitle()) + ", " + ((Object) notificationType.getContentText()) + "]");
        NotificationStrategy notificationStrategy = notificationType.getNotificationStrategy();
        boolean isForegroundService = notificationStrategy.isForegroundService();
        NotificationId notificationId = notificationStrategy.getNotificationId();
        startForeground(notificationId.getId(), NotificationCommon.getNotificationFromType(notificationType));
        Log.I(getClass().getSimpleName() + " - " + notificationId + " : " + notificationType);
        if (!isForegroundService) {
            stopForeground(false);
        }
        notificationId.setNotificationType(notificationType);
    }

    private void cancel(NotificationId notificationId) {
        startForeground(notificationId.getId(), NotificationCommon.getNotificationFromType(notificationId.getNotificationType()));
        stopForeground(true);
        notificationId.clearNotificationType();
    }

    private void avoidANR() {
        startForeground(NotificationId.XDM_NOTIFICATION_ID_NONE.getId(), NotificationCommon.getNotificationFromType(NotificationId.XDM_NOTIFICATION_ID_NONE.getNotificationType()));
        stopForeground(true);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.I("");
    }

    /* access modifiers changed from: private */
    public static class UnexpectedNotificationException extends RuntimeException {
        public UnexpectedNotificationException() {
        }

        public UnexpectedNotificationException(String str) {
            super(str);
        }
    }
}
