package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.samsung.android.app.twatchmanager.btutil.OldFormatConverter;
import com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager;
import com.samsung.android.app.twatchmanager.factory.BluetoothDeviceFactory;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.log.LoggerUtil;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.model.GearInfo;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.ResourceLoader;
import com.samsung.android.app.twatchmanager.util.SALogUtil;
import com.samsung.android.app.twatchmanager.util.StringResourceManagerUtil;
import com.samsung.android.app.twatchmanager.util.UIUtils;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

public class PairingFragment extends Fragment implements OnBackKeyListener, SetupWizardWelcomeActivity.IMultiWindowListener {
    private static final String TAG = ("tUHM:" + PairingFragment.class.getSimpleName());
    View.OnClickListener cancelBtnListener = new View.OnClickListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass4 */

        public void onClick(View view) {
            SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_PAIRING_CONFIRM_PASSKEY, SALogUtil.SA_LOG_EVENT_PAIRING_CANCEL_CONFIRM_PASSKEY, "Cancel");
            String str = PairingFragment.TAG;
            Log.d(str, "cancelButton is clicked = " + PairingFragment.this.mDeviceId);
            if (!PairingFragment.this.showScanningLayout) {
                PairingFragment.this.setPairingConfirmation(false);
                PairingFragment.this.popCurrentFragment();
                return;
            }
            PairingFragment.this.startDeviceList();
        }
    };
    private GearRulesManager gearRulesManager;
    String groupName;
    private Handler handler = new Handler();
    private boolean isScanFinished = false;
    private boolean isStopped = false;
    private boolean mBondingFailed = false;
    private String mDeviceId;
    private String mDeviceName;
    private BluetoothDevice mEasyPairBluetoothDevice;
    private final DeviceDiscoveryManager.ResponseListener mEasyPairingListener = new DeviceDiscoveryManager.ResponseListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass2 */

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onBondStateChanged(BluetoothDevice bluetoothDevice, int i) {
            String str;
            String str2 = PairingFragment.TAG;
            Log.d(str2, "onBondStateChanged, device [" + bluetoothDevice + "], bond state [" + i + "]");
            if (bluetoothDevice != null && PairingFragment.this.mDeviceId != null && bluetoothDevice.getAddress().equalsIgnoreCase(PairingFragment.this.mDeviceId)) {
                if (10 == i) {
                    PairingFragment.this.mBondingFailed = true;
                    String str3 = PairingFragment.TAG;
                    Log.d(str3, "Bonding failed. send intent for statistics G032. mLEDeviceAddress = " + PairingFragment.this.mLEDeviceAddress);
                    if (PairingFragment.this.mLEDeviceAddress == null || PairingFragment.this.mLEDeviceAddress.isEmpty()) {
                        PairingFragment.this.uiHelper.setRunnable(PairingFragment.this.popCurrentFragmentRunnable);
                        PairingFragment.this.uiHelper.setOperation(5);
                    } else {
                        PairingFragment.this.showGearResetGuideDialog();
                    }
                    LoggerUtil.insertLog(PairingFragment.this.getActivity(), "G032", null, null);
                    str = SALogUtil.SA_LOG_EVENT_PAIRING_CANCEL;
                } else if (11 == i) {
                    LoggerUtil.insertLog(PairingFragment.this.getActivity(), "G005", null, null);
                    str = SALogUtil.SA_LOG_EVENT_PAIRING_OK;
                } else {
                    ((SetupWizardWelcomeActivity) PairingFragment.this.getActivity()).setPairedByTUHM(true);
                    PairingFragment.this.uiHelper.setRunnable(PairingFragment.this.startFragmentRunnable);
                    PairingFragment.this.uiHelper.setOperation(3);
                    return;
                }
                SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_PAIRING, str);
            }
        }

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onError(int i) {
            String str = PairingFragment.TAG;
            Log.d(str, "onError [" + i + "]");
            if (i != 0) {
                PairingFragment.this.popCurrentFragment();
            }
        }

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onLEScanDeviceForDiscovery(BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem, int i) {
            String str = PairingFragment.TAG;
            Log.d(str, "onLEScanDeviceForDiscovery(" + bluetoothDeviceItem + ", " + i + ")");
        }

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onLeScanFinished(Set<BluetoothDevice> set, int i) {
            String str = PairingFragment.TAG;
            Log.d(str, "onLeScanFinished(" + set + ", " + i + ")");
        }

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onScanFinished(Set<BluetoothDevice> set, int i) {
            String str = PairingFragment.TAG;
            Log.d(str, "onScanFinished(" + set + ", " + i + ")");
        }
    };
    private String mLEDeviceAddress;
    private DeviceDiscoveryManager mLightConnectionManager = null;
    private final BroadcastReceiver mReciever = new BroadcastReceiver() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            String str = PairingFragment.TAG;
            Log.d(str, "action = " + intent.getAction());
            if ("android.bluetooth.device.action.PAIRING_REQUEST".equals(intent.getAction()) && intent.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", -1) == 2) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                String str2 = PairingFragment.TAG;
                Log.d(str2, "mDeviceId = " + PairingFragment.this.mDeviceId + ", EXTRA_DEVICE = " + bluetoothDevice);
                if (PairingFragment.this.mDeviceId != null && bluetoothDevice != null && PairingFragment.this.mDeviceId.equals(bluetoothDevice.getAddress()) && intent.hasExtra("android.bluetooth.device.extra.PAIRING_KEY")) {
                    PairingFragment.this.setCreatBondView(intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", -1));
                }
                abortBroadcast();
            }
        }
    };
    private final DeviceDiscoveryManager.ResponseListener mScanningListener = new DeviceDiscoveryManager.ResponseListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass3 */

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onBondStateChanged(BluetoothDevice bluetoothDevice, int i) {
            String str = PairingFragment.TAG;
            Log.d(str, "onBondStateChanged, device [" + bluetoothDevice + "], bond state [" + i + "]");
            if (bluetoothDevice == null || PairingFragment.this.mEasyPairBluetoothDevice == null) {
                Log.e(PairingFragment.TAG, "Abnormal case, must be checked additionally");
            } else if (!bluetoothDevice.getAddress().equalsIgnoreCase(PairingFragment.this.mEasyPairBluetoothDevice.getAddress())) {
            } else {
                if (10 == i) {
                    LoggerUtil.insertLog(PairingFragment.this.getActivity(), "G032", null, null);
                    SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_WELCOME, SALogUtil.SA_LOG_EVENT_PAIRING_CANCEL);
                    PairingFragment.this.startDeviceList();
                } else if (11 == i) {
                    LoggerUtil.insertLog(PairingFragment.this.getActivity(), "G005", null, null);
                    SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_WELCOME, SALogUtil.SA_LOG_EVENT_PAIRING_OK);
                } else {
                    ((SetupWizardWelcomeActivity) PairingFragment.this.getActivity()).setPairedByTUHM(true);
                    PairingFragment.this.startFragmentAfterScanning(3);
                }
            }
        }

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onError(int i) {
            String str = PairingFragment.TAG;
            Log.d(str, "onError [" + i + "]");
            if (i != 0) {
                PairingFragment.this.startDeviceList();
            }
        }

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onLEScanDeviceForDiscovery(BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem, int i) {
            String str = PairingFragment.TAG;
            Log.d(str, "onLEScanDeviceForDiscovery(" + bluetoothDeviceItem + ", " + i + ")");
        }

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onLeScanFinished(Set<BluetoothDevice> set, int i) {
            Log.d(PairingFragment.TAG, "onLeScanFinished(" + set + ", " + i + ")");
            if (i != 0) {
                Log.d(PairingFragment.TAG, "Error in Light Connection manager. Starting DeviceList");
            } else if (PairingFragment.this.isScanFinished) {
                Log.d(PairingFragment.TAG, "Scan already finished. Repeated response.");
                return;
            } else {
                PairingFragment.this.isScanFinished = true;
                if (set != null && set.size() > 0) {
                    GearInfo gearInfo = null;
                    int i2 = 0;
                    for (BluetoothDevice bluetoothDevice : set) {
                        GearInfo gearInfo2 = GearRulesManager.getInstance().getGearInfo(HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(bluetoothDevice.getAddress()));
                        if ((gearInfo2 != null && gearInfo2.group.name.equals(PairingFragment.this.groupName)) || PairingFragment.this.groupName == null) {
                            i2++;
                            PairingFragment.this.mEasyPairBluetoothDevice = bluetoothDevice;
                            gearInfo = gearInfo2;
                        }
                    }
                    if (i2 > 1 || i2 == 0) {
                        Log.d(PairingFragment.TAG, "calling startDeviceList");
                    } else if (i2 == 1) {
                        if (PairingFragment.this.mEasyPairBluetoothDevice.getBondState() == 12 || (gearInfo != null && !gearInfo.requiresPairing)) {
                            final CommonDialog commonDialog = new CommonDialog(PairingFragment.this.getActivity(), 1, 0, 3);
                            Log.d(PairingFragment.TAG, "showing already paired dialog for [" + PairingFragment.this.mEasyPairBluetoothDevice.toString() + "]");
                            String aliasName = BluetoothDeviceFactory.get().getAliasName(PairingFragment.this.mEasyPairBluetoothDevice);
                            commonDialog.createDialog();
                            commonDialog.setCancelable(false);
                            commonDialog.setTitle(HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(PairingFragment.this.mEasyPairBluetoothDevice.getAddress()));
                            commonDialog.setMessage(PairingFragment.this.getActivity().getResources().getString(R.string.setup_connection_dialog_body, aliasName));
                            commonDialog.setOkBtnListener(new View.OnClickListener() {
                                /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass3.AnonymousClass1 */

                                public void onClick(View view) {
                                    Log.d(PairingFragment.TAG, "CommonDialog :: Ok Button pressed");
                                    commonDialog.dismiss();
                                    PairingFragment.this.startFragmentAfterScanning(3);
                                }
                            });
                            commonDialog.setCancelBtnListener(new View.OnClickListener() {
                                /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass3.AnonymousClass2 */

                                public void onClick(View view) {
                                    Log.d(PairingFragment.TAG, "CommonDialog :: Cancel Button pressed");
                                    commonDialog.dismiss();
                                    PairingFragment.this.startDeviceList();
                                }
                            });
                            return;
                        } else if (Build.VERSION.SDK_INT >= 19 && HostManagerUtils.isSamsungDevice()) {
                            PairingFragment.this.updatePairingFragment();
                            return;
                        } else if (PairingFragment.this.mLightConnectionManager.createBond(PairingFragment.this.mEasyPairBluetoothDevice.getAddress())) {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
            PairingFragment.this.startDeviceList();
        }

        @Override // com.samsung.android.app.twatchmanager.easypairing.DeviceDiscoveryManager.ResponseListener
        public void onScanFinished(Set<BluetoothDevice> set, int i) {
            String str = PairingFragment.TAG;
            Log.d(str, "onScanFinished(" + set + ", " + i + ")");
        }
    };
    private final BluetoothDiscoveryUtility.SyncGearInterface mSyncGearInterface = new BluetoothDiscoveryUtility.SyncGearInterface() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass9 */

        @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.SyncGearInterface
        public void notifyDataSetChangedMethod(BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem, int i) {
            Log.d(PairingFragment.TAG, "notifyDataSetChangedMethod()");
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.SyncGearInterface
        public void onSyncCompleteCall() {
            Log.d(PairingFragment.TAG, "onSyncCompleteCall()");
            PairingFragment.this.startEasyPairing();
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.SyncGearInterface
        public void refreshBluetoothAdaptorStateChanged() {
            Log.d(PairingFragment.TAG, "refreshBluetoothAdaptorStateChanged()");
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.SyncGearInterface
        public void stopConnectUI() {
            Log.d(PairingFragment.TAG, "stopConnectUI()");
        }
    };
    View.OnClickListener okBtnListener = new View.OnClickListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass5 */

        public void onClick(View view) {
            String str = PairingFragment.TAG;
            Log.d(str, "pair_to_gear is clicked = " + PairingFragment.this.mDeviceId);
            SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_PAIRING_CONFIRM_PASSKEY, SALogUtil.SA_LOG_EVENT_PAIRING_OK_CONFIRM_PASSKEY, "Start");
            PairingFragment.this.setOKButtonAsProgress();
            PairingFragment.this.setPairingConfirmation(true);
        }
    };
    private Runnable popCurrentFragmentRunnable = new Runnable() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass11 */

        public void run() {
            PairingFragment.this.popCurrentFragment();
        }
    };
    private ImageView previewImage;
    private boolean showScanningLayout = false;
    private Runnable startFragmentRunnable = new Runnable() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass10 */

        public void run() {
            PairingFragment.this.startFragment(3);
        }
    };
    private PairingFragmentUIHelper uiHelper;

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initiateEasyPairing() {
        Log.d(TAG, "initiateEasyPairing()");
        if (getActivity() == null) {
            String str = TAG;
            Log.d(str, SetupWizardWelcomeActivity.class.getSimpleName() + " detached so return");
            return;
        }
        this.isScanFinished = false;
        LoggerUtil.insertLog(getActivity(), "G019", "Easy paring", null);
        BluetoothDiscoveryUtility.turnOnBT(getActivity(), new BluetoothDiscoveryUtility.IBTStatusOnListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass8 */

            @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.IBTStatusOnListener
            public void onStatus(boolean z) {
                if (z) {
                    PairingFragment.this.initiateEasyPairingSub();
                } else {
                    PairingFragment.this.getActivity().finish();
                }
            }
        }, true);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initiateEasyPairingSub() {
        Log.d(TAG, "initiateEasyPairingSub()");
        BluetoothDiscoveryUtility bluetoothDiscoveryUtility = new BluetoothDiscoveryUtility(getActivity(), this.mSyncGearInterface);
        if (!this.gearRulesManager.isDeviceInfoAvailable()) {
            Log.d(TAG, "Gear info is not available. Staring Sync");
            bluetoothDiscoveryUtility.syncGearInfo();
            return;
        }
        startEasyPairing();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void popCurrentFragment() {
        Log.d(TAG, "popCurrentFragment");
        stopConnectionManager();
        try {
            if (getActivity() == null) {
                return;
            }
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else if (getActivity() != null && (getActivity() instanceof SetupWizardWelcomeActivity)) {
                getActivity().finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setCreatBondView(int i) {
        this.uiHelper.setPincode(i);
        GearInfo gearInfo = this.gearRulesManager.getGearInfo(this.mDeviceName);
        this.uiHelper.setOperation(1, gearInfo == null ? StringResourceManagerUtil.WATCH_TYPE : gearInfo.group.wearableType);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setOKButtonAsProgress() {
        this.uiHelper.setOperation(2);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setPairingConfirmation(boolean z) {
        BluetoothDevice remoteDevice = OldFormatConverter.getRemoteDevice(this.mDeviceId);
        try {
            remoteDevice.getClass().getMethod("setPairingConfirmation", Boolean.TYPE).invoke(remoteDevice, Boolean.valueOf(z));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showGearResetGuideDialog() {
        Log.d(TAG, "showGearResetGuideDialog()");
        GearInfo gearInfo = this.gearRulesManager.getGearInfo(this.mDeviceName);
        if (gearInfo == null || !gearInfo.group.resetOption) {
            popCurrentFragment();
            return;
        }
        final CommonDialog commonDialog = new CommonDialog(getActivity(), 0, 0, 1);
        commonDialog.createDialog();
        commonDialog.setCancelable(false);
        commonDialog.setMessage(getResources().getString(R.string.gear_reset_guide));
        commonDialog.setOkBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass7 */

            public void onClick(View view) {
                commonDialog.dismiss();
                PairingFragment.this.popCurrentFragment();
            }
        });
    }

    private void startCreateBond() {
        this.mLightConnectionManager = new DeviceDiscoveryManager(getActivity(), this.mEasyPairingListener);
        this.mLightConnectionManager.createBond(this.mDeviceId);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startDeviceList() {
        Log.d(TAG, "startDeviceList()");
        stopConnectionManager();
        Activity activity = getActivity();
        if (activity instanceof SetupWizardWelcomeActivity) {
            SetupWizardWelcomeActivity setupWizardWelcomeActivity = (SetupWizardWelcomeActivity) activity;
            setupWizardWelcomeActivity.setLaunchMode(GlobalConst.LAUNCH_MODE_DEVICE_LIST);
            Bundle bundle = new Bundle();
            bundle.putString(GlobalConst.GROUP_NAME_ARG, this.groupName);
            bundle.putBoolean(GlobalConst.EXTRA_FROM_PAIRING_SCREEN, true);
            setupWizardWelcomeActivity.updateFragment(4, bundle);
        }
        popCurrentFragment();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startEasyPairing() {
        String str;
        String str2 = TAG;
        Log.d(str2, "startEasyPairing() for groupName = " + this.groupName);
        if (getActivity() == null) {
            startDeviceList();
            return;
        }
        this.mLightConnectionManager = new DeviceDiscoveryManager(getActivity(), this.mScanningListener);
        List<String> list = null;
        if (this.gearRulesManager.isDeviceInfoAvailable() && (str = this.groupName) != null) {
            list = this.gearRulesManager.getGroupDeviceNames(str);
        }
        String str3 = TAG;
        Log.d(str3, "startEasyPairing() filterList:" + list);
        SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_PICK_GEAR, SALogUtil.SA_LOG_EVENT_PICK_ITEM, "Select product", list.toString());
        this.mLightConnectionManager.startLeScan(list);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startFragment(int i) {
        String str = TAG;
        Log.d(str, "startFragment() :" + i);
        Bundle bundle = new Bundle();
        bundle.putString(GlobalConst.EXTRA_DEVICE_ADDRESS, this.mDeviceId);
        bundle.putString(GlobalConst.EXTRA_DEVICE_MODEL_NAME, this.mDeviceName);
        bundle.putBoolean(GlobalConst.EXTRA_FROM_PAIRING_SCREEN, true);
        Activity activity = getActivity();
        if (activity instanceof SetupWizardWelcomeActivity) {
            SetupWizardWelcomeActivity setupWizardWelcomeActivity = (SetupWizardWelcomeActivity) activity;
            setupWizardWelcomeActivity.setLaunchMode(GlobalConst.LAUNCH_MODE_DEVICE_LIST);
            setupWizardWelcomeActivity.updateFragment(i, bundle);
        }
        popCurrentFragment();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startFragmentAfterScanning(int i) {
        String str;
        String str2;
        String str3 = TAG;
        Log.d(str3, "startFragment() type:" + i + " isStopped:" + this.isStopped);
        if (!this.isStopped) {
            stopConnectionManager();
            Bundle bundle = new Bundle();
            bundle.putString(GlobalConst.EXTRA_DEVICE_ADDRESS, this.mEasyPairBluetoothDevice.getAddress());
            if (this.mEasyPairBluetoothDevice.getName() != null) {
                str2 = GlobalConst.EXTRA_DEVICE_MODEL_NAME;
                str = this.mEasyPairBluetoothDevice.getName();
            } else {
                str2 = GlobalConst.EXTRA_DEVICE_MODEL_NAME;
                str = HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(this.mEasyPairBluetoothDevice.getAddress());
            }
            bundle.putString(str2, str);
            String str4 = this.groupName;
            if (str4 != null) {
                bundle.putString(GlobalConst.GROUP_NAME_ARG, str4);
            }
            Activity activity = getActivity();
            if (activity instanceof SetupWizardWelcomeActivity) {
                SetupWizardWelcomeActivity setupWizardWelcomeActivity = (SetupWizardWelcomeActivity) activity;
                setupWizardWelcomeActivity.setLaunchMode(GlobalConst.LAUNCH_MODE_DEVICE_LIST);
                setupWizardWelcomeActivity.updateFragment(i, bundle);
            }
            popCurrentFragment();
        }
    }

    private void stopConnectionManager() {
        String str = TAG;
        Log.d(str, "stopConnectionManager()" + this.mLightConnectionManager);
        DeviceDiscoveryManager deviceDiscoveryManager = this.mLightConnectionManager;
        if (deviceDiscoveryManager != null) {
            deviceDiscoveryManager.terminate();
            this.mLightConnectionManager = null;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updatePairingFragment() {
        GearInfo gearInfo;
        this.showScanningLayout = false;
        stopConnectionManager();
        this.mDeviceId = this.mEasyPairBluetoothDevice.getAddress();
        this.mDeviceName = this.mEasyPairBluetoothDevice.getName();
        this.mEasyPairBluetoothDevice = null;
        if (this.mDeviceName == null) {
            this.mDeviceName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(this.mDeviceId);
        }
        if (this.groupName == null && (gearInfo = GearRulesManager.getInstance().getGearInfo(this.mDeviceName)) != null) {
            this.groupName = gearInfo.group.name;
        }
        startCreateBond();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
        getActivity().registerReceiver(this.mReciever, intentFilter);
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.OnBackKeyListener
    public boolean onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        return true;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "inside onCreateView()");
        this.gearRulesManager = GearRulesManager.getInstance();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ImageView.ScaleType scaleType;
        ImageView imageView;
        Log.d(TAG, "onCreateView()");
        super.onCreateView(layoutInflater, viewGroup, bundle);
        Bundle arguments = getArguments();
        this.groupName = null;
        if (arguments != null) {
            this.showScanningLayout = arguments.getBoolean(GlobalConst.SHOW_SCANNING_LAYOUT);
            this.groupName = arguments.getString(GlobalConst.GROUP_NAME_ARG);
            this.mDeviceId = arguments.getString(GlobalConst.EXTRA_DEVICE_ADDRESS);
            this.mDeviceName = arguments.getString(GlobalConst.EXTRA_DEVICE_MODEL_NAME);
            this.mLEDeviceAddress = arguments.getString(GlobalConst.LE_DEVICE_ADDRESS, "");
            String str = TAG;
            Log.d(str, " From bundle: groupName:" + this.groupName + " mDeviceId:" + this.mDeviceId + " mDeviceName:" + this.mDeviceName + " mLEDeviceAddress = " + this.mLEDeviceAddress + " showScanningLayout = " + this.showScanningLayout);
        }
        if (this.mDeviceName == null) {
            this.mDeviceName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(this.mDeviceId);
        }
        if (this.groupName == null) {
            GearInfo gearInfo = GearRulesManager.getInstance().getGearInfo(this.mDeviceName);
            if (gearInfo != null) {
                this.groupName = gearInfo.group.name;
            }
            String str2 = TAG;
            Log.d(str2, " groupName:" + this.groupName);
        }
        View inflate = layoutInflater.inflate(R.layout.device_scanning_layout, viewGroup, false);
        UIUtils.adjustLogoMargin(inflate.findViewById(R.id.gearManagerLogo));
        this.previewImage = (ImageView) inflate.findViewById(R.id.topView);
        this.uiHelper = new PairingFragmentUIHelper(getActivity(), inflate);
        if (this.mDeviceId != null && !this.showScanningLayout) {
            startCreateBond();
        }
        this.uiHelper.setCancelButtonListener(this.cancelBtnListener);
        this.uiHelper.setOkBtnListener(this.okBtnListener);
        String pairingImage = GearRulesManager.getInstance().getPairingImage(this.groupName);
        if (pairingImage != null) {
            this.uiHelper.setPairingImage(ResourceLoader.getDrawableId(getActivity(), pairingImage));
        }
        UIUtils.setHeaderImageWithRules(getActivity(), this.groupName, this.previewImage, inflate.findViewById(R.id.gearManagerLogo));
        if (HostManagerUtils.isTablet()) {
            if (UIUtils.isLandScapeMode(getActivity())) {
                imageView = this.previewImage;
                scaleType = ImageView.ScaleType.FIT_CENTER;
            } else {
                imageView = this.previewImage;
                scaleType = ImageView.ScaleType.FIT_XY;
            }
            imageView.setScaleType(scaleType);
            this.previewImage.setMaxHeight((int) getResources().getDimension(R.dimen.promotion_top_gradation_height));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.previewImage.getLayoutParams();
            layoutParams.addRule(14);
            this.previewImage.setLayoutParams(layoutParams);
        } else {
            this.previewImage.setScaleType(ImageView.ScaleType.FIT_START);
        }
        return inflate;
    }

    public void onDestroy() {
        Log.d(TAG, "onDestory() starts");
        this.previewImage.setImageBitmap(null);
        stopConnectionManager();
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        ((FragmentLifecycleCallbacks) getActivity()).onFragmentDetached(1);
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "inside onPause()");
        if (!this.showScanningLayout) {
            getActivity().unregisterReceiver(this.mReciever);
        }
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "inside onResume()");
        if (!this.showScanningLayout) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
            getActivity().registerReceiver(this.mReciever, intentFilter);
        }
        if (this.mBondingFailed) {
            popCurrentFragment();
            this.mBondingFailed = false;
        }
    }

    public void onStart() {
        super.onStart();
        Log.d(TAG, "inside onstart()");
        if (this.showScanningLayout) {
            this.isStopped = false;
        }
    }

    public void onStop() {
        super.onStop();
        Log.d(TAG, "inside onStop()");
        if (this.showScanningLayout) {
            this.isStopped = true;
            stopConnectionManager();
            Log.d(TAG, "removing current fragment");
            getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, bundle);
        view.setImportantForAccessibility(1);
        this.handler.post(new Runnable() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragment.AnonymousClass6 */

            public void run() {
                if (PairingFragment.this.showScanningLayout) {
                    String str = PairingFragment.TAG;
                    Log.d(str, "showScanningLayout = " + PairingFragment.this.showScanningLayout + " currentClickGroup = " + PairingFragment.this.groupName);
                    PairingFragment.this.initiateEasyPairing();
                }
            }
        });
        Activity activity = getActivity();
        this.uiHelper.updateViewsByMultiWindowMode((activity == null || !(activity instanceof SetupWizardWelcomeActivity)) ? false : ((SetupWizardWelcomeActivity) getActivity()).getCurrentMultiWindowMode());
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.IMultiWindowListener
    public void updateAfterMultiWindowChanged(boolean z) {
        this.uiHelper.updateViewsByMultiWindowMode(z);
    }
}
