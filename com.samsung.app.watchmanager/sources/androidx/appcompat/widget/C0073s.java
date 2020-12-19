package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.core.widget.g;
import b.a.a.a.a;
import b.a.j;

/* renamed from: androidx.appcompat.widget.s  reason: case insensitive filesystem */
public class C0073s {

    /* renamed from: a  reason: collision with root package name */
    private final ImageView f506a;

    /* renamed from: b  reason: collision with root package name */
    private ga f507b;

    /* renamed from: c  reason: collision with root package name */
    private ga f508c;

    /* renamed from: d  reason: collision with root package name */
    private ga f509d;

    public C0073s(ImageView imageView) {
        this.f506a = imageView;
    }

    private boolean a(Drawable drawable) {
        if (this.f509d == null) {
            this.f509d = new ga();
        }
        ga gaVar = this.f509d;
        gaVar.a();
        ColorStateList a2 = g.a(this.f506a);
        if (a2 != null) {
            gaVar.f470d = true;
            gaVar.f467a = a2;
        }
        PorterDuff.Mode b2 = g.b(this.f506a);
        if (b2 != null) {
            gaVar.f469c = true;
            gaVar.f468b = b2;
        }
        if (!gaVar.f470d && !gaVar.f469c) {
            return false;
        }
        C0072q.a(drawable, gaVar, this.f506a.getDrawableState());
        return true;
    }

    private boolean e() {
        int i = Build.VERSION.SDK_INT;
        return i > 21 ? this.f507b != null : i == 21;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        Drawable drawable = this.f506a.getDrawable();
        if (drawable != null) {
            E.b(drawable);
        }
        if (drawable == null) {
            return;
        }
        if (!e() || !a(drawable)) {
            ga gaVar = this.f508c;
            if (gaVar != null) {
                C0072q.a(drawable, gaVar, this.f506a.getDrawableState());
                return;
            }
            ga gaVar2 = this.f507b;
            if (gaVar2 != null) {
                C0072q.a(drawable, gaVar2, this.f506a.getDrawableState());
            }
        }
    }

    public void a(int i) {
        if (i != 0) {
            Drawable b2 = a.b(this.f506a.getContext(), i);
            if (b2 != null) {
                E.b(b2);
            }
            this.f506a.setImageDrawable(b2);
        } else {
            this.f506a.setImageDrawable(null);
        }
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList) {
        if (this.f508c == null) {
            this.f508c = new ga();
        }
        ga gaVar = this.f508c;
        gaVar.f467a = colorStateList;
        gaVar.f470d = true;
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(PorterDuff.Mode mode) {
        if (this.f508c == null) {
            this.f508c = new ga();
        }
        ga gaVar = this.f508c;
        gaVar.f468b = mode;
        gaVar.f469c = true;
        a();
    }

    public void a(AttributeSet attributeSet, int i) {
        int g;
        ia a2 = ia.a(this.f506a.getContext(), attributeSet, j.AppCompatImageView, i, 0);
        try {
            Drawable drawable = this.f506a.getDrawable();
            if (!(drawable != null || (g = a2.g(j.AppCompatImageView_srcCompat, -1)) == -1 || (drawable = a.b(this.f506a.getContext(), g)) == null)) {
                this.f506a.setImageDrawable(drawable);
            }
            if (drawable != null) {
                E.b(drawable);
            }
            if (a2.g(j.AppCompatImageView_tint)) {
                g.a(this.f506a, a2.a(j.AppCompatImageView_tint));
            }
            if (a2.g(j.AppCompatImageView_tintMode)) {
                g.a(this.f506a, E.a(a2.d(j.AppCompatImageView_tintMode, -1), null));
            }
        } finally {
            a2.a();
        }
    }

    /* access modifiers changed from: package-private */
    public ColorStateList b() {
        ga gaVar = this.f508c;
        if (gaVar != null) {
            return gaVar.f467a;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode c() {
        ga gaVar = this.f508c;
        if (gaVar != null) {
            return gaVar.f468b;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public boolean d() {
        return Build.VERSION.SDK_INT < 21 || !(this.f506a.getBackground() instanceof RippleDrawable);
    }
}
