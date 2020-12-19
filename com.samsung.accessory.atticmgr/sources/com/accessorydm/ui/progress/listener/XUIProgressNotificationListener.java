package com.accessorydm.ui.progress.listener;

import com.accessorydm.ui.notification.XUINotificationManager;
import com.accessorydm.ui.notification.manager.NotificationId;
import com.accessorydm.ui.notification.manager.NotificationType;
import com.accessorydm.ui.progress.XUIProgressModel;
import com.accessorydm.ui.progress.listener.XUIProgressListener;
import com.samsung.android.fotaprovider.log.Log;

public class XUIProgressNotificationListener extends XUIProgressBaseListener {
    public XUIProgressNotificationListener() {
        super(XUIProgressListener.ListenerPriority.Notification);
    }

    @Override // com.accessorydm.ui.progress.listener.XUIProgressListener
    public void onProgressInfoUpdated() {
        if (isOutOfRange()) {
            Log.W("not in progress range");
            XUINotificationManager.getInstance().xuiRemoveNotification(NotificationId.XDM_NOTIFICATION_ID_PRIMARY);
            XUIProgressModel.getInstance().setIncreasePercentage(false);
        } else if (XUIProgressModel.getInstance().isIncreasePercentage()) {
            int progressMode = XUIProgressModel.getInstance().getProgressMode();
            if (progressMode == 1) {
                XUINotificationManager.getInstance().xuiSetIndicator(NotificationType.XUI_INDICATOR_DOWNLOAD_PROGRESS);
            } else if (progressMode != 2) {
                XUINotificationManager.getInstance().xuiRemoveNotification(NotificationId.XDM_NOTIFICATION_ID_PRIMARY);
            } else {
                XUINotificationManager.getInstance().xuiSetIndicator(NotificationType.XUI_INDICATOR_COPY_PROGRESS);
            }
            XUIProgressModel.getInstance().setIncreasePercentage(false);
        }
    }
}
