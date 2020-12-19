package c.a.a.b.a;

import c.a.a.A;
import c.a.a.d.d;
import c.a.a.s;
import c.a.a.v;
import c.a.a.x;
import c.a.a.y;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/* renamed from: c.a.a.b.a.j  reason: case insensitive filesystem */
public final class C0127j extends d {
    private static final Writer l = new C0126i();
    private static final A m = new A("closed");
    private final List<v> n = new ArrayList();
    private String o;
    private v p = x.f1689a;

    public C0127j() {
        super(l);
    }

    private void a(v vVar) {
        if (this.o != null) {
            if (!vVar.f() || e()) {
                ((y) m()).a(this.o, vVar);
            }
            this.o = null;
        } else if (this.n.isEmpty()) {
            this.p = vVar;
        } else {
            v m2 = m();
            if (m2 instanceof s) {
                ((s) m2).a(vVar);
                return;
            }
            throw new IllegalStateException();
        }
    }

    private v m() {
        List<v> list = this.n;
        return list.get(list.size() - 1);
    }

    @Override // c.a.a.d.d
    public d a() {
        s sVar = new s();
        a(sVar);
        this.n.add(sVar);
        return this;
    }

    @Override // c.a.a.d.d
    public d a(long j) {
        a(new A(Long.valueOf(j)));
        return this;
    }

    @Override // c.a.a.d.d
    public d a(Number number) {
        if (number == null) {
            h();
            return this;
        }
        if (!g()) {
            double doubleValue = number.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                throw new IllegalArgumentException("JSON forbids NaN and infinities: " + number);
            }
        }
        a(new A(number));
        return this;
    }

    @Override // c.a.a.d.d
    public d a(String str) {
        if (this.n.isEmpty() || this.o != null) {
            throw new IllegalStateException();
        } else if (m() instanceof y) {
            this.o = str;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override // c.a.a.d.d
    public d b() {
        y yVar = new y();
        a(yVar);
        this.n.add(yVar);
        return this;
    }

    @Override // c.a.a.d.d
    public d c() {
        if (this.n.isEmpty() || this.o != null) {
            throw new IllegalStateException();
        } else if (m() instanceof s) {
            List<v> list = this.n;
            list.remove(list.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override // c.a.a.d.d
    public d c(String str) {
        if (str == null) {
            h();
            return this;
        }
        a(new A(str));
        return this;
    }

    @Override // c.a.a.d.d, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.n.isEmpty()) {
            this.n.add(m);
            return;
        }
        throw new IOException("Incomplete document");
    }

    @Override // c.a.a.d.d
    public d d() {
        if (this.n.isEmpty() || this.o != null) {
            throw new IllegalStateException();
        } else if (m() instanceof y) {
            List<v> list = this.n;
            list.remove(list.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override // c.a.a.d.d
    public d d(boolean z) {
        a(new A(Boolean.valueOf(z)));
        return this;
    }

    @Override // c.a.a.d.d, java.io.Flushable
    public void flush() {
    }

    @Override // c.a.a.d.d
    public d h() {
        a(x.f1689a);
        return this;
    }

    public v i() {
        if (this.n.isEmpty()) {
            return this.p;
        }
        throw new IllegalStateException("Expected one JSON element but was " + this.n);
    }
}
