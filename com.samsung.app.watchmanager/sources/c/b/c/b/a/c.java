package c.b.c.b.a;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import c.b.c.a.a.j;
import c.b.c.a.a.k;
import c.b.c.a.b.i;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class c extends c.b.c.b.a.a.b {
    protected float[] A;
    protected float[] B;
    float C;
    k D;
    k.a E;
    k F;
    k.a G;
    protected float H;
    protected float I;
    protected i J;
    protected i K;
    protected i L;
    protected Random M;
    int i = 1;
    boolean j;
    boolean k;
    boolean l;
    boolean m;
    HashMap<Integer, a> n = new HashMap<>();
    int o;
    protected float p;
    protected float q;
    protected float r;
    protected float s;
    protected float t;
    protected float u;
    protected float v;
    protected float[] w;
    protected float[] x;
    protected float[] y;
    protected float[] z;

    public class a {

        /* renamed from: a  reason: collision with root package name */
        ArrayList<C0034a> f1883a = new ArrayList<>();

        /* renamed from: b  reason: collision with root package name */
        int f1884b;

        /* renamed from: c  reason: collision with root package name */
        int f1885c;

        /* renamed from: c.b.c.b.a.c$a$a  reason: collision with other inner class name */
        public class C0034a {

            /* renamed from: a  reason: collision with root package name */
            public a f1887a;

            /* renamed from: b  reason: collision with root package name */
            public float f1888b;

            /* renamed from: c  reason: collision with root package name */
            public float f1889c;

            /* renamed from: d  reason: collision with root package name */
            public int f1890d;

            public C0034a() {
            }
        }

        a(Bitmap bitmap, float f) {
            a(bitmap, f);
        }

        public ArrayList<C0034a> a() {
            return this.f1883a;
        }

        /* access modifiers changed from: package-private */
        public void a(Bitmap bitmap, float f) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            this.f1884b = width;
            this.f1885c = height;
            float f2 = (float) width;
            float f3 = f2 * 0.5f;
            float f4 = ((float) height) * 0.5f;
            float f5 = f / f2;
            int[] iArr = new int[(width * height)];
            bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
            for (int i = 0; i < height; i++) {
                int i2 = width * i;
                for (int i3 = 0; i3 < width; i3++) {
                    int i4 = i2 + i3;
                    if (((char) Color.alpha(iArr[i4])) > 0) {
                        C0034a aVar = new C0034a();
                        aVar.f1890d = iArr[i4];
                        aVar.f1888b = (((float) i3) - f3) * f5;
                        aVar.f1889c = (((float) i) - f4) * (-f5);
                        aVar.f1887a = this;
                        this.f1883a.add(aVar);
                    }
                }
            }
        }

        public C0034a b() {
            if (this.f1883a.size() > 0) {
                ArrayList<C0034a> arrayList = this.f1883a;
                return arrayList.get(Math.abs(c.this.M.nextInt(arrayList.size() - 1)));
            }
            if (c.this.k) {
            }
            return null;
        }
    }

    public class b extends c.b.c.b.a.a.c {
        float m;
        float n;
        float o;
        float p;
        int q;
        int r;
        int s;
        char t;
        i u = new i();
        i v = new i();
        a.C0034a w = null;

        public b(c.b.c.b.a.a.b bVar) {
            super(bVar);
        }

        public b a(int i) {
            a(i, i);
            return this;
        }

        public b a(int i, int i2) {
            this.q = i;
            this.r = i2;
            this.s = i;
            return this;
        }

        public boolean a(a.C0034a aVar) {
            if (aVar == null) {
                return false;
            }
            if (!c.this.k) {
                return true;
            }
            p();
            this.w = aVar;
            aVar.f1887a.f1883a.add(aVar);
            return true;
        }

        public b b(int i) {
            this.t = (char) i;
            return this;
        }

        public b c(float f, float f2) {
            this.m = f;
            this.n = f2;
            this.o = f;
            return this;
        }

        public b g(float f) {
            this.p = f;
            return this;
        }

        public b h(float f) {
            c(f, f);
            return this;
        }

        @Override // c.b.c.b.a.a.c
        public b i() {
            super.i();
            p();
            return this;
        }

        public int j() {
            return this.s;
        }

        public float k() {
            return this.o;
        }

        public char l() {
            return this.t;
        }

        public i m() {
            return this.u;
        }

        public i n() {
            return this.v;
        }

        public a.C0034a o() {
            return this.w;
        }

        public boolean p() {
            a.C0034a aVar;
            if (!c.this.k || (aVar = this.w) == null) {
                return false;
            }
            aVar.f1887a.a().remove(this.w);
            this.w = null;
            return true;
        }
    }

    public c(Bitmap[] bitmapArr) {
        for (int i2 = 0; i2 < bitmapArr.length; i2++) {
            this.n.put(Integer.valueOf(i2), new a(bitmapArr[i2], 1.0f));
        }
        d(100.0f, 200.0f);
        a(5.0f);
        this.M = new Random();
        this.o = 0;
        this.p = 1.2f;
        this.q = 1.0f;
        this.j = true;
        this.s = 1.0f;
        this.t = 0.5f;
        this.k = false;
        this.u = 0.0f;
        this.v = 1.0f;
        this.x = new float[]{2.0f, 2.8f};
        this.w = new float[]{0.5f, 0.8f};
        this.y = new float[]{0.01f, 0.017f};
        this.z = new float[]{0.004f, 0.006f};
        this.A = new float[]{0.004f, 0.005f};
        this.B = new float[]{4.0f, 5.0f};
        b(j.a(), k.a.Out);
        a(j.a(), k.a.InOut);
        a(new int[][]{new int[]{255, 255, 255, 255}, new int[]{255, 0, 0, 0}});
        this.J = new i();
        this.K = new i();
        this.L = new i();
        a(1, false);
    }

    public c a(float f) {
        this.C = f;
        return this;
    }

    public c a(float f, float f2) {
        Iterator<c.b.c.b.a.a.c> it = d().iterator();
        while (it.hasNext()) {
            it.next().c(f);
        }
        a(5, true);
        this.s = f2;
        this.t = f;
        return this;
    }

    public c a(int i2, float f, boolean z2, boolean z3) {
        this.l = z2;
        this.m = z3;
        this.o = i2;
        this.q = f;
        this.j = true;
        return this;
    }

    public c a(int i2, boolean z2) {
        Iterator<c.b.c.b.a.a.c> it = d().iterator();
        while (it.hasNext()) {
            b bVar = (b) it.next();
            if (!(bVar.d() == 0 || bVar.l() == i2)) {
                bVar.b(i2);
                bVar.a(b(i2));
                if (z2) {
                    e(bVar);
                }
            }
        }
        this.j = true;
        return this;
    }

    public c a(k kVar, k.a aVar) {
        this.D = kVar;
        this.E = aVar;
        return this;
    }

    @Override // c.b.c.b.a.a.b
    public void a(Canvas canvas, Paint paint, com.samsung.td.particlesystem.GL.b bVar, c.b.c.b.a.a.a aVar, c.b.c.b.a.a.c cVar) {
        Paint paint2;
        b bVar2 = (b) cVar;
        i g = bVar2.g();
        float f = g.f1852c;
        float alpha = ((float) Color.alpha(bVar2.s)) * aVar.c() * Math.min(0.8f + f, 1.0f);
        int argb = Color.argb((int) alpha, Color.red(bVar2.s), Color.green(bVar2.s), Color.blue(bVar2.s));
        float f2 = bVar2.o;
        paint.setColor(argb);
        if (f >= 0.0f) {
            float f3 = f2 * f * 0.07f;
            char l2 = bVar2.l();
            if (l2 == 3 || l2 == 4 || l2 == 5) {
                if (alpha > 0.0f) {
                    paint2 = this.g[bVar2.k];
                } else {
                    return;
                }
            } else if (alpha > 0.0f) {
                paint2 = this.g[bVar2.k];
            } else {
                return;
            }
            paint2.setAlpha(paint.getAlpha());
            bVar.a(canvas, paint2, g, f3);
        }
    }

    @Override // c.b.c.b.a.a.b
    public void a(c.b.c.b.a.a.c cVar) {
    }

    /* access modifiers changed from: package-private */
    public boolean a(b bVar) {
        char l2 = bVar.l();
        return l2 == 4 || l2 == 5 || l2 == 6;
    }

    public char b(int i2) {
        if (i2 != 1) {
            return (i2 == 4 || i2 == 5 || i2 == 6) ? (char) 2 : 1;
        }
        return 3;
    }

    /* access modifiers changed from: package-private */
    public b b(b bVar) {
        b bVar2;
        boolean z2;
        float f;
        float f2;
        c.b.c.a.b.b a2 = c.b.c.a.b.b.a();
        int a3 = a2.a(0, this.h.length - 1);
        if (bVar == null) {
            b bVar3 = new b(this);
            bVar3.b(this.i);
            bVar2 = bVar3;
            z2 = false;
        } else {
            bVar2 = bVar;
            z2 = true;
        }
        if (!z2) {
            bVar2.k = (char) a3;
        }
        char l2 = bVar2.l();
        if (l2 != 1) {
            if (l2 != 3) {
                if (l2 == 4 || l2 == 5 || l2 == 6) {
                    a.C0034a b2 = this.n.get(Integer.valueOf(this.o)).b();
                    bVar2.w = b2;
                    int[][] iArr = this.h;
                    int i2 = iArr[a3][3];
                    int argb = this.l ? Color.argb(i2, Color.red(b2.f1890d), Color.green(b2.f1890d), Color.blue(b2.f1890d)) : Color.argb(i2, iArr[a3][0], iArr[a3][1], iArr[a3][2]);
                    float a4 = a2.a(this.H, this.I) * this.p;
                    i e = bVar2.e();
                    if (z2) {
                        bVar2.a(bVar2.j(), argb);
                        bVar2.c(bVar2.k(), a4);
                        f2 = 0.0f;
                    } else {
                        e.b((b2.f1888b * this.q) + (a2.a(-1.0f, 1.0f) * this.u), (b2.f1889c * this.q) + (a2.a(-1.0f, 1.0f) * this.u), a2.a(-1.0f, 1.0f) * this.u);
                        bVar2.a(argb);
                        bVar2.h(a4);
                        f2 = 0.0f;
                        bVar2.f(0.0f);
                    }
                    bVar2.a(b(4));
                    bVar2.a(b2);
                    i m2 = bVar2.m();
                    float f3 = b2.f1888b;
                    float f4 = this.q;
                    m2.b(f3 * f4, b2.f1889c * f4, f2);
                    bVar2.n().b(e);
                    char l3 = bVar2.l();
                    if (l3 == 4) {
                        f = this.v;
                        bVar2.e(f);
                    } else if (l3 == 6) {
                        i iVar = this.J;
                        iVar.b(bVar2.m());
                        iVar.e(e);
                        float a5 = this.J.a();
                        this.J.a(1.0f / a5);
                        bVar2.b().d(this.J);
                        bVar2.f(a5 * 0.01f);
                    }
                } else {
                    float a6 = a2.a(this.H, this.I) * this.p;
                    int[][] iArr2 = this.h;
                    int argb2 = Color.argb(iArr2[a3][3], iArr2[a3][0], iArr2[a3][1], iArr2[a3][2]);
                    i e2 = bVar2.e();
                    if (z2) {
                        bVar2.a(bVar2.j(), argb2);
                        bVar2.c(bVar2.k(), a6);
                    } else {
                        c.b.c.a.b.b a7 = c.b.c.a.b.b.a();
                        float[] fArr = this.B;
                        float a8 = a7.a(fArr[0], fArr[1]);
                        e2.b(a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f));
                        e2.b();
                        e2.a(a2.a(a8 * 0.8f, a8));
                        e2.a(0.0f, 0.0f, 0.0f);
                        bVar2.a(argb2);
                        bVar2.h(a6);
                    }
                    float[] fArr2 = this.A;
                    bVar2.g(a2.a(fArr2[0], fArr2[1]));
                    float[] fArr3 = this.w;
                    bVar2.e(a2.a(fArr3[0], fArr3[1]));
                    float[] fArr4 = this.y;
                    bVar2.f(a2.a(fArr4[0], fArr4[1]));
                    bVar2.a(b(2));
                    bVar2.b().b(a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f));
                }
            }
            float a9 = a2.a(this.H, this.I) * this.p;
            int[][] iArr3 = this.h;
            int argb3 = Color.argb(iArr3[a3][3], iArr3[a3][0], iArr3[a3][1], iArr3[a3][2]);
            i e3 = bVar2.e();
            if (z2) {
                bVar2.a(bVar2.j(), argb3);
                bVar2.c(bVar2.k(), a9);
            } else {
                float f5 = this.C;
                e3.b(a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f));
                e3.b();
                e3.a(a2.a(f5 * 0.8f, f5));
                e3.a(0.0f, 0.0f, 0.0f);
                bVar2.a(argb3);
                bVar2.h(a9);
            }
            i iVar2 = this.J;
            iVar2.d(e3);
            iVar2.b();
            c.b.c.a.b.b a10 = c.b.c.a.b.b.a();
            float[] fArr5 = this.B;
            float a11 = a10.a(fArr5[0], fArr5[1]);
            i m3 = bVar2.m();
            i iVar3 = this.J;
            m3.b((iVar3.f1850a * a11) + e3.f1850a, (iVar3.f1851b * a11) + e3.f1851b, (iVar3.f1852c * a11) + e3.f1852c);
            bVar2.n().b(e3);
            bVar2.a(b(2));
            float[] fArr6 = this.w;
            f = a2.a(fArr6[0], fArr6[1]);
            bVar2.e(f);
        } else {
            float a12 = a2.a(this.H, this.I) * this.p;
            int[][] iArr4 = this.h;
            int argb4 = Color.argb(iArr4[a3][3], iArr4[a3][0], iArr4[a3][1], iArr4[a3][2]);
            i e4 = bVar2.e();
            float f6 = this.C;
            e4.b(a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f));
            e4.b();
            e4.a(a2.a(0.3f, f6));
            e4.a(0.0f, 0.0f, 0.0f);
            if (z2) {
                bVar2.a(bVar2.j(), argb4);
            } else {
                bVar2.a(argb4);
            }
            bVar2.h(a12);
            float[] fArr7 = this.x;
            bVar2.e(a2.a(fArr7[0], fArr7[1]));
            float[] fArr8 = this.z;
            bVar2.f(a2.a(fArr8[0], fArr8[1]));
            bVar2.a(b(1));
            bVar2.b().b(a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f));
        }
        if (l2 == 5) {
            bVar2.c(1.0f);
        } else if (z2) {
            bVar2.c(0.0f);
        } else {
            bVar2.c(-1.0f);
        }
        return bVar2;
    }

    public c b(float f) {
        this.v = f;
        return this;
    }

    public c b(float f, float f2) {
        float[] fArr = this.B;
        fArr[0] = f;
        fArr[1] = f2;
        return this;
    }

    public c b(k kVar, k.a aVar) {
        this.F = kVar;
        this.G = aVar;
        return this;
    }

    @Override // c.b.c.b.a.a.b
    public void b(c.b.c.b.a.a.c cVar) {
        b bVar = (b) cVar;
        if (bVar.o() != null && this.m && a(bVar)) {
            cVar.a(cVar.a() * ((float) Color.alpha(bVar.o().f1890d)) * 0.003921569f * 1.0f * f());
        }
    }

    public c c(float f) {
        this.u = f;
        return this;
    }

    public c c(float f, float f2) {
        float[] fArr = this.w;
        fArr[0] = f;
        fArr[1] = f;
        return this;
    }

    public c c(int i2) {
        this.i = i2;
        return this;
    }

    public c d(float f) {
        this.r = f;
        return this;
    }

    public c d(float f, float f2) {
        this.H = f;
        this.I = f2;
        return this;
    }

    @Override // c.b.c.b.a.a.b
    public void d(c.b.c.b.a.a.c cVar) {
        i iVar;
        b bVar = (b) cVar;
        float c2 = bVar.c();
        float f = c2 + 1.0f;
        float f2 = 0.0f;
        float max = Math.max(Math.min(f, 1.0f), 0.0f);
        float max2 = Math.max(Math.min(c2, 1.0f), 0.0f);
        float max3 = Math.max(Math.min(c2 - 1.0f, 1.0f), 0.0f);
        int b2 = c.b.c.a.b.b.b(max2, bVar.q, bVar.r);
        float a2 = c.b.c.a.b.b.a(max2, bVar.m, bVar.n);
        bVar.s = Color.argb((int) (((float) Color.alpha(b2)) * 0.00393f * max * (1.0f - max3) * 255.0f), Color.red(b2), Color.green(b2), Color.blue(b2));
        bVar.o = a2 * j.a().c(max3, 1.0f, -0.5f, 1.0f);
        char l2 = bVar.l();
        if (!(l2 == 1 || l2 == 2)) {
            if (l2 != 4) {
                if (l2 != 5) {
                    float a3 = this.D.a(Math.min(c2 * 0.5f, 1.0f), 0.0f, 1.0f, 1.0f, this.E);
                    i iVar2 = this.J;
                    iVar2.b(bVar.n());
                    iVar2.a(bVar.m(), a3);
                    Log.i("ParticleLibrary", "Explosion Interpolation Val = " + a3);
                    cVar.b(this.J);
                } else {
                    float f3 = this.s * 0.5f;
                    float alpha = 1.0f - ((((float) Color.alpha(bVar.o().f1890d)) * 0.003921569f) * 1.0f);
                    if (alpha < Math.max(this.t - f3, 0.0f) || alpha > Math.min(this.t + f3, 1.0f)) {
                        max2 = 0.0f;
                    } else {
                        f2 = 1.0f;
                        max2 = 1.0f;
                    }
                    bVar.s = Color.argb((int) (f2 * 255.0f), Color.red(b2), Color.green(b2), Color.blue(b2));
                }
            }
            if (max2 >= 1.0f) {
                iVar = bVar.m();
            } else {
                float a4 = this.F.a(Math.min(f * 0.5f, 1.0f), 0.0f, 1.0f, 1.0f, this.G);
                i iVar3 = this.J;
                iVar3.b(bVar.n());
                iVar3.a(bVar.m(), a4);
                iVar = this.J;
            }
            cVar.b(iVar);
            return;
        }
        i iVar4 = this.J;
        iVar4.b(cVar.b());
        iVar4.a(cVar.f());
        cVar.e().a(this.J);
    }

    @Override // c.b.c.b.a.a.b
    public c.b.c.b.a.a.c e(c.b.c.b.a.a.c cVar) {
        if (cVar != null) {
            return cVar.d() == 0 ? cVar : b((b) cVar);
        }
        b b2 = b((b) cVar);
        d(b2);
        return b2;
    }

    public c e(float f) {
        this.p = f;
        return this;
    }

    public HashMap<Integer, a> e() {
        return this.n;
    }

    public float f() {
        return this.r;
    }
}
