package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import androidx.appcompat.R;
import androidx.appcompat.util.SeslMisc;
import androidx.core.content.res.ResourcesCompat;

public class SeslDropDownItemTextView extends SeslCheckedTextView {
    private static final String TAG = SeslDropDownItemTextView.class.getSimpleName();

    public SeslDropDownItemTextView(Context context) {
        this(context, null);
    }

    public SeslDropDownItemTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public SeslDropDownItemTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // androidx.appcompat.widget.SeslCheckedTextView
    public void setChecked(boolean z) {
        Context context;
        int i;
        super.setChecked(z);
        setTypeface(Typeface.create("sec-roboto-light", z ? 1 : 0));
        if (z && (context = getContext()) != null && getCurrentTextColor() == -65281) {
            Log.w(TAG, "text color reload!");
            if (SeslMisc.isLightTheme(context)) {
                i = R.color.sesl_spinner_dropdown_text_color_light;
            } else {
                i = R.color.sesl_spinner_dropdown_text_color_dark;
            }
            ColorStateList colorStateList = ResourcesCompat.getColorStateList(context.getResources(), i, context.getTheme());
            if (colorStateList != null) {
                setTextColor(colorStateList);
            } else {
                Log.w(TAG, "Didn't set SeslDropDownItemTextView text color!!");
            }
        }
    }
}
