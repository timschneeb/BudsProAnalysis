package com.samsung.accessory.hearablemgr.core.appwidget.util;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.permission.PermissionManager;
import seccompat.android.util.Log;

public class WallpaperColorManager {
    private static final String KEY_NEED_DARK_FONT = "need_dark_font";
    private static final int NOT_SUPPORTED = -1;
    private static final String TAG = (Application.TAG_ + WallpaperColorManager.class.getSimpleName());
    private static final int USER_ID = 0;
    private static boolean isWhiteWallpaper;
    private static WallpaperColorManager sWallpaperColorManager;

    public static synchronized WallpaperColorManager getInstance(Context context) {
        WallpaperColorManager wallpaperColorManager;
        synchronized (WallpaperColorManager.class) {
            if (sWallpaperColorManager == null) {
                sWallpaperColorManager = new WallpaperColorManager(context);
            }
            wallpaperColorManager = sWallpaperColorManager;
        }
        return wallpaperColorManager;
    }

    public WallpaperColorManager(Context context) {
        initWallpaperColor(context);
    }

    public static synchronized void initWallpaperColor(Context context) {
        synchronized (WallpaperColorManager.class) {
            boolean isWhiteWallPaper = isWhiteWallPaper(context);
            if (isWhiteWallpaper != isWhiteWallPaper) {
                isWhiteWallpaper = isWhiteWallPaper;
                WidgetUtil.updateWidgetProvider(context);
            }
            String str = TAG;
            Log.d(str, "initWallpaperColor isWhiteWallpaper : " + isWhiteWallpaper);
        }
    }

    public boolean isWhiteWallpaper() {
        return isWhiteWallpaper;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002b A[SYNTHETIC, Splitter:B:11:0x002b] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0020  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean isWhiteWallPaper(android.content.Context r8) {
        /*
        // Method dump skipped, instructions count: 162
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.appwidget.util.WallpaperColorManager.isWhiteWallPaper(android.content.Context):boolean");
    }

    private static boolean isPermissionDenied(Context context) {
        return Build.VERSION.SDK_INT >= 27 && !PermissionManager.isPermissionGranted(context, "android.permission.READ_EXTERNAL_STORAGE");
    }

    private static boolean isWallpaperSupported(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }
        try {
            return WallpaperManager.getInstance(context).isWallpaperSupported();
        } catch (Exception e) {
            String str = TAG;
            Log.e(str, "WallpaperManager is not supported:" + e.toString());
            return false;
        }
    }

    private static float[] getColorHSV(Bitmap bitmap, Rect[] rectArr) {
        float[] fArr = new float[3];
        float[] fArr2 = new float[3];
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int i = (int) ((width > height ? (float) height : (float) width) / 100.0f);
            if (i <= 0) {
                i = 1;
            }
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            int i2 = 0;
            for (int i3 = 0; i3 < rectArr.length; i3++) {
                int i4 = rectArr[i3].left;
                int i5 = rectArr[i3].right;
                int i6 = rectArr[i3].top;
                int i7 = rectArr[i3].bottom;
                while (i4 < i5) {
                    int i8 = i2;
                    float f4 = f;
                    for (int i9 = i6; i9 < i7; i9 += i) {
                        Color.colorToHSV(bitmap.getPixel(i4, i9), fArr);
                        f4 += fArr[0];
                        f2 += fArr[1];
                        f3 += fArr[2];
                        i8++;
                    }
                    i4 += i;
                    f = f4;
                    i2 = i8;
                }
            }
            float f5 = (float) i2;
            fArr2[0] = f / f5;
            fArr2[1] = f2 / f5;
            fArr2[2] = f3 / f5;
            return fArr2;
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + e);
            return null;
        }
    }
}
