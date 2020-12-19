package com.sothree.slidinguppanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import b.e.g.h;
import b.e.g.t;
import com.sothree.slidinguppanel.e;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SlidingUpPanelLayout extends ViewGroup {

    /* renamed from: a  reason: collision with root package name */
    private static final String f1990a = "SlidingUpPanelLayout";

    /* renamed from: b  reason: collision with root package name */
    private static c f1991b = c.COLLAPSED;

    /* renamed from: c  reason: collision with root package name */
    private static final int[] f1992c = {16842927};
    private boolean A;
    private float B;
    private float C;
    private float D;
    private boolean E;
    private List<b> F;
    private View.OnClickListener G;
    private final e H;
    private boolean I;
    private final Rect J;

    /* renamed from: d  reason: collision with root package name */
    private int f1993d;
    private int e;
    private final Paint f;
    private final Drawable g;
    private int h;
    private int i;
    private int j;
    private boolean k;
    private boolean l;
    private boolean m;
    private View n;
    private int o;
    private View p;
    private int q;
    private a r;
    private View s;
    private View t;
    private c u;
    private c v;
    private float w;
    private int x;
    private float y;
    private boolean z;

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        /* renamed from: a  reason: collision with root package name */
        private static final int[] f1994a = {16843137};

        /* renamed from: b  reason: collision with root package name */
        public float f1995b = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f1994a);
            if (obtainStyledAttributes != null) {
                this.f1995b = obtainStyledAttributes.getFloat(0, 0.0f);
            }
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    private class a extends e.a {
        private a() {
        }

        /* synthetic */ a(SlidingUpPanelLayout slidingUpPanelLayout, b bVar) {
            this();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:25:0x009b, code lost:
            if (r3.f1996a.w >= (r3.f1996a.y / 2.0f)) goto L_0x001e;
         */
        @Override // com.sothree.slidinguppanel.e.a
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(android.view.View r4, float r5, float r6) {
            /*
            // Method dump skipped, instructions count: 177
            */
            throw new UnsupportedOperationException("Method not decompiled: com.sothree.slidinguppanel.SlidingUpPanelLayout.a.a(android.view.View, float, float):void");
        }

        @Override // com.sothree.slidinguppanel.e.a
        public void a(View view, int i) {
            SlidingUpPanelLayout.this.b();
        }

        @Override // com.sothree.slidinguppanel.e.a
        public void a(View view, int i, int i2, int i3, int i4) {
            SlidingUpPanelLayout.this.b((SlidingUpPanelLayout) i2);
            SlidingUpPanelLayout.this.invalidate();
        }

        @Override // com.sothree.slidinguppanel.e.a
        public int b(View view) {
            return SlidingUpPanelLayout.this.x;
        }

        @Override // com.sothree.slidinguppanel.e.a
        public int b(View view, int i, int i2) {
            int a2 = SlidingUpPanelLayout.this.a((SlidingUpPanelLayout) 0.0f);
            int a3 = SlidingUpPanelLayout.this.a((SlidingUpPanelLayout) 1.0f);
            return SlidingUpPanelLayout.this.k ? Math.min(Math.max(i, a3), a2) : Math.min(Math.max(i, a2), a3);
        }

        @Override // com.sothree.slidinguppanel.e.a
        public boolean b(View view, int i) {
            return !SlidingUpPanelLayout.this.z && view == SlidingUpPanelLayout.this.s;
        }

        @Override // com.sothree.slidinguppanel.e.a
        public void c(int i) {
            SlidingUpPanelLayout slidingUpPanelLayout;
            c cVar;
            if (SlidingUpPanelLayout.this.H.d() == 0) {
                SlidingUpPanelLayout slidingUpPanelLayout2 = SlidingUpPanelLayout.this;
                slidingUpPanelLayout2.w = slidingUpPanelLayout2.a((SlidingUpPanelLayout) slidingUpPanelLayout2.s.getTop());
                SlidingUpPanelLayout.this.e();
                if (SlidingUpPanelLayout.this.w == 1.0f) {
                    SlidingUpPanelLayout.this.d();
                    slidingUpPanelLayout = SlidingUpPanelLayout.this;
                    cVar = c.EXPANDED;
                } else if (SlidingUpPanelLayout.this.w == 0.0f) {
                    slidingUpPanelLayout = SlidingUpPanelLayout.this;
                    cVar = c.COLLAPSED;
                } else if (SlidingUpPanelLayout.this.w < 0.0f) {
                    SlidingUpPanelLayout.this.setPanelStateInternal(c.HIDDEN);
                    SlidingUpPanelLayout.this.s.setVisibility(4);
                    return;
                } else {
                    SlidingUpPanelLayout.this.d();
                    slidingUpPanelLayout = SlidingUpPanelLayout.this;
                    cVar = c.ANCHORED;
                }
                slidingUpPanelLayout.setPanelStateInternal(cVar);
            }
        }
    }

    public interface b {
        void onPanelSlide(View view, float f);

        void onPanelStateChanged(View view, c cVar, c cVar2);
    }

    public enum c {
        EXPANDED,
        COLLAPSED,
        ANCHORED,
        HIDDEN,
        DRAGGING
    }

    public SlidingUpPanelLayout(Context context) {
        this(context, null);
    }

    public SlidingUpPanelLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingUpPanelLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Interpolator interpolator;
        Resources resources;
        int i3;
        this.f1993d = 400;
        this.e = -1728053248;
        this.f = new Paint();
        this.h = -1;
        this.i = -1;
        this.j = -1;
        this.l = false;
        this.m = true;
        this.o = -1;
        this.r = new a();
        c cVar = f1991b;
        this.u = cVar;
        this.v = cVar;
        this.y = 1.0f;
        this.E = false;
        this.F = new CopyOnWriteArrayList();
        this.I = true;
        this.J = new Rect();
        if (isInEditMode()) {
            this.g = null;
            this.H = null;
            return;
        }
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f1992c);
            if (obtainStyledAttributes != null) {
                setGravity(obtainStyledAttributes.getInt(0, 0));
            }
            obtainStyledAttributes.recycle();
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout);
            if (obtainStyledAttributes2 != null) {
                this.h = obtainStyledAttributes2.getDimensionPixelSize(com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout_umanoPanelHeight, -1);
                this.i = obtainStyledAttributes2.getDimensionPixelSize(com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout_umanoShadowHeight, -1);
                this.j = obtainStyledAttributes2.getDimensionPixelSize(com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout_umanoParallaxOffset, -1);
                this.f1993d = obtainStyledAttributes2.getInt(com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout_umanoFlingVelocity, 400);
                this.e = obtainStyledAttributes2.getColor(com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout_umanoFadeColor, -1728053248);
                this.o = obtainStyledAttributes2.getResourceId(com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout_umanoDragView, -1);
                this.q = obtainStyledAttributes2.getResourceId(com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout_umanoScrollableView, -1);
                this.l = obtainStyledAttributes2.getBoolean(com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout_umanoOverlay, false);
                this.m = obtainStyledAttributes2.getBoolean(com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout_umanoClipPanel, true);
                this.y = obtainStyledAttributes2.getFloat(com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout_umanoAnchorPoint, 1.0f);
                this.u = c.values()[obtainStyledAttributes2.getInt(com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout_umanoInitialState, f1991b.ordinal())];
                int resourceId = obtainStyledAttributes2.getResourceId(com.sothree.slidinguppanel.a.b.SlidingUpPanelLayout_umanoScrollInterpolator, -1);
                if (resourceId != -1) {
                    interpolator = AnimationUtils.loadInterpolator(context, resourceId);
                    obtainStyledAttributes2.recycle();
                }
            }
            interpolator = null;
            obtainStyledAttributes2.recycle();
        } else {
            interpolator = null;
        }
        float f2 = context.getResources().getDisplayMetrics().density;
        if (this.h == -1) {
            this.h = (int) ((68.0f * f2) + 0.5f);
        }
        if (this.i == -1) {
            this.i = (int) ((4.0f * f2) + 0.5f);
        }
        if (this.j == -1) {
            this.j = (int) (0.0f * f2);
        }
        if (this.i > 0) {
            if (this.k) {
                resources = getResources();
                i3 = com.sothree.slidinguppanel.a.a.above_shadow;
            } else {
                resources = getResources();
                i3 = com.sothree.slidinguppanel.a.a.below_shadow;
            }
            this.g = resources.getDrawable(i3);
        } else {
            this.g = null;
        }
        setWillNotDraw(false);
        this.H = e.a(this, 0.5f, interpolator, new a(this, null));
        this.H.a(((float) this.f1993d) * f2);
        this.A = true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float a(int i2) {
        int a2 = a(0.0f);
        return (this.k ? (float) (a2 - i2) : (float) (i2 - a2)) / ((float) this.x);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int a(float f2) {
        View view = this.s;
        int measuredHeight = view != null ? view.getMeasuredHeight() : 0;
        int i2 = (int) (f2 * ((float) this.x));
        return this.k ? ((getMeasuredHeight() - getPaddingBottom()) - this.h) - i2 : (getPaddingTop() - measuredHeight) + this.h + i2;
    }

    private boolean a(View view, int i2, int i3) {
        if (view == null) {
            return false;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        getLocationOnScreen(iArr2);
        int i4 = iArr2[0] + i2;
        int i5 = iArr2[1] + i3;
        return i4 >= iArr[0] && i4 < iArr[0] + view.getWidth() && i5 >= iArr[1] && i5 < iArr[1] + view.getHeight();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0060, code lost:
        if (((android.view.ViewGroup.MarginLayoutParams) r0).height == r1) goto L_0x006b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(int r6) {
        /*
        // Method dump skipped, instructions count: 115
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sothree.slidinguppanel.SlidingUpPanelLayout.b(int):void");
    }

    private static boolean b(View view) {
        Drawable background = view.getBackground();
        return background != null && background.getOpacity() == -1;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    @SuppressLint({"NewApi"})
    private void e() {
        if (this.j > 0) {
            t.b(this.t, (float) getCurrentParallaxOffset());
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setPanelStateInternal(c cVar) {
        c cVar2 = this.u;
        if (cVar2 != cVar) {
            this.u = cVar;
            a(this, cVar2, cVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(View view) {
        synchronized (this.F) {
            for (b bVar : this.F) {
                this.w = a(view.getTop());
                bVar.onPanelSlide(view, this.w);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(View view, c cVar, c cVar2) {
        synchronized (this.F) {
            for (b bVar : this.F) {
                bVar.onPanelStateChanged(view, cVar, cVar2);
            }
        }
        sendAccessibilityEvent(32);
    }

    public void a(b bVar) {
        synchronized (this.F) {
            this.F.add(bVar);
        }
    }

    public boolean a() {
        return (!this.A || this.s == null || this.u == c.HIDDEN) ? false : true;
    }

    /* access modifiers changed from: package-private */
    public boolean a(float f2, int i2) {
        if (isEnabled() && this.s != null && this.A) {
            int a2 = a(f2);
            e eVar = this.H;
            View view = this.s;
            if (eVar.b(view, view.getLeft(), a2)) {
                b();
                t.y(this);
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 4) {
                childAt.setVisibility(0);
            }
        }
    }

    public void b(b bVar) {
        synchronized (this.F) {
            this.F.remove(bVar);
        }
    }

    /* access modifiers changed from: protected */
    public void c() {
        a(0.0f, 0);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public void computeScroll() {
        e eVar = this.H;
        if (eVar != null && eVar.a(true)) {
            if (!isEnabled()) {
                this.H.a();
            } else {
                t.y(this);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void d() {
        int i2;
        int i3;
        int i4;
        int i5;
        if (getChildCount() != 0) {
            int paddingLeft = getPaddingLeft();
            int width = getWidth() - getPaddingRight();
            int paddingTop = getPaddingTop();
            int height = getHeight() - getPaddingBottom();
            View view = this.s;
            int i6 = 0;
            if (view == null || !b(view)) {
                i5 = 0;
                i4 = 0;
                i3 = 0;
                i2 = 0;
            } else {
                i5 = this.s.getLeft();
                i4 = this.s.getRight();
                i3 = this.s.getTop();
                i2 = this.s.getBottom();
            }
            View childAt = getChildAt(0);
            int max = Math.max(paddingLeft, childAt.getLeft());
            int max2 = Math.max(paddingTop, childAt.getTop());
            int min = Math.min(width, childAt.getRight());
            int min2 = Math.min(height, childAt.getBottom());
            if (max >= i5 && max2 >= i3 && min <= i4 && min2 <= i2) {
                i6 = 4;
            }
            childAt.setVisibility(i6);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int b2 = h.b(motionEvent);
        if (!isEnabled() || !a() || (this.z && b2 != 0)) {
            this.H.a();
            return super.dispatchTouchEvent(motionEvent);
        }
        float y2 = motionEvent.getY();
        if (b2 == 0) {
            this.E = false;
            this.B = y2;
        } else if (b2 == 2) {
            float f2 = y2 - this.B;
            this.B = y2;
            if (!a(this.p, (int) this.C, (int) this.D)) {
                return super.dispatchTouchEvent(motionEvent);
            }
            int i2 = -1;
            if (((float) (this.k ? 1 : -1)) * f2 <= 0.0f) {
                if (this.k) {
                    i2 = 1;
                }
                if (f2 * ((float) i2) < 0.0f) {
                    if (this.w < 1.0f) {
                        this.E = false;
                        return onTouchEvent(motionEvent);
                    }
                    if (!this.E && this.H.e()) {
                        this.H.b();
                        motionEvent.setAction(0);
                    }
                    this.E = true;
                    return super.dispatchTouchEvent(motionEvent);
                }
            } else if (this.r.a(this.p, this.k) > 0) {
                this.E = true;
                return super.dispatchTouchEvent(motionEvent);
            } else {
                if (this.E) {
                    MotionEvent obtain = MotionEvent.obtain(motionEvent);
                    obtain.setAction(3);
                    super.dispatchTouchEvent(obtain);
                    obtain.recycle();
                    motionEvent.setAction(0);
                }
                this.E = false;
                return onTouchEvent(motionEvent);
            }
        } else if (b2 == 1 && this.E) {
            this.H.a(0);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void draw(Canvas canvas) {
        View view;
        int i2;
        int i3;
        super.draw(canvas);
        if (this.g != null && (view = this.s) != null) {
            int right = view.getRight();
            if (this.k) {
                i3 = this.s.getTop() - this.i;
                i2 = this.s.getTop();
            } else {
                i3 = this.s.getBottom();
                i2 = this.s.getBottom() + this.i;
            }
            this.g.setBounds(this.s.getLeft(), i3, right, i2);
            this.g.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j2) {
        boolean z2;
        int save = canvas.save();
        View view2 = this.s;
        if (view2 == null || view2 == view) {
            z2 = super.drawChild(canvas, view, j2);
        } else {
            canvas.getClipBounds(this.J);
            if (!this.l) {
                if (this.k) {
                    Rect rect = this.J;
                    rect.bottom = Math.min(rect.bottom, this.s.getTop());
                } else {
                    Rect rect2 = this.J;
                    rect2.top = Math.max(rect2.top, this.s.getBottom());
                }
            }
            if (this.m) {
                canvas.clipRect(this.J);
            }
            z2 = super.drawChild(canvas, view, j2);
            int i2 = this.e;
            if (i2 != 0) {
                float f2 = this.w;
                if (f2 > 0.0f) {
                    this.f.setColor((i2 & 16777215) | (((int) (((float) ((-16777216 & i2) >>> 24)) * f2)) << 24));
                    canvas.drawRect(this.J, this.f);
                }
            }
        }
        canvas.restoreToCount(save);
        return z2;
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public float getAnchorPoint() {
        return this.y;
    }

    public int getCoveredFadeColor() {
        return this.e;
    }

    public int getCurrentParallaxOffset() {
        int max = (int) (((float) this.j) * Math.max(this.w, 0.0f));
        return this.k ? -max : max;
    }

    public int getMinFlingVelocity() {
        return this.f1993d;
    }

    public int getPanelHeight() {
        return this.h;
    }

    public c getPanelState() {
        return this.u;
    }

    public int getShadowHeight() {
        return this.i;
    }

    public float getSlideOffset() {
        return this.w;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.I = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.I = true;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        int i2 = this.o;
        if (i2 != -1) {
            setDragView(findViewById(i2));
        }
        int i3 = this.q;
        if (i3 != -1) {
            setScrollableView(findViewById(i3));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0038, code lost:
        if (r0 != 3) goto L_0x009d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r9) {
        /*
        // Method dump skipped, instructions count: 170
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sothree.slidinguppanel.SlidingUpPanelLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        float f2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        if (this.I) {
            int i6 = b.f2001a[this.u.ordinal()];
            if (i6 == 1) {
                f2 = 1.0f;
            } else if (i6 == 2) {
                f2 = this.y;
            } else if (i6 != 3) {
                this.w = 0.0f;
            } else {
                f2 = a(a(0.0f) + (this.k ? this.h : -this.h));
            }
            this.w = f2;
        }
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() != 8 || (i7 != 0 && !this.I)) {
                int measuredHeight = childAt.getMeasuredHeight();
                int a2 = childAt == this.s ? a(this.w) : paddingTop;
                if (!this.k && childAt == this.t && !this.l) {
                    a2 = a(this.w) + this.s.getMeasuredHeight();
                }
                int i8 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + paddingLeft;
                childAt.layout(i8, a2, childAt.getMeasuredWidth() + i8, measuredHeight + a2);
            }
        }
        if (this.I) {
            d();
        }
        e();
        this.I = false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size2 = View.MeasureSpec.getSize(i3);
        if (mode != 1073741824 && mode != Integer.MIN_VALUE) {
            throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
        } else if (mode2 == 1073741824 || mode2 == Integer.MIN_VALUE) {
            int childCount = getChildCount();
            if (childCount == 2) {
                this.t = getChildAt(0);
                this.s = getChildAt(1);
                if (this.n == null) {
                    setDragView(this.s);
                }
                if (this.s.getVisibility() != 0) {
                    this.u = c.HIDDEN;
                }
                int paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
                int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
                for (int i8 = 0; i8 < childCount; i8++) {
                    View childAt = getChildAt(i8);
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    if (childAt.getVisibility() != 8 || i8 != 0) {
                        if (childAt == this.t) {
                            i5 = (this.l || this.u == c.HIDDEN) ? paddingTop : paddingTop - this.h;
                            i4 = paddingLeft - (((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
                        } else {
                            i5 = childAt == this.s ? paddingTop - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin : paddingTop;
                            i4 = paddingLeft;
                        }
                        if (((ViewGroup.MarginLayoutParams) layoutParams).width == -2) {
                            i6 = View.MeasureSpec.makeMeasureSpec(i4, LinearLayoutManager.INVALID_OFFSET);
                        } else {
                            if (((ViewGroup.MarginLayoutParams) layoutParams).width != -1) {
                                i4 = ((ViewGroup.MarginLayoutParams) layoutParams).width;
                            }
                            i6 = View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
                        }
                        if (((ViewGroup.MarginLayoutParams) layoutParams).height == -2) {
                            i7 = View.MeasureSpec.makeMeasureSpec(i5, LinearLayoutManager.INVALID_OFFSET);
                        } else {
                            float f2 = layoutParams.f1995b;
                            if (f2 > 0.0f && f2 < 1.0f) {
                                i5 = (int) (((float) i5) * f2);
                            } else if (((ViewGroup.MarginLayoutParams) layoutParams).height != -1) {
                                i5 = ((ViewGroup.MarginLayoutParams) layoutParams).height;
                            }
                            i7 = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
                        }
                        childAt.measure(i6, i7);
                        View view = this.s;
                        if (childAt == view) {
                            this.x = view.getMeasuredHeight() - this.h;
                        }
                    }
                }
                setMeasuredDimension(size, size2);
                return;
            }
            throw new IllegalStateException("Sliding up panel layout must have exactly 2 children!");
        } else {
            throw new IllegalStateException("Height must have an exact value or MATCH_PARENT");
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.u = (c) bundle.getSerializable("sliding_state");
            c cVar = this.u;
            if (cVar == null) {
                cVar = f1991b;
            }
            this.u = cVar;
            parcelable = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        c cVar = this.u;
        if (cVar == c.DRAGGING) {
            cVar = this.v;
        }
        bundle.putSerializable("sliding_state", cVar);
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i3 != i5) {
            this.I = true;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.d(f1990a + "prom", " isTouchEnabled :" + a());
        int b2 = h.b(motionEvent);
        if (b2 == 1 || b2 == 3) {
            setTouchEnabled(true);
            setPanelState(getSlideOffset() >= 0.4f ? c.EXPANDED : c.COLLAPSED);
        }
        if (!isEnabled() || !a()) {
            return super.onTouchEvent(motionEvent);
        }
        try {
            this.H.a(motionEvent);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public void setAnchorPoint(float f2) {
        if (f2 > 0.0f && f2 <= 1.0f) {
            this.y = f2;
            this.I = true;
            requestLayout();
        }
    }

    public void setClipPanel(boolean z2) {
        this.m = z2;
    }

    public void setCoveredFadeColor(int i2) {
        this.e = i2;
        requestLayout();
    }

    public void setDragView(int i2) {
        this.o = i2;
        setDragView(findViewById(i2));
    }

    public void setDragView(View view) {
        View view2 = this.n;
        if (view2 != null) {
            view2.setOnClickListener(null);
        }
        this.n = view;
    }

    public void setFadeOnClickListener(View.OnClickListener onClickListener) {
        this.G = onClickListener;
    }

    public void setGravity(int i2) {
        if (i2 == 48 || i2 == 80) {
            this.k = i2 == 80;
            if (!this.I) {
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("gravity must be set to either top or bottom");
    }

    public void setMinFlingVelocity(int i2) {
        this.f1993d = i2;
    }

    public void setOverlayed(boolean z2) {
        this.l = z2;
    }

    public void setPanelHeight(int i2) {
        if (getPanelHeight() != i2) {
            this.h = i2;
            if (!this.I) {
                requestLayout();
            }
            if (getPanelState() == c.COLLAPSED) {
                c();
                invalidate();
            }
        }
    }

    public void setPanelState(c cVar) {
        c cVar2;
        float f2;
        if (cVar == null || cVar == c.DRAGGING) {
            throw new IllegalArgumentException("Panel state cannot be null or DRAGGING.");
        } else if (!isEnabled()) {
        } else {
            if ((this.I || this.s != null) && cVar != (cVar2 = this.u) && cVar2 != c.DRAGGING) {
                if (this.I) {
                    setPanelStateInternal(cVar);
                    return;
                }
                if (cVar2 == c.HIDDEN) {
                    this.s.setVisibility(0);
                    requestLayout();
                }
                int i2 = b.f2001a[cVar.ordinal()];
                if (i2 == 1) {
                    f2 = 1.0f;
                } else if (i2 == 2) {
                    f2 = this.y;
                } else if (i2 == 3) {
                    f2 = a(a(0.0f) + (this.k ? this.h : -this.h));
                } else if (i2 == 4) {
                    a(0.0f, 0);
                    return;
                } else {
                    return;
                }
                a(f2, 0);
            }
        }
    }

    public void setParallaxOffset(int i2) {
        this.j = i2;
        if (!this.I) {
            requestLayout();
        }
    }

    public void setScrollableView(View view) {
        this.p = view;
    }

    public void setScrollableViewHelper(a aVar) {
        this.r = aVar;
    }

    public void setShadowHeight(int i2) {
        this.i = i2;
        if (!this.I) {
            invalidate();
        }
    }

    public void setTouchEnabled(boolean z2) {
        this.A = z2;
    }
}
