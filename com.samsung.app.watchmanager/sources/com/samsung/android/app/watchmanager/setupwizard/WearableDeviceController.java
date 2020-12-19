package com.samsung.android.app.watchmanager.setupwizard;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.samsung.android.app.twatchmanager.factory.BluetoothDeviceFactory;
import com.samsung.android.app.twatchmanager.factory.BluetoothHeadsetFactory;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.model.GearInfo;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import java.util.Set;

public class WearableDeviceController {
    private static final String TAG = ("tUHM:" + WearableDeviceController.class.getSimpleName());
    private boolean addToBackStack = false;
    private String deviceId;
    BluetoothDiscoveryUtility.BluetoothDeviceItem leItem;
    private BluetoothHeadset mBluetoothHeadset = null;
    private BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    private Context mContext;
    private IPairingListener mPairingListener;
    private BluetoothProfile.ServiceListener mProfileListener = new BluetoothProfile.ServiceListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.WearableDeviceController.AnonymousClass2 */

        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            if (i == 1) {
                WearableDeviceController.this.mBluetoothHeadset = (BluetoothHeadset) bluetoothProfile;
                WearableDeviceController wearableDeviceController = WearableDeviceController.this;
                wearableDeviceController.connectHFP(wearableDeviceController.tempDevice);
            }
        }

        public void onServiceDisconnected(int i) {
            if (i == 1) {
                WearableDeviceController.this.mBluetoothHeadset = null;
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.android.app.watchmanager.setupwizard.WearableDeviceController.AnonymousClass1 */

        private BluetoothDevice getIntentData(Intent intent) {
            String str;
            String str2;
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            if (bluetoothDevice != null) {
                str2 = WearableDeviceController.TAG;
                str = "bt::Device Name [" + bluetoothDevice.getName() + "] and address [" + bluetoothDevice.getAddress() + "]";
            } else {
                str2 = WearableDeviceController.TAG;
                str = "printIntentData, device is null";
            }
            Log.w(str2, str);
            return bluetoothDevice;
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String str = WearableDeviceController.TAG;
            Log.d(str, "onReceive() action:" + action);
            if ("android.bluetooth.device.action.FOUND".equals(action)) {
                getIntentData(intent);
            } else if ("android.bluetooth.device.action.BOND_STATE_CHANGED".equals(action)) {
                int intExtra = intent.getIntExtra(GlobalConst.EXTRA_BOND_STATE, 0);
                if (intExtra == 11) {
                    Log.d(WearableDeviceController.TAG, "onReceive() EXTRA_BOND_STATE state = BOND_BONDING");
                    BluetoothDevice intentData = getIntentData(intent);
                    if (WearableDeviceController.this.mPairingListener != null) {
                        WearableDeviceController.this.mPairingListener.onBonding(intentData);
                    }
                } else if (intExtra == 12) {
                    Log.d(WearableDeviceController.TAG, "onReceive() EXTRA_BOND_STATE state BOND_BONDED");
                    BluetoothDevice intentData2 = getIntentData(intent);
                    if (WearableDeviceController.this.mPairingListener != null) {
                        WearableDeviceController.this.mPairingListener.onPaired(intentData2);
                    }
                } else if (intExtra == 10) {
                    Log.d(WearableDeviceController.TAG, "onReceive() EXTRA_BOND_STATE state BOND_NONE");
                    getIntentData(intent);
                    if (WearableDeviceController.this.mPairingListener != null) {
                        WearableDeviceController.this.mPairingListener.onCancel();
                    }
                }
            }
        }
    };
    private BluetoothDevice tempDevice = null;

    public interface IPairingListener {
        void onBonding(BluetoothDevice bluetoothDevice);

        void onCancel();

        void onPaired(BluetoothDevice bluetoothDevice);
    }

    public WearableDeviceController(Context context) {
        Log.d(TAG, "WearableDeviceController");
        this.mContext = context;
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

    public void addNextFragmentToStack(boolean z) {
        this.addToBackStack = z;
    }

    public void connectHFP(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            Log.e(TAG, "device is null.");
            return;
        }
        String str = TAG;
        Log.w(str, "connectBTHeadset() [" + bluetoothDevice.getAddress() + "] mBluetoothHeadset = " + this.mBluetoothHeadset);
        BluetoothHeadset bluetoothHeadset = this.mBluetoothHeadset;
        if (bluetoothHeadset == null) {
            BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
            if (bluetoothAdapter != null) {
                this.tempDevice = bluetoothDevice;
                bluetoothAdapter.getProfileProxy(this.mContext, this.mProfileListener, 1);
                return;
            }
            Log.e(TAG, "connectBTHeadset(), Bluetooth is not supported on this hardware platform");
        } else if (bluetoothHeadset.getConnectionState(bluetoothDevice) == 0) {
            Log.w(TAG, "mBluetoothHeadset.connect(device)");
            BluetoothHeadsetFactory.get().connect(this.mBluetoothHeadset, bluetoothDevice);
        }
    }

    public void destroy() {
        Log.e(TAG, "destroy starts");
        try {
            if (this.mReceiver != null) {
                this.mContext.unregisterReceiver(this.mReceiver);
            }
        } catch (IllegalArgumentException unused) {
        }
        this.mPairingListener = null;
    }

    public boolean isPaired(String str) {
        String str2 = TAG;
        Log.d(str2, "isPairder(), deviceID [" + str + "]");
        BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
        if (bluetoothAdapter == null) {
            Log.e(TAG, "isPaired(), Bluetooth is not supported on this hardware platform");
            return false;
        } else if (!bluetoothAdapter.isEnabled()) {
            Log.d(TAG, "btAdapter is turned off...");
            return true;
        } else {
            Set<BluetoothDevice> set = null;
            try {
                set = this.mBtAdapter.getBondedDevices();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            if (set != null && set.size() > 0) {
                for (BluetoothDevice bluetoothDevice : set) {
                    String str3 = TAG;
                    Log.d(str3, "isPairder(), founded deviceID = " + bluetoothDevice.getAddress());
                    if (bluetoothDevice.getAddress().equals(str)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public void pair(String str, IPairingListener iPairingListener) {
        String str2;
        String str3;
        String str4 = TAG;
        Log.d(str4, "pair, listener [" + iPairingListener + "] device:" + str);
        BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
        if (bluetoothAdapter == null) {
            str2 = TAG;
            str3 = "pair, Bluetooth is not supported on this hardware platform";
        } else {
            this.deviceId = str;
            this.mPairingListener = iPairingListener;
            boolean z = true;
            try {
                BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(str);
                GearInfo gearInfo = GearRulesManager.getInstance().getGearInfo(HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(this.deviceId));
                if (gearInfo != null) {
                    z = gearInfo.requiresPairing;
                }
                String str5 = TAG;
                Log.d(str5, "pair, requiresPairing [" + z + "]");
                if (!z || isPaired(this.deviceId)) {
                    if (this.mPairingListener != null) {
                        Context context = this.mContext;
                        if (context instanceof SetupWizardWelcomeActivity) {
                            ((SetupWizardWelcomeActivity) context).setPairedByTUHM(false);
                        }
                        this.mPairingListener.onPaired(remoteDevice);
                        return;
                    }
                    return;
                } else if (!HostManagerUtils.isSamsungDevice() || Build.VERSION.SDK_INT < 19) {
                    this.mContext.registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
                    String str6 = TAG;
                    Log.w(str6, "pairing() address:" + this.deviceId + " device.getBondState():" + remoteDevice.getBondState());
                    if (remoteDevice.getBondState() == 10) {
                        boolean createBond = createBond(remoteDevice);
                        String str7 = TAG;
                        Log.d(str7, "request createBond - " + createBond);
                        return;
                    }
                    return;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(BaseDeviceListFragment.EXTRA_DEVICE_ADDRESS, this.deviceId);
                    bundle.putString(BaseDeviceListFragment.EXTRA_DEVICE_MODEL_NAME, HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(this.deviceId));
                    BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem = this.leItem;
                    if (bluetoothDeviceItem != null) {
                        bundle.putString(GlobalConst.LE_DEVICE_ADDRESS, bluetoothDeviceItem.getAddress());
                    }
                    boolean z2 = this.addToBackStack;
                    if (z2) {
                        bundle.putBoolean(GlobalConst.EXTRA_FROM_WEARABLE_DEVICE_CONTROLLER, z2);
                        this.addToBackStack = false;
                    }
                    Context context2 = this.mContext;
                    if (context2 instanceof SetupWizardWelcomeActivity) {
                        SetupWizardWelcomeActivity setupWizardWelcomeActivity = (SetupWizardWelcomeActivity) context2;
                        setupWizardWelcomeActivity.setLaunchMode(GlobalConst.LAUNCH_MODE_DEVICE_LIST);
                        setupWizardWelcomeActivity.updateFragment(6, bundle);
                        return;
                    }
                    return;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                final CommonDialog commonDialog = new CommonDialog(this.mContext, 1, 0, 1);
                commonDialog.createDialog();
                commonDialog.setTitle(this.mContext.getString(R.string.uhm_update_notice));
                commonDialog.setCancelable(false);
                commonDialog.setMessage(this.mContext.getResources().getString(R.string.not_supported_device_or_version));
                commonDialog.setOkBtnListener(new View.OnClickListener() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.WearableDeviceController.AnonymousClass3 */

                    public void onClick(View view) {
                        commonDialog.dismiss();
                        WearableDeviceController.this.mPairingListener.onCancel();
                    }
                });
                str2 = TAG;
                str3 = "error while getting remote device";
            }
        }
        Log.d(str2, str3);
    }

    public void setLeBluetoothItem(BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem) {
        this.leItem = bluetoothDeviceItem;
    }
}
