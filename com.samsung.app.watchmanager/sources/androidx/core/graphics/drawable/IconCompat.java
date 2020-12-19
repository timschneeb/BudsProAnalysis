package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import androidx.versionedparcelable.CustomVersionedParcelable;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

public class IconCompat extends CustomVersionedParcelable {

    /* renamed from: a  reason: collision with root package name */
    static final PorterDuff.Mode f616a = PorterDuff.Mode.SRC_IN;

    /* renamed from: b  reason: collision with root package name */
    public int f617b;

    /* renamed from: c  reason: collision with root package name */
    Object f618c;

    /* renamed from: d  reason: collision with root package name */
    public byte[] f619d;
    public Parcelable e;
    public int f;
    public int g;
    public ColorStateList h = null;
    PorterDuff.Mode i = f616a;
    public String j;

    private static int a(Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getResId();
        }
        try {
            return ((Integer) icon.getClass().getMethod("getResId", new Class[0]).invoke(icon, new Object[0])).intValue();
        } catch (IllegalAccessException e2) {
            Log.e("IconCompat", "Unable to get icon resource", e2);
            return 0;
        } catch (InvocationTargetException e3) {
            Log.e("IconCompat", "Unable to get icon resource", e3);
            return 0;
        } catch (NoSuchMethodException e4) {
            Log.e("IconCompat", "Unable to get icon resource", e4);
            return 0;
        }
    }

    private static String a(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? "UNKNOWN" : "BITMAP_MASKABLE" : "URI" : "DATA" : "RESOURCE" : "BITMAP";
    }

    private static String b(Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getResPackage();
        }
        try {
            return (String) icon.getClass().getMethod("getResPackage", new Class[0]).invoke(icon, new Object[0]);
        } catch (IllegalAccessException e2) {
            Log.e("IconCompat", "Unable to get icon package", e2);
            return null;
        } catch (InvocationTargetException e3) {
            Log.e("IconCompat", "Unable to get icon package", e3);
            return null;
        } catch (NoSuchMethodException e4) {
            Log.e("IconCompat", "Unable to get icon package", e4);
            return null;
        }
    }

    public int a() {
        if (this.f617b == -1 && Build.VERSION.SDK_INT >= 23) {
            return a((Icon) this.f618c);
        }
        if (this.f617b == 2) {
            return this.f;
        }
        throw new IllegalStateException("called getResId() on " + this);
    }

    public void a(boolean z) {
        this.j = this.i.name();
        int i2 = this.f617b;
        if (i2 != -1) {
            if (i2 != 1) {
                if (i2 == 2) {
                    this.f619d = ((String) this.f618c).getBytes(Charset.forName("UTF-16"));
                    return;
                } else if (i2 == 3) {
                    this.f619d = (byte[]) this.f618c;
                    return;
                } else if (i2 == 4) {
                    this.f619d = this.f618c.toString().getBytes(Charset.forName("UTF-16"));
                    return;
                } else if (i2 != 5) {
                    return;
                }
            }
            if (z) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ((Bitmap) this.f618c).compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
                this.f619d = byteArrayOutputStream.toByteArray();
                return;
            }
        } else if (z) {
            throw new IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
        }
        this.e = (Parcelable) this.f618c;
    }

    public String b() {
        if (this.f617b == -1 && Build.VERSION.SDK_INT >= 23) {
            return b((Icon) this.f618c);
        }
        if (this.f617b == 2) {
            return ((String) this.f618c).split(":", -1)[0];
        }
        throw new IllegalStateException("called getResPackage() on " + this);
    }

    public void c() {
        Parcelable parcelable;
        this.i = PorterDuff.Mode.valueOf(this.j);
        int i2 = this.f617b;
        if (i2 != -1) {
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 == 3) {
                        this.f618c = this.f619d;
                        return;
                    } else if (i2 != 4) {
                        if (i2 != 5) {
                            return;
                        }
                    }
                }
                this.f618c = new String(this.f619d, Charset.forName("UTF-16"));
                return;
            }
            parcelable = this.e;
            if (parcelable == null) {
                byte[] bArr = this.f619d;
                this.f618c = bArr;
                this.f617b = 3;
                this.f = 0;
                this.g = bArr.length;
                return;
            }
        } else {
            parcelable = this.e;
            if (parcelable == null) {
                throw new IllegalArgumentException("Invalid icon");
            }
        }
        this.f618c = parcelable;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002b, code lost:
        if (r1 != 5) goto L_0x0097;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00ab  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String toString() {
        /*
        // Method dump skipped, instructions count: 191
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.drawable.IconCompat.toString():java.lang.String");
    }
}
