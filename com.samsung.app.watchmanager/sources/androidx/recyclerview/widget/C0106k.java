package androidx.recyclerview.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.recyclerview.widget.RecyclerView;
import b.e.g.t;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: androidx.recyclerview.widget.k  reason: case insensitive filesystem */
public class C0106k extends J {
    private static TimeInterpolator h;
    private ArrayList<RecyclerView.v> i = new ArrayList<>();
    private ArrayList<RecyclerView.v> j = new ArrayList<>();
    private ArrayList<b> k = new ArrayList<>();
    private ArrayList<a> l = new ArrayList<>();
    ArrayList<ArrayList<RecyclerView.v>> m = new ArrayList<>();
    ArrayList<ArrayList<b>> n = new ArrayList<>();
    ArrayList<ArrayList<a>> o = new ArrayList<>();
    ArrayList<RecyclerView.v> p = new ArrayList<>();
    ArrayList<RecyclerView.v> q = new ArrayList<>();
    ArrayList<RecyclerView.v> r = new ArrayList<>();
    ArrayList<RecyclerView.v> s = new ArrayList<>();

    /* access modifiers changed from: private */
    /* renamed from: androidx.recyclerview.widget.k$a */
    public static class a {

        /* renamed from: a  reason: collision with root package name */
        public RecyclerView.v f1088a;

        /* renamed from: b  reason: collision with root package name */
        public RecyclerView.v f1089b;

        /* renamed from: c  reason: collision with root package name */
        public int f1090c;

        /* renamed from: d  reason: collision with root package name */
        public int f1091d;
        public int e;
        public int f;

        private a(RecyclerView.v vVar, RecyclerView.v vVar2) {
            this.f1088a = vVar;
            this.f1089b = vVar2;
        }

        a(RecyclerView.v vVar, RecyclerView.v vVar2, int i, int i2, int i3, int i4) {
            this(vVar, vVar2);
            this.f1090c = i;
            this.f1091d = i2;
            this.e = i3;
            this.f = i4;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.f1088a + ", newHolder=" + this.f1089b + ", fromX=" + this.f1090c + ", fromY=" + this.f1091d + ", toX=" + this.e + ", toY=" + this.f + '}';
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: androidx.recyclerview.widget.k$b */
    public static class b {

        /* renamed from: a  reason: collision with root package name */
        public RecyclerView.v f1092a;

        /* renamed from: b  reason: collision with root package name */
        public int f1093b;

        /* renamed from: c  reason: collision with root package name */
        public int f1094c;

        /* renamed from: d  reason: collision with root package name */
        public int f1095d;
        public int e;

        b(RecyclerView.v vVar, int i, int i2, int i3, int i4) {
            this.f1092a = vVar;
            this.f1093b = i;
            this.f1094c = i2;
            this.f1095d = i3;
            this.e = i4;
        }
    }

    private void a(List<a> list, RecyclerView.v vVar) {
        for (int size = list.size() - 1; size >= 0; size--) {
            a aVar = list.get(size);
            if (a(aVar, vVar) && aVar.f1088a == null && aVar.f1089b == null) {
                list.remove(aVar);
            }
        }
    }

    private boolean a(a aVar, RecyclerView.v vVar) {
        boolean z = false;
        if (aVar.f1089b == vVar) {
            aVar.f1089b = null;
        } else if (aVar.f1088a != vVar) {
            return false;
        } else {
            aVar.f1088a = null;
            z = true;
        }
        vVar.itemView.setAlpha(1.0f);
        vVar.itemView.setTranslationX(0.0f);
        vVar.itemView.setTranslationY(0.0f);
        a(vVar, z);
        return true;
    }

    private void b(a aVar) {
        RecyclerView.v vVar = aVar.f1088a;
        if (vVar != null) {
            a(aVar, vVar);
        }
        RecyclerView.v vVar2 = aVar.f1089b;
        if (vVar2 != null) {
            a(aVar, vVar2);
        }
    }

    private void u(RecyclerView.v vVar) {
        View view = vVar.itemView;
        ViewPropertyAnimator animate = view.animate();
        this.r.add(vVar);
        animate.setDuration(f()).alpha(0.0f).setListener(new C0101f(this, vVar, animate, view)).start();
    }

    private void v(RecyclerView.v vVar) {
        if (h == null) {
            h = new ValueAnimator().getInterpolator();
        }
        vVar.itemView.animate().setInterpolator(h);
        d(vVar);
    }

    /* access modifiers changed from: package-private */
    public void a(a aVar) {
        RecyclerView.v vVar = aVar.f1088a;
        View view = null;
        View view2 = vVar == null ? null : vVar.itemView;
        RecyclerView.v vVar2 = aVar.f1089b;
        if (vVar2 != null) {
            view = vVar2.itemView;
        }
        if (view2 != null) {
            ViewPropertyAnimator duration = view2.animate().setDuration(d());
            this.s.add(aVar.f1088a);
            duration.translationX((float) (aVar.e - aVar.f1090c));
            duration.translationY((float) (aVar.f - aVar.f1091d));
            duration.alpha(0.0f).setListener(new C0104i(this, aVar, duration, view2)).start();
        }
        if (view != null) {
            ViewPropertyAnimator animate = view.animate();
            this.s.add(aVar.f1089b);
            animate.translationX(0.0f).translationY(0.0f).setDuration(d()).alpha(1.0f).setListener(new C0105j(this, aVar, animate, view)).start();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(List<RecyclerView.v> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            list.get(size).itemView.animate().cancel();
        }
    }

    @Override // androidx.recyclerview.widget.J
    public boolean a(RecyclerView.v vVar, int i2, int i3, int i4, int i5) {
        View view = vVar.itemView;
        int translationX = i2 + ((int) view.getTranslationX());
        int translationY = i3 + ((int) vVar.itemView.getTranslationY());
        v(vVar);
        int i6 = i4 - translationX;
        int i7 = i5 - translationY;
        if (i6 == 0 && i7 == 0) {
            j(vVar);
            return false;
        }
        if (i6 != 0) {
            view.setTranslationX((float) (-i6));
        }
        if (i7 != 0) {
            view.setTranslationY((float) (-i7));
        }
        this.k.add(new b(vVar, translationX, translationY, i4, i5));
        return true;
    }

    @Override // androidx.recyclerview.widget.J
    public boolean a(RecyclerView.v vVar, RecyclerView.v vVar2, int i2, int i3, int i4, int i5) {
        if (vVar == vVar2) {
            return a(vVar, i2, i3, i4, i5);
        }
        float translationX = vVar.itemView.getTranslationX();
        float translationY = vVar.itemView.getTranslationY();
        float alpha = vVar.itemView.getAlpha();
        v(vVar);
        int i6 = (int) (((float) (i4 - i2)) - translationX);
        int i7 = (int) (((float) (i5 - i3)) - translationY);
        vVar.itemView.setTranslationX(translationX);
        vVar.itemView.setTranslationY(translationY);
        vVar.itemView.setAlpha(alpha);
        if (vVar2 != null) {
            v(vVar2);
            vVar2.itemView.setTranslationX((float) (-i6));
            vVar2.itemView.setTranslationY((float) (-i7));
            vVar2.itemView.setAlpha(0.0f);
        }
        this.l.add(new a(vVar, vVar2, i2, i3, i4, i5));
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean a(RecyclerView.v vVar, List<Object> list) {
        return !list.isEmpty() || super.a(vVar, list);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public void b() {
        int size = this.k.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            b bVar = this.k.get(size);
            View view = bVar.f1092a.itemView;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            j(bVar.f1092a);
            this.k.remove(size);
        }
        for (int size2 = this.i.size() - 1; size2 >= 0; size2--) {
            l(this.i.get(size2));
            this.i.remove(size2);
        }
        int size3 = this.j.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            RecyclerView.v vVar = this.j.get(size3);
            vVar.itemView.setAlpha(1.0f);
            h(vVar);
            this.j.remove(size3);
        }
        for (int size4 = this.l.size() - 1; size4 >= 0; size4--) {
            b(this.l.get(size4));
        }
        this.l.clear();
        if (g()) {
            for (int size5 = this.n.size() - 1; size5 >= 0; size5--) {
                ArrayList<b> arrayList = this.n.get(size5);
                for (int size6 = arrayList.size() - 1; size6 >= 0; size6--) {
                    b bVar2 = arrayList.get(size6);
                    View view2 = bVar2.f1092a.itemView;
                    view2.setTranslationY(0.0f);
                    view2.setTranslationX(0.0f);
                    j(bVar2.f1092a);
                    arrayList.remove(size6);
                    if (arrayList.isEmpty()) {
                        this.n.remove(arrayList);
                    }
                }
            }
            for (int size7 = this.m.size() - 1; size7 >= 0; size7--) {
                ArrayList<RecyclerView.v> arrayList2 = this.m.get(size7);
                for (int size8 = arrayList2.size() - 1; size8 >= 0; size8--) {
                    RecyclerView.v vVar2 = arrayList2.get(size8);
                    vVar2.itemView.setAlpha(1.0f);
                    h(vVar2);
                    arrayList2.remove(size8);
                    if (arrayList2.isEmpty()) {
                        this.m.remove(arrayList2);
                    }
                }
            }
            for (int size9 = this.o.size() - 1; size9 >= 0; size9--) {
                ArrayList<a> arrayList3 = this.o.get(size9);
                for (int size10 = arrayList3.size() - 1; size10 >= 0; size10--) {
                    b(arrayList3.get(size10));
                    if (arrayList3.isEmpty()) {
                        this.o.remove(arrayList3);
                    }
                }
            }
            a(this.r);
            a(this.q);
            a(this.p);
            a(this.s);
            a();
        }
    }

    /* access modifiers changed from: package-private */
    public void b(RecyclerView.v vVar, int i2, int i3, int i4, int i5) {
        View view = vVar.itemView;
        int i6 = i4 - i2;
        int i7 = i5 - i3;
        if (i6 != 0) {
            view.animate().translationX(0.0f);
        }
        if (i7 != 0) {
            view.animate().translationY(0.0f);
        }
        ViewPropertyAnimator animate = view.animate();
        this.q.add(vVar);
        animate.setDuration(e()).setListener(new C0103h(this, vVar, i6, view, i7, animate)).start();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public void d(RecyclerView.v vVar) {
        View view = vVar.itemView;
        view.animate().cancel();
        int size = this.k.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            } else if (this.k.get(size).f1092a == vVar) {
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                j(vVar);
                this.k.remove(size);
            }
        }
        a(this.l, vVar);
        if (this.i.remove(vVar)) {
            view.setAlpha(1.0f);
            l(vVar);
        }
        if (this.j.remove(vVar)) {
            view.setAlpha(1.0f);
            h(vVar);
        }
        for (int size2 = this.o.size() - 1; size2 >= 0; size2--) {
            ArrayList<a> arrayList = this.o.get(size2);
            a(arrayList, vVar);
            if (arrayList.isEmpty()) {
                this.o.remove(size2);
            }
        }
        for (int size3 = this.n.size() - 1; size3 >= 0; size3--) {
            ArrayList<b> arrayList2 = this.n.get(size3);
            int size4 = arrayList2.size() - 1;
            while (true) {
                if (size4 < 0) {
                    break;
                } else if (arrayList2.get(size4).f1092a == vVar) {
                    view.setTranslationY(0.0f);
                    view.setTranslationX(0.0f);
                    j(vVar);
                    arrayList2.remove(size4);
                    if (arrayList2.isEmpty()) {
                        this.n.remove(size3);
                    }
                } else {
                    size4--;
                }
            }
        }
        for (int size5 = this.m.size() - 1; size5 >= 0; size5--) {
            ArrayList<RecyclerView.v> arrayList3 = this.m.get(size5);
            if (arrayList3.remove(vVar)) {
                view.setAlpha(1.0f);
                h(vVar);
                if (arrayList3.isEmpty()) {
                    this.m.remove(size5);
                }
            }
        }
        this.r.remove(vVar);
        this.p.remove(vVar);
        this.s.remove(vVar);
        this.q.remove(vVar);
        j();
    }

    @Override // androidx.recyclerview.widget.J
    public boolean f(RecyclerView.v vVar) {
        v(vVar);
        vVar.itemView.setAlpha(0.0f);
        this.j.add(vVar);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean g() {
        return !this.j.isEmpty() || !this.l.isEmpty() || !this.k.isEmpty() || !this.i.isEmpty() || !this.q.isEmpty() || !this.r.isEmpty() || !this.p.isEmpty() || !this.s.isEmpty() || !this.n.isEmpty() || !this.m.isEmpty() || !this.o.isEmpty();
    }

    @Override // androidx.recyclerview.widget.J
    public boolean g(RecyclerView.v vVar) {
        v(vVar);
        this.i.add(vVar);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public void i() {
        boolean z = !this.i.isEmpty();
        boolean z2 = !this.k.isEmpty();
        boolean z3 = !this.l.isEmpty();
        boolean z4 = !this.j.isEmpty();
        if (z || z2 || z4 || z3) {
            Iterator<RecyclerView.v> it = this.i.iterator();
            while (it.hasNext()) {
                u(it.next());
            }
            this.i.clear();
            if (z2) {
                ArrayList<b> arrayList = new ArrayList<>();
                arrayList.addAll(this.k);
                this.n.add(arrayList);
                this.k.clear();
                RunnableC0098c cVar = new RunnableC0098c(this, arrayList);
                if (z) {
                    t.a(arrayList.get(0).f1092a.itemView, cVar, f());
                } else {
                    cVar.run();
                }
            }
            if (z3) {
                ArrayList<a> arrayList2 = new ArrayList<>();
                arrayList2.addAll(this.l);
                this.o.add(arrayList2);
                this.l.clear();
                RunnableC0099d dVar = new RunnableC0099d(this, arrayList2);
                if (z) {
                    t.a(arrayList2.get(0).f1088a.itemView, dVar, f());
                } else {
                    dVar.run();
                }
            }
            if (z4) {
                ArrayList<RecyclerView.v> arrayList3 = new ArrayList<>();
                arrayList3.addAll(this.j);
                this.m.add(arrayList3);
                this.j.clear();
                RunnableC0100e eVar = new RunnableC0100e(this, arrayList3);
                if (z || z2 || z3) {
                    long j2 = 0;
                    long f = z ? f() : 0;
                    long e = z2 ? e() : 0;
                    if (z3) {
                        j2 = d();
                    }
                    t.a(arrayList3.get(0).itemView, eVar, f + Math.max(e, j2));
                    return;
                }
                eVar.run();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void j() {
        if (!g()) {
            a();
        }
    }

    /* access modifiers changed from: package-private */
    public void t(RecyclerView.v vVar) {
        View view = vVar.itemView;
        ViewPropertyAnimator animate = view.animate();
        this.p.add(vVar);
        animate.alpha(1.0f).setDuration(c()).setListener(new C0102g(this, vVar, view, animate)).start();
    }
}
