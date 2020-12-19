package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.btutil.OldFormatConverter;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.model.GearGroup;
import com.samsung.android.app.twatchmanager.model.GearInfo;
import com.samsung.android.app.twatchmanager.receiver.BackupCompleteReceiver;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsDBOperations;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import com.samsung.android.app.watchmanager.setupwizard.PluginChangeManager;
import java.util.HashMap;

public class HMConnectionManager implements GearRulesManager.ISyncCallback {
    public static final String TAG = ("tUHM:[Conn]" + HMConnectionManager.class.getSimpleName());
    String[] btAddressKeys = {HMConnectFragment.EXTRA_DEVICE_ADDRESS, SetupWizardWelcomeActivity.EXTRA_BT_ADDR, "call_plugin_address"};
    String[] deviceNameKeys = {"device_model_name", SetupWizardWelcomeActivity.EXTRA_DEVICE_MODEL, "MODEL_NAME"};
    private String mBtAddrToConnect;
    private boolean mBtEnabledBefore;
    private HMConnectionManagerCallback mCallback;
    private String mDeviceAliasName;
    private String mDeviceNameToConnect;
    private Handler mDisconnectCompleteListener = new Handler() {
        /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectionManager.AnonymousClass2 */

        public void handleMessage(Message message) {
            String str = HMConnectionManager.TAG;
            Log.d(str, "mDisconnectCompleteListener.handleMessage() starts.. msg : " + message.what);
            BackupCompleteReceiver.unregisterReceiver(TWatchManagerApplication.getAppContext());
            HMConnectionManager.this.mPluginChangeManager.startPluginChangeProcess();
        }
    };
    private GearInfo mGearInfoToConnect;
    private boolean mIsRebootRequired;
    private boolean mIsUpdateSuccess;
    private boolean mIsValidDevice;
    private HashMap<String, GearInfo> mLastGearInfoMap = new HashMap<>();
    private final PluginChangeManager.IPluginChangeCallback mPluginChangeCallback = new PluginChangeManager.IPluginChangeCallback() {
        /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectionManager.AnonymousClass3 */

        @Override // com.samsung.android.app.watchmanager.setupwizard.PluginChangeManager.IPluginChangeCallback
        public void onFinished() {
            HMConnectionManager.this.mCallback.onFinished(true);
        }
    };
    private PluginChangeManager mPluginChangeManager;

    public interface HMConnectionManagerCallback {
        void onFinished(boolean z);

        void onInitEnd(String str);

        void onRulesSyncEnd(boolean z, boolean z2);
    }

    public HMConnectionManager(Bundle bundle, HMConnectionManagerCallback hMConnectionManagerCallback) {
        this.mCallback = hMConnectionManagerCallback;
        this.mGearInfoToConnect = null;
        this.mPluginChangeManager = new PluginChangeManager(this.mPluginChangeCallback);
        parseBundle(bundle);
    }

    private boolean disconnectGearIfNeeded() {
        String str = TAG;
        Log.d(str, "disconnectGearIfNeeded() starts... mIsUpdateSuccess : " + this.mIsUpdateSuccess);
        initGearInfoToDisconnect();
        boolean z = true;
        if (!this.mLastGearInfoMap.isEmpty()) {
            BackupCompleteReceiver.registerReceiver(TWatchManagerApplication.getAppContext(), this.mDisconnectCompleteListener, this.mLastGearInfoMap, 1);
        } else {
            z = false;
        }
        String str2 = TAG;
        Log.d(str2, "disconnectGearIfNeeded() disconnectRequested : " + z);
        return z;
    }

    private GearInfo getDisconnectionNeededGearInfo(DeviceRegistryData deviceRegistryData) {
        if (!TextUtils.equals(this.mBtAddrToConnect, deviceRegistryData.deviceBtID)) {
            GearInfo gearInfo = GearRulesManager.getInstance().getGearInfo(HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(deviceRegistryData.deviceFixedName));
            if (HostManagerUtils.isApplicationEnabled(TWatchManagerApplication.getAppContext(), deviceRegistryData.packagename) && !isDeviceSupportMultiConnection(gearInfo)) {
                GearGroup gearGroup = gearInfo.group;
                boolean z = gearGroup == null ? false : gearGroup.requestDisconnectAlways;
                if (deviceRegistryData.isConnected == 2 || z) {
                    return gearInfo;
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initDeviceNameToConnect(boolean z) {
        DeviceRegistryData deviceRegistryData;
        if (z) {
            this.mDeviceNameToConnect = HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(this.mBtAddrToConnect);
        }
        if (TextUtils.isEmpty(this.mDeviceNameToConnect) && (deviceRegistryData = new RegistryDbManagerWithProvider().queryDevicebyDeviceIdRegistryData(this.mBtAddrToConnect, TWatchManagerApplication.getAppContext()).get(0)) != null) {
            this.mDeviceNameToConnect = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(deviceRegistryData.deviceFixedName);
        }
        String str = TAG;
        Log.d(str, "initDeviceNameToConnect() mDeviceNameToConnect : " + this.mDeviceNameToConnect);
    }

    private void initGearInfoToConnect() {
        this.mGearInfoToConnect = GearRulesManager.getInstance().getGearInfo(this.mDeviceNameToConnect);
        boolean z = true;
        if (!GearRulesManager.getInstance().isValidDevice(this.mDeviceNameToConnect) || !HostManagerUtilsRulesBTDevices.isShowingCondition(OldFormatConverter.getRemoteDevice(this.mBtAddrToConnect), true)) {
            z = false;
        }
        this.mIsValidDevice = z;
        this.mIsRebootRequired = HostManagerUtils.isRebootRequired(TWatchManagerApplication.getAppContext(), this.mDeviceNameToConnect);
    }

    private void initGearInfoToDisconnect() {
        for (DeviceRegistryData deviceRegistryData : new RegistryDbManagerWithProvider().queryAllDeviceRegistryData(TWatchManagerApplication.getAppContext())) {
            GearInfo disconnectionNeededGearInfo = getDisconnectionNeededGearInfo(deviceRegistryData);
            if (disconnectionNeededGearInfo != null) {
                this.mLastGearInfoMap.put(deviceRegistryData.deviceBtID, disconnectionNeededGearInfo);
            }
        }
        String str = TAG;
        Log.d(str, "initGearInfoToDisconnect() ends... mLastGearInfoMap : " + this.mLastGearInfoMap);
    }

    private void parseBundle(Bundle bundle) {
        Log.d(TAG, "parseBundle() starts.. bundle : " + bundle.toString());
        String[] strArr = this.btAddressKeys;
        for (String str : strArr) {
            if (bundle.containsKey(str)) {
                this.mBtAddrToConnect = bundle.getString(str);
            }
        }
        if (bundle.containsKey(SetupWizardWelcomeActivity.EXTRA_DEVICE_ALIAS_NAME)) {
            this.mDeviceAliasName = bundle.getString(SetupWizardWelcomeActivity.EXTRA_DEVICE_ALIAS_NAME);
        }
        this.mIsUpdateSuccess = bundle.getBoolean("is_update_success", false);
    }

    public String getBtAddressToConnect() {
        return this.mBtAddrToConnect;
    }

    public String getContainerPackageNameToConnect() {
        GearInfo gearInfo = this.mGearInfoToConnect;
        if (gearInfo != null) {
            return gearInfo.getContainerPackageName();
        }
        return null;
    }

    public String getDeviceAliasName() {
        return this.mDeviceAliasName;
    }

    public String getDeviceNameToConnect() {
        return this.mDeviceNameToConnect;
    }

    public GearInfo getGearInfoToConnect() {
        return this.mGearInfoToConnect;
    }

    public String getPluginPackageNameToConnect() {
        GearInfo gearInfo = this.mGearInfoToConnect;
        if (gearInfo != null) {
            return gearInfo.pluginPackage;
        }
        return null;
    }

    public void init() {
        String str = TAG;
        Log.d(str, "startConnectionProcess() mBtAddrToConnect : " + this.mBtAddrToConnect + " mDeviceNameToConnect : " + this.mDeviceNameToConnect);
        if (!TextUtils.isEmpty(this.mBtAddrToConnect)) {
            this.mBtEnabledBefore = BluetoothDiscoveryUtility.isBTEnabled();
            BluetoothDiscoveryUtility.turnOnBT(TWatchManagerApplication.getAppContext(), new BluetoothDiscoveryUtility.IBTStatusOnListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectionManager.AnonymousClass1 */

                @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.IBTStatusOnListener
                public void onStatus(boolean z) {
                    HMConnectionManager.this.initDeviceNameToConnect(z);
                    if (!TextUtils.isEmpty(HMConnectionManager.this.mDeviceNameToConnect)) {
                        GearRulesManager.getInstance().syncGearInfo(HMConnectionManager.this);
                    }
                    HMConnectionManager.this.mCallback.onInitEnd(HMConnectionManager.this.mDeviceNameToConnect);
                }
            }, true);
            return;
        }
        this.mCallback.onInitEnd(null);
    }

    public boolean isDeviceSupportMultiConnection(GearInfo gearInfo) {
        if (gearInfo != null) {
            return gearInfo.supportMultiConnection;
        }
        return false;
    }

    @Override // com.samsung.android.app.twatchmanager.manager.GearRulesManager.ISyncCallback
    public void onSyncComplete(boolean z) {
        HMConnectionManagerCallback hMConnectionManagerCallback;
        boolean z2;
        String str = TAG;
        Log.d(str, "onSyncComplete() syncSuccess : " + z);
        if (z) {
            initGearInfoToConnect();
            String str2 = TAG;
            Log.d(str2, " onSyncComplete() isValidDevice : " + this.mIsValidDevice + " isRebootRequired : " + this.mIsRebootRequired);
            this.mCallback.onRulesSyncEnd(this.mIsValidDevice, this.mIsRebootRequired);
            if (this.mIsValidDevice && !this.mIsRebootRequired) {
                this.mPluginChangeManager.init(this.mGearInfoToConnect);
                if (isDeviceSupportMultiConnection(this.mGearInfoToConnect)) {
                    BluetoothDiscoveryUtility.changeBTSetting(this.mBtEnabledBefore);
                    this.mPluginChangeManager.enableCurrentAllRelatedPackages();
                    hMConnectionManagerCallback = this.mCallback;
                    z2 = true;
                } else if (!disconnectGearIfNeeded()) {
                    this.mPluginChangeManager.startPluginChangeProcess();
                    return;
                } else {
                    return;
                }
            } else {
                return;
            }
        } else {
            hMConnectionManagerCallback = this.mCallback;
            z2 = false;
        }
        hMConnectionManagerCallback.onFinished(z2);
    }

    public void setLaunchMode(Activity activity) {
        boolean isConnected = HostManagerUtilsDBOperations.isConnected(TWatchManagerApplication.getAppContext(), this.mBtAddrToConnect);
        String str = TAG;
        Log.d(str, "setLaunchMode() starts.. isConnected : " + isConnected + " mIsUpdateSuccess : " + this.mIsUpdateSuccess);
        if (!isConnected && !this.mIsUpdateSuccess && activity != null) {
            ((SetupWizardWelcomeActivity) activity).setLaunchMode(GlobalConst.LAUNCH_MODE_DEVICE_LIST);
        }
    }
}
