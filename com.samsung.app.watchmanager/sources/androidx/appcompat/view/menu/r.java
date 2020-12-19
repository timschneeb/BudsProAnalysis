package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.menu.q;
import b.e.b.a.b;
import b.e.g.AbstractC0112b;

/* access modifiers changed from: package-private */
public class r extends q {

    class a extends q.a implements ActionProvider.VisibilityListener {
        AbstractC0112b.AbstractC0026b f;

        public a(Context context, ActionProvider actionProvider) {
            super(context, actionProvider);
        }

        @Override // b.e.g.AbstractC0112b
        public View a(MenuItem menuItem) {
            return this.f266d.onCreateActionView(menuItem);
        }

        @Override // b.e.g.AbstractC0112b
        public void a(AbstractC0112b.AbstractC0026b bVar) {
            this.f = bVar;
            this.f266d.setVisibilityListener(bVar != null ? this : null);
        }

        @Override // b.e.g.AbstractC0112b
        public boolean b() {
            return this.f266d.isVisible();
        }

        @Override // b.e.g.AbstractC0112b
        public boolean e() {
            return this.f266d.overridesItemVisibility();
        }

        public void onActionProviderVisibilityChanged(boolean z) {
            AbstractC0112b.AbstractC0026b bVar = this.f;
            if (bVar != null) {
                bVar.onActionProviderVisibilityChanged(z);
            }
        }
    }

    r(Context context, b bVar) {
        super(context, bVar);
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.appcompat.view.menu.q
    public q.a a(ActionProvider actionProvider) {
        return new a(this.f226b, actionProvider);
    }
}
