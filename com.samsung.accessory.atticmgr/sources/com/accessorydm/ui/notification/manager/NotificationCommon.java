package com.accessorydm.ui.notification.manager;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.service.XDMJobService;
import com.accessorydm.service.XDMService;
import com.accessorydm.ui.XUINotificationConnectActivity;

class NotificationCommon {
    NotificationCommon() {
    }

    static Notification getNotificationFromType(NotificationType notificationType) {
        Notification notification;
        if (Build.VERSION.SDK_INT >= 26) {
            notification = ((Notification.Builder) notificationType.xdmGetBuilder()).setContentIntent(getContentPendingIntent(notificationType)).setDeleteIntent(getDeletePendingIntent(notificationType)).build();
        } else {
            notification = ((NotificationCompat.Builder) notificationType.xdmGetBuilder()).setContentIntent(getContentPendingIntent(notificationType)).setDeleteIntent(getDeletePendingIntent(notificationType)).build();
        }
        switch (notificationType) {
            case XUI_INDICATOR_STUB:
                break;
            case XUI_INDICATOR_SYNC_DM:
            case XUI_INDICATOR_FOTA_UPDATE_COUNTDOWN:
            case XUI_INDICATOR_DOWNLOAD_PROGRESS:
            case XUI_INDICATOR_COPY_PROGRESS:
                notification.flags = 40;
                break;
            case XUI_INDICATOR_UPDATE_RESULT_SUCCESS:
            case XUI_INDICATOR_UPDATE_RESULT_FAILURE:
                notification.flags = 16;
                break;
            default:
                notification.flags = 32;
                break;
        }
        return notification;
    }

    private static PendingIntent getContentPendingIntent(NotificationType notificationType) {
        switch (notificationType) {
            case XUI_INDICATOR_FOTA_UPDATE_COUNTDOWN:
            case XUI_INDICATOR_DOWNLOAD_PROGRESS:
            case XUI_INDICATOR_COPY_PROGRESS:
            case XUI_INDICATOR_UPDATE_RESULT_SUCCESS:
            case XUI_INDICATOR_UPDATE_RESULT_FAILURE:
            case XUI_INDICATOR_UPDATE_BACK_KEY_POSTPONE:
            case XUI_INDICATOR_UPDATE_SCHEDULE_INSTALL:
            case XUI_INDICATOR_UPDATE_POSTPONE_SCHEDULE_INSTALL:
            case XUI_INDICATOR_FOTA_UPDATE:
            case XUI_INDICATOR_DOWNLOAD_START_CONFIRM:
            case XUI_INDICATOR_DOWNLOAD_RETRY_CONFIRM:
            case XUI_INDICATOR_DOWNLOAD_FAILED_NETWORK_DISCONNECTED:
            case XUI_INDICATOR_DOWNLOAD_FAILED_WIFI_DISCONNECTED:
            case XUI_INDICATOR_COPY_FAILED:
                Intent intent = new Intent(XDMDmUtils.getContext(), XUINotificationConnectActivity.class);
                intent.setFlags(335544320);
                intent.putExtra(NotificationTypeManagerService.NOTIFICATION_TYPE_KEY, notificationType);
                return PendingIntent.getActivity(XDMDmUtils.getContext(), 0, intent, 134217728);
            default:
                if (Build.VERSION.SDK_INT >= 26) {
                    return PendingIntent.getService(XDMDmUtils.getContext(), 0, new Intent(XDMDmUtils.getContext(), XDMJobService.class), 0);
                }
                return PendingIntent.getService(XDMDmUtils.getContext(), 0, new Intent(XDMDmUtils.getContext(), XDMService.class), 0);
        }
    }

    private static PendingIntent getDeletePendingIntent(NotificationType notificationType) {
        int i = AnonymousClass1.$SwitchMap$com$accessorydm$ui$notification$manager$NotificationType[notificationType.ordinal()];
        if (i == 6 || i == 7) {
            Intent intent = new Intent(XDMDmUtils.getContext(), XUINotificationConnectActivity.class);
            intent.setFlags(335544320);
            intent.putExtra(NotificationTypeManagerService.NOTIFICATION_TYPE_KEY, notificationType);
            return PendingIntent.getActivity(XDMDmUtils.getContext(), 0, intent, 134217728);
        } else if (Build.VERSION.SDK_INT >= 26) {
            return PendingIntent.getService(XDMDmUtils.getContext(), 0, new Intent(XDMDmUtils.getContext(), XDMJobService.class), 0);
        } else {
            return PendingIntent.getService(XDMDmUtils.getContext(), 0, new Intent(XDMDmUtils.getContext(), XDMService.class), 0);
        }
    }
}
