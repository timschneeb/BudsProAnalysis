package com.samsung.accessory.hearablemgr.core;

import com.samsung.accessory.fotaprovider.AccessoryState;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import seccompat.android.util.Log;

public class EarBudsFotaInfo {
    private static final String TAG = "Attic_EarBudsFotaInfo";
    public String deviceId;
    public String firmwareVersion;
    public int isFotaDM;
    public String modelNumber = "SM-R190";
    public String salesCode;
    public String serialNumber;
    public AccessoryState state;
    public String uniqueNumber;

    public EarBudsFotaInfo() {
        String str = this.salesCode;
        this.salesCode = str == null ? Preferences.getString(PreferenceKey.DEVICE_FOTA_SKU, null, UhmFwUtil.getLastLaunchDeviceId()) : str;
        this.uniqueNumber = "";
    }

    public void printFota() {
        Log.d(TAG, " : isFotaDM : " + this.isFotaDM);
        Log.d(TAG, " : deviceId : " + this.deviceId);
        Log.d(TAG, " : modelNumber : " + this.modelNumber);
        Log.d(TAG, " : salesCode : " + this.salesCode);
        Log.d(TAG, " : firmwareVersion : " + this.firmwareVersion);
        Log.d(TAG, " : uniqueNumber : " + this.uniqueNumber);
        Log.d(TAG, " : serialNumber : " + this.serialNumber);
    }
}
