package androidx.viewpager.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.core.content.a;

public class PagerTabStrip extends PagerTitleStrip {
    private boolean A;
    private int B;
    private boolean C;
    private float D;
    private float E;
    private int F;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private final Paint w;
    private final Rect x;
    private int y;
    private boolean z;

    public PagerTabStrip(Context context) {
        this(context, null);
    }

    public PagerTabStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.w = new Paint();
        this.x = new Rect();
        this.y = 255;
        this.z = false;
        this.A = false;
        this.q = this.p;
        this.w.setColor(this.q);
        float f = context.getResources().getDisplayMetrics().density;
        this.r = (int) ((3.0f * f) + 0.5f);
        this.s = (int) ((6.0f * f) + 0.5f);
        this.t = (int) (64.0f * f);
        this.v = (int) ((16.0f * f) + 0.5f);
        this.B = (int) ((1.0f * f) + 0.5f);
        this.u = (int) ((f * 32.0f) + 0.5f);
        this.F = ViewConfiguration.get(context).getScaledTouchSlop();
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        setTextSpacing(getTextSpacing());
        setWillNotDraw(false);
        this.f1180d.setFocusable(true);
        this.f1180d.setOnClickListener(new b(this));
        this.f.setFocusable(true);
        this.f.setOnClickListener(new c(this));
        if (getBackground() == null) {
            this.z = true;
        }
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.viewpager.widget.PagerTitleStrip
    public void a(int i, float f, boolean z2) {
        Rect rect = this.x;
        int height = getHeight();
        int left = this.e.getLeft() - this.v;
        int right = this.e.getRight() + this.v;
        int i2 = height - this.r;
        rect.set(left, i2, right, height);
        super.a(i, f, z2);
        this.y = (int) (Math.abs(f - 0.5f) * 2.0f * 255.0f);
        rect.union(this.e.getLeft() - this.v, i2, this.e.getRight() + this.v, height);
        invalidate(rect);
    }

    public boolean getDrawFullUnderline() {
        return this.z;
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.viewpager.widget.PagerTitleStrip
    public int getMinHeight() {
        return Math.max(super.getMinHeight(), this.u);
    }

    public int getTabIndicatorColor() {
        return this.q;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int left = this.e.getLeft() - this.v;
        int right = this.e.getRight() + this.v;
        this.w.setColor((this.y << 24) | (this.q & 16777215));
        float f = (float) height;
        canvas.drawRect((float) left, (float) (height - this.r), (float) right, f, this.w);
        if (this.z) {
            this.w.setColor(-16777216 | (this.q & 16777215));
            canvas.drawRect((float) getPaddingLeft(), (float) (height - this.B), (float) (getWidth() - getPaddingRight()), f, this.w);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        ViewPager viewPager;
        int currentItem;
        int action = motionEvent.getAction();
        if (action != 0 && this.C) {
            return false;
        }
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        if (action == 0) {
            this.D = x2;
            this.E = y2;
            this.C = false;
        } else if (action == 1) {
            if (x2 < ((float) (this.e.getLeft() - this.v))) {
                viewPager = this.f1179c;
                currentItem = viewPager.getCurrentItem() - 1;
            } else if (x2 > ((float) (this.e.getRight() + this.v))) {
                viewPager = this.f1179c;
                currentItem = viewPager.getCurrentItem() + 1;
            }
            viewPager.setCurrentItem(currentItem);
        } else if (action == 2 && (Math.abs(x2 - this.D) > ((float) this.F) || Math.abs(y2 - this.E) > ((float) this.F))) {
            this.C = true;
        }
        return true;
    }

    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
        if (!this.A) {
            this.z = (i & -16777216) == 0;
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (!this.A) {
            this.z = drawable == null;
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (!this.A) {
            this.z = i == 0;
        }
    }

    public void setDrawFullUnderline(boolean z2) {
        this.z = z2;
        this.A = true;
        invalidate();
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        int i5 = this.s;
        if (i4 < i5) {
            i4 = i5;
        }
        super.setPadding(i, i2, i3, i4);
    }

    public void setTabIndicatorColor(int i) {
        this.q = i;
        this.w.setColor(this.q);
        invalidate();
    }

    public void setTabIndicatorColorResource(int i) {
        setTabIndicatorColor(a.a(getContext(), i));
    }

    @Override // androidx.viewpager.widget.PagerTitleStrip
    public void setTextSpacing(int i) {
        int i2 = this.t;
        if (i < i2) {
            i = i2;
        }
        super.setTextSpacing(i);
    }
}
