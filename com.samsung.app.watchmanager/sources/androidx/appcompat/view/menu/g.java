package androidx.appcompat.view.menu;

import android.view.MenuItem;
import androidx.appcompat.view.menu.i;

class g implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ i.a f232a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ MenuItem f233b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ l f234c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ h f235d;

    g(h hVar, i.a aVar, MenuItem menuItem, l lVar) {
        this.f235d = hVar;
        this.f232a = aVar;
        this.f233b = menuItem;
        this.f234c = lVar;
    }

    public void run() {
        i.a aVar = this.f232a;
        if (aVar != null) {
            this.f235d.f236a.B = true;
            aVar.f241b.a(false);
            this.f235d.f236a.B = false;
        }
        if (this.f233b.isEnabled() && this.f233b.hasSubMenu()) {
            this.f234c.a(this.f233b, 4);
        }
    }
}
