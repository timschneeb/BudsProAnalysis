package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.core.content.a.h;
import b.a.a.a.a;

public class ia {

    /* renamed from: a  reason: collision with root package name */
    private final Context f474a;

    /* renamed from: b  reason: collision with root package name */
    private final TypedArray f475b;

    /* renamed from: c  reason: collision with root package name */
    private TypedValue f476c;

    private ia(Context context, TypedArray typedArray) {
        this.f474a = context;
        this.f475b = typedArray;
    }

    public static ia a(Context context, int i, int[] iArr) {
        return new ia(context, context.obtainStyledAttributes(i, iArr));
    }

    public static ia a(Context context, AttributeSet attributeSet, int[] iArr) {
        return new ia(context, context.obtainStyledAttributes(attributeSet, iArr));
    }

    public static ia a(Context context, AttributeSet attributeSet, int[] iArr, int i, int i2) {
        return new ia(context, context.obtainStyledAttributes(attributeSet, iArr, i, i2));
    }

    public float a(int i, float f) {
        return this.f475b.getFloat(i, f);
    }

    public int a(int i, int i2) {
        return this.f475b.getColor(i, i2);
    }

    public ColorStateList a(int i) {
        int resourceId;
        ColorStateList a2;
        return (!this.f475b.hasValue(i) || (resourceId = this.f475b.getResourceId(i, 0)) == 0 || (a2 = a.a(this.f474a, resourceId)) == null) ? this.f475b.getColorStateList(i) : a2;
    }

    public Typeface a(int i, int i2, h.a aVar) {
        int resourceId = this.f475b.getResourceId(i, 0);
        if (resourceId == 0) {
            return null;
        }
        if (this.f476c == null) {
            this.f476c = new TypedValue();
        }
        return h.a(this.f474a, resourceId, this.f476c, i2, aVar);
    }

    public void a() {
        this.f475b.recycle();
    }

    public boolean a(int i, boolean z) {
        return this.f475b.getBoolean(i, z);
    }

    public int b(int i, int i2) {
        return this.f475b.getDimensionPixelOffset(i, i2);
    }

    public Drawable b(int i) {
        int resourceId;
        return (!this.f475b.hasValue(i) || (resourceId = this.f475b.getResourceId(i, 0)) == 0) ? this.f475b.getDrawable(i) : a.b(this.f474a, resourceId);
    }

    public int c(int i, int i2) {
        return this.f475b.getDimensionPixelSize(i, i2);
    }

    public Drawable c(int i) {
        int resourceId;
        if (!this.f475b.hasValue(i) || (resourceId = this.f475b.getResourceId(i, 0)) == 0) {
            return null;
        }
        return C0072q.a().a(this.f474a, resourceId, true);
    }

    public int d(int i, int i2) {
        return this.f475b.getInt(i, i2);
    }

    public String d(int i) {
        return this.f475b.getString(i);
    }

    public int e(int i, int i2) {
        return this.f475b.getInteger(i, i2);
    }

    public CharSequence e(int i) {
        return this.f475b.getText(i);
    }

    public int f(int i, int i2) {
        return this.f475b.getLayoutDimension(i, i2);
    }

    public CharSequence[] f(int i) {
        return this.f475b.getTextArray(i);
    }

    public int g(int i, int i2) {
        return this.f475b.getResourceId(i, i2);
    }

    public boolean g(int i) {
        return this.f475b.hasValue(i);
    }
}
