package com.samsung.td.particlesystem.watch_oobe;

import c.b.c.a.a.k;
import c.b.c.a.b.l;
import c.b.c.a.b.m;
import c.b.c.a.c.b;

class f extends b.a {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_HighPerformance f1935b;

    f(ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance) {
        this.f1935b = particleView_Watch_OOBE_HighPerformance;
    }

    /* access modifiers changed from: protected */
    @Override // c.b.c.a.c.b.a
    public boolean a(int i) {
        l lVar = this.f1935b.Q;
        lVar.a(c.b.c.a.a.f.a(), k.a.Out);
        lVar.c(1.0f);
        lVar.a(1400, 0, m.a.OneWay);
        return false;
    }
}
