package androidx.reflect.widget;

import android.os.Build;
import android.widget.OverScroller;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

public class SeslOverScrollerReflector {
    private static final Class<?> mClass = OverScroller.class;

    private SeslOverScrollerReflector() {
    }

    public static void fling(OverScroller overScroller, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z, float f) {
        Method declaredMethod;
        if (Build.VERSION.SDK_INT < 30 || (declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_fling", Integer.TYPE, Integer.TYPE, Boolean.TYPE, Float.TYPE)) == null) {
            overScroller.fling(i, i2, i3, i4, i5, i6, i7, i8);
        } else {
            SeslBaseReflector.invoke(overScroller, declaredMethod, Integer.valueOf(i3), Integer.valueOf(i4), Boolean.valueOf(z), Float.valueOf(f));
        }
    }

    public static void setSmoothScrollEnabled(OverScroller overScroller, boolean z) {
        Method declaredMethod;
        if (Build.VERSION.SDK_INT >= 26 && (declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "semSetSmoothScrollEnabled", Boolean.TYPE)) != null) {
            SeslBaseReflector.invoke(overScroller, declaredMethod, Boolean.valueOf(z));
        }
    }
}
