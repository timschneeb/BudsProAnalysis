package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.update.PluginExecutor;
import com.samsung.android.app.twatchmanager.update.UpdateHistoryManager;
import com.samsung.android.app.twatchmanager.update.UpdateInstallData;
import com.samsung.android.app.twatchmanager.update.UpdateNotificationManager;
import com.samsung.android.app.twatchmanager.update.UpdatePlayStoreManager;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import com.samsung.android.app.watchmanager.R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PluginLoadingGearFragment extends Fragment {
    private static final String TAG = ("tUHM:[Update]" + PluginLoadingGearFragment.class.getSimpleName());
    private static final int TOO_MUCH_DOWNLOAD_SIZE_MB = 50;
    private Activity mActivity;
    private TextView mApkSize;
    private String mBTAddress;
    private Bundle mBundleData;
    private Button mCancel;
    private boolean mIsForceUpdateNeeded;
    private HashMap<String, String> mNewFeature;
    private UpdatePlayStoreManager.IPlayStoreUpdateCallback mPlayStoreCallback = new UpdatePlayStoreManager.IPlayStoreUpdateCallback() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PluginLoadingGearFragment.AnonymousClass5 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdatePlayStoreManager.IPlayStoreUpdateCallback
        public void onFinishUpdate(boolean z, String str) {
            String str2 = PluginLoadingGearFragment.TAG;
            Log.d(str2, "onFinishUpdate() isSuccess : " + z);
            if (PluginLoadingGearFragment.this.mActivity != null) {
                UpdateHistoryManager.getInstance().updateStatus(PluginLoadingGearFragment.this.mUpdatePackageSet, z ? 0 : 2);
                if (!z && UpdateUtil.isForceUpdateNeeded()) {
                    HostManagerUtils.handleTaskInternal();
                    PluginLoadingGearFragment.this.mActivity.finish();
                } else if (PluginLoadingGearFragment.this.mPluginExecutor != null) {
                    PluginLoadingGearFragment.this.mPluginExecutor.launchPluginAfterUpdate();
                }
            }
        }
    };
    private PluginExecutor mPluginExecutor;
    private Button mUpdate;
    private TextView mUpdateDescText;
    private LinearLayout mUpdateLoading;
    private Set<String> mUpdatePackageSet;
    private UpdatePlayStoreManager mUpdatePlayStoreManager;
    private LinearLayout mUpdateSelect;

    /* JADX WARNING: Removed duplicated region for block: B:7:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void changeUpdateDescriptionBySize(double r8) {
        /*
        // Method dump skipped, instructions count: 146
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.PluginLoadingGearFragment.changeUpdateDescriptionBySize(double):void");
    }

    private int getContentSizeInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getNewFeatureString() {
        Iterator<String> it = this.mNewFeature.keySet().iterator();
        String str = "";
        while (it.hasNext()) {
            HashMap<String, String> hashMap = this.mNewFeature;
            str = str + hashMap.get(it.next());
        }
        return str.replace(';', '\n');
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void goToPlayStoreUpdateStep(String str) {
        this.mUpdatePlayStoreManager = new UpdatePlayStoreManager(this.mUpdatePackageSet, str, this.mPlayStoreCallback);
        this.mUpdatePlayStoreManager.startUpdateViaPlayStore(this.mActivity);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initUpdateLoadingView() {
        this.mUpdateLoading.setVisibility(0);
        this.mUpdateSelect.setVisibility(8);
        this.mCancel.setVisibility(4);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void openNewFeatureDialog(String str) {
        final CommonDialog commonDialog = new CommonDialog(this.mActivity, 1, 0, 1);
        commonDialog.createDialog();
        commonDialog.setTitle(getString(R.string.uhm_whats_new_popup_title));
        commonDialog.setMessage(str);
        commonDialog.setOkBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PluginLoadingGearFragment.AnonymousClass4 */

            public void onClick(View view) {
                commonDialog.dismiss();
            }
        });
    }

    public void initUpdateSelectView(int i) {
        Button button;
        int i2;
        this.mNewFeature = new HashMap<>();
        Log.d(TAG, "initUpdateSelectView() starts...");
        double d2 = (double) i;
        Double.isNaN(d2);
        double d3 = d2 / 1048576.0d;
        this.mUpdateLoading.setVisibility(8);
        this.mUpdateSelect.setVisibility(0);
        this.mApkSize.setText(String.format("%.1f MB", Double.valueOf(d3)));
        changeUpdateDescriptionBySize(d3);
        this.mUpdate.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PluginLoadingGearFragment.AnonymousClass1 */

            public void onClick(View view) {
                if (HostManagerUtils.isSamsungDevice() || !HostManagerUtils.isPlayStoreAvailable(PluginLoadingGearFragment.this.mActivity)) {
                    ((SetupWizardWelcomeActivity) PluginLoadingGearFragment.this.mActivity).updateFragment(11, PluginLoadingGearFragment.this.mBundleData);
                    return;
                }
                PluginLoadingGearFragment.this.initUpdateLoadingView();
                PluginLoadingGearFragment pluginLoadingGearFragment = PluginLoadingGearFragment.this;
                pluginLoadingGearFragment.goToPlayStoreUpdateStep(pluginLoadingGearFragment.mBTAddress);
            }
        });
        this.mIsForceUpdateNeeded = UpdateUtil.isForceUpdateNeeded(this.mUpdatePackageSet);
        if (this.mIsForceUpdateNeeded) {
            button = this.mCancel;
            i2 = R.string.cancel;
        } else {
            button = this.mCancel;
            i2 = R.string.dialog_popup_button_later;
        }
        button.setText(i2);
        this.mCancel.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PluginLoadingGearFragment.AnonymousClass2 */

            public void onClick(View view) {
                UpdateUtil.checkForceUpdateWithUpdateList(PluginLoadingGearFragment.this.mUpdatePackageSet);
                if (PluginLoadingGearFragment.this.mIsForceUpdateNeeded) {
                    UpdateUtil.showUpdateCancelPopup(PluginLoadingGearFragment.this.mActivity, new View.OnClickListener() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.PluginLoadingGearFragment.AnonymousClass2.AnonymousClass1 */

                        public void onClick(View view) {
                            UpdateUtil.clearUpdateCheckPref(TWatchManagerApplication.getAppContext());
                            HostManagerUtils.handleTaskInternal();
                            if (PluginLoadingGearFragment.this.mActivity != null) {
                                PluginLoadingGearFragment.this.mActivity.finish();
                            }
                        }
                    });
                    return;
                }
                UpdateUtil.clearUpdateCheckPref(TWatchManagerApplication.getAppContext());
                UpdateHistoryManager.getInstance().updateStatus(PluginLoadingGearFragment.this.mUpdatePackageSet, 2);
                if (PluginLoadingGearFragment.this.mPluginExecutor != null) {
                    PluginLoadingGearFragment.this.mPluginExecutor.launchPluginAfterUpdate();
                }
            }
        });
        this.mCancel.setVisibility(0);
        UpdateNotificationManager.getInstance().cancel();
    }

    @Override // android.app.Fragment
    public void onAttach(Activity activity) {
        String str = TAG;
        Log.d(str, "onAttach(" + activity + ")");
        super.onAttach(activity);
        this.mActivity = activity;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "onCreateView");
        View inflate = layoutInflater.inflate(R.layout.setup_splash_waiting_layout, viewGroup, false);
        this.mUpdateSelect = (LinearLayout) inflate.findViewById(R.id.update_select_layout);
        this.mUpdateDescText = (TextView) inflate.findViewById(R.id.update_desc_text);
        this.mUpdateLoading = (LinearLayout) inflate.findViewById(R.id.update_loading_layout);
        this.mApkSize = (TextView) inflate.findViewById(R.id.apkSize_txt);
        this.mUpdate = (Button) inflate.findViewById(R.id.update_btn);
        this.mCancel = (Button) inflate.findViewById(R.id.cancel_btn);
        return inflate;
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy() starts...");
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
        Activity activity = this.mActivity;
        if (activity != null) {
            ((FragmentLifecycleCallbacks) activity).onFragmentDetached(1);
            this.mActivity = null;
        }
    }

    public void onPause() {
        Log.d(TAG, "onPause() starts...");
        super.onPause();
        Log.saveLog();
    }

    public void onResume() {
        Log.d(TAG, "onResume() starts...");
        super.onResume();
        if (UpdateInstallData.isNonSamsungInstallRequested()) {
            UpdateInstallData.setNonSamsungInstallRequested(false);
            UpdatePlayStoreManager updatePlayStoreManager = this.mUpdatePlayStoreManager;
            if (updatePlayStoreManager != null) {
                updatePlayStoreManager.checkAfterPlayStoreLaunched(this.mActivity);
            }
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        Log.d(TAG, "onViewCreated() starts...");
        super.onViewCreated(view, bundle);
        this.mBundleData = getArguments();
        Bundle bundle2 = this.mBundleData;
        if (bundle2 != null) {
            this.mBTAddress = bundle2.getString(PluginExecutor.UPDATE_PLUGIN_BT_ADDRESS, "");
            this.mUpdatePackageSet = new HashSet(this.mBundleData.getStringArrayList(PluginExecutor.UPDATE_PACKAGE_LIST));
            int i = 0;
            for (String str : this.mUpdatePackageSet) {
                i += getContentSizeInt(UpdateHistoryManager.getInstance().getUpdateHistoryData(str, UpdateHistoryManager.PREF_KEY_CONTENT_SIZE));
            }
            initUpdateSelectView(i);
            this.mPluginExecutor = new PluginExecutor(null, this.mActivity, this.mBundleData);
        }
    }
}
