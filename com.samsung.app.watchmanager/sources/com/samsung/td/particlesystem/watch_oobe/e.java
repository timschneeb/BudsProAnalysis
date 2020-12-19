package com.samsung.td.particlesystem.watch_oobe;

import c.b.c.a.a.k;
import c.b.c.a.b.h;
import c.b.c.a.b.i;
import c.b.c.a.b.m;
import c.b.c.a.c.b;

class e extends b.a {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ c.b.c.a.b.e f1933b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_HighPerformance f1934c;

    e(ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance, c.b.c.a.b.e eVar) {
        this.f1934c = particleView_Watch_OOBE_HighPerformance;
        this.f1933b = eVar;
    }

    /* access modifiers changed from: protected */
    @Override // c.b.c.a.c.b.a
    public boolean a(int i) {
        c.b.c.a.b.e eVar = this.f1933b;
        eVar.a(h.a(this.f1934c.S, new i(0.0f, 0.0f, -4.0f)));
        eVar.b(new i(1.0f, 1.0f, 1.0f));
        eVar.a(c.b.c.a.a.b.a(), k.a.InOut);
        eVar.a(3000, 0, m.a.OneWay);
        return false;
    }
}
