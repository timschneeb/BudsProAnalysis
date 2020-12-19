package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
/* renamed from: androidx.recyclerview.widget.m  reason: case insensitive filesystem */
public class C0108m extends RecyclerView.m {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ C0109n f1097a;

    C0108m(C0109n nVar) {
        this.f1097a = nVar;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.m
    public void a(RecyclerView recyclerView, int i, int i2) {
        this.f1097a.a(recyclerView.computeHorizontalScrollOffset(), recyclerView.computeVerticalScrollOffset());
    }
}
