package b.a.d;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.widget.ActionBarContextView;
import b.a.d.b;
import java.lang.ref.WeakReference;

public class e extends b implements l.a {

    /* renamed from: c  reason: collision with root package name */
    private Context f1236c;

    /* renamed from: d  reason: collision with root package name */
    private ActionBarContextView f1237d;
    private b.a e;
    private WeakReference<View> f;
    private boolean g;
    private boolean h;
    private l i;

    public e(Context context, ActionBarContextView actionBarContextView, b.a aVar, boolean z) {
        this.f1236c = context;
        this.f1237d = actionBarContextView;
        this.e = aVar;
        l lVar = new l(actionBarContextView.getContext());
        lVar.c(1);
        this.i = lVar;
        this.i.a(this);
        this.h = z;
    }

    @Override // b.a.d.b
    public void a() {
        if (!this.g) {
            this.g = true;
            this.f1237d.sendAccessibilityEvent(32);
            this.e.a(this);
        }
    }

    @Override // b.a.d.b
    public void a(int i2) {
        a((CharSequence) this.f1236c.getString(i2));
    }

    @Override // b.a.d.b
    public void a(View view) {
        this.f1237d.setCustomView(view);
        this.f = view != null ? new WeakReference<>(view) : null;
    }

    @Override // androidx.appcompat.view.menu.l.a
    public void a(l lVar) {
        i();
        this.f1237d.d();
    }

    @Override // b.a.d.b
    public void a(CharSequence charSequence) {
        this.f1237d.setSubtitle(charSequence);
    }

    @Override // b.a.d.b
    public void a(boolean z) {
        super.a(z);
        this.f1237d.setTitleOptional(z);
    }

    @Override // androidx.appcompat.view.menu.l.a
    public boolean a(l lVar, MenuItem menuItem) {
        return this.e.a(this, menuItem);
    }

    @Override // b.a.d.b
    public View b() {
        WeakReference<View> weakReference = this.f;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    @Override // b.a.d.b
    public void b(int i2) {
        b(this.f1236c.getString(i2));
    }

    @Override // b.a.d.b
    public void b(CharSequence charSequence) {
        this.f1237d.setTitle(charSequence);
    }

    @Override // b.a.d.b
    public Menu c() {
        return this.i;
    }

    @Override // b.a.d.b
    public MenuInflater d() {
        return new g(this.f1237d.getContext());
    }

    @Override // b.a.d.b
    public CharSequence e() {
        return this.f1237d.getSubtitle();
    }

    @Override // b.a.d.b
    public CharSequence g() {
        return this.f1237d.getTitle();
    }

    @Override // b.a.d.b
    public void i() {
        this.e.b(this, this.i);
    }

    @Override // b.a.d.b
    public boolean j() {
        return this.f1237d.b();
    }
}
