package com.samsung.accessory.hearablemgr.core.appwidget;

import android.appwidget.AppWidgetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetSettingBaseActivity;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WallpaperColorManager;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetUtil;
import seccompat.android.util.Log;

public class WidgetSettingMasterActivity extends WidgetSettingBaseActivity {
    private static final String TAG = "Attic_WidgetSettingQuickControlActivity";
    private ImageView mNoiseReductionImage;
    private TextView mNoiseReductionText;
    private TextView mTitleText;
    private ImageView mTouchpadLockImage;
    private TextView mTouchpadLockText;
    private ImageView mWidgetBackground;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetSettingBaseActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "onCreate()");
        setWidgetPreview(R.layout.widget_view_quick_control);
        WallpaperColorManager.initWallpaperColor(this);
        init();
        initView();
    }

    private void init() {
        this.mWidgetBackground = (ImageView) getWidgetPreview().findViewById(R.id.widget_background);
        this.mTitleText = (TextView) getWidgetPreview().findViewById(R.id.widget_text_device_bt_name);
        this.mNoiseReductionText = (TextView) getWidgetPreview().findViewById(R.id.text_widget_quick_control_option_1);
        this.mTouchpadLockText = (TextView) getWidgetPreview().findViewById(R.id.text_widget_quick_control_option_2);
        this.mNoiseReductionImage = (ImageView) getWidgetPreview().findViewById(R.id.image_widget_quick_control_option_1);
        this.mTouchpadLockImage = (ImageView) getWidgetPreview().findViewById(R.id.image_widget_quick_control_option_2);
    }

    private void initView() {
        this.mTitleText.setText(WidgetUtil.getDeviceAliasName(getApplicationContext()));
        this.mNoiseReductionText.setText(WidgetUtil.getNoiseControlStateText(getApplicationContext()));
        boolean isConnected = Application.getCoreService().isConnected();
        int i = R.drawable.widget_lock_touchpad_off;
        if (isConnected) {
            this.mNoiseReductionImage.setImageDrawable(getResources().getDrawable(WidgetUtil.getNoiseControlResourceId(Application.getContext(), true)));
            ImageView imageView = this.mTouchpadLockImage;
            Resources resources = getResources();
            if (WidgetUtil.getTouchpadLockEnabled(this)) {
                i = R.drawable.widget_lock_touchpad_on;
            }
            imageView.setImageDrawable(resources.getDrawable(i));
            this.mNoiseReductionImage.setAlpha(1.0f);
            this.mTouchpadLockImage.setAlpha(1.0f);
            this.mNoiseReductionText.setAlpha(1.0f);
            this.mTouchpadLockText.setAlpha(1.0f);
        } else {
            this.mNoiseReductionImage.setImageDrawable(getResources().getDrawable(WidgetUtil.getNoiseControlResourceId(Application.getContext(), false)));
            this.mTouchpadLockImage.setImageDrawable(getResources().getDrawable(R.drawable.widget_lock_touchpad_off));
            this.mNoiseReductionImage.setAlpha(0.4f);
            this.mTouchpadLockImage.setAlpha(0.4f);
            this.mNoiseReductionText.setAlpha(0.4f);
            this.mTouchpadLockText.setAlpha(0.4f);
        }
        onUpdatedAlpha(getWidgetInfo().alpha);
        onUpdatedColor(getWidgetInfo().color);
        onUpdatedDarkMode(getWidgetInfo().darkmode);
    }

    @Override // com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetSettingBaseActivity
    public Class getProviderClass() {
        return WidgetUtil.getWidgetMasterClass();
    }

    @Override // com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetSettingBaseActivity
    public void onUpdatedAlpha(int i) {
        this.mWidgetBackground.setImageAlpha((i * 255) / 100);
        onUpdatedColor(getWidgetInfo().color);
    }

    @Override // com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetSettingBaseActivity
    public void onUpdatedColor(int i) {
        int widgetColor = WidgetUtil.getWidgetColor(this, getWidgetInfo());
        this.mWidgetBackground.setColorFilter(i);
        if (widgetColor == -16711423) {
            this.mTitleText.setTextColor(getResources().getColor(R.color.title_text_normal_color));
            this.mNoiseReductionText.setTextColor(getResources().getColor(R.color.title_text_normal_color));
            this.mTouchpadLockText.setTextColor(getResources().getColor(R.color.title_text_normal_color));
        } else if (widgetColor == -328966) {
            this.mTitleText.setTextColor(getResources().getColor(R.color.color_black));
            this.mNoiseReductionText.setTextColor(getResources().getColor(R.color.color_black));
            this.mTouchpadLockText.setTextColor(getResources().getColor(R.color.color_black));
        }
    }

    @Override // com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetSettingBaseActivity
    public void onUpdatedDarkMode(boolean z) {
        int i;
        if (WidgetUtil.isDeviceDarkMode(getApplicationContext())) {
            onUpdatedAlpha((!z || getWidgetInfo().alpha <= 0) ? getWidgetInfo().alpha : 60);
            if (z) {
                i = -16711423;
            } else {
                i = getWidgetInfo().color;
            }
            onUpdatedColor(i);
        }
    }

    @Override // com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetSettingBaseActivity
    public void updateWidget(int i) {
        AppWidgetManager.getInstance(this).updateAppWidget(i, WidgetUtil.getWidgetMasterRemoteView(this, i));
    }
}
