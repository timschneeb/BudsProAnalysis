package androidx.appcompat.widget;

import android.view.View;
import androidx.appcompat.view.menu.z;

/* renamed from: androidx.appcompat.widget.m  reason: case insensitive filesystem */
class C0068m extends H {
    final /* synthetic */ ActivityChooserView j;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C0068m(ActivityChooserView activityChooserView, View view) {
        super(view);
        this.j = activityChooserView;
    }

    @Override // androidx.appcompat.widget.H
    public z a() {
        return this.j.getListPopupWindow();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.H
    public boolean b() {
        this.j.c();
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.H
    public boolean c() {
        this.j.a();
        return true;
    }
}
