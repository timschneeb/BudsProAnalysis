package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
public class z implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ RecyclerView f1126a;

    z(RecyclerView recyclerView) {
        this.f1126a = recyclerView;
    }

    public void run() {
        RecyclerView.f fVar = this.f1126a.W;
        if (fVar != null) {
            fVar.i();
        }
        this.f1126a.xa = false;
    }
}
