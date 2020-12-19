package com.samsung.android.app.twatchmanager.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.ResourceRulesManager;
import com.samsung.android.app.twatchmanager.model.GroupInfo;
import com.samsung.android.app.watchmanager.R;

public class UIUtils {
    private static final int SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR = 16;
    private static final String TAG = ("tUHM:" + UIUtils.class.getSimpleName());

    public static void adjustLogoMargin(View view) {
        String str = TAG;
        Log.d(str, " adjustLogoMargin() " + view);
        if (view != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMargins(view.getLeft(), view.getTop() + getStatusBarHeight(view.getContext()), view.getRight(), view.getBottom());
            view.setLayoutParams(marginLayoutParams);
        }
    }

    public static void changeNavigationBarColorValue(Activity activity, int i) {
        Window window;
        if (Build.VERSION.SDK_INT >= 21 && activity != null && (window = activity.getWindow()) != null) {
            window.setNavigationBarColor(i);
        }
    }

    public static int convertDpToPx(Context context, int i) {
        int applyDimension = (int) TypedValue.applyDimension(1, (float) i, context.getResources().getDisplayMetrics());
        String str = TAG;
        Log.d(str, "convertDpToPx() dp:" + i + " px:" + applyDimension);
        return applyDimension;
    }

    public static final int getColor(Context context, int i) {
        return Build.VERSION.SDK_INT >= 23 ? context.getColor(i) : context.getResources().getColor(i);
    }

    public static float getDisplayRatio(Context context) {
        return ((float) getWidth(context)) / ((float) getHeight(context));
    }

    public static int getDrawableIdFromFileName(Context context, String str) {
        String str2 = TAG;
        Log.d(str2, "getDrawableIdFromFileName() starts, fileName : " + str);
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf != -1) {
            str = str.substring(0, lastIndexOf);
        }
        return ResourceLoader.getDrawableId(context, str);
    }

    public static int getHeight(Context context) {
        return context.getResources().getConfiguration().screenHeightDp;
    }

    public static int getStatusBarHeight(Context context) {
        int identifier;
        if (context == null || (identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android")) <= 0) {
            return 0;
        }
        return context.getResources().getDimensionPixelSize(identifier);
    }

    public static int getWidth(Context context) {
        return context.getResources().getConfiguration().screenWidthDp;
    }

    public static int getWindowHeight(Activity activity) {
        int i;
        if (activity != null) {
            Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            i = point.y;
        } else {
            i = 0;
        }
        String str = TAG;
        Log.d(str, "getWindowHeight() return:" + i);
        return i;
    }

    public static int getWindowWidth(Activity activity) {
        int i;
        if (activity != null) {
            Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            i = point.x;
        } else {
            i = 0;
        }
        String str = TAG;
        Log.d(str, "getWindowWidth() return:" + i);
        return i;
    }

    public static boolean isLandScapeMode(Context context) {
        if (context == null) {
            return false;
        }
        int i = context.getResources().getConfiguration().orientation;
        String str = TAG;
        Log.d(str, "isLandScapeMode : " + i);
        return i == 2;
    }

    public static void setColorFilter(String str, Drawable drawable) {
        if (drawable != null) {
            int parseColor = Color.parseColor(str);
            drawable.setColorFilter(new ColorMatrixColorFilter(new float[]{0.0f, 0.0f, 0.0f, 0.0f, (float) ((16711680 & parseColor) / 65535), 0.0f, 0.0f, 0.0f, 0.0f, (float) ((65280 & parseColor) / 255), 0.0f, 0.0f, 0.0f, 0.0f, (float) (parseColor & 255), 0.0f, 0.0f, 0.0f, 1.0f, 0.0f}));
        }
    }

    public static boolean setDrawableByGroupName(Context context, GroupInfo.InfoType infoType, String str, ImageView imageView) {
        String str2;
        Log.d(TAG, "setDrawableByGroupName() starts, groupName : " + str);
        GroupInfo.ImageInfo imageInfoByGroupName = ResourceRulesManager.getInstance().getImageInfoByGroupName(infoType, str);
        if (imageInfoByGroupName == null) {
            return false;
        }
        if (isLandScapeMode(context)) {
            str2 = "land_" + imageInfoByGroupName.name;
        } else {
            str2 = imageInfoByGroupName.name;
        }
        int drawableIdFromFileName = getDrawableIdFromFileName(context, str2);
        if (drawableIdFromFileName == 0) {
            return false;
        }
        imageView.setImageResource(drawableIdFromFileName);
        return true;
    }

    public static boolean setDrawableByGroupName(Context context, String str, ImageView imageView) {
        int drawableIdFromFileName;
        String str2 = TAG;
        Log.d(str2, "setDrawableByGroupName() starts, groupName : " + str);
        GroupInfo.ImageInfo imageInfoByGroupName = ResourceRulesManager.getInstance().getImageInfoByGroupName(GroupInfo.InfoType.BACKGROUND, str);
        if (imageInfoByGroupName == null || (drawableIdFromFileName = getDrawableIdFromFileName(context, imageInfoByGroupName.name)) == 0) {
            return false;
        }
        ((BitmapDrawable) context.getResources().getDrawable(drawableIdFromFileName)).getBitmap();
        imageView.setImageResource(drawableIdFromFileName);
        return true;
    }

    public static void setHeaderImageWithRules(final Context context, final String str, final ImageView imageView, final View view) {
        if (context != null && imageView != null) {
            if (isLandScapeMode(context)) {
                setWidthForTablet(context, imageView);
            }
            if (!ResourceRulesManager.getInstance().isResourceInfoAvailable()) {
                ResourceRulesManager.getInstance().syncGearInfo(1, new ResourceRulesManager.ISyncCallback() {
                    /* class com.samsung.android.app.twatchmanager.util.UIUtils.AnonymousClass1 */

                    @Override // com.samsung.android.app.twatchmanager.manager.ResourceRulesManager.ISyncCallback
                    public void onSyncComplete(int i, boolean z) {
                        if (z) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                /* class com.samsung.android.app.twatchmanager.util.UIUtils.AnonymousClass1.AnonymousClass1 */

                                public void run() {
                                    AnonymousClass1 r0 = AnonymousClass1.this;
                                    if (UIUtils.setDrawableByGroupName(context, GroupInfo.InfoType.HEADER, str, imageView)) {
                                        return;
                                    }
                                    if (HostManagerUtils.isTablet()) {
                                        imageView.setImageResource(R.drawable.land_select_device_bg_default);
                                        View view = view;
                                        if (view != null) {
                                            view.setVisibility(4);
                                            return;
                                        }
                                        return;
                                    }
                                    imageView.setImageResource(R.drawable.select_device_bg_default);
                                }
                            });
                        }
                    }
                });
            } else if (setDrawableByGroupName(context, GroupInfo.InfoType.HEADER, str, imageView)) {
            } else {
                if (HostManagerUtils.isTablet()) {
                    imageView.setImageResource(R.drawable.land_select_device_bg_default);
                    if (view != null) {
                        view.setVisibility(4);
                        return;
                    }
                    return;
                }
                imageView.setImageResource(R.drawable.select_device_bg_default);
            }
        }
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView, int i) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return false;
        }
        int min = Math.min(adapter.getCount(), i);
        int i2 = 0;
        for (int i3 = 0; i3 < min; i3++) {
            View view = adapter.getView(i3, null, listView);
            view.measure(0, 0);
            i2 += view.getMeasuredHeight();
        }
        int dividerHeight = listView.getDividerHeight() * (min - 1);
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = i2 + dividerHeight;
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
        return true;
    }

    public static void setProperWidth(Context context, View view) {
        int convertDpToPx;
        double d2;
        int width = getWidth(context);
        if (width >= 1920) {
            convertDpToPx = convertDpToPx(context, width);
            d2 = 0.5d;
        } else if (width >= 960) {
            convertDpToPx = convertDpToPx(context, width);
            d2 = 0.75d;
        } else if (width >= 589 && getHeight(context) >= 411) {
            convertDpToPx = convertDpToPx(context, width);
            d2 = 0.9d;
        } else {
            return;
        }
        setWidthPercentage(view, convertDpToPx, d2);
    }

    public static void setWidthForTablet(Context context, View view) {
        int convertDpToPx;
        double d2;
        int width = getWidth(context);
        int height = getHeight(context);
        float displayRatio = getDisplayRatio(context);
        String str = TAG;
        Log.d(str, "setWidthForTablet()::width = " + width + ", height = " + height + ", ratio = " + displayRatio);
        double d3 = (double) displayRatio;
        if (d3 >= 1.6d) {
            convertDpToPx = convertDpToPx(context, width);
            d2 = 0.75d;
        } else if (d3 > 1.3d) {
            convertDpToPx = convertDpToPx(context, width);
            d2 = 0.94d;
        } else {
            return;
        }
        setWidthPercentage(view, convertDpToPx, d2);
    }

    public static void setWidthPercentage(View view, int i, double d2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        double d3 = (double) i;
        Double.isNaN(d3);
        layoutParams.width = (int) (d3 * d2);
    }
}
