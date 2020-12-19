package c.b.b.a.a.a;

import c.b.b.a.a.a.f.d.a;
import c.b.b.a.a.d;

/* access modifiers changed from: package-private */
public class c implements a<Void, Boolean> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ e f1722a;

    c(e eVar) {
        this.f1722a = eVar;
    }

    public Void a(Boolean bool) {
        if (!bool.booleanValue()) {
            return null;
        }
        d c2 = this.f1722a.f1740b.c();
        if (c2 == null) {
            a.a(this.f1722a.f1739a.getApplicationContext(), this.f1722a.f1740b).a(this.f1722a.f1739a.getApplicationContext());
            return null;
        }
        a.a(this.f1722a.f1739a.getApplicationContext(), this.f1722a.f1740b).a(new c.b.b.a.a.a.f.d.a.a(c2));
        return null;
    }
}
