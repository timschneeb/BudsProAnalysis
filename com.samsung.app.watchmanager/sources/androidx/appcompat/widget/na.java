package androidx.appcompat.widget;

import android.view.View;
import android.view.Window;
import androidx.appcompat.view.menu.C0051a;

/* access modifiers changed from: package-private */
public class na implements View.OnClickListener {

    /* renamed from: a  reason: collision with root package name */
    final C0051a f484a = new C0051a(this.f485b.f497a.getContext(), 0, 16908332, 0, 0, this.f485b.i);

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ pa f485b;

    na(pa paVar) {
        this.f485b = paVar;
    }

    public void onClick(View view) {
        pa paVar = this.f485b;
        Window.Callback callback = paVar.l;
        if (callback != null && paVar.m) {
            callback.onMenuItemSelected(0, this.f484a);
        }
    }
}
