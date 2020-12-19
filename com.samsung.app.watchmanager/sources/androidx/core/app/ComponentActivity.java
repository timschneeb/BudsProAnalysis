package androidx.core.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.f;
import androidx.lifecycle.h;
import androidx.lifecycle.j;
import b.c.i;
import b.e.g.C0114d;

public class ComponentActivity extends Activity implements h, C0114d.a {

    /* renamed from: a  reason: collision with root package name */
    private i<Class<? extends Object>, Object> f551a = new i<>();

    /* renamed from: b  reason: collision with root package name */
    private j f552b = new j(this);

    @Override // androidx.lifecycle.h
    public f a() {
        return this.f552b;
    }

    @Override // b.e.g.C0114d.a
    public boolean a(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        View decorView = getWindow().getDecorView();
        if (decorView == null || !C0114d.a(decorView, keyEvent)) {
            return C0114d.a(this, decorView, this, keyEvent);
        }
        return true;
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        View decorView = getWindow().getDecorView();
        if (decorView == null || !C0114d.a(decorView, keyEvent)) {
            return super.dispatchKeyShortcutEvent(keyEvent);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ReportFragment.a(this);
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        this.f552b.a(f.b.CREATED);
        super.onSaveInstanceState(bundle);
    }
}
