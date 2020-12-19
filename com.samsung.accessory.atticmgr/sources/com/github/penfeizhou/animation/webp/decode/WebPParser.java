package com.github.penfeizhou.animation.webp.decode;

import android.content.Context;
import com.github.penfeizhou.animation.io.Reader;
import com.github.penfeizhou.animation.io.StreamReader;
import com.github.penfeizhou.animation.webp.io.WebPReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WebPParser {

    /* access modifiers changed from: package-private */
    public static class FormatException extends IOException {
        FormatException() {
            super("WebP Format error");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x001e A[SYNTHETIC, Splitter:B:14:0x001e] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x002b A[SYNTHETIC, Splitter:B:24:0x002b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isAWebP(java.lang.String r2) {
        /*
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0027, all -> 0x001a }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0027, all -> 0x001a }
            com.github.penfeizhou.animation.io.StreamReader r2 = new com.github.penfeizhou.animation.io.StreamReader     // Catch:{ Exception -> 0x0028, all -> 0x0018 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0028, all -> 0x0018 }
            boolean r2 = isAWebP(r2)     // Catch:{ Exception -> 0x0028, all -> 0x0018 }
            r1.close()     // Catch:{ IOException -> 0x0013 }
            goto L_0x0017
        L_0x0013:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0017:
            return r2
        L_0x0018:
            r2 = move-exception
            goto L_0x001c
        L_0x001a:
            r2 = move-exception
            r1 = r0
        L_0x001c:
            if (r1 == 0) goto L_0x0026
            r1.close()     // Catch:{ IOException -> 0x0022 }
            goto L_0x0026
        L_0x0022:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0026:
            throw r2
        L_0x0027:
            r1 = r0
        L_0x0028:
            r2 = 0
            if (r1 == 0) goto L_0x0033
            r1.close()     // Catch:{ IOException -> 0x002f }
            goto L_0x0033
        L_0x002f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0033:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.penfeizhou.animation.webp.decode.WebPParser.isAWebP(java.lang.String):boolean");
    }

    public static boolean isAWebP(Context context, String str) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(str);
            boolean isAWebP = isAWebP(new StreamReader(inputStream));
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return isAWebP;
        } catch (Exception unused) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean isAWebP(Context context, int i) {
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().openRawResource(i);
            boolean isAWebP = isAWebP(new StreamReader(inputStream));
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return isAWebP;
        } catch (Exception unused) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean isAWebP(Reader reader) {
        WebPReader webPReader = reader instanceof WebPReader ? (WebPReader) reader : new WebPReader(reader);
        try {
            if (!webPReader.matchFourCC("RIFF")) {
                return false;
            }
            webPReader.skip(4);
            if (!webPReader.matchFourCC("WEBP")) {
                return false;
            }
            while (webPReader.available() > 0) {
                BaseChunk parseChunk = parseChunk(webPReader);
                if (parseChunk instanceof VP8XChunk) {
                    return ((VP8XChunk) parseChunk).animation();
                }
            }
            return false;
        } catch (IOException e) {
            if (!(e instanceof FormatException)) {
                e.printStackTrace();
            }
        }
    }

    public static List<BaseChunk> parse(WebPReader webPReader) throws IOException {
        if (webPReader.matchFourCC("RIFF")) {
            webPReader.skip(4);
            if (webPReader.matchFourCC("WEBP")) {
                ArrayList arrayList = new ArrayList();
                while (webPReader.available() > 0) {
                    arrayList.add(parseChunk(webPReader));
                }
                return arrayList;
            }
            throw new FormatException();
        }
        throw new FormatException();
    }

    static BaseChunk parseChunk(WebPReader webPReader) throws IOException {
        BaseChunk baseChunk;
        int position = webPReader.position();
        int fourCC = webPReader.getFourCC();
        int uInt32 = webPReader.getUInt32();
        if (VP8XChunk.ID == fourCC) {
            baseChunk = new VP8XChunk();
        } else if (ANIMChunk.ID == fourCC) {
            baseChunk = new ANIMChunk();
        } else if (ANMFChunk.ID == fourCC) {
            baseChunk = new ANMFChunk();
        } else if (ALPHChunk.ID == fourCC) {
            baseChunk = new ALPHChunk();
        } else if (VP8Chunk.ID == fourCC) {
            baseChunk = new VP8Chunk();
        } else if (VP8LChunk.ID == fourCC) {
            baseChunk = new VP8LChunk();
        } else if (ICCPChunk.ID == fourCC) {
            baseChunk = new ICCPChunk();
        } else if (XMPChunk.ID == fourCC) {
            baseChunk = new XMPChunk();
        } else if (EXIFChunk.ID == fourCC) {
            baseChunk = new EXIFChunk();
        } else {
            baseChunk = new BaseChunk();
        }
        baseChunk.chunkFourCC = fourCC;
        baseChunk.payloadSize = uInt32;
        baseChunk.offset = position;
        baseChunk.parse(webPReader);
        return baseChunk;
    }
}
