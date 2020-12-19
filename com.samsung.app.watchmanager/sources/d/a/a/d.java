package d.a.a;

import java.io.Serializable;

public abstract class d implements Serializable {

    /* renamed from: a  reason: collision with root package name */
    private static final d f2063a = new a("era", (byte) 1, i.c(), null);

    /* renamed from: b  reason: collision with root package name */
    private static final d f2064b = new a("yearOfEra", (byte) 2, i.m(), i.c());

    /* renamed from: c  reason: collision with root package name */
    private static final d f2065c = new a("centuryOfEra", (byte) 3, i.a(), i.c());

    /* renamed from: d  reason: collision with root package name */
    private static final d f2066d = new a("yearOfCentury", (byte) 4, i.m(), i.a());
    private static final d e = new a("year", (byte) 5, i.m(), null);
    private static final d f = new a("dayOfYear", (byte) 6, i.b(), i.m());
    private static final d g = new a("monthOfYear", (byte) 7, i.i(), i.m());
    private static final d h = new a("dayOfMonth", (byte) 8, i.b(), i.i());
    private static final d i = new a("weekyearOfCentury", (byte) 9, i.l(), i.a());
    private static final d j = new a("weekyear", (byte) 10, i.l(), null);
    private static final d k = new a("weekOfWeekyear", (byte) 11, i.k(), i.l());
    private static final d l = new a("dayOfWeek", (byte) 12, i.b(), i.k());
    private static final d m = new a("halfdayOfDay", (byte) 13, i.e(), i.b());
    private static final d n = new a("hourOfHalfday", (byte) 14, i.f(), i.e());
    private static final d o = new a("clockhourOfHalfday", (byte) 15, i.f(), i.e());
    private static final d p = new a("clockhourOfDay", (byte) 16, i.f(), i.b());
    private static final d q = new a("hourOfDay", (byte) 17, i.f(), i.b());
    private static final d r = new a("minuteOfDay", (byte) 18, i.h(), i.b());
    private static final d s = new a("minuteOfHour", (byte) 19, i.h(), i.f());
    private static final d t = new a("secondOfDay", (byte) 20, i.j(), i.b());
    private static final d u = new a("secondOfMinute", (byte) 21, i.j(), i.h());
    private static final d v = new a("millisOfDay", (byte) 22, i.g(), i.b());
    private static final d w = new a("millisOfSecond", (byte) 23, i.g(), i.j());
    private final String x;

    private static class a extends d {
        private final transient i A;
        private final byte y;
        private final transient i z;

        a(String str, byte b2, i iVar, i iVar2) {
            super(str);
            this.y = b2;
            this.z = iVar;
            this.A = iVar2;
        }

        @Override // d.a.a.d
        public c a(a aVar) {
            a a2 = e.a(aVar);
            switch (this.y) {
                case 1:
                    return a2.i();
                case 2:
                    return a2.J();
                case 3:
                    return a2.b();
                case 4:
                    return a2.I();
                case 5:
                    return a2.H();
                case 6:
                    return a2.g();
                case 7:
                    return a2.w();
                case 8:
                    return a2.e();
                case 9:
                    return a2.E();
                case 10:
                    return a2.D();
                case 11:
                    return a2.B();
                case 12:
                    return a2.f();
                case 13:
                    return a2.l();
                case 14:
                    return a2.o();
                case 15:
                    return a2.d();
                case 16:
                    return a2.c();
                case 17:
                    return a2.n();
                case 18:
                    return a2.t();
                case 19:
                    return a2.u();
                case 20:
                    return a2.y();
                case 21:
                    return a2.z();
                case 22:
                    return a2.r();
                case 23:
                    return a2.s();
                default:
                    throw new InternalError();
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && this.y == ((a) obj).y;
        }

        @Override // d.a.a.d
        public i h() {
            return this.z;
        }

        public int hashCode() {
            return 1 << this.y;
        }
    }

    protected d(String str) {
        this.x = str;
    }

    public static d a() {
        return f2065c;
    }

    public static d b() {
        return p;
    }

    public static d c() {
        return o;
    }

    public static d d() {
        return h;
    }

    public static d e() {
        return l;
    }

    public static d f() {
        return f;
    }

    public static d g() {
        return f2063a;
    }

    public static d j() {
        return m;
    }

    public static d k() {
        return q;
    }

    public static d l() {
        return n;
    }

    public static d m() {
        return v;
    }

    public static d n() {
        return w;
    }

    public static d o() {
        return r;
    }

    public static d p() {
        return s;
    }

    public static d q() {
        return g;
    }

    public static d r() {
        return t;
    }

    public static d s() {
        return u;
    }

    public static d t() {
        return k;
    }

    public static d u() {
        return j;
    }

    public static d v() {
        return i;
    }

    public static d w() {
        return e;
    }

    public static d x() {
        return f2066d;
    }

    public static d y() {
        return f2064b;
    }

    public abstract c a(a aVar);

    public abstract i h();

    public String i() {
        return this.x;
    }

    public String toString() {
        return i();
    }
}
