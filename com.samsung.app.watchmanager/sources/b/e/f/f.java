package b.e.f;

public class f<T> implements e<T> {

    /* renamed from: a  reason: collision with root package name */
    private final Object[] f1395a;

    /* renamed from: b  reason: collision with root package name */
    private int f1396b;

    public f(int i) {
        if (i > 0) {
            this.f1395a = new Object[i];
            return;
        }
        throw new IllegalArgumentException("The max pool size must be > 0");
    }

    private boolean b(T t) {
        for (int i = 0; i < this.f1396b; i++) {
            if (this.f1395a[i] == t) {
                return true;
            }
        }
        return false;
    }

    @Override // b.e.f.e
    public T a() {
        int i = this.f1396b;
        if (i <= 0) {
            return null;
        }
        int i2 = i - 1;
        Object[] objArr = this.f1395a;
        T t = (T) objArr[i2];
        objArr[i2] = null;
        this.f1396b = i - 1;
        return t;
    }

    @Override // b.e.f.e
    public boolean a(T t) {
        if (!b(t)) {
            int i = this.f1396b;
            Object[] objArr = this.f1395a;
            if (i >= objArr.length) {
                return false;
            }
            objArr[i] = t;
            this.f1396b = i + 1;
            return true;
        }
        throw new IllegalStateException("Already in the pool!");
    }
}
