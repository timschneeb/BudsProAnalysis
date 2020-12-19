package c.a.a.b.a;

import c.a.a.A;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.s;
import c.a.a.x;
import c.a.a.y;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* renamed from: c.a.a.b.a.h  reason: case insensitive filesystem */
public final class C0125h extends b {
    private static final Reader q = new C0124g();
    private static final Object r = new Object();
    private final List<Object> s;

    private Object B() {
        List<Object> list = this.s;
        return list.get(list.size() - 1);
    }

    private Object C() {
        List<Object> list = this.s;
        return list.remove(list.size() - 1);
    }

    private void a(c cVar) {
        if (o() != cVar) {
            throw new IllegalStateException("Expected " + cVar + " but was " + o());
        }
    }

    @Override // c.a.a.d.b
    public void a() {
        a(c.BEGIN_ARRAY);
        this.s.add(((s) B()).iterator());
    }

    @Override // c.a.a.d.b
    public void b() {
        a(c.BEGIN_OBJECT);
        this.s.add(((y) B()).i().iterator());
    }

    @Override // c.a.a.d.b
    public void c() {
        a(c.END_ARRAY);
        C();
        C();
    }

    @Override // c.a.a.d.b, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.s.clear();
        this.s.add(r);
    }

    @Override // c.a.a.d.b
    public void d() {
        a(c.END_OBJECT);
        C();
        C();
    }

    @Override // c.a.a.d.b
    public boolean f() {
        c o = o();
        return (o == c.END_OBJECT || o == c.END_ARRAY) ? false : true;
    }

    @Override // c.a.a.d.b
    public boolean h() {
        a(c.BOOLEAN);
        return ((A) C()).i();
    }

    @Override // c.a.a.d.b
    public double i() {
        c o = o();
        if (o == c.NUMBER || o == c.STRING) {
            double k = ((A) B()).k();
            if (g() || (!Double.isNaN(k) && !Double.isInfinite(k))) {
                C();
                return k;
            }
            throw new NumberFormatException("JSON forbids NaN and infinities: " + k);
        }
        throw new IllegalStateException("Expected " + c.NUMBER + " but was " + o);
    }

    @Override // c.a.a.d.b
    public int j() {
        c o = o();
        if (o == c.NUMBER || o == c.STRING) {
            int l = ((A) B()).l();
            C();
            return l;
        }
        throw new IllegalStateException("Expected " + c.NUMBER + " but was " + o);
    }

    @Override // c.a.a.d.b
    public long k() {
        c o = o();
        if (o == c.NUMBER || o == c.STRING) {
            long m = ((A) B()).m();
            C();
            return m;
        }
        throw new IllegalStateException("Expected " + c.NUMBER + " but was " + o);
    }

    @Override // c.a.a.d.b
    public String l() {
        a(c.NAME);
        Map.Entry entry = (Map.Entry) ((Iterator) B()).next();
        this.s.add(entry.getValue());
        return (String) entry.getKey();
    }

    @Override // c.a.a.d.b
    public void m() {
        a(c.NULL);
        C();
    }

    @Override // c.a.a.d.b
    public String n() {
        c o = o();
        if (o == c.STRING || o == c.NUMBER) {
            return ((A) C()).d();
        }
        throw new IllegalStateException("Expected " + c.STRING + " but was " + o);
    }

    @Override // c.a.a.d.b
    public c o() {
        if (this.s.isEmpty()) {
            return c.END_DOCUMENT;
        }
        Object B = B();
        if (B instanceof Iterator) {
            List<Object> list = this.s;
            boolean z = list.get(list.size() - 2) instanceof y;
            Iterator it = (Iterator) B;
            if (!it.hasNext()) {
                return z ? c.END_OBJECT : c.END_ARRAY;
            }
            if (z) {
                return c.NAME;
            }
            this.s.add(it.next());
            return o();
        } else if (B instanceof y) {
            return c.BEGIN_OBJECT;
        } else {
            if (B instanceof s) {
                return c.BEGIN_ARRAY;
            }
            if (B instanceof A) {
                A a2 = (A) B;
                if (a2.q()) {
                    return c.STRING;
                }
                if (a2.o()) {
                    return c.BOOLEAN;
                }
                if (a2.p()) {
                    return c.NUMBER;
                }
                throw new AssertionError();
            } else if (B instanceof x) {
                return c.NULL;
            } else {
                if (B == r) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
        }
    }

    @Override // c.a.a.d.b
    public void p() {
        if (o() == c.NAME) {
            l();
        } else {
            C();
        }
    }

    public void q() {
        a(c.NAME);
        Map.Entry entry = (Map.Entry) ((Iterator) B()).next();
        this.s.add(entry.getValue());
        this.s.add(new A((String) entry.getKey()));
    }

    @Override // c.a.a.d.b
    public String toString() {
        return C0125h.class.getSimpleName();
    }
}
