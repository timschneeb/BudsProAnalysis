package c.a.a.b;

import c.a.a.b.a.ca;
import c.a.a.d.d;
import c.a.a.v;
import java.io.Writer;

public final class A {

    private static final class a extends Writer {

        /* renamed from: a  reason: collision with root package name */
        private final Appendable f1536a;

        /* renamed from: b  reason: collision with root package name */
        private final C0031a f1537b;

        /* renamed from: c.a.a.b.A$a$a  reason: collision with other inner class name */
        static class C0031a implements CharSequence {

            /* renamed from: a  reason: collision with root package name */
            char[] f1538a;

            C0031a() {
            }

            public char charAt(int i) {
                return this.f1538a[i];
            }

            public int length() {
                return this.f1538a.length;
            }

            public CharSequence subSequence(int i, int i2) {
                return new String(this.f1538a, i, i2 - i);
            }
        }

        private a(Appendable appendable) {
            this.f1537b = new C0031a();
            this.f1536a = appendable;
        }

        @Override // java.io.Closeable, java.io.Writer, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
        }

        @Override // java.io.Writer
        public void write(int i) {
            this.f1536a.append((char) i);
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) {
            C0031a aVar = this.f1537b;
            aVar.f1538a = cArr;
            this.f1536a.append(aVar, i, i2 + i);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0016, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001c, code lost:
        throw new c.a.a.w(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001d, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0023, code lost:
        throw new c.a.a.D(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0024, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0025, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002a, code lost:
        return c.a.a.x.f1689a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0030, code lost:
        throw new c.a.a.D(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0015, code lost:
        throw new c.a.a.D(r2);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0016 A[ExcHandler: IOException (r2v5 'e' java.io.IOException A[CUSTOM_DECLARE]), Splitter:B:0:0x0000] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x001d A[ExcHandler: e (r2v4 'e' c.a.a.d.e A[CUSTOM_DECLARE]), Splitter:B:0:0x0000] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0028  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x000f A[ExcHandler: NumberFormatException (r2v6 'e' java.lang.NumberFormatException A[CUSTOM_DECLARE]), Splitter:B:0:0x0000] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static c.a.a.v a(c.a.a.d.b r2) {
        /*
            r2.o()     // Catch:{ EOFException -> 0x0024, e -> 0x001d, IOException -> 0x0016, NumberFormatException -> 0x000f }
            r0 = 0
            c.a.a.J<c.a.a.v> r1 = c.a.a.b.a.ca.P     // Catch:{ EOFException -> 0x000d, e -> 0x001d, IOException -> 0x0016, NumberFormatException -> 0x000f }
            java.lang.Object r2 = r1.a(r2)     // Catch:{ EOFException -> 0x000d, e -> 0x001d, IOException -> 0x0016, NumberFormatException -> 0x000f }
            c.a.a.v r2 = (c.a.a.v) r2     // Catch:{ EOFException -> 0x000d, e -> 0x001d, IOException -> 0x0016, NumberFormatException -> 0x000f }
            return r2
        L_0x000d:
            r2 = move-exception
            goto L_0x0026
        L_0x000f:
            r2 = move-exception
            c.a.a.D r0 = new c.a.a.D
            r0.<init>(r2)
            throw r0
        L_0x0016:
            r2 = move-exception
            c.a.a.w r0 = new c.a.a.w
            r0.<init>(r2)
            throw r0
        L_0x001d:
            r2 = move-exception
            c.a.a.D r0 = new c.a.a.D
            r0.<init>(r2)
            throw r0
        L_0x0024:
            r2 = move-exception
            r0 = 1
        L_0x0026:
            if (r0 == 0) goto L_0x002b
            c.a.a.x r2 = c.a.a.x.f1689a
            return r2
        L_0x002b:
            c.a.a.D r0 = new c.a.a.D
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: c.a.a.b.A.a(c.a.a.d.b):c.a.a.v");
    }

    public static Writer a(Appendable appendable) {
        return appendable instanceof Writer ? (Writer) appendable : new a(appendable);
    }

    public static void a(v vVar, d dVar) {
        ca.P.a(dVar, vVar);
    }
}
