package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import b.e.b.a.b;
import b.e.b.a.c;
import java.util.Iterator;
import java.util.Map;

/* renamed from: androidx.appcompat.view.menu.c  reason: case insensitive filesystem */
abstract class AbstractC0053c<T> extends C0054d<T> {

    /* renamed from: b  reason: collision with root package name */
    final Context f226b;

    /* renamed from: c  reason: collision with root package name */
    private Map<b, MenuItem> f227c;

    /* renamed from: d  reason: collision with root package name */
    private Map<c, SubMenu> f228d;

    AbstractC0053c(Context context, T t) {
        super(t);
        this.f226b = context;
    }

    /* access modifiers changed from: package-private */
    public final MenuItem a(MenuItem menuItem) {
        if (!(menuItem instanceof b)) {
            return menuItem;
        }
        b bVar = (b) menuItem;
        if (this.f227c == null) {
            this.f227c = new b.c.b();
        }
        MenuItem menuItem2 = this.f227c.get(menuItem);
        if (menuItem2 != null) {
            return menuItem2;
        }
        MenuItem a2 = x.a(this.f226b, bVar);
        this.f227c.put(bVar, a2);
        return a2;
    }

    /* access modifiers changed from: package-private */
    public final SubMenu a(SubMenu subMenu) {
        if (!(subMenu instanceof c)) {
            return subMenu;
        }
        c cVar = (c) subMenu;
        if (this.f228d == null) {
            this.f228d = new b.c.b();
        }
        SubMenu subMenu2 = this.f228d.get(cVar);
        if (subMenu2 != null) {
            return subMenu2;
        }
        SubMenu a2 = x.a(this.f226b, cVar);
        this.f228d.put(cVar, a2);
        return a2;
    }

    /* access modifiers changed from: package-private */
    public final void a(int i) {
        Map<b, MenuItem> map = this.f227c;
        if (map != null) {
            Iterator<b> it = map.keySet().iterator();
            while (it.hasNext()) {
                if (i == it.next().getGroupId()) {
                    it.remove();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void b() {
        Map<b, MenuItem> map = this.f227c;
        if (map != null) {
            map.clear();
        }
        Map<c, SubMenu> map2 = this.f228d;
        if (map2 != null) {
            map2.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public final void b(int i) {
        Map<b, MenuItem> map = this.f227c;
        if (map != null) {
            Iterator<b> it = map.keySet().iterator();
            while (it.hasNext()) {
                if (i == it.next().getItemId()) {
                    it.remove();
                    return;
                }
            }
        }
    }
}
