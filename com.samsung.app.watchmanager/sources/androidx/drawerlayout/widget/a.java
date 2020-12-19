package androidx.drawerlayout.widget;

import android.view.View;
import android.view.WindowInsets;

class a implements View.OnApplyWindowInsetsListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ DrawerLayout f682a;

    a(DrawerLayout drawerLayout) {
        this.f682a = drawerLayout;
    }

    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        ((DrawerLayout) view).setChildInsets(windowInsets, windowInsets.getSystemWindowInsetTop() > 0);
        return windowInsets.consumeSystemWindowInsets();
    }
}
