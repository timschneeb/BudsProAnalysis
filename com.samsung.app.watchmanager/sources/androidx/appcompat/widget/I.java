package androidx.appcompat.widget;

import android.view.View;

/* access modifiers changed from: package-private */
public class I implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ListPopupWindow f367a;

    I(ListPopupWindow listPopupWindow) {
        this.f367a = listPopupWindow;
    }

    public void run() {
        View e = this.f367a.e();
        if (e != null && e.getWindowToken() != null) {
            this.f367a.c();
        }
    }
}
