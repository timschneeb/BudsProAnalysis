package com.samsung.accessory.hearablemgr.module.setupwizard;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.text.TextUtils;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import seccompat.android.util.Log;

public class PrivacyNotice {
    private static final String ASSET_PATH = "Privacy Notice";
    private static final Map<String, String> MAP_GDPR_LANGUAGE_TO_FILENAME = new HashMap();
    private static final Map<String, String> MAP_GLOBAL_LANGUAGE_TO_FILENAME = new HashMap();
    private static final Map<String, String> MAP_US_LANGUAGE_TO_FILENAME = new HashMap();
    private static final String PRIVACY_POLICY_PAGE_URL = "https://account.samsung.com/membership/pp";
    private static final String TAG = "Attic_PrivacyNotice";

    static {
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("sq", "[EU] Galaxy Wearable Privacy Notice_200521_English_Albanian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("bs", "[EU] Galaxy Wearable Privacy Notice_200521_English_Bosnian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("bg", "[EU] Galaxy Wearable Privacy Notice_200521_English_Bulgarian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("ca", "[EU] Galaxy Wearable Privacy Notice_200521_English_Catalan.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("hr", "[EU] Galaxy Wearable Privacy Notice_200521_English_Croatian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("cs", "[EU] Galaxy Wearable Privacy Notice_200521_English_Czech.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("da", "[EU] Galaxy Wearable Privacy Notice_200521_English_Danish.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("nl", "[EU] Galaxy Wearable Privacy Notice_200521_English_Dutch.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put(HttpNetworkInterface.XTP_HTTP_LANGUAGE, "[EU] Galaxy Wearable Privacy Notice_200521_English_English_UK.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("et", "[EU] Galaxy Wearable Privacy Notice_200521_English_Estonian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("fi", "[EU] Galaxy Wearable Privacy Notice_200521_English_Finnish.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("fr", "[EU] Galaxy Wearable Privacy Notice_200521_English_French.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("de", "[EU] Galaxy Wearable Privacy Notice_200521_English_German.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("el", "[EU] Galaxy Wearable Privacy Notice_200521_English_Greek.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("hu", "[EU] Galaxy Wearable Privacy Notice_200521_English_Hungarian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("is", "[EU] Galaxy Wearable Privacy Notice_200521_English_Icelandic.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("it", "[EU] Galaxy Wearable Privacy Notice_200521_English_Italian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("lv", "[EU] Galaxy Wearable Privacy Notice_200521_English_Latvian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("lt", "[EU] Galaxy Wearable Privacy Notice_200521_English_Lithuanian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("mk", "[EU] Galaxy Wearable Privacy Notice_200521_English_Macedonian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("no", "[EU] Galaxy Wearable Privacy Notice_200521_English_Norwegian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("pl", "[EU] Galaxy Wearable Privacy Notice_200521_English_Polish.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("pt", "[EU] Galaxy Wearable Privacy Notice_200521_English_Portuguese.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("ro", "[EU] Galaxy Wearable Privacy Notice_200521_English_Romanian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("sr", "[EU] Galaxy Wearable Privacy Notice_200521_English_Serbian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("sk", "[EU] Galaxy Wearable Privacy Notice_200521_English_Slovak.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("sl", "[EU] Galaxy Wearable Privacy Notice_200521_English_Slovenian.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("es", "[EU] Galaxy Wearable Privacy Notice_200521_English_Spanish.txt");
        MAP_GDPR_LANGUAGE_TO_FILENAME.put("sv", "[EU] Galaxy Wearable Privacy Notice_200521_English_Swedish.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("sq", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Albanian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ar", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Arabic.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("hy", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Armenian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("as", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Assamese.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("az", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Azerbaijani.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("eu", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Basque.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("be", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Belarusian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("bn", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Bengali.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("bn-rBD", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Bengali_Bangladesh.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("bs", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Bosnian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("bg", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Bulgarian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ca", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Catalan.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("zh", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Chinese Simplified.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("zh-rHK", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Chinese Traditional_HongKong.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("zh-rTW", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Chinese Traditional_Taiwan.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("hr", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Croatian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("cs", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Czech.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("da", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Danish.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("nl", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Dutch.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("en-rCA", "[Global] Galaxy Wearable Privacy Notice_Global_191229_English_Canada.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put(HttpNetworkInterface.XTP_HTTP_LANGUAGE, "[Global] Galaxy Wearable Privacy Notice_Global_191229_English_UK.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("et", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Estonian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("fa", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Farsi.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("fil", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Filipino.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("fi", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Finnish.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("fr", "[Global] Galaxy Wearable Privacy Notice_Global_191229_French.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("fr-rCD", "[Global] Galaxy Wearable Privacy Notice_Global_191229_French_Canada.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("gl", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Galician.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ka", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Georgian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("de", "[Global] Galaxy Wearable Privacy Notice_Global_191229_German.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("el", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Greek.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("gu", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Gujarati.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("he", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Hebrew.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("hi", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Hindi.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("hu", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Hungarian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("is", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Icelandic.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("id", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Indonesian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ga", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Irish.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("it", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Italian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ja", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Japanese.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("kn", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Kannada.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("kk", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Kazakh.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("km", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Khmer.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ky", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Kyrgyz.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("lo", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Lao.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("lv", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Latvian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("lt", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Lithuanian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("mk", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Macedonian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ms", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Malay.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ml", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Malayalam.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("mr", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Marathi.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("mn", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Mongolian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("my", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Myanmar.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("my-rMM", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Myanmar_Unicode.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ne", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Nepali.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("no", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Norwegian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("or", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Odia.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("pl", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Polish.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("pt", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Portuguese.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("pt-Latn", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Portuguese_Latin.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("pa", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Punjabi.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ro", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Romanian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ru", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Russian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("sr", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Serbian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("si", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Sinhala.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("sk", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Slovak.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("sl", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Slovenian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("es", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Spanish.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("es-Latn", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Spanish_Latin.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("sv", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Swedish.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("tg", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Tajik.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ta", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Tamil.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("te", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Telugu.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("th", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Thai.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("tr", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Turkish.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("tk", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Turkmen.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("uk", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Ukrainian.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("ur", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Urdu.txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("uz", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Uzbek(Latin).txt");
        MAP_GLOBAL_LANGUAGE_TO_FILENAME.put("vi", "[Global] Galaxy Wearable Privacy Notice_Global_191229_Vietnamese.txt");
        MAP_US_LANGUAGE_TO_FILENAME.put("Latn", "[US] Galaxy Wearable Privacy Notice_US_200229_Spanish_Latin.txt");
        MAP_US_LANGUAGE_TO_FILENAME.put(HttpNetworkInterface.XTP_HTTP_LANGUAGE, "[US] Galaxy Wearable Privacy Notice_US_200229_English_US.txt");
        if (Application.DEBUG_MODE) {
            Log.d(TAG, "MAP_GDPR_LANGUAGE_TO_FILENAME.size() : " + MAP_GDPR_LANGUAGE_TO_FILENAME.size());
            Log.d(TAG, "MAP_GLOBAL_LANGUAGE_TO_FILENAME.size() : " + MAP_GLOBAL_LANGUAGE_TO_FILENAME.size());
            Log.d(TAG, "MAP_US_LANGUAGE_TO_FILENAME.size() : " + MAP_US_LANGUAGE_TO_FILENAME.size());
            HashSet hashSet = null;
            try {
                hashSet = new HashSet(Arrays.asList(Application.getContext().getAssets().list(ASSET_PATH)));
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Exception : " + e);
            }
            if (hashSet != null) {
                Map[] mapArr = {MAP_GDPR_LANGUAGE_TO_FILENAME, MAP_GLOBAL_LANGUAGE_TO_FILENAME, MAP_US_LANGUAGE_TO_FILENAME};
                int i = 0;
                for (Map map : mapArr) {
                    for (String str : map.keySet()) {
                        String str2 = (String) map.get(str);
                        if (hashSet.contains(str2)) {
                            i++;
                        } else {
                            throw new RuntimeException("'" + str2 + "' file does NOT exist");
                        }
                    }
                }
                Log.d(TAG, i + " file test done");
                return;
            }
            Log.e(TAG, "setAssetFiles == null");
        }
    }

    private static String getFilenameFromMap(Map<String, String> map) {
        Locale locale = Util.getLocale();
        String str = null;
        if (locale != null) {
            String language = locale.getLanguage();
            String country = locale.getCountry();
            String script = locale.getScript();
            Log.d(TAG, "getFilenameFromMap() : language=" + language + ", country=" + country + ", script=" + script);
            if (!TextUtils.isEmpty(language) && !TextUtils.isEmpty(country)) {
                String str2 = language + "-r" + country;
                String str3 = map.get(str2);
                Log.d(TAG, "getFilenameFromMap() : key=" + str2 + ", filename=" + str3);
                str = str3;
            }
            if (TextUtils.isEmpty(str) && !TextUtils.isEmpty(language) && !TextUtils.isEmpty(script)) {
                String str4 = language + "-" + script;
                str = map.get(str4);
                Log.d(TAG, "getFilenameFromMap() : key=" + str4 + ", filename=" + str);
            }
            if (TextUtils.isEmpty(str) && !TextUtils.isEmpty(language)) {
                String lowerCase = language.toLowerCase();
                str = map.get(lowerCase);
                Log.d(TAG, "getFilenameFromMap() : language=" + lowerCase + ", filename=" + str);
            }
        }
        if (str == null) {
            str = map.get(HttpNetworkInterface.XTP_HTTP_LANGUAGE);
        }
        Log.d(TAG, "getFilenameFromMap() : " + str);
        return str;
    }

    private static String getPrivacyNoticeFromFile(String str) {
        Exception e;
        Log.d(TAG, "getPrivacyNoticeFromFile() : " + str);
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            AssetManager assets = Application.getContext().getAssets();
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(assets.open("Privacy Notice/" + str)));
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
                    Log.e(TAG, "getPrivacyNoticeFromFile() : exception : " + e);
                    Util.safeClose(bufferedReader);
                    Log.d(TAG, "getPrivacyNoticeFromFile()_end : " + str);
                    return sb.toString();
                }
            }
            bufferedReader = bufferedReader2;
        } catch (Exception e3) {
            e = e3;
            Log.e(TAG, "getPrivacyNoticeFromFile() : exception : " + e);
            Util.safeClose(bufferedReader);
            Log.d(TAG, "getPrivacyNoticeFromFile()_end : " + str);
            return sb.toString();
        }
        Util.safeClose(bufferedReader);
        Log.d(TAG, "getPrivacyNoticeFromFile()_end : " + str);
        return sb.toString();
    }

    private static String getPrivacyNoticeUSA() {
        Locale locale = Util.getLocale();
        String str = (locale == null || !"Latn".equalsIgnoreCase(locale.getScript())) ? null : MAP_US_LANGUAGE_TO_FILENAME.get("Latn");
        if (str == null) {
            str = MAP_US_LANGUAGE_TO_FILENAME.get(HttpNetworkInterface.XTP_HTTP_LANGUAGE);
        }
        return getPrivacyNoticeFromFile(str);
    }

    private static String getPrivacyNoticeGDPR() {
        Log.d(TAG, "getPrivacyNoticeGDPR()");
        return getPrivacyNoticeFromFile(getFilenameFromMap(MAP_GDPR_LANGUAGE_TO_FILENAME));
    }

    private static String getPrivacyNoticeGlobal() {
        Log.d(TAG, "getPrivacyNoticeGlobal()");
        return getPrivacyNoticeFromFile(getFilenameFromMap(MAP_GLOBAL_LANGUAGE_TO_FILENAME));
    }

    public static String getString() {
        String countryIso = Util.getCountryIso();
        if (Util.isUSA(countryIso)) {
            return getPrivacyNoticeUSA();
        }
        if (Util.isGDPRCountry(countryIso) || Util.isLGPDCountry(countryIso)) {
            return getPrivacyNoticeGDPR();
        }
        return getPrivacyNoticeGlobal();
    }

    public static void startOnlinePage(Activity activity) {
        Log.d(TAG, "startOnlinePage() : " + Util.toSimpleString(activity));
        if (Application.DEBUG_MODE) {
            activity.startActivity(new Intent(activity, NoticePrivacyPolicyActivity.class));
        }
        UiUtil.startWebBrowser(activity, PRIVACY_POLICY_PAGE_URL);
    }
}
