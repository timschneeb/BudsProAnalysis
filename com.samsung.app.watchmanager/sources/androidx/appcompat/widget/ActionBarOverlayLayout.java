package androidx.appcompat.widget;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.widget.OverScroller;
import androidx.appcompat.view.menu.v;
import androidx.recyclerview.widget.LinearLayoutManager;
import b.a.f;
import b.e.g.m;
import b.e.g.n;
import b.e.g.t;

public class ActionBarOverlayLayout extends ViewGroup implements C, m {

    /* renamed from: a  reason: collision with root package name */
    static final int[] f284a = {b.a.a.actionBarSize, 16842841};
    private final Runnable A;
    private final n B;

    /* renamed from: b  reason: collision with root package name */
    private int f285b;

    /* renamed from: c  reason: collision with root package name */
    private int f286c;

    /* renamed from: d  reason: collision with root package name */
    private ContentFrameLayout f287d;
    ActionBarContainer e;
    private D f;
    private Drawable g;
    private boolean h;
    private boolean i;
    private boolean j;
    private boolean k;
    boolean l;
    private int m;
    private int n;
    private final Rect o;
    private final Rect p;
    private final Rect q;
    private final Rect r;
    private final Rect s;
    private final Rect t;
    private final Rect u;
    private a v;
    private OverScroller w;
    ViewPropertyAnimator x;
    final AnimatorListenerAdapter y;
    private final Runnable z;

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public interface a {
        void a();

        void a(int i);

        void a(boolean z);

        void b();

        void c();

        void d();
    }

    public ActionBarOverlayLayout(Context context) {
        this(context, null);
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f286c = 0;
        this.o = new Rect();
        this.p = new Rect();
        this.q = new Rect();
        this.r = new Rect();
        this.s = new Rect();
        this.t = new Rect();
        this.u = new Rect();
        this.y = new C0059d(this);
        this.z = new RunnableC0060e(this);
        this.A = new RunnableC0061f(this);
        a(context);
        this.B = new n(this);
    }

    private D a(View view) {
        if (view instanceof D) {
            return (D) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        throw new IllegalStateException("Can't make a decor toolbar out of " + view.getClass().getSimpleName());
    }

    private void a(Context context) {
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(f284a);
        boolean z2 = false;
        this.f285b = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.g = obtainStyledAttributes.getDrawable(1);
        setWillNotDraw(this.g == null);
        obtainStyledAttributes.recycle();
        if (context.getApplicationInfo().targetSdkVersion < 19) {
            z2 = true;
        }
        this.h = z2;
        this.w = new OverScroller(context);
    }

    private boolean a(float f2, float f3) {
        this.w.fling(0, 0, 0, (int) f3, 0, 0, LinearLayoutManager.INVALID_OFFSET, Integer.MAX_VALUE);
        return this.w.getFinalY() > this.e.getHeight();
    }

    private boolean a(View view, Rect rect, boolean z2, boolean z3, boolean z4, boolean z5) {
        boolean z6;
        int i2;
        int i3;
        int i4;
        int i5;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!z2 || ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin == (i5 = rect.left)) {
            z6 = false;
        } else {
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = i5;
            z6 = true;
        }
        if (z3 && ((ViewGroup.MarginLayoutParams) layoutParams).topMargin != (i4 = rect.top)) {
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = i4;
            z6 = true;
        }
        if (z5 && ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin != (i3 = rect.right)) {
            ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = i3;
            z6 = true;
        }
        if (!z4 || ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin == (i2 = rect.bottom)) {
            return z6;
        }
        ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = i2;
        return true;
    }

    private void j() {
        g();
        this.A.run();
    }

    private void k() {
        g();
        postDelayed(this.A, 600);
    }

    private void l() {
        g();
        postDelayed(this.z, 600);
    }

    private void m() {
        g();
        this.z.run();
    }

    @Override // androidx.appcompat.widget.C
    public void a(int i2) {
        i();
        if (i2 == 2) {
            this.f.l();
        } else if (i2 == 5) {
            this.f.m();
        } else if (i2 == 109) {
            setOverlayMode(true);
        }
    }

    @Override // androidx.appcompat.widget.C
    public boolean a() {
        i();
        return this.f.a();
    }

    @Override // androidx.appcompat.widget.C
    public boolean b() {
        i();
        return this.f.b();
    }

    @Override // androidx.appcompat.widget.C
    public boolean c() {
        i();
        return this.f.c();
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // androidx.appcompat.widget.C
    public boolean d() {
        i();
        return this.f.d();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.g != null && !this.h) {
            int bottom = this.e.getVisibility() == 0 ? (int) (((float) this.e.getBottom()) + this.e.getTranslationY() + 0.5f) : 0;
            this.g.setBounds(0, bottom, getWidth(), this.g.getIntrinsicHeight() + bottom);
            this.g.draw(canvas);
        }
    }

    @Override // androidx.appcompat.widget.C
    public boolean e() {
        i();
        return this.f.e();
    }

    @Override // androidx.appcompat.widget.C
    public void f() {
        i();
        this.f.f();
    }

    /* access modifiers changed from: protected */
    public boolean fitSystemWindows(Rect rect) {
        i();
        int p2 = t.p(this) & 256;
        boolean a2 = a(this.e, rect, true, true, false, true);
        this.r.set(rect);
        wa.a(this, this.r, this.o);
        if (!this.s.equals(this.r)) {
            this.s.set(this.r);
            a2 = true;
        }
        if (!this.p.equals(this.o)) {
            this.p.set(this.o);
            a2 = true;
        }
        if (a2) {
            requestLayout();
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void g() {
        removeCallbacks(this.z);
        removeCallbacks(this.A);
        ViewPropertyAnimator viewPropertyAnimator = this.x;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    /* access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public int getActionBarHideOffset() {
        ActionBarContainer actionBarContainer = this.e;
        if (actionBarContainer != null) {
            return -((int) actionBarContainer.getTranslationY());
        }
        return 0;
    }

    public int getNestedScrollAxes() {
        return this.B.a();
    }

    public CharSequence getTitle() {
        i();
        return this.f.getTitle();
    }

    public boolean h() {
        return this.i;
    }

    /* access modifiers changed from: package-private */
    public void i() {
        if (this.f287d == null) {
            this.f287d = (ContentFrameLayout) findViewById(f.action_bar_activity_content);
            this.e = (ActionBarContainer) findViewById(f.action_bar_container);
            this.f = a(findViewById(f.action_bar));
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        a(getContext());
        t.z(this);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        g();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        getPaddingRight();
        int paddingTop = getPaddingTop();
        getPaddingBottom();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i7 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + paddingLeft;
                int i8 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + paddingTop;
                childAt.layout(i7, i8, measuredWidth + i7, measuredHeight + i8);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4;
        i();
        measureChildWithMargins(this.e, i2, 0, i3, 0);
        LayoutParams layoutParams = (LayoutParams) this.e.getLayoutParams();
        int max = Math.max(0, this.e.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
        int max2 = Math.max(0, this.e.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        int combineMeasuredStates = View.combineMeasuredStates(0, this.e.getMeasuredState());
        boolean z2 = (t.p(this) & 256) != 0;
        if (z2) {
            i4 = this.f285b;
            if (this.j && this.e.getTabContainer() != null) {
                i4 += this.f285b;
            }
        } else {
            i4 = this.e.getVisibility() != 8 ? this.e.getMeasuredHeight() : 0;
        }
        this.q.set(this.o);
        this.t.set(this.r);
        Rect rect = (this.i || z2) ? this.t : this.q;
        rect.top += i4;
        rect.bottom += 0;
        a(this.f287d, this.q, true, true, true, true);
        if (!this.u.equals(this.t)) {
            this.u.set(this.t);
            this.f287d.a(this.t);
        }
        measureChildWithMargins(this.f287d, i2, 0, i3, 0);
        LayoutParams layoutParams2 = (LayoutParams) this.f287d.getLayoutParams();
        int max3 = Math.max(max, this.f287d.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin);
        int max4 = Math.max(max2, this.f287d.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin);
        int combineMeasuredStates2 = View.combineMeasuredStates(combineMeasuredStates, this.f287d.getMeasuredState());
        setMeasuredDimension(View.resolveSizeAndState(Math.max(max3 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i2, combineMeasuredStates2), View.resolveSizeAndState(Math.max(max4 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i3, combineMeasuredStates2 << 16));
    }

    @Override // b.e.g.m
    public boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        if (!this.k || !z2) {
            return false;
        }
        if (a(f2, f3)) {
            j();
        } else {
            m();
        }
        this.l = true;
        return true;
    }

    @Override // b.e.g.m
    public boolean onNestedPreFling(View view, float f2, float f3) {
        return false;
    }

    @Override // b.e.g.m
    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
    }

    @Override // b.e.g.m
    public void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        this.m += i3;
        setActionBarHideOffset(this.m);
    }

    @Override // b.e.g.m
    public void onNestedScrollAccepted(View view, View view2, int i2) {
        this.B.a(view, view2, i2);
        this.m = getActionBarHideOffset();
        g();
        a aVar = this.v;
        if (aVar != null) {
            aVar.d();
        }
    }

    @Override // b.e.g.m
    public boolean onStartNestedScroll(View view, View view2, int i2) {
        if ((i2 & 2) == 0 || this.e.getVisibility() != 0) {
            return false;
        }
        return this.k;
    }

    @Override // b.e.g.m
    public void onStopNestedScroll(View view) {
        if (this.k && !this.l) {
            if (this.m <= this.e.getHeight()) {
                l();
            } else {
                k();
            }
        }
        a aVar = this.v;
        if (aVar != null) {
            aVar.b();
        }
    }

    public void onWindowSystemUiVisibilityChanged(int i2) {
        if (Build.VERSION.SDK_INT >= 16) {
            super.onWindowSystemUiVisibilityChanged(i2);
        }
        i();
        int i3 = this.n ^ i2;
        this.n = i2;
        boolean z2 = false;
        boolean z3 = (i2 & 4) == 0;
        if ((i2 & 256) != 0) {
            z2 = true;
        }
        a aVar = this.v;
        if (aVar != null) {
            aVar.a(!z2);
            if (z3 || !z2) {
                this.v.a();
            } else {
                this.v.c();
            }
        }
        if ((i3 & 256) != 0 && this.v != null) {
            t.z(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int i2) {
        super.onWindowVisibilityChanged(i2);
        this.f286c = i2;
        a aVar = this.v;
        if (aVar != null) {
            aVar.a(i2);
        }
    }

    public void setActionBarHideOffset(int i2) {
        g();
        this.e.setTranslationY((float) (-Math.max(0, Math.min(i2, this.e.getHeight()))));
    }

    public void setActionBarVisibilityCallback(a aVar) {
        this.v = aVar;
        if (getWindowToken() != null) {
            this.v.a(this.f286c);
            int i2 = this.n;
            if (i2 != 0) {
                onWindowSystemUiVisibilityChanged(i2);
                t.z(this);
            }
        }
    }

    public void setHasNonEmbeddedTabs(boolean z2) {
        this.j = z2;
    }

    public void setHideOnContentScrollEnabled(boolean z2) {
        if (z2 != this.k) {
            this.k = z2;
            if (!z2) {
                g();
                setActionBarHideOffset(0);
            }
        }
    }

    public void setIcon(int i2) {
        i();
        this.f.setIcon(i2);
    }

    public void setIcon(Drawable drawable) {
        i();
        this.f.setIcon(drawable);
    }

    public void setLogo(int i2) {
        i();
        this.f.b(i2);
    }

    @Override // androidx.appcompat.widget.C
    public void setMenu(Menu menu, v.a aVar) {
        i();
        this.f.setMenu(menu, aVar);
    }

    @Override // androidx.appcompat.widget.C
    public void setMenuPrepared() {
        i();
        this.f.setMenuPrepared();
    }

    public void setOverlayMode(boolean z2) {
        this.i = z2;
        this.h = z2 && getContext().getApplicationInfo().targetSdkVersion < 19;
    }

    public void setShowingForActionMode(boolean z2) {
    }

    public void setUiOptions(int i2) {
    }

    @Override // androidx.appcompat.widget.C
    public void setWindowCallback(Window.Callback callback) {
        i();
        this.f.setWindowCallback(callback);
    }

    @Override // androidx.appcompat.widget.C
    public void setWindowTitle(CharSequence charSequence) {
        i();
        this.f.setWindowTitle(charSequence);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
