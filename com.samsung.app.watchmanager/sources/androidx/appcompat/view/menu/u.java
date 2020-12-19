package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.v;
import b.a.d;
import b.e.g.C0113c;
import b.e.g.t;

public class u implements n {

    /* renamed from: a  reason: collision with root package name */
    private final Context f272a;

    /* renamed from: b  reason: collision with root package name */
    private final l f273b;

    /* renamed from: c  reason: collision with root package name */
    private final boolean f274c;

    /* renamed from: d  reason: collision with root package name */
    private final int f275d;
    private final int e;
    private View f;
    private int g;
    private boolean h;
    private v.a i;
    private s j;
    private PopupWindow.OnDismissListener k;
    private final PopupWindow.OnDismissListener l;

    public u(Context context, l lVar, View view, boolean z, int i2) {
        this(context, lVar, view, z, i2, 0);
    }

    public u(Context context, l lVar, View view, boolean z, int i2, int i3) {
        this.g = 8388611;
        this.l = new t(this);
        this.f272a = context;
        this.f273b = lVar;
        this.f = view;
        this.f274c = z;
        this.f275d = i2;
        this.e = i3;
    }

    private void a(int i2, int i3, boolean z, boolean z2) {
        s b2 = b();
        b2.c(z2);
        if (z) {
            if ((C0113c.a(this.g, t.i(this.f)) & 7) == 5) {
                i2 -= this.f.getWidth();
            }
            b2.b(i2);
            b2.c(i3);
            int i4 = (int) ((this.f272a.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            b2.a(new Rect(i2 - i4, i3 - i4, i2 + i4, i3 + i4));
        }
        b2.c();
    }

    private s g() {
        Display defaultDisplay = ((WindowManager) this.f272a.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealSize(point);
        } else {
            defaultDisplay.getSize(point);
        }
        s iVar = Math.min(point.x, point.y) >= this.f272a.getResources().getDimensionPixelSize(d.abc_cascading_menus_min_smallest_width) ? new i(this.f272a, this.f, this.f275d, this.e, this.f274c) : new C(this.f272a, this.f273b, this.f, this.f275d, this.e, this.f274c);
        iVar.a(this.f273b);
        iVar.a(this.l);
        iVar.a(this.f);
        iVar.a(this.i);
        iVar.b(this.h);
        iVar.a(this.g);
        return iVar;
    }

    public void a() {
        if (c()) {
            this.j.dismiss();
        }
    }

    public void a(int i2) {
        this.g = i2;
    }

    public void a(View view) {
        this.f = view;
    }

    public void a(PopupWindow.OnDismissListener onDismissListener) {
        this.k = onDismissListener;
    }

    public void a(v.a aVar) {
        this.i = aVar;
        s sVar = this.j;
        if (sVar != null) {
            sVar.a(aVar);
        }
    }

    public void a(boolean z) {
        this.h = z;
        s sVar = this.j;
        if (sVar != null) {
            sVar.b(z);
        }
    }

    public boolean a(int i2, int i3) {
        if (c()) {
            return true;
        }
        if (this.f == null) {
            return false;
        }
        a(i2, i3, true, true);
        return true;
    }

    public s b() {
        if (this.j == null) {
            this.j = g();
        }
        return this.j;
    }

    public boolean c() {
        s sVar = this.j;
        return sVar != null && sVar.b();
    }

    /* access modifiers changed from: protected */
    public void d() {
        this.j = null;
        PopupWindow.OnDismissListener onDismissListener = this.k;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public void e() {
        if (!f()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public boolean f() {
        if (c()) {
            return true;
        }
        if (this.f == null) {
            return false;
        }
        a(0, 0, false, false);
        return true;
    }
}
