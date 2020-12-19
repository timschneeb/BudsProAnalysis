package com.samsung.td.particlesystem.GL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import c.b.c.a.b.i;
import c.b.c.b.a;

public class b {

    /* renamed from: a  reason: collision with root package name */
    Matrix f1911a;

    /* renamed from: b  reason: collision with root package name */
    Matrix f1912b;

    /* renamed from: c  reason: collision with root package name */
    Matrix f1913c;

    /* renamed from: d  reason: collision with root package name */
    Bitmap f1914d;
    Matrix e;
    i f;
    float g;
    a h;

    public b(Context context) {
        a(context);
    }

    public b a(Canvas canvas, Paint paint, i iVar, float f2) {
        if (this.h == null) {
            this.f1912b.setScale(f2, f2);
            this.f1911a.setConcat(this.f1912b, this.e);
            this.f1912b.setTranslate(iVar.f1850a, iVar.f1851b);
            Matrix matrix = this.f1911a;
            matrix.setConcat(this.f1912b, matrix);
            canvas.drawBitmap(this.f1914d, this.f1911a, paint);
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        Matrix matrix = new Matrix();
        this.e = new Matrix();
        this.e.reset();
        matrix.setTranslate((float) ((-this.f1914d.getWidth()) / 2), (float) ((-this.f1914d.getHeight()) / 2));
        Matrix matrix2 = this.e;
        matrix2.setConcat(matrix, matrix2);
        float f2 = this.g;
        matrix.setScale(f2 * 0.08f, f2 * 0.08f);
        Matrix matrix3 = this.e;
        matrix3.setConcat(matrix, matrix3);
    }

    public void a(float f2) {
        this.g = f2;
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(Context context) {
        this.f1911a = new Matrix();
        this.f1912b = new Matrix();
        this.f1913c = new Matrix();
        this.f = new i();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        this.f1914d = BitmapFactory.decodeResource(context.getResources(), a.particle, options);
        this.h = null;
        a(1.0f);
    }

    public float b() {
        return this.g;
    }
}
