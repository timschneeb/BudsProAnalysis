package b.c;

public class f<E> implements Cloneable {

    /* renamed from: a  reason: collision with root package name */
    private static final Object f1294a = new Object();

    /* renamed from: b  reason: collision with root package name */
    private boolean f1295b;

    /* renamed from: c  reason: collision with root package name */
    private long[] f1296c;

    /* renamed from: d  reason: collision with root package name */
    private Object[] f1297d;
    private int e;

    public f() {
        this(10);
    }

    public f(int i) {
        this.f1295b = false;
        if (i == 0) {
            this.f1296c = e.f1292b;
            this.f1297d = e.f1293c;
        } else {
            int c2 = e.c(i);
            this.f1296c = new long[c2];
            this.f1297d = new Object[c2];
        }
        this.e = 0;
    }

    private void c() {
        int i = this.e;
        long[] jArr = this.f1296c;
        Object[] objArr = this.f1297d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != f1294a) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.f1295b = false;
        this.e = i2;
    }

    public long a(int i) {
        if (this.f1295b) {
            c();
        }
        return this.f1296c[i];
    }

    public void a() {
        int i = this.e;
        Object[] objArr = this.f1297d;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.e = 0;
        this.f1295b = false;
    }

    public void a(long j) {
        Object[] objArr;
        Object obj;
        int a2 = e.a(this.f1296c, this.e, j);
        if (a2 >= 0 && (objArr = this.f1297d)[a2] != (obj = f1294a)) {
            objArr[a2] = obj;
            this.f1295b = true;
        }
    }

    public void a(long j, E e2) {
        int i = this.e;
        if (i == 0 || j > this.f1296c[i - 1]) {
            if (this.f1295b && this.e >= this.f1296c.length) {
                c();
            }
            int i2 = this.e;
            if (i2 >= this.f1296c.length) {
                int c2 = e.c(i2 + 1);
                long[] jArr = new long[c2];
                Object[] objArr = new Object[c2];
                long[] jArr2 = this.f1296c;
                System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
                Object[] objArr2 = this.f1297d;
                System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
                this.f1296c = jArr;
                this.f1297d = objArr;
            }
            this.f1296c[i2] = j;
            this.f1297d[i2] = e2;
            this.e = i2 + 1;
            return;
        }
        c(j, e2);
    }

    public int b() {
        if (this.f1295b) {
            c();
        }
        return this.e;
    }

    public E b(long j) {
        return b(j, null);
    }

    public E b(long j, E e2) {
        int a2 = e.a(this.f1296c, this.e, j);
        if (a2 >= 0) {
            Object[] objArr = this.f1297d;
            if (objArr[a2] != f1294a) {
                return (E) objArr[a2];
            }
        }
        return e2;
    }

    public void b(int i) {
        Object[] objArr = this.f1297d;
        Object obj = objArr[i];
        Object obj2 = f1294a;
        if (obj != obj2) {
            objArr[i] = obj2;
            this.f1295b = true;
        }
    }

    public E c(int i) {
        if (this.f1295b) {
            c();
        }
        return (E) this.f1297d[i];
    }

    public void c(long j, E e2) {
        int a2 = e.a(this.f1296c, this.e, j);
        if (a2 >= 0) {
            this.f1297d[a2] = e2;
            return;
        }
        int i = a2 ^ -1;
        if (i < this.e) {
            Object[] objArr = this.f1297d;
            if (objArr[i] == f1294a) {
                this.f1296c[i] = j;
                objArr[i] = e2;
                return;
            }
        }
        if (this.f1295b && this.e >= this.f1296c.length) {
            c();
            i = e.a(this.f1296c, this.e, j) ^ -1;
        }
        int i2 = this.e;
        if (i2 >= this.f1296c.length) {
            int c2 = e.c(i2 + 1);
            long[] jArr = new long[c2];
            Object[] objArr2 = new Object[c2];
            long[] jArr2 = this.f1296c;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr3 = this.f1297d;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f1296c = jArr;
            this.f1297d = objArr2;
        }
        int i3 = this.e;
        if (i3 - i != 0) {
            long[] jArr3 = this.f1296c;
            int i4 = i + 1;
            System.arraycopy(jArr3, i, jArr3, i4, i3 - i);
            Object[] objArr4 = this.f1297d;
            System.arraycopy(objArr4, i, objArr4, i4, this.e - i);
        }
        this.f1296c[i] = j;
        this.f1297d[i] = e2;
        this.e++;
    }

    @Override // java.lang.Object
    public f<E> clone() {
        try {
            f<E> fVar = (f) super.clone();
            fVar.f1296c = (long[]) this.f1296c.clone();
            fVar.f1297d = (Object[]) this.f1297d.clone();
            return fVar;
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    public String toString() {
        if (b() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.e * 28);
        sb.append('{');
        for (int i = 0; i < this.e; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(a(i));
            sb.append('=');
            E c2 = c(i);
            if (c2 != this) {
                sb.append((Object) c2);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
