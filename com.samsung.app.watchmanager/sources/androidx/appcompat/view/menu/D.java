package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.appcompat.view.menu.l;

public class D extends l implements SubMenu {
    private l B;
    private p C;

    public D(Context context, l lVar, p pVar) {
        super(context);
        this.B = lVar;
        this.C = pVar;
    }

    @Override // androidx.appcompat.view.menu.l
    public void a(l.a aVar) {
        this.B.a(aVar);
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.appcompat.view.menu.l
    public boolean a(l lVar, MenuItem menuItem) {
        return super.a(lVar, menuItem) || this.B.a(lVar, menuItem);
    }

    @Override // androidx.appcompat.view.menu.l
    public boolean a(p pVar) {
        return this.B.a(pVar);
    }

    @Override // androidx.appcompat.view.menu.l
    public boolean b(p pVar) {
        return this.B.b(pVar);
    }

    @Override // androidx.appcompat.view.menu.l
    public String d() {
        p pVar = this.C;
        int itemId = pVar != null ? pVar.getItemId() : 0;
        if (itemId == 0) {
            return null;
        }
        return super.d() + ":" + itemId;
    }

    public MenuItem getItem() {
        return this.C;
    }

    @Override // androidx.appcompat.view.menu.l
    public l m() {
        return this.B.m();
    }

    @Override // androidx.appcompat.view.menu.l
    public boolean o() {
        return this.B.o();
    }

    @Override // androidx.appcompat.view.menu.l
    public boolean p() {
        return this.B.p();
    }

    @Override // androidx.appcompat.view.menu.l
    public boolean q() {
        return this.B.q();
    }

    @Override // androidx.appcompat.view.menu.l
    public void setGroupDividerEnabled(boolean z) {
        this.B.setGroupDividerEnabled(z);
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(int i) {
        super.d(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(Drawable drawable) {
        super.a(drawable);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(int i) {
        super.e(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(CharSequence charSequence) {
        super.a(charSequence);
        return this;
    }

    public SubMenu setHeaderView(View view) {
        super.a(view);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(int i) {
        this.C.setIcon(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(Drawable drawable) {
        this.C.setIcon(drawable);
        return this;
    }

    @Override // androidx.appcompat.view.menu.l
    public void setQwertyMode(boolean z) {
        this.B.setQwertyMode(z);
    }

    public Menu t() {
        return this.B;
    }
}
