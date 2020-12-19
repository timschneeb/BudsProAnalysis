package com.samsung.android.app.twatchmanager.update;

import android.content.Context;
import android.os.Build;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import com.samsung.android.app.watchmanager.R;

public class FailDialogHelper {
    public static final String TAG = ("tUHM:[Fail]" + FailDialogHelper.class.getSimpleName());

    public enum FailType {
        UPDATE_CHECK_FAIL_BY_NETWORK(-1, R.string.network_problem_message, R.string.ok, -1),
        DOWNLOAD_FAIL_BY_NETWORK(-1, R.string.network_problem_message, R.string.retry, R.string.not_now),
        DOWNLOAD_URL_RESULT_INVALID(-1, R.string.unable_to_install_message, R.string.welcome_to_samsung_gear_promotion_contact_us, R.string.uhm_update_cancel_popup_button_ok),
        DOWNLOAD_FAIL_BY_STORAGE(R.string.not_enough_space_title, R.string.not_enough_space_message, R.string.ok, -1),
        INSTALL_FAIL_BY_STORAGE(R.string.not_enough_space_title, R.string.not_enough_space_message, R.string.ok, -1),
        INSTALL_FAIL_BY_UNKNOWN(-1, R.string.unable_to_install_message, R.string.welcome_to_samsung_gear_promotion_contact_us, R.string.uhm_update_cancel_popup_button_ok);
        
        private int mCancelId;
        private int mDescId;
        private int mOkId;
        private int mTitleId;

        private FailType(int i, int i2, int i3, int i4) {
            this.mTitleId = i;
            this.mDescId = i2;
            this.mOkId = i3;
            this.mCancelId = i4;
        }

        public int getCancelId() {
            return this.mCancelId;
        }

        public int getDescId() {
            return this.mDescId;
        }

        public int getOkId() {
            return this.mOkId;
        }

        public int getTitleId() {
            return this.mTitleId;
        }
    }

    private static String getStringFromResourceId(Context context, int i) {
        try {
            return context.getString(i);
        } catch (RuntimeException unused) {
            return "";
        }
    }

    public static CommonDialog makeFailDialogByType(Context context, FailType failType) {
        String stringFromResourceId = getStringFromResourceId(context, failType.mTitleId);
        String stringFromResourceId2 = getStringFromResourceId(context, failType.mOkId);
        String stringFromResourceId3 = getStringFromResourceId(context, failType.mCancelId);
        int i = failType.mTitleId == -1 ? 0 : 1;
        int i2 = failType.mCancelId == -1 ? 1 : 3;
        CommonDialog commonDialog = new CommonDialog(context, i, 0, i2);
        commonDialog.createDialog();
        if (i == 1) {
            commonDialog.setTitle(stringFromResourceId);
        }
        if (i2 == 3) {
            commonDialog.setTextToCancelBtn(stringFromResourceId3);
        }
        String stringFromResourceId4 = getStringFromResourceId(context, failType.mDescId);
        if (failType == FailType.INSTALL_FAIL_BY_STORAGE || failType == FailType.DOWNLOAD_FAIL_BY_STORAGE) {
            stringFromResourceId4 = context.getString(FailType.DOWNLOAD_FAIL_BY_STORAGE.getDescId(), context.getString(R.string.app_name));
        }
        if (failType == FailType.INSTALL_FAIL_BY_UNKNOWN) {
            boolean isTestMode4Update = UpdateUtil.isTestMode4Update();
            boolean isLocalInstallation = InstallationUtils.isLocalInstallation();
            boolean isEngBuild = HostManagerUtils.isEngBuild();
            Log.d(TAG, "makeFailDialogByType() QAStore : " + isTestMode4Update + " fakeServerExists : " + isLocalInstallation + " type(phone/apk) : (" + Build.TYPE + "/" + "release" + ")");
            if (isLocalInstallation) {
                stringFromResourceId4 = stringFromResourceId4 + "\n\n** please check fake server test mode";
            }
            if (isEngBuild) {
                stringFromResourceId4 = stringFromResourceId4 + "\n\n** can't install the apk from store on eng phone binary";
            }
        }
        commonDialog.setMessage(stringFromResourceId4);
        commonDialog.setTextToOkBtn(stringFromResourceId2);
        commonDialog.setCancelable(false);
        commonDialog.setOkBtnClickable(true);
        return commonDialog;
    }
}
