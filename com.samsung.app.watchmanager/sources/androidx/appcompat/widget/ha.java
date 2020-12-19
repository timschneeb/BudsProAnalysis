package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

/* access modifiers changed from: package-private */
public class ha extends L {

    /* renamed from: b  reason: collision with root package name */
    private final WeakReference<Context> f471b;

    public ha(Context context, Resources resources) {
        super(resources);
        this.f471b = new WeakReference<>(context);
    }

    @Override // androidx.appcompat.widget.L, android.content.res.Resources
    public Drawable getDrawable(int i) {
        Drawable drawable = super.getDrawable(i);
        Context context = this.f471b.get();
        if (!(drawable == null || context == null)) {
            C0072q.a();
            C0072q.a(context, i, drawable);
        }
        return drawable;
    }
}
