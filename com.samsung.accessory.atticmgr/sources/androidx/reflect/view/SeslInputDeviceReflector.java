package androidx.reflect.view;

import android.os.Build;
import android.view.InputDevice;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

public class SeslInputDeviceReflector {
    private static final Class<?> mClass = InputDevice.class;

    private SeslInputDeviceReflector() {
    }

    public static void semSetPointerType(InputDevice inputDevice, int i) {
        if (inputDevice != null) {
            Method method = null;
            if (Build.VERSION.SDK_INT >= 29) {
                method = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_setPointerType", Integer.TYPE);
            } else if (Build.VERSION.SDK_INT >= 28) {
                method = SeslBaseReflector.getMethod(mClass, "semSetPointerType", Integer.TYPE);
            } else if (Build.VERSION.SDK_INT >= 24) {
                method = SeslBaseReflector.getMethod(mClass, "setPointerType", Integer.TYPE);
            }
            if (method != null) {
                SeslBaseReflector.invoke(inputDevice, method, Integer.valueOf(i));
            }
        }
    }
}
