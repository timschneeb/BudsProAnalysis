package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import b.e.g.a.b;
import java.util.Arrays;

public class GridLayoutManager extends LinearLayoutManager {

    /* renamed from: a  reason: collision with root package name */
    boolean f941a = false;

    /* renamed from: b  reason: collision with root package name */
    int f942b = -1;

    /* renamed from: c  reason: collision with root package name */
    int[] f943c;

    /* renamed from: d  reason: collision with root package name */
    View[] f944d;
    final SparseIntArray e = new SparseIntArray();
    final SparseIntArray f = new SparseIntArray();
    b g = new a();
    final Rect h = new Rect();

    public static class LayoutParams extends RecyclerView.LayoutParams {
        int e = -1;
        int f = 0;

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public int e() {
            return this.e;
        }

        public int f() {
            return this.f;
        }
    }

    public static final class a extends b {
        @Override // androidx.recyclerview.widget.GridLayoutManager.b
        public int a(int i) {
            return 1;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.b
        public int c(int i, int i2) {
            return i % i2;
        }
    }

    public static abstract class b {

        /* renamed from: a  reason: collision with root package name */
        final SparseIntArray f945a = new SparseIntArray();

        /* renamed from: b  reason: collision with root package name */
        private boolean f946b = false;

        public abstract int a(int i);

        /* access modifiers changed from: package-private */
        public int a(int i, int i2) {
            if (!this.f946b) {
                return c(i, i2);
            }
            int i3 = this.f945a.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int c2 = c(i, i2);
            this.f945a.put(i, c2);
            return c2;
        }

        public void a() {
            this.f945a.clear();
        }

        public int b(int i, int i2) {
            int a2 = a(i);
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                int a3 = a(i5);
                i3 += a3;
                if (i3 == i2) {
                    i4++;
                    i3 = 0;
                } else if (i3 > i2) {
                    i4++;
                    i3 = a3;
                }
            }
            return i3 + a2 > i2 ? i4 + 1 : i4;
        }

        public abstract int c(int i, int i2);
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        a(RecyclerView.i.getProperties(context, attributeSet, i, i2).f997b);
    }

    private int a(RecyclerView.o oVar, RecyclerView.s sVar, int i) {
        if (!sVar.d()) {
            return this.g.b(i, this.f942b);
        }
        int a2 = oVar.a(i);
        if (a2 != -1) {
            return this.g.b(a2, this.f942b);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + i);
        return 0;
    }

    private void a() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            int a2 = layoutParams.a();
            this.e.put(a2, layoutParams.f());
            this.f.put(a2, layoutParams.e());
        }
    }

    private void a(float f2, int i) {
        b(Math.max(Math.round(f2 * ((float) this.f942b)), i));
    }

    private void a(View view, int i, int i2, boolean z) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (z ? shouldReMeasureChild(view, i, i2, layoutParams) : shouldMeasureChild(view, i, i2, layoutParams)) {
            view.measure(i, i2);
        }
    }

    private void a(View view, int i, boolean z) {
        int i2;
        int i3;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = layoutParams.f983b;
        int i4 = rect.top + rect.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        int i5 = rect.left + rect.right + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        int a2 = a(layoutParams.e, layoutParams.f);
        if (this.mOrientation == 1) {
            i2 = RecyclerView.i.getChildMeasureSpec(a2, i, i5, ((ViewGroup.MarginLayoutParams) layoutParams).width, false);
            i3 = RecyclerView.i.getChildMeasureSpec(this.mOrientationHelper.g(), getHeightMode(), i4, ((ViewGroup.MarginLayoutParams) layoutParams).height, true);
        } else {
            int childMeasureSpec = RecyclerView.i.getChildMeasureSpec(a2, i, i4, ((ViewGroup.MarginLayoutParams) layoutParams).height, false);
            int childMeasureSpec2 = RecyclerView.i.getChildMeasureSpec(this.mOrientationHelper.g(), getWidthMode(), i5, ((ViewGroup.MarginLayoutParams) layoutParams).width, true);
            i3 = childMeasureSpec;
            i2 = childMeasureSpec2;
        }
        a(view, i2, i3, z);
    }

    private void a(RecyclerView.o oVar, RecyclerView.s sVar, int i, int i2, boolean z) {
        int i3;
        int i4;
        int i5 = 0;
        int i6 = -1;
        if (z) {
            i6 = i;
            i4 = 0;
            i3 = 1;
        } else {
            i4 = i - 1;
            i3 = -1;
        }
        while (i4 != i6) {
            View view = this.f944d[i4];
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.f = c(oVar, sVar, getPosition(view));
            layoutParams.e = i5;
            i5 += layoutParams.f;
            i4 += i3;
        }
    }

    private void a(RecyclerView.o oVar, RecyclerView.s sVar, LinearLayoutManager.a aVar, int i) {
        boolean z = i == 1;
        int b2 = b(oVar, sVar, aVar.f955b);
        if (z) {
            while (b2 > 0) {
                int i2 = aVar.f955b;
                if (i2 > 0) {
                    aVar.f955b = i2 - 1;
                    b2 = b(oVar, sVar, aVar.f955b);
                } else {
                    return;
                }
            }
            return;
        }
        int a2 = sVar.a() - 1;
        int i3 = aVar.f955b;
        while (i3 < a2) {
            int i4 = i3 + 1;
            int b3 = b(oVar, sVar, i4);
            if (b3 <= b2) {
                break;
            }
            i3 = i4;
            b2 = b3;
        }
        aVar.f955b = i3;
    }

    static int[] a(int[] iArr, int i, int i2) {
        int i3;
        if (!(iArr != null && iArr.length == i + 1 && iArr[iArr.length - 1] == i2)) {
            iArr = new int[(i + 1)];
        }
        int i4 = 0;
        iArr[0] = 0;
        int i5 = i2 / i;
        int i6 = i2 % i;
        int i7 = 0;
        for (int i8 = 1; i8 <= i; i8++) {
            i4 += i6;
            if (i4 <= 0 || i - i4 >= i6) {
                i3 = i5;
            } else {
                i3 = i5 + 1;
                i4 -= i;
            }
            i7 += i3;
            iArr[i8] = i7;
        }
        return iArr;
    }

    private int b(RecyclerView.o oVar, RecyclerView.s sVar, int i) {
        if (!sVar.d()) {
            return this.g.a(i, this.f942b);
        }
        int i2 = this.f.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int a2 = oVar.a(i);
        if (a2 != -1) {
            return this.g.a(a2, this.f942b);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 0;
    }

    private void b() {
        this.e.clear();
        this.f.clear();
    }

    private void b(int i) {
        this.f943c = a(this.f943c, this.f942b, i);
    }

    private int c(RecyclerView.o oVar, RecyclerView.s sVar, int i) {
        if (!sVar.d()) {
            return this.g.a(i);
        }
        int i2 = this.e.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int a2 = oVar.a(i);
        if (a2 != -1) {
            return this.g.a(a2);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 1;
    }

    private void c() {
        View[] viewArr = this.f944d;
        if (viewArr == null || viewArr.length != this.f942b) {
            this.f944d = new View[this.f942b];
        }
    }

    private void d() {
        int i;
        int i2;
        if (getOrientation() == 1) {
            i2 = getWidth() - getPaddingRight();
            i = getPaddingLeft();
        } else {
            i2 = getHeight() - getPaddingBottom();
            i = getPaddingTop();
        }
        b(i2 - i);
    }

    /* access modifiers changed from: package-private */
    public int a(int i, int i2) {
        if (this.mOrientation != 1 || !isLayoutRTL()) {
            int[] iArr = this.f943c;
            return iArr[i2 + i] - iArr[i];
        }
        int[] iArr2 = this.f943c;
        int i3 = this.f942b;
        return iArr2[i3 - i] - iArr2[(i3 - i) - i2];
    }

    public void a(int i) {
        if (i != this.f942b) {
            this.f941a = true;
            if (i >= 1) {
                this.f942b = i;
                this.g.a();
                requestLayout();
                return;
            }
            throw new IllegalArgumentException("Span count should be at least 1. Provided " + i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void collectPrefetchPositionsForLayoutState(RecyclerView.s sVar, LinearLayoutManager.c cVar, RecyclerView.i.a aVar) {
        int i = this.f942b;
        for (int i2 = 0; i2 < this.f942b && cVar.a(sVar) && i > 0; i2++) {
            int i3 = cVar.f965d;
            aVar.a(i3, Math.max(0, cVar.g));
            i -= this.g.a(i3);
            cVar.f965d += cVar.e;
        }
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public View findReferenceChild(RecyclerView.o oVar, RecyclerView.s sVar, int i, int i2, int i3) {
        ensureLayoutState();
        int f2 = this.mOrientationHelper.f();
        int b2 = this.mOrientationHelper.b();
        int i4 = i2 > i ? 1 : -1;
        View view = null;
        View view2 = null;
        while (i != i2) {
            View childAt = getChildAt(i);
            int position = getPosition(childAt);
            if (position >= 0 && position < i3 && b(oVar, sVar, position) == 0) {
                if (((RecyclerView.LayoutParams) childAt.getLayoutParams()).c()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else if (this.mOrientationHelper.d(childAt) < b2 && this.mOrientationHelper.a(childAt) >= f2) {
                    return childAt;
                } else {
                    if (view == null) {
                        view = childAt;
                    }
                }
            }
            i += i4;
        }
        return view != null ? view : view2;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return this.mOrientation == 0 ? new LayoutParams(-2, -1) : new LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int getColumnCountForAccessibility(RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.mOrientation == 1) {
            return this.f942b;
        }
        if (sVar.a() < 1) {
            return 0;
        }
        return a(oVar, sVar, sVar.a() - 1) + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int getRowCountForAccessibility(RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.mOrientation == 0) {
            return this.f942b;
        }
        if (sVar.a() < 1) {
            return 0;
        }
        return a(oVar, sVar, sVar.a() - 1) + 1;
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void layoutChunk(RecyclerView.o oVar, RecyclerView.s sVar, LinearLayoutManager.c cVar, LinearLayoutManager.b bVar) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z;
        View a2;
        int e2 = this.mOrientationHelper.e();
        boolean z2 = e2 != 1073741824;
        int i9 = getChildCount() > 0 ? this.f943c[this.f942b] : 0;
        if (z2) {
            d();
        }
        boolean z3 = cVar.e == 1;
        int i10 = this.f942b;
        if (!z3) {
            i10 = b(oVar, sVar, cVar.f965d) + c(oVar, sVar, cVar.f965d);
        }
        int i11 = 0;
        int i12 = 0;
        while (i12 < this.f942b && cVar.a(sVar) && i10 > 0) {
            int i13 = cVar.f965d;
            int c2 = c(oVar, sVar, i13);
            if (c2 <= this.f942b) {
                i10 -= c2;
                if (i10 < 0 || (a2 = cVar.a(oVar)) == null) {
                    break;
                }
                i11 += c2;
                this.f944d[i12] = a2;
                i12++;
            } else {
                throw new IllegalArgumentException("Item at position " + i13 + " requires " + c2 + " spans but GridLayoutManager has only " + this.f942b + " spans.");
            }
        }
        if (i12 == 0) {
            bVar.f959b = true;
            return;
        }
        float f2 = 0.0f;
        a(oVar, sVar, i12, i11, z3);
        int i14 = 0;
        for (int i15 = 0; i15 < i12; i15++) {
            View view = this.f944d[i15];
            if (cVar.k != null) {
                z = false;
                if (z3) {
                    addDisappearingView(view);
                } else {
                    addDisappearingView(view, 0);
                }
            } else if (z3) {
                addView(view);
                z = false;
            } else {
                z = false;
                addView(view, 0);
            }
            calculateItemDecorationsForChild(view, this.h);
            a(view, e2, z);
            int b2 = this.mOrientationHelper.b(view);
            if (b2 > i14) {
                i14 = b2;
            }
            float c3 = (((float) this.mOrientationHelper.c(view)) * 1.0f) / ((float) ((LayoutParams) view.getLayoutParams()).f);
            if (c3 > f2) {
                f2 = c3;
            }
        }
        if (z2) {
            a(f2, i9);
            i14 = 0;
            for (int i16 = 0; i16 < i12; i16++) {
                View view2 = this.f944d[i16];
                a(view2, 1073741824, true);
                int b3 = this.mOrientationHelper.b(view2);
                if (b3 > i14) {
                    i14 = b3;
                }
            }
        }
        for (int i17 = 0; i17 < i12; i17++) {
            View view3 = this.f944d[i17];
            if (this.mOrientationHelper.b(view3) != i14) {
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                Rect rect = layoutParams.f983b;
                int i18 = rect.top + rect.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                int i19 = rect.left + rect.right + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                int a3 = a(layoutParams.e, layoutParams.f);
                if (this.mOrientation == 1) {
                    i8 = RecyclerView.i.getChildMeasureSpec(a3, 1073741824, i19, ((ViewGroup.MarginLayoutParams) layoutParams).width, false);
                    i7 = View.MeasureSpec.makeMeasureSpec(i14 - i18, 1073741824);
                } else {
                    int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i14 - i19, 1073741824);
                    i7 = RecyclerView.i.getChildMeasureSpec(a3, 1073741824, i18, ((ViewGroup.MarginLayoutParams) layoutParams).height, false);
                    i8 = makeMeasureSpec;
                }
                a(view3, i8, i7, true);
            }
        }
        int i20 = 0;
        bVar.f958a = i14;
        if (this.mOrientation == 1) {
            if (cVar.f == -1) {
                int i21 = cVar.f963b;
                i = i21;
                i2 = i21 - i14;
            } else {
                int i22 = cVar.f963b;
                i2 = i22;
                i = i14 + i22;
            }
            i4 = 0;
            i3 = 0;
        } else if (cVar.f == -1) {
            int i23 = cVar.f963b;
            int i24 = i23 - i14;
            i2 = 0;
            i = 0;
            i3 = i23;
            i4 = i24;
        } else {
            i4 = cVar.f963b;
            i3 = i14 + i4;
            i2 = 0;
            i = 0;
        }
        while (i20 < i12) {
            View view4 = this.f944d[i20];
            LayoutParams layoutParams2 = (LayoutParams) view4.getLayoutParams();
            if (this.mOrientation != 1) {
                i2 = getPaddingTop() + this.f943c[layoutParams2.e];
                i = this.mOrientationHelper.c(view4) + i2;
            } else if (isLayoutRTL()) {
                int paddingLeft = getPaddingLeft() + this.f943c[this.f942b - layoutParams2.e];
                i5 = paddingLeft;
                i6 = paddingLeft - this.mOrientationHelper.c(view4);
                layoutDecoratedWithMargins(view4, i6, i2, i5, i);
                if (!layoutParams2.c() || layoutParams2.b()) {
                    bVar.f960c = true;
                }
                bVar.f961d |= view4.hasFocusable();
                i20++;
                i4 = i6;
                i2 = i2;
                i3 = i5;
                i = i;
            } else {
                i4 = getPaddingLeft() + this.f943c[layoutParams2.e];
                i3 = this.mOrientationHelper.c(view4) + i4;
            }
            i6 = i4;
            i5 = i3;
            layoutDecoratedWithMargins(view4, i6, i2, i5, i);
            if (!layoutParams2.c()) {
            }
            bVar.f960c = true;
            bVar.f961d |= view4.hasFocusable();
            i20++;
            i4 = i6;
            i2 = i2;
            i3 = i5;
            i = i;
        }
        Arrays.fill(this.f944d, (Object) null);
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void onAnchorReady(RecyclerView.o oVar, RecyclerView.s sVar, LinearLayoutManager.a aVar, int i) {
        super.onAnchorReady(oVar, sVar, aVar, i);
        d();
        if (sVar.a() > 0 && !sVar.d()) {
            a(oVar, sVar, aVar, i);
        }
        c();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00d7, code lost:
        if (r13 == (r2 > r8)) goto L_0x00cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00f7, code lost:
        if (r13 == r11) goto L_0x00b7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0105  */
    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View onFocusSearchFailed(android.view.View r24, int r25, androidx.recyclerview.widget.RecyclerView.o r26, androidx.recyclerview.widget.RecyclerView.s r27) {
        /*
        // Method dump skipped, instructions count: 335
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.GridLayoutManager.onFocusSearchFailed(android.view.View, int, androidx.recyclerview.widget.RecyclerView$o, androidx.recyclerview.widget.RecyclerView$s):android.view.View");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.o oVar, RecyclerView.s sVar, View view, b.e.g.a.b bVar) {
        boolean z;
        boolean z2;
        int i;
        int i2;
        int i3;
        int i4;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem(view, bVar);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int a2 = a(oVar, sVar, layoutParams2.a());
        if (this.mOrientation == 0) {
            int e2 = layoutParams2.e();
            i3 = layoutParams2.f();
            i = 1;
            z2 = this.f942b > 1 && layoutParams2.f() == this.f942b;
            z = false;
            i4 = e2;
            i2 = a2;
        } else {
            i3 = 1;
            i2 = layoutParams2.e();
            i = layoutParams2.f();
            z2 = this.f942b > 1 && layoutParams2.f() == this.f942b;
            z = false;
            i4 = a2;
        }
        bVar.b(b.c.a(i4, i3, i2, i, z2, z));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        this.g.a();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onItemsChanged(RecyclerView recyclerView) {
        this.g.a();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        this.g.a();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        this.g.a();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
        this.g.a();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public void onLayoutChildren(RecyclerView.o oVar, RecyclerView.s sVar) {
        if (sVar.d()) {
            a();
        }
        super.onLayoutChildren(oVar, sVar);
        b();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public void onLayoutCompleted(RecyclerView.s sVar) {
        super.onLayoutCompleted(sVar);
        this.f941a = false;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public int scrollHorizontallyBy(int i, RecyclerView.o oVar, RecyclerView.s sVar) {
        d();
        c();
        return super.scrollHorizontallyBy(i, oVar, sVar);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public int scrollVerticallyBy(int i, RecyclerView.o oVar, RecyclerView.s sVar) {
        d();
        c();
        return super.scrollVerticallyBy(i, oVar, sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void setMeasuredDimension(Rect rect, int i, int i2) {
        int i3;
        int i4;
        if (this.f943c == null) {
            super.setMeasuredDimension(rect, i, i2);
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation == 1) {
            i4 = RecyclerView.i.chooseSize(i2, rect.height() + paddingTop, getMinimumHeight());
            int[] iArr = this.f943c;
            i3 = RecyclerView.i.chooseSize(i, iArr[iArr.length - 1] + paddingLeft, getMinimumWidth());
        } else {
            i3 = RecyclerView.i.chooseSize(i, rect.width() + paddingLeft, getMinimumWidth());
            int[] iArr2 = this.f943c;
            i4 = RecyclerView.i.chooseSize(i2, iArr2[iArr2.length - 1] + paddingTop, getMinimumHeight());
        }
        setMeasuredDimension(i3, i4);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void setStackFromEnd(boolean z) {
        if (!z) {
            super.setStackFromEnd(false);
            return;
        }
        throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && !this.f941a;
    }
}
