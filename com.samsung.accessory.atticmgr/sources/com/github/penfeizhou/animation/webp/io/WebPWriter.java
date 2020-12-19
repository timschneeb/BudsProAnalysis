package com.github.penfeizhou.animation.webp.io;

import android.text.TextUtils;
import com.github.penfeizhou.animation.io.ByteBufferWriter;

public class WebPWriter extends ByteBufferWriter {
    public void putUInt16(int i) {
        putByte((byte) (i & 255));
        putByte((byte) ((i >> 8) & 255));
    }

    public void putUInt24(int i) {
        putByte((byte) (i & 255));
        putByte((byte) ((i >> 8) & 255));
        putByte((byte) ((i >> 16) & 255));
    }

    public void putUInt32(int i) {
        putByte((byte) (i & 255));
        putByte((byte) ((i >> 8) & 255));
        putByte((byte) ((i >> 16) & 255));
        putByte((byte) ((i >> 24) & 255));
    }

    public void put1Based(int i) {
        putUInt24(i - 1);
    }

    public void putFourCC(String str) {
        if (TextUtils.isEmpty(str) || str.length() != 4) {
            skip(4);
            return;
        }
        putByte((byte) (str.charAt(0) & 255));
        putByte((byte) (str.charAt(1) & 255));
        putByte((byte) (str.charAt(2) & 255));
        putByte((byte) (str.charAt(3) & 255));
    }
}
