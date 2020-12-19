package com.github.penfeizhou.animation.io;

import java.io.IOException;
import java.io.InputStream;

public interface Reader {
    int available() throws IOException;

    void close() throws IOException;

    byte peek() throws IOException;

    int position();

    int read(byte[] bArr, int i, int i2) throws IOException;

    void reset() throws IOException;

    long skip(long j) throws IOException;

    InputStream toInputStream() throws IOException;
}
