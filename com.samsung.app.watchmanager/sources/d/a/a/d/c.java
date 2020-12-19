package d.a.a.d;

import com.samsung.android.app.twatchmanager.update.SAGUIDHelper;
import d.a.a.m;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class c {

    /* renamed from: a  reason: collision with root package name */
    private ArrayList<Object> f2073a = new ArrayList<>();

    /* renamed from: b  reason: collision with root package name */
    private Object f2074b;

    /* access modifiers changed from: package-private */
    public static class a implements m, k {

        /* renamed from: a  reason: collision with root package name */
        private final char f2075a;

        a(char c2) {
            this.f2075a = c2;
        }

        @Override // d.a.a.d.k
        public int a(e eVar, CharSequence charSequence, int i) {
            char upperCase;
            char upperCase2;
            if (i >= charSequence.length()) {
                return i ^ -1;
            }
            char charAt = charSequence.charAt(i);
            char c2 = this.f2075a;
            return (charAt == c2 || (upperCase = Character.toUpperCase(charAt)) == (upperCase2 = Character.toUpperCase(c2)) || Character.toLowerCase(upperCase) == Character.toLowerCase(upperCase2)) ? i + 1 : i ^ -1;
        }

        @Override // d.a.a.d.m
        public void a(Appendable appendable, long j, d.a.a.a aVar, int i, d.a.a.g gVar, Locale locale) {
            appendable.append(this.f2075a);
        }

        @Override // d.a.a.d.k
        public int b() {
            return 1;
        }

        @Override // d.a.a.d.m
        public int c() {
            return 1;
        }
    }

    /* access modifiers changed from: package-private */
    public static class b implements m, k {

        /* renamed from: a  reason: collision with root package name */
        private final m[] f2076a;

        /* renamed from: b  reason: collision with root package name */
        private final k[] f2077b;

        /* renamed from: c  reason: collision with root package name */
        private final int f2078c;

        /* renamed from: d  reason: collision with root package name */
        private final int f2079d;

        b(List<Object> list) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            a(list, arrayList, arrayList2);
            if (arrayList.contains(null) || arrayList.isEmpty()) {
                this.f2076a = null;
                this.f2078c = 0;
            } else {
                int size = arrayList.size();
                this.f2076a = new m[size];
                int i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    m mVar = (m) arrayList.get(i2);
                    i += mVar.c();
                    this.f2076a[i2] = mVar;
                }
                this.f2078c = i;
            }
            if (arrayList2.contains(null) || arrayList2.isEmpty()) {
                this.f2077b = null;
                this.f2079d = 0;
                return;
            }
            int size2 = arrayList2.size();
            this.f2077b = new k[size2];
            int i3 = 0;
            for (int i4 = 0; i4 < size2; i4++) {
                k kVar = (k) arrayList2.get(i4);
                i3 += kVar.b();
                this.f2077b[i4] = kVar;
            }
            this.f2079d = i3;
        }

        private void a(List<Object> list, List<Object> list2, List<Object> list3) {
            int size = list.size();
            for (int i = 0; i < size; i += 2) {
                Object obj = list.get(i);
                if (obj instanceof b) {
                    a(list2, ((b) obj).f2076a);
                } else {
                    list2.add(obj);
                }
                Object obj2 = list.get(i + 1);
                if (obj2 instanceof b) {
                    a(list3, ((b) obj2).f2077b);
                } else {
                    list3.add(obj2);
                }
            }
        }

        private void a(List<Object> list, Object[] objArr) {
            if (objArr != null) {
                for (Object obj : objArr) {
                    list.add(obj);
                }
            }
        }

        @Override // d.a.a.d.k
        public int a(e eVar, CharSequence charSequence, int i) {
            k[] kVarArr = this.f2077b;
            if (kVarArr != null) {
                int length = kVarArr.length;
                for (int i2 = 0; i2 < length && i >= 0; i2++) {
                    i = kVarArr[i2].a(eVar, charSequence, i);
                }
                return i;
            }
            throw new UnsupportedOperationException();
        }

        @Override // d.a.a.d.m
        public void a(Appendable appendable, long j, d.a.a.a aVar, int i, d.a.a.g gVar, Locale locale) {
            m[] mVarArr = this.f2076a;
            if (mVarArr != null) {
                Locale locale2 = locale == null ? Locale.getDefault() : locale;
                for (m mVar : mVarArr) {
                    mVar.a(appendable, j, aVar, i, gVar, locale2);
                }
                return;
            }
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            return this.f2077b != null;
        }

        @Override // d.a.a.d.k
        public int b() {
            return this.f2079d;
        }

        @Override // d.a.a.d.m
        public int c() {
            return this.f2078c;
        }

        /* access modifiers changed from: package-private */
        public boolean d() {
            return this.f2076a != null;
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d.a.a.d.c$c  reason: collision with other inner class name */
    public static class C0041c extends g {
        protected C0041c(d.a.a.d dVar, int i, boolean z) {
            super(dVar, i, z, i);
        }

        @Override // d.a.a.d.k, d.a.a.d.c.f
        public int a(e eVar, CharSequence charSequence, int i) {
            int i2;
            char charAt;
            int a2 = super.a(eVar, charSequence, i);
            if (a2 < 0 || a2 == (i2 = this.f2086b + i)) {
                return a2;
            }
            if (this.f2087c && ((charAt = charSequence.charAt(i)) == '-' || charAt == '+')) {
                i2++;
            }
            return a2 > i2 ? (i2 + 1) ^ -1 : a2 < i2 ? a2 ^ -1 : a2;
        }
    }

    /* access modifiers changed from: package-private */
    public static class d implements m, k {

        /* renamed from: a  reason: collision with root package name */
        private final d.a.a.d f2080a;

        /* renamed from: b  reason: collision with root package name */
        protected int f2081b;

        /* renamed from: c  reason: collision with root package name */
        protected int f2082c;

        protected d(d.a.a.d dVar, int i, int i2) {
            this.f2080a = dVar;
            int i3 = i2 <= 18 ? i2 : 18;
            this.f2081b = i;
            this.f2082c = i3;
        }

        /* JADX WARNING: Removed duplicated region for block: B:25:0x007a A[LOOP:0: B:1:0x000a->B:25:0x007a, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x006c A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private long[] a(long r8, d.a.a.c r10) {
            /*
            // Method dump skipped, instructions count: 166
            */
            throw new UnsupportedOperationException("Method not decompiled: d.a.a.d.c.d.a(long, d.a.a.c):long[]");
        }

        @Override // d.a.a.d.k
        public int a(e eVar, CharSequence charSequence, int i) {
            d.a.a.c a2 = this.f2080a.a(eVar.a());
            int min = Math.min(this.f2082c, charSequence.length() - i);
            long c2 = a2.a().c() * 10;
            long j = 0;
            int i2 = 0;
            while (i2 < min) {
                char charAt = charSequence.charAt(i + i2);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                i2++;
                c2 /= 10;
                j += ((long) (charAt - '0')) * c2;
            }
            long j2 = j / 10;
            if (i2 == 0) {
                return i ^ -1;
            }
            if (j2 > 2147483647L) {
                return i ^ -1;
            }
            eVar.a(new d.a.a.c.k(d.a.a.d.n(), d.a.a.c.i.f2048a, a2.a()), (int) j2);
            return i + i2;
        }

        /* access modifiers changed from: protected */
        public void a(Appendable appendable, long j, d.a.a.a aVar) {
            d.a.a.c a2 = this.f2080a.a(aVar);
            int i = this.f2081b;
            try {
                long c2 = a2.c(j);
                if (c2 == 0) {
                    while (true) {
                        i--;
                        if (i >= 0) {
                            appendable.append('0');
                        } else {
                            return;
                        }
                    }
                } else {
                    long[] a3 = a(c2, a2);
                    long j2 = a3[0];
                    int i2 = (int) a3[1];
                    String num = (2147483647L & j2) == j2 ? Integer.toString((int) j2) : Long.toString(j2);
                    int length = num.length();
                    while (length < i2) {
                        appendable.append('0');
                        i--;
                        i2--;
                    }
                    if (i < i2) {
                        while (i < i2 && length > 1 && num.charAt(length - 1) == '0') {
                            i2--;
                            length--;
                        }
                        if (length < num.length()) {
                            for (int i3 = 0; i3 < length; i3++) {
                                appendable.append(num.charAt(i3));
                            }
                            return;
                        }
                    }
                    appendable.append(num);
                }
            } catch (RuntimeException unused) {
                c.a(appendable, i);
            }
        }

        @Override // d.a.a.d.m
        public void a(Appendable appendable, long j, d.a.a.a aVar, int i, d.a.a.g gVar, Locale locale) {
            a(appendable, j, aVar);
        }

        @Override // d.a.a.d.k
        public int b() {
            return this.f2082c;
        }

        @Override // d.a.a.d.m
        public int c() {
            return this.f2082c;
        }
    }

    /* access modifiers changed from: package-private */
    public static class e implements k {

        /* renamed from: a  reason: collision with root package name */
        private final k[] f2083a;

        /* renamed from: b  reason: collision with root package name */
        private final int f2084b;

        e(k[] kVarArr) {
            int b2;
            this.f2083a = kVarArr;
            int length = kVarArr.length;
            int i = 0;
            while (true) {
                length--;
                if (length >= 0) {
                    k kVar = kVarArr[length];
                    if (kVar != null && (b2 = kVar.b()) > i) {
                        i = b2;
                    }
                } else {
                    this.f2084b = i;
                    return;
                }
            }
        }

        @Override // d.a.a.d.k
        public int a(e eVar, CharSequence charSequence, int i) {
            int i2;
            int i3;
            k[] kVarArr = this.f2083a;
            int length = kVarArr.length;
            Object f = eVar.f();
            boolean z = false;
            int i4 = i;
            int i5 = i4;
            Object obj = null;
            int i6 = 0;
            while (true) {
                if (i6 >= length) {
                    break;
                }
                k kVar = kVarArr[i6];
                if (kVar != null) {
                    int a2 = kVar.a(eVar, charSequence, i);
                    if (a2 >= i) {
                        if (a2 <= i4) {
                            continue;
                        } else if (a2 >= charSequence.length() || (i3 = i6 + 1) >= length || kVarArr[i3] == null) {
                            return a2;
                        } else {
                            obj = eVar.f();
                            i4 = a2;
                        }
                    } else if (a2 < 0 && (i2 = a2 ^ -1) > i5) {
                        i5 = i2;
                    }
                    eVar.a(f);
                    i6++;
                } else if (i4 <= i) {
                    return i;
                } else {
                    z = true;
                }
            }
            if (i4 <= i && (i4 != i || !z)) {
                return i5 ^ -1;
            }
            if (obj != null) {
                eVar.a(obj);
            }
            return i4;
        }

        @Override // d.a.a.d.k
        public int b() {
            return this.f2084b;
        }
    }

    static abstract class f implements m, k {

        /* renamed from: a  reason: collision with root package name */
        protected final d.a.a.d f2085a;

        /* renamed from: b  reason: collision with root package name */
        protected final int f2086b;

        /* renamed from: c  reason: collision with root package name */
        protected final boolean f2087c;

        f(d.a.a.d dVar, int i, boolean z) {
            this.f2085a = dVar;
            this.f2086b = i;
            this.f2087c = z;
        }

        @Override // d.a.a.d.k
        public int a(e eVar, CharSequence charSequence, int i) {
            int i2;
            int i3;
            boolean z;
            boolean z2;
            char charAt;
            int min = Math.min(this.f2086b, charSequence.length() - i);
            int i4 = 0;
            boolean z3 = false;
            boolean z4 = false;
            while (true) {
                if (i4 >= min) {
                    break;
                }
                int i5 = i + i4;
                char charAt2 = charSequence.charAt(i5);
                if (i4 != 0 || ((charAt2 != '-' && charAt2 != '+') || !this.f2087c)) {
                    if (charAt2 < '0' || charAt2 > '9') {
                        break;
                    }
                    i4++;
                } else {
                    z = true;
                    z2 = charAt2 == '-';
                    if (charAt2 != '+') {
                        z = false;
                    }
                    int i6 = i4 + 1;
                    if (i6 >= min || (charAt = charSequence.charAt(i5 + 1)) < '0' || charAt > '9') {
                        z4 = z;
                        z3 = z2;
                    } else {
                        min = Math.min(min + 1, charSequence.length() - i);
                        i4 = i6;
                        z4 = z;
                        z3 = z2;
                    }
                }
            }
            z4 = z;
            z3 = z2;
            if (i4 == 0) {
                return i ^ -1;
            }
            if (i4 < 9) {
                int i7 = (z3 || z4) ? i + 1 : i;
                int i8 = i7 + 1;
                try {
                    int charAt3 = charSequence.charAt(i7) - '0';
                    i2 = i + i4;
                    while (i8 < i2) {
                        i8++;
                        charAt3 = (((charAt3 << 3) + (charAt3 << 1)) + charSequence.charAt(i8)) - 48;
                    }
                    i3 = z3 ? -charAt3 : charAt3;
                } catch (StringIndexOutOfBoundsException unused) {
                    return i ^ -1;
                }
            } else if (z4) {
                i2 = i + i4;
                i3 = Integer.parseInt(charSequence.subSequence(i + 1, i2).toString());
            } else {
                int i9 = i4 + i;
                i3 = Integer.parseInt(charSequence.subSequence(i, i9).toString());
                i2 = i9;
            }
            eVar.a(this.f2085a, i3);
            return i2;
        }

        @Override // d.a.a.d.k
        public int b() {
            return this.f2086b;
        }
    }

    /* access modifiers changed from: package-private */
    public static class g extends f {

        /* renamed from: d  reason: collision with root package name */
        protected final int f2088d;

        protected g(d.a.a.d dVar, int i, boolean z, int i2) {
            super(dVar, i, z);
            this.f2088d = i2;
        }

        @Override // d.a.a.d.m
        public void a(Appendable appendable, long j, d.a.a.a aVar, int i, d.a.a.g gVar, Locale locale) {
            try {
                i.a(appendable, this.f2085a.a(aVar).a(j), this.f2088d);
            } catch (RuntimeException unused) {
                c.a(appendable, this.f2088d);
            }
        }

        @Override // d.a.a.d.m
        public int c() {
            return this.f2086b;
        }
    }

    /* access modifiers changed from: package-private */
    public static class h implements m, k {

        /* renamed from: a  reason: collision with root package name */
        private final String f2089a;

        h(String str) {
            this.f2089a = str;
        }

        @Override // d.a.a.d.k
        public int a(e eVar, CharSequence charSequence, int i) {
            return c.b(charSequence, i, this.f2089a) ? i + this.f2089a.length() : i ^ -1;
        }

        @Override // d.a.a.d.m
        public void a(Appendable appendable, long j, d.a.a.a aVar, int i, d.a.a.g gVar, Locale locale) {
            appendable.append(this.f2089a);
        }

        @Override // d.a.a.d.k
        public int b() {
            return this.f2089a.length();
        }

        @Override // d.a.a.d.m
        public int c() {
            return this.f2089a.length();
        }
    }

    /* access modifiers changed from: package-private */
    public static class i implements m, k {

        /* renamed from: a  reason: collision with root package name */
        private static Map<Locale, Map<d.a.a.d, Object[]>> f2090a = new ConcurrentHashMap();

        /* renamed from: b  reason: collision with root package name */
        private final d.a.a.d f2091b;

        /* renamed from: c  reason: collision with root package name */
        private final boolean f2092c;

        i(d.a.a.d dVar, boolean z) {
            this.f2091b = dVar;
            this.f2092c = z;
        }

        private String a(long j, d.a.a.a aVar, Locale locale) {
            d.a.a.c a2 = this.f2091b.a(aVar);
            return this.f2092c ? a2.a(j, locale) : a2.b(j, locale);
        }

        @Override // d.a.a.d.k
        public int a(e eVar, CharSequence charSequence, int i) {
            int i2;
            Map map;
            Locale b2 = eVar.b();
            Map<d.a.a.d, Object[]> map2 = f2090a.get(b2);
            if (map2 == null) {
                map2 = new ConcurrentHashMap<>();
                f2090a.put(b2, map2);
            }
            Object[] objArr = map2.get(this.f2091b);
            if (objArr == null) {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(32);
                m.a a2 = new d.a.a.m(0, d.a.a.g.f2149a).a(this.f2091b);
                int g = a2.g();
                int e = a2.e();
                if (e - g > 32) {
                    return i ^ -1;
                }
                i2 = a2.c(b2);
                while (g <= e) {
                    a2.a(g);
                    concurrentHashMap.put(a2.a(b2), Boolean.TRUE);
                    concurrentHashMap.put(a2.a(b2).toLowerCase(b2), Boolean.TRUE);
                    concurrentHashMap.put(a2.a(b2).toUpperCase(b2), Boolean.TRUE);
                    concurrentHashMap.put(a2.b(b2), Boolean.TRUE);
                    concurrentHashMap.put(a2.b(b2).toLowerCase(b2), Boolean.TRUE);
                    concurrentHashMap.put(a2.b(b2).toUpperCase(b2), Boolean.TRUE);
                    g++;
                }
                if ("en".equals(b2.getLanguage()) && this.f2091b == d.a.a.d.g()) {
                    concurrentHashMap.put("BCE", Boolean.TRUE);
                    concurrentHashMap.put("bce", Boolean.TRUE);
                    concurrentHashMap.put("CE", Boolean.TRUE);
                    concurrentHashMap.put("ce", Boolean.TRUE);
                    i2 = 3;
                }
                map2.put(this.f2091b, new Object[]{concurrentHashMap, Integer.valueOf(i2)});
                map = concurrentHashMap;
            } else {
                map = (Map) objArr[0];
                i2 = ((Integer) objArr[1]).intValue();
            }
            for (int min = Math.min(charSequence.length(), i2 + i); min > i; min--) {
                String charSequence2 = charSequence.subSequence(i, min).toString();
                if (map.containsKey(charSequence2)) {
                    eVar.a(this.f2091b, charSequence2, b2);
                    return min;
                }
            }
            return i ^ -1;
        }

        @Override // d.a.a.d.m
        public void a(Appendable appendable, long j, d.a.a.a aVar, int i, d.a.a.g gVar, Locale locale) {
            try {
                appendable.append(a(j, aVar, locale));
            } catch (RuntimeException unused) {
                appendable.append((char) 65533);
            }
        }

        @Override // d.a.a.d.k
        public int b() {
            return c();
        }

        @Override // d.a.a.d.m
        public int c() {
            return this.f2092c ? 6 : 20;
        }
    }

    /* access modifiers changed from: package-private */
    public enum j implements m, k {
        INSTANCE;
        

        /* renamed from: b  reason: collision with root package name */
        private static final List<String> f2094b = new ArrayList(d.a.a.g.a());

        /* renamed from: c  reason: collision with root package name */
        private static final Map<String, List<String>> f2095c = new HashMap();

        /* renamed from: d  reason: collision with root package name */
        private static final List<String> f2096d = new ArrayList();
        static final int e;
        static final int f;

        static {
            Collections.sort(f2094b);
            int i = 0;
            int i2 = 0;
            for (String str : f2094b) {
                int indexOf = str.indexOf(47);
                if (indexOf >= 0) {
                    if (indexOf < str.length()) {
                        indexOf++;
                    }
                    i2 = Math.max(i2, indexOf);
                    String substring = str.substring(0, indexOf + 1);
                    String substring2 = str.substring(indexOf);
                    if (!f2095c.containsKey(substring)) {
                        f2095c.put(substring, new ArrayList());
                    }
                    f2095c.get(substring).add(substring2);
                } else {
                    f2096d.add(str);
                }
                i = Math.max(i, str.length());
            }
            e = i;
            f = i2;
        }

        @Override // d.a.a.d.k
        public int a(e eVar, CharSequence charSequence, int i) {
            String str;
            int i2;
            List<String> list = f2096d;
            int length = charSequence.length();
            int min = Math.min(length, f + i);
            int i3 = i;
            while (true) {
                if (i3 >= min) {
                    str = "";
                    i2 = i;
                    break;
                } else if (charSequence.charAt(i3) == '/') {
                    int i4 = i3 + 1;
                    str = charSequence.subSequence(i, i4).toString();
                    i2 = str.length() + i;
                    list = f2095c.get(i3 < length ? str + charSequence.charAt(i4) : str);
                    if (list == null) {
                        return i ^ -1;
                    }
                } else {
                    i3++;
                }
            }
            String str2 = null;
            for (int i5 = 0; i5 < list.size(); i5++) {
                String str3 = list.get(i5);
                if (c.a(charSequence, i2, str3) && (str2 == null || str3.length() > str2.length())) {
                    str2 = str3;
                }
            }
            if (str2 == null) {
                return i ^ -1;
            }
            eVar.a(d.a.a.g.a(str + str2));
            return i2 + str2.length();
        }

        @Override // d.a.a.d.m
        public void a(Appendable appendable, long j, d.a.a.a aVar, int i, d.a.a.g gVar, Locale locale) {
            appendable.append(gVar != null ? gVar.c() : "");
        }

        @Override // d.a.a.d.k
        public int b() {
            return e;
        }

        @Override // d.a.a.d.m
        public int c() {
            return e;
        }
    }

    /* access modifiers changed from: package-private */
    public static class k implements m, k {

        /* renamed from: a  reason: collision with root package name */
        private final Map<String, d.a.a.g> f2097a;

        /* renamed from: b  reason: collision with root package name */
        private final int f2098b;

        k(int i, Map<String, d.a.a.g> map) {
            this.f2098b = i;
            this.f2097a = map;
        }

        private String a(long j, d.a.a.g gVar, Locale locale) {
            if (gVar == null) {
                return "";
            }
            int i = this.f2098b;
            return i != 0 ? i != 1 ? "" : gVar.b(j, locale) : gVar.a(j, locale);
        }

        @Override // d.a.a.d.k
        public int a(e eVar, CharSequence charSequence, int i) {
            Map<String, d.a.a.g> map = this.f2097a;
            if (map == null) {
                map = d.a.a.e.b();
            }
            String str = null;
            for (String str2 : map.keySet()) {
                if (c.a(charSequence, i, str2) && (str == null || str2.length() > str.length())) {
                    str = str2;
                }
            }
            if (str == null) {
                return i ^ -1;
            }
            eVar.a(map.get(str));
            return i + str.length();
        }

        @Override // d.a.a.d.m
        public void a(Appendable appendable, long j, d.a.a.a aVar, int i, d.a.a.g gVar, Locale locale) {
            appendable.append(a(j - ((long) i), gVar, locale));
        }

        @Override // d.a.a.d.k
        public int b() {
            return this.f2098b == 1 ? 4 : 20;
        }

        @Override // d.a.a.d.m
        public int c() {
            return this.f2098b == 1 ? 4 : 20;
        }
    }

    /* access modifiers changed from: package-private */
    public static class l implements m, k {

        /* renamed from: a  reason: collision with root package name */
        private final String f2099a;

        /* renamed from: b  reason: collision with root package name */
        private final String f2100b;

        /* renamed from: c  reason: collision with root package name */
        private final boolean f2101c;

        /* renamed from: d  reason: collision with root package name */
        private final int f2102d;
        private final int e;

        l(String str, String str2, boolean z, int i, int i2) {
            this.f2099a = str;
            this.f2100b = str2;
            this.f2101c = z;
            if (i <= 0 || i2 < i) {
                throw new IllegalArgumentException();
            }
            int i3 = 4;
            if (i > 4) {
                i2 = 4;
            } else {
                i3 = i;
            }
            this.f2102d = i3;
            this.e = i2;
        }

        private int a(CharSequence charSequence, int i, int i2) {
            int i3 = 0;
            for (int min = Math.min(charSequence.length() - i, i2); min > 0; min--) {
                char charAt = charSequence.charAt(i + i3);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                i3++;
            }
            return i3;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0083, code lost:
            if (r6 <= '9') goto L_0x0085;
         */
        @Override // d.a.a.d.k
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int a(d.a.a.d.e r12, java.lang.CharSequence r13, int r14) {
            /*
            // Method dump skipped, instructions count: 305
            */
            throw new UnsupportedOperationException("Method not decompiled: d.a.a.d.c.l.a(d.a.a.d.e, java.lang.CharSequence, int):int");
        }

        @Override // d.a.a.d.m
        public void a(Appendable appendable, long j, d.a.a.a aVar, int i, d.a.a.g gVar, Locale locale) {
            String str;
            if (gVar != null) {
                if (i != 0 || (str = this.f2099a) == null) {
                    if (i >= 0) {
                        appendable.append('+');
                    } else {
                        appendable.append('-');
                        i = -i;
                    }
                    int i2 = i / 3600000;
                    i.a(appendable, i2, 2);
                    if (this.e != 1) {
                        int i3 = i - (i2 * 3600000);
                        if (i3 != 0 || this.f2102d > 1) {
                            int i4 = i3 / 60000;
                            if (this.f2101c) {
                                appendable.append(':');
                            }
                            i.a(appendable, i4, 2);
                            if (this.e != 2) {
                                int i5 = i3 - (i4 * 60000);
                                if (i5 != 0 || this.f2102d > 2) {
                                    int i6 = i5 / SAGUIDHelper.GUID_REQUEST_ID;
                                    if (this.f2101c) {
                                        appendable.append(':');
                                    }
                                    i.a(appendable, i6, 2);
                                    if (this.e != 3) {
                                        int i7 = i5 - (i6 * SAGUIDHelper.GUID_REQUEST_ID);
                                        if (i7 != 0 || this.f2102d > 3) {
                                            if (this.f2101c) {
                                                appendable.append('.');
                                            }
                                            i.a(appendable, i7, 3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                appendable.append(str);
            }
        }

        @Override // d.a.a.d.k
        public int b() {
            return c();
        }

        @Override // d.a.a.d.m
        public int c() {
            int i = this.f2102d;
            int i2 = (i + 1) << 1;
            if (this.f2101c) {
                i2 += i - 1;
            }
            String str = this.f2099a;
            return (str == null || str.length() <= i2) ? i2 : this.f2099a.length();
        }
    }

    /* access modifiers changed from: package-private */
    public static class m implements m, k {

        /* renamed from: a  reason: collision with root package name */
        private final d.a.a.d f2103a;

        /* renamed from: b  reason: collision with root package name */
        private final int f2104b;

        /* renamed from: c  reason: collision with root package name */
        private final boolean f2105c;

        m(d.a.a.d dVar, int i, boolean z) {
            this.f2103a = dVar;
            this.f2104b = i;
            this.f2105c = z;
        }

        private int a(long j, d.a.a.a aVar) {
            try {
                int a2 = this.f2103a.a(aVar).a(j);
                if (a2 < 0) {
                    a2 = -a2;
                }
                return a2 % 100;
            } catch (RuntimeException unused) {
                return -1;
            }
        }

        @Override // d.a.a.d.k
        public int a(e eVar, CharSequence charSequence, int i) {
            int i2;
            int i3;
            int length = charSequence.length() - i;
            if (this.f2105c) {
                int i4 = i;
                int i5 = 0;
                boolean z = false;
                boolean z2 = false;
                while (i5 < length) {
                    char charAt = charSequence.charAt(i4 + i5);
                    if (i5 != 0 || (charAt != '-' && charAt != '+')) {
                        if (charAt < '0' || charAt > '9') {
                            break;
                        }
                        i5++;
                    } else {
                        z2 = charAt == '-';
                        if (z2) {
                            i5++;
                        } else {
                            i4++;
                            length--;
                        }
                        z = true;
                    }
                }
                if (i5 == 0) {
                    return i4 ^ -1;
                }
                if (z || i5 != 2) {
                    if (i5 >= 9) {
                        i2 = i5 + i4;
                        i3 = Integer.parseInt(charSequence.subSequence(i4, i2).toString());
                    } else {
                        int i6 = z2 ? i4 + 1 : i4;
                        int i7 = i6 + 1;
                        try {
                            int charAt2 = charSequence.charAt(i6) - '0';
                            i2 = i5 + i4;
                            while (i7 < i2) {
                                i7++;
                                charAt2 = (((charAt2 << 3) + (charAt2 << 1)) + charSequence.charAt(i7)) - 48;
                            }
                            i3 = z2 ? -charAt2 : charAt2;
                        } catch (StringIndexOutOfBoundsException unused) {
                            return i4 ^ -1;
                        }
                    }
                    eVar.a(this.f2103a, i3);
                    return i2;
                }
                i = i4;
            } else if (Math.min(2, length) < 2) {
                return i ^ -1;
            }
            char charAt3 = charSequence.charAt(i);
            if (charAt3 < '0' || charAt3 > '9') {
                return i ^ -1;
            }
            int i8 = charAt3 - '0';
            char charAt4 = charSequence.charAt(i + 1);
            if (charAt4 < '0' || charAt4 > '9') {
                return i ^ -1;
            }
            int i9 = (((i8 << 3) + (i8 << 1)) + charAt4) - 48;
            int i10 = this.f2104b;
            if (eVar.d() != null) {
                i10 = eVar.d().intValue();
            }
            int i11 = i10 - 50;
            int i12 = 100;
            int i13 = i11 >= 0 ? i11 % 100 : ((i11 + 1) % 100) + 99;
            if (i9 >= i13) {
                i12 = 0;
            }
            eVar.a(this.f2103a, i9 + ((i11 + i12) - i13));
            return i + 2;
        }

        @Override // d.a.a.d.m
        public void a(Appendable appendable, long j, d.a.a.a aVar, int i, d.a.a.g gVar, Locale locale) {
            int a2 = a(j, aVar);
            if (a2 < 0) {
                appendable.append((char) 65533);
                appendable.append((char) 65533);
                return;
            }
            i.a(appendable, a2, 2);
        }

        @Override // d.a.a.d.k
        public int b() {
            return this.f2105c ? 4 : 2;
        }

        @Override // d.a.a.d.m
        public int c() {
            return 2;
        }
    }

    /* access modifiers changed from: package-private */
    public static class n extends f {
        protected n(d.a.a.d dVar, int i, boolean z) {
            super(dVar, i, z);
        }

        @Override // d.a.a.d.m
        public void a(Appendable appendable, long j, d.a.a.a aVar, int i, d.a.a.g gVar, Locale locale) {
            try {
                i.a(appendable, this.f2085a.a(aVar).a(j));
            } catch (RuntimeException unused) {
                appendable.append((char) 65533);
            }
        }

        @Override // d.a.a.d.m
        public int c() {
            return this.f2086b;
        }
    }

    private c a(m mVar, k kVar) {
        this.f2074b = null;
        this.f2073a.add(mVar);
        this.f2073a.add(kVar);
        return this;
    }

    private c a(Object obj) {
        this.f2074b = null;
        this.f2073a.add(obj);
        this.f2073a.add(obj);
        return this;
    }

    private void a(g gVar) {
        if (gVar == null) {
            throw new IllegalArgumentException("No printer supplied");
        }
    }

    static void a(Appendable appendable, int i2) {
        while (true) {
            i2--;
            if (i2 >= 0) {
                appendable.append((char) 65533);
            } else {
                return;
            }
        }
    }

    static boolean a(CharSequence charSequence, int i2, String str) {
        int length = str.length();
        if (charSequence.length() - i2 < length) {
            return false;
        }
        for (int i3 = 0; i3 < length; i3++) {
            if (charSequence.charAt(i2 + i3) != str.charAt(i3)) {
                return false;
            }
        }
        return true;
    }

    static boolean b(CharSequence charSequence, int i2, String str) {
        char upperCase;
        char upperCase2;
        int length = str.length();
        if (charSequence.length() - i2 < length) {
            return false;
        }
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = charSequence.charAt(i2 + i3);
            char charAt2 = str.charAt(i3);
            if (!(charAt == charAt2 || (upperCase = Character.toUpperCase(charAt)) == (upperCase2 = Character.toUpperCase(charAt2)) || Character.toLowerCase(upperCase) == Character.toLowerCase(upperCase2))) {
                return false;
            }
        }
        return true;
    }

    private boolean b(Object obj) {
        if (!(obj instanceof k)) {
            return false;
        }
        if (obj instanceof b) {
            return ((b) obj).a();
        }
        return true;
    }

    private void c(d dVar) {
        if (dVar == null) {
            throw new IllegalArgumentException("No parser supplied");
        }
    }

    private boolean c(Object obj) {
        if (!(obj instanceof m)) {
            return false;
        }
        if (obj instanceof b) {
            return ((b) obj).d();
        }
        return true;
    }

    private Object k() {
        Object obj = this.f2074b;
        if (obj == null) {
            if (this.f2073a.size() == 2) {
                Object obj2 = this.f2073a.get(0);
                Object obj3 = this.f2073a.get(1);
                if (obj2 == null) {
                    obj = obj3;
                } else if (obj2 == obj3 || obj3 == null) {
                    obj = obj2;
                }
            }
            if (obj == null) {
                obj = new b(this.f2073a);
            }
            this.f2074b = obj;
        }
        return obj;
    }

    public c a() {
        a(d.a.a.d.e());
        return this;
    }

    public c a(char c2) {
        a(new a(c2));
        return this;
    }

    public c a(int i2) {
        a(d.a.a.d.b(), i2, 2);
        return this;
    }

    public c a(int i2, int i3) {
        c(d.a.a.d.a(), i2, i3);
        return this;
    }

    public c a(int i2, boolean z) {
        a(new m(d.a.a.d.u(), i2, z));
        return this;
    }

    public c a(b bVar) {
        if (bVar != null) {
            a(bVar.c(), bVar.b());
            return this;
        }
        throw new IllegalArgumentException("No formatter supplied");
    }

    public c a(d dVar) {
        c(dVar);
        a((m) null, f.a(dVar));
        return this;
    }

    public c a(g gVar, d[] dVarArr) {
        m a2;
        k eVar;
        if (gVar != null) {
            a(gVar);
        }
        if (dVarArr != null) {
            int length = dVarArr.length;
            int i2 = 0;
            if (length != 1) {
                k[] kVarArr = new k[length];
                while (i2 < length - 1) {
                    k a3 = f.a(dVarArr[i2]);
                    kVarArr[i2] = a3;
                    if (a3 != null) {
                        i2++;
                    } else {
                        throw new IllegalArgumentException("Incomplete parser array");
                    }
                }
                kVarArr[i2] = f.a(dVarArr[i2]);
                a2 = h.a(gVar);
                eVar = new e(kVarArr);
            } else if (dVarArr[0] != null) {
                a2 = h.a(gVar);
                eVar = f.a(dVarArr[0]);
            } else {
                throw new IllegalArgumentException("No parser supplied");
            }
            a(a2, eVar);
            return this;
        }
        throw new IllegalArgumentException("No parsers supplied");
    }

    public c a(d.a.a.d dVar) {
        if (dVar != null) {
            a(new i(dVar, true));
            return this;
        }
        throw new IllegalArgumentException("Field type must not be null");
    }

    public c a(d.a.a.d dVar, int i2) {
        if (dVar == null) {
            throw new IllegalArgumentException("Field type must not be null");
        } else if (i2 > 0) {
            a(new C0041c(dVar, i2, false));
            return this;
        } else {
            throw new IllegalArgumentException("Illegal number of digits: " + i2);
        }
    }

    public c a(d.a.a.d dVar, int i2, int i3) {
        if (dVar != null) {
            if (i3 < i2) {
                i3 = i2;
            }
            if (i2 < 0 || i3 <= 0) {
                throw new IllegalArgumentException();
            } else if (i2 <= 1) {
                a(new n(dVar, i3, false));
                return this;
            } else {
                a(new g(dVar, i3, false, i2));
                return this;
            }
        } else {
            throw new IllegalArgumentException("Field type must not be null");
        }
    }

    public c a(String str) {
        if (str != null) {
            int length = str.length();
            if (length == 0) {
                return this;
            }
            a(length != 1 ? new h(str) : new a(str.charAt(0)));
            return this;
        }
        throw new IllegalArgumentException("Literal must not be null");
    }

    public c a(String str, String str2, boolean z, int i2, int i3) {
        a(new l(str, str2, z, i2, i3));
        return this;
    }

    public c a(String str, boolean z, int i2, int i3) {
        a(new l(str, str, z, i2, i3));
        return this;
    }

    public c a(Map<String, d.a.a.g> map) {
        k kVar = new k(1, map);
        a(kVar, kVar);
        return this;
    }

    public c b() {
        b(d.a.a.d.e());
        return this;
    }

    public c b(int i2) {
        a(d.a.a.d.c(), i2, 2);
        return this;
    }

    public c b(int i2, int i3) {
        b(d.a.a.d.k(), i2, i3);
        return this;
    }

    public c b(int i2, boolean z) {
        a(new m(d.a.a.d.w(), i2, z));
        return this;
    }

    public c b(d dVar) {
        c(dVar);
        a((m) null, new e(new k[]{f.a(dVar), null}));
        return this;
    }

    public c b(d.a.a.d dVar) {
        if (dVar != null) {
            a(new i(dVar, false));
            return this;
        }
        throw new IllegalArgumentException("Field type must not be null");
    }

    public c b(d.a.a.d dVar, int i2, int i3) {
        if (dVar != null) {
            if (i3 < i2) {
                i3 = i2;
            }
            if (i2 < 0 || i3 <= 0) {
                throw new IllegalArgumentException();
            }
            a(new d(dVar, i2, i3));
            return this;
        }
        throw new IllegalArgumentException("Field type must not be null");
    }

    public c c() {
        b(d.a.a.d.g());
        return this;
    }

    public c c(int i2) {
        a(d.a.a.d.d(), i2, 2);
        return this;
    }

    public c c(int i2, int i3) {
        b(d.a.a.d.o(), i2, i3);
        return this;
    }

    public c c(d.a.a.d dVar, int i2, int i3) {
        if (dVar != null) {
            if (i3 < i2) {
                i3 = i2;
            }
            if (i2 < 0 || i3 <= 0) {
                throw new IllegalArgumentException();
            } else if (i2 <= 1) {
                a(new n(dVar, i3, true));
                return this;
            } else {
                a(new g(dVar, i3, true, i2));
                return this;
            }
        } else {
            throw new IllegalArgumentException("Field type must not be null");
        }
    }

    public c d() {
        b(d.a.a.d.j());
        return this;
    }

    public c d(int i2) {
        a(d.a.a.d.e(), i2, 1);
        return this;
    }

    public c d(int i2, int i3) {
        b(d.a.a.d.r(), i2, i3);
        return this;
    }

    public c e() {
        a(d.a.a.d.q());
        return this;
    }

    public c e(int i2) {
        a(d.a.a.d.f(), i2, 3);
        return this;
    }

    public c e(int i2, int i3) {
        c(d.a.a.d.u(), i2, i3);
        return this;
    }

    public c f() {
        b(d.a.a.d.q());
        return this;
    }

    public c f(int i2) {
        a(d.a.a.d.k(), i2, 2);
        return this;
    }

    public c f(int i2, int i3) {
        c(d.a.a.d.w(), i2, i3);
        return this;
    }

    public c g() {
        j jVar = j.INSTANCE;
        a(jVar, jVar);
        return this;
    }

    public c g(int i2) {
        a(d.a.a.d.l(), i2, 2);
        return this;
    }

    public c g(int i2, int i3) {
        a(d.a.a.d.y(), i2, i3);
        return this;
    }

    public c h() {
        a(new k(0, null), (k) null);
        return this;
    }

    public c h(int i2) {
        a(d.a.a.d.p(), i2, 2);
        return this;
    }

    public b i() {
        Object k2 = k();
        k kVar = null;
        m mVar = c(k2) ? (m) k2 : null;
        if (b(k2)) {
            kVar = (k) k2;
        }
        if (mVar != null || kVar != null) {
            return new b(mVar, kVar);
        }
        throw new UnsupportedOperationException("Both printing and parsing not supported");
    }

    public c i(int i2) {
        a(d.a.a.d.q(), i2, 2);
        return this;
    }

    public c j(int i2) {
        a(d.a.a.d.s(), i2, 2);
        return this;
    }

    public d j() {
        Object k2 = k();
        if (b(k2)) {
            return l.a((k) k2);
        }
        throw new UnsupportedOperationException("Parsing is not supported");
    }

    public c k(int i2) {
        a(d.a.a.d.t(), i2, 2);
        return this;
    }
}
