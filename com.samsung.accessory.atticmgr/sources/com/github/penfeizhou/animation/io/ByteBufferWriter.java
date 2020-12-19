package com.github.penfeizhou.animation.io;

import androidx.work.Data;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteBufferWriter implements Writer {
    protected ByteBuffer byteBuffer;

    @Override // com.github.penfeizhou.animation.io.Writer
    public void close() {
    }

    public ByteBufferWriter() {
        reset(Data.MAX_DATA_BYTES);
    }

    @Override // com.github.penfeizhou.animation.io.Writer
    public void putByte(byte b) {
        this.byteBuffer.put(b);
    }

    @Override // com.github.penfeizhou.animation.io.Writer
    public void putBytes(byte[] bArr) {
        this.byteBuffer.put(bArr);
    }

    @Override // com.github.penfeizhou.animation.io.Writer
    public int position() {
        return this.byteBuffer.position();
    }

    @Override // com.github.penfeizhou.animation.io.Writer
    public void skip(int i) {
        this.byteBuffer.position(i + position());
    }

    @Override // com.github.penfeizhou.animation.io.Writer
    public byte[] toByteArray() {
        return this.byteBuffer.array();
    }

    @Override // com.github.penfeizhou.animation.io.Writer
    public void reset(int i) {
        ByteBuffer byteBuffer2 = this.byteBuffer;
        if (byteBuffer2 == null || i > byteBuffer2.capacity()) {
            this.byteBuffer = ByteBuffer.allocate(i);
            this.byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        }
        this.byteBuffer.clear();
    }
}
