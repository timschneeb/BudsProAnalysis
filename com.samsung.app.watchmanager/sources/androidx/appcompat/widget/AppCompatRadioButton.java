package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;
import androidx.core.widget.n;
import b.a.a;

public class AppCompatRadioButton extends RadioButton implements n {

    /* renamed from: a  reason: collision with root package name */
    private final C0071p f327a;

    /* renamed from: b  reason: collision with root package name */
    private final A f328b;

    public AppCompatRadioButton(Context context) {
        this(context, null);
    }

    public AppCompatRadioButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.radioButtonStyle);
    }

    public AppCompatRadioButton(Context context, AttributeSet attributeSet, int i) {
        super(fa.a(context), attributeSet, i);
        this.f327a = new C0071p(this);
        this.f327a.a(attributeSet, i);
        this.f328b = new A(this);
        this.f328b.a(attributeSet, i);
    }

    public int getCompoundPaddingLeft() {
        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        C0071p pVar = this.f327a;
        return pVar != null ? pVar.a(compoundPaddingLeft) : compoundPaddingLeft;
    }

    public ColorStateList getSupportButtonTintList() {
        C0071p pVar = this.f327a;
        if (pVar != null) {
            return pVar.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportButtonTintMode() {
        C0071p pVar = this.f327a;
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
        C0071p pVar = this.f327a;
        if (pVar != null) {
            pVar.d();
        }
    }

    @Override // androidx.core.widget.n
    public void setSupportButtonTintList(ColorStateList colorStateList) {
        C0071p pVar = this.f327a;
        if (pVar != null) {
            pVar.a(colorStateList);
        }
    }

    @Override // androidx.core.widget.n
    public void setSupportButtonTintMode(PorterDuff.Mode mode) {
        C0071p pVar = this.f327a;
        if (pVar != null) {
            pVar.a(mode);
        }
    }
}
