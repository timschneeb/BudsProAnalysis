package androidx.swiperefreshlayout.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import b.e.f.h;
import b.h.a.a.b;

public class d extends Drawable implements Animatable {

    /* renamed from: a  reason: collision with root package name */
    private static final Interpolator f1154a = new LinearInterpolator();

    /* renamed from: b  reason: collision with root package name */
    private static final Interpolator f1155b = new b();

    /* renamed from: c  reason: collision with root package name */
    private static final int[] f1156c = {-16777216};

    /* renamed from: d  reason: collision with root package name */
    private final a f1157d = new a();
    private float e;
    private Resources f;
    private Animator g;
    float h;
    boolean i;

    /* access modifiers changed from: private */
    public static class a {

        /* renamed from: a  reason: collision with root package name */
        final RectF f1158a = new RectF();

        /* renamed from: b  reason: collision with root package name */
        final Paint f1159b = new Paint();

        /* renamed from: c  reason: collision with root package name */
        final Paint f1160c = new Paint();

        /* renamed from: d  reason: collision with root package name */
        final Paint f1161d = new Paint();
        float e = 0.0f;
        float f = 0.0f;
        float g = 0.0f;
        float h = 5.0f;
        int[] i;
        int j;
        float k;
        float l;
        float m;
        boolean n;
        Path o;
        float p = 1.0f;
        float q;
        int r;
        int s;
        int t = 255;
        int u;

        a() {
            this.f1159b.setStrokeCap(Paint.Cap.SQUARE);
            this.f1159b.setAntiAlias(true);
            this.f1159b.setStyle(Paint.Style.STROKE);
            this.f1160c.setStyle(Paint.Style.FILL);
            this.f1160c.setAntiAlias(true);
            this.f1161d.setColor(0);
        }

        /* access modifiers changed from: package-private */
        public int a() {
            return this.t;
        }

        /* access modifiers changed from: package-private */
        public void a(float f2) {
            if (f2 != this.p) {
                this.p = f2;
            }
        }

        /* access modifiers changed from: package-private */
        public void a(float f2, float f3) {
            this.r = (int) f2;
            this.s = (int) f3;
        }

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            this.t = i2;
        }

        /* access modifiers changed from: package-private */
        public void a(Canvas canvas, float f2, float f3, RectF rectF) {
            if (this.n) {
                Path path = this.o;
                if (path == null) {
                    this.o = new Path();
                    this.o.setFillType(Path.FillType.EVEN_ODD);
                } else {
                    path.reset();
                }
                this.o.moveTo(0.0f, 0.0f);
                this.o.lineTo(((float) this.r) * this.p, 0.0f);
                Path path2 = this.o;
                float f4 = this.p;
                path2.lineTo((((float) this.r) * f4) / 2.0f, ((float) this.s) * f4);
                this.o.offset(((Math.min(rectF.width(), rectF.height()) / 2.0f) + rectF.centerX()) - ((((float) this.r) * this.p) / 2.0f), rectF.centerY() + (this.h / 2.0f));
                this.o.close();
                this.f1160c.setColor(this.u);
                this.f1160c.setAlpha(this.t);
                canvas.save();
                canvas.rotate(f2 + f3, rectF.centerX(), rectF.centerY());
                canvas.drawPath(this.o, this.f1160c);
                canvas.restore();
            }
        }

        /* access modifiers changed from: package-private */
        public void a(Canvas canvas, Rect rect) {
            RectF rectF = this.f1158a;
            float f2 = this.q;
            float f3 = (this.h / 2.0f) + f2;
            if (f2 <= 0.0f) {
                f3 = (((float) Math.min(rect.width(), rect.height())) / 2.0f) - Math.max((((float) this.r) * this.p) / 2.0f, this.h / 2.0f);
            }
            rectF.set(((float) rect.centerX()) - f3, ((float) rect.centerY()) - f3, ((float) rect.centerX()) + f3, ((float) rect.centerY()) + f3);
            float f4 = this.e;
            float f5 = this.g;
            float f6 = (f4 + f5) * 360.0f;
            float f7 = ((this.f + f5) * 360.0f) - f6;
            this.f1159b.setColor(this.u);
            this.f1159b.setAlpha(this.t);
            float f8 = this.h / 2.0f;
            rectF.inset(f8, f8);
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2.0f, this.f1161d);
            float f9 = -f8;
            rectF.inset(f9, f9);
            canvas.drawArc(rectF, f6, f7, false, this.f1159b);
            a(canvas, f6, f7, rectF);
        }

        /* access modifiers changed from: package-private */
        public void a(ColorFilter colorFilter) {
            this.f1159b.setColorFilter(colorFilter);
        }

        /* access modifiers changed from: package-private */
        public void a(boolean z) {
            if (this.n != z) {
                this.n = z;
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int[] iArr) {
            this.i = iArr;
            c(0);
        }

        /* access modifiers changed from: package-private */
        public float b() {
            return this.f;
        }

        /* access modifiers changed from: package-private */
        public void b(float f2) {
            this.q = f2;
        }

        /* access modifiers changed from: package-private */
        public void b(int i2) {
            this.u = i2;
        }

        /* access modifiers changed from: package-private */
        public int c() {
            return this.i[d()];
        }

        /* access modifiers changed from: package-private */
        public void c(float f2) {
            this.f = f2;
        }

        /* access modifiers changed from: package-private */
        public void c(int i2) {
            this.j = i2;
            this.u = this.i[this.j];
        }

        /* access modifiers changed from: package-private */
        public int d() {
            return (this.j + 1) % this.i.length;
        }

        /* access modifiers changed from: package-private */
        public void d(float f2) {
            this.g = f2;
        }

        /* access modifiers changed from: package-private */
        public float e() {
            return this.e;
        }

        /* access modifiers changed from: package-private */
        public void e(float f2) {
            this.e = f2;
        }

        /* access modifiers changed from: package-private */
        public int f() {
            return this.i[this.j];
        }

        /* access modifiers changed from: package-private */
        public void f(float f2) {
            this.h = f2;
            this.f1159b.setStrokeWidth(f2);
        }

        /* access modifiers changed from: package-private */
        public float g() {
            return this.l;
        }

        /* access modifiers changed from: package-private */
        public float h() {
            return this.m;
        }

        /* access modifiers changed from: package-private */
        public float i() {
            return this.k;
        }

        /* access modifiers changed from: package-private */
        public void j() {
            c(d());
        }

        /* access modifiers changed from: package-private */
        public void k() {
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            e(0.0f);
            c(0.0f);
            d(0.0f);
        }

        /* access modifiers changed from: package-private */
        public void l() {
            this.k = this.e;
            this.l = this.f;
            this.m = this.g;
        }
    }

    public d(Context context) {
        h.a(context);
        this.f = context.getResources();
        this.f1157d.a(f1156c);
        c(2.5f);
        a();
    }

    private int a(float f2, int i2, int i3) {
        int i4 = (i2 >> 24) & 255;
        int i5 = (i2 >> 16) & 255;
        int i6 = (i2 >> 8) & 255;
        int i7 = i2 & 255;
        return ((i4 + ((int) (((float) (((i3 >> 24) & 255) - i4)) * f2))) << 24) | ((i5 + ((int) (((float) (((i3 >> 16) & 255) - i5)) * f2))) << 16) | ((i6 + ((int) (((float) (((i3 >> 8) & 255) - i6)) * f2))) << 8) | (i7 + ((int) (f2 * ((float) ((i3 & 255) - i7)))));
    }

    private void a() {
        a aVar = this.f1157d;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(new b(this, aVar));
        ofFloat.setRepeatCount(-1);
        ofFloat.setRepeatMode(1);
        ofFloat.setInterpolator(f1154a);
        ofFloat.addListener(new c(this, aVar));
        this.g = ofFloat;
    }

    private void a(float f2, float f3, float f4, float f5) {
        a aVar = this.f1157d;
        float f6 = this.f.getDisplayMetrics().density;
        aVar.f(f3 * f6);
        aVar.b(f2 * f6);
        aVar.c(0);
        aVar.a(f4 * f6, f5 * f6);
    }

    private void b(float f2, a aVar) {
        a(f2, aVar);
        aVar.e(aVar.i() + (((aVar.g() - 0.01f) - aVar.i()) * f2));
        aVar.c(aVar.g());
        aVar.d(aVar.h() + ((((float) (Math.floor((double) (aVar.h() / 0.8f)) + 1.0d)) - aVar.h()) * f2));
    }

    private void d(float f2) {
        this.e = f2;
    }

    public void a(float f2) {
        this.f1157d.a(f2);
        invalidateSelf();
    }

    public void a(float f2, float f3) {
        this.f1157d.e(f2);
        this.f1157d.c(f3);
        invalidateSelf();
    }

    /* access modifiers changed from: package-private */
    public void a(float f2, a aVar) {
        aVar.b(f2 > 0.75f ? a((f2 - 0.75f) / 0.25f, aVar.f(), aVar.c()) : aVar.f());
    }

    /* access modifiers changed from: package-private */
    public void a(float f2, a aVar, boolean z) {
        float f3;
        float f4;
        if (this.i) {
            b(f2, aVar);
        } else if (f2 != 1.0f || z) {
            float h2 = aVar.h();
            if (f2 < 0.5f) {
                float i2 = aVar.i();
                f3 = (f1155b.getInterpolation(f2 / 0.5f) * 0.79f) + 0.01f + i2;
                f4 = i2;
            } else {
                f3 = aVar.i() + 0.79f;
                f4 = f3 - (((1.0f - f1155b.getInterpolation((f2 - 0.5f) / 0.5f)) * 0.79f) + 0.01f);
            }
            aVar.e(f4);
            aVar.c(f3);
            aVar.d(h2 + (0.20999998f * f2));
            d((f2 + this.h) * 216.0f);
        }
    }

    public void a(int i2) {
        float f2;
        float f3;
        float f4;
        float f5;
        if (i2 == 0) {
            f2 = 11.0f;
            f5 = 3.0f;
            f4 = 12.0f;
            f3 = 6.0f;
        } else {
            f2 = 7.5f;
            f5 = 2.5f;
            f4 = 10.0f;
            f3 = 5.0f;
        }
        a(f2, f5, f4, f3);
        invalidateSelf();
    }

    public void a(boolean z) {
        this.f1157d.a(z);
        invalidateSelf();
    }

    public void a(int... iArr) {
        this.f1157d.a(iArr);
        this.f1157d.c(0);
        invalidateSelf();
    }

    public void b(float f2) {
        this.f1157d.d(f2);
        invalidateSelf();
    }

    public void c(float f2) {
        this.f1157d.f(f2);
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.rotate(this.e, bounds.exactCenterX(), bounds.exactCenterY());
        this.f1157d.a(canvas, bounds);
        canvas.restore();
    }

    public int getAlpha() {
        return this.f1157d.a();
    }

    public int getOpacity() {
        return -3;
    }

    public boolean isRunning() {
        return this.g.isRunning();
    }

    public void setAlpha(int i2) {
        this.f1157d.a(i2);
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.f1157d.a(colorFilter);
        invalidateSelf();
    }

    public void start() {
        long j;
        Animator animator;
        this.g.cancel();
        this.f1157d.l();
        if (this.f1157d.b() != this.f1157d.e()) {
            this.i = true;
            animator = this.g;
            j = 666;
        } else {
            this.f1157d.c(0);
            this.f1157d.k();
            animator = this.g;
            j = 1332;
        }
        animator.setDuration(j);
        this.g.start();
    }

    public void stop() {
        this.g.cancel();
        d(0.0f);
        this.f1157d.a(false);
        this.f1157d.c(0);
        this.f1157d.k();
        invalidateSelf();
    }
}
