package androidx.coordinatorlayout.widget;

import b.c.i;
import b.e.f.e;
import b.e.f.f;
import java.util.ArrayList;
import java.util.HashSet;

public final class c<T> {

    /* renamed from: a  reason: collision with root package name */
    private final e<ArrayList<T>> f545a = new f(10);

    /* renamed from: b  reason: collision with root package name */
    private final i<T, ArrayList<T>> f546b = new i<>();

    /* renamed from: c  reason: collision with root package name */
    private final ArrayList<T> f547c = new ArrayList<>();

    /* renamed from: d  reason: collision with root package name */
    private final HashSet<T> f548d = new HashSet<>();

    private void a(T t, ArrayList<T> arrayList, HashSet<T> hashSet) {
        if (!arrayList.contains(t)) {
            if (!hashSet.contains(t)) {
                hashSet.add(t);
                ArrayList<T> arrayList2 = this.f546b.get(t);
                if (arrayList2 != null) {
                    int size = arrayList2.size();
                    for (int i = 0; i < size; i++) {
                        a(arrayList2.get(i), arrayList, hashSet);
                    }
                }
                hashSet.remove(t);
                arrayList.add(t);
                return;
            }
            throw new RuntimeException("This graph contains cyclic dependencies");
        }
    }

    private void a(ArrayList<T> arrayList) {
        arrayList.clear();
        this.f545a.a(arrayList);
    }

    private ArrayList<T> c() {
        ArrayList<T> a2 = this.f545a.a();
        return a2 == null ? new ArrayList<>() : a2;
    }

    public void a() {
        int size = this.f546b.size();
        for (int i = 0; i < size; i++) {
            ArrayList<T> d2 = this.f546b.d(i);
            if (d2 != null) {
                a((ArrayList) d2);
            }
        }
        this.f546b.clear();
    }

    public void a(T t) {
        if (!this.f546b.containsKey(t)) {
            this.f546b.put(t, null);
        }
    }

    public void a(T t, T t2) {
        if (!this.f546b.containsKey(t) || !this.f546b.containsKey(t2)) {
            throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
        }
        ArrayList<T> arrayList = this.f546b.get(t);
        if (arrayList == null) {
            arrayList = c();
            this.f546b.put(t, arrayList);
        }
        arrayList.add(t2);
    }

    public ArrayList<T> b() {
        this.f547c.clear();
        this.f548d.clear();
        int size = this.f546b.size();
        for (int i = 0; i < size; i++) {
            a(this.f546b.b(i), this.f547c, this.f548d);
        }
        return this.f547c;
    }

    public boolean b(T t) {
        return this.f546b.containsKey(t);
    }

    public boolean c(T t) {
        int size = this.f546b.size();
        for (int i = 0; i < size; i++) {
            ArrayList<T> d2 = this.f546b.d(i);
            if (d2 != null && d2.contains(t)) {
                return true;
            }
        }
        return false;
    }
}
