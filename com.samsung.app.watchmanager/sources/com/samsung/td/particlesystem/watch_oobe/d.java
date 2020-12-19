package com.samsung.td.particlesystem.watch_oobe;

import c.b.c.a.a.f;
import c.b.c.a.a.k;
import c.b.c.a.c.b;

class d extends b.a {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_HighPerformance f1932b;

    d(ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance) {
        this.f1932b = particleView_Watch_OOBE_HighPerformance;
    }

    /* access modifiers changed from: protected */
    @Override // c.b.c.a.c.b.a
    public boolean a(int i) {
        c.b.c.b.a.b bVar = this.f1932b.E[0];
        bVar.d(0.4f);
        bVar.b(0.0f);
        bVar.c(1.0f);
        c.b.c.b.a.b bVar2 = this.f1932b.E[0];
        bVar2.a(f.a(), k.a.InOut);
        bVar2.a(1500, 0, true);
        return false;
    }
}
