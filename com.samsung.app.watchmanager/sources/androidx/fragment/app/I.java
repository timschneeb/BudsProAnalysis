package androidx.fragment.app;

import android.view.View;
import b.e.g.t;
import java.util.ArrayList;
import java.util.Map;

/* access modifiers changed from: package-private */
public class I implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ArrayList f745a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ Map f746b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ K f747c;

    I(K k, ArrayList arrayList, Map map) {
        this.f747c = k;
        this.f745a = arrayList;
        this.f746b = map;
    }

    public void run() {
        int size = this.f745a.size();
        for (int i = 0; i < size; i++) {
            View view = (View) this.f745a.get(i);
            String o = t.o(view);
            if (o != null) {
                t.a(view, K.a(this.f746b, o));
            }
        }
    }
}
