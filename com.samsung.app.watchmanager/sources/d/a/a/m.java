package d.a.a;

import d.a.a.a.c;
import java.io.Serializable;

public class m extends c implements n, Cloneable, Serializable {

    /* renamed from: c  reason: collision with root package name */
    private c f2165c;

    /* renamed from: d  reason: collision with root package name */
    private int f2166d;

    public static final class a extends d.a.a.c.a {

        /* renamed from: a  reason: collision with root package name */
        private m f2167a;

        /* renamed from: b  reason: collision with root package name */
        private c f2168b;

        a(m mVar, c cVar) {
            this.f2167a = mVar;
            this.f2168b = cVar;
        }

        public m a(int i) {
            this.f2167a.c(c().b(this.f2167a.a(), i));
            return this.f2167a;
        }

        /* access modifiers changed from: protected */
        @Override // d.a.a.c.a
        public a b() {
            return this.f2167a.getChronology();
        }

        @Override // d.a.a.c.a
        public c c() {
            return this.f2168b;
        }

        /* access modifiers changed from: protected */
        @Override // d.a.a.c.a
        public long f() {
            return this.f2167a.a();
        }
    }

    public m(long j, g gVar) {
        super(j, gVar);
    }

    public a a(d dVar) {
        if (dVar != null) {
            c a2 = dVar.a(getChronology());
            if (a2.i()) {
                return new a(this, a2);
            }
            throw new IllegalArgumentException("Field '" + dVar + "' is not supported");
        }
        throw new IllegalArgumentException("The DateTimeFieldType must not be null");
    }

    @Override // d.a.a.a.c
    public void c(long j) {
        int i = this.f2166d;
        if (i != 0) {
            if (i == 1) {
                j = this.f2165c.e(j);
            } else if (i == 2) {
                j = this.f2165c.d(j);
            } else if (i == 3) {
                j = this.f2165c.h(j);
            } else if (i == 4) {
                j = this.f2165c.f(j);
            } else if (i == 5) {
                j = this.f2165c.g(j);
            }
        }
        super.c(j);
    }

    @Override // java.lang.Object
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new InternalError("Clone error");
        }
    }
}
