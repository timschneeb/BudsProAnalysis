package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import java.lang.ref.WeakReference;

public class va extends Resources {

    /* renamed from: a  reason: collision with root package name */
    private static boolean f523a = false;

    /* renamed from: b  reason: collision with root package name */
    private final WeakReference<Context> f524b;

    public va(Context context, Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.f524b = new WeakReference<>(context);
    }

    public static boolean a() {
        return f523a;
    }

    public static boolean b() {
        return a() && Build.VERSION.SDK_INT <= 20;
    }

    /* access modifiers changed from: package-private */
    public final Drawable a(int i) {
        return super.getDrawable(i);
    }

    @Override // android.content.res.Resources
    public Drawable getDrawable(int i) {
        Context context = this.f524b.get();
        return context != null ? C0072q.a().a(context, this, i) : super.getDrawable(i);
    }
}
