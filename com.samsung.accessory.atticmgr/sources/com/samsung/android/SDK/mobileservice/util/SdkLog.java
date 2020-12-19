package com.samsung.android.sdk.mobileservice.util;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Iterator;
import java.util.MissingFormatArgumentException;

public enum SdkLog {
    INSTANCE;
    
    private static final boolean ENG = "eng".equals(Build.TYPE);
    private static final int LINE_LENGTH = 512;
    private static final String prefix = "SEMS-11.5.01_";
    private static final String version = "11.5.01_";

    public static void e(String str, String str2) {
        Iterator<String> it = INSTANCE.breakUpMsg(str2, 512).iterator();
        while (it.hasNext()) {
            Log.e(prefix + str, it.next());
        }
    }

    public static void d(String str, String str2) {
        Iterator<String> it = INSTANCE.breakUpMsg(str2, 512).iterator();
        while (it.hasNext()) {
            Log.d(prefix + str, it.next());
        }
    }

    public static void v(String str, String str2) {
        Iterator<String> it = INSTANCE.breakUpMsg(str2, 512).iterator();
        while (it.hasNext()) {
            Log.v(prefix + str, it.next());
        }
    }

    public static void s(String str, String str2, String... strArr) {
        if (strArr == null) {
            strArr = new String[1];
        }
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i] == null) {
                strArr[i] = "null";
            }
            if (!ENG) {
                char[] cArr = new char[strArr[i].length()];
                Arrays.fill(cArr, '*');
                strArr[i] = new String(cArr);
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            try {
                str2 = String.format(str2, strArr);
            } catch (FormatFlagsConversionMismatchException | MissingFormatArgumentException unused) {
            }
        }
        Iterator<String> it = INSTANCE.breakUpMsg(str2, 512).iterator();
        while (it.hasNext()) {
            Log.d(prefix + str, it.next());
        }
    }

    public static void s(String str, String str2) {
        if (ENG) {
            Iterator<String> it = INSTANCE.breakUpMsg(str2, 512).iterator();
            while (it.hasNext()) {
                Log.d(prefix + str, it.next());
            }
        }
    }

    public static void s(Exception exc) {
        if (ENG) {
            exc.printStackTrace();
            return;
        }
        SdkLog sdkLog = INSTANCE;
        d("SEMS_SDK", "fatal exception! Trace not allowed.\n" + exc.getMessage());
    }

    private ArrayList<String> breakUpMsg(String str, int i) {
        int i2;
        ArrayList<String> arrayList = new ArrayList<>();
        if (TextUtils.isEmpty(str)) {
            return arrayList;
        }
        int length = str.length();
        if (length > i) {
            int i3 = 0;
            while (true) {
                int i4 = i3 + 1;
                i2 = i * i4;
                arrayList.add(str.substring(i3 * i, i2));
                if (length <= (i4 + 1) * i) {
                    break;
                }
                i3 = i4;
            }
            arrayList.add(str.substring(i2, length));
        } else {
            arrayList.add(str);
        }
        return arrayList;
    }

    public static String getReference(Object obj) {
        try {
            return obj.toString().substring(obj.toString().lastIndexOf("@"));
        } catch (Exception unused) {
            return null;
        }
    }
}
