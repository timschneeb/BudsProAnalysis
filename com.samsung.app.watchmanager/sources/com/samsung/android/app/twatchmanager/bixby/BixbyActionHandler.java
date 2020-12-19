package com.samsung.android.app.twatchmanager.bixby;

import android.content.Context;
import android.os.Bundle;
import c.b.a.a.a.a.a;
import c.b.a.a.a.a.b;
import com.samsung.android.app.twatchmanager.contentprovider.BaseContentProvider;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.ResourceRulesParser;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class BixbyActionHandler extends a {
    private static final String TAG = ("tUHM:" + BixbyActionHandler.class.getSimpleName());
    private final String RESULT = "result";
    private final String RESULT_FAILURE = "failure";
    private final String RESULT_SUCCESS = "success";

    public static class Actions {
        public static final String ACTION_CHECK_AVAILABLE_DEVICES = "GetAvailableDevices";
        public static final String ACTION_CHECK_PLUGIN_AVAILABLE = "CanUsePlugin";
    }

    private String getPluginPackageName(String str) {
        String pluginPackage = GearRulesManager.getInstance().getPluginPackage(str.replaceAll("_", " "));
        String str2 = TAG;
        Log.d(str2, "getPluginPackageName()::package_name = " + pluginPackage);
        return pluginPackage;
    }

    private void handleCheckAvailableDevices(Context context, Bundle bundle, b bVar) {
        int i;
        syncRulesIfNecessary();
        try {
            HashMap hashMap = (HashMap) bundle.getSerializable(a.PARAMS);
            Iterator it = hashMap.keySet().iterator();
            String str = "";
            while (true) {
                i = 0;
                if (!it.hasNext()) {
                    break;
                }
                String str2 = (String) it.next();
                if (str2.equals("target_group")) {
                    str = (String) ((List) hashMap.get(str2)).get(0);
                }
            }
            String str3 = TAG;
            Log.d(str3, "handleCheckAvailableDevices()::tartget_group = " + str);
            List<DeviceRegistryData> queryAllDeviceRegistryData = new RegistryDbManagerWithProvider().queryAllDeviceRegistryData(context);
            String str4 = TAG;
            Log.d(str4, "handleCheckAvailableDevices()::deviceList = " + queryAllDeviceRegistryData);
            JSONArray jSONArray = new JSONArray();
            for (DeviceRegistryData deviceRegistryData : queryAllDeviceRegistryData) {
                String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(deviceRegistryData.deviceFixedName);
                if (str.equals(GearRulesManager.getInstance().getGearInfo(simpleBTNameByName).group.wearableType)) {
                    i++;
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(ResourceRulesParser.XML_TAG_GROUP_NAME, simpleBTNameByName);
                    jSONObject.put("bt_connection", deviceRegistryData.isConnected == 2 ? BaseContentProvider.DEVICE_CONNECTED : "not_connected");
                    jSONObject.put(BaseContentProvider.VERSION, HostManagerUtils.getVersionCode(context, deviceRegistryData.packagename));
                    jSONObject.put("package_name", deviceRegistryData.packagename);
                    jSONArray.put(jSONObject);
                }
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("result", "success");
            jSONObject2.put("number_of_devices", i);
            jSONObject2.put("plugins", jSONArray);
            bVar.a(jSONObject2.toString());
            String str5 = TAG;
            Log.d(str5, "handleCheckPluginAvailable()::actionResponse = " + jSONObject2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleCheckPluginAvailable(Context context, Bundle bundle, b bVar) {
        String str;
        boolean z;
        syncRulesIfNecessary();
        try {
            HashMap hashMap = (HashMap) bundle.getSerializable(a.PARAMS);
            String str2 = "";
            String str3 = str2;
            for (String str4 : hashMap.keySet()) {
                if (str4.equals("target_device")) {
                    str2 = (String) ((List) hashMap.get(str4)).get(0);
                } else if (str4.equals("required_ver")) {
                    str3 = (String) ((List) hashMap.get(str4)).get(0);
                }
            }
            String str5 = TAG;
            Log.d(str5, "handleCheckPluginAvailable()::target_device = " + str2 + ", required_ver = " + str3);
            int intValue = Integer.valueOf(str3).intValue();
            String pluginPackageName = getPluginPackageName(str2);
            if (pluginPackageName == null) {
                str = "not_available_device";
                z = false;
            } else {
                z = HostManagerUtils.isExistPackage(context, pluginPackageName);
                str = z ? "no_issue" : "not_installed";
            }
            if (z && intValue > HostManagerUtils.getVersionCode(context, pluginPackageName)) {
                str = "not_supported_version";
                z = false;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("result", z ? "success" : "failure");
            jSONObject.put("target_device", str2);
            jSONObject.put("more_info", str);
            bVar.a(jSONObject.toString());
            String str6 = TAG;
            Log.d(str6, "handleCheckPluginAvailable()::actionResponse = " + jSONObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncRulesIfNecessary() {
        if (!GearRulesManager.getInstance().isDeviceInfoAvailable()) {
            Log.e(TAG, "deviceInfo is not available, need to parse xml");
            GearRulesManager.getInstance().syncGearInfoSynchronously();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0046  */
    @Override // c.b.a.a.a.a.a
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void executeAction(android.content.Context r4, java.lang.String r5, android.os.Bundle r6, c.b.a.a.a.a.b r7) {
        /*
            r3 = this;
            int r0 = r5.hashCode()
            r1 = -1252465366(0xffffffffb558e52a, float:-8.079975E-7)
            r2 = 1
            if (r0 == r1) goto L_0x001a
            r1 = -1206493750(0xffffffffb8165dca, float:-3.5850135E-5)
            if (r0 == r1) goto L_0x0010
            goto L_0x0024
        L_0x0010:
            java.lang.String r0 = "CanUsePlugin"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0024
            r5 = 0
            goto L_0x0025
        L_0x001a:
            java.lang.String r0 = "GetAvailableDevices"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0024
            r5 = 1
            goto L_0x0025
        L_0x0024:
            r5 = -1
        L_0x0025:
            java.lang.String r0 = "params = "
            if (r5 == 0) goto L_0x0046
            if (r5 == r2) goto L_0x002c
            goto L_0x005f
        L_0x002c:
            java.lang.String r5 = com.samsung.android.app.twatchmanager.bixby.BixbyActionHandler.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            r1.append(r6)
            java.lang.String r0 = r1.toString()
            com.samsung.android.app.twatchmanager.log.Log.d(r5, r0)
            if (r7 == 0) goto L_0x005f
            r3.handleCheckAvailableDevices(r4, r6, r7)
            goto L_0x005f
        L_0x0046:
            java.lang.String r5 = com.samsung.android.app.twatchmanager.bixby.BixbyActionHandler.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            r1.append(r6)
            java.lang.String r0 = r1.toString()
            com.samsung.android.app.twatchmanager.log.Log.d(r5, r0)
            if (r7 == 0) goto L_0x005f
            r3.handleCheckPluginAvailable(r4, r6, r7)
        L_0x005f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.bixby.BixbyActionHandler.executeAction(android.content.Context, java.lang.String, android.os.Bundle, c.b.a.a.a.a.b):void");
    }
}
