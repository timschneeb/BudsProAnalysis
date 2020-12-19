package d.a.a.d;

import d.a.a.a;
import d.a.a.g;
import java.io.Writer;
import java.util.Locale;

/* access modifiers changed from: package-private */
public class h implements m {

    /* renamed from: a  reason: collision with root package name */
    private final g f2119a;

    private h(g gVar) {
        this.f2119a = gVar;
    }

    static m a(g gVar) {
        if (gVar == null) {
            return null;
        }
        return new h(gVar);
    }

    @Override // d.a.a.d.m
    public void a(Appendable appendable, long j, a aVar, int i, g gVar, Locale locale) {
        if (appendable instanceof StringBuffer) {
            this.f2119a.a((StringBuffer) appendable, j, aVar, i, gVar, locale);
        } else if (appendable instanceof Writer) {
            this.f2119a.a((Writer) appendable, j, aVar, i, gVar, locale);
        } else {
            StringBuffer stringBuffer = new StringBuffer(c());
            this.f2119a.a(stringBuffer, j, aVar, i, gVar, locale);
            appendable.append(stringBuffer);
        }
    }

    @Override // d.a.a.d.m
    public int c() {
        return this.f2119a.c();
    }
}
