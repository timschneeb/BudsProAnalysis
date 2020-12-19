package c.b.c.a.b;

import java.util.HashMap;
import java.util.Map;

public class f {

    /* renamed from: a  reason: collision with root package name */
    a f1842a = new a();

    /* renamed from: b  reason: collision with root package name */
    a f1843b = new a();

    /* renamed from: c  reason: collision with root package name */
    i f1844c = new i();

    /* renamed from: d  reason: collision with root package name */
    i f1845d = new i();
    HashMap<Integer, e> e = new HashMap<>();

    public e a(int i) {
        return this.e.get(Integer.valueOf(i));
    }

    public f a() {
        this.e.clear();
        return this;
    }

    public f a(int i, e eVar) {
        this.e.put(Integer.valueOf(i), eVar);
        return this;
    }

    public a b() {
        return this.f1842a;
    }

    public f c() {
        this.f1845d.b(1.0f, 1.0f, 1.0f);
        this.f1842a.a();
        this.f1844c.b(0.0f, 0.0f, 0.0f);
        for (Map.Entry<Integer, e> entry : this.e.entrySet()) {
            entry.getKey();
            e value = entry.getValue();
            this.f1845d.c(value.e());
            this.f1844c.a(value.d());
            this.f1843b.a(value.b(), value.a());
            this.f1842a.b(this.f1843b);
        }
        a aVar = this.f1843b;
        i iVar = this.f1845d;
        aVar.a(iVar.f1850a, iVar.f1851b, iVar.f1852c);
        this.f1842a.b(this.f1843b);
        this.f1843b.a(this.f1844c);
        this.f1842a.b(this.f1843b);
        return this;
    }
}
