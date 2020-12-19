package com.samsung.accessory.hearablemgr.core.appwidget.util;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.ui.SingleToast;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.MsgLockTouchpad;
import com.samsung.accessory.hearablemgr.module.LaunchActivity;
import seccompat.SecCompatUtil;

public class WidgetUtilMain {
    private static final int EARBUD_PLACEMENT_IN_OPEN_CASE = 3;

    public static int getNoiseControls(Context context) {
        return 0;
    }

    public static int makeAlphaColor(int i, int i2) {
        return (i & ViewCompat.MEASURED_SIZE_MASK) | (i2 << 24);
    }

    public static void setNoiseControls(Context context, int i) {
    }

    public static void updateWidgetProvider(Context context) {
        sendPermissionBroadcast(context, new Intent("android.appwidget.action.APPWIDGET_UPDATE"));
    }

    public static void updateWidgetProvider(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        sendPermissionBroadcast(context, intent);
    }

    public static String getDeviceAliasName(Context context) {
        String str;
        String lastLaunchDeviceId = UhmFwUtil.getLastLaunchDeviceId();
        if (lastLaunchDeviceId != null) {
            str = BluetoothUtil.getAliasName(lastLaunchDeviceId);
            if (str == null) {
                str = Application.getUhmDatabase().getDeviceName(lastLaunchDeviceId);
            }
        } else {
            str = null;
        }
        return str == null ? context.getString(R.string.app_name) : str;
    }

    public static void sendPermissionBroadcast(Context context, Intent intent) {
        context.sendBroadcast(intent, "com.samsung.accessory.atticmgr.permission.SIGNATURE");
    }

    public static boolean isDeviceDarkMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static boolean isSupportedDarkMode() {
        if (!SecCompatUtil.isSupportSEPVersion(Application.getContext(), 110000) && Build.VERSION.SDK_INT < 29) {
            return false;
        }
        return true;
    }

    public static String getStringDarkMode(Context context) {
        return context.getString(Build.VERSION.SDK_INT >= 29 ? R.string.widget_match_with_dark_mode : R.string.widget_night_mode);
    }

    public static boolean isConnected(Context context) {
        return Application.getCoreService().isConnected();
    }

    public static boolean isConnectedLeftDevice(Context context) {
        return isConnected(context) && Application.getCoreService().getEarBudsInfo().batteryL > 0;
    }

    public static boolean isConnectedRightDevice(Context context) {
        return isConnected(context) && Application.getCoreService().getEarBudsInfo().batteryR > 0;
    }

    public static boolean isConnectedCradle(Context context) {
        CoreService coreService = Application.getCoreService();
        return isConnected(context) && coreService.isExtendedStatusReady() && (coreService.getEarBudsInfo().placementL >= 3 || coreService.getEarBudsInfo().placementR >= 3);
    }

    public static boolean isCommonBattery(Context context) {
        return isConnected(context) && Application.getCoreService().getEarBudsInfo().batteryI > 0;
    }

    public static int getBatteryGaugeLeft(Context context) {
        return Application.getCoreService().getEarBudsInfo().batteryL;
    }

    public static int getBatteryGaugeRight(Context context) {
        return Application.getCoreService().getEarBudsInfo().batteryR;
    }

    public static int getBatteryGaugeCommon(Context context) {
        return Application.getCoreService().getEarBudsInfo().batteryI;
    }

    public static int getBatteryGaugeCradle(Context context) {
        return Application.getCoreService().getEarBudsInfo().batteryCase;
    }

    public static void setTouchpadLock(Context context, boolean z) {
        Application.getCoreService().getEarBudsInfo().touchpadLocked = z;
        Application.getCoreService().sendSppMessage(new MsgLockTouchpad(z));
    }

    public static void showSingleToast(Context context, int i) {
        SingleToast.show(context, context.getResources().getString(i), 0);
    }

    public static boolean getTouchpadLockEnabled(Context context) {
        return Application.getCoreService().getEarBudsInfo().touchpadLocked;
    }

    public static boolean isWhiteWallpaper(Context context) {
        return WallpaperColorManager.getInstance(context).isWhiteWallpaper();
    }

    public static int getWallpaperColor(Context context) {
        if (isWhiteWallpaper(context)) {
            return WidgetInfo.WIDGET_COLOR_WHITE;
        }
        return -16711423;
    }

    public static int getWidgetBgColor(Context context, WidgetInfo widgetInfo) {
        if (!isDeviceDarkMode(context) || !widgetInfo.darkmode) {
            return widgetInfo.color;
        }
        return -16711423;
    }

    public static int getWidgetColor(Context context, WidgetInfo widgetInfo) {
        int i = widgetInfo.alpha;
        if (isDeviceDarkMode(context) && widgetInfo.darkmode && widgetInfo.alpha > 0) {
            return -16711423;
        }
        if (i < 50) {
            return getWallpaperColor(context);
        }
        return getWidgetBgColor(context, widgetInfo);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LaunchActivity.class);
        intent.putExtra("deviceid", UhmFwUtil.getLastLaunchDeviceId());
        intent.addFlags(268468224);
        context.startActivity(intent);
    }

    public static float DP_TO_PX(float f) {
        return UiUtil.DP_TO_PX(f);
    }

    public static void setTextShadowWithChildren(View view, boolean z) {
        int i = z ? 3 : 0;
        int color = ContextCompat.getColor(Application.getContext(), R.color.color_black_opacity_80);
        if (view instanceof TextView) {
            ((TextView) view).setShadowLayer((float) i, 0.0f, 0.0f, color);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                setTextShadowWithChildren(viewGroup.getChildAt(i2), z);
            }
        }
    }

    public static boolean isInstalledPackage(String str) {
        return Util.isInstalledPackage(str);
    }
}
