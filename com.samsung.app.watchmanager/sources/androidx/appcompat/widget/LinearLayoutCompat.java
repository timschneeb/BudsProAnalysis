package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import b.a.j;
import b.e.g.C0113c;
import b.e.g.t;

public class LinearLayoutCompat extends ViewGroup {

    /* renamed from: a  reason: collision with root package name */
    private boolean f370a;

    /* renamed from: b  reason: collision with root package name */
    private int f371b;

    /* renamed from: c  reason: collision with root package name */
    private int f372c;

    /* renamed from: d  reason: collision with root package name */
    private int f373d;
    private int e;
    private int f;
    private float g;
    private boolean h;
    private int[] i;
    private int[] j;
    private Drawable k;
    private int l;
    private int m;
    private int n;
    private int o;

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        /* renamed from: a  reason: collision with root package name */
        public float f374a;

        /* renamed from: b  reason: collision with root package name */
        public int f375b;

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.f375b = -1;
            this.f374a = 0.0f;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f375b = -1;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, j.LinearLayoutCompat_Layout);
            this.f374a = obtainStyledAttributes.getFloat(j.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.f375b = obtainStyledAttributes.getInt(j.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.f375b = -1;
        }
    }

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f370a = true;
        this.f371b = -1;
        this.f372c = 0;
        this.e = 8388659;
        ia a2 = ia.a(context, attributeSet, j.LinearLayoutCompat, i2, 0);
        int d2 = a2.d(j.LinearLayoutCompat_android_orientation, -1);
        if (d2 >= 0) {
            setOrientation(d2);
        }
        int d3 = a2.d(j.LinearLayoutCompat_android_gravity, -1);
        if (d3 >= 0) {
            setGravity(d3);
        }
        boolean a3 = a2.a(j.LinearLayoutCompat_android_baselineAligned, true);
        if (!a3) {
            setBaselineAligned(a3);
        }
        this.g = a2.a(j.LinearLayoutCompat_android_weightSum, -1.0f);
        this.f371b = a2.d(j.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.h = a2.a(j.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(a2.b(j.LinearLayoutCompat_divider));
        this.n = a2.d(j.LinearLayoutCompat_showDividers, 0);
        this.o = a2.c(j.LinearLayoutCompat_dividerPadding, 0);
        a2.a();
    }

    private void a(View view, int i2, int i3, int i4, int i5) {
        view.layout(i2, i3, i4 + i2, i5 + i3);
    }

    private void c(int i2, int i3) {
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i4 = 0; i4 < i2; i4++) {
            View a2 = a(i4);
            if (a2.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) a2.getLayoutParams();
                if (((ViewGroup.MarginLayoutParams) layoutParams).height == -1) {
                    int i5 = ((ViewGroup.MarginLayoutParams) layoutParams).width;
                    ((ViewGroup.MarginLayoutParams) layoutParams).width = a2.getMeasuredWidth();
                    measureChildWithMargins(a2, i3, 0, makeMeasureSpec, 0);
                    ((ViewGroup.MarginLayoutParams) layoutParams).width = i5;
                }
            }
        }
    }

    private void d(int i2, int i3) {
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i4 = 0; i4 < i2; i4++) {
            View a2 = a(i4);
            if (a2.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) a2.getLayoutParams();
                if (((ViewGroup.MarginLayoutParams) layoutParams).width == -1) {
                    int i5 = ((ViewGroup.MarginLayoutParams) layoutParams).height;
                    ((ViewGroup.MarginLayoutParams) layoutParams).height = a2.getMeasuredHeight();
                    measureChildWithMargins(a2, makeMeasureSpec, 0, i3, 0);
                    ((ViewGroup.MarginLayoutParams) layoutParams).height = i5;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int a(View view) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int a(View view, int i2) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public View a(int i2) {
        return getChildAt(i2);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x03aa, code lost:
        if (r8 > 0) goto L_0x03b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x03b5, code lost:
        if (r8 < 0) goto L_0x03b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x03b7, code lost:
        r8 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x03b8, code lost:
        r14.measure(android.view.View.MeasureSpec.makeMeasureSpec(r8, r4), r2);
        r9 = android.view.View.combineMeasuredStates(r9, r14.getMeasuredState() & -16777216);
        r2 = r2;
        r4 = r3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0192  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01c6  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01d1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r39, int r40) {
        /*
        // Method dump skipped, instructions count: 1287
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.a(int, int):void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00e9  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00fd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r25, int r26, int r27, int r28) {
        /*
        // Method dump skipped, instructions count: 326
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.a(int, int, int, int):void");
    }

    /* access modifiers changed from: package-private */
    public void a(Canvas canvas) {
        int i2;
        int i3;
        int i4;
        int virtualChildCount = getVirtualChildCount();
        boolean a2 = wa.a(this);
        for (int i5 = 0; i5 < virtualChildCount; i5++) {
            View a3 = a(i5);
            if (!(a3 == null || a3.getVisibility() == 8 || !b(i5))) {
                LayoutParams layoutParams = (LayoutParams) a3.getLayoutParams();
                b(canvas, a2 ? a3.getRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin : (a3.getLeft() - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin) - this.l);
            }
        }
        if (b(virtualChildCount)) {
            View a4 = a(virtualChildCount - 1);
            if (a4 != null) {
                LayoutParams layoutParams2 = (LayoutParams) a4.getLayoutParams();
                if (a2) {
                    i4 = a4.getLeft();
                    i3 = ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin;
                } else {
                    i2 = a4.getRight() + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin;
                    b(canvas, i2);
                }
            } else if (a2) {
                i2 = getPaddingLeft();
                b(canvas, i2);
            } else {
                i4 = getWidth();
                i3 = getPaddingRight();
            }
            i2 = (i4 - i3) - this.l;
            b(canvas, i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Canvas canvas, int i2) {
        this.k.setBounds(getPaddingLeft() + this.o, i2, (getWidth() - getPaddingRight()) - this.o, this.m + i2);
        this.k.draw(canvas);
    }

    /* access modifiers changed from: package-private */
    public void a(View view, int i2, int i3, int i4, int i5, int i6) {
        measureChildWithMargins(view, i3, i4, i5, i6);
    }

    /* access modifiers changed from: package-private */
    public int b(View view) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x02dd, code lost:
        if (r15 > 0) goto L_0x02eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x02e8, code lost:
        if (r15 < 0) goto L_0x02ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x02ea, code lost:
        r15 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x02eb, code lost:
        r13.measure(r9, android.view.View.MeasureSpec.makeMeasureSpec(r15, r10));
        r1 = android.view.View.combineMeasuredStates(r1, r13.getMeasuredState() & -256);
     */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x0326  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(int r34, int r35) {
        /*
        // Method dump skipped, instructions count: 913
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.b(int, int):void");
    }

    /* access modifiers changed from: package-private */
    public void b(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int paddingLeft = getPaddingLeft();
        int i8 = i4 - i2;
        int paddingRight = i8 - getPaddingRight();
        int paddingRight2 = (i8 - paddingLeft) - getPaddingRight();
        int virtualChildCount = getVirtualChildCount();
        int i9 = this.e;
        int i10 = i9 & 112;
        int i11 = i9 & 8388615;
        int paddingTop = i10 != 16 ? i10 != 80 ? getPaddingTop() : ((getPaddingTop() + i5) - i3) - this.f : getPaddingTop() + (((i5 - i3) - this.f) / 2);
        int i12 = 0;
        while (i12 < virtualChildCount) {
            View a2 = a(i12);
            if (a2 == null) {
                paddingTop += c(i12);
            } else if (a2.getVisibility() != 8) {
                int measuredWidth = a2.getMeasuredWidth();
                int measuredHeight = a2.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams) a2.getLayoutParams();
                int i13 = layoutParams.f375b;
                if (i13 < 0) {
                    i13 = i11;
                }
                int a3 = C0113c.a(i13, t.i(this)) & 7;
                if (a3 == 1) {
                    i6 = ((paddingRight2 - measuredWidth) / 2) + paddingLeft + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                    i7 = i6 - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                } else if (a3 != 5) {
                    i7 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + paddingLeft;
                } else {
                    i6 = paddingRight - measuredWidth;
                    i7 = i6 - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                }
                if (b(i12)) {
                    paddingTop += this.m;
                }
                int i14 = paddingTop + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                a(a2, i7, i14 + a(a2), measuredWidth, measuredHeight);
                i12 += a(a2, i12);
                paddingTop = i14 + measuredHeight + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + b(a2);
            }
            i12++;
        }
    }

    /* access modifiers changed from: package-private */
    public void b(Canvas canvas) {
        int virtualChildCount = getVirtualChildCount();
        for (int i2 = 0; i2 < virtualChildCount; i2++) {
            View a2 = a(i2);
            if (!(a2 == null || a2.getVisibility() == 8 || !b(i2))) {
                a(canvas, (a2.getTop() - ((ViewGroup.MarginLayoutParams) ((LayoutParams) a2.getLayoutParams())).topMargin) - this.m);
            }
        }
        if (b(virtualChildCount)) {
            View a3 = a(virtualChildCount - 1);
            a(canvas, a3 == null ? (getHeight() - getPaddingBottom()) - this.m : a3.getBottom() + ((ViewGroup.MarginLayoutParams) ((LayoutParams) a3.getLayoutParams())).bottomMargin);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(Canvas canvas, int i2) {
        this.k.setBounds(i2, getPaddingTop() + this.o, this.l + i2, (getHeight() - getPaddingBottom()) - this.o);
        this.k.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public boolean b(int i2) {
        if (i2 == 0) {
            return (this.n & 1) != 0;
        }
        if (i2 == getChildCount()) {
            return (this.n & 4) != 0;
        }
        if ((this.n & 2) == 0) {
            return false;
        }
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            if (getChildAt(i3).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public int c(int i2) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        int i2 = this.f373d;
        if (i2 == 0) {
            return new LayoutParams(-2, -2);
        }
        if (i2 == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getBaseline() {
        int i2;
        if (this.f371b < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i3 = this.f371b;
        if (childCount > i3) {
            View childAt = getChildAt(i3);
            int baseline = childAt.getBaseline();
            if (baseline != -1) {
                int i4 = this.f372c;
                if (this.f373d == 1 && (i2 = this.e & 112) != 48) {
                    if (i2 == 16) {
                        i4 += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.f) / 2;
                    } else if (i2 == 80) {
                        i4 = ((getBottom() - getTop()) - getPaddingBottom()) - this.f;
                    }
                }
                return i4 + ((ViewGroup.MarginLayoutParams) ((LayoutParams) childAt.getLayoutParams())).topMargin + baseline;
            } else if (this.f371b == 0) {
                return -1;
            } else {
                throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
            }
        } else {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
    }

    public int getBaselineAlignedChildIndex() {
        return this.f371b;
    }

    public Drawable getDividerDrawable() {
        return this.k;
    }

    public int getDividerPadding() {
        return this.o;
    }

    public int getDividerWidth() {
        return this.l;
    }

    public int getGravity() {
        return this.e;
    }

    public int getOrientation() {
        return this.f373d;
    }

    public int getShowDividers() {
        return this.n;
    }

    /* access modifiers changed from: package-private */
    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.g;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.k != null) {
            if (this.f373d == 1) {
                b(canvas);
            } else {
                a(canvas);
            }
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(LinearLayoutCompat.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(LinearLayoutCompat.class.getName());
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        if (this.f373d == 1) {
            b(i2, i3, i4, i5);
        } else {
            a(i2, i3, i4, i5);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        if (this.f373d == 1) {
            b(i2, i3);
        } else {
            a(i2, i3);
        }
    }

    public void setBaselineAligned(boolean z) {
        this.f370a = z;
    }

    public void setBaselineAlignedChildIndex(int i2) {
        if (i2 < 0 || i2 >= getChildCount()) {
            throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
        }
        this.f371b = i2;
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable != this.k) {
            this.k = drawable;
            boolean z = false;
            if (drawable != null) {
                this.l = drawable.getIntrinsicWidth();
                this.m = drawable.getIntrinsicHeight();
            } else {
                this.l = 0;
                this.m = 0;
            }
            if (drawable == null) {
                z = true;
            }
            setWillNotDraw(z);
            requestLayout();
        }
    }

    public void setDividerPadding(int i2) {
        this.o = i2;
    }

    public void setGravity(int i2) {
        if (this.e != i2) {
            if ((8388615 & i2) == 0) {
                i2 |= 8388611;
            }
            if ((i2 & 112) == 0) {
                i2 |= 48;
            }
            this.e = i2;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int i2) {
        int i3 = i2 & 8388615;
        int i4 = this.e;
        if ((8388615 & i4) != i3) {
            this.e = i3 | (-8388616 & i4);
            requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.h = z;
    }

    public void setOrientation(int i2) {
        if (this.f373d != i2) {
            this.f373d = i2;
            requestLayout();
        }
    }

    public void setShowDividers(int i2) {
        if (i2 != this.n) {
            requestLayout();
        }
        this.n = i2;
    }

    public void setVerticalGravity(int i2) {
        int i3 = i2 & 112;
        int i4 = this.e;
        if ((i4 & 112) != i3) {
            this.e = i3 | (i4 & -113);
            requestLayout();
        }
    }

    public void setWeightSum(float f2) {
        this.g = Math.max(0.0f, f2);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
