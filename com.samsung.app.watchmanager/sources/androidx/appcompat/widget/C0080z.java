package androidx.appcompat.widget;

import android.graphics.Typeface;
import androidx.core.content.a.h;
import java.lang.ref.WeakReference;

/* renamed from: androidx.appcompat.widget.z  reason: case insensitive filesystem */
class C0080z extends h.a {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ WeakReference f531a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ A f532b;

    C0080z(A a2, WeakReference weakReference) {
        this.f532b = a2;
        this.f531a = weakReference;
    }

    @Override // androidx.core.content.a.h.a
    public void a(int i) {
    }

    @Override // androidx.core.content.a.h.a
    public void a(Typeface typeface) {
        this.f532b.a(this.f531a, typeface);
    }
}
