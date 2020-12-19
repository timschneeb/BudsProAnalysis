package androidx.fragment.app;

import android.view.View;
import b.e.g.t;
import java.util.ArrayList;
import java.util.Map;

/* access modifiers changed from: package-private */
public class J implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ArrayList f748a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ Map f749b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ K f750c;

    J(K k, ArrayList arrayList, Map map) {
        this.f750c = k;
        this.f748a = arrayList;
        this.f749b = map;
    }

    public void run() {
        int size = this.f748a.size();
        for (int i = 0; i < size; i++) {
            View view = (View) this.f748a.get(i);
            t.a(view, (String) this.f749b.get(t.o(view)));
        }
    }
}
