package com.samsung.accessory.hearablemgr.core.appwidget.util;

import android.content.Context;
import android.widget.RemoteViews;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.appwidget.WidgetBatteryProvider;
import com.samsung.accessory.hearablemgr.core.appwidget.WidgetMasterProvider;
import com.samsung.accessory.hearablemgr.core.service.message.MsgNoiseControls;
import com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlUtil;

public class WidgetUtil extends WidgetUtilMain {
    public static Class getWidgetBatteryClass() {
        return WidgetBatteryProvider.class;
    }

    public static Class getWidgetMasterClass() {
        return WidgetMasterProvider.class;
    }

    public static RemoteViews getWidgetMasterRemoteView(Context context, int i) {
        return new WidgetMasterProvider().getRemoteView(context, i);
    }

    public static RemoteViews getWidgetBatteryRemoteView(Context context, int i) {
        return new WidgetBatteryProvider().getRemoteView(context, i);
    }

    public static void setNoiseControls(Context context, int i) {
        Application.getCoreService().getEarBudsInfo().noiseControls = i;
        Application.getCoreService().sendSppMessage(new MsgNoiseControls((byte) i));
    }

    public static int setNextNoiseControls(Context context) {
        return NoiseControlUtil.setNextNoiseControl(context);
    }

    public static int getNoiseControls(Context context) {
        return Application.getCoreService().getEarBudsInfo().noiseControls;
    }

    public static int getNoiseControlResourceId(Context context, boolean z) {
        int noiseControls = getNoiseControls(context);
        return noiseControls != 1 ? noiseControls != 2 ? z ? R.drawable.widget_noise_control_off_connected : R.drawable.widget_noise_control_off_disconnected : z ? R.drawable.widget_noise_control_ambient_connected : R.drawable.widget_noise_control_ambient_disconnected : z ? R.drawable.widget_noise_control_anc_connected : R.drawable.widget_noise_control_anc_disconnected;
    }

    public static String getNoiseControlStateText(Context context) {
        int noiseControls = getNoiseControls(context);
        if (noiseControls == 1) {
            return context.getString(R.string.settings_noise_reduction_title);
        }
        if (noiseControls != 2) {
            return context.getString(R.string.noise_control_off);
        }
        return context.getString(R.string.settings_ambient_sound);
    }
}
