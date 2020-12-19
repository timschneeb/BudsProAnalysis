package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.view.menu.v;
import androidx.appcompat.widget.MenuPopupWindow;
import b.a.d;
import b.a.g;
import b.e.g.t;

final class C extends s implements PopupWindow.OnDismissListener, AdapterView.OnItemClickListener, v, View.OnKeyListener {

    /* renamed from: b  reason: collision with root package name */
    private static final int f208b = g.abc_popup_menu_item_layout;

    /* renamed from: c  reason: collision with root package name */
    private final Context f209c;

    /* renamed from: d  reason: collision with root package name */
    private final l f210d;
    private final k e;
    private final boolean f;
    private final int g;
    private final int h;
    private final int i;
    final MenuPopupWindow j;
    final ViewTreeObserver.OnGlobalLayoutListener k = new A(this);
    private final View.OnAttachStateChangeListener l = new B(this);
    private PopupWindow.OnDismissListener m;
    private View n;
    View o;
    private v.a p;
    ViewTreeObserver q;
    private boolean r;
    private boolean s;
    private int t;
    private int u = 0;
    private boolean v;

    public C(Context context, l lVar, View view, int i2, int i3, boolean z) {
        this.f209c = context;
        this.f210d = lVar;
        this.f = z;
        this.e = new k(lVar, LayoutInflater.from(context), this.f, f208b);
        this.h = i2;
        this.i = i3;
        Resources resources = context.getResources();
        this.g = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(d.abc_config_prefDialogWidth));
        this.n = view;
        this.j = new MenuPopupWindow(this.f209c, null, this.h, this.i);
        lVar.a(this, context);
    }

    private boolean g() {
        View view;
        if (b()) {
            return true;
        }
        if (this.r || (view = this.n) == null) {
            return false;
        }
        this.o = view;
        this.j.a((PopupWindow.OnDismissListener) this);
        this.j.a((AdapterView.OnItemClickListener) this);
        this.j.a(true);
        View view2 = this.o;
        boolean z = this.q == null;
        this.q = view2.getViewTreeObserver();
        if (z) {
            this.q.addOnGlobalLayoutListener(this.k);
        }
        view2.addOnAttachStateChangeListener(this.l);
        this.j.a(view2);
        this.j.c(this.u);
        if (!this.s) {
            this.t = s.a(this.e, null, this.f209c, this.g);
            this.s = true;
        }
        this.j.b(this.t);
        this.j.e(2);
        this.j.a(f());
        this.j.c();
        ListView d2 = this.j.d();
        d2.setOnKeyListener(this);
        if (this.v && this.f210d.h() != null) {
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.f209c).inflate(g.abc_popup_menu_header_item_layout, (ViewGroup) d2, false);
            TextView textView = (TextView) frameLayout.findViewById(16908310);
            if (textView != null) {
                textView.setText(this.f210d.h());
            }
            frameLayout.setEnabled(false);
            d2.addHeaderView(frameLayout, null, false);
        }
        this.j.a((ListAdapter) this.e);
        this.j.c();
        return true;
    }

    @Override // androidx.appcompat.view.menu.s
    public void a(int i2) {
        this.u = i2;
    }

    @Override // androidx.appcompat.view.menu.s
    public void a(View view) {
        this.n = view;
    }

    @Override // androidx.appcompat.view.menu.s
    public void a(PopupWindow.OnDismissListener onDismissListener) {
        this.m = onDismissListener;
    }

    @Override // androidx.appcompat.view.menu.s
    public void a(l lVar) {
    }

    @Override // androidx.appcompat.view.menu.v
    public void a(l lVar, boolean z) {
        if (lVar == this.f210d) {
            dismiss();
            v.a aVar = this.p;
            if (aVar != null) {
                aVar.a(lVar, z);
            }
        }
    }

    @Override // androidx.appcompat.view.menu.v
    public void a(v.a aVar) {
        this.p = aVar;
    }

    @Override // androidx.appcompat.view.menu.v
    public void a(boolean z) {
        this.s = false;
        k kVar = this.e;
        if (kVar != null) {
            kVar.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.v
    public boolean a() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.v
    public boolean a(D d2) {
        if (d2.hasVisibleItems()) {
            u uVar = new u(this.f209c, d2, this.o, this.f, this.h, this.i);
            uVar.a(this.p);
            uVar.a(s.b(d2));
            uVar.a(this.m);
            this.m = null;
            this.f210d.a(false);
            int g2 = this.j.g();
            int h2 = this.j.h();
            if ((Gravity.getAbsoluteGravity(this.u, t.i(this.n)) & 7) == 5) {
                g2 += this.n.getWidth();
            }
            if (uVar.a(g2, h2)) {
                v.a aVar = this.p;
                if (aVar == null) {
                    return true;
                }
                aVar.a(d2);
                return true;
            }
        }
        return false;
    }

    @Override // androidx.appcompat.view.menu.s
    public void b(int i2) {
        this.j.d(i2);
    }

    @Override // androidx.appcompat.view.menu.s
    public void b(boolean z) {
        this.e.a(z);
    }

    @Override // androidx.appcompat.view.menu.z
    public boolean b() {
        return !this.r && this.j.b();
    }

    @Override // androidx.appcompat.view.menu.z
    public void c() {
        if (!g()) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
    }

    @Override // androidx.appcompat.view.menu.s
    public void c(int i2) {
        this.j.h(i2);
    }

    @Override // androidx.appcompat.view.menu.s
    public void c(boolean z) {
        this.v = z;
    }

    @Override // androidx.appcompat.view.menu.z
    public ListView d() {
        return this.j.d();
    }

    @Override // androidx.appcompat.view.menu.z
    public void dismiss() {
        if (b()) {
            this.j.dismiss();
        }
    }

    public void onDismiss() {
        this.r = true;
        this.f210d.close();
        ViewTreeObserver viewTreeObserver = this.q;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.q = this.o.getViewTreeObserver();
            }
            this.q.removeGlobalOnLayoutListener(this.k);
            this.q = null;
        }
        this.o.removeOnAttachStateChangeListener(this.l);
        PopupWindow.OnDismissListener onDismissListener = this.m;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
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
