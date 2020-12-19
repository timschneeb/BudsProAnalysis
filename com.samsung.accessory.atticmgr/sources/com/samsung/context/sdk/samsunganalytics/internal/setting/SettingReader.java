package com.samsung.context.sdk.samsunganalytics.internal.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SettingReader {
    private static final int STATUS_LOG_BODY_LENGTH_LIMIT = 512;
    private final String THREE_DEPTH_ENTITY_DELIMETER = Utils.Depth.THREE_DEPTH.getCollectionDLM();
    private final String TWO_DEPTH_DELIMETER = Utils.Depth.TWO_DEPTH.getKeyValueDLM();
    private final String TWO_DEPTH_ENTITY_DELIMETER = Utils.Depth.TWO_DEPTH.getCollectionDLM();
    private Set<String> appPrefNames;
    private Context context;

    public SettingReader(Context context2) {
        this.context = context2;
        this.appPrefNames = Preferences.getPreferences(context2).getStringSet(Preferences.APP_PREF_NAMES, new HashSet());
    }

    private SharedPreferences getPreference(String str) {
        return this.context.getSharedPreferences(str, 0);
    }

    private Set<String> getKeySet(String str) {
        return Preferences.getPreferences(this.context).getStringSet(str, new HashSet());
    }

    private List<String> readFromApp() {
        String str;
        if (this.appPrefNames.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        String str2 = "";
        for (String str3 : this.appPrefNames) {
            SharedPreferences preference = getPreference(str3);
            Set<String> keySet = getKeySet(str3);
            for (Map.Entry<String, ?> entry : preference.getAll().entrySet()) {
                if (keySet.contains(entry.getKey())) {
                    Class<?> cls = entry.getValue().getClass();
                    if (cls.equals(Integer.class) || cls.equals(Float.class) || cls.equals(Long.class) || cls.equals(String.class) || cls.equals(Boolean.class)) {
                        str = "" + entry.getKey() + this.TWO_DEPTH_DELIMETER + entry.getValue();
                    } else {
                        String str4 = "" + entry.getKey() + this.TWO_DEPTH_DELIMETER;
                        String str5 = null;
                        for (String str6 : (Set) entry.getValue()) {
                            if (!TextUtils.isEmpty(str5)) {
                                str5 = str5 + this.THREE_DEPTH_ENTITY_DELIMETER;
                            }
                            str5 = str5 + str6;
                        }
                        str = str4 + str5;
                    }
                    if (str2.length() + str.length() > 512) {
                        arrayList.add(str2);
                        str2 = "";
                    } else if (!TextUtils.isEmpty(str2)) {
                        str2 = str2 + this.TWO_DEPTH_ENTITY_DELIMETER;
                    }
                    str2 = str2 + str;
                }
            }
        }
        if (str2.length() != 0) {
            arrayList.add(str2);
        }
        return arrayList;
    }

    public List<String> read() {
        return readFromApp();
    }
}
