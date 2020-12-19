package d.a.a;

import d.a.a.b.q;
import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class e {

    /* renamed from: a  reason: collision with root package name */
    public static final a f2126a = new b();

    /* renamed from: b  reason: collision with root package name */
    private static volatile a f2127b = f2126a;

    /* renamed from: c  reason: collision with root package name */
    private static final AtomicReference<Map<String, g>> f2128c = new AtomicReference<>();

    public interface a {
        long a();
    }

    static class b implements a {
        b() {
        }

        @Override // d.a.a.e.a
        public long a() {
            return System.currentTimeMillis();
        }
    }

    public static final long a() {
        return f2127b.a();
    }

    public static final a a(a aVar) {
        return aVar == null ? q.N() : aVar;
    }

    public static final a a(q qVar) {
        if (qVar == null) {
            return q.N();
        }
        a chronology = qVar.getChronology();
        return chronology == null ? q.N() : chronology;
    }

    public static final DateFormatSymbols a(Locale locale) {
        try {
            return (DateFormatSymbols) DateFormatSymbols.class.getMethod("getInstance", Locale.class).invoke(null, locale);
        } catch (Exception unused) {
            return new DateFormatSymbols(locale);
        }
    }

    private static void a(Map<String, g> map, String str, String str2) {
        try {
            map.put(str, g.a(str2));
        } catch (RuntimeException unused) {
        }
    }

    public static final long b(q qVar) {
        return qVar == null ? a() : qVar.a();
    }

    public static final Map<String, g> b() {
        Map<String, g> map = f2128c.get();
        if (map != null) {
            return map;
        }
        Map<String, g> c2 = c();
        return !f2128c.compareAndSet(null, c2) ? f2128c.get() : c2;
    }

    private static Map<String, g> c() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("UT", g.f2149a);
        linkedHashMap.put("UTC", g.f2149a);
        linkedHashMap.put("GMT", g.f2149a);
        a(linkedHashMap, "EST", "America/New_York");
        a(linkedHashMap, "EDT", "America/New_York");
        a(linkedHashMap, "CST", "America/Chicago");
        a(linkedHashMap, "CDT", "America/Chicago");
        a(linkedHashMap, "MST", "America/Denver");
        a(linkedHashMap, "MDT", "America/Denver");
        a(linkedHashMap, "PST", "America/Los_Angeles");
        a(linkedHashMap, "PDT", "America/Los_Angeles");
        return Collections.unmodifiableMap(linkedHashMap);
    }
}
