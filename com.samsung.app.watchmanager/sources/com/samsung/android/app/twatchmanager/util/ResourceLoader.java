package com.samsung.android.app.twatchmanager.util;

import android.content.Context;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.plugininfoservice.MessageConfig;

public final class ResourceLoader {
    private static final String TAG = ("tUHM:" + ResourceLoader.class.getSimpleName());

    public static int getDrawableId(Context context, String str) {
        String str2 = TAG;
        Log.d(str2, "getDrawableId [" + str + "]");
        try {
            return getResourceId(context, str, "drawable");
        } catch (Exception e) {
            Log.w(TAG, "getDrawableId()", e);
            return 0;
        }
    }

    public static int getLayoutId(Context context, String str) {
        String str2 = TAG;
        Log.d(str2, "getLayoutId [" + str + "]");
        return getResourceId(context, str, "layout");
    }

    private static int getResourceId(Context context, String str, String str2) {
        return context.getResources().getIdentifier(str, str2, context.getPackageName());
    }

    public static int getStringId(Context context, String str) {
        String str2 = TAG;
        Log.d(str2, "getStringId [" + str + "]");
        return getResourceId(context, str, MessageConfig.RETURN_TYPE_STRING);
    }
}
