package com.samsung.android.app.twatchmanager.plugindatamanager;

import android.content.Context;
import android.os.Bundle;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.plugininfoservice.MessageConfig;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import java.util.ArrayList;
import java.util.List;

public class PluginDataManager {
    private static final String TAG = ("tUHM:" + PluginDataManager.class.getSimpleName());
    private static volatile PluginDataManager mInstance = null;

    public static PluginDataManager getInstance() {
        if (mInstance == null) {
            mInstance = new PluginDataManager();
        }
        return mInstance;
    }

    public ArrayList<ApiInfo> createApiList() {
        ArrayList<ApiInfo> arrayList = new ArrayList<>();
        arrayList.add(new ApiInfo(MessageConfig.Type.INSTALLED_PLUGIN_LIST));
        return arrayList;
    }

    public ArrayList<PluginData> getBandPluginList() {
        return new ArrayList<>();
    }

    public ArrayList<PluginData> getEarbudPluginList() {
        return new ArrayList<>();
    }

    public ArrayList<PluginData> getInstalledPluginList(Context context) {
        List<DeviceRegistryData> queryAllDeviceRegistryData = new RegistryDbManagerWithProvider().queryAllDeviceRegistryData(context);
        ArrayList<PluginData> arrayList = new ArrayList<>();
        for (DeviceRegistryData deviceRegistryData : queryAllDeviceRegistryData) {
            String str = deviceRegistryData.packagename;
            if (HostManagerUtils.isExistPackage(context, str)) {
                arrayList.add(new PluginData(deviceRegistryData.deviceBtID, str, GearRulesManager.getInstance().getGearInfo(HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(deviceRegistryData.deviceFixedName)).group.wearableType, HostManagerUtils.isApplicationEnabled(context, str)));
            } else {
                String str2 = TAG;
                Log.d(str2, str + " is not installed.");
            }
        }
        return arrayList;
    }

    public Bundle getInstalledPluginListResult(Context context) {
        Bundle bundle = new Bundle();
        ArrayList<PluginData> installedPluginList = getInstalledPluginList(context);
        StringBuilder sb = new StringBuilder();
        if (!(installedPluginList == null || installedPluginList.size() == 0)) {
            sb.append("[");
            for (int i = 0; i < installedPluginList.size(); i++) {
                if (i != 0) {
                    sb.append(",");
                }
                sb.append(installedPluginList.get(i));
            }
            sb.append("]");
        }
        String str = TAG;
        Log.d(str, "getInstalledPluginListResult() : " + sb.toString());
        bundle.putString("result", sb.toString());
        return bundle;
    }

    public Bundle getSupportedApiList() {
        Bundle bundle = new Bundle();
        ArrayList<ApiInfo> createApiList = createApiList();
        StringBuilder sb = new StringBuilder();
        if (!(createApiList == null || createApiList.size() == 0)) {
            sb.append("[");
            for (int i = 0; i < createApiList.size(); i++) {
                if (i != 0) {
                    sb.append(",");
                }
                sb.append(createApiList.get(i));
            }
            sb.append("]");
        }
        String str = TAG;
        Log.d(str, "getSupportedApiList() : " + sb.toString());
        bundle.putString("result", sb.toString());
        return bundle;
    }

    public ArrayList<PluginData> getWatchPluginList() {
        return new ArrayList<>();
    }
}
