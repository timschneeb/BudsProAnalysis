package androidx.reflect.text;

import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Method;

public class SeslTextUtilsReflector {
    private static final Class<?> mClass = TextUtils.class;

    private SeslTextUtilsReflector() {
    }

    public static char[] semGetPrefixCharForSpan(TextPaint textPaint, CharSequence charSequence, char[] cArr) {
        Method method;
        if (Build.VERSION.SDK_INT >= 29) {
            method = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_semGetPrefixCharForSpan", TextPaint.class, CharSequence.class, char[].class);
        } else if (Build.VERSION.SDK_INT >= 24) {
            method = SeslBaseReflector.getMethod(mClass, "semGetPrefixCharForSpan", TextPaint.class, CharSequence.class, char[].class);
        } else {
            method = SeslBaseReflector.getMethod(mClass, "getPrefixCharForIndian", TextPaint.class, CharSequence.class, char[].class);
        }
        if (method == null) {
            return new char[0];
        }
        Object invoke = SeslBaseReflector.invoke(null, method, textPaint, charSequence, cArr);
        if (invoke instanceof char[]) {
            return (char[]) invoke;
        }
        return null;
    }
}
