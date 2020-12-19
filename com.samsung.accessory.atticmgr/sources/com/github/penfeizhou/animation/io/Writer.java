package com.github.penfeizhou.animation.io;

import java.io.IOException;

public interface Writer {
    void close() throws IOException;

    int position();

    void putByte(byte b);

    void putBytes(byte[] bArr);

    void reset(int i);

    void skip(int i);

    byte[] toByteArray();
}
