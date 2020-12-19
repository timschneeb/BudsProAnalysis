package c.b.c.b.a;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import c.b.c.a.a.f;
import c.b.c.a.a.k;
import c.b.c.b.a.a.a;

public class b {

    /* renamed from: a  reason: collision with root package name */
    boolean f1879a;

    /* renamed from: b  reason: collision with root package name */
    boolean f1880b;

    /* renamed from: c  reason: collision with root package name */
    int f1881c;

    /* renamed from: d  reason: collision with root package name */
    Bitmap[] f1882d;
    Canvas[] e;
    float f;
    float g;
    float h;
    float i;
    float j;
    float[] k;
    boolean l = false;
    ValueAnimator m;
    Handler n;
    k.a o;
    k p;

    public b(int i2, int i3, int i4, a aVar, com.samsung.td.particlesystem.GL.b bVar, c cVar, c.b.c.a.b.a aVar2, boolean z) {
        a(i4);
        this.e = new Canvas[i4];
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        float f2 = 1.0f / ((float) i4);
        for (int i5 = 0; i5 < i4; i5++) {
            aVar.a(cVar);
            this.f1882d[i5] = Bitmap.createBitmap(i2, i3, config);
            this.e[i5] = new Canvas(this.f1882d[i5]);
            if (z) {
                cVar.a((((float) (i5 + 1)) * f2) - (0.5f * f2), f2);
            } else {
                cVar.a(0.5f, 1.0f);
            }
            aVar.d();
            aVar.a(aVar2, this.e[i5], null, bVar);
            aVar.a();
        }
    }

    public b(int i2, String str, Context context) {
        a(i2);
        Resources resources = context.getResources();
        this.e = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        int i3 = 0;
        options.inScaled = false;
        while (i3 < i2) {
            Bitmap[] bitmapArr = this.f1882d;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            int i4 = i3 + 1;
            sb.append(i4);
            bitmapArr[i3] = BitmapFactory.decodeResource(resources, resources.getIdentifier(sb.toString(), "drawable", context.getPackageName()), options);
            i3 = i4;
        }
    }

    public b a(float f2) {
        this.i = f2;
        return this;
    }

    public b a(k kVar, k.a aVar) {
        this.o = aVar;
        this.p = kVar;
        return this;
    }

    public void a() {
        Bitmap[] bitmapArr = this.f1882d;
        if (bitmapArr != null) {
            for (Bitmap bitmap : bitmapArr) {
                bitmap.recycle();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        this.f1879a = false;
        this.f = 1.0f;
        this.j = 0.2f;
        this.i = 1.0f;
        this.m = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.m.setInterpolator(new LinearInterpolator());
        this.h = 0.0f;
        this.g = 1.0f;
        this.n = new Handler(new a(this));
        this.f1881c = i2;
        this.f1882d = new Bitmap[i2];
        this.f1880b = false;
        this.k = new float[]{0.0f, 0.0f};
        a(f.a(), k.a.Out);
    }

    public void a(long j2, long j3, boolean z) {
        this.m.getCurrentPlayTime();
        float floatValue = ((Float) this.m.getAnimatedValue()).floatValue();
        this.m.getDuration();
        this.m.cancel();
        this.f1879a = z;
        float f2 = z ? 0.0f : 1.0f;
        this.m.setDuration(j2);
        this.n.removeCallbacksAndMessages(null);
        this.m.setFloatValues(floatValue, f2);
        this.m.setStartDelay(j3);
        if (j3 < 0) {
            this.m.start();
        } else {
            this.n.sendEmptyMessageDelayed(0, j3);
        }
    }

    public void a(Canvas canvas, Paint paint) {
        float f2;
        float f3;
        b bVar = this;
        float floatValue = ((Float) bVar.m.getAnimatedValue()).floatValue();
        float f4 = 1.0f;
        float width = bVar.l ? 1440.0f / ((float) canvas.getWidth()) : 1.0f;
        float width2 = (float) (canvas.getWidth() / 2);
        float height = (float) (canvas.getHeight() / 2);
        float f5 = bVar.g;
        float f6 = bVar.h;
        int i2 = 1;
        float f7 = 0.0f;
        if ((floatValue > 0.0f) || bVar.f1880b) {
            bVar.f1880b = false;
            int i3 = 0;
            while (true) {
                Bitmap[] bitmapArr = bVar.f1882d;
                if (i3 < bitmapArr.length) {
                    Bitmap bitmap = bitmapArr[i3];
                    float f8 = bVar.j;
                    float min = Math.min(Math.max((((((float) (bitmapArr.length - i2)) * f8) + f4) * floatValue) - (((float) i3) * f8), f7), f4);
                    float a2 = bVar.p.a(min, 0.0f, 1.0f, 1.0f, bVar.o);
                    float a3 = f.a().a(min, f7, f4, f4);
                    float a4 = c.b.c.a.b.b.a(a2, f5, f4) * width * bVar.f;
                    paint.setAlpha((int) (((float) c.b.c.a.b.b.a(a3, 0, 255)) * bVar.i));
                    if (bVar.i > 0.0f) {
                        float width3 = ((float) bitmap.getWidth()) * 0.5f;
                        float height2 = ((float) bitmap.getHeight()) * 0.5f;
                        canvas.save();
                        f3 = floatValue;
                        f2 = width;
                        canvas.translate((width2 - width3) + (bVar.k[0] * ((float) canvas.getWidth())), (height - height2) + (bVar.k[1] * ((float) canvas.getHeight())));
                        canvas.rotate(c.b.c.a.b.b.a(a2, f6, 0.0f), width3, height2);
                        canvas.scale(a4, a4, width3, height2);
                        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                        canvas.restore();
                    } else {
                        f3 = floatValue;
                        f2 = width;
                    }
                    i3++;
                    f4 = 1.0f;
                    i2 = 1;
                    f7 = 0.0f;
                    bVar = this;
                    floatValue = f3;
                    width = f2;
                } else {
                    return;
                }
            }
        }
    }

    public b b(float f2) {
        this.h = f2;
        return this;
    }

    public void b() {
        this.n.removeCallbacksAndMessages(null);
        this.m.cancel();
        this.m.setFloatValues(0.0f, 1.0f);
        this.m.setCurrentPlayTime(0);
        this.f1880b = true;
    }

    public b c(float f2) {
        this.g = f2;
        return this;
    }

    public b d(float f2) {
        this.j = f2;
        return this;
    }

    public b e(float f2) {
        this.f = f2;
        return this;
    }
}
