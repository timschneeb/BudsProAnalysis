package com.samsung.android.app.watchmanager;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.contentprovider.DeviceRegistryData;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.util.ResourceLoader;
import java.util.List;

public class ManageDevicesItemAdapter extends BaseAdapter {
    private static final String TAG = ("tUHM:" + ManageDevicesItemAdapter.class.getSimpleName());
    private static LayoutInflater layoutInflater;
    Context mContext;
    private Button mDeleteButton;
    List<DeviceRegistryData> mDeviceItems;
    private GearRulesManager mGearRulesManager = GearRulesManager.getInstance();
    private CheckBox mSelectAllCheckbox;
    private TextView mTitleTextView;
    public boolean[] selectedList = new boolean[this.mDeviceItems.size()];

    public static class DeviceItemHolder {
        TextView connectionStatus;
        TextView deviceName;
        View divider;
        ImageView itemIcon;
        RelativeLayout layout;
        CheckBox selectCheckBox;
    }

    public ManageDevicesItemAdapter(Context context, List<DeviceRegistryData> list) {
        this.mDeviceItems = list;
        this.mContext = context;
        layoutInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        this.mGearRulesManager.syncGearInfoSynchronously();
        String str = TAG;
        Log.d(str, "ManageDevicesItemAdapter()mDeviceItems.size() = " + this.mDeviceItems.size());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void checkDeleteButtonEnable() {
        float f;
        Button button;
        if (this.mDeleteButton != null) {
            int i = 0;
            boolean z = false;
            boolean z2 = true;
            while (true) {
                boolean[] zArr = this.selectedList;
                if (i >= zArr.length) {
                    break;
                }
                if (zArr[i]) {
                    z = true;
                } else {
                    z2 = false;
                }
                i++;
            }
            this.mDeleteButton.setEnabled(z);
            if (z) {
                button = this.mDeleteButton;
                f = 1.0f;
            } else {
                button = this.mDeleteButton;
                f = 0.4f;
            }
            button.setAlpha(f);
            CheckBox checkBox = this.mSelectAllCheckbox;
            if (checkBox != null) {
                checkBox.setChecked(z2);
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void checkSelectedItems() {
        int i = 0;
        int i2 = 0;
        while (true) {
            boolean[] zArr = this.selectedList;
            if (i >= zArr.length) {
                break;
            }
            if (zArr[i]) {
                i2++;
            }
            i++;
        }
        if (i2 == 0) {
            this.mTitleTextView.setText(this.mContext.getResources().getString(R.string.manage_devices_title_text));
            return;
        }
        this.mTitleTextView.setText(String.format(this.mContext.getResources().getString(R.string.manage_devices_title_text_select), Integer.valueOf(i2)));
    }

    private int getDeviceItemIcon(String str) {
        String icon = this.mGearRulesManager.getIcon(str);
        if (icon != null) {
            return ResourceLoader.getDrawableId(this.mContext, icon);
        }
        return -1;
    }

    public int getCount() {
        return this.mDeviceItems.size();
    }

    public Object getItem(int i) {
        return this.mDeviceItems.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public boolean[] getSelectedList() {
        return this.selectedList;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        final DeviceItemHolder deviceItemHolder;
        RelativeLayout relativeLayout;
        Resources resources;
        int i2;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_manage_devices_list, (ViewGroup) null);
            deviceItemHolder = new DeviceItemHolder();
            deviceItemHolder.layout = (RelativeLayout) view.findViewById(R.id.item_layout);
            deviceItemHolder.deviceName = (TextView) view.findViewById(R.id.device_item_name);
            deviceItemHolder.connectionStatus = (TextView) view.findViewById(R.id.device_connection_status);
            deviceItemHolder.selectCheckBox = (CheckBox) view.findViewById(R.id.item_checkbox);
            deviceItemHolder.itemIcon = (ImageView) view.findViewById(R.id.item_image);
            deviceItemHolder.divider = view.findViewById(R.id.divider);
            view.setTag(deviceItemHolder);
        } else {
            deviceItemHolder = (DeviceItemHolder) view.getTag();
        }
        DeviceRegistryData deviceRegistryData = this.mDeviceItems.get(i);
        deviceItemHolder.deviceName.setText(deviceRegistryData.deviceName);
        if (deviceRegistryData.isConnected == 2) {
            deviceItemHolder.connectionStatus.setVisibility(0);
            deviceItemHolder.connectionStatus.setText(this.mContext.getResources().getString(R.string.connected));
        } else {
            deviceItemHolder.connectionStatus.setVisibility(8);
        }
        deviceItemHolder.selectCheckBox.setChecked(this.selectedList[i]);
        deviceItemHolder.layout.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.ManageDevicesItemAdapter.AnonymousClass1 */

            public void onClick(View view) {
                ManageDevicesItemAdapter.this.selectedList[i] = !deviceItemHolder.selectCheckBox.isChecked();
                deviceItemHolder.selectCheckBox.setChecked(ManageDevicesItemAdapter.this.selectedList[i]);
                ManageDevicesItemAdapter.this.checkDeleteButtonEnable();
                ManageDevicesItemAdapter.this.checkSelectedItems();
            }
        });
        deviceItemHolder.itemIcon.setImageResource(getDeviceItemIcon(deviceRegistryData.deviceFixedName));
        if (getCount() - 1 > i) {
            deviceItemHolder.divider.setVisibility(0);
        }
        if (this.mDeviceItems.size() == 1) {
            relativeLayout = deviceItemHolder.layout;
            resources = this.mContext.getResources();
            i2 = R.drawable.item_background_show_shape_grey;
        } else if (i == 0) {
            relativeLayout = deviceItemHolder.layout;
            resources = this.mContext.getResources();
            i2 = R.drawable.top_round_corner_ripple_effect;
        } else if (i == this.mDeviceItems.size() - 1) {
            relativeLayout = deviceItemHolder.layout;
            resources = this.mContext.getResources();
            i2 = R.drawable.bottom_rounded_corner_ripple_effect;
        } else {
            relativeLayout = deviceItemHolder.layout;
            resources = this.mContext.getResources();
            i2 = R.drawable.list_ripple_effect;
        }
        relativeLayout.setBackground(resources.getDrawable(i2));
        return view;
    }

    public void setAllChecked(boolean z) {
        int length = this.selectedList.length;
        for (int i = 0; i < length; i++) {
            this.selectedList[i] = z;
        }
        checkDeleteButtonEnable();
        checkSelectedItems();
    }

    public void setDeleteButton(Button button) {
        this.mDeleteButton = button;
        this.mDeleteButton.setEnabled(false);
        this.mDeleteButton.setAlpha(0.4f);
    }

    public void setSelectAllCheckbox(CheckBox checkBox) {
        this.mSelectAllCheckbox = checkBox;
    }

    public void setTitleTextView(TextView textView) {
        this.mTitleTextView = textView;
    }
}
