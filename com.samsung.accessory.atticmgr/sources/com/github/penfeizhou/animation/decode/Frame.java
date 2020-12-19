package com.github.penfeizhou.animation.decode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.github.penfeizhou.animation.io.Reader;
import com.github.penfeizhou.animation.io.Writer;

public abstract class Frame<R extends Reader, W extends Writer> {
    public int frameDuration;
    public int frameHeight;
    public int frameWidth;
    public int frameX;
    public int frameY;
    protected final R reader;

    public abstract Bitmap draw(Canvas canvas, Paint paint, int i, Bitmap bitmap, W w);

    public Frame(R r) {
        this.reader = r;
    }
}
