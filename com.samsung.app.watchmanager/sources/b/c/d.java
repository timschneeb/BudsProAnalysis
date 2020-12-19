package b.c;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class d<E> implements Collection<E>, Set<E> {

    /* renamed from: a  reason: collision with root package name */
    private static final int[] f1287a = new int[0];

    /* renamed from: b  reason: collision with root package name */
    private static final Object[] f1288b = new Object[0];

    /* renamed from: c  reason: collision with root package name */
    private static Object[] f1289c;

    /* renamed from: d  reason: collision with root package name */
    private static int f1290d;
    private static Object[] e;
    private static int f;
    private int[] g;
    Object[] h;
    int i;
    private h<E, E> j;

    public d() {
        this(0);
    }

    public d(int i2) {
        if (i2 == 0) {
            this.g = f1287a;
            this.h = f1288b;
        } else {
            d(i2);
        }
        this.i = 0;
    }

    private int a(Object obj, int i2) {
        int i3 = this.i;
        if (i3 == 0) {
            return -1;
        }
        int a2 = e.a(this.g, i3, i2);
        if (a2 < 0 || obj.equals(this.h[a2])) {
            return a2;
        }
        int i4 = a2 + 1;
        while (i4 < i3 && this.g[i4] == i2) {
            if (obj.equals(this.h[i4])) {
                return i4;
            }
            i4++;
        }
        int i5 = a2 - 1;
        while (i5 >= 0 && this.g[i5] == i2) {
            if (obj.equals(this.h[i5])) {
                return i5;
            }
            i5--;
        }
        return i4 ^ -1;
    }

    private h<E, E> a() {
        if (this.j == null) {
            this.j = new c(this);
        }
        return this.j;
    }

    private static void a(int[] iArr, Object[] objArr, int i2) {
        if (iArr.length == 8) {
            synchronized (d.class) {
                if (f < 10) {
                    objArr[0] = e;
                    objArr[1] = iArr;
                    for (int i3 = i2 - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    e = objArr;
                    f++;
                }
            }
        } else if (iArr.length == 4) {
            synchronized (d.class) {
                if (f1290d < 10) {
                    objArr[0] = f1289c;
                    objArr[1] = iArr;
                    for (int i4 = i2 - 1; i4 >= 2; i4--) {
                        objArr[i4] = null;
                    }
                    f1289c = objArr;
                    f1290d++;
                }
            }
        }
    }

    private int b() {
        int i2 = this.i;
        if (i2 == 0) {
            return -1;
        }
        int a2 = e.a(this.g, i2, 0);
        if (a2 < 0 || this.h[a2] == null) {
            return a2;
        }
        int i3 = a2 + 1;
        while (i3 < i2 && this.g[i3] == 0) {
            if (this.h[i3] == null) {
                return i3;
            }
            i3++;
        }
        int i4 = a2 - 1;
        while (i4 >= 0 && this.g[i4] == 0) {
            if (this.h[i4] == null) {
                return i4;
            }
            i4--;
        }
        return i3 ^ -1;
    }

    private void d(int i2) {
        if (i2 == 8) {
            synchronized (d.class) {
                if (e != null) {
                    Object[] objArr = e;
                    this.h = objArr;
                    e = (Object[]) objArr[0];
                    this.g = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    f--;
                    return;
                }
            }
        } else if (i2 == 4) {
            synchronized (d.class) {
                if (f1289c != null) {
                    Object[] objArr2 = f1289c;
                    this.h = objArr2;
                    f1289c = (Object[]) objArr2[0];
                    this.g = (int[]) objArr2[1];
                    objArr2[1] = null;
                    objArr2[0] = null;
                    f1290d--;
                    return;
                }
            }
        }
        this.g = new int[i2];
        this.h = new Object[i2];
    }

    public void a(int i2) {
        int[] iArr = this.g;
        if (iArr.length < i2) {
            Object[] objArr = this.h;
            d(i2);
            int i3 = this.i;
            if (i3 > 0) {
                System.arraycopy(iArr, 0, this.g, 0, i3);
                System.arraycopy(objArr, 0, this.h, 0, this.i);
            }
            a(iArr, objArr, this.i);
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean add(E e2) {
        int i2;
        int i3;
        if (e2 == null) {
            i3 = b();
            i2 = 0;
        } else {
            int hashCode = e2.hashCode();
            i2 = hashCode;
            i3 = a(e2, hashCode);
        }
        if (i3 >= 0) {
            return false;
        }
        int i4 = i3 ^ -1;
        int i5 = this.i;
        if (i5 >= this.g.length) {
            int i6 = 4;
            if (i5 >= 8) {
                i6 = (i5 >> 1) + i5;
            } else if (i5 >= 4) {
                i6 = 8;
            }
            int[] iArr = this.g;
            Object[] objArr = this.h;
            d(i6);
            int[] iArr2 = this.g;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr, 0, this.h, 0, objArr.length);
            }
            a(iArr, objArr, this.i);
        }
        int i7 = this.i;
        if (i4 < i7) {
            int[] iArr3 = this.g;
            int i8 = i4 + 1;
            System.arraycopy(iArr3, i4, iArr3, i8, i7 - i4);
            Object[] objArr2 = this.h;
            System.arraycopy(objArr2, i4, objArr2, i8, this.i - i4);
        }
        this.g[i4] = i2;
        this.h[i4] = e2;
        this.i++;
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: b.c.d<E> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Collection, java.util.Set
    public boolean addAll(Collection<? extends E> collection) {
        a(this.i + collection.size());
        Iterator<? extends E> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= add(it.next());
        }
        return z;
    }

    public E b(int i2) {
        Object[] objArr = this.h;
        E e2 = (E) objArr[i2];
        int i3 = this.i;
        if (i3 <= 1) {
            a(this.g, objArr, i3);
            this.g = f1287a;
            this.h = f1288b;
            this.i = 0;
        } else {
            int[] iArr = this.g;
            int i4 = 8;
            if (iArr.length <= 8 || i3 >= iArr.length / 3) {
                this.i--;
                int i5 = this.i;
                if (i2 < i5) {
                    int[] iArr2 = this.g;
                    int i6 = i2 + 1;
                    System.arraycopy(iArr2, i6, iArr2, i2, i5 - i2);
                    Object[] objArr2 = this.h;
                    System.arraycopy(objArr2, i6, objArr2, i2, this.i - i2);
                }
                this.h[this.i] = null;
            } else {
                if (i3 > 8) {
                    i4 = i3 + (i3 >> 1);
                }
                int[] iArr3 = this.g;
                Object[] objArr3 = this.h;
                d(i4);
                this.i--;
                if (i2 > 0) {
                    System.arraycopy(iArr3, 0, this.g, 0, i2);
                    System.arraycopy(objArr3, 0, this.h, 0, i2);
                }
                int i7 = this.i;
                if (i2 < i7) {
                    int i8 = i2 + 1;
                    System.arraycopy(iArr3, i8, this.g, i2, i7 - i2);
                    System.arraycopy(objArr3, i8, this.h, i2, this.i - i2);
                }
            }
        }
        return e2;
    }

    public E c(int i2) {
        return (E) this.h[i2];
    }

    public void clear() {
        int i2 = this.i;
        if (i2 != 0) {
            a(this.g, this.h, i2);
            this.g = f1287a;
            this.h = f1288b;
            this.i = 0;
        }
    }

    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean containsAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            if (size() != set.size()) {
                return false;
            }
            for (int i2 = 0; i2 < this.i; i2++) {
                try {
                    if (!set.contains(c(i2))) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        int[] iArr = this.g;
        int i2 = this.i;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 += iArr[i4];
        }
        return i3;
    }

    public int indexOf(Object obj) {
        return obj == null ? b() : a(obj, obj.hashCode());
    }

    public boolean isEmpty() {
        return this.i <= 0;
    }

    @Override // java.util.Collection, java.util.Set, java.lang.Iterable
    public Iterator<E> iterator() {
        return a().e().iterator();
    }

    public boolean remove(Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf < 0) {
            return false;
        }
        b(indexOf);
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean removeAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= remove(it.next());
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean retainAll(Collection<?> collection) {
        boolean z = false;
        for (int i2 = this.i - 1; i2 >= 0; i2--) {
            if (!collection.contains(this.h[i2])) {
                b(i2);
                z = true;
            }
        }
        return z;
    }

    public int size() {
        return this.i;
    }

    public Object[] toArray() {
        int i2 = this.i;
        Object[] objArr = new Object[i2];
        System.arraycopy(this.h, 0, objArr, 0, i2);
        return objArr;
    }

    @Override // java.util.Collection, java.util.Set
    public <T> T[] toArray(T[] tArr) {
        if (tArr.length < this.i) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.i));
        }
        System.arraycopy(this.h, 0, tArr, 0, this.i);
        int length = tArr.length;
        int i2 = this.i;
        if (length > i2) {
            tArr[i2] = null;
        }
        return tArr;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.i * 14);
        sb.append('{');
        for (int i2 = 0; i2 < this.i; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            E c2 = c(i2);
            if (c2 != this) {
                sb.append((Object) c2);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
