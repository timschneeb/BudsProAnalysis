package b.e.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import androidx.core.content.a.c;
import androidx.core.content.a.h;
import b.c.g;
import b.e.d.f;

public class c {

    /* renamed from: a  reason: collision with root package name */
    private static final j f1329a;

    /* renamed from: b  reason: collision with root package name */
    private static final g<String, Typeface> f1330b = new g<>(16);

    static {
        int i = Build.VERSION.SDK_INT;
        f1329a = i >= 28 ? new g() : i >= 26 ? new f() : (i < 24 || !e.a()) ? Build.VERSION.SDK_INT >= 21 ? new d() : new j() : new e();
    }

    public static Typeface a(Context context, Resources resources, int i, String str, int i2) {
        Typeface a2 = f1329a.a(context, resources, i, str, i2);
        if (a2 != null) {
            f1330b.a(b(resources, i, i2), a2);
        }
        return a2;
    }

    public static Typeface a(Context context, CancellationSignal cancellationSignal, f.b[] bVarArr, int i) {
        return f1329a.a(context, cancellationSignal, bVarArr, i);
    }

    public static Typeface a(Context context, c.a aVar, Resources resources, int i, int i2, h.a aVar2, Handler handler, boolean z) {
        Typeface typeface;
        if (aVar instanceof c.d) {
            c.d dVar = (c.d) aVar;
            boolean z2 = false;
            if (!z ? aVar2 == null : dVar.a() == 0) {
                z2 = true;
            }
            typeface = f.a(context, dVar.b(), aVar2, handler, z2, z ? dVar.c() : -1, i2);
        } else {
            typeface = f1329a.a(context, (c.b) aVar, resources, i2);
            if (aVar2 != null) {
                if (typeface != null) {
                    aVar2.a(typeface, handler);
                } else {
                    aVar2.a(-3, handler);
                }
            }
        }
        if (typeface != null) {
            f1330b.a(b(resources, i, i2), typeface);
        }
        return typeface;
    }

    public static Typeface a(Resources resources, int i, int i2) {
        return f1330b.b(b(resources, i, i2));
    }

    private static String b(Resources resources, int i, int i2) {
        return resources.getResourcePackageName(i) + "-" + i + "-" + i2;
    }
}
