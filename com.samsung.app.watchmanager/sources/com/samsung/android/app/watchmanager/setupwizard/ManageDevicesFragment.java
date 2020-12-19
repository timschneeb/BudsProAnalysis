package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.factory.BluetoothDeviceFactory;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.CommonDialog;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.UIUtils;
import com.samsung.android.app.twatchmanager.util.UninstallManager;
import com.samsung.android.app.watchmanager.ManageDevicesItemAdapter;
import com.samsung.android.app.watchmanager.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ManageDevicesFragment extends Fragment implements OnBackKeyListener {
    private static final String TAG = ("tUHM:" + ManageDevicesFragment.class.getSimpleName());
    private List<packageDelete> dPackageList;
    protected Activity mActivity;
    private CheckBox mAllCheckBox;
    private List<DeviceRegistryData> mCheckedDeviceList;
    private Button mDeleteButton;
    private Dialog mDeleteConfirmdialog = null;
    private ListView mDevicesListview;
    private boolean mIsRemovedAllDevices = false;
    private ManageDevicesItemAdapter mManageDevicesItemAdapter;
    private CommonDialog mProgressDialog;
    private ArrayList<String> mRemoveDeviceList;
    private TextView mTitleTextView;
    private List<DeviceRegistryData> mUnCheckedDeviceList;
    private ArrayList<String> mUninstallPackageList;
    private TextView mainText1;
    private TextView mainText2;
    private final UninstallManager.UninstallationListener uninstallListener = new UninstallManager.UninstallationListener() {
        /* class com.samsung.android.app.watchmanager.setupwizard.ManageDevicesFragment.AnonymousClass1 */

        @Override // com.samsung.android.app.twatchmanager.util.UninstallManager.UninstallationListener
        public void onFinished() {
            Log.d(ManageDevicesFragment.TAG, "onFinished()");
            if (ManageDevicesFragment.this.mProgressDialog != null && ManageDevicesFragment.this.mProgressDialog.isShowing()) {
                ManageDevicesFragment.this.mProgressDialog.dismiss();
            }
            ManageDevicesFragment.this.onBackPressed();
        }
    };

    public static class DeletpackageDialogAdapter extends BaseAdapter {
        private final List<packageDelete> dList;
        private final Activity mContext;

        public static class ViewHolder {
            protected ImageView mImgae;
            protected TextView mName;
        }

        public DeletpackageDialogAdapter(Activity activity, List<packageDelete> list) {
            this.mContext = activity;
            this.dList = list;
        }

        public int getCount() {
            return this.dList.size();
        }

        public Object getItem(int i) {
            return this.dList.get(i).pckname;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.mContext.getLayoutInflater().inflate(R.layout.delete_plugin_package_item, (ViewGroup) null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.mImgae = (ImageView) view.findViewById(R.id.idpckicon);
                viewHolder.mName = (TextView) view.findViewById(R.id.idpckname);
                view.setTag(viewHolder);
            }
            ViewHolder viewHolder2 = (ViewHolder) view.getTag();
            viewHolder2.mImgae.setImageDrawable(this.dList.get(i).getPckIcon());
            viewHolder2.mName.setText(this.dList.get(i).getPckname());
            return view;
        }
    }

    public static class packageDelete {
        private Drawable pckIcon;
        private String pckname;

        public packageDelete(String str, Drawable drawable) {
            this.pckname = str;
            this.pckIcon = drawable;
        }

        public Drawable getPckIcon() {
            return this.pckIcon;
        }

        public String getPckname() {
            return this.pckname;
        }
    }

    private void checkLastLaunchedDevice() {
        if (new RegistryDbManagerWithProvider().queryLastLaunchDeviceRegistryData(getActivity()).size() == 0) {
            List<DeviceRegistryData> list = this.mUnCheckedDeviceList;
            if (list == null || list.size() <= 0) {
                Log.d(TAG, "All devices are removed.");
                this.mIsRemovedAllDevices = true;
                return;
            }
            new RegistryDbManagerWithProvider().updateDeviceLastLaunchRegistryData(this.mUnCheckedDeviceList.get(0).deviceBtID, getActivity());
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getCheckedDeviceList() {
        this.mCheckedDeviceList = new ArrayList();
        this.mUnCheckedDeviceList = new ArrayList();
        this.mRemoveDeviceList = new ArrayList<>();
        this.mUninstallPackageList = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean[] selectedList = this.mManageDevicesItemAdapter.getSelectedList();
        for (int i = 0; i < selectedList.length; i++) {
            DeviceRegistryData deviceRegistryData = (DeviceRegistryData) this.mManageDevicesItemAdapter.getItem(i);
            if (selectedList[i]) {
                this.mCheckedDeviceList.add(deviceRegistryData);
                this.mRemoveDeviceList.add(deviceRegistryData.deviceBtID);
                if (!arrayList.contains(deviceRegistryData.packagename)) {
                    arrayList.add(deviceRegistryData.packagename);
                }
            } else {
                this.mUnCheckedDeviceList.add(deviceRegistryData);
                if (!arrayList2.contains(deviceRegistryData.packagename)) {
                    arrayList2.add(deviceRegistryData.packagename);
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!arrayList2.contains(str)) {
                this.mUninstallPackageList.add(str);
            }
        }
        String str2 = TAG;
        Log.d(str2, "getCheckedDeviceList()::mCheckedDeviceList" + this.mCheckedDeviceList);
        String str3 = TAG;
        Log.d(str3, "getCheckedDeviceList()::mUninstallPackageList" + this.mUninstallPackageList);
        return this.mCheckedDeviceList.size();
    }

    private List<DeviceRegistryData> getDBList() {
        List<DeviceRegistryData> queryLastLaunchDeviceRegistryData = new RegistryDbManagerWithProvider().queryLastLaunchDeviceRegistryData(this.mActivity);
        List<DeviceRegistryData> queryAllDeviceRegistryData = new RegistryDbManagerWithProvider().queryAllDeviceRegistryData(this.mActivity);
        if (queryLastLaunchDeviceRegistryData.size() == 0) {
            return queryAllDeviceRegistryData;
        }
        String str = queryLastLaunchDeviceRegistryData.get(0).deviceBtID;
        for (DeviceRegistryData deviceRegistryData : queryAllDeviceRegistryData) {
            if (str == null || !str.equals(deviceRegistryData.deviceBtID)) {
                queryLastLaunchDeviceRegistryData.add(deviceRegistryData);
            }
        }
        return queryLastLaunchDeviceRegistryData;
    }

    private boolean removeBond(BluetoothDevice bluetoothDevice) {
        try {
            Log.d(TAG, "Remove Bond...");
            Boolean valueOf = Boolean.valueOf(BluetoothDeviceFactory.get().removeBond(bluetoothDevice));
            if (valueOf != null) {
                return valueOf.booleanValue();
            }
            Log.w(TAG, "removeBond() returns null!!");
            return false;
        } catch (Exception e) {
            Log.w(TAG, e.getMessage());
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void removeBondDevice() {
        List<DeviceRegistryData> list = this.mCheckedDeviceList;
        if (list != null) {
            for (DeviceRegistryData deviceRegistryData : list) {
                removeBond(HostManagerUtilsRulesBTDevices.getBluetoothDevice(deviceRegistryData.deviceBtID));
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void removeDevicesFromDB(Context context) {
        Iterator<String> it = this.mRemoveDeviceList.iterator();
        while (it.hasNext()) {
            new RegistryDbManagerWithProvider().deleteDeviceRegistryDataDeviceID(it.next(), context);
        }
        checkLastLaunchedDevice();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void removeDevicesPlugin() {
        ArrayList<String> arrayList = this.mUninstallPackageList;
        if (arrayList == null || arrayList.size() == 0) {
            Log.d(TAG, "There is no list to remove.");
            onBackPressed();
            return;
        }
        showProgressDialog();
        new UninstallManager(this.mUninstallPackageList, this.uninstallListener).start();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showDeletePluginDialog(int i) {
        List<packageDelete> list;
        this.mDeleteConfirmdialog = new Dialog(getActivity());
        this.mDeleteConfirmdialog.requestWindowFeature(1);
        this.mDeleteConfirmdialog.setContentView(R.layout.delete_plugin_packages_dialog);
        this.mDeleteConfirmdialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mDeleteConfirmdialog.setCancelable(false);
        ((TextView) this.mDeleteConfirmdialog.findViewById(R.id.textview1)).setText(getResources().getQuantityString(R.plurals.manage_devices_delete_popup_main_text, i, Integer.valueOf(i)));
        getDeletePackageList();
        if (HostManagerUtils.isSamsungDevice() && (list = this.dPackageList) != null && list.size() > 0) {
            this.mDeleteConfirmdialog.findViewById(R.id.uninstall_layout).setVisibility(0);
            DeletpackageDialogAdapter deletpackageDialogAdapter = new DeletpackageDialogAdapter(getActivity(), this.dPackageList);
            ListView listView = (ListView) this.mDeleteConfirmdialog.findViewById(R.id.listview1);
            listView.setDivider(null);
            listView.setAdapter((ListAdapter) deletpackageDialogAdapter);
        }
        TextView textView = (TextView) this.mDeleteConfirmdialog.findViewById(R.id.btn_ok);
        textView.setText(R.string.manage_devices_delete_btn_text);
        textView.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.ManageDevicesFragment.AnonymousClass4 */

            public void onClick(View view) {
                ManageDevicesFragment.this.mDeleteConfirmdialog.dismiss();
                ManageDevicesFragment manageDevicesFragment = ManageDevicesFragment.this;
                manageDevicesFragment.removeDevicesFromDB(manageDevicesFragment.getActivity());
                if (HostManagerUtils.isSamsungDevice()) {
                    ManageDevicesFragment.this.removeBondDevice();
                    ManageDevicesFragment.this.removeDevicesPlugin();
                    return;
                }
                ManageDevicesFragment.this.onBackPressed();
            }
        });
        ((TextView) this.mDeleteConfirmdialog.findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.ManageDevicesFragment.AnonymousClass5 */

            public void onClick(View view) {
                ManageDevicesFragment.this.mDeleteConfirmdialog.dismiss();
            }
        });
        this.mDeleteConfirmdialog.show();
    }

    private void showProgressDialog() {
        this.mProgressDialog = new CommonDialog(getActivity(), 0, 4, 0);
        this.mProgressDialog.createDialog();
        this.mProgressDialog.setMessage(getActivity().getString(R.string.manage_devices_deleteing));
    }

    public void getDeletePackageList() {
        this.dPackageList = new ArrayList();
        Iterator<String> it = this.mUninstallPackageList.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (next != null && !next.equals("")) {
                String str = TAG;
                Log.d(str, "Delete pacakge name : " + next);
                this.dPackageList.add(new packageDelete(getPackageAppName(next), getPackageImage(next)));
            }
        }
    }

    public String getPackageAppName(String str) {
        ApplicationInfo applicationInfo;
        PackageManager packageManager = getActivity().getApplicationContext().getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            String str2 = TAG;
            android.util.Log.d(str2, "Exception Occuered When analize package = " + e);
            applicationInfo = null;
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "");
    }

    public Drawable getPackageImage(String str) {
        try {
            return getActivity().getPackageManager().getApplicationIcon(str);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.OnBackKeyListener
    public boolean onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        Activity activity = getActivity();
        if (this.mIsRemovedAllDevices) {
            Bundle bundle = new Bundle();
            bundle.putInt(SetupWizardWelcomeActivity.EXTRA_CONNECT_CASE, 1);
            ((SetupWizardWelcomeActivity) this.mActivity).updateFragment(9, bundle);
            return true;
        }
        if (activity != null && (activity instanceof SetupWizardWelcomeActivity)) {
            boolean startLastLaunchedPlugin = ((SetupWizardWelcomeActivity) activity).startLastLaunchedPlugin(false, null);
            String str = TAG;
            Log.d(str, "isPluginStarted" + startLastLaunchedPlugin + " mActivity:" + activity);
            if (activity != null) {
                activity.overridePendingTransition(R.anim.activity_left_to_right_in, R.anim.activity_left_to_right_out);
                activity.finish();
            }
        }
        return true;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d(TAG, "onConfigurationChanged");
        UIUtils.setProperWidth(getActivity(), this.mDevicesListview);
        UIUtils.setProperWidth(getActivity(), this.mainText1);
        UIUtils.setProperWidth(getActivity(), this.mainText2);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mActivity = getActivity();
        String str = TAG;
        Log.d(str, "OnCreate ends getActivity:" + getActivity());
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "inside onCreateView()");
        View inflate = layoutInflater.inflate(R.layout.layout_manage_devices, viewGroup, false);
        UIUtils.adjustLogoMargin(inflate.findViewById(R.id.actionbar_container));
        this.mDevicesListview = (ListView) inflate.findViewById(R.id.device_item_listview);
        this.mDeleteButton = (Button) inflate.findViewById(R.id.delete_device_button);
        this.mAllCheckBox = (CheckBox) inflate.findViewById(R.id.all_checkbox);
        this.mTitleTextView = (TextView) inflate.findViewById(R.id.manage_device_title);
        this.mainText1 = (TextView) inflate.findViewById(R.id.main_text1);
        this.mainText2 = (TextView) inflate.findViewById(R.id.main_text2);
        this.mManageDevicesItemAdapter = new ManageDevicesItemAdapter(getActivity(), getDBList());
        this.mManageDevicesItemAdapter.setDeleteButton(this.mDeleteButton);
        this.mManageDevicesItemAdapter.setTitleTextView(this.mTitleTextView);
        this.mManageDevicesItemAdapter.setSelectAllCheckbox(this.mAllCheckBox);
        this.mDevicesListview.setAdapter((ListAdapter) this.mManageDevicesItemAdapter);
        UIUtils.setProperWidth(this.mActivity, this.mDevicesListview);
        UIUtils.setProperWidth(this.mActivity, this.mainText1);
        UIUtils.setProperWidth(this.mActivity, this.mainText2);
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, bundle);
        this.mAllCheckBox.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.ManageDevicesFragment.AnonymousClass2 */

            public void onClick(View view) {
                ManageDevicesFragment.this.mManageDevicesItemAdapter.setAllChecked(ManageDevicesFragment.this.mAllCheckBox.isChecked());
                ManageDevicesFragment.this.mManageDevicesItemAdapter.notifyDataSetChanged();
            }
        });
        this.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.ManageDevicesFragment.AnonymousClass3 */

            public void onClick(View view) {
                ManageDevicesFragment.this.showDeletePluginDialog(ManageDevicesFragment.this.getCheckedDeviceList());
            }
        });
    }
}
