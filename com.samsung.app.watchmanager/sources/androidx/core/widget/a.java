package androidx.core.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import b.e.g.t;
import com.samsung.android.app.twatchmanager.smartswitch.SmartSwitchConstants;

public abstract class a implements View.OnTouchListener {

    /* renamed from: a  reason: collision with root package name */
    private static final int f641a = ViewConfiguration.getTapTimeout();

    /* renamed from: b  reason: collision with root package name */
    final C0012a f642b = new C0012a();

    /* renamed from: c  reason: collision with root package name */
    private final Interpolator f643c = new AccelerateInterpolator();

    /* renamed from: d  reason: collision with root package name */
    final View f644d;
    private Runnable e;
    private float[] f = {0.0f, 0.0f};
    private float[] g = {Float.MAX_VALUE, Float.MAX_VALUE};
    private int h;
    private int i;
    private float[] j = {0.0f, 0.0f};
    private float[] k = {0.0f, 0.0f};
    private float[] l = {Float.MAX_VALUE, Float.MAX_VALUE};
    private boolean m;
    boolean n;
    boolean o;
    boolean p;
    private boolean q;
    private boolean r;

    /* access modifiers changed from: private */
    /* renamed from: androidx.core.widget.a$a  reason: collision with other inner class name */
    public static class C0012a {

        /* renamed from: a  reason: collision with root package name */
        private int f645a;

        /* renamed from: b  reason: collision with root package name */
        private int f646b;

        /* renamed from: c  reason: collision with root package name */
        private float f647c;

        /* renamed from: d  reason: collision with root package name */
        private float f648d;
        private long e = Long.MIN_VALUE;
        private long f = 0;
        private int g = 0;
        private int h = 0;
        private long i = -1;
        private float j;
        private int k;

        C0012a() {
        }

        private float a(float f2) {
            return (-4.0f * f2 * f2) + (f2 * 4.0f);
        }

        private float a(long j2) {
            if (j2 < this.e) {
                return 0.0f;
            }
            long j3 = this.i;
            if (j3 < 0 || j2 < j3) {
                return a.a(((float) (j2 - this.e)) / ((float) this.f645a), 0.0f, 1.0f) * 0.5f;
            }
            long j4 = j2 - j3;
            float f2 = this.j;
            return (1.0f - f2) + (f2 * a.a(((float) j4) / ((float) this.k), 0.0f, 1.0f));
        }

        public void a() {
            if (this.f != 0) {
                long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
                float a2 = a(a(currentAnimationTimeMillis));
                this.f = currentAnimationTimeMillis;
                float f2 = ((float) (currentAnimationTimeMillis - this.f)) * a2;
                this.g = (int) (this.f647c * f2);
                this.h = (int) (f2 * this.f648d);
                return;
            }
            throw new RuntimeException("Cannot compute scroll delta before calling start()");
        }

        public void a(float f2, float f3) {
            this.f647c = f2;
            this.f648d = f3;
        }

        public void a(int i2) {
            this.f646b = i2;
        }

        public int b() {
            return this.g;
        }

        public void b(int i2) {
            this.f645a = i2;
        }

        public int c() {
            return this.h;
        }

        public int d() {
            float f2 = this.f647c;
            return (int) (f2 / Math.abs(f2));
        }

        public int e() {
            float f2 = this.f648d;
            return (int) (f2 / Math.abs(f2));
        }

        public boolean f() {
            return this.i > 0 && AnimationUtils.currentAnimationTimeMillis() > this.i + ((long) this.k);
        }

        public void g() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.k = a.a((int) (currentAnimationTimeMillis - this.e), 0, this.f646b);
            this.j = a(currentAnimationTimeMillis);
            this.i = currentAnimationTimeMillis;
        }

        public void h() {
            this.e = AnimationUtils.currentAnimationTimeMillis();
            this.i = -1;
            this.f = this.e;
            this.j = 0.5f;
            this.g = 0;
            this.h = 0;
        }
    }

    /* access modifiers changed from: private */
    public class b implements Runnable {
        b() {
        }

        public void run() {
            a aVar = a.this;
            if (aVar.p) {
                if (aVar.n) {
                    aVar.n = false;
                    aVar.f642b.h();
                }
                C0012a aVar2 = a.this.f642b;
                if (aVar2.f() || !a.this.b()) {
                    a.this.p = false;
                    return;
                }
                a aVar3 = a.this;
                if (aVar3.o) {
                    aVar3.o = false;
                    aVar3.a();
                }
                aVar2.a();
                a.this.a(aVar2.b(), aVar2.c());
                t.a(a.this.f644d, this);
            }
        }
    }

    public a(View view) {
        this.f644d = view;
        float f2 = Resources.getSystem().getDisplayMetrics().density;
        float f3 = (float) ((int) ((1575.0f * f2) + 0.5f));
        b(f3, f3);
        float f4 = (float) ((int) ((f2 * 315.0f) + 0.5f));
        c(f4, f4);
        d(1);
        a(Float.MAX_VALUE, Float.MAX_VALUE);
        d(0.2f, 0.2f);
        e(1.0f, 1.0f);
        c(f641a);
        f(SmartSwitchConstants.SLEEP);
        e(SmartSwitchConstants.SLEEP);
    }

    static float a(float f2, float f3, float f4) {
        return f2 > f4 ? f4 : f2 < f3 ? f3 : f2;
    }

    private float a(float f2, float f3, float f4, float f5) {
        float f6;
        float a2 = a(f2 * f3, 0.0f, f4);
        float f7 = f(f3 - f5, a2) - f(f5, a2);
        if (f7 < 0.0f) {
            f6 = -this.f643c.getInterpolation(-f7);
        } else if (f7 <= 0.0f) {
            return 0.0f;
        } else {
            f6 = this.f643c.getInterpolation(f7);
        }
        return a(f6, -1.0f, 1.0f);
    }

    private float a(int i2, float f2, float f3, float f4) {
        float a2 = a(this.f[i2], f3, this.g[i2], f2);
        if (a2 == 0.0f) {
            return 0.0f;
        }
        float f5 = this.j[i2];
        float f6 = this.k[i2];
        float f7 = this.l[i2];
        float f8 = f5 * f4;
        return a2 > 0.0f ? a(a2 * f8, f6, f7) : -a((-a2) * f8, f6, f7);
    }

    static int a(int i2, int i3, int i4) {
        return i2 > i4 ? i4 : i2 < i3 ? i3 : i2;
    }

    private void c() {
        if (this.n) {
            this.p = false;
        } else {
            this.f642b.g();
        }
    }

    private void d() {
        int i2;
        if (this.e == null) {
            this.e = new b();
        }
        this.p = true;
        this.n = true;
        if (this.m || (i2 = this.i) <= 0) {
            this.e.run();
        } else {
            t.a(this.f644d, this.e, (long) i2);
        }
        this.m = true;
    }

    private float f(float f2, float f3) {
        if (f3 == 0.0f) {
            return 0.0f;
        }
        int i2 = this.h;
        if (i2 == 0 || i2 == 1) {
            if (f2 < f3) {
                return f2 >= 0.0f ? 1.0f - (f2 / f3) : (!this.p || this.h != 1) ? 0.0f : 1.0f;
            }
        } else if (i2 == 2 && f2 < 0.0f) {
            return f2 / (-f3);
        }
    }

    public a a(float f2, float f3) {
        float[] fArr = this.g;
        fArr[0] = f2;
        fArr[1] = f3;
        return this;
    }

    public a a(boolean z) {
        if (this.q && !z) {
            c();
        }
        this.q = z;
        return this;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
        this.f644d.onTouchEvent(obtain);
        obtain.recycle();
    }

    public abstract void a(int i2, int i3);

    public abstract boolean a(int i2);

    public a b(float f2, float f3) {
        float[] fArr = this.l;
        fArr[0] = f2 / 1000.0f;
        fArr[1] = f3 / 1000.0f;
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean b() {
        C0012a aVar = this.f642b;
        int e2 = aVar.e();
        int d2 = aVar.d();
        return (e2 != 0 && b(e2)) || (d2 != 0 && a(d2));
    }

    public abstract boolean b(int i2);

    public a c(float f2, float f3) {
        float[] fArr = this.k;
        fArr[0] = f2 / 1000.0f;
        fArr[1] = f3 / 1000.0f;
        return this;
    }

    public a c(int i2) {
        this.i = i2;
        return this;
    }

    public a d(float f2, float f3) {
        float[] fArr = this.f;
        fArr[0] = f2;
        fArr[1] = f3;
        return this;
    }

    public a d(int i2) {
        this.h = i2;
        return this;
    }

    public a e(float f2, float f3) {
        float[] fArr = this.j;
        fArr[0] = f2 / 1000.0f;
        fArr[1] = f3 / 1000.0f;
        return this;
    }

    public a e(int i2) {
        this.f642b.a(i2);
        return this;
    }

    public a f(int i2) {
        this.f642b.b(i2);
        return this;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0013, code lost:
        if (r0 != 3) goto L_0x0058;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouch(android.view.View r6, android.view.MotionEvent r7) {
        /*
            r5 = this;
            boolean r0 = r5.q
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            int r0 = r7.getActionMasked()
            r2 = 1
            if (r0 == 0) goto L_0x001a
            if (r0 == r2) goto L_0x0016
            r3 = 2
            if (r0 == r3) goto L_0x001e
            r6 = 3
            if (r0 == r6) goto L_0x0016
            goto L_0x0058
        L_0x0016:
            r5.c()
            goto L_0x0058
        L_0x001a:
            r5.o = r2
            r5.m = r1
        L_0x001e:
            float r0 = r7.getX()
            int r3 = r6.getWidth()
            float r3 = (float) r3
            android.view.View r4 = r5.f644d
            int r4 = r4.getWidth()
            float r4 = (float) r4
            float r0 = r5.a(r1, r0, r3, r4)
            float r7 = r7.getY()
            int r6 = r6.getHeight()
            float r6 = (float) r6
            android.view.View r3 = r5.f644d
            int r3 = r3.getHeight()
            float r3 = (float) r3
            float r6 = r5.a(r2, r7, r6, r3)
            androidx.core.widget.a$a r7 = r5.f642b
            r7.a(r0, r6)
            boolean r6 = r5.p
            if (r6 != 0) goto L_0x0058
            boolean r6 = r5.b()
            if (r6 == 0) goto L_0x0058
            r5.d()
        L_0x0058:
            boolean r6 = r5.r
            if (r6 == 0) goto L_0x0061
            boolean r6 = r5.p
            if (r6 == 0) goto L_0x0061
            r1 = 1
        L_0x0061:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.a.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }
}
