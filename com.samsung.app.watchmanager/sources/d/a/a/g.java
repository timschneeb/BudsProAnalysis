package d.a.a;

import com.samsung.android.app.twatchmanager.update.SAGUIDHelper;
import d.a.a.d.b;
import d.a.a.d.c;
import d.a.a.d.i;
import d.a.a.e.d;
import d.a.a.e.e;
import d.a.a.e.f;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

public abstract class g implements Serializable {

    /* renamed from: a  reason: collision with root package name */
    public static final g f2149a = r.f;

    /* renamed from: b  reason: collision with root package name */
    private static final AtomicReference<f> f2150b = new AtomicReference<>();

    /* renamed from: c  reason: collision with root package name */
    private static final AtomicReference<e> f2151c = new AtomicReference<>();

    /* renamed from: d  reason: collision with root package name */
    private static final AtomicReference<g> f2152d = new AtomicReference<>();
    private final String e;

    /* access modifiers changed from: package-private */
    public static final class a {

        /* renamed from: a  reason: collision with root package name */
        static final Map<String, String> f2153a = b();

        /* renamed from: b  reason: collision with root package name */
        static final b f2154b = a();

        private static b a() {
            f fVar = new f();
            c cVar = new c();
            cVar.a(null, true, 2, 4);
            return cVar.i().a(fVar);
        }

        private static Map<String, String> b() {
            HashMap hashMap = new HashMap();
            hashMap.put("GMT", "UTC");
            hashMap.put("WET", "WET");
            hashMap.put("CET", "CET");
            hashMap.put("MET", "CET");
            hashMap.put("ECT", "CET");
            hashMap.put("EET", "EET");
            hashMap.put("MIT", "Pacific/Apia");
            hashMap.put("HST", "Pacific/Honolulu");
            hashMap.put("AST", "America/Anchorage");
            hashMap.put("PST", "America/Los_Angeles");
            hashMap.put("MST", "America/Denver");
            hashMap.put("PNT", "America/Phoenix");
            hashMap.put("CST", "America/Chicago");
            hashMap.put("EST", "America/New_York");
            hashMap.put("IET", "America/Indiana/Indianapolis");
            hashMap.put("PRT", "America/Puerto_Rico");
            hashMap.put("CNT", "America/St_Johns");
            hashMap.put("AGT", "America/Argentina/Buenos_Aires");
            hashMap.put("BET", "America/Sao_Paulo");
            hashMap.put("ART", "Africa/Cairo");
            hashMap.put("CAT", "Africa/Harare");
            hashMap.put("EAT", "Africa/Addis_Ababa");
            hashMap.put("NET", "Asia/Yerevan");
            hashMap.put("PLT", "Asia/Karachi");
            hashMap.put("IST", "Asia/Kolkata");
            hashMap.put("BST", "Asia/Dhaka");
            hashMap.put("VST", "Asia/Ho_Chi_Minh");
            hashMap.put("CTT", "Asia/Shanghai");
            hashMap.put("JST", "Asia/Tokyo");
            hashMap.put("ACT", "Australia/Darwin");
            hashMap.put("AET", "Australia/Sydney");
            hashMap.put("SST", "Pacific/Guadalcanal");
            hashMap.put("NST", "Pacific/Auckland");
            return Collections.unmodifiableMap(hashMap);
        }
    }

    protected g(String str) {
        if (str != null) {
            this.e = str;
            return;
        }
        throw new IllegalArgumentException("Id must not be null");
    }

    private static f a(f fVar) {
        Set<String> a2 = fVar.a();
        if (a2 == null || a2.size() == 0) {
            throw new IllegalArgumentException("The provider doesn't have any available ids");
        } else if (!a2.contains("UTC")) {
            throw new IllegalArgumentException("The provider doesn't support UTC");
        } else if (f2149a.equals(fVar.a("UTC"))) {
            return fVar;
        } else {
            throw new IllegalArgumentException("Invalid UTC zone provided");
        }
    }

    public static g a(int i) {
        if (i >= -86399999 && i <= 86399999) {
            return a(b(i), i);
        }
        throw new IllegalArgumentException("Millis out of range: " + i);
    }

    public static g a(String str) {
        if (str == null) {
            return b();
        }
        if (str.equals("UTC")) {
            return f2149a;
        }
        g a2 = e().a(str);
        if (a2 != null) {
            return a2;
        }
        if (str.startsWith("+") || str.startsWith("-")) {
            int d2 = d(str);
            return ((long) d2) == 0 ? f2149a : a(b(d2), d2);
        }
        throw new IllegalArgumentException("The datetime zone id '" + str + "' is not recognised");
    }

    private static g a(String str, int i) {
        return i == 0 ? f2149a : new d(str, null, i, i);
    }

    public static g a(TimeZone timeZone) {
        char charAt;
        if (timeZone == null) {
            return b();
        }
        String id = timeZone.getID();
        if (id == null) {
            throw new IllegalArgumentException("The TimeZone id must not be null");
        } else if (id.equals("UTC")) {
            return f2149a;
        } else {
            g gVar = null;
            String c2 = c(id);
            f e2 = e();
            if (c2 != null) {
                gVar = e2.a(c2);
            }
            if (gVar == null) {
                gVar = e2.a(id);
            }
            if (gVar != null) {
                return gVar;
            }
            if (c2 != null || (!id.startsWith("GMT+") && !id.startsWith("GMT-"))) {
                throw new IllegalArgumentException("The datetime zone id '" + id + "' is not recognised");
            }
            String substring = id.substring(3);
            if (substring.length() > 2 && (charAt = substring.charAt(1)) > '9' && Character.isDigit(charAt)) {
                substring = b(substring);
            }
            int d2 = d(substring);
            return ((long) d2) == 0 ? f2149a : a(b(d2), d2);
        }
    }

    public static Set<String> a() {
        return e().a();
    }

    public static g b() {
        g gVar = f2152d.get();
        if (gVar != null) {
            return gVar;
        }
        try {
            String property = System.getProperty("user.timezone");
            if (property != null) {
                gVar = a(property);
            }
        } catch (RuntimeException unused) {
        }
        if (gVar == null) {
            try {
                gVar = a(TimeZone.getDefault());
            } catch (IllegalArgumentException unused2) {
            }
        }
        if (gVar == null) {
            gVar = f2149a;
        }
        return !f2152d.compareAndSet(null, gVar) ? f2152d.get() : gVar;
    }

    private static String b(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        if (i >= 0) {
            stringBuffer.append('+');
        } else {
            stringBuffer.append('-');
            i = -i;
        }
        int i2 = i / 3600000;
        i.a(stringBuffer, i2, 2);
        int i3 = i - (i2 * 3600000);
        int i4 = i3 / 60000;
        stringBuffer.append(':');
        i.a(stringBuffer, i4, 2);
        int i5 = i3 - (i4 * 60000);
        if (i5 == 0) {
            return stringBuffer.toString();
        }
        int i6 = i5 / SAGUIDHelper.GUID_REQUEST_ID;
        stringBuffer.append(':');
        i.a(stringBuffer, i6, 2);
        int i7 = i5 - (i6 * SAGUIDHelper.GUID_REQUEST_ID);
        if (i7 == 0) {
            return stringBuffer.toString();
        }
        stringBuffer.append('.');
        i.a(stringBuffer, i7, 3);
        return stringBuffer.toString();
    }

    private static String b(String str) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < sb.length(); i++) {
            int digit = Character.digit(sb.charAt(i), 10);
            if (digit >= 0) {
                sb.setCharAt(i, (char) (digit + 48));
            }
        }
        return sb.toString();
    }

    private static String c(String str) {
        return a.f2153a.get(str);
    }

    private static int d(String str) {
        return -((int) a.f2154b.b(str));
    }

    public static e d() {
        e eVar = f2151c.get();
        if (eVar != null) {
            return eVar;
        }
        e g = g();
        return !f2151c.compareAndSet(null, g) ? f2151c.get() : g;
    }

    public static f e() {
        f fVar = f2150b.get();
        if (fVar != null) {
            return fVar;
        }
        f h = h();
        return !f2150b.compareAndSet(null, h) ? f2150b.get() : h;
    }

    private static e g() {
        e eVar = null;
        try {
            String property = System.getProperty("org.joda.time.DateTimeZone.NameProvider");
            if (property != null) {
                try {
                    Class<?> cls = Class.forName(property, false, g.class.getClassLoader());
                    if (e.class.isAssignableFrom(cls)) {
                        eVar = (e) cls.asSubclass(e.class).getConstructor(new Class[0]).newInstance(new Object[0]);
                    } else {
                        throw new IllegalArgumentException("System property referred to class that does not implement " + e.class);
                    }
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        } catch (SecurityException unused) {
        }
        return eVar == null ? new d.a.a.e.c() : eVar;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:0|1|2|(4:4|5|6|(2:8|9)(2:10|11))|15|17|18|(3:20|21|22)|26|27|28) */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x007b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007c, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0084, code lost:
        return new d.a.a.e.g();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0070 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static d.a.a.e.f h() {
        /*
        // Method dump skipped, instructions count: 133
        */
        throw new UnsupportedOperationException("Method not decompiled: d.a.a.g.h():d.a.a.e.f");
    }

    public long a(long j) {
        long c2 = (long) c(j);
        long j2 = j + c2;
        if ((j ^ j2) >= 0 || (j ^ c2) < 0) {
            return j2;
        }
        throw new ArithmeticException("Adding time zone offset caused overflow");
    }

    public long a(long j, boolean z) {
        long j2;
        int c2 = c(j);
        long j3 = j - ((long) c2);
        int c3 = c(j3);
        if (c2 != c3 && (z || c2 < 0)) {
            long g = g(j3);
            long j4 = Long.MAX_VALUE;
            if (g == j3) {
                g = Long.MAX_VALUE;
            }
            long j5 = j - ((long) c3);
            long g2 = g(j5);
            if (g2 != j5) {
                j4 = g2;
            }
            if (g != j4) {
                if (z) {
                    throw new k(j, c());
                }
                long j6 = (long) c2;
                j2 = j - j6;
                if ((j ^ j2) < 0 || (j ^ j6) >= 0) {
                    return j2;
                }
                throw new ArithmeticException("Subtracting time zone offset caused overflow");
            }
        }
        c2 = c3;
        long j62 = (long) c2;
        j2 = j - j62;
        if ((j ^ j2) < 0) {
        }
        return j2;
    }

    public long a(long j, boolean z, long j2) {
        int c2 = c(j2);
        long j3 = j - ((long) c2);
        return c(j3) == c2 ? j3 : a(j, z);
    }

    public String a(long j, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String b2 = b(j);
        if (b2 == null) {
            return this.e;
        }
        e d2 = d();
        String a2 = d2 instanceof d.a.a.e.c ? ((d.a.a.e.c) d2).a(locale, this.e, b2, f(j)) : d2.a(locale, this.e, b2);
        return a2 != null ? a2 : b(c(j));
    }

    public abstract String b(long j);

    public String b(long j, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String b2 = b(j);
        if (b2 == null) {
            return this.e;
        }
        e d2 = d();
        String b3 = d2 instanceof d.a.a.e.c ? ((d.a.a.e.c) d2).b(locale, this.e, b2, f(j)) : d2.b(locale, this.e, b2);
        return b3 != null ? b3 : b(c(j));
    }

    public abstract int c(long j);

    public final String c() {
        return this.e;
    }

    public int d(long j) {
        int c2 = c(j);
        long j2 = j - ((long) c2);
        int c3 = c(j2);
        if (c2 != c3) {
            if (c2 - c3 < 0) {
                long g = g(j2);
                if (g == j2) {
                    g = Long.MAX_VALUE;
                }
                long j3 = j - ((long) c3);
                long g2 = g(j3);
                if (g2 == j3) {
                    g2 = Long.MAX_VALUE;
                }
                if (g != g2) {
                    return c2;
                }
            }
        } else if (c2 >= 0) {
            long h = h(j2);
            if (h < j2) {
                int c4 = c(h);
                if (j2 - h <= ((long) (c4 - c2))) {
                    return c4;
                }
            }
        }
        return c3;
    }

    public abstract int e(long j);

    public abstract boolean equals(Object obj);

    public abstract boolean f();

    public boolean f(long j) {
        return c(j) == e(j);
    }

    public abstract long g(long j);

    public abstract long h(long j);

    public int hashCode() {
        return c().hashCode() + 57;
    }

    public String toString() {
        return c();
    }
}
