package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.Log;
import androidx.core.graphics.drawable.d;
import java.lang.reflect.Method;

/* access modifiers changed from: package-private */
public class e extends d {
    private static Method h;

    private static class a extends d.a {
        a(d.a aVar, Resources resources) {
            super(aVar, resources);
        }

        @Override // androidx.core.graphics.drawable.d.a
        public Drawable newDrawable(Resources resources) {
            return new e(this, resources);
        }
    }

    e(Drawable drawable) {
        super(drawable);
        d();
    }

    e(d.a aVar, Resources resources) {
        super(aVar, resources);
        d();
    }

    private void d() {
        if (h == null) {
            try {
                h = Drawable.class.getDeclaredMethod("isProjected", new Class[0]);
            } catch (Exception e) {
                Log.w("WrappedDrawableApi21", "Failed to retrieve Drawable#isProjected() method", e);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.core.graphics.drawable.d
    public boolean b() {
        if (Build.VERSION.SDK_INT != 21) {
            return false;
        }
        Drawable drawable = this.g;
        return (drawable instanceof GradientDrawable) || (drawable instanceof DrawableContainer) || (drawable instanceof InsetDrawable) || (drawable instanceof RippleDrawable);
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.core.graphics.drawable.d
    public d.a c() {
        return new a(this.e, null);
    }

    public Rect getDirtyBounds() {
        return this.g.getDirtyBounds();
    }

    public void getOutline(Outline outline) {
        this.g.getOutline(outline);
    }

    public boolean isProjected() {
        Method method;
        Drawable drawable = this.g;
        if (!(drawable == null || (method = h) == null)) {
            try {
                return ((Boolean) method.invoke(drawable, new Object[0])).booleanValue();
            } catch (Exception e) {
                Log.w("WrappedDrawableApi21", "Error calling Drawable#isProjected() method", e);
            }
        }
        return false;
    }

    public void setHotspot(float f, float f2) {
        this.g.setHotspot(f, f2);
    }

    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        this.g.setHotspotBounds(i, i2, i3, i4);
    }

    @Override // androidx.core.graphics.drawable.d
    public boolean setState(int[] iArr) {
        if (!super.setState(iArr)) {
            return false;
        }
        invalidateSelf();
        return true;
    }

    @Override // androidx.core.graphics.drawable.d, androidx.core.graphics.drawable.b
    public void setTint(int i) {
        if (b()) {
            super.setTint(i);
        } else {
            this.g.setTint(i);
        }
    }

    @Override // androidx.core.graphics.drawable.d, androidx.core.graphics.drawable.b
    public void setTintList(ColorStateList colorStateList) {
        if (b()) {
            super.setTintList(colorStateList);
        } else {
            this.g.setTintList(colorStateList);
        }
    }

    @Override // androidx.core.graphics.drawable.d, androidx.core.graphics.drawable.b
    public void setTintMode(PorterDuff.Mode mode) {
        if (b()) {
            super.setTintMode(mode);
        } else {
            this.g.setTintMode(mode);
        }
    }
}
