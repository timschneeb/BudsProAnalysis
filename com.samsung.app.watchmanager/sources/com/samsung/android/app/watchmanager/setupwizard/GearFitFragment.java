package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.ActivityUtils;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.watchmanager.R;

public class GearFitFragment extends Fragment {
    private static final String FRAGMENT_TAG = "GearFitFragment";
    private static final String TAG = ("tUHM:" + GearFitFragment.class.getSimpleName());
    private Activity mActivity;
    private IDisconnectTask mTask;

    public interface IDisconnectTask {
        void doTask();
    }

    public static void checkConnection(Activity activity, IDisconnectTask iDisconnectTask) {
        String str = TAG;
        Log.d(str, "checkConnection starts, activity [" + activity + "], task [" + iDisconnectTask + "]");
        if (activity != null) {
            if ("Wingtip".equals(getConnectedWearable(activity))) {
                ActivityUtils.addFragmentToActivity(activity.getFragmentManager(), getInstance(iDisconnectTask), R.id.container, FRAGMENT_TAG);
            } else if (iDisconnectTask != null) {
                iDisconnectTask.doTask();
            }
            Log.d(TAG, "verifyPermissions ends");
        } else if (iDisconnectTask != null) {
            iDisconnectTask.doTask();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void finishFragment() {
        String str = TAG;
        Log.d(str, "finishFragment, mActivity [" + this.mActivity + "]");
        Activity activity = this.mActivity;
        if (activity != null) {
            ActivityUtils.removeFragment(activity.getFragmentManager(), this);
        }
    }

    private static String getConnectedWearable(Context context) {
        String string = HostManagerUtils.isSamsungDevice() ? Settings.System.getString(context.getContentResolver(), "connected_wearable") : "";
        String str = TAG;
        Log.d(str, "getConnectedWearable : " + string);
        return string;
    }

    private static GearFitFragment getInstance(IDisconnectTask iDisconnectTask) {
        GearFitFragment gearFitFragment = new GearFitFragment();
        gearFitFragment.mTask = iDisconnectTask;
        return gearFitFragment;
    }

    @Override // android.app.Fragment
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach()");
        super.onAttach(activity);
        this.mActivity = activity;
    }

    public void onCreate(Bundle bundle) {
        Log.d(TAG, "onCreate");
        super.onCreate(bundle);
        setRetainInstance(true);
        String str = TAG;
        Log.d(str, "onCreate, activity [" + this.mActivity + "], task [" + this.mTask + "]");
        if (this.mActivity == null) {
            IDisconnectTask iDisconnectTask = this.mTask;
            if (iDisconnectTask != null) {
                iDisconnectTask.doTask();
            }
            finishFragment();
            return;
        }
        showDisconnectDialog();
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        this.mTask = null;
    }

    public void onDetach() {
        Log.d(TAG, "onDetach()");
        super.onDetach();
        this.mActivity = null;
    }

    /* access modifiers changed from: package-private */
    public void showDisconnectDialog() {
        Log.d(TAG, "showDisconnectDialog()");
        final CommonDialog commonDialog = new CommonDialog(this.mActivity, 1, 0, 3);
        commonDialog.createDialog();
        commonDialog.setCancelable(false);
        commonDialog.setTitle(getString(R.string.welcome_to_samsung_gear_promotion_connect_to_gear));
        commonDialog.setMessage(getString(R.string.alertdialog_disconnect_popup_new_device_content, getString(R.string.gear_fit_model_name)));
        commonDialog.setOkBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.GearFitFragment.AnonymousClass1 */

            public void onClick(View view) {
                Log.d(GearFitFragment.TAG, "disconnect wearable device");
                commonDialog.dismiss();
                if (HostManagerUtils.isSamsungDevice()) {
                    Settings.System.putString(GearFitFragment.this.mActivity.getContentResolver(), SetupWizardWelcomeActivity.EXTRA_CONNECTED_WEARABLE_ID, "");
                    Settings.System.putString(GearFitFragment.this.mActivity.getContentResolver(), "connected_wearable", "Gear2");
                    Settings.System.putString(GearFitFragment.this.mActivity.getContentResolver(), "connected_wearable", "");
                }
                if (GearFitFragment.this.mTask != null) {
                    GearFitFragment.this.mTask.doTask();
                }
                GearFitFragment.this.finishFragment();
            }
        });
        commonDialog.setCancelBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.GearFitFragment.AnonymousClass2 */

            public void onClick(View view) {
                commonDialog.cancel();
                GearFitFragment.this.mActivity.finish();
            }
        });
    }
}
