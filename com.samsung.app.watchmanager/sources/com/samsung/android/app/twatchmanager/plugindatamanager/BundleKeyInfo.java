package com.samsung.android.app.twatchmanager.plugindatamanager;

import com.samsung.android.app.twatchmanager.plugininfoservice.MessageConfig;
import org.json.JSONException;
import org.json.JSONObject;

public class BundleKeyInfo {
    private String bundleKey;
    private String returnType;

    public BundleKeyInfo(MessageConfig.Key key) {
        this.bundleKey = key.KEY;
        this.returnType = key.TYPE;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("bundleKey", this.bundleKey);
            jSONObject.put("returnType", this.returnType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
