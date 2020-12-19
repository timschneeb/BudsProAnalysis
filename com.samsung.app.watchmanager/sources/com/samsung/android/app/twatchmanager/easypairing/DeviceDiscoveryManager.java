package com.samsung.android.app.twatchmanager.easypairing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import com.samsung.android.app.twatchmanager.factory.BluetoothDeviceFactory;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.log.LoggerUtil;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeviceDiscoveryManager {
    private static final int ADAPTER_ENABLE_RETRY_COUNT = 5;
    private static final int ADAPTER_ENABLE_RETRY_TIME = 1000;
    private static final int DISCOVERY_LE_SCAN_TIMEOUT = 5000;
    private static final int EASY_PAIRING_LE_SCAN_TIMEOUT = 2000;
    private static final int MSG_BT_ADAPTER_ENABLE_RETRY = 101;
    public static final int STATUS_FAIL = 1;
    public static final int STATUS_SUCCESS = 0;
    private static String TAG = "tUHM:DeviceDiscoveryManager";
    public static final int TYPE_ALL_MODE = 2;
    private static final int TYPE_SETUP_MODE = 1;
    private Activity mActivity;
    private BluetoothLeScanner mBleScanner;
    private BluetoothAdapter mBtAdapter;
    private int mBtAdapterEnableRetryCount;
    private BluetoothManager mBtManager;
    private Context mContext;
    private DeviceDiscoveryReceiver mEasyPairingReceiver;
    private ResponseListener mEasyPairingResponseListener;
    private Handler mHandler;
    private DeviceDiscoveryReceiver mLEScanDiscoveryReceiver;
    private ResponseListener mLEScanResponseListener;
    private BluetoothAdapter.LeScanCallback mLeCallback;
    private ScanCallback mScanCallback;
    private int mSearchingType;
    private StopScanTask mTask;
    private Handler selfHandler;

    public interface ResponseListener {
        void onBondStateChanged(BluetoothDevice bluetoothDevice, int i);

        void onError(int i);

        void onLEScanDeviceForDiscovery(BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem, int i);

        void onLeScanFinished(Set<BluetoothDevice> set, int i);

        void onScanFinished(Set<BluetoothDevice> set, int i);
    }

    /* access modifiers changed from: package-private */
    public class StopScanTask implements Runnable {
        StopScanTask() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:23:0x00a2  */
        /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            // Method dump skipped, instructions count: 172
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.StopScanTask.run():void");
        }
    }

    public DeviceDiscoveryManager(Activity activity, ResponseListener responseListener) {
        this(activity, responseListener, 1);
    }

    public DeviceDiscoveryManager(Activity activity, ResponseListener responseListener, int i) {
        this.mContext = null;
        this.mHandler = null;
        this.mTask = null;
        this.mLeCallback = null;
        this.mBleScanner = null;
        this.mScanCallback = null;
        this.mEasyPairingResponseListener = null;
        this.mEasyPairingReceiver = null;
        this.mLEScanResponseListener = null;
        this.mLEScanDiscoveryReceiver = null;
        this.mBtManager = null;
        this.mBtAdapter = null;
        this.mBtAdapterEnableRetryCount = 0;
        this.selfHandler = new Handler() {
            /* class com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.AnonymousClass1 */

            public void handleMessage(Message message) {
                ResponseListener responseListener;
                if (message.what == 101) {
                    if (DeviceDiscoveryManager.this.mBtAdapterEnableRetryCount < 5) {
                        String str = DeviceDiscoveryManager.TAG;
                        Log.d(str, "retry start Device Discovery manager, retry cnt: " + DeviceDiscoveryManager.this.mBtAdapterEnableRetryCount);
                        DeviceDiscoveryManager.this.initialize();
                        return;
                    }
                    if (DeviceDiscoveryManager.this.mEasyPairingResponseListener != null) {
                        Log.d(DeviceDiscoveryManager.TAG, "set mEasyPairingResponseListener on selfHandler()");
                        responseListener = DeviceDiscoveryManager.this.mEasyPairingResponseListener;
                    } else if (DeviceDiscoveryManager.this.mLEScanResponseListener != null) {
                        Log.d(DeviceDiscoveryManager.TAG, "set mLEScanResponseListener on selfHandler()");
                        responseListener = DeviceDiscoveryManager.this.mLEScanResponseListener;
                    } else {
                        return;
                    }
                    responseListener.onError(1);
                }
            }
        };
        Log.d(TAG, "DeviceDiscoveryManager()");
        this.mActivity = activity;
        this.mContext = activity.getApplicationContext();
        this.mSearchingType = i;
        if (i == 1) {
            Log.d(TAG, "TYPE_SETUP_MODE - easy pairing");
            this.mEasyPairingResponseListener = responseListener;
        } else if (i != 2) {
            Log.d(TAG, "never enter here");
        } else {
            Log.d(TAG, "TYPE_ALL_MODE");
            this.mLEScanResponseListener = responseListener;
        }
        this.mHandler = new Handler(activity.getMainLooper());
        this.mTask = new StopScanTask();
        this.mBtAdapterEnableRetryCount = 0;
        initialize();
    }

    @SuppressLint({"NewApi"})
    private boolean createBond(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            Log.e(TAG, "bluetoothDevice is null");
            return false;
        } else if (Build.VERSION.SDK_INT < 26) {
            return bluetoothDevice.createBond();
        } else {
            Log.d(TAG, "Call api semCreateBond()");
            try {
                return BluetoothDeviceFactory.get().createBond(bluetoothDevice);
            } catch (Exception e) {
                String str = TAG;
                Log.e(str, "Exception return false : " + e.toString());
                return false;
            }
        }
    }

    private List<ScanFilter> getScanFilter() {
        int[] iArr;
        ArrayList arrayList = new ArrayList();
        byte[] bArr = {1, 0, 2, 0, 1};
        byte[] bArr2 = {-1, -1, -1, -1, -1};
        for (int i : new int[]{117, 29952}) {
            arrayList.add(new ScanFilter.Builder().setManufacturerData(i, bArr, bArr2).build());
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00c0, code lost:
        if (r4 >= 18) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00c2, code lost:
        r8.mLeCallback = r0.getLeScanCallback();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00c9, code lost:
        com.samsung.android.app.twatchmanager.log.Log.d(com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.TAG, "initialize: Version is API level 17 or below, Le scan not supported");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x010a, code lost:
        if (r4 >= 18) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initialize() {
        /*
        // Method dump skipped, instructions count: 275
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.initialize():void");
    }

    private boolean isNeedApplicationFilter() {
        if (this.mSearchingType != 2) {
            return false;
        }
        Log.d(TAG, "isNeedApplicationFilter : true");
        return true;
    }

    private boolean pairing(String str) {
        BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
        if (bluetoothAdapter == null) {
            Log.d(TAG, "pairing: mBtAdapter is null");
            return false;
        }
        try {
            BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(str);
            String str2 = TAG;
            Log.d(str2, "pairing: pairing() address:" + str + " device.getBondState():" + remoteDevice.getBondState());
            if (remoteDevice.getBondState() == 10) {
                boolean createBond = createBond(remoteDevice);
                Log.d(TAG, "pairing: getBondState() == BOND_NONE->createBond()");
                LoggerUtil.insertLog(this.mContext, "G021", "BT pairing", null);
                return createBond;
            } else if (remoteDevice.getBondState() != 12) {
                return false;
            } else {
                String str3 = TAG;
                Log.d(str3, "pairing: pairing() state already BOND_BONDED - mAddress is " + str);
                return false;
            }
        } catch (IllegalArgumentException e) {
            String str4 = TAG;
            Log.e(str4, "pairing: IllegalArgumentException address : " + str + ", " + e);
            return false;
        }
    }

    private boolean startBLEScan(List<String> list) {
        return startBLEScan(list, isNeedApplicationFilter());
    }

    private boolean startBLEScan(List<String> list, boolean z) {
        String str;
        String str2;
        DeviceDiscoveryReceiver deviceDiscoveryReceiver;
        DeviceDiscoveryReceiver deviceDiscoveryReceiver2;
        String str3 = TAG;
        Log.d(str3, "startBLEScan: useApplicationFilter : " + z);
        BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
        if (bluetoothAdapter == null) {
            str = TAG;
            str2 = "startBLEScan: BT Adapter is null, return";
        } else if (!bluetoothAdapter.isEnabled()) {
            Log.d(TAG, "startBLEScan: BT is Off!! BT 'll be enabled as part of scan request");
            if (!this.mBtAdapter.enable()) {
                str = TAG;
                str2 = "startBLEScan: failed to turn on the BtAdapter";
            } else {
                if (this.mEasyPairingReceiver != null) {
                    Log.d(TAG, "startBLEScan: wait for BT_TURNED_ON on mEasyPairingReceiver");
                    deviceDiscoveryReceiver2 = this.mEasyPairingReceiver;
                } else {
                    if (this.mLEScanDiscoveryReceiver != null) {
                        Log.d(TAG, "startBLEScan: wait for BT_TURNED_ON on mLEScanDiscoveryReceiver");
                        deviceDiscoveryReceiver2 = this.mLEScanDiscoveryReceiver;
                    }
                    return false;
                }
                deviceDiscoveryReceiver2.setScanTurnedOnBT(true);
                return false;
            }
        } else if (true == this.mBtAdapter.isDiscovering()) {
            str = TAG;
            str2 = "startBLEScan: BR/EDR is discovering, return";
        } else {
            DeviceDiscoveryReceiver deviceDiscoveryReceiver3 = this.mEasyPairingReceiver;
            if (!(deviceDiscoveryReceiver3 == null && (deviceDiscoveryReceiver3 = this.mLEScanDiscoveryReceiver) == null)) {
                deviceDiscoveryReceiver3.setIsBleScan(true);
            }
            DeviceDiscoveryReceiver deviceDiscoveryReceiver4 = this.mEasyPairingReceiver;
            if (!(deviceDiscoveryReceiver4 == null && (deviceDiscoveryReceiver4 = this.mLEScanDiscoveryReceiver) == null)) {
                deviceDiscoveryReceiver4.refreshLEDeviceAddressList();
            }
            if (list != null) {
                if (this.mEasyPairingReceiver != null) {
                    Log.d(TAG, "startBLEScan : setLEScanDeviceName on mEasyPairingReceiver");
                    deviceDiscoveryReceiver = this.mEasyPairingReceiver;
                } else if (this.mLEScanDiscoveryReceiver != null) {
                    Log.d(TAG, "startBLEScan : setLEScanDeviceName on mLEScanDiscoveryReceiver");
                    deviceDiscoveryReceiver = this.mLEScanDiscoveryReceiver;
                }
                deviceDiscoveryReceiver.setLEScanDeviceName(list);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (this.mBleScanner == null) {
                    this.mBleScanner = this.mBtAdapter.getBluetoothLeScanner();
                }
                if (this.mBleScanner == null || this.mScanCallback == null) {
                    str = TAG;
                    str2 = "startBLEScan : Ble scanner is null, return";
                } else {
                    Log.d(TAG, "startBLEScan : Bluetooth LE Scan is started - Lollipop or more");
                    ScanSettings.Builder builder = new ScanSettings.Builder();
                    builder.setScanMode(2);
                    List<ScanFilter> list2 = null;
                    if (!z) {
                        Log.d(TAG, "startBLEScan : add filter device names");
                        list2 = getScanFilter();
                    }
                    this.mBleScanner.startScan(list2, builder.build(), this.mScanCallback);
                    return true;
                }
            } else if (this.mLeCallback == null) {
                str = TAG;
                str2 = "startBLEScan: mLeCallback is null, return";
            } else {
                Log.d(TAG, "startBLEScan: LE Scan is started - KitKat or below");
                this.mBtAdapter.startLeScan(this.mLeCallback);
                return true;
            }
        }
        Log.d(str, str2);
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x006f  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x007d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean startDiscovery() {
        /*
        // Method dump skipped, instructions count: 133
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.startDiscovery():boolean");
    }

    private void startScan4NonSamsung() {
        if (!HostManagerUtils.isSamsungDevice()) {
            Log.d(TAG, "Non Samsung Device should make pop up condition");
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null && defaultAdapter.getState() == 12 && !defaultAdapter.isDiscovering()) {
                Log.d(TAG, "run BR/EDR scan & stop");
                defaultAdapter.startDiscovery();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                defaultAdapter.cancelDiscovery();
            }
        }
    }

    public boolean createBond(String str) {
        DeviceDiscoveryReceiver deviceDiscoveryReceiver;
        String str2;
        String str3;
        if (str == null) {
            str2 = TAG;
            str3 = "createBond: address is null, return";
        } else {
            BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
            if (bluetoothAdapter == null) {
                str2 = TAG;
                str3 = "createBond: mBtAdapter is null";
            } else if (!bluetoothAdapter.isEnabled()) {
                Log.d(TAG, "createBond: BT is OFF, enable BT and request for device pairing");
                if (!this.mBtAdapter.enable()) {
                    str2 = TAG;
                    str3 = "createBond: failed to turn on the BtAdapter";
                } else {
                    DeviceDiscoveryReceiver deviceDiscoveryReceiver2 = this.mEasyPairingReceiver;
                    if (deviceDiscoveryReceiver2 != null) {
                        deviceDiscoveryReceiver2.setPairAddress(str);
                        deviceDiscoveryReceiver = this.mEasyPairingReceiver;
                    } else {
                        DeviceDiscoveryReceiver deviceDiscoveryReceiver3 = this.mLEScanDiscoveryReceiver;
                        if (deviceDiscoveryReceiver3 != null) {
                            deviceDiscoveryReceiver3.setPairAddress(str);
                            deviceDiscoveryReceiver = this.mLEScanDiscoveryReceiver;
                        }
                        return false;
                    }
                    deviceDiscoveryReceiver.setPairingBtOn(true);
                    return false;
                }
            } else {
                if (this.mBtAdapter.isDiscovering()) {
                    Log.d(TAG, "createBond: discovery is in progress. cancel the discovery.");
                    this.mHandler.removeCallbacks(this.mTask);
                    this.mBtAdapter.cancelDiscovery();
                }
                Log.d(TAG, "createBond...");
                return pairing(str);
            }
        }
        Log.d(str2, str3);
        return false;
    }

    public Handler getmHandler() {
        return this.mHandler;
    }

    public StopScanTask getmTask() {
        return this.mTask;
    }

    public void startLeScan() {
        startLeScan(null);
    }

    public boolean startLeScan(List<String> list) {
        ResponseListener responseListener;
        if (list == null) {
            list = null;
        }
        boolean startBLEScan = startBLEScan(list);
        if (true == startBLEScan) {
            int i = DISCOVERY_LE_SCAN_TIMEOUT;
            if (this.mEasyPairingReceiver != null) {
                Log.d(TAG, "startLeScan : set leScanTimeout on mEasyPairingReceiver");
                i = 2000;
            }
            if (this.mHandler != null) {
                String str = TAG;
                Log.d(str, "startLeScan: Call stopBluetoothLeScan after " + i + " milli seconds");
                this.mHandler.removeCallbacks(this.mTask);
                this.mHandler.postDelayed(this.mTask, (long) i);
            }
        } else {
            Log.d(TAG, "startLeScan: Bluetooth Condition is abnormal statet. so skip EasyPairing");
            if (this.mEasyPairingResponseListener != null) {
                Log.d(TAG, "startLeScan : get onError on mEasyPairingResponseListener");
                responseListener = this.mEasyPairingResponseListener;
            } else if (this.mLEScanResponseListener != null) {
                Log.d(TAG, "startLeScan : get onError on mLEScanDiscoveryReceiver");
                responseListener = this.mLEScanResponseListener;
            }
            responseListener.onError(1);
        }
        return startBLEScan;
    }

    public boolean startScan() {
        DeviceDiscoveryReceiver deviceDiscoveryReceiver;
        String str;
        String str2;
        DeviceDiscoveryReceiver deviceDiscoveryReceiver2 = this.mEasyPairingReceiver;
        if (deviceDiscoveryReceiver2 == null || true != deviceDiscoveryReceiver2.getIsBleScan()) {
            DeviceDiscoveryReceiver deviceDiscoveryReceiver3 = this.mLEScanDiscoveryReceiver;
            if (deviceDiscoveryReceiver3 == null || true != deviceDiscoveryReceiver3.getIsBleScan()) {
                boolean startDiscovery = startDiscovery();
                if (true == startDiscovery && this.mHandler != null) {
                    Log.d(TAG, "startScan : Call stopBluetoothScan after 5000 milli seconds");
                    this.mHandler.removeCallbacks(this.mTask);
                    this.mHandler.postDelayed(this.mTask, 5000);
                    if (this.mEasyPairingReceiver != null) {
                        Log.d(TAG, "startScan : setIsBleScan on mEasyPairingReceiver");
                        deviceDiscoveryReceiver = this.mEasyPairingReceiver;
                    } else if (this.mLEScanDiscoveryReceiver != null) {
                        Log.d(TAG, "startScan : setIsBleScan on mLEScanDiscoveryReceiver");
                        deviceDiscoveryReceiver = this.mLEScanDiscoveryReceiver;
                    }
                    deviceDiscoveryReceiver.setIsBleScan(false);
                }
                return startDiscovery;
            }
            str = TAG;
            str2 = "startScan : BLE scan is in mLEScanDiscoveryReceiver";
        } else {
            str = TAG;
            str2 = "startScan : BLE scan is in mEasyPairingReceiver";
        }
        Log.d(str, str2);
        return false;
    }

    public boolean stopBluetoothScan() {
        BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
        if (bluetoothAdapter == null) {
            Log.d(TAG, "stopBluetoothScan: mBtAdapter is null");
            return false;
        } else if (!bluetoothAdapter.isDiscovering()) {
            return true;
        } else {
            Log.d(TAG, "stopBluetoothScan: Bluetooth Scan is stopped");
            return this.mBtAdapter.cancelDiscovery();
        }
    }

    public boolean stopLeScan() {
        String str;
        String str2;
        BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
        if (bluetoothAdapter == null) {
            str = TAG;
            str2 = "stopLeScan: Bt adapter is null, return";
        } else if (Build.VERSION.SDK_INT >= 21) {
            if (this.mBleScanner == null) {
                this.mBleScanner = bluetoothAdapter.getBluetoothLeScanner();
            }
            if (this.mBleScanner == null || this.mScanCallback == null) {
                str = TAG;
                str2 = "BleScanner or mScanCallback is null, return";
            } else {
                Log.d(TAG, "Bluetooth Le Scan is stopped - Lollipop or more");
                this.mBleScanner.stopScan(this.mScanCallback);
                return true;
            }
        } else if (this.mLeCallback == null) {
            str = TAG;
            str2 = "stopLeScan: LeScanCallback is null, return";
        } else {
            Log.d(TAG, "stopLeScan: Bluetooth Le Scan is stopped - Kitkat or less");
            this.mBtAdapter.stopLeScan(this.mLeCallback);
            return true;
        }
        Log.d(str, str2);
        return false;
    }

    public void terminate() {
        Log.d(TAG, "terminate");
        this.mContext = null;
        if (this.mEasyPairingReceiver != null) {
            if (this.mHandler != null) {
                Log.d(TAG, "terminate: removeCallbacks");
                this.mHandler.removeCallbacks(this.mTask);
            }
            if (true == this.mEasyPairingReceiver.getIsBleScan()) {
                stopLeScan();
            }
            this.mEasyPairingReceiver.setIsBleScan(false);
            this.mEasyPairingReceiver.terminate();
            this.mEasyPairingReceiver = null;
        } else if (this.mLEScanDiscoveryReceiver != null) {
            if (this.mHandler != null) {
                Log.d(TAG, "terminate: removeCallbacks");
                this.mHandler.removeCallbacks(this.mTask);
            }
            if (true == this.mLEScanDiscoveryReceiver.getIsBleScan()) {
                stopLeScan();
            }
            this.mLEScanDiscoveryReceiver.setIsBleScan(false);
            this.mLEScanDiscoveryReceiver.terminate();
            this.mLEScanDiscoveryReceiver = null;
        }
        this.mTask = null;
        this.mBtAdapter = null;
        this.mBtManager = null;
        if (this.mEasyPairingResponseListener != null) {
            this.mEasyPairingResponseListener = null;
        } else if (this.mLEScanResponseListener != null) {
            this.mLEScanResponseListener = null;
        }
        this.mLeCallback = null;
        this.mBleScanner = null;
        this.mScanCallback = null;
    }
}
