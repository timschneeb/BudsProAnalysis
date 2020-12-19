package androidx.appcompat.widget;

import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import b.e.g.t;
import b.e.g.u;

/* access modifiers changed from: package-private */
public class ta implements View.OnLongClickListener, View.OnHoverListener, View.OnAttachStateChangeListener {

    /* renamed from: a  reason: collision with root package name */
    private static ta f514a;

    /* renamed from: b  reason: collision with root package name */
    private static ta f515b;

    /* renamed from: c  reason: collision with root package name */
    private final View f516c;

    /* renamed from: d  reason: collision with root package name */
    private final CharSequence f517d;
    private final int e;
    private final Runnable f = new ra(this);
    private final Runnable g = new sa(this);
    private int h;
    private int i;
    private ua j;
    private boolean k;

    private ta(View view, CharSequence charSequence) {
        this.f516c = view;
        this.f517d = charSequence;
        this.e = u.a(ViewConfiguration.get(this.f516c.getContext()));
        c();
        this.f516c.setOnLongClickListener(this);
        this.f516c.setOnHoverListener(this);
    }

    public static void a(View view, CharSequence charSequence) {
        ta taVar = f514a;
        if (taVar != null && taVar.f516c == view) {
            a((ta) null);
        }
        if (TextUtils.isEmpty(charSequence)) {
            ta taVar2 = f515b;
            if (taVar2 != null && taVar2.f516c == view) {
                taVar2.a();
            }
            view.setOnLongClickListener(null);
            view.setLongClickable(false);
            view.setOnHoverListener(null);
            return;
        }
        new ta(view, charSequence);
    }

    private static void a(ta taVar) {
        ta taVar2 = f514a;
        if (taVar2 != null) {
            taVar2.b();
        }
        f514a = taVar;
        ta taVar3 = f514a;
        if (taVar3 != null) {
            taVar3.d();
        }
    }

    private boolean a(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (Math.abs(x - this.h) <= this.e && Math.abs(y - this.i) <= this.e) {
            return false;
        }
        this.h = x;
        this.i = y;
        return true;
    }

    private void b() {
        this.f516c.removeCallbacks(this.f);
    }

    private void c() {
        this.h = Integer.MAX_VALUE;
        this.i = Integer.MAX_VALUE;
    }

    private void d() {
        this.f516c.postDelayed(this.f, (long) ViewConfiguration.getLongPressTimeout());
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (f515b == this) {
            f515b = null;
            ua uaVar = this.j;
            if (uaVar != null) {
                uaVar.a();
                this.j = null;
                c();
                this.f516c.removeOnAttachStateChangeListener(this);
            } else {
                Log.e("TooltipCompatHandler", "sActiveHandler.mPopup == null");
            }
        }
        if (f514a == this) {
            a((ta) null);
        }
        this.f516c.removeCallbacks(this.g);
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z) {
        long j2;
        if (t.v(this.f516c)) {
            a((ta) null);
            ta taVar = f515b;
            if (taVar != null) {
                taVar.a();
            }
            f515b = this;
            this.k = z;
            this.j = new ua(this.f516c.getContext());
            this.j.a(this.f516c, this.h, this.i, this.k, this.f517d);
            this.f516c.addOnAttachStateChangeListener(this);
            if (this.k) {
                j2 = 2500;
            } else {
                j2 = ((t.p(this.f516c) & 1) == 1 ? 3000 : 15000) - ((long) ViewConfiguration.getLongPressTimeout());
            }
            this.f516c.removeCallbacks(this.g);
            this.f516c.postDelayed(this.g, j2);
        }
    }

    public boolean onHover(View view, MotionEvent motionEvent) {
        if (this.j != null && this.k) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.f516c.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 7) {
            if (action == 10) {
                c();
                a();
            }
        } else if (this.f516c.isEnabled() && this.j == null && a(motionEvent)) {
            a(this);
        }
        return false;
    }

    public boolean onLongClick(View view) {
        this.h = view.getWidth() / 2;
        this.i = view.getHeight() / 2;
        a(true);
        return true;
    }

    public void onViewAttachedToWindow(View view) {
    }

    public void onViewDetachedFromWindow(View view) {
        a();
    }
}
