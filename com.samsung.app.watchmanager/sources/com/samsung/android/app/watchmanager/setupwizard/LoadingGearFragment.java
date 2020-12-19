package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import b.j.a.b;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.update.QAPasswordCheckThread;
import com.samsung.android.app.twatchmanager.update.SAGUIDHelper;
import com.samsung.android.app.twatchmanager.update.UpdateHistoryManager;
import com.samsung.android.app.twatchmanager.update.UpdateInstallData;
import com.samsung.android.app.twatchmanager.update.UpdateManager;
import com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment;
import com.samsung.android.app.twatchmanager.update.UpdateNotificationManager;
import com.samsung.android.app.twatchmanager.update.UpdatePlayStoreManager;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import com.samsung.android.app.watchmanager.R;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class LoadingGearFragment extends Fragment implements OnBackKeyListener {
    public static final String ACTION_NETWORK_USAGE_AGREE = "NETWORK_USAGE_AGREEMENT";
    public static final int LOADING_TYPE_JUST_LOADING = 3;
    public static final String LOADING_TYPE_KEY = "loading_type";
    public static final int LOADING_TYPE_SHOW_NETWORK_USAGE_POPUP = 1;
    public static final int LOADING_TYPE_UPDATE_CHECK = 2;
    private static final String TAG = ("tUHM:[Update]" + LoadingGearFragment.class.getSimpleName());
    private static final int TOO_MUCH_DOWNLOAD_SIZE_MB = 50;
    private Activity mActivity;
    private TextView mApkSize;
    private AlertDialog.Builder mBuilder;
    private Button mCancel;
    private Dialog mDialog;
    private SAGUIDHelper.IGUIDResultCallback mGUIDCallback = new SAGUIDHelper.IGUIDResultCallback() {
        /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass2 */

        @Override // com.samsung.android.app.twatchmanager.update.SAGUIDHelper.IGUIDResultCallback
        public void onResult(String str, boolean z) {
            String str2 = LoadingGearFragment.TAG;
            Log.d(str2, "IGUIDResultCallback.onResult() isSuccess : " + z);
            LoadingGearFragment.this.showQAStoreConfirmDialog(str);
        }
    };
    private boolean mIsForceUpdateNeeded;
    private BroadcastReceiver mNetworkUsageAgreeReceiver = new BroadcastReceiver() {
        /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            Log.d(LoadingGearFragment.TAG, "onReceive() starts..");
            LoadingGearFragment.this.runUpdateManager();
            try {
                b.a(context).a(LoadingGearFragment.this.mNetworkUsageAgreeReceiver);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    };
    private HashMap<String, String> mNewFeature;
    private UpdatePlayStoreManager.IPlayStoreUpdateCallback mPlayStoreCallback = new UpdatePlayStoreManager.IPlayStoreUpdateCallback() {
        /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass11 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdatePlayStoreManager.IPlayStoreUpdateCallback
        public void onFinishUpdate(boolean z, String str) {
            String str2 = LoadingGearFragment.TAG;
            Log.d(str2, "onFinishUpdate() isSuccess : " + z);
            if (LoadingGearFragment.this.mActivity != null) {
                UpdateHistoryManager.getInstance().updateStatus(LoadingGearFragment.this.mUpdateList, z ? 0 : 2);
                if (z || !UpdateUtil.isForceUpdateNeeded()) {
                    ((SetupWizardWelcomeActivity) LoadingGearFragment.this.mActivity).onUpdateFragmentFinished(z, str);
                    return;
                }
                HostManagerUtils.handleTaskInternal();
                LoadingGearFragment.this.mActivity.finish();
            }
        }
    };
    private Button mUpdate;
    UpdateManager.IUpdateManagerCallback mUpdateCallback = new UpdateManager.IUpdateManagerCallback() {
        /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass10 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdateManager.IUpdateManagerCallback
        public void onUpdateAvailable(int i, String str) {
            String str2 = LoadingGearFragment.TAG;
            Log.d(str2, "UpdateCheckingReceiver.onUpdateAvailable() totalUpdateAppSize: " + i + " btAddress : " + str);
            if (LoadingGearFragment.this.mActivity != null && !LoadingGearFragment.this.mActivity.isFinishing()) {
                LoadingGearFragment.this.initUpdateSelectView(i, str);
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateManager.IUpdateManagerCallback
        public void onUpdateCheckTimeOut(String str) {
            SetupWizardWelcomeActivity setupWizardWelcomeActivity = (SetupWizardWelcomeActivity) LoadingGearFragment.this.mActivity;
            if (setupWizardWelcomeActivity != null && !setupWizardWelcomeActivity.isFinishing()) {
                Intent intent = setupWizardWelcomeActivity.getIntent();
                boolean z = false;
                if (intent.getBooleanExtra(UpdateNotificationManager.EXTRA_UPDATE_NOTIFICATION, false) && intent.getBooleanExtra(SetupWizardWelcomeActivity.EXTRA_IS_FROM_PLUGIN, false)) {
                    z = true;
                }
                String str2 = LoadingGearFragment.TAG;
                Log.d(str2, "onUpdateCheckTimeOut() isFromPluginUpdateButton : " + z);
                if (z) {
                    LoadingGearFragment.this.showUpdateCheckFailDialog(setupWizardWelcomeActivity, str);
                } else {
                    onUpdateUnAvailable();
                }
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateManager.IUpdateManagerCallback
        public void onUpdateUnAvailable() {
            Log.d(LoadingGearFragment.TAG, "onUpdateUnAvailable() starts...");
            SetupWizardWelcomeActivity setupWizardWelcomeActivity = (SetupWizardWelcomeActivity) LoadingGearFragment.this.mActivity;
            if (setupWizardWelcomeActivity != null && !setupWizardWelcomeActivity.isFinishing()) {
                setupWizardWelcomeActivity.doActionsAfterUpdate();
            }
        }
    };
    private TextView mUpdateDescText;
    private Set<String> mUpdateList;
    private LinearLayout mUpdateLoading;
    private UpdateManager mUpdateManager;
    private UpdatePlayStoreManager mUpdatePlayStoreManager;
    private LinearLayout mUpdateSelect;

    /* JADX WARNING: Removed duplicated region for block: B:7:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void changeUpdateDescriptionBySize(double r8) {
        /*
        // Method dump skipped, instructions count: 146
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.changeUpdateDescriptionBySize(double):void");
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
    private void goToDownloadInstallStep(Bundle bundle) {
        SetupWizardWelcomeActivity setupWizardWelcomeActivity = (SetupWizardWelcomeActivity) this.mActivity;
        if (UpdateUtil.isLocalUpdateTestModeEnabled() || (!HostManagerUtils.isEngBuild() && !InstallationUtils.isLocalInstallation())) {
            setupWizardWelcomeActivity.updateFragment(5, bundle);
        } else {
            setupWizardWelcomeActivity.doActionsAfterUpdate();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void goToPlayStoreUpdateStep(String str) {
        this.mUpdatePlayStoreManager = new UpdatePlayStoreManager(this.mUpdateList, str, this.mPlayStoreCallback);
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
            /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass7 */

            public void onClick(View view) {
                commonDialog.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showQAStoreConfirmDialog(final String str) {
        try {
            this.mBuilder = this.mActivity == null ? new AlertDialog.Builder(getActivity()) : new AlertDialog.Builder(this.mActivity);
            this.mBuilder.setCancelable(false);
            this.mBuilder.setTitle("QA Store Password");
            this.mBuilder.setMessage(getString(R.string.qa_store_dialog_password));
            final EditText editText = new EditText(this.mActivity);
            editText.setInputType(129);
            this.mBuilder.setView(editText);
            this.mBuilder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    LoadingGearFragment.this.mDialog.hide();
                    new QAPasswordCheckThread(editText.getText().toString(), str, new QAPasswordCheckThread.IQAPasswordCallback() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass8.AnonymousClass1 */

                        @Override // com.samsung.android.app.twatchmanager.update.QAPasswordCheckThread.IQAPasswordCallback
                        public void resultCallback(final boolean z) {
                            String str = LoadingGearFragment.TAG;
                            Log.d(str, "QAPasswordThread.resultCallback() isConfirmed : " + z);
                            if (LoadingGearFragment.this.mActivity != null) {
                                LoadingGearFragment.this.mActivity.runOnUiThread(new Runnable() {
                                    /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass8.AnonymousClass1.AnonymousClass1 */

                                    public void run() {
                                        if (z) {
                                            UpdateUtil.setQAStoreConfirm(true);
                                            LoadingGearFragment.this.runUpdateManager();
                                            return;
                                        }
                                        Toast.makeText(LoadingGearFragment.this.mActivity, "Invalid password, Finish Galaxy Wearable", 1).show();
                                        LoadingGearFragment.this.mActivity.finish();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            });
            this.mDialog = this.mBuilder.create();
            this.mDialog.setCanceledOnTouchOutside(false);
            this.mDialog.show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showUpdateCheckFailDialog(final Activity activity, final String str) {
        final CommonDialog commonDialog = new CommonDialog(activity, 1, 0, 1);
        commonDialog.createDialog();
        commonDialog.setOkBtnClickable(true);
        commonDialog.setCancelable(false);
        commonDialog.setTitle(getString(R.string.no_network_title));
        commonDialog.setTextToOkBtn(getString(R.string.ok));
        commonDialog.setMessage(getString(R.string.no_network_message));
        commonDialog.setOkBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass9 */

            public void onClick(View view) {
                CommonDialog commonDialog = commonDialog;
                if (commonDialog != null) {
                    commonDialog.setOkBtnClickable(false);
                    if (commonDialog.isShowing()) {
                        commonDialog.dismiss();
                        Activity activity = activity;
                        if (activity != null) {
                            ((SetupWizardWelcomeActivity) activity).doActionsAfterUpdate(str);
                        }
                    }
                }
            }
        });
    }

    public void initUpdateSelectView(int i, final String str) {
        int i2;
        Button button;
        this.mNewFeature = new HashMap<>();
        this.mUpdateList = UpdateUtil.getAppsUpdateList(this.mActivity);
        String str2 = TAG;
        Log.d(str2, "initUpdateSelectView() starts... mUpdateList : " + this.mUpdateList);
        double d2 = (double) i;
        Double.isNaN(d2);
        double d3 = d2 / 1048576.0d;
        this.mUpdateLoading.setVisibility(8);
        this.mUpdateSelect.setVisibility(0);
        this.mApkSize.setText(String.format("%.1f MB", Double.valueOf(d3)));
        changeUpdateDescriptionBySize(d3);
        this.mUpdate.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass4 */

            public void onClick(View view) {
                String str = LoadingGearFragment.TAG;
                Log.d(str, "update start button is pressed... updateList" + LoadingGearFragment.this.mUpdateList);
                if (HostManagerUtils.isSamsungDevice() || !HostManagerUtils.isPlayStoreAvailable(LoadingGearFragment.this.mActivity)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(UpdateNoticeFragment.UPDATE_PLUGIN_BTADDR_KEY, str);
                    LoadingGearFragment.this.goToDownloadInstallStep(bundle);
                    return;
                }
                LoadingGearFragment.this.initUpdateLoadingView();
                LoadingGearFragment.this.goToPlayStoreUpdateStep(str);
            }
        });
        this.mIsForceUpdateNeeded = UpdateUtil.isForceUpdateNeeded(this.mUpdateList);
        if (this.mIsForceUpdateNeeded) {
            button = this.mCancel;
            i2 = R.string.cancel;
        } else {
            button = this.mCancel;
            i2 = R.string.dialog_popup_button_later;
        }
        button.setText(i2);
        this.mCancel.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass5 */

            public void onClick(View view) {
                String str = LoadingGearFragment.TAG;
                Log.d(str, "update cancel button is pressed... updateList" + LoadingGearFragment.this.mUpdateList);
                UpdateUtil.checkForceUpdateWithUpdateList(LoadingGearFragment.this.mUpdateList);
                if (LoadingGearFragment.this.mIsForceUpdateNeeded) {
                    UpdateUtil.showUpdateCancelPopup(LoadingGearFragment.this.mActivity, new View.OnClickListener() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass5.AnonymousClass1 */

                        public void onClick(View view) {
                            UpdateUtil.clearUpdateCheckPref(TWatchManagerApplication.getAppContext());
                            HostManagerUtils.handleTaskInternal();
                            if (LoadingGearFragment.this.mActivity != null) {
                                LoadingGearFragment.this.mActivity.finish();
                            }
                        }
                    });
                    return;
                }
                UpdateUtil.clearUpdateCheckPref(TWatchManagerApplication.getAppContext());
                UpdateHistoryManager.getInstance().updateStatus(LoadingGearFragment.this.mUpdateList, 2);
                ((SetupWizardWelcomeActivity) LoadingGearFragment.this.mActivity).doActionsAfterUpdate();
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

    @Override // com.samsung.android.app.watchmanager.setupwizard.OnBackKeyListener
    public boolean onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        this.mActivity.finish();
        return true;
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
        Activity activity = this.mActivity;
        if (activity != null) {
            try {
                b.a(activity).a(this.mNetworkUsageAgreeReceiver);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
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
        Bundle arguments = getArguments();
        if (arguments != null) {
            int i = arguments.getInt(LOADING_TYPE_KEY, 3);
            String str = TAG;
            Log.d(str, "onViewCreated() loadingType : " + i);
            if (i == 1) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(ACTION_NETWORK_USAGE_AGREE);
                b.a(this.mActivity).a(this.mNetworkUsageAgreeReceiver, intentFilter);
            } else if (i == 2) {
                runUpdateManager();
            }
        }
    }

    public void runUpdateManager() {
        Log.d(TAG, "runUpdateManager() starts...");
        new Handler().postDelayed(new Runnable() {
            /* class com.samsung.android.app.watchmanager.setupwizard.LoadingGearFragment.AnonymousClass3 */

            public void run() {
                if (UpdateUtil.isQAStoreConfirmed()) {
                    LoadingGearFragment loadingGearFragment = LoadingGearFragment.this;
                    loadingGearFragment.mUpdateManager = new UpdateManager(loadingGearFragment.mUpdateCallback, false);
                    LoadingGearFragment.this.mUpdateManager.startUpdateChecking();
                    return;
                }
                SAGUIDHelper.getInstance().getGUID(LoadingGearFragment.this.mGUIDCallback, LoadingGearFragment.this.getActivity());
            }
        }, 1000);
    }
}
