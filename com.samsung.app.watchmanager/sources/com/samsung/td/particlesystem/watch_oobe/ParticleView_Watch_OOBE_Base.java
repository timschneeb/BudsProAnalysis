package com.samsung.td.particlesystem.watch_oobe;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import com.samsung.td.particlesystem.GL.b;
import java.util.Random;

public abstract class ParticleView_Watch_OOBE_Base extends View {

    /* renamed from: a  reason: collision with root package name */
    a f1921a = null;

    /* renamed from: b  reason: collision with root package name */
    final int f1922b = -1;

    /* renamed from: c  reason: collision with root package name */
    final int f1923c = 0;

    /* renamed from: d  reason: collision with root package name */
    float f1924d = 40.0f;
    float e = 1.0f;
    c.b.c.a.b.a f;
    c.b.c.a.b.a g;
    c.b.c.a.b.a h;
    int i;
    int j;
    Random k;
    b l;
    ValueAnimator m;
    boolean n = false;
    Handler o;
    int p;

    public interface a {
        void onEndParticleState(int i);

        void onParticleState(int i);

        void onProgressOpacityUpdate(float f);

        void onWatchOpacityUpdate(float f);
    }

    public ParticleView_Watch_OOBE_Base(Context context) {
        super(context);
    }

    public ParticleView_Watch_OOBE_Base(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ParticleView_Watch_OOBE_Base(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    /* access modifiers changed from: package-private */
    public Bitmap a(int i2, int i3) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inSampleSize = i3;
        return BitmapFactory.decodeResource(getResources(), i2, options);
    }

    public void a() {
        int i2 = this.p;
        a(i2 == 4 ? 0 : i2 + 1);
    }

    public void a(int i2) {
        if (i2 != this.p) {
            this.o.sendEmptyMessage(i2);
        }
    }

    public void b() {
        this.m.cancel();
        this.o.removeCallbacksAndMessages(null);
        this.f1921a = null;
    }

    /* access modifiers changed from: package-private */
    public abstract void b(int i2);

    public boolean b(int i2, int i3) {
        if (this.n) {
            return false;
        }
        this.n = true;
        this.l = new b(getContext());
        this.k = new Random();
        this.f = new c.b.c.a.b.a();
        this.f.a();
        this.h = new c.b.c.a.b.a();
        this.h.a();
        this.m = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.m.setDuration(1000L);
        this.m.setRepeatCount(-1);
        this.m.addUpdateListener(new b(this));
        this.o = new Handler(new c(this));
        this.p = 0;
        return true;
    }

    public void c() {
        this.p = 0;
        this.m.cancel();
        this.n = false;
    }

    public void c(int i2, int i3) {
    }

    /* access modifiers changed from: protected */
    public void d(int i2, int i3) {
        this.i = i2;
        this.j = i3;
        this.g = new c.b.c.a.b.a();
        float f2 = (float) i3;
        float f3 = (float) i2;
        this.g.a(0.9424779f, f2 / f3, this.e, this.f1924d);
        c.b.c.a.b.a aVar = new c.b.c.a.b.a();
        aVar.b(f3, f2, 0.0f, 1.0f);
        this.g.b(aVar);
        c.b.c.a.b.a aVar2 = this.h;
        aVar2.a(this.f);
        aVar2.b(this.g);
    }

    public a getListener() {
        return this.f1921a;
    }

    public b getParticleDrawer() {
        return this.l;
    }

    /* access modifiers changed from: protected */
    public int getRangedRandVal() {
        return this.k.nextBoolean() ? -1 : 1;
    }

    public int getSceneType() {
        return this.p;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        c.b.c.a.b.a aVar = this.h;
        aVar.a(this.f);
        aVar.b(this.g);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.l.a(((float) i2) / 1440.0f);
        d(i2, i3);
    }

    public void setListener(a aVar) {
        this.f1921a = aVar;
    }
}
