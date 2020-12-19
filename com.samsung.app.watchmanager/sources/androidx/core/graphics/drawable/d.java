package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;

/* access modifiers changed from: package-private */
public class d extends Drawable implements Drawable.Callback, c, b {

    /* renamed from: a  reason: collision with root package name */
    static final PorterDuff.Mode f624a = PorterDuff.Mode.SRC_IN;

    /* renamed from: b  reason: collision with root package name */
    private int f625b;

    /* renamed from: c  reason: collision with root package name */
    private PorterDuff.Mode f626c;

    /* renamed from: d  reason: collision with root package name */
    private boolean f627d;
    a e;
    private boolean f;
    Drawable g;

    /* access modifiers changed from: protected */
    public static abstract class a extends Drawable.ConstantState {

        /* renamed from: a  reason: collision with root package name */
        int f628a;

        /* renamed from: b  reason: collision with root package name */
        Drawable.ConstantState f629b;

        /* renamed from: c  reason: collision with root package name */
        ColorStateList f630c = null;

        /* renamed from: d  reason: collision with root package name */
        PorterDuff.Mode f631d = d.f624a;

        a(a aVar, Resources resources) {
            if (aVar != null) {
                this.f628a = aVar.f628a;
                this.f629b = aVar.f629b;
                this.f630c = aVar.f630c;
                this.f631d = aVar.f631d;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            return this.f629b != null;
        }

        public int getChangingConfigurations() {
            int i = this.f628a;
            Drawable.ConstantState constantState = this.f629b;
            return i | (constantState != null ? constantState.getChangingConfigurations() : 0);
        }

        public Drawable newDrawable() {
            return newDrawable(null);
        }

        public abstract Drawable newDrawable(Resources resources);
    }

    /* access modifiers changed from: private */
    public static class b extends a {
        b(a aVar, Resources resources) {
            super(aVar, resources);
        }

        @Override // androidx.core.graphics.drawable.d.a
        public Drawable newDrawable(Resources resources) {
            return new d(this, resources);
        }
    }

    d(Drawable drawable) {
        this.e = c();
        a(drawable);
    }

    d(a aVar, Resources resources) {
        this.e = aVar;
        a(resources);
    }

    private void a(Resources resources) {
        Drawable.ConstantState constantState;
        a aVar = this.e;
        if (aVar != null && (constantState = aVar.f629b) != null) {
            a(constantState.newDrawable(resources));
        }
    }

    private boolean a(int[] iArr) {
        if (!b()) {
            return false;
        }
        a aVar = this.e;
        ColorStateList colorStateList = aVar.f630c;
        PorterDuff.Mode mode = aVar.f631d;
        if (colorStateList == null || mode == null) {
            this.f627d = false;
            clearColorFilter();
        } else {
            int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
            if (!(this.f627d && colorForState == this.f625b && mode == this.f626c)) {
                setColorFilter(colorForState, mode);
                this.f625b = colorForState;
                this.f626c = mode;
                this.f627d = true;
                return true;
            }
        }
        return false;
    }

    @Override // androidx.core.graphics.drawable.c
    public final Drawable a() {
        return this.g;
    }

    @Override // androidx.core.graphics.drawable.c
    public final void a(Drawable drawable) {
        Drawable drawable2 = this.g;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.g = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            setVisible(drawable.isVisible(), true);
            setState(drawable.getState());
            setLevel(drawable.getLevel());
            setBounds(drawable.getBounds());
            a aVar = this.e;
            if (aVar != null) {
                aVar.f629b = drawable.getConstantState();
            }
        }
        invalidateSelf();
    }

    /* access modifiers changed from: protected */
    public boolean b() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public a c() {
        return new b(this.e, null);
    }

    public void draw(Canvas canvas) {
        this.g.draw(canvas);
    }

    public int getChangingConfigurations() {
        int changingConfigurations = super.getChangingConfigurations();
        a aVar = this.e;
        return changingConfigurations | (aVar != null ? aVar.getChangingConfigurations() : 0) | this.g.getChangingConfigurations();
    }

    public Drawable.ConstantState getConstantState() {
        a aVar = this.e;
        if (aVar == null || !aVar.a()) {
            return null;
        }
        this.e.f628a = getChangingConfigurations();
        return this.e;
    }

    public Drawable getCurrent() {
        return this.g.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.g.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.g.getIntrinsicWidth();
    }

    public int getMinimumHeight() {
        return this.g.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.g.getMinimumWidth();
    }

    public int getOpacity() {
        return this.g.getOpacity();
    }

    public boolean getPadding(Rect rect) {
        return this.g.getPadding(rect);
    }

    public int[] getState() {
        return this.g.getState();
    }

    public Region getTransparentRegion() {
        return this.g.getTransparentRegion();
    }

    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    public boolean isAutoMirrored() {
        return this.g.isAutoMirrored();
    }

    public boolean isStateful() {
        a aVar;
        ColorStateList colorStateList = (!b() || (aVar = this.e) == null) ? null : aVar.f630c;
        return (colorStateList != null && colorStateList.isStateful()) || this.g.isStateful();
    }

    public void jumpToCurrentState() {
        this.g.jumpToCurrentState();
    }

    public Drawable mutate() {
        if (!this.f && super.mutate() == this) {
            this.e = c();
            Drawable drawable = this.g;
            if (drawable != null) {
                drawable.mutate();
            }
            a aVar = this.e;
            if (aVar != null) {
                Drawable drawable2 = this.g;
                aVar.f629b = drawable2 != null ? drawable2.getConstantState() : null;
            }
            this.f = true;
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.g;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int i) {
        return this.g.setLevel(i);
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    public void setAlpha(int i) {
        this.g.setAlpha(i);
    }

    public void setAutoMirrored(boolean z) {
        this.g.setAutoMirrored(z);
    }

    public void setChangingConfigurations(int i) {
        this.g.setChangingConfigurations(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.g.setColorFilter(colorFilter);
    }

    public void setDither(boolean z) {
        this.g.setDither(z);
    }

    public void setFilterBitmap(boolean z) {
        this.g.setFilterBitmap(z);
    }

    public boolean setState(int[] iArr) {
        return a(iArr) || this.g.setState(iArr);
    }

    @Override // androidx.core.graphics.drawable.b
    public void setTint(int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    @Override // androidx.core.graphics.drawable.b
    public void setTintList(ColorStateList colorStateList) {
        this.e.f630c = colorStateList;
        a(getState());
    }

    @Override // androidx.core.graphics.drawable.b
    public void setTintMode(PorterDuff.Mode mode) {
        this.e.f631d = mode;
        a(getState());
    }

    public boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2) || this.g.setVisible(z, z2);
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }
}
