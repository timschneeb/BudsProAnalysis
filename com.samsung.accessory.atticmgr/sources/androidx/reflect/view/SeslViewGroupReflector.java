package androidx.reflect.view;

import android.view.ViewGroup;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

public class SeslViewGroupReflector {
    private static final Class<?> mClass = ViewGroup.class;

    private SeslViewGroupReflector() {
    }

    public static void resolvePadding(ViewGroup viewGroup) {
        Method method = SeslBaseReflector.getMethod(mClass, "resolvePadding", new Class[0]);
        if (method != null) {
            SeslBaseReflector.invoke(viewGroup, method, new Object[0]);
        }
    }
}
