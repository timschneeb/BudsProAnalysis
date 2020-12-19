package b.a.d;

import android.view.View;
import b.e.g.A;
import b.e.g.B;

/* access modifiers changed from: package-private */
public class h extends B {

    /* renamed from: a  reason: collision with root package name */
    private boolean f1255a = false;

    /* renamed from: b  reason: collision with root package name */
    private int f1256b = 0;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ i f1257c;

    h(i iVar) {
        this.f1257c = iVar;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.f1256b = 0;
        this.f1255a = false;
        this.f1257c.b();
    }

    @Override // b.e.g.A
    public void b(View view) {
        int i = this.f1256b + 1;
        this.f1256b = i;
        if (i == this.f1257c.f1258a.size()) {
            A a2 = this.f1257c.f1261d;
            if (a2 != null) {
                a2.b(null);
            }
            a();
        }
    }

    @Override // b.e.g.B, b.e.g.A
    public void c(View view) {
        if (!this.f1255a) {
            this.f1255a = true;
            A a2 = this.f1257c.f1261d;
            if (a2 != null) {
                a2.c(null);
            }
        }
    }
}
