package c.b.c.a.b;

public class i {

    /* renamed from: a  reason: collision with root package name */
    public float f1850a;

    /* renamed from: b  reason: collision with root package name */
    public float f1851b;

    /* renamed from: c  reason: collision with root package name */
    public float f1852c;

    public i() {
        this.f1850a = 0.0f;
        this.f1851b = 0.0f;
        this.f1852c = 0.0f;
    }

    public i(float f, float f2, float f3) {
        this.f1850a = f;
        this.f1851b = f2;
        this.f1852c = f3;
    }

    public float a() {
        float f = this.f1850a;
        float f2 = this.f1851b;
        float f3 = (f * f) + (f2 * f2);
        float f4 = this.f1852c;
        return (float) Math.sqrt((double) (f3 + (f4 * f4)));
    }

    public i a(float f) {
        this.f1850a *= f;
        this.f1851b *= f;
        this.f1852c *= f;
        return this;
    }

    public i a(float f, float f2, float f3) {
        this.f1850a += f;
        this.f1851b += f2;
        this.f1852c += f3;
        return this;
    }

    public i a(a aVar) {
        float[][] fArr = aVar.f1835b;
        float f = fArr[0][0];
        float f2 = this.f1850a;
        float f3 = fArr[1][0];
        float f4 = this.f1851b;
        float f5 = fArr[2][0];
        float f6 = this.f1852c;
        float f7 = (f * f2) + (f3 * f4) + (f5 * f6) + fArr[3][0];
        float f8 = (fArr[0][1] * f2) + (fArr[1][1] * f4) + (fArr[2][1] * f6) + fArr[3][1];
        float f9 = 1.0f / ((((fArr[0][3] * f2) + (fArr[1][3] * f4)) + (fArr[2][3] * f6)) + fArr[3][3]);
        this.f1850a = f7 * f9;
        this.f1851b = f8 * f9;
        this.f1852c = ((fArr[0][2] * f2) + (fArr[1][2] * f4) + (fArr[2][2] * f6) + fArr[3][2]) * f9;
        return this;
    }

    public i a(i iVar) {
        this.f1850a += iVar.f1850a;
        this.f1851b += iVar.f1851b;
        this.f1852c += iVar.f1852c;
        return this;
    }

    public i a(i iVar, float f) {
        this.f1850a = b.a(f, this.f1850a, iVar.f1850a);
        this.f1851b = b.a(f, this.f1851b, iVar.f1851b);
        this.f1852c = b.a(f, this.f1852c, iVar.f1852c);
        return this;
    }

    public i b() {
        float f = this.f1850a;
        float f2 = this.f1851b;
        float f3 = (f * f) + (f2 * f2);
        float f4 = this.f1852c;
        float sqrt = 1.0f / ((float) Math.sqrt((double) (f3 + (f4 * f4))));
        this.f1850a *= sqrt;
        this.f1851b *= sqrt;
        this.f1852c *= sqrt;
        return this;
    }

    public i b(float f, float f2, float f3) {
        this.f1850a = f;
        this.f1851b = f2;
        this.f1852c = f3;
        return this;
    }

    public i b(i iVar) {
        this.f1850a = iVar.f1850a;
        this.f1851b = iVar.f1851b;
        this.f1852c = iVar.f1852c;
        return this;
    }

    public i c(i iVar) {
        this.f1850a *= iVar.f1850a;
        this.f1851b *= iVar.f1851b;
        this.f1852c *= iVar.f1852c;
        return this;
    }

    public i d(i iVar) {
        this.f1850a = iVar.f1850a;
        this.f1851b = iVar.f1851b;
        this.f1852c = iVar.f1852c;
        return this;
    }

    public i e(i iVar) {
        this.f1850a -= iVar.f1850a;
        this.f1851b -= iVar.f1851b;
        this.f1852c -= iVar.f1852c;
        return this;
    }
}
