package com.samsung.android.app.twatchmanager.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.samsung.android.app.twatchmanager.contentprovider.BaseContentProvider;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.log.LoggerUtil;
import com.samsung.android.app.twatchmanager.update.StubAPIHelper;
import com.samsung.android.app.twatchmanager.update.UpdateDownloadManager;
import com.samsung.android.app.twatchmanager.update.UpdateInstallManager;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsDBOperations;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.SafeRemoveTaskHandler;
import com.samsung.android.app.twatchmanager.util.Toaster;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment;
import com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PluginExecutor {
    private static final String TAG = ("tUHM:[Update][Conn]" + PluginExecutor.class.getSimpleName());
    public static final String UPDATE_PACKAGE_LIST = "update_package_list";
    public static final String UPDATE_PLUGIN_BT_ADDRESS = "update_plugin_bt_address";
    public static final String UPDATE_PLUGIN_DEVICE_NAME = "update_plugin_device_name";
    public static final String UPDATE_PLUGIN_EXTRA_DATA = "update_plugin_extra_data";
    public static final String UPDATE_PLUGIN_IS_SWITCHING = "update_plugin_is_switching";
    public static final String UPDATE_PLUGIN_LAUNCH_MODE = "update_plugin_launch_mode";
    public static final String UPDATE_PLUGIN_PACKAGE_NAME = "update_plugin_package_name";
    public static final String UPDATE_PLUGIN_TARGET_PAGE = "update_plugin_target_page";
    private Activity mActivity;
    private String mBTAddress;
    private String mDeviceName;
    private UpdateDownloadManager.IDownloadManagerCallback mDownloadManagerCallback = new UpdateDownloadManager.IDownloadManagerCallback() {
        /* class com.samsung.android.app.twatchmanager.update.PluginExecutor.AnonymousClass1 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onDownloadAvailable(HashMap<String, StubAPIHelper.XMLResult> hashMap, int i) {
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onDownloading(int i, double d2) {
            PluginExecutor.this.mListener.onDownloading(i, d2);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onFailToDownload(UpdateDownloadManager.ErrorCode errorCode) {
            PluginExecutor.this.mListener.onDownloadError(errorCode);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onFinishDownload(Map<String, String> map) {
            PluginExecutor.this.mListener.onDownloadEnd(map);
            PluginExecutor.this.requestInstall(map);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onStartDownload(double d2) {
            PluginExecutor.this.mListener.onDownloadStart(d2);
        }
    };
    private String mExtraData;
    private UpdateInstallManager.IInstallManagerCallback mInstallManagerCallback = new UpdateInstallManager.IInstallManagerCallback() {
        /* class com.samsung.android.app.twatchmanager.update.PluginExecutor.AnonymousClass2 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onDisconnectBeforePluginInstall(String str) {
            PluginExecutor.this.mListener.onNotifyDisconnectBeforePluginInstall(str);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onEndOfInstall() {
            PluginExecutor.this.launchPluginAfterUpdate();
            UpdateUtil.sendUpdateCompleteBroadcast(PluginExecutor.this.mBTAddress);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onFailToInstall(int i, String str) {
            PluginExecutor.this.mListener.onInstallError(i, str);
            UpdateUtil.sendUpdateCompleteBroadcast(PluginExecutor.this.mBTAddress);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onInstallUHM() {
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onStartInstall() {
            PluginExecutor.this.mListener.onInstallStart();
        }
    };
    private boolean mIsSwitching;
    private int mLaunchMode;
    private IPluginExecutorListener mListener = null;
    private String mPackageName;
    private String mTargetPage;
    private UpdateDownloadManager mUpdateDownloadManager = null;
    private UpdateInstallManager mUpdateInstallManager = null;
    private Set<String> mUpdatePkgSet = null;

    public interface IPluginExecutorListener {
        void onDownloadEnd(Map<String, String> map);

        void onDownloadError(UpdateDownloadManager.ErrorCode errorCode);

        void onDownloadStart(double d2);

        void onDownloading(int i, double d2);

        void onInstallError(int i, String str);

        void onInstallStart();

        void onNotifyDisconnectBeforePluginInstall(String str);
    }

    public PluginExecutor(IPluginExecutorListener iPluginExecutorListener, Activity activity, Bundle bundle) {
        this.mBTAddress = bundle.getString(UPDATE_PLUGIN_BT_ADDRESS, "");
        this.mPackageName = bundle.getString(UPDATE_PLUGIN_PACKAGE_NAME, "");
        this.mDeviceName = bundle.getString(UPDATE_PLUGIN_DEVICE_NAME, "");
        this.mTargetPage = bundle.getString(UPDATE_PLUGIN_TARGET_PAGE, "");
        this.mLaunchMode = bundle.getInt(UPDATE_PLUGIN_LAUNCH_MODE, GlobalConst.LAUNCH_MODE_AFTER_UPDATE);
        this.mIsSwitching = bundle.getBoolean(UPDATE_PLUGIN_IS_SWITCHING, false);
        this.mExtraData = bundle.getString(UPDATE_PLUGIN_EXTRA_DATA, "");
        this.mActivity = activity;
        this.mListener = iPluginExecutorListener;
        this.mUpdatePkgSet = new HashSet();
        this.mUpdatePkgSet.add(this.mPackageName);
        ArrayList<String> stringArrayList = bundle.getStringArrayList(UPDATE_PACKAGE_LIST);
        this.mUpdatePkgSet = new HashSet();
        if (stringArrayList != null && !stringArrayList.isEmpty()) {
            this.mUpdatePkgSet.addAll(stringArrayList);
        }
    }

    public static void requestStartPlugin(Activity activity, String str, String str2, String str3, String str4, int i, boolean z, String str5) {
        String str6 = TAG;
        Log.d(str6, "requestStartPlugin() pluginPackage=" + str + " deviceName=" + str3 + " deviceName=" + str3 + " launchMode=" + i + " isSwitching=" + z);
        String changeContainerPackageForGear12S = UpdateUtil.changeContainerPackageForGear12S(str);
        boolean updateAvailable = UpdateHistoryManager.getInstance().getUpdateAvailable(changeContainerPackageForGear12S);
        String str7 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("requestStartPlugin() isPluginUpdateAvailable=");
        sb.append(updateAvailable);
        Log.d(str7, sb.toString());
        if (updateAvailable) {
            Bundle bundle = new Bundle();
            bundle.putString(UPDATE_PLUGIN_BT_ADDRESS, str2);
            bundle.putString(UPDATE_PLUGIN_DEVICE_NAME, str3);
            bundle.putString(UPDATE_PLUGIN_PACKAGE_NAME, str);
            bundle.putString(UPDATE_PLUGIN_TARGET_PAGE, str4);
            bundle.putInt(UPDATE_PLUGIN_LAUNCH_MODE, i);
            bundle.putBoolean(UPDATE_PLUGIN_IS_SWITCHING, z);
            bundle.putString(UPDATE_PLUGIN_EXTRA_DATA, str5);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(changeContainerPackageForGear12S);
            if (!HostManagerUtils.isSamsungDevice() && UpdateHistoryManager.getInstance().getUpdateAvailable(GlobalConst.PACKAGE_NAME_SAMSUNG_ACCESSORY)) {
                arrayList.add(GlobalConst.PACKAGE_NAME_SAMSUNG_ACCESSORY);
            }
            String str8 = TAG;
            Log.d(str8, "requestStartPlugin() updatePackageList=" + arrayList);
            bundle.putStringArrayList(UPDATE_PACKAGE_LIST, arrayList);
            ((SetupWizardWelcomeActivity) activity).updateFragment(12, bundle);
            return;
        }
        startPluginActivity(activity, str, str2, str3, str4, i, z, str5);
    }

    private static void startPluginActivity(Activity activity, String str, String str2, String str3, String str4, int i, boolean z, String str5) {
        boolean z2;
        Log.d(TAG, "startPluginActivity() pluginPackage:" + str + " deviceAddress:" + str2 + " deviceName:" + str3 + " targetPage:" + str4 + " launchMode:" + i + " extraData :" + str5 + ", isSwitching = " + z);
        LoggerUtil.Builder builder = new LoggerUtil.Builder(activity, "T002");
        StringBuilder sb = new StringBuilder();
        sb.append("PackageName:");
        sb.append(str);
        sb.append(",  deviceName:");
        sb.append(str3);
        builder.setExtra(sb.toString()).buildAndSend();
        Set<String> appsUpdateList = UpdateUtil.getAppsUpdateList(activity);
        String[] strArr = appsUpdateList.isEmpty() ? new String[0] : (String[]) appsUpdateList.toArray(new String[appsUpdateList.size()]);
        Intent pluginIntent = HostManagerUtils.getPluginIntent(str);
        if (activity instanceof SetupWizardWelcomeActivity) {
            SetupWizardWelcomeActivity setupWizardWelcomeActivity = (SetupWizardWelcomeActivity) activity;
            z2 = setupWizardWelcomeActivity.isPairedByTUHM();
            Log.d(TAG, "pairedByTUHM : " + z2);
            pluginIntent.putExtra("pairedByTUHM", z2);
            setupWizardWelcomeActivity.setPairedByTUHM(false);
        } else {
            pluginIntent.putExtra("pairedByTUHM", false);
            z2 = true;
        }
        pluginIntent.putExtra("target_page", str4);
        pluginIntent.putExtra(HMConnectFragment.EXTRA_DEVICE_ADDRESS, str2);
        pluginIntent.putExtra("deviceid", str2);
        pluginIntent.putExtra(SetupWizardWelcomeActivity.EXTRA_BT_ADDR, str2);
        pluginIntent.putExtra(BaseContentProvider.DEVICE_NAME, str3);
        pluginIntent.putExtra(SetupWizardWelcomeActivity.EXTRA_LAUNCH_MODE, i);
        pluginIntent.putExtra(SetupWizardWelcomeActivity.EXTRA_SWITCHING, z);
        pluginIntent.putExtra(GlobalConst.EXTRA_UPDATE_AVAILABLE, strArr);
        if (activity.getIntent().hasExtra(SetupWizardWelcomeActivity.EXTRA_IS_AUTO_SWITCH)) {
            pluginIntent.putExtra(SetupWizardWelcomeActivity.EXTRA_IS_AUTO_SWITCH, activity.getIntent().getStringExtra(SetupWizardWelcomeActivity.EXTRA_IS_AUTO_SWITCH));
        }
        try {
            PackageManager packageManager = activity.getPackageManager();
            if (packageManager != null) {
                pluginIntent.putExtra("uhm_version", packageManager.getPackageInfo(activity.getPackageName(), 0).versionCode);
            } else {
                Log.e(TAG, "pm is null");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        boolean isDeviceAlreadyConnected = HostManagerUtilsDBOperations.isDeviceAlreadyConnected(activity, str2, HostManagerUtilsRulesBTDevices.getSimpleBTNameByAddress(str2));
        boolean isTopActivity = HostManagerUtils.isTopActivity(activity, "HMRootActivity");
        Log.d(TAG, "startPluginActivity() isAlreadyConnected : " + isDeviceAlreadyConnected + " isTopActivity : " + isTopActivity);
        pluginIntent.addFlags(268435456);
        String str6 = "Intent.FLAG_ACTIVITY_NEW_TASK";
        if ((!isDeviceAlreadyConnected && !z) || i == 1003 || i == 1009 || i == 1011 || (i == 1002 && !z2)) {
            pluginIntent.addFlags(32768);
            Log.d(TAG, "add flag.");
            str6 = str6 + "|| Intent.FLAG_ACTIVITY_CLEAR_TASK ";
        }
        if (HostManagerUtils.isExistPackage(activity, str)) {
            RegistryDbManagerWithProvider registryDbManagerWithProvider = new RegistryDbManagerWithProvider();
            List<DeviceRegistryData> queryDevicebyDeviceIdRegistryData = registryDbManagerWithProvider.queryDevicebyDeviceIdRegistryData(str2, activity);
            if (str2 == null || queryDevicebyDeviceIdRegistryData.size() == 0) {
                Log.d(TAG, "device [" + str2 + "] does not exist in DB, lets add");
                registryDbManagerWithProvider.addDeviceRegistryData(new DeviceRegistryData(str, str3, str2, 1, 0, HostManagerUtilsRulesBTDevices.supportsPairing(str3)), activity);
            } else {
                Log.d(TAG, "device [" + str2 + "] already in DB");
                for (DeviceRegistryData deviceRegistryData : queryDevicebyDeviceIdRegistryData) {
                    if (!deviceRegistryData.packagename.equals(str)) {
                        Log.d(TAG, " DB corrupted initialize repair");
                        registryDbManagerWithProvider.updateDevicePackageNameRegistryData(deviceRegistryData.deviceBtID, str, activity);
                    }
                }
            }
        }
        if (str5 != null) {
            pluginIntent.putExtra(GlobalConst.EXTRA_DATA_FROM_TUHM, str5);
        }
        try {
            Log.d(TAG, "startPluginActivity() launch flag : " + str6 + " launchMode : " + i);
            activity.startActivity(pluginIntent);
            if (i == 1004 || i == 1002) {
                activity.overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
            } else {
                activity.overridePendingTransition(0, 0);
            }
            SafeRemoveTaskHandler.getInstance().stop();
            SafeRemoveTaskHandler.getInstance().start(activity);
        } catch (RuntimeException e2) {
            e2.printStackTrace();
            Log.d(TAG, "startPluginActivity() return false");
            Toaster.show(activity, "No plug-in");
        }
        Log.d(TAG, "startPluginActivity() return true");
    }

    public void checkAfterPackageInstallerLaunched() {
        UpdateInstallManager updateInstallManager = this.mUpdateInstallManager;
        if (updateInstallManager != null) {
            updateInstallManager.checkAfterPackageInstallerLaunched();
        }
    }

    public void clearResources() {
        UpdateDownloadManager updateDownloadManager = this.mUpdateDownloadManager;
        if (updateDownloadManager != null) {
            updateDownloadManager.clearResources();
            this.mUpdateDownloadManager = null;
        }
    }

    public String getPluginPackageName() {
        return this.mPackageName;
    }

    public Set<String> getUpdatePackageSet() {
        return this.mUpdatePkgSet;
    }

    public void launchPluginAfterUpdate() {
        Log.d(TAG, "launchPluginAfterUpdate()");
        startPluginActivity(this.mActivity, this.mPackageName, this.mBTAddress, this.mDeviceName, this.mTargetPage, this.mLaunchMode, this.mIsSwitching, this.mExtraData);
    }

    public void requestDownload(Context context) {
        Log.d(TAG, "requestDownload()");
        UpdateDownloadManager updateDownloadManager = this.mUpdateDownloadManager;
        if (updateDownloadManager != null) {
            updateDownloadManager.clearResources();
        } else {
            this.mUpdateDownloadManager = new UpdateDownloadManager(this.mDownloadManagerCallback, this.mUpdatePkgSet, false);
        }
        this.mUpdateDownloadManager.startUpdateDownloadManager(context);
    }

    public void requestInstall(Map<String, String> map) {
        Log.d(TAG, "requestInstall()");
        this.mUpdateInstallManager = new UpdateInstallManager(this.mInstallManagerCallback, this.mBTAddress, this.mDeviceName, map, false);
        this.mUpdateInstallManager.pluginInstallProcess();
    }
}
