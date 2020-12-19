package androidx.appcompat.widget;

import android.view.MenuItem;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;

/* access modifiers changed from: package-private */
public class ja implements ActionMenuView.d {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Toolbar f478a;

    ja(Toolbar toolbar) {
        this.f478a = toolbar;
    }

    @Override // androidx.appcompat.widget.ActionMenuView.d
    public boolean onMenuItemClick(MenuItem menuItem) {
        Toolbar.b bVar = this.f478a.G;
        if (bVar != null) {
            return bVar.onMenuItemClick(menuItem);
        }
        return false;
    }
}
