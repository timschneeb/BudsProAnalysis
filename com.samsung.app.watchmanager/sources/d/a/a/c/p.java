package d.a.a.c;

import d.a.a.c;
import d.a.a.d;
import d.a.a.h;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;

public final class p extends c implements Serializable {

    /* renamed from: a  reason: collision with root package name */
    private static HashMap<d, p> f2058a;

    /* renamed from: b  reason: collision with root package name */
    private final d f2059b;

    /* renamed from: c  reason: collision with root package name */
    private final h f2060c;

    private p(d dVar, h hVar) {
        if (dVar == null || hVar == null) {
            throw new IllegalArgumentException();
        }
        this.f2059b = dVar;
        this.f2060c = hVar;
    }

    public static synchronized p a(d dVar, h hVar) {
        p pVar;
        synchronized (p.class) {
            pVar = null;
            if (f2058a == null) {
                f2058a = new HashMap<>(7);
            } else {
                p pVar2 = f2058a.get(dVar);
                if (pVar2 == null || pVar2.a() == hVar) {
                    pVar = pVar2;
                }
            }
            if (pVar == null) {
                pVar = new p(dVar, hVar);
                f2058a.put(dVar, pVar);
            }
        }
        return pVar;
    }

    private UnsupportedOperationException j() {
        return new UnsupportedOperationException(this.f2059b + " field is unsupported");
    }

    @Override // d.a.a.c
    public int a(long j) {
        throw j();
    }

    @Override // d.a.a.c
    public int a(Locale locale) {
        throw j();
    }

    @Override // d.a.a.c
    public long a(long j, int i) {
        return a().a(j, i);
    }

    @Override // d.a.a.c
    public long a(long j, String str, Locale locale) {
        throw j();
    }

    @Override // d.a.a.c
    public h a() {
        return this.f2060c;
    }

    @Override // d.a.a.c
    public String a(int i, Locale locale) {
        throw j();
    }

    @Override // d.a.a.c
    public String a(long j, Locale locale) {
        throw j();
    }

    @Override // d.a.a.c
    public long b(long j, int i) {
        throw j();
    }

    @Override // d.a.a.c
    public h b() {
        return null;
    }

    @Override // d.a.a.c
    public String b(int i, Locale locale) {
        throw j();
    }

    @Override // d.a.a.c
    public String b(long j, Locale locale) {
        throw j();
    }

    @Override // d.a.a.c
    public boolean b(long j) {
        throw j();
    }

    @Override // d.a.a.c
    public int c() {
        throw j();
    }

    @Override // d.a.a.c
    public long c(long j) {
        throw j();
    }

    @Override // d.a.a.c
    public int d() {
        throw j();
    }

    @Override // d.a.a.c
    public long d(long j) {
        throw j();
    }

    @Override // d.a.a.c
    public long e(long j) {
        throw j();
    }

    @Override // d.a.a.c
    public String e() {
        return this.f2059b.i();
    }

    @Override // d.a.a.c
    public long f(long j) {
        throw j();
    }

    @Override // d.a.a.c
    public h f() {
        return null;
    }

    @Override // d.a.a.c
    public long g(long j) {
        throw j();
    }

    @Override // d.a.a.c
    public d g() {
        return this.f2059b;
    }

    @Override // d.a.a.c
    public long h(long j) {
        throw j();
    }

    @Override // d.a.a.c
    public boolean h() {
        return false;
    }

    @Override // d.a.a.c
    public boolean i() {
        return false;
    }

    public String toString() {
        return "UnsupportedDateTimeField";
    }
}
