package androidx.appcompat.widget;

import android.view.View;

public class TooltipCompat {
    public static void setTooltipText(View view, CharSequence charSequence) {
        TooltipCompatHandler.setTooltipText(view, charSequence);
    }

    public static void seslSetTooltipForceBelow(boolean z) {
        TooltipCompatHandler.seslSetTooltipForceBelow(z);
    }

    public static void seslSetTooltipForceActionBarPosX(boolean z) {
        TooltipCompatHandler.seslSetTooltipForceActionBarPosX(z);
    }

    static void setTooltipPosition(int i, int i2, int i3) {
        TooltipCompatHandler.seslSetTooltipPosition(i, i2, i3);
    }

    public static void setTooltipNull(boolean z) {
        TooltipCompatHandler.seslSetTooltipNull(z);
    }

    private TooltipCompat() {
    }
}
