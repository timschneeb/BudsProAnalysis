package c.a.a.b.a;

import java.io.Writer;

/* renamed from: c.a.a.b.a.i  reason: case insensitive filesystem */
class C0126i extends Writer {
    C0126i() {
    }

    @Override // java.io.Closeable, java.io.Writer, java.lang.AutoCloseable
    public void close() {
        throw new AssertionError();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        throw new AssertionError();
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        throw new AssertionError();
    }
}
