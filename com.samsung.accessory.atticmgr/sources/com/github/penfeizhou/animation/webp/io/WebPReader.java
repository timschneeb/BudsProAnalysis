package com.github.penfeizhou.animation.webp.io;

import android.text.TextUtils;
import com.github.penfeizhou.animation.io.FilterReader;
import com.github.penfeizhou.animation.io.Reader;
import java.io.IOException;

public class WebPReader extends FilterReader {
    private static ThreadLocal<byte[]> __intBytes = new ThreadLocal<>();

    protected static byte[] ensureBytes() {
        byte[] bArr = __intBytes.get();
        if (bArr != null) {
            return bArr;
        }
        byte[] bArr2 = new byte[4];
        __intBytes.set(bArr2);
        return bArr2;
    }

    public WebPReader(Reader reader) {
        super(reader);
    }

    public int getUInt16() throws IOException {
        byte[] ensureBytes = ensureBytes();
        read(ensureBytes, 0, 2);
        return ((ensureBytes[1] & 255) << 8) | (ensureBytes[0] & 255);
    }

    public int getUInt24() throws IOException {
        byte[] ensureBytes = ensureBytes();
        read(ensureBytes, 0, 3);
        return ((ensureBytes[2] & 255) << 16) | (ensureBytes[0] & 255) | ((ensureBytes[1] & 255) << 8);
    }

    public int getUInt32() throws IOException {
        byte[] ensureBytes = ensureBytes();
        read(ensureBytes, 0, 4);
        return ((ensureBytes[3] & 255) << 24) | (ensureBytes[0] & 255) | ((ensureBytes[1] & 255) << 8) | ((ensureBytes[2] & 255) << 16);
    }

    public int getFourCC() throws IOException {
        byte[] ensureBytes = ensureBytes();
        read(ensureBytes, 0, 4);
        return ((ensureBytes[3] & 255) << 24) | (ensureBytes[0] & 255) | ((ensureBytes[1] & 255) << 8) | ((ensureBytes[2] & 255) << 16);
    }

    public int get1Based() throws IOException {
        return getUInt24() + 1;
    }

    public boolean matchFourCC(String str) throws IOException {
        if (TextUtils.isEmpty(str) || str.length() != 4) {
            return false;
        }
        int fourCC = getFourCC();
        for (int i = 0; i < 4; i++) {
            if (((fourCC >> (i * 8)) & 255) != str.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
