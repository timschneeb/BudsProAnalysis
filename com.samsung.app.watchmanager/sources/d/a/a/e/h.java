package d.a.a.e;

import java.io.InputStream;
import java.security.PrivilegedAction;

/* access modifiers changed from: package-private */
public class h implements PrivilegedAction<InputStream> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f2143a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ i f2144b;

    h(i iVar, String str) {
        this.f2144b = iVar;
        this.f2143a = str;
    }

    @Override // java.security.PrivilegedAction
    public InputStream run() {
        return this.f2144b.f2147c != null ? this.f2144b.f2147c.getResourceAsStream(this.f2143a) : ClassLoader.getSystemResourceAsStream(this.f2143a);
    }
}
