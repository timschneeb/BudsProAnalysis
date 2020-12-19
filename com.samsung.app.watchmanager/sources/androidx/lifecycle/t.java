package androidx.lifecycle;

import java.util.HashMap;

public class t {

    /* renamed from: a  reason: collision with root package name */
    private final HashMap<String, r> f858a = new HashMap<>();

    /* access modifiers changed from: package-private */
    public final r a(String str) {
        return this.f858a.get(str);
    }

    public final void a() {
        for (r rVar : this.f858a.values()) {
            rVar.a();
        }
        this.f858a.clear();
    }

    /* access modifiers changed from: package-private */
    public final void a(String str, r rVar) {
        r put = this.f858a.put(str, rVar);
        if (put != null) {
            put.a();
        }
    }
}
