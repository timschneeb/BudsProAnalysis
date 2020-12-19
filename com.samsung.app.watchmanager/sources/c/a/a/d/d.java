package c.a.a.d;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class d implements Closeable, Flushable {

    /* renamed from: a  reason: collision with root package name */
    private static final String[] f1666a = new String[128];

    /* renamed from: b  reason: collision with root package name */
    private static final String[] f1667b;

    /* renamed from: c  reason: collision with root package name */
    private final Writer f1668c;

    /* renamed from: d  reason: collision with root package name */
    private int[] f1669d = new int[32];
    private int e = 0;
    private String f;
    private String g;
    private boolean h;
    private boolean i;
    private String j;
    private boolean k;

    static {
        for (int i2 = 0; i2 <= 31; i2++) {
            f1666a[i2] = String.format("\\u%04x", Integer.valueOf(i2));
        }
        String[] strArr = f1666a;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
        f1667b = (String[]) strArr.clone();
        String[] strArr2 = f1667b;
        strArr2[60] = "\\u003c";
        strArr2[62] = "\\u003e";
        strArr2[38] = "\\u0026";
        strArr2[61] = "\\u003d";
        strArr2[39] = "\\u0027";
    }

    public d(Writer writer) {
        a(6);
        this.g = ":";
        this.k = true;
        if (writer != null) {
            this.f1668c = writer;
            return;
        }
        throw new NullPointerException("out == null");
    }

    private d a(int i2, int i3, String str) {
        int k2 = k();
        if (k2 != i3 && k2 != i2) {
            throw new IllegalStateException("Nesting problem.");
        } else if (this.j == null) {
            this.e--;
            if (k2 == i3) {
                j();
            }
            this.f1668c.write(str);
            return this;
        } else {
            throw new IllegalStateException("Dangling name: " + this.j);
        }
    }

    private d a(int i2, String str) {
        e(true);
        a(i2);
        this.f1668c.write(str);
        return this;
    }

    private void a(int i2) {
        int i3 = this.e;
        int[] iArr = this.f1669d;
        if (i3 == iArr.length) {
            int[] iArr2 = new int[(i3 * 2)];
            System.arraycopy(iArr, 0, iArr2, 0, i3);
            this.f1669d = iArr2;
        }
        int[] iArr3 = this.f1669d;
        int i4 = this.e;
        this.e = i4 + 1;
        iArr3[i4] = i2;
    }

    private void b(int i2) {
        this.f1669d[this.e - 1] = i2;
    }

    private void d(String str) {
        String str2;
        String[] strArr = this.i ? f1667b : f1666a;
        this.f1668c.write("\"");
        int length = str.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = str.charAt(i3);
            if (charAt < 128) {
                str2 = strArr[charAt];
                if (str2 == null) {
                }
            } else if (charAt == 8232) {
                str2 = "\\u2028";
            } else if (charAt == 8233) {
                str2 = "\\u2029";
            }
            if (i2 < i3) {
                this.f1668c.write(str, i2, i3 - i2);
            }
            this.f1668c.write(str2);
            i2 = i3 + 1;
        }
        if (i2 < length) {
            this.f1668c.write(str, i2, length - i2);
        }
        this.f1668c.write("\"");
    }

    private void e(boolean z) {
        int k2 = k();
        if (k2 == 1) {
            b(2);
        } else if (k2 == 2) {
            this.f1668c.append(',');
        } else if (k2 != 4) {
            if (k2 != 6) {
                if (k2 != 7) {
                    throw new IllegalStateException("Nesting problem.");
                } else if (!this.h) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
            }
            if (this.h || z) {
                b(7);
                return;
            }
            throw new IllegalStateException("JSON must start with an array or an object.");
        } else {
            this.f1668c.append((CharSequence) this.g);
            b(5);
            return;
        }
        j();
    }

    private void i() {
        int k2 = k();
        if (k2 == 5) {
            this.f1668c.write(44);
        } else if (k2 != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        j();
        b(4);
    }

    private void j() {
        if (this.f != null) {
            this.f1668c.write("\n");
            int i2 = this.e;
            for (int i3 = 1; i3 < i2; i3++) {
                this.f1668c.write(this.f);
            }
        }
    }

    private int k() {
        int i2 = this.e;
        if (i2 != 0) {
            return this.f1669d[i2 - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    private void l() {
        if (this.j != null) {
            i();
            d(this.j);
            this.j = null;
        }
    }

    public d a() {
        l();
        a(1, "[");
        return this;
    }

    public d a(long j2) {
        l();
        e(false);
        this.f1668c.write(Long.toString(j2));
        return this;
    }

    public d a(Number number) {
        if (number == null) {
            return h();
        }
        l();
        String obj = number.toString();
        if (this.h || (!obj.equals("-Infinity") && !obj.equals("Infinity") && !obj.equals("NaN"))) {
            e(false);
            this.f1668c.append((CharSequence) obj);
            return this;
        }
        throw new IllegalArgumentException("Numeric values must be finite, but was " + number);
    }

    public d a(String str) {
        if (str == null) {
            throw new NullPointerException("name == null");
        } else if (this.j != null) {
            throw new IllegalStateException();
        } else if (this.e != 0) {
            this.j = str;
            return this;
        } else {
            throw new IllegalStateException("JsonWriter is closed.");
        }
    }

    public final void a(boolean z) {
        this.i = z;
    }

    public d b() {
        l();
        a(3, "{");
        return this;
    }

    public final void b(String str) {
        String str2;
        if (str.length() == 0) {
            this.f = null;
            str2 = ":";
        } else {
            this.f = str;
            str2 = ": ";
        }
        this.g = str2;
    }

    public final void b(boolean z) {
        this.h = z;
    }

    public d c() {
        a(1, 2, "]");
        return this;
    }

    public d c(String str) {
        if (str == null) {
            return h();
        }
        l();
        e(false);
        d(str);
        return this;
    }

    public final void c(boolean z) {
        this.k = z;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.f1668c.close();
        int i2 = this.e;
        if (i2 > 1 || (i2 == 1 && this.f1669d[i2 - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.e = 0;
    }

    public d d() {
        a(3, 5, "}");
        return this;
    }

    public d d(boolean z) {
        l();
        e(false);
        this.f1668c.write(z ? "true" : "false");
        return this;
    }

    public final boolean e() {
        return this.k;
    }

    public final boolean f() {
        return this.i;
    }

    @Override // java.io.Flushable
    public void flush() {
        if (this.e != 0) {
            this.f1668c.flush();
            return;
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    public boolean g() {
        return this.h;
    }

    public d h() {
        if (this.j != null) {
            if (this.k) {
                l();
            } else {
                this.j = null;
                return this;
            }
        }
        e(false);
        this.f1668c.write("null");
        return this;
    }
}
