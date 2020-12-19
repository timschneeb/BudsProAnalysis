package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.watchmanager.R;

public class NetworkUsageManager {
    private static final String TAG = ("tUHM:" + NetworkUsageManager.class.getSimpleName());
    private Activity mActivity;
    private NetworkListener mListener;

    public interface NetworkListener {
        void onCancel();

        void onContinue();

        void onShow();
    }

    public NetworkUsageManager(Activity activity, NetworkListener networkListener) {
        this.mActivity = activity;
        this.mListener = networkListener;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void continueProcess() {
        Log.d(TAG, "continueProcess starts");
        NetworkListener networkListener = this.mListener;
        if (networkListener != null) {
            networkListener.onContinue();
        }
    }

    private static boolean getDoNotShowNetworkDialog(Context context) {
        boolean z = false;
        if (context != null) {
            z = context.getSharedPreferences(GlobalConst.XML_PROMOTIOIN_IMAGES_AUTO_UPDATE, 0).getBoolean(GlobalConst.DO_NOT_SHOW_NETWORK_DIALOG, false);
        } else {
            Log.d(TAG, "context is null");
        }
        String str = TAG;
        Log.d(str, "getDoNotShowNetworkDialog, result [" + z + "]");
        return z;
    }

    public static boolean isAcceptedNetworkAgreement(Context context) {
        if (!HostManagerUtils.isSamsungChinaModel()) {
            Log.d(TAG, "isAcceptedNetworkAgreement, non china model");
            return true;
        }
        boolean doNotShowNetworkDialog = getDoNotShowNetworkDialog(context);
        String str = TAG;
        Log.d(str, "isAcceptedNetworkAgreement, result [" + doNotShowNetworkDialog + "]");
        return doNotShowNetworkDialog;
    }

    private void showNetworkDialog() {
        boolean isSamsungChinaModel = HostManagerUtils.isSamsungChinaModel();
        boolean doNotShowNetworkDialog = getDoNotShowNetworkDialog(this.mActivity);
        String str = TAG;
        Log.d(str, "showNetworkDialog() isSamsungChinaModel : " + isSamsungChinaModel + " doNotShowNetworkDialog : " + doNotShowNetworkDialog);
        if (!isSamsungChinaModel || doNotShowNetworkDialog) {
            continueProcess();
            return;
        }
        NetworkListener networkListener = this.mListener;
        if (networkListener != null) {
            networkListener.onShow();
        }
        final CommonDialog commonDialog = new CommonDialog(this.mActivity, 1, 0, 3);
        commonDialog.createDialog();
        commonDialog.setTitle(this.mActivity.getString(R.string.use_network_connection_dialog_title));
        commonDialog.setCancelable(false);
        commonDialog.setMessage(this.mActivity.getResources().getString(R.string.use_network_connection_dialog_body, this.mActivity.getResources().getString(R.string.app_name)));
        commonDialog.setCheckCB();
        commonDialog.setCheckBoxListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.NetworkUsageManager.AnonymousClass1 */

            public void onClick(View view) {
                Log.d(NetworkUsageManager.TAG, "NetworkDialog :: onClick checkbox");
                commonDialog.setCheckCB();
            }
        });
        commonDialog.setOkBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.NetworkUsageManager.AnonymousClass2 */

            public void onClick(View view) {
                boolean isCheckedCB = commonDialog.isCheckedCB();
                String str = NetworkUsageManager.TAG;
                Log.d(str, "NetworkDialog :: doNotShow [" + isCheckedCB + "]");
                if (isCheckedCB) {
                    NetworkUsageManager.updateDoNotShowNetworkDialog(NetworkUsageManager.this.mActivity);
                }
                commonDialog.dismiss();
                NetworkUsageManager.this.continueProcess();
            }
        });
        commonDialog.setCancelBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.NetworkUsageManager.AnonymousClass3 */

            public void onClick(View view) {
                Log.d(NetworkUsageManager.TAG, "NetworkDialog :: onClick Cancel");
                if (NetworkUsageManager.this.mListener != null) {
                    NetworkUsageManager.this.mListener.onCancel();
                }
            }
        });
        Log.d(TAG, "showNetworkDialog ends");
    }

    /* access modifiers changed from: private */
    public static void updateDoNotShowNetworkDialog(Context context) {
        String str = TAG;
        Log.d(str, "updateDoNotShowNetworkDialog(" + context + ")");
        if (context != null) {
            SharedPreferences.Editor edit = context.getSharedPreferences(GlobalConst.XML_PROMOTIOIN_IMAGES_AUTO_UPDATE, 0).edit();
            edit.putBoolean(GlobalConst.DO_NOT_SHOW_NETWORK_DIALOG, true);
            edit.apply();
        }
    }

    public void show() {
        Log.d(TAG, "show starts");
        showNetworkDialog();
    }
}
