package com.samsung.accessory.hearablemgr.core.appwidget;

import android.appwidget.AppWidgetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetSettingBaseActivity;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WallpaperColorManager;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetUtil;
import seccompat.android.util.Log;

public class WidgetSettingBatteryActivity extends WidgetSettingBaseActivity {
    private static final String TAG = (Application.TAG_ + WidgetSettingBatteryActivity.class.getSimpleName());
    private TextView mBatteryGaugeCommon;
    private TextView mBatteryGaugeCradle;
    private TextView mBatteryGaugeLeft;
    private TextView mBatteryGaugeRight;
    private View mBatteryView;
    private ImageView mCommonLeftImage;
    private ImageView mCommonRightImage;
    private TextView mCommonText;
    private View mCommonView;
    private ImageView mCradleImage;
    private TextView mCradleText;
    private ImageView mDeviceLeftImage;
    private TextView mDeviceLeftText;
    private ImageView mDeviceRightImage;
    private TextView mDeviceRightText;
    boolean mIsCradleConnected = false;
    boolean mIsLeftConnected = false;
    boolean mIsRightConnected = false;
    private TextView mTitleText;
    private ImageView mWidgetBackground;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetSettingBaseActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "onCreate()");
        setWidgetPreview(R.layout.widget_view_battery);
        WallpaperColorManager.initWallpaperColor(this);
        init();
        initView();
    }

    private void init() {
        this.mWidgetBackground = (ImageView) getWidgetPreview().findViewById(R.id.widget_background);
        this.mTitleText = (TextView) getWidgetPreview().findViewById(R.id.widget_text_device_bt_name);
        this.mBatteryGaugeLeft = (TextView) getWidgetPreview().findViewById(R.id.text_widget_battery_gauge_left);
        this.mBatteryGaugeRight = (TextView) getWidgetPreview().findViewById(R.id.text_widget_battery_gauge_right);
        this.mBatteryGaugeCradle = (TextView) getWidgetPreview().findViewById(R.id.text_widget_battery_gauge_cradle);
        this.mBatteryGaugeCommon = (TextView) getWidgetPreview().findViewById(R.id.text_widget_battery_gauge_common);
        this.mDeviceLeftText = (TextView) getWidgetPreview().findViewById(R.id.text_widget_device_left);
        this.mDeviceRightText = (TextView) getWidgetPreview().findViewById(R.id.text_widget_device_right);
        this.mCommonText = (TextView) getWidgetPreview().findViewById(R.id.text_widget_common);
        this.mCradleText = (TextView) getWidgetPreview().findViewById(R.id.text_widget_cradle);
        this.mDeviceLeftImage = (ImageView) getWidgetPreview().findViewById(R.id.image_widget_device_left);
        this.mDeviceRightImage = (ImageView) getWidgetPreview().findViewById(R.id.image_widget_device_right);
        this.mCommonLeftImage = (ImageView) getWidgetPreview().findViewById(R.id.image_widget_common_left);
        this.mCommonRightImage = (ImageView) getWidgetPreview().findViewById(R.id.image_widget_common_right);
        this.mCradleImage = (ImageView) getWidgetPreview().findViewById(R.id.image_widget_cradle);
        this.mIsLeftConnected = WidgetUtil.isConnectedLeftDevice(this);
        this.mIsRightConnected = WidgetUtil.isConnectedRightDevice(this);
        this.mIsCradleConnected = WidgetUtil.isConnectedCradle(this);
        this.mCommonView = getWidgetPreview().findViewById(R.id.layout_widget_battery_common);
        this.mBatteryView = getWidgetPreview().findViewById(R.id.layout_widget_battery_device);
    }

    private void initView() {
        this.mTitleText.setText(WidgetUtil.getDeviceAliasName(getApplicationContext()));
        float f = 1.0f;
        this.mDeviceLeftImage.setAlpha(this.mIsLeftConnected ? 1.0f : 0.4f);
        this.mDeviceRightImage.setAlpha(this.mIsRightConnected ? 1.0f : 0.4f);
        ImageView imageView = this.mCradleImage;
        if (!this.mIsCradleConnected) {
            f = 0.4f;
        }
        imageView.setAlpha(f);
        int i = 4;
        this.mBatteryGaugeLeft.setVisibility(this.mIsLeftConnected ? 0 : 4);
        this.mBatteryGaugeRight.setVisibility(this.mIsRightConnected ? 0 : 4);
        TextView textView = this.mBatteryGaugeCradle;
        if (this.mIsCradleConnected) {
            i = 0;
        }
        textView.setVisibility(i);
        int i2 = 8;
        this.mCommonView.setVisibility(WidgetUtil.isCommonBattery(this) ? 0 : 8);
        View view = this.mBatteryView;
        if (!WidgetUtil.isCommonBattery(this)) {
            i2 = 0;
        }
        view.setVisibility(i2);
        onUpdatedAlpha(getWidgetInfo().alpha);
        onUpdatedColor(getWidgetInfo().color);
        onUpdatedDarkMode(getWidgetInfo().darkmode);
    }

    @Override // com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetSettingBaseActivity
    public Class getProviderClass() {
        return WidgetUtil.getWidgetBatteryClass();
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
            this.mTitleText.setTextColor(getResources().getColor(R.color.widget_title_color_style_black));
            this.mBatteryGaugeLeft.setTextColor(getResources().getColor(R.color.widget_title_color_style_black));
            this.mBatteryGaugeRight.setTextColor(getResources().getColor(R.color.widget_title_color_style_black));
            this.mBatteryGaugeCommon.setTextColor(getResources().getColor(R.color.widget_title_color_style_black));
            this.mBatteryGaugeCradle.setTextColor(getResources().getColor(R.color.widget_title_color_style_black));
            this.mDeviceLeftText.setTextColor(getResources().getColor(R.color.widget_device_name_color_style_black));
            this.mDeviceRightText.setTextColor(getResources().getColor(R.color.widget_device_name_color_style_black));
            this.mCommonText.setTextColor(getResources().getColor(R.color.widget_device_name_color_style_black));
            this.mCradleText.setTextColor(getResources().getColor(R.color.widget_device_name_color_style_black));
        } else if (widgetColor == -328966) {
            this.mTitleText.setTextColor(getResources().getColor(R.color.widget_title_color_style_white));
            this.mBatteryGaugeLeft.setTextColor(getResources().getColor(R.color.widget_title_color_style_white));
            this.mBatteryGaugeRight.setTextColor(getResources().getColor(R.color.widget_title_color_style_white));
            this.mBatteryGaugeCommon.setTextColor(getResources().getColor(R.color.widget_title_color_style_white));
            this.mBatteryGaugeCradle.setTextColor(getResources().getColor(R.color.widget_title_color_style_white));
            this.mDeviceLeftText.setTextColor(getResources().getColor(R.color.widget_device_name_color_style_white));
            this.mDeviceRightText.setTextColor(getResources().getColor(R.color.widget_device_name_color_style_white));
            this.mCommonText.setTextColor(getResources().getColor(R.color.widget_device_name_color_style_white));
            this.mCradleText.setTextColor(getResources().getColor(R.color.widget_device_name_color_style_white));
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
        AppWidgetManager.getInstance(this).updateAppWidget(i, WidgetUtil.getWidgetBatteryRemoteView(this, i));
    }
}
