package androidx.appcompat.widget;

import android.view.View;
import b.e.g.B;

class oa extends B {

    /* renamed from: a  reason: collision with root package name */
    private boolean f490a = false;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ int f491b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ pa f492c;

    oa(pa paVar, int i) {
        this.f492c = paVar;
        this.f491b = i;
    }

    @Override // b.e.g.B, b.e.g.A
    public void a(View view) {
        this.f490a = true;
    }

    @Override // b.e.g.A
    public void b(View view) {
        if (!this.f490a) {
            this.f492c.f497a.setVisibility(this.f491b);
        }
    }

    @Override // b.e.g.B, b.e.g.A
    public void c(View view) {
        this.f492c.f497a.setVisibility(0);
    }
}
