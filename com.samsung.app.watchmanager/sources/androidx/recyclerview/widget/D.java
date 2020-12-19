package androidx.recyclerview.widget;

import androidx.recyclerview.widget.C0096a;
import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
public class D implements C0096a.AbstractC0015a {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ RecyclerView f938a;

    D(RecyclerView recyclerView) {
        this.f938a = recyclerView;
    }

    @Override // androidx.recyclerview.widget.C0096a.AbstractC0015a
    public RecyclerView.v a(int i) {
        RecyclerView.v a2 = this.f938a.a(i, true);
        if (a2 != null && !this.f938a.o.c(a2.itemView)) {
            return a2;
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.C0096a.AbstractC0015a
    public void a(int i, int i2) {
        this.f938a.g(i, i2);
        this.f938a.ua = true;
    }

    @Override // androidx.recyclerview.widget.C0096a.AbstractC0015a
    public void a(int i, int i2, Object obj) {
        this.f938a.a(i, i2, obj);
        this.f938a.va = true;
    }

    @Override // androidx.recyclerview.widget.C0096a.AbstractC0015a
    public void a(C0096a.b bVar) {
        c(bVar);
    }

    @Override // androidx.recyclerview.widget.C0096a.AbstractC0015a
    public void b(int i, int i2) {
        this.f938a.a(i, i2, false);
        this.f938a.ua = true;
    }

    @Override // androidx.recyclerview.widget.C0096a.AbstractC0015a
    public void b(C0096a.b bVar) {
        c(bVar);
    }

    @Override // androidx.recyclerview.widget.C0096a.AbstractC0015a
    public void c(int i, int i2) {
        this.f938a.f(i, i2);
        this.f938a.ua = true;
    }

    /* access modifiers changed from: package-private */
    public void c(C0096a.b bVar) {
        int i = bVar.f1053a;
        if (i == 1) {
            RecyclerView recyclerView = this.f938a;
            recyclerView.w.onItemsAdded(recyclerView, bVar.f1054b, bVar.f1056d);
        } else if (i == 2) {
            RecyclerView recyclerView2 = this.f938a;
            recyclerView2.w.onItemsRemoved(recyclerView2, bVar.f1054b, bVar.f1056d);
        } else if (i == 4) {
            RecyclerView recyclerView3 = this.f938a;
            recyclerView3.w.onItemsUpdated(recyclerView3, bVar.f1054b, bVar.f1056d, bVar.f1055c);
        } else if (i == 8) {
            RecyclerView recyclerView4 = this.f938a;
            recyclerView4.w.onItemsMoved(recyclerView4, bVar.f1054b, bVar.f1056d, 1);
        }
    }

    @Override // androidx.recyclerview.widget.C0096a.AbstractC0015a
    public void d(int i, int i2) {
        this.f938a.a(i, i2, true);
        RecyclerView recyclerView = this.f938a;
        recyclerView.ua = true;
        recyclerView.ra.f1022d += i2;
    }
}
