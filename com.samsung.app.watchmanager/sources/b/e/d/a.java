package b.e.d;

import android.util.Base64;
import b.e.f.h;
import java.util.List;

public final class a {

    /* renamed from: a  reason: collision with root package name */
    private final String f1341a;

    /* renamed from: b  reason: collision with root package name */
    private final String f1342b;

    /* renamed from: c  reason: collision with root package name */
    private final String f1343c;

    /* renamed from: d  reason: collision with root package name */
    private final List<List<byte[]>> f1344d;
    private final int e = 0;
    private final String f = (this.f1341a + "-" + this.f1342b + "-" + this.f1343c);

    public a(String str, String str2, String str3, List<List<byte[]>> list) {
        h.a(str);
        this.f1341a = str;
        h.a(str2);
        this.f1342b = str2;
        h.a(str3);
        this.f1343c = str3;
        h.a(list);
        this.f1344d = list;
    }

    public List<List<byte[]>> a() {
        return this.f1344d;
    }

    public int b() {
        return this.e;
    }

    public String c() {
        return this.f;
    }

    public String d() {
        return this.f1341a;
    }

    public String e() {
        return this.f1342b;
    }

    public String f() {
        return this.f1343c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FontRequest {mProviderAuthority: " + this.f1341a + ", mProviderPackage: " + this.f1342b + ", mQuery: " + this.f1343c + ", mCertificates:");
        for (int i = 0; i < this.f1344d.size(); i++) {
            sb.append(" [");
            List<byte[]> list = this.f1344d.get(i);
            for (int i2 = 0; i2 < list.size(); i2++) {
                sb.append(" \"");
                sb.append(Base64.encodeToString(list.get(i2), 0));
                sb.append("\"");
            }
            sb.append(" ]");
        }
        sb.append("}");
        sb.append("mCertificatesArray: " + this.e);
        return sb.toString();
    }
}
