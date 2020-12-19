package androidx.recyclerview.widget;

import androidx.recyclerview.widget.O;
import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
public class B implements O.b {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ RecyclerView f936a;

    B(RecyclerView recyclerView) {
        this.f936a = recyclerView;
    }

    @Override // androidx.recyclerview.widget.O.b
    public void a(RecyclerView.v vVar) {
        RecyclerView recyclerView = this.f936a;
        recyclerView.w.removeAndRecycleView(vVar.itemView, recyclerView.l);
    }

    @Override // androidx.recyclerview.widget.O.b
    public void a(RecyclerView.v vVar, RecyclerView.f.c cVar, RecyclerView.f.c cVar2) {
        this.f936a.a(vVar, cVar, cVar2);
    }

    @Override // androidx.recyclerview.widget.O.b
    public void b(RecyclerView.v vVar, RecyclerView.f.c cVar, RecyclerView.f.c cVar2) {
        this.f936a.l.c(vVar);
        this.f936a.b(vVar, cVar, cVar2);
    }

    @Override // androidx.recyclerview.widget.O.b
    public void c(RecyclerView.v vVar, RecyclerView.f.c cVar, RecyclerView.f.c cVar2) {
        vVar.setIsRecyclable(false);
        RecyclerView recyclerView = this.f936a;
        if (recyclerView.N) {
            if (!recyclerView.W.a(vVar, vVar, cVar, cVar2)) {
                return;
            }
        } else if (!recyclerView.W.c(vVar, cVar, cVar2)) {
            return;
        }
        this.f936a.s();
    }
}
