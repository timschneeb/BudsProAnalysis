package com.samsung.android.app.twatchmanager.update;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import d.a.a.b;
import d.a.a.d.a;

public class UpdateTimer {
    public static final String ACTION_AFTER_TUHM_INSTALL_REQUEST = "com.samsung.uhm.action.ACTION_AFTER_TUHM_INSTALL_REQUEST";
    public static final String ACTION_BACKGROUND_UPDATE = "com.samsung.uhm.action.ACTION_BACKGROUND_UPDATE";
    public static final int BACKGROUND_UPDATE_MIDNIGHT_END_HOUR = 4;
    public static final int BACKGROUND_UPDATE_MIDNIGHT_END_MIN = 0;
    private static final int BACKGROUND_UPDATE_PENDING_INTENT_ID = 1001;
    public static final int BACKGROUND_UPDATE_START_HOUR = 2;
    public static final int BACKGROUND_UPDATE_START_MIN = 0;
    public static final int BACKGROUND_UPDATE_TUHM_UPDATE_ALARM_MIN = 2;
    private static final String TAG = ("tUHM:[Update]" + UpdateTimer.class.getSimpleName());

    public static boolean checkMidnightTime() {
        b a2 = new b().a(2, 0, 0, 0);
        b a3 = new b().a(4, 0, 0, 0);
        d.a.a.d.b a4 = a.a("yyyy-MM-dd E HH:mm:ss");
        boolean b2 = a2.b();
        boolean c2 = a3.c();
        String str = TAG;
        Log.d(str, "checkMidnightTime() current Time : " + b.h().a(a4) + " underBound : " + b2 + " upperBound : " + c2);
        return b2 && c2;
    }

    private static b getWakeUpTime(String str) {
        b a2 = new b().a(2, 0, 0, 0);
        if (a2.c()) {
            a2 = a2.a(1);
        }
        d.a.a.d.b a3 = a.a("yyyy-MM-dd E HH:mm:ss");
        if (ACTION_AFTER_TUHM_INSTALL_REQUEST.equals(str)) {
            a2 = new b().c(2);
        }
        String str2 = TAG;
        Log.d(str2, "setBackgroundInstallTimer() current : " + b.h().a(a3) + " set alarm : " + a2.a(a3));
        return a2;
    }

    public static boolean isPendingIntentWorking() {
        Context appContext = TWatchManagerApplication.getAppContext();
        Intent intent = new Intent(appContext, UpdateCheckingReceiver.class);
        intent.setAction(ACTION_BACKGROUND_UPDATE);
        PendingIntent broadcast = PendingIntent.getBroadcast(appContext, 1001, intent, 536870912);
        String str = TAG;
        Log.d(str, "isPendingIntentWorking() pi : " + broadcast);
        return broadcast != null;
    }

    private static PendingIntent makingAlarmIntent(String str) {
        Context appContext = TWatchManagerApplication.getAppContext();
        Intent intent = new Intent(appContext, UpdateCheckingReceiver.class);
        intent.setAction(str);
        return PendingIntent.getBroadcast(appContext, 1001, intent, 268435456);
    }

    public static void setNextBackgroundUpdateAlarm(String str) {
        PendingIntent makingAlarmIntent = makingAlarmIntent(str);
        b wakeUpTime = getWakeUpTime(str);
        AlarmManager alarmManager = (AlarmManager) TWatchManagerApplication.getAppContext().getSystemService("alarm");
        String str2 = TAG;
        Log.d(str2, "setNextBackgroundUpdateAlarm() alarmMgr : " + alarmManager);
        if (alarmManager != null) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 23) {
                alarmManager.setExactAndAllowWhileIdle(0, wakeUpTime.a(), makingAlarmIntent);
            } else if (i >= 19) {
                alarmManager.setExact(0, wakeUpTime.a(), makingAlarmIntent);
            } else {
                alarmManager.set(0, wakeUpTime.a(), makingAlarmIntent);
            }
        }
    }
}
