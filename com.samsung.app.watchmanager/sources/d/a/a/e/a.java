package d.a.a.e;

import androidx.recyclerview.widget.LinearLayoutManager;
import d.a.a.g;

public class a extends g {
    private static final int f;
    private final g g;
    private final transient C0042a[] h = new C0042a[(f + 1)];

    /* access modifiers changed from: private */
    /* renamed from: d.a.a.e.a$a  reason: collision with other inner class name */
    public static final class C0042a {

        /* renamed from: a  reason: collision with root package name */
        public final long f2129a;

        /* renamed from: b  reason: collision with root package name */
        public final g f2130b;

        /* renamed from: c  reason: collision with root package name */
        C0042a f2131c;

        /* renamed from: d  reason: collision with root package name */
        private String f2132d;
        private int e = LinearLayoutManager.INVALID_OFFSET;
        private int f = LinearLayoutManager.INVALID_OFFSET;

        C0042a(g gVar, long j) {
            this.f2129a = j;
            this.f2130b = gVar;
        }

        public String a(long j) {
            C0042a aVar = this.f2131c;
            if (aVar != null && j >= aVar.f2129a) {
                return aVar.a(j);
            }
            if (this.f2132d == null) {
                this.f2132d = this.f2130b.b(this.f2129a);
            }
            return this.f2132d;
        }

        public int b(long j) {
            C0042a aVar = this.f2131c;
            if (aVar != null && j >= aVar.f2129a) {
                return aVar.b(j);
            }
            if (this.e == Integer.MIN_VALUE) {
                this.e = this.f2130b.c(this.f2129a);
            }
            return this.e;
        }

        public int c(long j) {
            C0042a aVar = this.f2131c;
            if (aVar != null && j >= aVar.f2129a) {
                return aVar.c(j);
            }
            if (this.f == Integer.MIN_VALUE) {
                this.f = this.f2130b.e(this.f2129a);
            }
            return this.f;
        }
    }

    static {
        Integer num;
        int i;
        try {
            num = Integer.getInteger("org.joda.time.tz.CachedDateTimeZone.size");
        } catch (SecurityException unused) {
            num = null;
        }
        if (num == null) {
            i = 512;
        } else {
            int i2 = 0;
            for (int intValue = num.intValue() - 1; intValue > 0; intValue >>= 1) {
                i2++;
            }
            i = 1 << i2;
        }
        f = i - 1;
    }

    private a(g gVar) {
        super(gVar.c());
        this.g = gVar;
    }

    public static a a(g gVar) {
        return gVar instanceof a ? (a) gVar : new a(gVar);
    }

    private C0042a i(long j) {
        long j2 = j & -4294967296L;
        C0042a aVar = new C0042a(this.g, j2);
        long j3 = 4294967295L | j2;
        C0042a aVar2 = aVar;
        while (true) {
            long g2 = this.g.g(j2);
            if (g2 == j2 || g2 > j3) {
                return aVar;
            }
            C0042a aVar3 = new C0042a(this.g, g2);
            aVar2.f2131c = aVar3;
            aVar2 = aVar3;
            j2 = g2;
        }
        return aVar;
    }

    private C0042a j(long j) {
        int i = (int) (j >> 32);
        C0042a[] aVarArr = this.h;
        int i2 = f & i;
        C0042a aVar = aVarArr[i2];
        if (aVar != null && ((int) (aVar.f2129a >> 32)) == i) {
            return aVar;
        }
        C0042a i3 = i(j);
        aVarArr[i2] = i3;
        return i3;
    }

    @Override // d.a.a.g
    public String b(long j) {
        return j(j).a(j);
    }

    @Override // d.a.a.g
    public int c(long j) {
        return j(j).b(j);
    }

    @Override // d.a.a.g
    public int e(long j) {
        return j(j).c(j);
    }

    @Override // d.a.a.g
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof a) {
            return this.g.equals(((a) obj).g);
        }
        return false;
    }

    @Override // d.a.a.g
    public boolean f() {
        return this.g.f();
    }

    @Override // d.a.a.g
    public long g(long j) {
        return this.g.g(j);
    }

    @Override // d.a.a.g
    public long h(long j) {
        return this.g.h(j);
    }

    @Override // d.a.a.g
    public int hashCode() {
        return this.g.hashCode();
    }
}
