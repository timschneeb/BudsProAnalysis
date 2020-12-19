package com.github.penfeizhou.animation.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ByteBufferReader implements Reader {
    private final ByteBuffer byteBuffer;

    @Override // com.github.penfeizhou.animation.io.Reader
    public void close() throws IOException {
    }

    public ByteBufferReader(ByteBuffer byteBuffer2) {
        this.byteBuffer = byteBuffer2;
        byteBuffer2.position(0);
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public long skip(long j) throws IOException {
        ByteBuffer byteBuffer2 = this.byteBuffer;
        byteBuffer2.position((int) (((long) byteBuffer2.position()) + j));
        return j;
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public byte peek() throws IOException {
        return this.byteBuffer.get();
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public void reset() throws IOException {
        this.byteBuffer.position(0);
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public int position() {
        return this.byteBuffer.position();
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public int read(byte[] bArr, int i, int i2) throws IOException {
        this.byteBuffer.get(bArr, i, i2);
        return i2;
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public int available() throws IOException {
        return this.byteBuffer.limit() - this.byteBuffer.position();
    }

    @Override // com.github.penfeizhou.animation.io.Reader
    public InputStream toInputStream() throws IOException {
        return new ByteArrayInputStream(this.byteBuffer.array());
    }
}
