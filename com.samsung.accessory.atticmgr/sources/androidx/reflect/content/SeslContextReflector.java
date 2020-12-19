package androidx.reflect.content;

import android.content.Context;
import android.os.Build;
import android.os.UserHandle;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

public class SeslContextReflector {
    private static final Class<?> mClass = Context.class;

    private SeslContextReflector() {
    }

    public static Context createPackageContextAsUser(Context context, String str, int i, UserHandle userHandle) {
        Method method;
        if (Build.VERSION.SDK_INT >= 29) {
            method = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_createPackageContextAsUser", String.class, Integer.TYPE, UserHandle.class);
        } else {
            method = SeslBaseReflector.getMethod(mClass, "createPackageContextAsUser", String.class, Integer.TYPE, UserHandle.class);
        }
        if (method == null) {
            return null;
        }
        Object invoke = SeslBaseReflector.invoke(context, method, str, Integer.valueOf(i), userHandle);
        if (invoke instanceof Context) {
            return (Context) invoke;
        }
        return null;
    }

    public static String[] getTheme(Context context) {
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_getTheme", new Class[0]);
        if (declaredMethod == null) {
            return null;
        }
        Object invoke = SeslBaseReflector.invoke(context, declaredMethod, new Object[0]);
        if (invoke instanceof String[]) {
            return (String[]) invoke;
        }
        return null;
    }
}
