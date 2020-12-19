package androidx.fragment.app;

import android.graphics.Rect;
import android.view.View;
import b.c.b;

/* access modifiers changed from: package-private */
public class z implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Fragment f812a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ Fragment f813b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ boolean f814c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ b f815d;
    final /* synthetic */ View e;
    final /* synthetic */ K f;
    final /* synthetic */ Rect g;

    z(Fragment fragment, Fragment fragment2, boolean z, b bVar, View view, K k, Rect rect) {
        this.f812a = fragment;
        this.f813b = fragment2;
        this.f814c = z;
        this.f815d = bVar;
        this.e = view;
        this.f = k;
        this.g = rect;
    }

    public void run() {
        B.a(this.f812a, this.f813b, this.f814c, (b<String, View>) this.f815d, false);
        View view = this.e;
        if (view != null) {
            this.f.a(view, this.g);
        }
    }
}
