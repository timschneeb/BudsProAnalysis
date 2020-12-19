package c.b.b.a.a.a.f;

import android.content.Context;
import c.b.b.a.a.a.f.a.e;
import c.b.b.a.a.c;

public class d {

    /* renamed from: a  reason: collision with root package name */
    private static b f1782a;

    public static b a(Context context, int i, c cVar) {
        b cVar2;
        if (f1782a == null) {
            synchronized (d.class) {
                if (i == 0) {
                    cVar2 = new c.b.b.a.a.a.f.b.c(context, cVar);
                } else if (i == 1) {
                    cVar2 = new e(context, cVar);
                } else if (i == 2 || i == 3) {
                    cVar2 = new c.b.b.a.a.a.f.c.d(context, cVar);
                }
                f1782a = cVar2;
            }
        }
        return f1782a;
    }
}
