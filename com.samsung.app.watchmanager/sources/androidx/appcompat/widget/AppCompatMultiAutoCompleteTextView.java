package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.MultiAutoCompleteTextView;
import b.a.a;
import b.e.g.q;

public class AppCompatMultiAutoCompleteTextView extends MultiAutoCompleteTextView implements q {

    /* renamed from: a  reason: collision with root package name */
    private static final int[] f322a = {16843126};

    /* renamed from: b  reason: collision with root package name */
    private final C0070o f323b;

    /* renamed from: c  reason: collision with root package name */
    private final A f324c;

    public AppCompatMultiAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public AppCompatMultiAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.autoCompleteTextViewStyle);
    }

    public AppCompatMultiAutoCompleteTextView(Context context, AttributeSet attributeSet, int i) {
        super(fa.a(context), attributeSet, i);
        ia a2 = ia.a(getContext(), attributeSet, f322a, i, 0);
        if (a2.g(0)) {
            setDropDownBackgroundDrawable(a2.b(0));
        }
        a2.a();
        this.f323b = new C0070o(this);
        this.f323b.a(attributeSet, i);
        this.f324c = new A(this);
        this.f324c.a(attributeSet, i);
        this.f324c.a();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C0070o oVar = this.f323b;
        if (oVar != null) {
            oVar.a();
        }
        A a2 = this.f324c;
        if (a2 != null) {
            a2.a();
        }
    }

    @Override // b.e.g.q
    public ColorStateList getSupportBackgroundTintList() {
        C0070o oVar = this.f323b;
        if (oVar != null) {
            return oVar.b();
        }
        return null;
    }

    @Override // b.e.g.q
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0070o oVar = this.f323b;
        if (oVar != null) {
            return oVar.c();
        }
        return null;
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        r.a(onCreateInputConnection, editorInfo, this);
        return onCreateInputConnection;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0070o oVar = this.f323b;
        if (oVar != null) {
            oVar.a(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        C0070o oVar = this.f323b;
        if (oVar != null) {
            oVar.a(i);
        }
    }

    public void setDropDownBackgroundResource(int i) {
        setDropDownBackgroundDrawable(b.a.a.a.a.b(getContext(), i));
    }

    @Override // b.e.g.q
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0070o oVar = this.f323b;
        if (oVar != null) {
            oVar.b(colorStateList);
        }
    }

    @Override // b.e.g.q
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0070o oVar = this.f323b;
        if (oVar != null) {
            oVar.a(mode);
        }
    }

    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        A a2 = this.f324c;
        if (a2 != null) {
            a2.a(context, i);
        }
    }
}
