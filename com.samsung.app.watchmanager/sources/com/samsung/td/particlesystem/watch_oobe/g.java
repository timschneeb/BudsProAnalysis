package com.samsung.td.particlesystem.watch_oobe;

import c.b.c.a.c.b;
import com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base;

class g extends b.a {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ int f1936b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_HighPerformance f1937c;

    g(ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance, int i) {
        this.f1937c = particleView_Watch_OOBE_HighPerformance;
        this.f1936b = i;
    }

    /* access modifiers changed from: protected */
    @Override // c.b.c.a.c.b.a
    public boolean a(int i) {
        ParticleView_Watch_OOBE_Base.a aVar = this.f1937c.f1921a;
        if (aVar == null) {
            return false;
        }
        aVar.onEndParticleState(this.f1936b);
        return false;
    }
}
