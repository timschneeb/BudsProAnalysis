package androidx.appcompat.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class fa extends ContextWrapper {

    /* renamed from: a  reason: collision with root package name */
    private static final Object f463a = new Object();

    /* renamed from: b  reason: collision with root package name */
    private static ArrayList<WeakReference<fa>> f464b;

    /* renamed from: c  reason: collision with root package name */
    private final Resources f465c;

    /* renamed from: d  reason: collision with root package name */
    private final Resources.Theme f466d;

    private fa(Context context) {
        super(context);
        if (va.b()) {
            this.f465c = new va(this, context.getResources());
            this.f466d = this.f465c.newTheme();
            this.f466d.setTo(context.getTheme());
            return;
        }
        this.f465c = new ha(this, context.getResources());
        this.f466d = null;
    }

    public static Context a(Context context) {
        if (!b(context)) {
            return context;
        }
        synchronized (f463a) {
            if (f464b == null) {
                f464b = new ArrayList<>();
            } else {
                for (int size = f464b.size() - 1; size >= 0; size--) {
                    WeakReference<fa> weakReference = f464b.get(size);
                    if (weakReference == null || weakReference.get() == null) {
                        f464b.remove(size);
                    }
                }
                for (int size2 = f464b.size() - 1; size2 >= 0; size2--) {
                    WeakReference<fa> weakReference2 = f464b.get(size2);
                    fa faVar = weakReference2 != null ? weakReference2.get() : null;
                    if (faVar != null && faVar.getBaseContext() == context) {
                        return faVar;
                    }
                }
            }
            fa faVar2 = new fa(context);
            f464b.add(new WeakReference<>(faVar2));
            return faVar2;
        }
    }

    private static boolean b(Context context) {
        if ((context instanceof fa) || (context.getResources() instanceof ha) || (context.getResources() instanceof va)) {
            return false;
        }
        return Build.VERSION.SDK_INT < 21 || va.b();
    }

    public AssetManager getAssets() {
        return this.f465c.getAssets();
    }

    public Resources getResources() {
        return this.f465c;
    }

    public Resources.Theme getTheme() {
        Resources.Theme theme = this.f466d;
        return theme == null ? super.getTheme() : theme;
    }

    public void setTheme(int i) {
        Resources.Theme theme = this.f466d;
        if (theme == null) {
            super.setTheme(i);
        } else {
            theme.applyStyle(i, true);
        }
    }
}
