package androidx.swiperefreshlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListView;
import androidx.core.widget.i;
import b.e.g.j;
import b.e.g.k;
import b.e.g.m;
import b.e.g.n;
import b.e.g.t;

public class SwipeRefreshLayout extends ViewGroup implements m, j {

    /* renamed from: a  reason: collision with root package name */
    private static final String f1141a = "SwipeRefreshLayout";

    /* renamed from: b  reason: collision with root package name */
    private static final int[] f1142b = {16842766};
    protected int A;
    int B;
    int C;
    d D;
    private Animation E;
    private Animation F;
    private Animation G;
    private Animation H;
    private Animation I;
    boolean J;
    private int K;
    boolean L;
    private a M;
    private Animation.AnimationListener N;
    private final Animation O;
    private final Animation P;

    /* renamed from: c  reason: collision with root package name */
    private View f1143c;

    /* renamed from: d  reason: collision with root package name */
    b f1144d;
    boolean e;
    private int f;
    private float g;
    private float h;
    private final n i;
    private final k j;
    private final int[] k;
    private final int[] l;
    private boolean m;
    private int n;
    int o;
    private float p;
    private float q;
    private boolean r;
    private int s;
    boolean t;
    private boolean u;
    private final DecelerateInterpolator v;
    a w;
    private int x;
    protected int y;
    float z;

    public interface a {
        boolean a(SwipeRefreshLayout swipeRefreshLayout, View view);
    }

    public interface b {
        void a();
    }

    public SwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = false;
        this.g = -1.0f;
        this.k = new int[2];
        this.l = new int[2];
        this.s = -1;
        this.x = -1;
        this.N = new e(this);
        this.O = new j(this);
        this.P = new k(this);
        this.f = ViewConfiguration.get(context).getScaledTouchSlop();
        this.n = getResources().getInteger(17694721);
        setWillNotDraw(false);
        this.v = new DecelerateInterpolator(2.0f);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.K = (int) (displayMetrics.density * 40.0f);
        c();
        setChildrenDrawingOrderEnabled(true);
        this.B = (int) (displayMetrics.density * 64.0f);
        this.g = (float) this.B;
        this.i = new n(this);
        this.j = new k(this);
        setNestedScrollingEnabled(true);
        int i2 = -this.K;
        this.o = i2;
        this.A = i2;
        a(1.0f);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f1142b);
        setEnabled(obtainStyledAttributes.getBoolean(0, true));
        obtainStyledAttributes.recycle();
    }

    private Animation a(int i2, int i3) {
        h hVar = new h(this, i2, i3);
        hVar.setDuration(300);
        this.w.a(null);
        this.w.clearAnimation();
        this.w.startAnimation(hVar);
        return hVar;
    }

    private void a(int i2, Animation.AnimationListener animationListener) {
        this.y = i2;
        this.O.reset();
        this.O.setDuration(200);
        this.O.setInterpolator(this.v);
        if (animationListener != null) {
            this.w.a(animationListener);
        }
        this.w.clearAnimation();
        this.w.startAnimation(this.O);
    }

    private void a(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.s) {
            this.s = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
        }
    }

    private void a(boolean z2, boolean z3) {
        if (this.e != z2) {
            this.J = z3;
            d();
            this.e = z2;
            if (this.e) {
                a(this.o, this.N);
            } else {
                a(this.N);
            }
        }
    }

    private boolean a(Animation animation) {
        return animation != null && animation.hasStarted() && !animation.hasEnded();
    }

    private void b(float f2) {
        if (f2 > this.g) {
            a(true, true);
            return;
        }
        this.e = false;
        this.D.a(0.0f, 0.0f);
        i iVar = null;
        if (!this.t) {
            iVar = new i(this);
        }
        b(this.o, iVar);
        this.D.a(false);
    }

    private void b(int i2, Animation.AnimationListener animationListener) {
        if (this.t) {
            c(i2, animationListener);
            return;
        }
        this.y = i2;
        this.P.reset();
        this.P.setDuration(200);
        this.P.setInterpolator(this.v);
        if (animationListener != null) {
            this.w.a(animationListener);
        }
        this.w.clearAnimation();
        this.w.startAnimation(this.P);
    }

    private void b(Animation.AnimationListener animationListener) {
        this.w.setVisibility(0);
        this.D.setAlpha(255);
        this.E = new f(this);
        this.E.setDuration((long) this.n);
        if (animationListener != null) {
            this.w.a(animationListener);
        }
        this.w.clearAnimation();
        this.w.startAnimation(this.E);
    }

    private void c() {
        this.w = new a(getContext(), -328966);
        this.D = new d(getContext());
        this.D.a(1);
        this.w.setImageDrawable(this.D);
        this.w.setVisibility(8);
        addView(this.w);
    }

    private void c(float f2) {
        this.D.a(true);
        float min = Math.min(1.0f, Math.abs(f2 / this.g));
        double d2 = (double) min;
        Double.isNaN(d2);
        float max = (((float) Math.max(d2 - 0.4d, 0.0d)) * 5.0f) / 3.0f;
        float abs = Math.abs(f2) - this.g;
        int i2 = this.C;
        if (i2 <= 0) {
            i2 = this.L ? this.B - this.A : this.B;
        }
        float f3 = (float) i2;
        double max2 = (double) (Math.max(0.0f, Math.min(abs, f3 * 2.0f) / f3) / 4.0f);
        double pow = Math.pow(max2, 2.0d);
        Double.isNaN(max2);
        float f4 = ((float) (max2 - pow)) * 2.0f;
        int i3 = this.A + ((int) ((f3 * min) + (f3 * f4 * 2.0f)));
        if (this.w.getVisibility() != 0) {
            this.w.setVisibility(0);
        }
        if (!this.t) {
            this.w.setScaleX(1.0f);
            this.w.setScaleY(1.0f);
        }
        if (this.t) {
            setAnimationProgress(Math.min(1.0f, f2 / this.g));
        }
        if (f2 < this.g) {
            if (this.D.getAlpha() > 76 && !a(this.G)) {
                f();
            }
        } else if (this.D.getAlpha() < 255 && !a(this.H)) {
            e();
        }
        this.D.a(0.0f, Math.min(0.8f, max * 0.8f));
        this.D.a(Math.min(1.0f, max));
        this.D.b((((max * 0.4f) - 16.0f) + (f4 * 2.0f)) * 0.5f);
        setTargetOffsetTopAndBottom(i3 - this.o);
    }

    private void c(int i2, Animation.AnimationListener animationListener) {
        this.y = i2;
        this.z = this.w.getScaleX();
        this.I = new l(this);
        this.I.setDuration(150);
        if (animationListener != null) {
            this.w.a(animationListener);
        }
        this.w.clearAnimation();
        this.w.startAnimation(this.I);
    }

    private void d() {
        if (this.f1143c == null) {
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                View childAt = getChildAt(i2);
                if (!childAt.equals(this.w)) {
                    this.f1143c = childAt;
                    return;
                }
            }
        }
    }

    private void d(float f2) {
        float f3 = this.q;
        int i2 = this.f;
        if (f2 - f3 > ((float) i2) && !this.r) {
            this.p = f3 + ((float) i2);
            this.r = true;
            this.D.setAlpha(76);
        }
    }

    private void e() {
        this.H = a(this.D.getAlpha(), 255);
    }

    private void f() {
        this.G = a(this.D.getAlpha(), 76);
    }

    private void setColorViewAlpha(int i2) {
        this.w.getBackground().setAlpha(i2);
        this.D.setAlpha(i2);
    }

    /* access modifiers changed from: package-private */
    public void a(float f2) {
        int i2 = this.y;
        setTargetOffsetTopAndBottom((i2 + ((int) (((float) (this.A - i2)) * f2))) - this.w.getTop());
    }

    /* access modifiers changed from: package-private */
    public void a(Animation.AnimationListener animationListener) {
        this.F = new g(this);
        this.F.setDuration(150);
        this.w.a(animationListener);
        this.w.clearAnimation();
        this.w.startAnimation(this.F);
    }

    public boolean a() {
        a aVar = this.M;
        if (aVar != null) {
            return aVar.a(this, this.f1143c);
        }
        View view = this.f1143c;
        return view instanceof ListView ? i.a((ListView) view, -1) : view.canScrollVertically(-1);
    }

    /* access modifiers changed from: package-private */
    public void b() {
        this.w.clearAnimation();
        this.D.stop();
        this.w.setVisibility(8);
        setColorViewAlpha(255);
        if (this.t) {
            setAnimationProgress(0.0f);
        } else {
            setTargetOffsetTopAndBottom(this.A - this.o);
        }
        this.o = this.w.getTop();
    }

    public boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return this.j.a(f2, f3, z2);
    }

    public boolean dispatchNestedPreFling(float f2, float f3) {
        return this.j.a(f2, f3);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return this.j.a(i2, i3, iArr, iArr2);
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return this.j.a(i2, i3, i4, i5, iArr);
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i2, int i3) {
        int i4 = this.x;
        return i4 < 0 ? i3 : i3 == i2 + -1 ? i4 : i3 >= i4 ? i3 + 1 : i3;
    }

    public int getNestedScrollAxes() {
        return this.i.a();
    }

    public int getProgressCircleDiameter() {
        return this.K;
    }

    public int getProgressViewEndOffset() {
        return this.B;
    }

    public int getProgressViewStartOffset() {
        return this.A;
    }

    public boolean hasNestedScrollingParent() {
        return this.j.a();
    }

    @Override // b.e.g.j
    public boolean isNestedScrollingEnabled() {
        return this.j.b();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        b();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        d();
        int actionMasked = motionEvent.getActionMasked();
        if (this.u && actionMasked == 0) {
            this.u = false;
        }
        if (!isEnabled() || this.u || a() || this.e || this.m) {
            return false;
        }
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int i2 = this.s;
                    if (i2 == -1) {
                        Log.e(f1141a, "Got ACTION_MOVE event but don't have an active pointer id.");
                        return false;
                    }
                    int findPointerIndex = motionEvent.findPointerIndex(i2);
                    if (findPointerIndex < 0) {
                        return false;
                    }
                    d(motionEvent.getY(findPointerIndex));
                } else if (actionMasked != 3) {
                    if (actionMasked == 6) {
                        a(motionEvent);
                    }
                }
            }
            this.r = false;
            this.s = -1;
        } else {
            setTargetOffsetTopAndBottom(this.A - this.w.getTop());
            this.s = motionEvent.getPointerId(0);
            this.r = false;
            int findPointerIndex2 = motionEvent.findPointerIndex(this.s);
            if (findPointerIndex2 < 0) {
                return false;
            }
            this.q = motionEvent.getY(findPointerIndex2);
        }
        return this.r;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (getChildCount() != 0) {
            if (this.f1143c == null) {
                d();
            }
            View view = this.f1143c;
            if (view != null) {
                int paddingLeft = getPaddingLeft();
                int paddingTop = getPaddingTop();
                view.layout(paddingLeft, paddingTop, ((measuredWidth - getPaddingLeft()) - getPaddingRight()) + paddingLeft, ((measuredHeight - getPaddingTop()) - getPaddingBottom()) + paddingTop);
                int measuredWidth2 = this.w.getMeasuredWidth();
                int measuredHeight2 = this.w.getMeasuredHeight();
                int i6 = measuredWidth / 2;
                int i7 = measuredWidth2 / 2;
                int i8 = this.o;
                this.w.layout(i6 - i7, i8, i6 + i7, measuredHeight2 + i8);
            }
        }
    }

    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.f1143c == null) {
            d();
        }
        View view = this.f1143c;
        if (view != null) {
            view.measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
            this.w.measure(View.MeasureSpec.makeMeasureSpec(this.K, 1073741824), View.MeasureSpec.makeMeasureSpec(this.K, 1073741824));
            this.x = -1;
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                if (getChildAt(i4) == this.w) {
                    this.x = i4;
                    return;
                }
            }
        }
    }

    @Override // b.e.g.m
    public boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        return dispatchNestedFling(f2, f3, z2);
    }

    @Override // b.e.g.m
    public boolean onNestedPreFling(View view, float f2, float f3) {
        return dispatchNestedPreFling(f2, f3);
    }

    @Override // b.e.g.m
    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
        if (i3 > 0) {
            float f2 = this.h;
            if (f2 > 0.0f) {
                float f3 = (float) i3;
                if (f3 > f2) {
                    iArr[1] = i3 - ((int) f2);
                    this.h = 0.0f;
                } else {
                    this.h = f2 - f3;
                    iArr[1] = i3;
                }
                c(this.h);
            }
        }
        if (this.L && i3 > 0 && this.h == 0.0f && Math.abs(i3 - iArr[1]) > 0) {
            this.w.setVisibility(8);
        }
        int[] iArr2 = this.k;
        if (dispatchNestedPreScroll(i2 - iArr[0], i3 - iArr[1], iArr2, null)) {
            iArr[0] = iArr[0] + iArr2[0];
            iArr[1] = iArr[1] + iArr2[1];
        }
    }

    @Override // b.e.g.m
    public void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        dispatchNestedScroll(i2, i3, i4, i5, this.l);
        int i6 = i5 + this.l[1];
        if (i6 < 0 && !a()) {
            this.h += (float) Math.abs(i6);
            c(this.h);
        }
    }

    @Override // b.e.g.m
    public void onNestedScrollAccepted(View view, View view2, int i2) {
        this.i.a(view, view2, i2);
        startNestedScroll(i2 & 2);
        this.h = 0.0f;
        this.m = true;
    }

    @Override // b.e.g.m
    public boolean onStartNestedScroll(View view, View view2, int i2) {
        return isEnabled() && !this.u && !this.e && (i2 & 2) != 0;
    }

    @Override // b.e.g.m
    public void onStopNestedScroll(View view) {
        this.i.a(view);
        this.m = false;
        float f2 = this.h;
        if (f2 > 0.0f) {
            b(f2);
            this.h = 0.0f;
        }
        stopNestedScroll();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (this.u && actionMasked == 0) {
            this.u = false;
        }
        if (!isEnabled() || this.u || a() || this.e || this.m) {
            return false;
        }
        if (actionMasked == 0) {
            this.s = motionEvent.getPointerId(0);
            this.r = false;
        } else if (actionMasked == 1) {
            int findPointerIndex = motionEvent.findPointerIndex(this.s);
            if (findPointerIndex < 0) {
                Log.e(f1141a, "Got ACTION_UP event but don't have an active pointer id.");
                return false;
            }
            if (this.r) {
                this.r = false;
                b((motionEvent.getY(findPointerIndex) - this.p) * 0.5f);
            }
            this.s = -1;
            return false;
        } else if (actionMasked == 2) {
            int findPointerIndex2 = motionEvent.findPointerIndex(this.s);
            if (findPointerIndex2 < 0) {
                Log.e(f1141a, "Got ACTION_MOVE event but have an invalid active pointer id.");
                return false;
            }
            float y2 = motionEvent.getY(findPointerIndex2);
            d(y2);
            if (this.r) {
                float f2 = (y2 - this.p) * 0.5f;
                if (f2 <= 0.0f) {
                    return false;
                }
                c(f2);
            }
        } else if (actionMasked == 3) {
            return false;
        } else {
            if (actionMasked == 5) {
                int actionIndex = motionEvent.getActionIndex();
                if (actionIndex < 0) {
                    Log.e(f1141a, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                this.s = motionEvent.getPointerId(actionIndex);
            } else if (actionMasked == 6) {
                a(motionEvent);
            }
        }
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean z2) {
        if (Build.VERSION.SDK_INT >= 21 || !(this.f1143c instanceof AbsListView)) {
            View view = this.f1143c;
            if (view == null || t.x(view)) {
                super.requestDisallowInterceptTouchEvent(z2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setAnimationProgress(float f2) {
        this.w.setScaleX(f2);
        this.w.setScaleY(f2);
    }

    @Deprecated
    public void setColorScheme(int... iArr) {
        setColorSchemeResources(iArr);
    }

    public void setColorSchemeColors(int... iArr) {
        d();
        this.D.a(iArr);
    }

    public void setColorSchemeResources(int... iArr) {
        Context context = getContext();
        int[] iArr2 = new int[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr2[i2] = androidx.core.content.a.a(context, iArr[i2]);
        }
        setColorSchemeColors(iArr2);
    }

    public void setDistanceToTriggerSync(int i2) {
        this.g = (float) i2;
    }

    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        if (!z2) {
            b();
        }
    }

    public void setNestedScrollingEnabled(boolean z2) {
        this.j.a(z2);
    }

    public void setOnChildScrollUpCallback(a aVar) {
        this.M = aVar;
    }

    public void setOnRefreshListener(b bVar) {
        this.f1144d = bVar;
    }

    @Deprecated
    public void setProgressBackgroundColor(int i2) {
        setProgressBackgroundColorSchemeResource(i2);
    }

    public void setProgressBackgroundColorSchemeColor(int i2) {
        this.w.setBackgroundColor(i2);
    }

    public void setProgressBackgroundColorSchemeResource(int i2) {
        setProgressBackgroundColorSchemeColor(androidx.core.content.a.a(getContext(), i2));
    }

    public void setProgressViewEndTarget(boolean z2, int i2) {
        this.B = i2;
        this.t = z2;
        this.w.invalidate();
    }

    public void setProgressViewOffset(boolean z2, int i2, int i3) {
        this.t = z2;
        this.A = i2;
        this.B = i3;
        this.L = true;
        b();
        this.e = false;
    }

    public void setRefreshing(boolean z2) {
        if (!z2 || this.e == z2) {
            a(z2, false);
            return;
        }
        this.e = z2;
        setTargetOffsetTopAndBottom((!this.L ? this.B + this.A : this.B) - this.o);
        this.J = false;
        b(this.N);
    }

    public void setSize(int i2) {
        if (i2 == 0 || i2 == 1) {
            this.K = (int) (getResources().getDisplayMetrics().density * (i2 == 0 ? 56.0f : 40.0f));
            this.w.setImageDrawable(null);
            this.D.a(i2);
            this.w.setImageDrawable(this.D);
        }
    }

    public void setSlingshotDistance(int i2) {
        this.C = i2;
    }

    /* access modifiers changed from: package-private */
    public void setTargetOffsetTopAndBottom(int i2) {
        this.w.bringToFront();
        t.b((View) this.w, i2);
        this.o = this.w.getTop();
    }

    public boolean startNestedScroll(int i2) {
        return this.j.b(i2);
    }

    @Override // b.e.g.j
    public void stopNestedScroll() {
        this.j.c();
    }
}
