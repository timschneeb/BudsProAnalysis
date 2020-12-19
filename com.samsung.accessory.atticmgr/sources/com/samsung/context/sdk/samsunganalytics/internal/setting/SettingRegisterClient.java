package com.samsung.context.sdk.samsunganalytics.internal.setting;

import android.content.Context;
import android.content.SharedPreferences;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SettingRegisterClient implements AsyncTaskClient {
    private Context context;
    private Map<String, Set<String>> map;

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public int onFinish() {
        return 0;
    }

    public SettingRegisterClient(Context context2, Map<String, Set<String>> map2) {
        this.context = context2;
        this.map = map2;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public void run() {
        SharedPreferences preferences = Preferences.getPreferences(this.context);
        for (String str : preferences.getStringSet(Preferences.APP_PREF_NAMES, new HashSet())) {
            preferences.edit().remove(str).apply();
        }
        preferences.edit().remove(Preferences.APP_PREF_NAMES).apply();
        HashSet hashSet = new HashSet();
        for (Map.Entry<String, Set<String>> entry : this.map.entrySet()) {
            String key = entry.getKey();
            hashSet.add(key);
            preferences.edit().putStringSet(key, entry.getValue()).apply();
        }
        preferences.edit().putStringSet(Preferences.APP_PREF_NAMES, hashSet).apply();
    }
}
