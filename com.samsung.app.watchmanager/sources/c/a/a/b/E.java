package c.a.a.b;

/* access modifiers changed from: package-private */
public class E extends F {
    E() {
    }

    @Override // c.a.a.b.F
    public <T> T a(Class<T> cls) {
        throw new UnsupportedOperationException("Cannot allocate " + cls);
    }
}
