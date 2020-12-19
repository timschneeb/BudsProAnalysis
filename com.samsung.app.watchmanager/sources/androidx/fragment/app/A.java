package androidx.fragment.app;

import android.graphics.Rect;
import android.view.View;
import androidx.fragment.app.B;
import b.c.b;
import java.util.ArrayList;

/* access modifiers changed from: package-private */
public class A implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ K f684a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ b f685b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ Object f686c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ B.a f687d;
    final /* synthetic */ ArrayList e;
    final /* synthetic */ View f;
    final /* synthetic */ Fragment g;
    final /* synthetic */ Fragment h;
    final /* synthetic */ boolean i;
    final /* synthetic */ ArrayList j;
    final /* synthetic */ Object k;
    final /* synthetic */ Rect l;

    A(K k2, b bVar, Object obj, B.a aVar, ArrayList arrayList, View view, Fragment fragment, Fragment fragment2, boolean z, ArrayList arrayList2, Object obj2, Rect rect) {
        this.f684a = k2;
        this.f685b = bVar;
        this.f686c = obj;
        this.f687d = aVar;
        this.e = arrayList;
        this.f = view;
        this.g = fragment;
        this.h = fragment2;
        this.i = z;
        this.j = arrayList2;
        this.k = obj2;
        this.l = rect;
    }

    public void run() {
        b<String, View> a2 = B.a(this.f684a, this.f685b, this.f686c, this.f687d);
        if (a2 != null) {
            this.e.addAll(a2.values());
            this.e.add(this.f);
        }
        B.a(this.g, this.h, this.i, a2, false);
        Object obj = this.f686c;
        if (obj != null) {
            this.f684a.b(obj, this.j, this.e);
            View a3 = B.a(a2, this.f687d, this.k, this.i);
            if (a3 != null) {
                this.f684a.a(a3, this.l);
            }
        }
    }
}
