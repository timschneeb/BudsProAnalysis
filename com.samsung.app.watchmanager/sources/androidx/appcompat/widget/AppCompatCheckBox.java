package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import androidx.core.widget.n;
import b.a.a;

public class AppCompatCheckBox extends CheckBox implements n {

    /* renamed from: a  reason: collision with root package name */
    private final C0071p f313a;

    public AppCompatCheckBox(Context context) {
        this(context, null);
    }

    public AppCompatCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.checkboxStyle);
    }

    public AppCompatCheckBox(Context context, AttributeSet attributeSet, int i) {
        super(fa.a(context), attributeSet, i);
        this.f313a = new C0071p(this);
        this.f313a.a(attributeSet, i);
    }

    public int getCompoundPaddingLeft() {
        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        C0071p pVar = this.f313a;
        return pVar != null ? pVar.a(compoundPaddingLeft) : compoundPaddingLeft;
    }

    public ColorStateList getSupportButtonTintList() {
        C0071p pVar = this.f313a;
        if (pVar != null) {
            return pVar.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportButtonTintMode() {
        C0071p pVar = this.f313a;
        if (pVar != null) {
            return pVar.c();
        }
        return null;
    }

    @Override // android.widget.CompoundButton
    public void setButtonDrawable(int i) {
        setButtonDrawable(b.a.a.a.a.b(getContext(), i));
    }

    @Override // android.widget.CompoundButton
    public void setButtonDrawable(Drawable drawable) {
        super.setButtonDrawable(drawable);
        C0071p pVar = this.f313a;
        if (pVar != null) {
            pVar.d();
        }
    }

    @Override // androidx.core.widget.n
    public void setSupportButtonTintList(ColorStateList colorStateList) {
        C0071p pVar = this.f313a;
        if (pVar != null) {
            pVar.a(colorStateList);
        }
    }

    @Override // androidx.core.widget.n
    public void setSupportButtonTintMode(PorterDuff.Mode mode) {
        C0071p pVar = this.f313a;
        if (pVar != null) {
            pVar.a(mode);
        }
    }
}
