package c.b.c.b.a.a;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import c.b.c.a.b.i;
import com.samsung.td.particlesystem.GL.b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class a {

    /* renamed from: a  reason: collision with root package name */
    ArrayList<c> f1867a;

    /* renamed from: b  reason: collision with root package name */
    float f1868b;

    /* renamed from: c  reason: collision with root package name */
    c.b.c.a.b.a f1869c;

    /* renamed from: d  reason: collision with root package name */
    boolean f1870d;
    Random e;
    i f;
    i g;
    i h;
    c.b.c.a.b.a i;
    c.b.c.a.b.a j;
    int k;
    int l;
    int m;

    public a(Context context) {
        a(context);
    }

    public int a(int i2, b bVar) {
        int min = Math.min(i2, bVar.a());
        if (min > 0) {
            bVar.a(this.f1867a, min, this);
        }
        return min;
    }

    public int a(b bVar) {
        return a(bVar.c(), bVar);
    }

    public a a() {
        Iterator<c> it = this.f1867a.iterator();
        while (it.hasNext()) {
            c next = it.next();
            next.h().c(next);
            it.remove();
        }
        this.f1867a.clear();
        return this;
    }

    public a a(float f2) {
        Iterator<c> it = this.f1867a.iterator();
        while (it.hasNext()) {
            it.next().f *= f2;
        }
        return this;
    }

    public a a(int i2) {
        this.m = i2;
        return this;
    }

    public a a(c.b.c.a.b.a aVar, Canvas canvas, Paint paint, b bVar) {
        a(aVar);
        if (paint == null) {
            paint = new Paint();
        } else {
            paint.reset();
        }
        a(canvas, paint, bVar);
        return this;
    }

    public a a(boolean z) {
        this.f1870d = z;
        return this;
    }

    /* access modifiers changed from: protected */
    public void a(Context context) {
        this.e = new Random();
        this.f1867a = new ArrayList<>();
        this.f1869c = c.b.c.a.b.a.b();
        b(1.0f);
        a(true);
        this.f = new i();
        this.g = new i();
        this.h = new i();
        this.i = new c.b.c.a.b.a();
        this.j = new c.b.c.a.b.a();
        b(0);
        a(100);
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, Paint paint, b bVar) {
        if (this.f1868b > 0.0f) {
            paint.setAntiAlias(true);
            canvas.save();
            Iterator<c> it = this.f1867a.iterator();
            while (it.hasNext()) {
                c next = it.next();
                next.h().a(canvas, paint, bVar, this, next);
            }
            canvas.restore();
        }
    }

    /* access modifiers changed from: protected */
    public void a(c.b.c.a.b.a aVar) {
        this.i.a(this.f1869c);
        this.i.b(aVar);
        c.b.c.a.b.a aVar2 = this.i;
        Iterator<c> it = this.f1867a.iterator();
        while (it.hasNext()) {
            c next = it.next();
            c.b.c.a.b.a b2 = next.h().b();
            if (b2 == null) {
                this.j.a(aVar2);
            } else {
                this.j.a(b2);
                this.j.b(aVar2);
            }
            c.b.c.a.b.a aVar3 = this.j;
            i iVar = next.f1876b;
            iVar.b(next.e());
            iVar.a(aVar3);
        }
        boolean z = this.f1870d;
    }

    public a b(float f2) {
        this.f1868b = Math.max(Math.min(f2, 1.0f), 0.0f);
        return this;
    }

    public a b(int i2) {
        this.l = i2;
        this.k = 0;
        return this;
    }

    public a b(c.b.c.a.b.a aVar) {
        this.f1869c.a(aVar);
        return this;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        int i2 = this.k;
        if (i2 != 0) {
            this.k = i2 < this.l ? i2 + 1 : 0;
        }
    }

    public float c() {
        return this.f1868b;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0076, code lost:
        if (r5 > 1.0f) goto L_0x0078;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0087, code lost:
        if (r5 > 1.0f) goto L_0x0078;
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0066  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public c.b.c.b.a.a.a d() {
        /*
        // Method dump skipped, instructions count: 170
        */
        throw new UnsupportedOperationException("Method not decompiled: c.b.c.b.a.a.a.d():c.b.c.b.a.a.a");
    }
}
