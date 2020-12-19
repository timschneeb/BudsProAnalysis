package androidx.recyclerview.widget;

import androidx.recyclerview.widget.C0106k;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: androidx.recyclerview.widget.c  reason: case insensitive filesystem */
class RunnableC0098c implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ArrayList f1062a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ C0106k f1063b;

    RunnableC0098c(C0106k kVar, ArrayList arrayList) {
        this.f1063b = kVar;
        this.f1062a = arrayList;
    }

    public void run() {
        Iterator it = this.f1062a.iterator();
        while (it.hasNext()) {
            C0106k.b bVar = (C0106k.b) it.next();
            this.f1063b.b(bVar.f1092a, bVar.f1093b, bVar.f1094c, bVar.f1095d, bVar.e);
        }
        this.f1062a.clear();
        this.f1063b.n.remove(this.f1062a);
    }
}
