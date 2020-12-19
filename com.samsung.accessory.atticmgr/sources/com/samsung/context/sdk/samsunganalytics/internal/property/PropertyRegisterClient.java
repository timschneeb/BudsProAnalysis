package com.samsung.context.sdk.samsunganalytics.internal.property;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import java.util.Map;

public class PropertyRegisterClient implements AsyncTaskClient {
    private Context mContext;
    private Map<String, String> mMap;

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public int onFinish() {
        return 0;
    }

    public PropertyRegisterClient(Context context, Map<String, String> map) {
        this.mContext = context;
        map.remove("t");
        this.mMap = map;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public void run() {
        SharedPreferences propertyPreferences = Preferences.getPropertyPreferences(this.mContext);
        for (Map.Entry<String, String> entry : this.mMap.entrySet()) {
            if (TextUtils.isEmpty(entry.getValue())) {
                propertyPreferences.edit().remove(entry.getKey()).apply();
            } else {
                propertyPreferences.edit().putString(entry.getKey(), entry.getValue()).apply();
            }
        }
    }
}
