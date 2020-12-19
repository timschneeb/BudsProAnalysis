package com.accessorydm.tp.downloadInfo;

import com.samsung.android.fotaprovider.log.Log;
import java.util.Arrays;

public class DownloadFlexibleBuffer {
    private byte[] flexibleBuffer;
    private int flexibleBufferLen;
    private int nAppendBufferSize;
    private int nRealWriteBufferLen;
    private int nReceiveBufferSize;
    private byte[] realWriteBuffer;

    public DownloadFlexibleBuffer(int i, int i2) {
        this.nAppendBufferSize = i;
        this.nReceiveBufferSize = i2;
    }

    public void flexibleBufferAllClear() {
        this.flexibleBuffer = null;
        this.flexibleBufferLen = 0;
        this.realWriteBuffer = null;
        this.nRealWriteBufferLen = 0;
    }

    public void addFlexibleBuffer(byte[] bArr, int i) {
        try {
            if (this.flexibleBuffer == null) {
                this.flexibleBuffer = new byte[(this.nAppendBufferSize + this.nReceiveBufferSize)];
            }
            System.arraycopy(bArr, 0, this.flexibleBuffer, this.flexibleBufferLen, i);
            addFlexibleBuffer(i);
        } catch (Exception e) {
            Log.printStackTrace(e);
        }
    }

    private void addRealWriteBuffer() {
        try {
            this.realWriteBuffer = new byte[this.flexibleBufferLen];
            setRealWriteBufferLength(this.flexibleBufferLen);
            System.arraycopy(this.flexibleBuffer, 0, this.realWriteBuffer, 0, this.flexibleBufferLen);
        } catch (Exception e) {
            Log.printStackTrace(e);
        }
    }

    private void addFlexibleBuffer(int i) {
        this.flexibleBufferLen += i;
    }

    public int getFlexibleBufferLength() {
        return this.flexibleBufferLen;
    }

    public void clearFlexibleBuffer() {
        this.flexibleBuffer = null;
        this.flexibleBufferLen = 0;
    }

    public int getAppendSavedBufferSize() {
        return this.nAppendBufferSize;
    }

    public byte[] getRealWriteBuffer() {
        addRealWriteBuffer();
        byte[] bArr = this.realWriteBuffer;
        return Arrays.copyOf(bArr, bArr.length);
    }

    private void setRealWriteBufferLength(int i) {
        this.nRealWriteBufferLen += i;
    }

    public int getRealWriteBufferLength() {
        int i = this.nRealWriteBufferLen;
        this.nRealWriteBufferLen = 0;
        return i;
    }
}
