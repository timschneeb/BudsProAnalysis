package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.view.menu.v;
import androidx.appcompat.widget.K;
import androidx.appcompat.widget.MenuPopupWindow;
import b.a.d;
import b.a.g;
import b.e.g.C0113c;
import b.e.g.t;
import java.util.ArrayList;
import java.util.List;

/* access modifiers changed from: package-private */
public final class i extends s implements v, View.OnKeyListener, PopupWindow.OnDismissListener {

    /* renamed from: b  reason: collision with root package name */
    private static final int f237b = g.abc_cascading_menu_item_layout;
    private PopupWindow.OnDismissListener A;
    boolean B;

    /* renamed from: c  reason: collision with root package name */
    private final Context f238c;

    /* renamed from: d  reason: collision with root package name */
    private final int f239d;
    private final int e;
    private final int f;
    private final boolean g;
    final Handler h;
    private final List<l> i = new ArrayList();
    final List<a> j = new ArrayList();
    final ViewTreeObserver.OnGlobalLayoutListener k = new ViewTreeObserver$OnGlobalLayoutListenerC0055e(this);
    private final View.OnAttachStateChangeListener l = new f(this);
    private final K m = new h(this);
    private int n = 0;
    private int o = 0;
    private View p;
    View q;
    private int r;
    private boolean s;
    private boolean t;
    private int u;
    private int v;
    private boolean w;
    private boolean x;
    private v.a y;
    ViewTreeObserver z;

    /* access modifiers changed from: private */
    public static class a {

        /* renamed from: a  reason: collision with root package name */
        public final MenuPopupWindow f240a;

        /* renamed from: b  reason: collision with root package name */
        public final l f241b;

        /* renamed from: c  reason: collision with root package name */
        public final int f242c;

        public a(MenuPopupWindow menuPopupWindow, l lVar, int i) {
            this.f240a = menuPopupWindow;
            this.f241b = lVar;
            this.f242c = i;
        }

        public ListView a() {
            return this.f240a.d();
        }
    }

    public i(Context context, View view, int i2, int i3, boolean z2) {
        this.f238c = context;
        this.p = view;
        this.e = i2;
        this.f = i3;
        this.g = z2;
        this.w = false;
        this.r = h();
        Resources resources = context.getResources();
        this.f239d = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(d.abc_config_prefDialogWidth));
        this.h = new Handler();
    }

    private MenuItem a(l lVar, l lVar2) {
        int size = lVar.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = lVar.getItem(i2);
            if (item.hasSubMenu() && lVar2 == item.getSubMenu()) {
                return item;
            }
        }
        return null;
    }

    private View a(a aVar, l lVar) {
        int i2;
        k kVar;
        int firstVisiblePosition;
        MenuItem a2 = a(aVar.f241b, lVar);
        if (a2 == null) {
            return null;
        }
        ListView a3 = aVar.a();
        ListAdapter adapter = a3.getAdapter();
        int i3 = 0;
        if (adapter instanceof HeaderViewListAdapter) {
            HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
            i2 = headerViewListAdapter.getHeadersCount();
            kVar = (k) headerViewListAdapter.getWrappedAdapter();
        } else {
            kVar = (k) adapter;
            i2 = 0;
        }
        int count = kVar.getCount();
        while (true) {
            if (i3 >= count) {
                i3 = -1;
                break;
            } else if (a2 == kVar.getItem(i3)) {
                break;
            } else {
                i3++;
            }
        }
        if (i3 != -1 && (firstVisiblePosition = (i3 + i2) - a3.getFirstVisiblePosition()) >= 0 && firstVisiblePosition < a3.getChildCount()) {
            return a3.getChildAt(firstVisiblePosition);
        }
        return null;
    }

    private int c(l lVar) {
        int size = this.j.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (lVar == this.j.get(i2).f241b) {
                return i2;
            }
        }
        return -1;
    }

    private int d(int i2) {
        List<a> list = this.j;
        ListView a2 = list.get(list.size() - 1).a();
        int[] iArr = new int[2];
        a2.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        this.q.getWindowVisibleDisplayFrame(rect);
        return this.r == 1 ? (iArr[0] + a2.getWidth()) + i2 > rect.right ? 0 : 1 : iArr[0] - i2 < 0 ? 1 : 0;
    }

    private void d(l lVar) {
        View view;
        a aVar;
        int i2;
        int i3;
        int i4;
        LayoutInflater from = LayoutInflater.from(this.f238c);
        k kVar = new k(lVar, from, this.g, f237b);
        if (!b() && this.w) {
            kVar.a(true);
        } else if (b()) {
            kVar.a(s.b(lVar));
        }
        int a2 = s.a(kVar, null, this.f238c, this.f239d);
        MenuPopupWindow g2 = g();
        g2.a((ListAdapter) kVar);
        g2.b(a2);
        g2.c(this.o);
        if (this.j.size() > 0) {
            List<a> list = this.j;
            aVar = list.get(list.size() - 1);
            view = a(aVar, lVar);
        } else {
            aVar = null;
            view = null;
        }
        if (view != null) {
            g2.c(false);
            g2.a((Object) null);
            int d2 = d(a2);
            boolean z2 = d2 == 1;
            this.r = d2;
            if (Build.VERSION.SDK_INT >= 26) {
                g2.a(view);
                i3 = 0;
                i2 = 0;
            } else {
                int[] iArr = new int[2];
                this.p.getLocationOnScreen(iArr);
                int[] iArr2 = new int[2];
                view.getLocationOnScreen(iArr2);
                if ((this.o & 7) == 5) {
                    iArr[0] = iArr[0] + this.p.getWidth();
                    iArr2[0] = iArr2[0] + view.getWidth();
                }
                i2 = iArr2[0] - iArr[0];
                i3 = iArr2[1] - iArr[1];
            }
            if ((this.o & 5) != 5) {
                if (z2) {
                    a2 = view.getWidth();
                }
                i4 = i2 - a2;
                g2.d(i4);
                g2.b(true);
                g2.h(i3);
            } else if (!z2) {
                a2 = view.getWidth();
                i4 = i2 - a2;
                g2.d(i4);
                g2.b(true);
                g2.h(i3);
            }
            i4 = i2 + a2;
            g2.d(i4);
            g2.b(true);
            g2.h(i3);
        } else {
            if (this.s) {
                g2.d(this.u);
            }
            if (this.t) {
                g2.h(this.v);
            }
            g2.a(f());
        }
        this.j.add(new a(g2, lVar, this.r));
        g2.c();
        ListView d3 = g2.d();
        d3.setOnKeyListener(this);
        if (aVar == null && this.x && lVar.h() != null) {
            FrameLayout frameLayout = (FrameLayout) from.inflate(g.abc_popup_menu_header_item_layout, (ViewGroup) d3, false);
            frameLayout.setEnabled(false);
            ((TextView) frameLayout.findViewById(16908310)).setText(lVar.h());
            d3.addHeaderView(frameLayout, null, false);
            g2.c();
        }
    }

    private MenuPopupWindow g() {
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.f238c, null, this.e, this.f);
        menuPopupWindow.a(this.m);
        menuPopupWindow.a((AdapterView.OnItemClickListener) this);
        menuPopupWindow.a((PopupWindow.OnDismissListener) this);
        menuPopupWindow.a(this.p);
        menuPopupWindow.c(this.o);
        menuPopupWindow.a(true);
        menuPopupWindow.e(2);
        return menuPopupWindow;
    }

    private int h() {
        return t.i(this.p) == 1 ? 0 : 1;
    }

    @Override // androidx.appcompat.view.menu.s
    public void a(int i2) {
        if (this.n != i2) {
            this.n = i2;
            this.o = C0113c.a(i2, t.i(this.p));
        }
    }

    @Override // androidx.appcompat.view.menu.s
    public void a(View view) {
        if (this.p != view) {
            this.p = view;
            this.o = C0113c.a(this.n, t.i(this.p));
        }
    }

    @Override // androidx.appcompat.view.menu.s
    public void a(PopupWindow.OnDismissListener onDismissListener) {
        this.A = onDismissListener;
    }

    @Override // androidx.appcompat.view.menu.s
    public void a(l lVar) {
        lVar.a(this, this.f238c);
        if (b()) {
            d(lVar);
        } else {
            this.i.add(lVar);
        }
    }

    @Override // androidx.appcompat.view.menu.v
    public void a(l lVar, boolean z2) {
        int c2 = c(lVar);
        if (c2 >= 0) {
            int i2 = c2 + 1;
            if (i2 < this.j.size()) {
                this.j.get(i2).f241b.a(false);
            }
            a remove = this.j.remove(c2);
            remove.f241b.b(this);
            if (this.B) {
                remove.f240a.b((Object) null);
                remove.f240a.a(0);
            }
            remove.f240a.dismiss();
            int size = this.j.size();
            this.r = size > 0 ? this.j.get(size - 1).f242c : h();
            if (size == 0) {
                dismiss();
                v.a aVar = this.y;
                if (aVar != null) {
                    aVar.a(lVar, true);
                }
                ViewTreeObserver viewTreeObserver = this.z;
                if (viewTreeObserver != null) {
                    if (viewTreeObserver.isAlive()) {
                        this.z.removeGlobalOnLayoutListener(this.k);
                    }
                    this.z = null;
                }
                this.q.removeOnAttachStateChangeListener(this.l);
                this.A.onDismiss();
            } else if (z2) {
                this.j.get(0).f241b.a(false);
            }
        }
    }

    @Override // androidx.appcompat.view.menu.v
    public void a(v.a aVar) {
        this.y = aVar;
    }

    @Override // androidx.appcompat.view.menu.v
    public void a(boolean z2) {
        for (a aVar : this.j) {
            s.a(aVar.a().getAdapter()).notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.v
    public boolean a() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.v
    public boolean a(D d2) {
        for (a aVar : this.j) {
            if (d2 == aVar.f241b) {
                aVar.a().requestFocus();
                return true;
            }
        }
        if (!d2.hasVisibleItems()) {
            return false;
        }
        a((l) d2);
        v.a aVar2 = this.y;
        if (aVar2 != null) {
            aVar2.a(d2);
        }
        return true;
    }

    @Override // androidx.appcompat.view.menu.s
    public void b(int i2) {
        this.s = true;
        this.u = i2;
    }

    @Override // androidx.appcompat.view.menu.s
    public void b(boolean z2) {
        this.w = z2;
    }

    @Override // androidx.appcompat.view.menu.z
    public boolean b() {
        return this.j.size() > 0 && this.j.get(0).f240a.b();
    }

    @Override // androidx.appcompat.view.menu.z
    public void c() {
        if (!b()) {
            for (l lVar : this.i) {
                d(lVar);
            }
            this.i.clear();
            this.q = this.p;
            if (this.q != null) {
                boolean z2 = this.z == null;
                this.z = this.q.getViewTreeObserver();
                if (z2) {
                    this.z.addOnGlobalLayoutListener(this.k);
                }
                this.q.addOnAttachStateChangeListener(this.l);
            }
        }
    }

    @Override // androidx.appcompat.view.menu.s
    public void c(int i2) {
        this.t = true;
        this.v = i2;
    }

    @Override // androidx.appcompat.view.menu.s
    public void c(boolean z2) {
        this.x = z2;
    }

    @Override // androidx.appcompat.view.menu.z
    public ListView d() {
        if (this.j.isEmpty()) {
            return null;
        }
        List<a> list = this.j;
        return list.get(list.size() - 1).a();
    }

    @Override // androidx.appcompat.view.menu.z
    public void dismiss() {
        int size = this.j.size();
        if (size > 0) {
            a[] aVarArr = (a[]) this.j.toArray(new a[size]);
            for (int i2 = size - 1; i2 >= 0; i2--) {
                a aVar = aVarArr[i2];
                if (aVar.f240a.b()) {
                    aVar.f240a.dismiss();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.view.menu.s
    public boolean e() {
        return false;
    }

    public void onDismiss() {
        a aVar;
        int size = this.j.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                aVar = null;
                break;
            }
            aVar = this.j.get(i2);
            if (!aVar.f240a.b()) {
                break;
            }
            i2++;
        }
        if (aVar != null) {
            aVar.f241b.a(false);
        }
    }

    public boolean onKey(View view, int i2, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i2 != 82) {
            return false;
        }
        dismiss();
        return true;
    }
}
