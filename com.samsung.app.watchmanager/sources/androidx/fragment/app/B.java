package androidx.fragment.app;

import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.app.j;
import b.c.b;
import b.e.g.t;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class B {

    /* renamed from: a  reason: collision with root package name */
    private static final int[] f688a = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8};

    /* renamed from: b  reason: collision with root package name */
    private static final K f689b = (Build.VERSION.SDK_INT >= 21 ? new G() : null);

    /* renamed from: c  reason: collision with root package name */
    private static final K f690c = a();

    /* access modifiers changed from: package-private */
    public static class a {

        /* renamed from: a  reason: collision with root package name */
        public Fragment f691a;

        /* renamed from: b  reason: collision with root package name */
        public boolean f692b;

        /* renamed from: c  reason: collision with root package name */
        public C0081a f693c;

        /* renamed from: d  reason: collision with root package name */
        public Fragment f694d;
        public boolean e;
        public C0081a f;

        a() {
        }
    }

    static View a(b<String, View> bVar, a aVar, Object obj, boolean z) {
        ArrayList<String> arrayList;
        C0081a aVar2 = aVar.f693c;
        if (obj == null || bVar == null || (arrayList = aVar2.r) == null || arrayList.isEmpty()) {
            return null;
        }
        return bVar.get((z ? aVar2.r : aVar2.s).get(0));
    }

    private static a a(a aVar, SparseArray<a> sparseArray, int i) {
        if (aVar != null) {
            return aVar;
        }
        a aVar2 = new a();
        sparseArray.put(i, aVar2);
        return aVar2;
    }

    private static K a() {
        try {
            return (K) Class.forName("androidx.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    private static K a(Fragment fragment, Fragment fragment2) {
        ArrayList arrayList = new ArrayList();
        if (fragment != null) {
            Object m = fragment.m();
            if (m != null) {
                arrayList.add(m);
            }
            Object u = fragment.u();
            if (u != null) {
                arrayList.add(u);
            }
            Object w = fragment.w();
            if (w != null) {
                arrayList.add(w);
            }
        }
        if (fragment2 != null) {
            Object k = fragment2.k();
            if (k != null) {
                arrayList.add(k);
            }
            Object s = fragment2.s();
            if (s != null) {
                arrayList.add(s);
            }
            Object v = fragment2.v();
            if (v != null) {
                arrayList.add(v);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        K k2 = f689b;
        if (k2 != null && a(k2, arrayList)) {
            return f689b;
        }
        K k3 = f690c;
        if (k3 != null && a(k3, arrayList)) {
            return f690c;
        }
        if (f689b == null && f690c == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    private static b<String, String> a(int i, ArrayList<C0081a> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        ArrayList<String> arrayList3;
        ArrayList<String> arrayList4;
        b<String, String> bVar = new b<>();
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            C0081a aVar = arrayList.get(i4);
            if (aVar.b(i)) {
                boolean booleanValue = arrayList2.get(i4).booleanValue();
                ArrayList<String> arrayList5 = aVar.r;
                if (arrayList5 != null) {
                    int size = arrayList5.size();
                    if (booleanValue) {
                        arrayList3 = aVar.r;
                        arrayList4 = aVar.s;
                    } else {
                        ArrayList<String> arrayList6 = aVar.r;
                        arrayList3 = aVar.s;
                        arrayList4 = arrayList6;
                    }
                    for (int i5 = 0; i5 < size; i5++) {
                        String str = arrayList4.get(i5);
                        String str2 = arrayList3.get(i5);
                        String remove = bVar.remove(str2);
                        if (remove != null) {
                            bVar.put(str, remove);
                        } else {
                            bVar.put(str, str2);
                        }
                    }
                }
            }
        }
        return bVar;
    }

    static b<String, View> a(K k, b<String, String> bVar, Object obj, a aVar) {
        j jVar;
        ArrayList<String> arrayList;
        String a2;
        Fragment fragment = aVar.f691a;
        View y = fragment.y();
        if (bVar.isEmpty() || obj == null || y == null) {
            bVar.clear();
            return null;
        }
        b<String, View> bVar2 = new b<>();
        k.a((Map<String, View>) bVar2, y);
        C0081a aVar2 = aVar.f693c;
        if (aVar.f692b) {
            jVar = fragment.n();
            arrayList = aVar2.r;
        } else {
            jVar = fragment.l();
            arrayList = aVar2.s;
        }
        if (arrayList != null) {
            bVar2.a((Collection<?>) arrayList);
            bVar2.a((Collection<?>) bVar.values());
        }
        if (jVar != null) {
            jVar.a(arrayList, bVar2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view = bVar2.get(str);
                if (view == null) {
                    String a3 = a(bVar, str);
                    if (a3 != null) {
                        bVar.remove(a3);
                    }
                } else if (!str.equals(t.o(view)) && (a2 = a(bVar, str)) != null) {
                    bVar.put(a2, t.o(view));
                }
            }
        } else {
            a(bVar, bVar2);
        }
        return bVar2;
    }

    private static Object a(K k, ViewGroup viewGroup, View view, b<String, String> bVar, a aVar, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        b<String, String> bVar2;
        Object obj3;
        Object obj4;
        Rect rect;
        Fragment fragment = aVar.f691a;
        Fragment fragment2 = aVar.f694d;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        boolean z = aVar.f692b;
        if (bVar.isEmpty()) {
            bVar2 = bVar;
            obj3 = null;
        } else {
            obj3 = a(k, fragment, fragment2, z);
            bVar2 = bVar;
        }
        b<String, View> b2 = b(k, bVar2, obj3, aVar);
        if (bVar.isEmpty()) {
            obj4 = null;
        } else {
            arrayList.addAll(b2.values());
            obj4 = obj3;
        }
        if (obj == null && obj2 == null && obj4 == null) {
            return null;
        }
        a(fragment, fragment2, z, b2, true);
        if (obj4 != null) {
            rect = new Rect();
            k.b(obj4, view, arrayList);
            a(k, obj4, obj2, b2, aVar.e, aVar.f);
            if (obj != null) {
                k.a(obj, rect);
            }
        } else {
            rect = null;
        }
        L.a(viewGroup, new A(k, bVar, obj4, aVar, arrayList2, view, fragment, fragment2, z, arrayList, obj, rect));
        return obj4;
    }

    private static Object a(K k, Fragment fragment, Fragment fragment2, boolean z) {
        if (fragment == null || fragment2 == null) {
            return null;
        }
        return k.c(k.b(z ? fragment2.w() : fragment.v()));
    }

    private static Object a(K k, Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return k.b(z ? fragment.s() : fragment.k());
    }

    private static Object a(K k, Object obj, Object obj2, Object obj3, Fragment fragment, boolean z) {
        return (obj == null || obj2 == null || fragment == null) ? true : z ? fragment.f() : fragment.e() ? k.b(obj2, obj, obj3) : k.a(obj2, obj, obj3);
    }

    private static String a(b<String, String> bVar, String str) {
        int size = bVar.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(bVar.d(i))) {
                return bVar.b(i);
            }
        }
        return null;
    }

    static ArrayList<View> a(K k, Object obj, Fragment fragment, ArrayList<View> arrayList, View view) {
        if (obj == null) {
            return null;
        }
        ArrayList<View> arrayList2 = new ArrayList<>();
        View y = fragment.y();
        if (y != null) {
            k.a(arrayList2, y);
        }
        if (arrayList != null) {
            arrayList2.removeAll(arrayList);
        }
        if (arrayList2.isEmpty()) {
            return arrayList2;
        }
        arrayList2.add(view);
        k.a(obj, arrayList2);
        return arrayList2;
    }

    static void a(Fragment fragment, Fragment fragment2, boolean z, b<String, View> bVar, boolean z2) {
        j l = z ? fragment2.l() : fragment.l();
        if (l != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int size = bVar == null ? 0 : bVar.size();
            for (int i = 0; i < size; i++) {
                arrayList2.add(bVar.b(i));
                arrayList.add(bVar.d(i));
            }
            if (z2) {
                l.b(arrayList2, arrayList, null);
            } else {
                l.a(arrayList2, arrayList, null);
            }
        }
    }

    private static void a(K k, ViewGroup viewGroup, Fragment fragment, View view, ArrayList<View> arrayList, Object obj, ArrayList<View> arrayList2, Object obj2, ArrayList<View> arrayList3) {
        L.a(viewGroup, new y(obj, k, view, fragment, arrayList, arrayList2, arrayList3, obj2));
    }

    private static void a(K k, Object obj, Fragment fragment, ArrayList<View> arrayList) {
        if (fragment != null && obj != null && fragment.m && fragment.C && fragment.Q) {
            fragment.f(true);
            k.a(obj, fragment.y(), arrayList);
            L.a(fragment.J, new x(arrayList));
        }
    }

    private static void a(K k, Object obj, Object obj2, b<String, View> bVar, boolean z, C0081a aVar) {
        ArrayList<String> arrayList = aVar.r;
        if (arrayList != null && !arrayList.isEmpty()) {
            View view = bVar.get((z ? aVar.s : aVar.r).get(0));
            k.c(obj, view);
            if (obj2 != null) {
                k.c(obj2, view);
            }
        }
    }

    public static void a(C0081a aVar, SparseArray<a> sparseArray, boolean z) {
        int size = aVar.f755b.size();
        for (int i = 0; i < size; i++) {
            a(aVar, aVar.f755b.get(i), sparseArray, false, z);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0041, code lost:
        if (r10.m != false) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0076, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0092, code lost:
        if (r10.C == false) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0094, code lost:
        r1 = true;
     */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00a2  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x00e7 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:96:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(androidx.fragment.app.C0081a r16, androidx.fragment.app.C0081a.C0013a r17, android.util.SparseArray<androidx.fragment.app.B.a> r18, boolean r19, boolean r20) {
        /*
        // Method dump skipped, instructions count: 242
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.B.a(androidx.fragment.app.a, androidx.fragment.app.a$a, android.util.SparseArray, boolean, boolean):void");
    }

    private static void a(r rVar, int i, a aVar, View view, b<String, String> bVar) {
        Fragment fragment;
        Fragment fragment2;
        K a2;
        Object obj;
        ViewGroup viewGroup = rVar.t.a() ? (ViewGroup) rVar.t.a(i) : null;
        if (viewGroup != null && (a2 = a((fragment2 = aVar.f694d), (fragment = aVar.f691a))) != null) {
            boolean z = aVar.f692b;
            boolean z2 = aVar.e;
            Object a3 = a(a2, fragment, z);
            Object b2 = b(a2, fragment2, z2);
            ArrayList arrayList = new ArrayList();
            ArrayList<View> arrayList2 = new ArrayList<>();
            Object a4 = a(a2, viewGroup, view, bVar, aVar, arrayList, arrayList2, a3, b2);
            if (a3 == null && a4 == null) {
                obj = b2;
                if (obj == null) {
                    return;
                }
            } else {
                obj = b2;
            }
            ArrayList<View> a5 = a(a2, obj, fragment2, arrayList, view);
            Object obj2 = (a5 == null || a5.isEmpty()) ? null : obj;
            a2.a(a3, view);
            Object a6 = a(a2, a3, obj2, a4, fragment, aVar.f692b);
            if (a6 != null) {
                ArrayList<View> arrayList3 = new ArrayList<>();
                a2.a(a6, a3, arrayList3, obj2, a5, a4, arrayList2);
                a(a2, viewGroup, fragment, view, arrayList2, a3, arrayList3, obj2, a5);
                a2.a((View) viewGroup, arrayList2, (Map<String, String>) bVar);
                a2.a(viewGroup, a6);
                a2.a(viewGroup, arrayList2, (Map<String, String>) bVar);
            }
        }
    }

    static void a(r rVar, ArrayList<C0081a> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, boolean z) {
        if (rVar.r >= 1) {
            SparseArray sparseArray = new SparseArray();
            for (int i3 = i; i3 < i2; i3++) {
                C0081a aVar = arrayList.get(i3);
                if (arrayList2.get(i3).booleanValue()) {
                    b(aVar, sparseArray, z);
                } else {
                    a(aVar, sparseArray, z);
                }
            }
            if (sparseArray.size() != 0) {
                View view = new View(rVar.s.c());
                int size = sparseArray.size();
                for (int i4 = 0; i4 < size; i4++) {
                    int keyAt = sparseArray.keyAt(i4);
                    b<String, String> a2 = a(keyAt, arrayList, arrayList2, i, i2);
                    a aVar2 = (a) sparseArray.valueAt(i4);
                    if (z) {
                        b(rVar, keyAt, aVar2, view, a2);
                    } else {
                        a(rVar, keyAt, aVar2, view, a2);
                    }
                }
            }
        }
    }

    private static void a(b<String, String> bVar, b<String, View> bVar2) {
        for (int size = bVar.size() - 1; size >= 0; size--) {
            if (!bVar2.containsKey(bVar.d(size))) {
                bVar.c(size);
            }
        }
    }

    static void a(ArrayList<View> arrayList, int i) {
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).setVisibility(i);
            }
        }
    }

    private static void a(ArrayList<View> arrayList, b<String, View> bVar, Collection<String> collection) {
        for (int size = bVar.size() - 1; size >= 0; size--) {
            View d2 = bVar.d(size);
            if (collection.contains(t.o(d2))) {
                arrayList.add(d2);
            }
        }
    }

    private static boolean a(K k, List<Object> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!k.a(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static b<String, View> b(K k, b<String, String> bVar, Object obj, a aVar) {
        j jVar;
        ArrayList<String> arrayList;
        if (bVar.isEmpty() || obj == null) {
            bVar.clear();
            return null;
        }
        Fragment fragment = aVar.f694d;
        b<String, View> bVar2 = new b<>();
        k.a((Map<String, View>) bVar2, fragment.y());
        C0081a aVar2 = aVar.f;
        if (aVar.e) {
            jVar = fragment.l();
            arrayList = aVar2.s;
        } else {
            jVar = fragment.n();
            arrayList = aVar2.r;
        }
        bVar2.a((Collection<?>) arrayList);
        if (jVar != null) {
            jVar.a(arrayList, bVar2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view = bVar2.get(str);
                if (view == null) {
                    bVar.remove(str);
                } else if (!str.equals(t.o(view))) {
                    bVar.put(t.o(view), bVar.remove(str));
                }
            }
        } else {
            bVar.a((Collection<?>) bVar2.keySet());
        }
        return bVar2;
    }

    private static Object b(K k, ViewGroup viewGroup, View view, b<String, String> bVar, a aVar, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        Object obj3;
        Rect rect;
        View view2;
        Fragment fragment = aVar.f691a;
        Fragment fragment2 = aVar.f694d;
        if (fragment != null) {
            fragment.y().setVisibility(0);
        }
        if (fragment == null || fragment2 == null) {
            return null;
        }
        boolean z = aVar.f692b;
        Object a2 = bVar.isEmpty() ? null : a(k, fragment, fragment2, z);
        b<String, View> b2 = b(k, bVar, a2, aVar);
        b<String, View> a3 = a(k, bVar, a2, aVar);
        if (bVar.isEmpty()) {
            if (b2 != null) {
                b2.clear();
            }
            if (a3 != null) {
                a3.clear();
            }
            obj3 = null;
        } else {
            a(arrayList, b2, bVar.keySet());
            a(arrayList2, a3, bVar.values());
            obj3 = a2;
        }
        if (obj == null && obj2 == null && obj3 == null) {
            return null;
        }
        a(fragment, fragment2, z, b2, true);
        if (obj3 != null) {
            arrayList2.add(view);
            k.b(obj3, view, arrayList);
            a(k, obj3, obj2, b2, aVar.e, aVar.f);
            Rect rect2 = new Rect();
            View a4 = a(a3, aVar, obj, z);
            if (a4 != null) {
                k.a(obj, rect2);
            }
            rect = rect2;
            view2 = a4;
        } else {
            view2 = null;
            rect = null;
        }
        L.a(viewGroup, new z(fragment, fragment2, z, a3, view2, k, rect));
        return obj3;
    }

    private static Object b(K k, Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return k.b(z ? fragment.u() : fragment.m());
    }

    public static void b(C0081a aVar, SparseArray<a> sparseArray, boolean z) {
        if (aVar.f754a.t.a()) {
            for (int size = aVar.f755b.size() - 1; size >= 0; size--) {
                a(aVar, aVar.f755b.get(size), sparseArray, true, z);
            }
        }
    }

    private static void b(r rVar, int i, a aVar, View view, b<String, String> bVar) {
        Fragment fragment;
        Fragment fragment2;
        K a2;
        Object obj;
        ViewGroup viewGroup = rVar.t.a() ? (ViewGroup) rVar.t.a(i) : null;
        if (viewGroup != null && (a2 = a((fragment2 = aVar.f694d), (fragment = aVar.f691a))) != null) {
            boolean z = aVar.f692b;
            boolean z2 = aVar.e;
            ArrayList<View> arrayList = new ArrayList<>();
            ArrayList<View> arrayList2 = new ArrayList<>();
            Object a3 = a(a2, fragment, z);
            Object b2 = b(a2, fragment2, z2);
            Object b3 = b(a2, viewGroup, view, bVar, aVar, arrayList2, arrayList, a3, b2);
            if (a3 == null && b3 == null) {
                obj = b2;
                if (obj == null) {
                    return;
                }
            } else {
                obj = b2;
            }
            ArrayList<View> a4 = a(a2, obj, fragment2, arrayList2, view);
            ArrayList<View> a5 = a(a2, a3, fragment, arrayList, view);
            a(a5, 4);
            Object a6 = a(a2, a3, obj, b3, fragment, z);
            if (a6 != null) {
                a(a2, obj, fragment2, a4);
                ArrayList<String> a7 = a2.a(arrayList);
                a2.a(a6, a3, a5, obj, a4, b3, arrayList);
                a2.a(viewGroup, a6);
                a2.a(viewGroup, arrayList2, arrayList, a7, bVar);
                a(a5, 0);
                a2.b(b3, arrayList2, arrayList);
            }
        }
    }
}
