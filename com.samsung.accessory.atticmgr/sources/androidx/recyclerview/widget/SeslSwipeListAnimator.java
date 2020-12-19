package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

public class SeslSwipeListAnimator {
    private static final String TAG = "SeslSwipeListAnimator";
    private final int DEFAULT_DRAWABLE_PADDING = 10;
    private final int DEFAULT_LEFT_COLOR = Color.parseColor("#6ebd52");
    private final int DEFAULT_RIGHT_COLOR = Color.parseColor("#56c0e5");
    private final int DEFAULT_TEXT_COLOR = Color.parseColor("#ffffff");
    private final int DEFAULT_TEXT_SIZE = 15;
    private final int DIRECTION_LTR = 0;
    private final int DIRECTION_RTL = 1;
    private Paint mBgLeftToRight = null;
    private Paint mBgRightToLeft = null;
    private Context mContext;
    private BitmapDrawable mDrawSwipeBitmapDrawable = null;
    private RecyclerView mRecyclerView;
    private Bitmap mSwipeBitmap = null;
    private SwipeConfiguration mSwipeConfiguration;
    private Rect mSwipeRect = null;
    private Paint mTextPaint = null;

    public SeslSwipeListAnimator(RecyclerView recyclerView, Context context) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
    }

    public void setSwipeConfiguration(SwipeConfiguration swipeConfiguration) {
        this.mSwipeConfiguration = swipeConfiguration;
        if (this.mSwipeConfiguration.textLeftToRight == null) {
            this.mSwipeConfiguration.textLeftToRight = " ";
        }
        if (this.mSwipeConfiguration.textRightToLeft == null) {
            this.mSwipeConfiguration.textRightToLeft = " ";
        }
        if (this.mSwipeConfiguration.colorLeftToRight == this.mSwipeConfiguration.UNSET_VALUE) {
            this.mSwipeConfiguration.colorLeftToRight = this.DEFAULT_LEFT_COLOR;
        }
        if (this.mSwipeConfiguration.colorRightToLeft == this.mSwipeConfiguration.UNSET_VALUE) {
            this.mSwipeConfiguration.colorRightToLeft = this.DEFAULT_RIGHT_COLOR;
        }
        if (this.mSwipeConfiguration.textColor == this.mSwipeConfiguration.UNSET_VALUE) {
            this.mSwipeConfiguration.textColor = this.DEFAULT_TEXT_COLOR;
        }
        if (this.mSwipeConfiguration.textSize == this.mSwipeConfiguration.UNSET_VALUE) {
            this.mSwipeConfiguration.textSize = 15;
        }
        if (this.mSwipeConfiguration.drawablePadding == this.mSwipeConfiguration.UNSET_VALUE) {
            this.mSwipeConfiguration.drawablePadding = 10;
        }
        this.mBgLeftToRight = initPaintWithAlphaAntiAliasing(this.mSwipeConfiguration.colorLeftToRight);
        this.mBgRightToLeft = initPaintWithAlphaAntiAliasing(this.mSwipeConfiguration.colorRightToLeft);
        this.mTextPaint = initPaintWithAlphaAntiAliasing(this.mSwipeConfiguration.textColor);
        this.mTextPaint.setTextSize((float) convertDipToPixels(this.mContext, this.mSwipeConfiguration.textSize));
    }

    private Paint initPaintWithAlphaAntiAliasing(int i) {
        Paint paint = new Paint();
        paint.setColor(i);
        paint.setAntiAlias(true);
        return paint;
    }

    private int convertDipToPixels(Context context, int i) {
        return Math.round(context.getResources().getDisplayMetrics().density * ((float) i));
    }

    public void doMoveAction(Canvas canvas, View view, float f, boolean z) {
        Log.i(TAG, "doMoveAction: viewForeground = " + view + " deltaX = " + f + ", isCurrentlyActive = " + z);
        if (f != 0.0f || z) {
            Log.i(TAG, "doMoveAction: #1 drawRectToBitmapCanvas");
            drawRectToBitmapCanvas(view, f, f / ((float) view.getWidth()));
            view.setTranslationX(f);
            view.setAlpha(1.0f - (Math.abs(f) / ((float) view.getWidth())));
            this.mDrawSwipeBitmapDrawable = getBitmapDrawableToSwipeBitmap();
            BitmapDrawable bitmapDrawable = this.mDrawSwipeBitmapDrawable;
            if (bitmapDrawable != null) {
                this.mRecyclerView.invalidate(bitmapDrawable.getBounds());
                Log.i(TAG, "doMoveAction: draw");
                this.mDrawSwipeBitmapDrawable.draw(canvas);
                return;
            }
            return;
        }
        Log.i(TAG, "doMoveAction: #2 reutrn");
        clearSwipeAnimation(view);
    }

    private int calculateTopOfList(View view) {
        int top = view.getTop();
        View view2 = (View) view.getParent();
        return (view2 == null || (view2 instanceof RecyclerView)) ? top : top + calculateTopOfList(view2);
    }

    private Canvas drawRectToBitmapCanvas(View view, float f, float f2) {
        Rect rect;
        int i;
        Rect rect2;
        int[] iArr = new int[2];
        this.mRecyclerView.getLocationInWindow(iArr);
        int[] iArr2 = new int[2];
        view.setTranslationX(0.0f);
        view.getLocationInWindow(iArr2);
        iArr2[0] = iArr2[0] - iArr[0];
        int calculateTopOfList = calculateTopOfList(view);
        int width = view.getWidth();
        int height = view.getHeight();
        this.mSwipeRect = new Rect(iArr2[0] + view.getPaddingLeft(), calculateTopOfList, (iArr2[0] + view.getWidth()) - view.getPaddingRight(), calculateTopOfList + height);
        if (this.mSwipeBitmap == null) {
            this.mSwipeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(this.mSwipeBitmap);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        float abs = Math.abs(f);
        float width2 = (abs / ((float) view.getWidth())) * 255.0f;
        if (f2 > 0.0f) {
            Drawable drawable = this.mSwipeConfiguration.drawableLeftToRight;
            if (drawable != null) {
                Rect bounds = drawable.getBounds();
                int width3 = bounds.width();
                int height2 = bounds.height();
                Log.i(TAG, "#1 draw LtoR, d = " + drawable + ", d.getBounds()=" + drawable.getBounds());
                Rect rect3 = new Rect(this.mSwipeConfiguration.drawablePadding, 0, width3 + this.mSwipeConfiguration.drawablePadding, height2);
                rect3.offset(0, (height - height2) / 2);
                rect2 = rect3;
            } else {
                Log.i(TAG, "#2 draw LtoR, d = null");
                rect2 = new Rect(0, 0, 0, 0);
            }
            int i2 = (int) f;
            drawRectInto(canvas, new Rect(0, 0, i2, height), rect2, drawable, this.mBgLeftToRight, 255, this.mSwipeConfiguration.textLeftToRight, (float) this.mSwipeConfiguration.textSize, 0);
            drawRectInto(canvas, new Rect(i2, 0, width, height), rect2, drawable, this.mBgLeftToRight, (int) width2, this.mSwipeConfiguration.textLeftToRight, (float) this.mSwipeConfiguration.textSize, 0);
        } else if (f2 < 0.0f) {
            Drawable drawable2 = this.mSwipeConfiguration.drawableRightToLeft;
            if (drawable2 != null) {
                Rect bounds2 = drawable2.getBounds();
                int width4 = bounds2.width();
                int height3 = bounds2.height();
                int i3 = width - this.mSwipeConfiguration.drawablePadding;
                Log.i(TAG, "#3 draw RtoL, d = " + drawable2 + ", d.getBounds()=" + drawable2.getBounds());
                i = 0;
                Rect rect4 = new Rect(i3 - width4, 0, i3, height3);
                rect4.offset(0, (height - height3) / 2);
                rect = rect4;
            } else {
                i = 0;
                Log.i(TAG, "#4 draw RtoL, d = null");
                rect = new Rect(width, 0, width, 0);
            }
            int i4 = width - ((int) abs);
            drawRectInto(canvas, new Rect(i4, i, width, height), rect, drawable2, this.mBgRightToLeft, 255, this.mSwipeConfiguration.textRightToLeft, (float) this.mSwipeConfiguration.textSize, 1);
            drawRectInto(canvas, new Rect(0, 0, i4, height), rect, drawable2, this.mBgRightToLeft, (int) width2, this.mSwipeConfiguration.textRightToLeft, (float) this.mSwipeConfiguration.textSize, 1);
        }
        return canvas;
    }

    private void drawRectInto(Canvas canvas, Rect rect, Rect rect2, Drawable drawable, Paint paint, int i, String str, float f, int i2) {
        canvas.save();
        paint.setAlpha(i);
        this.mTextPaint.setAlpha(i);
        canvas.clipRect(rect);
        canvas.drawRect(rect, paint);
        if (drawable != null) {
            drawable.setBounds(rect2);
            drawable.draw(canvas);
        }
        drawSwipeText(canvas, this.mTextPaint, str, i2, rect2);
        canvas.restore();
    }

    private void drawSwipeText(Canvas canvas, Paint paint, String str, int i, Rect rect) {
        int i2;
        Rect rect2 = new Rect();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(str, 0, str.length(), rect2);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float height = ((((float) canvas.getHeight()) / 2.0f) + (Math.abs(fontMetrics.top - fontMetrics.bottom) / 2.0f)) - fontMetrics.bottom;
        if (i == 0) {
            i2 = rect.right + this.mSwipeConfiguration.drawablePadding;
        } else {
            i2 = (rect.left - this.mSwipeConfiguration.drawablePadding) - rect2.right;
        }
        canvas.drawText(str, (float) i2, height, paint);
    }

    private BitmapDrawable getBitmapDrawableToSwipeBitmap() {
        if (this.mSwipeBitmap == null) {
            return null;
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mRecyclerView.getResources(), this.mSwipeBitmap);
        bitmapDrawable.setBounds(this.mSwipeRect);
        return bitmapDrawable;
    }

    private void drawTextToCenter(Canvas canvas, Paint paint, String str) {
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        Rect rect = new Rect();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(str, 0, str.length(), rect);
        canvas.drawText(str, ((((float) width) / 2.0f) - (((float) rect.width()) / 2.0f)) - ((float) rect.left), ((((float) height) / 2.0f) + (((float) rect.height()) / 2.0f)) - ((float) rect.bottom), paint);
    }

    public void clearSwipeAnimation(View view) {
        Log.i(TAG, "clearSwipeAnimation: view = " + view + " mDrawSwipeBitmapDrawable = " + this.mDrawSwipeBitmapDrawable);
        BitmapDrawable bitmapDrawable = this.mDrawSwipeBitmapDrawable;
        if (bitmapDrawable != null) {
            bitmapDrawable.getBitmap().recycle();
            this.mDrawSwipeBitmapDrawable = null;
            this.mSwipeBitmap = null;
        }
        if (view != null) {
            Log.i(TAG, "clearSwipeAnimation: view.getTranslationX() = " + view.getTranslationX());
            if (view.getTranslationX() != 0.0f) {
                Log.i(TAG, "clearSwipeAnimation: **** set view.setTranslationX(0f) ****");
                view.setTranslationX(0.0f);
            }
        }
    }

    public void onSwiped(View view) {
        Log.i(TAG, "onSwiped");
        clearSwipeAnimation(view);
        view.setTranslationX(0.0f);
        view.setAlpha(1.0f);
    }

    public static class SwipeConfiguration {
        public int UNSET_VALUE = -1;
        public int colorLeftToRight;
        public int colorRightToLeft;
        public Drawable drawableLeftToRight;
        public int drawablePadding;
        public Drawable drawableRightToLeft;
        public int textColor;
        public String textLeftToRight;
        public String textRightToLeft;
        public int textSize;

        public SwipeConfiguration() {
            int i = this.UNSET_VALUE;
            this.colorLeftToRight = i;
            this.colorRightToLeft = i;
            this.drawablePadding = i;
            this.textSize = i;
            this.textColor = i;
        }
    }
}
