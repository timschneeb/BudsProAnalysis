package com.samsung.accessory.hearablemgr;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import com.samsung.accessory.atticmgr.R;
import oreocompat.OreoCompatUtil;
import seccompat.android.util.Log;

public class NotificationChannels {
    public static final String ID_GENERAL_NOTIFICATIONS = "010_general_notifications";
    public static final String ID_NOTIFICATION_DELAYS = "030_notification_delays";
    private static final String TAG = "Attic_AppNotificationChannels";

    public static void register(Context context) {
        if (OreoCompatUtil.isOreoSupportDevice()) {
            Log.d(TAG, "register() : " + context);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            registerGeneralNotificationsChannel(context, notificationManager);
            registerNotificationDelaysChannel(context, notificationManager);
        }
    }

    private static void registerGeneralNotificationsChannel(Context context, NotificationManager notificationManager) {
        OreoCompatUtil.createNotificationChannel(notificationManager, new NotificationChannel(ID_GENERAL_NOTIFICATIONS, context.getString(R.string.general_notifications), 3));
    }

    private static void registerNotificationDelaysChannel(Context context, NotificationManager notificationManager) {
        OreoCompatUtil.createNotificationChannel(notificationManager, new NotificationChannel(ID_NOTIFICATION_DELAYS, context.getString(R.string.notification_delays), 1));
    }
}
