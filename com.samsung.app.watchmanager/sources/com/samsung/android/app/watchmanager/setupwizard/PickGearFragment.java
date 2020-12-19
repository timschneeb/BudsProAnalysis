package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.manager.ResourceRulesManager;
import com.samsung.android.app.twatchmanager.model.GroupBase;
import com.samsung.android.app.twatchmanager.model.GroupInfo;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.PermissionUtils;
import com.samsung.android.app.twatchmanager.util.SALogUtil;
import com.samsung.android.app.twatchmanager.util.UIUtils;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.RecyclerViewAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import java.util.ArrayList;
import java.util.List;

public class PickGearFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {
    private static final int GROUP_LIMIT = 12;
    private static final int REQUEST_CODE_LOCATION_SETTING = 9001;
    private static final String TAG = ("tUHM:" + PickGearFragment.class.getSimpleName());
    private GroupBase currentClickGroup;
    private RecyclerViewAdapter gridAdapter;
    private List<GroupInfo> groupInfos;
    private ImageView mGradationImageView;
    private CommonDialog mLoactionSettingDialog;
    private LocationManager mLocationManager;
    private int mOnItemClickPosition;
    private View mOnItemClickView;
    private RecyclerView recyclerView;
    private FrameLayout recyclerViewLayout;
    private ResourceRulesManager resourceRulesManager;
    private TextView titleTextView;

    private boolean checkLocationSetting() {
        if (Build.VERSION.SDK_INT <= 28 || this.mLocationManager.isLocationEnabled()) {
            return true;
        }
        this.mLoactionSettingDialog = new CommonDialog(getActivity(), 1, 0, 1);
        this.mLoactionSettingDialog.createDialog();
        this.mLoactionSettingDialog.setTitle(getString(R.string.turn_on_location_title));
        this.mLoactionSettingDialog.setMessage(getString(R.string.turn_on_location_desc));
        this.mLoactionSettingDialog.setTextToOkBtn(getString(R.string.settings));
        this.mLoactionSettingDialog.setCancelable(true);
        this.mLoactionSettingDialog.setOkBtnListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PickGearFragment.AnonymousClass3 */

            public void onClick(View view) {
                PickGearFragment.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), PickGearFragment.REQUEST_CODE_LOCATION_SETTING);
            }
        });
        return false;
    }

    private List<String> checkPermissionMineNotHereClicked() {
        return PermissionFragment.arePermissionsgranted(getActivity(), PermissionUtils.INITIAL_PERMISSION);
    }

    private ArrayList<ImageItem> populateData() {
        int i = 12;
        ArrayList<ImageItem> arrayList = new ArrayList<>(12);
        if (this.resourceRulesManager.isResourceInfoAvailable()) {
            this.groupInfos = this.resourceRulesManager.getGearGroupInfo();
            if (HostManagerUtils.isTablet()) {
                ArrayList arrayList2 = new ArrayList();
                for (GroupInfo groupInfo : this.groupInfos) {
                    syncRulesIfNecessary();
                    if (GearRulesManager.getInstance().getGearInfo(groupInfo.name).supportTablet) {
                        arrayList2.add(groupInfo);
                    }
                }
                this.groupInfos = arrayList2;
            }
            List<GroupInfo> list = this.groupInfos;
            if (list != null && list.size() > 0) {
                int size = this.groupInfos.size();
                if (size <= 12) {
                    i = size;
                }
                for (int i2 = 0; i2 < i; i2++) {
                    GroupInfo groupInfo2 = this.groupInfos.get(i2);
                    arrayList.add(new ImageItem(groupInfo2.getGroupImageInfo(GroupInfo.InfoType.ICON), groupInfo2.getTitle()));
                }
            }
        }
        return arrayList;
    }

    private void startDeviceList(Bundle bundle) {
        Log.d(TAG, "startDeviceList()");
        Activity activity = getActivity();
        if (activity instanceof SetupWizardWelcomeActivity) {
            SetupWizardWelcomeActivity setupWizardWelcomeActivity = (SetupWizardWelcomeActivity) activity;
            setupWizardWelcomeActivity.setLaunchMode(GlobalConst.LAUNCH_MODE_DEVICE_LIST);
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(GlobalConst.EXTRA_FROM_PICK_GEAR_FRAGMENT, true);
            setupWizardWelcomeActivity.updateFragment(4, bundle);
        }
    }

    private void startDeviceScanningInPairingFragment() {
        Bundle bundle = new Bundle();
        Activity activity = getActivity();
        if (activity instanceof SetupWizardWelcomeActivity) {
            bundle.putBoolean(GlobalConst.EXTRA_FROM_PICK_GEAR_FRAGMENT, true);
            bundle.putString(GlobalConst.GROUP_NAME_ARG, this.currentClickGroup.getName());
            bundle.putBoolean(GlobalConst.SHOW_SCANNING_LAYOUT, true);
            ((SetupWizardWelcomeActivity) activity).updateFragment(6, bundle);
        }
    }

    private void startTUHMPluginPermissionFragment(Bundle bundle) {
        Log.d(TAG, "startDeviceList()");
        Activity activity = getActivity();
        if (activity instanceof SetupWizardWelcomeActivity) {
            bundle.putBoolean(GlobalConst.EXTRA_FROM_PICK_GEAR_FRAGMENT_TO_ALL_PERMISSIONS_FRAGMENTS, true);
            ((SetupWizardWelcomeActivity) activity).updateFragment(7, bundle);
        }
    }

    private void startTUHMPluginPermissionFragment(boolean z, List<String> list) {
        Bundle bundle = new Bundle();
        if (z) {
            bundle.putInt(PermissionsGuideFragment.CALLEDBY_FRAGMENT_CASE, 2);
        } else {
            bundle.putInt(PermissionsGuideFragment.CALLEDBY_FRAGMENT_CASE, 1);
            bundle.putString(GlobalConst.GROUP_NAME_ARG, this.currentClickGroup.getName());
        }
        bundle.putStringArrayList(PermissionsGuideFragment.PERMISSIONS_NOT_GRANTED_LIST, new ArrayList<>(list));
        startTUHMPluginPermissionFragment(bundle);
    }

    private void syncRulesIfNecessary() {
        if (!GearRulesManager.getInstance().isDeviceInfoAvailable()) {
            Log.e(TAG, "deviceInfo is not available, need to parse xml");
            GearRulesManager.getInstance().syncGearInfoSynchronously();
        }
    }

    public void initGearIconList() {
        Log.d(TAG, "initGearIconList() starts ... ");
        this.gridAdapter.setData(populateData());
        this.gridAdapter.notifyDataSetChanged();
        this.recyclerView.setAdapter(this.gridAdapter);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        CommonDialog commonDialog;
        String str = TAG;
        Log.d(str, "onActivityResult() requestCode : " + i + ", resultCode : " + i2);
        if (i == REQUEST_CODE_LOCATION_SETTING && Build.VERSION.SDK_INT > 28 && this.mLocationManager.isLocationEnabled() && (commonDialog = this.mLoactionSettingDialog) != null && commonDialog.isShowing()) {
            this.mLoactionSettingDialog.dismiss();
            onItemClick(this.mOnItemClickView, this.mOnItemClickPosition);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.currentClickGroup = null;
        this.resourceRulesManager = ResourceRulesManager.getInstance();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "onCreateView() starts");
        super.onCreate(bundle);
        View inflate = layoutInflater.inflate(R.layout.select_gear_layout, viewGroup, false);
        this.gridAdapter = new RecyclerViewAdapter(getActivity());
        this.gridAdapter.setClickListener(this);
        this.recyclerViewLayout = (FrameLayout) inflate.findViewById(R.id.recyclerViewLayout);
        this.recyclerViewLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PickGearFragment.AnonymousClass1 */

            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (PickGearFragment.this.recyclerView.getHeight() == PickGearFragment.this.recyclerViewLayout.getHeight()) {
                    Log.d(PickGearFragment.TAG, "making recycler view scrollable");
                    Fragment parentFragment = PickGearFragment.this.getParentFragment();
                    if (parentFragment instanceof PromotionFragment) {
                        ((PromotionFragment) parentFragment).setScrollableView(PickGearFragment.this.recyclerView);
                    }
                }
            }
        });
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.grid);
        UIUtils.setProperWidth(getActivity(), this.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        this.recyclerView.a(new VerticalSpaceItemDecoration((int) getResources().getDimension(R.dimen.select_product_vertical_spacing)));
        this.mGradationImageView = (ImageView) inflate.findViewById(R.id.topViewGradiant);
        this.titleTextView = (TextView) inflate.findViewById(R.id.title);
        return inflate;
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy() starts");
        super.onDestroy();
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.RecyclerViewAdapter.ItemClickListener
    public void onItemClick(View view, int i) {
        this.mOnItemClickView = view;
        this.mOnItemClickPosition = i;
        List<String> checkPermissionMineNotHereClicked = checkPermissionMineNotHereClicked();
        if (this.mOnItemClickPosition == this.gridAdapter.getItemCount() - 1) {
            SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_PICK_GEAR, SALogUtil.SA_LOG_EVENT_MINES_NOT_HERE, "Search all devices");
            if (!checkPermissionMineNotHereClicked.isEmpty()) {
                startTUHMPluginPermissionFragment(true, checkPermissionMineNotHereClicked);
            } else if (checkLocationSetting()) {
                startDeviceList(null);
            }
        } else {
            this.currentClickGroup = this.groupInfos.get(this.mOnItemClickPosition);
            String str = TAG;
            Log.d(str, "clicked on : " + this.currentClickGroup);
            if (!checkPermissionMineNotHereClicked.isEmpty()) {
                startTUHMPluginPermissionFragment(false, checkPermissionMineNotHereClicked);
            } else if (checkLocationSetting()) {
                startDeviceScanningInPairingFragment();
            }
        }
    }

    public void onMultiWindowModeChanged(boolean z, Configuration configuration) {
        String str = TAG;
        Log.d(str, "onMultiWindowModeChanged :" + z);
        super.onMultiWindowModeChanged(z, configuration);
        this.gridAdapter.updateViewsForMultiWindowCase(z);
        this.gridAdapter.notifyDataSetChanged();
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public void onViewCreated(View view, Bundle bundle) {
        Log.d(TAG, "onViewCreated()");
        super.onViewCreated(view, bundle);
        if (ResourceRulesManager.getInstance().isResourceInfoAvailable()) {
            initGearIconList();
        } else {
            ResourceRulesManager.getInstance().syncGearInfo(1, new ResourceRulesManager.ISyncCallback() {
                /* class com.samsung.android.app.watchmanager.setupwizard.PickGearFragment.AnonymousClass2 */

                @Override // com.samsung.android.app.twatchmanager.manager.ResourceRulesManager.ISyncCallback
                public void onSyncComplete(int i, boolean z) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.PickGearFragment.AnonymousClass2.AnonymousClass1 */

                        public void run() {
                            PickGearFragment.this.initGearIconList();
                        }
                    });
                }
            });
        }
        this.mLocationManager = (LocationManager) getActivity().getSystemService("location");
        if (Build.VERSION.SDK_INT >= 24) {
            this.gridAdapter.updateViewsForMultiWindowCase(getActivity().isInMultiWindowMode());
            this.gridAdapter.notifyDataSetChanged();
        }
    }

    public void updateTitleMargin(boolean z, SlidingUpPanelLayout.c cVar) {
        int i;
        TextView textView;
        if (cVar == SlidingUpPanelLayout.c.DRAGGING) {
            Activity activity = getActivity();
            int dimension = (int) activity.getResources().getDimension(R.dimen.action_bar_h);
            int dimension2 = (int) activity.getResources().getDimension(R.dimen.promotion_top_title_height);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.titleTextView.getLayoutParams();
            if (z) {
                marginLayoutParams.topMargin = dimension;
            } else {
                marginLayoutParams.topMargin = dimension2;
            }
            this.titleTextView.setLayoutParams(marginLayoutParams);
        }
        if (z) {
            this.mGradationImageView.setVisibility(4);
            textView = this.titleTextView;
            i = getResources().getColor(R.color.navigationbar_color_dark);
        } else {
            i = 0;
            this.mGradationImageView.setVisibility(0);
            textView = this.titleTextView;
        }
        textView.setBackgroundColor(i);
    }
}
