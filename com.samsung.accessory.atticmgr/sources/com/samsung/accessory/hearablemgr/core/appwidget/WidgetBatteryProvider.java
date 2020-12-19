package com.samsung.accessory.hearablemgr.core.appwidget;

import android.content.Context;
import android.widget.RemoteViews;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetBaseProvider;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetConstants;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetInfo;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetInfoManager;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetUtil;

public class WidgetBatteryProvider extends WidgetBaseProvider {
    private static final String TAG = (Application.TAG_ + WidgetBatteryProvider.class.getSimpleName());

    /* JADX WARNING: Removed duplicated region for block: B:17:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x007d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r7, android.content.Intent r8) {
        /*
        // Method dump skipped, instructions count: 148
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.appwidget.WidgetBatteryProvider.onReceive(android.content.Context, android.content.Intent):void");
    }

    @Override // com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetBaseProvider
    public RemoteViews getRemoteView(Context context, int i) {
        RemoteViews remoteViews;
        int i2;
        int i3;
        String str;
        String str2;
        WidgetInfo widgetInfo = new WidgetInfoManager(context, getClass()).getWidgetInfo(i);
        int widgetBgColor = WidgetUtil.getWidgetBgColor(context, widgetInfo);
        int widgetColor = WidgetUtil.getWidgetColor(context, widgetInfo);
        if (widgetInfo.alpha == 0 && widgetColor == -16711423) {
            remoteViews = new RemoteViews(context.getPackageName(), (int) R.layout.widget_view_battery_opacity_0);
        } else {
            remoteViews = new RemoteViews(context.getPackageName(), (int) R.layout.widget_view_battery);
        }
        if (widgetColor != -16711423) {
            i3 = context.getResources().getColor(R.color.widget_title_color_style_white);
            i2 = context.getResources().getColor(R.color.widget_device_name_color_style_white);
        } else {
            i3 = context.getResources().getColor(R.color.widget_title_color_style_black);
            i2 = context.getResources().getColor(R.color.widget_device_name_color_style_black);
        }
        remoteViews.setTextViewText(R.id.widget_text_device_bt_name, WidgetUtil.getDeviceAliasName(context));
        remoteViews.setInt(R.id.widget_text_device_bt_name, "setTextColor", i3);
        remoteViews.setInt(R.id.text_widget_device_left, "setTextColor", i2);
        remoteViews.setInt(R.id.text_widget_device_right, "setTextColor", i2);
        remoteViews.setInt(R.id.text_widget_common, "setTextColor", i2);
        remoteViews.setInt(R.id.text_widget_battery_gauge_left, "setTextColor", i3);
        remoteViews.setInt(R.id.text_widget_battery_gauge_right, "setTextColor", i3);
        remoteViews.setInt(R.id.text_widget_battery_gauge_common, "setTextColor", i3);
        remoteViews.setInt(R.id.text_widget_battery_gauge_cradle, "setTextColor", i3);
        remoteViews.setInt(R.id.widget_background, "setColorFilter", widgetBgColor);
        if (!WidgetUtil.isDeviceDarkMode(context) || !widgetInfo.darkmode || widgetInfo.alpha <= 0) {
            remoteViews.setInt(R.id.widget_background, "setImageAlpha", (widgetInfo.alpha * 255) / 100);
        } else {
            remoteViews.setInt(R.id.widget_background, "setImageAlpha", 153);
        }
        boolean isConnectedLeftDevice = WidgetUtil.isConnectedLeftDevice(context);
        boolean isConnectedRightDevice = WidgetUtil.isConnectedRightDevice(context);
        boolean isConnectedCradle = WidgetUtil.isConnectedCradle(context);
        boolean isCommonBattery = WidgetUtil.isCommonBattery(context);
        int i4 = 0;
        if (WidgetUtil.isConnected(context)) {
            remoteViews.setTextViewText(R.id.text_widget_battery_gauge_left, WidgetUtil.getBatteryGaugeLeft(context) + "%");
            remoteViews.setTextViewText(R.id.text_widget_battery_gauge_right, WidgetUtil.getBatteryGaugeRight(context) + "%");
            remoteViews.setTextViewText(R.id.text_widget_battery_gauge_common, WidgetUtil.getBatteryGaugeCommon(context) + "%");
            remoteViews.setTextViewText(R.id.text_widget_battery_gauge_cradle, WidgetUtil.getBatteryGaugeCradle(context) + "%");
            if (isCommonBattery) {
                remoteViews.setContentDescription(R.id.layout_widget_battery_common, context.getString(R.string.battery_percent, Integer.valueOf(WidgetUtil.getBatteryGaugeCommon(context))));
            } else {
                if (WidgetUtil.isConnectedLeftDevice(context) && WidgetUtil.isConnectedRightDevice(context)) {
                    str2 = String.format(context.getString(R.string.remaining_battery_both), Integer.valueOf(WidgetUtil.getBatteryGaugeLeft(context)), Integer.valueOf(WidgetUtil.getBatteryGaugeRight(context)));
                } else if (WidgetUtil.isConnectedLeftDevice(context)) {
                    str2 = String.format(context.getString(R.string.remaining_battery_left_only), Integer.valueOf(WidgetUtil.getBatteryGaugeLeft(context)));
                } else {
                    str2 = WidgetUtil.isConnectedRightDevice(context) ? String.format(context.getString(R.string.remaining_battery_right_only), Integer.valueOf(WidgetUtil.getBatteryGaugeRight(context))) : "";
                }
                remoteViews.setContentDescription(R.id.layout_widget_battery_device, str2);
            }
            if (isConnectedCradle) {
                str = context.getString(R.string.case_d_percent, Integer.valueOf(WidgetUtil.getBatteryGaugeCradle(context)));
            } else {
                str = context.getString(R.string.widget_cradle) + ", " + context.getString(R.string.va_disabled);
            }
            remoteViews.setContentDescription(R.id.layout_widget_cradle, str);
        } else {
            remoteViews.setContentDescription(R.id.layout_widget_battery_device, context.getString(R.string.earbuds) + ", " + context.getString(R.string.va_disabled));
            remoteViews.setContentDescription(R.id.layout_widget_cradle, context.getString(R.string.widget_cradle) + ", " + context.getString(R.string.va_disabled));
        }
        remoteViews.setOnClickPendingIntent(R.id.layout_widget_battery_container, getPendingIntent(context, WidgetConstants.WIDGET_ACTION_START_LAUNCH_ACTIVITY));
        int i5 = 102;
        remoteViews.setInt(R.id.image_widget_device_left, "setAlpha", isConnectedLeftDevice ? 255 : 102);
        remoteViews.setInt(R.id.image_widget_device_right, "setAlpha", isConnectedRightDevice ? 255 : 102);
        if (isConnectedCradle) {
            i5 = 255;
        }
        remoteViews.setInt(R.id.image_widget_cradle, "setAlpha", i5);
        remoteViews.setInt(R.id.text_widget_device_left, "setTextColor", i2);
        remoteViews.setInt(R.id.text_widget_device_right, "setTextColor", i2);
        remoteViews.setInt(R.id.text_widget_cradle, "setTextColor", i2);
        int i6 = 4;
        remoteViews.setViewVisibility(R.id.text_widget_battery_gauge_left, isConnectedLeftDevice ? 0 : 4);
        remoteViews.setViewVisibility(R.id.text_widget_battery_gauge_right, isConnectedRightDevice ? 0 : 4);
        if (isConnectedCradle) {
            i6 = 0;
        }
        remoteViews.setViewVisibility(R.id.text_widget_battery_gauge_cradle, i6);
        remoteViews.setViewVisibility(R.id.layout_widget_battery_common, isCommonBattery ? 0 : 8);
        if (isCommonBattery) {
            i4 = 8;
        }
        remoteViews.setViewVisibility(R.id.layout_widget_battery_device, i4);
        return remoteViews;
    }
}
