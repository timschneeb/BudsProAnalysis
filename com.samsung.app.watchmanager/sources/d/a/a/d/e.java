package d.a.a.d;

import d.a.a.c;
import d.a.a.d;
import d.a.a.g;
import d.a.a.h;
import d.a.a.i;
import d.a.a.j;
import d.a.a.k;
import java.util.Arrays;
import java.util.Locale;

public class e {

    /* renamed from: a  reason: collision with root package name */
    private final d.a.a.a f2106a;

    /* renamed from: b  reason: collision with root package name */
    private final long f2107b;

    /* renamed from: c  reason: collision with root package name */
    private final Locale f2108c;

    /* renamed from: d  reason: collision with root package name */
    private final int f2109d;
    private final g e;
    private final Integer f;
    private g g;
    private Integer h;
    private Integer i;
    private a[] j;
    private int k;
    private boolean l;
    private Object m;

    /* access modifiers changed from: package-private */
    public static class a implements Comparable<a> {

        /* renamed from: a  reason: collision with root package name */
        c f2110a;

        /* renamed from: b  reason: collision with root package name */
        int f2111b;

        /* renamed from: c  reason: collision with root package name */
        String f2112c;

        /* renamed from: d  reason: collision with root package name */
        Locale f2113d;

        a() {
        }

        /* renamed from: a */
        public int compareTo(a aVar) {
            c cVar = aVar.f2110a;
            int a2 = e.a(this.f2110a.f(), cVar.f());
            return a2 != 0 ? a2 : e.a(this.f2110a.a(), cVar.a());
        }

        /* access modifiers changed from: package-private */
        public long a(long j, boolean z) {
            String str = this.f2112c;
            long c2 = str == null ? this.f2110a.c(j, this.f2111b) : this.f2110a.a(j, str, this.f2113d);
            return z ? this.f2110a.e(c2) : c2;
        }

        /* access modifiers changed from: package-private */
        public void a(c cVar, int i) {
            this.f2110a = cVar;
            this.f2111b = i;
            this.f2112c = null;
            this.f2113d = null;
        }

        /* access modifiers changed from: package-private */
        public void a(c cVar, String str, Locale locale) {
            this.f2110a = cVar;
            this.f2111b = 0;
            this.f2112c = str;
            this.f2113d = locale;
        }
    }

    /* access modifiers changed from: package-private */
    public class b {

        /* renamed from: a  reason: collision with root package name */
        final g f2114a;

        /* renamed from: b  reason: collision with root package name */
        final Integer f2115b;

        /* renamed from: c  reason: collision with root package name */
        final a[] f2116c;

        /* renamed from: d  reason: collision with root package name */
        final int f2117d;

        b() {
            this.f2114a = e.this.g;
            this.f2115b = e.this.h;
            this.f2116c = e.this.j;
            this.f2117d = e.this.k;
        }

        /* access modifiers changed from: package-private */
        public boolean a(e eVar) {
            if (eVar != e.this) {
                return false;
            }
            eVar.g = this.f2114a;
            eVar.h = this.f2115b;
            eVar.j = this.f2116c;
            if (this.f2117d < eVar.k) {
                eVar.l = true;
            }
            eVar.k = this.f2117d;
            return true;
        }
    }

    public e(long j2, d.a.a.a aVar, Locale locale, Integer num, int i2) {
        d.a.a.a a2 = d.a.a.e.a(aVar);
        this.f2107b = j2;
        this.e = a2.k();
        this.f2106a = a2.G();
        this.f2108c = locale == null ? Locale.getDefault() : locale;
        this.f2109d = i2;
        this.f = num;
        this.g = this.e;
        this.i = this.f;
        this.j = new a[8];
    }

    static int a(h hVar, h hVar2) {
        if (hVar == null || !hVar.e()) {
            return (hVar2 == null || !hVar2.e()) ? 0 : -1;
        }
        if (hVar2 == null || !hVar2.e()) {
            return 1;
        }
        return -hVar.compareTo(hVar2);
    }

    private static void a(a[] aVarArr, int i2) {
        if (i2 > 10) {
            Arrays.sort(aVarArr, 0, i2);
            return;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = i3; i4 > 0; i4--) {
                int i5 = i4 - 1;
                if (aVarArr[i5].compareTo(aVarArr[i4]) <= 0) {
                    break;
                }
                a aVar = aVarArr[i4];
                aVarArr[i4] = aVarArr[i5];
                aVarArr[i5] = aVar;
            }
        }
    }

    private a g() {
        a[] aVarArr = this.j;
        int i2 = this.k;
        if (i2 == aVarArr.length || this.l) {
            a[] aVarArr2 = new a[(i2 == aVarArr.length ? i2 * 2 : aVarArr.length)];
            System.arraycopy(aVarArr, 0, aVarArr2, 0, i2);
            this.j = aVarArr2;
            this.l = false;
            aVarArr = aVarArr2;
        }
        this.m = null;
        a aVar = aVarArr[i2];
        if (aVar == null) {
            aVar = new a();
            aVarArr[i2] = aVar;
        }
        this.k = i2 + 1;
        return aVar;
    }

    /* access modifiers changed from: package-private */
    public long a(k kVar, CharSequence charSequence) {
        int a2 = kVar.a(this, charSequence, 0);
        if (a2 < 0) {
            a2 ^= -1;
        } else if (a2 >= charSequence.length()) {
            return a(true, charSequence);
        }
        throw new IllegalArgumentException(i.a(charSequence.toString(), a2));
    }

    public long a(boolean z, CharSequence charSequence) {
        a[] aVarArr = this.j;
        int i2 = this.k;
        if (this.l) {
            aVarArr = (a[]) aVarArr.clone();
            this.j = aVarArr;
            this.l = false;
        }
        a(aVarArr, i2);
        if (i2 > 0) {
            h a2 = i.i().a(this.f2106a);
            h a3 = i.b().a(this.f2106a);
            h a4 = aVarArr[0].f2110a.a();
            if (a(a4, a2) >= 0 && a(a4, a3) <= 0) {
                a(d.w(), this.f2109d);
                return a(z, charSequence);
            }
        }
        long j2 = this.f2107b;
        for (int i3 = 0; i3 < i2; i3++) {
            try {
                j2 = aVarArr[i3].a(j2, z);
            } catch (j e2) {
                if (charSequence != null) {
                    e2.a("Cannot parse \"" + ((Object) charSequence) + '\"');
                }
                throw e2;
            }
        }
        if (z) {
            int i4 = 0;
            while (i4 < i2) {
                if (!aVarArr[i4].f2110a.h()) {
                    j2 = aVarArr[i4].a(j2, i4 == i2 + -1);
                }
                i4++;
            }
        }
        Integer num = this.h;
        if (num != null) {
            return j2 - ((long) num.intValue());
        }
        g gVar = this.g;
        if (gVar == null) {
            return j2;
        }
        int d2 = gVar.d(j2);
        long j3 = j2 - ((long) d2);
        if (d2 == this.g.c(j3)) {
            return j3;
        }
        String str = "Illegal instant due to time zone offset transition (" + this.g + ')';
        if (charSequence != null) {
            str = "Cannot parse \"" + ((Object) charSequence) + "\": " + str;
        }
        throw new k(str);
    }

    public long a(boolean z, String str) {
        return a(z, (CharSequence) str);
    }

    public d.a.a.a a() {
        return this.f2106a;
    }

    public void a(c cVar, int i2) {
        g().a(cVar, i2);
    }

    public void a(d dVar, int i2) {
        g().a(dVar.a(this.f2106a), i2);
    }

    public void a(d dVar, String str, Locale locale) {
        g().a(dVar.a(this.f2106a), str, locale);
    }

    public void a(g gVar) {
        this.m = null;
        this.g = gVar;
    }

    public void a(Integer num) {
        this.m = null;
        this.h = num;
    }

    public boolean a(Object obj) {
        if (!(obj instanceof b) || !((b) obj).a(this)) {
            return false;
        }
        this.m = obj;
        return true;
    }

    public Locale b() {
        return this.f2108c;
    }

    public Integer c() {
        return this.h;
    }

    public Integer d() {
        return this.i;
    }

    public g e() {
        return this.g;
    }

    public Object f() {
        if (this.m == null) {
            this.m = new b();
        }
        return this.m;
    }
}
