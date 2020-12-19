package com.samsung.android.app.twatchmanager.util;

import android.content.Context;
import com.samsung.android.app.twatchmanager.log.Log;

public class Toaster {
    private static final String TAG = ("tUHM:" + HostManagerUtils.class.getSimpleName());

    public static void show(Context context, String str) {
        String str2;
        String str3;
        StringBuilder sb;
        if (context == null) {
            str2 = TAG;
            sb = new StringBuilder();
            str3 = "can't display Toast [";
        } else {
            str2 = TAG;
            sb = new StringBuilder();
            str3 = "displaying Toast [";
        }
        sb.append(str3);
        sb.append(str);
        sb.append("]");
        Log.d(str2, sb.toString());
    }
}
