package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.packagecontroller.PackageControllerFactory;
import com.samsung.android.app.twatchmanager.update.FailDialogHelper;
import com.samsung.android.app.twatchmanager.update.PluginExecutor;
import com.samsung.android.app.twatchmanager.update.UpdateDownloadManager;
import com.samsung.android.app.twatchmanager.update.UpdateHistoryManager;
import com.samsung.android.app.twatchmanager.update.UpdateInstallData;
import com.samsung.android.app.twatchmanager.update.UpdateNotificationManager;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import com.samsung.android.app.watchmanager.R;
import java.util.Map;

public class PluginUpdateNoticeFragment extends Fragment implements OnBackKeyListener {
    private static final String TAG = ("tUHM:[Update]" + PluginUpdateNoticeFragment.class.getSimpleName());
    protected Activity mActivity;
    private Button mCancelButton = null;
    private LinearLayout mDownloadContainerLayout;
    private boolean mIsForceUpdateNeeded;
    private PluginExecutor mPluginExecutor;
    private PluginExecutor.IPluginExecutorListener mPluginExecutorListener = new PluginExecutor.IPluginExecutorListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass9 */

        @Override // com.samsung.android.app.twatchmanager.update.PluginExecutor.IPluginExecutorListener
        public void onDownloadEnd(Map<String, String> map) {
            Activity activity = PluginUpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                PluginUpdateNoticeFragment pluginUpdateNoticeFragment = PluginUpdateNoticeFragment.this;
                pluginUpdateNoticeFragment.mSizeInfoText = String.format("(%.1f MB / %.1f MB)", Double.valueOf(pluginUpdateNoticeFragment.mTotalSizeInMB), Double.valueOf(PluginUpdateNoticeFragment.this.mTotalSizeInMB));
                PluginUpdateNoticeFragment.this.showInstallPhaseDescription();
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.PluginExecutor.IPluginExecutorListener
        public void onDownloadError(final UpdateDownloadManager.ErrorCode errorCode) {
            final Activity activity = PluginUpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                activity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass9.AnonymousClass1 */

                    public void run() {
                        PluginUpdateNoticeFragment.this.showDownloadFailPopup(errorCode, activity);
                    }
                });
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.PluginExecutor.IPluginExecutorListener
        public void onDownloadStart(double d2) {
            Activity activity = PluginUpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                PluginUpdateNoticeFragment.this.mUpdateProgressBar.setIndeterminate(false);
                PluginUpdateNoticeFragment.this.mUpdateProgressBar.setProgress(0);
                PluginUpdateNoticeFragment.this.mCancelButton.setAlpha(1.0f);
                PluginUpdateNoticeFragment.this.mCancelButton.setEnabled(true);
            }
            PluginUpdateNoticeFragment.this.mTotalSizeInMB = d2;
        }

        @Override // com.samsung.android.app.twatchmanager.update.PluginExecutor.IPluginExecutorListener
        public void onDownloading(int i, double d2) {
            Activity activity = PluginUpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                PluginUpdateNoticeFragment.this.mUpdateProgressBar.setProgress(i);
                String format = String.format("(%.1f MB / %.1f MB)", Double.valueOf(d2), Double.valueOf(PluginUpdateNoticeFragment.this.mTotalSizeInMB));
                TextView textView = PluginUpdateNoticeFragment.this.mUpdateDescription;
                textView.setText((PluginUpdateNoticeFragment.this.getResources().getString(R.string.uhm_update_download_progress) + " ") + format);
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.PluginExecutor.IPluginExecutorListener
        public void onInstallError(final int i, String str) {
            String str2 = PluginUpdateNoticeFragment.TAG;
            Log.d(str2, "onInstallError() packageName : " + str + " reason : " + i);
            final Activity activity = PluginUpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                activity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass9.AnonymousClass3 */

                    public void run() {
                        Log.d(PluginUpdateNoticeFragment.TAG, "onInstallError() installation failed due to insufficient storage. Showing the MSG to user");
                        PluginUpdateNoticeFragment.this.showInstallErrorPopup(activity, i);
                    }
                });
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.PluginExecutor.IPluginExecutorListener
        public void onInstallStart() {
            Activity activity = PluginUpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                activity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass9.AnonymousClass2 */

                    public void run() {
                        TextView textView = PluginUpdateNoticeFragment.this.mUpdateDescription;
                        textView.setText((PluginUpdateNoticeFragment.this.getResources().getString(R.string.uhm_update_install_progress) + " ") + PluginUpdateNoticeFragment.this.mSizeInfoText);
                    }
                });
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.PluginExecutor.IPluginExecutorListener
        public void onNotifyDisconnectBeforePluginInstall(String str) {
            Log.d(PluginUpdateNoticeFragment.TAG, "onNotifyDisconnectBeforePluginInstall() disconnect the watch device first, and then start install");
            PluginUpdateNoticeFragment.this.mUpdateDescription.setText(PluginUpdateNoticeFragment.this.getResources().getString(R.string.uhm_update_install_disconnect_device, str));
        }
    };
    private String mSizeInfoText;
    private double mTotalSizeInMB = 0.0d;
    private TextView mUpdateDescription = null;
    private IUpdateFragmentListener mUpdateFragmentListener;
    private ProgressBar mUpdateProgressBar = null;

    /* access modifiers changed from: package-private */
    /* renamed from: com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment$10  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType = new int[FailDialogHelper.FailType.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$app$twatchmanager$update$UpdateDownloadManager$ErrorCode = new int[UpdateDownloadManager.ErrorCode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(17:0|(2:1|2)|3|5|6|7|8|9|10|11|12|13|15|16|17|18|20) */
        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|1|2|3|5|6|7|8|9|10|11|12|13|15|16|17|18|20) */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0035 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0053 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x002a */
        static {
            /*
                com.samsung.android.app.twatchmanager.update.FailDialogHelper$FailType[] r0 = com.samsung.android.app.twatchmanager.update.FailDialogHelper.FailType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass10.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType = r0
                r0 = 1
                int[] r1 = com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass10.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.samsung.android.app.twatchmanager.update.FailDialogHelper$FailType r2 = com.samsung.android.app.twatchmanager.update.FailDialogHelper.FailType.DOWNLOAD_FAIL_BY_NETWORK     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass10.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType     // Catch:{ NoSuchFieldError -> 0x001f }
                com.samsung.android.app.twatchmanager.update.FailDialogHelper$FailType r3 = com.samsung.android.app.twatchmanager.update.FailDialogHelper.FailType.DOWNLOAD_URL_RESULT_INVALID     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r2 = com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass10.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType     // Catch:{ NoSuchFieldError -> 0x002a }
                com.samsung.android.app.twatchmanager.update.FailDialogHelper$FailType r3 = com.samsung.android.app.twatchmanager.update.FailDialogHelper.FailType.DOWNLOAD_FAIL_BY_STORAGE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r4 = 3
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r2 = com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass10.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.samsung.android.app.twatchmanager.update.FailDialogHelper$FailType r3 = com.samsung.android.app.twatchmanager.update.FailDialogHelper.FailType.INSTALL_FAIL_BY_UNKNOWN     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r4 = 4
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r2 = com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass10.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.samsung.android.app.twatchmanager.update.FailDialogHelper$FailType r3 = com.samsung.android.app.twatchmanager.update.FailDialogHelper.FailType.INSTALL_FAIL_BY_STORAGE     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r4 = 5
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                com.samsung.android.app.twatchmanager.update.UpdateDownloadManager$ErrorCode[] r2 = com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.ErrorCode.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass10.$SwitchMap$com$samsung$android$app$twatchmanager$update$UpdateDownloadManager$ErrorCode = r2
                int[] r2 = com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass10.$SwitchMap$com$samsung$android$app$twatchmanager$update$UpdateDownloadManager$ErrorCode     // Catch:{ NoSuchFieldError -> 0x0053 }
                com.samsung.android.app.twatchmanager.update.UpdateDownloadManager$ErrorCode r3 = com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.ErrorCode.DOWNLOAD_URL_INVALID     // Catch:{ NoSuchFieldError -> 0x0053 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0053 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0053 }
            L_0x0053:
                int[] r0 = com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass10.$SwitchMap$com$samsung$android$app$twatchmanager$update$UpdateDownloadManager$ErrorCode     // Catch:{ NoSuchFieldError -> 0x005d }
                com.samsung.android.app.twatchmanager.update.UpdateDownloadManager$ErrorCode r2 = com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.ErrorCode.DOWNLOAD_FAILED_BY_LACK_STORAGE     // Catch:{ NoSuchFieldError -> 0x005d }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x005d }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x005d }
            L_0x005d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass10.<clinit>():void");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void prepareDownload() {
        Log.d(TAG, "prepareDownload()");
        Bundle arguments = getArguments();
        this.mUpdateDescription.setText(R.string.uhm_update_download_progress);
        this.mPluginExecutor = new PluginExecutor(this.mPluginExecutorListener, this.mActivity, arguments);
        this.mPluginExecutor.requestDownload(this.mActivity);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showDownloadFailPopup(UpdateDownloadManager.ErrorCode errorCode, final Activity activity) {
        View.OnClickListener onClickListener;
        FailDialogHelper.FailType failType = FailDialogHelper.FailType.DOWNLOAD_FAIL_BY_NETWORK;
        int i = AnonymousClass10.$SwitchMap$com$samsung$android$app$twatchmanager$update$UpdateDownloadManager$ErrorCode[errorCode.ordinal()];
        if (i == 1) {
            failType = FailDialogHelper.FailType.DOWNLOAD_URL_RESULT_INVALID;
        } else if (i == 2) {
            failType = FailDialogHelper.FailType.DOWNLOAD_FAIL_BY_STORAGE;
        }
        final CommonDialog makeFailDialogByType = FailDialogHelper.makeFailDialogByType(activity, failType);
        int i2 = AnonymousClass10.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType[failType.ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                makeFailDialogByType.setOkBtnListener(new View.OnClickListener() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass3 */

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
                });
            } else if (i2 == 3) {
                onClickListener = new View.OnClickListener() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass4 */

                    public void onClick(View view) {
                        CommonDialog commonDialog = makeFailDialogByType;
                        if (commonDialog != null) {
                            commonDialog.setOkBtnClickable(false);
                            if (makeFailDialogByType.isShowing()) {
                                makeFailDialogByType.dismiss();
                                PluginUpdateNoticeFragment.this.finish();
                            }
                        }
                    }
                };
            }
            makeFailDialogByType.setCancelBtnListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass5 */

                public void onClick(View view) {
                    CommonDialog commonDialog = makeFailDialogByType;
                    if (commonDialog != null) {
                        commonDialog.setOkBtnClickable(false);
                        if (makeFailDialogByType.isShowing()) {
                            makeFailDialogByType.dismiss();
                            PluginUpdateNoticeFragment.this.finish();
                        }
                    }
                }
            });
        }
        onClickListener = new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass2 */

            public void onClick(View view) {
                CommonDialog commonDialog = makeFailDialogByType;
                if (commonDialog != null) {
                    commonDialog.setOkBtnClickable(false);
                    if (makeFailDialogByType.isShowing()) {
                        makeFailDialogByType.dismiss();
                        PluginUpdateNoticeFragment.this.prepareDownload();
                    }
                }
            }
        };
        makeFailDialogByType.setOkBtnListener(onClickListener);
        makeFailDialogByType.setCancelBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass5 */

            public void onClick(View view) {
                CommonDialog commonDialog = makeFailDialogByType;
                if (commonDialog != null) {
                    commonDialog.setOkBtnClickable(false);
                    if (makeFailDialogByType.isShowing()) {
                        makeFailDialogByType.dismiss();
                        PluginUpdateNoticeFragment.this.finish();
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showInstallErrorPopup(final Activity activity, int i) {
        FailDialogHelper.FailType failType = FailDialogHelper.FailType.INSTALL_FAIL_BY_UNKNOWN;
        if (PackageControllerFactory.getStorageErrorCode(activity) == i) {
            failType = FailDialogHelper.FailType.INSTALL_FAIL_BY_STORAGE;
        }
        String str = TAG;
        Log.d(str, "Installation failed ! ErrorCode: " + i);
        final CommonDialog makeFailDialogByType = FailDialogHelper.makeFailDialogByType(activity, failType);
        int i2 = AnonymousClass10.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType[failType.ordinal()];
        if (i2 == 4) {
            makeFailDialogByType.setOkBtnListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass6 */

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
            });
        } else if (i2 == 5) {
            makeFailDialogByType.setOkBtnListener(new View.OnClickListener() {
                /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass7 */

                public void onClick(View view) {
                    CommonDialog commonDialog = makeFailDialogByType;
                    if (commonDialog != null) {
                        commonDialog.setOkBtnClickable(false);
                        if (makeFailDialogByType.isShowing()) {
                            makeFailDialogByType.dismiss();
                            PluginUpdateNoticeFragment.this.finish();
                        }
                    }
                }
            });
        }
        makeFailDialogByType.setCancelBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass8 */

            public void onClick(View view) {
                CommonDialog commonDialog = makeFailDialogByType;
                if (commonDialog != null) {
                    commonDialog.setOkBtnClickable(false);
                    if (makeFailDialogByType.isShowing()) {
                        makeFailDialogByType.dismiss();
                        PluginUpdateNoticeFragment.this.finish();
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showInstallPhaseDescription() {
        TextView textView = this.mUpdateDescription;
        textView.setText((getResources().getString(R.string.uhm_update_install_progress) + " ") + this.mSizeInfoText);
        this.mUpdateProgressBar.setIndeterminate(true);
        this.mCancelButton.setAlpha(0.5f);
        this.mCancelButton.setEnabled(false);
    }

    public void finish() {
        String str = TAG;
        Log.d(str, "finish() starts... mIsForceUpdateNeeded : " + this.mIsForceUpdateNeeded + " mUpdateFragmentListener : " + this.mUpdateFragmentListener);
        UpdateHistoryManager.getInstance().updateStatus(this.mPluginExecutor.getUpdatePackageSet(), 2);
        if (!this.mIsForceUpdateNeeded) {
            this.mPluginExecutor.launchPluginAfterUpdate();
        } else if (this.mActivity != null) {
            HostManagerUtils.handleTaskInternal();
            this.mActivity.finish();
        }
    }

    @Override // android.app.Fragment
    public void onAttach(Activity activity) {
        String str = TAG;
        Log.d(str, "onAttach(" + activity + ")");
        super.onAttach(activity);
        try {
            this.mUpdateFragmentListener = (IUpdateFragmentListener) activity;
            this.mActivity = activity;
        } catch (ClassCastException unused) {
            throw new ClassCastException(activity.toString() + " must implement IUpdateFragmentListener");
        }
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.OnBackKeyListener
    public boolean onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        return true;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String str = TAG;
        Log.d(str, "onCreate(" + bundle + ")");
        UpdateNotificationManager.getInstance().cancel();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "onCreateView()");
        View inflate = layoutInflater.inflate(R.layout.setup_splash_waiting_layout, viewGroup, false);
        HostManagerUtils.setStatusBarOpenByNotification(this.mActivity);
        this.mDownloadContainerLayout = (LinearLayout) inflate.findViewById(R.id.download_layout);
        this.mUpdateDescription = (TextView) inflate.findViewById(R.id.statusText);
        this.mUpdateProgressBar = (ProgressBar) inflate.findViewById(R.id.setupProgress);
        this.mCancelButton = (Button) inflate.findViewById(R.id.cancel_btn);
        this.mCancelButton.setVisibility(0);
        this.mDownloadContainerLayout.setVisibility(0);
        inflate.findViewById(R.id.update_select_layout).setVisibility(8);
        inflate.findViewById(R.id.update_loading_layout).setVisibility(8);
        return inflate;
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        PluginExecutor pluginExecutor = this.mPluginExecutor;
        if (pluginExecutor != null) {
            pluginExecutor.clearResources();
        }
        Log.d(TAG, "onDestroy() disabling CMStateReceiver broadcast receiver");
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
        ((FragmentLifecycleCallbacks) getActivity()).onFragmentDetached(1);
        this.mUpdateFragmentListener = null;
        this.mActivity = null;
    }

    public void onPause() {
        Log.d(TAG, "onPause() starts...");
        super.onPause();
        Log.saveLog();
    }

    public void onResume() {
        super.onResume();
        if (UpdateInstallData.isNonSamsungInstallRequested()) {
            UpdateInstallData.setNonSamsungInstallRequested(false);
            PluginExecutor pluginExecutor = this.mPluginExecutor;
            if (pluginExecutor != null) {
                pluginExecutor.checkAfterPackageInstallerLaunched();
            }
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        int i;
        Button button;
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, bundle);
        view.setImportantForAccessibility(1);
        prepareDownload();
        this.mIsForceUpdateNeeded = UpdateUtil.isForceUpdateNeeded(this.mPluginExecutor.getUpdatePackageSet());
        if (this.mIsForceUpdateNeeded) {
            button = this.mCancelButton;
            i = R.string.cancel;
        } else {
            button = this.mCancelButton;
            i = R.string.dialog_popup_button_later;
        }
        button.setText(i);
        this.mCancelButton.setAlpha(0.5f);
        this.mCancelButton.setEnabled(false);
        this.mCancelButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass1 */

            public void onClick(View view) {
                UpdateUtil.checkForceUpdateWithUpdateList(PluginUpdateNoticeFragment.this.mPluginExecutor.getUpdatePackageSet());
                if (PluginUpdateNoticeFragment.this.mIsForceUpdateNeeded) {
                    UpdateUtil.showUpdateCancelPopup(PluginUpdateNoticeFragment.this.getActivity(), new View.OnClickListener() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.PluginUpdateNoticeFragment.AnonymousClass1.AnonymousClass1 */

                        public void onClick(View view) {
                            PluginUpdateNoticeFragment.this.finish();
                        }
                    });
                } else {
                    PluginUpdateNoticeFragment.this.finish();
                }
            }
        });
    }
}
