package c.b.b.a.a.a.e;

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class b {

    /* renamed from: a  reason: collision with root package name */
    private SSLContext f1741a;

    private static class a {

        /* renamed from: a  reason: collision with root package name */
        private static final b f1742a = new b();
    }

    private b() {
        c();
    }

    public static b a() {
        return a.f1742a;
    }

    private void c() {
        try {
            KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
            instance.load(null, null);
            KeyStore instance2 = KeyStore.getInstance("AndroidCAStore");
            instance2.load(null, null);
            Enumeration<String> aliases = instance2.aliases();
            while (aliases.hasMoreElements()) {
                String nextElement = aliases.nextElement();
                X509Certificate x509Certificate = (X509Certificate) instance2.getCertificate(nextElement);
                if (nextElement.startsWith("system:")) {
                    instance.setCertificateEntry(nextElement, x509Certificate);
                }
            }
            TrustManagerFactory instance3 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance3.init(instance);
            this.f1741a = SSLContext.getInstance("TLS");
            this.f1741a.init(null, instance3.getTrustManagers(), null);
            c.b.b.a.a.a.i.a.c("pinning success");
        } catch (Exception e) {
            c.b.b.a.a.a.i.a.c("pinning fail : " + e.getMessage());
        }
    }

    public SSLContext b() {
        return this.f1741a;
    }
}
