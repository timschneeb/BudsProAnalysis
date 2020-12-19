package d.a.a.e;

import java.util.Collections;
import java.util.Set;

public final class g implements f {

    /* renamed from: a  reason: collision with root package name */
    private static final Set<String> f2142a = Collections.singleton("UTC");

    @Override // d.a.a.e.f
    public d.a.a.g a(String str) {
        if ("UTC".equalsIgnoreCase(str)) {
            return d.a.a.g.f2149a;
        }
        return null;
    }

    @Override // d.a.a.e.f
    public Set<String> a() {
        return f2142a;
    }
}
