package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import b.e.b.a.a;

/* access modifiers changed from: package-private */
public class y extends AbstractC0053c<a> implements Menu {
    y(Context context, a aVar) {
        super(context, aVar);
    }

    @Override // android.view.Menu
    public MenuItem add(int i) {
        return a(this.f229a.add(i));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, int i4) {
        return a(this.f229a.add(i, i2, i3, i4));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return a(this.f229a.add(i, i2, i3, charSequence));
    }

    @Override // android.view.Menu
    public MenuItem add(CharSequence charSequence) {
        return a(this.f229a.add(charSequence));
    }

    public int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        MenuItem[] menuItemArr2 = menuItemArr != null ? new MenuItem[menuItemArr.length] : null;
        int addIntentOptions = this.f229a.addIntentOptions(i, i2, i3, componentName, intentArr, intent, i4, menuItemArr2);
        if (menuItemArr2 != null) {
            int length = menuItemArr2.length;
            for (int i5 = 0; i5 < length; i5++) {
                menuItemArr[i5] = a(menuItemArr2[i5]);
            }
        }
        return addIntentOptions;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i) {
        return a(this.f229a.addSubMenu(i));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return a(this.f229a.addSubMenu(i, i2, i3, i4));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        return a(this.f229a.addSubMenu(i, i2, i3, charSequence));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(CharSequence charSequence) {
        return a(this.f229a.addSubMenu(charSequence));
    }

    public void clear() {
        b();
        this.f229a.clear();
    }

    public void close() {
        this.f229a.close();
    }

    public MenuItem findItem(int i) {
        return a(this.f229a.findItem(i));
    }

    public MenuItem getItem(int i) {
        return a(this.f229a.getItem(i));
    }

    public boolean hasVisibleItems() {
        return this.f229a.hasVisibleItems();
    }

    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return this.f229a.isShortcutKey(i, keyEvent);
    }

    public boolean performIdentifierAction(int i, int i2) {
        return this.f229a.performIdentifierAction(i, i2);
    }

    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        return this.f229a.performShortcut(i, keyEvent, i2);
    }

    public void removeGroup(int i) {
        a(i);
        this.f229a.removeGroup(i);
    }

    public void removeItem(int i) {
        b(i);
        this.f229a.removeItem(i);
    }

    public void setGroupCheckable(int i, boolean z, boolean z2) {
        this.f229a.setGroupCheckable(i, z, z2);
    }

    public void setGroupEnabled(int i, boolean z) {
        this.f229a.setGroupEnabled(i, z);
    }

    public void setGroupVisible(int i, boolean z) {
        this.f229a.setGroupVisible(i, z);
    }

    public void setQwertyMode(boolean z) {
        this.f229a.setQwertyMode(z);
    }

    public int size() {
        return this.f229a.size();
    }
}
