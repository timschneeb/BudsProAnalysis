package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.recyclerview.widget.RecyclerView;

public class t extends RecyclerView.r {
    protected final LinearInterpolator i = new LinearInterpolator();
    protected final DecelerateInterpolator j = new DecelerateInterpolator();
    protected PointF k;
    private final float l;
    protected int m = 0;
    protected int n = 0;

    public t(Context context) {
        this.l = a(context.getResources().getDisplayMetrics());
    }

    private int b(int i2, int i3) {
        int i4 = i2 - i3;
        if (i2 * i4 <= 0) {
            return 0;
        }
        return i4;
    }

    /* access modifiers changed from: protected */
    public float a(DisplayMetrics displayMetrics) {
        return 25.0f / ((float) displayMetrics.densityDpi);
    }

    public int a(int i2, int i3, int i4, int i5, int i6) {
        if (i6 == -1) {
            return i4 - i2;
        }
        if (i6 == 0) {
            int i7 = i4 - i2;
            if (i7 > 0) {
                return i7;
            }
            int i8 = i5 - i3;
            if (i8 < 0) {
                return i8;
            }
            return 0;
        } else if (i6 == 1) {
            return i5 - i3;
        } else {
            throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
        }
    }

    public int a(View view, int i2) {
        RecyclerView.i b2 = b();
        if (b2 == null || !b2.canScrollHorizontally()) {
            return 0;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        return a(b2.getDecoratedLeft(view) - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, b2.getDecoratedRight(view) + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, b2.getPaddingLeft(), b2.getWidth() - b2.getPaddingRight(), i2);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.recyclerview.widget.RecyclerView.r
    public void a(int i2, int i3, RecyclerView.s sVar, RecyclerView.r.a aVar) {
        if (a() == 0) {
            h();
            return;
        }
        this.m = b(this.m, i2);
        this.n = b(this.n, i3);
        if (this.m == 0 && this.n == 0) {
            a(aVar);
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.recyclerview.widget.RecyclerView.r
    public void a(View view, RecyclerView.s sVar, RecyclerView.r.a aVar) {
        int a2 = a(view, i());
        int b2 = b(view, j());
        int d2 = d((int) Math.sqrt((double) ((a2 * a2) + (b2 * b2))));
        if (d2 > 0) {
            aVar.a(-a2, -b2, d2, this.j);
        }
    }

    /* access modifiers changed from: protected */
    public void a(RecyclerView.r.a aVar) {
        PointF a2 = a(c());
        if (a2 == null || (a2.x == 0.0f && a2.y == 0.0f)) {
            aVar.a(c());
            h();
            return;
        }
        a(a2);
        this.k = a2;
        this.m = (int) (a2.x * 10000.0f);
        this.n = (int) (a2.y * 10000.0f);
        aVar.a((int) (((float) this.m) * 1.2f), (int) (((float) this.n) * 1.2f), (int) (((float) e(10000)) * 1.2f), this.i);
    }

    public int b(View view, int i2) {
        RecyclerView.i b2 = b();
        if (b2 == null || !b2.canScrollVertically()) {
            return 0;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        return a(b2.getDecoratedTop(view) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, b2.getDecoratedBottom(view) + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, b2.getPaddingTop(), b2.getHeight() - b2.getPaddingBottom(), i2);
    }

    /* access modifiers changed from: protected */
    public int d(int i2) {
        double e = (double) e(i2);
        Double.isNaN(e);
        return (int) Math.ceil(e / 0.3356d);
    }

    /* access modifiers changed from: protected */
    public int e(int i2) {
        return (int) Math.ceil((double) (((float) Math.abs(i2)) * this.l));
    }

    /* access modifiers changed from: protected */
    @Override // androidx.recyclerview.widget.RecyclerView.r
    public void f() {
    }

    /* access modifiers changed from: protected */
    @Override // androidx.recyclerview.widget.RecyclerView.r
    public void g() {
        this.n = 0;
        this.m = 0;
        this.k = null;
    }

    /* access modifiers changed from: protected */
    public int i() {
        PointF pointF = this.k;
        if (pointF != null) {
            float f = pointF.x;
            if (f != 0.0f) {
                return f > 0.0f ? 1 : -1;
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int j() {
        PointF pointF = this.k;
        if (pointF != null) {
            float f = pointF.y;
            if (f != 0.0f) {
                return f > 0.0f ? 1 : -1;
            }
        }
        return 0;
    }
}
