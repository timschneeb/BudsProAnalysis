package androidx.core.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

@Deprecated
public final class l {

    /* renamed from: a  reason: collision with root package name */
    OverScroller f659a;

    l(Context context, Interpolator interpolator) {
        this.f659a = interpolator != null ? new OverScroller(context, interpolator) : new OverScroller(context);
    }

    @Deprecated
    public static l a(Context context, Interpolator interpolator) {
        return new l(context, interpolator);
    }

    @Deprecated
    public void a() {
        this.f659a.abortAnimation();
    }

    @Deprecated
    public void a(int i, int i2, int i3, int i4, int i5) {
        this.f659a.startScroll(i, i2, i3, i4, i5);
    }

    @Deprecated
    public boolean b() {
        return this.f659a.computeScrollOffset();
    }

    @Deprecated
    public int c() {
        return this.f659a.getCurrX();
    }

    @Deprecated
    public int d() {
        return this.f659a.getCurrY();
    }

    @Deprecated
    public int e() {
        return this.f659a.getFinalX();
    }

    @Deprecated
    public int f() {
        return this.f659a.getFinalY();
    }

    @Deprecated
    public boolean g() {
        return this.f659a.isFinished();
    }
}
