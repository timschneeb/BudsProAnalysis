package com.samsung.sht.floating;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class CompassView extends View {
    private float angle = 0.0f;

    public CompassView(Context context) {
        super(context);
    }

    public void setAngle(float f) {
        this.angle = f;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth() / 2;
        int height = canvas.getHeight() / 2;
        int width2 = (canvas.getWidth() / 2) - 10;
        Paint paint = new Paint(1);
        paint.setStrokeWidth(10.0f);
        paint.setColor(-1);
        paint.setStyle(Paint.Style.FILL);
        float f = (float) width;
        float f2 = (float) height;
        float f3 = (float) width2;
        canvas.drawCircle(f, f2, f3, paint);
        paint.setColor(-16776961);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(f, f2, f3, paint);
        paint.setStrokeWidth(3.0f);
        paint.setColor(-7829368);
        canvas.drawLine(f, f2, (float) (width - width2), f2, paint);
        canvas.drawLine(f, f2, (float) (width + width2), f2, paint);
        float f4 = (float) (height - width2);
        canvas.drawLine(f, f2, f, f4, paint);
        canvas.drawLine(f, f2, f, (float) (height + width2), paint);
        paint.setColor(-16776961);
        paint.setStrokeWidth(10.0f);
        canvas.save();
        canvas.rotate(this.angle, f, f2);
        canvas.drawLine(f, f2, f, f4, paint);
        canvas.restore();
    }
}
