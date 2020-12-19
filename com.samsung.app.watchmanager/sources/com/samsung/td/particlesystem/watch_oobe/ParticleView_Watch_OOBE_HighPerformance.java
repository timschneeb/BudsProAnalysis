package com.samsung.td.particlesystem.watch_oobe;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import c.b.c.a.a.j;
import c.b.c.a.a.k;
import c.b.c.a.b.e;
import c.b.c.a.b.f;
import c.b.c.a.b.h;
import c.b.c.a.b.i;
import c.b.c.a.b.l;
import c.b.c.a.b.m;
import c.b.c.b.a.b;
import c.b.c.b.a.c;
import c.b.c.b.a.d;
import com.samsung.android.app.twatchmanager.smartswitch.SmartSwitchConstants;
import com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base;

public class ParticleView_Watch_OOBE_HighPerformance extends ParticleView_Watch_OOBE_Base {
    c.b.c.b.a.a.a A;
    d B;
    d C;
    c D;
    b[] E;
    Bitmap F;
    Handler G;
    Handler H;
    Handler I;
    c.b.c.a.c.b J;
    f K;
    f L;
    f M;
    l N;
    l O;
    l P;
    l Q;
    l R;
    i S;
    Paint T;
    int[][] U;
    a[] V;
    boolean W;
    int q;
    int r;
    int s;
    int t;
    int u;
    float v;
    Object w;
    boolean x;
    c.b.c.b.a.a.a y;
    c.b.c.b.a.a.a z;

    /* access modifiers changed from: package-private */
    public class a {

        /* renamed from: a  reason: collision with root package name */
        int f1925a;

        /* renamed from: b  reason: collision with root package name */
        float f1926b;

        /* renamed from: c  reason: collision with root package name */
        float f1927c;

        /* renamed from: d  reason: collision with root package name */
        float f1928d = 0.0f;
        boolean e;
        float f;
        String g;
        int h;
        float i = 0.0f;
        float j = 1.0f;
        float k = 1.0f;

        a(int i2, String str, int i3, float f2, float f3, boolean z, float f4) {
            this.f1925a = i2;
            this.f1926b = f2;
            this.e = z;
            this.f = f4;
            this.f1927c = f3;
            this.g = str;
            this.h = i3;
        }
    }

    public ParticleView_Watch_OOBE_HighPerformance(Context context) {
        super(context);
        this.w = null;
        this.x = false;
        this.G = null;
        this.H = null;
        this.I = null;
        this.K = null;
        this.L = null;
        this.M = null;
        this.U = new int[][]{new int[]{178, 178, 178, 255}, new int[]{127, 127, 127, 255}, new int[]{102, 102, 102, 255}, new int[]{76, 89, 89, 255}};
        this.V = new a[]{new a(c.b.c.b.b.a.formingmap_watch, null, 2, 4.7f, 0.2f, true, 3.0f)};
        this.W = true;
    }

    public ParticleView_Watch_OOBE_HighPerformance(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.w = null;
        this.x = false;
        this.G = null;
        this.H = null;
        this.I = null;
        this.K = null;
        this.L = null;
        this.M = null;
        this.U = new int[][]{new int[]{178, 178, 178, 255}, new int[]{127, 127, 127, 255}, new int[]{102, 102, 102, 255}, new int[]{76, 89, 89, 255}};
        this.V = new a[]{new a(c.b.c.b.b.a.formingmap_watch, null, 2, 4.7f, 0.2f, true, 3.0f)};
        this.W = true;
    }

    public ParticleView_Watch_OOBE_HighPerformance(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.w = null;
        this.x = false;
        this.G = null;
        this.H = null;
        this.I = null;
        this.K = null;
        this.L = null;
        this.M = null;
        this.U = new int[][]{new int[]{178, 178, 178, 255}, new int[]{127, 127, 127, 255}, new int[]{102, 102, 102, 255}, new int[]{76, 89, 89, 255}};
        this.V = new a[]{new a(c.b.c.b.b.a.formingmap_watch, null, 2, 4.7f, 0.2f, true, 3.0f)};
        this.W = true;
    }

    /* access modifiers changed from: package-private */
    public void a(a[] aVarArr, int i, int i2, int i3, int i4) {
        b bVar;
        a aVar;
        c.b.c.a.b.a aVar2 = new c.b.c.a.b.a();
        float f = (float) i2;
        float f2 = (float) i;
        aVar2.a(0.9424779f, f / f2, this.e, this.f1924d);
        c.b.c.a.b.a aVar3 = new c.b.c.a.b.a();
        float f3 = (float) i3;
        float f4 = (float) i4;
        float f5 = 0.0f;
        aVar3.b(f3, f4, 0.0f, 1.0f);
        c.b.c.a.b.a aVar4 = new c.b.c.a.b.a();
        aVar4.a(f2 / f3, f / f4, 1.0f);
        c.b.c.a.b.a aVar5 = new c.b.c.a.b.a(this.f);
        aVar5.b(aVar2);
        aVar5.b(aVar4);
        aVar5.b(aVar3);
        e a2 = this.K.a(0);
        a2.a(this.S, 1.0f, c.b.c.a.b.b.f, 0.0f);
        new ArgbEvaluator().evaluate(0.5f, -65536, -16776961);
        b[] bVarArr = this.E;
        if (bVarArr != null) {
            for (b bVar2 : bVarArr) {
                bVar2.a();
            }
        }
        this.E = new b[aVarArr.length];
        c.b.c.b.a.a.a aVar6 = new c.b.c.b.a.a.a(getContext());
        aVar6.b(a2.c());
        aVar6.a(false);
        this.l.b();
        int i5 = 0;
        while (i5 < aVarArr.length) {
            a aVar7 = aVarArr[i5];
            String str = aVar7.g;
            if (str == null) {
                this.D.a((int) Math.min(((float) (this.D.e().get(Integer.valueOf(i5)).a().size() / 25)) * aVar7.f1927c * this.v, 5000.0f));
                c cVar = this.D;
                cVar.d(aVar7.f);
                cVar.a(i5, aVar7.f1926b, aVar7.e, aVar7.f != f5);
                aVar = aVar7;
                bVar = new b(i3, i4, aVar7.h, aVar6, this.l, this.D, aVar5, true);
            } else {
                aVar = aVar7;
                bVar = new b(aVar.h, str, getContext());
                bVar.e(aVar.f1926b * aVar.k);
            }
            bVar.d(aVar.f1928d);
            bVar.b(aVar.i);
            bVar.c(aVar.j);
            this.E[i5] = bVar;
            i5++;
            f5 = 0.0f;
        }
        aVar6.a();
    }

    @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base
    public void b() {
        synchronized (this.w) {
            super.b();
            this.x = true;
            Log.i("Particle_Watch_High", "Release");
            c();
            for (b bVar : this.E) {
                bVar.a();
            }
            this.F.recycle();
            this.E = null;
            if (this.K != null) {
                this.K.a();
            }
            if (this.K != null) {
                this.L.a();
            }
            if (this.K != null) {
                this.M.a();
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base
    public void b(int i) {
        Handler handler;
        long j;
        new i(1.0f, 1.0f, 0.0f).b();
        f fVar = this.K;
        if (fVar != null && this.L != null && this.M != null && this.E != null && this.V != null) {
            e a2 = fVar.a(0);
            e a3 = this.L.a(0);
            e a4 = this.M.a(0);
            ParticleView_Watch_OOBE_Base.a aVar = this.f1921a;
            if (aVar != null) {
                aVar.onParticleState(i);
            }
            this.J.a();
            if (i == 1) {
                a3.a(h.a(this.S, new i(0.0f, 0.0f, 4.0f)), new i(0.5f, 0.5f, 1.0f), c.b.c.a.b.b.f, 0.0f);
                a4.a(h.a(this.S, new i(0.0f, 0.0f, -2.0f)), 1.0f, c.b.c.a.b.b.f, 0.0f);
                l lVar = this.O;
                lVar.a(c.b.c.a.a.h.a(), k.a.Out);
                lVar.c(1.0f);
                lVar.a(SmartSwitchConstants.SLEEP, 0, m.a.OneWay);
                d dVar = this.B;
                dVar.b(0.7f, 1.5f);
                dVar.d(0.004f, 0.009f);
                dVar.c(60.0f, 170.0f);
                dVar.a(0.2f, 3.5f);
                dVar.a(this.q);
                c.b.c.b.a.a.a aVar2 = this.z;
                aVar2.b(0);
                aVar2.a(2);
                aVar2.a(this.B);
                a3.a(h.a(this.S, new i(0.0f, 0.0f, -4.0f)));
                a3.b(new i(1.0f, 1.0f, 1.0f));
                a3.a(c.b.c.a.a.b.a(), k.a.Out);
                a3.a(1500, 0, m.a.OneWay);
                handler = this.J.a(new i(this, i));
                j = 1600;
            } else if (i == 2) {
                a2.a(h.a(this.S, new i(0.0f, 0.0f, -2.0f)), new i(1.0f, 0.8f, 1.0f), c.b.c.a.b.b.f, 0.0f);
                this.B.a(0);
                a3.a(h.a(this.S, new i(0.0f, 0.0f, 1.0f)));
                a3.a(c.b.c.a.a.b.a(), k.a.InOut);
                a3.a(1500, 0, m.a.OneWay);
                this.J.a(new j(this, a2)).sendEmptyMessageDelayed(0, 450);
                this.J.a(new k(this)).sendEmptyMessageDelayed(0, 350);
                this.J.a(new l(this, a4)).sendEmptyMessageDelayed(0, 600);
                this.J.a(new m(this)).sendEmptyMessageDelayed(0, 1400);
                this.J.a(new n(this)).sendEmptyMessageDelayed(0, 1700);
                this.J.a(new o(this)).sendEmptyMessageDelayed(0, 2700);
                handler = this.J.a(new p(this, i));
                j = 3800;
            } else if (i == 3) {
                this.J.a(new q(this, a4, a2)).sendEmptyMessageDelayed(0, 0);
                this.J.a(new d(this)).sendEmptyMessageDelayed(0, 150);
                this.J.a(new e(this, a4)).sendEmptyMessageDelayed(0, 200);
                this.J.a(new f(this)).sendEmptyMessageDelayed(0, 1900);
                handler = this.J.a(new g(this, i));
                j = 3400;
            } else if (i != 4) {
                c();
                invalidate();
                ParticleView_Watch_OOBE_Base.a aVar3 = this.f1921a;
                if (aVar3 != null) {
                    aVar3.onEndParticleState(i);
                    return;
                }
                return;
            } else {
                this.C.a(0);
                this.A.a(3.0f);
                this.B.a(0);
                this.z.a(3.0f);
                l lVar2 = this.R;
                lVar2.a(c.b.c.a.a.h.a(), k.a.In);
                lVar2.c(0.0f);
                lVar2.a(1200, 0, m.a.OneWay);
                l lVar3 = this.N;
                lVar3.a(c.b.c.a.a.h.a(), k.a.In);
                lVar3.c(0.0f);
                lVar3.a(1200, 0, m.a.OneWay);
                l lVar4 = this.P;
                lVar4.a(c.b.c.a.a.h.a(), k.a.In);
                lVar4.c(0.0f);
                lVar4.a(1200, 0, m.a.OneWay);
                l lVar5 = this.O;
                lVar5.a(c.b.c.a.a.h.a(), k.a.In);
                lVar5.c(0.0f);
                lVar5.a(1200, 0, m.a.OneWay);
                l lVar6 = this.Q;
                lVar6.a(c.b.c.a.a.d.a(), k.a.None);
                lVar6.c(0.0f);
                lVar6.a(700, 0, m.a.OneWay);
                a3.a(h.a(this.S, new i(0.0f, 0.0f, 8.0f)));
                a3.a(c.b.c.a.a.b.a(), k.a.In);
                a3.a(1200, 0, m.a.OneWay);
                a4.a(h.a(this.S, new i(0.0f, 0.0f, 8.0f)));
                a4.a(c.b.c.a.a.b.a(), k.a.In);
                a4.a(1200, 0, m.a.OneWay);
                a2.a(h.a(this.S, new i(0.0f, 0.0f, 8.0f)));
                a2.a(c.b.c.a.a.b.a(), k.a.In);
                a2.a(1200, 0, m.a.OneWay);
                b bVar = this.E[0];
                bVar.d(0.0f);
                bVar.b(0.0f);
                bVar.c(1.0f);
                b bVar2 = this.E[0];
                bVar2.a(j.a(), k.a.Out);
                bVar2.a(600, 0, true);
                handler = this.J.a(new h(this, i));
                j = 1300;
            }
            handler.sendEmptyMessageDelayed(0, j);
        }
    }

    @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base
    public boolean b(int i, int i2) {
        if (!super.b(i, i2)) {
            return false;
        }
        this.x = false;
        this.J = new c.b.c.a.c.b();
        this.w = new Object();
        setPerformance(true);
        this.T = new Paint();
        new i(0.0f, 0.0f, 0.0f);
        new i();
        this.B = new d();
        this.B.a(this.U);
        this.C = new d();
        this.C.a(this.U);
        this.S = new i(0.0f, 0.0f, 14.5f);
        this.K = new f();
        this.K.a(0, new e());
        this.K.a(1, new e());
        this.L = new f();
        this.L.a(0, new e());
        this.L.a(1, new e());
        this.M = new f();
        this.M.a(0, new e());
        this.M.a(1, new e());
        this.N = new l();
        this.O = new l();
        this.P = new l();
        this.Q = new l();
        this.R = new l();
        Context context = getContext();
        c.b.c.b.a.a.a aVar = new c.b.c.b.a.a.a(context);
        aVar.a(false);
        this.y = aVar;
        c.b.c.b.a.a.a aVar2 = new c.b.c.b.a.a.a(context);
        aVar2.a(false);
        this.z = aVar2;
        c.b.c.b.a.a.a aVar3 = new c.b.c.b.a.a.a(context);
        aVar3.a(false);
        this.A = aVar3;
        this.E = null;
        float f = ((float) i) / 1440.0f;
        Bitmap[] bitmapArr = new Bitmap[this.V.length];
        int i3 = 0;
        while (true) {
            a[] aVarArr = this.V;
            if (i3 >= aVarArr.length) {
                break;
            }
            bitmapArr[i3] = a(aVarArr[i3].f1925a, Math.round(1.0f / f));
            i3++;
        }
        this.D = new c(bitmapArr);
        this.D.a(this.U);
        for (Bitmap bitmap : bitmapArr) {
            bitmap.recycle();
        }
        this.D.d(50.0f, 300.0f);
        this.D.d(0.1f);
        this.D.e(0.8f);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        this.F = BitmapFactory.decodeResource(getResources(), c.b.c.b.b.a.watch_shape_watch, options);
        return true;
    }

    @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base
    public void c() {
        super.c();
        this.J.a();
        Log.i("Particle_Watch_High", "Reset");
        Handler handler = this.G;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        Handler handler2 = this.H;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
        }
        Handler handler3 = this.I;
        if (handler3 != null) {
            handler3.removeCallbacksAndMessages(null);
        }
        c.b.c.b.a.a.a aVar = this.y;
        if (aVar != null) {
            aVar.a();
        }
        c.b.c.b.a.a.a aVar2 = this.z;
        if (aVar2 != null) {
            aVar2.a();
        }
        c.b.c.b.a.a.a aVar3 = this.A;
        if (aVar3 != null) {
            aVar3.a();
        }
        this.B.a(0);
        this.D.a(0);
        c cVar = this.D;
        cVar.c(1);
        cVar.a(1, true);
        this.D.c(1.0f);
        this.K.a(0).a(this.S, 1.0f, c.b.c.a.b.b.f, 0.0f);
        this.K.a(1).g();
        this.L.a(0).a(this.S, 1.0f, c.b.c.a.b.b.f, 0.0f);
        this.L.a(1).g();
        this.M.a(0).a(this.S, 1.0f, c.b.c.a.b.b.f, 0.0f);
        this.M.a(1).g();
        this.N.d();
        this.O.d();
        this.P.d();
        this.Q.d();
        this.R.d();
        for (b bVar : this.E) {
            bVar.b();
        }
        this.y.a();
        this.z.a();
        this.A.a();
    }

    @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base
    public void c(int i, int i2) {
        super.c(i, i2);
        a(this.V, i, i2, (int) (((float) i) * 0.5f), (int) (((float) i2) * 0.5f));
        int i3 = this.p;
        this.p = -1;
        a(i3);
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base
    public void d(int i, int i2) {
        super.d(i, i2);
        c(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base
    public void onDraw(Canvas canvas) {
        synchronized (this.w) {
            if (!this.x) {
                super.onDraw(canvas);
                c.b.c.a.b.a aVar = this.h;
                Paint paint = this.T;
                paint.reset();
                float f = (float) (this.i / 2);
                float f2 = (float) (this.j / 2);
                float f3 = (((float) this.i) / 1440.0f) * 1.0f;
                float a2 = this.N.a();
                float a3 = this.O.a();
                float a4 = this.P.a();
                float a5 = this.Q.a();
                float a6 = this.R.a();
                f fVar = this.L;
                fVar.c();
                c.b.c.a.b.a b2 = fVar.b();
                f fVar2 = this.M;
                fVar2.c();
                c.b.c.a.b.a b3 = fVar2.b();
                f fVar3 = this.K;
                fVar3.c();
                c.b.c.a.b.a b4 = fVar3.b();
                c.b.c.b.a.a.a aVar2 = this.z;
                aVar2.b(b2);
                aVar2.d();
                aVar2.b(a3);
                aVar2.a(aVar, canvas, paint, this.l);
                b[] bVarArr = this.E;
                int length = bVarArr.length;
                int i = 0;
                while (i < length) {
                    b bVar = bVarArr[i];
                    bVar.a(0.8f);
                    bVar.a(canvas, paint);
                    i++;
                    length = length;
                    bVarArr = bVarArr;
                }
                c.b.c.b.a.a.a aVar3 = this.y;
                aVar3.b(b4);
                aVar3.d();
                aVar3.b(a2);
                aVar3.a(aVar, canvas, paint, this.l);
                c.b.c.b.a.a.a aVar4 = this.A;
                aVar4.b(b3);
                aVar4.d();
                aVar4.b(a4);
                aVar4.a(aVar, canvas, paint, this.l);
                Matrix matrix = new Matrix();
                matrix.setScale(f3, f3);
                Matrix matrix2 = new Matrix();
                matrix2.setTranslate(f - ((((float) this.F.getWidth()) * 0.5f) * f3), f2 - ((((float) this.F.getHeight()) * 0.5f) * f3));
                matrix2.setConcat(matrix2, matrix);
                paint.setAlpha((int) (a6 * 255.0f));
                canvas.drawBitmap(this.F, matrix2, paint);
                paint.setColor(Color.argb((int) (a5 * 0.4f * 255.0f), 0, 0, 0));
                canvas.drawCircle(f, f2, f3 * 200.0f, paint);
                if (this.f1921a != null) {
                    this.f1921a.onWatchOpacityUpdate(a6);
                    this.f1921a.onProgressOpacityUpdate(a5);
                }
            }
        }
    }

    public void setPerformance(boolean z2) {
        float f;
        if (z2) {
            this.q = 400;
            this.r = 100;
            this.s = 250;
            this.t = 400;
            this.u = 400;
            f = 1.5f;
        } else {
            this.q = 200;
            this.r = 80;
            this.s = 70;
            this.t = 200;
            this.u = 150;
            f = 2.5f;
        }
        this.v = f;
    }
}
