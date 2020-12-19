package androidx.reflect.provider;

import android.os.Build;
import android.provider.Settings;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SeslSettingsReflector {
    private SeslSettingsReflector() {
    }

    public static class SeslSystemReflector {
        private static final Class<?> mClass = Settings.System.class;

        private SeslSystemReflector() {
        }

        public static String getField_SEM_PEN_HOVERING() {
            Object obj = null;
            if (Build.VERSION.SDK_INT >= 29) {
                Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_SEM_PEN_HOVERING", new Class[0]);
                if (declaredMethod != null) {
                    obj = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
                }
            } else {
                Field field = SeslBaseReflector.getField(mClass, Build.VERSION.SDK_INT >= 24 ? "SEM_PEN_HOVERING" : "PEN_HOVERING");
                if (field != null) {
                    obj = SeslBaseReflector.get(null, field);
                }
            }
            return obj instanceof String ? (String) obj : "pen_hovering";
        }
    }
}
