package com.samsung.android.app.twatchmanager.update;

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
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.packagecontroller.PackageControllerFactory;
import com.samsung.android.app.twatchmanager.update.FailDialogHelper;
import com.samsung.android.app.twatchmanager.update.StubAPIHelper;
import com.samsung.android.app.twatchmanager.update.UpdateDownloadManager;
import com.samsung.android.app.twatchmanager.update.UpdateInstallManager;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.FragmentLifecycleCallbacks;
import com.samsung.android.app.watchmanager.setupwizard.IUpdateFragmentListener;
import com.samsung.android.app.watchmanager.setupwizard.OnBackKeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UpdateNoticeFragment extends Fragment implements OnBackKeyListener {
    private static final String TAG = ("tUHM:[Update]" + UpdateNoticeFragment.class.getSimpleName());
    public static final String UPDATE_PLUGIN_BTADDR_KEY = "update_plugin_btaddr";
    protected Activity mActivity;
    private String mBtAddress;
    private Button mCancelButton = null;
    private LinearLayout mDownloadContainerLayout;
    private UpdateDownloadManager mDownloadManager;
    private UpdateDownloadManager.IDownloadManagerCallback mDownloadManagerCallback = new UpdateDownloadManager.IDownloadManagerCallback() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass9 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onDownloadAvailable(HashMap<String, StubAPIHelper.XMLResult> hashMap, int i) {
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onDownloading(int i, double d2) {
            Activity activity = UpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                UpdateNoticeFragment.this.mUpdateProgressBar.setProgress(i);
                String format = String.format("(%.1f MB / %.1f MB)", Double.valueOf(d2), Double.valueOf(UpdateNoticeFragment.this.mTotalSizeInMB));
                TextView textView = UpdateNoticeFragment.this.mUpdateDescription;
                textView.setText((UpdateNoticeFragment.this.getResources().getString(R.string.uhm_update_download_progress) + " ") + format);
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onFailToDownload(final UpdateDownloadManager.ErrorCode errorCode) {
            final Activity activity = UpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                activity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass9.AnonymousClass1 */

                    public void run() {
                        UpdateNoticeFragment.this.showDownloadFailPopup(errorCode, activity);
                    }
                });
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onFinishDownload(Map<String, String> map) {
            Activity activity = UpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                UpdateNoticeFragment updateNoticeFragment = UpdateNoticeFragment.this;
                updateNoticeFragment.mSizeInfoText = String.format("(%.1f MB / %.1f MB)", Double.valueOf(updateNoticeFragment.mTotalSizeInMB), Double.valueOf(UpdateNoticeFragment.this.mTotalSizeInMB));
                UpdateNoticeFragment.this.showInstallPhaseDescription();
                UpdateNoticeFragment updateNoticeFragment2 = UpdateNoticeFragment.this;
                updateNoticeFragment2.mInstallManager = new UpdateInstallManager(updateNoticeFragment2.mInstallManagerCallback, UpdateNoticeFragment.this.mBtAddress, map, false);
                UpdateNoticeFragment.this.mInstallManager.pluginInstallProcess();
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.IDownloadManagerCallback
        public void onStartDownload(double d2) {
            Activity activity = UpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                UpdateNoticeFragment.this.mUpdateProgressBar.setIndeterminate(false);
                UpdateNoticeFragment.this.mUpdateProgressBar.setProgress(0);
                UpdateNoticeFragment.this.mCancelButton.setAlpha(1.0f);
                UpdateNoticeFragment.this.mCancelButton.setEnabled(true);
            }
            UpdateNoticeFragment.this.mTotalSizeInMB = d2;
        }
    };
    private UpdateInstallManager mInstallManager;
    private UpdateInstallManager.IInstallManagerCallback mInstallManagerCallback = new UpdateInstallManager.IInstallManagerCallback() {
        /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass10 */

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onDisconnectBeforePluginInstall(String str) {
            Log.d(UpdateNoticeFragment.TAG, "onDisconnectBeforeDownload() disconnect the watch device first, and then start install");
            UpdateNoticeFragment.this.mUpdateDescription.setText(UpdateNoticeFragment.this.getResources().getString(R.string.uhm_update_install_disconnect_device, str));
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onEndOfInstall() {
            Log.d(UpdateNoticeFragment.TAG, "onEndOfInstall() install process is done successfully...");
            UpdateNoticeFragment.this.finish(true);
            UpdateUtil.sendUpdateCompleteBroadcast(UpdateNoticeFragment.this.mBtAddress);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onFailToInstall(final int i, String str) {
            String str2 = UpdateNoticeFragment.TAG;
            Log.d(str2, "onFailToInstall() packageName : " + str + " reason : " + i);
            final Activity activity = UpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                activity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass10.AnonymousClass3 */

                    public void run() {
                        Log.d(UpdateNoticeFragment.TAG, "installation failed due to insufficient storage. Showing the MSG to user");
                        UpdateNoticeFragment.this.showInstallErrorPopup(activity, i);
                    }
                });
            }
            UpdateUtil.sendUpdateCompleteBroadcast(UpdateNoticeFragment.this.mBtAddress);
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onInstallUHM() {
            Activity activity = UpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                activity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass10.AnonymousClass2 */

                    public void run() {
                        final CommonDialog commonDialog = new CommonDialog(UpdateNoticeFragment.this.mActivity, 1, 0, 1);
                        commonDialog.createDialog();
                        commonDialog.setCancelable(false);
                        commonDialog.setCanceledOnTouchOutside(false);
                        commonDialog.setTitle(UpdateNoticeFragment.this.getResources().getString(R.string.uhm_update_reopen_popup_title));
                        commonDialog.setMessage(UpdateNoticeFragment.this.getResources().getString(R.string.uhm_update_reopen_popup_desc));
                        commonDialog.setOkBtnListener(new View.OnClickListener() {
                            /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass10.AnonymousClass2.AnonymousClass1 */

                            public void onClick(View view) {
                                CommonDialog commonDialog = commonDialog;
                                if (commonDialog != null && commonDialog.isShowing()) {
                                    commonDialog.dismiss();
                                }
                                String str = UpdateNoticeFragment.TAG;
                                Log.d(str, "startUHMInstallProcess() start to install tUHM... mInstallManager : " + UpdateNoticeFragment.this.mInstallManager);
                                UpdateUtil.sendUpdateCompleteBroadcast(UpdateNoticeFragment.this.mBtAddress);
                                if (UpdateNoticeFragment.this.mInstallManager != null) {
                                    UpdateNoticeFragment.this.mInstallManager.installTUHMPackage();
                                }
                            }
                        });
                    }
                });
            }
        }

        @Override // com.samsung.android.app.twatchmanager.update.UpdateInstallManager.IInstallManagerCallback
        public void onStartInstall() {
            Activity activity = UpdateNoticeFragment.this.getActivity();
            if (activity != null && !activity.isFinishing()) {
                activity.runOnUiThread(new Runnable() {
                    /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass10.AnonymousClass1 */

                    public void run() {
                        TextView textView = UpdateNoticeFragment.this.mUpdateDescription;
                        textView.setText((UpdateNoticeFragment.this.getResources().getString(R.string.uhm_update_install_progress) + " ") + UpdateNoticeFragment.this.mSizeInfoText);
                    }
                });
            }
        }
    };
    private boolean mIsForceUpdateNeeded;
    private String mSizeInfoText;
    private double mTotalSizeInMB = 0.0d;
    private TextView mUpdateDescription = null;
    private IUpdateFragmentListener mUpdateFragmentListener;
    private Set<String> mUpdateList;
    private ProgressBar mUpdateProgressBar = null;

    /* access modifiers changed from: package-private */
    /* renamed from: com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment$11  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass11 {
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
                com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass11.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType = r0
                r0 = 1
                int[] r1 = com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass11.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.samsung.android.app.twatchmanager.update.FailDialogHelper$FailType r2 = com.samsung.android.app.twatchmanager.update.FailDialogHelper.FailType.DOWNLOAD_FAIL_BY_NETWORK     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass11.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType     // Catch:{ NoSuchFieldError -> 0x001f }
                com.samsung.android.app.twatchmanager.update.FailDialogHelper$FailType r3 = com.samsung.android.app.twatchmanager.update.FailDialogHelper.FailType.DOWNLOAD_URL_RESULT_INVALID     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r2 = com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass11.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType     // Catch:{ NoSuchFieldError -> 0x002a }
                com.samsung.android.app.twatchmanager.update.FailDialogHelper$FailType r3 = com.samsung.android.app.twatchmanager.update.FailDialogHelper.FailType.DOWNLOAD_FAIL_BY_STORAGE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r4 = 3
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r2 = com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass11.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.samsung.android.app.twatchmanager.update.FailDialogHelper$FailType r3 = com.samsung.android.app.twatchmanager.update.FailDialogHelper.FailType.INSTALL_FAIL_BY_UNKNOWN     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r4 = 4
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r2 = com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass11.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.samsung.android.app.twatchmanager.update.FailDialogHelper$FailType r3 = com.samsung.android.app.twatchmanager.update.FailDialogHelper.FailType.INSTALL_FAIL_BY_STORAGE     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r4 = 5
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                com.samsung.android.app.twatchmanager.update.UpdateDownloadManager$ErrorCode[] r2 = com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.ErrorCode.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass11.$SwitchMap$com$samsung$android$app$twatchmanager$update$UpdateDownloadManager$ErrorCode = r2
                int[] r2 = com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass11.$SwitchMap$com$samsung$android$app$twatchmanager$update$UpdateDownloadManager$ErrorCode     // Catch:{ NoSuchFieldError -> 0x0053 }
                com.samsung.android.app.twatchmanager.update.UpdateDownloadManager$ErrorCode r3 = com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.ErrorCode.DOWNLOAD_URL_INVALID     // Catch:{ NoSuchFieldError -> 0x0053 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0053 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0053 }
            L_0x0053:
                int[] r0 = com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass11.$SwitchMap$com$samsung$android$app$twatchmanager$update$UpdateDownloadManager$ErrorCode     // Catch:{ NoSuchFieldError -> 0x005d }
                com.samsung.android.app.twatchmanager.update.UpdateDownloadManager$ErrorCode r2 = com.samsung.android.app.twatchmanager.update.UpdateDownloadManager.ErrorCode.DOWNLOAD_FAILED_BY_LACK_STORAGE     // Catch:{ NoSuchFieldError -> 0x005d }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x005d }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x005d }
            L_0x005d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass11.<clinit>():void");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void prepareDownload() {
        Log.d(TAG, "prepareDownload()");
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mBtAddress = arguments.getString(UPDATE_PLUGIN_BTADDR_KEY, "");
        }
        if (UpdateManager.checkPluginUpdateRemain()) {
            this.mSizeInfoText = "";
            showInstallPhaseDescription();
            Set<String> pluginUpdateRemainSet = UpdateManager.getPluginUpdateRemainSet();
            HashMap hashMap = new HashMap();
            for (String str : pluginUpdateRemainSet) {
                hashMap.put(str, "");
            }
            this.mInstallManager = new UpdateInstallManager(this.mInstallManagerCallback, this.mBtAddress, hashMap, true);
            this.mInstallManager.pluginInstallProcess();
            UpdateManager.clearPluginUpdateRemainPref();
            return;
        }
        this.mUpdateList = UpdateUtil.getAppsUpdateList(TWatchManagerApplication.getAppContext());
        this.mUpdateDescription.setText(R.string.uhm_update_download_progress);
        UpdateDownloadManager updateDownloadManager = this.mDownloadManager;
        if (updateDownloadManager == null) {
            this.mDownloadManager = new UpdateDownloadManager(this.mDownloadManagerCallback, this.mUpdateList, false);
        } else {
            updateDownloadManager.clearResources();
        }
        this.mDownloadManager.startUpdateDownloadManager(this.mActivity);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showDownloadFailPopup(UpdateDownloadManager.ErrorCode errorCode, final Activity activity) {
        View.OnClickListener onClickListener;
        FailDialogHelper.FailType failType = FailDialogHelper.FailType.DOWNLOAD_FAIL_BY_NETWORK;
        int i = AnonymousClass11.$SwitchMap$com$samsung$android$app$twatchmanager$update$UpdateDownloadManager$ErrorCode[errorCode.ordinal()];
        if (i == 1) {
            failType = FailDialogHelper.FailType.DOWNLOAD_URL_RESULT_INVALID;
        } else if (i == 2) {
            failType = FailDialogHelper.FailType.DOWNLOAD_FAIL_BY_STORAGE;
        }
        final CommonDialog makeFailDialogByType = FailDialogHelper.makeFailDialogByType(activity, failType);
        int i2 = AnonymousClass11.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType[failType.ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                makeFailDialogByType.setOkBtnListener(new View.OnClickListener() {
                    /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass3 */

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
                    /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass4 */

                    public void onClick(View view) {
                        CommonDialog commonDialog = makeFailDialogByType;
                        if (commonDialog != null) {
                            commonDialog.setOkBtnClickable(false);
                            if (makeFailDialogByType.isShowing()) {
                                makeFailDialogByType.dismiss();
                                UpdateNoticeFragment.this.finish(false);
                            }
                        }
                    }
                };
            }
            makeFailDialogByType.setCancelBtnListener(new View.OnClickListener() {
                /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass5 */

                public void onClick(View view) {
                    CommonDialog commonDialog = makeFailDialogByType;
                    if (commonDialog != null) {
                        commonDialog.setOkBtnClickable(false);
                        if (makeFailDialogByType.isShowing()) {
                            makeFailDialogByType.dismiss();
                            UpdateNoticeFragment.this.finish(false);
                        }
                    }
                }
            });
        }
        onClickListener = new View.OnClickListener() {
            /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass2 */

            public void onClick(View view) {
                CommonDialog commonDialog = makeFailDialogByType;
                if (commonDialog != null) {
                    commonDialog.setOkBtnClickable(false);
                    if (makeFailDialogByType.isShowing()) {
                        makeFailDialogByType.dismiss();
                        UpdateNoticeFragment.this.prepareDownload();
                    }
                }
            }
        };
        makeFailDialogByType.setOkBtnListener(onClickListener);
        makeFailDialogByType.setCancelBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass5 */

            public void onClick(View view) {
                CommonDialog commonDialog = makeFailDialogByType;
                if (commonDialog != null) {
                    commonDialog.setOkBtnClickable(false);
                    if (makeFailDialogByType.isShowing()) {
                        makeFailDialogByType.dismiss();
                        UpdateNoticeFragment.this.finish(false);
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
        int i2 = AnonymousClass11.$SwitchMap$com$samsung$android$app$twatchmanager$update$FailDialogHelper$FailType[failType.ordinal()];
        if (i2 == 4) {
            makeFailDialogByType.setOkBtnListener(new View.OnClickListener() {
                /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass6 */

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
                /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass7 */

                public void onClick(View view) {
                    CommonDialog commonDialog = makeFailDialogByType;
                    if (commonDialog != null) {
                        commonDialog.setOkBtnClickable(false);
                        if (makeFailDialogByType.isShowing()) {
                            makeFailDialogByType.dismiss();
                            UpdateNoticeFragment.this.finish(false);
                        }
                    }
                }
            });
        }
        makeFailDialogByType.setCancelBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass8 */

            public void onClick(View view) {
                CommonDialog commonDialog = makeFailDialogByType;
                if (commonDialog != null) {
                    commonDialog.setOkBtnClickable(false);
                    if (makeFailDialogByType.isShowing()) {
                        makeFailDialogByType.dismiss();
                        UpdateNoticeFragment.this.finish(false);
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

    public void finish(boolean z) {
        String str = TAG;
        Log.d(str, "finish() starts... mIsForceUpdateNeeded : " + this.mIsForceUpdateNeeded + " (update)isSuccess : " + z + " mUpdateFragmentListener : " + this.mUpdateFragmentListener);
        UpdateDownloadManager updateDownloadManager = this.mDownloadManager;
        if (updateDownloadManager != null) {
            updateDownloadManager.clearResources();
        }
        UpdateHistoryManager.getInstance().updateStatus(this.mUpdateList, z ? 0 : 2);
        if (z || !this.mIsForceUpdateNeeded) {
            IUpdateFragmentListener iUpdateFragmentListener = this.mUpdateFragmentListener;
            if (iUpdateFragmentListener != null) {
                iUpdateFragmentListener.onUpdateFragmentFinished(z, this.mBtAddress);
            }
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
        UpdateDownloadManager updateDownloadManager = this.mDownloadManager;
        if (updateDownloadManager == null) {
            return true;
        }
        updateDownloadManager.clearResources();
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
        UpdateDownloadManager updateDownloadManager = this.mDownloadManager;
        if (updateDownloadManager != null) {
            updateDownloadManager.clearResources();
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
            UpdateInstallManager updateInstallManager = this.mInstallManager;
            if (updateInstallManager != null) {
                updateInstallManager.checkAfterPackageInstallerLaunched();
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
        this.mIsForceUpdateNeeded = UpdateUtil.isForceUpdateNeeded(this.mUpdateList);
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
            /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass1 */

            public void onClick(View view) {
                UpdateUtil.checkForceUpdateWithUpdateList(UpdateNoticeFragment.this.mUpdateList);
                if (UpdateNoticeFragment.this.mIsForceUpdateNeeded) {
                    UpdateUtil.showUpdateCancelPopup(UpdateNoticeFragment.this.getActivity(), new View.OnClickListener() {
                        /* class com.samsung.android.app.twatchmanager.update.UpdateNoticeFragment.AnonymousClass1.AnonymousClass1 */

                        public void onClick(View view) {
                            UpdateNoticeFragment.this.finish(false);
                        }
                    });
                } else {
                    UpdateNoticeFragment.this.finish(false);
                }
            }
        });
    }
}
