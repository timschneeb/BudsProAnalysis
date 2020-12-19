package androidx.fragment.app;

import androidx.lifecycle.t;
import java.util.List;

public class s {

    /* renamed from: a  reason: collision with root package name */
    private final List<Fragment> f804a;

    /* renamed from: b  reason: collision with root package name */
    private final List<s> f805b;

    /* renamed from: c  reason: collision with root package name */
    private final List<t> f806c;

    s(List<Fragment> list, List<s> list2, List<t> list3) {
        this.f804a = list;
        this.f805b = list2;
        this.f806c = list3;
    }

    /* access modifiers changed from: package-private */
    public List<s> a() {
        return this.f805b;
    }

    /* access modifiers changed from: package-private */
    public List<Fragment> b() {
        return this.f804a;
    }

    /* access modifiers changed from: package-private */
    public List<t> c() {
        return this.f806c;
    }
}
