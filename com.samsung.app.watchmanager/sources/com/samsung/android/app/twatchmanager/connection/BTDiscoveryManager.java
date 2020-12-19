package com.samsung.android.app.twatchmanager.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;

public class BTDiscoveryManager {
    public static final String TAG = ("tUHM:" + BTDiscoveryManager.class.getSimpleName());
    private boolean isDiscoveryFinished;
    private BluetoothAdapter mBTAdapter = null;
    private final BroadcastReceiver mBTScanReceiver = new BroadcastReceiver() {
        /* class com.samsung.android.app.twatchmanager.connection.BTDiscoveryManager.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String str = BTDiscoveryManager.TAG;
            Log.w(str, "mBTScanReceiver.onReceive() action = " + action);
            if ("android.bluetooth.device.action.FOUND".equals(action)) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                BluetoothClass bluetoothClass = (BluetoothClass) intent.getParcelableExtra("android.bluetooth.device.extra.CLASS");
                if (bluetoothDevice != null && bluetoothClass != null) {
                    String address = bluetoothDevice.getAddress();
                    int deviceClass = bluetoothClass.getDeviceClass();
                    String str2 = BTDiscoveryManager.TAG;
                    Log.w(str2, "mBTScanReceiver.onReceive() found device addr = " + address + ", class = " + deviceClass);
                    if (!TextUtils.isEmpty(address) && address.equals(BTDiscoveryManager.this.mBtAddress)) {
                        String str3 = BTDiscoveryManager.TAG;
                        Log.w(str3, "mBTScanReceiver.onReceive() device is found, isDiscoveryFinished : " + BTDiscoveryManager.this.isDiscoveryFinished);
                        if (!BTDiscoveryManager.this.isDiscoveryFinished) {
                            BTDiscoveryManager.this.mListener.onResult(true);
                        }
                        BTDiscoveryManager.this.afterDiscoveryFinished();
                    }
                }
            }
        }
    };
    private String mBtAddress = null;
    private String mDeviceName = null;
    private IBTDiscoveryListener mListener;
    private Handler mTimeoutHandler;

    public interface IBTDiscoveryListener {
        void onResult(boolean z);
    }

    public BTDiscoveryManager(String str, String str2, IBTDiscoveryListener iBTDiscoveryListener) {
        this.mDeviceName = str;
        this.mBtAddress = str2;
        this.mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        this.isDiscoveryFinished = false;
        this.mTimeoutHandler = new Handler();
        this.mListener = iBTDiscoveryListener;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void afterDiscoveryFinished() {
        try {
            TWatchManagerApplication.getAppContext().unregisterReceiver(this.mBTScanReceiver);
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
        Handler handler = this.mTimeoutHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        BluetoothAdapter bluetoothAdapter = this.mBTAdapter;
        if (bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
            this.mBTAdapter.cancelDiscovery();
        }
        this.isDiscoveryFinished = true;
    }

    public void doDiscovery() {
        Log.d(TAG, "doDiscovery() starts...");
        this.isDiscoveryFinished = false;
        TWatchManagerApplication.getAppContext().registerReceiver(this.mBTScanReceiver, new IntentFilter("android.bluetooth.device.action.FOUND"));
        if (this.mBTAdapter == null) {
            this.mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        BluetoothAdapter bluetoothAdapter = this.mBTAdapter;
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isDiscovering()) {
                this.mBTAdapter.cancelDiscovery();
            }
            this.mBTAdapter.startDiscovery();
            this.mTimeoutHandler.postDelayed(new Runnable() {
                /* class com.samsung.android.app.twatchmanager.connection.BTDiscoveryManager.AnonymousClass2 */

                public void run() {
                    String str = BTDiscoveryManager.TAG;
                    Log.d(str, "discovery time out::address = " + BTDiscoveryManager.this.mBtAddress + ", deviceName = " + BTDiscoveryManager.this.mDeviceName);
                    BTDiscoveryManager.this.mListener.onResult(false);
                    BTDiscoveryManager.this.afterDiscoveryFinished();
                }
            }, 4000);
        }
    }
}
