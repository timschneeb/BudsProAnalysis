package c.b.b.a.a.a.a;

import c.b.b.a.a.a.i.e;

/* JADX INFO: Failed to restore enum class, 'enum' modifier removed */
public final class c extends Enum<c> {

    /* renamed from: a  reason: collision with root package name */
    public static final c f1708a;

    /* renamed from: b  reason: collision with root package name */
    public static final c f1709b;

    /* renamed from: c  reason: collision with root package name */
    public static final c f1710c = new c("DLS", 2, "");

    /* renamed from: d  reason: collision with root package name */
    private static final /* synthetic */ c[] f1711d = {f1708a, f1709b, f1710c};
    String e;

    static {
        String str = "https://stg-api.di.atlas.samsung.com";
        f1708a = new c("REGISTRATION", 0, e.a() ? str : "https://regi.di.atlas.samsung.com");
        if (!e.a()) {
            str = "https://dc.di.atlas.samsung.com";
        }
        f1709b = new c("POLICY", 1, str);
    }

    private c(String str, int i, String str2) {
        this.e = str2;
    }

    public static c valueOf(String str) {
        return (c) Enum.valueOf(c.class, str);
    }

    public static c[] values() {
        return (c[]) f1711d.clone();
    }

    public void a(String str) {
        this.e = str;
    }

    public String b() {
        return this.e;
    }
}
