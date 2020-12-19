package androidx.reflect.os;

import android.os.Build;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Field;

public class SeslBuildReflector {
    private SeslBuildReflector() {
    }

    public static class SeslVersionReflector {
        private static final Class<?> mClass = Build.VERSION.class;

        private SeslVersionReflector() {
        }

        public static int getField_SEM_PLATFORM_INT() {
            Field declaredField;
            if (Build.VERSION.SDK_INT < 28 || (declaredField = SeslBaseReflector.getDeclaredField(mClass, "SEM_PLATFORM_INT")) == null || !(SeslBaseReflector.get(null, declaredField) instanceof Integer)) {
                return -1;
            }
            return ((Integer) SeslBaseReflector.get(null, declaredField)).intValue();
        }
    }
}
