package com.samsung.accessory.hearablemgr.core.appwidget.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.accessory.hearablemgr.Application;
import seccompat.android.util.Log;

public class WidgetInfoManager {
    private static final String TAG = (Application.TAG_ + WidgetInfoManager.class.getSimpleName());
    private Context mContext;
    private String mWidgetProviderName;

    public WidgetInfoManager(Context context, Class cls) {
        this.mContext = context;
        this.mWidgetProviderName = cls.getSimpleName();
    }

    public WidgetInfo getWidgetInfo(int i) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(WidgetConstants.PREFERENCE_WIDGET, 4);
        WidgetInfo widgetInfo = new WidgetInfo();
        widgetInfo.alpha = sharedPreferences.getInt(getKey(WidgetConstants.WIDGET_STYLE_ALPHA, i), 90);
        widgetInfo.color = sharedPreferences.getInt(getKey(WidgetConstants.WIDGET_STYLE_COLOR, i), WidgetInfo.WIDGET_COLOR_WHITE);
        widgetInfo.darkmode = sharedPreferences.getBoolean(getKey(WidgetConstants.WIDGET_STYLE_DARK_MODE, i), true);
        if (widgetInfo.color == -1) {
            widgetInfo.color = WidgetInfo.WIDGET_COLOR_WHITE;
        }
        if (widgetInfo.color == -16777216) {
            widgetInfo.color = -16711423;
        }
        return widgetInfo;
    }

    public void setWidgetInfo(int i, WidgetInfo widgetInfo) {
        SharedPreferences.Editor edit = this.mContext.getSharedPreferences(WidgetConstants.PREFERENCE_WIDGET, 4).edit();
        edit.putInt(getKey(WidgetConstants.WIDGET_STYLE_ALPHA, i), widgetInfo.alpha);
        edit.putInt(getKey(WidgetConstants.WIDGET_STYLE_COLOR, i), widgetInfo.color);
        edit.putBoolean(getKey(WidgetConstants.WIDGET_STYLE_DARK_MODE, i), widgetInfo.darkmode);
        edit.apply();
    }

    public void removeWidgetInfo(int i) {
        SharedPreferences.Editor edit = this.mContext.getSharedPreferences(WidgetConstants.PREFERENCE_WIDGET, 4).edit();
        String str = TAG;
        Log.d(str, "removeWidgetInfo  provider : " + this.mWidgetProviderName + ", id : " + i);
        edit.remove(getKey(WidgetConstants.WIDGET_STYLE_ALPHA, i));
        edit.remove(getKey(WidgetConstants.WIDGET_STYLE_COLOR, i));
        edit.remove(getKey(WidgetConstants.WIDGET_STYLE_DARK_MODE, i));
        edit.apply();
    }

    public String getKey(String str, int i) {
        return str + XDMInterface.XDM_BASE_PATH + this.mWidgetProviderName + XDMInterface.XDM_BASE_PATH + i;
    }
}
