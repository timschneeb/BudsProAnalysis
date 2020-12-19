package com.samsung.android.app.twatchmanager.manager;

import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.samsung.android.app.twatchmanager.btutil.ManufacturerData;
import com.samsung.android.app.twatchmanager.btutil.OldFormatConverter;
import com.samsung.android.app.twatchmanager.btutil.SamsungFormatConverter;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.GearInfo;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsDBOperations;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.StringResourceManagerUtil;
import com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity;

public class ApplicationLaunchManager {
    public static final String TAG = ("tUHM:" + ApplicationLaunchManager.class.getSimpleName());

    private static class LazyHolder {
        private static final ApplicationLaunchManager INSTANCE = new ApplicationLaunchManager();

        private LazyHolder() {
        }
    }

    private ApplicationLaunchManager() {
    }

    public static synchronized ApplicationLaunchManager getInstance() {
        ApplicationLaunchManager applicationLaunchManager;
        synchronized (ApplicationLaunchManager.class) {
            applicationLaunchManager = LazyHolder.INSTANCE;
        }
        return applicationLaunchManager;
    }

    private String getValidDeviceName(String str, String str2) {
        String str3 = TAG;
        Log.i(str3, "getValidDeviceName()::deviceName = " + str2);
        BluetoothDevice remoteDevice = OldFormatConverter.getRemoteDevice(str);
        if (remoteDevice == null) {
            Log.e(TAG, "getValidDeviceName() : device is null");
            return str2;
        }
        int i = 0;
        try {
            i = remoteDevice.getType();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        String str4 = TAG;
        Log.e(str4, "getValidDeviceName() : type = " + i);
        if (i == 2) {
            Log.e(TAG, "getValidDeviceName() : type of device is LE");
            ManufacturerData manufacturerData = SamsungFormatConverter.getManufacturerData(remoteDevice);
            if (manufacturerData == null || !manufacturerData.isSSManufacturerType()) {
                Log.w(TAG, "getValidDeviceName() : OLD_FORMAT");
                str = new OldFormatConverter(GearRulesManager.getInstance()).getBrDeviceFromStub(str, str2).getAddress();
            } else if (manufacturerData.haveBRDevice()) {
                Log.w(TAG, "getValidDeviceName() : SAMSUNG_FORMAT");
                str = SamsungFormatConverter.convertLEtoBR(str, manufacturerData.getBTMacAddress());
            } else {
                Log.w(TAG, "getValidDeviceName() : LE only device.");
            }
        } else {
            Log.e(TAG, "getValidDeviceName() : type of device is BR");
        }
        return HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(str);
    }

    private void startApplication(Context context, Intent intent) {
        ComponentName componentName = new ComponentName("com.samsung.android.app.watchmanager", SetupWizardWelcomeActivity.class.getName());
        intent.setFlags(268435456);
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setComponent(componentName);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            String str = TAG;
            Log.e(str, "startActivity exception" + e);
        }
    }

    private void syncRulesManager() {
        if (!GearRulesManager.getInstance().isDeviceInfoAvailable()) {
            GearRulesManager.getInstance().syncGearInfoSynchronously();
        }
    }

    public synchronized void startGearManagerFromStub(Context context, Intent intent) {
        syncRulesManager();
        String stringExtra = intent.getStringExtra("BT_ADD_FROM_STUB");
        String validDeviceName = getValidDeviceName(stringExtra, intent.getStringExtra("MODEL_NAME"));
        GearInfo gearInfo = GearRulesManager.getInstance().getGearInfo(validDeviceName);
        if (gearInfo == null) {
            String str = TAG;
            Log.e(str, "startGearManagerFromStub()::modelName = " + validDeviceName);
        } else if (!StringResourceManagerUtil.EARBUD_TYPE.equals(gearInfo.group.wearableType) || !HostManagerUtils.isExistPackage(context, gearInfo.pluginPackage) || !HostManagerUtilsDBOperations.isExistAddress(context, stringExtra)) {
            startApplication(context, intent);
        } else {
            String str2 = TAG;
            Log.e(str2, "startGearManagerFromStub()::[" + gearInfo.pluginPackage + "] is already exist(" + validDeviceName + ")");
        }
    }
}
