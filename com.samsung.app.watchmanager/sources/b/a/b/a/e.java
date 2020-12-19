package b.a.b.a;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.a;

public class e extends Drawable implements Drawable.Callback {

    /* renamed from: a  reason: collision with root package name */
    private Drawable f1227a;

    public e(Drawable drawable) {
        a(drawable);
    }

    public Drawable a() {
        return this.f1227a;
    }

    public void a(Drawable drawable) {
        Drawable drawable2 = this.f1227a;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.f1227a = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
    }

    public void draw(Canvas canvas) {
        this.f1227a.draw(canvas);
    }

    public int getChangingConfigurations() {
        return this.f1227a.getChangingConfigurations();
    }

    public Drawable getCurrent() {
        return this.f1227a.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.f1227a.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.f1227a.getIntrinsicWidth();
    }

    public int getMinimumHeight() {
        return this.f1227a.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.f1227a.getMinimumWidth();
    }

    public int getOpacity() {
        return this.f1227a.getOpacity();
    }

    public boolean getPadding(Rect rect) {
        return this.f1227a.getPadding(rect);
    }

    public int[] getState() {
        return this.f1227a.getState();
    }

    public Region getTransparentRegion() {
        return this.f1227a.getTransparentRegion();
    }

    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    public boolean isAutoMirrored() {
        return a.e(this.f1227a);
    }

    public boolean isStateful() {
        return this.f1227a.isStateful();
    }

    public void jumpToCurrentState() {
        a.f(this.f1227a);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.f1227a.setBounds(rect);
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int i) {
        return this.f1227a.setLevel(i);
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    public void setAlpha(int i) {
        this.f1227a.setAlpha(i);
    }

    public void setAutoMirrored(boolean z) {
        a.a(this.f1227a, z);
    }

    public void setChangingConfigurations(int i) {
        this.f1227a.setChangingConfigurations(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.f1227a.setColorFilter(colorFilter);
    }

    public void setDither(boolean z) {
        this.f1227a.setDither(z);
    }

    public void setFilterBitmap(boolean z) {
        this.f1227a.setFilterBitmap(z);
    }

    public void setHotspot(float f, float f2) {
        a.a(this.f1227a, f, f2);
    }

    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        a.a(this.f1227a, i, i2, i3, i4);
    }

    public boolean setState(int[] iArr) {
        return this.f1227a.setState(iArr);
    }

    public void setTint(int i) {
        a.b(this.f1227a, i);
    }

    public void setTintList(ColorStateList colorStateList) {
        a.a(this.f1227a, colorStateList);
    }

    public void setTintMode(PorterDuff.Mode mode) {
        a.a(this.f1227a, mode);
    }

    public boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2) || this.f1227a.setVisible(z, z2);
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }
}
