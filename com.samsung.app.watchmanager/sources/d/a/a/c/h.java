package d.a.a.c;

import d.a.a.d;
import d.a.a.i;

public abstract class h extends b {

    /* renamed from: b  reason: collision with root package name */
    final long f2045b;

    /* renamed from: c  reason: collision with root package name */
    private final d.a.a.h f2046c;

    private final class a extends c {
        a(i iVar) {
            super(iVar);
        }

        @Override // d.a.a.h
        public long a(long j, int i) {
            return h.this.a(j, i);
        }

        @Override // d.a.a.h
        public long a(long j, long j2) {
            return h.this.a(j, j2);
        }

        @Override // d.a.a.h
        public long c() {
            return h.this.f2045b;
        }

        @Override // d.a.a.h
        public boolean d() {
            return false;
        }
    }

    public h(d dVar, long j) {
        super(dVar);
        this.f2045b = j;
        this.f2046c = new a(dVar.h());
    }

    public abstract long a(long j, long j2);

    @Override // d.a.a.c
    public final d.a.a.h a() {
        return this.f2046c;
    }
}
