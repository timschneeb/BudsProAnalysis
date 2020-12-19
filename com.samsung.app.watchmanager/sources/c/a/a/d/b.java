package c.a.a.d;

import c.a.a.b.r;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

public class b implements Closeable {

    /* renamed from: a  reason: collision with root package name */
    private static final char[] f1658a = ")]}'\n".toCharArray();

    /* renamed from: b  reason: collision with root package name */
    private final Reader f1659b;

    /* renamed from: c  reason: collision with root package name */
    private boolean f1660c = false;

    /* renamed from: d  reason: collision with root package name */
    private final char[] f1661d = new char[1024];
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h = 0;
    private int i = 0;
    private long j;
    private int k;
    private String l;
    private int[] m = new int[32];
    private int n = 0;
    private String[] o;
    private int[] p;

    static {
        r.f1636a = new a();
    }

    public b(Reader reader) {
        int[] iArr = this.m;
        int i2 = this.n;
        this.n = i2 + 1;
        iArr[i2] = 6;
        this.o = new String[32];
        this.p = new int[32];
        if (reader != null) {
            this.f1659b = reader;
            return;
        }
        throw new NullPointerException("in == null");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0048, code lost:
        q();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void A() {
        /*
        // Method dump skipped, instructions count: 102
        */
        throw new UnsupportedOperationException("Method not decompiled: c.a.a.d.b.A():void");
    }

    private boolean a(char c2) {
        if (c2 == '\t' || c2 == '\n' || c2 == '\f' || c2 == '\r' || c2 == ' ') {
            return false;
        }
        if (c2 != '#') {
            if (c2 == ',') {
                return false;
            }
            if (!(c2 == '/' || c2 == '=')) {
                if (c2 == '{' || c2 == '}' || c2 == ':') {
                    return false;
                }
                if (c2 != ';') {
                    switch (c2) {
                        case '[':
                        case ']':
                            return false;
                        case '\\':
                            break;
                        default:
                            return true;
                    }
                }
            }
        }
        q();
        return false;
    }

    private boolean a(int i2) {
        int i3;
        char[] cArr = this.f1661d;
        int i4 = this.h;
        int i5 = this.e;
        this.h = i4 - i5;
        int i6 = this.f;
        if (i6 != i5) {
            this.f = i6 - i5;
            System.arraycopy(cArr, i5, cArr, 0, this.f);
        } else {
            this.f = 0;
        }
        this.e = 0;
        do {
            Reader reader = this.f1659b;
            int i7 = this.f;
            int read = reader.read(cArr, i7, cArr.length - i7);
            if (read == -1) {
                return false;
            }
            this.f += read;
            if (this.g == 0 && (i3 = this.h) == 0 && this.f > 0 && cArr[0] == 65279) {
                this.e++;
                this.h = i3 + 1;
                i2++;
            }
        } while (this.f < i2);
        return true;
    }

    private boolean a(String str) {
        while (true) {
            if (this.e + str.length() > this.f && !a(str.length())) {
                return false;
            }
            char[] cArr = this.f1661d;
            int i2 = this.e;
            if (cArr[i2] == '\n') {
                this.g++;
                this.h = i2 + 1;
            } else {
                for (int i3 = 0; i3 < str.length(); i3++) {
                    if (this.f1661d[this.e + i3] == str.charAt(i3)) {
                    }
                }
                return true;
            }
            this.e++;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005f, code lost:
        if (r1 != '/') goto L_0x00a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0061, code lost:
        r7.e = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0064, code lost:
        if (r4 != r2) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0066, code lost:
        r7.e--;
        r2 = a(2);
        r7.e++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0074, code lost:
        if (r2 != false) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0076, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0077, code lost:
        q();
        r2 = r7.e;
        r3 = r0[r2];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0080, code lost:
        if (r3 == '*') goto L_0x008e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0082, code lost:
        if (r3 == '/') goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0084, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0085, code lost:
        r7.e = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x008e, code lost:
        r7.e = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0098, code lost:
        if (a("*\/") == false) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009f, code lost:
        b("Unterminated comment");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a5, code lost:
        throw null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a8, code lost:
        if (r1 != '#') goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00aa, code lost:
        r7.e = r4;
        q();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b0, code lost:
        r7.e = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b2, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int b(boolean r8) {
        /*
        // Method dump skipped, instructions count: 182
        */
        throw new UnsupportedOperationException("Method not decompiled: c.a.a.d.b.b(boolean):int");
    }

    private IOException b(String str) {
        throw new e(str + " at line " + u() + " column " + t() + " path " + e());
    }

    private String b(char c2) {
        char[] cArr = this.f1661d;
        StringBuilder sb = new StringBuilder();
        while (true) {
            int i2 = this.e;
            int i3 = this.f;
            while (true) {
                if (i2 < i3) {
                    int i4 = i2 + 1;
                    char c3 = cArr[i2];
                    if (c3 == c2) {
                        this.e = i4;
                        sb.append(cArr, i2, (i4 - i2) - 1);
                        return sb.toString();
                    } else if (c3 == '\\') {
                        this.e = i4;
                        sb.append(cArr, i2, (i4 - i2) - 1);
                        sb.append(y());
                        break;
                    } else {
                        if (c3 == '\n') {
                            this.g++;
                            this.h = i4;
                        }
                        i2 = i4;
                    }
                } else {
                    sb.append(cArr, i2, i2 - i2);
                    this.e = i2;
                    if (!a(1)) {
                        b("Unterminated string");
                        throw null;
                    }
                }
            }
        }
    }

    private void b(int i2) {
        int i3 = this.n;
        int[] iArr = this.m;
        if (i3 == iArr.length) {
            int[] iArr2 = new int[(i3 * 2)];
            int[] iArr3 = new int[(i3 * 2)];
            String[] strArr = new String[(i3 * 2)];
            System.arraycopy(iArr, 0, iArr2, 0, i3);
            System.arraycopy(this.p, 0, iArr3, 0, this.n);
            System.arraycopy(this.o, 0, strArr, 0, this.n);
            this.m = iArr2;
            this.p = iArr3;
            this.o = strArr;
        }
        int[] iArr4 = this.m;
        int i4 = this.n;
        this.n = i4 + 1;
        iArr4[i4] = i2;
    }

    private void c(char c2) {
        char[] cArr = this.f1661d;
        while (true) {
            int i2 = this.e;
            int i3 = this.f;
            while (true) {
                if (i2 < i3) {
                    int i4 = i2 + 1;
                    char c3 = cArr[i2];
                    if (c3 == c2) {
                        this.e = i4;
                        return;
                    } else if (c3 == '\\') {
                        this.e = i4;
                        y();
                        break;
                    } else {
                        if (c3 == '\n') {
                            this.g++;
                            this.h = i4;
                        }
                        i2 = i4;
                    }
                } else {
                    this.e = i2;
                    if (!a(1)) {
                        b("Unterminated string");
                        throw null;
                    }
                }
            }
        }
    }

    private void q() {
        if (!this.f1660c) {
            b("Use JsonReader.setLenient(true) to accept malformed JSON");
            throw null;
        }
    }

    private void r() {
        b(true);
        this.e--;
        int i2 = this.e;
        char[] cArr = f1658a;
        if (i2 + cArr.length <= this.f || a(cArr.length)) {
            int i3 = 0;
            while (true) {
                char[] cArr2 = f1658a;
                if (i3 >= cArr2.length) {
                    this.e += cArr2.length;
                    return;
                } else if (this.f1661d[this.e + i3] == cArr2[i3]) {
                    i3++;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0113  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int s() {
        /*
        // Method dump skipped, instructions count: 380
        */
        throw new UnsupportedOperationException("Method not decompiled: c.a.a.d.b.s():int");
    }

    /* access modifiers changed from: private */
    public int t() {
        return (this.e - this.h) + 1;
    }

    /* access modifiers changed from: private */
    public int u() {
        return this.g + 1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x004b, code lost:
        q();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String v() {
        /*
        // Method dump skipped, instructions count: 160
        */
        throw new UnsupportedOperationException("Method not decompiled: c.a.a.d.b.v():java.lang.String");
    }

    private int w() {
        String str;
        String str2;
        int i2;
        char c2 = this.f1661d[this.e];
        if (c2 == 't' || c2 == 'T') {
            i2 = 5;
            str2 = "true";
            str = "TRUE";
        } else if (c2 == 'f' || c2 == 'F') {
            i2 = 6;
            str2 = "false";
            str = "FALSE";
        } else if (c2 != 'n' && c2 != 'N') {
            return 0;
        } else {
            i2 = 7;
            str2 = "null";
            str = "NULL";
        }
        int length = str2.length();
        for (int i3 = 1; i3 < length; i3++) {
            if (this.e + i3 >= this.f && !a(i3 + 1)) {
                return 0;
            }
            char c3 = this.f1661d[this.e + i3];
            if (!(c3 == str2.charAt(i3) || c3 == str.charAt(i3))) {
                return 0;
            }
        }
        if ((this.e + length < this.f || a(length + 1)) && a(this.f1661d[this.e + length])) {
            return 0;
        }
        this.e += length;
        this.i = i2;
        return i2;
    }

    private int x() {
        int i2;
        char c2;
        char[] cArr = this.f1661d;
        int i3 = this.e;
        int i4 = 0;
        int i5 = this.f;
        int i6 = 0;
        char c3 = 0;
        boolean z = true;
        long j2 = 0;
        boolean z2 = false;
        while (true) {
            if (i3 + i6 == i5) {
                if (i6 == cArr.length) {
                    return i4;
                }
                if (!a(i6 + 1)) {
                    break;
                }
                i3 = this.e;
                i5 = this.f;
            }
            c2 = cArr[i3 + i6];
            if (c2 == '+') {
                i4 = 0;
                if (c3 != 5) {
                    return 0;
                }
            } else if (c2 == 'E' || c2 == 'e') {
                i4 = 0;
                if (c3 != 2 && c3 != 4) {
                    return 0;
                }
                c3 = 5;
                i6++;
            } else {
                if (c2 == '-') {
                    i4 = 0;
                    if (c3 == 0) {
                        c3 = 1;
                        z2 = true;
                    } else if (c3 != 5) {
                        return 0;
                    }
                } else if (c2 == '.') {
                    i4 = 0;
                    if (c3 != 2) {
                        return 0;
                    }
                    c3 = 3;
                } else if (c2 >= '0' && c2 <= '9') {
                    if (c3 == 1 || c3 == 0) {
                        j2 = (long) (-(c2 - '0'));
                        i4 = 0;
                        c3 = 2;
                    } else {
                        if (c3 == 2) {
                            if (j2 == 0) {
                                return 0;
                            }
                            long j3 = (10 * j2) - ((long) (c2 - '0'));
                            boolean z3 = j2 > -922337203685477580L || (j2 == -922337203685477580L && j3 < j2);
                            j2 = j3;
                            z = z3 & z;
                        } else if (c3 == 3) {
                            i4 = 0;
                            c3 = 4;
                        } else if (c3 == 5 || c3 == 6) {
                            i4 = 0;
                            c3 = 7;
                        }
                        i4 = 0;
                    }
                }
                i6++;
            }
            c3 = 6;
            i6++;
        }
        if (a(c2)) {
            return 0;
        }
        if (c3 == 2 && z && (j2 != Long.MIN_VALUE || z2)) {
            if (!z2) {
                j2 = -j2;
            }
            this.j = j2;
            this.e += i6;
            i2 = 15;
        } else if (c3 != 2 && c3 != 4 && c3 != 7) {
            return 0;
        } else {
            this.k = i6;
            i2 = 16;
        }
        this.i = i2;
        return i2;
    }

    private char y() {
        int i2;
        int i3;
        if (this.e != this.f || a(1)) {
            char[] cArr = this.f1661d;
            int i4 = this.e;
            this.e = i4 + 1;
            char c2 = cArr[i4];
            if (c2 == '\n') {
                this.g++;
                this.h = this.e;
            } else if (c2 == 'b') {
                return '\b';
            } else {
                if (c2 == 'f') {
                    return '\f';
                }
                if (c2 == 'n') {
                    return '\n';
                }
                if (c2 == 'r') {
                    return '\r';
                }
                if (c2 == 't') {
                    return '\t';
                }
                if (c2 == 'u') {
                    if (this.e + 4 <= this.f || a(4)) {
                        char c3 = 0;
                        int i5 = this.e;
                        int i6 = i5 + 4;
                        while (i5 < i6) {
                            char c4 = this.f1661d[i5];
                            char c5 = (char) (c3 << 4);
                            if (c4 < '0' || c4 > '9') {
                                if (c4 >= 'a' && c4 <= 'f') {
                                    i2 = c4 - 'a';
                                } else if (c4 < 'A' || c4 > 'F') {
                                    throw new NumberFormatException("\\u" + new String(this.f1661d, this.e, 4));
                                } else {
                                    i2 = c4 - 'A';
                                }
                                i3 = i2 + 10;
                            } else {
                                i3 = c4 - '0';
                            }
                            c3 = (char) (c5 + i3);
                            i5++;
                        }
                        this.e += 4;
                        return c3;
                    }
                    b("Unterminated escape sequence");
                    throw null;
                }
            }
            return c2;
        }
        b("Unterminated escape sequence");
        throw null;
    }

    private void z() {
        char c2;
        do {
            if (this.e < this.f || a(1)) {
                char[] cArr = this.f1661d;
                int i2 = this.e;
                this.e = i2 + 1;
                c2 = cArr[i2];
                if (c2 == '\n') {
                    this.g++;
                    this.h = this.e;
                    return;
                }
            } else {
                return;
            }
        } while (c2 != '\r');
    }

    public void a() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        if (i2 == 3) {
            b(1);
            this.p[this.n - 1] = 0;
            this.i = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_ARRAY but was " + o() + " at line " + u() + " column " + t() + " path " + e());
    }

    public final void a(boolean z) {
        this.f1660c = z;
    }

    public void b() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        if (i2 == 1) {
            b(3);
            this.i = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_OBJECT but was " + o() + " at line " + u() + " column " + t() + " path " + e());
    }

    public void c() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        if (i2 == 4) {
            this.n--;
            int[] iArr = this.p;
            int i3 = this.n - 1;
            iArr[i3] = iArr[i3] + 1;
            this.i = 0;
            return;
        }
        throw new IllegalStateException("Expected END_ARRAY but was " + o() + " at line " + u() + " column " + t() + " path " + e());
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.i = 0;
        this.m[0] = 8;
        this.n = 1;
        this.f1659b.close();
    }

    public void d() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        if (i2 == 2) {
            this.n--;
            String[] strArr = this.o;
            int i3 = this.n;
            strArr[i3] = null;
            int[] iArr = this.p;
            int i4 = i3 - 1;
            iArr[i4] = iArr[i4] + 1;
            this.i = 0;
            return;
        }
        throw new IllegalStateException("Expected END_OBJECT but was " + o() + " at line " + u() + " column " + t() + " path " + e());
    }

    public String e() {
        StringBuilder sb = new StringBuilder();
        sb.append('$');
        int i2 = this.n;
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = this.m[i3];
            if (i4 == 1 || i4 == 2) {
                sb.append('[');
                sb.append(this.p[i3]);
                sb.append(']');
            } else if (i4 == 3 || i4 == 4 || i4 == 5) {
                sb.append('.');
                String[] strArr = this.o;
                if (strArr[i3] != null) {
                    sb.append(strArr[i3]);
                }
            }
        }
        return sb.toString();
    }

    public boolean f() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        return (i2 == 2 || i2 == 4) ? false : true;
    }

    public final boolean g() {
        return this.f1660c;
    }

    public boolean h() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        if (i2 == 5) {
            this.i = 0;
            int[] iArr = this.p;
            int i3 = this.n - 1;
            iArr[i3] = iArr[i3] + 1;
            return true;
        } else if (i2 == 6) {
            this.i = 0;
            int[] iArr2 = this.p;
            int i4 = this.n - 1;
            iArr2[i4] = iArr2[i4] + 1;
            return false;
        } else {
            throw new IllegalStateException("Expected a boolean but was " + o() + " at line " + u() + " column " + t() + " path " + e());
        }
    }

    public double i() {
        String str;
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        if (i2 == 15) {
            this.i = 0;
            int[] iArr = this.p;
            int i3 = this.n - 1;
            iArr[i3] = iArr[i3] + 1;
            return (double) this.j;
        }
        if (i2 == 16) {
            this.l = new String(this.f1661d, this.e, this.k);
            this.e += this.k;
        } else {
            if (i2 == 8 || i2 == 9) {
                str = b(i2 == 8 ? '\'' : '\"');
            } else if (i2 == 10) {
                str = v();
            } else if (i2 != 11) {
                throw new IllegalStateException("Expected a double but was " + o() + " at line " + u() + " column " + t() + " path " + e());
            }
            this.l = str;
        }
        this.i = 11;
        double parseDouble = Double.parseDouble(this.l);
        if (this.f1660c || (!Double.isNaN(parseDouble) && !Double.isInfinite(parseDouble))) {
            this.l = null;
            this.i = 0;
            int[] iArr2 = this.p;
            int i4 = this.n - 1;
            iArr2[i4] = iArr2[i4] + 1;
            return parseDouble;
        }
        throw new e("JSON forbids NaN and infinities: " + parseDouble + " at line " + u() + " column " + t() + " path " + e());
    }

    public int j() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        if (i2 == 15) {
            long j2 = this.j;
            int i3 = (int) j2;
            if (j2 == ((long) i3)) {
                this.i = 0;
                int[] iArr = this.p;
                int i4 = this.n - 1;
                iArr[i4] = iArr[i4] + 1;
                return i3;
            }
            throw new NumberFormatException("Expected an int but was " + this.j + " at line " + u() + " column " + t() + " path " + e());
        }
        if (i2 == 16) {
            this.l = new String(this.f1661d, this.e, this.k);
            this.e += this.k;
        } else if (i2 == 8 || i2 == 9) {
            this.l = b(i2 == 8 ? '\'' : '\"');
            try {
                int parseInt = Integer.parseInt(this.l);
                this.i = 0;
                int[] iArr2 = this.p;
                int i5 = this.n - 1;
                iArr2[i5] = iArr2[i5] + 1;
                return parseInt;
            } catch (NumberFormatException unused) {
            }
        } else {
            throw new IllegalStateException("Expected an int but was " + o() + " at line " + u() + " column " + t() + " path " + e());
        }
        this.i = 11;
        double parseDouble = Double.parseDouble(this.l);
        int i6 = (int) parseDouble;
        if (((double) i6) == parseDouble) {
            this.l = null;
            this.i = 0;
            int[] iArr3 = this.p;
            int i7 = this.n - 1;
            iArr3[i7] = iArr3[i7] + 1;
            return i6;
        }
        throw new NumberFormatException("Expected an int but was " + this.l + " at line " + u() + " column " + t() + " path " + e());
    }

    public long k() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        if (i2 == 15) {
            this.i = 0;
            int[] iArr = this.p;
            int i3 = this.n - 1;
            iArr[i3] = iArr[i3] + 1;
            return this.j;
        }
        if (i2 == 16) {
            this.l = new String(this.f1661d, this.e, this.k);
            this.e += this.k;
        } else if (i2 == 8 || i2 == 9) {
            this.l = b(i2 == 8 ? '\'' : '\"');
            try {
                long parseLong = Long.parseLong(this.l);
                this.i = 0;
                int[] iArr2 = this.p;
                int i4 = this.n - 1;
                iArr2[i4] = iArr2[i4] + 1;
                return parseLong;
            } catch (NumberFormatException unused) {
            }
        } else {
            throw new IllegalStateException("Expected a long but was " + o() + " at line " + u() + " column " + t() + " path " + e());
        }
        this.i = 11;
        double parseDouble = Double.parseDouble(this.l);
        long j2 = (long) parseDouble;
        if (((double) j2) == parseDouble) {
            this.l = null;
            this.i = 0;
            int[] iArr3 = this.p;
            int i5 = this.n - 1;
            iArr3[i5] = iArr3[i5] + 1;
            return j2;
        }
        throw new NumberFormatException("Expected a long but was " + this.l + " at line " + u() + " column " + t() + " path " + e());
    }

    public String l() {
        String str;
        char c2;
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        if (i2 == 14) {
            str = v();
        } else {
            if (i2 == 12) {
                c2 = '\'';
            } else if (i2 == 13) {
                c2 = '\"';
            } else {
                throw new IllegalStateException("Expected a name but was " + o() + " at line " + u() + " column " + t() + " path " + e());
            }
            str = b(c2);
        }
        this.i = 0;
        this.o[this.n - 1] = str;
        return str;
    }

    public void m() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        if (i2 == 7) {
            this.i = 0;
            int[] iArr = this.p;
            int i3 = this.n - 1;
            iArr[i3] = iArr[i3] + 1;
            return;
        }
        throw new IllegalStateException("Expected null but was " + o() + " at line " + u() + " column " + t() + " path " + e());
    }

    public String n() {
        String str;
        char c2;
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        if (i2 == 10) {
            str = v();
        } else {
            if (i2 == 8) {
                c2 = '\'';
            } else if (i2 == 9) {
                c2 = '\"';
            } else if (i2 == 11) {
                str = this.l;
                this.l = null;
            } else if (i2 == 15) {
                str = Long.toString(this.j);
            } else if (i2 == 16) {
                str = new String(this.f1661d, this.e, this.k);
                this.e += this.k;
            } else {
                throw new IllegalStateException("Expected a string but was " + o() + " at line " + u() + " column " + t() + " path " + e());
            }
            str = b(c2);
        }
        this.i = 0;
        int[] iArr = this.p;
        int i3 = this.n - 1;
        iArr[i3] = iArr[i3] + 1;
        return str;
    }

    public c o() {
        int i2 = this.i;
        if (i2 == 0) {
            i2 = s();
        }
        switch (i2) {
            case 1:
                return c.BEGIN_OBJECT;
            case 2:
                return c.END_OBJECT;
            case 3:
                return c.BEGIN_ARRAY;
            case 4:
                return c.END_ARRAY;
            case 5:
            case 6:
                return c.BOOLEAN;
            case 7:
                return c.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return c.STRING;
            case 12:
            case 13:
            case 14:
                return c.NAME;
            case 15:
            case 16:
                return c.NUMBER;
            case 17:
                return c.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    public void p() {
        char c2;
        int i2 = 0;
        do {
            int i3 = this.i;
            if (i3 == 0) {
                i3 = s();
            }
            if (i3 == 3) {
                b(1);
            } else if (i3 == 1) {
                b(3);
            } else if (i3 == 4 || i3 == 2) {
                this.n--;
                i2--;
                this.i = 0;
            } else if (i3 == 14 || i3 == 10) {
                A();
                this.i = 0;
            } else {
                if (i3 == 8 || i3 == 12) {
                    c2 = '\'';
                } else if (i3 == 9 || i3 == 13) {
                    c2 = '\"';
                } else {
                    if (i3 == 16) {
                        this.e += this.k;
                    }
                    this.i = 0;
                }
                c(c2);
                this.i = 0;
            }
            i2++;
            this.i = 0;
        } while (i2 != 0);
        int[] iArr = this.p;
        int i4 = this.n;
        int i5 = i4 - 1;
        iArr[i5] = iArr[i5] + 1;
        this.o[i4 - 1] = "null";
    }

    public String toString() {
        return getClass().getSimpleName() + " at line " + u() + " column " + t();
    }
}
