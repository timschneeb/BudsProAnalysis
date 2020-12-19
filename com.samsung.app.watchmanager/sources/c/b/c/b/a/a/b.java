package c.b.c.b.a.a;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import c.b.c.a.b.a;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class b {

    /* renamed from: a  reason: collision with root package name */
    protected int f1871a = Integer.MAX_VALUE;

    /* renamed from: b  reason: collision with root package name */
    protected ArrayList<c> f1872b = new ArrayList<>();

    /* renamed from: c  reason: collision with root package name */
    protected a f1873c = null;

    /* renamed from: d  reason: collision with root package name */
    protected Matrix f1874d = new Matrix();
    protected Matrix e;
    protected Matrix f;
    protected Paint[] g;
    protected int[][] h;

    public b() {
        this.f1874d.reset();
        this.e = new Matrix();
        this.f1874d.reset();
        this.f = new Matrix();
        this.f.reset();
    }

    public int a() {
        Iterator<c> it = this.f1872b.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().d() == 0) {
                i++;
            }
        }
        return c() - (this.f1872b.size() - i);
    }

    public b a(int i) {
        this.f1871a = i;
        int a2 = a();
        if (a2 < 0) {
            Iterator<c> it = this.f1872b.iterator();
            while (it.hasNext()) {
                c next = it.next();
                if (a2 >= 0) {
                    break;
                } else if (next.d() != 0) {
                    next.a((char) 0);
                    a2++;
                }
            }
        }
        return this;
    }

    public b a(int[][] iArr) {
        this.h = new int[iArr.length][];
        for (int i = 0; i < iArr.length; i++) {
            this.h[i] = new int[4];
            for (int i2 = 0; i2 < 4; i2++) {
                this.h[i][i2] = iArr[i][i2];
            }
        }
        this.g = new Paint[iArr.length];
        for (int i3 = 0; i3 < iArr.length; i3++) {
            this.g[i3] = new Paint();
            this.g[i3].setColorFilter(new PorterDuffColorFilter(Color.argb(255, iArr[i3][0], iArr[i3][1], iArr[i3][2]), PorterDuff.Mode.SRC_IN));
        }
        return this;
    }

    public abstract void a(Canvas canvas, Paint paint, com.samsung.td.particlesystem.GL.b bVar, a aVar, c cVar);

    public abstract void a(c cVar);

    public void a(ArrayList<c> arrayList, int i, a aVar) {
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(e(null));
        }
    }

    public a b() {
        return this.f1873c;
    }

    public abstract void b(c cVar);

    public int c() {
        return this.f1871a;
    }

    public void c(c cVar) {
        cVar.i();
    }

    public ArrayList<c> d() {
        return this.f1872b;
    }

    public abstract void d(c cVar);

    public abstract c e(c cVar);
}
