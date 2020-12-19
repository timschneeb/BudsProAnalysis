package com.samsung.android.fotaprovider.appstate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import androidx.work.WorkRequest;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBRegistrationAdp;
import com.accessorydm.postpone.PostponeManager;
import com.samsung.android.fotaagent.FotaNoticeIntent;
import com.samsung.android.fotaagent.polling.Polling;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.OperatorUtil;
import com.samsung.android.fotaprovider.util.galaxywearable.BroadcastHelper;

public class FotaProviderState {
    private static final int BLOCK_MILLISECONDS_FOR_CHANGED_DEVICE_PROCESS = 10000;
    private static final int DEFAULT_CHANGED_DEVICE_TIME = 0;
    private static final String PREFERENCE_KEY_BADGE_COUNT = "FOTAPROVIDER_FOTA_BADGECOUNT";
    private static final String PREFERENCE_KEY_FCM_ID = "FOTAPROVIDER_FCM_REG_ID";
    private static final String PREFERENCE_NAME = "FOTAPROVIDER_PREFERENCE";
    private static long changedDeviceTime;

    public static String getFcmID(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(PREFERENCE_KEY_FCM_ID, "");
    }

    public static void storeFcmID(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.remove(PREFERENCE_KEY_FCM_ID);
            edit.putString(PREFERENCE_KEY_FCM_ID, str);
            edit.apply();
        }
    }

    public static void setFotaBadgeState(int i) {
        setFotaBadgeCount(isInProgress(i) ? 1 : 0);
    }

    public static void setFotaBadgeCount(int i) {
        Log.D("set badge to " + i);
        Intent intent = new Intent(FotaNoticeIntent.INTENT_FOTA_BADGE_COUNT);
        intent.putExtra("badge_count", i);
        intent.setPackage(FotaProviderInitializer.getContext().getPackageName());
        BroadcastHelper.sendBroadcast(intent);
        SharedPreferences sharedPreferences = FotaProviderInitializer.getContext().getSharedPreferences(PREFERENCE_NAME, 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.remove(PREFERENCE_KEY_BADGE_COUNT);
            edit.putString(PREFERENCE_KEY_BADGE_COUNT, String.valueOf(i));
            edit.apply();
        }
    }

    public static boolean isInChangedDeviceProcess() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = changedDeviceTime;
        return j != 0 && WorkRequest.MIN_BACKOFF_MILLIS + j >= elapsedRealtime;
    }

    public static void blockActionDuringChangedDeviceProcess() {
        if (isInChangedDeviceProcess()) {
            Log.D("do nothing in changed device process");
            return;
        }
        Log.D("block action in few seconds for changed device process");
        changedDeviceTime = SystemClock.elapsedRealtime();
    }

    public static void resetChangedDeviceProcess() {
        Log.D("reset changed device process");
        changedDeviceTime = 0;
    }

    public static void resetDataAndStopAlarms(Context context) {
        storeFcmID(context, "");
        XDB.xdbFullResetAll();
        stopAllAlarms(context);
    }

    private static void stopAllAlarms(Context context) {
        Polling.stopPollingTimer(context);
        PostponeManager.stopAlarm();
    }

    public static boolean isDeviceRegisteredDB() {
        return XDBRegistrationAdp.getDeviceRegistrationStatus() == 1;
    }

    public static boolean isPushRegisteredDB() {
        int pushRegistrationStatus = XDBRegistrationAdp.getPushRegistrationStatus();
        if (OperatorUtil.isSPP()) {
            return pushRegistrationStatus == 1;
        }
        if (getFcmID(FotaProviderInitializer.getContext()).isEmpty()) {
            XDBRegistrationAdp.setPushRegistrationStatus(0);
        }
        return pushRegistrationStatus == 2;
    }

    public static boolean isInProgress(int i) {
        if (i == 20 || i == 30 || i == 40 || i == 50 || i == 200 || i == 220 || i == 230 || i == 240 || i == 250 || i == 251) {
            Log.I("Current status is in progress");
            return true;
        }
        Log.I("Current status is not in progress");
        return false;
    }

    public static boolean isInUpdateReporting(int i) {
        if (i == 65 || i == 80 || i == 100) {
            Log.I("Current status is in update reporting progress");
            return true;
        }
        Log.I("Current status is not in update reporting progress");
        return false;
    }

    public static boolean isAvailableShowOnPhone(int i) {
        if (i == 40 || i == 50 || i == 200 || i == 220 || i == 251) {
            Log.I("show on phone is available");
            return true;
        }
        Log.I("show on phone is not available");
        return false;
    }
}
