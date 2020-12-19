package androidx.appcompat.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.appcompat.widget.va;
import androidx.core.app.a;
import androidx.core.app.d;
import androidx.core.app.k;
import androidx.fragment.app.FragmentActivity;
import b.a.d.b;

public class AppCompatActivity extends FragmentActivity implements l, k.a, AbstractC0044a {
    private m m;
    private int n = 0;
    private Resources o;

    private boolean a(int i, KeyEvent keyEvent) {
        Window window;
        return Build.VERSION.SDK_INT < 26 && !keyEvent.isCtrlPressed() && !KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState()) && keyEvent.getRepeatCount() == 0 && !KeyEvent.isModifierKey(keyEvent.getKeyCode()) && (window = getWindow()) != null && window.getDecorView() != null && window.getDecorView().dispatchKeyShortcutEvent(keyEvent);
    }

    @Override // androidx.appcompat.app.l
    public b a(b.a aVar) {
        return null;
    }

    public void a(Intent intent) {
        d.a(this, intent);
    }

    public void a(k kVar) {
        kVar.a((Activity) this);
    }

    @Override // androidx.appcompat.app.l
    public void a(b bVar) {
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        h().a(view, layoutParams);
    }

    public void b(k kVar) {
    }

    @Override // androidx.appcompat.app.l
    public void b(b bVar) {
    }

    public boolean b(Intent intent) {
        return d.b(this, intent);
    }

    @Override // androidx.core.app.k.a
    public Intent c() {
        return d.a(this);
    }

    public void closeOptionsMenu() {
        ActionBar i = i();
        if (!getWindow().hasFeature(0)) {
            return;
        }
        if (i == null || !i.e()) {
            super.closeOptionsMenu();
        }
    }

    @Override // androidx.core.app.ComponentActivity
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        ActionBar i = i();
        if (keyCode != 82 || i == null || !i.a(keyEvent)) {
            return super.dispatchKeyEvent(keyEvent);
        }
        return true;
    }

    @Override // android.app.Activity
    public <T extends View> T findViewById(int i) {
        return (T) h().a(i);
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void g() {
        h().f();
    }

    public MenuInflater getMenuInflater() {
        return h().c();
    }

    public Resources getResources() {
        if (this.o == null && va.b()) {
            this.o = new va(this, super.getResources());
        }
        Resources resources = this.o;
        return resources == null ? super.getResources() : resources;
    }

    public m h() {
        if (this.m == null) {
            this.m = m.a(this, this);
        }
        return this.m;
    }

    public ActionBar i() {
        return h().d();
    }

    public void invalidateOptionsMenu() {
        h().f();
    }

    @Deprecated
    public void j() {
    }

    public boolean k() {
        Intent c2 = c();
        if (c2 == null) {
            return false;
        }
        if (b(c2)) {
            k a2 = k.a((Context) this);
            a(a2);
            b(a2);
            a2.a();
            try {
                a.a((Activity) this);
                return true;
            } catch (IllegalStateException unused) {
                finish();
                return true;
            }
        } else {
            a(c2);
            return true;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        h().a(configuration);
        if (this.o != null) {
            this.o.updateConfiguration(configuration, super.getResources().getDisplayMetrics());
        }
    }

    public void onContentChanged() {
        j();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        int i;
        m h = h();
        h.e();
        h.a(bundle);
        if (h.a() && (i = this.n) != 0) {
            if (Build.VERSION.SDK_INT >= 23) {
                onApplyThemeResource(getTheme(), this.n, false);
            } else {
                setTheme(i);
            }
        }
        super.onCreate(bundle);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        h().g();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (a(i, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // androidx.fragment.app.FragmentActivity
    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        ActionBar i2 = i();
        if (menuItem.getItemId() != 16908332 || i2 == null || (i2.g() & 4) == 0) {
            return false;
        }
        return k();
    }

    public boolean onMenuOpened(int i, Menu menu) {
        return super.onMenuOpened(i, menu);
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onPanelClosed(int i, Menu menu) {
        super.onPanelClosed(i, menu);
    }

    /* access modifiers changed from: protected */
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        h().b(bundle);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onPostResume() {
        super.onPostResume();
        h().h();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.core.app.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        h().c(bundle);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onStart() {
        super.onStart();
        h().i();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onStop() {
        super.onStop();
        h().j();
    }

    /* access modifiers changed from: protected */
    public void onTitleChanged(CharSequence charSequence, int i) {
        super.onTitleChanged(charSequence, i);
        h().a(charSequence);
    }

    public void openOptionsMenu() {
        ActionBar i = i();
        if (!getWindow().hasFeature(0)) {
            return;
        }
        if (i == null || !i.k()) {
            super.openOptionsMenu();
        }
    }

    @Override // android.app.Activity
    public void setContentView(int i) {
        h().c(i);
    }

    @Override // android.app.Activity
    public void setContentView(View view) {
        h().a(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        h().b(view, layoutParams);
    }

    @Override // android.view.ContextThemeWrapper, android.app.Activity
    public void setTheme(int i) {
        super.setTheme(i);
        this.n = i;
    }
}
