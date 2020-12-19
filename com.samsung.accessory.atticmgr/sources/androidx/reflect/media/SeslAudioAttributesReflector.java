package androidx.reflect.media;

import android.media.AudioAttributes;
import android.os.Build;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SeslAudioAttributesReflector {
    private static final Class<?> mClass = AudioAttributes.class;

    private SeslAudioAttributesReflector() {
    }

    public static int getField_FLAG_BYPASS_INTERRUPTION_POLICY() {
        Field field;
        Object obj = null;
        if (Build.VERSION.SDK_INT >= 29) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_FLAG_BYPASS_INTERRUPTION_POLICY", new Class[0]);
            if (declaredMethod != null) {
                obj = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
            }
        } else if (Build.VERSION.SDK_INT >= 23 && (field = SeslBaseReflector.getField(mClass, "FLAG_BYPASS_INTERRUPTION_POLICY")) != null) {
            obj = SeslBaseReflector.get(null, field);
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 1;
    }
}
