package androidx.appcompat.widget;

import android.view.ViewTreeObserver;
import androidx.appcompat.widget.AppCompatSpinner;

/* access modifiers changed from: package-private */
/* renamed from: androidx.appcompat.widget.x  reason: case insensitive filesystem */
public class ViewTreeObserver$OnGlobalLayoutListenerC0078x implements ViewTreeObserver.OnGlobalLayoutListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AppCompatSpinner.b f528a;

    ViewTreeObserver$OnGlobalLayoutListenerC0078x(AppCompatSpinner.b bVar) {
        this.f528a = bVar;
    }

    public void onGlobalLayout() {
        AppCompatSpinner.b bVar = this.f528a;
        if (!bVar.b(AppCompatSpinner.this)) {
            this.f528a.dismiss();
            return;
        }
        this.f528a.l();
        ViewTreeObserver$OnGlobalLayoutListenerC0078x.super.c();
    }
}
