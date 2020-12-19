package com.samsung.android.app.watchmanager.setupwizard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.util.ResourceLoader;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import java.util.List;

public class AvailableDeviceFromPluginAdapter extends ArrayAdapter<BluetoothDiscoveryUtility.BluetoothDeviceItem> {
    private static final String TAG = "AvailableDeviceFromPluginAdapter";
    private List<BluetoothDiscoveryUtility.BluetoothDeviceItem> bluetoothDeviceItems;
    private Context context;
    private GearRulesManager rulesManager = GearRulesManager.getInstance();

    private static class Holder {
        ImageView icon;
        TextView textView;

        private Holder() {
        }
    }

    public AvailableDeviceFromPluginAdapter(Context context2) {
        super(context2, -1);
        this.context = context2;
    }

    public AvailableDeviceFromPluginAdapter(Context context2, List<BluetoothDiscoveryUtility.BluetoothDeviceItem> list) {
        super(context2, -1, list);
        this.bluetoothDeviceItems = list;
        this.context = context2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.list_device_item_icon_subtext, viewGroup, false);
            Holder holder = new Holder();
            holder.textView = (TextView) view.findViewById(R.id.device_item_text);
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            view.findViewById(R.id.device_status_text).setVisibility(8);
            view.setTag(holder);
        }
        Holder holder2 = (Holder) view.getTag();
        BluetoothDiscoveryUtility.BluetoothDeviceItem bluetoothDeviceItem = (BluetoothDiscoveryUtility.BluetoothDeviceItem) getItem(i);
        holder2.textView.setText(bluetoothDeviceItem.toString());
        String icon = this.rulesManager.getIcon(bluetoothDeviceItem.getFixedName());
        if (icon != null) {
            holder2.icon.setImageResource(ResourceLoader.getDrawableId(this.context, icon));
        }
        return view;
    }
}
