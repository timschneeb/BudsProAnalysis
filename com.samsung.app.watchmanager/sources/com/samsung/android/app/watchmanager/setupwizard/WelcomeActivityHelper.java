package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.log.LoggerUtil;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.watchmanager.R;
import java.util.Iterator;
import java.util.List;

public class WelcomeActivityHelper {
    private static final String TAG = ("tUHM:" + WelcomeActivityHelper.class.getSimpleName());

    public static void sendUpdateDBRequestIntent(Context context) {
        Log.d(TAG, "sendUpdateDBRequestIntent()");
        Intent intent = new Intent();
        intent.setAction(GlobalConst.ACTION_UPDATE_DB_REQUEST);
        context.sendBroadcast(intent, "com.samsung.android.hostmanager.permission.ACCESS_UNIFIED_HOST_MANAGER");
    }

    public Fragment getCurrentFragment(Activity activity) {
        Fragment findFragmentById = activity.getFragmentManager().findFragmentById(R.id.container);
        String str = TAG;
        Log.d(str, "getCurrentFragment() :" + findFragmentById);
        return findFragmentById;
    }

    public boolean isFromStubByNFC(Intent intent) {
        boolean z = false;
        boolean booleanExtra = intent.getBooleanExtra(SetupWizardWelcomeActivity.EXTRA_IS_FROM_PLUGIN, false);
        String stringExtra = intent.getStringExtra(SetupWizardWelcomeActivity.EXTRA_BT_ADDR);
        if (intent.hasExtra(SetupWizardWelcomeActivity.EXTRA_IS_FROM_NFC) || (stringExtra != null && stringExtra.length() > 0 && !booleanExtra)) {
            z = true;
        }
        String str = TAG;
        Log.d(str, "isFromStubByNFC() return :" + z);
        return z;
    }

    public boolean isFromWearableInstaller(Intent intent) {
        boolean hasExtra = intent.hasExtra("MODEL_NAME");
        boolean hasExtra2 = intent.hasExtra("BT_ADD_FROM_STUB");
        String str = TAG;
        Log.d(str, "isFromWearableInstaller() has(BT_ADD_FROM_STUB) = " + hasExtra2 + ", has(MODEL_NAME) = " + hasExtra);
        if (intent.hasExtra(SetupWizardWelcomeActivity.EXTRA_CALLER_PACKAGER_NAME)) {
            String str2 = TAG;
            Log.d(str2, "isFromWearableInstaller() has(CALLER_PACKAGE) = " + intent.getStringExtra(SetupWizardWelcomeActivity.EXTRA_CALLER_PACKAGER_NAME));
        }
        return hasExtra2 && hasExtra;
    }

    public void printDB(Context context) {
        List<DeviceRegistryData> queryAllDeviceRegistryData = new RegistryDbManagerWithProvider().queryAllDeviceRegistryData(context);
        if (queryAllDeviceRegistryData != null) {
            Log.d(TAG, "Print all device data:");
            Iterator<DeviceRegistryData> it = queryAllDeviceRegistryData.iterator();
            while (it.hasNext()) {
                String str = TAG;
                Log.d(str, "" + it.next());
            }
            Log.d(TAG, "Print all device data ends");
        }
    }

    public void sendLogging(Context context, boolean z) {
        if (z) {
            LoggerUtil.insertLog(context, "G019", "Phone BT list", null);
        }
    }

    public void setAppBadge(Activity activity) {
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count_package_name", activity.getComponentName().getPackageName());
        intent.putExtra("badge_count_class_name", activity.getComponentName().getClassName());
        String str = TAG;
        Log.d(str, "setAppBadge() packageName :" + activity.getComponentName().getPackageName());
        String str2 = TAG;
        Log.d(str2, "setAppBadge() className :" + activity.getComponentName().getClassName());
        intent.putExtra("badge_count", 0);
        activity.sendBroadcast(intent);
    }

    public void showCustomBinaryDialog(Context context, final Activity activity) {
        Log.d(TAG, "showCustomBinaryDialog");
        final CommonDialog commonDialog = new CommonDialog(context, 1, 0, 1);
        commonDialog.createDialog();
        commonDialog.setMessage(context.getString(R.string.custom_binary_error_dialog_title));
        commonDialog.setTitle(context.getString(R.string.uhm_update_notice));
        commonDialog.setCancelable(false);
        commonDialog.setOkBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.WelcomeActivityHelper.AnonymousClass2 */

            public void onClick(View view) {
                Log.d(WelcomeActivityHelper.TAG, "on click ok button");
                commonDialog.dismiss();
                activity.finish();
            }
        });
    }

    public void showMaximumPowerSavingModeDialog(Context context, final Activity activity) {
        Log.d(TAG, "showMaximumPowerSavingModeDialog");
        final CommonDialog commonDialog = new CommonDialog(context, 1, 0, 1);
        commonDialog.createDialog();
        commonDialog.setMessage(context.getString(R.string.maximum_power_saving_mode_dialog_body) + " " + context.getString(R.string.maximum_power_saving_mode_dialog_body_p2, context.getString(R.string.app_name)));
        commonDialog.setTitle(context.getString(R.string.maximum_power_saving_mode_dialog_title));
        commonDialog.setCancelable(false);
        commonDialog.setOkBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.WelcomeActivityHelper.AnonymousClass1 */

            public void onClick(View view) {
                Log.d(WelcomeActivityHelper.TAG, "on click ok button");
                commonDialog.dismiss();
                activity.finish();
            }
        });
    }

    public void syncRulesIfNecessary() {
        if (!GearRulesManager.getInstance().isDeviceInfoAvailable()) {
            Log.e(TAG, "deviceInfo is not available, need to parse xml");
            GearRulesManager.getInstance().syncGearInfoSynchronously();
        }
    }
}
