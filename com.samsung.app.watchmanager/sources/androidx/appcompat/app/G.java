package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.D;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.Toolbar;
import b.a.d.b;
import b.a.d.g;
import b.a.d.i;
import b.a.f;
import b.a.j;
import b.e.g.A;
import b.e.g.C;
import b.e.g.t;
import b.e.g.z;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class G extends ActionBar implements ActionBarOverlayLayout.a {

    /* renamed from: a  reason: collision with root package name */
    private static final Interpolator f158a = new AccelerateInterpolator();

    /* renamed from: b  reason: collision with root package name */
    private static final Interpolator f159b = new DecelerateInterpolator();
    private boolean A = true;
    i B;
    private boolean C;
    boolean D;
    final A E = new D(this);
    final A F = new E(this);
    final C G = new F(this);

    /* renamed from: c  reason: collision with root package name */
    Context f160c;

    /* renamed from: d  reason: collision with root package name */
    private Context f161d;
    private Activity e;
    private Dialog f;
    ActionBarOverlayLayout g;
    ActionBarContainer h;
    D i;
    ActionBarContextView j;
    View k;
    ScrollingTabContainerView l;
    private ArrayList<Object> m = new ArrayList<>();
    private int n = -1;
    private boolean o;
    a p;
    b q;
    b.a r;
    private boolean s;
    private ArrayList<ActionBar.a> t = new ArrayList<>();
    private boolean u;
    private int v = 0;
    boolean w = true;
    boolean x;
    boolean y;
    private boolean z;

    public class a extends b implements l.a {

        /* renamed from: c  reason: collision with root package name */
        private final Context f162c;

        /* renamed from: d  reason: collision with root package name */
        private final l f163d;
        private b.a e;
        private WeakReference<View> f;

        public a(Context context, b.a aVar) {
            this.f162c = context;
            this.e = aVar;
            l lVar = new l(context);
            lVar.c(1);
            this.f163d = lVar;
            this.f163d.a(this);
        }

        @Override // b.a.d.b
        public void a() {
            G g2 = G.this;
            if (g2.p == this) {
                if (!G.a(g2.x, g2.y, false)) {
                    G g3 = G.this;
                    g3.q = this;
                    g3.r = this.e;
                } else {
                    this.e.a(this);
                }
                this.e = null;
                G.this.e(false);
                G.this.j.a();
                G.this.i.i().sendAccessibilityEvent(32);
                G g4 = G.this;
                g4.g.setHideOnContentScrollEnabled(g4.D);
                G.this.p = null;
            }
        }

        @Override // b.a.d.b
        public void a(int i) {
            a((CharSequence) G.this.f160c.getResources().getString(i));
        }

        @Override // b.a.d.b
        public void a(View view) {
            G.this.j.setCustomView(view);
            this.f = new WeakReference<>(view);
        }

        @Override // androidx.appcompat.view.menu.l.a
        public void a(l lVar) {
            if (this.e != null) {
                i();
                G.this.j.d();
            }
        }

        @Override // b.a.d.b
        public void a(CharSequence charSequence) {
            G.this.j.setSubtitle(charSequence);
        }

        @Override // b.a.d.b
        public void a(boolean z) {
            super.a(z);
            G.this.j.setTitleOptional(z);
        }

        @Override // androidx.appcompat.view.menu.l.a
        public boolean a(l lVar, MenuItem menuItem) {
            b.a aVar = this.e;
            if (aVar != null) {
                return aVar.a(this, menuItem);
            }
            return false;
        }

        @Override // b.a.d.b
        public View b() {
            WeakReference<View> weakReference = this.f;
            if (weakReference != null) {
                return weakReference.get();
            }
            return null;
        }

        @Override // b.a.d.b
        public void b(int i) {
            b(G.this.f160c.getResources().getString(i));
        }

        @Override // b.a.d.b
        public void b(CharSequence charSequence) {
            G.this.j.setTitle(charSequence);
        }

        @Override // b.a.d.b
        public Menu c() {
            return this.f163d;
        }

        @Override // b.a.d.b
        public MenuInflater d() {
            return new g(this.f162c);
        }

        @Override // b.a.d.b
        public CharSequence e() {
            return G.this.j.getSubtitle();
        }

        @Override // b.a.d.b
        public CharSequence g() {
            return G.this.j.getTitle();
        }

        @Override // b.a.d.b
        public void i() {
            if (G.this.p == this) {
                this.f163d.s();
                try {
                    this.e.b(this, this.f163d);
                } finally {
                    this.f163d.r();
                }
            }
        }

        @Override // b.a.d.b
        public boolean j() {
            return G.this.j.b();
        }

        public boolean k() {
            this.f163d.s();
            try {
                return this.e.a(this, this.f163d);
            } finally {
                this.f163d.r();
            }
        }
    }

    public G(Activity activity, boolean z2) {
        this.e = activity;
        View decorView = activity.getWindow().getDecorView();
        b(decorView);
        if (!z2) {
            this.k = decorView.findViewById(16908290);
        }
    }

    public G(Dialog dialog) {
        this.f = dialog;
        b(dialog.getWindow().getDecorView());
    }

    private D a(View view) {
        if (view instanceof D) {
            return (D) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Can't make a decor toolbar out of ");
        sb.append(view != null ? view.getClass().getSimpleName() : "null");
        throw new IllegalStateException(sb.toString());
    }

    static boolean a(boolean z2, boolean z3, boolean z4) {
        if (z4) {
            return true;
        }
        return !z2 && !z3;
    }

    private void b(View view) {
        this.g = (ActionBarOverlayLayout) view.findViewById(f.decor_content_parent);
        ActionBarOverlayLayout actionBarOverlayLayout = this.g;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setActionBarVisibilityCallback(this);
        }
        this.i = a(view.findViewById(f.action_bar));
        this.j = (ActionBarContextView) view.findViewById(f.action_context_bar);
        this.h = (ActionBarContainer) view.findViewById(f.action_bar_container);
        D d2 = this.i;
        if (d2 == null || this.j == null || this.h == null) {
            throw new IllegalStateException(G.class.getSimpleName() + " can only be used " + "with a compatible window decor layout");
        }
        this.f160c = d2.j();
        boolean z2 = (this.i.k() & 4) != 0;
        if (z2) {
            this.o = true;
        }
        b.a.d.a a2 = b.a.d.a.a(this.f160c);
        j(a2.a() || z2);
        k(a2.f());
        TypedArray obtainStyledAttributes = this.f160c.obtainStyledAttributes(null, j.ActionBar, b.a.a.actionBarStyle, 0);
        if (obtainStyledAttributes.getBoolean(j.ActionBar_hideOnContentScroll, false)) {
            i(true);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(j.ActionBar_elevation, 0);
        if (dimensionPixelSize != 0) {
            a((float) dimensionPixelSize);
        }
        obtainStyledAttributes.recycle();
    }

    private void k(boolean z2) {
        this.u = z2;
        if (!this.u) {
            this.i.a((ScrollingTabContainerView) null);
            this.h.setTabContainer(this.l);
        } else {
            this.h.setTabContainer(null);
            this.i.a(this.l);
        }
        boolean z3 = true;
        boolean z4 = m() == 2;
        ScrollingTabContainerView scrollingTabContainerView = this.l;
        if (scrollingTabContainerView != null) {
            if (z4) {
                scrollingTabContainerView.setVisibility(0);
                ActionBarOverlayLayout actionBarOverlayLayout = this.g;
                if (actionBarOverlayLayout != null) {
                    t.z(actionBarOverlayLayout);
                }
            } else {
                scrollingTabContainerView.setVisibility(8);
            }
        }
        this.i.b(!this.u && z4);
        ActionBarOverlayLayout actionBarOverlayLayout2 = this.g;
        if (this.u || !z4) {
            z3 = false;
        }
        actionBarOverlayLayout2.setHasNonEmbeddedTabs(z3);
    }

    private void l(boolean z2) {
        if (a(this.x, this.y, this.z)) {
            if (!this.A) {
                this.A = true;
                g(z2);
            }
        } else if (this.A) {
            this.A = false;
            f(z2);
        }
    }

    private void n() {
        if (this.z) {
            this.z = false;
            ActionBarOverlayLayout actionBarOverlayLayout = this.g;
            if (actionBarOverlayLayout != null) {
                actionBarOverlayLayout.setShowingForActionMode(false);
            }
            l(false);
        }
    }

    private boolean o() {
        return t.w(this.h);
    }

    private void p() {
        if (!this.z) {
            this.z = true;
            ActionBarOverlayLayout actionBarOverlayLayout = this.g;
            if (actionBarOverlayLayout != null) {
                actionBarOverlayLayout.setShowingForActionMode(true);
            }
            l(false);
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public b a(b.a aVar) {
        a aVar2 = this.p;
        if (aVar2 != null) {
            aVar2.a();
        }
        this.g.setHideOnContentScrollEnabled(false);
        this.j.c();
        a aVar3 = new a(this.j.getContext(), aVar);
        if (!aVar3.k()) {
            return null;
        }
        this.p = aVar3;
        aVar3.i();
        this.j.a(aVar3);
        e(true);
        this.j.sendAccessibilityEvent(32);
        return aVar3;
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.a
    public void a() {
        if (this.y) {
            this.y = false;
            l(true);
        }
    }

    public void a(float f2) {
        t.a(this.h, f2);
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.a
    public void a(int i2) {
        this.v = i2;
    }

    public void a(int i2, int i3) {
        int k2 = this.i.k();
        if ((i3 & 4) != 0) {
            this.o = true;
        }
        this.i.a((i2 & i3) | ((i3 ^ -1) & k2));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void a(Configuration configuration) {
        k(b.a.d.a.a(this.f160c).f());
    }

    @Override // androidx.appcompat.app.ActionBar
    public void a(CharSequence charSequence) {
        this.i.setWindowTitle(charSequence);
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.a
    public void a(boolean z2) {
        this.w = z2;
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean a(int i2, KeyEvent keyEvent) {
        Menu c2;
        a aVar = this.p;
        if (aVar == null || (c2 = aVar.c()) == null) {
            return false;
        }
        boolean z2 = true;
        if (KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() == 1) {
            z2 = false;
        }
        c2.setQwertyMode(z2);
        return c2.performShortcut(i2, keyEvent, 0);
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.a
    public void b() {
    }

    @Override // androidx.appcompat.app.ActionBar
    public void b(boolean z2) {
        if (z2 != this.s) {
            this.s = z2;
            int size = this.t.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.t.get(i2).onMenuVisibilityChanged(z2);
            }
        }
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.a
    public void c() {
        if (!this.y) {
            this.y = true;
            l(true);
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void c(boolean z2) {
        if (!this.o) {
            h(z2);
        }
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.a
    public void d() {
        i iVar = this.B;
        if (iVar != null) {
            iVar.a();
            this.B = null;
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void d(boolean z2) {
        i iVar;
        this.C = z2;
        if (!z2 && (iVar = this.B) != null) {
            iVar.a();
        }
    }

    public void e(boolean z2) {
        z zVar;
        z zVar2;
        if (z2) {
            p();
        } else {
            n();
        }
        if (o()) {
            if (z2) {
                zVar = this.i.a(4, 100);
                zVar2 = this.j.a(0, 200);
            } else {
                zVar2 = this.i.a(0, 200);
                zVar = this.j.a(8, 100);
            }
            i iVar = new i();
            iVar.a(zVar, zVar2);
            iVar.c();
        } else if (z2) {
            this.i.c(4);
            this.j.setVisibility(0);
        } else {
            this.i.c(0);
            this.j.setVisibility(8);
        }
    }

    public void f(boolean z2) {
        View view;
        i iVar = this.B;
        if (iVar != null) {
            iVar.a();
        }
        if (this.v != 0 || (!this.C && !z2)) {
            this.E.b(null);
            return;
        }
        this.h.setAlpha(1.0f);
        this.h.setTransitioning(true);
        i iVar2 = new i();
        float f2 = (float) (-this.h.getHeight());
        if (z2) {
            int[] iArr = {0, 0};
            this.h.getLocationInWindow(iArr);
            f2 -= (float) iArr[1];
        }
        z a2 = t.a(this.h);
        a2.b(f2);
        a2.a(this.G);
        iVar2.a(a2);
        if (this.w && (view = this.k) != null) {
            z a3 = t.a(view);
            a3.b(f2);
            iVar2.a(a3);
        }
        iVar2.a(f158a);
        iVar2.a(250);
        iVar2.a(this.E);
        this.B = iVar2;
        iVar2.c();
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean f() {
        D d2 = this.i;
        if (d2 == null || !d2.g()) {
            return false;
        }
        this.i.collapseActionView();
        return true;
    }

    @Override // androidx.appcompat.app.ActionBar
    public int g() {
        return this.i.k();
    }

    public void g(boolean z2) {
        View view;
        View view2;
        i iVar = this.B;
        if (iVar != null) {
            iVar.a();
        }
        this.h.setVisibility(0);
        if (this.v != 0 || (!this.C && !z2)) {
            this.h.setAlpha(1.0f);
            this.h.setTranslationY(0.0f);
            if (this.w && (view = this.k) != null) {
                view.setTranslationY(0.0f);
            }
            this.F.b(null);
        } else {
            this.h.setTranslationY(0.0f);
            float f2 = (float) (-this.h.getHeight());
            if (z2) {
                int[] iArr = {0, 0};
                this.h.getLocationInWindow(iArr);
                f2 -= (float) iArr[1];
            }
            this.h.setTranslationY(f2);
            i iVar2 = new i();
            z a2 = t.a(this.h);
            a2.b(0.0f);
            a2.a(this.G);
            iVar2.a(a2);
            if (this.w && (view2 = this.k) != null) {
                view2.setTranslationY(f2);
                z a3 = t.a(this.k);
                a3.b(0.0f);
                iVar2.a(a3);
            }
            iVar2.a(f159b);
            iVar2.a(250);
            iVar2.a(this.F);
            this.B = iVar2;
            iVar2.c();
        }
        ActionBarOverlayLayout actionBarOverlayLayout = this.g;
        if (actionBarOverlayLayout != null) {
            t.z(actionBarOverlayLayout);
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public Context h() {
        if (this.f161d == null) {
            TypedValue typedValue = new TypedValue();
            this.f160c.getTheme().resolveAttribute(b.a.a.actionBarWidgetTheme, typedValue, true);
            int i2 = typedValue.resourceId;
            if (i2 != 0) {
                this.f161d = new ContextThemeWrapper(this.f160c, i2);
            } else {
                this.f161d = this.f160c;
            }
        }
        return this.f161d;
    }

    public void h(boolean z2) {
        a(z2 ? 4 : 0, 4);
    }

    public void i(boolean z2) {
        if (!z2 || this.g.h()) {
            this.D = z2;
            this.g.setHideOnContentScrollEnabled(z2);
            return;
        }
        throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
    }

    public void j(boolean z2) {
        this.i.a(z2);
    }

    /* access modifiers changed from: package-private */
    public void l() {
        b.a aVar = this.r;
        if (aVar != null) {
            aVar.a(this.q);
            this.q = null;
            this.r = null;
        }
    }

    public int m() {
        return this.i.h();
    }
}
