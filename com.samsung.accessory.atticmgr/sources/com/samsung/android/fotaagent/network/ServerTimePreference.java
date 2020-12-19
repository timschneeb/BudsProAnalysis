package com.samsung.android.fotaagent.network;

import android.content.Context;
import android.content.SharedPreferences;
import com.samsung.android.fotaprovider.log.Log;

public class ServerTimePreference {
    private static final String KEY_OFFSET = "ServerTime.OffsetTime";
    private static final String KEY_SETTING_TIME = "ServerTime.SettingTime";
    private static final String SERVER_TIME_SETTING = "OSP_SERVER_TIME";
    public static ServerTimePreference instance = new ServerTimePreference();

    public long getServerTime(Context context) {
        long longPreference = getLongPreference(context, SERVER_TIME_SETTING, KEY_SETTING_TIME, 0);
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        if (longPreference == 0) {
            Log.I("Server time is zero");
            return 0;
        } else if (longPreference + 86400000 < currentTimeMillis) {
            Log.I("Server time is invalid, the day passed");
            resetServerTime(context);
            return 0;
        } else if (longPreference - 86400000 > currentTimeMillis) {
            Log.I("Server time is invalid, a day later");
            resetServerTime(context);
            return 0;
        } else {
            return (System.currentTimeMillis() / 1000) + getLongPreference(context, SERVER_TIME_SETTING, KEY_OFFSET, 0);
        }
    }

    public void setServerTime(Context context, long j) {
        long j2;
        long j3;
        if (j != 0) {
            long currentTimeMillis = System.currentTimeMillis() / 1000;
            j2 = j - currentTimeMillis;
            j3 = currentTimeMillis;
        } else {
            j2 = 0;
            j3 = 0;
        }
        setLongPreference(context, SERVER_TIME_SETTING, KEY_SETTING_TIME, j3);
        setLongPreference(context, SERVER_TIME_SETTING, KEY_OFFSET, j2);
    }

    public void resetServerTime(Context context) {
        setServerTime(context, 0);
    }

    private long getLongPreference(Context context, String str, String str2, int i) {
        if (context == null) {
            return (long) i;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        if (sharedPreferences == null) {
            return (long) i;
        }
        return sharedPreferences.getLong(str2, (long) i);
    }

    private void setLongPreference(Context context, String str, String str2, long j) {
        SharedPreferences sharedPreferences;
        if (context != null && (sharedPreferences = context.getSharedPreferences(str, 0)) != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.remove(str2);
            edit.putLong(str2, j);
            edit.apply();
        }
    }
}
