package com.samsung.accessory.hearablemgr.core.appwidget;

import android.content.Context;
import android.widget.RemoteViews;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetUtil;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;

public class WidgetMasterProvider extends WidgetQuickControlProvider {
    /* access modifiers changed from: protected */
    @Override // com.samsung.accessory.hearablemgr.core.appwidget.WidgetQuickControlProvider
    public void onClickQuickControlOption2(Context context) {
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.accessory.hearablemgr.core.appwidget.WidgetQuickControlProvider
    public void onClickQuickControlOption1(Context context) {
        SamsungAnalyticsUtil.sendEvent(SA.Event.TOUCH_CONTROL, SA.Screen.QUICK_CONTROL_WIDGET, SamsungAnalyticsUtil.makeTouchControlDetail(WidgetUtil.setNextNoiseControls(context)));
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.accessory.hearablemgr.core.appwidget.WidgetQuickControlProvider
    public void updateRemoteViews(Context context, int i, RemoteViews remoteViews) {
        boolean isConnected = WidgetUtil.isConnected(context);
        int i2 = R.string.va_off;
        if (isConnected) {
            remoteViews.setImageViewResource(R.id.image_widget_quick_control_option_1, WidgetUtil.getNoiseControlResourceId(context, true));
            remoteViews.setTextViewText(R.id.text_widget_quick_control_option_1, WidgetUtil.getNoiseControlStateText(context));
            remoteViews.setContentDescription(R.id.image_widget_quick_control_option_1, context.getString(R.string.noise_controls) + ", " + WidgetUtil.getNoiseControlStateText(context));
            StringBuilder sb = new StringBuilder();
            sb.append(context.getString(R.string.settings_touchpad_menu1));
            sb.append(", ");
            if (WidgetUtil.getTouchpadLockEnabled(context)) {
                i2 = R.string.va_on;
            }
            sb.append(context.getString(i2));
            remoteViews.setContentDescription(R.id.image_widget_quick_control_option_2, sb.toString());
            return;
        }
        remoteViews.setImageViewResource(R.id.image_widget_quick_control_option_1, WidgetUtil.getNoiseControlResourceId(context, false));
        remoteViews.setTextViewText(R.id.text_widget_quick_control_option_1, context.getString(R.string.noise_control_off));
        remoteViews.setContentDescription(R.id.image_widget_quick_control_option_1, context.getString(R.string.settings_noise_reduction_title) + ", " + context.getString(R.string.va_off));
        remoteViews.setContentDescription(R.id.image_widget_quick_control_option_2, context.getString(R.string.settings_touchpad_menu1) + ", " + context.getString(R.string.va_off));
    }
}
