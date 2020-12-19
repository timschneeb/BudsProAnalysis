package d.a.a.d;

import com.samsung.android.app.twatchmanager.update.UpdateManager;
import d.a.a.a;
import d.a.a.e;
import d.a.a.g;
import d.a.a.q;
import java.io.IOException;
import java.util.Locale;

public class b {

    /* renamed from: a  reason: collision with root package name */
    private final m f2069a;

    /* renamed from: b  reason: collision with root package name */
    private final k f2070b;

    /* renamed from: c  reason: collision with root package name */
    private final Locale f2071c;

    /* renamed from: d  reason: collision with root package name */
    private final boolean f2072d;
    private final a e;
    private final g f;
    private final Integer g;
    private final int h;

    b(m mVar, k kVar) {
        this.f2069a = mVar;
        this.f2070b = kVar;
        this.f2071c = null;
        this.f2072d = false;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = UpdateManager.UPDATE_CHECK_TIMEOUT_PER_REQUESET;
    }

    private b(m mVar, k kVar, Locale locale, boolean z, a aVar, g gVar, Integer num, int i) {
        this.f2069a = mVar;
        this.f2070b = kVar;
        this.f2071c = locale;
        this.f2072d = z;
        this.e = aVar;
        this.f = gVar;
        this.g = num;
        this.h = i;
    }

    private void a(Appendable appendable, long j, a aVar) {
        m f2 = f();
        a b2 = b(aVar);
        g k = b2.k();
        int c2 = k.c(j);
        long j2 = (long) c2;
        long j3 = j + j2;
        if ((j ^ j3) < 0 && (j2 ^ j) >= 0) {
            k = g.f2149a;
            c2 = 0;
            j3 = j;
        }
        f2.a(appendable, j3, b2.G(), c2, k, this.f2071c);
    }

    private a b(a aVar) {
        a a2 = e.a(aVar);
        a aVar2 = this.e;
        if (aVar2 != null) {
            a2 = aVar2;
        }
        g gVar = this.f;
        return gVar != null ? a2.a(gVar) : a2;
    }

    private k e() {
        k kVar = this.f2070b;
        if (kVar != null) {
            return kVar;
        }
        throw new UnsupportedOperationException("Parsing not supported");
    }

    private m f() {
        m mVar = this.f2069a;
        if (mVar != null) {
            return mVar;
        }
        throw new UnsupportedOperationException("Printing not supported");
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public d.a.a.b a(java.lang.String r11) {
        /*
        // Method dump skipped, instructions count: 105
        */
        throw new UnsupportedOperationException("Method not decompiled: d.a.a.d.b.a(java.lang.String):d.a.a.b");
    }

    public b a(a aVar) {
        return this.e == aVar ? this : new b(this.f2069a, this.f2070b, this.f2071c, this.f2072d, aVar, this.f, this.g, this.h);
    }

    public b a(g gVar) {
        return this.f == gVar ? this : new b(this.f2069a, this.f2070b, this.f2071c, false, this.e, gVar, this.g, this.h);
    }

    public d a() {
        return l.a(this.f2070b);
    }

    public String a(q qVar) {
        StringBuilder sb = new StringBuilder(f().c());
        try {
            a(sb, qVar);
        } catch (IOException unused) {
        }
        return sb.toString();
    }

    public void a(Appendable appendable, q qVar) {
        a(appendable, e.b(qVar), e.a(qVar));
    }

    public long b(String str) {
        return new e(0, b(this.e), this.f2071c, this.g, this.h).a(e(), str);
    }

    /* access modifiers changed from: package-private */
    public k b() {
        return this.f2070b;
    }

    /* access modifiers changed from: package-private */
    public m c() {
        return this.f2069a;
    }

    public b d() {
        return a(g.f2149a);
    }
}
