package androidx.fragment.app;

import android.view.View;

/* renamed from: androidx.fragment.app.m  reason: case insensitive filesystem */
class RunnableC0093m implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ n f772a;

    RunnableC0093m(n nVar) {
        this.f772a = nVar;
    }

    public void run() {
        if (this.f772a.f774c.g() != null) {
            this.f772a.f774c.a((View) null);
            n nVar = this.f772a;
            r rVar = nVar.f775d;
            Fragment fragment = nVar.f774c;
            rVar.a(fragment, fragment.x(), 0, 0, false);
        }
    }
}
