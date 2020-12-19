package com.github.penfeizhou.animation.webp.decode;

import com.github.penfeizhou.animation.webp.io.WebPReader;
import java.io.IOException;

public class VP8XChunk extends BaseChunk {
    private static final int FLAG_ALPHA = 16;
    private static final int FLAG_ANIMATION = 2;
    static final int ID = BaseChunk.fourCCToInt("VP8X");
    public int canvasHeight;
    public int canvasWidth;
    byte flags;

    /* access modifiers changed from: package-private */
    @Override // com.github.penfeizhou.animation.webp.decode.BaseChunk
    public void innerParse(WebPReader webPReader) throws IOException {
        this.flags = webPReader.peek();
        webPReader.skip(3);
        this.canvasWidth = webPReader.get1Based();
        this.canvasHeight = webPReader.get1Based();
    }

    /* access modifiers changed from: package-private */
    public boolean animation() {
        return (this.flags & 2) == 2;
    }

    /* access modifiers changed from: package-private */
    public boolean alpha() {
        return (this.flags & 16) == 16;
    }
}
