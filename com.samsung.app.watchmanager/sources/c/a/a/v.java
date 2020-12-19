package c.a.a;

import c.a.a.b.A;
import c.a.a.d.d;
import java.io.IOException;
import java.io.StringWriter;

public abstract class v {
    public s a() {
        if (e()) {
            return (s) this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    public y b() {
        if (g()) {
            return (y) this;
        }
        throw new IllegalStateException("Not a JSON Object: " + this);
    }

    public A c() {
        if (h()) {
            return (A) this;
        }
        throw new IllegalStateException("This is not a JSON Primitive.");
    }

    public String d() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public boolean e() {
        return this instanceof s;
    }

    public boolean f() {
        return this instanceof x;
    }

    public boolean g() {
        return this instanceof y;
    }

    public boolean h() {
        return this instanceof A;
    }

    public String toString() {
        try {
            StringWriter stringWriter = new StringWriter();
            d dVar = new d(stringWriter);
            dVar.b(true);
            A.a(this, dVar);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
