package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import b.a.j;
import b.e.g.t;

/* access modifiers changed from: package-private */
/* renamed from: androidx.appcompat.widget.o  reason: case insensitive filesystem */
public class C0070o {

    /* renamed from: a  reason: collision with root package name */
    private final View f486a;

    /* renamed from: b  reason: collision with root package name */
    private final C0072q f487b;

    /* renamed from: c  reason: collision with root package name */
    private int f488c = -1;

    /* renamed from: d  reason: collision with root package name */
    private ga f489d;
    private ga e;
    private ga f;

    C0070o(View view) {
        this.f486a = view;
        this.f487b = C0072q.a();
    }

    private boolean b(Drawable drawable) {
        if (this.f == null) {
            this.f = new ga();
        }
        ga gaVar = this.f;
        gaVar.a();
        ColorStateList b2 = t.b(this.f486a);
        if (b2 != null) {
            gaVar.f470d = true;
            gaVar.f467a = b2;
        }
        PorterDuff.Mode c2 = t.c(this.f486a);
        if (c2 != null) {
            gaVar.f469c = true;
            gaVar.f468b = c2;
        }
        if (!gaVar.f470d && !gaVar.f469c) {
            return false;
        }
        C0072q.a(drawable, gaVar, this.f486a.getDrawableState());
        return true;
    }

    private boolean d() {
        int i = Build.VERSION.SDK_INT;
        return i > 21 ? this.f489d != null : i == 21;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        Drawable background = this.f486a.getBackground();
        if (background == null) {
            return;
        }
        if (!d() || !b(background)) {
            ga gaVar = this.e;
            if (gaVar != null) {
                C0072q.a(background, gaVar, this.f486a.getDrawableState());
                return;
            }
            ga gaVar2 = this.f489d;
            if (gaVar2 != null) {
                C0072q.a(background, gaVar2, this.f486a.getDrawableState());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i) {
        this.f488c = i;
        C0072q qVar = this.f487b;
        a(qVar != null ? qVar.b(this.f486a.getContext(), i) : null);
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.f489d == null) {
                this.f489d = new ga();
            }
            ga gaVar = this.f489d;
            gaVar.f467a = colorStateList;
            gaVar.f470d = true;
        } else {
            this.f489d = null;
        }
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(PorterDuff.Mode mode) {
        if (this.e == null) {
            this.e = new ga();
        }
        ga gaVar = this.e;
        gaVar.f468b = mode;
        gaVar.f469c = true;
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(Drawable drawable) {
        this.f488c = -1;
        a((ColorStateList) null);
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(AttributeSet attributeSet, int i) {
        ia a2 = ia.a(this.f486a.getContext(), attributeSet, j.ViewBackgroundHelper, i, 0);
        try {
            if (a2.g(j.ViewBackgroundHelper_android_background)) {
                this.f488c = a2.g(j.ViewBackgroundHelper_android_background, -1);
                ColorStateList b2 = this.f487b.b(this.f486a.getContext(), this.f488c);
                if (b2 != null) {
                    a(b2);
                }
            }
            if (a2.g(j.ViewBackgroundHelper_backgroundTint)) {
                t.a(this.f486a, a2.a(j.ViewBackgroundHelper_backgroundTint));
            }
            if (a2.g(j.ViewBackgroundHelper_backgroundTintMode)) {
                t.a(this.f486a, E.a(a2.d(j.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
        } finally {
            a2.a();
        }
    }

    /* access modifiers changed from: package-private */
    public ColorStateList b() {
        ga gaVar = this.e;
        if (gaVar != null) {
            return gaVar.f467a;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void b(ColorStateList colorStateList) {
        if (this.e == null) {
            this.e = new ga();
        }
        ga gaVar = this.e;
        gaVar.f467a = colorStateList;
        gaVar.f470d = true;
        a();
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode c() {
        ga gaVar = this.e;
        if (gaVar != null) {
            return gaVar.f468b;
        }
        return null;
    }
}
