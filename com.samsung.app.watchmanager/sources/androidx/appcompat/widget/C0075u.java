package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import androidx.core.graphics.drawable.a;
import b.a.j;
import b.e.g.t;

/* access modifiers changed from: package-private */
/* renamed from: androidx.appcompat.widget.u  reason: case insensitive filesystem */
public class C0075u extends C0074t {

    /* renamed from: d  reason: collision with root package name */
    private final SeekBar f518d;
    private Drawable e;
    private ColorStateList f = null;
    private PorterDuff.Mode g = null;
    private boolean h = false;
    private boolean i = false;

    C0075u(SeekBar seekBar) {
        super(seekBar);
        this.f518d = seekBar;
    }

    private void d() {
        if (this.e == null) {
            return;
        }
        if (this.h || this.i) {
            this.e = a.h(this.e.mutate());
            if (this.h) {
                a.a(this.e, this.f);
            }
            if (this.i) {
                a.a(this.e, this.g);
            }
            if (this.e.isStateful()) {
                this.e.setState(this.f518d.getDrawableState());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Canvas canvas) {
        if (this.e != null) {
            int max = this.f518d.getMax();
            int i2 = 1;
            if (max > 1) {
                int intrinsicWidth = this.e.getIntrinsicWidth();
                int intrinsicHeight = this.e.getIntrinsicHeight();
                int i3 = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                if (intrinsicHeight >= 0) {
                    i2 = intrinsicHeight / 2;
                }
                this.e.setBounds(-i3, -i2, i3, i2);
                float width = ((float) ((this.f518d.getWidth() - this.f518d.getPaddingLeft()) - this.f518d.getPaddingRight())) / ((float) max);
                int save = canvas.save();
                canvas.translate((float) this.f518d.getPaddingLeft(), (float) (this.f518d.getHeight() / 2));
                for (int i4 = 0; i4 <= max; i4++) {
                    this.e.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(save);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Drawable drawable) {
        Drawable drawable2 = this.e;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.e = drawable;
        if (drawable != null) {
            drawable.setCallback(this.f518d);
            a.a(drawable, t.i(this.f518d));
            if (drawable.isStateful()) {
                drawable.setState(this.f518d.getDrawableState());
            }
            d();
        }
        this.f518d.invalidate();
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.appcompat.widget.C0074t
    public void a(AttributeSet attributeSet, int i2) {
        super.a(attributeSet, i2);
        ia a2 = ia.a(this.f518d.getContext(), attributeSet, j.AppCompatSeekBar, i2, 0);
        Drawable c2 = a2.c(j.AppCompatSeekBar_android_thumb);
        if (c2 != null) {
            this.f518d.setThumb(c2);
        }
        a(a2.b(j.AppCompatSeekBar_tickMark));
        if (a2.g(j.AppCompatSeekBar_tickMarkTintMode)) {
            this.g = E.a(a2.d(j.AppCompatSeekBar_tickMarkTintMode, -1), this.g);
            this.i = true;
        }
        if (a2.g(j.AppCompatSeekBar_tickMarkTint)) {
            this.f = a2.a(j.AppCompatSeekBar_tickMarkTint);
            this.h = true;
        }
        a2.a();
        d();
    }

    /* access modifiers changed from: package-private */
    public void b() {
        Drawable drawable = this.e;
        if (drawable != null && drawable.isStateful() && drawable.setState(this.f518d.getDrawableState())) {
            this.f518d.invalidateDrawable(drawable);
        }
    }

    /* access modifiers changed from: package-private */
    public void c() {
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }
}
