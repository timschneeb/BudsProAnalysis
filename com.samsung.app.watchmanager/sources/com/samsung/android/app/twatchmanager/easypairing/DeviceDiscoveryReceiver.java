package com.samsung.android.app.twatchmanager.easypairing;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.samsung.android.app.twatchmanager.btutil.OldFormatConverter;
import com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager;
import com.samsung.android.app.twatchmanager.easypairing.util.OldFormat;
import com.samsung.android.app.twatchmanager.factory.BluetoothDeviceFactory;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class DeviceDiscoveryReceiver extends BroadcastReceiver {
    private static final String ACTION_LE_DISCOVERY_FINISHED = "com.samsung.android.app.twatchmanager.easypairing.ACTION_LE_DISCOVERY_FINISHED";
    private static final String DISCOVERY_LE_SCAN_TIME_OUT = "discovery_le_scan_time_out";
    private static final String EASYPAIR_LE_SCAN_RSSI = "easypair_le_scan_rssi";
    private static final String EASYPAIR_LE_SCAN_TIME_OUT = "easypair_le_scan_time_out";
    private static final String EASYPAIR_SCAN_RSSI = "easypair_scan_rssi";
    private static final String EASYPAIR_SCAN_TIME_OUT = "easypair_scan_time_out";
    private static final String PREF_DISCOVERY_SCAN_INFO = "pref_discovery_scan_info";
    private static final String PREF_EASYPAIR_SCAN_INFO = "pref_easypair_scan_info";
    private static final int STOP_DISCOVERY_LE_SCAN_TIMEOUT = 3000;
    private static final int STOP_LE_SCAN_TIMEOUT = 2000;
    private static final int STOP_SCAN_TIMEOUT = 3000;
    private static String TAG = "tUHM:DeviceDiscoveryReceiver";
    private static final int THRESHOLD_LE_RSSI = -70;
    private static final int THRESHOLD_RSSI = -70;
    private final Handler EventHandler = new Handler() {
        /* class com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryReceiver.AnonymousClass1 */

        public void handleMessage(Message message) {
            String str;
            String str2;
            Log.d(DeviceDiscoveryReceiver.TAG, "EventHandler: Received intent");
            Intent intent = (Intent) message.obj;
            if (intent == null) {
                str = DeviceDiscoveryReceiver.TAG;
                str2 = "EventHandler: Intent is null";
            } else {
                String action = intent.getAction();
                if (action == null) {
                    str = DeviceDiscoveryReceiver.TAG;
                    str2 = "EventHandler: Intent action is null";
                } else {
                    String str3 = DeviceDiscoveryReceiver.TAG;
                    Log.d(str3, "EventHandler: intent msg : " + action);
                    if (action.equals("android.bluetooth.device.action.FOUND")) {
                        DeviceDiscoveryReceiver.this.bluetoothDeviceActionFound(intent);
                        return;
                    } else if (action.equals("android.bluetooth.adapter.action.DISCOVERY_FINISHED")) {
                        DeviceDiscoveryReceiver.this.bluetoothAdapterActionDiscoveryFinished(intent);
                        return;
                    } else if (action.equals("android.bluetooth.device.action.BOND_STATE_CHANGED")) {
                        DeviceDiscoveryReceiver.this.bluetoothDeviceActionBondStateChanged(intent);
                        return;
                    } else if (action.equals(GlobalConst.ACTION_STATE_CHANGED)) {
                        DeviceDiscoveryReceiver.this.bluetoothAdapterActionStateChanged(intent);
                        return;
                    } else if (action.equals(DeviceDiscoveryReceiver.ACTION_LE_DISCOVERY_FINISHED)) {
                        DeviceDiscoveryReceiver.this.bluetoothLeActionDiscoveryFinished(intent);
                        return;
                    } else {
                        return;
                    }
                }
            }
            Log.w(str, str2);
        }
    };
    private boolean isBleScan = false;
    private boolean isPairingBtOn = false;
    private boolean isScanStarted = false;
    private boolean isScanTurnedOnBT = false;
    private List<String> mAppDeviceList = null;
    private BluetoothAdapter mBtAdapter;
    private OldFormatConverter mBtConverter;
    private String mCallerActivityType = null;
    private Context mContext = null;
    private DeviceDiscoveryManager mDeviceDiscoveryManager = null;
    private int mLastAddedDeviceRSSI = -70;
    private LeEventHandler mLeEventHandler = null;
    private LeScanEventHandler mLeScanEventHanlder = null;
    private Set<BluetoothDevice> mLeScannedDeviceList = null;
    private DeviceDiscoveryManager.ResponseListener mResponseListener = null;
    private ArrayList<BluetoothDiscoveryUtility.BluetoothDeviceItem> mScannedACDevicesArrayList = new ArrayList<>();
    private Set<BluetoothDevice> mScannedDeviceList = null;
    private String pairAddress = null;

    @TargetApi(18)
    class LeEventHandler {
        public BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
            /* class com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryReceiver.LeEventHandler.AnonymousClass1 */

            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                String str;
                String str2;
                if (bluetoothDevice == null || DeviceDiscoveryReceiver.this.mContext == null) {
                    str = DeviceDiscoveryReceiver.TAG;
                    str2 = "onLeScan: device/mContext is null, return";
                } else {
                    Log.d(DeviceDiscoveryReceiver.TAG, "onLeScan: Device Found - " + bluetoothDevice.getName() + " with Address - " + bluetoothDevice.getAddress());
                    if (!HostManagerUtilsRulesBTDevices.isShowingCondition(bluetoothDevice, false)) {
                        str = DeviceDiscoveryReceiver.TAG;
                        str2 = "onLeScan: Device not supported. Returning";
                    } else {
                        OldFormat oldFormat = new OldFormat(bArr);
                        String str3 = bluetoothDevice.getName() + " - " + bluetoothDevice.getAddress();
                        Log.d(DeviceDiscoveryReceiver.TAG, "onLeScan : Device found - " + DeviceDiscoveryReceiver.this.mCallerActivityType + " - " + str3 + " : " + i);
                        Log.d(DeviceDiscoveryReceiver.TAG, oldFormat.toString());
                        if (!DeviceDiscoveryReceiver.this.mCallerActivityType.equals(GlobalConst.DISCOVERY_MANAGER)) {
                            int leThresholdRssi = DeviceDiscoveryReceiver.this.getLeThresholdRssi();
                            if (oldFormat.isSetupMode() && i >= leThresholdRssi && i > DeviceDiscoveryReceiver.this.mLastAddedDeviceRSSI) {
                                Log.w(DeviceDiscoveryReceiver.TAG, "add device : " + str3 + "(" + i + ")");
                                DeviceDiscoveryReceiver.this.addSetupModeDevice(bluetoothDevice);
                                return;
                            }
                            return;
                        } else if (oldFormat.isAutoConnectionMode()) {
                            Log.d(DeviceDiscoveryReceiver.TAG, "add device : " + str3);
                            DeviceDiscoveryReceiver.this.addACModeDevice(bluetoothDevice);
                            return;
                        } else {
                            return;
                        }
                    }
                }
                Log.d(str, str2);
            }
        };

        LeEventHandler() {
        }
    }

    @TargetApi(21)
    class LeScanEventHandler {
        public ScanCallback mScanCallback = new ScanCallback() {
            /* class com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryReceiver.LeScanEventHandler.AnonymousClass1 */

            @Override // android.bluetooth.le.ScanCallback
            public void onBatchScanResults(List<ScanResult> list) {
                Log.d(DeviceDiscoveryReceiver.TAG, "onBatchResults: ...");
            }

            public void onScanFailed(int i) {
                Log.d(DeviceDiscoveryReceiver.TAG, "onScanFailed: ...");
                if (DeviceDiscoveryReceiver.this.mResponseListener != null) {
                    DeviceDiscoveryReceiver.this.mResponseListener.onError(1);
                }
            }

            public void onScanResult(int i, ScanResult scanResult) {
                String str;
                String str2;
                Log.d(DeviceDiscoveryReceiver.TAG, "onScanResult: ...");
                BluetoothDevice device = scanResult.getDevice();
                if (device == null || DeviceDiscoveryReceiver.this.mContext == null) {
                    str = DeviceDiscoveryReceiver.TAG;
                    str2 = "device/mContext is null, return";
                } else {
                    Log.d(DeviceDiscoveryReceiver.TAG, "Device Found - " + device.getName() + " with Address - " + device.getAddress());
                    if (!HostManagerUtilsRulesBTDevices.isShowingCondition(device, false)) {
                        str = DeviceDiscoveryReceiver.TAG;
                        str2 = "onScanResult - Device not supported. Returning";
                    } else {
                        String str3 = device.getName() + " - " + device.getAddress();
                        int rssi = scanResult.getRssi();
                        Log.d(DeviceDiscoveryReceiver.TAG, "Device found: " + DeviceDiscoveryReceiver.this.mCallerActivityType + " - " + str3 + " : " + rssi);
                        OldFormat oldFormat = new OldFormat(scanResult);
                        Log.d(DeviceDiscoveryReceiver.TAG, oldFormat.toString());
                        if (!DeviceDiscoveryReceiver.this.mCallerActivityType.equals(GlobalConst.DISCOVERY_MANAGER)) {
                            int leThresholdRssi = DeviceDiscoveryReceiver.this.getLeThresholdRssi();
                            if (oldFormat.isSetupMode() && rssi >= leThresholdRssi && rssi > DeviceDiscoveryReceiver.this.mLastAddedDeviceRSSI) {
                                Log.w(DeviceDiscoveryReceiver.TAG, "add device : " + str3 + "(" + rssi + ")");
                                DeviceDiscoveryReceiver.this.mLastAddedDeviceRSSI = rssi;
                                DeviceDiscoveryReceiver.this.addSetupModeDevice(device);
                                return;
                            }
                            return;
                        } else if (oldFormat.isAutoConnectionMode()) {
                            Log.d(DeviceDiscoveryReceiver.TAG, "add device : " + str3);
                            DeviceDiscoveryReceiver.this.addACModeDevice(device);
                            return;
                        } else {
                            return;
                        }
                    }
                }
                Log.d(str, str2);
            }
        };

        LeScanEventHandler() {
        }
    }

    public DeviceDiscoveryReceiver(Activity activity, DeviceDiscoveryManager deviceDiscoveryManager, DeviceDiscoveryManager.ResponseListener responseListener, String str) {
        this.mDeviceDiscoveryManager = deviceDiscoveryManager;
        this.mResponseListener = responseListener;
        this.mCallerActivityType = str;
        if (this.mBtAdapter == null) {
            this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        int i = Build.VERSION.SDK_INT;
        if (i >= 21) {
            this.mLeScanEventHanlder = new LeScanEventHandler();
        } else if (i >= 18) {
            this.mLeEventHandler = new LeEventHandler();
        } else {
            Log.d(TAG, "DeviceDiscoveryReceiver: Version is API level 17 or below, Le scan not supported");
        }
        this.mScannedDeviceList = new HashSet();
        this.mLeScannedDeviceList = new HashSet();
        this.mContext = activity.getApplicationContext();
        savePreferences(this.mCallerActivityType);
        registerReceiver();
        syncRulesIfNecessary();
        this.mBtConverter = new OldFormatConverter(GearRulesManager.getInstance());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void addACModeDevice(BluetoothDevice bluetoothDevice) {
        String name = bluetoothDevice.getName();
        String str = TAG;
        Log.d(str, "addACModeDevice : " + name);
        BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem = new BluetoothDiscoveryUtility.BluetoothDeviceItem(BluetoothDeviceFactory.get().getAliasName(bluetoothDevice), bluetoothDevice.getName(), bluetoothDevice.getAddress(), 0);
        Set<BluetoothDevice> bondedDevices = this.mBtAdapter.getBondedDevices();
        Log.d(TAG, "addACModeDevice : send device list to ResponseListenerForDiscovery");
        if (!TextUtils.isEmpty(bluetoothDeviceItem.getName()) && bluetoothDeviceItem.getName().toLowerCase().contains(HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(name).toLowerCase()) && !containsAddress(bondedDevices, bluetoothDevice.getAddress()) && !containsAddress(this.mScannedACDevicesArrayList, bluetoothDevice.getAddress())) {
            String str2 = TAG;
            Log.w(str2, "added in list : " + name);
            this.mScannedACDevicesArrayList.add(bluetoothDeviceItem);
            this.mResponseListener.onLEScanDeviceForDiscovery(bluetoothDeviceItem, 1);
        }
    }

    private void addLeScannedDevice(BluetoothDevice bluetoothDevice) {
        if (this.mBtConverter.isLEdevice(bluetoothDevice)) {
            bluetoothDevice = this.mBtConverter.getBRdevice(bluetoothDevice.getAddress());
            String str = TAG;
            Log.d(str, "change device - " + bluetoothDevice.getName());
        }
        if (!containsAddress(this.mLeScannedDeviceList, bluetoothDevice.getAddress())) {
            String str2 = TAG;
            Log.w(str2, "addLeScannedDevice - add to list : " + bluetoothDevice.getName());
            this.mLeScannedDeviceList.clear();
            this.mLeScannedDeviceList.add(bluetoothDevice);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void addSetupModeDevice(BluetoothDevice bluetoothDevice) {
        String name = bluetoothDevice.getName();
        String str = TAG;
        Log.d(str, "addSetupModeDevice : " + name);
        if (this.mLeScannedDeviceList == null) {
            return;
        }
        if (this.mAppDeviceList != null) {
            for (int i = 0; i < this.mAppDeviceList.size(); i++) {
                String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(name);
                if (!TextUtils.isEmpty(simpleBTNameByName) && simpleBTNameByName.toLowerCase(Locale.ENGLISH).equals(this.mAppDeviceList.get(i).toLowerCase(Locale.ENGLISH))) {
                    String str2 = TAG;
                    Log.d(str2, "addSetupModeDevice : check & add : " + name);
                    addLeScannedDevice(bluetoothDevice);
                }
            }
            return;
        }
        Log.d(TAG, "mAppDeviceList is null. without filtering of device, set mLeScannedDeviceList. ");
        addLeScannedDevice(bluetoothDevice);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void bluetoothAdapterActionDiscoveryFinished(Intent intent) {
        Log.d(TAG, "action : BluetoothAdapter.ACTION_DISCOVERY_FINISHED");
        if (this.mScannedDeviceList != null) {
            if (this.mResponseListener != null) {
                Log.d(TAG, "bluetoothAdapterActionDiscoveryFinished: scan finished successfully");
                if (this.isScanStarted) {
                    this.mResponseListener.onScanFinished(this.mScannedDeviceList, 0);
                } else {
                    Log.d(TAG, "bluetoothAdapterActionDiscoveryFinished: scan is not triggered by easy pairing CM");
                }
            }
            this.mScannedDeviceList.clear();
        } else if (this.mResponseListener != null) {
            Log.d(TAG, "bluetoothAdapterActionDiscoveryFinished: can finished un-successfully");
            this.mResponseListener.onError(1);
        }
        this.isScanStarted = false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void bluetoothAdapterActionStateChanged(Intent intent) {
        int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", LinearLayoutManager.INVALID_OFFSET);
        if (intExtra == 12) {
            Log.d(TAG, "bluetoothAdapterActionStateChanged; Bluetooth state ON");
            if (true == this.isScanTurnedOnBT) {
                if (true == getIsBleScan()) {
                    Log.d(TAG, "bluetoothAdapterActionStateChanged: start Le scan");
                    this.mDeviceDiscoveryManager.startLeScan();
                } else {
                    Log.d(TAG, "bluetoothAdapterActionStateChanged: start scan");
                    this.mDeviceDiscoveryManager.startScan();
                }
                this.isScanTurnedOnBT = false;
            }
            if (true == this.isPairingBtOn) {
                Log.d(TAG, "bluetoothAdapterActionStateChanged: create bond");
                String str = this.pairAddress;
                if (str != null) {
                    this.mDeviceDiscoveryManager.createBond(str);
                }
                this.isPairingBtOn = false;
                this.pairAddress = null;
            }
        } else if (intExtra == 10) {
            Log.d(TAG, "bluetoothAdapterActionStateChanged; Bluetooth state OFF");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void bluetoothDeviceActionBondStateChanged(Intent intent) {
        int intExtra = intent.getIntExtra(GlobalConst.EXTRA_BOND_STATE, LinearLayoutManager.INVALID_OFFSET);
        BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        if (bluetoothDevice == null) {
            Log.d(TAG, "bluetoothDeviceActionBondStateChanged: device is null, return");
            return;
        }
        String str = TAG;
        Log.d(str, "bluetoothDeviceActionBondStateChanged: Device: " + bluetoothDevice.getAddress() + " bond state changed: " + intExtra);
        DeviceDiscoveryManager.ResponseListener responseListener = this.mResponseListener;
        if (responseListener != null) {
            responseListener.onBondStateChanged(bluetoothDevice, intExtra);
        } else {
            Log.d(TAG, "bluetoothDeviceActionBondStateChanged: mResponseListener is null ");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void bluetoothDeviceActionFound(Intent intent) {
        String str;
        String str2;
        Log.d(TAG, "action : BluetoothDevice.ACTION_FOUND");
        BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        if (bluetoothDevice == null || this.mContext == null) {
            str = TAG;
            str2 = "bluetoothDeviceActionFound: device/mContext is null, return";
        } else {
            Log.d(TAG, "bluetoothDeviceActionFound: Device Found - " + bluetoothDevice.getName() + " with Address - " + bluetoothDevice.getAddress());
            if (!HostManagerUtilsRulesBTDevices.isShowingCondition(bluetoothDevice, true)) {
                str = TAG;
                str2 = "Device not supported. Returning";
            } else {
                String str3 = bluetoothDevice.getName() + " - " + bluetoothDevice.getAddress();
                int thresholdRssi = getThresholdRssi();
                short shortExtra = intent.getShortExtra("android.bluetooth.device.extra.RSSI", Short.MIN_VALUE);
                Log.d(TAG, "bluetoothDeviceActionFound: Device found: " + str3 + " with RSSI: " + ((int) shortExtra));
                if (shortExtra >= thresholdRssi) {
                    Set<BluetoothDevice> set = this.mScannedDeviceList;
                    if (set != null && set.isEmpty()) {
                        Log.d(TAG, "bluetoothDeviceActionFound: Adding Device(BT) to HashMap: " + str3 + " with RSSI: " + ((int) shortExtra) + ", threshold rssi is: " + thresholdRssi);
                        this.mScannedDeviceList.add(bluetoothDevice);
                        if (GlobalConst.EASYPAIRING_MANAGER.equals(this.mCallerActivityType) && this.mDeviceDiscoveryManager.getmHandler() != null) {
                            Log.d(TAG, "bluetoothDeviceActionFound: First Gear device found, stop scan");
                            this.mDeviceDiscoveryManager.getmHandler().removeCallbacks(this.mDeviceDiscoveryManager.getmTask());
                            stopBluetoothScan();
                            return;
                        }
                        return;
                    }
                    return;
                }
                Log.d(TAG, "bluetoothDeviceActionFound: Device(BT) not added to HashMap: " + str3 + " with RSSI: " + ((int) shortExtra) + ", threshold rssi is: " + thresholdRssi);
                return;
            }
        }
        Log.d(str, str2);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void bluetoothLeActionDiscoveryFinished(Intent intent) {
        DeviceDiscoveryManager.ResponseListener responseListener;
        int i;
        Set<BluetoothDevice> set;
        Log.d(TAG, "bluetoothLeActionDiscoveryFinished()");
        if (this.mLeScannedDeviceList != null) {
            if (this.mResponseListener != null) {
                Log.d(TAG, "bluetoothLeActionDiscoveryFinished: Le scan finished successfully");
                responseListener = this.mResponseListener;
                set = this.mLeScannedDeviceList;
                i = 0;
            } else {
                return;
            }
        } else if (this.mResponseListener != null) {
            Log.d(TAG, "bluetoothLeActionDiscoveryFinished: scan finished un-successfully");
            responseListener = this.mResponseListener;
            set = this.mLeScannedDeviceList;
            i = 1;
        } else {
            return;
        }
        responseListener.onLeScanFinished(set, i);
    }

    private boolean clearSharedPreferences() {
        Log.d(TAG, "clearSharedPreferences...");
        Context context = this.mContext;
        if (context == null) {
            Log.d(TAG, "clearSharedPreferences: mContext is null, return");
            return false;
        }
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF_EASYPAIR_SCAN_INFO, 0).edit();
        edit.clear();
        edit.apply();
        return true;
    }

    private boolean containsAddress(Set<BluetoothDevice> set, String str) {
        if (set == null) {
            return false;
        }
        for (BluetoothDevice bluetoothDevice : set) {
            if (str.equals(bluetoothDevice.getAddress())) {
                Log.d(TAG, "Address found in the list");
                return true;
            }
        }
        return false;
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.device.action.FOUND");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        intentFilter.addAction(GlobalConst.ACTION_STATE_CHANGED);
        Context context = this.mContext;
        if (context != null) {
            context.registerReceiver(this, intentFilter);
        }
    }

    private boolean savePreferences(String str) {
        SharedPreferences.Editor edit;
        Log.d(TAG, "savePreferences: save timeout & threshold rssi values");
        if (this.mContext == null) {
            Log.d(TAG, "savePreferences: mContext is null, return");
            return false;
        }
        if (GlobalConst.EASYPAIRING_MANAGER.equals(str)) {
            edit = this.mContext.getSharedPreferences(PREF_EASYPAIR_SCAN_INFO, 0).edit();
            edit.putInt(EASYPAIR_SCAN_TIME_OUT, 3000);
            edit.putInt(EASYPAIR_SCAN_RSSI, -70);
            edit.putInt(EASYPAIR_LE_SCAN_TIME_OUT, 2000);
            edit.putInt(EASYPAIR_LE_SCAN_RSSI, -70);
        } else if (!GlobalConst.DISCOVERY_MANAGER.equals(str)) {
            return true;
        } else {
            edit = this.mContext.getSharedPreferences(PREF_DISCOVERY_SCAN_INFO, 0).edit();
            edit.putInt(DISCOVERY_LE_SCAN_TIME_OUT, 3000);
        }
        edit.apply();
        return true;
    }

    private boolean stopBluetoothScan() {
        DeviceDiscoveryManager deviceDiscoveryManager = this.mDeviceDiscoveryManager;
        if (deviceDiscoveryManager != null) {
            return deviceDiscoveryManager.stopBluetoothScan();
        }
        return false;
    }

    private boolean stopLeScan() {
        if (this.mDeviceDiscoveryManager == null) {
            return false;
        }
        setIsBleScan(false);
        return this.mDeviceDiscoveryManager.stopLeScan();
    }

    private void syncRulesIfNecessary() {
        if (!GearRulesManager.getInstance().isDeviceInfoAvailable()) {
            Log.e(TAG, "deviceInfo is not available, need to parse xml");
            GearRulesManager.getInstance().syncGearInfoSynchronously();
        }
    }

    private void unregisterReceiver() {
        Context context = this.mContext;
        if (context != null) {
            context.unregisterReceiver(this);
        }
    }

    public boolean containsAddress(List<BluetoothDiscoveryUtility.BluetoothDeviceItem> list, String str) {
        if (list == null) {
            return false;
        }
        for (BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem : list) {
            if (str.equals(bluetoothDeviceItem.getAddress())) {
                Log.d(TAG, "Address found in the list");
                return true;
            }
        }
        return false;
    }

    public boolean getIsBleScan() {
        return this.isBleScan;
    }

    public BluetoothAdapter.LeScanCallback getLeScanCallback() {
        LeEventHandler leEventHandler = this.mLeEventHandler;
        if (leEventHandler != null) {
            return leEventHandler.mLeScanCallback;
        }
        return null;
    }

    public int getLeThresholdRssi() {
        Context context = this.mContext;
        if (context != null) {
            return context.getSharedPreferences(PREF_EASYPAIR_SCAN_INFO, 0).getInt(EASYPAIR_LE_SCAN_RSSI, -70);
        }
        return -70;
    }

    public ScanCallback getScanCallback() {
        LeScanEventHandler leScanEventHandler = this.mLeScanEventHanlder;
        if (leScanEventHandler != null) {
            return leScanEventHandler.mScanCallback;
        }
        return null;
    }

    public int getThresholdRssi() {
        Context context = this.mContext;
        if (context == null) {
            return -70;
        }
        int i = context.getSharedPreferences(PREF_EASYPAIR_SCAN_INFO, 0).getInt(EASYPAIR_SCAN_RSSI, -70);
        String str = TAG;
        Log.d(str, "getThresholdRssi:: threshold rssi: " + i);
        return i;
    }

    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "EventHandler : onReceive()..");
        if (intent == null) {
            Log.d(TAG, "intent is null return;");
            return;
        }
        Message obtain = Message.obtain();
        obtain.obj = intent;
        this.EventHandler.sendMessage(obtain);
    }

    public void refreshLEDeviceAddressList() {
        Log.d(TAG, "refreshLEDeviceAddressList()");
        ArrayList<BluetoothDiscoveryUtility.BluetoothDeviceItem> arrayList = this.mScannedACDevicesArrayList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    public void sendActionLeDiscoveryFinishedIntent() {
        Intent intent = new Intent();
        intent.setAction(ACTION_LE_DISCOVERY_FINISHED);
        Message obtain = Message.obtain();
        if (obtain != null) {
            obtain.obj = intent;
        }
        Handler handler = this.EventHandler;
        if (handler != null) {
            handler.sendMessage(obtain);
        }
    }

    public void setIsBleScan(boolean z) {
        this.isBleScan = z;
    }

    public void setLEScanDeviceName(List<String> list) {
        this.mAppDeviceList = list;
    }

    public void setPairAddress(String str) {
        this.pairAddress = str;
    }

    public void setPairingBtOn(boolean z) {
        this.isPairingBtOn = z;
    }

    public void setScanStarted(boolean z) {
        this.isScanStarted = z;
    }

    public void setScanTurnedOnBT(boolean z) {
        this.isScanTurnedOnBT = z;
    }

    public void terminate() {
        Log.d(TAG, " terminate ");
        unregisterReceiver();
        clearSharedPreferences();
        Set<BluetoothDevice> set = this.mScannedDeviceList;
        if (set != null) {
            set.clear();
            this.mScannedDeviceList = null;
        }
        Set<BluetoothDevice> set2 = this.mLeScannedDeviceList;
        if (set2 != null) {
            set2.clear();
            this.mLeScannedDeviceList = null;
        }
        this.isScanTurnedOnBT = false;
        this.isScanStarted = false;
        this.isBleScan = false;
        this.isPairingBtOn = false;
        this.pairAddress = null;
        this.mContext = null;
        this.mResponseListener = null;
        this.mLeEventHandler = null;
        this.mLeScanEventHanlder = null;
        this.mDeviceDiscoveryManager = null;
    }
}
