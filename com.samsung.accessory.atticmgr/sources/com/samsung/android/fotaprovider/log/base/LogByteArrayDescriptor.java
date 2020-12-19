package com.samsung.android.fotaprovider.log.base;

import com.samsung.android.fotaprovider.log.base.LogDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class LogByteArrayDescriptor extends LogDescriptor.Stream {
    private ByteArrayOutputStream stream = new ByteArrayOutputStream();

    public byte[] toByteArray() {
        return this.stream.toByteArray();
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.fotaprovider.log.base.LogDescriptor.Stream
    public long size() {
        return (long) this.stream.size();
    }

    @Override // com.samsung.android.fotaprovider.log.base.LogDescriptor
    public void shift() {
        this.stream.reset();
    }

    @Override // com.samsung.android.fotaprovider.log.base.LogDescriptor.Stream
    public OutputStream getOutputStream() {
        return this.stream;
    }
}
