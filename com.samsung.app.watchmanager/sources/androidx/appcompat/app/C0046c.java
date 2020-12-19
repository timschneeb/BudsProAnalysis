package androidx.appcompat.app;

import android.view.View;
import androidx.core.widget.NestedScrollView;

/* renamed from: androidx.appcompat.app.c  reason: case insensitive filesystem */
class C0046c implements NestedScrollView.b {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ View f165a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ View f166b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ AlertController f167c;

    C0046c(AlertController alertController, View view, View view2) {
        this.f167c = alertController;
        this.f165a = view;
        this.f166b = view2;
    }

    @Override // androidx.core.widget.NestedScrollView.b
    public void a(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
        AlertController.a(nestedScrollView, this.f165a, this.f166b);
    }
}
