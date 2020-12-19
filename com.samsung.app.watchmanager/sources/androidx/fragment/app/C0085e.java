package androidx.fragment.app;

import androidx.lifecycle.f;
import androidx.lifecycle.h;
import androidx.lifecycle.j;

/* access modifiers changed from: package-private */
/* renamed from: androidx.fragment.app.e  reason: case insensitive filesystem */
public class C0085e implements h {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Fragment f764a;

    C0085e(Fragment fragment) {
        this.f764a = fragment;
    }

    @Override // androidx.lifecycle.h
    public f a() {
        Fragment fragment = this.f764a;
        if (fragment.V == null) {
            fragment.V = new j(fragment.W);
        }
        return this.f764a.V;
    }
}
