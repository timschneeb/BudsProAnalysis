package com.samsung.td.particlesystem.watch_oobe;

import c.b.c.a.c.b;
import com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base;

class h extends b.a {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ int f1938b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_HighPerformance f1939c;

    h(ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance, int i) {
        this.f1939c = particleView_Watch_OOBE_HighPerformance;
        this.f1938b = i;
    }

    /* access modifiers changed from: protected */
    @Override // c.b.c.a.c.b.a
    public boolean a(int i) {
        this.f1939c.m.cancel();
        this.f1939c.o.removeCallbacksAndMessages(null);
        ParticleView_Watch_OOBE_Base.a aVar = this.f1939c.f1921a;
        if (aVar == null) {
            return false;
        }
        aVar.onEndParticleState(this.f1938b);
        return false;
    }
}
