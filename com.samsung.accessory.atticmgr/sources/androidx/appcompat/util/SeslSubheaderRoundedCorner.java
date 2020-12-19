package androidx.appcompat.util;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class SeslSubheaderRoundedCorner extends SeslRoundedCorner {
    private static final String TAG = "SeslSubheaderRoundedCorner";

    public SeslSubheaderRoundedCorner(Context context) {
        super(context);
    }

    public void drawRoundedCorner(int i, int i2, int i3, int i4, Canvas canvas) {
        this.mRoundedCornerBounds.set(i, i2, i3, i4);
        drawRoundedCornerInternal(canvas);
    }

    @Override // androidx.appcompat.util.SeslRoundedCorner
    public void drawRoundedCorner(View view, Canvas canvas) {
        if (view.getTranslationY() != 0.0f) {
            this.mX = Math.round(view.getX());
            this.mY = Math.round(view.getY());
        } else {
            this.mX = view.getLeft();
            this.mY = view.getTop();
        }
        this.mRoundedCornerBounds.set(this.mX, this.mY, this.mX + view.getWidth(), this.mY + view.getHeight());
        drawRoundedCornerInternal(canvas);
    }

    private void drawRoundedCornerInternal(Canvas canvas) {
        int i = this.mRoundedCornerBounds.left;
        int i2 = this.mRoundedCornerBounds.right;
        int i3 = this.mRoundedCornerBounds.top;
        int i4 = this.mRoundedCornerBounds.bottom;
        if ((this.mRoundedCornerMode & 1) != 0) {
            this.mTopLeftRound.setBounds(i, i4, this.mRoundRadius + i, this.mRoundRadius + i4);
            this.mTopLeftRound.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 2) != 0) {
            this.mTopRightRound.setBounds(i2 - this.mRoundRadius, i4, i2, this.mRoundRadius + i4);
            this.mTopRightRound.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 4) != 0) {
            this.mBottomLeftRound.setBounds(i, i3 - this.mRoundRadius, this.mRoundRadius + i, i3);
            this.mBottomLeftRound.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 8) != 0) {
            this.mBottomRightRound.setBounds(i2 - this.mRoundRadius, i3 - this.mRoundRadius, i2, i3);
            this.mBottomRightRound.draw(canvas);
        }
    }
}
