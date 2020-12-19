package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.model.GearInfo;
import com.samsung.android.app.twatchmanager.packagecontroller.PackageControllerFactory;
import com.samsung.android.app.twatchmanager.update.FailDialogHelper;
import com.samsung.android.app.twatchmanager.update.PluginExecutor;
import com.samsung.android.app.twatchmanager.update.StubAPIHelper;
import com.samsung.android.app.twatchmanager.update.UpdateDownloadManager;
import com.samsung.android.app.twatchmanager.update.UpdateInstallData;
import com.samsung.android.app.twatchmanager.update.UpdateInstallManager;
import com.samsung.android.app.twatchmanager.update.UpdatePackageSet;
import com.samsung.android.app.twatchmanager.update.UpdatePlayStoreManager;
import com.samsung.android.app.twatchmanager.update.UpdateProviderInstallManager;
import com.samsung.android.app.twatchmanager.util.ActivityUtils;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.twatchmanager.util.PermissionUtils;
import com.samsung.android.app.twatchmanager.util.ResourceLoader;
import com.samsung.android.app.twatchmanager.util.SALogUtil;
import com.samsung.android.app.twatchmanager.util.StringResourceManagerUtil;
import com.samsung.android.app.twatchmanager.util.UIUtils;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.HMConnectionManager;
import com.samsung.android.app.watchmanager.setupwizard.PermissionFragment;
import com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity;
import com.samsung.td.particlesystem.watch_oobe.ParticleViewManager_Watch_OOBE;
import com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HMConnectFragment extends Fragment implements OnBackKeyListener, View.OnClickListener, SetupWizardWelcomeActivity.IMultiWindowListener {
    public static final String EXTRA_DEVICE_ADDRESS = "device_address";
    private static final int FRAGMENT_MINIMUM_SHOWN_TIME = 1000;
    private static final String TAG = ("tUHM:[Conn]" + HMConnectFragment.class.getSimpleName());
    private RelativeLayout actionbarLayout;
    private TextView cancelButton;
    private ViewStub downInstallLayoutStub;
    private ViewSwitcher downloadInstallViewSwitcher;
    private boolean isSwitching;
    private RelativeLayout logoHeaderLayout;
    private Activity mActivity;
    private final HMConnectionManager.HMConnectionManagerCallback mCallback = new HMConnectionManager.HMConnectionManagerCallback() {
        /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass14 */

        @Override // com.samsung.android.app.watchmanager.setupwizard.HMConnectionManager.HMConnectionManagerCallback
        public void onFinished(final boolean z) {
            if (HMConnectFragment.this.mActivity != null) {
                HMConnectFragment.this.mActivity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass14.AnonymousClass3 */

                    public void run() {
                        if (z) {
                            PermissionFragment.verifyPermissions(HMConnectFragment.this.mActivity, HMConnectFragment.this.mPermissionTask, PermissionUtils.INITIAL_PERMISSION);
                        } else {
                            HMConnectFragment.this.mActivity.finish();
                        }
                    }
                });
            }
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.HMConnectionManager.HMConnectionManagerCallback
        public void onInitEnd(final String str) {
            String str2 = HMConnectFragment.TAG;
            Log.d(str2, "onInitEnd() starts... deviceSimpleName : " + str);
            if (HMConnectFragment.this.mActivity != null) {
                HMConnectFragment.this.mActivity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass14.AnonymousClass1 */

                    public void run() {
                        if (!TextUtils.isEmpty(str)) {
                            HMConnectFragment.this.mHMConnManager.setLaunchMode(HMConnectFragment.this.getActivity());
                            HMConnectFragment.this.setDeviceSpecificDownloadLayout(str);
                            return;
                        }
                        HMConnectFragment.this.mActivity.finish();
                    }
                });
            }
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.HMConnectionManager.HMConnectionManagerCallback
        public void onRulesSyncEnd(final boolean z, final boolean z2) {
            if (HMConnectFragment.this.mActivity != null) {
                HMConnectFragment.this.mActivity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass14.AnonymousClass2 */

                    public void run() {
                        if (!z) {
                            HMConnectFragment hMConnectFragment = HMConnectFragment.this;
                            hMConnectFragment.showDownloadFailPopup(UpdateDownloadManager.ErrorCode.DOWNLOAD_URL_INVALID, hMConnectFragment.mActivity);
                        } else if (z2) {
                            HMConnectFragment.this.showRebootDialog();
                        }
                    }
                });
            }
        }
    };
    private UpdateDownloadManager.IDownloadManagerCallback mDownloadCallback = new UpdateDownloadManager.IDownloadManagerCallback() {
        /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass15 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onDownloadAvailable(HashMap<String, StubAPIHelper.XMLResult> hashMap, int i) {
            String str = HMConnectFragment.TAG;
            Log.d(str, "onDownloadAvailable() mWatchParticleView : " + HMConnectFragment.this.mWatchParticleView);
            if (HMConnectFragment.this.mWatchParticleView != null) {
                HMConnectFragment.this.mWatchParticleView.getParticleView().a(1);
            }
            GearInfo gearInfoToConnect = HMConnectFragment.this.mHMConnManager.getGearInfoToConnect();
            HMConnectFragment.this.pluginInfo.setText(gearInfoToConnect.pluginAppName + "\n" + (i / 1048576) + "MB");
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onDownloading(int i, double d2) {
            String format = NumberFormat.getInstance().format((long) i);
            TextView textView = HMConnectFragment.this.progressPercentageTextView;
            textView.setText(format + "%");
            if (i % 20 == 0) {
                String str = HMConnectFragment.TAG;
                Log.d(str, " Download percentage:" + i);
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onFailToDownload(final UpdateDownloadManager.ErrorCode errorCode) {
            String str = HMConnectFragment.TAG;
            Log.d(str, "onFailToDownload() fail reason : " + errorCode.toString());
            if (HMConnectFragment.this.mActivity != null) {
                HMConnectFragment.this.mActivity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass15.AnonymousClass1 */

                    public void run() {
                        int i = AnonymousClass19.$SwitchMap$com$samsung$android$app$twatchmanager$update$UpdateDownloadManager$ErrorCode[errorCode.ordinal()];
                        if (i == 1 || i == 2 || i == 3 || i == 4) {
                            HMConnectFragment hMConnectFragment = HMConnectFragment.this;
                            hMConnectFragment.showDownloadFailPopup(errorCode, hMConnectFragment.mActivity);
                        } else if (i == 5) {
                            HMConnectFragment.this.confirmAllProviderInstallations();
                        }
                    }
                });
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onFinishDownload(Map<String, String> map) {
            String btAddressToConnect = HMConnectFragment.this.mHMConnManager.getBtAddressToConnect();
            String deviceNameToConnect = HMConnectFragment.this.mHMConnManager.getDeviceNameToConnect();
            HMConnectFragment hMConnectFragment = HMConnectFragment.this;
            hMConnectFragment.mInstallManager = new UpdateInstallManager(hMConnectFragment.mInstallManagerCallback, btAddressToConnect, deviceNameToConnect, map, false);
            HMConnectFragment.this.mInstallManager.pluginInstallProcess();
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onStartDownload(double d2) {
            int i;
            TextView textView;
            HMConnectFragment.this.mTotalSizeInMB = d2;
            if (HostManagerUtils.isTablet()) {
                textView = HMConnectFragment.this.messageTextView;
                i = R.string.setup_message_download_tablet;
            } else {
                textView = HMConnectFragment.this.messageTextView;
                i = R.string.setup_message_download;
            }
            textView.setText(i);
            HMConnectFragment.this.downloadInstallViewSwitcher.setVisibility(0);
            HMConnectFragment.this.pluginInfo.setVisibility(0);
            HMConnectFragment.this.pluginInfoTitle.setVisibility(0);
            HMConnectFragment.this.cancelButton.setVisibility(0);
            HMConnectFragment.this.hideTransitionLayout();
        }
    };
    private UpdateDownloadManager mDownloadManager;
    private HMConnectionManager mHMConnManager;
    private UpdateInstallManager mInstallManager = null;
    private UpdateInstallManager.IInstallManagerCallback mInstallManagerCallback = new UpdateInstallManager.IInstallManagerCallback() {
        /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass16 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onDisconnectBeforePluginInstall(String str) {
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onEndOfInstall() {
            if (HMConnectFragment.this.mActivity != null) {
                HMConnectFragment.this.mActivity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass16.AnonymousClass3 */

                    public void run() {
                        if (HMConnectFragment.this.mWatchParticleView == null || HMConnectFragment.this.mWatchParticleView.getParticleView().getSceneType() != 1) {
                            HMConnectFragment.this.fullInstallationComplete();
                        } else {
                            HMConnectFragment.this.mWatchParticleView.getParticleView().a(4);
                        }
                    }
                });
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onFailToInstall(final int i, String str) {
            if (HMConnectFragment.this.mActivity != null) {
                HMConnectFragment.this.mActivity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass16.AnonymousClass2 */

                    public void run() {
                        HMConnectFragment hMConnectFragment = HMConnectFragment.this;
                        hMConnectFragment.showInstallErrorPopup(hMConnectFragment.mActivity, i);
                    }
                });
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onInstallUHM() {
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onStartInstall() {
            Log.d(HMConnectFragment.TAG, "onStartInstall() starts...");
            if (HMConnectFragment.this.mActivity != null) {
                HMConnectFragment.this.mActivity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass16.AnonymousClass1 */

                    public void run() {
                        if (!(HMConnectFragment.this.mWatchParticleView == null || HMConnectFragment.this.mWatchParticleView.getParticleView().getSceneType() == 1)) {
                            HMConnectFragment.this.mWatchParticleView.getParticleView().a(1);
                        }
                        HMConnectFragment.this.setButtonEnable(false);
                        HMConnectFragment.this.hideTransitionLayout();
                    }
                });
            }
        }
    };
    private final PermissionFragment.IImprovedGrantedTask mPermissionTask = new PermissionFragment.IImprovedGrantedTask() {
        /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass13 */

        @Override // com.samsung.android.app.watchmanager.setupwizard.PermissionFragment.IGrantedTask
        public void doTask() {
            Log.d(HMConnectFragment.TAG, "checkAndStartDownload :: allPermissionGranted");
            HMConnectFragment.this.checkAndStartDownload();
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.PermissionFragment.IImprovedGrantedTask
        public void onFinish() {
            Log.d(HMConnectFragment.TAG, "checkAndStartDownload :: permissions are not granted");
        }
    };
    private UpdatePlayStoreManager.IPlayStoreUpdateCallback mPlayStoreCallback = new UpdatePlayStoreManager.IPlayStoreUpdateCallback() {
        /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass17 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdatePlayStoreManager.IPlayStoreUpdateCallback
        public void onFinishUpdate(final boolean z, String str) {
            String str2 = HMConnectFragment.TAG;
            Log.d(str2, "mPlayStoreCallback.onFinishUpdate() isSuccess : " + z);
            if (HMConnectFragment.this.mActivity != null) {
                HMConnectFragment.this.mActivity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass17.AnonymousClass1 */

                    public void run() {
                        if (z) {
                            new Handler().postDelayed(new Runnable() {
                                /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass17.AnonymousClass1.AnonymousClass1 */

                                public void run() {
                                    HMConnectFragment.this.mInstallManagerCallback.onEndOfInstall();
                                }
                            }, 2000);
                            return;
                        }
                        Log.d(HMConnectFragment.TAG, "onInstallationResult() finish()");
                        HMConnectFragment.this.mActivity.finish();
                    }
                });
            }
        }
    };
    private UpdateProviderInstallManager mProviderInstallManager;
    private double mTotalSizeInMB = 0.0d;
    private UpdatePlayStoreManager mUpdatePlayStoreManager = null;
    private ParticleViewManager_Watch_OOBE mWatchParticleView;
    private TextView messageTextView;
    private ParticleView_Watch_OOBE_Base.a particleAnimatorListener = new ParticleView_Watch_OOBE_Base.a() {
        /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass12 */

        @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base.a
        public void onEndParticleState(int i) {
            String str = HMConnectFragment.TAG;
            Log.d(str, "onEndParticleState state : " + i);
            if (i == 4) {
                if (HMConnectFragment.this.mWatchParticleView != null) {
                    HMConnectFragment.this.mWatchParticleView.getParticleView().b();
                }
                HMConnectFragment.this.fullInstallationComplete();
            }
        }

        @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base.a
        public void onParticleState(int i) {
            String str = HMConnectFragment.TAG;
            Log.d(str, "onParticleState state : " + i);
        }

        @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base.a
        public void onProgressOpacityUpdate(float f) {
        }

        @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base.a
        public void onWatchOpacityUpdate(float f) {
        }
    };
    private TextView pluginInfo;
    private TextView pluginInfoTitle;
    private ImageView previewImage;
    private TextView progressPercentageTextView;
    private String targetAppStorePkg = null;
    private RelativeLayout transitionLayout;
    private RelativeLayout transitionLayoutSwitching;

    /* access modifiers changed from: package-private */
    /* renamed from: com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment$19  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass19 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType = new int[FailDialogHelper.FailType.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$app$twatchmanager$update$UpdateDownloadManager$ErrorCode = new int[UpdateDownloadManager.ErrorCode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|(2:13|14)|15|(2:17|18)|19|21|22|23|24|25|26|27|28|(3:29|30|32)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(22:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|(2:17|18)|19|21|22|23|24|25|26|27|28|(3:29|30|32)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(24:0|1|2|3|5|6|7|(2:9|10)|11|13|14|15|17|18|19|21|22|23|24|25|26|27|28|(3:29|30|32)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0053 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x005d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x0067 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0071 */
        static {
            /*
            // Method dump skipped, instructions count: 124
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass19.<clinit>():void");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void callPluginActivity() {
        Log.d(TAG, "callPluginActivity() starts...");
        String pluginPackageNameToConnect = this.mHMConnManager.getPluginPackageNameToConnect();
        String btAddressToConnect = this.mHMConnManager.getBtAddressToConnect();
        String deviceNameToConnect = this.mHMConnManager.getDeviceNameToConnect();
        Activity activity = this.mActivity;
        if (activity != null && (activity instanceof SetupWizardWelcomeActivity)) {
            int launchMode = ((SetupWizardWelcomeActivity) activity).getLaunchMode();
            if (HostManagerUtils.verifyPluginActivity(this.mActivity, pluginPackageNameToConnect)) {
                PluginExecutor.requestStartPlugin(this.mActivity, pluginPackageNameToConnect, btAddressToConnect, deviceNameToConnect, null, launchMode, this.isSwitching, null);
            } else {
                Log.e(TAG, "callPluginActivity(), abnormal case. Plugin could not be launched");
            }
        }
    }

    private void callPluginActivityWithDelay() {
        Log.d(TAG, "callPluginActivityWithDelay() starts...");
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass1 */

            public void run() {
                HMConnectFragment.this.callPluginActivity();
            }
        }, 1000);
    }

    private void changeStatusBarColor() {
        int i;
        Window window = this.mActivity.getWindow();
        if (window != null && (i = Build.VERSION.SDK_INT) >= 21 && i < 23) {
            window.setStatusBarColor(0);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void checkAndStartDownload() {
        Set<String> makePackageListToDownload = makePackageListToDownload();
        if (!makePackageListToDownload.isEmpty()) {
            boolean isLocalInstallation = InstallationUtils.isLocalInstallation();
            String str = TAG;
            Log.d(str, "checkAndStartDownload() fakeServerExists : " + isLocalInstallation);
            if (isLocalInstallation) {
                simulateDownload(makePackageListToDownload);
            } else if (HostManagerUtils.isSamsungDevice() || !HostManagerUtils.isPlayStoreAvailable(TWatchManagerApplication.getAppContext())) {
                checkDownloadViaGalaxyStore(makePackageListToDownload);
            } else {
                checkDownloadViaPlayStore(makePackageListToDownload);
            }
        } else {
            this.mDownloadCallback.onFailToDownload(UpdateDownloadManager.ErrorCode.DOWNLOAD_NOT_REQUIRED);
        }
    }

    private void checkDownloadViaGalaxyStore(Set<String> set) {
        Log.d(TAG, "checkDownloadViaGalaxyStore() starts...");
        this.mDownloadManager = new UpdateDownloadManager(this.mDownloadCallback, set, false);
        this.mDownloadManager.startUpdateDownloadManager(TWatchManagerApplication.getAppContext());
    }

    private void checkDownloadViaPlayStore(final Set<String> set) {
        Log.d(TAG, "checkDownloadViaPlayStore()");
        final CommonDialog commonDialog = new CommonDialog(this.mActivity, 1, 0, 1);
        commonDialog.createDialog();
        commonDialog.setTitle(getString(R.string.uhm_update_notice));
        commonDialog.setCancelable(false);
        String string = getResources().getString(R.string.install_from_play_store_device_type_watch);
        try {
            String str = this.mHMConnManager.getGearInfoToConnect().group.wearableType;
            if (StringResourceManagerUtil.WATCH_TYPE.equalsIgnoreCase(str)) {
                string = getString(R.string.install_from_play_store_device_type_watch);
            } else if (StringResourceManagerUtil.BAND_TYPE.equalsIgnoreCase(str)) {
                string = getString(R.string.install_from_play_store_device_type_band);
            } else if (StringResourceManagerUtil.EARBUD_TYPE.equalsIgnoreCase(str)) {
                string = getString(R.string.install_from_play_store_device_type_earbuds);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        commonDialog.setTitle(getString(R.string.uhm_install_plugin_from_play_store, this.mHMConnManager.getGearInfoToConnect().pluginAppName));
        commonDialog.setMessage(string);
        commonDialog.setOkBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass18 */

            public void onClick(View view) {
                commonDialog.dismiss();
                HMConnectFragment hMConnectFragment = HMConnectFragment.this;
                hMConnectFragment.mUpdatePlayStoreManager = new UpdatePlayStoreManager(set, hMConnectFragment.mHMConnManager.getBtAddressToConnect(), HMConnectFragment.this.mPlayStoreCallback);
                HMConnectFragment.this.mUpdatePlayStoreManager.startUpdateViaPlayStore(HMConnectFragment.this.mActivity);
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void confirmAllProviderInstallations() {
        Log.d(TAG, "startProviderInstall() starts...");
        this.mProviderInstallManager = new UpdateProviderInstallManager(new UpdateProviderInstallManager.IInstallManagerCallback() {
            /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass11 */

            @Override // com.samsung.android.app.twatchmanager.update.UpdateProviderInstallManager.IInstallManagerCallback
            public void onEndOfInstall() {
                HMConnectFragment.this.mInstallManagerCallback.onEndOfInstall();
            }

            @Override // com.samsung.android.app.twatchmanager.update.UpdateProviderInstallManager.IInstallManagerCallback
            public void onFailToInstall(int i, String str) {
                HMConnectFragment hMConnectFragment = HMConnectFragment.this;
                hMConnectFragment.showInstallErrorPopup(hMConnectFragment.mActivity, i);
            }

            @Override // com.samsung.android.app.twatchmanager.update.UpdateProviderInstallManager.IInstallManagerCallback
            public void onStartInstall() {
                if (HMConnectFragment.this.mWatchParticleView != null && HMConnectFragment.this.mWatchParticleView.getParticleView().getSceneType() != 1) {
                    HMConnectFragment.this.mWatchParticleView.getParticleView().a(1);
                }
            }
        });
        this.mProviderInstallManager.startInstallProviders(this.mHMConnManager.getBtAddressToConnect(), this.mHMConnManager.getDeviceNameToConnect(), this.mHMConnManager.getContainerPackageNameToConnect());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void fullInstallationComplete() {
        Log.d(TAG, "fullInstallationComplete() starts...");
        if (HostManagerUtils.isRebootRequired(this.mActivity, this.mHMConnManager.getDeviceNameToConnect())) {
            showRebootDialog();
        } else {
            callPluginActivityWithDelay();
        }
    }

    private boolean getIsFromPairingScreen() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getBoolean(GlobalConst.EXTRA_FROM_PAIRING_SCREEN);
        }
        return false;
    }

    private String getTargetAppStorePkg() {
        if (this.targetAppStorePkg == null) {
            this.targetAppStorePkg = InstallationUtils.getTargetAppStorePkg();
        }
        String str = TAG;
        Log.d(str, "getTargetAppStorePkg() = " + this.targetAppStorePkg);
        return this.targetAppStorePkg;
    }

    private Set<String> makePackageListToDownload() {
        HashSet hashSet = new HashSet();
        GearInfo gearInfoToConnect = this.mHMConnManager.getGearInfoToConnect();
        if (gearInfoToConnect != null) {
            for (String str : new UpdatePackageSet(gearInfoToConnect).get()) {
                if (!HostManagerUtils.isExistPackage(TWatchManagerApplication.getAppContext(), str)) {
                    hashSet.add(str);
                }
            }
        }
        String str2 = TAG;
        Log.d(str2, "makePackageListToDownload() toDownloadSet : " + hashSet);
        return hashSet;
    }

    private void popCurrentFragment() {
        Log.d(TAG, "popCurrentFragment");
        try {
            if (getActivity() == null) {
                return;
            }
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                Log.d(TAG, "top fragment popped");
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
    private void setButtonEnable(boolean z) {
        TextView textView;
        float f;
        TextView textView2 = this.cancelButton;
        if (textView2 != null) {
            textView2.setEnabled(z);
            if (z) {
                textView = this.cancelButton;
                f = 1.0f;
            } else {
                textView = this.cancelButton;
                f = 0.4f;
            }
            textView.setAlpha(f);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setDeviceSpecificDownloadLayout(String str) {
        Log.d(TAG, "setDeviceSpecificDownloadLayout() starts");
        ViewStub viewStub = (ViewStub) getView().findViewById(R.id.download_install_layout_stub);
        viewStub.setLayoutResource(R.layout.pop_download_install);
        View inflate = viewStub.inflate();
        this.messageTextView = (TextView) inflate.findViewById(R.id.messageText);
        this.downloadInstallViewSwitcher = (ViewSwitcher) getView().findViewById(R.id.download_install_switcher);
        this.downloadInstallViewSwitcher.setVisibility(4);
        this.cancelButton = (TextView) getView().findViewById(R.id.cancel_btn);
        this.cancelButton.setVisibility(4);
        this.progressPercentageTextView = (TextView) this.downloadInstallViewSwitcher.findViewById(R.id.setupPercentage);
        this.pluginInfo = (TextView) getView().findViewById(R.id.pluginInfo);
        this.pluginInfoTitle = (TextView) getView().findViewById(R.id.pluginInfoTitle);
        this.mWatchParticleView = (ParticleViewManager_Watch_OOBE) inflate.findViewById(R.id.fragment_container);
        if (this.mWatchParticleView != null) {
            Log.d(TAG, "setting particle animation");
            this.mWatchParticleView.a((int) getActivity().getResources().getDimension(R.dimen.pairing_watch_vi_width), (int) getActivity().getResources().getDimension(R.dimen.pairing_watch_vi_height));
            this.mWatchParticleView.getParticleView().setListener(this.particleAnimatorListener);
            this.mWatchParticleView.getParticleView().a(0);
        }
        TextView textView = (TextView) inflate.findViewById(R.id.cancel_btn);
        if (textView != null) {
            textView.setOnClickListener(this);
        }
        GearInfo gearInfo = GearRulesManager.getInstance().getGearInfo(str);
        if (gearInfo != null) {
            UIUtils.setHeaderImageWithRules(getActivity(), gearInfo.group.name, this.previewImage, getView().findViewById(R.id.gearManagerLogo));
        }
        boolean isFromPairingScreen = getIsFromPairingScreen();
        String str2 = TAG;
        Log.d(str2, "setDeviceSpecificDownloadLayout() starts isSwitching : " + this.isSwitching + " fromPairingScreen : " + isFromPairingScreen);
        if (this.isSwitching) {
            showSwitchingLayout(str);
        } else if (!isFromPairingScreen) {
            showTransitionLayout();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showDownloadFailPopup(UpdateDownloadManager.ErrorCode errorCode, final Activity activity) {
        View.OnClickListener onClickListener;
        FailDialogHelper.FailType failType = FailDialogHelper.FailType.DOWNLOAD_FAIL_BY_NETWORK;
        int i = AnonymousClass19.$SwitchMap$com$samsung$android$app$twatchmanager$update$UpdateDownloadManager$ErrorCode[errorCode.ordinal()];
        if (i == 1) {
            failType = FailDialogHelper.FailType.DOWNLOAD_URL_RESULT_INVALID;
        } else if (i == 2) {
            failType = FailDialogHelper.FailType.DOWNLOAD_FAIL_BY_STORAGE;
        }
        final CommonDialog makeFailDialogByType = FailDialogHelper.makeFailDialogByType(activity, failType);
        if (failType == FailDialogHelper.FailType.DOWNLOAD_FAIL_BY_NETWORK) {
            makeFailDialogByType.setTextToCancelBtn(getString(R.string.uhm_update_cancel_popup_button_ok));
        }
        int i2 = AnonymousClass19.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType[failType.ordinal()];
        if (i2 == 3) {
            onClickListener = new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass5 */

                public void onClick(View view) {
                    CommonDialog commonDialog = makeFailDialogByType;
                    if (commonDialog != null) {
                        commonDialog.setOkBtnClickable(false);
                        if (makeFailDialogByType.isShowing()) {
                            makeFailDialogByType.dismiss();
                            HMConnectFragment.this.checkAndStartDownload();
                        }
                    }
                }
            };
        } else if (i2 != 4) {
            if (i2 == 5) {
                onClickListener = new View.OnClickListener() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass7 */

                    public void onClick(View view) {
                        CommonDialog commonDialog = makeFailDialogByType;
                        if (commonDialog != null) {
                            commonDialog.setOkBtnClickable(false);
                            if (makeFailDialogByType.isShowing()) {
                                makeFailDialogByType.dismiss();
                                activity.finish();
                            }
                        }
                    }
                };
            }
            makeFailDialogByType.setCancelBtnListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass8 */

                public void onClick(View view) {
                    CommonDialog commonDialog = makeFailDialogByType;
                    if (commonDialog != null) {
                        commonDialog.setOkBtnClickable(false);
                        if (makeFailDialogByType.isShowing()) {
                            makeFailDialogByType.dismiss();
                            activity.finish();
                        }
                    }
                }
            });
        } else {
            onClickListener = new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass6 */

                public void onClick(View view) {
                    CommonDialog commonDialog = makeFailDialogByType;
                    if (commonDialog != null) {
                        commonDialog.setOkBtnClickable(false);
                        if (makeFailDialogByType.isShowing()) {
                            makeFailDialogByType.dismiss();
                            HostManagerUtils.startContactUsProcess(activity);
                        }
                        Activity activity = activity;
                        if (activity != null) {
                            activity.finish();
                        }
                    }
                }
            };
        }
        makeFailDialogByType.setOkBtnListener(onClickListener);
        makeFailDialogByType.setCancelBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass8 */

            public void onClick(View view) {
                CommonDialog commonDialog = makeFailDialogByType;
                if (commonDialog != null) {
                    commonDialog.setOkBtnClickable(false);
                    if (makeFailDialogByType.isShowing()) {
                        makeFailDialogByType.dismiss();
                        activity.finish();
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showInstallErrorPopup(final Activity activity, int i) {
        View.OnClickListener onClickListener;
        FailDialogHelper.FailType failType = FailDialogHelper.FailType.INSTALL_FAIL_BY_UNKNOWN;
        if (PackageControllerFactory.getStorageErrorCode(activity) == i) {
            failType = FailDialogHelper.FailType.INSTALL_FAIL_BY_STORAGE;
        }
        String str = TAG;
        Log.d(str, "Installation failed ! ErrorCode: " + i);
        final CommonDialog makeFailDialogByType = FailDialogHelper.makeFailDialogByType(activity, failType);
        int i2 = AnonymousClass19.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType[failType.ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                onClickListener = new View.OnClickListener() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass3 */

                    public void onClick(View view) {
                        CommonDialog commonDialog = makeFailDialogByType;
                        if (commonDialog != null) {
                            commonDialog.setOkBtnClickable(false);
                            if (makeFailDialogByType.isShowing()) {
                                makeFailDialogByType.dismiss();
                                activity.finish();
                            }
                        }
                    }
                };
            }
            makeFailDialogByType.setCancelBtnListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass4 */

                public void onClick(View view) {
                    CommonDialog commonDialog = makeFailDialogByType;
                    if (commonDialog != null) {
                        commonDialog.setOkBtnClickable(false);
                        if (makeFailDialogByType.isShowing()) {
                            makeFailDialogByType.dismiss();
                            activity.finish();
                        }
                    }
                }
            });
        }
        onClickListener = new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass2 */

            public void onClick(View view) {
                CommonDialog commonDialog = makeFailDialogByType;
                if (commonDialog != null) {
                    commonDialog.setOkBtnClickable(false);
                    if (makeFailDialogByType.isShowing()) {
                        makeFailDialogByType.dismiss();
                        HostManagerUtils.startContactUsProcess(activity);
                    }
                    Activity activity = activity;
                    if (activity != null) {
                        activity.finish();
                    }
                }
            }
        };
        makeFailDialogByType.setOkBtnListener(onClickListener);
        makeFailDialogByType.setCancelBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass4 */

            public void onClick(View view) {
                CommonDialog commonDialog = makeFailDialogByType;
                if (commonDialog != null) {
                    commonDialog.setOkBtnClickable(false);
                    if (makeFailDialogByType.isShowing()) {
                        makeFailDialogByType.dismiss();
                        activity.finish();
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showRebootDialog() {
        final CommonDialog commonDialog = new CommonDialog(this.mActivity, 1, 0, 3);
        commonDialog.createDialog();
        commonDialog.setTitle(getString(R.string.uhm_update_notice));
        commonDialog.setCancelable(false);
        Resources resources = getResources();
        commonDialog.setTextToOkBtn(resources.getString(R.string.reboot));
        commonDialog.setTextToCancelBtn(resources.getString(R.string.not_now));
        commonDialog.setMessage(resources.getString(R.string.reboot_phone_message));
        commonDialog.setOkBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass9 */

            public void onClick(View view) {
                commonDialog.dismiss();
                if (HMConnectFragment.this.mActivity != null) {
                    HostManagerUtils.resetBootRequiredFlag();
                    try {
                        ((PowerManager) HMConnectFragment.this.mActivity.getSystemService("power")).reboot(null);
                    } catch (Exception e) {
                        Log.w(HMConnectFragment.TAG, "powerManager.reboot(null);", e);
                    }
                }
            }
        });
        commonDialog.setCancelBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment.AnonymousClass10 */

            public void onClick(View view) {
                if (HMConnectFragment.this.mActivity != null) {
                    Log.d(HMConnectFragment.TAG, "Clicked not now on reboot dialog");
                    ActivityUtils.replaceFragmentToActivity(HMConnectFragment.this.getFragmentManager(), new PromotionFragment(), R.id.container, ActivityUtils.FRAGMENT_TAG);
                    commonDialog.dismiss();
                }
            }
        });
    }

    private void simulateDownload(Set<String> set) {
        Log.e(TAG, "simulateDownload() skip download process...");
        HashMap hashMap = new HashMap();
        for (String str : set) {
            hashMap.put(str, "");
        }
        this.mDownloadCallback.onFinishDownload(hashMap);
    }

    private void startFragment(int i, Bundle bundle) {
        String str = TAG;
        Log.d(str, "startFragment() :" + i);
        Activity activity = getActivity();
        if (activity instanceof SetupWizardWelcomeActivity) {
            SetupWizardWelcomeActivity setupWizardWelcomeActivity = (SetupWizardWelcomeActivity) activity;
            setupWizardWelcomeActivity.setLaunchMode(GlobalConst.LAUNCH_MODE_DEVICE_LIST);
            setupWizardWelcomeActivity.updateFragment(i, bundle);
        }
    }

    public void hideTransitionLayout() {
        changeStatusBarColor();
        this.transitionLayout.setVisibility(8);
        this.transitionLayoutSwitching.setVisibility(8);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Bundle arguments = getArguments();
        String str = TAG;
        Log.d(str, "onActivityCreated() bundle : " + arguments.toString());
        this.mHMConnManager = new HMConnectionManager(arguments, this.mCallback);
        this.mHMConnManager.init();
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.OnBackKeyListener
    public boolean onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        return true;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.cancel_btn) {
            SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_SETUP_INSTALL_PLUGIN, SALogUtil.SA_LOG_EVENT_CANCEL_INSTALL, "Cancel");
            Log.d(TAG, " Cancel Button clicked");
            if (this.mActivity != null) {
                Bundle bundle = new Bundle();
                bundle.putInt(SetupWizardWelcomeActivity.EXTRA_CONNECT_CASE, 2);
                startFragment(9, bundle);
                popCurrentFragment();
            }
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        String str = TAG;
        Log.i(str, "onConfigurationChanged " + configuration.orientation);
        GearInfo gearInfoToConnect = this.mHMConnManager.getGearInfoToConnect();
        if (gearInfoToConnect != null) {
            UIUtils.setHeaderImageWithRules(getActivity(), gearInfoToConnect.group.name, this.previewImage, getView().findViewById(R.id.gearManagerLogo));
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mActivity = getActivity();
        this.isSwitching = this.mActivity.getIntent().getBooleanExtra(SetupWizardWelcomeActivity.EXTRA_SWITCHING, false);
        this.mActivity.getWindow().addFlags(128);
        String str = TAG;
        Log.d(str, "OnCreate ends getActivity:" + getActivity());
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "onCreateView()");
        View inflate = layoutInflater.inflate(R.layout.template_download_install, viewGroup, false);
        UIUtils.adjustLogoMargin(inflate.findViewById(R.id.gearManagerLogo));
        this.transitionLayout = (RelativeLayout) inflate.findViewById(R.id.transtition_view);
        this.transitionLayoutSwitching = (RelativeLayout) inflate.findViewById(R.id.transtition_view_switching);
        this.previewImage = (ImageView) inflate.findViewById(R.id.topView);
        this.downInstallLayoutStub = (ViewStub) inflate.findViewById(R.id.download_install_layout_stub);
        this.logoHeaderLayout = (RelativeLayout) inflate.findViewById(R.id.top_image_title);
        this.actionbarLayout = (RelativeLayout) inflate.findViewById(R.id.top_actionbar_title);
        int statusBarHeight = UIUtils.getStatusBarHeight(getActivity());
        this.transitionLayout.findViewById(R.id.transition_included_layout).setPadding(0, statusBarHeight, 0, 0);
        this.transitionLayoutSwitching.findViewById(R.id.switching_included_layout).setPadding(0, statusBarHeight, 0, 0);
        return inflate;
    }

    public void onDestroy() {
        Log.i(TAG, "onDestroy() starts");
        GearRulesManager.getInstance().setSyncCallback(null);
        UpdateDownloadManager updateDownloadManager = this.mDownloadManager;
        if (updateDownloadManager != null) {
            updateDownloadManager.clearResources();
        }
        this.previewImage.setImageBitmap(null);
        this.mActivity = null;
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        ((FragmentLifecycleCallbacks) getActivity()).onFragmentDetached(1);
    }

    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        Log.saveLog();
    }

    public void onResume() {
        super.onResume();
        if (UpdateInstallData.isNonSamsungInstallRequested()) {
            UpdateInstallData.setNonSamsungInstallRequested(false);
            UpdatePlayStoreManager updatePlayStoreManager = this.mUpdatePlayStoreManager;
            if (updatePlayStoreManager != null) {
                updatePlayStoreManager.checkAfterPlayStoreLaunched(this.mActivity);
                return;
            }
            UpdateInstallManager updateInstallManager = this.mInstallManager;
            if (updateInstallManager != null) {
                updateInstallManager.checkAfterPackageInstallerLaunched();
            }
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        Log.d(TAG, "onViewCreated()");
        super.onViewCreated(view, bundle);
        view.setImportantForAccessibility(1);
        Activity activity = getActivity();
        updateAfterMultiWindowChanged((activity == null || !(activity instanceof SetupWizardWelcomeActivity)) ? false : ((SetupWizardWelcomeActivity) getActivity()).getCurrentMultiWindowMode());
    }

    public void showSwitchingLayout(String str) {
        this.transitionLayoutSwitching.setVisibility(0);
        this.transitionLayout.setVisibility(8);
        GearRulesManager instance = GearRulesManager.getInstance();
        String icon = instance.getIcon(str);
        String switchGearIcon = instance.getSwitchGearIcon(str);
        String str2 = TAG;
        Log.d(str2, "showSwitchingLayout() deviceSimpleName : " + str + " icon : " + icon + " titleIcon : " + switchGearIcon);
        if (icon != null) {
            ((ImageView) this.transitionLayoutSwitching.findViewById(R.id.icon)).setImageResource(ResourceLoader.getDrawableId(getActivity(), icon));
        }
        ImageView imageView = (ImageView) this.transitionLayoutSwitching.findViewById(R.id.watch_logo_view);
        TextView textView = (TextView) this.transitionLayoutSwitching.findViewById(R.id.tvActionTitle);
        if (switchGearIcon != null) {
            imageView.setImageResource(ResourceLoader.getDrawableId(getActivity(), switchGearIcon));
            imageView.setVisibility(0);
            textView.setVisibility(8);
            return;
        }
        imageView.setVisibility(8);
        textView.setVisibility(0);
        textView.setText(str);
    }

    public void showTransitionLayout() {
        this.transitionLayout.setVisibility(0);
        this.transitionLayoutSwitching.setVisibility(8);
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.IMultiWindowListener
    public void updateAfterMultiWindowChanged(boolean z) {
        Activity activity = getActivity();
        if (activity != null) {
            int dimension = (int) activity.getResources().getDimension(R.dimen.action_bar_h);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.downInstallLayoutStub.getLayoutParams();
            if (z) {
                this.actionbarLayout.setVisibility(0);
                this.logoHeaderLayout.setVisibility(8);
                marginLayoutParams.topMargin = dimension;
                this.downInstallLayoutStub.setLayoutParams(marginLayoutParams);
                return;
            }
            this.actionbarLayout.setVisibility(8);
            this.logoHeaderLayout.setVisibility(0);
        }
    }
}
