package com.samsung.td.particlesystem.watch_oobe;

import c.b.c.a.a.k;
import c.b.c.a.b.e;
import c.b.c.a.b.h;
import c.b.c.a.b.i;
import c.b.c.a.b.m;
import c.b.c.a.c.b;
import c.b.c.b.a.a.a;
import c.b.c.b.a.c;
import c.b.c.b.a.d;
import com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_HighPerformance;

class j extends b.a {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ e f1942b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_HighPerformance f1943c;

    j(ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance, e eVar) {
        this.f1943c = particleView_Watch_OOBE_HighPerformance;
        this.f1942b = eVar;
    }

    /* access modifiers changed from: protected */
    @Override // c.b.c.a.c.b.a
    public boolean a(int i) {
        d dVar = this.f1943c.B;
        dVar.c(150.0f, 200.0f);
        dVar.a(2.0f, 5.0f);
        dVar.a(this.f1943c.r);
        a aVar = this.f1943c.z;
        aVar.b(0);
        aVar.a(5);
        aVar.a(this.f1943c.B);
        ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance = this.f1943c;
        particleView_Watch_OOBE_HighPerformance.D.a(particleView_Watch_OOBE_HighPerformance.u);
        c cVar = this.f1943c.D;
        cVar.b(2.0f);
        ParticleView_Watch_OOBE_HighPerformance.a[] aVarArr = this.f1943c.V;
        cVar.a(0, aVarArr[0].f1926b, aVarArr[0].e, aVarArr[0].f != 0.0f);
        cVar.c(0.8f);
        cVar.a(5.0f);
        cVar.c(4);
        ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance2 = this.f1943c;
        particleView_Watch_OOBE_HighPerformance2.y.a(particleView_Watch_OOBE_HighPerformance2.D);
        e eVar = this.f1942b;
        eVar.a(h.a(this.f1943c.S, new i(0.0f, 0.0f, 0.0f)));
        eVar.b(new i(1.0f, 1.0f, 1.0f));
        eVar.a(c.b.c.a.a.j.a(), k.a.InOut);
        eVar.a(1300, 0, m.a.OneWay);
        return false;
    }
}
