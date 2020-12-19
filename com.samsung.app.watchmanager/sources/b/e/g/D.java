package b.e.g;

import android.os.Build;
import android.view.WindowInsets;

public class D {

    /* renamed from: a  reason: collision with root package name */
    private final Object f1398a;

    private D(Object obj) {
        this.f1398a = obj;
    }

    static D a(Object obj) {
        if (obj == null) {
            return null;
        }
        return new D(obj);
    }

    static Object a(D d2) {
        if (d2 == null) {
            return null;
        }
        return d2.f1398a;
    }

    public int a() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.f1398a).getSystemWindowInsetBottom();
        }
        return 0;
    }

    public D a(int i, int i2, int i3, int i4) {
        if (Build.VERSION.SDK_INT >= 20) {
            return new D(((WindowInsets) this.f1398a).replaceSystemWindowInsets(i, i2, i3, i4));
        }
        return null;
    }

    public int b() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.f1398a).getSystemWindowInsetLeft();
        }
        return 0;
    }

    public int c() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.f1398a).getSystemWindowInsetRight();
        }
        return 0;
    }

    public int d() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.f1398a).getSystemWindowInsetTop();
        }
        return 0;
    }

    public boolean e() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.f1398a).isConsumed();
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || D.class != obj.getClass()) {
            return false;
        }
        D d2 = (D) obj;
        Object obj2 = this.f1398a;
        return obj2 == null ? d2.f1398a == null : obj2.equals(d2.f1398a);
    }

    public int hashCode() {
        Object obj = this.f1398a;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }
}
