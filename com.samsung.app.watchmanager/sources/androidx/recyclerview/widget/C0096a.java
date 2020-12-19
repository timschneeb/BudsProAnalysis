package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.u;
import b.e.f.e;
import b.e.f.f;
import java.util.ArrayList;
import java.util.List;

/* access modifiers changed from: package-private */
/* renamed from: androidx.recyclerview.widget.a  reason: case insensitive filesystem */
public class C0096a implements u.a {

    /* renamed from: a  reason: collision with root package name */
    private e<b> f1049a;

    /* renamed from: b  reason: collision with root package name */
    final ArrayList<b> f1050b;

    /* renamed from: c  reason: collision with root package name */
    final ArrayList<b> f1051c;

    /* renamed from: d  reason: collision with root package name */
    final AbstractC0015a f1052d;
    Runnable e;
    final boolean f;
    final u g;
    private int h;

    /* access modifiers changed from: package-private */
    /* renamed from: androidx.recyclerview.widget.a$a  reason: collision with other inner class name */
    public interface AbstractC0015a {
        RecyclerView.v a(int i);

        void a(int i, int i2);

        void a(int i, int i2, Object obj);

        void a(b bVar);

        void b(int i, int i2);

        void b(b bVar);

        void c(int i, int i2);

        void d(int i, int i2);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: androidx.recyclerview.widget.a$b */
    public static class b {

        /* renamed from: a  reason: collision with root package name */
        int f1053a;

        /* renamed from: b  reason: collision with root package name */
        int f1054b;

        /* renamed from: c  reason: collision with root package name */
        Object f1055c;

        /* renamed from: d  reason: collision with root package name */
        int f1056d;

        b(int i, int i2, int i3, Object obj) {
            this.f1053a = i;
            this.f1054b = i2;
            this.f1056d = i3;
            this.f1055c = obj;
        }

        /* access modifiers changed from: package-private */
        public String a() {
            int i = this.f1053a;
            return i != 1 ? i != 2 ? i != 4 ? i != 8 ? "??" : "mv" : "up" : "rm" : "add";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || b.class != obj.getClass()) {
                return false;
            }
            b bVar = (b) obj;
            int i = this.f1053a;
            if (i != bVar.f1053a) {
                return false;
            }
            if (i == 8 && Math.abs(this.f1056d - this.f1054b) == 1 && this.f1056d == bVar.f1054b && this.f1054b == bVar.f1056d) {
                return true;
            }
            if (this.f1056d != bVar.f1056d || this.f1054b != bVar.f1054b) {
                return false;
            }
            Object obj2 = this.f1055c;
            if (obj2 != null) {
                if (!obj2.equals(bVar.f1055c)) {
                    return false;
                }
            } else if (bVar.f1055c != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.f1053a * 31) + this.f1054b) * 31) + this.f1056d;
        }

        public String toString() {
            return Integer.toHexString(System.identityHashCode(this)) + "[" + a() + ",s:" + this.f1054b + "c:" + this.f1056d + ",p:" + this.f1055c + "]";
        }
    }

    C0096a(AbstractC0015a aVar) {
        this(aVar, false);
    }

    C0096a(AbstractC0015a aVar, boolean z) {
        this.f1049a = new f(30);
        this.f1050b = new ArrayList<>();
        this.f1051c = new ArrayList<>();
        this.h = 0;
        this.f1052d = aVar;
        this.f = z;
        this.g = new u(this);
    }

    private void b(b bVar) {
        g(bVar);
    }

    private void c(b bVar) {
        g(bVar);
    }

    private int d(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        for (int size = this.f1051c.size() - 1; size >= 0; size--) {
            b bVar = this.f1051c.get(size);
            int i7 = bVar.f1053a;
            if (i7 == 8) {
                int i8 = bVar.f1054b;
                int i9 = bVar.f1056d;
                if (i8 >= i9) {
                    i9 = i8;
                    i8 = i9;
                }
                if (i < i8 || i > i9) {
                    int i10 = bVar.f1054b;
                    if (i < i10) {
                        if (i2 == 1) {
                            bVar.f1054b = i10 + 1;
                            i4 = bVar.f1056d + 1;
                        } else if (i2 == 2) {
                            bVar.f1054b = i10 - 1;
                            i4 = bVar.f1056d - 1;
                        }
                        bVar.f1056d = i4;
                    }
                } else {
                    int i11 = bVar.f1054b;
                    if (i8 == i11) {
                        if (i2 == 1) {
                            i6 = bVar.f1056d + 1;
                        } else {
                            if (i2 == 2) {
                                i6 = bVar.f1056d - 1;
                            }
                            i++;
                        }
                        bVar.f1056d = i6;
                        i++;
                    } else {
                        if (i2 == 1) {
                            i5 = i11 + 1;
                        } else {
                            if (i2 == 2) {
                                i5 = i11 - 1;
                            }
                            i--;
                        }
                        bVar.f1054b = i5;
                        i--;
                    }
                }
            } else {
                int i12 = bVar.f1054b;
                if (i12 > i) {
                    if (i2 == 1) {
                        i3 = i12 + 1;
                    } else if (i2 == 2) {
                        i3 = i12 - 1;
                    }
                    bVar.f1054b = i3;
                } else if (i7 == 1) {
                    i -= bVar.f1056d;
                } else if (i7 == 2) {
                    i += bVar.f1056d;
                }
            }
        }
        for (int size2 = this.f1051c.size() - 1; size2 >= 0; size2--) {
            b bVar2 = this.f1051c.get(size2);
            if (bVar2.f1053a == 8) {
                int i13 = bVar2.f1056d;
                if (i13 != bVar2.f1054b && i13 >= 0) {
                }
            } else if (bVar2.f1056d > 0) {
            }
            this.f1051c.remove(size2);
            a(bVar2);
        }
        return i;
    }

    private void d(b bVar) {
        char c2;
        boolean z;
        int i = bVar.f1054b;
        int i2 = bVar.f1056d + i;
        int i3 = 0;
        char c3 = 65535;
        int i4 = i;
        while (i4 < i2) {
            if (this.f1052d.a(i4) != null || d(i4)) {
                if (c3 == 0) {
                    f(a(2, i, i3, null));
                    z = true;
                } else {
                    z = false;
                }
                c2 = 1;
            } else {
                if (c3 == 1) {
                    g(a(2, i, i3, null));
                    z = true;
                } else {
                    z = false;
                }
                c2 = 0;
            }
            if (z) {
                i4 -= i3;
                i2 -= i3;
                i3 = 1;
            } else {
                i3++;
            }
            i4++;
            c3 = c2;
        }
        if (i3 != bVar.f1056d) {
            a(bVar);
            bVar = a(2, i, i3, null);
        }
        if (c3 == 0) {
            f(bVar);
        } else {
            g(bVar);
        }
    }

    private boolean d(int i) {
        int size = this.f1051c.size();
        for (int i2 = 0; i2 < size; i2++) {
            b bVar = this.f1051c.get(i2);
            int i3 = bVar.f1053a;
            if (i3 == 8) {
                if (a(bVar.f1056d, i2 + 1) == i) {
                    return true;
                }
            } else if (i3 == 1) {
                int i4 = bVar.f1054b;
                int i5 = bVar.f1056d + i4;
                while (i4 < i5) {
                    if (a(i4, i2 + 1) == i) {
                        return true;
                    }
                    i4++;
                }
                continue;
            } else {
                continue;
            }
        }
        return false;
    }

    private void e(b bVar) {
        int i = bVar.f1054b;
        int i2 = bVar.f1056d + i;
        int i3 = i;
        int i4 = 0;
        char c2 = 65535;
        while (i < i2) {
            if (this.f1052d.a(i) != null || d(i)) {
                if (c2 == 0) {
                    f(a(4, i3, i4, bVar.f1055c));
                    i3 = i;
                    i4 = 0;
                }
                c2 = 1;
            } else {
                if (c2 == 1) {
                    g(a(4, i3, i4, bVar.f1055c));
                    i3 = i;
                    i4 = 0;
                }
                c2 = 0;
            }
            i4++;
            i++;
        }
        if (i4 != bVar.f1056d) {
            Object obj = bVar.f1055c;
            a(bVar);
            bVar = a(4, i3, i4, obj);
        }
        if (c2 == 0) {
            f(bVar);
        } else {
            g(bVar);
        }
    }

    private void f(b bVar) {
        int i;
        int i2 = bVar.f1053a;
        if (i2 == 1 || i2 == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int d2 = d(bVar.f1054b, i2);
        int i3 = bVar.f1054b;
        int i4 = bVar.f1053a;
        if (i4 == 2) {
            i = 0;
        } else if (i4 == 4) {
            i = 1;
        } else {
            throw new IllegalArgumentException("op should be remove or update." + bVar);
        }
        int i5 = d2;
        int i6 = i3;
        int i7 = 1;
        for (int i8 = 1; i8 < bVar.f1056d; i8++) {
            int d3 = d(bVar.f1054b + (i * i8), bVar.f1053a);
            int i9 = bVar.f1053a;
            if (i9 == 2 ? d3 == i5 : i9 == 4 && d3 == i5 + 1) {
                i7++;
            } else {
                b a2 = a(bVar.f1053a, i5, i7, bVar.f1055c);
                a(a2, i6);
                a(a2);
                if (bVar.f1053a == 4) {
                    i6 += i7;
                }
                i5 = d3;
                i7 = 1;
            }
        }
        Object obj = bVar.f1055c;
        a(bVar);
        if (i7 > 0) {
            b a3 = a(bVar.f1053a, i5, i7, obj);
            a(a3, i6);
            a(a3);
        }
    }

    private void g(b bVar) {
        this.f1051c.add(bVar);
        int i = bVar.f1053a;
        if (i == 1) {
            this.f1052d.c(bVar.f1054b, bVar.f1056d);
        } else if (i == 2) {
            this.f1052d.b(bVar.f1054b, bVar.f1056d);
        } else if (i == 4) {
            this.f1052d.a(bVar.f1054b, bVar.f1056d, bVar.f1055c);
        } else if (i == 8) {
            this.f1052d.a(bVar.f1054b, bVar.f1056d);
        } else {
            throw new IllegalArgumentException("Unknown update op type for " + bVar);
        }
    }

    public int a(int i) {
        int size = this.f1050b.size();
        for (int i2 = 0; i2 < size; i2++) {
            b bVar = this.f1050b.get(i2);
            int i3 = bVar.f1053a;
            if (i3 != 1) {
                if (i3 == 2) {
                    int i4 = bVar.f1054b;
                    if (i4 <= i) {
                        int i5 = bVar.f1056d;
                        if (i4 + i5 > i) {
                            return -1;
                        }
                        i -= i5;
                    } else {
                        continue;
                    }
                } else if (i3 == 8) {
                    int i6 = bVar.f1054b;
                    if (i6 == i) {
                        i = bVar.f1056d;
                    } else {
                        if (i6 < i) {
                            i--;
                        }
                        if (bVar.f1056d <= i) {
                            i++;
                        }
                    }
                }
            } else if (bVar.f1054b <= i) {
                i += bVar.f1056d;
            }
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    public int a(int i, int i2) {
        int size = this.f1051c.size();
        while (i2 < size) {
            b bVar = this.f1051c.get(i2);
            int i3 = bVar.f1053a;
            if (i3 == 8) {
                int i4 = bVar.f1054b;
                if (i4 == i) {
                    i = bVar.f1056d;
                } else {
                    if (i4 < i) {
                        i--;
                    }
                    if (bVar.f1056d <= i) {
                        i++;
                    }
                }
            } else {
                int i5 = bVar.f1054b;
                if (i5 > i) {
                    continue;
                } else if (i3 == 2) {
                    int i6 = bVar.f1056d;
                    if (i < i5 + i6) {
                        return -1;
                    }
                    i -= i6;
                } else if (i3 == 1) {
                    i += bVar.f1056d;
                }
            }
            i2++;
        }
        return i;
    }

    @Override // androidx.recyclerview.widget.u.a
    public b a(int i, int i2, int i3, Object obj) {
        b a2 = this.f1049a.a();
        if (a2 == null) {
            return new b(i, i2, i3, obj);
        }
        a2.f1053a = i;
        a2.f1054b = i2;
        a2.f1056d = i3;
        a2.f1055c = obj;
        return a2;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        int size = this.f1051c.size();
        for (int i = 0; i < size; i++) {
            this.f1052d.b(this.f1051c.get(i));
        }
        a(this.f1051c);
        this.h = 0;
    }

    @Override // androidx.recyclerview.widget.u.a
    public void a(b bVar) {
        if (!this.f) {
            bVar.f1055c = null;
            this.f1049a.a(bVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(b bVar, int i) {
        this.f1052d.a(bVar);
        int i2 = bVar.f1053a;
        if (i2 == 2) {
            this.f1052d.d(i, bVar.f1056d);
        } else if (i2 == 4) {
            this.f1052d.a(i, bVar.f1056d, bVar.f1055c);
        } else {
            throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        }
    }

    /* access modifiers changed from: package-private */
    public void a(List<b> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            a(list.get(i));
        }
        list.clear();
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i, int i2, int i3) {
        if (i == i2) {
            return false;
        }
        if (i3 == 1) {
            this.f1050b.add(a(8, i, i2, null));
            this.h |= 8;
            return this.f1050b.size() == 1;
        }
        throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i, int i2, Object obj) {
        if (i2 < 1) {
            return false;
        }
        this.f1050b.add(a(4, i, i2, obj));
        this.h |= 4;
        return this.f1050b.size() == 1;
    }

    /* access modifiers changed from: package-private */
    public int b(int i) {
        return a(i, 0);
    }

    /* access modifiers changed from: package-private */
    public void b() {
        a();
        int size = this.f1050b.size();
        for (int i = 0; i < size; i++) {
            b bVar = this.f1050b.get(i);
            int i2 = bVar.f1053a;
            if (i2 == 1) {
                this.f1052d.b(bVar);
                this.f1052d.c(bVar.f1054b, bVar.f1056d);
            } else if (i2 == 2) {
                this.f1052d.b(bVar);
                this.f1052d.d(bVar.f1054b, bVar.f1056d);
            } else if (i2 == 4) {
                this.f1052d.b(bVar);
                this.f1052d.a(bVar.f1054b, bVar.f1056d, bVar.f1055c);
            } else if (i2 == 8) {
                this.f1052d.b(bVar);
                this.f1052d.a(bVar.f1054b, bVar.f1056d);
            }
            Runnable runnable = this.e;
            if (runnable != null) {
                runnable.run();
            }
        }
        a(this.f1050b);
        this.h = 0;
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i, int i2) {
        if (i2 < 1) {
            return false;
        }
        this.f1050b.add(a(1, i, i2, null));
        this.h |= 1;
        return this.f1050b.size() == 1;
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        return this.f1050b.size() > 0;
    }

    /* access modifiers changed from: package-private */
    public boolean c(int i) {
        return (i & this.h) != 0;
    }

    /* access modifiers changed from: package-private */
    public boolean c(int i, int i2) {
        if (i2 < 1) {
            return false;
        }
        this.f1050b.add(a(2, i, i2, null));
        this.h |= 2;
        return this.f1050b.size() == 1;
    }

    /* access modifiers changed from: package-private */
    public boolean d() {
        return !this.f1051c.isEmpty() && !this.f1050b.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public void e() {
        this.g.a(this.f1050b);
        int size = this.f1050b.size();
        for (int i = 0; i < size; i++) {
            b bVar = this.f1050b.get(i);
            int i2 = bVar.f1053a;
            if (i2 == 1) {
                b(bVar);
            } else if (i2 == 2) {
                d(bVar);
            } else if (i2 == 4) {
                e(bVar);
            } else if (i2 == 8) {
                c(bVar);
            }
            Runnable runnable = this.e;
            if (runnable != null) {
                runnable.run();
            }
        }
        this.f1050b.clear();
    }

    /* access modifiers changed from: package-private */
    public void f() {
        a(this.f1050b);
        a(this.f1051c);
        this.h = 0;
    }
}
