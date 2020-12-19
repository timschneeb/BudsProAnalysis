package com.samsung.android.app.watchmanager.setupwizard;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.ActivityUtils;
import com.samsung.android.app.twatchmanager.util.PermissionUtils;
import com.samsung.android.app.watchmanager.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionFragment extends Fragment {
    private static final String FRAGMENT_TAG = "PermissionFragment";
    private static final int REQUEST_CODE_INITIAL_PERMISSIONS = 5003;
    private static final String TAG = ("tUHM:" + PermissionFragment.class.getSimpleName());
    private Activity mActivity;
    private String[] mPermissions;
    private IGrantedTask mTask;
    private ArrayList<String> normalPermissions;
    private ArrayList<String> settingsPermissions;

    public interface IGrantedTask {
        void doTask();
    }

    public interface IImprovedGrantedTask extends IGrantedTask {
        void onFinish();
    }

    public static List<String> arePermissionsgranted(Activity activity, String[] strArr) {
        ArrayList arrayList = new ArrayList();
        if (strArr != null) {
            for (String str : strArr) {
                if (activity.checkCallingOrSelfPermission(str) != 0) {
                    Log.d(TAG, "verifyPermissions, permission [" + str + "] + not granted");
                    arrayList.add(str);
                }
            }
        }
        return arrayList;
    }

    private void finishFragment() {
        String str = TAG;
        Log.d(str, "finishFragment, mActivity [" + this.mActivity + "]");
        Activity activity = this.mActivity;
        if (activity != null) {
            ActivityUtils.removeFragment(activity.getFragmentManager(), this);
        }
    }

    private static PermissionFragment getInstance(IGrantedTask iGrantedTask, String[] strArr) {
        PermissionFragment permissionFragment = new PermissionFragment();
        permissionFragment.mTask = iGrantedTask;
        permissionFragment.mPermissions = strArr;
        return permissionFragment;
    }

    public static void verifyPermissions(Activity activity, IGrantedTask iGrantedTask, String[] strArr) {
        Log.d(TAG, "verifyPermissions starts, activity [" + activity + "], task [" + iGrantedTask + "], permissions [" + Arrays.toString(strArr) + "]");
        if (activity == null || strArr == null || Build.VERSION.SDK_INT < 23) {
            Log.e(TAG, "could not check permissions");
            if (iGrantedTask != null) {
                iGrantedTask.doTask();
                return;
            }
            return;
        }
        boolean z = true;
        if (strArr != null) {
            boolean z2 = true;
            for (String str : strArr) {
                boolean z3 = activity.checkCallingOrSelfPermission(str) == 0;
                z2 &= z3;
                Log.d(TAG, "verifyPermissions, permission [" + str + "] has value [" + z3 + "], entire granted [" + z2 + "]");
            }
            z = z2;
        }
        if (z) {
            for (String str2 : strArr) {
                PermissionUtils.setNeverShow(PermissionUtils.getRequestCode(str2), false);
            }
            if (iGrantedTask != null) {
                iGrantedTask.doTask();
                return;
            }
            return;
        }
        ActivityUtils.addFragmentToActivity(activity.getFragmentManager(), getInstance(iGrantedTask, strArr), R.id.container, FRAGMENT_TAG);
    }

    @Override // android.app.Fragment
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach()");
        super.onAttach(activity);
        this.mActivity = activity;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
        String str = TAG;
        Log.d(str, "onCreate, activity [" + this.mActivity + "], task [" + this.mTask + "], permissions [" + Arrays.toString(this.mPermissions) + "]");
        if (this.mPermissions == null || this.mActivity == null) {
            IGrantedTask iGrantedTask = this.mTask;
            if (iGrantedTask != null) {
                iGrantedTask.doTask();
            }
            finishFragment();
            return;
        }
        requestPermission();
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        this.settingsPermissions = null;
        this.normalPermissions = null;
        this.mTask = null;
        this.mPermissions = null;
    }

    public void onDetach() {
        Log.d(TAG, "onDetach()");
        super.onDetach();
        ((FragmentLifecycleCallbacks) getActivity()).onFragmentDetached(1);
        this.mActivity = null;
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        Log.d(TAG, "onRequestPermissionsResult()");
        if (i == 5003) {
            if (strArr == null || strArr.length <= 0) {
                IGrantedTask iGrantedTask = this.mTask;
                if (iGrantedTask instanceof IImprovedGrantedTask) {
                    ((IImprovedGrantedTask) iGrantedTask).onFinish();
                }
                this.mActivity.finish();
            } else {
                for (int i2 = 0; i2 < strArr.length; i2++) {
                    String str = TAG;
                    Log.d(str, "onRequestPermissionsResult() permission:" + strArr[i2] + " grant value:" + iArr[i2]);
                    if (iArr[i2] == 0) {
                        PermissionUtils.setNeverShow(PermissionUtils.getRequestCode(strArr[i2]), false);
                    } else if (!shouldShowRequestPermissionRationale(strArr[i2])) {
                        PermissionUtils.setNeverShow(PermissionUtils.getRequestCode(strArr[i2]), true);
                    }
                }
                ArrayList<String> arrayList = this.settingsPermissions;
                if (arrayList == null || arrayList.size() <= 0) {
                    this.mTask.doTask();
                }
            }
        }
        finishFragment();
    }

    public void onViewCreated(View view, Bundle bundle) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, bundle);
        view.setImportantForAccessibility(1);
    }

    /* access modifiers changed from: package-private */
    @TargetApi(23)
    public void requestPermission() {
        ArrayList<String> arrayList;
        String[] strArr = this.mPermissions;
        for (String str : strArr) {
            if (this.mActivity.checkCallingOrSelfPermission(str) != 0) {
                if (shouldShowRequestPermissionRationale(str) || !PermissionUtils.isNeverShowEnabled(PermissionUtils.getRequestCode(str))) {
                    if (this.normalPermissions == null) {
                        this.normalPermissions = new ArrayList<>(this.mPermissions.length);
                    }
                    arrayList = this.normalPermissions;
                } else {
                    if (this.settingsPermissions == null) {
                        this.settingsPermissions = new ArrayList<>(this.mPermissions.length);
                    }
                    arrayList = this.settingsPermissions;
                }
                arrayList.add(str);
            }
        }
        Log.d(TAG, "requestPermission() settingsPermissions:" + this.settingsPermissions);
        Log.d(TAG, "requestPermission() normalPermissions:" + this.normalPermissions);
        ArrayList<String> arrayList2 = this.normalPermissions;
        if (arrayList2 == null || arrayList2.size() <= 0) {
            ArrayList<String> arrayList3 = this.settingsPermissions;
            if (arrayList3 == null || arrayList3.size() <= 0) {
                IGrantedTask iGrantedTask = this.mTask;
                if (iGrantedTask != null) {
                    iGrantedTask.doTask();
                }
            } else {
                Activity activity = this.mActivity;
                PermissionUtils.showPermissionSettingsDialog(activity, activity.getResources().getString(R.string.app_name), this.settingsPermissions, true);
                this.settingsPermissions = null;
            }
            finishFragment();
            return;
        }
        requestPermissions((String[]) this.normalPermissions.toArray(new String[this.normalPermissions.size()]), 5003);
        this.normalPermissions = null;
    }
}
