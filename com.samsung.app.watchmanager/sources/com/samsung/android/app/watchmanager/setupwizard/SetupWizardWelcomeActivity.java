package com.samsung.android.app.watchmanager.setupwizard;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import b.j.a.b;
import com.samsung.android.app.twatchmanager.autoswitch.AutoSwitchIntentService;
import com.samsung.android.app.twatchmanager.btutil.ManufacturerData;
import com.samsung.android.app.twatchmanager.btutil.OldFormatConverter;
import com.samsung.android.app.twatchmanager.btutil.SamsungFormatConverter;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.manager.InstallationCallback;
import com.samsung.android.app.twatchmanager.manager.ResourceRulesManager;
import com.samsung.android.app.twatchmanager.manager.UHMDownloadManager;
import com.samsung.android.app.twatchmanager.receiver.BackupCompleteReceiver;
import com.samsung.android.app.twatchmanager.update.PluginExecutor;
import com.samsung.android.app.twatchmanager.update.SAGUIDHelper;
import com.samsung.android.app.twatchmanager.update.UpdateManager;
import com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment;
import com.samsung.android.app.twatchmanager.update.UpdateNotificationManager;
import com.samsung.android.app.twatchmanager.update.UpdateTimer;
import com.samsung.android.app.twatchmanager.util.ActivityUtils;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsDBOperations;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.twatchmanager.util.PermissionUtils;
import com.samsung.android.app.twatchmanager.util.SafeRemoveTaskHandler;
import com.samsung.android.app.twatchmanager.util.StringResourceManagerUtil;
import com.samsung.android.app.twatchmanager.util.Toaster;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.BackgroundFragment;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import com.samsung.android.app.watchmanager.setupwizard.GearFitFragment;
import com.samsung.android.app.watchmanager.setupwizard.NetworkUsageManager;
import com.samsung.android.app.watchmanager.setupwizard.PermissionFragment;
import com.samsung.android.app.watchmanager.setupwizard.WearableDeviceController;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import java.io.File;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SetupWizardWelcomeActivity extends Activity implements IUpdateFragmentListener, BackgroundFragment.ConnectCase, FragmentLifecycleCallbacks {
    private static final String ACTION_VIEW_DETAIL = "com.samsung.android.spv.ACTION_VIEW_DETAIL";
    public static final String EXTRA_BT_ADDR = "bt_addr";
    public static final String EXTRA_CALLER_PACKAGER_NAME = "CALLER_PACKAGE";
    public static final String EXTRA_CLASSNAME = "classname";
    public static final String EXTRA_CONNECTED_WEARABLE_ID = "connected_wearable_id";
    public static final String EXTRA_CONNECT_CASE = "connect_case";
    public static final String EXTRA_CONNECT_NEW_GEAR = "connect_new_gear";
    public static final String EXTRA_DEVICE_ADDRESS_FROM_STUB = "BT_ADD_FROM_STUB";
    public static final String EXTRA_DEVICE_ALIAS_NAME = "DEVICE_ALIAS_NAME";
    public static final String EXTRA_DEVICE_MODEL = "DEVICE_MODEL";
    public static final String EXTRA_DEVICE_TYPE = "DEVICE_TYPE";
    public static final String EXTRA_IS_AUTO_SWITCH = "is_auto_switch";
    public static final String EXTRA_IS_FROM_NFC = "isFromNFC";
    public static final String EXTRA_IS_FROM_PLUGIN = "isFromPlugin";
    public static final String EXTRA_IS_MANAGE_DEVICE = "isManageDevice";
    public static final String EXTRA_LAUNCH_MODE = "launch_mode";
    public static final String EXTRA_MODEL_NAME = "MODEL_NAME";
    public static final String EXTRA_PACKAGENAME = "packageName";
    public static final String EXTRA_SWITCHING = "switching";
    public static final int FRAGMENT_TYPE_DEVICE_LIST = 2;
    public static final int FRAGMENT_TYPE_DISCOVERY_GUIDE = 10;
    public static final int FRAGMENT_TYPE_HMCONNECT = 3;
    public static final int FRAGMENT_TYPE_LOADING = 1;
    public static final int FRAGMENT_TYPE_MANAGE_DEVICES = 8;
    public static final int FRAGMENT_TYPE_PAIRING = 6;
    public static final int FRAGMENT_TYPE_PLUGIN_LOADING = 12;
    public static final int FRAGMENT_TYPE_PLUGIN_UPDATE_NOTICE = 11;
    public static final int FRAGMENT_TYPE_PROMOTION = 9;
    public static final int FRAGMENT_TYPE_SETUP_DEVICE_LIST = 4;
    public static final int FRAGMENT_TYPE_TUHM_PLUGIN_PERMISSIONS = 7;
    public static final int FRAGMENT_TYPE_UPDATE_NOTICE = 5;
    public static final int REQUEST_SYSTEM_INTENT_INSTALLATION = 17;
    public static final String SCHEME_GALAXYWEARABLE_FROM_QR = "galaxywearablefromqr";
    private static final String TAG = ("tUHM:" + SetupWizardWelcomeActivity.class.getSimpleName());
    private static boolean isGuiRunning;
    private WelcomeActivityHelper activityHelper = null;
    BroadcastReceiver autoSwitchListener = new BroadcastReceiver() {
        /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AutoSwitchIntentService.BROADCAST_ACTION_AUTO_SWITCH_COMPLETE)) {
                SetupWizardWelcomeActivity.this.unregisterReceiver(this);
                SetupWizardWelcomeActivity.this.init2();
            }
        }
    };
    private String btAddress = null;
    private int connectCase = 1;
    private ViewGroup fragmentContainer;
    private InstallationCallback installCallback;
    private boolean isFromDisconnectedNoti;
    private boolean isFromPlugin;
    private boolean isFromQRScanner;
    private boolean isFromSamsungApps;
    private boolean isFromStubByNFC = false;
    private boolean isFromWearableInstaller = false;
    private boolean isManageDevice;
    private boolean isPluginDisabled = false;
    private boolean isSwitching;
    private int launchMode = GlobalConst.LAUNCH_MODE_IDLE;
    private AlertDialog.Builder mBuilder;
    private WearableDeviceController mDeviceController;
    private Dialog mDialog;
    private Intent mIntent;
    private boolean mIsAfterUpdateCheck = false;
    private boolean mIsInMultiWindowMode = false;
    private String mLEAddress = "";
    private final Lock mLock = new ReentrantLock();
    private boolean pairedByTUHM = false;
    private WearableDeviceController.IPairingListener wearableListener = new WearableDeviceController.IPairingListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.AnonymousClass2 */

        @Override // com.samsung.android.app.watchmanager.setupwizard.WearableDeviceController.IPairingListener
        public void onBonding(BluetoothDevice bluetoothDevice) {
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.WearableDeviceController.IPairingListener
        public void onCancel() {
            Log.d(SetupWizardWelcomeActivity.TAG, "onCancel");
            SetupWizardWelcomeActivity.this.finish();
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.WearableDeviceController.IPairingListener
        public void onPaired(BluetoothDevice bluetoothDevice) {
            String name = bluetoothDevice.getName();
            SetupWizardWelcomeActivity.this.activityHelper.syncRulesIfNecessary();
            boolean connectAsAudio = GearRulesManager.getInstance().connectAsAudio(name);
            boolean isSupportTablet = GearRulesManager.getInstance().isSupportTablet(name);
            boolean isMultiConnectionDevice = GearRulesManager.getInstance().isMultiConnectionDevice(name);
            String str = SetupWizardWelcomeActivity.TAG;
            Log.d(str, "onPaired()::BONDED device connectAsAudio: " + connectAsAudio + ", isSupportTablet: " + isSupportTablet + ", supportMultiConnection: " + isMultiConnectionDevice);
            if (name == null || !isMultiConnectionDevice) {
                if (SetupWizardWelcomeActivity.this.isFromWearableInstaller) {
                    SetupWizardWelcomeActivity.this.installerConnectionProcess();
                } else if (SetupWizardWelcomeActivity.this.isFromStubByNFC) {
                    SetupWizardWelcomeActivity.this.NFCConnectionProcess();
                }
            } else if (!HostManagerUtils.isTablet() || !connectAsAudio || isSupportTablet) {
                SetupWizardWelcomeActivity.this.checkDeviceNameAndShowIntroFragment(name);
            } else {
                SetupWizardWelcomeActivity.this.mDeviceController.connectHFP(bluetoothDevice);
                SetupWizardWelcomeActivity.this.finish();
            }
        }
    };

    public interface IMultiWindowListener {
        void updateAfterMultiWindowChanged(boolean z);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void NFCConnectionProcess() {
        Log.d(TAG, "launch mode = LAUNCHED_FROM_NFC");
        setLaunchMode(GlobalConst.LAUNCH_MODE_NFC);
        String str = this.btAddress;
        showIntroFragment(str, HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(str));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void checkDeviceNameAndShowIntroFragment(String str) {
        if (TextUtils.isEmpty(str)) {
            str = HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(this.btAddress);
            String str2 = TAG;
            Log.d(str2, "startActivity with deviceNameFromBTAdaptor = " + str);
            if (TextUtils.isEmpty(str)) {
                showIntroFragment(null, null);
                return;
            }
        } else {
            String str3 = TAG;
            Log.d(str3, "startActivity with deviceModelName = " + str);
        }
        showIntroFragment(this.btAddress, str);
    }

    private void checkLastLaunchedDevice() {
        String stringExtra;
        Intent intent;
        String str;
        RegistryDbManagerWithProvider registryDbManagerWithProvider = new RegistryDbManagerWithProvider();
        checkLastLaunchedDeviceUnpairState(registryDbManagerWithProvider);
        if (this.isFromWearableInstaller) {
            intent = this.mIntent;
            str = "BT_ADD_FROM_STUB";
        } else if (this.isFromDisconnectedNoti) {
            intent = this.mIntent;
            str = EXTRA_CONNECTED_WEARABLE_ID;
        } else if (this.isSwitching) {
            stringExtra = this.mIntent.getStringExtra(EXTRA_BT_ADDR);
            checkPackageEnabled(stringExtra);
            registryDbManagerWithProvider.updateDeviceLastLaunchRegistryData(stringExtra, this);
        } else if (!this.isFromPlugin && !this.isFromSamsungApps && !this.isFromQRScanner && !this.isFromStubByNFC) {
            checkLastLaunchedDeviceConnectedState(registryDbManagerWithProvider);
            return;
        } else {
            return;
        }
        stringExtra = intent.getStringExtra(str);
        registryDbManagerWithProvider.updateDeviceLastLaunchRegistryData(stringExtra, this);
    }

    private void checkLastLaunchedDeviceConnectedState(RegistryDbManagerWithProvider registryDbManagerWithProvider) {
        List<DeviceRegistryData> queryConnectedDevice;
        Log.d(TAG, "checkLastLaunchedDeviceConnectedState()");
        List<DeviceRegistryData> queryLastLaunchDeviceRegistryData = registryDbManagerWithProvider.queryLastLaunchDeviceRegistryData(this);
        if (queryLastLaunchDeviceRegistryData != null && queryLastLaunchDeviceRegistryData.size() > 0 && queryLastLaunchDeviceRegistryData.get(0).isConnected != 2 && (queryConnectedDevice = registryDbManagerWithProvider.queryConnectedDevice(this)) != null && queryConnectedDevice.size() > 0) {
            String str = queryConnectedDevice.get(0).deviceBtID;
            String simpleBTNameByAddress = HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(str);
            String str2 = TAG;
            Log.d(str2, "checkLastLaunchedDeviceConnectedState()::LastLaunched device is not connected. Change as " + simpleBTNameByAddress + "(" + str + ")");
            new RegistryDbManagerWithProvider().updateDeviceLastLaunchRegistryData(str, this);
        }
    }

    private void checkLastLaunchedDeviceUnpairState(RegistryDbManagerWithProvider registryDbManagerWithProvider) {
        DeviceRegistryData deviceRegistryData;
        DeviceRegistryData deviceRegistryData2;
        Log.d(TAG, "checkLastLaunchedDeviceUnpairState()");
        List<DeviceRegistryData> queryLastLaunchDeviceRegistryData = registryDbManagerWithProvider.queryLastLaunchDeviceRegistryData(this);
        List<DeviceRegistryData> queryAllDeviceRegistryData = new RegistryDbManagerWithProvider().queryAllDeviceRegistryData(this);
        if (queryLastLaunchDeviceRegistryData.size() > 0) {
            deviceRegistryData2 = queryLastLaunchDeviceRegistryData.get(0);
        } else {
            int size = queryAllDeviceRegistryData.size();
            if (size > 0) {
                deviceRegistryData2 = queryAllDeviceRegistryData.get(size - 1);
            } else {
                deviceRegistryData = null;
                if (deviceRegistryData != null && deviceRegistryData.supportsPairing == 1) {
                    WearableDeviceController wearableDeviceController = this.mDeviceController;
                    if (wearableDeviceController == null) {
                        Log.d(TAG, "Activity already destroyed ignore this call");
                        return;
                    }
                    boolean isPaired = wearableDeviceController.isPaired(deviceRegistryData.deviceBtID);
                    String str = TAG;
                    Log.d(str, "lastLaunchedDeviceID = " + deviceRegistryData + ", isPairedState = " + isPaired);
                    if (!isPaired) {
                        new RegistryDbManagerWithProvider();
                        RegistryDbManagerWithProvider.updateDeviceRegistryConnectionState(this, deviceRegistryData.deviceBtID, 0);
                        for (DeviceRegistryData deviceRegistryData3 : queryAllDeviceRegistryData) {
                            if (deviceRegistryData3.isConnected == 2) {
                                String str2 = TAG;
                                Log.d(str2, deviceRegistryData3.deviceName + "(" + deviceRegistryData3.deviceBtID + ") will be lastLaunchedDevice...");
                                registryDbManagerWithProvider.updateDeviceLastLaunchRegistryData(deviceRegistryData3.deviceBtID, this);
                                return;
                            }
                        }
                        Log.d(TAG, "There is no connected device in DB...");
                        for (DeviceRegistryData deviceRegistryData4 : queryAllDeviceRegistryData) {
                            if (deviceRegistryData4.isConnected == 1) {
                                String str3 = TAG;
                                Log.d(str3, deviceRegistryData4.deviceName + "(" + deviceRegistryData4.deviceBtID + ") will be lastLaunchedDevice...");
                                registryDbManagerWithProvider.updateDeviceLastLaunchRegistryData(deviceRegistryData4.deviceBtID, this);
                                return;
                            }
                        }
                        return;
                    }
                    Log.i(TAG, "No need to check that model");
                    return;
                }
            }
        }
        deviceRegistryData = deviceRegistryData2;
        if (deviceRegistryData != null) {
        }
    }

    private boolean checkLaunchFromStub() {
        String str = TAG;
        Log.d(str, "checkLaunchFromStub, isFromStubByNFC [" + this.isFromStubByNFC + "] and isFromWearableInstaller [" + this.isFromWearableInstaller + "]");
        boolean z = this.isFromStubByNFC || this.isFromWearableInstaller;
        this.activityHelper.sendLogging(this, this.isFromWearableInstaller);
        if (z) {
            Bundle bundle = new Bundle();
            bundle.putInt(LoadingGearFragment.LOADING_TYPE_KEY, 3);
            updateFragment(1, bundle);
            doPairing(this.btAddress);
        }
        String str2 = TAG;
        Log.d(str2, "checkLaunchFromStub, res [" + z + "]");
        return z;
    }

    private void checkPackageEnabled(String str) {
        List<DeviceRegistryData> queryDevicebyDeviceIdRegistryData = new RegistryDbManagerWithProvider().queryDevicebyDeviceIdRegistryData(str, this);
        if (!queryDevicebyDeviceIdRegistryData.isEmpty()) {
            this.isPluginDisabled = !HostManagerUtils.isApplicationEnabled(this, queryDevicebyDeviceIdRegistryData.get(0).packagename);
            String str2 = TAG;
            Log.d(str2, "checkPackageEnabled()::packageName = " + queryDevicebyDeviceIdRegistryData.get(0).packagename + ", isPluginDisabled = " + this.isPluginDisabled);
        }
    }

    private void doPairing(final String str) {
        String str2 = TAG;
        Log.d(str2, "doPairing [" + str + "]");
        BluetoothDiscoveryUtility.turnOnBT(this, new BluetoothDiscoveryUtility.IBTStatusOnListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.AnonymousClass9 */

            @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.IBTStatusOnListener
            public void onStatus(boolean z) {
                if (z) {
                    GearFitFragment.checkConnection(SetupWizardWelcomeActivity.this, new GearFitFragment.IDisconnectTask() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.AnonymousClass9.AnonymousClass1 */

                        @Override // com.samsung.android.app.watchmanager.setupwizard.GearFitFragment.IDisconnectTask
                        public void doTask() {
                            Log.d(SetupWizardWelcomeActivity.TAG, "mDisconnectTask :: disconnected");
                            SetupWizardWelcomeActivity.this.activityHelper.syncRulesIfNecessary();
                            String simpleBTNameByAddress = HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(str);
                            String str = SetupWizardWelcomeActivity.TAG;
                            Log.d(str, "doPairing, deviceName [" + simpleBTNameByAddress + "]");
                            if (SetupWizardWelcomeActivity.this.isFromWearableInstaller || GearRulesManager.getInstance().isValidDevice(simpleBTNameByAddress)) {
                                Log.d(SetupWizardWelcomeActivity.TAG, "doPairing, able to proceed this wearable device");
                                if (SetupWizardWelcomeActivity.this.mDeviceController != null) {
                                    WearableDeviceController wearableDeviceController = SetupWizardWelcomeActivity.this.mDeviceController;
                                    AnonymousClass9 r1 = AnonymousClass9.this;
                                    wearableDeviceController.pair(str, SetupWizardWelcomeActivity.this.wearableListener);
                                    return;
                                }
                                return;
                            }
                            Log.e(SetupWizardWelcomeActivity.TAG, "doPairing, could not pair, let's proceed to next fragment which will show Failed error message");
                            AnonymousClass9 r12 = AnonymousClass9.this;
                            SetupWizardWelcomeActivity.this.showIntroFragment(str, simpleBTNameByAddress);
                        }
                    });
                } else {
                    Log.w(SetupWizardWelcomeActivity.TAG, " Failed to enable BT");
                }
            }
        }, true);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void init() {
        Log.d(TAG, "UHM init()");
        this.mLock.lock();
        boolean isRunning = AutoSwitchIntentService.isRunning();
        this.mLock.unlock();
        if (isRunning) {
            Bundle bundle = new Bundle();
            bundle.putInt(LoadingGearFragment.LOADING_TYPE_KEY, 3);
            updateFragment(1, bundle);
            registerReceiver(this.autoSwitchListener, new IntentFilter(AutoSwitchIntentService.BROADCAST_ACTION_AUTO_SWITCH_COMPLETE));
            return;
        }
        init2();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void init2() {
        isGuiRunning = true;
        checkLastLaunchedDevice();
        if (this.isFromWearableInstaller) {
            setLaunchMode(GlobalConst.LAUNCH_MODE_BT_SETTING);
            setDeviceAddressFromStub(this.mIntent);
            String str = TAG;
            Log.d(str, "btAddress(BT_ADD_FROM_STUB) = " + this.btAddress);
            PermissionFragment.verifyPermissions(this, new PermissionFragment.IGrantedTask() {
                /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.AnonymousClass7 */

                @Override // com.samsung.android.app.watchmanager.setupwizard.PermissionFragment.IGrantedTask
                public void doTask() {
                    SetupWizardWelcomeActivity.this.startUpdateCheck();
                }
            }, PermissionUtils.INITIAL_PERMISSION);
            return;
        }
        if (this.isFromSamsungApps) {
            String stringExtra = this.mIntent.getStringExtra("packageName");
            String str2 = TAG;
            Log.d(str2, "launched from samsungapps after " + stringExtra + "is installed");
            if (startLastLaunchedPlugin(true, stringExtra)) {
                finish();
                return;
            }
        } else if (this.isFromDisconnectedNoti) {
            setLaunchMode(GlobalConst.LAUNCH_MODE_QUICK_PANNEL);
            sendStealthFinishBR();
        } else if (this.isFromPlugin && this.isManageDevice) {
            Log.d(TAG, "need to launch Manage device");
            updateFragment(8, null);
            return;
        } else if (this.isFromQRScanner) {
            Log.d(TAG, "launched from QR scanner.");
            setLaunchMode(GlobalConst.LAUNCH_MODE_QR_SCANNER);
            this.activityHelper.syncRulesIfNecessary();
            for (DeviceRegistryData deviceRegistryData : new RegistryDbManagerWithProvider().queryAllDeviceRegistryData(this)) {
                this.activityHelper.syncRulesIfNecessary();
                if (StringResourceManagerUtil.WATCH_TYPE.equalsIgnoreCase(GearRulesManager.getInstance().getGearInfo(deviceRegistryData.deviceName).group.wearableType) && HostManagerUtils.isApplicationEnabled(this, deviceRegistryData.packagename)) {
                    if (deviceRegistryData.lastLaunch != 1) {
                        new RegistryDbManagerWithProvider().updateDeviceLastLaunchRegistryData(deviceRegistryData.deviceBtID, this);
                    }
                    Log.d(TAG, "need to launch watch plugin.");
                    if (startLastLaunchedPlugin(false, null)) {
                        finish();
                        return;
                    }
                }
            }
            Log.d(TAG, "launch promotion fragment.");
            showIntroFragment(this.btAddress, null);
            return;
        } else if (this.isFromStubByNFC) {
            setLaunchMode(GlobalConst.LAUNCH_MODE_NFC);
            setDeviceAddressFromNfc(this.mIntent.getStringExtra(EXTRA_BT_ADDR));
        }
        startUpdateCheck();
    }

    private void initCheckNetwork() {
        new NetworkUsageManager(this, new NetworkUsageManager.NetworkListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.AnonymousClass5 */

            @Override // com.samsung.android.app.watchmanager.setupwizard.NetworkUsageManager.NetworkListener
            public void onCancel() {
                Log.d(SetupWizardWelcomeActivity.TAG, "NetworkUsageManager :: onCancel");
                SetupWizardWelcomeActivity.this.finish();
            }

            @Override // com.samsung.android.app.watchmanager.setupwizard.NetworkUsageManager.NetworkListener
            public void onContinue() {
                Log.d(SetupWizardWelcomeActivity.TAG, "NetworkUsageManager :: onContinue");
                b.a(SetupWizardWelcomeActivity.this).a(new Intent(LoadingGearFragment.ACTION_NETWORK_USAGE_AGREE));
                SetupWizardWelcomeActivity.this.init();
            }

            @Override // com.samsung.android.app.watchmanager.setupwizard.NetworkUsageManager.NetworkListener
            public void onShow() {
                Log.d(SetupWizardWelcomeActivity.TAG, "NetworkUsageManager :: onShow");
                Bundle bundle = new Bundle();
                bundle.putInt(LoadingGearFragment.LOADING_TYPE_KEY, 1);
                SetupWizardWelcomeActivity.this.updateFragment(1, bundle);
            }
        }).show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void installerConnectionProcess() {
        setLaunchMode(GlobalConst.LAUNCH_MODE_BT_SETTING);
        String simpleBTNameByAddress = HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(this.btAddress);
        boolean isDeviceAlreadyConnected = HostManagerUtilsDBOperations.isDeviceAlreadyConnected(this, this.btAddress, simpleBTNameByAddress);
        String str = TAG;
        Log.d(str, "isAlreadyConnected = " + isDeviceAlreadyConnected);
        if (isDeviceAlreadyConnected) {
            String str2 = TAG;
            Log.d(str2, this.btAddress + " is already connected. Do nothing.");
            GearRulesManager instance = GearRulesManager.getInstance();
            if (HostManagerUtils.isExistPackage(this, instance.getSupportPackage(simpleBTNameByAddress))) {
                String pluginPackage = instance.getPluginPackage(simpleBTNameByAddress);
                if (HostManagerUtils.verifyPluginActivity(this, pluginPackage)) {
                    HostManagerUtils.startPluginActivity(this, pluginPackage, this.btAddress, simpleBTNameByAddress, null, this.launchMode, false);
                } else {
                    showIntroFragment(null, null);
                }
            } else {
                showIntroFragment(this.btAddress, simpleBTNameByAddress);
            }
        } else {
            checkDeviceNameAndShowIntroFragment(simpleBTNameByAddress);
        }
    }

    public static boolean isGuiRunning() {
        String str = TAG;
        Log.d(str, "isGuiRunning() return :" + isGuiRunning);
        return isGuiRunning;
    }

    private void launchVerifiedPluginActivity(final Activity activity, final String str, final String str2, final String str3, final String str4, final int i, final boolean z) {
        Log.d(TAG, "launchVerifiedPluginActivity()");
        PermissionFragment.verifyPermissions(this, new PermissionFragment.IGrantedTask() {
            /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.AnonymousClass8 */

            @Override // com.samsung.android.app.watchmanager.setupwizard.PermissionFragment.IGrantedTask
            public void doTask() {
                Log.d(SetupWizardWelcomeActivity.TAG, "mLaunchPluginTask :: allPermissionGranted");
                if (SetupWizardWelcomeActivity.this.isFromQRScanner) {
                    PluginExecutor.requestStartPlugin(activity, str, str2, str3, str4, i, z, SetupWizardWelcomeActivity.this.mIntent.getDataString());
                } else {
                    HostManagerUtils.startPluginActivity(activity, str, str2, str3, str4, i, z);
                }
                if (SetupWizardWelcomeActivity.this.mIsAfterUpdateCheck) {
                    SetupWizardWelcomeActivity.this.mIsAfterUpdateCheck = false;
                    SetupWizardWelcomeActivity.this.overridePendingTransition(0, 0);
                }
            }
        }, PermissionUtils.INITIAL_PERMISSION);
    }

    private void parseIntent() {
        this.isFromQRScanner = SCHEME_GALAXYWEARABLE_FROM_QR.equals(this.mIntent.getScheme());
        this.isFromPlugin = this.mIntent.getBooleanExtra(EXTRA_IS_FROM_PLUGIN, false);
        this.isSwitching = this.mIntent.getBooleanExtra(EXTRA_SWITCHING, false);
        this.isFromDisconnectedNoti = this.mIntent.hasExtra(EXTRA_CONNECTED_WEARABLE_ID);
        this.isManageDevice = this.mIntent.getBooleanExtra(EXTRA_IS_MANAGE_DEVICE, false);
        this.connectCase = this.mIntent.getIntExtra(EXTRA_CONNECT_NEW_GEAR, 1);
        this.isFromWearableInstaller = this.activityHelper.isFromWearableInstaller(this.mIntent);
        this.isFromStubByNFC = this.activityHelper.isFromStubByNFC(this.mIntent);
        this.isFromSamsungApps = this.mIntent.hasExtra("packageName");
        String str = TAG;
        Log.d(str, "parseIntent() isFromQRScanner = " + this.isFromQRScanner);
        String str2 = TAG;
        Log.d(str2, "parseIntent() isFromPlugin = " + this.isFromPlugin);
        String str3 = TAG;
        Log.d(str3, "parseIntent() isSwitching = " + this.isSwitching);
        String str4 = TAG;
        Log.d(str4, "parseIntent() isManageDevice = " + this.isManageDevice);
        String str5 = TAG;
        Log.d(str5, "parseIntent() isFromDisconnectedNoti = " + this.isFromDisconnectedNoti);
        String str6 = TAG;
        Log.d(str6, "parseIntent() isFromWearableInstaller = " + this.isFromWearableInstaller);
        String str7 = TAG;
        Log.d(str7, "parseIntent() isFromSamsungApps = " + this.isFromSamsungApps);
        String str8 = TAG;
        Log.d(str8, "parseIntent() connectCase = " + this.connectCase);
    }

    private void sendStealthFinishBR() {
        Intent intent = new Intent();
        intent.setAction(GlobalConst.ACTION_STEALTH_FINISH);
        intent.putExtra(GlobalConst.DEVICE_ID, this.mIntent.getStringExtra(EXTRA_CONNECTED_WEARABLE_ID));
        sendBroadcast(intent, "com.samsung.android.hostmanager.permission.ACCESS_UNIFIED_HOST_MANAGER");
    }

    private void setDeviceAddress(BluetoothDevice bluetoothDevice, String str) {
        this.btAddress = bluetoothDevice.getAddress();
        if (str != null && !str.equalsIgnoreCase(this.btAddress)) {
            this.mLEAddress = str;
        }
        String str2 = TAG;
        Log.e(str2, "setDeviceAddress original [" + str + "], converted [" + this.btAddress + "] for [" + bluetoothDevice + "]");
    }

    private void setDeviceAddressFromNfc(String str) {
        setDeviceAddress(new OldFormatConverter(GearRulesManager.getInstance()).getBRdevice(str), str);
    }

    private void setDeviceAddressFromStub(Intent intent) {
        this.activityHelper.syncRulesIfNecessary();
        if (intent == null) {
            Log.e(TAG, "setDeviceAddressFromStub() : intent is null");
            return;
        }
        String stringExtra = intent.getStringExtra("BT_ADD_FROM_STUB");
        String stringExtra2 = intent.getStringExtra("MODEL_NAME");
        String str = TAG;
        Log.d(str, "setDeviceAddressFromStub() : " + stringExtra2 + " / " + stringExtra);
        HostManagerUtilsRulesBTDevices.setDeviceNameFromStub(stringExtra, stringExtra2);
        if (Build.VERSION.SDK_INT >= 28) {
            setDeviceAddressFromStubUpperPos(stringExtra, stringExtra2);
        } else {
            setDeviceAddressFromStubBelowPos(stringExtra, stringExtra2);
        }
    }

    private void setDeviceAddressFromStubBelowPos(String str, String str2) {
        setDeviceAddress(new OldFormatConverter(GearRulesManager.getInstance()).getBrDeviceFromStub(str, str2), str);
    }

    private void setDeviceAddressFromStubUpperPos(String str, String str2) {
        BluetoothDevice remoteDevice = OldFormatConverter.getRemoteDevice(str);
        if (remoteDevice == null) {
            Log.e(TAG, "setDeviceAddressFromStubUpperPos() : device is null");
            return;
        }
        int i = 0;
        try {
            i = remoteDevice.getType();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (i == 2) {
            Log.e(TAG, "setDeviceAddressFromStubUpperPos() : type of device is LE");
            ManufacturerData manufacturerData = SamsungFormatConverter.getManufacturerData(remoteDevice);
            if (manufacturerData == null || !manufacturerData.isSSManufacturerType()) {
                Log.w(TAG, "setDeviceAddressFromStubUpperPos() : OLD_FORMAT");
                this.btAddress = new OldFormatConverter(GearRulesManager.getInstance()).getBrDeviceFromStub(str, str2).getAddress();
                if (str != null && !str.equalsIgnoreCase(this.btAddress)) {
                    this.mLEAddress = str;
                    return;
                }
                return;
            } else if (manufacturerData.haveBRDevice()) {
                Log.w(TAG, "setDeviceAddressFromStubUpperPos() : SAMSUNG_FORMAT");
                this.mLEAddress = str;
                str = SamsungFormatConverter.convertLEtoBR(str, manufacturerData.getBTMacAddress());
            } else {
                Log.w(TAG, "setDeviceAddressFromStubUpperPos() : LE only device.");
                this.mLEAddress = str;
            }
        } else {
            Log.e(TAG, "setDeviceAddressFromStubUpperPos() : type of device is BR");
        }
        this.btAddress = str;
    }

    private void showAppPermissionDialog() {
        this.mBuilder = new AlertDialog.Builder(this);
        this.mBuilder.setCancelable(true);
        this.mBuilder.setTitle(getResources().getString(R.string.permission_dialog_title));
        this.mBuilder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.AnonymousClass3 */

            public void onClick(DialogInterface dialogInterface, int i) {
                SetupWizardWelcomeActivity.this.mDialog.hide();
                SetupWizardWelcomeActivity.this.finish();
            }
        });
        this.mBuilder.setMessage(getResources().getString(R.string.permission_dialog_body_1) + "\n• " + getResources().getString(R.string.permission_dialog_body_2) + "\n• " + getResources().getString(R.string.permission_dialog_body_3) + "\n• " + getResources().getString(R.string.permission_dialog_body_4) + "\n• " + getResources().getString(R.string.permission_dialog_body_5));
        this.mDialog = this.mBuilder.create();
        this.mDialog.setCanceledOnTouchOutside(true);
        this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.AnonymousClass4 */

            public void onDismiss(DialogInterface dialogInterface) {
                SetupWizardWelcomeActivity.this.finish();
            }
        });
        this.mDialog.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showIntroFragment(String str, String str2) {
        showIntroFragment(str, str2, false);
    }

    private void showIntroFragment(String str, String str2, boolean z) {
        int i;
        Log.d(TAG, "showIntroFragment()");
        Bundle bundle = new Bundle();
        if (str == null || str2 == null) {
            i = 2;
        } else {
            String str3 = TAG;
            Log.d(str3, "startIntroActivity() bt_addr = " + str);
            String str4 = TAG;
            Log.d(str4, "startIntroActivity() DEVICE_MODEL = " + str2);
            bundle.putString(EXTRA_BT_ADDR, str);
            bundle.putString(EXTRA_DEVICE_MODEL, str2);
            if (this.mIntent.hasExtra(EXTRA_LAUNCH_MODE)) {
                bundle.putString(EXTRA_DEVICE_ALIAS_NAME, this.mIntent.getStringExtra(EXTRA_DEVICE_MODEL));
                String str5 = TAG;
                Log.d(str5, "startIntroActivity() EXTRA_DEVICE_ALIAS_NAME = " + this.mIntent.getStringExtra(EXTRA_DEVICE_MODEL));
            }
            i = 3;
        }
        updateFragment(i, bundle, z);
    }

    private void startPlugin() {
        if (!startLastLaunchedPlugin(getIntent().getBooleanExtra(EXTRA_SWITCHING, false), null)) {
            WelcomeActivityHelper welcomeActivityHelper = this.activityHelper;
            WelcomeActivityHelper.sendUpdateDBRequestIntent(this);
            showIntroFragment(null, null);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startUpdateCheck() {
        boolean checkPluginUpdateRemain = UpdateManager.checkPluginUpdateRemain();
        String str = TAG;
        Log.d(str, "startUpdateCheck() pluginUpdateRemains : " + checkPluginUpdateRemain);
        if (checkPluginUpdateRemain) {
            List<DeviceRegistryData> queryLastLaunchDeviceRegistryData = new RegistryDbManagerWithProvider().queryLastLaunchDeviceRegistryData(this);
            Bundle bundle = new Bundle();
            if (!queryLastLaunchDeviceRegistryData.isEmpty()) {
                bundle.putString(UpdateNoticeFragment.UPDATE_PLUGIN_BTADDR_KEY, queryLastLaunchDeviceRegistryData.get(0).deviceBtID);
            }
            updateFragment(5, bundle);
            return;
        }
        boolean isUpdateCheckAvailable = UpdateManager.isUpdateCheckAvailable(false, getIntent());
        String str2 = TAG;
        Log.d(str2, "startUpdateCheck() isUpdateCheckAvailable : " + isUpdateCheckAvailable);
        if (isUpdateCheckAvailable) {
            Bundle bundle2 = new Bundle();
            bundle2.putInt(LoadingGearFragment.LOADING_TYPE_KEY, 2);
            updateFragment(1, bundle2);
            return;
        }
        doActionsAfterUpdate();
    }

    public void checkAutoSwitchScenario(Intent intent) {
        if (intent.hasExtra(EXTRA_IS_AUTO_SWITCH) && Boolean.parseBoolean(intent.getStringExtra(EXTRA_IS_AUTO_SWITCH))) {
            setLaunchMode(GlobalConst.LAUNCH_MODE_DRAWER);
        }
    }

    public void doActionsAfterUpdate() {
        if (!checkLaunchFromStub()) {
            String stringExtra = this.mIntent.hasExtra(EXTRA_CLASSNAME) ? this.mIntent.getStringExtra(EXTRA_CLASSNAME) : null;
            String str = TAG;
            Log.d(str, "doActionsAfterUpdate, classname = " + stringExtra);
            if (SetupWizardWelcomeActivity.class.getName().equals(stringExtra)) {
                setLaunchMode(GlobalConst.LAUNCH_MODE_DEVICE_LIST);
                updateFragment(4, null);
            } else if (this.isFromPlugin) {
                final String stringExtra2 = this.mIntent.getStringExtra(EXTRA_BT_ADDR);
                if (stringExtra2 != null) {
                    if (this.mIntent.hasExtra(EXTRA_LAUNCH_MODE)) {
                        setLaunchMode(this.mIntent.getIntExtra(EXTRA_LAUNCH_MODE, this.launchMode));
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt(LoadingGearFragment.LOADING_TYPE_KEY, 3);
                    updateFragment(1, bundle);
                    BluetoothDiscoveryUtility.turnOnBT(this, new BluetoothDiscoveryUtility.IBTStatusOnListener() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.AnonymousClass6 */

                        @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.IBTStatusOnListener
                        public void onStatus(boolean z) {
                            if (z) {
                                SetupWizardWelcomeActivity setupWizardWelcomeActivity = SetupWizardWelcomeActivity.this;
                                String str = stringExtra2;
                                setupWizardWelcomeActivity.showIntroFragment(str, HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(str));
                                return;
                            }
                            Log.w(SetupWizardWelcomeActivity.TAG, "BT cannot enabled");
                            SetupWizardWelcomeActivity.this.finish();
                            HostManagerUtils.removeAllTasks(2000);
                        }
                    }, true);
                    return;
                }
                showIntroFragment(null, null);
            } else {
                startPlugin();
            }
        }
    }

    public void doActionsAfterUpdate(String str) {
        Intent intent = this.mIntent;
        if (intent != null) {
            intent.putExtra(EXTRA_BT_ADDR, str);
        }
        doActionsAfterUpdate();
    }

    public boolean getCurrentMultiWindowMode() {
        return this.mIsInMultiWindowMode;
    }

    public int getLaunchMode() {
        return this.launchMode;
    }

    public boolean isPairedByTUHM() {
        return this.pairedByTUHM;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        String str = TAG;
        Log.d(str, "onActivityResult requestCode [" + i + "], resultCode [" + i2 + "], intent [" + intent + "]");
        if (i == 17) {
            String str2 = TAG;
            Log.d(str2, "System Installation Intent result result: " + i2);
            InstallationCallback installationCallback = this.installCallback;
            if (installationCallback != null) {
                installationCallback.onInstallationResult(i2);
            } else {
                Log.e(TAG, "installCallback is null");
            }
        } else if (i == 1000) {
            SAGUIDHelper.getInstance().onActivityResult(i, i2, intent);
        }
    }

    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        Fragment currentFragment = this.activityHelper.getCurrentFragment(this);
        if (currentFragment == null || !(currentFragment instanceof OnBackKeyListener) || !((OnBackKeyListener) currentFragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (ResourceRulesManager.getInstance().isResourceInfoAvailable()) {
            Log.d(TAG, "onConfigurationChanged() update ImageMap array after configuration is changed(screen Zoom)");
            ResourceRulesManager.getInstance().initImageMapArray();
        }
        Fragment currentFragment = this.activityHelper.getCurrentFragment(this);
        String str = TAG;
        Log.d(str, "onConfigurationChanged, currentFragment [" + currentFragment + "]");
        if (currentFragment != null && (currentFragment instanceof PromotionFragment)) {
            showIntroFragment(null, null, true);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(null);
        this.mIntent = getIntent();
        String str = TAG;
        Log.d(str, "UHM onCreate() Intent: " + this.mIntent);
        Intent intent = this.mIntent;
        if (intent == null || !ACTION_VIEW_DETAIL.equals(intent.getAction())) {
            checkAutoSwitchScenario(this.mIntent);
            if (Build.VERSION.SDK_INT >= 24) {
                this.mIsInMultiWindowMode = isInMultiWindowMode();
            }
            this.activityHelper = new WelcomeActivityHelper();
            if (!HostManagerUtils.isTablet() && Build.VERSION.SDK_INT != 26) {
                setRequestedOrientation(1);
            }
            setContentView(R.layout.layout_container_main);
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            if (HostManagerUtils.isSamsungDeviceWithCustomBinary()) {
                this.activityHelper.showCustomBinaryDialog(this, this);
            } else if (HostManagerUtils.isMaximumPowerSavingMode()) {
                this.activityHelper.showMaximumPowerSavingModeDialog(this, this);
            } else {
                if (!HostManagerUtils.isSamsungDevice()) {
                    File file = new File(UHMDownloadManager.PATH_FOR_EXTERNAL_STORAGE);
                    if (file.exists()) {
                        InstallationUtils.deleteAllContent(file);
                    }
                }
                this.fragmentContainer = (ViewGroup) findViewById(R.id.container);
                this.activityHelper.printDB(this);
                HostManagerUtils.setStatusBarOpenByNotification(this);
                parseIntent();
                this.mDeviceController = new WearableDeviceController(this);
                if (this.isFromPlugin) {
                    init();
                } else {
                    initCheckNetwork();
                }
                getWindow().getDecorView().setSystemUiVisibility(1280);
                this.activityHelper.setAppBadge(this);
                UpdateNotificationManager.getInstance().cancel();
                if (!UpdateTimer.isPendingIntentWorking()) {
                    Log.d(TAG, "onCreate() register background alarm If it is not registered...");
                }
            }
        } else {
            requestWindowFeature(1);
            setTheme(R.style.Theme_FloatingDialog);
            showAppPermissionDialog();
        }
    }

    public void onDestroy() {
        Log.d(TAG, "UHM onDestroy");
        isGuiRunning = false;
        Toaster.show(this, "-UHM destroyed-");
        WearableDeviceController wearableDeviceController = this.mDeviceController;
        if (wearableDeviceController != null) {
            wearableDeviceController.destroy();
            this.mDeviceController = null;
        }
        Log.saveLog();
        BackupCompleteReceiver.unregisterReceiver(this);
        super.onDestroy();
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.FragmentLifecycleCallbacks
    public void onFragmentDetached(int i) {
        if (getFragmentManager() != null) {
            Fragment currentFragment = this.activityHelper.getCurrentFragment(this);
            String str = TAG;
            Log.d(str, "onFragmentDetached " + i);
            String str2 = TAG;
            Log.d(str2, "topFrag:" + currentFragment);
            if (currentFragment != null && currentFragment.getView() != null) {
                currentFragment.getView().setImportantForAccessibility(i);
            }
        }
    }

    public void onMultiWindowModeChanged(boolean z, Configuration configuration) {
        this.mIsInMultiWindowMode = z;
        super.onMultiWindowModeChanged(z, configuration);
        Fragment findFragmentByTag = getFragmentManager().findFragmentByTag(ActivityUtils.FRAGMENT_TAG);
        if (findFragmentByTag == null) {
            Log.d(TAG, "onMultiWindowModeChanged()::fragment is null.");
            return;
        }
        boolean z2 = findFragmentByTag instanceof IMultiWindowListener;
        String str = TAG;
        Log.d(str, "onMultiWindowModeChanged() isInMultiWindowMode: " + z + " current fragment : " + findFragmentByTag.getClass().getSimpleName() + " hasListener : " + z2);
        if (z2) {
            ((IMultiWindowListener) findFragmentByTag).updateAfterMultiWindowChanged(z);
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        String str;
        super.onNewIntent(intent);
        this.mIntent = intent;
        parseIntent();
        Fragment findFragmentById = getFragmentManager().findFragmentById(R.id.container);
        String str2 = TAG;
        Log.d(str2, "onNewIntent() isFromStubByNFC:" + this.isFromStubByNFC + " isFromWearableInstaller:" + this.isFromWearableInstaller + " currentFragment:" + findFragmentById);
        if (this.isFromStubByNFC || this.isFromWearableInstaller) {
            checkAutoSwitchScenario(intent);
            if (this.isFromStubByNFC) {
                setDeviceAddressFromNfc(intent.getStringExtra(EXTRA_BT_ADDR));
            } else {
                setDeviceAddressFromStub(intent);
            }
            this.activityHelper.sendLogging(this, this.isFromWearableInstaller);
            if (!(findFragmentById instanceof LoadingGearFragment) && !(findFragmentById instanceof UpdateNoticeFragment) && (str = this.btAddress) != null) {
                doPairing(str);
            }
        } else if (this.isSwitching) {
            Log.d(TAG, "onNewIntent() switching case...");
            SafeRemoveTaskHandler.getInstance().stop();
            init2();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Log.d(TAG, "onPause");
        Log.saveLog();
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.IUpdateFragmentListener
    public void onUpdateFragmentFinished(boolean z, String str) {
        int i;
        String str2 = TAG;
        Log.d(str2, "onUpdateFragmentFinished() starts... isSuccess : " + z + " btAddress : " + str);
        if (!checkLaunchFromStub()) {
            String str3 = null;
            if (!TextUtils.isEmpty(str)) {
                List<DeviceRegistryData> queryDevicebyDeviceIdRegistryData = new RegistryDbManagerWithProvider().queryDevicebyDeviceIdRegistryData(str, this);
                if (!queryDevicebyDeviceIdRegistryData.isEmpty()) {
                    str3 = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(queryDevicebyDeviceIdRegistryData.get(0).deviceFixedName);
                }
                Log.d(TAG, "onUpdateFragmentFinished() go to HMConnectFragment .. ");
                if (this.isSwitching) {
                    if (!queryDevicebyDeviceIdRegistryData.isEmpty() && this.isPluginDisabled) {
                        HostManagerUtils.disableApplication(this, queryDevicebyDeviceIdRegistryData.get(0).packagename);
                    }
                    i = GlobalConst.LAUNCH_MODE_DRAWER;
                } else {
                    i = GlobalConst.LAUNCH_MODE_AFTER_UPDATE;
                }
                setLaunchMode(i);
            }
            int i2 = 2;
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str3)) {
                String str4 = TAG;
                Log.d(str4, "onUpdateFragmentFinished() bt_addr = " + str);
                String str5 = TAG;
                Log.d(str5, "onUpdateFragmentFinished() DEVICE_MODEL = " + str3);
                bundle.putString(EXTRA_BT_ADDR, str);
                bundle.putString(EXTRA_DEVICE_MODEL, str3);
                bundle.putBoolean("is_update_success", z);
                i2 = 3;
            }
            updateFragment(i2, bundle, false);
        }
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.BackgroundFragment.ConnectCase
    public void sendPanelState(SlidingUpPanelLayout.c cVar) {
        Fragment currentFragment = this.activityHelper.getCurrentFragment(this);
        if (currentFragment != null && currentFragment.getClass() == PromotionFragment.class) {
            ((PromotionFragment) currentFragment).updatePanelState(cVar);
        }
    }

    public void setInstallationCallback(InstallationCallback installationCallback) {
        this.installCallback = installationCallback;
    }

    public void setLaunchMode(int i) {
        String str = TAG;
        Log.d(str, " setLaunchMode() launchMode:" + i);
        int i2 = this.launchMode;
        if (i2 == 1009 || i2 == 1006 || i2 == 1002) {
            Log.w(TAG, " setLaunchMode() unable to modify launch mode");
        } else {
            this.launchMode = i;
        }
    }

    public void setPairedByTUHM(boolean z) {
        this.pairedByTUHM = z;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00e0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean startLastLaunchedPlugin(boolean r13, java.lang.String r14) {
        /*
        // Method dump skipped, instructions count: 349
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.startLastLaunchedPlugin(boolean, java.lang.String):boolean");
    }

    public void updateFragment(int i, Bundle bundle) {
        updateFragment(i, bundle, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00d9, code lost:
        if (r10.getBoolean(com.samsung.android.app.twatchmanager.util.GlobalConst.EXTRA_FROM_WEARABLE_DEVICE_CONTROLLER) == false) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00fd, code lost:
        if (r10.getBoolean(com.samsung.android.app.twatchmanager.util.GlobalConst.EXTRA_FROM_PAIRING_SCREEN) == false) goto L_0x008b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateFragment(int r9, android.os.Bundle r10, boolean r11) {
        /*
        // Method dump skipped, instructions count: 538
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.updateFragment(int, android.os.Bundle, boolean):void");
    }
}
