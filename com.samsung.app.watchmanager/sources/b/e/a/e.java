package b.e.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.core.content.a.c;
import b.c.i;
import b.e.d.f;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

class e extends j {

    /* renamed from: a  reason: collision with root package name */
    private static final Class f1331a;

    /* renamed from: b  reason: collision with root package name */
    private static final Constructor f1332b;

    /* renamed from: c  reason: collision with root package name */
    private static final Method f1333c;

    /* renamed from: d  reason: collision with root package name */
    private static final Method f1334d;

    static {
        Method method;
        Method method2;
        Class<?> cls;
        Constructor<?> constructor = null;
        try {
            cls = Class.forName("android.graphics.FontFamily");
            Constructor<?> constructor2 = cls.getConstructor(new Class[0]);
            method = cls.getMethod("addFontWeightStyle", ByteBuffer.class, Integer.TYPE, List.class, Integer.TYPE, Boolean.TYPE);
            method2 = Typeface.class.getMethod("createFromFamiliesWithDefault", Array.newInstance(cls, 1).getClass());
            constructor = constructor2;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e("TypefaceCompatApi24Impl", e.getClass().getName(), e);
            cls = null;
            method2 = null;
            method = null;
        }
        f1332b = constructor;
        f1331a = cls;
        f1333c = method;
        f1334d = method2;
    }

    e() {
    }

    private static Typeface a(Object obj) {
        try {
            Object newInstance = Array.newInstance(f1331a, 1);
            Array.set(newInstance, 0, obj);
            return (Typeface) f1334d.invoke(null, newInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean a() {
        if (f1333c == null) {
            Log.w("TypefaceCompatApi24Impl", "Unable to collect necessary private methods.Fallback to legacy implementation.");
        }
        return f1333c != null;
    }

    private static boolean a(Object obj, ByteBuffer byteBuffer, int i, int i2, boolean z) {
        try {
            return ((Boolean) f1333c.invoke(obj, byteBuffer, Integer.valueOf(i), null, Integer.valueOf(i2), Boolean.valueOf(z))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object b() {
        try {
            return f1332b.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // b.e.a.j
    public Typeface a(Context context, CancellationSignal cancellationSignal, f.b[] bVarArr, int i) {
        Object b2 = b();
        i iVar = new i();
        for (f.b bVar : bVarArr) {
            Uri c2 = bVar.c();
            ByteBuffer byteBuffer = (ByteBuffer) iVar.get(c2);
            if (byteBuffer == null) {
                byteBuffer = k.a(context, cancellationSignal, c2);
                iVar.put(c2, byteBuffer);
            }
            if (!a(b2, byteBuffer, bVar.b(), bVar.d(), bVar.e())) {
                return null;
            }
        }
        return Typeface.create(a(b2), i);
    }

    @Override // b.e.a.j
    public Typeface a(Context context, c.b bVar, Resources resources, int i) {
        Object b2 = b();
        c.C0011c[] a2 = bVar.a();
        for (c.C0011c cVar : a2) {
            ByteBuffer a3 = k.a(context, resources, cVar.b());
            if (a3 == null || !a(b2, a3, cVar.c(), cVar.e(), cVar.f())) {
                return null;
            }
        }
        return a(b2);
    }
}
