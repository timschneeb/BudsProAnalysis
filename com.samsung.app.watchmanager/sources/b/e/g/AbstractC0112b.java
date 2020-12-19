package b.e.g;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* renamed from: b.e.g.b  reason: case insensitive filesystem */
public abstract class AbstractC0112b {

    /* renamed from: a  reason: collision with root package name */
    private final Context f1411a;

    /* renamed from: b  reason: collision with root package name */
    private a f1412b;

    /* renamed from: c  reason: collision with root package name */
    private AbstractC0026b f1413c;

    /* renamed from: b.e.g.b$a */
    public interface a {
        void b(boolean z);
    }

    /* renamed from: b.e.g.b$b  reason: collision with other inner class name */
    public interface AbstractC0026b {
        void onActionProviderVisibilityChanged(boolean z);
    }

    public AbstractC0112b(Context context) {
        this.f1411a = context;
    }

    public View a(MenuItem menuItem) {
        return c();
    }

    public void a(SubMenu subMenu) {
    }

    public void a(a aVar) {
        this.f1412b = aVar;
    }

    public void a(AbstractC0026b bVar) {
        if (!(this.f1413c == null || bVar == null)) {
            Log.w("ActionProvider(support)", "setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this " + getClass().getSimpleName() + " instance while it is still in use somewhere else?");
        }
        this.f1413c = bVar;
    }

    public void a(boolean z) {
        a aVar = this.f1412b;
        if (aVar != null) {
            aVar.b(z);
        }
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return true;
    }

    public abstract View c();

    public boolean d() {
        return false;
    }

    public boolean e() {
        return false;
    }

    public void f() {
        this.f1413c = null;
        this.f1412b = null;
    }
}
