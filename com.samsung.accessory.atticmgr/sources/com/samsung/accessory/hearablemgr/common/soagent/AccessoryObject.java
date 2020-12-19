package com.samsung.accessory.hearablemgr.common.soagent;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class AccessoryObject extends JSONObject {
    public static final String TAG_BDADDRESS = "accessoryBDAddress";
    public static final String TAG_FUMOACCESSORY = "accessoryVO";
    public static final String TAG_HOSTDEVICEINFO = "hostDeviceInfo";
    public static final String TAG_MCC = "accessoryMCC";
    public static final String TAG_MODELNAME = "accessoryModelID";
    public static final String TAG_SERIALNUMBER = "accessorySerialNumber";
    public static final String TAG_TMCC = "hostDeviceMccByNetwork";
    public static final String TAG_TMNC = "hostDeviceMncByNetwork";
    public static final String TAG_TYPECODE = "accessoryTypeCode";
    AccessoryInfo mAccInfo;

    public AccessoryInfo getAccInfo() {
        return this.mAccInfo;
    }

    public AccessoryObject(AccessoryInfo accessoryInfo) {
        this.mAccInfo = accessoryInfo;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(TAG_TYPECODE, accessoryInfo.getType());
            jSONObject.put(TAG_SERIALNUMBER, "TWID:" + accessoryInfo.getSerialNumber().trim());
            jSONObject.put(TAG_BDADDRESS, accessoryInfo.getBTAddress());
            if (!TextUtils.isEmpty(accessoryInfo.getModelName())) {
                jSONObject.put(TAG_MODELNAME, accessoryInfo.getModelName());
            }
            if (!TextUtils.isEmpty(accessoryInfo.getMCC())) {
                jSONObject.put(TAG_MCC, accessoryInfo.getMCC());
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(TAG_TMCC, accessoryInfo.getHDMCC());
            jSONObject2.put(TAG_TMNC, accessoryInfo.getHDMNC());
            jSONObject.put(TAG_HOSTDEVICEINFO, jSONObject2);
            put(TAG_FUMOACCESSORY, jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
