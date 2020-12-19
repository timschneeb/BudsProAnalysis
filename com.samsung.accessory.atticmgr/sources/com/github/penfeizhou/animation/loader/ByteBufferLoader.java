package com.github.penfeizhou.animation.loader;

import com.github.penfeizhou.animation.io.ByteBufferReader;
import com.github.penfeizhou.animation.io.Reader;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class ByteBufferLoader implements Loader {
    public abstract ByteBuffer getByteBuffer();

    @Override // com.github.penfeizhou.animation.loader.Loader
    public Reader obtain() throws IOException {
        return new ByteBufferReader(getByteBuffer());
    }
}
