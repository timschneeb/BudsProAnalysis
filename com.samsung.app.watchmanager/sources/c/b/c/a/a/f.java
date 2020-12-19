package c.b.c.a.a;

import c.b.c.a.a.k;

public class f implements k {

    /* renamed from: a  reason: collision with root package name */
    private static f f1825a;

    private f() {
    }

    public static f a() {
        if (f1825a == null) {
            f1825a = new f();
        }
        return f1825a;
    }

    public float a(float f, float f2, float f3, float f4) {
        float f5 = f / f4;
        return (f3 * f5 * f5) + f2;
    }

    @Override // c.b.c.a.a.k
    public float a(float f, float f2, float f3, float f4, k.a aVar) {
        int i = e.f1824a[aVar.ordinal()];
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? ((f3 * f) / f4) + f2 : d(f, f2, f3, f4) : b(f, f2, f3, f4) : c(f, f2, f3, f4) : a(f, f2, f3, f4);
    }

    public float b(float f, float f2, float f3, float f4) {
        float f5;
        float f6 = f / (f4 / 2.0f);
        if (f6 < 1.0f) {
            f5 = (f3 / 2.0f) * f6;
        } else {
            f5 = (-f3) / 2.0f;
            float f7 = f6 - 1.0f;
            f6 = (f7 * (f7 - 2.0f)) - 1.0f;
        }
        return (f5 * f6) + f2;
    }

    public float c(float f, float f2, float f3, float f4) {
        float f5 = f / f4;
        return ((-f3) * f5 * (f5 - 2.0f)) + f2;
    }

    public float d(float f, float f2, float f3, float f4) {
        if (f < f4 / 2.0f) {
            float f5 = (f * 2.0f) / f4;
            return ((-(f3 / 2.0f)) * f5 * (f5 - 2.0f)) + f2;
        }
        float f6 = f3 / 2.0f;
        float f7 = ((f * 2.0f) - f4) / f4;
        return (f6 * f7 * f7) + f2 + f6;
    }
}
