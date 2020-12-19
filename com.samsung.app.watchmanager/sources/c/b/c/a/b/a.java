package c.b.c.a.b;

import java.lang.reflect.Array;

public class a {

    /* renamed from: a  reason: collision with root package name */
    static final a f1834a = new a(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f}, false);

    /* renamed from: b  reason: collision with root package name */
    public float[][] f1835b = ((float[][]) Array.newInstance(float.class, 4, 4));

    public a() {
    }

    public a(a aVar) {
        a(aVar);
    }

    public a(float[] fArr, boolean z) {
        a(fArr, z);
    }

    public static a b() {
        a aVar = new a();
        aVar.a();
        return aVar;
    }

    public a a() {
        float[][] fArr = this.f1835b;
        fArr[0][0] = 1.0f;
        fArr[0][1] = 0.0f;
        fArr[0][2] = 0.0f;
        fArr[0][3] = 0.0f;
        fArr[1][0] = 0.0f;
        fArr[1][1] = 1.0f;
        fArr[1][2] = 0.0f;
        fArr[1][3] = 0.0f;
        fArr[2][0] = 0.0f;
        fArr[2][1] = 0.0f;
        fArr[2][2] = 1.0f;
        fArr[2][3] = 0.0f;
        fArr[3][0] = 0.0f;
        fArr[3][1] = 0.0f;
        fArr[3][2] = 0.0f;
        fArr[3][3] = 1.0f;
        return this;
    }

    public a a(float f, float f2, float f3) {
        float[][] fArr = this.f1835b;
        fArr[0][0] = f;
        fArr[0][1] = 0.0f;
        fArr[0][2] = 0.0f;
        fArr[0][3] = 0.0f;
        fArr[1][0] = 0.0f;
        fArr[1][1] = f2;
        fArr[1][2] = 0.0f;
        fArr[1][3] = 0.0f;
        fArr[2][0] = 0.0f;
        fArr[2][1] = 0.0f;
        fArr[2][2] = f3;
        fArr[2][3] = 0.0f;
        fArr[3][0] = 0.0f;
        fArr[3][1] = 0.0f;
        fArr[3][2] = 0.0f;
        fArr[3][3] = 1.0f;
        return this;
    }

    public a a(float f, float f2, float f3, float f4) {
        float tan = 1.0f / ((float) Math.tan((double) (f * 0.5f)));
        float[][] fArr = this.f1835b;
        fArr[0][0] = tan;
        fArr[0][1] = 0.0f;
        fArr[0][2] = 0.0f;
        fArr[0][3] = 0.0f;
        fArr[1][0] = 0.0f;
        fArr[1][1] = tan / f2;
        fArr[1][2] = 0.0f;
        fArr[1][3] = 0.0f;
        fArr[2][0] = 0.0f;
        fArr[2][1] = 0.0f;
        float f5 = f4 - f3;
        fArr[2][2] = (-f3) / f5;
        fArr[2][3] = 1.0f;
        fArr[3][0] = 0.0f;
        fArr[3][1] = 0.0f;
        fArr[3][2] = (f3 * f4) / f5;
        fArr[3][3] = 0.0f;
        return this;
    }

    public a a(a aVar) {
        float[][] fArr = this.f1835b;
        float[] fArr2 = fArr[0];
        float[][] fArr3 = aVar.f1835b;
        fArr2[0] = fArr3[0][0];
        fArr[0][1] = fArr3[0][1];
        fArr[0][2] = fArr3[0][2];
        fArr[0][3] = fArr3[0][3];
        fArr[1][0] = fArr3[1][0];
        fArr[1][1] = fArr3[1][1];
        fArr[1][2] = fArr3[1][2];
        fArr[1][3] = fArr3[1][3];
        fArr[2][0] = fArr3[2][0];
        fArr[2][1] = fArr3[2][1];
        fArr[2][2] = fArr3[2][2];
        fArr[2][3] = fArr3[2][3];
        fArr[3][0] = fArr3[3][0];
        fArr[3][1] = fArr3[3][1];
        fArr[3][2] = fArr3[3][2];
        fArr[3][3] = fArr3[3][3];
        return this;
    }

    public a a(i iVar) {
        float[][] fArr = this.f1835b;
        fArr[0][0] = 1.0f;
        fArr[0][1] = 0.0f;
        fArr[0][2] = 0.0f;
        fArr[0][3] = 0.0f;
        fArr[1][0] = 0.0f;
        fArr[1][1] = 1.0f;
        fArr[1][2] = 0.0f;
        fArr[1][3] = 0.0f;
        fArr[2][0] = 0.0f;
        fArr[2][1] = 0.0f;
        fArr[2][2] = 1.0f;
        fArr[2][3] = 0.0f;
        fArr[3][0] = iVar.f1850a;
        fArr[3][1] = iVar.f1851b;
        fArr[3][2] = iVar.f1852c;
        fArr[3][3] = 1.0f;
        return this;
    }

    public a a(i iVar, float f) {
        double d2 = (double) f;
        float sin = (float) Math.sin(d2);
        float cos = (float) Math.cos(d2);
        float f2 = 1.0f - cos;
        float[][] fArr = this.f1835b;
        float[] fArr2 = fArr[0];
        float f3 = iVar.f1850a;
        fArr2[0] = (f3 * f3 * f2) + cos;
        float[] fArr3 = fArr[0];
        float f4 = iVar.f1851b;
        float f5 = iVar.f1852c;
        fArr3[1] = (f2 * f3 * f4) + (f5 * sin);
        fArr[0][2] = ((f2 * f3) * f5) - (f4 * sin);
        fArr[0][3] = 0.0f;
        fArr[1][0] = ((f2 * f3) * f4) - (f5 * sin);
        fArr[1][1] = (f4 * f4 * f2) + cos;
        fArr[1][2] = (f2 * f4 * f5) + (f3 * sin);
        fArr[1][3] = 0.0f;
        fArr[2][0] = (f2 * f3 * f5) + (f4 * sin);
        fArr[2][1] = ((f4 * f2) * f5) - (f3 * sin);
        fArr[2][2] = (f2 * f5 * f5) + cos;
        fArr[2][3] = 0.0f;
        fArr[3][0] = 0.0f;
        fArr[3][1] = 0.0f;
        fArr[3][2] = 0.0f;
        fArr[3][3] = 1.0f;
        return this;
    }

    public a a(float[] fArr, boolean z) {
        if (z) {
            float[][] fArr2 = this.f1835b;
            fArr2[0][0] = fArr[0];
            fArr2[0][1] = fArr[4];
            fArr2[0][2] = fArr[8];
            fArr2[0][3] = fArr[12];
            fArr2[1][0] = fArr[1];
            fArr2[1][1] = fArr[5];
            fArr2[1][2] = fArr[9];
            fArr2[1][3] = fArr[13];
            fArr2[2][0] = fArr[2];
            fArr2[2][1] = fArr[6];
            fArr2[2][2] = fArr[10];
            fArr2[2][3] = fArr[14];
            fArr2[3][0] = fArr[3];
            fArr2[3][1] = fArr[7];
            fArr2[3][2] = fArr[11];
            fArr2[3][3] = fArr[15];
        } else {
            float[][] fArr3 = this.f1835b;
            fArr3[0][0] = fArr[0];
            fArr3[0][1] = fArr[1];
            fArr3[0][2] = fArr[2];
            fArr3[0][3] = fArr[3];
            fArr3[1][0] = fArr[4];
            fArr3[1][1] = fArr[5];
            fArr3[1][2] = fArr[6];
            fArr3[1][3] = fArr[7];
            fArr3[2][0] = fArr[8];
            fArr3[2][1] = fArr[9];
            fArr3[2][2] = fArr[10];
            fArr3[2][3] = fArr[11];
            fArr3[3][0] = fArr[12];
            fArr3[3][1] = fArr[13];
            fArr3[3][2] = fArr[14];
            fArr3[3][3] = fArr[15];
        }
        return this;
    }

    public a b(float f, float f2, float f3, float f4) {
        float f5 = f * 0.5f;
        float f6 = f2 * 0.5f;
        float[][] fArr = this.f1835b;
        fArr[0][0] = f5;
        fArr[0][1] = 0.0f;
        fArr[0][2] = 0.0f;
        fArr[0][3] = 0.0f;
        fArr[1][0] = 0.0f;
        fArr[1][1] = -f6;
        fArr[1][2] = 0.0f;
        fArr[1][3] = 0.0f;
        fArr[2][0] = 0.0f;
        fArr[2][1] = 0.0f;
        fArr[2][2] = f4 - f3;
        fArr[2][3] = 0.0f;
        fArr[3][0] = f5;
        fArr[3][1] = f6;
        fArr[3][2] = f3;
        fArr[3][3] = 1.0f;
        return this;
    }

    public a b(a aVar) {
        float[][] fArr = this.f1835b;
        float f = fArr[0][0];
        float[][] fArr2 = aVar.f1835b;
        float f2 = (f * fArr2[0][0]) + (fArr[0][1] * fArr2[1][0]) + (fArr[0][2] * fArr2[2][0]) + (fArr[0][3] * fArr2[3][0]);
        float f3 = (fArr[0][0] * fArr2[0][1]) + (fArr[0][1] * fArr2[1][1]) + (fArr[0][2] * fArr2[2][1]) + (fArr[0][3] * fArr2[3][1]);
        float f4 = (fArr[0][0] * fArr2[0][2]) + (fArr[0][1] * fArr2[1][2]) + (fArr[0][2] * fArr2[2][2]) + (fArr[0][3] * fArr2[3][2]);
        float f5 = (fArr[0][0] * fArr2[0][3]) + (fArr[0][1] * fArr2[1][3]) + (fArr[0][2] * fArr2[2][3]) + (fArr[0][3] * fArr2[3][3]);
        float f6 = (fArr[1][0] * fArr2[0][0]) + (fArr[1][1] * fArr2[1][0]) + (fArr[1][2] * fArr2[2][0]) + (fArr[1][3] * fArr2[3][0]);
        float f7 = (fArr[1][0] * fArr2[0][1]) + (fArr[1][1] * fArr2[1][1]) + (fArr[1][2] * fArr2[2][1]) + (fArr[1][3] * fArr2[3][1]);
        float f8 = (fArr[1][0] * fArr2[0][2]) + (fArr[1][1] * fArr2[1][2]) + (fArr[1][2] * fArr2[2][2]) + (fArr[1][3] * fArr2[3][2]);
        float f9 = (fArr[1][0] * fArr2[0][3]) + (fArr[1][1] * fArr2[1][3]) + (fArr[1][2] * fArr2[2][3]) + (fArr[1][3] * fArr2[3][3]);
        float f10 = (fArr[2][0] * fArr2[0][0]) + (fArr[2][1] * fArr2[1][0]) + (fArr[2][2] * fArr2[2][0]) + (fArr[2][3] * fArr2[3][0]);
        float f11 = (fArr[2][0] * fArr2[0][1]) + (fArr[2][1] * fArr2[1][1]) + (fArr[2][2] * fArr2[2][1]) + (fArr[2][3] * fArr2[3][1]);
        float f12 = (fArr[2][0] * fArr2[0][2]) + (fArr[2][1] * fArr2[1][2]) + (fArr[2][2] * fArr2[2][2]) + (fArr[2][3] * fArr2[3][2]);
        float f13 = (fArr[2][0] * fArr2[0][3]) + (fArr[2][1] * fArr2[1][3]) + (fArr[2][2] * fArr2[2][3]) + (fArr[2][3] * fArr2[3][3]);
        float f14 = (fArr[3][0] * fArr2[0][0]) + (fArr[3][1] * fArr2[1][0]) + (fArr[3][2] * fArr2[2][0]) + (fArr[3][3] * fArr2[3][0]);
        float f15 = (fArr[3][0] * fArr2[0][1]) + (fArr[3][1] * fArr2[1][1]) + (fArr[3][2] * fArr2[2][1]) + (fArr[3][3] * fArr2[3][1]);
        float f16 = (fArr[3][0] * fArr2[0][2]) + (fArr[3][1] * fArr2[1][2]) + (fArr[3][2] * fArr2[2][2]) + (fArr[3][3] * fArr2[3][2]);
        fArr[0][0] = f2;
        fArr[0][1] = f3;
        fArr[0][2] = f4;
        fArr[0][3] = f5;
        fArr[1][0] = f6;
        fArr[1][1] = f7;
        fArr[1][2] = f8;
        fArr[1][3] = f9;
        fArr[2][0] = f10;
        fArr[2][1] = f11;
        fArr[2][2] = f12;
        fArr[2][3] = f13;
        fArr[3][0] = f14;
        fArr[3][1] = f15;
        fArr[3][2] = f16;
        fArr[3][3] = (fArr[3][0] * fArr2[0][3]) + (fArr[3][1] * fArr2[1][3]) + (fArr[3][2] * fArr2[2][3]) + (fArr[3][3] * fArr2[3][3]);
        return this;
    }
}
