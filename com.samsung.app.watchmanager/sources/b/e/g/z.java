package b.e.g;

import android.os.Build;
import android.view.View;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;

public final class z {

    /* renamed from: a  reason: collision with root package name */
    private WeakReference<View> f1442a;

    /* renamed from: b  reason: collision with root package name */
    Runnable f1443b = null;

    /* renamed from: c  reason: collision with root package name */
    Runnable f1444c = null;

    /* renamed from: d  reason: collision with root package name */
    int f1445d = -1;

    /* access modifiers changed from: package-private */
    public static class a implements A {

        /* renamed from: a  reason: collision with root package name */
        z f1446a;

        /* renamed from: b  reason: collision with root package name */
        boolean f1447b;

        a(z zVar) {
            this.f1446a = zVar;
        }

        @Override // b.e.g.A
        public void a(View view) {
            Object tag = view.getTag(2113929216);
            A a2 = tag instanceof A ? (A) tag : null;
            if (a2 != null) {
                a2.a(view);
            }
        }

        @Override // b.e.g.A
        public void b(View view) {
            int i = this.f1446a.f1445d;
            A a2 = null;
            if (i > -1) {
                view.setLayerType(i, null);
                this.f1446a.f1445d = -1;
            }
            if (Build.VERSION.SDK_INT >= 16 || !this.f1447b) {
                z zVar = this.f1446a;
                Runnable runnable = zVar.f1444c;
                if (runnable != null) {
                    zVar.f1444c = null;
                    runnable.run();
                }
                Object tag = view.getTag(2113929216);
                if (tag instanceof A) {
                    a2 = (A) tag;
                }
                if (a2 != null) {
                    a2.b(view);
                }
                this.f1447b = true;
            }
        }

        @Override // b.e.g.A
        public void c(View view) {
            this.f1447b = false;
            A a2 = null;
            if (this.f1446a.f1445d > -1) {
                view.setLayerType(2, null);
            }
            z zVar = this.f1446a;
            Runnable runnable = zVar.f1443b;
            if (runnable != null) {
                zVar.f1443b = null;
                runnable.run();
            }
            Object tag = view.getTag(2113929216);
            if (tag instanceof A) {
                a2 = (A) tag;
            }
            if (a2 != null) {
                a2.c(view);
            }
        }
    }

    z(View view) {
        this.f1442a = new WeakReference<>(view);
    }

    private void a(View view, A a2) {
        if (a2 != null) {
            view.animate().setListener(new x(this, a2, view));
        } else {
            view.animate().setListener(null);
        }
    }

    public z a(float f) {
        View view = this.f1442a.get();
        if (view != null) {
            view.animate().alpha(f);
        }
        return this;
    }

    public z a(long j) {
        View view = this.f1442a.get();
        if (view != null) {
            view.animate().setDuration(j);
        }
        return this;
    }

    public z a(Interpolator interpolator) {
        View view = this.f1442a.get();
        if (view != null) {
            view.animate().setInterpolator(interpolator);
        }
        return this;
    }

    public z a(A a2) {
        View view = this.f1442a.get();
        if (view != null) {
            if (Build.VERSION.SDK_INT < 16) {
                view.setTag(2113929216, a2);
                a2 = new a(this);
            }
            a(view, a2);
        }
        return this;
    }

    public z a(C c2) {
        View view = this.f1442a.get();
        if (view != null && Build.VERSION.SDK_INT >= 19) {
            y yVar = null;
            if (c2 != null) {
                yVar = new y(this, c2, view);
            }
            view.animate().setUpdateListener(yVar);
        }
        return this;
    }

    public void a() {
        View view = this.f1442a.get();
        if (view != null) {
            view.animate().cancel();
        }
    }

    public long b() {
        View view = this.f1442a.get();
        if (view != null) {
            return view.animate().getDuration();
        }
        return 0;
    }

    public z b(float f) {
        View view = this.f1442a.get();
        if (view != null) {
            view.animate().translationY(f);
        }
        return this;
    }

    public z b(long j) {
        View view = this.f1442a.get();
        if (view != null) {
            view.animate().setStartDelay(j);
        }
        return this;
    }

    public void c() {
        View view = this.f1442a.get();
        if (view != null) {
            view.animate().start();
        }
    }
}
