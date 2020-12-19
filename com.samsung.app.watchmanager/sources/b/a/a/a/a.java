package b.a.a.a;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import androidx.appcompat.widget.C0072q;
import java.util.WeakHashMap;

public final class a {

    /* renamed from: a  reason: collision with root package name */
    private static final ThreadLocal<TypedValue> f1205a = new ThreadLocal<>();

    /* renamed from: b  reason: collision with root package name */
    private static final WeakHashMap<Context, SparseArray<C0018a>> f1206b = new WeakHashMap<>(0);

    /* renamed from: c  reason: collision with root package name */
    private static final Object f1207c = new Object();

    /* access modifiers changed from: private */
    /* renamed from: b.a.a.a.a$a  reason: collision with other inner class name */
    public static class C0018a {

        /* renamed from: a  reason: collision with root package name */
        final ColorStateList f1208a;

        /* renamed from: b  reason: collision with root package name */
        final Configuration f1209b;

        C0018a(ColorStateList colorStateList, Configuration configuration) {
            this.f1208a = colorStateList;
            this.f1209b = configuration;
        }
    }

    public static ColorStateList a(Context context, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColorStateList(i);
        }
        ColorStateList c2 = c(context, i);
        if (c2 != null) {
            return c2;
        }
        ColorStateList d2 = d(context, i);
        if (d2 == null) {
            return androidx.core.content.a.b(context, i);
        }
        a(context, i, d2);
        return d2;
    }

    private static TypedValue a() {
        TypedValue typedValue = f1205a.get();
        if (typedValue != null) {
            return typedValue;
        }
        TypedValue typedValue2 = new TypedValue();
        f1205a.set(typedValue2);
        return typedValue2;
    }

    private static void a(Context context, int i, ColorStateList colorStateList) {
        synchronized (f1207c) {
            SparseArray<C0018a> sparseArray = f1206b.get(context);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>();
                f1206b.put(context, sparseArray);
            }
            sparseArray.append(i, new C0018a(colorStateList, context.getResources().getConfiguration()));
        }
    }

    public static Drawable b(Context context, int i) {
        return C0072q.a().a(context, i);
    }

    private static ColorStateList c(Context context, int i) {
        C0018a aVar;
        synchronized (f1207c) {
            SparseArray<C0018a> sparseArray = f1206b.get(context);
            if (!(sparseArray == null || sparseArray.size() <= 0 || (aVar = sparseArray.get(i)) == null)) {
                if (aVar.f1209b.equals(context.getResources().getConfiguration())) {
                    return aVar.f1208a;
                }
                sparseArray.remove(i);
            }
            return null;
        }
    }

    private static ColorStateList d(Context context, int i) {
        if (e(context, i)) {
            return null;
        }
        Resources resources = context.getResources();
        try {
            return androidx.core.content.a.a.a(resources, resources.getXml(i), context.getTheme());
        } catch (Exception e) {
            Log.e("AppCompatResources", "Failed to inflate ColorStateList, leaving it to the framework", e);
            return null;
        }
    }

    private static boolean e(Context context, int i) {
        Resources resources = context.getResources();
        TypedValue a2 = a();
        resources.getValue(i, a2, true);
        int i2 = a2.type;
        return i2 >= 28 && i2 <= 31;
    }
}
