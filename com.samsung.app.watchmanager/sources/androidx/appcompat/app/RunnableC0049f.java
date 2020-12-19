package androidx.appcompat.app;

import android.view.View;

/* renamed from: androidx.appcompat.app.f  reason: case insensitive filesystem */
class RunnableC0049f implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ View f174a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ View f175b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ AlertController f176c;

    RunnableC0049f(AlertController alertController, View view, View view2) {
        this.f176c = alertController;
        this.f174a = view;
        this.f175b = view2;
    }

    public void run() {
        AlertController.a(this.f176c.g, this.f174a, this.f175b);
    }
}
