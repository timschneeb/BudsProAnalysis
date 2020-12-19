package androidx.appcompat.view.menu;

import android.content.Context;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import androidx.appcompat.view.menu.v;
import androidx.appcompat.view.menu.w;
import b.a.g;
import java.util.ArrayList;

public class j implements v, AdapterView.OnItemClickListener {

    /* renamed from: a  reason: collision with root package name */
    Context f243a;

    /* renamed from: b  reason: collision with root package name */
    LayoutInflater f244b;

    /* renamed from: c  reason: collision with root package name */
    l f245c;

    /* renamed from: d  reason: collision with root package name */
    ExpandedMenuView f246d;
    int e;
    int f;
    int g;
    private v.a h;
    a i;

    private class a extends BaseAdapter {

        /* renamed from: a  reason: collision with root package name */
        private int f247a = -1;

        public a() {
            a();
        }

        /* access modifiers changed from: package-private */
        public void a() {
            p f = j.this.f245c.f();
            if (f != null) {
                ArrayList<p> j = j.this.f245c.j();
                int size = j.size();
                for (int i = 0; i < size; i++) {
                    if (j.get(i) == f) {
                        this.f247a = i;
                        return;
                    }
                }
            }
            this.f247a = -1;
        }

        public int getCount() {
            int size = j.this.f245c.j().size() - j.this.e;
            return this.f247a < 0 ? size : size - 1;
        }

        public p getItem(int i) {
            ArrayList<p> j = j.this.f245c.j();
            int i2 = i + j.this.e;
            int i3 = this.f247a;
            if (i3 >= 0 && i2 >= i3) {
                i2++;
            }
            return j.get(i2);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                j jVar = j.this;
                view = jVar.f244b.inflate(jVar.g, viewGroup, false);
            }
            ((w.a) view).a(getItem(i), 0);
            return view;
        }

        public void notifyDataSetChanged() {
            a();
            super.notifyDataSetChanged();
        }
    }

    public j(int i2, int i3) {
        this.g = i2;
        this.f = i3;
    }

    public j(Context context, int i2) {
        this(i2, 0);
        this.f243a = context;
        this.f244b = LayoutInflater.from(this.f243a);
    }

    public w a(ViewGroup viewGroup) {
        if (this.f246d == null) {
            this.f246d = (ExpandedMenuView) this.f244b.inflate(g.abc_expanded_menu_layout, viewGroup, false);
            if (this.i == null) {
                this.i = new a();
            }
            this.f246d.setAdapter((ListAdapter) this.i);
            this.f246d.setOnItemClickListener(this);
        }
        return this.f246d;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001c, code lost:
        if (r2.f244b == null) goto L_0x000b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    @Override // androidx.appcompat.view.menu.v
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(android.content.Context r3, androidx.appcompat.view.menu.l r4) {
        /*
            r2 = this;
            int r0 = r2.f
            if (r0 == 0) goto L_0x0014
            android.view.ContextThemeWrapper r1 = new android.view.ContextThemeWrapper
            r1.<init>(r3, r0)
            r2.f243a = r1
        L_0x000b:
            android.content.Context r3 = r2.f243a
            android.view.LayoutInflater r3 = android.view.LayoutInflater.from(r3)
            r2.f244b = r3
            goto L_0x001f
        L_0x0014:
            android.content.Context r0 = r2.f243a
            if (r0 == 0) goto L_0x001f
            r2.f243a = r3
            android.view.LayoutInflater r3 = r2.f244b
            if (r3 != 0) goto L_0x001f
            goto L_0x000b
        L_0x001f:
            r2.f245c = r4
            androidx.appcompat.view.menu.j$a r3 = r2.i
            if (r3 == 0) goto L_0x0028
            r3.notifyDataSetChanged()
        L_0x0028:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.j.a(android.content.Context, androidx.appcompat.view.menu.l):void");
    }

    @Override // androidx.appcompat.view.menu.v
    public void a(l lVar, boolean z) {
        v.a aVar = this.h;
        if (aVar != null) {
            aVar.a(lVar, z);
        }
    }

    @Override // androidx.appcompat.view.menu.v
    public void a(v.a aVar) {
        this.h = aVar;
    }

    @Override // androidx.appcompat.view.menu.v
    public void a(boolean z) {
        a aVar = this.i;
        if (aVar != null) {
            aVar.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.v
    public boolean a() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.v
    public boolean a(D d2) {
        if (!d2.hasVisibleItems()) {
            return false;
        }
        new m(d2).a((IBinder) null);
        v.a aVar = this.h;
        if (aVar == null) {
            return true;
        }
        aVar.a(d2);
        return true;
    }

    @Override // androidx.appcompat.view.menu.v
    public boolean a(l lVar, p pVar) {
        return false;
    }

    public ListAdapter b() {
        if (this.i == null) {
            this.i = new a();
        }
        return this.i;
    }

    @Override // androidx.appcompat.view.menu.v
    public boolean b(l lVar, p pVar) {
        return false;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
        this.f245c.a(this.i.getItem(i2), this, 0);
    }
}
