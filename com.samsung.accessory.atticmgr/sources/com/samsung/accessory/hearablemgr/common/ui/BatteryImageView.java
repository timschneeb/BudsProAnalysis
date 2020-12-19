package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.samsung.accessory.atticmgr.R;

public class BatteryImageView extends FrameLayout {
    static final int COLOR_LOW_BATTERY = Color.parseColor("#D04A02");
    static final int COLOR_LOW_BATTERY_BG = Color.parseColor("#F0C7B1");
    static final int COLOR_NORMAL = Color.parseColor("#45BF10");
    static final int COLOR_NORMAL_BG = Color.parseColor("#C5EBB5");
    static final int VALUE_LOW_BATTERY = 30;
    private final PorterDuffXfermode MODE_COMPOSITE = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private int mBatteryValue;
    private boolean mCradle;
    private Drawable mDrawableMask;
    private ImageView mImageBgFrame;
    private Paint mPaintMask = new Paint(1);
    private View mViewBatteryGauge;

    public BatteryImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0, 0);
    }

    public BatteryImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i, 0);
    }

    public BatteryImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context, attributeSet, i, i2);
    }

    private void init(Context context, AttributeSet attributeSet, int i, int i2) {
        setLayerType(1, null);
        this.mDrawableMask = getResources().getDrawable(R.drawable.battery_dim);
        Object tag = getTag();
        if (tag != null && (tag instanceof String)) {
            String str = (String) tag;
            char c = 65535;
            if (str.hashCode() == -1352410677 && str.equals("cradle")) {
                c = 0;
            }
            if (c == 0) {
                this.mCradle = true;
            }
        }
        addView(LayoutInflater.from(context).inflate(R.layout.view_battery_image, (ViewGroup) null));
        this.mImageBgFrame = (ImageView) findViewById(R.id.image_bg_frame);
        this.mViewBatteryGauge = findViewById(R.id.view_battery_gauge);
        setBatteryValue(0);
    }

    public void setBatteryValue(int i) {
        this.mBatteryValue = i;
        int i2 = this.mBatteryValue;
        if (i2 >= 30) {
            this.mImageBgFrame.setImageResource(R.drawable.battery_dim);
            this.mImageBgFrame.setColorFilter(COLOR_NORMAL_BG);
            this.mViewBatteryGauge.setVisibility(0);
        } else if (i2 >= 10) {
            this.mImageBgFrame.setImageResource(R.drawable.battery_dim);
            this.mImageBgFrame.setColorFilter(COLOR_LOW_BATTERY_BG);
            this.mViewBatteryGauge.setVisibility(0);
        } else if (i2 > 0) {
            this.mImageBgFrame.setImageResource(R.drawable.battery_low);
            this.mImageBgFrame.setColorFilter((ColorFilter) null);
            this.mViewBatteryGauge.setVisibility(0);
        } else if (!this.mCradle || i2 != 0) {
            this.mImageBgFrame.setImageResource(R.drawable.battery_disconnect);
            this.mImageBgFrame.setColorFilter((ColorFilter) null);
            this.mViewBatteryGauge.setVisibility(4);
        } else {
            this.mImageBgFrame.setImageResource(R.drawable.battery_low);
            this.mImageBgFrame.setColorFilter((ColorFilter) null);
            this.mViewBatteryGauge.setVisibility(0);
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mViewBatteryGauge.getLayoutParams();
        int i3 = this.mBatteryValue;
        layoutParams.matchConstraintPercentHeight = i3 >= 0 ? ((float) i3) / 100.0f : 0.0f;
        this.mViewBatteryGauge.setLayoutParams(layoutParams);
        int i4 = this.mBatteryValue;
        if (i4 >= 0) {
            this.mViewBatteryGauge.setBackgroundColor(i4 >= 30 ? COLOR_NORMAL : COLOR_LOW_BATTERY);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Bitmap createBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap);
        this.mDrawableMask.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        this.mDrawableMask.draw(canvas2);
        this.mPaintMask.setXfermode(this.MODE_COMPOSITE);
        canvas.drawBitmap(createBitmap, 0.0f, 0.0f, this.mPaintMask);
        this.mPaintMask.setXfermode(null);
    }
}
