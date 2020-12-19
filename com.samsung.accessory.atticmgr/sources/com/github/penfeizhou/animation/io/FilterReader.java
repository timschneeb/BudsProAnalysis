package com.github.penfeizhou.animation.io;

import java.io.IOException;
import java.io.InputStream;

public class FilterReader implements Reader {
    protected Reader reader;

    public FilterReader(Reader reader2) {
        this.reader = reader2;
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public long skip(long j) throws IOException {
        return this.reader.skip(j);
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public byte peek() throws IOException {
        return this.reader.peek();
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public void reset() throws IOException {
        this.reader.reset();
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public int position() {
        return this.reader.position();
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.reader.read(bArr, i, i2);
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public int available() throws IOException {
        return this.reader.available();
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public void close() throws IOException {
        this.reader.close();
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public InputStream toInputStream() throws IOException {
        reset();
        return this.reader.toInputStream();
    }
}
