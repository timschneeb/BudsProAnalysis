package com.github.penfeizhou.animation.webp.decode;

import com.github.penfeizhou.animation.webp.io.WebPReader;
import java.io.IOException;

public class ANIMChunk extends BaseChunk {
    static final int ID = BaseChunk.fourCCToInt("ANIM");
    int backgroundColor;
    int loopCount;

    /* access modifiers changed from: package-private */
    @Override // com.github.penfeizhou.animation.webp.decode.BaseChunk
    public void innerParse(WebPReader webPReader) throws IOException {
        this.backgroundColor = webPReader.getUInt32();
        this.loopCount = webPReader.getUInt16();
    }
}
