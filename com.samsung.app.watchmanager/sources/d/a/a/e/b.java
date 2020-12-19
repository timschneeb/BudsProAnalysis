package d.a.a.e;

import d.a.a.b.q;
import d.a.a.g;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class b {

    /* access modifiers changed from: private */
    public static final class a extends g {
        final int f;
        final d g;
        final d h;

        a(String str, int i, d dVar, d dVar2) {
            super(str);
            this.f = i;
            this.g = dVar;
            this.h = dVar2;
        }

        static a a(DataInput dataInput, String str) {
            return new a(str, (int) b.a(dataInput), d.a(dataInput), d.a(dataInput));
        }

        private d i(long j) {
            long j2;
            int i = this.f;
            d dVar = this.g;
            d dVar2 = this.h;
            try {
                j2 = dVar.a(j, i, dVar2.b());
            } catch (ArithmeticException | IllegalArgumentException unused) {
                j2 = j;
            }
            try {
                j = dVar2.a(j, i, dVar.b());
            } catch (ArithmeticException | IllegalArgumentException unused2) {
            }
            return j2 > j ? dVar : dVar2;
        }

        @Override // d.a.a.g
        public String b(long j) {
            return i(j).a();
        }

        @Override // d.a.a.g
        public int c(long j) {
            return this.f + i(j).b();
        }

        @Override // d.a.a.g
        public int e(long j) {
            return this.f;
        }

        @Override // d.a.a.g
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return c().equals(aVar.c()) && this.f == aVar.f && this.g.equals(aVar.g) && this.h.equals(aVar.h);
        }

        @Override // d.a.a.g
        public boolean f() {
            return false;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0016, code lost:
            if (r5 < 0) goto L_0x0018;
         */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x0030  */
        /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
        @Override // d.a.a.g
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long g(long r9) {
            /*
                r8 = this;
                int r0 = r8.f
                d.a.a.e.b$d r1 = r8.g
                d.a.a.e.b$d r2 = r8.h
                r3 = 0
                int r5 = r2.b()     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x0018 }
                long r5 = r1.a(r9, r0, r5)     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x0018 }
                int r7 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                if (r7 <= 0) goto L_0x0019
                int r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
                if (r7 >= 0) goto L_0x0019
            L_0x0018:
                r5 = r9
            L_0x0019:
                int r1 = r1.b()     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x002b }
                long r0 = r2.a(r9, r0, r1)     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x002b }
                int r2 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                if (r2 <= 0) goto L_0x002a
                int r2 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
                if (r2 >= 0) goto L_0x002a
                goto L_0x002b
            L_0x002a:
                r9 = r0
            L_0x002b:
                int r0 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
                if (r0 <= 0) goto L_0x0030
                goto L_0x0031
            L_0x0030:
                r9 = r5
            L_0x0031:
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: d.a.a.e.b.a.g(long):long");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0019, code lost:
            if (r7 > 0) goto L_0x001b;
         */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x0032  */
        @Override // d.a.a.g
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long h(long r11) {
            /*
                r10 = this;
                r0 = 1
                long r11 = r11 + r0
                int r2 = r10.f
                d.a.a.e.b$d r3 = r10.g
                d.a.a.e.b$d r4 = r10.h
                r5 = 0
                int r7 = r4.b()     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x001b }
                long r7 = r3.b(r11, r2, r7)     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x001b }
                int r9 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                if (r9 >= 0) goto L_0x001c
                int r9 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
                if (r9 <= 0) goto L_0x001c
            L_0x001b:
                r7 = r11
            L_0x001c:
                int r3 = r3.b()     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x002e }
                long r2 = r4.b(r11, r2, r3)     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x002e }
                int r4 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                if (r4 >= 0) goto L_0x002d
                int r4 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
                if (r4 <= 0) goto L_0x002d
                goto L_0x002e
            L_0x002d:
                r11 = r2
            L_0x002e:
                int r2 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
                if (r2 <= 0) goto L_0x0033
                r11 = r7
            L_0x0033:
                long r11 = r11 - r0
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: d.a.a.e.b.a.h(long):long");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d.a.a.e.b$b  reason: collision with other inner class name */
    public static final class C0043b {

        /* renamed from: a  reason: collision with root package name */
        final char f2133a;

        /* renamed from: b  reason: collision with root package name */
        final int f2134b;

        /* renamed from: c  reason: collision with root package name */
        final int f2135c;

        /* renamed from: d  reason: collision with root package name */
        final int f2136d;
        final boolean e;
        final int f;

        C0043b(char c2, int i, int i2, int i3, boolean z, int i4) {
            if (c2 == 'u' || c2 == 'w' || c2 == 's') {
                this.f2133a = c2;
                this.f2134b = i;
                this.f2135c = i2;
                this.f2136d = i3;
                this.e = z;
                this.f = i4;
                return;
            }
            throw new IllegalArgumentException("Unknown mode: " + c2);
        }

        private long a(d.a.a.a aVar, long j) {
            if (this.f2135c >= 0) {
                return aVar.e().b(j, this.f2135c);
            }
            return aVar.e().a(aVar.w().a(aVar.e().b(j, 1), 1), this.f2135c);
        }

        static C0043b a(DataInput dataInput) {
            return new C0043b((char) dataInput.readUnsignedByte(), dataInput.readUnsignedByte(), dataInput.readByte(), dataInput.readUnsignedByte(), dataInput.readBoolean(), (int) b.a(dataInput));
        }

        private long b(d.a.a.a aVar, long j) {
            try {
                return a(aVar, j);
            } catch (IllegalArgumentException e2) {
                if (this.f2134b == 2 && this.f2135c == 29) {
                    while (!aVar.H().b(j)) {
                        j = aVar.H().a(j, 1);
                    }
                    return a(aVar, j);
                }
                throw e2;
            }
        }

        private long c(d.a.a.a aVar, long j) {
            try {
                return a(aVar, j);
            } catch (IllegalArgumentException e2) {
                if (this.f2134b == 2 && this.f2135c == 29) {
                    while (!aVar.H().b(j)) {
                        j = aVar.H().a(j, -1);
                    }
                    return a(aVar, j);
                }
                throw e2;
            }
        }

        private long d(d.a.a.a aVar, long j) {
            int a2 = this.f2136d - aVar.f().a(j);
            if (a2 == 0) {
                return j;
            }
            if (this.e) {
                if (a2 < 0) {
                    a2 += 7;
                }
            } else if (a2 > 0) {
                a2 -= 7;
            }
            return aVar.f().a(j, a2);
        }

        public long a(long j, int i, int i2) {
            char c2 = this.f2133a;
            if (c2 == 'w') {
                i += i2;
            } else if (c2 != 's') {
                i = 0;
            }
            long j2 = (long) i;
            long j3 = j + j2;
            q O = q.O();
            long b2 = b(O, O.r().a(O.r().b(O.w().b(j3, this.f2134b), 0), this.f));
            if (this.f2136d != 0) {
                b2 = d(O, b2);
                if (b2 <= j3) {
                    b2 = d(O, b(O, O.w().b(O.H().a(b2, 1), this.f2134b)));
                }
            } else if (b2 <= j3) {
                b2 = b(O, O.H().a(b2, 1));
            }
            return O.r().a(O.r().b(b2, 0), this.f) - j2;
        }

        public long b(long j, int i, int i2) {
            char c2 = this.f2133a;
            if (c2 == 'w') {
                i += i2;
            } else if (c2 != 's') {
                i = 0;
            }
            long j2 = (long) i;
            long j3 = j + j2;
            q O = q.O();
            long c3 = c(O, O.r().a(O.r().b(O.w().b(j3, this.f2134b), 0), this.f));
            if (this.f2136d != 0) {
                c3 = d(O, c3);
                if (c3 >= j3) {
                    c3 = d(O, c(O, O.w().b(O.H().a(c3, -1), this.f2134b)));
                }
            } else if (c3 >= j3) {
                c3 = c(O, O.H().a(c3, -1));
            }
            return O.r().a(O.r().b(c3, 0), this.f) - j2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof C0043b)) {
                return false;
            }
            C0043b bVar = (C0043b) obj;
            return this.f2133a == bVar.f2133a && this.f2134b == bVar.f2134b && this.f2135c == bVar.f2135c && this.f2136d == bVar.f2136d && this.e == bVar.e && this.f == bVar.f;
        }

        public String toString() {
            return "[OfYear]\nMode: " + this.f2133a + '\n' + "MonthOfYear: " + this.f2134b + '\n' + "DayOfMonth: " + this.f2135c + '\n' + "DayOfWeek: " + this.f2136d + '\n' + "AdvanceDayOfWeek: " + this.e + '\n' + "MillisOfDay: " + this.f + '\n';
        }
    }

    /* access modifiers changed from: private */
    public static final class c extends g {
        private final long[] f;
        private final int[] g;
        private final int[] h;
        private final String[] i;
        private final a j;

        private c(String str, long[] jArr, int[] iArr, int[] iArr2, String[] strArr, a aVar) {
            super(str);
            this.f = jArr;
            this.g = iArr;
            this.h = iArr2;
            this.i = strArr;
            this.j = aVar;
        }

        static c a(DataInput dataInput, String str) {
            int i2;
            int readUnsignedShort = dataInput.readUnsignedShort();
            String[] strArr = new String[readUnsignedShort];
            for (int i3 = 0; i3 < readUnsignedShort; i3++) {
                strArr[i3] = dataInput.readUTF();
            }
            int readInt = dataInput.readInt();
            long[] jArr = new long[readInt];
            int[] iArr = new int[readInt];
            int[] iArr2 = new int[readInt];
            String[] strArr2 = new String[readInt];
            for (int i4 = 0; i4 < readInt; i4++) {
                jArr[i4] = b.a(dataInput);
                iArr[i4] = (int) b.a(dataInput);
                iArr2[i4] = (int) b.a(dataInput);
                if (readUnsignedShort < 256) {
                    try {
                        i2 = dataInput.readUnsignedByte();
                    } catch (ArrayIndexOutOfBoundsException unused) {
                        throw new IOException("Invalid encoding");
                    }
                } else {
                    i2 = dataInput.readUnsignedShort();
                }
                strArr2[i4] = strArr[i2];
            }
            return new c(str, jArr, iArr, iArr2, strArr2, dataInput.readBoolean() ? a.a(dataInput, str) : null);
        }

        @Override // d.a.a.g
        public String b(long j2) {
            long[] jArr = this.f;
            int binarySearch = Arrays.binarySearch(jArr, j2);
            if (binarySearch >= 0) {
                return this.i[binarySearch];
            }
            int i2 = binarySearch ^ -1;
            if (i2 < jArr.length) {
                return i2 > 0 ? this.i[i2 - 1] : "UTC";
            }
            a aVar = this.j;
            return aVar == null ? this.i[i2 - 1] : aVar.b(j2);
        }

        @Override // d.a.a.g
        public int c(long j2) {
            long[] jArr = this.f;
            int binarySearch = Arrays.binarySearch(jArr, j2);
            if (binarySearch >= 0) {
                return this.g[binarySearch];
            }
            int i2 = binarySearch ^ -1;
            if (i2 >= jArr.length) {
                a aVar = this.j;
                return aVar == null ? this.g[i2 - 1] : aVar.c(j2);
            } else if (i2 > 0) {
                return this.g[i2 - 1];
            } else {
                return 0;
            }
        }

        @Override // d.a.a.g
        public int e(long j2) {
            long[] jArr = this.f;
            int binarySearch = Arrays.binarySearch(jArr, j2);
            if (binarySearch >= 0) {
                return this.h[binarySearch];
            }
            int i2 = binarySearch ^ -1;
            if (i2 >= jArr.length) {
                a aVar = this.j;
                return aVar == null ? this.h[i2 - 1] : aVar.e(j2);
            } else if (i2 > 0) {
                return this.h[i2 - 1];
            } else {
                return 0;
            }
        }

        @Override // d.a.a.g
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            if (c().equals(cVar.c()) && Arrays.equals(this.f, cVar.f) && Arrays.equals(this.i, cVar.i) && Arrays.equals(this.g, cVar.g) && Arrays.equals(this.h, cVar.h)) {
                a aVar = this.j;
                if (aVar == null) {
                    if (cVar.j == null) {
                        return true;
                    }
                } else if (aVar.equals(cVar.j)) {
                    return true;
                }
            }
            return false;
        }

        @Override // d.a.a.g
        public boolean f() {
            return false;
        }

        @Override // d.a.a.g
        public long g(long j2) {
            long[] jArr = this.f;
            int binarySearch = Arrays.binarySearch(jArr, j2);
            int i2 = binarySearch >= 0 ? binarySearch + 1 : binarySearch ^ -1;
            if (i2 < jArr.length) {
                return jArr[i2];
            }
            if (this.j == null) {
                return j2;
            }
            long j3 = jArr[jArr.length - 1];
            if (j2 < j3) {
                j2 = j3;
            }
            return this.j.g(j2);
        }

        @Override // d.a.a.g
        public long h(long j2) {
            long[] jArr = this.f;
            int binarySearch = Arrays.binarySearch(jArr, j2);
            if (binarySearch >= 0) {
                return j2 > Long.MIN_VALUE ? j2 - 1 : j2;
            }
            int i2 = binarySearch ^ -1;
            if (i2 < jArr.length) {
                if (i2 > 0) {
                    long j3 = jArr[i2 - 1];
                    if (j3 > Long.MIN_VALUE) {
                        return j3 - 1;
                    }
                }
                return j2;
            }
            a aVar = this.j;
            if (aVar != null) {
                long h2 = aVar.h(j2);
                if (h2 < j2) {
                    return h2;
                }
            }
            long j4 = jArr[i2 - 1];
            return j4 > Long.MIN_VALUE ? j4 - 1 : j2;
        }
    }

    /* access modifiers changed from: private */
    public static final class d {

        /* renamed from: a  reason: collision with root package name */
        final C0043b f2137a;

        /* renamed from: b  reason: collision with root package name */
        final String f2138b;

        /* renamed from: c  reason: collision with root package name */
        final int f2139c;

        d(C0043b bVar, String str, int i) {
            this.f2137a = bVar;
            this.f2138b = str;
            this.f2139c = i;
        }

        static d a(DataInput dataInput) {
            return new d(C0043b.a(dataInput), dataInput.readUTF(), (int) b.a(dataInput));
        }

        public long a(long j, int i, int i2) {
            return this.f2137a.a(j, i, i2);
        }

        public String a() {
            return this.f2138b;
        }

        public int b() {
            return this.f2139c;
        }

        public long b(long j, int i, int i2) {
            return this.f2137a.b(j, i, i2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof d)) {
                return false;
            }
            d dVar = (d) obj;
            return this.f2139c == dVar.f2139c && this.f2138b.equals(dVar.f2138b) && this.f2137a.equals(dVar.f2137a);
        }

        public String toString() {
            return this.f2137a + " named " + this.f2138b + " at " + this.f2139c;
        }
    }

    static long a(DataInput dataInput) {
        long readUnsignedByte;
        long j;
        int readUnsignedByte2 = dataInput.readUnsignedByte();
        int i = readUnsignedByte2 >> 6;
        if (i == 1) {
            readUnsignedByte = (long) (dataInput.readUnsignedByte() | ((readUnsignedByte2 << 26) >> 2) | (dataInput.readUnsignedByte() << 16) | (dataInput.readUnsignedByte() << 8));
            j = 60000;
        } else if (i == 2) {
            readUnsignedByte = ((((long) readUnsignedByte2) << 58) >> 26) | ((long) (dataInput.readUnsignedByte() << 24)) | ((long) (dataInput.readUnsignedByte() << 16)) | ((long) (dataInput.readUnsignedByte() << 8)) | ((long) dataInput.readUnsignedByte());
            j = 1000;
        } else if (i == 3) {
            return dataInput.readLong();
        } else {
            readUnsignedByte = (long) ((readUnsignedByte2 << 26) >> 26);
            j = 1800000;
        }
        return readUnsignedByte * j;
    }

    public static g a(DataInput dataInput, String str) {
        int readUnsignedByte = dataInput.readUnsignedByte();
        if (readUnsignedByte == 67) {
            return a.a(c.a(dataInput, str));
        }
        if (readUnsignedByte == 70) {
            d dVar = new d(str, dataInput.readUTF(), (int) a(dataInput), (int) a(dataInput));
            return dVar.equals(g.f2149a) ? g.f2149a : dVar;
        } else if (readUnsignedByte == 80) {
            return c.a(dataInput, str);
        } else {
            throw new IOException("Invalid encoding");
        }
    }

    public static g a(InputStream inputStream, String str) {
        return inputStream instanceof DataInput ? a((DataInput) inputStream, str) : a((DataInput) new DataInputStream(inputStream), str);
    }
}
