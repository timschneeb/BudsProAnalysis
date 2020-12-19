package com.samsung.android.app.twatchmanager.plugindatamanager;

import org.json.JSONException;
import org.json.JSONObject;

public class PluginData {
    public String mDeviceId;
    public boolean mIsEnabled;
    public String mPackageName;
    public String mPluginType;

    public PluginData(String str, String str2, String str3, boolean z) {
        this.mDeviceId = str;
        this.mPackageName = str2;
        this.mPluginType = str3;
        this.mIsEnabled = z;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("deviceId", this.mDeviceId);
            jSONObject.put("packageName", this.mPackageName);
            jSONObject.put("pluginType", this.mPluginType);
            jSONObject.put("isEnabled", this.mIsEnabled);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
