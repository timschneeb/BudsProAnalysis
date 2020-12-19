package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public abstract class J extends RecyclerView.f {
    boolean g = true;

    public final void a(RecyclerView.v vVar, boolean z) {
        c(vVar, z);
        c(vVar);
    }

    public abstract boolean a(RecyclerView.v vVar, int i, int i2, int i3, int i4);

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean a(RecyclerView.v vVar, RecyclerView.f.c cVar, RecyclerView.f.c cVar2) {
        return (cVar == null || (cVar.f991a == cVar2.f991a && cVar.f992b == cVar2.f992b)) ? f(vVar) : a(vVar, cVar.f991a, cVar.f992b, cVar2.f991a, cVar2.f992b);
    }

    public abstract boolean a(RecyclerView.v vVar, RecyclerView.v vVar2, int i, int i2, int i3, int i4);

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean a(RecyclerView.v vVar, RecyclerView.v vVar2, RecyclerView.f.c cVar, RecyclerView.f.c cVar2) {
        int i;
        int i2;
        int i3 = cVar.f991a;
        int i4 = cVar.f992b;
        if (vVar2.shouldIgnore()) {
            int i5 = cVar.f991a;
            i = cVar.f992b;
            i2 = i5;
        } else {
            i2 = cVar2.f991a;
            i = cVar2.f992b;
        }
        return a(vVar, vVar2, i3, i4, i2, i);
    }

    public final void b(RecyclerView.v vVar, boolean z) {
        d(vVar, z);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean b(RecyclerView.v vVar) {
        return !this.g || vVar.isInvalid();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean b(RecyclerView.v vVar, RecyclerView.f.c cVar, RecyclerView.f.c cVar2) {
        int i = cVar.f991a;
        int i2 = cVar.f992b;
        View view = vVar.itemView;
        int left = cVar2 == null ? view.getLeft() : cVar2.f991a;
        int top = cVar2 == null ? view.getTop() : cVar2.f992b;
        if (vVar.isRemoved() || (i == left && i2 == top)) {
            return g(vVar);
        }
        view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
        return a(vVar, i, i2, left, top);
    }

    public void c(RecyclerView.v vVar, boolean z) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean c(RecyclerView.v vVar, RecyclerView.f.c cVar, RecyclerView.f.c cVar2) {
        if (cVar.f991a != cVar2.f991a || cVar.f992b != cVar2.f992b) {
            return a(vVar, cVar.f991a, cVar.f992b, cVar2.f991a, cVar2.f992b);
        }
        j(vVar);
        return false;
    }

    public void d(RecyclerView.v vVar, boolean z) {
    }

    public abstract boolean f(RecyclerView.v vVar);

    public abstract boolean g(RecyclerView.v vVar);

    public final void h(RecyclerView.v vVar) {
        n(vVar);
        c(vVar);
    }

    public final void i(RecyclerView.v vVar) {
        o(vVar);
    }

    public final void j(RecyclerView.v vVar) {
        p(vVar);
        c(vVar);
    }

    public final void k(RecyclerView.v vVar) {
        q(vVar);
    }

    public final void l(RecyclerView.v vVar) {
        r(vVar);
        c(vVar);
    }

    public final void m(RecyclerView.v vVar) {
        s(vVar);
    }

    public void n(RecyclerView.v vVar) {
    }

    public void o(RecyclerView.v vVar) {
    }

    public void p(RecyclerView.v vVar) {
    }

    public void q(RecyclerView.v vVar) {
    }

    public void r(RecyclerView.v vVar) {
    }

    public void s(RecyclerView.v vVar) {
    }
}
