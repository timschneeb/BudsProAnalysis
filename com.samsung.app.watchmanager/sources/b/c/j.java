package b.c;

public class j<E> implements Cloneable {

    /* renamed from: a  reason: collision with root package name */
    private static final Object f1320a = new Object();

    /* renamed from: b  reason: collision with root package name */
    private boolean f1321b;

    /* renamed from: c  reason: collision with root package name */
    private int[] f1322c;

    /* renamed from: d  reason: collision with root package name */
    private Object[] f1323d;
    private int e;

    public j() {
        this(10);
    }

    public j(int i) {
        this.f1321b = false;
        if (i == 0) {
            this.f1322c = e.f1291a;
            this.f1323d = e.f1293c;
        } else {
            int b2 = e.b(i);
            this.f1322c = new int[b2];
            this.f1323d = new Object[b2];
        }
        this.e = 0;
    }

    private void c() {
        int i = this.e;
        int[] iArr = this.f1322c;
        Object[] objArr = this.f1323d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != f1320a) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.f1321b = false;
        this.e = i2;
    }

    public void a() {
        int i = this.e;
        Object[] objArr = this.f1323d;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.e = 0;
        this.f1321b = false;
    }

    public void a(int i) {
        Object[] objArr;
        Object obj;
        int a2 = e.a(this.f1322c, this.e, i);
        if (a2 >= 0 && (objArr = this.f1323d)[a2] != (obj = f1320a)) {
            objArr[a2] = obj;
            this.f1321b = true;
        }
    }

    public void a(int i, E e2) {
        int i2 = this.e;
        if (i2 == 0 || i > this.f1322c[i2 - 1]) {
            if (this.f1321b && this.e >= this.f1322c.length) {
                c();
            }
            int i3 = this.e;
            if (i3 >= this.f1322c.length) {
                int b2 = e.b(i3 + 1);
                int[] iArr = new int[b2];
                Object[] objArr = new Object[b2];
                int[] iArr2 = this.f1322c;
                System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
                Object[] objArr2 = this.f1323d;
                System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
                this.f1322c = iArr;
                this.f1323d = objArr;
            }
            this.f1322c[i3] = i;
            this.f1323d[i3] = e2;
            this.e = i3 + 1;
            return;
        }
        c(i, e2);
    }

    public int b() {
        if (this.f1321b) {
            c();
        }
        return this.e;
    }

    public E b(int i) {
        return b(i, null);
    }

    public E b(int i, E e2) {
        int a2 = e.a(this.f1322c, this.e, i);
        if (a2 >= 0) {
            Object[] objArr = this.f1323d;
            if (objArr[a2] != f1320a) {
                return (E) objArr[a2];
            }
        }
        return e2;
    }

    public int c(int i) {
        if (this.f1321b) {
            c();
        }
        return this.f1322c[i];
    }

    public void c(int i, E e2) {
        int a2 = e.a(this.f1322c, this.e, i);
        if (a2 >= 0) {
            this.f1323d[a2] = e2;
            return;
        }
        int i2 = a2 ^ -1;
        if (i2 < this.e) {
            Object[] objArr = this.f1323d;
            if (objArr[i2] == f1320a) {
                this.f1322c[i2] = i;
                objArr[i2] = e2;
                return;
            }
        }
        if (this.f1321b && this.e >= this.f1322c.length) {
            c();
            i2 = e.a(this.f1322c, this.e, i) ^ -1;
        }
        int i3 = this.e;
        if (i3 >= this.f1322c.length) {
            int b2 = e.b(i3 + 1);
            int[] iArr = new int[b2];
            Object[] objArr2 = new Object[b2];
            int[] iArr2 = this.f1322c;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr3 = this.f1323d;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f1322c = iArr;
            this.f1323d = objArr2;
        }
        int i4 = this.e;
        if (i4 - i2 != 0) {
            int[] iArr3 = this.f1322c;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4 - i2);
            Object[] objArr4 = this.f1323d;
            System.arraycopy(objArr4, i2, objArr4, i5, this.e - i2);
        }
        this.f1322c[i2] = i;
        this.f1323d[i2] = e2;
        this.e++;
    }

    @Override // java.lang.Object
    public j<E> clone() {
        try {
            j<E> jVar = (j) super.clone();
            jVar.f1322c = (int[]) this.f1322c.clone();
            jVar.f1323d = (Object[]) this.f1323d.clone();
            return jVar;
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    public void d(int i) {
        a(i);
    }

    public E e(int i) {
        if (this.f1321b) {
            c();
        }
        return (E) this.f1323d[i];
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
            sb.append(c(i));
            sb.append('=');
            E e2 = e(i);
            if (e2 != this) {
                sb.append((Object) e2);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
