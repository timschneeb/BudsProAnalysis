package d.a.a;

import java.io.Serializable;

public abstract class i implements Serializable {

    /* renamed from: a  reason: collision with root package name */
    static final i f2155a = new a("eras", (byte) 1);

    /* renamed from: b  reason: collision with root package name */
    static final i f2156b = new a("centuries", (byte) 2);

    /* renamed from: c  reason: collision with root package name */
    static final i f2157c = new a("weekyears", (byte) 3);

    /* renamed from: d  reason: collision with root package name */
    static final i f2158d = new a("years", (byte) 4);
    static final i e = new a("months", (byte) 5);
    static final i f = new a("weeks", (byte) 6);
    static final i g = new a("days", (byte) 7);
    static final i h = new a("halfdays", (byte) 8);
    static final i i = new a("hours", (byte) 9);
    static final i j = new a("minutes", (byte) 10);
    static final i k = new a("seconds", (byte) 11);
    static final i l = new a("millis", (byte) 12);
    private final String m;

    private static class a extends i {
        private final byte n;

        a(String str, byte b2) {
            super(str);
            this.n = b2;
        }

        @Override // d.a.a.i
        public h a(a aVar) {
            a a2 = e.a(aVar);
            switch (this.n) {
                case 1:
                    return a2.j();
                case 2:
                    return a2.a();
                case 3:
                    return a2.F();
                case 4:
                    return a2.K();
                case 5:
                    return a2.x();
                case 6:
                    return a2.C();
                case 7:
                    return a2.h();
                case 8:
                    return a2.m();
                case 9:
                    return a2.p();
                case 10:
                    return a2.v();
                case 11:
                    return a2.A();
                case 12:
                    return a2.q();
                default:
                    throw new InternalError();
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && this.n == ((a) obj).n;
        }

        public int hashCode() {
            return 1 << this.n;
        }
    }

    protected i(String str) {
        this.m = str;
    }

    public static i a() {
        return f2156b;
    }

    public static i b() {
        return g;
    }

    public static i c() {
        return f2155a;
    }

    public static i e() {
        return h;
    }

    public static i f() {
        return i;
    }

    public static i g() {
        return l;
    }

    public static i h() {
        return j;
    }

    public static i i() {
        return e;
    }

    public static i j() {
        return k;
    }

    public static i k() {
        return f;
    }

    public static i l() {
        return f2157c;
    }

    public static i m() {
        return f2158d;
    }

    public abstract h a(a aVar);

    public String d() {
        return this.m;
    }

    public String toString() {
        return d();
    }
}
