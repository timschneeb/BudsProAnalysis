package d.a.a.c;

import d.a.a.c;
import d.a.a.d;
import java.io.Serializable;
import java.util.Locale;

public abstract class a implements Serializable {
    public int a() {
        return c().a(f());
    }

    public String a(Locale locale) {
        return c().a(f(), locale);
    }

    /* access modifiers changed from: protected */
    public d.a.a.a b() {
        throw new UnsupportedOperationException("The method getChronology() was added in v1.4 and needs to be implemented by subclasses of AbstractReadableInstantFieldProperty");
    }

    public String b(Locale locale) {
        return c().b(f(), locale);
    }

    public int c(Locale locale) {
        return c().a(locale);
    }

    public abstract c c();

    public d d() {
        return c().g();
    }

    public int e() {
        return c().c();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return a() == aVar.a() && d().equals(aVar.d()) && g.a(b(), aVar.b());
    }

    /* access modifiers changed from: protected */
    public abstract long f();

    public int g() {
        return c().d();
    }

    public String h() {
        return c().e();
    }

    public int hashCode() {
        return (a() * 17) + d().hashCode() + b().hashCode();
    }

    public String toString() {
        return "Property[" + h() + "]";
    }
}
