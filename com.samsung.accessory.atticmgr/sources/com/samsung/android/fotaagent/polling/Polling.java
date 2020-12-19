package com.samsung.android.fotaagent.polling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.accessorydm.db.file.XDBPollingAdp;
import com.samsung.android.fotaagent.PollingReceiver;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.GeneralUtil;
import java.util.GregorianCalendar;
import java.util.Random;

public class Polling {
    private static final String PERIOD_UNIT_HOUR = "hour";
    private static final String PERIOD_UNIT_MIN = "min";

    public static void startPollingTimer(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (alarmManager == null) {
            Log.W("AlarmManager is null!!");
            return;
        }
        long xdbGetNextPollingTime = XDBPollingAdp.xdbGetNextPollingTime();
        alarmManager.setRepeating(0, xdbGetNextPollingTime, 3600000, PendingIntent.getBroadcast(context, 0, new Intent(context, PollingReceiver.class).setAction(PollingIntent.INTENT_POLLING_TIME), 134217728));
        Log.I("Start polling timer");
        Log.D("next polling time: " + GeneralUtil.convertMillisToDateTime(xdbGetNextPollingTime));
    }

    public static void stopPollingTimer(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (alarmManager == null) {
            Log.W("AlarmManager is null!!");
            return;
        }
        alarmManager.cancel(PendingIntent.getBroadcast(context, 0, new Intent(context, PollingReceiver.class).setAction(PollingIntent.INTENT_POLLING_TIME), 134217728));
        Log.I("Stop polling timer");
    }

    public static boolean isPassedPollingTime() {
        long currentTimeMillis = System.currentTimeMillis();
        long xdbGetNextPollingTime = XDBPollingAdp.xdbGetNextPollingTime();
        Log.H("Current time:" + GeneralUtil.convertMillisToDateTime(currentTimeMillis));
        Log.H("Next polling time:" + GeneralUtil.convertMillisToDateTime(xdbGetNextPollingTime));
        if (xdbGetNextPollingTime <= 0 || xdbGetNextPollingTime > currentTimeMillis) {
            Log.I("not passed polling time. not available polling.");
            return false;
        }
        Log.I("passed polling time. available polling.");
        return true;
    }

    public static void calculateNextPollingTime() {
        long j;
        long period;
        Log.I("Calculate next polling time");
        try {
            PollingInfo xdbGetPollingInfo = XDBPollingAdp.xdbGetPollingInfo();
            Random random = new Random();
            long currentTimeMillis = System.currentTimeMillis();
            if (PERIOD_UNIT_HOUR.equals(xdbGetPollingInfo.getPeriodUnit())) {
                period = ((long) xdbGetPollingInfo.getPeriod()) * 60;
            } else if (PERIOD_UNIT_MIN.equals(xdbGetPollingInfo.getPeriodUnit())) {
                period = (long) xdbGetPollingInfo.getPeriod();
            } else {
                int range = xdbGetPollingInfo.getRange();
                if (range == 0) {
                    Log.W("Range can't be zero. set to default range, 1");
                    range = 1;
                }
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(currentTimeMillis);
                gregorianCalendar.add(5, xdbGetPollingInfo.getPeriod());
                gregorianCalendar.set(11, xdbGetPollingInfo.getTime() + random.nextInt(range));
                gregorianCalendar.set(12, random.nextInt(60));
                gregorianCalendar.set(13, random.nextInt(60));
                j = gregorianCalendar.getTimeInMillis();
                Log.H("Current time:" + GeneralUtil.convertMillisToDateTime(currentTimeMillis));
                Log.H("Next polling time:" + GeneralUtil.convertMillisToDateTime(j));
                XDBPollingAdp.xdbSetNextPollingTime(j);
            }
            j = (period * 60 * 1000) + currentTimeMillis;
            Log.H("Current time:" + GeneralUtil.convertMillisToDateTime(currentTimeMillis));
            Log.H("Next polling time:" + GeneralUtil.convertMillisToDateTime(j));
            XDBPollingAdp.xdbSetNextPollingTime(j);
        } catch (Exception e) {
            Log.E("Exception : " + e.toString());
        }
    }
}
