package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.PermissionUtils;
import com.samsung.android.app.twatchmanager.util.SALogUtil;
import com.samsung.android.app.twatchmanager.util.UIUtils;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import com.samsung.android.app.watchmanager.setupwizard.PermissionFragment;
import com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PermissionsGuideFragment extends Fragment implements OnBackKeyListener, SetupWizardWelcomeActivity.IMultiWindowListener {
    public static final String CALLEDBY_FRAGMENT_CASE = "calledby_fragment_case";
    private static final String[] EXTRA_PERMISSIONS = {"android.permission.INSTALL_PACKAGES", "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"};
    public static final String IS_PERMMISION_ALREADY_SHOWN = "is_permission_already_shown";
    public static final String PERMISSIONS_NOT_GRANTED_LIST = "permissions_not_granted_list";
    private static final String[][] PERMISSION_DESCRIPTION_MAP = {new String[]{"android.permission.ACCESS_FINE_LOCATION", "2131624113"}, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "2131624115"}, new String[]{"android.permission.READ_PHONE_STATE", "2131624114"}, new String[]{"android.permission.GET_ACCOUNTS", "2131624111"}, new String[]{"android.permission.INSTALL_PACKAGES", "2131624110"}, new String[]{"android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS", "2131624117"}};
    private static final String[][] PERMISSION_JAPAN_DESCRIPTION_MAP = {new String[]{"android.permission.ACCESS_FINE_LOCATION", "2131624113"}, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "2131624115"}, new String[]{"android.permission.READ_PHONE_STATE", "2131624114"}, new String[]{"android.permission.GET_ACCOUNTS", "2131624112"}};
    private static final String[][] PERMISSION_TITLE_MAP = {new String[]{"android.permission.ACCESS_FINE_LOCATION", "2131624116"}, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "2131624120"}, new String[]{"android.permission.READ_PHONE_STATE", "2131624101"}, new String[]{"android.permission.GET_ACCOUNTS", "2131624102"}, new String[]{"android.permission.INSTALL_PACKAGES", "2131624100"}, new String[]{"android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS", "2131624118"}};
    public static final int PICK_GEAR_FRAGMENT_MINE_NOT_HERE = 2;
    public static final int PICK_GEAR_FRAGMENT_SELECT_ITEM = 1;
    private static final int REQUEST_CODE_LOCATION_SETTING = 9001;
    private static final String TAG = ("tUHM:" + PermissionsGuideFragment.class.getSimpleName());
    private RelativeLayout actionbarLayout;
    private int calledBy = 0;
    private String currentClickGroup = "";
    private RelativeLayout logoHeaderLayout;
    private Activity mActivity;
    private CommonDialog mAlertBackgroundLocationDialog;
    private final View.OnClickListener mIconOnClickListener = new View.OnClickListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PermissionsGuideFragment.AnonymousClass2 */

        public void onClick(View view) {
            Log.d(PermissionsGuideFragment.TAG, "inside mIconOnClickListener");
            SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_PERMISSION_GUIDE, SALogUtil.SA_LOG_EVENT_OK_CLICK, PermissionsGuideFragment.this.permissionsNotGranted.size() + " Number of permission items listed");
            if (!BluetoothDiscoveryUtility.isBTEnabled()) {
                new Handler().postDelayed(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.PermissionsGuideFragment.AnonymousClass2.AnonymousClass1 */

                    public void run() {
                        if (PermissionsGuideFragment.this.getActivity() != null) {
                            BluetoothDiscoveryUtility.turnOnBT(PermissionsGuideFragment.this.getActivity(), new BluetoothDiscoveryUtility.IBTStatusOnListener() {
                                /* class com.samsung.android.app.watchmanager.setupwizard.PermissionsGuideFragment.AnonymousClass2.AnonymousClass1.AnonymousClass1 */

                                @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.IBTStatusOnListener
                                public void onStatus(boolean z) {
                                    if (!z && PermissionsGuideFragment.this.getActivity() != null) {
                                        PermissionsGuideFragment.this.getActivity().finish();
                                    } else if (z && PermissionsGuideFragment.this.permissionsNotGranted != null && PermissionsGuideFragment.this.permissionsNotGranted.size() > 0) {
                                        PermissionsGuideFragment.this.takePermissionsFromUser();
                                    }
                                }
                            }, true);
                        }
                    }
                }, 100);
            } else {
                PermissionsGuideFragment.this.takePermissionsFromUser();
            }
        }
    };
    private CommonDialog mLoactionSettingDialog;
    private LocationManager mLocationManager;
    private LinearLayout permissionsLayoutContainer;
    private List<String> permissionsNotGranted;
    private ImageView previewImage;
    private RecyclerView rv;
    private Button startPermissionPopups;
    private TextView switchOnBT;
    private TextView tvNeededPermission;

    private boolean checkLocationSetting() {
        if (Build.VERSION.SDK_INT <= 28 || this.mLocationManager.isLocationEnabled()) {
            return true;
        }
        this.mLoactionSettingDialog = new CommonDialog(this.mActivity, 1, 0, 1);
        this.mLoactionSettingDialog.createDialog();
        this.mLoactionSettingDialog.setTitle(getString(R.string.turn_on_location_title));
        this.mLoactionSettingDialog.setMessage(getString(R.string.turn_on_location_desc));
        this.mLoactionSettingDialog.setTextToOkBtn(getString(R.string.settings));
        this.mLoactionSettingDialog.setCancelable(true);
        this.mLoactionSettingDialog.setOkBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PermissionsGuideFragment.AnonymousClass4 */

            public void onClick(View view) {
                if (PermissionsGuideFragment.this.isAdded()) {
                    PermissionsGuideFragment.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), PermissionsGuideFragment.REQUEST_CODE_LOCATION_SETTING);
                }
            }
        });
        return false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void finishFragment() {
        String str = TAG;
        Log.d(str, "finishFragment, mActivity [" + getActivity() + "]");
        int i = this.calledBy;
        if (i == 1) {
            startDeviceScanningInPairingFragment();
        } else if (i == 2) {
            startDeviceList();
        }
        if (getActivity() != null) {
            Log.d(TAG, "calling popFragment");
            popCurrentFragment();
        }
    }

    private void popCurrentFragment() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else if (getActivity() != null && (getActivity() instanceof SetupWizardWelcomeActivity)) {
            getActivity().finish();
        }
    }

    private void startDeviceList() {
        Log.d(TAG, "startDeviceList()");
        Activity activity = getActivity();
        if (activity instanceof SetupWizardWelcomeActivity) {
            SetupWizardWelcomeActivity setupWizardWelcomeActivity = (SetupWizardWelcomeActivity) activity;
            setupWizardWelcomeActivity.setLaunchMode(GlobalConst.LAUNCH_MODE_DEVICE_LIST);
            Bundle bundle = new Bundle();
            bundle.putBoolean(GlobalConst.EXTRA_FROM_TUHM_PLUGIN_PERMISSIONS_FRAGMENT, true);
            setupWizardWelcomeActivity.updateFragment(4, bundle);
        }
    }

    private void startDeviceScanningInPairingFragment() {
        Bundle bundle = new Bundle();
        Activity activity = getActivity();
        if (activity instanceof SetupWizardWelcomeActivity) {
            bundle.putBoolean(GlobalConst.EXTRA_FROM_TUHM_PLUGIN_PERMISSIONS_FRAGMENT, true);
            bundle.putString(GlobalConst.GROUP_NAME_ARG, this.currentClickGroup);
            bundle.putBoolean(GlobalConst.SHOW_SCANNING_LAYOUT, true);
            ((SetupWizardWelcomeActivity) activity).updateFragment(6, bundle);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void takePermissionsFromUser() {
        List<String> list;
        String str = TAG;
        Log.d(str, "takePermissionsFromUser() starts... permissionsNotGranted : " + this.permissionsNotGranted);
        if (checkLocationSetting() && (list = this.permissionsNotGranted) != null) {
            PermissionFragment.verifyPermissions(getActivity(), new PermissionFragment.IGrantedTask() {
                /* class com.samsung.android.app.watchmanager.setupwizard.PermissionsGuideFragment.AnonymousClass3 */

                @Override // com.samsung.android.app.watchmanager.setupwizard.PermissionFragment.IGrantedTask
                public void doTask() {
                    PermissionsGuideFragment.this.finishFragment();
                }
            }, (String[]) list.toArray(new String[list.size()]));
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        CommonDialog commonDialog;
        String str = TAG;
        Log.d(str, "onActivityResult() requestCode : " + i + ", resultCode : " + i2);
        if (i == REQUEST_CODE_LOCATION_SETTING && Build.VERSION.SDK_INT > 28 && this.mLocationManager.isLocationEnabled() && (commonDialog = this.mLoactionSettingDialog) != null && commonDialog.isShowing()) {
            this.mLoactionSettingDialog.dismiss();
            takePermissionsFromUser();
        }
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.OnBackKeyListener
    public boolean onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        popCurrentFragment();
        return true;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "inside onCreateView()");
        View inflate = layoutInflater.inflate(R.layout.permission_tuhm_plugin_layout, viewGroup, false);
        UIUtils.adjustLogoMargin(inflate.findViewById(R.id.gearManagerLogo));
        return inflate;
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        this.permissionsNotGranted = null;
    }

    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        ((FragmentLifecycleCallbacks) getActivity()).onFragmentDetached(1);
    }

    public void onResume() {
        int i;
        PermissionGuideItem permissionGuideItem;
        PermissionGuideItem permissionGuideItem2;
        super.onResume();
        ArrayList arrayList = new ArrayList(this.permissionsNotGranted.size());
        Iterator<String> it = this.permissionsNotGranted.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String next = it.next();
            if (next.equalsIgnoreCase("android.permission.ACCESS_FINE_LOCATION")) {
                permissionGuideItem2 = new PermissionGuideItem(getResources().getDrawable(Integer.parseInt(PermissionUtils.PERMISSION_DRAWABLE_MAP[0][1])), getResources().getString(Integer.parseInt(PERMISSION_TITLE_MAP[0][1])), getResources().getString(Integer.parseInt(PERMISSION_DESCRIPTION_MAP[0][1])));
            } else if (next.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE")) {
                arrayList.add(new PermissionGuideItem(getResources().getDrawable(Integer.parseInt(PermissionUtils.PERMISSION_DRAWABLE_MAP[1][1])), getResources().getString(Integer.parseInt(PERMISSION_TITLE_MAP[1][1])), getResources().getString(Integer.parseInt(PERMISSION_DESCRIPTION_MAP[1][1]))));
            } else if (next.equalsIgnoreCase("android.permission.READ_PHONE_STATE")) {
                permissionGuideItem2 = new PermissionGuideItem(getResources().getDrawable(Integer.parseInt(PermissionUtils.PERMISSION_DRAWABLE_MAP[2][1])), getResources().getString(Integer.parseInt(PERMISSION_TITLE_MAP[2][1])), getResources().getString(Integer.parseInt(PERMISSION_DESCRIPTION_MAP[2][1])));
            } else if (next.equalsIgnoreCase("android.permission.GET_ACCOUNTS")) {
                permissionGuideItem2 = new PermissionGuideItem(getResources().getDrawable(Integer.parseInt(PermissionUtils.PERMISSION_DRAWABLE_MAP[3][1])), getResources().getString(Integer.parseInt(PERMISSION_TITLE_MAP[3][1])), getResources().getString(Integer.parseInt(HostManagerUtils.isJapanModel() ? PERMISSION_JAPAN_DESCRIPTION_MAP[3][1] : PERMISSION_DESCRIPTION_MAP[3][1])));
            }
            arrayList.add(permissionGuideItem2);
        }
        SharedPreferences sharedPreferences = this.mActivity.getSharedPreferences(GlobalConst.COMMON_PREF_NAME, 0);
        if (!sharedPreferences.getBoolean(IS_PERMMISION_ALREADY_SHOWN, false)) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean(IS_PERMMISION_ALREADY_SHOWN, true);
            edit.apply();
            String[] strArr = EXTRA_PERMISSIONS;
            for (String str : strArr) {
                if (str.equalsIgnoreCase("android.permission.INSTALL_PACKAGES")) {
                    permissionGuideItem = new PermissionGuideItem(getResources().getDrawable(Integer.parseInt(PermissionUtils.PERMISSION_DRAWABLE_MAP[4][1])), getResources().getString(Integer.parseInt(PERMISSION_TITLE_MAP[4][1])), getResources().getString(Integer.parseInt(PERMISSION_DESCRIPTION_MAP[4][1])));
                } else {
                    if (str.equalsIgnoreCase("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS") && !HostManagerUtils.isSamsungDevice()) {
                        permissionGuideItem = new PermissionGuideItem(getResources().getDrawable(Integer.parseInt(PermissionUtils.PERMISSION_DRAWABLE_MAP[5][1])), getResources().getString(Integer.parseInt(PERMISSION_TITLE_MAP[5][1])), getResources().getString(Integer.parseInt(PERMISSION_DESCRIPTION_MAP[5][1])));
                    }
                }
                arrayList.add(permissionGuideItem);
            }
        }
        Log.d(TAG, "permissionGuideItems = " + arrayList);
        this.rv.setLayoutManager(new LinearLayoutManager(getActivity()) {
            /* class com.samsung.android.app.watchmanager.setupwizard.PermissionsGuideFragment.AnonymousClass1 */

            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
            public boolean canScrollVertically() {
                return PermissionsGuideFragment.this.permissionsLayoutContainer.getHeight() <= PermissionsGuideFragment.this.rv.getHeight();
            }
        });
        this.rv.setAdapter(new PermissionGuideAdapter(arrayList));
    }

    public void onViewCreated(View view, Bundle bundle) {
        ImageView.ScaleType scaleType;
        ImageView imageView;
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, bundle);
        view.setImportantForAccessibility(1);
        this.switchOnBT = (TextView) view.findViewById(R.id.switchOnBT);
        this.startPermissionPopups = (Button) view.findViewById(R.id.start_permission_popups);
        this.previewImage = (ImageView) view.findViewById(R.id.topView);
        this.actionbarLayout = (RelativeLayout) view.findViewById(R.id.top_actionbar_title);
        this.logoHeaderLayout = (RelativeLayout) view.findViewById(R.id.top_image_title);
        this.tvNeededPermission = (TextView) view.findViewById(R.id.needsPermissionText);
        this.startPermissionPopups.setOnClickListener(this.mIconOnClickListener);
        this.rv = (RecyclerView) view.findViewById(R.id.list_permissions);
        UIUtils.setProperWidth(getActivity(), this.rv);
        this.permissionsLayoutContainer = (LinearLayout) view.findViewById(R.id.permissions_layout_container);
        Bundle arguments = getArguments();
        this.calledBy = arguments.getInt(CALLEDBY_FRAGMENT_CASE);
        this.permissionsNotGranted = new ArrayList(arguments.getStringArrayList(PERMISSIONS_NOT_GRANTED_LIST));
        this.currentClickGroup = arguments.getString(GlobalConst.GROUP_NAME_ARG);
        String str = TAG;
        Log.d(str, "permissionsNotGranted  " + this.permissionsNotGranted + ", currentClickGroup = " + this.currentClickGroup);
        UIUtils.setHeaderImageWithRules(getActivity(), this.currentClickGroup, this.previewImage, view.findViewById(R.id.gearManagerLogo));
        if (HostManagerUtils.isTablet()) {
            if (UIUtils.isLandScapeMode(getActivity())) {
                imageView = this.previewImage;
                scaleType = ImageView.ScaleType.FIT_CENTER;
            } else {
                imageView = this.previewImage;
                scaleType = ImageView.ScaleType.FIT_XY;
            }
            imageView.setScaleType(scaleType);
            this.previewImage.setMaxHeight((int) getResources().getDimension(R.dimen.promotion_top_gradation_height));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.previewImage.getLayoutParams();
            layoutParams.addRule(14);
            this.previewImage.setLayoutParams(layoutParams);
        } else {
            this.previewImage.setScaleType(ImageView.ScaleType.FIT_START);
        }
        boolean z = false;
        if (!BluetoothDiscoveryUtility.isBTEnabled()) {
            this.switchOnBT.setVisibility(0);
        }
        this.rv.a(new VerticalSpaceItemDecoration((int) getResources().getDimension(R.dimen.permission_guide_vertical_spacing)));
        this.mActivity = getActivity();
        Activity activity = this.mActivity;
        if (activity != null && (activity instanceof SetupWizardWelcomeActivity)) {
            z = ((SetupWizardWelcomeActivity) getActivity()).getCurrentMultiWindowMode();
        }
        updateAfterMultiWindowChanged(z);
        this.mLocationManager = (LocationManager) this.mActivity.getSystemService("location");
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.IMultiWindowListener
    public void updateAfterMultiWindowChanged(boolean z) {
        Activity activity = getActivity();
        if (activity != null) {
            int dimension = (int) activity.getResources().getDimension(R.dimen.action_bar_h);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.tvNeededPermission.getLayoutParams();
            if (z) {
                this.actionbarLayout.setVisibility(0);
                this.logoHeaderLayout.setVisibility(8);
                marginLayoutParams.topMargin = dimension;
                this.tvNeededPermission.setLayoutParams(marginLayoutParams);
                return;
            }
            this.actionbarLayout.setVisibility(8);
            this.logoHeaderLayout.setVisibility(0);
        }
    }
}
