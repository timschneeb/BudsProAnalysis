package com.samsung.accessory.hearablemgr.core.appwidget;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WallpaperColorManager;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetConstants;
import com.samsung.accessory.hearablemgr.core.appwidget.util.WidgetUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import seccompat.android.util.Log;

public class WidgetManager {
    private static final String TAG = (Application.TAG_ + WidgetManager.class.getSimpleName());
    private Context mContext;
    private BroadcastReceiver mWidgetReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.appwidget.WidgetManager.AnonymousClass2 */

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        public void onReceive(Context context, Intent intent) {
            char c;
            String str = WidgetManager.TAG;
            Log.d(str, "onReceive : " + intent.getAction());
            String action = intent.getAction();
            switch (action.hashCode()) {
                case -1854841232:
                    if (action.equals(CoreService.ACTION_MSG_ID_STATUS_UPDATED)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -1809423998:
                    if (action.equals(CoreService.ACTION_MSG_ID_LOCK_TOUCHPAD_UPDATED)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -1645270254:
                    if (action.equals(WidgetConstants.ACTION_WALLPAPER_CHANGED)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case -1577670709:
                    if (action.equals(CoreService.ACTION_MSG_ID_NOISE_CONTROLS_UPDATE)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -1129240014:
                    if (action.equals(CoreService.ACTION_MSG_ID_EXTENDED_STATUS_UPDATED)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -654353038:
                    if (action.equals(CoreService.ACTION_MSG_ID_CALL_STATE)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 158859398:
                    if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 952856008:
                    if (action.equals(CoreService.ACTION_MSG_ID_NOISE_REDUCTION_UPDATED)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1150598536:
                    if (action.equals(WidgetConstants.ACTION_SEC_WALLPAPER_CHANGED)) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case 1174571750:
                    if (action.equals("android.bluetooth.device.action.ALIAS_CHANGED")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1335721824:
                    if (action.equals(CoreService.ACTION_DEVICE_DISCONNECTED)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1403073508:
                    if (action.equals(CoreService.ACTION_DEVICE_CONNECTED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1795645538:
                    if (action.equals(CoreService.ACTION_MSG_ID_AMBIENT_SOUND_UPDATED)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1882440235:
                    if (action.equals(CoreService.ACTION_MSG_ID_SCO_STATE_UPDATED)) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    WallpaperColorManager.initWallpaperColor(context);
                    WidgetUtil.updateWidgetProvider(context);
                    return;
                case 5:
                    WidgetUtil.updateWidgetProvider(context, WidgetUtil.getWidgetBatteryClass());
                    return;
                case 6:
                case 7:
                case '\b':
                case '\t':
                    WidgetUtil.updateWidgetProvider(context, WidgetUtil.getWidgetMasterClass());
                    return;
                case '\n':
                case 11:
                    String str2 = WidgetManager.TAG;
                    Log.d(str2, "isCPCall() : " + Util.isCPCall() + ", isScoConnected() : " + Util.isScoConnected());
                    WidgetUtil.updateWidgetProvider(context, WidgetUtil.getWidgetMasterClass());
                    return;
                case '\f':
                case '\r':
                    WallpaperColorManager.initWallpaperColor(context);
                    return;
                default:
                    return;
            }
        }
    };
    private WallpaperManager.OnColorsChangedListener wallpaperColorManager;
    private WallpaperManager wallpaperManager;

    public WidgetManager(Context context) {
        this.mContext = context;
        onCreate();
    }

    public void onCreate() {
        registerReceiver();
        registerWallpaperCallback();
    }

    public void onDestroy() {
        unregisterReceiver();
        unregisterWallpaperCallback();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.device.action.ALIAS_CHANGED");
        intentFilter.addAction(CoreService.ACTION_DEVICE_CONNECTED);
        intentFilter.addAction(CoreService.ACTION_DEVICE_DISCONNECTED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_EXTENDED_STATUS_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_STATUS_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_AMBIENT_SOUND_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_NOISE_REDUCTION_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_NOISE_CONTROLS_UPDATE);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_LOCK_TOUCHPAD_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_CALL_STATE);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_SCO_STATE_UPDATED);
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        if (Util.isSamsungDevice()) {
            intentFilter.addAction(WidgetConstants.ACTION_SEC_WALLPAPER_CHANGED);
        } else {
            intentFilter.addAction(WidgetConstants.ACTION_WALLPAPER_CHANGED);
        }
        this.mContext.registerReceiver(this.mWidgetReceiver, intentFilter);
    }

    private void registerWallpaperCallback() {
        if (Build.VERSION.SDK_INT >= 27) {
            this.wallpaperColorManager = new WallpaperManager.OnColorsChangedListener() {
                /* class com.samsung.accessory.hearablemgr.core.appwidget.WidgetManager.AnonymousClass1 */

                public void onColorsChanged(WallpaperColors wallpaperColors, int i) {
                    if (wallpaperColors != null) {
                        String str = WidgetManager.TAG;
                        Log.d(str, "onWallpaperColorChanged : " + wallpaperColors.getPrimaryColor());
                        WallpaperColorManager.initWallpaperColor(WidgetManager.this.mContext);
                    }
                }
            };
            this.wallpaperManager = WallpaperManager.getInstance(this.mContext);
            WallpaperManager wallpaperManager2 = this.wallpaperManager;
            if (wallpaperManager2 != null) {
                wallpaperManager2.addOnColorsChangedListener(this.wallpaperColorManager, new Handler(Looper.getMainLooper()));
            }
        }
    }

    private void unregisterReceiver() {
        this.mContext.unregisterReceiver(this.mWidgetReceiver);
    }

    private void unregisterWallpaperCallback() {
        WallpaperManager.OnColorsChangedListener onColorsChangedListener;
        WallpaperManager wallpaperManager2;
        if (Build.VERSION.SDK_INT >= 27 && (onColorsChangedListener = this.wallpaperColorManager) != null && (wallpaperManager2 = this.wallpaperManager) != null) {
            wallpaperManager2.removeOnColorsChangedListener(onColorsChangedListener);
        }
    }
}
