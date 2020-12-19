package com.samsung.td.particlesystem.watch_oobe;

import c.b.c.a.a.f;
import c.b.c.a.a.j;
import c.b.c.a.a.k;
import c.b.c.a.b.e;
import c.b.c.a.b.h;
import c.b.c.a.b.i;
import c.b.c.a.b.l;
import c.b.c.a.b.m;
import c.b.c.a.c.b;
import c.b.c.b.a.a.a;
import c.b.c.b.a.c;
import c.b.c.b.a.d;
import com.samsung.android.app.twatchmanager.update.SAGUIDHelper;
import com.samsung.android.app.twatchmanager.update.UpdateManager;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;

class q extends b.a {

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ e f1952b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f1953c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_HighPerformance f1954d;

    q(ParticleView_Watch_OOBE_HighPerformance particleView_Watch_OOBE_HighPerformance, e eVar, e eVar2) {
        this.f1954d = particleView_Watch_OOBE_HighPerformance;
        this.f1952b = eVar;
        this.f1953c = eVar2;
    }

    /* access modifiers changed from: protected */
    @Override // c.b.c.a.c.b.a
    public boolean a(int i) {
        this.f1954d.E[0].b();
        this.f1954d.P.d();
        this.f1954d.B.a(0);
        this.f1954d.A.a();
        d dVar = this.f1954d.C;
        dVar.b(0.7f, 1.5f);
        dVar.d(0.004f, 0.009f);
        dVar.c(60.0f, 140.0f);
        dVar.a(0.2f, 3.5f);
        dVar.a(this.f1954d.t);
        a aVar = this.f1954d.A;
        aVar.b(0);
        aVar.a(3);
        aVar.a(this.f1954d.C);
        this.f1952b.a(h.a(this.f1954d.S, new i(0.0f, 0.0f, -4.0f)), new i(0.5f, 0.8f, 1.0f), c.b.c.a.b.b.f, 0.0f);
        l lVar = this.f1954d.P;
        lVar.a(j.a(), k.a.None);
        lVar.c(1.0f);
        lVar.a(SAGUIDHelper.GUID_REQUEST_ID, 0, m.a.OneWay);
        l lVar2 = this.f1954d.R;
        lVar2.a(j.a(), k.a.In);
        lVar2.c(0.0f);
        lVar2.a(InstallationUtils.CURRENT_OPERATION_UNKNOWN, 0, m.a.OneWay);
        c cVar = this.f1954d.D;
        cVar.a(j.a(), k.a.InOut);
        cVar.c(2.0f, 3.0f);
        cVar.b(0.3f, 3.0f);
        cVar.a(3, true);
        e eVar = this.f1953c;
        eVar.a(h.a(this.f1954d.S, new i(0.0f, 0.0f, 0.0f)));
        eVar.b(new i(1.2f, 0.9f, 1.0f));
        eVar.a(f.a(), k.a.InOut);
        eVar.a(UpdateManager.UPDATE_CHECK_TIMEOUT_PER_REQUESET, 0, m.a.OneWay);
        l lVar3 = this.f1954d.N;
        lVar3.a(j.a(), k.a.Out);
        lVar3.c(1.0f);
        lVar3.a(InstallationUtils.CURRENT_OPERATION_UNKNOWN, 0, m.a.OneWay);
        c.b.c.b.a.b bVar = this.f1954d.E[0];
        bVar.d(0.0f);
        bVar.b(0.0f);
        bVar.c(1.0f);
        c.b.c.b.a.b bVar2 = this.f1954d.E[0];
        bVar2.a(j.a(), k.a.None);
        bVar2.a(100, 0, false);
        return false;
    }
}
