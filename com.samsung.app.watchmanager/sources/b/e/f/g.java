package b.e.f;

public class g<T> extends f<T> {

    /* renamed from: c  reason: collision with root package name */
    private final Object f1397c = new Object();

    public g(int i) {
        super(i);
    }

    @Override // b.e.f.f, b.e.f.e
    public T a() {
        T t;
        synchronized (this.f1397c) {
            t = (T) super.a();
        }
        return t;
    }

    @Override // b.e.f.f, b.e.f.e
    public boolean a(T t) {
        boolean a2;
        synchronized (this.f1397c) {
            a2 = super.a(t);
        }
        return a2;
    }
}
