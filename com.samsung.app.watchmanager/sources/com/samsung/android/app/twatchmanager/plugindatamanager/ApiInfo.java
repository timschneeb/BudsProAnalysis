package com.samsung.android.app.twatchmanager.plugindatamanager;

import com.samsung.android.app.twatchmanager.plugininfoservice.MessageConfig;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiInfo {
    private ArrayList<BundleKeyInfo> bundleKeyInfo;
    private int requestId;
    private String requestMessageName;
    private int responseId;
    private String responseMessageName;
    private String returnType;

    public ApiInfo(MessageConfig.Type type) {
        this.requestMessageName = type.REQUEST_MESSAGE_NAME;
        this.requestId = type.REQUEST_ID;
        this.responseMessageName = type.RESPONSE_MESSAGE_NAME;
        this.responseId = type.RESPONSE_ID;
        this.returnType = type.RETURN_TYPE;
        this.bundleKeyInfo = getReturnKeyValueInfoList(type);
    }

    private String getBundleKeyInfo() {
        StringBuilder sb = new StringBuilder();
        ArrayList<BundleKeyInfo> arrayList = this.bundleKeyInfo;
        if (!(arrayList == null || arrayList.size() == 0)) {
            sb.append("[");
            for (int i = 0; i < this.bundleKeyInfo.size(); i++) {
                if (i != 0) {
                    sb.append(",");
                }
                sb.append(this.bundleKeyInfo.get(i));
            }
            sb.append("]");
        }
        return sb.toString();
    }

    private ArrayList<BundleKeyInfo> getReturnKeyValueInfoList(MessageConfig.Type type) {
        ArrayList<BundleKeyInfo> arrayList = new ArrayList<>();
        if (type == MessageConfig.Type.INSTALLED_PLUGIN_LIST) {
            arrayList.add(new BundleKeyInfo(MessageConfig.Key.INSTALLED_PLUGIN_LIST_RESULT));
        }
        return arrayList;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("requestMessageName", this.requestMessageName);
            jSONObject.put("requestId", this.requestId);
            jSONObject.put("responseMessageName", this.responseMessageName);
            jSONObject.put("responseId", this.responseId);
            jSONObject.put("bundleKeyInfo", getBundleKeyInfo());
            jSONObject.put("returnType", this.returnType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
