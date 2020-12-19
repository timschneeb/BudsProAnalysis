package com.samsung.android.app.twatchmanager.uiitems;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.samsung.android.app.watchmanager.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SpaceView extends View {
    private static final String TAG = "SpaceView";
    int animateStarCount;
    int count = 0;
    int delay = 30;
    long deltaTime;
    long fps;
    float fpsOffset;
    Handler handler = new Handler();
    Runnable invalidateRunnable = new Runnable() {
        /* class com.samsung.android.app.twatchmanager.uiitems.SpaceView.AnonymousClass1 */

        public void run() {
            SpaceView.this.invalidate();
        }
    };
    long prevTime = System.currentTimeMillis();
    Random random;
    private boolean restoreState;
    List<Star> starAnimationList;
    int starColor = -1;
    int starCount = 200;
    Paint starFullPaint;
    ArrayList<Star> starList;
    Paint starPaint;
    Paint starShadowPaint;
    int starShadowRadious = 0;

    /* access modifiers changed from: private */
    public static class Star {
        static int inr = 10;
        static Random random = new Random();
        int STATE_DIEING = 0;
        int STATE_GLOWING = 1;
        int alpha = 255;
        boolean animationFinished;
        int currentStatus = this.STATE_DIEING;
        Point point;
        int starRadius = 1;

        public Star() {
        }

        public Star(Point point2, boolean z, boolean z2) {
            this.point = point2;
            if (z2) {
                this.starRadius = 3;
            } else {
                this.starRadius = 1;
            }
            this.alpha = random.nextInt(255);
            inr = random.nextInt(20) + 10;
            this.currentStatus = z ? this.STATE_DIEING : this.STATE_GLOWING;
        }

        public static Star getInstance(String str) {
            String[] split = str.split(",");
            Point point2 = new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            Star star = new Star();
            star.point = point2;
            star.starRadius = Integer.parseInt(split[2]);
            inr = Integer.parseInt(split[3]);
            star.currentStatus = Integer.parseInt(split[4]);
            star.alpha = Integer.parseInt(split[5]);
            return star;
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private String getState() {
            return String.valueOf(this.point.x) + "," + this.point.y + "," + this.starRadius + "," + inr + "," + this.currentStatus + "," + this.alpha;
        }

        /* access modifiers changed from: package-private */
        public void animate() {
            int i;
            int i2;
            if (this.currentStatus == this.STATE_DIEING && (i2 = this.alpha) > 0) {
                this.alpha = i2 - inr;
            }
            if (this.currentStatus == this.STATE_DIEING && this.alpha <= 0) {
                this.currentStatus = this.STATE_GLOWING;
                this.alpha = 0;
                this.animationFinished = true;
            }
            if (this.currentStatus == this.STATE_GLOWING && (i = this.alpha) < 255) {
                this.alpha = i + inr;
            }
            if (this.currentStatus == this.STATE_GLOWING && this.alpha >= 255) {
                this.alpha = 255;
                this.currentStatus = this.STATE_DIEING;
                this.animationFinished = true;
            }
        }

        public String toString() {
            return "Star{point=" + this.point + ", alpha=" + this.alpha + ", currentStatus=" + this.currentStatus + ", animationFinished=" + this.animationFinished + '}';
        }
    }

    public SpaceView(Context context) {
        super(context);
        init(null, 0);
    }

    public SpaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0);
    }

    public SpaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }

    private void init(AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.SpaceView, i, 0);
        this.starCount = obtainStyledAttributes.getInt(1, 200);
        this.starColor = obtainStyledAttributes.getColor(0, -1);
        obtainStyledAttributes.recycle();
        this.random = new Random();
        this.starPaint = new Paint();
        this.starPaint.setColor(this.starColor);
        this.starPaint.setAntiAlias(true);
        this.starFullPaint = new Paint();
        this.starFullPaint.setColor(this.starColor);
        this.starFullPaint.setTextSize(60.0f);
        this.starFullPaint.setAntiAlias(true);
        this.starShadowPaint = new Paint();
        this.starShadowPaint.setColor(this.starColor);
        this.starShadowPaint.setAntiAlias(true);
        this.starList = new ArrayList<>(this.starCount);
        this.animateStarCount = this.starCount / 4;
        this.starAnimationList = new ArrayList();
    }

    public String getAnimationState() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Star> arrayList = this.starList;
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<Star> it = this.starList.iterator();
            while (it.hasNext()) {
                sb.append(it.next().getState());
                sb.append("#");
            }
        }
        String str = TAG;
        Log.d(str, "getAnimationState():" + ((Object) sb));
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.count++;
        this.deltaTime = System.currentTimeMillis() - this.prevTime;
        if (this.deltaTime > 1000) {
            this.fps = (long) this.count;
            this.prevTime = System.currentTimeMillis();
            this.count = 0;
            Log.d(TAG, "fps :" + this.fps);
        }
        Iterator<Star> it = this.starList.iterator();
        while (it.hasNext()) {
            Star next = it.next();
            next.animate();
            this.starPaint.setAlpha(next.alpha);
            Point point = next.point;
            canvas.drawCircle((float) point.x, (float) point.y, (float) next.starRadius, this.starPaint);
        }
        this.handler.postDelayed(this.invalidateRunnable, (long) this.delay);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (!this.restoreState) {
            double d2 = (double) this.starCount;
            Double.isNaN(d2);
            int i5 = (int) (d2 * 0.025d);
            Log.d(TAG, "onSizeChanged() starCount:" + this.starCount + " bigStarCount:" + i5);
            int i6 = 0;
            boolean z = true;
            while (i6 < this.starCount) {
                this.starList.add(new Star(new Point(this.random.nextInt(i), this.random.nextInt(i2)), z, i6 < i5));
                z = !z;
                i6++;
            }
        }
    }

    public void restoreState(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split("#");
            int length = split.length;
            Log.d(TAG, "restoreState() length:" + length);
            for (String str2 : split) {
                this.starList.add(Star.getInstance(str2));
            }
            this.restoreState = true;
        }
    }
}
