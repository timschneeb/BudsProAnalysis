package androidx.reflect.app;

import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

public class SeslApplicationPackageManagerReflector {
    private static String mClassName = "android.app.ApplicationPackageManager";

    private SeslApplicationPackageManagerReflector() {
    }

    public static Drawable semGetApplicationIconForIconTray(Object obj, String str, int i) {
        Method method;
        if (Build.VERSION.SDK_INT >= 29) {
            method = SeslBaseReflector.getDeclaredMethod(mClassName, "hidden_semGetApplicationIconForIconTray", String.class, Integer.TYPE);
        } else if (Build.VERSION.SDK_INT >= 24) {
            method = SeslBaseReflector.getMethod(mClassName, "semGetApplicationIconForIconTray", String.class, Integer.TYPE);
        } else {
            method = null;
        }
        if (method != null) {
            Object invoke = SeslBaseReflector.invoke(obj, method, str, Integer.valueOf(i));
            if (invoke instanceof Drawable) {
                return (Drawable) invoke;
            }
        }
        return null;
    }
}
