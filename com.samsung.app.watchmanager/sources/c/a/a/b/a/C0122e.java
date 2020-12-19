package c.a.a.b.a;

import c.a.a.J;
import c.a.a.K;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* renamed from: c.a.a.b.a.e  reason: case insensitive filesystem */
public final class C0122e extends J<Date> {

    /* renamed from: a  reason: collision with root package name */
    public static final K f1569a = new C0121d();

    /* renamed from: b  reason: collision with root package name */
    private final DateFormat f1570b = DateFormat.getDateTimeInstance(2, 2, Locale.US);

    /* renamed from: c  reason: collision with root package name */
    private final DateFormat f1571c = DateFormat.getDateTimeInstance(2, 2);

    /* renamed from: d  reason: collision with root package name */
    private final DateFormat f1572d = a();

    private static DateFormat a() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:1:0x0001 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:6:0x000b */
    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: java.lang.String */
    /* JADX DEBUG: Multi-variable search result rejected for r3v2, resolved type: java.lang.String */
    /* JADX DEBUG: Multi-variable search result rejected for r0v2, resolved type: java.text.DateFormat */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.util.Date] */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.util.Date] */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:6|7|8|9) */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001a, code lost:
        return r2.f1572d.parse(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0021, code lost:
        throw new c.a.a.D(r3, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
        r3 = r2.f1570b.parse(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        return r3;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0013 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x000b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.util.Date a(java.lang.String r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            java.text.DateFormat r0 = r2.f1571c     // Catch:{ ParseException -> 0x000b }
            java.util.Date r3 = r0.parse(r3)     // Catch:{ ParseException -> 0x000b }
            monitor-exit(r2)
            return r3
        L_0x0009:
            r3 = move-exception
            goto L_0x0022
        L_0x000b:
            java.text.DateFormat r0 = r2.f1570b     // Catch:{ ParseException -> 0x0013 }
            java.util.Date r3 = r0.parse(r3)     // Catch:{ ParseException -> 0x0013 }
            monitor-exit(r2)
            return r3
        L_0x0013:
            java.text.DateFormat r0 = r2.f1572d     // Catch:{ ParseException -> 0x001b }
            java.util.Date r3 = r0.parse(r3)     // Catch:{ ParseException -> 0x001b }
            monitor-exit(r2)
            return r3
        L_0x001b:
            r0 = move-exception
            c.a.a.D r1 = new c.a.a.D     // Catch:{ all -> 0x0009 }
            r1.<init>(r3, r0)     // Catch:{ all -> 0x0009 }
            throw r1     // Catch:{ all -> 0x0009 }
        L_0x0022:
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: c.a.a.b.a.C0122e.a(java.lang.String):java.util.Date");
    }

    @Override // c.a.a.J
    public Date a(b bVar) {
        if (bVar.o() != c.NULL) {
            return a(bVar.n());
        }
        bVar.m();
        return null;
    }

    public synchronized void a(d dVar, Date date) {
        if (date == null) {
            dVar.h();
        } else {
            dVar.c(this.f1570b.format(date));
        }
    }
}
