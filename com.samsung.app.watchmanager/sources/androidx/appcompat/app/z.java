package androidx.appcompat.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import b.a.a;
import b.a.d.b;
import b.e.g.C0114d;

public class z extends Dialog implements l {

    /* renamed from: a  reason: collision with root package name */
    private m f203a;

    /* renamed from: b  reason: collision with root package name */
    private final C0114d.a f204b = new y(this);

    public z(Context context, int i) {
        super(context, a(context, i));
        a().a((Bundle) null);
        a().a();
    }

    private static int a(Context context, int i) {
        if (i != 0) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(a.dialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    public m a() {
        if (this.f203a == null) {
            this.f203a = m.a(this, this);
        }
        return this.f203a;
    }

    @Override // androidx.appcompat.app.l
    public b a(b.a aVar) {
        return null;
    }

    @Override // androidx.appcompat.app.l
    public void a(b bVar) {
    }

    public boolean a(int i) {
        return a().b(i);
    }

    /* access modifiers changed from: package-private */
    public boolean a(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        a().a(view, layoutParams);
    }

    @Override // androidx.appcompat.app.l
    public void b(b bVar) {
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return C0114d.a(this.f204b, getWindow().getDecorView(), this, keyEvent);
    }

    @Override // android.app.Dialog
    public <T extends View> T findViewById(int i) {
        return (T) a().a(i);
    }

    public void invalidateOptionsMenu() {
        a().f();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        a().e();
        super.onCreate(bundle);
        a().a(bundle);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        a().j();
    }

    @Override // android.app.Dialog
    public void setContentView(int i) {
        a().c(i);
    }

    @Override // android.app.Dialog
    public void setContentView(View view) {
        a().a(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        a().b(view, layoutParams);
    }

    @Override // android.app.Dialog
    public void setTitle(int i) {
        super.setTitle(i);
        a().a(getContext().getString(i));
    }

    @Override // android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        a().a(charSequence);
    }
}
