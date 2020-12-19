package c.b.c.a.b;

import android.graphics.Color;
import java.util.Random;

public class b {

    /* renamed from: a  reason: collision with root package name */
    public static Random f1836a;

    /* renamed from: b  reason: collision with root package name */
    public static b f1837b;

    /* renamed from: c  reason: collision with root package name */
    public static final i f1838c = new i(0.0f, 0.0f, 0.0f);

    /* renamed from: d  reason: collision with root package name */
    public static final i f1839d = new i(1.0f, 1.0f, 1.0f);
    public static final i e = new i(1.0f, 0.0f, 0.0f);
    public static final i f = new i(0.0f, 1.0f, 0.0f);
    public static final i g = new i(0.0f, 0.0f, 1.0f);

    b() {
        f1836a = new Random();
    }

    public static float a(float f2, float f3, float f4) {
        return f3 + ((f4 - f3) * f2);
    }

    public static int a(float f2, int i, int i2) {
        return i + ((int) (((float) (i2 - i)) * f2));
    }

    public static b a() {
        if (f1837b == null) {
            f1837b = new b();
        }
        return f1837b;
    }

    public static float b(float f2, float f3, float f4) {
        return Math.max(Math.min(f2, f3), f4);
    }

    public static int b(float f2, int i, int i2) {
        return Color.argb(a(f2, Color.alpha(i), Color.alpha(i2)), a(f2, Color.red(i), Color.red(i2)), a(f2, Color.green(i), Color.green(i2)), a(f2, Color.blue(i), Color.blue(i2)));
    }

    public float a(float f2, float f3) {
        return f2 + (f1836a.nextFloat() * (f3 - f2));
    }

    public int a(int i, int i2) {
        return i2 < i ? i - (Math.abs(f1836a.nextInt()) % (i - i2)) : i2 > i ? i + (Math.abs(f1836a.nextInt()) % (i2 - i)) : i;
    }
}
