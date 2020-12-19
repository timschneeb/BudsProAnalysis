package com.sec.android.diagmonagent.log.ged.db.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import com.sec.android.diagmonagent.log.ged.db.model.ServiceInfo;
import com.sec.android.diagmonagent.log.ged.util.DeviceUtils;
import java.util.concurrent.TimeUnit;

public class ServiceDao {
    private static final String PREFERENCE_NAME = "DIAGMON_SERVICE";
    public final long MAX_KEEP_TIME = TimeUnit.DAYS.toMillis(30);
    private SharedPreferences preferences;

    public ServiceDao(Context context) {
        this.preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
    }

    public ServiceInfo getServiceInfo() {
        String string = this.preferences.getString("serviceId", "");
        if (TextUtils.isEmpty(string)) {
            Log.d(DeviceUtils.TAG, "service is not exist");
            return null;
        }
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId(string);
        serviceInfo.setTrackingId(this.preferences.getString("trackingId", ""));
        serviceInfo.setDeviceId(this.preferences.getString("deviceId", ""));
        serviceInfo.setServiceVersion(this.preferences.getString("serviceVersion", ""));
        serviceInfo.setServiceAgreeType(this.preferences.getString("serviceAgreeType", ""));
        serviceInfo.setSdkVersion(this.preferences.getString("sdkVersion", ""));
        serviceInfo.setSdkType(this.preferences.getString("sdkType", ""));
        serviceInfo.setDocumentId(this.preferences.getString("documentId", ""));
        serviceInfo.setStatus(this.preferences.getInt("status", 0));
        serviceInfo.setTimestamp(this.preferences.getLong("timestamp", 0));
        return serviceInfo;
    }

    public boolean existUnregisteredService() {
        if (TextUtils.isEmpty(this.preferences.getString("serviceId", ""))) {
            Log.d(DeviceUtils.TAG, "service is not exist");
            return false;
        } else if (this.preferences.getInt("status", 0) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void insert(ServiceInfo serviceInfo) {
        SharedPreferences.Editor edit = this.preferences.edit();
        edit.putString("serviceId", serviceInfo.getServiceId());
        edit.putString("trackingId", serviceInfo.getTrackingId());
        edit.putString("deviceId", serviceInfo.getDeviceId());
        edit.putString("serviceVersion", serviceInfo.getServiceVersion());
        edit.putString("serviceAgreeType", serviceInfo.getServiceAgreeType());
        edit.putString("sdkVersion", serviceInfo.getSdkVersion());
        edit.putString("sdkType", serviceInfo.getSdkType());
        edit.putString("documentId", serviceInfo.getDocumentId());
        edit.putInt("status", serviceInfo.getStatus());
        edit.putLong("timestamp", serviceInfo.getTimestamp());
        edit.apply();
    }

    public void update(ServiceInfo serviceInfo) {
        insert(serviceInfo);
    }

    public void updateDocumentId(String str) {
        this.preferences.edit().putString("documentId", str).apply();
    }

    public void updateStatus(int i) {
        this.preferences.edit().putInt("status", i).apply();
    }

    public void deleteServiceByTime(long j) {
        long j2 = this.preferences.getLong("timestamp", 0);
        if (this.preferences.getInt("status", 0) == 0 && j > 0 && j2 <= j) {
            Log.w(DeviceUtils.TAG, "delete service by time");
            SharedPreferences.Editor edit = this.preferences.edit();
            edit.clear();
            edit.apply();
        }
    }

    public void printAllServiceInfo() {
        Log.d(DeviceUtils.TAG, "=================ServiceInfo=================");
        String str = DeviceUtils.TAG;
        Log.d(str, "serviceId: " + this.preferences.getString("serviceId", "") + ", trackingId: " + this.preferences.getString("trackingId", "") + ", deviceId: " + this.preferences.getString("deviceId", "") + ", documentId: " + this.preferences.getString("documentId", "") + ", serviceVersion: " + this.preferences.getString("serviceVersion", "") + ", serviceAgreeType: " + this.preferences.getString("serviceAgreeType", "") + ", sdkVersion: " + this.preferences.getString("sdkVersion", "") + ", sdkType: " + this.preferences.getString("sdkType", "") + ", status: " + this.preferences.getInt("status", 0) + ", timestamp: " + this.preferences.getLong("timestamp", 0));
    }
}
