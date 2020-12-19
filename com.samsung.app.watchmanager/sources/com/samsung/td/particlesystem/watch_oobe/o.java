package com.samsung.td.particlesystem.watch_oobe;

import c.b.c.a.a.j;
import c.b.c.a.a.k;
import c.b.c.a.b.l;
import c.b.c.a.b.m;
import c.b.c.a.c.b;
import com.samsung.android.app.twatchmanager.update.SAGUIDHelper;

class o extends b.a {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_HighPerformance f1949b;

    o(ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance) {
        this.f1949b = particleView_Watch_OOBE_HighPerformance;
    }

    /* access modifiers changed from: protected */
    @Override // c.b.c.a.c.b.a
    public boolean a(int i) {
        l lVar = this.f1949b.N;
        lVar.c(0.0f);
        lVar.a(SAGUIDHelper.GUID_REQUEST_ID, 0, m.a.OneWay);
        l lVar2 = this.f1949b.P;
        lVar2.c(0.0f);
        lVar2.a(SAGUIDHelper.GUID_REQUEST_ID, 0, m.a.OneWay);
        c.b.c.b.a.b bVar = this.f1949b.E[0];
        bVar.a(j.a(), k.a.In);
        bVar.c(1.0f);
        bVar.a(1000, 0, true);
        return false;
    }
}
