package com.samsung.td.particlesystem.watch_oobe;

import c.b.c.a.a.k;
import c.b.c.a.b.e;
import c.b.c.a.b.h;
import c.b.c.a.b.i;
import c.b.c.a.b.m;
import c.b.c.a.c.b;
import c.b.c.b.a.a.a;
import c.b.c.b.a.d;
import com.samsung.android.app.twatchmanager.smartswitch.SmartSwitchConstants;
import com.samsung.android.app.twatchmanager.update.UpdateManager;

class l extends b.a {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ e f1945b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_HighPerformance f1946c;

    l(ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance, e eVar) {
        this.f1946c = particleView_Watch_OOBE_HighPerformance;
        this.f1945b = eVar;
    }

    /* access modifiers changed from: protected */
    @Override // c.b.c.a.c.b.a
    public boolean a(int i) {
        c.b.c.a.b.l lVar = this.f1946c.P;
        lVar.c(1.0f);
        lVar.a(SmartSwitchConstants.SLEEP, 0, m.a.OneWay);
        e eVar = this.f1945b;
        eVar.a(h.a(this.f1946c.S, new i(0.0f, 0.0f, 0.0f)));
        eVar.a(c.b.c.a.a.h.a(), k.a.InOut);
        eVar.a(UpdateManager.UPDATE_CHECK_TIMEOUT_PER_REQUESET, 0, m.a.OneWay);
        d dVar = this.f1946c.C;
        dVar.b(0.5f, 1.0f);
        dVar.d(0.0015f, 0.0025f);
        dVar.c(130.0f, 180.0f);
        dVar.a(2.0f);
        dVar.a(this.f1946c.s);
        a aVar = this.f1946c.A;
        aVar.b(0);
        aVar.a(4);
        aVar.a(this.f1946c.C);
        return false;
    }
}
