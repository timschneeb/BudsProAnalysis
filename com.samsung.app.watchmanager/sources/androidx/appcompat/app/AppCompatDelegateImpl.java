package androidx.appcompat.app;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.view.menu.j;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.view.menu.v;
import androidx.appcompat.view.menu.w;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.C;
import androidx.appcompat.widget.C0072q;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.G;
import androidx.appcompat.widget.ia;
import androidx.appcompat.widget.va;
import androidx.appcompat.widget.wa;
import b.a.d.b;
import b.a.d.f;
import b.a.g;
import b.a.i;
import b.e.g.C0114d;
import b.e.g.t;
import b.e.g.z;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

/* access modifiers changed from: package-private */
public class AppCompatDelegateImpl extends m implements l.a, LayoutInflater.Factory2 {

    /* renamed from: b  reason: collision with root package name */
    private static final boolean f116b = (Build.VERSION.SDK_INT < 21);

    /* renamed from: c  reason: collision with root package name */
    private static final int[] f117c = {16842836};

    /* renamed from: d  reason: collision with root package name */
    private static boolean f118d = true;
    private boolean A;
    boolean B;
    boolean C;
    boolean D;
    boolean E;
    boolean F;
    private boolean G;
    private PanelFeatureState[] H;
    private PanelFeatureState I;
    private boolean J;
    boolean K;
    private int L = -100;
    private boolean M;
    private d N;
    boolean O;
    int P;
    private final Runnable Q = new o(this);
    private boolean R;
    private Rect S;
    private Rect T;
    private AppCompatViewInflater U;
    final Context e;
    final Window f;
    final Window.Callback g;
    final Window.Callback h;
    final l i;
    ActionBar j;
    MenuInflater k;
    private CharSequence l;
    private C m;
    private a n;
    private f o;
    b.a.d.b p;
    ActionBarContextView q;
    PopupWindow r;
    Runnable s;
    z t = null;
    private boolean u = true;
    private boolean v;
    private ViewGroup w;
    private TextView x;
    private View y;
    private boolean z;

    /* access modifiers changed from: protected */
    public static final class PanelFeatureState {

        /* renamed from: a  reason: collision with root package name */
        int f119a;

        /* renamed from: b  reason: collision with root package name */
        int f120b;

        /* renamed from: c  reason: collision with root package name */
        int f121c;

        /* renamed from: d  reason: collision with root package name */
        int f122d;
        int e;
        int f;
        ViewGroup g;
        View h;
        View i;
        l j;
        j k;
        Context l;
        boolean m;
        boolean n;
        boolean o;
        public boolean p;
        boolean q = false;
        boolean r;
        Bundle s;

        /* access modifiers changed from: private */
        public static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new x();

            /* renamed from: a  reason: collision with root package name */
            int f123a;

            /* renamed from: b  reason: collision with root package name */
            boolean f124b;

            /* renamed from: c  reason: collision with root package name */
            Bundle f125c;

            SavedState() {
            }

            static SavedState a(Parcel parcel, ClassLoader classLoader) {
                SavedState savedState = new SavedState();
                savedState.f123a = parcel.readInt();
                boolean z = true;
                if (parcel.readInt() != 1) {
                    z = false;
                }
                savedState.f124b = z;
                if (savedState.f124b) {
                    savedState.f125c = parcel.readBundle(classLoader);
                }
                return savedState;
            }

            public int describeContents() {
                return 0;
            }

            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.f123a);
                parcel.writeInt(this.f124b ? 1 : 0);
                if (this.f124b) {
                    parcel.writeBundle(this.f125c);
                }
            }
        }

        PanelFeatureState(int i2) {
            this.f119a = i2;
        }

        /* access modifiers changed from: package-private */
        public w a(v.a aVar) {
            if (this.j == null) {
                return null;
            }
            if (this.k == null) {
                this.k = new j(this.l, g.abc_list_menu_item_layout);
                this.k.a(aVar);
                this.j.a(this.k);
            }
            return this.k.a(this.g);
        }

        /* access modifiers changed from: package-private */
        public void a(Context context) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme newTheme = context.getResources().newTheme();
            newTheme.setTo(context.getTheme());
            newTheme.resolveAttribute(b.a.a.actionBarPopupTheme, typedValue, true);
            int i2 = typedValue.resourceId;
            if (i2 != 0) {
                newTheme.applyStyle(i2, true);
            }
            newTheme.resolveAttribute(b.a.a.panelMenuListTheme, typedValue, true);
            int i3 = typedValue.resourceId;
            if (i3 == 0) {
                i3 = i.Theme_AppCompat_CompactMenu;
            }
            newTheme.applyStyle(i3, true);
            b.a.d.d dVar = new b.a.d.d(context, 0);
            dVar.getTheme().setTo(newTheme);
            this.l = dVar;
            TypedArray obtainStyledAttributes = dVar.obtainStyledAttributes(b.a.j.AppCompatTheme);
            this.f120b = obtainStyledAttributes.getResourceId(b.a.j.AppCompatTheme_panelBackground, 0);
            this.f = obtainStyledAttributes.getResourceId(b.a.j.AppCompatTheme_android_windowAnimationStyle, 0);
            obtainStyledAttributes.recycle();
        }

        /* access modifiers changed from: package-private */
        public void a(l lVar) {
            j jVar;
            l lVar2 = this.j;
            if (lVar != lVar2) {
                if (lVar2 != null) {
                    lVar2.b(this.k);
                }
                this.j = lVar;
                if (lVar != null && (jVar = this.k) != null) {
                    lVar.a(jVar);
                }
            }
        }

        public boolean a() {
            if (this.h == null) {
                return false;
            }
            return this.i != null || this.k.b().getCount() > 0;
        }
    }

    /* access modifiers changed from: private */
    public final class a implements v.a {
        a() {
        }

        @Override // androidx.appcompat.view.menu.v.a
        public void a(l lVar, boolean z) {
            AppCompatDelegateImpl.this.b(lVar);
        }

        @Override // androidx.appcompat.view.menu.v.a
        public boolean a(l lVar) {
            Window.Callback o = AppCompatDelegateImpl.this.o();
            if (o == null) {
                return true;
            }
            o.onMenuOpened(108, lVar);
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public class b implements b.a {

        /* renamed from: a  reason: collision with root package name */
        private b.a f127a;

        public b(b.a aVar) {
            this.f127a = aVar;
        }

        @Override // b.a.d.b.a
        public void a(b.a.d.b bVar) {
            this.f127a.a(bVar);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl.r != null) {
                appCompatDelegateImpl.f.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.s);
            }
            AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl2.q != null) {
                appCompatDelegateImpl2.l();
                AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                z a2 = t.a(appCompatDelegateImpl3.q);
                a2.a(0.0f);
                appCompatDelegateImpl3.t = a2;
                AppCompatDelegateImpl.this.t.a(new v(this));
            }
            AppCompatDelegateImpl appCompatDelegateImpl4 = AppCompatDelegateImpl.this;
            l lVar = appCompatDelegateImpl4.i;
            if (lVar != null) {
                lVar.b(appCompatDelegateImpl4.p);
            }
            AppCompatDelegateImpl.this.p = null;
        }

        @Override // b.a.d.b.a
        public boolean a(b.a.d.b bVar, Menu menu) {
            return this.f127a.a(bVar, menu);
        }

        @Override // b.a.d.b.a
        public boolean a(b.a.d.b bVar, MenuItem menuItem) {
            return this.f127a.a(bVar, menuItem);
        }

        @Override // b.a.d.b.a
        public boolean b(b.a.d.b bVar, Menu menu) {
            return this.f127a.b(bVar, menu);
        }
    }

    class c extends b.a.d.j {
        c(Window.Callback callback) {
            super(callback);
        }

        /* access modifiers changed from: package-private */
        public final ActionMode a(ActionMode.Callback callback) {
            f.a aVar = new f.a(AppCompatDelegateImpl.this.e, callback);
            b.a.d.b a2 = AppCompatDelegateImpl.this.a(aVar);
            if (a2 != null) {
                return aVar.b(a2);
            }
            return null;
        }

        @Override // b.a.d.j
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // b.a.d.j
        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return super.dispatchKeyShortcutEvent(keyEvent) || AppCompatDelegateImpl.this.b(keyEvent.getKeyCode(), keyEvent);
        }

        public void onContentChanged() {
        }

        @Override // b.a.d.j
        public boolean onCreatePanelMenu(int i, Menu menu) {
            if (i != 0 || (menu instanceof l)) {
                return super.onCreatePanelMenu(i, menu);
            }
            return false;
        }

        @Override // b.a.d.j
        public boolean onMenuOpened(int i, Menu menu) {
            super.onMenuOpened(i, menu);
            AppCompatDelegateImpl.this.g(i);
            return true;
        }

        @Override // b.a.d.j
        public void onPanelClosed(int i, Menu menu) {
            super.onPanelClosed(i, menu);
            AppCompatDelegateImpl.this.h(i);
        }

        @Override // b.a.d.j
        public boolean onPreparePanel(int i, View view, Menu menu) {
            l lVar = menu instanceof l ? (l) menu : null;
            if (i == 0 && lVar == null) {
                return false;
            }
            if (lVar != null) {
                lVar.c(true);
            }
            boolean onPreparePanel = super.onPreparePanel(i, view, menu);
            if (lVar != null) {
                lVar.c(false);
            }
            return onPreparePanel;
        }

        @Override // b.a.d.j, android.view.Window.Callback
        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
            l lVar;
            PanelFeatureState a2 = AppCompatDelegateImpl.this.a(0, true);
            if (a2 == null || (lVar = a2.j) == null) {
                super.onProvideKeyboardShortcuts(list, menu, i);
            } else {
                super.onProvideKeyboardShortcuts(list, lVar, i);
            }
        }

        @Override // b.a.d.j
        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            if (Build.VERSION.SDK_INT >= 23) {
                return null;
            }
            return AppCompatDelegateImpl.this.p() ? a(callback) : super.onWindowStartingActionMode(callback);
        }

        @Override // b.a.d.j
        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
            return (!AppCompatDelegateImpl.this.p() || i != 0) ? super.onWindowStartingActionMode(callback, i) : a(callback);
        }
    }

    /* access modifiers changed from: package-private */
    public final class d {

        /* renamed from: a  reason: collision with root package name */
        private C f130a;

        /* renamed from: b  reason: collision with root package name */
        private boolean f131b;

        /* renamed from: c  reason: collision with root package name */
        private BroadcastReceiver f132c;

        /* renamed from: d  reason: collision with root package name */
        private IntentFilter f133d;

        d(C c2) {
            this.f130a = c2;
            this.f131b = c2.a();
        }

        /* access modifiers changed from: package-private */
        public void a() {
            BroadcastReceiver broadcastReceiver = this.f132c;
            if (broadcastReceiver != null) {
                AppCompatDelegateImpl.this.e.unregisterReceiver(broadcastReceiver);
                this.f132c = null;
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            boolean a2 = this.f130a.a();
            if (a2 != this.f131b) {
                this.f131b = a2;
                AppCompatDelegateImpl.this.a();
            }
        }

        /* access modifiers changed from: package-private */
        public int c() {
            this.f131b = this.f130a.a();
            return this.f131b ? 2 : 1;
        }

        /* access modifiers changed from: package-private */
        public void d() {
            a();
            if (this.f132c == null) {
                this.f132c = new w(this);
            }
            if (this.f133d == null) {
                this.f133d = new IntentFilter();
                this.f133d.addAction("android.intent.action.TIME_SET");
                this.f133d.addAction("android.intent.action.TIMEZONE_CHANGED");
                this.f133d.addAction("android.intent.action.TIME_TICK");
            }
            AppCompatDelegateImpl.this.e.registerReceiver(this.f132c, this.f133d);
        }
    }

    /* access modifiers changed from: private */
    public class e extends ContentFrameLayout {
        public e(Context context) {
            super(context);
        }

        private boolean a(int i2, int i3) {
            return i2 < -5 || i3 < -5 || i2 > getWidth() + 5 || i3 > getHeight() + 5;
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0 || !a((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return super.onInterceptTouchEvent(motionEvent);
            }
            AppCompatDelegateImpl.this.d(0);
            return true;
        }

        public void setBackgroundResource(int i2) {
            setBackgroundDrawable(b.a.a.a.a.b(getContext(), i2));
        }
    }

    /* access modifiers changed from: private */
    public final class f implements v.a {
        f() {
        }

        @Override // androidx.appcompat.view.menu.v.a
        public void a(l lVar, boolean z) {
            l m = lVar.m();
            boolean z2 = m != lVar;
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (z2) {
                lVar = m;
            }
            PanelFeatureState a2 = appCompatDelegateImpl.a((Menu) lVar);
            if (a2 == null) {
                return;
            }
            if (z2) {
                AppCompatDelegateImpl.this.a(a2.f119a, a2, m);
                AppCompatDelegateImpl.this.a(a2, true);
                return;
            }
            AppCompatDelegateImpl.this.a(a2, z);
        }

        @Override // androidx.appcompat.view.menu.v.a
        public boolean a(l lVar) {
            Window.Callback o;
            if (lVar != null) {
                return true;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (!appCompatDelegateImpl.B || (o = appCompatDelegateImpl.o()) == null || AppCompatDelegateImpl.this.K) {
                return true;
            }
            o.onMenuOpened(108, lVar);
            return true;
        }
    }

    static {
        if (f116b && !f118d) {
            Thread.setDefaultUncaughtExceptionHandler(new n(Thread.getDefaultUncaughtExceptionHandler()));
        }
    }

    AppCompatDelegateImpl(Context context, Window window, l lVar) {
        this.e = context;
        this.f = window;
        this.i = lVar;
        this.g = this.f.getCallback();
        Window.Callback callback = this.g;
        if (!(callback instanceof c)) {
            this.h = new c(callback);
            this.f.setCallback(this.h);
            ia a2 = ia.a(context, (AttributeSet) null, f117c);
            Drawable c2 = a2.c(0);
            if (c2 != null) {
                this.f.setBackgroundDrawable(c2);
            }
            a2.a();
            return;
        }
        throw new IllegalStateException("AppCompat has already installed itself into the Window");
    }

    private void A() {
        if (this.v) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    private void a(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        int i2;
        ViewGroup.LayoutParams layoutParams;
        if (!panelFeatureState.o && !this.K) {
            if (panelFeatureState.f119a == 0) {
                if ((this.e.getResources().getConfiguration().screenLayout & 15) == 4) {
                    return;
                }
            }
            Window.Callback o2 = o();
            if (o2 == null || o2.onMenuOpened(panelFeatureState.f119a, panelFeatureState.j)) {
                WindowManager windowManager = (WindowManager) this.e.getSystemService("window");
                if (windowManager != null && b(panelFeatureState, keyEvent)) {
                    if (panelFeatureState.g == null || panelFeatureState.q) {
                        ViewGroup viewGroup = panelFeatureState.g;
                        if (viewGroup == null) {
                            if (!b(panelFeatureState) || panelFeatureState.g == null) {
                                return;
                            }
                        } else if (panelFeatureState.q && viewGroup.getChildCount() > 0) {
                            panelFeatureState.g.removeAllViews();
                        }
                        if (a(panelFeatureState) && panelFeatureState.a()) {
                            ViewGroup.LayoutParams layoutParams2 = panelFeatureState.h.getLayoutParams();
                            if (layoutParams2 == null) {
                                layoutParams2 = new ViewGroup.LayoutParams(-2, -2);
                            }
                            panelFeatureState.g.setBackgroundResource(panelFeatureState.f120b);
                            ViewParent parent = panelFeatureState.h.getParent();
                            if (parent != null && (parent instanceof ViewGroup)) {
                                ((ViewGroup) parent).removeView(panelFeatureState.h);
                            }
                            panelFeatureState.g.addView(panelFeatureState.h, layoutParams2);
                            if (!panelFeatureState.h.hasFocus()) {
                                panelFeatureState.h.requestFocus();
                            }
                        } else {
                            return;
                        }
                    } else {
                        View view = panelFeatureState.i;
                        if (!(view == null || (layoutParams = view.getLayoutParams()) == null || layoutParams.width != -1)) {
                            i2 = -1;
                            panelFeatureState.n = false;
                            WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams(i2, -2, panelFeatureState.f122d, panelFeatureState.e, GlobalConst.LAUNCH_MODE_BT_SETTING, 8519680, -3);
                            layoutParams3.gravity = panelFeatureState.f121c;
                            layoutParams3.windowAnimations = panelFeatureState.f;
                            windowManager.addView(panelFeatureState.g, layoutParams3);
                            panelFeatureState.o = true;
                            return;
                        }
                    }
                    i2 = -2;
                    panelFeatureState.n = false;
                    WindowManager.LayoutParams layoutParams32 = new WindowManager.LayoutParams(i2, -2, panelFeatureState.f122d, panelFeatureState.e, GlobalConst.LAUNCH_MODE_BT_SETTING, 8519680, -3);
                    layoutParams32.gravity = panelFeatureState.f121c;
                    layoutParams32.windowAnimations = panelFeatureState.f;
                    windowManager.addView(panelFeatureState.g, layoutParams32);
                    panelFeatureState.o = true;
                    return;
                }
                return;
            }
            a(panelFeatureState, true);
        }
    }

    private void a(l lVar, boolean z2) {
        C c2 = this.m;
        if (c2 == null || !c2.b() || (ViewConfiguration.get(this.e).hasPermanentMenuKey() && !this.m.c())) {
            PanelFeatureState a2 = a(0, true);
            a2.q = true;
            a(a2, false);
            a(a2, (KeyEvent) null);
            return;
        }
        Window.Callback o2 = o();
        if (this.m.a() && z2) {
            this.m.d();
            if (!this.K) {
                o2.onPanelClosed(108, a(0, true).j);
            }
        } else if (o2 != null && !this.K) {
            if (this.O && (this.P & 1) != 0) {
                this.f.getDecorView().removeCallbacks(this.Q);
                this.Q.run();
            }
            PanelFeatureState a3 = a(0, true);
            l lVar2 = a3.j;
            if (lVar2 != null && !a3.r && o2.onPreparePanel(0, a3.i, lVar2)) {
                o2.onMenuOpened(108, a3.j);
                this.m.e();
            }
        }
    }

    private boolean a(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        View decorView = this.f.getDecorView();
        while (viewParent != null) {
            if (viewParent == decorView || !(viewParent instanceof View) || t.v((View) viewParent)) {
                return false;
            }
            viewParent = viewParent.getParent();
        }
        return true;
    }

    private boolean a(PanelFeatureState panelFeatureState) {
        View view = panelFeatureState.i;
        if (view != null) {
            panelFeatureState.h = view;
            return true;
        } else if (panelFeatureState.j == null) {
            return false;
        } else {
            if (this.o == null) {
                this.o = new f();
            }
            panelFeatureState.h = (View) panelFeatureState.a(this.o);
            return panelFeatureState.h != null;
        }
    }

    private boolean a(PanelFeatureState panelFeatureState, int i2, KeyEvent keyEvent, int i3) {
        l lVar;
        boolean z2 = false;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((panelFeatureState.m || b(panelFeatureState, keyEvent)) && (lVar = panelFeatureState.j) != null) {
            z2 = lVar.performShortcut(i2, keyEvent, i3);
        }
        if (z2 && (i3 & 1) == 0 && this.m == null) {
            a(panelFeatureState, true);
        }
        return z2;
    }

    private boolean b(PanelFeatureState panelFeatureState) {
        panelFeatureState.a(m());
        panelFeatureState.g = new e(panelFeatureState.l);
        panelFeatureState.f121c = 81;
        return true;
    }

    private boolean b(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        C c2;
        C c3;
        C c4;
        if (this.K) {
            return false;
        }
        if (panelFeatureState.m) {
            return true;
        }
        PanelFeatureState panelFeatureState2 = this.I;
        if (!(panelFeatureState2 == null || panelFeatureState2 == panelFeatureState)) {
            a(panelFeatureState2, false);
        }
        Window.Callback o2 = o();
        if (o2 != null) {
            panelFeatureState.i = o2.onCreatePanelView(panelFeatureState.f119a);
        }
        int i2 = panelFeatureState.f119a;
        boolean z2 = i2 == 0 || i2 == 108;
        if (z2 && (c4 = this.m) != null) {
            c4.setMenuPrepared();
        }
        if (panelFeatureState.i == null) {
            if (z2) {
                r();
            }
            if (panelFeatureState.j == null || panelFeatureState.r) {
                if (panelFeatureState.j == null && (!c(panelFeatureState) || panelFeatureState.j == null)) {
                    return false;
                }
                if (z2 && this.m != null) {
                    if (this.n == null) {
                        this.n = new a();
                    }
                    this.m.setMenu(panelFeatureState.j, this.n);
                }
                panelFeatureState.j.s();
                if (!o2.onCreatePanelMenu(panelFeatureState.f119a, panelFeatureState.j)) {
                    panelFeatureState.a((l) null);
                    if (z2 && (c3 = this.m) != null) {
                        c3.setMenu(null, this.n);
                    }
                    return false;
                }
                panelFeatureState.r = false;
            }
            panelFeatureState.j.s();
            Bundle bundle = panelFeatureState.s;
            if (bundle != null) {
                panelFeatureState.j.a(bundle);
                panelFeatureState.s = null;
            }
            if (!o2.onPreparePanel(0, panelFeatureState.i, panelFeatureState.j)) {
                if (z2 && (c2 = this.m) != null) {
                    c2.setMenu(null, this.n);
                }
                panelFeatureState.j.r();
                return false;
            }
            panelFeatureState.p = KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1;
            panelFeatureState.j.setQwertyMode(panelFeatureState.p);
            panelFeatureState.j.r();
        }
        panelFeatureState.m = true;
        panelFeatureState.n = false;
        this.I = panelFeatureState;
        return true;
    }

    private boolean c(PanelFeatureState panelFeatureState) {
        Context context = this.e;
        int i2 = panelFeatureState.f119a;
        if ((i2 == 0 || i2 == 108) && this.m != null) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(b.a.a.actionBarTheme, typedValue, true);
            Resources.Theme theme2 = null;
            if (typedValue.resourceId != 0) {
                theme2 = context.getResources().newTheme();
                theme2.setTo(theme);
                theme2.applyStyle(typedValue.resourceId, true);
                theme2.resolveAttribute(b.a.a.actionBarWidgetTheme, typedValue, true);
            } else {
                theme.resolveAttribute(b.a.a.actionBarWidgetTheme, typedValue, true);
            }
            if (typedValue.resourceId != 0) {
                if (theme2 == null) {
                    theme2 = context.getResources().newTheme();
                    theme2.setTo(theme);
                }
                theme2.applyStyle(typedValue.resourceId, true);
            }
            if (theme2 != null) {
                b.a.d.d dVar = new b.a.d.d(context, 0);
                dVar.getTheme().setTo(theme2);
                context = dVar;
            }
        }
        l lVar = new l(context);
        lVar.a(this);
        panelFeatureState.a(lVar);
        return true;
    }

    private boolean d(int i2, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() != 0) {
            return false;
        }
        PanelFeatureState a2 = a(i2, true);
        if (!a2.o) {
            return b(a2, keyEvent);
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x006c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean e(int r4, android.view.KeyEvent r5) {
        /*
        // Method dump skipped, instructions count: 132
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.e(int, android.view.KeyEvent):boolean");
    }

    private void j(int i2) {
        this.P = (1 << i2) | this.P;
        if (!this.O) {
            t.a(this.f.getDecorView(), this.Q);
            this.O = true;
        }
    }

    private int k(int i2) {
        if (i2 == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        } else if (i2 != 9) {
            return i2;
        } else {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            return 109;
        }
    }

    private boolean l(int i2) {
        Resources resources = this.e.getResources();
        Configuration configuration = resources.getConfiguration();
        int i3 = configuration.uiMode & 48;
        int i4 = i2 == 2 ? 32 : 16;
        if (i3 == i4) {
            return false;
        }
        if (z()) {
            ((Activity) this.e).recreate();
            return true;
        }
        Configuration configuration2 = new Configuration(configuration);
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration2.uiMode = i4 | (configuration2.uiMode & -49);
        resources.updateConfiguration(configuration2, displayMetrics);
        if (Build.VERSION.SDK_INT >= 26) {
            return true;
        }
        A.a(resources);
        return true;
    }

    private void t() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) this.w.findViewById(16908290);
        View decorView = this.f.getDecorView();
        contentFrameLayout.setDecorPadding(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        TypedArray obtainStyledAttributes = this.e.obtainStyledAttributes(b.a.j.AppCompatTheme);
        obtainStyledAttributes.getValue(b.a.j.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
        obtainStyledAttributes.getValue(b.a.j.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
        if (obtainStyledAttributes.hasValue(b.a.j.AppCompatTheme_windowFixedWidthMajor)) {
            obtainStyledAttributes.getValue(b.a.j.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor());
        }
        if (obtainStyledAttributes.hasValue(b.a.j.AppCompatTheme_windowFixedWidthMinor)) {
            obtainStyledAttributes.getValue(b.a.j.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor());
        }
        if (obtainStyledAttributes.hasValue(b.a.j.AppCompatTheme_windowFixedHeightMajor)) {
            obtainStyledAttributes.getValue(b.a.j.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor());
        }
        if (obtainStyledAttributes.hasValue(b.a.j.AppCompatTheme_windowFixedHeightMinor)) {
            obtainStyledAttributes.getValue(b.a.j.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor());
        }
        obtainStyledAttributes.recycle();
        contentFrameLayout.requestLayout();
    }

    private ViewGroup u() {
        ViewGroup viewGroup;
        TypedArray obtainStyledAttributes = this.e.obtainStyledAttributes(b.a.j.AppCompatTheme);
        if (obtainStyledAttributes.hasValue(b.a.j.AppCompatTheme_windowActionBar)) {
            if (obtainStyledAttributes.getBoolean(b.a.j.AppCompatTheme_windowNoTitle, false)) {
                b(1);
            } else if (obtainStyledAttributes.getBoolean(b.a.j.AppCompatTheme_windowActionBar, false)) {
                b(108);
            }
            if (obtainStyledAttributes.getBoolean(b.a.j.AppCompatTheme_windowActionBarOverlay, false)) {
                b(109);
            }
            if (obtainStyledAttributes.getBoolean(b.a.j.AppCompatTheme_windowActionModeOverlay, false)) {
                b(10);
            }
            this.E = obtainStyledAttributes.getBoolean(b.a.j.AppCompatTheme_android_windowIsFloating, false);
            obtainStyledAttributes.recycle();
            this.f.getDecorView();
            LayoutInflater from = LayoutInflater.from(this.e);
            if (this.F) {
                viewGroup = (ViewGroup) from.inflate(this.D ? g.abc_screen_simple_overlay_action_mode : g.abc_screen_simple, (ViewGroup) null);
                if (Build.VERSION.SDK_INT >= 21) {
                    t.a(viewGroup, new p(this));
                } else {
                    ((G) viewGroup).setOnFitSystemWindowsListener(new q(this));
                }
            } else if (this.E) {
                viewGroup = (ViewGroup) from.inflate(g.abc_dialog_title_material, (ViewGroup) null);
                this.C = false;
                this.B = false;
            } else if (this.B) {
                TypedValue typedValue = new TypedValue();
                this.e.getTheme().resolveAttribute(b.a.a.actionBarTheme, typedValue, true);
                int i2 = typedValue.resourceId;
                viewGroup = (ViewGroup) LayoutInflater.from(i2 != 0 ? new b.a.d.d(this.e, i2) : this.e).inflate(g.abc_screen_toolbar, (ViewGroup) null);
                this.m = (C) viewGroup.findViewById(b.a.f.decor_content_parent);
                this.m.setWindowCallback(o());
                if (this.C) {
                    this.m.a(109);
                }
                if (this.z) {
                    this.m.a(2);
                }
                if (this.A) {
                    this.m.a(5);
                }
            } else {
                viewGroup = null;
            }
            if (viewGroup != null) {
                if (this.m == null) {
                    this.x = (TextView) viewGroup.findViewById(b.a.f.title);
                }
                wa.b(viewGroup);
                ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(b.a.f.action_bar_activity_content);
                ViewGroup viewGroup2 = (ViewGroup) this.f.findViewById(16908290);
                if (viewGroup2 != null) {
                    while (viewGroup2.getChildCount() > 0) {
                        View childAt = viewGroup2.getChildAt(0);
                        viewGroup2.removeViewAt(0);
                        contentFrameLayout.addView(childAt);
                    }
                    viewGroup2.setId(-1);
                    contentFrameLayout.setId(16908290);
                    if (viewGroup2 instanceof FrameLayout) {
                        ((FrameLayout) viewGroup2).setForeground(null);
                    }
                }
                this.f.setContentView(viewGroup);
                contentFrameLayout.setAttachListener(new r(this));
                return viewGroup;
            }
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.B + ", windowActionBarOverlay: " + this.C + ", android:windowIsFloating: " + this.E + ", windowActionModeOverlay: " + this.D + ", windowNoTitle: " + this.F + " }");
        }
        obtainStyledAttributes.recycle();
        throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
    }

    private void v() {
        if (this.N == null) {
            this.N = new d(C.a(this.e));
        }
    }

    private void w() {
        if (!this.v) {
            this.w = u();
            CharSequence n2 = n();
            if (!TextUtils.isEmpty(n2)) {
                C c2 = this.m;
                if (c2 != null) {
                    c2.setWindowTitle(n2);
                } else if (r() != null) {
                    r().a(n2);
                } else {
                    TextView textView = this.x;
                    if (textView != null) {
                        textView.setText(n2);
                    }
                }
            }
            t();
            a(this.w);
            this.v = true;
            PanelFeatureState a2 = a(0, false);
            if (this.K) {
                return;
            }
            if (a2 == null || a2.j == null) {
                j(108);
            }
        }
    }

    private int x() {
        int i2 = this.L;
        return i2 != -100 ? i2 : m.b();
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void y() {
        /*
            r3 = this;
            r3.w()
            boolean r0 = r3.B
            if (r0 == 0) goto L_0x0033
            androidx.appcompat.app.ActionBar r0 = r3.j
            if (r0 == 0) goto L_0x000c
            goto L_0x0033
        L_0x000c:
            android.view.Window$Callback r0 = r3.g
            boolean r1 = r0 instanceof android.app.Activity
            if (r1 == 0) goto L_0x001e
            androidx.appcompat.app.G r1 = new androidx.appcompat.app.G
            android.app.Activity r0 = (android.app.Activity) r0
            boolean r2 = r3.C
            r1.<init>(r0, r2)
        L_0x001b:
            r3.j = r1
            goto L_0x002a
        L_0x001e:
            boolean r1 = r0 instanceof android.app.Dialog
            if (r1 == 0) goto L_0x002a
            androidx.appcompat.app.G r1 = new androidx.appcompat.app.G
            android.app.Dialog r0 = (android.app.Dialog) r0
            r1.<init>(r0)
            goto L_0x001b
        L_0x002a:
            androidx.appcompat.app.ActionBar r0 = r3.j
            if (r0 == 0) goto L_0x0033
            boolean r1 = r3.R
            r0.c(r1)
        L_0x0033:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.y():void");
    }

    private boolean z() {
        if (this.M) {
            Context context = this.e;
            if (context instanceof Activity) {
                try {
                    return (context.getPackageManager().getActivityInfo(new ComponentName(this.e, this.e.getClass()), 0).configChanges & 512) == 0;
                } catch (PackageManager.NameNotFoundException e2) {
                    Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e2);
                    return true;
                }
            }
        }
        return false;
    }

    @Override // androidx.appcompat.app.m
    public <T extends View> T a(int i2) {
        w();
        return (T) this.f.findViewById(i2);
    }

    public View a(View view, String str, Context context, AttributeSet attributeSet) {
        boolean z2;
        AppCompatViewInflater appCompatViewInflater;
        boolean z3 = false;
        if (this.U == null) {
            String string = this.e.obtainStyledAttributes(b.a.j.AppCompatTheme).getString(b.a.j.AppCompatTheme_viewInflaterClass);
            if (string == null || AppCompatViewInflater.class.getName().equals(string)) {
                appCompatViewInflater = new AppCompatViewInflater();
            } else {
                try {
                    this.U = (AppCompatViewInflater) Class.forName(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (Throwable th) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + string + ". Falling back to default.", th);
                    appCompatViewInflater = new AppCompatViewInflater();
                }
            }
            this.U = appCompatViewInflater;
        }
        if (f116b) {
            if (!(attributeSet instanceof XmlPullParser)) {
                z3 = a((ViewParent) view);
            } else if (((XmlPullParser) attributeSet).getDepth() > 1) {
                z3 = true;
            }
            z2 = z3;
        } else {
            z2 = false;
        }
        return this.U.a(view, str, context, attributeSet, z2, f116b, true, va.b());
    }

    /* access modifiers changed from: protected */
    public PanelFeatureState a(int i2, boolean z2) {
        PanelFeatureState[] panelFeatureStateArr = this.H;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i2) {
            PanelFeatureState[] panelFeatureStateArr2 = new PanelFeatureState[(i2 + 1)];
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.H = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArr[i2];
        if (panelFeatureState != null) {
            return panelFeatureState;
        }
        PanelFeatureState panelFeatureState2 = new PanelFeatureState(i2);
        panelFeatureStateArr[i2] = panelFeatureState2;
        return panelFeatureState2;
    }

    /* access modifiers changed from: package-private */
    public PanelFeatureState a(Menu menu) {
        PanelFeatureState[] panelFeatureStateArr = this.H;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i2 = 0; i2 < length; i2++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i2];
            if (panelFeatureState != null && panelFeatureState.j == menu) {
                return panelFeatureState;
            }
        }
        return null;
    }

    public b.a.d.b a(b.a aVar) {
        l lVar;
        if (aVar != null) {
            b.a.d.b bVar = this.p;
            if (bVar != null) {
                bVar.a();
            }
            b bVar2 = new b(aVar);
            ActionBar d2 = d();
            if (d2 != null) {
                this.p = d2.a(bVar2);
                b.a.d.b bVar3 = this.p;
                if (!(bVar3 == null || (lVar = this.i) == null)) {
                    lVar.a(bVar3);
                }
            }
            if (this.p == null) {
                this.p = b(bVar2);
            }
            return this.p;
        }
        throw new IllegalArgumentException("ActionMode callback can not be null.");
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, PanelFeatureState panelFeatureState, Menu menu) {
        if (menu == null) {
            if (panelFeatureState == null && i2 >= 0) {
                PanelFeatureState[] panelFeatureStateArr = this.H;
                if (i2 < panelFeatureStateArr.length) {
                    panelFeatureState = panelFeatureStateArr[i2];
                }
            }
            if (panelFeatureState != null) {
                menu = panelFeatureState.j;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.o) && !this.K) {
            this.g.onPanelClosed(i2, menu);
        }
    }

    @Override // androidx.appcompat.app.m
    public void a(Configuration configuration) {
        ActionBar d2;
        if (this.B && this.v && (d2 = d()) != null) {
            d2.a(configuration);
        }
        C0072q.a().a(this.e);
        a();
    }

    @Override // androidx.appcompat.app.m
    public void a(Bundle bundle) {
        Window.Callback callback = this.g;
        if (callback instanceof Activity) {
            String str = null;
            try {
                str = androidx.core.app.d.b((Activity) callback);
            } catch (IllegalArgumentException unused) {
            }
            if (str != null) {
                ActionBar r2 = r();
                if (r2 == null) {
                    this.R = true;
                } else {
                    r2.c(true);
                }
            }
        }
        if (bundle != null && this.L == -100) {
            this.L = bundle.getInt("appcompat:local_night_mode", -100);
        }
    }

    @Override // androidx.appcompat.app.m
    public void a(View view) {
        w();
        ViewGroup viewGroup = (ViewGroup) this.w.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.g.onContentChanged();
    }

    @Override // androidx.appcompat.app.m
    public void a(View view, ViewGroup.LayoutParams layoutParams) {
        w();
        ((ViewGroup) this.w.findViewById(16908290)).addView(view, layoutParams);
        this.g.onContentChanged();
    }

    /* access modifiers changed from: package-private */
    public void a(ViewGroup viewGroup) {
    }

    /* access modifiers changed from: package-private */
    public void a(PanelFeatureState panelFeatureState, boolean z2) {
        ViewGroup viewGroup;
        C c2;
        if (!z2 || panelFeatureState.f119a != 0 || (c2 = this.m) == null || !c2.a()) {
            WindowManager windowManager = (WindowManager) this.e.getSystemService("window");
            if (!(windowManager == null || !panelFeatureState.o || (viewGroup = panelFeatureState.g) == null)) {
                windowManager.removeView(viewGroup);
                if (z2) {
                    a(panelFeatureState.f119a, panelFeatureState, null);
                }
            }
            panelFeatureState.m = false;
            panelFeatureState.n = false;
            panelFeatureState.o = false;
            panelFeatureState.h = null;
            panelFeatureState.q = true;
            if (this.I == panelFeatureState) {
                this.I = null;
                return;
            }
            return;
        }
        b(panelFeatureState.j);
    }

    @Override // androidx.appcompat.view.menu.l.a
    public void a(l lVar) {
        a(lVar, true);
    }

    @Override // androidx.appcompat.app.m
    public final void a(CharSequence charSequence) {
        this.l = charSequence;
        C c2 = this.m;
        if (c2 != null) {
            c2.setWindowTitle(charSequence);
        } else if (r() != null) {
            r().a(charSequence);
        } else {
            TextView textView = this.x;
            if (textView != null) {
                textView.setText(charSequence);
            }
        }
    }

    @Override // androidx.appcompat.app.m
    public boolean a() {
        int x2 = x();
        int f2 = f(x2);
        boolean l2 = f2 != -1 ? l(f2) : false;
        if (x2 == 0) {
            v();
            this.N.d();
        }
        this.M = true;
        return l2;
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i2, KeyEvent keyEvent) {
        boolean z2 = true;
        if (i2 == 4) {
            if ((keyEvent.getFlags() & 128) == 0) {
                z2 = false;
            }
            this.J = z2;
        } else if (i2 == 82) {
            d(0, keyEvent);
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean a(KeyEvent keyEvent) {
        View decorView;
        Window.Callback callback = this.g;
        boolean z2 = true;
        if (((callback instanceof C0114d.a) || (callback instanceof z)) && (decorView = this.f.getDecorView()) != null && C0114d.a(decorView, keyEvent)) {
            return true;
        }
        if (keyEvent.getKeyCode() == 82 && this.g.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyEvent.getAction() != 0) {
            z2 = false;
        }
        return z2 ? a(keyCode, keyEvent) : c(keyCode, keyEvent);
    }

    @Override // androidx.appcompat.view.menu.l.a
    public boolean a(l lVar, MenuItem menuItem) {
        PanelFeatureState a2;
        Window.Callback o2 = o();
        if (o2 == null || this.K || (a2 = a((Menu) lVar.m())) == null) {
            return false;
        }
        return o2.onMenuItemSelected(a2.f119a, menuItem);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public b.a.d.b b(b.a.d.b.a r8) {
        /*
        // Method dump skipped, instructions count: 371
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.b(b.a.d.b$a):b.a.d.b");
    }

    @Override // androidx.appcompat.app.m
    public void b(Bundle bundle) {
        w();
    }

    @Override // androidx.appcompat.app.m
    public void b(View view, ViewGroup.LayoutParams layoutParams) {
        w();
        ViewGroup viewGroup = (ViewGroup) this.w.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.g.onContentChanged();
    }

    /* access modifiers changed from: package-private */
    public void b(l lVar) {
        if (!this.G) {
            this.G = true;
            this.m.f();
            Window.Callback o2 = o();
            if (o2 != null && !this.K) {
                o2.onPanelClosed(108, lVar);
            }
            this.G = false;
        }
    }

    @Override // androidx.appcompat.app.m
    public boolean b(int i2) {
        int k2 = k(i2);
        if (this.F && k2 == 108) {
            return false;
        }
        if (this.B && k2 == 1) {
            this.B = false;
        }
        if (k2 == 1) {
            A();
            this.F = true;
            return true;
        } else if (k2 == 2) {
            A();
            this.z = true;
            return true;
        } else if (k2 == 5) {
            A();
            this.A = true;
            return true;
        } else if (k2 == 10) {
            A();
            this.D = true;
            return true;
        } else if (k2 == 108) {
            A();
            this.B = true;
            return true;
        } else if (k2 != 109) {
            return this.f.requestFeature(k2);
        } else {
            A();
            this.C = true;
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i2, KeyEvent keyEvent) {
        ActionBar d2 = d();
        if (d2 != null && d2.a(i2, keyEvent)) {
            return true;
        }
        PanelFeatureState panelFeatureState = this.I;
        if (panelFeatureState == null || !a(panelFeatureState, keyEvent.getKeyCode(), keyEvent, 1)) {
            if (this.I == null) {
                PanelFeatureState a2 = a(0, true);
                b(a2, keyEvent);
                boolean a3 = a(a2, keyEvent.getKeyCode(), keyEvent, 1);
                a2.m = false;
                if (a3) {
                    return true;
                }
            }
            return false;
        }
        PanelFeatureState panelFeatureState2 = this.I;
        if (panelFeatureState2 != null) {
            panelFeatureState2.n = true;
        }
        return true;
    }

    @Override // androidx.appcompat.app.m
    public MenuInflater c() {
        if (this.k == null) {
            y();
            ActionBar actionBar = this.j;
            this.k = new b.a.d.g(actionBar != null ? actionBar.h() : this.e);
        }
        return this.k;
    }

    @Override // androidx.appcompat.app.m
    public void c(int i2) {
        w();
        ViewGroup viewGroup = (ViewGroup) this.w.findViewById(16908290);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.e).inflate(i2, viewGroup);
        this.g.onContentChanged();
    }

    @Override // androidx.appcompat.app.m
    public void c(Bundle bundle) {
        int i2 = this.L;
        if (i2 != -100) {
            bundle.putInt("appcompat:local_night_mode", i2);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean c(int i2, KeyEvent keyEvent) {
        if (i2 == 4) {
            boolean z2 = this.J;
            this.J = false;
            PanelFeatureState a2 = a(0, false);
            if (a2 != null && a2.o) {
                if (!z2) {
                    a(a2, true);
                }
                return true;
            } else if (q()) {
                return true;
            }
        } else if (i2 == 82) {
            e(0, keyEvent);
            return true;
        }
        return false;
    }

    @Override // androidx.appcompat.app.m
    public ActionBar d() {
        y();
        return this.j;
    }

    /* access modifiers changed from: package-private */
    public void d(int i2) {
        a(a(i2, true), true);
    }

    @Override // androidx.appcompat.app.m
    public void e() {
        LayoutInflater from = LayoutInflater.from(this.e);
        if (from.getFactory() == null) {
            b.e.g.e.a(from, this);
        } else if (!(from.getFactory2() instanceof AppCompatDelegateImpl)) {
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    /* access modifiers changed from: package-private */
    public void e(int i2) {
        PanelFeatureState a2;
        PanelFeatureState a3 = a(i2, true);
        if (a3.j != null) {
            Bundle bundle = new Bundle();
            a3.j.b(bundle);
            if (bundle.size() > 0) {
                a3.s = bundle;
            }
            a3.j.s();
            a3.j.clear();
        }
        a3.r = true;
        a3.q = true;
        if ((i2 == 108 || i2 == 0) && this.m != null && (a2 = a(0, false)) != null) {
            a2.m = false;
            b(a2, (KeyEvent) null);
        }
    }

    /* access modifiers changed from: package-private */
    public int f(int i2) {
        if (i2 == -100) {
            return -1;
        }
        if (i2 != 0) {
            return i2;
        }
        if (Build.VERSION.SDK_INT >= 23 && ((UiModeManager) this.e.getSystemService(UiModeManager.class)).getNightMode() == 0) {
            return -1;
        }
        v();
        return this.N.c();
    }

    @Override // androidx.appcompat.app.m
    public void f() {
        ActionBar d2 = d();
        if (d2 == null || !d2.i()) {
            j(0);
        }
    }

    @Override // androidx.appcompat.app.m
    public void g() {
        if (this.O) {
            this.f.getDecorView().removeCallbacks(this.Q);
        }
        this.K = true;
        ActionBar actionBar = this.j;
        if (actionBar != null) {
            actionBar.j();
        }
        d dVar = this.N;
        if (dVar != null) {
            dVar.a();
        }
    }

    /* access modifiers changed from: package-private */
    public void g(int i2) {
        ActionBar d2;
        if (i2 == 108 && (d2 = d()) != null) {
            d2.b(true);
        }
    }

    @Override // androidx.appcompat.app.m
    public void h() {
        ActionBar d2 = d();
        if (d2 != null) {
            d2.d(true);
        }
    }

    /* access modifiers changed from: package-private */
    public void h(int i2) {
        if (i2 == 108) {
            ActionBar d2 = d();
            if (d2 != null) {
                d2.b(false);
            }
        } else if (i2 == 0) {
            PanelFeatureState a2 = a(i2, true);
            if (a2.o) {
                a(a2, false);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int i(int i2) {
        boolean z2;
        boolean z3;
        ActionBarContextView actionBarContextView = this.q;
        int i3 = 0;
        if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z2 = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.q.getLayoutParams();
            z2 = true;
            if (this.q.isShown()) {
                if (this.S == null) {
                    this.S = new Rect();
                    this.T = new Rect();
                }
                Rect rect = this.S;
                Rect rect2 = this.T;
                rect.set(0, i2, 0, 0);
                wa.a(this.w, rect, rect2);
                if (marginLayoutParams.topMargin != (rect2.top == 0 ? i2 : 0)) {
                    marginLayoutParams.topMargin = i2;
                    View view = this.y;
                    if (view == null) {
                        this.y = new View(this.e);
                        this.y.setBackgroundColor(this.e.getResources().getColor(b.a.c.abc_input_method_navigation_guard));
                        this.w.addView(this.y, -1, new ViewGroup.LayoutParams(-1, i2));
                    } else {
                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        if (layoutParams.height != i2) {
                            layoutParams.height = i2;
                            this.y.setLayoutParams(layoutParams);
                        }
                    }
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (this.y == null) {
                    z2 = false;
                }
                if (!this.D && z2) {
                    i2 = 0;
                }
            } else {
                if (marginLayoutParams.topMargin != 0) {
                    marginLayoutParams.topMargin = 0;
                    z3 = true;
                } else {
                    z3 = false;
                }
                z2 = false;
            }
            if (z3) {
                this.q.setLayoutParams(marginLayoutParams);
            }
        }
        View view2 = this.y;
        if (view2 != null) {
            if (!z2) {
                i3 = 8;
            }
            view2.setVisibility(i3);
        }
        return i2;
    }

    @Override // androidx.appcompat.app.m
    public void i() {
        a();
    }

    @Override // androidx.appcompat.app.m
    public void j() {
        ActionBar d2 = d();
        if (d2 != null) {
            d2.d(false);
        }
        d dVar = this.N;
        if (dVar != null) {
            dVar.a();
        }
    }

    /* access modifiers changed from: package-private */
    public void k() {
        l lVar;
        C c2 = this.m;
        if (c2 != null) {
            c2.f();
        }
        if (this.r != null) {
            this.f.getDecorView().removeCallbacks(this.s);
            if (this.r.isShowing()) {
                try {
                    this.r.dismiss();
                } catch (IllegalArgumentException unused) {
                }
            }
            this.r = null;
        }
        l();
        PanelFeatureState a2 = a(0, false);
        if (a2 != null && (lVar = a2.j) != null) {
            lVar.close();
        }
    }

    /* access modifiers changed from: package-private */
    public void l() {
        z zVar = this.t;
        if (zVar != null) {
            zVar.a();
        }
    }

    /* access modifiers changed from: package-private */
    public final Context m() {
        ActionBar d2 = d();
        Context h2 = d2 != null ? d2.h() : null;
        return h2 == null ? this.e : h2;
    }

    /* access modifiers changed from: package-private */
    public final CharSequence n() {
        Window.Callback callback = this.g;
        return callback instanceof Activity ? ((Activity) callback).getTitle() : this.l;
    }

    /* access modifiers changed from: package-private */
    public final Window.Callback o() {
        return this.f.getCallback();
    }

    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return a(view, str, context, attributeSet);
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    public boolean p() {
        return this.u;
    }

    /* access modifiers changed from: package-private */
    public boolean q() {
        b.a.d.b bVar = this.p;
        if (bVar != null) {
            bVar.a();
            return true;
        }
        ActionBar d2 = d();
        return d2 != null && d2.f();
    }

    /* access modifiers changed from: package-private */
    public final ActionBar r() {
        return this.j;
    }

    /* access modifiers changed from: package-private */
    public final boolean s() {
        ViewGroup viewGroup;
        return this.v && (viewGroup = this.w) != null && t.w(viewGroup);
    }
}
