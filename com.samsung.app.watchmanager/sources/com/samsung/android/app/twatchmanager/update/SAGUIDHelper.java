package com.samsung.android.app.twatchmanager.update;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.GlobalConst;
import com.samsung.android.app.watchmanager.setupwizard.contactus.connection.SAWebViewActivity;

public class SAGUIDHelper {
    private static final String ACTION_SA_REGISTRATION_CANCELLED = "android.intent.action.REGISTRATION_CANCELED";
    private static final String ACTION_SA_REGISTRATION_CANCELLED_FROM_OOS = "com.samsung.account.REGISTRATION_CANCELED";
    private static final String ACTION_SA_RESIGNIN_COMPLETED = "android.intent.action.SAMSUNGACCOUNT_RESIGNIN_COMPLETED";
    private static final String ACTION_SA_RESIGNIN_COMPLETED_FROM_OOS = "com.samsung.account.SAMSUNGACCOUNT_RESIGNIN_COMPLETED";
    private static final String ACTION_SA_SIGNIN_COMPLETED = "android.intent.action.SAMSUNGACCOUNT_SIGNIN_COMPLETED";
    private static final String ACTION_SA_SIGNIN_COMPLETED_FROM_OOS = "com.samsung.account.SAMSUNGACCOUNT_SIGNIN_COMPLETED";
    private static final String ACTION_SA_SIGNOUT_COMPLETED = "android.intent.action.SAMSUNGACCOUNT_SIGNOUT_COMPLETED";
    private static final String ACTION_SA_SIGNOUT_COMPLETED_FROM_OOS = "com.samsung.account.SAMSUNGACCOUNT_SIGNOUT_COMPLETED";
    public static final int GUID_REQUEST_ID = 1000;
    public static final String GUID_RESULT_KEY = "user_id";
    public static final String SAMSUNG_ACCOUNT_TYPE = "com.osp.app.signin";
    private static final String SA_ACTION_LOGIN = "com.osp.app.signin.action.ADD_SAMSUNG_ACCOUNT";
    private static final String SA_ACTION_LOGIN_POPUP = "com.msc.action.samsungaccount.SIGNIN_POPUP";
    private static final String SA_ACTION_REQUEST_GUID = "com.msc.action.samsungaccount.REQUEST_ACCESSTOKEN";
    public static final String TAG = ("tUHM:[Update]" + SAGUIDHelper.class.getSimpleName());
    private Activity mActivity;
    private IGUIDResultCallback mCallback;
    private BroadcastReceiver mReceiver;

    public interface IGUIDResultCallback {
        void onResult(String str, boolean z);
    }

    /* access modifiers changed from: private */
    public static class LazyHolder {
        private static final SAGUIDHelper INSTANCE = new SAGUIDHelper();

        private LazyHolder() {
        }
    }

    private SAGUIDHelper() {
        this.mReceiver = new BroadcastReceiver() {
            /* class com.samsung.android.app.twatchmanager.update.SAGUIDHelper.AnonymousClass1 */

            /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
            public void onReceive(Context context, Intent intent) {
                char c2;
                String action = intent.getAction();
                switch (action.hashCode()) {
                    case 219672875:
                        if (action.equals(SAGUIDHelper.ACTION_SA_RESIGNIN_COMPLETED_FROM_OOS)) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 491351928:
                        if (action.equals(SAGUIDHelper.ACTION_SA_SIGNIN_COMPLETED_FROM_OOS)) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 551274037:
                        if (action.equals(SAGUIDHelper.ACTION_SA_SIGNIN_COMPLETED)) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1148406364:
                        if (action.equals(SAWebViewActivity.ACTION_SA_WEBVIEW_LOGIN_SUCCESS)) {
                            c2 = 4;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1970244776:
                        if (action.equals(SAGUIDHelper.ACTION_SA_RESIGNIN_COMPLETED)) {
                            c2 = 2;
                            break;
                        }
                        c2 = 65535;
                        break;
                    default:
                        c2 = 65535;
                        break;
                }
                if (c2 == 0 || c2 == 1 || c2 == 2 || c2 == 3) {
                    SAGUIDHelper.this.requestGUIDProcess();
                    return;
                }
                String str = "";
                if (c2 != 4) {
                    SAGUIDHelper.this.mCallback.onResult(str, false);
                    return;
                }
                if (intent.hasExtra(GlobalConst.USER_ID)) {
                    str = intent.getStringExtra(GlobalConst.USER_ID);
                }
                SAGUIDHelper.this.mCallback.onResult(str, !TextUtils.isEmpty(str));
            }
        };
    }

    public static Account[] getAccountArray(Context context, String str) {
        AccountManager accountManager;
        if (context == null || (accountManager = AccountManager.get(context)) == null) {
            return null;
        }
        return accountManager.getAccountsByType(str);
    }

    public static SAGUIDHelper getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static boolean isSignedIn(Context context) {
        Account[] accountArray = getAccountArray(context, SAMSUNG_ACCOUNT_TYPE);
        boolean z = accountArray != null && accountArray.length > 0;
        String str = TAG;
        Log.d(str, "isSignedIn() " + z);
        return z;
    }

    private void launchSALoginActivity() {
        Log.d(TAG, "launchSALoginActivity() starts...");
        Intent intent = new Intent(SA_ACTION_LOGIN);
        intent.putExtra("client_id", GlobalConst.SCS_CLIENT_ID_OF_HM);
        if (Build.VERSION.SDK_INT <= 23) {
            intent.putExtra("client_secret", "USING_CLIENT_PACKAGE_INFORMATION");
        }
        intent.putExtra("mypackage", "com.samsung.android.app.watchmanager");
        intent.putExtra("OSP_VER", "OSP_02");
        intent.putExtra("MODE", "ADD_ACCOUNT");
        try {
            this.mActivity.startActivityForResult(intent, GUID_REQUEST_ID);
        } catch (Exception e) {
            e.printStackTrace();
            intent.setAction(SA_ACTION_LOGIN_POPUP);
            intent.removeExtra("MODE");
            intent.putExtra("theme", "light");
            try {
                this.mActivity.startActivityForResult(intent, GUID_REQUEST_ID);
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.d(TAG, "launchSALoginActivity() unexpected problem...");
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void requestGUIDProcess() {
        Log.d(TAG, "requestGUIDProcess() starts...");
        if (isSignedIn(this.mActivity)) {
            String[] strArr = {GUID_RESULT_KEY};
            Intent intent = new Intent(SA_ACTION_REQUEST_GUID);
            intent.putExtra("client_id", GlobalConst.SCS_CLIENT_ID_OF_HM);
            if (Build.VERSION.SDK_INT <= 23) {
                intent.putExtra("client_secret", "USING_CLIENT_PACKAGE_INFORMATION");
            }
            intent.putExtra("additional", strArr);
            intent.putExtra("progress_theme", "light");
            try {
                this.mActivity.startActivityForResult(intent, GUID_REQUEST_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            requestSALoginPopup();
        }
    }

    private void requestSALoginPopup() {
        String str;
        Log.d(TAG, "requestSALoginPopup() starts...");
        IntentFilter intentFilter = new IntentFilter();
        if (HostManagerUtils.isSamsungDevice()) {
            if (Build.VERSION.SDK_INT >= 26) {
                intentFilter.addAction(ACTION_SA_SIGNIN_COMPLETED_FROM_OOS);
                intentFilter.addAction(ACTION_SA_RESIGNIN_COMPLETED_FROM_OOS);
                intentFilter.addAction(ACTION_SA_SIGNOUT_COMPLETED_FROM_OOS);
                str = ACTION_SA_REGISTRATION_CANCELLED_FROM_OOS;
            } else {
                intentFilter.addAction(ACTION_SA_SIGNIN_COMPLETED);
                intentFilter.addAction(ACTION_SA_RESIGNIN_COMPLETED);
                intentFilter.addAction(ACTION_SA_SIGNOUT_COMPLETED);
                str = ACTION_SA_REGISTRATION_CANCELLED;
            }
            intentFilter.addAction(str);
            launchSALoginActivity();
        } else {
            intentFilter.addAction(SAWebViewActivity.ACTION_SA_WEBVIEW_LOGIN_SUCCESS);
            try {
                this.mActivity.startActivity(new Intent(this.mActivity, SAWebViewActivity.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.mActivity.registerReceiver(this.mReceiver, intentFilter);
    }

    public void getGUID(IGUIDResultCallback iGUIDResultCallback, Activity activity) {
        this.mCallback = iGUIDResultCallback;
        this.mActivity = activity;
        if (this.mCallback != null && this.mActivity != null) {
            requestGUIDProcess();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1000 && i2 == -1) {
            String string = intent.getExtras().getString(GUID_RESULT_KEY);
            if (!TextUtils.isEmpty(string)) {
                this.mCallback.onResult(string, true);
                return;
            }
        }
        this.mCallback.onResult("", false);
    }
}
