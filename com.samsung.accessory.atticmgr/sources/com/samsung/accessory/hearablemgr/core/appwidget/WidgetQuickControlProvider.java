package com.samsung.accessory.hearablemgr.core.appwidget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.ui.SingleToast;
import com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetBaseProvider;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetConstants;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetInfo;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetInfoManager;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetUtil;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import seccompat.android.util.Log;

public abstract class WidgetQuickControlProvider extends WidgetBaseProvider {
    protected static final String TAG = (Application.TAG_ + WidgetQuickControlProvider.class.getSimpleName());

    /* access modifiers changed from: protected */
    public abstract void onClickQuickControlOption1(Context context);

    /* access modifiers changed from: protected */
    public abstract void onClickQuickControlOption2(Context context);

    /* access modifiers changed from: protected */
    public abstract void updateRemoteViews(Context context, int i, RemoteViews remoteViews);

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public void onReceive(Context context, Intent intent) {
        char c;
        super.onReceive(context, intent);
        String action = intent.getAction();
        switch (action.hashCode()) {
            case -689938766:
                if (action.equals("android.appwidget.action.APPWIDGET_UPDATE_OPTIONS")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 762897642:
                if (action.equals(WidgetConstants.WIDGET_ACTION_START_LAUNCH_ACTIVITY)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 836821213:
                if (action.equals(WidgetConstants.WIDGET_ACTION_UPDATE_QUICK_CONTROL_OPTION_1)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 836821214:
                if (action.equals(WidgetConstants.WIDGET_ACTION_UPDATE_QUICK_CONTROL_OPTION_2)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1619576947:
                if (action.equals("android.appwidget.action.APPWIDGET_UPDATE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            Log.d(TAG, "onReceive : ACTION_APPWIDGET_OPTIONS_CHANGED");
            int i = intent.getExtras().getInt("appWidgetId");
            if (i != 0) {
                updateUI(context, i);
            }
        } else if (c == 1) {
            updateUI(context);
        } else if (c == 2) {
            Log.d(TAG, "onReceive : WIDGET_ACTION_UPDATE_QUICK_CONTROL_OPTION_1");
            if (WidgetUtil.isConnected(context)) {
                onClickQuickControlOption1(context);
            } else {
                updateUI(context);
            }
        } else if (c == 3) {
            Log.d(TAG, "onReceive : WIDGET_ACTION_UPDATE_QUICK_CONTROL_OPTION_2");
            if (WidgetUtil.isConnected(context)) {
                clickTouchpadLock(context);
                onClickQuickControlOption2(context);
                return;
            }
            updateUI(context);
        } else if (c == 4) {
            Log.d(TAG, "onReceive : WIDGET_ACTION_START_LAUNCH_ACTIVITY");
            if (!WidgetUtil.isInstalledPackage(UhmFwUtil.getUhmPackageName())) {
                Log.d(TAG, "it's not installed galaxy wearable");
                SingleToast.show(context, Application.getContext().getString(R.string.cant_open_galaxy_wearable), 0);
                return;
            }
            WidgetUtil.startActivity(context);
            updateUI(context);
        }
    }

    private void clickTouchpadLock(Context context) {
        boolean z = !WidgetUtil.getTouchpadLockEnabled(context);
        WidgetUtil.setTouchpadLock(context, z);
        SamsungAnalyticsUtil.sendEvent(SA.Event.WIDGET_LOCK_TOUCHPAD, SA.Screen.QUICK_CONTROL_WIDGET, z ? "b" : "a");
    }

    @Override // com.samsung.accessory.hearablemgr.core.appwidget.base.WidgetBaseProvider
    public RemoteViews getRemoteView(Context context, int i) {
        RemoteViews remoteViews;
        int i2;
        WidgetInfo widgetInfo = new WidgetInfoManager(context, getClass()).getWidgetInfo(i);
        int widgetBgColor = WidgetUtil.getWidgetBgColor(context, widgetInfo);
        int widgetColor = WidgetUtil.getWidgetColor(context, widgetInfo);
        if (widgetInfo.alpha == 0 && widgetColor == -16711423) {
            remoteViews = new RemoteViews(context.getPackageName(), (int) R.layout.widget_view_quick_control_opacity_0);
        } else {
            remoteViews = new RemoteViews(context.getPackageName(), (int) R.layout.widget_view_quick_control);
        }
        if (widgetColor != -16711423) {
            i2 = context.getResources().getColor(R.color.widget_title_color_style_white);
        } else {
            i2 = context.getResources().getColor(R.color.widget_title_color_style_black);
        }
        remoteViews.setTextViewText(R.id.widget_text_device_bt_name, WidgetUtil.getDeviceAliasName(context));
        remoteViews.setInt(R.id.widget_text_device_bt_name, "setTextColor", i2);
        remoteViews.setInt(R.id.text_widget_quick_control_option_1, "setTextColor", i2);
        remoteViews.setInt(R.id.text_widget_quick_control_option_2, "setTextColor", i2);
        remoteViews.setInt(R.id.widget_background, "setColorFilter", widgetBgColor);
        if (!WidgetUtil.isDeviceDarkMode(context) || !widgetInfo.darkmode || widgetInfo.alpha <= 0) {
            remoteViews.setInt(R.id.widget_background, "setImageAlpha", (widgetInfo.alpha * 255) / 100);
        } else {
            remoteViews.setInt(R.id.widget_background, "setImageAlpha", 153);
        }
        boolean isConnected = WidgetUtil.isConnected(context);
        int i3 = R.drawable.widget_lock_touchpad_off;
        if (isConnected) {
            if (WidgetUtil.getTouchpadLockEnabled(context)) {
                i3 = R.drawable.widget_lock_touchpad_on;
            }
            remoteViews.setImageViewResource(R.id.image_widget_quick_control_option_2, i3);
            remoteViews.setOnClickPendingIntent(R.id.switch_widget_quick_control_option_1, getPendingIntent(context, WidgetConstants.WIDGET_ACTION_UPDATE_QUICK_CONTROL_OPTION_1));
            remoteViews.setOnClickPendingIntent(R.id.switch_widget_quick_control_option_2, getPendingIntent(context, WidgetConstants.WIDGET_ACTION_UPDATE_QUICK_CONTROL_OPTION_2));
            remoteViews.setOnClickPendingIntent(R.id.layout_widget_quick_control_container, null);
            remoteViews.setInt(R.id.image_widget_quick_control_option_1, "setAlpha", 255);
            remoteViews.setInt(R.id.image_widget_quick_control_option_2, "setAlpha", 255);
        } else {
            remoteViews.setImageViewResource(R.id.image_widget_quick_control_option_2, R.drawable.widget_lock_touchpad_off);
            remoteViews.setOnClickPendingIntent(R.id.switch_widget_quick_control_option_1, getPendingIntent(context, WidgetConstants.WIDGET_ACTION_START_LAUNCH_ACTIVITY));
            remoteViews.setOnClickPendingIntent(R.id.switch_widget_quick_control_option_2, getPendingIntent(context, WidgetConstants.WIDGET_ACTION_START_LAUNCH_ACTIVITY));
            remoteViews.setOnClickPendingIntent(R.id.layout_widget_quick_control_container, getPendingIntent(context, WidgetConstants.WIDGET_ACTION_START_LAUNCH_ACTIVITY));
            remoteViews.setInt(R.id.image_widget_quick_control_option_1, "setAlpha", 102);
            remoteViews.setInt(R.id.image_widget_quick_control_option_2, "setAlpha", 102);
        }
        updateRemoteViews(context, i, remoteViews);
        return remoteViews;
    }
}
