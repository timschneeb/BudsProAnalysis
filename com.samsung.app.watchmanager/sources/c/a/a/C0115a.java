package c.a.a;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* renamed from: c.a.a.a  reason: case insensitive filesystem */
final class C0115a implements C<Date>, u<Date> {

    /* renamed from: a  reason: collision with root package name */
    private final DateFormat f1533a;

    /* renamed from: b  reason: collision with root package name */
    private final DateFormat f1534b;

    /* renamed from: c  reason: collision with root package name */
    private final DateFormat f1535c;

    C0115a() {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    public C0115a(int i, int i2) {
        this(DateFormat.getDateTimeInstance(i, i2, Locale.US), DateFormat.getDateTimeInstance(i, i2));
    }

    C0115a(String str) {
        this(new SimpleDateFormat(str, Locale.US), new SimpleDateFormat(str));
    }

    C0115a(DateFormat dateFormat, DateFormat dateFormat2) {
        this.f1533a = dateFormat;
        this.f1534b = dateFormat2;
        this.f1535c = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        this.f1535c.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:2:0x0003 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:8:0x0011 */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [c.a.a.v] */
    /* JADX WARN: Type inference failed for: r4v1, types: [c.a.a.v] */
    /* JADX WARN: Type inference failed for: r4v2, types: [c.a.a.v] */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.util.Date] */
    /* JADX WARN: Type inference failed for: r4v7, types: [java.util.Date] */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:12|13|14|15) */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:8|9|10|11) */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r4 = r3.f1535c.parse(r4.d());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0028, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0029, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0033, code lost:
        throw new c.a.a.D(r4.d(), r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:?, code lost:
        r4 = r3.f1533a.parse(r4.d());
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x001d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0011 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Date a(c.a.a.v r4) {
        /*
            r3 = this;
            java.text.DateFormat r0 = r3.f1534b
            monitor-enter(r0)
            java.text.DateFormat r1 = r3.f1534b     // Catch:{ ParseException -> 0x0011 }
            java.lang.String r2 = r4.d()     // Catch:{ ParseException -> 0x0011 }
            java.util.Date r4 = r1.parse(r2)     // Catch:{ ParseException -> 0x0011 }
            monitor-exit(r0)     // Catch:{ all -> 0x000f }
            return r4
        L_0x000f:
            r4 = move-exception
            goto L_0x0034
        L_0x0011:
            java.text.DateFormat r1 = r3.f1533a     // Catch:{ ParseException -> 0x001d }
            java.lang.String r2 = r4.d()     // Catch:{ ParseException -> 0x001d }
            java.util.Date r4 = r1.parse(r2)     // Catch:{ ParseException -> 0x001d }
            monitor-exit(r0)
            return r4
        L_0x001d:
            java.text.DateFormat r1 = r3.f1535c     // Catch:{ ParseException -> 0x0029 }
            java.lang.String r2 = r4.d()     // Catch:{ ParseException -> 0x0029 }
            java.util.Date r4 = r1.parse(r2)     // Catch:{ ParseException -> 0x0029 }
            monitor-exit(r0)
            return r4
        L_0x0029:
            r1 = move-exception
            c.a.a.D r2 = new c.a.a.D
            java.lang.String r4 = r4.d()
            r2.<init>(r4, r1)
            throw r2
        L_0x0034:
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: c.a.a.C0115a.a(c.a.a.v):java.util.Date");
    }

    public v a(Date date, Type type, B b2) {
        A a2;
        synchronized (this.f1534b) {
            a2 = new A(this.f1533a.format(date));
        }
        return a2;
    }

    @Override // c.a.a.u
    public Date a(v vVar, Type type, t tVar) {
        if (vVar instanceof A) {
            Date a2 = a(vVar);
            if (type == Date.class) {
                return a2;
            }
            if (type == Timestamp.class) {
                return new Timestamp(a2.getTime());
            }
            if (type == java.sql.Date.class) {
                return new java.sql.Date(a2.getTime());
            }
            throw new IllegalArgumentException(C0115a.class + " cannot deserialize to " + type);
        }
        throw new z("The date should be a string value");
    }

    public String toString() {
        return C0115a.class.getSimpleName() + '(' + this.f1534b.getClass().getSimpleName() + ')';
    }
}
