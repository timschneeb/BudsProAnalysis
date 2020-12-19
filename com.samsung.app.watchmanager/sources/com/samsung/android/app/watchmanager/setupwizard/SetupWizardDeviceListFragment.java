package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.log.LoggerUtil;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.SALogUtil;
import com.samsung.android.app.twatchmanager.util.StringResourceManagerUtil;
import com.samsung.android.app.twatchmanager.util.UIUtils;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity;
import java.util.ArrayList;
import java.util.List;

public class SetupWizardDeviceListFragment extends BaseDeviceListFragment implements SetupWizardWelcomeActivity.IMultiWindowListener {
    private static final String TAG = ("tUHM:" + SetupWizardDeviceListFragment.class.getSimpleName());
    private static final int VIEW_ID_NO_DEVICE_BAND = 1004;
    private static final int VIEW_ID_NO_DEVICE_COMMON = 1006;
    private static final int VIEW_ID_NO_DEVICE_EARBUD = 1005;
    private static final int VIEW_ID_NO_DEVICE_WATCH = 1003;
    private static final int VIEW_ID_SEARCHING = 1001;
    private RelativeLayout actionbarLayout;
    private boolean basInitCalledOnce = false;
    private ListView deviceListView;
    private final ViewTreeObserver.OnGlobalLayoutListener globalLayoutListner = new ViewTreeObserver.OnGlobalLayoutListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardDeviceListFragment.AnonymousClass1 */

        public void onGlobalLayout() {
            Log.d(SetupWizardDeviceListFragment.TAG, "onGlobalLayout()");
            SetupWizardDeviceListFragment.this.unregisterGlobalLayoutListner();
            if (!SetupWizardDeviceListFragment.this.basInitCalledOnce) {
                Log.d(SetupWizardDeviceListFragment.TAG, "calling baseInit from OnGlobalLayoutListener");
                SetupWizardDeviceListFragment.this.basInitCalledOnce = true;
                SetupWizardDeviceListFragment.this.baseInit();
            }
        }
    };
    private RelativeLayout logoHeaderLayout;
    Bundle mBundle;
    private TextView mContactUs;
    private LinearLayout mContentsLayout;
    private List<BluetoothDiscoveryUtility.BluetoothDeviceItem> mDeviceList;
    private TextView mDeviceNotHereTextView;
    private SetupWizardDeviceListAdapter mDevicesArrayAdapter;
    private String mGroupName;
    private final View.OnClickListener mIconOnClickListener = new View.OnClickListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardDeviceListFragment.AnonymousClass2 */

        /* JADX WARNING: Removed duplicated region for block: B:10:0x007e  */
        /* JADX WARNING: Removed duplicated region for block: B:9:0x0069  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onClick(android.view.View r4) {
            /*
            // Method dump skipped, instructions count: 168
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.SetupWizardDeviceListFragment.AnonymousClass2.onClick(android.view.View):void");
        }
    };
    private LayoutInflater mLayoutInflater;
    private LinearLayout mSearchLayout;
    private TextView mSearchingTitle;
    private final BluetoothDiscoveryUtility.SyncGearInterface mSyncGearInterface = new BluetoothDiscoveryUtility.SyncGearInterface() {
        /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardDeviceListFragment.AnonymousClass3 */
        boolean normalScan = true;

        @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.SyncGearInterface
        public void notifyDataSetChangedMethod(BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem, int i) {
            String str;
            String str2;
            String str3 = SetupWizardDeviceListFragment.TAG;
            Log.d(str3, "ACTION TO BE DONE VALUE:: " + i);
            if (i == 1 && SetupWizardDeviceListFragment.this.mDeviceList != null && SetupWizardDeviceListFragment.this.isCurrentGroupDevice(bluetoothDeviceItem)) {
                SetupWizardDeviceListFragment setupWizardDeviceListFragment = SetupWizardDeviceListFragment.this;
                if (!setupWizardDeviceListFragment.mBluetoothDiscoveryUtility.containsAddress(setupWizardDeviceListFragment.mDeviceList, bluetoothDeviceItem.getAddress())) {
                    String str4 = SetupWizardDeviceListFragment.TAG;
                    Log.d(str4, "mDeviceList  = " + SetupWizardDeviceListFragment.this.mDeviceList);
                    SetupWizardDeviceListFragment.this.mDeviceList.add(bluetoothDeviceItem);
                    SetupWizardDeviceListFragment.this.mDevicesArrayAdapter.notifyDataSetChanged();
                    return;
                }
                str = SetupWizardDeviceListFragment.TAG;
                str2 = "item already exists";
            } else if (i == 2 && SetupWizardDeviceListFragment.this.mDeviceList != null && bluetoothDeviceItem != null) {
                int size = SetupWizardDeviceListFragment.this.mDeviceList.size();
                int i2 = -1;
                for (int i3 = 0; i3 < size; i3++) {
                    if (((BluetoothDiscoveryUtility.BluetoothDeviceItem) SetupWizardDeviceListFragment.this.mDeviceList.get(i3)).getAddress().equals(bluetoothDeviceItem.getAddress())) {
                        String str5 = SetupWizardDeviceListFragment.TAG;
                        Log.d(str5, "The device is there in the paired list at position:: " + i3);
                        i2 = i3;
                    }
                }
                if (i2 > -1) {
                    Log.d(SetupWizardDeviceListFragment.TAG, "Removing the device from the list");
                    SetupWizardDeviceListFragment.this.mDeviceList.remove(i2);
                    return;
                }
                str = SetupWizardDeviceListFragment.TAG;
                str2 = "Device not present in the list";
            } else {
                return;
            }
            Log.d(str, str2);
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.SyncGearInterface
        public void onSyncCompleteCall() {
            Log.d(SetupWizardDeviceListFragment.TAG, "inside onSyncCompleteCall()");
            SetupWizardDeviceListFragment.this.createDeviceListView();
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.SyncGearInterface
        public void refreshBluetoothAdaptorStateChanged() {
            String str = SetupWizardDeviceListFragment.TAG;
            Log.d(str, "onReceive() BT is turned on...isTurnedOnBT = " + SetupWizardDeviceListFragment.this.isTurnedOnBT);
            SetupWizardDeviceListFragment setupWizardDeviceListFragment = SetupWizardDeviceListFragment.this;
            if (setupWizardDeviceListFragment.isTurnedOnBT) {
                CommonDialog commonDialog = setupWizardDeviceListFragment.mDialogBTOn;
                if (commonDialog != null) {
                    commonDialog.dismiss();
                    SetupWizardDeviceListFragment.this.mDialogBTOn = null;
                }
                SetupWizardDeviceListFragment.this.doOnItemClick();
                return;
            }
            setupWizardDeviceListFragment.refreshPairedDevice();
            SetupWizardDeviceListFragment.this.doDiscovery();
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.SyncGearInterface
        public void stopConnectUI() {
            if (this.normalScan) {
                SetupWizardDeviceListFragment setupWizardDeviceListFragment = SetupWizardDeviceListFragment.this;
                if (!setupWizardDeviceListFragment.cancelBTAdapterCalled) {
                    this.normalScan = false;
                    setupWizardDeviceListFragment.doDiscoveryLE();
                    return;
                }
            }
            this.normalScan = true;
            SetupWizardDeviceListFragment setupWizardDeviceListFragment2 = SetupWizardDeviceListFragment.this;
            setupWizardDeviceListFragment2.cancelBTAdapterCalled = false;
            if (setupWizardDeviceListFragment2.getActivity() != null) {
                SetupWizardDeviceListFragment.this.stopConnectUI();
            }
        }
    };
    private ImageView previewImage;
    private ProgressBar progressCircle;
    private TextView searchText;

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getViewID(String str) {
        if (HostManagerUtils.isTablet()) {
            return 1005;
        }
        if (str == null) {
            return 1006;
        }
        String str2 = GearRulesManager.getInstance().getGearInfo(str).group.wearableType;
        if (StringResourceManagerUtil.WATCH_TYPE.equalsIgnoreCase(str2)) {
            return 1003;
        }
        if (StringResourceManagerUtil.BAND_TYPE.equalsIgnoreCase(str2)) {
            return 1004;
        }
        return StringResourceManagerUtil.EARBUD_TYPE.equalsIgnoreCase(str2) ? 1005 : 1006;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isCurrentGroupDevice(BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem) {
        String str;
        String str2;
        if (bluetoothDeviceItem == null) {
            return false;
        }
        if (this.mGroupName == null) {
            str = TAG;
            str2 = "isCurrentGroupDevice()::mGroupName is null.";
        } else {
            String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(bluetoothDeviceItem.getName());
            GearRulesManager instance = GearRulesManager.getInstance();
            List<String> list = null;
            if (instance.isDeviceInfoAvailable()) {
                list = instance.getGroupDeviceNames(this.mGroupName);
            }
            Log.d(TAG, "isCurrentGroupDevice()::simpleName = " + simpleBTNameByName + ", mGroupName = " + this.mGroupName + " filterList : " + list);
            if (list != null) {
                for (String str3 : list) {
                    if (str3 != null && str3.equalsIgnoreCase(simpleBTNameByName)) {
                        str = TAG;
                        str2 = "isCurrentGroupDevice()::name = " + str3;
                    }
                }
            }
            return false;
        }
        Log.d(str, str2);
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void refreshPairedDevice() {
        Log.d(TAG, "refreshPairedDevice()");
        List<BluetoothDiscoveryUtility.BluetoothDeviceItem> refreshPariedDevice = this.mBluetoothDiscoveryUtility.refreshPariedDevice();
        if (refreshPariedDevice != null) {
            for (BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem : refreshPariedDevice) {
                if (!this.mBluetoothDiscoveryUtility.containsAddress(this.mDeviceList, bluetoothDeviceItem.address) && isCurrentGroupDevice(bluetoothDeviceItem)) {
                    this.mDeviceList.add(bluetoothDeviceItem);
                }
            }
            this.mDevicesArrayAdapter.notifyDataSetChanged();
            return;
        }
        Log.d(TAG, "mPairedDevicesList is null");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setContentView(int i) {
        String str;
        StringBuilder sb;
        int i2;
        Resources resources;
        this.mContentsLayout.removeAllViews();
        View inflate = this.mLayoutInflater.inflate(R.layout.layout_devicelist_content_no_devices_guide, (ViewGroup) this.mContentsLayout, false);
        switch (i) {
            case 1001:
                inflate = this.mLayoutInflater.inflate(R.layout.layout_devicelist_content_searching, (ViewGroup) this.mContentsLayout, false);
                this.mSearchingTitle = (TextView) inflate.findViewById(R.id.device_scanning_title_view);
                this.mSearchingTitle.setText(getResources().getString(R.string.scan_for_wearable_devices));
                this.mContactUs.setVisibility(8);
                break;
            case 1003:
                inflate = this.mLayoutInflater.inflate(R.layout.layout_devicelist_content_no_devices, (ViewGroup) this.mContentsLayout, false);
                str = getResources().getString(R.string.no_gear_body) + "\n\n" + getResources().getString(R.string.discovery_guide_description) + "\n\n" + getResources().getString(R.string.watch_guide);
                if (HostManagerUtils.less1_5GbMemory(getActivity())) {
                    str = str + "\n\n" + getString(R.string.less_ram_size_description, "1.5");
                }
                ((TextView) inflate.findViewById(R.id.no_device_found_description)).setText(str);
                this.mContactUs.setVisibility(0);
                break;
            case 1004:
                inflate = this.mLayoutInflater.inflate(R.layout.layout_devicelist_content_no_devices, (ViewGroup) this.mContentsLayout, false);
                sb = new StringBuilder();
                sb.append(getResources().getString(R.string.no_gear_body));
                sb.append("\n\n");
                sb.append(getResources().getString(R.string.discovery_guide_description));
                sb.append("\n\n");
                resources = getResources();
                i2 = R.string.band_guide;
                sb.append(resources.getString(i2));
                str = sb.toString();
                ((TextView) inflate.findViewById(R.id.no_device_found_description)).setText(str);
                this.mContactUs.setVisibility(0);
                break;
            case 1005:
                inflate = this.mLayoutInflater.inflate(R.layout.layout_devicelist_content_no_devices, (ViewGroup) this.mContentsLayout, false);
                sb = new StringBuilder();
                sb.append(getResources().getString(R.string.no_earbud_body_1));
                sb.append("\n\n");
                sb.append(getResources().getString(R.string.no_earbud_body_2));
                sb.append("\n\n");
                resources = getResources();
                i2 = R.string.no_earbud_body_3;
                sb.append(resources.getString(i2));
                str = sb.toString();
                ((TextView) inflate.findViewById(R.id.no_device_found_description)).setText(str);
                this.mContactUs.setVisibility(0);
                break;
            case 1006:
                inflate = this.mLayoutInflater.inflate(R.layout.layout_devicelist_content_no_devices_guide, (ViewGroup) this.mContentsLayout, false);
                TextView textView = (TextView) inflate.findViewById(R.id.learn_more);
                textView.setVisibility(0);
                textView.setPaintFlags(textView.getPaintFlags() | 8);
                textView.setOnClickListener(new View.OnClickListener() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardDeviceListFragment.AnonymousClass6 */

                    public void onClick(View view) {
                        SetupWizardDeviceListFragment.this.setContentView(1005);
                    }
                });
                this.mContactUs.setVisibility(0);
                break;
        }
        this.mContentsLayout.addView(inflate);
    }

    private void setFooterView(View view) {
        if (this.deviceListView.getFooterViewsCount() == 0) {
            this.deviceListView.addFooterView(view);
        }
        this.mDeviceNotHereTextView = (TextView) view.findViewById(R.id.device_not_appear);
        TextView textView = this.mDeviceNotHereTextView;
        textView.setPaintFlags(textView.getPaintFlags() | 8);
        this.mDeviceNotHereTextView.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardDeviceListFragment.AnonymousClass7 */

            public void onClick(View view) {
                SetupWizardDeviceListFragment setupWizardDeviceListFragment = SetupWizardDeviceListFragment.this;
                setupWizardDeviceListFragment.setContentView(setupWizardDeviceListFragment.getViewID(setupWizardDeviceListFragment.mGroupName));
            }
        });
        this.mDeviceNotHereTextView.setVisibility(8);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void unregisterGlobalLayoutListner() {
        View view = getView();
        if (view != null) {
            try {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this.globalLayoutListner);
            } catch (IllegalStateException e) {
                Log.w(TAG, " unregisterGlobalLayoutListner() ", e);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment
    public void doCreateDeviceListView() {
        Log.d(TAG, "doCreateDeviceListView()");
        View view = getView();
        if (view == null) {
            Log.d(TAG, "doCreateDeviceListView() getView() == null");
            return;
        }
        this.deviceListView = (ListView) view.findViewById(R.id.available_devices);
        if (this.deviceListView != null) {
            this.mDeviceList = new ArrayList();
            this.mDevicesArrayAdapter = new SetupWizardDeviceListAdapter(this.mActivity, this.mDeviceList);
            this.deviceListView.setAdapter((ListAdapter) this.mDevicesArrayAdapter);
            refreshPairedDevice();
            startConnectUI();
            this.deviceListView.setOnItemClickListener(this.mDeviceClickListener);
            return;
        }
        Log.d(TAG, "deviceListView is null");
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment
    public BluetoothDiscoveryUtility.SyncGearInterface getSyncGearInterface() {
        return this.mSyncGearInterface;
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment, com.samsung.android.app.watchmanager.setupwizard.OnBackKeyListener
    public boolean onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        this.mIsBackPressed = true;
        cancelBTAdapter();
        this.mBtAdapter = null;
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            Activity activity = this.mActivity;
            if (activity != null && (activity instanceof SetupWizardWelcomeActivity)) {
                activity.finish();
            }
        }
        return true;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "inside onCreateView()");
        this.mLayoutInflater = layoutInflater;
        View inflate = this.mLayoutInflater.inflate(R.layout.layout_setup_wizard_welcome_device_list_fragment, viewGroup, false);
        UIUtils.adjustLogoMargin(inflate.findViewById(R.id.gearManagerLogo));
        return inflate;
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment
    public void onDestroy() {
        Log.d(TAG, "onDestroy() starts");
        ListView listView = this.deviceListView;
        if (listView != null) {
            listView.setAdapter((ListAdapter) null);
            this.deviceListView.setOnItemClickListener(null);
            this.deviceListView = null;
        }
        unregisterGlobalLayoutListner();
        this.previewImage.setImageBitmap(null);
        super.onDestroy();
    }

    public void onViewCreated(View view, Bundle bundle) {
        ImageView.ScaleType scaleType;
        ImageView imageView;
        super.onViewCreated(view, bundle);
        Log.d(TAG, "onViewCreated()");
        view.setImportantForAccessibility(1);
        if (bundle == null) {
            bundle = getArguments() != null ? getArguments() : getActivity().getIntent().getExtras();
        }
        this.mBundle = bundle;
        this.mGroupName = null;
        Bundle bundle2 = this.mBundle;
        if (bundle2 != null) {
            this.mGroupName = bundle2.getString(GlobalConst.GROUP_NAME_ARG);
        }
        this.mContentsLayout = (LinearLayout) view.findViewById(R.id.view_container);
        this.previewImage = (ImageView) view.findViewById(R.id.topView);
        this.mSearchLayout = (LinearLayout) view.findViewById(R.id.search_again);
        boolean z = false;
        this.mSearchLayout.setEnabled(false);
        this.searchText = (TextView) view.findViewById(R.id.search_text);
        this.mSearchLayout.setOnClickListener(this.mIconOnClickListener);
        this.progressCircle = (ProgressBar) view.findViewById(R.id.progress_circle);
        this.actionbarLayout = (RelativeLayout) view.findViewById(R.id.top_actionbar_title);
        this.logoHeaderLayout = (RelativeLayout) view.findViewById(R.id.top_image_title);
        this.mContactUs = (TextView) view.findViewById(R.id.contact_us);
        setContentView(1001);
        this.mContactUs.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardDeviceListFragment.AnonymousClass4 */

            public void onClick(View view) {
                SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_SETUP_DEVICE_LIST_NO_GEAR, SALogUtil.SA_LOG_EVENT_CONTACT_US, "Contact us");
                Log.d(SetupWizardDeviceListFragment.TAG, "OnClick Contact us");
                HostManagerUtils.startSamsungMembers(SetupWizardDeviceListFragment.this.getActivity());
            }
        });
        view.getViewTreeObserver().addOnGlobalLayoutListener(this.globalLayoutListner);
        UIUtils.setHeaderImageWithRules(getActivity(), this.mGroupName, this.previewImage, view.findViewById(R.id.gearManagerLogo));
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
        new Handler().postDelayed(new Runnable() {
            /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardDeviceListFragment.AnonymousClass5 */

            public void run() {
                if (!BluetoothDiscoveryUtility.isBTEnabled() && SetupWizardDeviceListFragment.this.getActivity() != null) {
                    BluetoothDiscoveryUtility.turnOnBT(SetupWizardDeviceListFragment.this.getActivity(), new BluetoothDiscoveryUtility.IBTStatusOnListener() {
                        /* class com.samsung.android.app.watchmanager.setupwizard.SetupWizardDeviceListFragment.AnonymousClass5.AnonymousClass1 */

                        @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.IBTStatusOnListener
                        public void onStatus(boolean z) {
                            if (!z && SetupWizardDeviceListFragment.this.getActivity() != null) {
                                SetupWizardDeviceListFragment.this.getActivity().finish();
                            }
                        }
                    }, true);
                }
            }
        }, 100);
        Activity activity = getActivity();
        if (activity != null && (activity instanceof SetupWizardWelcomeActivity)) {
            z = ((SetupWizardWelcomeActivity) getActivity()).getCurrentMultiWindowMode();
        }
        updateAfterMultiWindowChanged(z);
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment
    public void startConnectUI() {
        Log.d(TAG, "startConnectUI()");
        this.progressCircle.setVisibility(0);
        this.searchText.setText("");
        this.mSearchLayout.setEnabled(false);
        LoggerUtil.insertLog(getActivity(), "G021", "BT device list", null);
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.app.watchmanager.setupwizard.BaseDeviceListFragment
    public void stopConnectUI() {
        this.searchText.setText(getResources().getString(R.string.scan_all_gear_nearby));
        List<BluetoothDiscoveryUtility.BluetoothDeviceItem> list = this.mDeviceList;
        if (list == null || list.size() <= 0) {
            Log.d(TAG, "stopConnectUI()::mDeviceList size is 0");
            setContentView(getViewID(this.mGroupName));
        } else {
            String str = TAG;
            Log.d(str, "stopConnectUI()::mDeviceList size is:: " + this.mDeviceList.size());
            setFooterView(this.mLayoutInflater.inflate(R.layout.item_textview, (ViewGroup) null, false));
            this.mDeviceNotHereTextView.setVisibility(0);
            this.mSearchingTitle.setText(getResources().getString(R.string.which_gear));
        }
        this.progressCircle.setVisibility(8);
        this.mSearchLayout.setEnabled(true);
        BluetoothDiscoveryUtility bluetoothDiscoveryUtility = this.mBluetoothDiscoveryUtility;
        if (bluetoothDiscoveryUtility != null) {
            bluetoothDiscoveryUtility.unregisterReceiver();
        }
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.IMultiWindowListener
    public void updateAfterMultiWindowChanged(boolean z) {
        Activity activity = getActivity();
        if (activity != null) {
            Log.d(TAG, "updateAfterMultiWindowChanged");
            int dimension = (int) activity.getResources().getDimension(R.dimen.action_bar_h);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mContentsLayout.getLayoutParams();
            if (z) {
                this.actionbarLayout.setVisibility(0);
                this.logoHeaderLayout.setVisibility(8);
                marginLayoutParams.topMargin = dimension;
                this.mContentsLayout.setLayoutParams(marginLayoutParams);
                return;
            }
            this.actionbarLayout.setVisibility(8);
            this.logoHeaderLayout.setVisibility(0);
        }
    }
}
