package com.samsung.accessory.hearablemgr.common.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;

public class BufferBuilder {
    private List<Byte> mByteList;
    private ByteOrder mByteOrder;

    public BufferBuilder(ByteOrder byteOrder) {
        this.mByteList = new LinkedList();
        this.mByteOrder = byteOrder;
    }

    public BufferBuilder() {
        this(ByteOrder.LITTLE_ENDIAN);
    }

    public BufferBuilder put(byte b) {
        this.mByteList.add(Byte.valueOf(b));
        return this;
    }

    public BufferBuilder putShort(short s) {
        ByteBuffer allocate = ByteBuffer.allocate(2);
        allocate.order(this.mByteOrder);
        allocate.putShort(s);
        putByteBuffer(allocate);
        return this;
    }

    public BufferBuilder putInt(int i) {
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.order(this.mByteOrder);
        allocate.putInt(i);
        putByteBuffer(allocate);
        return this;
    }

    public BufferBuilder putLong(long j) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(this.mByteOrder);
        allocate.putLong(j);
        putByteBuffer(allocate);
        return this;
    }

    public final byte[] array() {
        byte[] bArr = new byte[this.mByteList.size()];
        int i = 0;
        for (Byte b : this.mByteList) {
            bArr[i] = b.byteValue();
            i++;
        }
        return bArr;
    }

    private BufferBuilder putByteBuffer(ByteBuffer byteBuffer) {
        for (byte b : byteBuffer.array()) {
            this.mByteList.add(Byte.valueOf(b));
        }
        return this;
    }
}
