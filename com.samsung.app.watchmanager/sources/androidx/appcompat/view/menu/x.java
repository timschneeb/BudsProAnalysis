package androidx.appcompat.view.menu;

import android.content.Context;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import b.e.b.a.a;
import b.e.b.a.b;
import b.e.b.a.c;

public final class x {
    public static Menu a(Context context, a aVar) {
        return new y(context, aVar);
    }

    public static MenuItem a(Context context, b bVar) {
        return Build.VERSION.SDK_INT >= 16 ? new r(context, bVar) : new q(context, bVar);
    }

    public static SubMenu a(Context context, c cVar) {
        return new E(context, cVar);
    }
}
