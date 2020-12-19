package c.b.c.a.b;

import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;
import c.b.c.a.a.d;
import c.b.c.a.a.k;
import c.b.c.a.b.m;

public class e extends g implements m {
    m.a g;
    k.a h;
    k i;
    ValueAnimator.AnimatorUpdateListener j;
    i k;
    i l;
    i m;
    float n;
    ValueAnimator o;
    i p;
    i q;
    float r;
    float s;

    public e() {
        f();
        g();
    }

    public e a(int i2, int i3, m.a aVar) {
        k();
        this.g = aVar;
        if (d.f1841a[aVar.ordinal()] != 2) {
            this.o.setFloatValues(0.0f, 1.0f);
        } else {
            this.o.setFloatValues(0.0f, 1.0f, 0.0f);
        }
        this.o.setDuration((long) i2);
        this.o.setStartDelay((long) i3);
        this.o.start();
        return this;
    }

    public e a(k kVar, k.a aVar) {
        this.h = aVar;
        this.i = kVar;
        return this;
    }

    public e a(i iVar) {
        j();
        this.m.b(iVar);
        return this;
    }

    public e a(i iVar, float f, i iVar2, float f2) {
        this.k.b(f, f, f);
        a(iVar, this.k, iVar2, f2);
        return this;
    }

    @Override // c.b.c.a.b.g
    public e a(i iVar, i iVar2, i iVar3, float f) {
        super.a(iVar, iVar2, iVar3, f);
        this.n = 0.0f;
        this.l.b(iVar);
        this.m.b(iVar);
        this.p.d(iVar2);
        this.q.d(iVar2);
        this.r = f;
        this.s = f;
        return this;
    }

    /* access modifiers changed from: package-private */
    public void a(float f) {
        this.n = b.b(f, 1.0f, 0.0f);
        this.f1847b.b(this.l);
        this.f1847b.a(this.m, f);
        this.f1848c.b(this.p);
        this.f1848c.a(this.q, f);
        this.e = b.a(f, this.r, this.s);
        h();
    }

    public e b(i iVar) {
        j();
        this.q.b(iVar);
        return this;
    }

    /* access modifiers changed from: package-private */
    @Override // c.b.c.a.b.g
    public void f() {
        super.f();
        this.k = new i();
        this.l = new i();
        this.m = new i();
        this.p = new i();
        this.q = new i();
        a(d.a(), k.a.InOut);
        this.o = new ValueAnimator();
        this.j = new c(this);
        this.o.setInterpolator(new LinearInterpolator());
        this.o.addUpdateListener(this.j);
    }

    @Override // c.b.c.a.b.g
    public e g() {
        a(b.f1838c, b.f1839d, b.f, 0.0f);
        return this;
    }

    public boolean i() {
        return this.o.isStarted();
    }

    /* access modifiers changed from: package-private */
    public void j() {
        this.n = 0.0f;
        this.l.b(this.f1847b);
        this.p.b(this.f1848c);
        float f = this.e;
        if (f >= 6.2831855f) {
            this.e = f % 6.2831855f;
        }
        this.r = this.e;
        h();
    }

    public e k() {
        if (i()) {
            this.o.cancel();
        }
        return this;
    }
}
