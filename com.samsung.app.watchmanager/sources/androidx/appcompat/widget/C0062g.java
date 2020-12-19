package androidx.appcompat.widget;

import android.view.View;
import androidx.appcompat.view.menu.z;
import androidx.appcompat.widget.ActionMenuPresenter;

/* access modifiers changed from: package-private */
/* renamed from: androidx.appcompat.widget.g  reason: case insensitive filesystem */
public class C0062g extends H {
    final /* synthetic */ ActionMenuPresenter j;
    final /* synthetic */ ActionMenuPresenter.d k;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C0062g(ActionMenuPresenter.d dVar, View view, ActionMenuPresenter actionMenuPresenter) {
        super(view);
        this.k = dVar;
        this.j = actionMenuPresenter;
    }

    @Override // androidx.appcompat.widget.H
    public z a() {
        ActionMenuPresenter.e eVar = ActionMenuPresenter.this.z;
        if (eVar == null) {
            return null;
        }
        return eVar.b();
    }

    @Override // androidx.appcompat.widget.H
    public boolean b() {
        ActionMenuPresenter.this.i();
        return true;
    }

    @Override // androidx.appcompat.widget.H
    public boolean c() {
        ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
        if (actionMenuPresenter.B != null) {
            return false;
        }
        actionMenuPresenter.e();
        return true;
    }
}
