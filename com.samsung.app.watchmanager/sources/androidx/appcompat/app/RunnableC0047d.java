package androidx.appcompat.app;

import android.view.View;

/* renamed from: androidx.appcompat.app.d  reason: case insensitive filesystem */
class RunnableC0047d implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ View f168a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ View f169b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ AlertController f170c;

    RunnableC0047d(AlertController alertController, View view, View view2) {
        this.f170c = alertController;
        this.f168a = view;
        this.f169b = view2;
    }

    public void run() {
        AlertController.a(this.f170c.A, this.f168a, this.f169b);
    }
}
