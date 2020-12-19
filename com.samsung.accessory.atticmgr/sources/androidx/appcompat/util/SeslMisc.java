package androidx.appcompat.util;

import android.content.Context;
import android.util.TypedValue;
import androidx.appcompat.R;

public class SeslMisc {
    public static boolean isLightTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true);
        if (typedValue.data != 0) {
            return true;
        }
        return false;
    }
}
