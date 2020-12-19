package androidx.lifecycle;

import android.util.Log;
import androidx.lifecycle.f;
import b.b.a.b.b;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class j extends f {

    /* renamed from: a  reason: collision with root package name */
    private b.b.a.b.a<g, a> f846a = new b.b.a.b.a<>();

    /* renamed from: b  reason: collision with root package name */
    private f.b f847b;

    /* renamed from: c  reason: collision with root package name */
    private final WeakReference<h> f848c;

    /* renamed from: d  reason: collision with root package name */
    private int f849d = 0;
    private boolean e = false;
    private boolean f = false;
    private ArrayList<f.b> g = new ArrayList<>();

    /* access modifiers changed from: package-private */
    public static class a {

        /* renamed from: a  reason: collision with root package name */
        f.b f850a;

        /* renamed from: b  reason: collision with root package name */
        e f851b;

        a(g gVar, f.b bVar) {
            this.f851b = l.a(gVar);
            this.f850a = bVar;
        }

        /* access modifiers changed from: package-private */
        public void a(h hVar, f.a aVar) {
            f.b a2 = j.a(aVar);
            this.f850a = j.a(this.f850a, a2);
            this.f851b.a(hVar, aVar);
            this.f850a = a2;
        }
    }

    public j(h hVar) {
        this.f848c = new WeakReference<>(hVar);
        this.f847b = f.b.INITIALIZED;
    }

    static f.b a(f.a aVar) {
        switch (i.f844a[aVar.ordinal()]) {
            case 1:
            case 2:
                return f.b.CREATED;
            case 3:
            case 4:
                return f.b.STARTED;
            case 5:
                return f.b.RESUMED;
            case 6:
                return f.b.DESTROYED;
            default:
                throw new IllegalArgumentException("Unexpected event value " + aVar);
        }
    }

    static f.b a(f.b bVar, f.b bVar2) {
        return (bVar2 == null || bVar2.compareTo(bVar) >= 0) ? bVar : bVar2;
    }

    private void a(h hVar) {
        Iterator<Map.Entry<g, a>> descendingIterator = this.f846a.descendingIterator();
        while (descendingIterator.hasNext() && !this.f) {
            Map.Entry<g, a> next = descendingIterator.next();
            a value = next.getValue();
            while (value.f850a.compareTo((Enum) this.f847b) > 0 && !this.f && this.f846a.contains(next.getKey())) {
                f.a b2 = b(value.f850a);
                d(a(b2));
                value.a(hVar, b2);
                c();
            }
        }
    }

    private static f.a b(f.b bVar) {
        int i = i.f845b[bVar.ordinal()];
        if (i == 1) {
            throw new IllegalArgumentException();
        } else if (i == 2) {
            return f.a.ON_DESTROY;
        } else {
            if (i == 3) {
                return f.a.ON_STOP;
            }
            if (i == 4) {
                return f.a.ON_PAUSE;
            }
            if (i != 5) {
                throw new IllegalArgumentException("Unexpected state value " + bVar);
            }
            throw new IllegalArgumentException();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v3, resolved type: b.b.a.b.a<androidx.lifecycle.g, androidx.lifecycle.j$a> */
    /* JADX WARN: Multi-variable type inference failed */
    private void b(h hVar) {
        b<K, V>.d b2 = this.f846a.b();
        while (b2.hasNext() && !this.f) {
            Map.Entry entry = (Map.Entry) b2.next();
            a aVar = (a) entry.getValue();
            while (aVar.f850a.compareTo((Enum) this.f847b) < 0 && !this.f && this.f846a.contains(entry.getKey())) {
                d(aVar.f850a);
                aVar.a(hVar, e(aVar.f850a));
                c();
            }
        }
    }

    private boolean b() {
        if (this.f846a.size() == 0) {
            return true;
        }
        f.b bVar = this.f846a.a().getValue().f850a;
        f.b bVar2 = this.f846a.c().getValue().f850a;
        return bVar == bVar2 && this.f847b == bVar2;
    }

    private f.b c(g gVar) {
        Map.Entry<g, a> b2 = this.f846a.b(gVar);
        f.b bVar = null;
        f.b bVar2 = b2 != null ? b2.getValue().f850a : null;
        if (!this.g.isEmpty()) {
            ArrayList<f.b> arrayList = this.g;
            bVar = arrayList.get(arrayList.size() - 1);
        }
        return a(a(this.f847b, bVar2), bVar);
    }

    private void c() {
        ArrayList<f.b> arrayList = this.g;
        arrayList.remove(arrayList.size() - 1);
    }

    private void c(f.b bVar) {
        if (this.f847b != bVar) {
            this.f847b = bVar;
            if (this.e || this.f849d != 0) {
                this.f = true;
                return;
            }
            this.e = true;
            d();
            this.e = false;
        }
    }

    private void d() {
        h hVar = this.f848c.get();
        if (hVar == null) {
            Log.w("LifecycleRegistry", "LifecycleOwner is garbage collected, you shouldn't try dispatch new events from it.");
            return;
        }
        while (!b()) {
            this.f = false;
            if (this.f847b.compareTo((Enum) this.f846a.a().getValue().f850a) < 0) {
                a(hVar);
            }
            Map.Entry<g, a> c2 = this.f846a.c();
            if (!this.f && c2 != null && this.f847b.compareTo((Enum) c2.getValue().f850a) > 0) {
                b(hVar);
            }
        }
        this.f = false;
    }

    private void d(f.b bVar) {
        this.g.add(bVar);
    }

    private static f.a e(f.b bVar) {
        int i = i.f845b[bVar.ordinal()];
        if (i != 1) {
            if (i == 2) {
                return f.a.ON_START;
            }
            if (i == 3) {
                return f.a.ON_RESUME;
            }
            if (i == 4) {
                throw new IllegalArgumentException();
            } else if (i != 5) {
                throw new IllegalArgumentException("Unexpected state value " + bVar);
            }
        }
        return f.a.ON_CREATE;
    }

    @Override // androidx.lifecycle.f
    public f.b a() {
        return this.f847b;
    }

    public void a(f.b bVar) {
        c(bVar);
    }

    @Override // androidx.lifecycle.f
    public void a(g gVar) {
        h hVar;
        f.b bVar = this.f847b;
        f.b bVar2 = f.b.DESTROYED;
        if (bVar != bVar2) {
            bVar2 = f.b.INITIALIZED;
        }
        a aVar = new a(gVar, bVar2);
        if (this.f846a.b(gVar, aVar) == null && (hVar = this.f848c.get()) != null) {
            boolean z = this.f849d != 0 || this.e;
            f.b c2 = c(gVar);
            this.f849d++;
            while (aVar.f850a.compareTo((Enum) c2) < 0 && this.f846a.contains(gVar)) {
                d(aVar.f850a);
                aVar.a(hVar, e(aVar.f850a));
                c();
                c2 = c(gVar);
            }
            if (!z) {
                d();
            }
            this.f849d--;
        }
    }

    public void b(f.a aVar) {
        c(a(aVar));
    }

    @Override // androidx.lifecycle.f
    public void b(g gVar) {
        this.f846a.remove(gVar);
    }
}
