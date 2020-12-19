package c.b.c.b.a;

import android.graphics.Color;
import c.b.c.a.b.h;
import c.b.c.a.b.i;
import c.b.c.b.a.a.b;
import c.b.c.b.a.a.c;

public class d extends b {
    protected float i;
    protected float j;
    protected float k;
    protected float l;
    protected float[] m = {1.0f, 1.0f};
    protected float[] n = {0.4f, 0.7f};
    protected float[] o = {0.4f, 0.6f};
    protected i p = new i();
    protected i q = new i();
    protected i r = new i();
    protected i s = new i();

    public class a extends c {
        protected float m;
        protected float n;
        protected int o;
        protected int p;

        public a(b bVar) {
            super(bVar);
            j();
        }

        public a a(int i) {
            this.o = i;
            this.p = i;
            return this;
        }

        public a g(float f) {
            this.m = f;
            this.n = f;
            return this;
        }

        /* access modifiers changed from: protected */
        public a j() {
            return this;
        }
    }

    public d() {
        a(5.0f);
        c(100.0f, 200.0f);
    }

    public d a(float f) {
        a(0.0f, f);
        return this;
    }

    public d a(float f, float f2) {
        this.i = f;
        this.j = f2;
        return this;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    @Override // c.b.c.b.a.a.b
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(android.graphics.Canvas r11, android.graphics.Paint r12, com.samsung.td.particlesystem.GL.b r13, c.b.c.b.a.a.a r14, c.b.c.b.a.a.c r15) {
        /*
        // Method dump skipped, instructions count: 146
        */
        throw new UnsupportedOperationException("Method not decompiled: c.b.c.b.a.d.a(android.graphics.Canvas, android.graphics.Paint, com.samsung.td.particlesystem.GL.b, c.b.c.b.a.a.a, c.b.c.b.a.a.c):void");
    }

    @Override // c.b.c.b.a.a.b
    public void a(c cVar) {
    }

    public d b(float f, float f2) {
        float[] fArr = this.o;
        fArr[0] = f;
        fArr[1] = f2;
        return this;
    }

    @Override // c.b.c.b.a.a.b
    public void b(c cVar) {
    }

    public d c(float f, float f2) {
        this.k = f;
        this.l = f2;
        return this;
    }

    public d d(float f, float f2) {
        float[] fArr = this.n;
        fArr[0] = f;
        fArr[1] = f2;
        return this;
    }

    @Override // c.b.c.b.a.a.b
    public void d(c cVar) {
        cVar.e().a(h.a(cVar.b(), cVar.f()));
    }

    public float e() {
        return this.i;
    }

    @Override // c.b.c.b.a.a.b
    public c e(c cVar) {
        c.b.c.a.b.b a2 = c.b.c.a.b.b.a();
        a aVar = cVar == null ? new a(this) : (a) cVar;
        float e = e();
        float f = f();
        int a3 = a2.a(0, this.h.length - 1);
        float[] fArr = this.m;
        int[][] iArr = this.h;
        int argb = Color.argb((int) (((float) this.h[a3][3]) * a2.a(fArr[0], fArr[1])), iArr[a3][0], iArr[a3][1], iArr[a3][2]);
        aVar.k = (char) a3;
        aVar.g(a2.a(this.k, this.l));
        aVar.a(argb);
        i iVar = this.q;
        iVar.b(a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f));
        iVar.b();
        iVar.a(a2.a(f, e));
        iVar.a(this.p);
        aVar.b(iVar);
        float[] fArr2 = this.o;
        aVar.e(a2.a(fArr2[0], fArr2[1]));
        i iVar2 = this.q;
        iVar2.b(a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f), a2.a(-1.0f, 1.0f));
        iVar2.b();
        aVar.a(iVar2);
        float[] fArr3 = this.n;
        aVar.f(a2.a(fArr3[0], fArr3[1]));
        aVar.a((char) 3);
        return aVar;
    }

    public float f() {
        return this.j;
    }
}
