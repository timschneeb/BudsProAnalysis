package com.github.penfeizhou.animation.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamReader extends FilterInputStream implements Reader {
    private int position;

    @Override // com.github.penfeizhou.animation.io.Reader
    public InputStream toInputStream() throws IOException {
        return this;
    }

    public StreamReader(InputStream inputStream) {
        super(inputStream);
        try {
            inputStream.reset();
        } catch (IOException unused) {
        }
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public byte peek() throws IOException {
        byte read = (byte) read();
        this.position++;
        return read;
    }

    @Override // java.io.FilterInputStream, com.github.penfeizhou.animation.io.Reader, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = super.read(bArr, i, i2);
        this.position += Math.max(0, read);
        return read;
    }

    @Override // java.io.FilterInputStream, com.github.penfeizhou.animation.io.Reader, java.io.InputStream
    public synchronized void reset() throws IOException {
        super.reset();
        this.position = 0;
    }

    @Override // java.io.FilterInputStream, com.github.penfeizhou.animation.io.Reader, java.io.InputStream
    public long skip(long j) throws IOException {
        long skip = super.skip(j);
        this.position = (int) (((long) this.position) + skip);
        return skip;
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public int position() {
        return this.position;
    }
}
