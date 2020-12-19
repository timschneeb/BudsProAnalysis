package androidx.versionedparcelable;

import android.os.Parcelable;
import java.lang.reflect.InvocationTargetException;

public abstract class b {
    protected static <T extends d> T a(String str, b bVar) {
        try {
            return (T) ((d) Class.forName(str, true, b.class.getClassLoader()).getDeclaredMethod("read", b.class).invoke(null, bVar));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (ClassNotFoundException e4) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e4);
        }
    }

    private static Class a(Class<? extends d> cls) {
        return Class.forName(String.format("%s.%sParcelizer", cls.getPackage().getName(), cls.getSimpleName()), false, cls.getClassLoader());
    }

    protected static <T extends d> void a(T t, b bVar) {
        try {
            b(t).getDeclaredMethod("write", t.getClass(), b.class).invoke(null, t, bVar);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (ClassNotFoundException e4) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e4);
        }
    }

    private static <T extends d> Class b(T t) {
        return a((Class<? extends d>) t.getClass());
    }

    private void c(d dVar) {
        try {
            a(a((Class<? extends d>) dVar.getClass()).getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(dVar.getClass().getSimpleName() + " does not have a Parcelizer", e);
        }
    }

    public int a(int i, int i2) {
        return !a(i2) ? i : e();
    }

    public <T extends Parcelable> T a(T t, int i) {
        return !a(i) ? t : (T) f();
    }

    public <T extends d> T a(T t, int i) {
        return !a(i) ? t : (T) h();
    }

    public String a(String str, int i) {
        return !a(i) ? str : g();
    }

    /* access modifiers changed from: protected */
    public abstract void a();

    /* access modifiers changed from: protected */
    public abstract void a(Parcelable parcelable);

    /* access modifiers changed from: protected */
    public void a(d dVar) {
        if (dVar == null) {
            a((String) null);
            return;
        }
        c(dVar);
        b b2 = b();
        a(dVar, b2);
        b2.a();
    }

    /* access modifiers changed from: protected */
    public abstract void a(String str);

    public void a(boolean z, boolean z2) {
    }

    /* access modifiers changed from: protected */
    public abstract void a(byte[] bArr);

    /* access modifiers changed from: protected */
    public abstract boolean a(int i);

    public byte[] a(byte[] bArr, int i) {
        return !a(i) ? bArr : d();
    }

    /* access modifiers changed from: protected */
    public abstract b b();

    /* access modifiers changed from: protected */
    public abstract void b(int i);

    public void b(int i, int i2) {
        b(i2);
        c(i);
    }

    public void b(Parcelable parcelable, int i) {
        b(i);
        a(parcelable);
    }

    public void b(d dVar, int i) {
        b(i);
        a(dVar);
    }

    public void b(String str, int i) {
        b(i);
        a(str);
    }

    public void b(byte[] bArr, int i) {
        b(i);
        a(bArr);
    }

    /* access modifiers changed from: protected */
    public abstract void c(int i);

    public boolean c() {
        return false;
    }

    /* access modifiers changed from: protected */
    public abstract byte[] d();

    /* access modifiers changed from: protected */
    public abstract int e();

    /* access modifiers changed from: protected */
    public abstract <T extends Parcelable> T f();

    /* access modifiers changed from: protected */
    public abstract String g();

    /* access modifiers changed from: protected */
    public <T extends d> T h() {
        String g = g();
        if (g == null) {
            return null;
        }
        return (T) a(g, b());
    }
}
