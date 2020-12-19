package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.samsung.android.app.twatchmanager.btutil.OldFormatConverter;
import com.samsung.android.app.twatchmanager.factory.BluetoothDeviceFactory;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.watchmanager.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothDiscoveryUtility {
    private static String EXTRA_DEVICE_MODEL_NAME = "device_model_name";
    public static final String TAG = ("tUHM:" + BluetoothDiscoveryUtility.class.getSimpleName());
    private boolean discoveryFinishedBroadCastReceived = false;
    private Activity mActivity;
    private BluetoothAdapter mBtAdapter;
    private CommonDialog mDialogBTOn = null;
    private GearRulesManager mGearRulesManager = null;
    private GearRulesManager.ISyncCallback mISyncCallback = new GearRulesManager.ISyncCallback() {
        /* class com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.AnonymousClass1 */

        @Override // com.samsung.android.app.twatchmanager.manager.GearRulesManager.ISyncCallback
        public void onSyncComplete(final boolean z) {
            String str = BluetoothDiscoveryUtility.TAG;
            Log.d(str, "onSyncComplete() isSuccess : " + z);
            BluetoothDiscoveryUtility.this.mOnSyncCompleteHandler.post(new Runnable() {
                /* class com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.AnonymousClass1.AnonymousClass1 */

                public void run() {
                    if (!z) {
                        return;
                    }
                    if (BluetoothDiscoveryUtility.this.mActivity == null || BluetoothDiscoveryUtility.this.mActivity.isDestroyed() || BluetoothDiscoveryUtility.this.mActivity.isFinishing()) {
                        Log.d(BluetoothDiscoveryUtility.TAG, "Activity finished/Destroyed");
                    } else if (BluetoothDiscoveryUtility.this.mSyncGearInterface != null) {
                        BluetoothDiscoveryUtility.this.mSyncGearInterface.onSyncCompleteCall();
                    }
                }
            });
        }
    };
    private final Object mLock = new Object();
    private ArrayList<BluetoothDeviceItem> mNewDevicesArrayList = new ArrayList<>();
    private Handler mOnSyncCompleteHandler = null;
    private List<BluetoothDeviceItem> mPairedDevicesList = new ArrayList();
    private BluetoothBroadcastReceiver mReceiver;
    private SyncGearInterface mSyncGearInterface;
    private boolean wasInBondingButCanceled = false;

    private class BluetoothBroadcastReceiver extends BroadcastReceiver {
        private IFunctional mDiscoveryFinished;

        private BluetoothBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String str = BluetoothDiscoveryUtility.TAG;
            Log.d(str, "onReceive() action:" + action);
            if (GlobalConst.ACTION_STATE_CHANGED.equals(action) && BluetoothDiscoveryUtility.this.mBtAdapter != null && BluetoothDiscoveryUtility.this.mBtAdapter.isEnabled() && BluetoothDiscoveryUtility.this.mSyncGearInterface != null) {
                BluetoothDiscoveryUtility.this.mSyncGearInterface.refreshBluetoothAdaptorStateChanged();
            }
            if ("android.bluetooth.device.action.FOUND".equals(action)) {
                BluetoothDiscoveryUtility.this.btActionFound(context, intent);
            } else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                String str2 = BluetoothDiscoveryUtility.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("onReceive() ACTION_DISCOVERY_FINISHED count:");
                sb.append(BluetoothDiscoveryUtility.this.mNewDevicesArrayList == null ? -1 : BluetoothDiscoveryUtility.this.mNewDevicesArrayList.size());
                Log.d(str2, sb.toString());
                IFunctional iFunctional = this.mDiscoveryFinished;
                if (iFunctional != null) {
                    iFunctional.action();
                    this.mDiscoveryFinished = null;
                    return;
                }
                BluetoothDiscoveryUtility.this.discoveryFinishedBroadCastReceived = true;
                if (BluetoothDiscoveryUtility.this.mSyncGearInterface != null) {
                    BluetoothDiscoveryUtility.this.mSyncGearInterface.stopConnectUI();
                }
            } else if ("android.bluetooth.device.action.BOND_STATE_CHANGED".equals(action)) {
                BluetoothDiscoveryUtility.this.btBondStateChanged(context, intent);
            }
        }

        /* access modifiers changed from: package-private */
        public void setDiscoveryCancelListener(IFunctional iFunctional) {
            this.mDiscoveryFinished = iFunctional;
        }
    }

    public static class BluetoothDeviceItem {
        public static final int TYPE_DEVICE = 0;
        public static final int TYPE_MESSAGE = 1;
        String address;
        String fixedName;
        String name;
        int type = 0;

        public BluetoothDeviceItem(String str, String str2, String str3, int i) {
            this.name = str == null ? str2 : str;
            this.address = str3;
            this.type = i;
            this.fixedName = str2;
        }

        public String getAddress() {
            return this.address;
        }

        public String getFixedName() {
            return this.fixedName;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String toString() {
            return this.type == 0 ? this.name : this.name;
        }
    }

    /* access modifiers changed from: private */
    public static class BluetoothStateReceiver extends BroadcastReceiver {
        private CommonDialog dialog;
        Context mContext;
        IBTStatusOnListener mListener;

        public BluetoothStateReceiver(Context context, IBTStatusOnListener iBTStatusOnListener) {
            this.mListener = iBTStatusOnListener;
            this.mContext = context;
        }

        public void cleanUp() {
            this.mListener = null;
            CommonDialog commonDialog = this.dialog;
            if (commonDialog != null && commonDialog.isShowing()) {
                this.dialog.dismiss();
                this.dialog = null;
            }
            try {
                this.mContext.unregisterReceiver(this);
            } catch (Exception e) {
                Log.w(BluetoothDiscoveryUtility.TAG, "activity.unregisterReceiver(mBluetoothStateReceiver);", e);
            }
            this.mContext = null;
        }

        public void onReceive(Context context, Intent intent) {
            boolean z;
            if (intent.getAction().equals(GlobalConst.ACTION_STATE_CHANGED)) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", LinearLayoutManager.INVALID_OFFSET);
                if (intExtra == Integer.MIN_VALUE) {
                    z = false;
                } else if (intExtra == 12) {
                    z = true;
                } else {
                    return;
                }
                IBTStatusOnListener iBTStatusOnListener = this.mListener;
                if (iBTStatusOnListener != null) {
                    iBTStatusOnListener.onStatus(z);
                }
                cleanUp();
            }
        }

        public void setDialog(CommonDialog commonDialog) {
            this.dialog = commonDialog;
        }

        public void setListner(IBTStatusOnListener iBTStatusOnListener) {
            this.mListener = iBTStatusOnListener;
        }
    }

    public interface IBTStatusOnListener {
        void onStatus(boolean z);
    }

    /* access modifiers changed from: private */
    public interface IFunctional {
        void action();
    }

    public interface SyncGearInterface {
        void notifyDataSetChangedMethod(BluetoothDeviceItem bluetoothDeviceItem, int i);

        void onSyncCompleteCall();

        void refreshBluetoothAdaptorStateChanged();

        void stopConnectUI();
    }

    public BluetoothDiscoveryUtility(Activity activity, SyncGearInterface syncGearInterface) {
        this.mActivity = activity;
        this.mSyncGearInterface = syncGearInterface;
        if (this.mBtAdapter == null) {
            this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        this.mGearRulesManager = GearRulesManager.getInstance();
        this.mOnSyncCompleteHandler = new Handler();
    }

    private void addBondedDevice(BluetoothDevice bluetoothDevice) {
        BluetoothDeviceItem bluetoothDeviceItem;
        String str;
        String str2;
        if (this.mPairedDevicesList == null) {
            str = TAG;
            str2 = "mPairedDevicesArrayAdapter == null";
        } else if (bluetoothDevice == null) {
            str = TAG;
            str2 = "device == null";
        } else {
            BluetoothClass bluetoothClass = bluetoothDevice.getBluetoothClass();
            if (bluetoothClass != null) {
                String str3 = TAG;
                Log.d(str3, "addBondedDevice :: btClass.getDeviceClass() = " + bluetoothClass.getDeviceClass());
                if (HostManagerUtilsRulesBTDevices.isShowingCondition(bluetoothDevice, true)) {
                    String str4 = TAG;
                    Log.d(str4, "onCreate() mPairedDevicesArrayAdapter.add() name:" + bluetoothDevice.getName() + " address:" + bluetoothDevice.getAddress() + " btClass:" + bluetoothClass.getDeviceClass());
                    int size = this.mPairedDevicesList.size();
                    for (int i = 0; i < size; i++) {
                        BluetoothDeviceItem bluetoothDeviceItem2 = this.mPairedDevicesList.get(i);
                        if (bluetoothDevice != null && bluetoothDevice.getAddress() != null && bluetoothDeviceItem2 != null && bluetoothDeviceItem2.getAddress() != null && bluetoothDevice.getAddress().equals(bluetoothDeviceItem2.getAddress())) {
                            str = TAG;
                            str2 = "addBondedDevice :: already have this device in bonded array";
                        }
                    }
                    if (this.mPairedDevicesList.size() == 1 && (bluetoothDeviceItem = this.mPairedDevicesList.get(0)) != null && bluetoothDeviceItem.getName() != null && bluetoothDeviceItem.getName().equals(this.mActivity.getResources().getText(R.string.none_paired))) {
                        this.mPairedDevicesList.remove(bluetoothDeviceItem);
                    }
                    this.mPairedDevicesList.add(new BluetoothDeviceItem(BluetoothDeviceFactory.get().getAliasName(bluetoothDevice), bluetoothDevice.getName(), bluetoothDevice.getAddress(), 0));
                    return;
                }
                String str5 = TAG;
                Log.i(str5, "onCreate() mPairedDevicesArrayAdapter ignore name:" + bluetoothDevice.getName() + " address:" + bluetoothDevice.getAddress() + " btClass:" + bluetoothClass.getDeviceClass());
                return;
            }
            Log.i(TAG, "btClass is null");
            return;
        }
        Log.d(str, str2);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void btActionFound(Context context, Intent intent) {
        String str;
        String str2;
        Log.d(TAG, "BluetoothDevice.ACTION_FOUND");
        BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        if (bluetoothDevice == null) {
            Log.d(TAG, "device is null.");
            return;
        }
        BluetoothClass bluetoothClass = (BluetoothClass) intent.getParcelableExtra("android.bluetooth.device.extra.CLASS");
        String str3 = TAG;
        Log.d(str3, "discoveryFinishedBroadCastReceived:: " + this.discoveryFinishedBroadCastReceived);
        if (this.discoveryFinishedBroadCastReceived) {
            this.discoveryFinishedBroadCastReceived = false;
        }
        if (bluetoothDevice.getBondState() == 12) {
            Log.w(TAG, "onReceive() this item is Bonded");
        } else if (!HostManagerUtilsRulesBTDevices.isShowingCondition(bluetoothDevice, false)) {
            String str4 = TAG;
            android.util.Log.w(str4, "onReceive() this item is not watch!! ignored. name:" + bluetoothDevice.getName() + " address:" + bluetoothDevice.getAddress() + "btClass.getDeviceClass()" + bluetoothClass.getDeviceClass());
        } else if (!containsAddress(this.mNewDevicesArrayList, bluetoothDevice.getAddress())) {
            String str5 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onReceive() mNewDevicesArrayAdapter.add() name:");
            sb.append(bluetoothDevice.getName());
            sb.append(" address:");
            sb.append(bluetoothDevice.getAddress());
            sb.append(" btClass:");
            sb.append(bluetoothClass == null ? "--Null Object--" : Integer.valueOf(bluetoothClass.getDeviceClass()));
            Log.d(str5, sb.toString());
            synchronized (this.mLock) {
                if (new OldFormatConverter(this.mGearRulesManager).isLEdevice(bluetoothDevice)) {
                    BluetoothDeviceItem bRDevice = HostManagerUtilsRulesBTDevices.getBRDevice(bluetoothDevice);
                    if (TextUtils.isEmpty(bRDevice.getName()) || containsAddress(this.mNewDevicesArrayList, bRDevice.getAddress())) {
                        str = TAG;
                        str2 = "mSyncGearInterface is NULL 1";
                    } else {
                        this.mNewDevicesArrayList.add(bRDevice);
                        if (this.mSyncGearInterface != null) {
                            this.mSyncGearInterface.notifyDataSetChangedMethod(bRDevice, 1);
                        }
                    }
                } else if (bluetoothDevice.getName() == null || bluetoothDevice.getName().isEmpty() || this.mNewDevicesArrayList == null) {
                    str = TAG;
                    str2 = "Device name is null";
                } else {
                    BluetoothDeviceItem bluetoothDeviceItem = new BluetoothDeviceItem(BluetoothDeviceFactory.get().getAliasName(bluetoothDevice), bluetoothDevice.getName(), bluetoothDevice.getAddress(), 0);
                    this.mNewDevicesArrayList.add(bluetoothDeviceItem);
                    if (this.mSyncGearInterface != null) {
                        this.mSyncGearInterface.notifyDataSetChangedMethod(bluetoothDeviceItem, 1);
                    } else {
                        str = TAG;
                        str2 = "mSyncGearInterface is NULL 1";
                    }
                }
                Log.d(str, str2);
            }
        } else {
            String str6 = TAG;
            Log.w(str6, "onReceive() duplicate item!! ignored. name:" + bluetoothDevice.getName() + " address:" + bluetoothDevice.getAddress() + " btClass:" + bluetoothClass.getDeviceClass());
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void btBondStateChanged(Context context, Intent intent) {
        int intExtra = intent.getIntExtra(GlobalConst.EXTRA_BOND_STATE, 0);
        String str = TAG;
        Log.d(str, "onReceive() EXTRA_BOND_STATE state = " + intExtra);
        if (intExtra == 11) {
            this.wasInBondingButCanceled = true;
            CommonDialog commonDialog = this.mDialogBTOn;
            if (commonDialog != null && commonDialog.isShowing()) {
                this.mDialogBTOn.dismiss();
            }
            this.mDialogBTOn = new CommonDialog(this.mActivity, 0, 4, 0);
            this.mDialogBTOn.createDialog();
            this.mDialogBTOn.setCancelable(false);
            this.mDialogBTOn.setMessage(this.mActivity.getString(R.string.connect_popup_content));
        } else if (intExtra == 12) {
            CommonDialog commonDialog2 = this.mDialogBTOn;
            if (commonDialog2 != null) {
                commonDialog2.dismiss();
                this.mDialogBTOn = null;
            }
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            bluetoothDevice.getBluetoothClass();
            if (HostManagerUtilsRulesBTDevices.isShowingCondition(bluetoothDevice, true)) {
                String address = bluetoothDevice.getAddress();
                Bundle bundle = new Bundle();
                bundle.putString(HMConnectFragment.EXTRA_DEVICE_ADDRESS, address);
                bundle.putString(EXTRA_DEVICE_MODEL_NAME, getSimpleDeviceName(address));
                bundle.putBoolean(SetupWizardWelcomeActivity.EXTRA_IS_FROM_PLUGIN, false);
                setSuccessResult(bundle);
            }
        } else if (intExtra == 10) {
            BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            BluetoothDeviceItem bluetoothDeviceItem = new BluetoothDeviceItem(BluetoothDeviceFactory.get().getAliasName(bluetoothDevice2), bluetoothDevice2.getName(), bluetoothDevice2.getAddress(), 0);
            String str2 = TAG;
            Log.d(str2, " goToNewDeviceItem address:: " + bluetoothDeviceItem.getAddress());
            if (containsAddress(this.mPairedDevicesList, bluetoothDevice2.getAddress()) && this.mNewDevicesArrayList != null) {
                if (!this.wasInBondingButCanceled) {
                    Log.d(TAG, "this device is in paired list so we have to remove it.");
                    removeFromPairedDevicesList(bluetoothDeviceItem);
                    this.mNewDevicesArrayList.add(bluetoothDeviceItem);
                    SyncGearInterface syncGearInterface = this.mSyncGearInterface;
                    if (syncGearInterface != null) {
                        syncGearInterface.notifyDataSetChangedMethod(bluetoothDeviceItem, 2);
                        this.mSyncGearInterface.notifyDataSetChangedMethod(bluetoothDeviceItem, 1);
                    }
                } else {
                    Log.d(TAG, "not removing the device");
                    this.wasInBondingButCanceled = false;
                }
            }
            CommonDialog commonDialog3 = this.mDialogBTOn;
            if (commonDialog3 != null) {
                commonDialog3.dismiss();
                this.mDialogBTOn = null;
            }
        }
    }

    public static boolean changeBTSetting(boolean z) {
        if (isBTEnabled() == z) {
            return true;
        }
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            return z ? defaultAdapter.enable() : defaultAdapter.disable();
        }
        return false;
    }

    public static boolean enableBT() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        return defaultAdapter != null && defaultAdapter.enable();
    }

    public static boolean isBTEnabled() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean z = defaultAdapter != null && defaultAdapter.isEnabled();
        String str = TAG;
        Log.d(str, "isBTEnabled() result:" + z);
        return z;
    }

    public static void turnOnBT(Context context, IBTStatusOnListener iBTStatusOnListener) {
        if (!isBTEnabled()) {
            turnOnBT(context, iBTStatusOnListener, true);
        } else if (iBTStatusOnListener != null) {
            iBTStatusOnListener.onStatus(true);
        }
    }

    public static void turnOnBT(final Context context, final IBTStatusOnListener iBTStatusOnListener, boolean z) {
        if (!isBTEnabled() && context != null) {
            final BluetoothStateReceiver bluetoothStateReceiver = new BluetoothStateReceiver(context, iBTStatusOnListener);
            context.registerReceiver(bluetoothStateReceiver, new IntentFilter(GlobalConst.ACTION_STATE_CHANGED));
            if (z) {
                turnOnBTInternal(context, iBTStatusOnListener, bluetoothStateReceiver);
                return;
            }
            final CommonDialog commonDialog = new CommonDialog(context, 1, 0, 3);
            commonDialog.createDialog();
            commonDialog.setCancelable(false);
            commonDialog.setTitle(context.getString(R.string.popup_title_message_bluetooth_turn_on));
            commonDialog.setMessage(context.getString(R.string.popup_message_bluetooth_turn_on));
            commonDialog.setTextToOkBtn(context.getString(R.string.button_text_bluetooth_turn_on));
            commonDialog.setOkBtnListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.AnonymousClass2 */

                public void onClick(View view) {
                    commonDialog.dismiss();
                    if (!BluetoothDiscoveryUtility.isBTEnabled()) {
                        BluetoothDiscoveryUtility.turnOnBTInternal(context, iBTStatusOnListener, bluetoothStateReceiver);
                        return;
                    }
                    IBTStatusOnListener iBTStatusOnListener = iBTStatusOnListener;
                    if (iBTStatusOnListener != null) {
                        iBTStatusOnListener.onStatus(true);
                    }
                }
            });
            commonDialog.setCancelBtnListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.AnonymousClass3 */

                public void onClick(View view) {
                    commonDialog.dismiss();
                    IBTStatusOnListener iBTStatusOnListener = iBTStatusOnListener;
                    if (iBTStatusOnListener != null) {
                        iBTStatusOnListener.onStatus(BluetoothDiscoveryUtility.isBTEnabled());
                    }
                    bluetoothStateReceiver.cleanUp();
                }
            });
            bluetoothStateReceiver.setDialog(commonDialog);
        } else if (iBTStatusOnListener != null) {
            iBTStatusOnListener.onStatus(true);
        }
    }

    /* access modifiers changed from: private */
    public static void turnOnBTInternal(Context context, IBTStatusOnListener iBTStatusOnListener, BluetoothStateReceiver bluetoothStateReceiver) {
        if (!enableBT()) {
            bluetoothStateReceiver.cleanUp();
            if (iBTStatusOnListener != null) {
                iBTStatusOnListener.onStatus(false);
            }
        }
    }

    public void addNewDevicesArrayList(BluetoothDeviceItem bluetoothDeviceItem) {
        Log.d(TAG, "addNewDevicesArrayList");
        ArrayList<BluetoothDeviceItem> arrayList = this.mNewDevicesArrayList;
        if (arrayList != null && !containsAddress(arrayList, bluetoothDeviceItem.getAddress())) {
            this.mNewDevicesArrayList.add(bluetoothDeviceItem);
        }
    }

    public void addToPairedDevicesList(BluetoothDeviceItem bluetoothDeviceItem) {
        List<BluetoothDeviceItem> list = this.mPairedDevicesList;
        if (list != null && !containsAddress(list, bluetoothDeviceItem.getAddress())) {
            this.mPairedDevicesList.add(bluetoothDeviceItem);
        }
    }

    public boolean containsAddress(List<BluetoothDeviceItem> list, String str) {
        if (list == null) {
            return false;
        }
        for (BluetoothDeviceItem bluetoothDeviceItem : list) {
            if (str.equals(bluetoothDeviceItem.address)) {
                Log.d(TAG, "Address found in the list");
                return true;
            }
        }
        return false;
    }

    public boolean containsType(List<BluetoothDeviceItem> list, int i) {
        for (BluetoothDeviceItem bluetoothDeviceItem : list) {
            if (i == bluetoothDeviceItem.type) {
                return true;
            }
        }
        return false;
    }

    public void doDiscovery() {
        Log.d(TAG, "doDiscovery()");
        BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled()) {
                Log.d(TAG, "try to enable BT Adapter");
            } else if (this.mBtAdapter.isDiscovering()) {
                Log.d(TAG, "Already discovering. Cancel Discovery");
                BluetoothBroadcastReceiver bluetoothBroadcastReceiver = this.mReceiver;
                if (bluetoothBroadcastReceiver != null) {
                    bluetoothBroadcastReceiver.setDiscoveryCancelListener(new IFunctional() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.AnonymousClass4 */

                        @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.IFunctional
                        public void action() {
                            Log.d(BluetoothDiscoveryUtility.TAG, "Previous discovery finished. Let's initiate a new one.");
                            BluetoothDiscoveryUtility.this.mBtAdapter.startDiscovery();
                        }
                    });
                }
                this.mBtAdapter.cancelDiscovery();
            } else {
                this.mBtAdapter.startDiscovery();
            }
        }
    }

    public String getBTNameFromUHMdapter(String str) {
        if (str == null) {
            return null;
        }
        List<BluetoothDeviceItem> list = this.mPairedDevicesList;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                BluetoothDeviceItem bluetoothDeviceItem = this.mPairedDevicesList.get(i);
                if (bluetoothDeviceItem != null && str.equals(bluetoothDeviceItem.getAddress())) {
                    String str2 = TAG;
                    Log.d(str2, "getBTNameFromUHMdapter :: mPairedDevicesArrayAdapter.item.getName() = " + bluetoothDeviceItem.getName());
                    return bluetoothDeviceItem.getName();
                }
            }
        }
        ArrayList<BluetoothDeviceItem> arrayList = this.mNewDevicesArrayList;
        if (arrayList != null) {
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                BluetoothDeviceItem bluetoothDeviceItem2 = this.mNewDevicesArrayList.get(i2);
                if (bluetoothDeviceItem2 != null && str.equals(bluetoothDeviceItem2.getAddress())) {
                    String str3 = TAG;
                    Log.d(str3, "getBTNameFromUHMdapter :: mNewDevicesArrayAdapter.item.getName() = " + bluetoothDeviceItem2.getName());
                    return bluetoothDeviceItem2.getName();
                }
            }
        }
        return null;
    }

    public String getSimpleDeviceName(String str) {
        String simpleBTNameByAddress = HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(str);
        if (simpleBTNameByAddress != null) {
            return simpleBTNameByAddress;
        }
        String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(getBTNameFromUHMdapter(str));
        String str2 = TAG;
        Log.d(str2, "getSimpleDeviceName :: btSimpleName 22 = " + simpleBTNameByName);
        return simpleBTNameByName;
    }

    public void refreshDeviceAddressList() {
        ArrayList<BluetoothDeviceItem> arrayList = this.mNewDevicesArrayList;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    public List<BluetoothDeviceItem> refreshPariedDevice() {
        Log.d(TAG, "refreshPariedDevice()");
        if (this.mPairedDevicesList == null) {
            Log.d(TAG, "mPairedDevicesList == null");
            return null;
        }
        Set<BluetoothDevice> bondedDevices = this.mBtAdapter.getBondedDevices();
        this.mPairedDevicesList.clear();
        if (bondedDevices != null && bondedDevices.size() > 0) {
            String str = TAG;
            Log.d(str, "pairedDevices.size() = " + bondedDevices.size());
            for (BluetoothDevice bluetoothDevice : bondedDevices) {
                addBondedDevice(bluetoothDevice);
            }
        }
        return this.mPairedDevicesList;
    }

    public void registerReceiverForDiscovery() {
        Log.d(TAG, "registerReceiverForDiscovery()");
        if (this.mReceiver == null) {
            this.mReceiver = new BluetoothBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
            intentFilter.addAction(GlobalConst.HFP_STATE_CHANGED_ICS);
            intentFilter.addAction(GlobalConst.CONNECTION_STATE_CHANGED);
            intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
            intentFilter.addAction(GlobalConst.ACTION_STATE_CHANGED);
            intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
            this.mActivity.registerReceiver(this.mReceiver, intentFilter);
        }
    }

    public void removeFromPairedDevicesList(BluetoothDeviceItem bluetoothDeviceItem) {
        Log.d(TAG, "removeFromPairedDevicesList");
        List<BluetoothDeviceItem> list = this.mPairedDevicesList;
        if (list != null && list.size() > 0) {
            int i = -1;
            for (int i2 = 0; i2 < this.mPairedDevicesList.size(); i2++) {
                if (this.mPairedDevicesList.get(i2).getAddress().equals(bluetoothDeviceItem.getAddress())) {
                    String str = TAG;
                    Log.d(str, "The device is there in the paired list at position:: " + i2);
                    i = i2;
                }
            }
            if (i > -1) {
                Log.d(TAG, "Removing the device from the list");
                this.mPairedDevicesList.remove(i);
                return;
            }
            Log.d(TAG, "Device not present in the list");
        }
    }

    public void setSuccessResult(Bundle bundle) {
        String str = TAG;
        Log.d(str, "setSuccessResult() extraInfo:" + bundle);
        String str2 = (String) bundle.get("call_plugin_address");
        Activity activity = this.mActivity;
        if (activity instanceof SetupWizardWelcomeActivity) {
            ((SetupWizardWelcomeActivity) activity).setLaunchMode(GlobalConst.LAUNCH_MODE_DEVICE_LIST);
        }
        if (str2 != null) {
            String str3 = (String) bundle.get(EXTRA_DEVICE_MODEL_NAME);
            String pluginPackage = GearRulesManager.getInstance().getPluginPackage(str3);
            String str4 = TAG;
            Log.d(str4, " call plugin directly pluginPackage:" + pluginPackage + " deviceName: " + str3);
            if (HostManagerUtils.verifyPluginActivity(this.mActivity, pluginPackage)) {
                HostManagerUtils.startPluginActivity(this.mActivity, pluginPackage, str2, str3, null, GlobalConst.LAUNCH_MODE_DEVICE_LIST, false);
                this.mActivity.overridePendingTransition(0, 0);
                return;
            }
        }
        Activity activity2 = this.mActivity;
        if (activity2 instanceof SetupWizardWelcomeActivity) {
            ((SetupWizardWelcomeActivity) activity2).updateFragment(3, bundle);
        }
    }

    public void stopSyncGearInfo() {
        Log.d(TAG, "stopSyncGearInfo");
        this.mSyncGearInterface = null;
        this.mGearRulesManager.setSyncCallback(null);
    }

    public void syncGearInfo() {
        Log.d(TAG, "syncGearInfo");
        this.mGearRulesManager.syncGearInfo(this.mISyncCallback);
    }

    public void unregisterReceiver() {
        Log.d(TAG, "Fragment destroyed unregistering the receiver");
        BluetoothBroadcastReceiver bluetoothBroadcastReceiver = this.mReceiver;
        if (bluetoothBroadcastReceiver != null) {
            this.mActivity.unregisterReceiver(bluetoothBroadcastReceiver);
            this.mReceiver = null;
        }
        CommonDialog commonDialog = this.mDialogBTOn;
        if (commonDialog != null && commonDialog.isShowing()) {
            this.mDialogBTOn.dismiss();
        }
    }
}
