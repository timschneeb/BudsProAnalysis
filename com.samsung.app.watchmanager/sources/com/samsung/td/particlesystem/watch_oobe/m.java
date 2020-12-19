package com.samsung.td.particlesystem.watch_oobe;

import c.b.c.a.a.j;
import c.b.c.a.a.k;
import c.b.c.a.b.l;
import c.b.c.a.b.m;
import c.b.c.a.c.b;

class m extends b.a {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_HighPerformance f1947b;

    m(ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance) {
        this.f1947b = particleView_Watch_OOBE_HighPerformance;
    }

    /* access modifiers changed from: protected */
    @Override // c.b.c.a.c.b.a
    public boolean a(int i) {
        l lVar = this.f1947b.R;
        lVar.a(j.a(), k.a.Out);
        lVar.c(1.0f);
        lVar.a(1100, 0, m.a.OneWay);
        return false;
    }
}
