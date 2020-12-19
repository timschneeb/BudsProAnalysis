package d.a.a.b;

import d.a.a.d;
import d.a.a.e;
import d.a.a.j;
import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class m {

    /* renamed from: a  reason: collision with root package name */
    private static ConcurrentMap<Locale, m> f2027a = new ConcurrentHashMap();

    /* renamed from: b  reason: collision with root package name */
    private final String[] f2028b;

    /* renamed from: c  reason: collision with root package name */
    private final String[] f2029c;

    /* renamed from: d  reason: collision with root package name */
    private final String[] f2030d;
    private final String[] e;
    private final String[] f;
    private final String[] g;
    private final TreeMap<String, Integer> h;
    private final TreeMap<String, Integer> i;
    private final TreeMap<String, Integer> j;
    private final int k;
    private final int l;
    private final int m;
    private final int n;
    private final int o;
    private final int p;

    private m(Locale locale) {
        DateFormatSymbols a2 = e.a(locale);
        this.f2028b = a2.getEras();
        this.f2029c = b(a2.getWeekdays());
        this.f2030d = b(a2.getShortWeekdays());
        this.e = c(a2.getMonths());
        this.f = c(a2.getShortMonths());
        this.g = a2.getAmPmStrings();
        Integer[] numArr = new Integer[13];
        for (int i2 = 0; i2 < 13; i2++) {
            numArr[i2] = Integer.valueOf(i2);
        }
        this.h = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        a(this.h, this.f2028b, numArr);
        if ("en".equals(locale.getLanguage())) {
            this.h.put("BCE", numArr[0]);
            this.h.put("CE", numArr[1]);
        }
        this.i = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        a(this.i, this.f2029c, numArr);
        a(this.i, this.f2030d, numArr);
        a(this.i, 1, 7, numArr);
        this.j = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        a(this.j, this.e, numArr);
        a(this.j, this.f, numArr);
        a(this.j, 1, 12, numArr);
        this.k = a(this.f2028b);
        this.l = a(this.f2029c);
        this.m = a(this.f2030d);
        this.n = a(this.e);
        this.o = a(this.f);
        this.p = a(this.g);
    }

    private static int a(String[] strArr) {
        int length;
        int length2 = strArr.length;
        int i2 = 0;
        while (true) {
            length2--;
            if (length2 < 0) {
                return i2;
            }
            String str = strArr[length2];
            if (str != null && (length = str.length()) > i2) {
                i2 = length;
            }
        }
    }

    static m a(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        m mVar = f2027a.get(locale);
        if (mVar != null) {
            return mVar;
        }
        m mVar2 = new m(locale);
        m putIfAbsent = f2027a.putIfAbsent(locale, mVar2);
        return putIfAbsent != null ? putIfAbsent : mVar2;
    }

    private static void a(TreeMap<String, Integer> treeMap, int i2, int i3, Integer[] numArr) {
        while (i2 <= i3) {
            treeMap.put(String.valueOf(i2).intern(), numArr[i2]);
            i2++;
        }
    }

    private static void a(TreeMap<String, Integer> treeMap, String[] strArr, Integer[] numArr) {
        int length = strArr.length;
        while (true) {
            length--;
            if (length >= 0) {
                String str = strArr[length];
                if (str != null) {
                    treeMap.put(str, numArr[length]);
                }
            } else {
                return;
            }
        }
    }

    private static String[] b(String[] strArr) {
        String[] strArr2 = new String[8];
        int i2 = 1;
        while (i2 < 8) {
            strArr2[i2] = strArr[i2 < 7 ? i2 + 1 : 1];
            i2++;
        }
        return strArr2;
    }

    private static String[] c(String[] strArr) {
        String[] strArr2 = new String[13];
        for (int i2 = 1; i2 < 13; i2++) {
            strArr2[i2] = strArr[i2 - 1];
        }
        return strArr2;
    }

    public int a() {
        return this.l;
    }

    public int a(String str) {
        Integer num = this.i.get(str);
        if (num != null) {
            return num.intValue();
        }
        throw new j(d.e(), str);
    }

    public String a(int i2) {
        return this.f2030d[i2];
    }

    public int b() {
        return this.k;
    }

    public int b(String str) {
        Integer num = this.h.get(str);
        if (num != null) {
            return num.intValue();
        }
        throw new j(d.g(), str);
    }

    public String b(int i2) {
        return this.f2029c[i2];
    }

    public int c() {
        return this.p;
    }

    public int c(String str) {
        String[] strArr = this.g;
        int length = strArr.length;
        do {
            length--;
            if (length < 0) {
                throw new j(d.j(), str);
            }
        } while (!strArr[length].equalsIgnoreCase(str));
        return length;
    }

    public String c(int i2) {
        return this.f2028b[i2];
    }

    public int d() {
        return this.n;
    }

    public int d(String str) {
        Integer num = this.j.get(str);
        if (num != null) {
            return num.intValue();
        }
        throw new j(d.q(), str);
    }

    public String d(int i2) {
        return this.g[i2];
    }

    public String e(int i2) {
        return this.f[i2];
    }

    public String f(int i2) {
        return this.e[i2];
    }
}
