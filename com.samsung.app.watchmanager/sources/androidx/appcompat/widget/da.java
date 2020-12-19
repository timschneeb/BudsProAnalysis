package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import b.e.a.a;

/* access modifiers changed from: package-private */
public class da {

    /* renamed from: a  reason: collision with root package name */
    private static final ThreadLocal<TypedValue> f457a = new ThreadLocal<>();

    /* renamed from: b  reason: collision with root package name */
    static final int[] f458b = {-16842910};

    /* renamed from: c  reason: collision with root package name */
    static final int[] f459c = {16842908};

    /* renamed from: d  reason: collision with root package name */
    static final int[] f460d = {16843518};
    static final int[] e = {16842919};
    static final int[] f = {16842912};
    static final int[] g = {16842913};
    static final int[] h = {-16842919, -16842908};
    static final int[] i = new int[0];
    private static final int[] j = new int[1];

    public static int a(Context context, int i2) {
        ColorStateList c2 = c(context, i2);
        if (c2 != null && c2.isStateful()) {
            return c2.getColorForState(f458b, c2.getDefaultColor());
        }
        TypedValue a2 = a();
        context.getTheme().resolveAttribute(16842803, a2, true);
        return a(context, i2, a2.getFloat());
    }

    static int a(Context context, int i2, float f2) {
        int b2 = b(context, i2);
        return a.b(b2, Math.round(((float) Color.alpha(b2)) * f2));
    }

    private static TypedValue a() {
        TypedValue typedValue = f457a.get();
        if (typedValue != null) {
            return typedValue;
        }
        TypedValue typedValue2 = new TypedValue();
        f457a.set(typedValue2);
        return typedValue2;
    }

    public static int b(Context context, int i2) {
        int[] iArr = j;
        iArr[0] = i2;
        ia a2 = ia.a(context, (AttributeSet) null, iArr);
        try {
            return a2.a(0, 0);
        } finally {
            a2.a();
        }
    }

    public static ColorStateList c(Context context, int i2) {
        int[] iArr = j;
        iArr[0] = i2;
        ia a2 = ia.a(context, (AttributeSet) null, iArr);
        try {
            return a2.a(0);
        } finally {
            a2.a();
        }
    }
}
