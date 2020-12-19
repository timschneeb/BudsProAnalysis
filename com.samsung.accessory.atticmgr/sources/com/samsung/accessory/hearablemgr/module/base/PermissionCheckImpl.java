package com.samsung.accessory.hearablemgr.module.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.permission.PermissionManager;
import com.samsung.accessory.hearablemgr.common.util.Util;
import seccompat.android.util.Log;

public class PermissionCheckImpl {
    private static final String TAG = (Application.TAG_ + PermissionCheckImpl.class.getSimpleName());
    private AlertDialog dialog;
    private final Activity mActivity;
    private boolean mAutoFinish = true;

    public PermissionCheckImpl(Activity activity) {
        this.mActivity = activity;
    }

    public void setAutoFinish(boolean z) {
        this.mAutoFinish = z;
    }

    public boolean isAllPermissionGranted() {
        boolean isBasicPermissionGranted = PermissionManager.isBasicPermissionGranted(this.mActivity, PermissionManager.ALL_PERMISSION_LIST);
        String str = TAG;
        Log.d(str, "isAllPermissionGranted() : " + isBasicPermissionGranted);
        return isBasicPermissionGranted;
    }

    public void checkPermission() {
        Log.d(TAG, "checkPermission()");
        if (!isAllPermissionGranted()) {
            chkPermission();
        }
    }

    public void dismissDialog() {
        Log.d(TAG, "dismissDialog()");
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void chkPermission() {
        if (Util.getSDKVer() > 22) {
            boolean isSystemDialogEnable = PermissionManager.isSystemDialogEnable(this.mActivity, PermissionManager.ALL_PERMISSION_LIST);
            String str = TAG;
            Log.d(str, "isSystemDialogEnable : " + isSystemDialogEnable);
            if (isSystemDialogEnable) {
                Log.d(TAG, "show System Dialog");
                this.mActivity.requestPermissions(PermissionManager.ALL_PERMISSION_LIST, 0);
                return;
            }
            Log.d(TAG, "show Custom Dialog");
            AlertDialog alertDialog = this.dialog;
            if (alertDialog == null || !alertDialog.isShowing()) {
                Activity activity = this.mActivity;
                this.dialog = PermissionManager.showPermissionSettingsDialog(activity, activity.getString(R.string.app_name), PermissionManager.ALL_PERMISSION_LIST, new PermissionManager.DialogListener() {
                    /* class com.samsung.accessory.hearablemgr.module.base.PermissionCheckImpl.AnonymousClass1 */

                    @Override // com.samsung.accessory.hearablemgr.common.permission.PermissionManager.DialogListener
                    public void onRequestDismissAction() {
                    }

                    @Override // com.samsung.accessory.hearablemgr.common.permission.PermissionManager.DialogListener
                    public void onRequestPositiveAction() {
                        PermissionCheckImpl.this.mActivity.startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + PermissionCheckImpl.this.mActivity.getPackageName())));
                    }

                    @Override // com.samsung.accessory.hearablemgr.common.permission.PermissionManager.DialogListener
                    public void onRequestNegativeAction() {
                        if (PermissionCheckImpl.this.dialog != null) {
                            PermissionCheckImpl.this.dialog.dismiss();
                        }
                        if (PermissionCheckImpl.this.mAutoFinish) {
                            PermissionCheckImpl.this.mActivity.finishAffinity();
                        }
                    }
                });
            }
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        Log.d(TAG, "onRequestPermissionsResult()");
        if (Util.getSDKVer() <= 22) {
            return;
        }
        if (strArr == null || strArr.length <= 0) {
            Log.e(TAG, "wrong permission list");
            return;
        }
        boolean z = true;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (iArr[i2] == -1) {
                Log.v(TAG, "permission = " + strArr[i2] + " , " + iArr[i2] + " , " + this.mActivity.shouldShowRequestPermissionRationale(strArr[i2]));
                if (!this.mActivity.shouldShowRequestPermissionRationale(strArr[i2])) {
                    PermissionManager.setPermissionNeverAskAgain(strArr[i2], true);
                }
                z = true;
            }
        }
        if (z || z) {
            if (this.mAutoFinish) {
                this.mActivity.finishAffinity();
            }
        } else if (z && z) {
            if (Application.getNotificationCoreService() != null) {
                Application.getNotificationCoreService().registerMissedCallObserver();
                Log.d(TAG, "register MissedCallObserver for O");
                return;
            }
            Log.d(TAG, "no remote service 2");
        }
    }
}
