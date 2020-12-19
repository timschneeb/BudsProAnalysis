package com.samsung.td.particlesystem.watch_oobe;

import c.b.c.a.c.b;
import com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base;

class p extends b.a {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ int f1950b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_HighPerformance f1951c;

    p(ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance, int i) {
        this.f1951c = particleView_Watch_OOBE_HighPerformance;
        this.f1950b = i;
    }

    /* access modifiers changed from: protected */
    @Override // c.b.c.a.c.b.a
    public boolean a(int i) {
        this.f1951c.C.a(0);
        ParticleView_Watch_OOBE_Base.a aVar = this.f1951c.f1921a;
        if (aVar != null) {
            aVar.onEndParticleState(this.f1950b);
        }
        return false;
    }
}
