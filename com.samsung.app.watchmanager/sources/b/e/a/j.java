package b.e.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import androidx.core.content.a.c;
import b.e.d.f;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/* access modifiers changed from: package-private */
public class j {

    /* access modifiers changed from: private */
    public interface a<T> {
        int a(T t);

        boolean b(T t);
    }

    j() {
    }

    private c.C0011c a(c.b bVar, int i) {
        return (c.C0011c) a(bVar.a(), i, new i(this));
    }

    private static <T> T a(T[] tArr, int i, a<T> aVar) {
        int i2 = (i & 1) == 0 ? 400 : 700;
        boolean z = (i & 2) != 0;
        T t = null;
        int i3 = Integer.MAX_VALUE;
        for (T t2 : tArr) {
            int abs = (Math.abs(aVar.a(t2) - i2) * 2) + (aVar.b(t2) == z ? 0 : 1);
            if (t == null || i3 > abs) {
                t = t2;
                i3 = abs;
            }
        }
        return t;
    }

    public Typeface a(Context context, Resources resources, int i, String str, int i2) {
        File a2 = k.a(context);
        if (a2 == null) {
            return null;
        }
        try {
            if (!k.a(a2, resources, i)) {
                return null;
            }
            Typeface createFromFile = Typeface.createFromFile(a2.getPath());
            a2.delete();
            return createFromFile;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            a2.delete();
        }
    }

    public Typeface a(Context context, CancellationSignal cancellationSignal, f.b[] bVarArr, int i) {
        InputStream inputStream;
        Throwable th;
        InputStream inputStream2 = null;
        if (bVarArr.length < 1) {
            return null;
        }
        try {
            inputStream = context.getContentResolver().openInputStream(a(bVarArr, i).c());
            try {
                Typeface a2 = a(context, inputStream);
                k.a(inputStream);
                return a2;
            } catch (IOException unused) {
                k.a(inputStream);
                return null;
            } catch (Throwable th2) {
                th = th2;
                inputStream2 = inputStream;
                k.a(inputStream2);
                throw th;
            }
        } catch (IOException unused2) {
            inputStream = null;
            k.a(inputStream);
            return null;
        } catch (Throwable th3) {
            th = th3;
            k.a(inputStream2);
            throw th;
        }
    }

    public Typeface a(Context context, c.b bVar, Resources resources, int i) {
        c.C0011c a2 = a(bVar, i);
        if (a2 == null) {
            return null;
        }
        return c.a(context, resources, a2.b(), a2.a(), i);
    }

    /* access modifiers changed from: protected */
    public Typeface a(Context context, InputStream inputStream) {
        File a2 = k.a(context);
        if (a2 == null) {
            return null;
        }
        try {
            if (!k.a(a2, inputStream)) {
                return null;
            }
            Typeface createFromFile = Typeface.createFromFile(a2.getPath());
            a2.delete();
            return createFromFile;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            a2.delete();
        }
    }

    /* access modifiers changed from: protected */
    public f.b a(f.b[] bVarArr, int i) {
        return (f.b) a(bVarArr, i, new h(this));
    }
}
