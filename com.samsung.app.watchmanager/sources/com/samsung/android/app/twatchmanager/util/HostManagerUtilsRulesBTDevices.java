package com.samsung.android.app.twatchmanager.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.btutil.OldFormatConverter;
import com.samsung.android.app.twatchmanager.factory.BluetoothDeviceFactory;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.model.GearInfo;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import java.util.HashMap;

public class HostManagerUtilsRulesBTDevices {
    private static final ParcelUuid Handsfree = ParcelUuid.fromString("0000111E-0000-1000-8000-00805F9B34FB");
    private static final String TAG = ("tUHM:" + HostManagerUtilsRulesBTDevices.class.getSimpleName());
    private static HashMap<String, String> deviceNameFromStub = new HashMap<>();

    public static BluetoothDiscoveryUtility.BluetoothDeviceItem getBRDevice(BluetoothDevice bluetoothDevice) {
        String str;
        OldFormatConverter oldFormatConverter = new OldFormatConverter(GearRulesManager.getInstance());
        if (oldFormatConverter.isLEdevice(bluetoothDevice) && !supportsBLEOnly(getSimpleBTNameByName(bluetoothDevice.getName()))) {
            bluetoothDevice = oldFormatConverter.getBRdevice(bluetoothDevice.getAddress());
        }
        String name = bluetoothDevice.getName();
        if (name == null || name.equalsIgnoreCase("")) {
            String str2 = TAG;
            Log.d(str2, "change device - " + name);
            name = getOriginalBTName(bluetoothDevice.getAddress());
            str = name;
        } else {
            str = BluetoothDeviceFactory.get().getAliasName(bluetoothDevice);
        }
        String str3 = TAG;
        Log.d(str3, "name and btAliasName- " + name + " " + str);
        return new BluetoothDiscoveryUtility.BluetoothDeviceItem(str, name, bluetoothDevice.getAddress(), 0);
    }

    public static BluetoothDevice getBluetoothDevice(String str) {
        String str2;
        String str3;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            str2 = TAG;
            str3 = "Bluetooth adapter is null";
        } else if (str != null && BluetoothAdapter.checkBluetoothAddress(str)) {
            return defaultAdapter.getRemoteDevice(str);
        } else {
            str2 = TAG;
            str3 = "Address is not correctly";
        }
        Log.d(str2, str3);
        return null;
    }

    public static String getOriginalBTName(String str) {
        if (str == null) {
            String str2 = TAG;
            Log.d(str2, "getOriginalBTName() BTAddress = " + str);
            return null;
        }
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null && defaultAdapter.isEnabled()) {
            try {
                String name = defaultAdapter.getRemoteDevice(str).getName();
                String str3 = TAG;
                Log.d(str3, "getOriginalBTName() getBTName from BT Adapter : " + name);
                if (name == null) {
                    name = OldFormatConverter.getDeviceNameFromBRaddr(str);
                }
                String str4 = TAG;
                Log.d(str4, "getOriginalBTName() getBTName from OldFormatConverter : " + name);
                if (name == null) {
                    name = deviceNameFromStub.get(str);
                }
                String str5 = TAG;
                Log.d(str5, "getOriginalBTName() getBTName from stub : " + name);
                return name;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "getOriginalBTName() can't get the BT name...");
        return null;
    }

    public static String getSimpleBTNameByAddress(String str) {
        String str2 = TAG;
        Log.d(str2, "getSimpleBTNameByAddress() BTAddress = " + str);
        String originalBTName = getOriginalBTName(str);
        if (!TextUtils.isEmpty(originalBTName)) {
            return getSimpleBTNameByName(originalBTName);
        }
        return null;
    }

    public static String getSimpleBTNameByName(String str) {
        if (str == null) {
            return null;
        }
        String trim = str.trim();
        int indexOf = str.indexOf("(");
        if (indexOf > 0) {
            trim = str.substring(0, indexOf).trim();
        }
        String str2 = TAG;
        Log.d(str2, "getSimpleBTNameByName() simpleName : " + trim);
        return trim;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0043, code lost:
        if (r6.hasService(2097152) != false) goto L_0x002b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isHFPdevice(android.bluetooth.BluetoothDevice r6) {
        /*
            r0 = 0
            if (r6 != 0) goto L_0x000b
            java.lang.String r6 = com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices.TAG
            java.lang.String r1 = "isHFPdevice device is null"
        L_0x0007:
            com.samsung.android.app.twatchmanager.log.Log.d(r6, r1)
            return r0
        L_0x000b:
            android.os.ParcelUuid[] r1 = r6.getUuids()
            r2 = 1
            if (r1 == 0) goto L_0x0030
            int r3 = r1.length
            if (r3 != 0) goto L_0x0016
            goto L_0x0030
        L_0x0016:
            java.lang.String r6 = com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices.TAG
            java.lang.String r3 = "isHFPdevice check uuids value"
            com.samsung.android.app.twatchmanager.log.Log.d(r6, r3)
            int r6 = r1.length
            r3 = 0
        L_0x001f:
            if (r3 >= r6) goto L_0x0046
            r4 = r1[r3]
            android.os.ParcelUuid r5 = com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices.Handsfree
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x002d
        L_0x002b:
            r0 = 1
            goto L_0x0046
        L_0x002d:
            int r3 = r3 + 1
            goto L_0x001f
        L_0x0030:
            java.lang.String r1 = com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices.TAG
            java.lang.String r3 = "isHFPdevice check COD value"
            com.samsung.android.app.twatchmanager.log.Log.d(r1, r3)
            android.bluetooth.BluetoothClass r6 = r6.getBluetoothClass()
            if (r6 == 0) goto L_0x0046
            r1 = 2097152(0x200000, float:2.938736E-39)
            boolean r6 = r6.hasService(r1)
            if (r6 == 0) goto L_0x0046
            goto L_0x002b
        L_0x0046:
            java.lang.String r6 = com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "isHFPdevice = "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            goto L_0x0007
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices.isHFPdevice(android.bluetooth.BluetoothDevice):boolean");
    }

    public static boolean isShowingCondition(BluetoothDevice bluetoothDevice, boolean z) {
        String str;
        String str2;
        if (bluetoothDevice == null) {
            str = TAG;
            str2 = "isShowingCondition() return false";
        } else if (!z || bluetoothDevice.getType() != 2 || supportsBLEOnly(getSimpleBTNameByName(bluetoothDevice.getName()))) {
            return isShowingCondition(bluetoothDevice.getName());
        } else {
            str = TAG;
            str2 = "isShowingCondition() - DEVICE_TYPE_LE : return false";
        }
        Log.d(str, str2);
        return false;
    }

    private static boolean isShowingCondition(String str) {
        String str2 = TAG;
        Log.d(str2, "isShowingCondition( " + str + " )");
        GearRulesManager instance = GearRulesManager.getInstance();
        if (!instance.isPossibleSamsungWearable(str)) {
            return false;
        }
        String simpleBTNameByName = getSimpleBTNameByName(str);
        String str3 = TAG;
        Log.d(str3, "isShowingCondition - deviceType " + simpleBTNameByName);
        if (instance.isSkipWearableDevice(simpleBTNameByName)) {
            return false;
        }
        String str4 = TAG;
        Log.d(str4, "value of mGearRulesManager:: " + instance + " for device type:: " + simpleBTNameByName);
        GearInfo gearInfo = instance.getGearInfo(simpleBTNameByName);
        String str5 = TAG;
        Log.d(str5, " gearINFO value:: " + gearInfo);
        if (gearInfo != null) {
            return isSupportedInHostDevice(gearInfo);
        }
        GearRulesManager.getInstance().syncGearInfo(new GearRulesManager.ISyncCallback() {
            /* class com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices.AnonymousClass1 */

            @Override // com.samsung.android.app.twatchmanager.manager.GearRulesManager.ISyncCallback
            public void onSyncComplete(boolean z) {
                Log.d(HostManagerUtilsRulesBTDevices.TAG, "sync is done...");
            }
        });
        Log.d(TAG, "Return false for this case. the device will be added once sync is done");
        return false;
    }

    private static boolean isSupportedInHostDevice(GearInfo gearInfo) {
        String str;
        StringBuilder sb;
        String str2;
        String str3 = TAG;
        Log.d(str3, "isSupportedInHostDevice() gearInfo:" + gearInfo);
        if (((double) gearInfo.hostMinMemory) >= HostManagerUtils.getDeviceMemorySize(TWatchManagerApplication.getAppContext())) {
            String str4 = TAG;
            Log.d(str4, "This model(Host) is under 1G ram, and device is gear. deviceName = " + gearInfo.deviceName);
            if (!HostManagerUtils.isGS3Model()) {
                return false;
            }
        } else {
            Log.d(TAG, "host has required memory");
        }
        if (HostManagerUtils.isTablet() && !gearInfo.supportTablet) {
            str = TAG;
            sb = new StringBuilder();
            str2 = "This is not available device at tablet. deviceName = ";
        } else if (!HostManagerUtils.isSamsungDevice() && !gearInfo.supportNonSamsung) {
            str = TAG;
            sb = new StringBuilder();
            str2 = "unsupported in non samsung device :";
        } else if (Build.VERSION.SDK_INT > gearInfo.group.maxAPILevel) {
            str = TAG;
            sb = new StringBuilder();
            sb.append("unsupported in API level :");
            sb.append(gearInfo.group.maxAPILevel);
            Log.d(str, sb.toString());
            return false;
        } else {
            Log.d(TAG, "isSupportedInHostDevice() return true");
            return true;
        }
        sb.append(str2);
        sb.append(gearInfo.deviceName);
        Log.d(str, sb.toString());
        return false;
    }

    public static void setDeviceNameFromStub(String str, String str2) {
        String str3 = TAG;
        Log.d(str3, "setDeviceNameFromStub() btAddress : " + str + " deviceName : " + str2);
        deviceNameFromStub.put(str, str2);
    }

    public static boolean supportsBLEOnly(String str) {
        String str2 = TAG;
        Log.d(str2, "supportsBLEOnly() for [" + str + "]");
        GearInfo gearInfo = GearRulesManager.getInstance().getGearInfo(str);
        String str3 = TAG;
        Log.d(str3, "supportsBLEOnly() gearInfo [" + gearInfo + "]");
        boolean z = gearInfo != null ? gearInfo.supportsBLEOnly : false;
        String str4 = TAG;
        Log.d(str4, "supportsBLEOnly() result [" + z + "]");
        return z;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v4, resolved type: boolean */
    /* JADX WARN: Multi-variable type inference failed */
    public static int supportsPairing(String str) {
        String str2 = TAG;
        Log.d(str2, "supportsPairing() for [" + str + "]");
        GearInfo gearInfo = GearRulesManager.getInstance().getGearInfo(str);
        String str3 = TAG;
        Log.d(str3, "supportsPairing() gearInfo [" + gearInfo + "]");
        int i = gearInfo != null ? gearInfo.requiresPairing : 1;
        String str4 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("supportsPairing() result [");
        int i2 = i == 1 ? 1 : 0;
        int i3 = i == 1 ? 1 : 0;
        int i4 = i == 1 ? 1 : 0;
        sb.append(i2);
        sb.append("]");
        Log.d(str4, sb.toString());
        return i;
    }
}
