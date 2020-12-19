package c.b.c.a.a;

import c.b.c.a.a.k;

public class j implements k {

    /* renamed from: a  reason: collision with root package name */
    private static j f1829a;

    private j() {
    }

    public static j a() {
        if (f1829a == null) {
            f1829a = new j();
        }
        return f1829a;
    }

    public float a(float f, float f2, float f3, float f4) {
        double d2 = (double) (f / f4);
        Double.isNaN(d2);
        return ((-f3) * ((float) Math.cos(d2 * 1.5707963267948966d))) + f3 + f2;
    }

    @Override // c.b.c.a.a.k
    public float a(float f, float f2, float f3, float f4, k.a aVar) {
        int i = i.f1828a[aVar.ordinal()];
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? ((f3 * f) / f4) + f2 : d(f, f2, f3, f4) : b(f, f2, f3, f4) : c(f, f2, f3, f4) : a(f, f2, f3, f4);
    }

    public float b(float f, float f2, float f3, float f4) {
        double d2 = (double) f;
        Double.isNaN(d2);
        double d3 = (double) f4;
        Double.isNaN(d3);
        return (((-f3) / 2.0f) * (((float) Math.cos((d2 * 3.141592653589793d) / d3)) - 1.0f)) + f2;
    }

    public float c(float f, float f2, float f3, float f4) {
        double d2 = (double) (f / f4);
        Double.isNaN(d2);
        return (f3 * ((float) Math.sin(d2 * 1.5707963267948966d))) + f2;
    }

    public float d(float f, float f2, float f3, float f4) {
        if (f < f4 / 2.0f) {
            double d2 = (double) ((f * 2.0f) / f4);
            Double.isNaN(d2);
            return ((f3 / 2.0f) * ((float) Math.sin(d2 * 1.5707963267948966d))) + f2;
        }
        float f5 = f3 / 2.0f;
        double d3 = (double) (((f * 2.0f) - f4) / f4);
        Double.isNaN(d3);
        return ((-f5) * ((float) Math.cos(d3 * 1.5707963267948966d))) + f5 + f2 + f5;
    }
}
