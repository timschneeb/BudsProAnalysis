package androidx.lifecycle;

public class s {

    /* renamed from: a  reason: collision with root package name */
    private final a f856a;

    /* renamed from: b  reason: collision with root package name */
    private final t f857b;

    public interface a {
        <T extends r> T a(Class<T> cls);
    }

    public s(t tVar, a aVar) {
        this.f856a = aVar;
        this.f857b = tVar;
    }

    public <T extends r> T a(Class<T> cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null) {
            return (T) a("androidx.lifecycle.ViewModelProvider.DefaultKey:" + canonicalName, cls);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    public <T extends r> T a(String str, Class<T> cls) {
        T t = (T) this.f857b.a(str);
        if (cls.isInstance(t)) {
            return t;
        }
        T t2 = (T) this.f856a.a(cls);
        this.f857b.a(str, t2);
        return t2;
    }
}
