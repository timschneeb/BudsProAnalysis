package b.e.g.a;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

public class b {

    /* renamed from: a  reason: collision with root package name */
    private final AccessibilityNodeInfo f1402a;

    /* renamed from: b  reason: collision with root package name */
    public int f1403b = -1;

    public static class a {
        public static final a A = new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN : null);
        public static final a B = new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT : null);
        public static final a C = new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK : null);
        public static final a D = new a(Build.VERSION.SDK_INT >= 24 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS : null);
        public static final a E = new a(Build.VERSION.SDK_INT >= 26 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW : null);
        public static final a F = new a(Build.VERSION.SDK_INT >= 28 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP : null);
        public static final a G;

        /* renamed from: a  reason: collision with root package name */
        public static final a f1404a = new a(1, null);

        /* renamed from: b  reason: collision with root package name */
        public static final a f1405b = new a(2, null);

        /* renamed from: c  reason: collision with root package name */
        public static final a f1406c = new a(4, null);

        /* renamed from: d  reason: collision with root package name */
        public static final a f1407d = new a(8, null);
        public static final a e = new a(16, null);
        public static final a f = new a(32, null);
        public static final a g = new a(64, null);
        public static final a h = new a(128, null);
        public static final a i = new a(256, null);
        public static final a j = new a(512, null);
        public static final a k = new a(1024, null);
        public static final a l = new a(2048, null);
        public static final a m = new a(4096, null);
        public static final a n = new a(8192, null);
        public static final a o = new a(16384, null);
        public static final a p = new a(32768, null);
        public static final a q = new a(65536, null);
        public static final a r = new a(131072, null);
        public static final a s = new a(262144, null);
        public static final a t = new a(524288, null);
        public static final a u = new a(1048576, null);
        public static final a v = new a(2097152, null);
        public static final a w = new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN : null);
        public static final a x = new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION : null);
        public static final a y = new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP : null);
        public static final a z = new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT : null);
        final Object H;

        static {
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction = null;
            if (Build.VERSION.SDK_INT >= 28) {
                accessibilityAction = AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP;
            }
            G = new a(accessibilityAction);
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public a(int i2, CharSequence charSequence) {
            this(Build.VERSION.SDK_INT >= 21 ? new AccessibilityNodeInfo.AccessibilityAction(i2, charSequence) : null);
        }

        a(Object obj) {
            this.H = obj;
        }
    }

    /* renamed from: b.e.g.a.b$b  reason: collision with other inner class name */
    public static class C0025b {

        /* renamed from: a  reason: collision with root package name */
        final Object f1408a;

        C0025b(Object obj) {
            this.f1408a = obj;
        }

        public static C0025b a(int i, int i2, boolean z, int i3) {
            int i4 = Build.VERSION.SDK_INT;
            return i4 >= 21 ? new C0025b(AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z, i3)) : i4 >= 19 ? new C0025b(AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z)) : new C0025b(null);
        }
    }

    public static class c {

        /* renamed from: a  reason: collision with root package name */
        final Object f1409a;

        c(Object obj) {
            this.f1409a = obj;
        }

        public static c a(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            int i5 = Build.VERSION.SDK_INT;
            return i5 >= 21 ? new c(AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z, z2)) : i5 >= 19 ? new c(AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z)) : new c(null);
        }
    }

    private b(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.f1402a = accessibilityNodeInfo;
    }

    public static b a(AccessibilityNodeInfo accessibilityNodeInfo) {
        return new b(accessibilityNodeInfo);
    }

    public static b a(b bVar) {
        return a(AccessibilityNodeInfo.obtain(bVar.f1402a));
    }

    private static String c(int i) {
        if (i == 1) {
            return "ACTION_FOCUS";
        }
        if (i == 2) {
            return "ACTION_CLEAR_FOCUS";
        }
        switch (i) {
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            default:
                return "ACTION_UNKNOWN";
        }
    }

    public int a() {
        return this.f1402a.getActions();
    }

    public void a(int i) {
        this.f1402a.addAction(i);
    }

    public void a(Rect rect) {
        this.f1402a.getBoundsInParent(rect);
    }

    public void a(View view) {
        this.f1402a.addChild(view);
    }

    public void a(CharSequence charSequence) {
        this.f1402a.setClassName(charSequence);
    }

    public void a(Object obj) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.f1402a.setCollectionInfo(obj == null ? null : (AccessibilityNodeInfo.CollectionInfo) ((C0025b) obj).f1408a);
        }
    }

    public void a(boolean z) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.f1402a.setAccessibilityFocused(z);
        }
    }

    public boolean a(a aVar) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.f1402a.removeAction((AccessibilityNodeInfo.AccessibilityAction) aVar.H);
        }
        return false;
    }

    public CharSequence b() {
        return this.f1402a.getClassName();
    }

    public void b(int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.f1402a.setMovementGranularities(i);
        }
    }

    public void b(Rect rect) {
        this.f1402a.getBoundsInScreen(rect);
    }

    public void b(View view) {
        this.f1402a.setParent(view);
    }

    public void b(CharSequence charSequence) {
        this.f1402a.setContentDescription(charSequence);
    }

    public void b(Object obj) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.f1402a.setCollectionItemInfo(obj == null ? null : (AccessibilityNodeInfo.CollectionItemInfo) ((c) obj).f1409a);
        }
    }

    public void b(boolean z) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.f1402a.setCanOpenPopup(z);
        }
    }

    public CharSequence c() {
        return this.f1402a.getContentDescription();
    }

    public void c(Rect rect) {
        this.f1402a.setBoundsInParent(rect);
    }

    public void c(View view) {
        this.f1402a.setSource(view);
    }

    public void c(CharSequence charSequence) {
        this.f1402a.setPackageName(charSequence);
    }

    public void c(boolean z) {
        this.f1402a.setClickable(z);
    }

    public int d() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.f1402a.getMovementGranularities();
        }
        return 0;
    }

    public void d(Rect rect) {
        this.f1402a.setBoundsInScreen(rect);
    }

    public void d(boolean z) {
        this.f1402a.setEnabled(z);
    }

    public CharSequence e() {
        return this.f1402a.getPackageName();
    }

    public void e(boolean z) {
        this.f1402a.setFocusable(z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || b.class != obj.getClass()) {
            return false;
        }
        b bVar = (b) obj;
        AccessibilityNodeInfo accessibilityNodeInfo = this.f1402a;
        if (accessibilityNodeInfo == null) {
            if (bVar.f1402a != null) {
                return false;
            }
        } else if (!accessibilityNodeInfo.equals(bVar.f1402a)) {
            return false;
        }
        return true;
    }

    public CharSequence f() {
        return this.f1402a.getText();
    }

    public void f(boolean z) {
        this.f1402a.setFocused(z);
    }

    public String g() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.f1402a.getViewIdResourceName();
        }
        return null;
    }

    public void g(boolean z) {
        this.f1402a.setLongClickable(z);
    }

    public void h(boolean z) {
        this.f1402a.setScrollable(z);
    }

    public boolean h() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.f1402a.isAccessibilityFocused();
        }
        return false;
    }

    public int hashCode() {
        AccessibilityNodeInfo accessibilityNodeInfo = this.f1402a;
        if (accessibilityNodeInfo == null) {
            return 0;
        }
        return accessibilityNodeInfo.hashCode();
    }

    public void i(boolean z) {
        this.f1402a.setSelected(z);
    }

    public boolean i() {
        return this.f1402a.isCheckable();
    }

    public void j(boolean z) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.f1402a.setVisibleToUser(z);
        }
    }

    public boolean j() {
        return this.f1402a.isChecked();
    }

    public boolean k() {
        return this.f1402a.isClickable();
    }

    public boolean l() {
        return this.f1402a.isEnabled();
    }

    public boolean m() {
        return this.f1402a.isFocusable();
    }

    public boolean n() {
        return this.f1402a.isFocused();
    }

    public boolean o() {
        return this.f1402a.isLongClickable();
    }

    public boolean p() {
        return this.f1402a.isPassword();
    }

    public boolean q() {
        return this.f1402a.isScrollable();
    }

    public boolean r() {
        return this.f1402a.isSelected();
    }

    public boolean s() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.f1402a.isVisibleToUser();
        }
        return false;
    }

    public void t() {
        this.f1402a.recycle();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        Rect rect = new Rect();
        a(rect);
        sb.append("; boundsInParent: " + rect);
        b(rect);
        sb.append("; boundsInScreen: " + rect);
        sb.append("; packageName: ");
        sb.append(e());
        sb.append("; className: ");
        sb.append(b());
        sb.append("; text: ");
        sb.append(f());
        sb.append("; contentDescription: ");
        sb.append(c());
        sb.append("; viewId: ");
        sb.append(g());
        sb.append("; checkable: ");
        sb.append(i());
        sb.append("; checked: ");
        sb.append(j());
        sb.append("; focusable: ");
        sb.append(m());
        sb.append("; focused: ");
        sb.append(n());
        sb.append("; selected: ");
        sb.append(r());
        sb.append("; clickable: ");
        sb.append(k());
        sb.append("; longClickable: ");
        sb.append(o());
        sb.append("; enabled: ");
        sb.append(l());
        sb.append("; password: ");
        sb.append(p());
        sb.append("; scrollable: " + q());
        sb.append("; [");
        int a2 = a();
        while (a2 != 0) {
            int numberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(a2);
            a2 &= numberOfTrailingZeros ^ -1;
            sb.append(c(numberOfTrailingZeros));
            if (a2 != 0) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public AccessibilityNodeInfo u() {
        return this.f1402a;
    }
}
