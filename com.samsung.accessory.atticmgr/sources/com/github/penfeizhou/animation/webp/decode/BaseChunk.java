package com.github.penfeizhou.animation.webp.decode;

import android.text.TextUtils;
import com.github.penfeizhou.animation.webp.io.WebPReader;
import java.io.IOException;

public class BaseChunk {
    public static final int CHUNCK_HEADER_OFFSET = 8;
    public int chunkFourCC;
    public int offset;
    public int payloadSize;

    /* access modifiers changed from: package-private */
    public void innerParse(WebPReader webPReader) throws IOException {
    }

    public static int fourCCToInt(String str) {
        if (TextUtils.isEmpty(str) || str.length() != 4) {
            return -1159790593;
        }
        return ((str.charAt(3) & 255) << 24) | (str.charAt(0) & 255) | ((str.charAt(1) & 255) << 8) | ((str.charAt(2) & 255) << 16);
    }

    /* access modifiers changed from: package-private */
    public final void parse(WebPReader webPReader) throws IOException {
        int available = webPReader.available();
        innerParse(webPReader);
        int available2 = available - webPReader.available();
        int i = this.payloadSize;
        int i2 = i + (i & 1);
        if (available2 > i2) {
            throw new IOException("Out of chunk area");
        } else if (available2 < i2) {
            webPReader.skip((long) (i2 - available2));
        }
    }
}
