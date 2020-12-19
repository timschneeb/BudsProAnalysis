package c.b.c.a.b;

import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;
import c.b.c.a.a.d;
import c.b.c.a.a.k;
import c.b.c.a.b.m;

public class l implements m {

    /* renamed from: a  reason: collision with root package name */
    m.a f1855a;

    /* renamed from: b  reason: collision with root package name */
    k.a f1856b;

    /* renamed from: c  reason: collision with root package name */
    k f1857c;

    /* renamed from: d  reason: collision with root package name */
    ValueAnimator.AnimatorUpdateListener f1858d;
    float e;
    float f;
    float g;
    float h;
    long i;
    ValueAnimator j;

    public l() {
        b();
        d();
    }

    public float a() {
        return this.h;
    }

    public l a(int i2, int i3, m.a aVar) {
        f();
        this.f1855a = aVar;
        if (k.f1854a[aVar.ordinal()] != 2) {
            this.j.setFloatValues(0.0f, 1.0f);
        } else {
            this.j.setFloatValues(0.0f, 1.0f, 0.0f);
        }
        this.j.setDuration((long) i2);
        this.j.setStartDelay((long) i3);
        this.j.start();
        return this;
    }

    public l a(k kVar, k.a aVar) {
        this.f1856b = aVar;
        this.f1857c = kVar;
        return this;
    }

    /* access modifiers changed from: package-private */
    public void a(float f2) {
        this.e = b.b(f2, 1.0f, 0.0f);
        this.h = b.a(this.e, this.f, this.g);
    }

    public l b(float f2) {
        f();
        this.e = 0.0f;
        this.i = 0;
        this.h = f2;
        this.f = f2;
        this.g = f2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        a(d.a(), k.a.InOut);
        this.j = new ValueAnimator();
        this.f1858d = new j(this);
        this.j.setInterpolator(new LinearInterpolator());
        this.j.addUpdateListener(this.f1858d);
        this.h = 0.0f;
    }

    public l c(float f2) {
        e();
        this.g = f2;
        return this;
    }

    public boolean c() {
        return this.j.isStarted();
    }

    public l d() {
        b(0.0f);
        return this;
    }

    /* access modifiers changed from: package-private */
    public void e() {
        this.f = this.h;
    }

    public l f() {
        if (c()) {
            this.j.setCurrentPlayTime(0);
            this.j.cancel();
        }
        return this;
    }
}
