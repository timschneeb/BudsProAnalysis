package d.a.a.a;

import d.a.a.a;
import d.a.a.b.q;
import d.a.a.e;
import d.a.a.g;
import d.a.a.p;
import java.io.Serializable;

public abstract class c extends a implements p, Serializable {

    /* renamed from: a  reason: collision with root package name */
    private volatile long f2007a;

    /* renamed from: b  reason: collision with root package name */
    private volatile a f2008b;

    public c() {
        this(e.a(), q.N());
    }

    public c(long j, a aVar) {
        this.f2008b = a(aVar);
        a(j, this.f2008b);
        this.f2007a = j;
        h();
    }

    public c(long j, g gVar) {
        this(j, q.b(gVar));
    }

    private void h() {
        if (this.f2007a == Long.MIN_VALUE || this.f2007a == Long.MAX_VALUE) {
            this.f2008b = this.f2008b.G();
        }
    }

    @Override // d.a.a.q
    public long a() {
        return this.f2007a;
    }

    /* access modifiers changed from: protected */
    public long a(long j, a aVar) {
        return j;
    }

    /* access modifiers changed from: protected */
    public a a(a aVar) {
        return e.a(aVar);
    }

    /* access modifiers changed from: protected */
    public void c(long j) {
        a(j, this.f2008b);
        this.f2007a = j;
    }

    @Override // d.a.a.q
    public a getChronology() {
        return this.f2008b;
    }
}
