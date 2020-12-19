package androidx.appcompat.app;

import b.e.g.z;

class t implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AppCompatDelegateImpl f198a;

    t(AppCompatDelegateImpl appCompatDelegateImpl) {
        this.f198a = appCompatDelegateImpl;
    }

    public void run() {
        AppCompatDelegateImpl appCompatDelegateImpl = this.f198a;
        appCompatDelegateImpl.r.showAtLocation(appCompatDelegateImpl.q, 55, 0, 0);
        this.f198a.l();
        if (this.f198a.s()) {
            this.f198a.q.setAlpha(0.0f);
            AppCompatDelegateImpl appCompatDelegateImpl2 = this.f198a;
            z a2 = b.e.g.t.a(appCompatDelegateImpl2.q);
            a2.a(1.0f);
            appCompatDelegateImpl2.t = a2;
            this.f198a.t.a(new s(this));
            return;
        }
        this.f198a.q.setAlpha(1.0f);
        this.f198a.q.setVisibility(0);
    }
}
