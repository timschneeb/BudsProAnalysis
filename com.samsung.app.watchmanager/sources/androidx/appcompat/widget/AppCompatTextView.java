package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import androidx.core.widget.b;
import androidx.core.widget.m;
import b.e.e.a;
import b.e.g.q;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppCompatTextView extends TextView implements q, b {

    /* renamed from: a  reason: collision with root package name */
    private final C0070o f337a;

    /* renamed from: b  reason: collision with root package name */
    private final A f338b;

    /* renamed from: c  reason: collision with root package name */
    private Future<a> f339c;

    public AppCompatTextView(Context context) {
        this(context, null);
    }

    public AppCompatTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public AppCompatTextView(Context context, AttributeSet attributeSet, int i) {
        super(fa.a(context), attributeSet, i);
        this.f337a = new C0070o(this);
        this.f337a.a(attributeSet, i);
        this.f338b = new A(this);
        this.f338b.a(attributeSet, i);
        this.f338b.a();
    }

    private void d() {
        Future<a> future = this.f339c;
        if (future != null) {
            try {
                this.f339c = null;
                m.a(this, future.get());
            } catch (InterruptedException | ExecutionException unused) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C0070o oVar = this.f337a;
        if (oVar != null) {
            oVar.a();
        }
        A a2 = this.f338b;
        if (a2 != null) {
            a2.a();
        }
    }

    public int getAutoSizeMaxTextSize() {
        if (b.f650a) {
            return super.getAutoSizeMaxTextSize();
        }
        A a2 = this.f338b;
        if (a2 != null) {
            return a2.c();
        }
        return -1;
    }

    public int getAutoSizeMinTextSize() {
        if (b.f650a) {
            return super.getAutoSizeMinTextSize();
        }
        A a2 = this.f338b;
        if (a2 != null) {
            return a2.d();
        }
        return -1;
    }

    public int getAutoSizeStepGranularity() {
        if (b.f650a) {
            return super.getAutoSizeStepGranularity();
        }
        A a2 = this.f338b;
        if (a2 != null) {
            return a2.e();
        }
        return -1;
    }

    public int[] getAutoSizeTextAvailableSizes() {
        if (b.f650a) {
            return super.getAutoSizeTextAvailableSizes();
        }
        A a2 = this.f338b;
        return a2 != null ? a2.f() : new int[0];
    }

    public int getAutoSizeTextType() {
        if (b.f650a) {
            return super.getAutoSizeTextType() == 1 ? 1 : 0;
        }
        A a2 = this.f338b;
        if (a2 != null) {
            return a2.g();
        }
        return 0;
    }

    public int getFirstBaselineToTopHeight() {
        return m.a(this);
    }

    public int getLastBaselineToBottomHeight() {
        return m.b(this);
    }

    @Override // b.e.g.q
    public ColorStateList getSupportBackgroundTintList() {
        C0070o oVar = this.f337a;
        if (oVar != null) {
            return oVar.b();
        }
        return null;
    }

    @Override // b.e.g.q
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0070o oVar = this.f337a;
        if (oVar != null) {
            return oVar.c();
        }
        return null;
    }

    public CharSequence getText() {
        d();
        return super.getText();
    }

    public a.C0022a getTextMetricsParamsCompat() {
        return m.c(this);
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        r.a(onCreateInputConnection, editorInfo, this);
        return onCreateInputConnection;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        A a2 = this.f338b;
        if (a2 != null) {
            a2.a(z, i, i2, i3, i4);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        d();
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        A a2 = this.f338b;
        if (a2 != null && !b.f650a && a2.h()) {
            this.f338b.b();
        }
    }

    public void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) {
        if (b.f650a) {
            super.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
            return;
        }
        A a2 = this.f338b;
        if (a2 != null) {
            a2.a(i, i2, i3, i4);
        }
    }

    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] iArr, int i) {
        if (b.f650a) {
            super.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
            return;
        }
        A a2 = this.f338b;
        if (a2 != null) {
            a2.a(iArr, i);
        }
    }

    public void setAutoSizeTextTypeWithDefaults(int i) {
        if (b.f650a) {
            super.setAutoSizeTextTypeWithDefaults(i);
            return;
        }
        A a2 = this.f338b;
        if (a2 != null) {
            a2.a(i);
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0070o oVar = this.f337a;
        if (oVar != null) {
            oVar.a(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        C0070o oVar = this.f337a;
        if (oVar != null) {
            oVar.a(i);
        }
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(m.a(this, callback));
    }

    public void setFirstBaselineToTopHeight(int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            super.setFirstBaselineToTopHeight(i);
        } else {
            m.a(this, i);
        }
    }

    public void setLastBaselineToBottomHeight(int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            super.setLastBaselineToBottomHeight(i);
        } else {
            m.b(this, i);
        }
    }

    public void setLineHeight(int i) {
        m.c(this, i);
    }

    public void setPrecomputedText(a aVar) {
        m.a(this, aVar);
    }

    @Override // b.e.g.q
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0070o oVar = this.f337a;
        if (oVar != null) {
            oVar.b(colorStateList);
        }
    }

    @Override // b.e.g.q
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0070o oVar = this.f337a;
        if (oVar != null) {
            oVar.a(mode);
        }
    }

    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        A a2 = this.f338b;
        if (a2 != null) {
            a2.a(context, i);
        }
    }

    public void setTextFuture(Future<a> future) {
        this.f339c = future;
        requestLayout();
    }

    public void setTextMetricsParamsCompat(a.C0022a aVar) {
        m.a(this, aVar);
    }

    public void setTextSize(int i, float f) {
        if (b.f650a) {
            super.setTextSize(i, f);
            return;
        }
        A a2 = this.f338b;
        if (a2 != null) {
            a2.a(i, f);
        }
    }
}
