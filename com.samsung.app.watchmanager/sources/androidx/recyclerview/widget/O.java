package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import b.c.f;
import b.e.f.e;

class O {

    /* renamed from: a  reason: collision with root package name */
    final b.c.b<RecyclerView.v, a> f972a = new b.c.b<>();

    /* renamed from: b  reason: collision with root package name */
    final f<RecyclerView.v> f973b = new f<>();

    /* access modifiers changed from: package-private */
    public static class a {

        /* renamed from: a  reason: collision with root package name */
        static e<a> f974a = new b.e.f.f(20);

        /* renamed from: b  reason: collision with root package name */
        int f975b;

        /* renamed from: c  reason: collision with root package name */
        RecyclerView.f.c f976c;

        /* renamed from: d  reason: collision with root package name */
        RecyclerView.f.c f977d;

        private a() {
        }

        static void a() {
            do {
            } while (f974a.a() != null);
        }

        static void a(a aVar) {
            aVar.f975b = 0;
            aVar.f976c = null;
            aVar.f977d = null;
            f974a.a(aVar);
        }

        static a b() {
            a a2 = f974a.a();
            return a2 == null ? new a() : a2;
        }
    }

    interface b {
        void a(RecyclerView.v vVar);

        void a(RecyclerView.v vVar, RecyclerView.f.c cVar, RecyclerView.f.c cVar2);

        void b(RecyclerView.v vVar, RecyclerView.f.c cVar, RecyclerView.f.c cVar2);

        void c(RecyclerView.v vVar, RecyclerView.f.c cVar, RecyclerView.f.c cVar2);
    }

    O() {
    }

    private RecyclerView.f.c a(RecyclerView.v vVar, int i) {
        a d2;
        RecyclerView.f.c cVar;
        int a2 = this.f972a.a(vVar);
        if (a2 >= 0 && (d2 = this.f972a.d(a2)) != null) {
            int i2 = d2.f975b;
            if ((i2 & i) != 0) {
                d2.f975b = (i ^ -1) & i2;
                if (i == 4) {
                    cVar = d2.f976c;
                } else if (i == 8) {
                    cVar = d2.f977d;
                } else {
                    throw new IllegalArgumentException("Must provide flag PRE or POST");
                }
                if ((d2.f975b & 12) == 0) {
                    this.f972a.c(a2);
                    a.a(d2);
                }
                return cVar;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public RecyclerView.v a(long j) {
        return this.f973b.b(j);
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.f972a.clear();
        this.f973b.a();
    }

    /* access modifiers changed from: package-private */
    public void a(long j, RecyclerView.v vVar) {
        this.f973b.c(j, vVar);
    }

    /* access modifiers changed from: package-private */
    public void a(b bVar) {
        RecyclerView.f.c cVar;
        RecyclerView.f.c cVar2;
        for (int size = this.f972a.size() - 1; size >= 0; size--) {
            RecyclerView.v b2 = this.f972a.b(size);
            a c2 = this.f972a.c(size);
            int i = c2.f975b;
            if ((i & 3) != 3) {
                if ((i & 1) != 0) {
                    cVar = c2.f976c;
                    if (cVar != null) {
                        cVar2 = c2.f977d;
                    }
                } else {
                    if ((i & 14) != 14) {
                        if ((i & 12) == 12) {
                            bVar.c(b2, c2.f976c, c2.f977d);
                        } else if ((i & 4) != 0) {
                            cVar = c2.f976c;
                            cVar2 = null;
                        } else if ((i & 8) == 0) {
                        }
                        a.a(c2);
                    }
                    bVar.a(b2, c2.f976c, c2.f977d);
                    a.a(c2);
                }
                bVar.b(b2, cVar, cVar2);
                a.a(c2);
            }
            bVar.a(b2);
            a.a(c2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(RecyclerView.v vVar) {
        a aVar = this.f972a.get(vVar);
        if (aVar == null) {
            aVar = a.b();
            this.f972a.put(vVar, aVar);
        }
        aVar.f975b |= 1;
    }

    /* access modifiers changed from: package-private */
    public void a(RecyclerView.v vVar, RecyclerView.f.c cVar) {
        a aVar = this.f972a.get(vVar);
        if (aVar == null) {
            aVar = a.b();
            this.f972a.put(vVar, aVar);
        }
        aVar.f975b |= 2;
        aVar.f976c = cVar;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        a.a();
    }

    /* access modifiers changed from: package-private */
    public void b(RecyclerView.v vVar, RecyclerView.f.c cVar) {
        a aVar = this.f972a.get(vVar);
        if (aVar == null) {
            aVar = a.b();
            this.f972a.put(vVar, aVar);
        }
        aVar.f977d = cVar;
        aVar.f975b |= 8;
    }

    /* access modifiers changed from: package-private */
    public boolean b(RecyclerView.v vVar) {
        a aVar = this.f972a.get(vVar);
        return (aVar == null || (aVar.f975b & 1) == 0) ? false : true;
    }

    /* access modifiers changed from: package-private */
    public void c(RecyclerView.v vVar, RecyclerView.f.c cVar) {
        a aVar = this.f972a.get(vVar);
        if (aVar == null) {
            aVar = a.b();
            this.f972a.put(vVar, aVar);
        }
        aVar.f976c = cVar;
        aVar.f975b |= 4;
    }

    /* access modifiers changed from: package-private */
    public boolean c(RecyclerView.v vVar) {
        a aVar = this.f972a.get(vVar);
        return (aVar == null || (aVar.f975b & 4) == 0) ? false : true;
    }

    public void d(RecyclerView.v vVar) {
        g(vVar);
    }

    /* access modifiers changed from: package-private */
    public RecyclerView.f.c e(RecyclerView.v vVar) {
        return a(vVar, 8);
    }

    /* access modifiers changed from: package-private */
    public RecyclerView.f.c f(RecyclerView.v vVar) {
        return a(vVar, 4);
    }

    /* access modifiers changed from: package-private */
    public void g(RecyclerView.v vVar) {
        a aVar = this.f972a.get(vVar);
        if (aVar != null) {
            aVar.f975b &= -2;
        }
    }

    /* access modifiers changed from: package-private */
    public void h(RecyclerView.v vVar) {
        int b2 = this.f973b.b() - 1;
        while (true) {
            if (b2 < 0) {
                break;
            } else if (vVar == this.f973b.c(b2)) {
                this.f973b.b(b2);
                break;
            } else {
                b2--;
            }
        }
        a remove = this.f972a.remove(vVar);
        if (remove != null) {
            a.a(remove);
        }
    }
}
