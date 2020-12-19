package com.samsung.accessory.hearablemgr.core.notification.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.notification.NotificationConstants;
import com.samsung.accessory.hearablemgr.core.notification.NotificationUtil;
import seccompat.android.util.Log;

public class CalendarReceiver extends BroadcastReceiver {
    private static final String CALENDAR_EVENTID_EXTRA = "eventid";
    private static final String CALENDAR_ISEVENT = "isEventAlert";
    private static final String CALENDAR_TASKID_EXTRA = "_id";
    public static final Uri EVENT_CONTENT_URI = Uri.parse("content://com.android.calendar/events");
    private static final String TAG = "Attic_CalendarReceiver";
    public static final Uri TASK_CONTENT_URI = Uri.parse("content://com.android.calendar/syncTasks");
    private Context mContext;

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void getEvent(long r14) {
        /*
        // Method dump skipped, instructions count: 271
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.notification.receiver.CalendarReceiver.getEvent(long):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00ff  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void getTask(long r14) {
        /*
        // Method dump skipped, instructions count: 265
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.notification.receiver.CalendarReceiver.getTask(long):void");
    }

    private String getApplicationLabel(Context context) {
        return context.getResources().getString(R.string.notification_schedule);
    }

    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        Log.d(TAG, "onReceive action: Calendar");
        if (!NotificationUtil.checkAllStatus(NotificationConstants.CALENDAR_PACKAGENAME)) {
            Log.d(TAG, "is not enable");
        } else if (NotificationUtil.semAreNotificationsEnabledForPackage(NotificationConstants.CALENDAR_PACKAGENAME, false, 0)) {
            try {
                if ((context.getPackageManager().getPackageInfo(NotificationConstants.CALENDAR_PACKAGENAME, 0).applicationInfo.flags & 1073741824) == 0) {
                    Log.d(TAG, "not suspended");
                    if (!Util.isSamsungDevice()) {
                        return;
                    }
                    if (intent.getAction().equalsIgnoreCase(NotificationConstants.CALENDAR_SEND_ALERTINFO_ACTION)) {
                        try {
                            long longExtra = intent.getLongExtra(CALENDAR_EVENTID_EXTRA, -1);
                            boolean booleanExtra = intent.getBooleanExtra(CALENDAR_ISEVENT, true);
                            Log.d(TAG, "Calendar event notification received: EventID = " + longExtra);
                            if (booleanExtra) {
                                getEvent(longExtra);
                            } else {
                                getTask(longExtra);
                            }
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }
                    } else if (intent.getAction().equals(NotificationConstants.CALENDAR_ACTION_TASK_ALARM)) {
                        try {
                            long longExtra2 = intent.getLongExtra("_id", -1);
                            Log.d(TAG, "Calendar task notification received: EventID = " + longExtra2);
                            getTask(longExtra2);
                        } catch (RuntimeException e2) {
                            e2.printStackTrace();
                        }
                    }
                } else {
                    Log.d(TAG, "suspended!");
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }
}
