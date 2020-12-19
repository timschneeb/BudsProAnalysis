package com.samsung.android.app.watchmanager.setupwizard;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.smartswitch.SmartSwitchConstants;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import java.util.List;

public class SetupWizardDeviceListAdapter extends ArrayAdapter<BluetoothDiscoveryUtility.BluetoothDeviceItem> {
    private List<BluetoothDiscoveryUtility.BluetoothDeviceItem> bluetoothDeviceItems;
    private Context context;

    private static class Holder {
        TextView tv;

        private Holder() {
        }
    }

    public SetupWizardDeviceListAdapter(Context context2) {
        super(context2, -1);
        this.context = context2;
    }

    public SetupWizardDeviceListAdapter(Context context2, List<BluetoothDiscoveryUtility.BluetoothDeviceItem> list) {
        super(context2, -1, list);
        this.bluetoothDeviceItems = list;
        this.context = context2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.layout_welcome_device_name, viewGroup, false);
            Holder holder = new Holder();
            holder.tv = (TextView) view.findViewById(R.id.device_item_text);
            view.setTag(holder);
            view.setBackgroundResource(R.drawable.setupwizard_listitem_effect);
            TransitionDrawable transitionDrawable = (TransitionDrawable) view.getBackground();
            transitionDrawable.setCrossFadeEnabled(true);
            transitionDrawable.startTransition(SmartSwitchConstants.SLEEP);
        }
        Holder holder2 = (Holder) view.getTag();
        holder2.tv.setText(((BluetoothDiscoveryUtility.BluetoothDeviceItem) getItem(i)).toString());
        if (this.context.getResources().getText(R.string.none_paired).toString().equals(((BluetoothDiscoveryUtility.BluetoothDeviceItem) getItem(i)).toString())) {
            holder2.tv.setVisibility(8);
        }
        return view;
    }
}
