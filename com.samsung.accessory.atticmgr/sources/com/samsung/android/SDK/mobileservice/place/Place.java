package com.samsung.android.sdk.mobileservice.place;

import android.os.Bundle;
import android.text.TextUtils;
import com.samsung.android.sdk.mobileservice.social.buddy.provider.BuddyContract;

public class Place {
    public static final int METHOD_TYPE_BT = 4;
    public static final int METHOD_TYPE_MAP = 1;
    public static final int METHOD_TYPE_MAP_WITH_WIFI = 3;
    public static final int METHOD_TYPE_NONE = 0;
    public static final int PLACE_TYPE_CAR = 3;
    public static final int PLACE_TYPE_HOME = 1;
    public static final int PLACE_TYPE_OTHERS = 4;
    public static final int PLACE_TYPE_SCHOOL = 5;
    public static final int PLACE_TYPE_WORK = 2;
    private String mAddress;
    private String mBluetoothMacAddress;
    private String mBluetoothName;
    private String mId;
    private double mLatitude;
    private double mLongitude;
    private String mName;
    private int mPlaceType;
    private int mSourceType = 1;
    private long mUpdatedTime;
    private String mWifiBssId;
    private String mWifiName;

    interface SourceType {
        public static final int APP_PROVIDED = 2;
        public static final int SYSTEM_PREDICTED = 3;
        public static final int USER_DEFINED = 1;
    }

    public Place(Bundle bundle) {
        readFromBundle(bundle);
    }

    public Place() {
    }

    public String getId() {
        return this.mId;
    }

    public void setId(String str) {
        this.mId = str;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public int getPlaceType() {
        return this.mPlaceType;
    }

    public void setPlaceType(int i) {
        this.mPlaceType = i;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public void setAddress(String str) {
        this.mAddress = str;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public void setLatitude(double d) {
        this.mLatitude = d;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public void setLongitude(double d) {
        this.mLongitude = d;
    }

    public String getWifiName() {
        return this.mWifiName;
    }

    public void setWifiName(String str) {
        this.mWifiName = str;
    }

    public String getWifiBssId() {
        return this.mWifiBssId;
    }

    public void setWifiBssId(String str) {
        this.mWifiBssId = str;
    }

    public String getBluetoothName() {
        return this.mBluetoothName;
    }

    public void setBluetoothName(String str) {
        this.mBluetoothName = str;
    }

    public String getBluetoothMacAddress() {
        return this.mBluetoothMacAddress;
    }

    public void setBluetoothMacAddress(String str) {
        this.mBluetoothMacAddress = str;
    }

    public void setSourceType(int i) {
        this.mSourceType = i;
    }

    public int getSourceType() {
        return this.mSourceType;
    }

    public int getMethodType() {
        if (!TextUtils.isEmpty(this.mBluetoothName)) {
            return 4;
        }
        if (!TextUtils.isEmpty(this.mAddress)) {
            return TextUtils.isEmpty(this.mWifiName) ? 1 : 3;
        }
        return 0;
    }

    public long getUpdatedTime() {
        return this.mUpdatedTime;
    }

    private void setUpdatedTime(long j) {
        this.mUpdatedTime = j;
    }

    private void readFromBundle(Bundle bundle) {
        this.mId = bundle.getString("place_key");
        this.mName = bundle.getString("name");
        this.mPlaceType = bundle.getInt("category");
        this.mAddress = bundle.getString(BuddyContract.Email.ADDRESS);
        this.mLatitude = bundle.getDouble("latitude");
        this.mLongitude = bundle.getDouble("longitude");
        this.mWifiName = bundle.getString("wifi_name");
        this.mWifiBssId = bundle.getString("wifi_bss_id");
        this.mBluetoothName = bundle.getString("bluetooth_name");
        this.mBluetoothMacAddress = bundle.getString("bluetooth_mac_address");
        this.mUpdatedTime = bundle.getLong("time_stamp_utc");
        this.mSourceType = bundle.getInt("type");
    }

    /* access modifiers changed from: package-private */
    public Bundle writeToBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("place_key", this.mId);
        bundle.putString("name", this.mName);
        bundle.putInt("category", this.mPlaceType);
        bundle.putString(BuddyContract.Email.ADDRESS, this.mAddress);
        bundle.putDouble("latitude", this.mLatitude);
        bundle.putDouble("longitude", this.mLongitude);
        bundle.putString("wifi_name", this.mWifiName);
        bundle.putString("wifi_bss_id", this.mWifiBssId);
        bundle.putString("bluetooth_name", this.mBluetoothName);
        bundle.putString("bluetooth_mac_address", this.mBluetoothMacAddress);
        bundle.putLong("time_stamp_utc", this.mUpdatedTime);
        bundle.putInt("type", this.mSourceType);
        bundle.putInt("location_type", getMethodType());
        return bundle;
    }
}
