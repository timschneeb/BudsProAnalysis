package androidx.reflect.view.inputmethod;

import android.view.inputmethod.InputMethodManager;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

public class SeslInputMethodManagerReflector {
    private static final Class<?> mClass = InputMethodManager.class;

    private SeslInputMethodManagerReflector() {
    }

    public static int isAccessoryKeyboardState(InputMethodManager inputMethodManager) {
        Method method = SeslBaseReflector.getMethod(mClass, "isAccessoryKeyboardState", new Class[0]);
        if (method != null) {
            Object invoke = SeslBaseReflector.invoke(inputMethodManager, method, new Object[0]);
            if (invoke instanceof Integer) {
                return ((Integer) invoke).intValue();
            }
        }
        return 0;
    }
}
