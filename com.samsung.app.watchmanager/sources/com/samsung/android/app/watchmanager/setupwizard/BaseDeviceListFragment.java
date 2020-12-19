package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import com.samsung.android.app.twatchmanager.btutil.OldFormatConverter;
import com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager;
import com.samsung.android.app.twatchmanager.factory.BluetoothDeviceFactory;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.log.LoggerUtil;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.PermissionUtils;
import com.samsung.android.app.twatchmanager.util.SALogUtil;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import com.samsung.android.app.watchmanager.setupwizard.PermissionFragment;
import com.samsung.android.app.watchmanager.setupwizard.WearableDeviceController;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseDeviceListFragment extends Fragment implements OnBackKeyListener {
    protected static String EXTRA_DEVICE_ADDRESS = HMConnectFragment.EXTRA_DEVICE_ADDRESS;
    protected static String EXTRA_DEVICE_MODEL_NAME = "device_model_name";
    private static final String TAG = ("tUHM:" + BaseDeviceListFragment.class.getSimpleName());
    protected boolean cancelBTAdapterCalled = false;
    private boolean enableClick = true;
    private Handler enableClickHandler = new Handler();
    private Runnable enableClickRunnable = new Runnable() {
        /* class com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment.AnonymousClass1 */

        public void run() {
            Log.d(BaseDeviceListFragment.TAG, "TimeOut: enable click");
            BaseDeviceListFragment.this.enableClick = true;
        }
    };
    private long idBTOn = 0;
    protected boolean isTurnedOnBT = false;
    Map<String, BluetoothDiscoveryUtility.BluetoothDeviceItem> leItemMap = new HashMap();
    protected Activity mActivity;
    protected BluetoothDiscoveryUtility mBluetoothDiscoveryUtility;
    protected BluetoothAdapter mBtAdapter;
    protected AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment.AnonymousClass3 */

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            String str = BaseDeviceListFragment.TAG;
            Log.d(str, "onItemClick() enableClick:" + BaseDeviceListFragment.this.enableClick);
            if (BaseDeviceListFragment.this.enableClick) {
                BaseDeviceListFragment.this.enableClickHandler.removeCallbacksAndMessages(null);
                BaseDeviceListFragment.this.enableClick = false;
                BaseDeviceListFragment.this.enableClickHandler.postDelayed(BaseDeviceListFragment.this.enableClickRunnable, 2000);
                BaseDeviceListFragment baseDeviceListFragment = BaseDeviceListFragment.this;
                if (baseDeviceListFragment.mBtAdapter != null) {
                    baseDeviceListFragment.cancelBTAdapter();
                    if (!BaseDeviceListFragment.this.mBtAdapter.isEnabled()) {
                        Log.d(BaseDeviceListFragment.TAG, "Turn on BT...");
                        BaseDeviceListFragment baseDeviceListFragment2 = BaseDeviceListFragment.this;
                        baseDeviceListFragment2.mDialogBTOn = new CommonDialog(baseDeviceListFragment2.mActivity, 0, 4, 0);
                        BaseDeviceListFragment.this.mDialogBTOn.createDialog();
                        BaseDeviceListFragment.this.mDialogBTOn.setCancelable(false);
                        BaseDeviceListFragment baseDeviceListFragment3 = BaseDeviceListFragment.this;
                        baseDeviceListFragment3.mDialogBTOn.setMessage(baseDeviceListFragment3.getString(R.string.connect_popup_content));
                        BaseDeviceListFragment.this.mParentBTOn = adapterView;
                        BaseDeviceListFragment.this.viewBTOn = view;
                        BaseDeviceListFragment.this.positionBTOn = i;
                        BaseDeviceListFragment.this.idBTOn = j;
                        BaseDeviceListFragment.this.isTurnedOnBT = true;
                        new Handler().postDelayed(new Runnable() {
                            /* class com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment.AnonymousClass3.AnonymousClass1 */

                            public void run() {
                                if (!BluetoothDiscoveryUtility.isBTEnabled()) {
                                    BluetoothDiscoveryUtility.turnOnBT(BaseDeviceListFragment.this.getActivity(), new BluetoothDiscoveryUtility.IBTStatusOnListener() {
                                        /* class com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment.AnonymousClass3.AnonymousClass1.AnonymousClass1 */

                                        @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.IBTStatusOnListener
                                        public void onStatus(boolean z) {
                                            if (!z) {
                                                CommonDialog commonDialog = BaseDeviceListFragment.this.mDialogBTOn;
                                                if (commonDialog != null && commonDialog.isShowing()) {
                                                    BaseDeviceListFragment.this.enableClick = true;
                                                    BaseDeviceListFragment.this.mDialogBTOn.dismiss();
                                                    return;
                                                }
                                                return;
                                            }
                                            BaseDeviceListFragment.this.doOnItemClick();
                                        }
                                    }, true);
                                }
                            }
                        }, 100);
                        return;
                    }
                }
                if (adapterView == null) {
                    String str2 = BaseDeviceListFragment.TAG;
                    Log.d(str2, "parent = " + ((Object) null));
                    return;
                }
                String str3 = BaseDeviceListFragment.TAG;
                Log.d(str3, "onItemClick() position = " + i);
                String str4 = BaseDeviceListFragment.TAG;
                Log.d(str4, "onItemClick() parent.getCount() = " + adapterView.getCount());
                if (i >= adapterView.getCount()) {
                    Log.d(BaseDeviceListFragment.TAG, "onItemClick() fail to check size of adapter");
                    return;
                }
                BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem = (BluetoothDiscoveryUtility.BluetoothDeviceItem) adapterView.getItemAtPosition(i);
                if (bluetoothDeviceItem != null && 1 != bluetoothDeviceItem.type) {
                    LoggerUtil.insertLog(BaseDeviceListFragment.this.getActivity(), "G019", "Manager BT list", null);
                    BaseDeviceListFragment.this.mDeviceController.addNextFragmentToStack(true);
                    BaseDeviceListFragment.this.connect(bluetoothDeviceItem);
                }
            }
        }
    };
    private WearableDeviceController mDeviceController;
    protected CommonDialog mDialogBTOn = null;
    private CommonDialog mDisconnectDialog = null;
    private boolean mDisconnectWearableDevice = false;
    private final DeviceDiscoveryManager.ResponseListener mEasyPairingListenerACMode = new DeviceDiscoveryManager.ResponseListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment.AnonymousClass7 */
        private boolean wasInBondingButCanceled = false;

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onBondStateChanged(BluetoothDevice bluetoothDevice, int i) {
            BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem;
            String str;
            String str2;
            String str3 = BaseDeviceListFragment.TAG;
            Log.d(str3, "ACMode: onBondStateChanged, device [" + bluetoothDevice + "], bond state [" + i + "]");
            if (i == 11) {
                this.wasInBondingButCanceled = true;
                CommonDialog commonDialog = BaseDeviceListFragment.this.mDialogBTOn;
                if (commonDialog != null && commonDialog.isShowing()) {
                    BaseDeviceListFragment.this.mDialogBTOn.dismiss();
                }
                BaseDeviceListFragment baseDeviceListFragment = BaseDeviceListFragment.this;
                baseDeviceListFragment.mDialogBTOn = new CommonDialog(baseDeviceListFragment.mActivity, 0, 4, 0);
                BaseDeviceListFragment.this.mDialogBTOn.createDialog();
                BaseDeviceListFragment.this.mDialogBTOn.setCancelable(false);
                BaseDeviceListFragment baseDeviceListFragment2 = BaseDeviceListFragment.this;
                baseDeviceListFragment2.mDialogBTOn.setMessage(baseDeviceListFragment2.mActivity.getString(R.string.connect_popup_content));
            } else if (i == 12) {
                CommonDialog commonDialog2 = BaseDeviceListFragment.this.mDialogBTOn;
                if (commonDialog2 != null) {
                    commonDialog2.dismiss();
                    BaseDeviceListFragment.this.mDialogBTOn = null;
                }
                bluetoothDevice.getBluetoothClass();
                if (HostManagerUtilsRulesBTDevices.isShowingCondition(bluetoothDevice, true)) {
                    String address = bluetoothDevice.getAddress();
                    Bundle bundle = new Bundle();
                    bundle.putString(HMConnectFragment.EXTRA_DEVICE_ADDRESS, address);
                    bundle.putString(BaseDeviceListFragment.EXTRA_DEVICE_MODEL_NAME, BaseDeviceListFragment.this.mBluetoothDiscoveryUtility.getSimpleDeviceName(address));
                    bundle.putBoolean(SetupWizardWelcomeActivity.EXTRA_IS_FROM_PLUGIN, false);
                    BaseDeviceListFragment.this.mBluetoothDiscoveryUtility.setSuccessResult(bundle);
                }
            } else if (i == 10) {
                OldFormatConverter oldFormatConverter = new OldFormatConverter(GearRulesManager.getInstance());
                if (oldFormatConverter.isLEdevice(bluetoothDevice)) {
                    BluetoothDevice bRdevice = oldFormatConverter.getBRdevice(bluetoothDevice.getAddress());
                    if (bRdevice.getName() == null || bRdevice.getName().equalsIgnoreCase("")) {
                        String str4 = BaseDeviceListFragment.TAG;
                        Log.d(str4, "change device - " + bRdevice.getName());
                        str = HostManagerUtilsRulesBTDevices.getOriginalBTName(bRdevice.getAddress());
                        str2 = str;
                    } else {
                        str = bRdevice.getName();
                        str2 = BluetoothDeviceFactory.get().getAliasName(bRdevice);
                    }
                    String str5 = BaseDeviceListFragment.TAG;
                    Log.d(str5, "name and btAliasName- " + str + " " + str2);
                    bluetoothDeviceItem = new BluetoothDiscoveryUtility.BluetoothDeviceItem(str2, str, bRdevice.getAddress(), 0);
                } else {
                    bluetoothDeviceItem = new BluetoothDiscoveryUtility.BluetoothDeviceItem(BluetoothDeviceFactory.get().getAliasName(bluetoothDevice), bluetoothDevice.getName(), bluetoothDevice.getAddress(), 0);
                }
                String str6 = BaseDeviceListFragment.TAG;
                Log.d(str6, " goToNewDeviceItem address:: " + bluetoothDeviceItem.getAddress());
                if (!this.wasInBondingButCanceled) {
                    Log.d(BaseDeviceListFragment.TAG, "this device is in paired list so we have to remove it.");
                    BaseDeviceListFragment.this.mBluetoothDiscoveryUtility.removeFromPairedDevicesList(bluetoothDeviceItem);
                    BaseDeviceListFragment.this.mBluetoothDiscoveryUtility.addNewDevicesArrayList(bluetoothDeviceItem);
                    BluetoothDiscoveryUtility.SyncGearInterface syncGearInterface = BaseDeviceListFragment.this.mSyncGearInterface;
                    if (syncGearInterface != null) {
                        syncGearInterface.notifyDataSetChangedMethod(bluetoothDeviceItem, 2);
                        BaseDeviceListFragment.this.mSyncGearInterface.notifyDataSetChangedMethod(bluetoothDeviceItem, 1);
                    }
                } else {
                    Log.d(BaseDeviceListFragment.TAG, "not removing the device");
                    this.wasInBondingButCanceled = false;
                }
                CommonDialog commonDialog3 = BaseDeviceListFragment.this.mDialogBTOn;
                if (commonDialog3 != null) {
                    commonDialog3.dismiss();
                    BaseDeviceListFragment.this.mDialogBTOn = null;
                }
            }
        }

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onError(int i) {
            String str = BaseDeviceListFragment.TAG;
            Log.d(str, "ACMode: onError [" + i + "]");
            if (i != 0) {
                BluetoothDiscoveryUtility.SyncGearInterface syncGearInterface = BaseDeviceListFragment.this.mSyncGearInterface;
                if (syncGearInterface != null) {
                    syncGearInterface.stopConnectUI();
                }
                BaseDeviceListFragment.this.mLightConnectionManagerACMode.terminate();
            }
        }

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onLEScanDeviceForDiscovery(BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem, int i) {
            BluetoothDevice bluetoothDevice;
            String str;
            String str2 = BaseDeviceListFragment.TAG;
            Log.d(str2, "ACMode: onLEScanDeviceForDiscovery(" + bluetoothDeviceItem + ", " + i + ")");
            OldFormatConverter oldFormatConverter = new OldFormatConverter(GearRulesManager.getInstance());
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter == null || (str = bluetoothDeviceItem.address) == null) {
                bluetoothDevice = null;
            } else {
                bluetoothDevice = defaultAdapter.getRemoteDevice(str);
                String str3 = BaseDeviceListFragment.TAG;
                Log.d(str3, "get device - " + bluetoothDevice.getName());
            }
            BluetoothDiscoveryUtility.BluetoothDeviceItem bRDevice = oldFormatConverter.isLEdevice(bluetoothDevice) ? HostManagerUtilsRulesBTDevices.getBRDevice(bluetoothDevice) : bluetoothDeviceItem;
            if (!TextUtils.isEmpty(bRDevice.getName())) {
                BaseDeviceListFragment.this.leItemMap.put(bRDevice.getAddress(), bluetoothDeviceItem);
                String str4 = BaseDeviceListFragment.TAG;
                Log.d(str4, "leItemMap = " + BaseDeviceListFragment.this.leItemMap);
                BaseDeviceListFragment.this.mBluetoothDiscoveryUtility.addNewDevicesArrayList(bRDevice);
                BluetoothDiscoveryUtility.SyncGearInterface syncGearInterface = BaseDeviceListFragment.this.mSyncGearInterface;
                if (syncGearInterface != null) {
                    syncGearInterface.notifyDataSetChangedMethod(bRDevice, 1);
                    return;
                }
                return;
            }
            Log.d(BaseDeviceListFragment.TAG, "device name is empty");
        }

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onLeScanFinished(Set<BluetoothDevice> set, int i) {
            String str = BaseDeviceListFragment.TAG;
            Log.d(str, "ACMode: onLeScanFinished(" + set + ", " + i + ")");
            BluetoothDiscoveryUtility.SyncGearInterface syncGearInterface = BaseDeviceListFragment.this.mSyncGearInterface;
            if (syncGearInterface != null) {
                syncGearInterface.stopConnectUI();
            }
            BaseDeviceListFragment.this.mLightConnectionManagerACMode.terminate();
        }

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onScanFinished(Set<BluetoothDevice> set, int i) {
            String str = BaseDeviceListFragment.TAG;
            Log.d(str, "ACMode: onScanFinished(" + set + ", " + i + ")");
        }
    };
    protected GearRulesManager mGearRulesManager = null;
    protected boolean mIsBackPressed;
    protected DeviceDiscoveryManager mLightConnectionManagerACMode = null;
    private AdapterView<?> mParentBTOn = null;
    private CommonDialog mProgressDialog;
    BluetoothDiscoveryUtility.SyncGearInterface mSyncGearInterface = null;
    private int positionBTOn = -1;
    private String tempConnectedAddress = null;
    private View viewBTOn = null;
    private WearableDeviceController.IPairingListener wearableListener = new WearableDeviceController.IPairingListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment.AnonymousClass2 */

        @Override // com.samsung.android.app.watchmanager.setupwizard.WearableDeviceController.IPairingListener
        public void onBonding(BluetoothDevice bluetoothDevice) {
            String str = BaseDeviceListFragment.TAG;
            Log.d(str, "onBonding() device [" + bluetoothDevice + "]");
            BaseDeviceListFragment.this.showProgressDialog();
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.WearableDeviceController.IPairingListener
        public void onCancel() {
            Log.d(BaseDeviceListFragment.TAG, "onCancel()");
            if (BaseDeviceListFragment.this.mProgressDialog != null && BaseDeviceListFragment.this.mProgressDialog.isShowing()) {
                BaseDeviceListFragment.this.mProgressDialog.dismiss();
            }
            BaseDeviceListFragment.this.enableClick = true;
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.WearableDeviceController.IPairingListener
        public void onPaired(BluetoothDevice bluetoothDevice) {
            if (BaseDeviceListFragment.this.mProgressDialog != null && BaseDeviceListFragment.this.mProgressDialog.isShowing()) {
                BaseDeviceListFragment.this.mProgressDialog.dismiss();
            }
            String address = bluetoothDevice.getAddress();
            String str = BaseDeviceListFragment.TAG;
            Log.d(str, "onPaired() address [" + address + "]");
            Bundle bundle = new Bundle();
            bundle.putString(BaseDeviceListFragment.EXTRA_DEVICE_ADDRESS, address);
            bundle.putString(BaseDeviceListFragment.EXTRA_DEVICE_MODEL_NAME, BaseDeviceListFragment.this.mBluetoothDiscoveryUtility.getSimpleDeviceName(address));
            BaseDeviceListFragment.this.mBluetoothDiscoveryUtility.setSuccessResult(bundle);
        }
    };

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void connect(BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem) {
        if (bluetoothDeviceItem.type == 0) {
            String str = bluetoothDeviceItem.address;
            String str2 = TAG;
            Log.d(str2, "connect() address : " + str);
            if (str == null) {
                Log.e(TAG, "connect() address is null");
                return;
            }
            if (this.mBtAdapter == null) {
                this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            cancelBTAdapter();
            if (BluetoothDiscoveryUtility.isBTEnabled()) {
                String simpleBTNameByAddress = HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(str);
                if (getClass().getSimpleName().equalsIgnoreCase("SetupWizardDeviceListFragment")) {
                    SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_SETUP_DEVICE_LIST, SALogUtil.SA_LOG_EVENT_SELECT_DEVICE, "Select device", simpleBTNameByAddress);
                }
                BluetoothDiscoveryUtility bluetoothDiscoveryUtility = this.mBluetoothDiscoveryUtility;
                if (bluetoothDiscoveryUtility != null) {
                    bluetoothDiscoveryUtility.unregisterReceiver();
                }
                DeviceDiscoveryManager deviceDiscoveryManager = this.mLightConnectionManagerACMode;
                if (deviceDiscoveryManager != null) {
                    deviceDiscoveryManager.terminate();
                }
                pairing(str);
            } else {
                Log.d(TAG, " connect() Exceptional case BT is off");
            }
        } else {
            Log.w(TAG, "connect() type is not device");
        }
        this.mParentBTOn = null;
        this.viewBTOn = null;
        this.positionBTOn = -1;
        this.idBTOn = 0;
        this.mDisconnectWearableDevice = false;
        CommonDialog commonDialog = this.mDialogBTOn;
        if (commonDialog != null && commonDialog.isShowing()) {
            this.mDialogBTOn.dismiss();
            this.mDialogBTOn = null;
        }
    }

    private void pairing(String str) {
        if (!this.mDeviceController.isPaired(str)) {
            LoggerUtil.insertLog(getActivity(), "G021", "BT pairing", null);
        }
        Map<String, BluetoothDiscoveryUtility.BluetoothDeviceItem> map = this.leItemMap;
        if (map != null) {
            this.mDeviceController.setLeBluetoothItem(map.get(str));
        }
        this.mDeviceController.pair(str, this.wearableListener);
    }

    private void setTempItemValue(AdapterView<?> adapterView, View view, int i, long j, String str) {
        this.mParentBTOn = adapterView;
        this.viewBTOn = view;
        this.positionBTOn = i;
        this.idBTOn = j;
        this.mDisconnectWearableDevice = true;
        this.tempConnectedAddress = str;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showProgressDialog() {
        this.mProgressDialog = new CommonDialog(getActivity(), 0, 4, 0);
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.createDialog();
        this.mProgressDialog.setMessage(getActivity().getString(R.string.connect_popup_content));
    }

    /* access modifiers changed from: protected */
    public void baseInit() {
        if (!this.mGearRulesManager.isDeviceInfoAvailable()) {
            Log.d(TAG, "inside sync part");
            this.mBluetoothDiscoveryUtility.syncGearInfo();
            return;
        }
        createDeviceListView();
    }

    /* access modifiers changed from: protected */
    public void cancelBTAdapter() {
        this.cancelBTAdapterCalled = true;
        BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
        if (bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
            this.mBtAdapter.cancelDiscovery();
        }
    }

    /* access modifiers changed from: protected */
    public void createDeviceListView() {
        Log.d(TAG, "inside createDeviceListView()");
        this.mBluetoothDiscoveryUtility.registerReceiverForDiscovery();
        doCreateDeviceListView();
        doDiscovery();
    }

    /* access modifiers changed from: protected */
    public abstract void doCreateDeviceListView();

    /* access modifiers changed from: protected */
    public void doDiscovery() {
        PermissionFragment.verifyPermissions(this.mActivity, new PermissionFragment.IGrantedTask() {
            /* class com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment.AnonymousClass5 */

            @Override // com.samsung.android.app.watchmanager.setupwizard.PermissionFragment.IGrantedTask
            public void doTask() {
                Log.d(BaseDeviceListFragment.TAG, "mLaunchPluginTask :: allPermissionGranted");
                BaseDeviceListFragment baseDeviceListFragment = BaseDeviceListFragment.this;
                baseDeviceListFragment.cancelBTAdapterCalled = false;
                baseDeviceListFragment.mBluetoothDiscoveryUtility.doDiscovery();
            }
        }, PermissionUtils.INITIAL_PERMISSION);
    }

    /* access modifiers changed from: protected */
    public void doDiscoveryLE() {
        PermissionFragment.verifyPermissions(this.mActivity, new PermissionFragment.IGrantedTask() {
            /* class com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment.AnonymousClass6 */

            @Override // com.samsung.android.app.watchmanager.setupwizard.PermissionFragment.IGrantedTask
            public void doTask() {
                BaseDeviceListFragment.this.mBluetoothDiscoveryUtility.unregisterReceiver();
                Log.d(BaseDeviceListFragment.TAG, "mLaunchPluginTask :: allPermissionGranted : starting LE");
                BaseDeviceListFragment baseDeviceListFragment = BaseDeviceListFragment.this;
                baseDeviceListFragment.mLightConnectionManagerACMode = new DeviceDiscoveryManager(baseDeviceListFragment.mActivity, baseDeviceListFragment.mEasyPairingListenerACMode, 2);
                BaseDeviceListFragment.this.leItemMap.clear();
                BaseDeviceListFragment.this.mLightConnectionManagerACMode.startLeScan(null);
            }
        }, PermissionUtils.INITIAL_PERMISSION);
    }

    /* access modifiers changed from: protected */
    public void doOnItemClick() {
        this.enableClick = true;
        new Handler().postDelayed(new Runnable() {
            /* class com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment.AnonymousClass4 */

            public void run() {
                BaseDeviceListFragment baseDeviceListFragment = BaseDeviceListFragment.this;
                baseDeviceListFragment.mDeviceClickListener.onItemClick(baseDeviceListFragment.mParentBTOn, BaseDeviceListFragment.this.viewBTOn, BaseDeviceListFragment.this.positionBTOn, BaseDeviceListFragment.this.idBTOn);
            }
        }, 500);
    }

    /* access modifiers changed from: protected */
    public abstract BluetoothDiscoveryUtility.SyncGearInterface getSyncGearInterface();

    @Override // android.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.OnBackKeyListener
    public boolean onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        this.mIsBackPressed = true;
        cancelBTAdapter();
        this.mBtAdapter = null;
        Activity activity = this.mActivity;
        if (activity instanceof SetupWizardWelcomeActivity) {
            boolean startLastLaunchedPlugin = ((SetupWizardWelcomeActivity) activity).startLastLaunchedPlugin(false, null);
            String str = TAG;
            Log.d(str, "isPluginStarted" + startLastLaunchedPlugin + " mActivity:" + this.mActivity);
            Activity activity2 = this.mActivity;
            if (activity2 != null) {
                activity2.finish();
            }
        }
        return true;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "Inside onCreate");
        this.enableClick = true;
        this.mGearRulesManager = GearRulesManager.getInstance();
        this.mSyncGearInterface = getSyncGearInterface();
        this.mBluetoothDiscoveryUtility = new BluetoothDiscoveryUtility(this.mActivity, this.mSyncGearInterface);
        if (this.mBtAdapter == null) {
            this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        this.mDeviceController = new WearableDeviceController(this.mActivity);
    }

    public void onDestroy() {
        Log.d(TAG, "Inside onDestroy");
        this.mBtAdapter = null;
        CommonDialog commonDialog = this.mDialogBTOn;
        if (commonDialog != null) {
            if (commonDialog.isShowing()) {
                this.mDialogBTOn.dismiss();
            }
            this.mDialogBTOn = null;
        }
        this.mBluetoothDiscoveryUtility.stopSyncGearInfo();
        this.mBluetoothDiscoveryUtility.unregisterReceiver();
        DeviceDiscoveryManager deviceDiscoveryManager = this.mLightConnectionManagerACMode;
        if (deviceDiscoveryManager != null) {
            deviceDiscoveryManager.terminate();
        }
        WearableDeviceController wearableDeviceController = this.mDeviceController;
        if (wearableDeviceController != null) {
            wearableDeviceController.destroy();
            this.mDeviceController = null;
        }
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        ((FragmentLifecycleCallbacks) getActivity()).onFragmentDetached(1);
        this.mActivity = null;
    }

    public void onPause() {
        Log.d(TAG, "Inside onPause");
        cancelBTAdapter();
        stopConnectUI();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "Inside onResume()");
    }

    public void onStop() {
        Log.d(TAG, "Inside onStop");
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public abstract void startConnectUI();

    /* access modifiers changed from: protected */
    public abstract void stopConnectUI();
}
