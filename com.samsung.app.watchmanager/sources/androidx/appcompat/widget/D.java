package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import androidx.appcompat.view.menu.v;
import b.e.g.z;

public interface D {
    z a(int i, long j);

    void a(int i);

    void a(ScrollingTabContainerView scrollingTabContainerView);

    void a(boolean z);

    boolean a();

    void b(int i);

    void b(boolean z);

    boolean b();

    void c(int i);

    boolean c();

    void collapseActionView();

    boolean d();

    boolean e();

    void f();

    boolean g();

    CharSequence getTitle();

    int h();

    ViewGroup i();

    Context j();

    int k();

    void l();

    void m();

    void setIcon(int i);

    void setIcon(Drawable drawable);

    void setMenu(Menu menu, v.a aVar);

    void setMenuPrepared();

    void setWindowCallback(Window.Callback callback);

    void setWindowTitle(CharSequence charSequence);
}
