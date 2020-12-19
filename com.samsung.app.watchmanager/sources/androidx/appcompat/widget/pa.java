package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.view.menu.v;
import androidx.appcompat.widget.Toolbar;
import b.a.a;
import b.a.e;
import b.a.f;
import b.a.h;
import b.a.j;
import b.e.g.t;
import b.e.g.z;

public class pa implements D {

    /* renamed from: a  reason: collision with root package name */
    Toolbar f497a;

    /* renamed from: b  reason: collision with root package name */
    private int f498b;

    /* renamed from: c  reason: collision with root package name */
    private View f499c;

    /* renamed from: d  reason: collision with root package name */
    private View f500d;
    private Drawable e;
    private Drawable f;
    private Drawable g;
    private boolean h;
    CharSequence i;
    private CharSequence j;
    private CharSequence k;
    Window.Callback l;
    boolean m;
    private ActionMenuPresenter n;
    private int o;
    private int p;
    private Drawable q;

    public pa(Toolbar toolbar, boolean z) {
        this(toolbar, z, h.abc_action_bar_up_description, e.abc_ic_ab_back_material);
    }

    public pa(Toolbar toolbar, boolean z, int i2, int i3) {
        Drawable drawable;
        this.o = 0;
        this.p = 0;
        this.f497a = toolbar;
        this.i = toolbar.getTitle();
        this.j = toolbar.getSubtitle();
        this.h = this.i != null;
        this.g = toolbar.getNavigationIcon();
        ia a2 = ia.a(toolbar.getContext(), null, j.ActionBar, a.actionBarStyle, 0);
        this.q = a2.b(j.ActionBar_homeAsUpIndicator);
        if (z) {
            CharSequence e2 = a2.e(j.ActionBar_title);
            if (!TextUtils.isEmpty(e2)) {
                c(e2);
            }
            CharSequence e3 = a2.e(j.ActionBar_subtitle);
            if (!TextUtils.isEmpty(e3)) {
                b(e3);
            }
            Drawable b2 = a2.b(j.ActionBar_logo);
            if (b2 != null) {
                a(b2);
            }
            Drawable b3 = a2.b(j.ActionBar_icon);
            if (b3 != null) {
                setIcon(b3);
            }
            if (this.g == null && (drawable = this.q) != null) {
                b(drawable);
            }
            a(a2.d(j.ActionBar_displayOptions, 0));
            int g2 = a2.g(j.ActionBar_customNavigationLayout, 0);
            if (g2 != 0) {
                a(LayoutInflater.from(this.f497a.getContext()).inflate(g2, (ViewGroup) this.f497a, false));
                a(this.f498b | 16);
            }
            int f2 = a2.f(j.ActionBar_height, 0);
            if (f2 > 0) {
                ViewGroup.LayoutParams layoutParams = this.f497a.getLayoutParams();
                layoutParams.height = f2;
                this.f497a.setLayoutParams(layoutParams);
            }
            int b4 = a2.b(j.ActionBar_contentInsetStart, -1);
            int b5 = a2.b(j.ActionBar_contentInsetEnd, -1);
            if (b4 >= 0 || b5 >= 0) {
                this.f497a.setContentInsetsRelative(Math.max(b4, 0), Math.max(b5, 0));
            }
            int g3 = a2.g(j.ActionBar_titleTextStyle, 0);
            if (g3 != 0) {
                Toolbar toolbar2 = this.f497a;
                toolbar2.setTitleTextAppearance(toolbar2.getContext(), g3);
            }
            int g4 = a2.g(j.ActionBar_subtitleTextStyle, 0);
            if (g4 != 0) {
                Toolbar toolbar3 = this.f497a;
                toolbar3.setSubtitleTextAppearance(toolbar3.getContext(), g4);
            }
            int g5 = a2.g(j.ActionBar_popupTheme, 0);
            if (g5 != 0) {
                this.f497a.setPopupTheme(g5);
            }
        } else {
            this.f498b = n();
        }
        a2.a();
        d(i2);
        this.k = this.f497a.getNavigationContentDescription();
        this.f497a.setNavigationOnClickListener(new na(this));
    }

    private void d(CharSequence charSequence) {
        this.i = charSequence;
        if ((this.f498b & 8) != 0) {
            this.f497a.setTitle(charSequence);
        }
    }

    private int n() {
        if (this.f497a.getNavigationIcon() == null) {
            return 11;
        }
        this.q = this.f497a.getNavigationIcon();
        return 15;
    }

    private void o() {
        if ((this.f498b & 4) == 0) {
            return;
        }
        if (TextUtils.isEmpty(this.k)) {
            this.f497a.setNavigationContentDescription(this.p);
        } else {
            this.f497a.setNavigationContentDescription(this.k);
        }
    }

    private void p() {
        Drawable drawable;
        Toolbar toolbar;
        if ((this.f498b & 4) != 0) {
            toolbar = this.f497a;
            drawable = this.g;
            if (drawable == null) {
                drawable = this.q;
            }
        } else {
            toolbar = this.f497a;
            drawable = null;
        }
        toolbar.setNavigationIcon(drawable);
    }

    private void q() {
        Drawable drawable;
        int i2 = this.f498b;
        if ((i2 & 2) == 0) {
            drawable = null;
        } else if ((i2 & 1) == 0 || (drawable = this.f) == null) {
            drawable = this.e;
        }
        this.f497a.setLogo(drawable);
    }

    @Override // androidx.appcompat.widget.D
    public z a(int i2, long j2) {
        z a2 = t.a(this.f497a);
        a2.a(i2 == 0 ? 1.0f : 0.0f);
        a2.a(j2);
        a2.a(new oa(this, i2));
        return a2;
    }

    @Override // androidx.appcompat.widget.D
    public void a(int i2) {
        View view;
        CharSequence charSequence;
        Toolbar toolbar;
        int i3 = this.f498b ^ i2;
        this.f498b = i2;
        if (i3 != 0) {
            if ((i3 & 4) != 0) {
                if ((i2 & 4) != 0) {
                    o();
                }
                p();
            }
            if ((i3 & 3) != 0) {
                q();
            }
            if ((i3 & 8) != 0) {
                if ((i2 & 8) != 0) {
                    this.f497a.setTitle(this.i);
                    toolbar = this.f497a;
                    charSequence = this.j;
                } else {
                    charSequence = null;
                    this.f497a.setTitle((CharSequence) null);
                    toolbar = this.f497a;
                }
                toolbar.setSubtitle(charSequence);
            }
            if ((i3 & 16) != 0 && (view = this.f500d) != null) {
                if ((i2 & 16) != 0) {
                    this.f497a.addView(view);
                } else {
                    this.f497a.removeView(view);
                }
            }
        }
    }

    public void a(Drawable drawable) {
        this.f = drawable;
        q();
    }

    public void a(View view) {
        View view2 = this.f500d;
        if (!(view2 == null || (this.f498b & 16) == 0)) {
            this.f497a.removeView(view2);
        }
        this.f500d = view;
        if (view != null && (this.f498b & 16) != 0) {
            this.f497a.addView(this.f500d);
        }
    }

    @Override // androidx.appcompat.widget.D
    public void a(ScrollingTabContainerView scrollingTabContainerView) {
        Toolbar toolbar;
        View view = this.f499c;
        if (view != null && view.getParent() == (toolbar = this.f497a)) {
            toolbar.removeView(this.f499c);
        }
        this.f499c = scrollingTabContainerView;
        if (scrollingTabContainerView != null && this.o == 2) {
            this.f497a.addView(this.f499c, 0);
            Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) this.f499c.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).width = -2;
            ((ViewGroup.MarginLayoutParams) layoutParams).height = -2;
            layoutParams.f104a = 8388691;
            scrollingTabContainerView.setAllowCollapse(true);
        }
    }

    public void a(CharSequence charSequence) {
        this.k = charSequence;
        o();
    }

    @Override // androidx.appcompat.widget.D
    public void a(boolean z) {
    }

    @Override // androidx.appcompat.widget.D
    public boolean a() {
        return this.f497a.i();
    }

    @Override // androidx.appcompat.widget.D
    public void b(int i2) {
        a(i2 != 0 ? b.a.a.a.a.b(j(), i2) : null);
    }

    public void b(Drawable drawable) {
        this.g = drawable;
        p();
    }

    public void b(CharSequence charSequence) {
        this.j = charSequence;
        if ((this.f498b & 8) != 0) {
            this.f497a.setSubtitle(charSequence);
        }
    }

    @Override // androidx.appcompat.widget.D
    public void b(boolean z) {
        this.f497a.setCollapsible(z);
    }

    @Override // androidx.appcompat.widget.D
    public boolean b() {
        return this.f497a.b();
    }

    @Override // androidx.appcompat.widget.D
    public void c(int i2) {
        this.f497a.setVisibility(i2);
    }

    public void c(CharSequence charSequence) {
        this.h = true;
        d(charSequence);
    }

    @Override // androidx.appcompat.widget.D
    public boolean c() {
        return this.f497a.h();
    }

    @Override // androidx.appcompat.widget.D
    public void collapseActionView() {
        this.f497a.c();
    }

    public void d(int i2) {
        if (i2 != this.p) {
            this.p = i2;
            if (TextUtils.isEmpty(this.f497a.getNavigationContentDescription())) {
                e(this.p);
            }
        }
    }

    @Override // androidx.appcompat.widget.D
    public boolean d() {
        return this.f497a.g();
    }

    public void e(int i2) {
        a(i2 == 0 ? null : j().getString(i2));
    }

    @Override // androidx.appcompat.widget.D
    public boolean e() {
        return this.f497a.k();
    }

    @Override // androidx.appcompat.widget.D
    public void f() {
        this.f497a.d();
    }

    @Override // androidx.appcompat.widget.D
    public boolean g() {
        return this.f497a.f();
    }

    @Override // androidx.appcompat.widget.D
    public CharSequence getTitle() {
        return this.f497a.getTitle();
    }

    @Override // androidx.appcompat.widget.D
    public int h() {
        return this.o;
    }

    @Override // androidx.appcompat.widget.D
    public ViewGroup i() {
        return this.f497a;
    }

    @Override // androidx.appcompat.widget.D
    public Context j() {
        return this.f497a.getContext();
    }

    @Override // androidx.appcompat.widget.D
    public int k() {
        return this.f498b;
    }

    @Override // androidx.appcompat.widget.D
    public void l() {
        Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
    }

    @Override // androidx.appcompat.widget.D
    public void m() {
        Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
    }

    @Override // androidx.appcompat.widget.D
    public void setIcon(int i2) {
        setIcon(i2 != 0 ? b.a.a.a.a.b(j(), i2) : null);
    }

    @Override // androidx.appcompat.widget.D
    public void setIcon(Drawable drawable) {
        this.e = drawable;
        q();
    }

    @Override // androidx.appcompat.widget.D
    public void setMenu(Menu menu, v.a aVar) {
        if (this.n == null) {
            this.n = new ActionMenuPresenter(this.f497a.getContext());
            this.n.a(f.action_menu_presenter);
        }
        this.n.a(aVar);
        this.f497a.setMenu((l) menu, this.n);
    }

    @Override // androidx.appcompat.widget.D
    public void setMenuPrepared() {
        this.m = true;
    }

    @Override // androidx.appcompat.widget.D
    public void setWindowCallback(Window.Callback callback) {
        this.l = callback;
    }

    @Override // androidx.appcompat.widget.D
    public void setWindowTitle(CharSequence charSequence) {
        if (!this.h) {
            d(charSequence);
        }
    }
}
