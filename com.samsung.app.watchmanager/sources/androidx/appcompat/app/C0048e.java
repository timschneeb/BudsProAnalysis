package androidx.appcompat.app;

import android.view.View;
import android.widget.AbsListView;

/* renamed from: androidx.appcompat.app.e  reason: case insensitive filesystem */
class C0048e implements AbsListView.OnScrollListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ View f171a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ View f172b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ AlertController f173c;

    C0048e(AlertController alertController, View view, View view2) {
        this.f173c = alertController;
        this.f171a = view;
        this.f172b = view2;
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        AlertController.a(absListView, this.f171a, this.f172b);
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
    }
}
