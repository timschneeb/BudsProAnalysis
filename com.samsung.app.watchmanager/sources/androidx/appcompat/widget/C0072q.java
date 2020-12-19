package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import b.c.f;
import b.c.g;
import b.c.j;
import b.l.a.a.k;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;

/* renamed from: androidx.appcompat.widget.q  reason: case insensitive filesystem */
public final class C0072q {

    /* renamed from: a  reason: collision with root package name */
    private static final PorterDuff.Mode f501a = PorterDuff.Mode.SRC_IN;

    /* renamed from: b  reason: collision with root package name */
    private static C0072q f502b;

    /* renamed from: c  reason: collision with root package name */
    private static final c f503c = new c(6);

    /* renamed from: d  reason: collision with root package name */
    private static final int[] f504d = {b.a.e.abc_textfield_search_default_mtrl_alpha, b.a.e.abc_textfield_default_mtrl_alpha, b.a.e.abc_ab_share_pack_mtrl_alpha};
    private static final int[] e = {b.a.e.abc_ic_commit_search_api_mtrl_alpha, b.a.e.abc_seekbar_tick_mark_material, b.a.e.abc_ic_menu_share_mtrl_alpha, b.a.e.abc_ic_menu_copy_mtrl_am_alpha, b.a.e.abc_ic_menu_cut_mtrl_alpha, b.a.e.abc_ic_menu_selectall_mtrl_alpha, b.a.e.abc_ic_menu_paste_mtrl_am_alpha};
    private static final int[] f = {b.a.e.abc_textfield_activated_mtrl_alpha, b.a.e.abc_textfield_search_activated_mtrl_alpha, b.a.e.abc_cab_background_top_mtrl_alpha, b.a.e.abc_text_cursor_material, b.a.e.abc_text_select_handle_left_mtrl_dark, b.a.e.abc_text_select_handle_middle_mtrl_dark, b.a.e.abc_text_select_handle_right_mtrl_dark, b.a.e.abc_text_select_handle_left_mtrl_light, b.a.e.abc_text_select_handle_middle_mtrl_light, b.a.e.abc_text_select_handle_right_mtrl_light};
    private static final int[] g = {b.a.e.abc_popup_background_mtrl_mult, b.a.e.abc_cab_background_internal_bg, b.a.e.abc_menu_hardkey_panel_mtrl_mult};
    private static final int[] h = {b.a.e.abc_tab_indicator_material, b.a.e.abc_textfield_search_material};
    private static final int[] i = {b.a.e.abc_btn_check_material, b.a.e.abc_btn_radio_material};
    private WeakHashMap<Context, j<ColorStateList>> j;
    private b.c.b<String, d> k;
    private j<String> l;
    private final WeakHashMap<Context, f<WeakReference<Drawable.ConstantState>>> m = new WeakHashMap<>(0);
    private TypedValue n;
    private boolean o;

    /* access modifiers changed from: package-private */
    /* renamed from: androidx.appcompat.widget.q$a */
    public static class a implements d {
        a() {
        }

        @Override // androidx.appcompat.widget.C0072q.d
        public Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return b.a.b.a.b.a(context, context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("AsldcInflateDelegate", "Exception while inflating <animated-selector>", e);
                return null;
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: androidx.appcompat.widget.q$b */
    public static class b implements d {
        b() {
        }

        @Override // androidx.appcompat.widget.C0072q.d
        public Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return b.l.a.a.d.a(context, context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", e);
                return null;
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: androidx.appcompat.widget.q$c */
    public static class c extends g<Integer, PorterDuffColorFilter> {
        public c(int i) {
            super(i);
        }

        private static int b(int i, PorterDuff.Mode mode) {
            return ((i + 31) * 31) + mode.hashCode();
        }

        /* access modifiers changed from: package-private */
        public PorterDuffColorFilter a(int i, PorterDuff.Mode mode) {
            return (PorterDuffColorFilter) b(Integer.valueOf(b(i, mode)));
        }

        /* access modifiers changed from: package-private */
        public PorterDuffColorFilter a(int i, PorterDuff.Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return (PorterDuffColorFilter) a(Integer.valueOf(b(i, mode)), porterDuffColorFilter);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: androidx.appcompat.widget.q$d */
    public interface d {
        Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme);
    }

    /* access modifiers changed from: private */
    /* renamed from: androidx.appcompat.widget.q$e */
    public static class e implements d {
        e() {
        }

        @Override // androidx.appcompat.widget.C0072q.d
        public Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return k.createFromXmlInner(context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("VdcInflateDelegate", "Exception while inflating <vector>", e);
                return null;
            }
        }
    }

    private static long a(TypedValue typedValue) {
        return (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
    }

    static PorterDuff.Mode a(int i2) {
        if (i2 == b.a.e.abc_switch_thumb_material) {
            return PorterDuff.Mode.MULTIPLY;
        }
        return null;
    }

    public static synchronized PorterDuffColorFilter a(int i2, PorterDuff.Mode mode) {
        PorterDuffColorFilter a2;
        synchronized (C0072q.class) {
            a2 = f503c.a(i2, mode);
            if (a2 == null) {
                a2 = new PorterDuffColorFilter(i2, mode);
                f503c.a(i2, mode, a2);
            }
        }
        return a2;
    }

    private static PorterDuffColorFilter a(ColorStateList colorStateList, PorterDuff.Mode mode, int[] iArr) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return a(colorStateList.getColorForState(iArr, 0), mode);
    }

    private Drawable a(Context context, int i2, boolean z, Drawable drawable) {
        LayerDrawable layerDrawable;
        Drawable findDrawableByLayerId;
        int i3;
        ColorStateList b2 = b(context, i2);
        if (b2 != null) {
            if (E.a(drawable)) {
                drawable = drawable.mutate();
            }
            Drawable h2 = androidx.core.graphics.drawable.a.h(drawable);
            androidx.core.graphics.drawable.a.a(h2, b2);
            PorterDuff.Mode a2 = a(i2);
            if (a2 == null) {
                return h2;
            }
            androidx.core.graphics.drawable.a.a(h2, a2);
            return h2;
        }
        if (i2 == b.a.e.abc_seekbar_track_material) {
            layerDrawable = (LayerDrawable) drawable;
            a(layerDrawable.findDrawableByLayerId(16908288), da.b(context, b.a.a.colorControlNormal), f501a);
            findDrawableByLayerId = layerDrawable.findDrawableByLayerId(16908303);
            i3 = b.a.a.colorControlNormal;
        } else if (i2 == b.a.e.abc_ratingbar_material || i2 == b.a.e.abc_ratingbar_indicator_material || i2 == b.a.e.abc_ratingbar_small_material) {
            layerDrawable = (LayerDrawable) drawable;
            a(layerDrawable.findDrawableByLayerId(16908288), da.a(context, b.a.a.colorControlNormal), f501a);
            findDrawableByLayerId = layerDrawable.findDrawableByLayerId(16908303);
            i3 = b.a.a.colorControlActivated;
        } else if (a(context, i2, drawable) || !z) {
            return drawable;
        } else {
            return null;
        }
        a(findDrawableByLayerId, da.b(context, i3), f501a);
        a(layerDrawable.findDrawableByLayerId(16908301), da.b(context, b.a.a.colorControlActivated), f501a);
        return drawable;
    }

    private synchronized Drawable a(Context context, long j2) {
        f<WeakReference<Drawable.ConstantState>> fVar = this.m.get(context);
        if (fVar == null) {
            return null;
        }
        WeakReference<Drawable.ConstantState> b2 = fVar.b(j2);
        if (b2 != null) {
            Drawable.ConstantState constantState = b2.get();
            if (constantState != null) {
                return constantState.newDrawable(context.getResources());
            }
            fVar.a(j2);
        }
        return null;
    }

    public static synchronized C0072q a() {
        C0072q qVar;
        synchronized (C0072q.class) {
            if (f502b == null) {
                f502b = new C0072q();
                a(f502b);
            }
            qVar = f502b;
        }
        return qVar;
    }

    private void a(Context context, int i2, ColorStateList colorStateList) {
        if (this.j == null) {
            this.j = new WeakHashMap<>();
        }
        j<ColorStateList> jVar = this.j.get(context);
        if (jVar == null) {
            jVar = new j<>();
            this.j.put(context, jVar);
        }
        jVar.a(i2, colorStateList);
    }

    private static void a(Drawable drawable, int i2, PorterDuff.Mode mode) {
        if (E.a(drawable)) {
            drawable = drawable.mutate();
        }
        if (mode == null) {
            mode = f501a;
        }
        drawable.setColorFilter(a(i2, mode));
    }

    static void a(Drawable drawable, ga gaVar, int[] iArr) {
        if (!E.a(drawable) || drawable.mutate() == drawable) {
            if (gaVar.f470d || gaVar.f469c) {
                drawable.setColorFilter(a(gaVar.f470d ? gaVar.f467a : null, gaVar.f469c ? gaVar.f468b : f501a, iArr));
            } else {
                drawable.clearColorFilter();
            }
            if (Build.VERSION.SDK_INT <= 23) {
                drawable.invalidateSelf();
                return;
            }
            return;
        }
        Log.d("AppCompatDrawableManag", "Mutated drawable is not the same instance as the input.");
    }

    private static void a(C0072q qVar) {
        if (Build.VERSION.SDK_INT < 24) {
            qVar.a("vector", new e());
            qVar.a("animated-vector", new b());
            qVar.a("animated-selector", new a());
        }
    }

    private void a(String str, d dVar) {
        if (this.k == null) {
            this.k = new b.c.b<>();
        }
        this.k.put(str, dVar);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0061 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean a(android.content.Context r6, int r7, android.graphics.drawable.Drawable r8) {
        /*
            android.graphics.PorterDuff$Mode r0 = androidx.appcompat.widget.C0072q.f501a
            int[] r1 = androidx.appcompat.widget.C0072q.f504d
            boolean r1 = a(r1, r7)
            r2 = 16842801(0x1010031, float:2.3693695E-38)
            r3 = -1
            r4 = 0
            r5 = 1
            if (r1 == 0) goto L_0x0015
            int r2 = b.a.a.colorControlNormal
        L_0x0012:
            r7 = 1
            r1 = -1
            goto L_0x0044
        L_0x0015:
            int[] r1 = androidx.appcompat.widget.C0072q.f
            boolean r1 = a(r1, r7)
            if (r1 == 0) goto L_0x0020
            int r2 = b.a.a.colorControlActivated
            goto L_0x0012
        L_0x0020:
            int[] r1 = androidx.appcompat.widget.C0072q.g
            boolean r1 = a(r1, r7)
            if (r1 == 0) goto L_0x002b
            android.graphics.PorterDuff$Mode r0 = android.graphics.PorterDuff.Mode.MULTIPLY
            goto L_0x0012
        L_0x002b:
            int r1 = b.a.e.abc_list_divider_mtrl_alpha
            if (r7 != r1) goto L_0x003c
            r2 = 16842800(0x1010030, float:2.3693693E-38)
            r7 = 1109603123(0x42233333, float:40.8)
            int r7 = java.lang.Math.round(r7)
            r1 = r7
            r7 = 1
            goto L_0x0044
        L_0x003c:
            int r1 = b.a.e.abc_dialog_material_background
            if (r7 != r1) goto L_0x0041
            goto L_0x0012
        L_0x0041:
            r7 = 0
            r1 = -1
            r2 = 0
        L_0x0044:
            if (r7 == 0) goto L_0x0061
            boolean r7 = androidx.appcompat.widget.E.a(r8)
            if (r7 == 0) goto L_0x0050
            android.graphics.drawable.Drawable r8 = r8.mutate()
        L_0x0050:
            int r6 = androidx.appcompat.widget.da.b(r6, r2)
            android.graphics.PorterDuffColorFilter r6 = a(r6, r0)
            r8.setColorFilter(r6)
            if (r1 == r3) goto L_0x0060
            r8.setAlpha(r1)
        L_0x0060:
            return r5
        L_0x0061:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.C0072q.a(android.content.Context, int, android.graphics.drawable.Drawable):boolean");
    }

    private synchronized boolean a(Context context, long j2, Drawable drawable) {
        boolean z;
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState != null) {
            f<WeakReference<Drawable.ConstantState>> fVar = this.m.get(context);
            if (fVar == null) {
                fVar = new f<>();
                this.m.put(context, fVar);
            }
            fVar.c(j2, new WeakReference<>(constantState));
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private static boolean a(Drawable drawable) {
        return (drawable instanceof k) || "android.graphics.drawable.VectorDrawable".equals(drawable.getClass().getName());
    }

    private static boolean a(int[] iArr, int i2) {
        for (int i3 : iArr) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    private void b(Context context) {
        if (!this.o) {
            this.o = true;
            Drawable a2 = a(context, b.a.e.abc_vector_test);
            if (a2 == null || !a(a2)) {
                this.o = false;
                throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
            }
        }
    }

    private ColorStateList c(Context context) {
        return c(context, 0);
    }

    private ColorStateList c(Context context, int i2) {
        int b2 = da.b(context, b.a.a.colorControlHighlight);
        int a2 = da.a(context, b.a.a.colorButtonNormal);
        return new ColorStateList(new int[][]{da.f458b, da.e, da.f459c, da.i}, new int[]{a2, b.e.a.a.a(b2, i2), b.e.a.a.a(b2, i2), i2});
    }

    private ColorStateList d(Context context) {
        return c(context, da.b(context, b.a.a.colorAccent));
    }

    private Drawable d(Context context, int i2) {
        if (this.n == null) {
            this.n = new TypedValue();
        }
        TypedValue typedValue = this.n;
        context.getResources().getValue(i2, typedValue, true);
        long a2 = a(typedValue);
        Drawable a3 = a(context, a2);
        if (a3 != null) {
            return a3;
        }
        if (i2 == b.a.e.abc_cab_background_top_material) {
            a3 = new LayerDrawable(new Drawable[]{a(context, b.a.e.abc_cab_background_internal_bg), a(context, b.a.e.abc_cab_background_top_mtrl_alpha)});
        }
        if (a3 != null) {
            a3.setChangingConfigurations(typedValue.changingConfigurations);
            a(context, a2, a3);
        }
        return a3;
    }

    private ColorStateList e(Context context) {
        return c(context, da.b(context, b.a.a.colorButtonNormal));
    }

    private ColorStateList e(Context context, int i2) {
        j<ColorStateList> jVar;
        WeakHashMap<Context, j<ColorStateList>> weakHashMap = this.j;
        if (weakHashMap == null || (jVar = weakHashMap.get(context)) == null) {
            return null;
        }
        return jVar.b(i2);
    }

    private ColorStateList f(Context context) {
        int[][] iArr = new int[3][];
        int[] iArr2 = new int[3];
        ColorStateList c2 = da.c(context, b.a.a.colorSwitchThumbNormal);
        if (c2 == null || !c2.isStateful()) {
            iArr[0] = da.f458b;
            iArr2[0] = da.a(context, b.a.a.colorSwitchThumbNormal);
            iArr[1] = da.f;
            iArr2[1] = da.b(context, b.a.a.colorControlActivated);
            iArr[2] = da.i;
            iArr2[2] = da.b(context, b.a.a.colorSwitchThumbNormal);
        } else {
            iArr[0] = da.f458b;
            iArr2[0] = c2.getColorForState(iArr[0], 0);
            iArr[1] = da.f;
            iArr2[1] = da.b(context, b.a.a.colorControlActivated);
            iArr[2] = da.i;
            iArr2[2] = c2.getDefaultColor();
        }
        return new ColorStateList(iArr, iArr2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0073 A[Catch:{ Exception -> 0x00a2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x009a A[Catch:{ Exception -> 0x00a2 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable f(android.content.Context r11, int r12) {
        /*
        // Method dump skipped, instructions count: 179
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.C0072q.f(android.content.Context, int):android.graphics.drawable.Drawable");
    }

    public synchronized Drawable a(Context context, int i2) {
        return a(context, i2, false);
    }

    /* access modifiers changed from: package-private */
    public synchronized Drawable a(Context context, int i2, boolean z) {
        Drawable f2;
        b(context);
        f2 = f(context, i2);
        if (f2 == null) {
            f2 = d(context, i2);
        }
        if (f2 == null) {
            f2 = androidx.core.content.a.c(context, i2);
        }
        if (f2 != null) {
            f2 = a(context, i2, z, f2);
        }
        if (f2 != null) {
            E.b(f2);
        }
        return f2;
    }

    /* access modifiers changed from: package-private */
    public synchronized Drawable a(Context context, va vaVar, int i2) {
        Drawable f2 = f(context, i2);
        if (f2 == null) {
            f2 = vaVar.a(i2);
        }
        if (f2 == null) {
            return null;
        }
        return a(context, i2, false, f2);
    }

    public synchronized void a(Context context) {
        f<WeakReference<Drawable.ConstantState>> fVar = this.m.get(context);
        if (fVar != null) {
            fVar.a();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0078  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized android.content.res.ColorStateList b(android.content.Context r3, int r4) {
        /*
        // Method dump skipped, instructions count: 130
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.C0072q.b(android.content.Context, int):android.content.res.ColorStateList");
    }
}
