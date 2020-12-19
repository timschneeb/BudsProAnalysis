package com.accessorydm.ui.notification.manager;

public interface NotificationTypeManagerService {
    public static final String NOTIFICATION_CHANNELID = "b.SOFTWARE_UPDATES_CHANNEL_ID";
    public static final String NOTIFICATION_ID_KEY = "com.sec.android.fotaprovider.NOTIFICATION_ID_KEY";
    public static final String NOTIFICATION_TYPE_KEY = "com.sec.android.fotaprovider.NOTIFICATION_TYPE_KEY";

    public static class Operation {
        static final String CANCEL = "OPERATION_CANCEL";
        static final String KEY = "OPERATION_KEY";
        static final String NOTIFY = "OPERATION_NOTIFY";
    }
}
