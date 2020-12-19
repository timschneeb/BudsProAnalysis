package com.samsung.accessory.hearablemgr.common.preference;

import android.content.SharedPreferences;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import seccompat.android.util.Log;

public class Preferences {
    public static final String MODE_MANAGER = "manager";
    public static final String NAME_PREFERENCE_LIST = "preference_name_list";
    private static final String TAG = "Attic_Preferences";

    private static void putObject(String str, Object obj, String str2) {
        if (str2 == null) {
            Log.e(TAG, "[null." + str + "] put(" + _autoPrivateAddress(obj) + ") failed!");
            return;
        }
        String lowerCase = str2.toLowerCase();
        registerPreferenceName(lowerCase);
        SharedPreferences.Editor edit = Application.getContext().getSharedPreferences(lowerCase, 4).edit();
        if (obj == null) {
            edit.remove(str);
        } else if (obj instanceof String) {
            edit.putString(str, (String) obj);
        } else if (obj instanceof Integer) {
            edit.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Boolean) {
            edit.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Long) {
            edit.putLong(str, ((Long) obj).longValue());
        }
        edit.apply();
        Log.v(TAG, "[" + _autoPrivateAddress(lowerCase) + XDMInterface.XDM_BASE_PATH + str + "] put(" + _autoPrivateAddress(obj) + ")");
    }

    public static void putString(String str, String str2, String str3) {
        putObject(str, str2, str3);
    }

    public static void putString(String str, String str2) {
        String lastLaunchDeviceId = getLastLaunchDeviceId();
        if (lastLaunchDeviceId != null) {
            putString(str, str2, lastLaunchDeviceId);
        }
    }

    public static void putInt(String str, Integer num, String str2) {
        putObject(str, num, str2);
    }

    public static void putInt(String str, Integer num) {
        String lastLaunchDeviceId = getLastLaunchDeviceId();
        if (lastLaunchDeviceId != null) {
            putInt(str, num, lastLaunchDeviceId);
        }
    }

    public static void putBoolean(String str, Boolean bool, String str2) {
        putObject(str, bool, str2);
    }

    public static void putBoolean(String str, Boolean bool) {
        String lastLaunchDeviceId = getLastLaunchDeviceId();
        if (lastLaunchDeviceId != null) {
            putBoolean(str, bool, lastLaunchDeviceId);
        }
    }

    public static void putLong(String str, Long l, String str2) {
        putObject(str, l, str2);
    }

    public static void putLong(String str, Long l) {
        String lastLaunchDeviceId = getLastLaunchDeviceId();
        if (lastLaunchDeviceId != null) {
            putLong(str, l, lastLaunchDeviceId);
        }
    }

    private static Object getObject(String str, Class cls, String str2) {
        Object obj = null;
        if (str2 == null) {
            Log.e(TAG, "[null." + str + "] null - failed!");
            return null;
        }
        String lowerCase = str2.toLowerCase();
        SharedPreferences sharedPreferences = Application.getContext().getSharedPreferences(lowerCase, 4);
        if (sharedPreferences.contains(str)) {
            if (cls == String.class) {
                obj = sharedPreferences.getString(str, null);
            } else if (cls == Integer.class) {
                obj = Integer.valueOf(sharedPreferences.getInt(str, 0));
            } else if (cls == Boolean.class) {
                obj = Boolean.valueOf(sharedPreferences.getBoolean(str, false));
            } else if (cls == Long.class) {
                obj = Long.valueOf(sharedPreferences.getLong(str, 0));
            }
        }
        Log.v(TAG, "[" + _autoPrivateAddress(lowerCase) + XDMInterface.XDM_BASE_PATH + str + "] " + _autoPrivateAddress(obj));
        return obj;
    }

    public static String getString(String str, String str2, String str3) {
        String str4 = (String) getObject(str, String.class, str3);
        return str4 == null ? str2 : str4;
    }

    public static String getString(String str, String str2) {
        return getString(str, str2, getLastLaunchDeviceId());
    }

    public static int getInt(String str, int i, String str2) {
        Integer num = (Integer) getObject(str, Integer.class, str2);
        if (num == null) {
            num = Integer.valueOf(i);
        }
        return num.intValue();
    }

    public static int getInt(String str, int i) {
        return getInt(str, i, getLastLaunchDeviceId());
    }

    public static boolean getBoolean(String str, boolean z, String str2) {
        Boolean bool = (Boolean) getObject(str, Boolean.class, str2);
        if (bool == null) {
            bool = Boolean.valueOf(z);
        }
        return bool.booleanValue();
    }

    public static boolean getBoolean(String str, boolean z) {
        return getBoolean(str, z, getLastLaunchDeviceId());
    }

    public static long getLong(String str, long j, String str2) {
        Long l = (Long) getObject(str, Long.class, str2);
        if (l == null) {
            l = Long.valueOf(j);
        }
        return l.longValue();
    }

    public static long getLong(String str, long j) {
        return getLong(str, j, getLastLaunchDeviceId());
    }

    public static void remove(String str, String str2) {
        if (str2 == null) {
            Log.e(TAG, "[null." + str + "] null - failed!");
            return;
        }
        SharedPreferences.Editor edit = Application.getContext().getSharedPreferences(str2.toLowerCase(), 4).edit();
        edit.remove(str);
        edit.apply();
    }

    private static void registerPreferenceName(String str) {
        if (str != null) {
            SharedPreferences sharedPreferences = Application.getContext().getSharedPreferences(NAME_PREFERENCE_LIST, 4);
            if (!sharedPreferences.contains(str)) {
                sharedPreferences.edit().putBoolean(str, true).apply();
                return;
            }
            return;
        }
        throw new RuntimeException("preferenceName == null");
    }

    private static void clearSharedPreferences(String str) {
        Log.d(TAG, "clearSharedPreferences() : " + str);
        Application.getContext().getSharedPreferences(str, 4).edit().clear().apply();
    }

    public static void clear(String str) {
        if (str != null) {
            clearSharedPreferences(str.toLowerCase());
            return;
        }
        throw new RuntimeException("targetAddress == null");
    }

    public static void clearAll() {
        for (String str : Application.getContext().getSharedPreferences(NAME_PREFERENCE_LIST, 4).getAll().keySet()) {
            clearSharedPreferences(str);
        }
        clearSharedPreferences(MODE_MANAGER);
    }

    private static String getLastLaunchDeviceId() {
        return getString(PreferenceKey.LAST_LAUNCH_BT_ADDRESS, null, MODE_MANAGER);
    }

    private static String _autoPrivateAddress(Object obj) {
        return BluetoothUtil.autoPrivateAddress(obj);
    }
}
