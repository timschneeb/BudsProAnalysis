package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: androidx.recyclerview.widget.e  reason: case insensitive filesystem */
class RunnableC0100e implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ArrayList f1066a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ C0106k f1067b;

    RunnableC0100e(C0106k kVar, ArrayList arrayList) {
        this.f1067b = kVar;
        this.f1066a = arrayList;
    }

    public void run() {
        Iterator it = this.f1066a.iterator();
        while (it.hasNext()) {
            this.f1067b.t((RecyclerView.v) it.next());
        }
        this.f1066a.clear();
        this.f1067b.m.remove(this.f1066a);
    }
}
