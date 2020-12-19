package androidx.fragment.app;

import android.view.View;
import b.e.g.t;
import java.util.ArrayList;

/* access modifiers changed from: package-private */
public class H implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ int f741a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ArrayList f742b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ ArrayList f743c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ ArrayList f744d;
    final /* synthetic */ ArrayList e;
    final /* synthetic */ K f;

    H(K k, int i, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4) {
        this.f = k;
        this.f741a = i;
        this.f742b = arrayList;
        this.f743c = arrayList2;
        this.f744d = arrayList3;
        this.e = arrayList4;
    }

    public void run() {
        for (int i = 0; i < this.f741a; i++) {
            t.a((View) this.f742b.get(i), (String) this.f743c.get(i));
            t.a((View) this.f744d.get(i), (String) this.e.get(i));
        }
    }
}
