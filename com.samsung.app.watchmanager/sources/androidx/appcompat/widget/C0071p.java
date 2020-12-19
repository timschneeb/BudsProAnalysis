package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import androidx.core.graphics.drawable.a;
import androidx.core.widget.c;
import b.a.j;

/* access modifiers changed from: package-private */
/* renamed from: androidx.appcompat.widget.p  reason: case insensitive filesystem */
public class C0071p {

    /* renamed from: a  reason: collision with root package name */
    private final CompoundButton f493a;

    /* renamed from: b  reason: collision with root package name */
    private ColorStateList f494b = null;

    /* renamed from: c  reason: collision with root package name */
    private PorterDuff.Mode f495c = null;

    /* renamed from: d  reason: collision with root package name */
    private boolean f496d = false;
    private boolean e = false;
    private boolean f;

    C0071p(CompoundButton compoundButton) {
        this.f493a = compoundButton;
    }

    /* access modifiers changed from: package-private */
    public int a(int i) {
        Drawable a2;
        return (Build.VERSION.SDK_INT >= 17 || (a2 = c.a(this.f493a)) == null) ? i : i + a2.getIntrinsicWidth();
    }

    /* access modifiers changed from: package-private */
    public void a() {
        Drawable a2 = c.a(this.f493a);
        if (a2 == null) {
            return;
        }
        if (this.f496d || this.e) {
            Drawable mutate = a.h(a2).mutate();
            if (this.f496d) {
                a.a(mutate, this.f494b);
            }
            if (this.e) {
                a.a(mutate, this.f495c);
            }
            if (mutate.isStateful()) {
                mutate.setState(this.f493a.getDrawableState());
            }
            this.f493a.setButtonDrawable(mutate);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList) {
        this.f494b = colorStateList;
        this.f496d = true;
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(PorterDuff.Mode mode) {
        this.f495c = mode;
        this.e = true;
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(AttributeSet attributeSet, int i) {
        int resourceId;
        TypedArray obtainStyledAttributes = this.f493a.getContext().obtainStyledAttributes(attributeSet, j.CompoundButton, i, 0);
        try {
            if (obtainStyledAttributes.hasValue(j.CompoundButton_android_button) && (resourceId = obtainStyledAttributes.getResourceId(j.CompoundButton_android_button, 0)) != 0) {
                this.f493a.setButtonDrawable(b.a.a.a.a.b(this.f493a.getContext(), resourceId));
            }
            if (obtainStyledAttributes.hasValue(j.CompoundButton_buttonTint)) {
                c.a(this.f493a, obtainStyledAttributes.getColorStateList(j.CompoundButton_buttonTint));
            }
            if (obtainStyledAttributes.hasValue(j.CompoundButton_buttonTintMode)) {
                c.a(this.f493a, E.a(obtainStyledAttributes.getInt(j.CompoundButton_buttonTintMode, -1), null));
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* access modifiers changed from: package-private */
    public ColorStateList b() {
        return this.f494b;
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode c() {
        return this.f495c;
    }

    /* access modifiers changed from: package-private */
    public void d() {
        if (this.f) {
            this.f = false;
            return;
        }
        this.f = true;
        a();
    }
}
