package com.samsung.accessory.hearablemgr.common.uhm;

import android.content.ContentValues;
import android.text.TextUtils;

public class DeviceRegistryData {
    public Integer connected;
    public String deviceFixedName;
    public String deviceId;
    public String deviceName;
    public Integer lastLaunch;
    public String packageName;

    public DeviceRegistryData(String str, String str2, String str3, String str4, Integer num, Integer num2) {
        this.packageName = str;
        this.deviceName = str2;
        this.deviceFixedName = str3;
        this.deviceId = str4;
        this.lastLaunch = num;
        this.connected = num2;
    }

    public ContentValues toContentValues() {
        if (TextUtils.isEmpty(this.deviceName) || TextUtils.isEmpty(this.deviceId)) {
            throw new IllegalArgumentException();
        }
        ContentValues contentValues = new ContentValues();
        String str = this.packageName;
        if (str != null) {
            contentValues.put(BaseContentProvider.PACKAGE_NAME, str);
        }
        String str2 = this.deviceName;
        if (str2 != null) {
            contentValues.put("device_name", str2);
        }
        String str3 = this.deviceFixedName;
        if (str3 != null) {
            contentValues.put(BaseContentProvider.DEVICE_FIXED_NAME, str3);
        }
        String str4 = this.deviceId;
        if (str4 != null) {
            contentValues.put(BaseContentProvider.DEVICE_BT_ID, str4);
        }
        Integer num = this.lastLaunch;
        if (num != null) {
            contentValues.put(BaseContentProvider.LAST_LAUNCH, num);
        }
        Integer num2 = this.connected;
        if (num2 != null) {
            contentValues.put("connected", num2);
        }
        return contentValues;
    }
}
