package com.github.penfeizhou.animation.webp.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.github.penfeizhou.animation.decode.Frame;
import com.github.penfeizhou.animation.io.Reader;
import com.github.penfeizhou.animation.io.Writer;
import java.io.IOException;

public class StillFrame extends Frame {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public StillFrame(Reader reader, int i, int i2) {
        super(reader);
        this.frameWidth = i;
        this.frameHeight = i2;
    }

    @Override // com.github.penfeizhou.animation.decode.Frame
    public Bitmap draw(Canvas canvas, Paint paint, int i, Bitmap bitmap, Writer writer) {
        Bitmap bitmap2;
        IOException e;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = i;
        options.inMutable = true;
        options.inBitmap = bitmap;
        try {
            bitmap2 = BitmapFactory.decodeStream(this.reader.toInputStream(), null, options);
            try {
                paint.setXfermode(null);
                canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
            } catch (IOException e2) {
                e = e2;
            }
        } catch (IOException e3) {
            e = e3;
            bitmap2 = null;
            e.printStackTrace();
            return bitmap2;
        }
        return bitmap2;
    }
}
