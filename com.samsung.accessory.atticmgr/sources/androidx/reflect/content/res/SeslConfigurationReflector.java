package androidx.reflect.content.res;

import android.content.res.Configuration;
import android.os.Build;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SeslConfigurationReflector {
    private static final Class<?> mClass = Configuration.class;

    private SeslConfigurationReflector() {
    }

    public static boolean isDexEnabled(Configuration configuration) {
        return getField_semDesktopModeEnabled(configuration) == getField_SEM_DESKTOP_MODE_ENABLED();
    }

    private static int getField_semDesktopModeEnabled(Configuration configuration) {
        Field declaredField;
        Object obj = null;
        if (Build.VERSION.SDK_INT >= 29) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_semDesktopModeEnabled", new Class[0]);
            if (declaredMethod != null) {
                obj = SeslBaseReflector.invoke(configuration, declaredMethod, new Object[0]);
            }
        } else if (Build.VERSION.SDK_INT >= 24 && (declaredField = SeslBaseReflector.getDeclaredField(mClass, "semDesktopModeEnabled")) != null) {
            obj = SeslBaseReflector.get(configuration, declaredField);
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return -1;
    }

    private static int getField_SEM_DESKTOP_MODE_ENABLED() {
        Field declaredField;
        Object obj = null;
        if (Build.VERSION.SDK_INT >= 29) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_SEM_DESKTOP_MODE_ENABLED", new Class[0]);
            if (declaredMethod != null) {
                obj = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
            }
        } else if (Build.VERSION.SDK_INT >= 24 && (declaredField = SeslBaseReflector.getDeclaredField(mClass, "SEM_DESKTOP_MODE_ENABLED")) != null) {
            obj = SeslBaseReflector.get(null, declaredField);
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 0;
    }
}
