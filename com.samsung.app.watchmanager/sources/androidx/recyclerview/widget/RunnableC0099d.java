package androidx.recyclerview.widget;

import androidx.recyclerview.widget.C0106k;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: androidx.recyclerview.widget.d  reason: case insensitive filesystem */
class RunnableC0099d implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ArrayList f1064a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ C0106k f1065b;

    RunnableC0099d(C0106k kVar, ArrayList arrayList) {
        this.f1065b = kVar;
        this.f1064a = arrayList;
    }

    public void run() {
        Iterator it = this.f1064a.iterator();
        while (it.hasNext()) {
            this.f1065b.a((C0106k.a) it.next());
        }
        this.f1064a.clear();
        this.f1065b.o.remove(this.f1064a);
    }
}
