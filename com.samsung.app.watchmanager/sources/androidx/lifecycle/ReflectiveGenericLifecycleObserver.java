package androidx.lifecycle;

import androidx.lifecycle.a;
import androidx.lifecycle.f;

/* access modifiers changed from: package-private */
public class ReflectiveGenericLifecycleObserver implements e {

    /* renamed from: a  reason: collision with root package name */
    private final Object f828a;

    /* renamed from: b  reason: collision with root package name */
    private final a.C0014a f829b = a.f832a.a(this.f828a.getClass());

    ReflectiveGenericLifecycleObserver(Object obj) {
        this.f828a = obj;
    }

    @Override // androidx.lifecycle.e
    public void a(h hVar, f.a aVar) {
        this.f829b.a(hVar, aVar, this.f828a);
    }
}
