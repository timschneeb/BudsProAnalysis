package c.b.b.a.a;

import android.text.TextUtils;
import java.util.Map;

public class e extends f<e> {
    @Override // c.b.b.a.a.f
    public Map<String, String> a() {
        if (!this.f1817a.containsKey("en")) {
            c.b.b.a.a.a.i.e.b("Failure to build Log : Event name cannot be null");
        }
        a("t", "ev");
        return super.a();
    }

    /* access modifiers changed from: protected */
    @Override // c.b.b.a.a.f
    public e b() {
        return this;
    }

    public e b(String str) {
        if (TextUtils.isEmpty(str)) {
            c.b.b.a.a.a.i.e.b("Failure to build Log : Event name cannot be null");
        }
        a("en", str);
        return this;
    }

    @Override // c.b.b.a.a.f
    public /* bridge */ /* synthetic */ long c() {
        return super.c();
    }
}
