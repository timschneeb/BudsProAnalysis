package seccompat.android.app;

import seccompat.Reflection;
import seccompat.SecCompatUtil;

public class ActivityManager {
    public static boolean proxyRemoveTask(android.app.ActivityManager activityManager, int i, int i2) {
        if (SecCompatUtil.isSEPDevice()) {
            return activityManager.semRemoveTask(i, i2);
        }
        try {
            return ((Boolean) Reflection.callMethod(activityManager, "removeTask", Integer.valueOf(i), Integer.valueOf(i2))).booleanValue();
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }
}
