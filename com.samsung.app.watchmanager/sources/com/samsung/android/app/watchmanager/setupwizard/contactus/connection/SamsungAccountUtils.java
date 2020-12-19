package com.samsung.android.app.watchmanager.setupwizard.contactus.connection;

import android.text.TextUtils;
import android.util.Log;
import java.io.InputStream;
import java.util.Scanner;

public class SamsungAccountUtils {
    private static final String TAG = "SamsungAccountUtils";
    public static final int URL_TYPE_API = 0;
    public static final int URL_TYPE_AUTH = 1;

    private static String convertHttpStreamToString(InputStream inputStream) {
        Scanner useDelimiter = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        return useDelimiter.hasNext() ? useDelimiter.next() : "";
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x010d  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0112 A[SYNTHETIC, Splitter:B:45:0x0112] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0119  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0125  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x012a A[SYNTHETIC, Splitter:B:57:0x012a] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0131 A[SYNTHETIC, Splitter:B:61:0x0131] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getNewAccessToken(java.lang.String r11, android.content.SharedPreferences r12) {
        /*
        // Method dump skipped, instructions count: 314
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.SamsungAccountUtils.getNewAccessToken(java.lang.String, android.content.SharedPreferences):java.lang.String");
    }

    public static String getServerUrlFromToken(int i, String str) {
        String str2 = ".samsungosp.com";
        String str3 = "";
        if (i == 0) {
            str3 = "api";
        } else if (i != 1) {
            str2 = str3;
        } else {
            str3 = "auth";
        }
        if (!TextUtils.isEmpty(str)) {
            char charAt = str.toLowerCase().charAt(0);
            if (charAt == 'a' || charAt == 'b' || charAt == 'c') {
                str3 = "us-auth2";
            } else if (charAt == 'd' || charAt == 'e' || charAt == 'f') {
                str3 = "cn-" + str3;
            } else if (charAt == 'g' || charAt == 'h' || charAt == 'j') {
                str3 = "eu-auth2";
            }
        }
        Log.d(TAG, "getServerUrlFromToken() type : " + i + " prefix : " + str3);
        return str3 + str2;
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x0168 */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x012f  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0134 A[SYNTHETIC, Splitter:B:35:0x0134] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x015e  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0163  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0171  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0176 A[SYNTHETIC, Splitter:B:52:0x0176] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x017e  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0183 A[SYNTHETIC, Splitter:B:58:0x0183] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x019f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01a0  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01a8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean getTokenValidation(android.content.Context r6, java.lang.String r7, java.lang.String r8) {
        /*
        // Method dump skipped, instructions count: 454
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.SamsungAccountUtils.getTokenValidation(android.content.Context, java.lang.String, java.lang.String):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00cc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean parseValidationFromXML(java.lang.String r8) {
        /*
        // Method dump skipped, instructions count: 210
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.SamsungAccountUtils.parseValidationFromXML(java.lang.String):boolean");
    }
}
