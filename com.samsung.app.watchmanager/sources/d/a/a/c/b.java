package d.a.a.c;

import d.a.a.c;
import d.a.a.d;
import d.a.a.h;
import d.a.a.j;
import java.util.Locale;

public abstract class b extends c {

    /* renamed from: a  reason: collision with root package name */
    private final d f2039a;

    protected b(d dVar) {
        if (dVar != null) {
            this.f2039a = dVar;
            return;
        }
        throw new IllegalArgumentException("The type must not be null");
    }

    /* access modifiers changed from: protected */
    public int a(String str, Locale locale) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            throw new j(g(), str);
        }
    }

    @Override // d.a.a.c
    public int a(Locale locale) {
        int c2 = c();
        if (c2 >= 0) {
            if (c2 < 10) {
                return 1;
            }
            if (c2 < 100) {
                return 2;
            }
            if (c2 < 1000) {
                return 3;
            }
        }
        return Integer.toString(c2).length();
    }

    @Override // d.a.a.c
    public long a(long j, int i) {
        return a().a(j, i);
    }

    @Override // d.a.a.c
    public long a(long j, String str, Locale locale) {
        return b(j, a(str, locale));
    }

    @Override // d.a.a.c
    public String a(int i, Locale locale) {
        return b(i, locale);
    }

    @Override // d.a.a.c
    public String a(long j, Locale locale) {
        return a(a(j), locale);
    }

    @Override // d.a.a.c
    public h b() {
        return null;
    }

    @Override // d.a.a.c
    public String b(int i, Locale locale) {
        return Integer.toString(i);
    }

    @Override // d.a.a.c
    public String b(long j, Locale locale) {
        return b(a(j), locale);
    }

    @Override // d.a.a.c
    public boolean b(long j) {
        return false;
    }

    @Override // d.a.a.c
    public long c(long j) {
        return j - e(j);
    }

    @Override // d.a.a.c
    public long d(long j) {
        long e = e(j);
        return e != j ? a(e, 1) : j;
    }

    @Override // d.a.a.c
    public final String e() {
        return this.f2039a.i();
    }

    @Override // d.a.a.c
    public long f(long j) {
        long e = e(j);
        long d2 = d(j);
        return d2 - j <= j - e ? d2 : e;
    }

    @Override // d.a.a.c
    public long g(long j) {
        long e = e(j);
        long d2 = d(j);
        long j2 = j - e;
        long j3 = d2 - j;
        return j2 < j3 ? e : (j3 >= j2 && (a(d2) & 1) != 0) ? e : d2;
    }

    @Override // d.a.a.c
    public final d g() {
        return this.f2039a;
    }

    @Override // d.a.a.c
    public long h(long j) {
        long e = e(j);
        long d2 = d(j);
        return j - e <= d2 - j ? e : d2;
    }

    public int i(long j) {
        return c();
    }

    @Override // d.a.a.c
    public final boolean i() {
        return true;
    }

    public String toString() {
        return "DateTimeField[" + e() + ']';
    }
}
