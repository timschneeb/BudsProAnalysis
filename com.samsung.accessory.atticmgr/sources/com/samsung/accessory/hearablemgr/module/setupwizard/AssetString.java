package com.samsung.accessory.hearablemgr.module.setupwizard;

import android.content.res.Configuration;
import android.os.Build;
import android.text.TextUtils;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import seccompat.android.util.Log;

public class AssetString {
    private static final String TAG = "Attic_AssetString";

    public static String getStringEULA() {
        Log.d(TAG, "getStringEULA()");
        String string = getString("EULA");
        if (!Util.isJapanModel()) {
            string = string.replaceAll("Samsung Account", "Galaxy account");
        }
        Log.d(TAG, "getStringEULA()_end");
        return string;
    }

    public static String getStringRDI() {
        Log.d(TAG, "getStringRDI()");
        String string = getString("RDI");
        Log.d(TAG, "getStringRDI()_end");
        return string;
    }

    public static String getString(String str) {
        String str2;
        Locale[] localeArr;
        Log.d(TAG, "getString() : " + str);
        try {
            String[] list = Application.getContext().getAssets().list(str);
            if (list != null) {
                Configuration configuration = Application.getContext().getResources().getConfiguration();
                if (Build.VERSION.SDK_INT >= 24) {
                    localeArr = new Locale[configuration.getLocales().size()];
                    for (int i = 0; i < localeArr.length; i++) {
                        localeArr[i] = configuration.getLocales().get(0);
                    }
                } else {
                    localeArr = new Locale[]{configuration.locale};
                }
                String str3 = null;
                for (int i2 = 0; i2 < localeArr.length && str3 == null; i2++) {
                    Locale locale = localeArr[i2];
                    if (locale != null) {
                        String language = locale.getLanguage();
                        String country = locale.getCountry();
                        Log.d(TAG, "getString() : " + str + " : language=" + language + ", country=" + country);
                        if (TextUtils.isEmpty(str3) && !TextUtils.isEmpty(language) && !TextUtils.isEmpty(country)) {
                            String str4 = language + "-r" + country + ".txt";
                            if (listContains(list, str4)) {
                                str3 = str4;
                            }
                            Log.d(TAG, "getString() : " + str + " : key=" + str4 + ", filename=" + str3);
                        }
                        if (TextUtils.isEmpty(str3) && !TextUtils.isEmpty(language)) {
                            String str5 = language + ".txt";
                            if (listContains(list, str5)) {
                                str3 = str5;
                            }
                            Log.d(TAG, "getString() : " + str + " : key=" + str5 + ", filename=" + str3);
                        }
                        if (TextUtils.isEmpty(str3) && !TextUtils.isEmpty(language)) {
                            String str6 = str3;
                            for (String str7 : list) {
                                if (str7 != null && str7.startsWith(language)) {
                                    str6 = str7;
                                }
                            }
                            Log.d(TAG, "getString() : " + str + " : language=" + language + ", filename=" + str6);
                            str3 = str6;
                        }
                    }
                }
                if (str3 == null) {
                    str3 = "en.txt";
                }
                str2 = getStringFromPath(str + "/" + str3);
            } else {
                str2 = null;
            }
            return str2;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getString() : " + str + " : Exception : " + e);
            return null;
        }
    }

    public static String getStringFromPath(String str) {
        Exception e;
        Log.d(TAG, "getStringFromFile() : " + str);
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(Application.getContext().getAssets().open(str)));
            while (true) {
                try {
                    String readLine = bufferedReader2.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                    sb.append("\n");
                } catch (Exception e2) {
                    e = e2;
                    bufferedReader = bufferedReader2;
                    Log.e(TAG, "getStringFromFile() : exception : " + e);
                    Util.safeClose(bufferedReader);
                    Log.d(TAG, "getStringFromFile()_end : " + str);
                    return sb.toString();
                }
            }
            bufferedReader = bufferedReader2;
        } catch (Exception e3) {
            e = e3;
            Log.e(TAG, "getStringFromFile() : exception : " + e);
            Util.safeClose(bufferedReader);
            Log.d(TAG, "getStringFromFile()_end : " + str);
            return sb.toString();
        }
        Util.safeClose(bufferedReader);
        Log.d(TAG, "getStringFromFile()_end : " + str);
        return sb.toString();
    }

    private static boolean listContains(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }
}
