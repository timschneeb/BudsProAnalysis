package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import androidx.core.widget.m;
import b.a.a;
import b.e.g.q;

public class AppCompatEditText extends EditText implements q {

    /* renamed from: a  reason: collision with root package name */
    private final C0070o f316a;

    /* renamed from: b  reason: collision with root package name */
    private final A f317b;

    public AppCompatEditText(Context context) {
        this(context, null);
    }

    public AppCompatEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.editTextStyle);
    }

    public AppCompatEditText(Context context, AttributeSet attributeSet, int i) {
        super(fa.a(context), attributeSet, i);
        this.f316a = new C0070o(this);
        this.f316a.a(attributeSet, i);
        this.f317b = new A(this);
        this.f317b.a(attributeSet, i);
        this.f317b.a();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C0070o oVar = this.f316a;
        if (oVar != null) {
            oVar.a();
        }
        A a2 = this.f317b;
        if (a2 != null) {
            a2.a();
        }
    }

    @Override // b.e.g.q
    public ColorStateList getSupportBackgroundTintList() {
        C0070o oVar = this.f316a;
        if (oVar != null) {
            return oVar.b();
        }
        return null;
    }

    @Override // b.e.g.q
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0070o oVar = this.f316a;
        if (oVar != null) {
            return oVar.c();
        }
        return null;
    }

    @Override // android.widget.EditText, android.widget.EditText
    public Editable getText() {
        return Build.VERSION.SDK_INT >= 28 ? super.getText() : super.getEditableText();
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        r.a(onCreateInputConnection, editorInfo, this);
        return onCreateInputConnection;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0070o oVar = this.f316a;
        if (oVar != null) {
            oVar.a(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        C0070o oVar = this.f316a;
        if (oVar != null) {
            oVar.a(i);
        }
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(m.a(this, callback));
    }

    @Override // b.e.g.q
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0070o oVar = this.f316a;
        if (oVar != null) {
            oVar.b(colorStateList);
        }
    }

    @Override // b.e.g.q
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0070o oVar = this.f316a;
        if (oVar != null) {
            oVar.a(mode);
        }
    }

    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        A a2 = this.f317b;
        if (a2 != null) {
            a2.a(context, i);
        }
    }
}
