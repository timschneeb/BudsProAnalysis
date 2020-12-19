package com.samsung.td.particlesystem.watch_oobe;

import c.b.c.a.a.j;
import c.b.c.a.a.k;
import c.b.c.a.b.l;
import c.b.c.a.b.m;
import c.b.c.a.c.b;

class k extends b.a {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_HighPerformance f1944b;

    k(ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance) {
        this.f1944b = particleView_Watch_OOBE_HighPerformance;
    }

    /* access modifiers changed from: protected */
    @Override // c.b.c.a.c.b.a
    public boolean a(int i) {
        l lVar = this.f1944b.N;
        lVar.c(1.0f);
        lVar.a(700, 0, m.a.OneWay);
        c.b.c.b.a.b bVar = this.f1944b.E[0];
        bVar.d(0.3f);
        bVar.b(0.0f);
        bVar.c(1.1f);
        bVar.a(j.a(), k.a.Out);
        bVar.a(1200, 0, false);
        return false;
    }
}
