package com.github.penfeizhou.animation.webp.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import com.github.penfeizhou.animation.decode.Frame;
import com.github.penfeizhou.animation.webp.io.WebPReader;
import com.github.penfeizhou.animation.webp.io.WebPWriter;
import java.io.IOException;

public class AnimationFrame extends Frame<WebPReader, WebPWriter> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final PorterDuffXfermode PORTERDUFF_XFERMODE_SRC_OVER = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
    final boolean blendingMethod;
    final boolean disposalMethod;
    final int imagePayloadOffset;
    final int imagePayloadSize;
    private final boolean useAlpha;

    public AnimationFrame(WebPReader webPReader, ANMFChunk aNMFChunk) {
        super(webPReader);
        this.frameWidth = aNMFChunk.frameWidth;
        this.frameHeight = aNMFChunk.frameHeight;
        this.frameX = aNMFChunk.frameX;
        this.frameY = aNMFChunk.frameY;
        this.frameDuration = aNMFChunk.frameDuration;
        if (this.frameDuration == 0) {
            this.frameDuration = 100;
        }
        this.blendingMethod = aNMFChunk.blendingMethod();
        this.disposalMethod = aNMFChunk.disposalMethod();
        this.imagePayloadOffset = aNMFChunk.offset + 8 + 16;
        boolean z = true;
        this.imagePayloadSize = (aNMFChunk.payloadSize - 16) + (aNMFChunk.payloadSize & 1);
        this.useAlpha = aNMFChunk.alphChunk == null ? false : z;
    }

    private int encode(WebPWriter webPWriter) {
        int i = 30 + this.imagePayloadSize;
        webPWriter.reset(i);
        webPWriter.putFourCC("RIFF");
        webPWriter.putUInt32(i);
        webPWriter.putFourCC("WEBP");
        webPWriter.putUInt32(VP8XChunk.ID);
        webPWriter.putUInt32(10);
        webPWriter.putByte((byte) (this.useAlpha ? 16 : 0));
        webPWriter.putUInt24(0);
        webPWriter.put1Based(this.frameWidth);
        webPWriter.put1Based(this.frameHeight);
        try {
            ((WebPReader) this.reader).reset();
            ((WebPReader) this.reader).skip((long) this.imagePayloadOffset);
            ((WebPReader) this.reader).read(webPWriter.toByteArray(), webPWriter.position(), this.imagePayloadSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    public Bitmap draw(Canvas canvas, Paint paint, int i, Bitmap bitmap, WebPWriter webPWriter) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = i;
        options.inMutable = true;
        options.inBitmap = bitmap;
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(webPWriter.toByteArray(), 0, encode(webPWriter), options);
        if (this.blendingMethod) {
            paint.setXfermode(null);
        } else {
            paint.setXfermode(PORTERDUFF_XFERMODE_SRC_OVER);
        }
        float f = (float) i;
        canvas.drawBitmap(decodeByteArray, (((float) this.frameX) * 2.0f) / f, (((float) this.frameY) * 2.0f) / f, paint);
        return decodeByteArray;
    }
}
