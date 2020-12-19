package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatSeekBar;
import com.samsung.accessory.atticmgr.R;

public class TwoTickMarkSeekBar extends AppCompatSeekBar {
    Drawable mTickMark;

    public TwoTickMarkSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTickMark = context.getDrawable(R.drawable.circle_tick_mark);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.AppCompatSeekBar
    public synchronized void onDraw(Canvas canvas) {
        drawTickMark(canvas);
        super.onDraw(canvas);
    }

    private void drawTickMark(Canvas canvas) {
        Drawable drawable = this.mTickMark;
        if (drawable != null) {
            int max = getMax();
            int i = 1;
            if (max > 1) {
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                int i2 = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                if (intrinsicHeight >= 0) {
                    i = intrinsicHeight / 2;
                }
                drawable.setBounds(-i2, -i, i2, i);
                float width = ((float) ((getWidth() - getPaddingLeft()) - getPaddingRight())) / ((float) max);
                int save = canvas.save();
                canvas.translate((float) getPaddingLeft(), (float) (getHeight() / 2));
                for (int i3 = 0; i3 <= max; i3++) {
                    if (i3 == 0 || i3 == max || i3 == max / 2) {
                        drawable.draw(canvas);
                    }
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(save);
            }
        }
    }
}
