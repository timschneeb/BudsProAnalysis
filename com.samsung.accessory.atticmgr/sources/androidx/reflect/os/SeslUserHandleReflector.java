package androidx.reflect.os;

import android.os.Build;
import android.os.UserHandle;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

public class SeslUserHandleReflector {
    private static final Class<?> mClass = UserHandle.class;

    private SeslUserHandleReflector() {
    }

    public static int myUserId() {
        Method method;
        if (Build.VERSION.SDK_INT >= 29) {
            method = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_myUserId", new Class[0]);
        } else {
            method = SeslBaseReflector.getMethod(mClass, "myUserId", new Class[0]);
        }
        if (method != null) {
            Object invoke = SeslBaseReflector.invoke(null, method, new Object[0]);
            if (invoke instanceof Integer) {
                return ((Integer) invoke).intValue();
            }
        }
        return 0;
    }
}
