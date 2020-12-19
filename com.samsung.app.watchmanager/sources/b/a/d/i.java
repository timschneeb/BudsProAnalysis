package b.a.d;

import android.view.animation.Interpolator;
import b.e.g.A;
import b.e.g.B;
import b.e.g.z;
import java.util.ArrayList;
import java.util.Iterator;

public class i {

    /* renamed from: a  reason: collision with root package name */
    final ArrayList<z> f1258a = new ArrayList<>();

    /* renamed from: b  reason: collision with root package name */
    private long f1259b = -1;

    /* renamed from: c  reason: collision with root package name */
    private Interpolator f1260c;

    /* renamed from: d  reason: collision with root package name */
    A f1261d;
    private boolean e;
    private final B f = new h(this);

    public i a(long j) {
        if (!this.e) {
            this.f1259b = j;
        }
        return this;
    }

    public i a(Interpolator interpolator) {
        if (!this.e) {
            this.f1260c = interpolator;
        }
        return this;
    }

    public i a(A a2) {
        if (!this.e) {
            this.f1261d = a2;
        }
        return this;
    }

    public i a(z zVar) {
        if (!this.e) {
            this.f1258a.add(zVar);
        }
        return this;
    }

    public i a(z zVar, z zVar2) {
        this.f1258a.add(zVar);
        zVar2.b(zVar.b());
        this.f1258a.add(zVar2);
        return this;
    }

    public void a() {
        if (this.e) {
            Iterator<z> it = this.f1258a.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
            this.e = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void b() {
        this.e = false;
    }

    public void c() {
        if (!this.e) {
            Iterator<z> it = this.f1258a.iterator();
            while (it.hasNext()) {
                z next = it.next();
                long j = this.f1259b;
                if (j >= 0) {
                    next.a(j);
                }
                Interpolator interpolator = this.f1260c;
                if (interpolator != null) {
                    next.a(interpolator);
                }
                if (this.f1261d != null) {
                    next.a(this.f);
                }
                next.c();
            }
            this.e = true;
        }
    }
}
