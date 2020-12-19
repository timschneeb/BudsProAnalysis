package c.b.c.a.a;

import c.b.c.a.a.k;

public class d implements k {

    /* renamed from: a  reason: collision with root package name */
    private static d f1823a;

    private d() {
    }

    public static d a() {
        if (f1823a == null) {
            f1823a = new d();
        }
        return f1823a;
    }

    public float a(float f, float f2, float f3, float f4) {
        return ((f3 * f) / f4) + f2;
    }

    @Override // c.b.c.a.a.k
    public float a(float f, float f2, float f3, float f4, k.a aVar) {
        int i = c.f1822a[aVar.ordinal()];
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? ((f3 * f) / f4) + f2 : d(f, f2, f3, f4) : b(f, f2, f3, f4) : c(f, f2, f3, f4) : a(f, f2, f3, f4);
    }

    public float b(float f, float f2, float f3, float f4) {
        return ((f3 * f) / f4) + f2;
    }

    public float c(float f, float f2, float f3, float f4) {
        return ((f3 * f) / f4) + f2;
    }

    public float d(float f, float f2, float f3, float f4) {
        return ((f3 * f) / f4) + f2;
    }
}
