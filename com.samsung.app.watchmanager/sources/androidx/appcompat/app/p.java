package androidx.appcompat.app;

import android.view.View;
import b.e.g.D;
import b.e.g.o;
import b.e.g.t;

class p implements o {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AppCompatDelegateImpl f194a;

    p(AppCompatDelegateImpl appCompatDelegateImpl) {
        this.f194a = appCompatDelegateImpl;
    }

    @Override // b.e.g.o
    public D a(View view, D d2) {
        int d3 = d2.d();
        int i = this.f194a.i(d3);
        if (d3 != i) {
            d2 = d2.a(d2.b(), i, d2.c(), d2.a());
        }
        return t.b(view, d2);
    }
}
