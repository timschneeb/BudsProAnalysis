package androidx.appcompat.widget;

import android.os.Build;
import android.view.View;

public class qa {
    public static void a(View view, CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setTooltipText(charSequence);
        } else {
            ta.a(view, charSequence);
        }
    }
}
