package com.samsung.android.app.twatchmanager.contentprovider;

import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;

public class DeviceRegistryData {
    private static final String TAG = ("tUHM:" + DeviceRegistryData.class.getSimpleName());
    public String _id;
    public String deviceBtID;
    public String deviceFixedName;
    public String deviceName;
    public int isConnected;
    public int lastLaunch;
    public String neckletAutoConnection;
    public String packagename;
    public int supportsPairing;

    public DeviceRegistryData(String str, String str2, String str3, int i, int i2, int i3) {
        this(str, str2, str3, i, i2, "null", i3);
    }

    public DeviceRegistryData(String str, String str2, String str3, int i, int i2, String str4, int i3) {
        this(str, str2, str3, i, i2, str4, HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(str3), i3);
    }

    public DeviceRegistryData(String str, String str2, String str3, int i, int i2, String str4, String str5, int i3) {
        this.supportsPairing = 1;
        this.packagename = str;
        this.deviceName = str2;
        this.deviceBtID = str3;
        this.lastLaunch = i;
        this.isConnected = i2;
        this.neckletAutoConnection = str4;
        this.deviceFixedName = str5;
        this.supportsPairing = i3;
    }

    public String toString() {
        return (((((("\nDB::[-- DeviceRegistryData --]" + "\nDB::packagename : " + this.packagename) + "\nDB::deviceBtID : " + this.deviceBtID) + "\nDB::deviceFixedName : " + this.deviceFixedName) + "\nDB::isConnected : " + this.isConnected) + "\nDB::lastLaunch : " + this.lastLaunch) + "\nDB::neckletAutoConnection : " + this.neckletAutoConnection) + "\nDB::supportsPairing : " + this.supportsPairing;
    }
}
