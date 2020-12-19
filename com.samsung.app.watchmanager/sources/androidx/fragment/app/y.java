package androidx.fragment.app;

import android.view.View;
import java.util.ArrayList;

/* access modifiers changed from: package-private */
public class y implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Object f808a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ K f809b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ View f810c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ Fragment f811d;
    final /* synthetic */ ArrayList e;
    final /* synthetic */ ArrayList f;
    final /* synthetic */ ArrayList g;
    final /* synthetic */ Object h;

    y(Object obj, K k, View view, Fragment fragment, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Object obj2) {
        this.f808a = obj;
        this.f809b = k;
        this.f810c = view;
        this.f811d = fragment;
        this.e = arrayList;
        this.f = arrayList2;
        this.g = arrayList3;
        this.h = obj2;
    }

    public void run() {
        Object obj = this.f808a;
        if (obj != null) {
            this.f809b.b(obj, this.f810c);
            this.f.addAll(B.a(this.f809b, this.f808a, this.f811d, this.e, this.f810c));
        }
        if (this.g != null) {
            if (this.h != null) {
                ArrayList<View> arrayList = new ArrayList<>();
                arrayList.add(this.f810c);
                this.f809b.a(this.h, this.g, arrayList);
            }
            this.g.clear();
            this.g.add(this.f810c);
        }
    }
}
