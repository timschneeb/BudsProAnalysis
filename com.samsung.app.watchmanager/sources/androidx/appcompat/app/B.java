package androidx.appcompat.app;

/* access modifiers changed from: package-private */
public class B {

    /* renamed from: a  reason: collision with root package name */
    private static B f143a;

    /* renamed from: b  reason: collision with root package name */
    public long f144b;

    /* renamed from: c  reason: collision with root package name */
    public long f145c;

    /* renamed from: d  reason: collision with root package name */
    public int f146d;

    B() {
    }

    static B a() {
        if (f143a == null) {
            f143a = new B();
        }
        return f143a;
    }

    public void a(long j, double d2, double d3) {
        float f = ((float) (j - 946728000000L)) / 8.64E7f;
        float f2 = (0.01720197f * f) + 6.24006f;
        double d4 = (double) f2;
        Double.isNaN(d4);
        double sin = (Math.sin(d4) * 0.03341960161924362d) + d4 + (Math.sin((double) (2.0f * f2)) * 3.4906598739326E-4d) + (Math.sin((double) (f2 * 3.0f)) * 5.236000106378924E-6d) + 1.796593063d + 3.141592653589793d;
        double d5 = (-d3) / 360.0d;
        double d6 = (double) (f - 9.0E-4f);
        Double.isNaN(d6);
        double round = (double) (((float) Math.round(d6 - d5)) + 9.0E-4f);
        Double.isNaN(round);
        double sin2 = round + d5 + (Math.sin(d4) * 0.0053d) + (Math.sin(2.0d * sin) * -0.0069d);
        double asin = Math.asin(Math.sin(sin) * Math.sin(0.4092797040939331d));
        double d7 = 0.01745329238474369d * d2;
        double sin3 = (Math.sin(-0.10471975803375244d) - (Math.sin(d7) * Math.sin(asin))) / (Math.cos(d7) * Math.cos(asin));
        if (sin3 >= 1.0d) {
            this.f146d = 1;
        } else if (sin3 <= -1.0d) {
            this.f146d = 0;
        } else {
            double acos = (double) ((float) (Math.acos(sin3) / 6.283185307179586d));
            Double.isNaN(acos);
            this.f144b = Math.round((sin2 + acos) * 8.64E7d) + 946728000000L;
            Double.isNaN(acos);
            this.f145c = Math.round((sin2 - acos) * 8.64E7d) + 946728000000L;
            if (this.f145c >= j || this.f144b <= j) {
                this.f146d = 1;
                return;
            } else {
                this.f146d = 0;
                return;
            }
        }
        this.f144b = -1;
        this.f145c = -1;
    }
}
