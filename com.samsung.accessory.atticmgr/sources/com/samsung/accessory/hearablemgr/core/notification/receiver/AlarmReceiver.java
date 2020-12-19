package com.samsung.accessory.hearablemgr.core.notification.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import com.accessorydm.interfaces.XCommonInterface;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.notification.NotificationConstants;
import com.samsung.accessory.hearablemgr.core.notification.NotificationMessage;
import com.samsung.accessory.hearablemgr.core.notification.NotificationTTSCore;
import com.samsung.accessory.hearablemgr.core.notification.NotificationUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import seccompat.android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    public static final int COLUMN_ALARMNAME = 20;
    public static final int COLUMN_ALARMTIME = 4;
    private static final String TAG = "Attic_AlarmReceiver";
    public static int preAlarmID = -1;

    private NotificationMessage getAlarmMessage(Context context, int i) {
        Uri parse = Uri.parse("content://com.samsung.sec.android.clockpackage/alarm");
        try {
            ContentResolver contentResolver = context.getContentResolver();
            if (!(contentResolver == null || parse == null)) {
                Cursor query = contentResolver.query(parse, null, "_id = " + i, null, "alerttime ASC");
                if (query == null) {
                    return null;
                }
                if (query.moveToFirst() && query.getCount() > 0) {
                    String string = query.getString(20);
                    int i2 = query.getInt(4);
                    query.close();
                    if (string != null && i2 > 0) {
                        if (NotificationUtil.getAppNotificationDetails(NotificationConstants.ALARM_PACKAGENAME).equals(NotificationConstants.NOTIFICATION_TYPE_SUMMARY)) {
                            i2 = 0;
                        }
                        NotificationMessage notificationMessage = new NotificationMessage(NotificationMessage.TYPE_ALARM, NotificationConstants.ALARM_PACKAGENAME, getApplicationLabel(context), null, string, (long) i2);
                        notificationMessage.log();
                        return notificationMessage;
                    }
                }
                query.close();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private NotificationMessage makeAlarmMessage(Context context) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm", Locale.getDefault());
        int parseInt = (Integer.parseInt(simpleDateFormat.format(date)) * 100) + Integer.parseInt(simpleDateFormat2.format(date));
        if (NotificationUtil.getAppNotificationDetails(NotificationConstants.ALARM_PACKAGENAME).equals(NotificationConstants.NOTIFICATION_TYPE_SUMMARY)) {
            parseInt = 0;
        }
        return new NotificationMessage(NotificationMessage.TYPE_ALARM, NotificationConstants.ALARM_PACKAGENAME, getApplicationLabel(context), null, null, (long) parseInt);
    }

    private String getApplicationLabel(Context context) {
        return context.getResources().getString(R.string.notification_alarm);
    }

    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive action:" + action);
        if (NotificationUtil.semAreNotificationsEnabledForPackage(NotificationConstants.ALARM_PACKAGENAME, false, 0)) {
            if (action.equals("com.samsung.sec.android.clockpackage.alarm.ALARM_STOPPED_IN_ALERT")) {
                preAlarmID = -1;
                final NotificationTTSCore notificationTTSCore = Application.getNotificationCoreService().getNotificationTTSCore();
                if (!notificationTTSCore.isQueueEmpty()) {
                    new Handler().postDelayed(new Runnable() {
                        /* class com.samsung.accessory.hearablemgr.core.notification.receiver.AlarmReceiver.AnonymousClass1 */

                        public void run() {
                            notificationTTSCore.makeTTS(context);
                        }
                    }, XCommonInterface.WAKE_LOCK_TIMEOUT);
                }
            } else if (!action.equals("com.samsung.sec.android.clockpackage.alarm.ALARM_STARTED_IN_ALERT")) {
            } else {
                if (!NotificationUtil.checkAllStatus(NotificationConstants.ALARM_PACKAGENAME)) {
                    Log.d(TAG, "is not enable");
                    return;
                }
                int intExtra = intent.getIntExtra("alertAlarmID", -1);
                NotificationMessage alarmMessage = getAlarmMessage(context, intExtra);
                Log.d(TAG, "getAlarmMessage :" + alarmMessage);
                if (alarmMessage == null) {
                    alarmMessage = makeAlarmMessage(context);
                    Log.d(TAG, "makeAlarmMessage :" + alarmMessage);
                }
                if (alarmMessage != null && preAlarmID != intExtra) {
                    preAlarmID = intExtra;
                    Intent intent2 = new Intent(NotificationConstants.ACTION_UPDATE_VN_MESSAGE);
                    intent2.putExtra(NotificationConstants.VN_MESSAGE, alarmMessage);
                    context.sendBroadcast(intent2, "com.samsung.accessory.atticmgr.permission.SIGNATURE");
                }
            }
        }
    }
}
