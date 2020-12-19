package com.samsung.accessory.hearablemgr.core.uhmdb;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.uhm.BaseContentProvider;
import com.samsung.accessory.hearablemgr.common.uhm.DeviceRegistryData;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.common.util.WorkerHandler;
import com.samsung.accessory.hearablemgr.core.bluetooth.BluetoothManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import seccompat.android.util.Log;

public class UhmDatabase {
    public static final String ACTION_DB_UPDATED = "com.samsung.accessory.hearablenmgr.core.uhmdb.UhmDatabase.ACTION_DB_UPDATED";
    public static final String ACTION_UHM_DB_CONNECTION_UPDATED = "com.samsung.android.uhm.db.CONNECTION_UPDATED";
    public static final String ACTION_UPDATE_DB_REQUEST = "com.samsung.android.hostmanager.REQUEST_UPDATE_DB";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.US);
    private static final int DB_LOG_MAX_SIZE = 20;
    private static final String DB_LOG_PREF_KEY = "messages";
    private static final String DB_LOG_PREF_NAME = "uhm_db_log";
    private static final String DELIMITER = "↵";
    private static final String DEVICE_FIXED_NAME = "Galaxy Buds Pro";
    private static final String DEVICE_FIXED_NAME_COMPAT = "Galaxy Buds Pro";
    private static final String TAG = "Attic_UhmDatabase";
    private Context mContext;
    private List<DeviceRegistryData> mDeviceList = new ArrayList();
    private Handler mHandler;
    private boolean mObserved;
    private ContentObserver mObserver;
    private String mObserverFailureReason;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase.AnonymousClass2 */

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        public void onReceive(Context context, Intent intent) {
            char c;
            Log.d(UhmDatabase.TAG, "onReceive() : " + intent.getAction());
            String action = intent.getAction();
            switch (action.hashCode()) {
                case 545516589:
                    if (action.equals("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1174571750:
                    if (action.equals("android.bluetooth.device.action.ALIAS_CHANGED")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1244161670:
                    if (action.equals("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1442301109:
                    if (action.equals(BluetoothManager.ACTION_READY)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2116862345:
                    if (action.equals("android.bluetooth.device.action.BOND_STATE_CHANGED")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                UhmDatabase.cleanUpUnpairedDevices();
                Application.getUhmDatabase().postUpdatePluginDeviceName();
                Application.getUhmDatabase().postUpdateDeviceConnectionState();
            } else if (c == 1 || c == 2) {
                Application.getUhmDatabase().postUpdateDeviceConnectionState();
            } else if (c == 3) {
                int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE);
                if (intExtra == 10 || intExtra == 12) {
                    UhmDatabase.cleanUpUnpairedDevices();
                }
            } else if (c == 4) {
                Application.getUhmDatabase().postUpdatePluginDeviceName();
            }
        }
    };
    private WorkerHandler mWorkerHandler;

    public UhmDatabase(Context context) {
        this.mContext = context;
        this.mHandler = new Handler();
        this.mWorkerHandler = WorkerHandler.createWorkerHandler("uhm_db_cache_worker");
        this.mObserver = new ContentObserver(this.mWorkerHandler) {
            /* class com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase.AnonymousClass1 */

            public void onChange(boolean z) {
                Log.d(UhmDatabase.TAG, "onChange() : " + z);
                UhmDatabase.this.updateDeviceList(false);
            }
        };
        updateDeviceList(true);
        this.mContext.registerReceiver(this.mReceiver, getIntentFilter());
        registerContentProviderObserver();
    }

    private void registerContentProviderObserver() {
        Log.d(TAG, "registerContentProviderObserver()");
        if (!this.mObserved) {
            try {
                this.mContext.getContentResolver().registerContentObserver(BaseContentProvider.DEVICE_CONTENT_URI, true, this.mObserver);
                this.mObserved = true;
            } catch (Throwable th) {
                th.printStackTrace();
                this.mObserverFailureReason = th.toString();
                Log.e(TAG, "registerContentProviderObserver() : Exception : " + this.mObserverFailureReason);
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onFirstDeviceList() {
        Log.d(TAG, "onFirstDeviceList()");
        updatePluginDeviceName();
        updateDeviceConnectionState();
    }

    public void destroy() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mObserver);
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mWorkerHandler.quit();
    }

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothManager.ACTION_READY);
        intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.ALIAS_CHANGED");
        return intentFilter;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateDeviceList(final boolean z) {
        if (this.mObserved) {
            Log.d(TAG, "updateDeviceList()");
        } else {
            Log.d(TAG, "updateDeviceList() : " + this.mObserverFailureReason);
        }
        if (!z && !this.mObserved) {
            registerContentProviderObserver();
        }
        this.mWorkerHandler.post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase.AnonymousClass1TaskUpdateDeviceList */

            public void run() {
                Log.d(UhmDatabase.TAG, "TaskUpdateDeviceList() : run()");
                UhmDatabase.this.mDeviceList = UhmDatabase.queryDeviceRegistryData(null);
                UhmDatabase.printDeviceList(UhmDatabase.this.mDeviceList);
                Log.d(UhmDatabase.TAG, "updateDeviceList() : run()_done : mDeviceList.size() = " + UhmDatabase.this.mDeviceList.size());
                Util.sendPermissionBroadcast(UhmDatabase.this.mContext, new Intent(UhmDatabase.ACTION_DB_UPDATED));
                if (z) {
                    UhmDatabase.this.mHandler.post(new Runnable() {
                        /* class com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase.AnonymousClass1TaskUpdateDeviceList.AnonymousClass1 */

                        public void run() {
                            UhmDatabase.this.onFirstDeviceList();
                        }
                    });
                }
            }
        });
    }

    public void postUpdateDeviceList() {
        Log.d(TAG, "postUpdateDeviceList()");
        updateDeviceList(false);
    }

    public List<DeviceRegistryData> getDeviceList() {
        ArrayList arrayList = new ArrayList(this.mDeviceList);
        Log.d(TAG, "getDeviceList() : result.size() = " + arrayList.size());
        return arrayList;
    }

    public DeviceRegistryData getDevice(String str) {
        Log.d(TAG, "getDevice() : " + BluetoothUtil.privateAddress(str));
        for (DeviceRegistryData deviceRegistryData : getDeviceList()) {
            if (Util.equalsIgnoreCase(str, deviceRegistryData.deviceId)) {
                return deviceRegistryData;
            }
        }
        return null;
    }

    public String getDeviceFixedName(String str) {
        Log.d(TAG, "getDeviceFixedName() : " + BluetoothUtil.privateAddress(str));
        DeviceRegistryData device = getDevice(str);
        if (device == null) {
            return null;
        }
        return device.deviceFixedName;
    }

    public String getDeviceName(String str) {
        Log.d(TAG, "getDeviceName() : " + BluetoothUtil.privateAddress(str));
        DeviceRegistryData device = getDevice(str);
        if (device == null) {
            return null;
        }
        return device.deviceName;
    }

    public void postUpdateDeviceConnection(final String str, final int i) {
        Log.d(TAG, "postUpdateDeviceConnection() : " + BluetoothUtil.privateAddress(str) + ", connected=" + i);
        this.mWorkerHandler.post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase.AnonymousClass1TaskUpdateDeviceConnection */

            public void run() {
                UhmDatabase.updateDeviceConnection(str, i);
            }
        });
    }

    public void postDeleteDevice(final String str) {
        Log.d(TAG, "postDeleteDevice() : " + BluetoothUtil.privateAddress(str));
        this.mWorkerHandler.post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase.AnonymousClass1TaskDeleteDevice */

            public void run() {
                UhmDatabase.deleteDeviceRegistryData(str);
            }
        });
    }

    public void postUpdateLastLaunchDevice() {
        Log.d(TAG, "postUpdateLastLaunchDevice()");
        this.mWorkerHandler.post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase.AnonymousClass3 */

            public void run() {
                UhmDatabase.updateLastLaunchDevice(UhmFwUtil.getLastLaunchDeviceId());
            }
        });
    }

    public void postUpdateDeviceNameFromBtSystem(final String str) {
        Log.d(TAG, "postUpdateDeviceNameFromBtSystem() : " + BluetoothUtil.privateAddress(str));
        this.mWorkerHandler.post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase.AnonymousClass1TaskUpdateDeviceNameFromBtSystem */

            public void run() {
                UhmDatabase.updateDeviceNameFromBtSystem(str);
            }
        });
    }

    /* access modifiers changed from: private */
    public static void printDeviceList(List<DeviceRegistryData> list) {
        Log.v(TAG, "printDeviceList() :");
        for (DeviceRegistryData deviceRegistryData : list) {
            Log.v(TAG, "printDeviceList() : " + deviceRegistryData.deviceName + " / " + deviceRegistryData.deviceFixedName + " / " + BluetoothUtil.privateAddress(deviceRegistryData.deviceId) + " / last_launch: " + deviceRegistryData.lastLaunch + " / connected: " + deviceRegistryData.connected + " / " + deviceRegistryData.packageName);
        }
        Log.v(TAG, "printDeviceList() :");
        String[] log = getLog();
        for (String str : log) {
            Log.v(TAG, "printDeviceList() : " + str);
        }
    }

    public static boolean updateLastLaunchDevice(String str) {
        try {
            ContentResolver contentResolver = Application.getContext().getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BaseContentProvider.LAST_LAUNCH, (Integer) 0);
            int update = contentResolver.update(BaseContentProvider.DEVICE_CONTENT_URI, contentValues, "last_launch = 1", null);
            Log.d(TAG, "updateLastLaunchFlag() LAST_LAUNCH = 0 : count=" + update);
            contentValues.put(BaseContentProvider.LAST_LAUNCH, (Integer) 1);
            int update2 = contentResolver.update(BaseContentProvider.DEVICE_CONTENT_URI, contentValues, "bt_id = ?", new String[]{str});
            Log.d(TAG, "updateLastLaunchFlag(" + BluetoothUtil.privateAddress(str) + ") LAST_LAUNCH = 1 : count=" + update2);
            StringBuilder sb = new StringBuilder();
            sb.append("updateLastLaunchDevice(");
            sb.append(BluetoothUtil.privateAddress(str));
            sb.append(")");
            log(sb.toString());
            return true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Log.e(TAG, "updateLastLaunchFlag(" + BluetoothUtil.privateAddress(str) + ") : failed !!!");
            return false;
        }
    }

    public static boolean updateDeviceConnection(String str, int i) {
        Log.d(TAG, "updateDeviceConnection() : " + BluetoothUtil.privateAddress(str) + ", connected=" + i);
        List<DeviceRegistryData> queryDeviceRegistryData = queryDeviceRegistryData(str);
        if (queryDeviceRegistryData.size() == 0) {
            return false;
        }
        if (queryDeviceRegistryData.get(0).connected.intValue() == i) {
            return true;
        }
        ContentValues contentValues = new ContentValues();
        int i2 = -1;
        contentValues.put("connected", Integer.valueOf(i));
        ContentResolver contentResolver = Application.getContext().getContentResolver();
        try {
            i2 = contentResolver.update(BaseContentProvider.DEVICE_CONTENT_URI, contentValues, "bt_id = ?", new String[]{str});
            log("updateDeviceConnection(" + BluetoothUtil.privateAddress(str) + ", " + i + ")");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            sendUpdateDBRequestIntent();
        }
        if (i2 > 0) {
            Intent intent = new Intent(ACTION_UHM_DB_CONNECTION_UPDATED);
            Bundle bundle = new Bundle();
            bundle.putString("deviceId", str);
            bundle.putInt("conntected", i);
            intent.putExtras(bundle);
            Application.getContext().sendBroadcast(intent);
            return true;
        }
        Log.e(TAG, "updateDeviceConnection() : count > 0 == false !!!");
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x007d, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x007e, code lost:
        if (r9 != null) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0084, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0085, code lost:
        r0.addSuppressed(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0088, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<com.samsung.accessory.hearablemgr.common.uhm.DeviceRegistryData> queryDeviceRegistryData(java.lang.String r9) {
        /*
        // Method dump skipped, instructions count: 143
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase.queryDeviceRegistryData(java.lang.String):java.util.List");
    }

    public static boolean deleteDeviceRegistryData(String str) {
        Log.d(TAG, "deleteDeviceRegistryData() : " + BluetoothUtil.privateAddress(str));
        try {
            int delete = Application.getContext().getContentResolver().delete(BaseContentProvider.DEVICE_CONTENT_URI, "bt_id = ?", new String[]{str});
            Log.d(TAG, "deleteDeviceRegistryData() : count=" + delete);
            log("deleteDeviceRegistryData(" + BluetoothUtil.privateAddress(str) + ")");
            if (delete != 0) {
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Log.e(TAG, "deleteDeviceRegistryData(" + BluetoothUtil.privateAddress(str) + ") : failed !!!");
            return false;
        }
    }

    public static void sendUpdateDBRequestIntent() {
        Log.d(TAG, "sendUpdateDBRequestIntent()");
        Intent intent = new Intent();
        intent.setAction(ACTION_UPDATE_DB_REQUEST);
        Application.getContext().sendBroadcast(intent);
    }

    public static void updateDeviceNameFromBtSystem(String str) {
        String aliasName = BluetoothUtil.getAliasName(str);
        Log.d(TAG, "updateDeviceNameFromBtSystem() : " + BluetoothUtil.privateAddress(str) + ", deviceName= " + aliasName);
        if (aliasName != null) {
            ContentValues contentValues = new ContentValues();
            int i = -1;
            contentValues.put("device_name", aliasName);
            ContentResolver contentResolver = Application.getContext().getContentResolver();
            try {
                i = contentResolver.update(BaseContentProvider.DEVICE_CONTENT_URI, contentValues, "bt_id = ?", new String[]{str});
                log("updateDeviceNameFromBtSystem(" + BluetoothUtil.privateAddress(str) + ", " + aliasName + ")");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "updateDeviceNameFromBtSystem() : count= " + i);
        }
    }

    public void postCleanUpUnpairedDevices() {
        Log.d(TAG, "postCleanUpUnpairedDevices()");
        this.mHandler.post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase.AnonymousClass4 */

            public void run() {
                UhmDatabase.cleanUpUnpairedDevices();
            }
        });
    }

    public static void cleanUpUnpairedDevices() {
        Set<BluetoothDevice> bondedDevices;
        Log.d(TAG, "cleanUpUnpairedDevices()");
        BluetoothAdapter adapter = BluetoothUtil.getAdapter();
        boolean z = false;
        if (!(adapter == null || adapter.getState() != 12 || (bondedDevices = adapter.getBondedDevices()) == null)) {
            Log.d(TAG, "cleanUpUnpairedDevices() : bondedDevices.size()=" + bondedDevices.size());
            for (DeviceRegistryData deviceRegistryData : Application.getUhmDatabase().getDeviceList()) {
                if (deviceRegistryData.deviceFixedName.equals("Galaxy Buds Pro") || deviceRegistryData.deviceFixedName.equals("Galaxy Buds Pro")) {
                    Iterator<BluetoothDevice> it = bondedDevices.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (Util.equalsIgnoreCase(it.next().getAddress(), deviceRegistryData.deviceId)) {
                                break;
                            }
                        } else {
                            Application.getUhmDatabase().postDeleteDevice(deviceRegistryData.deviceId);
                            z = true;
                            break;
                        }
                    }
                }
            }
        }
        Log.d(TAG, "cleanUpUnpairedDevices()_end : " + z);
    }

    public void postUpdatePluginDeviceName() {
        Log.d(TAG, "postUpdatePluginDeviceName()");
        this.mWorkerHandler.post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase.AnonymousClass5 */

            public void run() {
                UhmDatabase.updatePluginDeviceName();
            }
        });
    }

    public static void updatePluginDeviceName() {
        Log.d(TAG, "updatePluginDeviceName()");
        if (!BluetoothUtil.isAdapterOn()) {
            Log.w(TAG, "updatePluginDeviceName() : BluetoothUtil.isAdapterOn() == false");
            return;
        }
        for (DeviceRegistryData deviceRegistryData : Application.getUhmDatabase().getDeviceList()) {
            if ((deviceRegistryData.deviceFixedName.equals("Galaxy Buds Pro") || deviceRegistryData.deviceFixedName.equals("Galaxy Buds Pro")) && !deviceRegistryData.deviceName.equals(BluetoothUtil.getAliasName(deviceRegistryData.deviceId))) {
                Application.getUhmDatabase().postUpdateDeviceNameFromBtSystem(deviceRegistryData.deviceId);
            }
        }
        Log.d(TAG, "updatePluginDeviceName()_end");
    }

    public static void updateDeviceConnectionState() {
        BluetoothDevice bondedDevice;
        Log.d(TAG, "updateDeviceConnectionState()");
        List<DeviceRegistryData> deviceList = Application.getUhmDatabase().getDeviceList();
        boolean isAdapterOn = BluetoothUtil.isAdapterOn();
        BluetoothManager bluetoothManager = Application.getBluetoothManager();
        if (!isAdapterOn || bluetoothManager.isReady()) {
            for (DeviceRegistryData deviceRegistryData : deviceList) {
                if (deviceRegistryData.deviceFixedName.equals("Galaxy Buds Pro") || deviceRegistryData.deviceFixedName.equals("Galaxy Buds Pro")) {
                    int i = 1;
                    if (isAdapterOn && (bondedDevice = BluetoothUtil.getBondedDevice(deviceRegistryData.deviceId)) != null && (bluetoothManager.getHeadsetState(bondedDevice) == 2 || bluetoothManager.getA2dpState(bondedDevice) == 2)) {
                        i = 2;
                    }
                    if (deviceRegistryData.connected.intValue() != i) {
                        Application.getUhmDatabase().postUpdateDeviceConnection(deviceRegistryData.deviceId, i);
                    }
                }
            }
            Log.d(TAG, "updateDeviceConnectionState()_end");
            return;
        }
        Log.w(TAG, "updateDeviceConnectionState() : BluetoothManager is NOT ready");
    }

    public void postUpdateDeviceConnectionState() {
        Log.d(TAG, "postUpdateDeviceConnectionState()");
        this.mWorkerHandler.post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase.AnonymousClass6 */

            public void run() {
                UhmDatabase.updateDeviceConnectionState();
            }
        });
    }

    public static int queryAppUpdateCancelCount(String str) throws RuntimeException {
        Cursor query;
        Log.d(TAG, "queryAppUpdateCancelCount() packageName : " + str);
        ContentResolver contentResolver = Application.getContext().getContentResolver();
        int i = -1;
        if (!TextUtils.isEmpty(str) && (query = contentResolver.query(BaseContentProvider.APP_CONTENT_URI, null, "package_name = ?", new String[]{str}, null)) != null && query.moveToFirst()) {
            int columnIndex = query.getColumnIndex(BaseContentProvider.UPDATE_CANCEL_COUNT);
            if (columnIndex >= 0) {
                i = query.getInt(columnIndex);
            }
            query.close();
        }
        Log.d(TAG, "queryAppUpdateCancelCount() update cancel count = " + i);
        return i;
    }

    private static synchronized void log(String str) {
        synchronized (UhmDatabase.class) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(DATE_FORMAT.format(Long.valueOf(System.currentTimeMillis())));
                sb.append(" - ");
                sb.append(str);
                String[] log = getLog();
                int length = log.length;
                int i = 0;
                int i2 = 1;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    String str2 = log[i];
                    if (i2 >= 20) {
                        break;
                    }
                    sb.append(DELIMITER);
                    sb.append(str2);
                    i2++;
                    i++;
                }
                SharedPreferences.Editor edit = Application.getContext().getSharedPreferences(DB_LOG_PREF_NAME, 4).edit();
                edit.putString(DB_LOG_PREF_KEY, sb.toString());
                edit.apply();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "log() : " + e);
            }
        }
        return;
    }

    private static String[] getLog() {
        try {
            String string = Application.getContext().getSharedPreferences(DB_LOG_PREF_NAME, 4).getString(DB_LOG_PREF_KEY, "");
            return string != null ? string.split(DELIMITER) : new String[0];
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getLog() : " + e);
            return new String[0];
        }
    }
}
