package androidx.fragment.app;

import android.view.View;
import android.view.ViewTreeObserver;

/* access modifiers changed from: package-private */
public class L implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {

    /* renamed from: a  reason: collision with root package name */
    private final View f751a;

    /* renamed from: b  reason: collision with root package name */
    private ViewTreeObserver f752b;

    /* renamed from: c  reason: collision with root package name */
    private final Runnable f753c;

    private L(View view, Runnable runnable) {
        this.f751a = view;
        this.f752b = view.getViewTreeObserver();
        this.f753c = runnable;
    }

    public static L a(View view, Runnable runnable) {
        L l = new L(view, runnable);
        view.getViewTreeObserver().addOnPreDrawListener(l);
        view.addOnAttachStateChangeListener(l);
        return l;
    }

    public void a() {
        (this.f752b.isAlive() ? this.f752b : this.f751a.getViewTreeObserver()).removeOnPreDrawListener(this);
        this.f751a.removeOnAttachStateChangeListener(this);
    }

    public boolean onPreDraw() {
        a();
        this.f753c.run();
        return true;
    }

    public void onViewAttachedToWindow(View view) {
        this.f752b = view.getViewTreeObserver();
    }

    public void onViewDetachedFromWindow(View view) {
        a();
    }
}
