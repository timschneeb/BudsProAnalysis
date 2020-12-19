package com.samsung.accessory.hearablemgr.module.home.drawer;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.uhm.DeviceRegistryData;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import seccompat.android.util.Log;

public class DrawerListAdapter extends BaseAdapter {
    private static final int COLOR_ICON_DEFAULT = Application.getContext().getResources().getColor(R.color.drawer_icon_default);
    private static final int COLOR_PRIMARY = Application.getContext().getResources().getColor(R.color.colorPrimary);
    private static final int KEY_ASYNC_TASK_INSTANCE = "KEY_ASYNC_TASK_INSTANCE".hashCode();
    private static final String TAG = "Attic_DrawerListAdapter";
    private static Executor THREAD_POOL = Executors.newFixedThreadPool(4);
    List<DeviceRegistryData> mData = new LinkedList();
    AdapterView.OnItemClickListener mOnItemClickListener;

    public long getItemId(int i) {
        return (long) i;
    }

    /* access modifiers changed from: package-private */
    public void setData(List<DeviceRegistryData> list) {
        LinkedList linkedList = new LinkedList();
        for (DeviceRegistryData deviceRegistryData : list) {
            if (Util.equalsIgnoreCase(deviceRegistryData.deviceId, UhmFwUtil.getLastLaunchDeviceId())) {
                linkedList.addFirst(deviceRegistryData);
            } else {
                linkedList.add(deviceRegistryData);
            }
        }
        this.mData = linkedList;
    }

    /* access modifiers changed from: package-private */
    public void setOnDeviceItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public int getCount() {
        return this.mData.size();
    }

    public DeviceRegistryData getItem(int i) {
        return this.mData.get(i);
    }

    public View getView(final int i, final View view, final ViewGroup viewGroup) {
        Log.d(TAG, "getView() : " + i);
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_drawer_list_item, viewGroup, false);
        }
        view.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.drawer.DrawerListAdapter.AnonymousClass1 */

            public void onClick(View view) {
                if (DrawerListAdapter.this.mOnItemClickListener != null) {
                    View view2 = view;
                    int i = i;
                    DrawerListAdapter.this.mOnItemClickListener.onItemClick((AdapterView) viewGroup, view2, i, DrawerListAdapter.this.getItemId(i));
                }
            }
        });
        ImageView imageView = (ImageView) view.findViewById(R.id.image_icon);
        final TextView textView = (TextView) view.findViewById(R.id.text_title);
        TextView textView2 = (TextView) view.findViewById(R.id.text_description);
        textView.setTextColor(viewGroup.getContext().getResources().getColor(R.color.list_item_normal_color));
        textView.setTypeface(textView.getTypeface(), 0);
        imageView.setColorFilter(COLOR_ICON_DEFAULT);
        final DeviceRegistryData item = getItem(i);
        imageView.setImageResource(getDeviceDrawerIcon(item.deviceFixedName));
        textView2.setVisibility(item.connected.intValue() == 2 ? 0 : 8);
        if (Util.equalsIgnoreCase(item.deviceId, UhmFwUtil.getLastLaunchDeviceId())) {
            textView.setTextColor(COLOR_PRIMARY);
            textView.setTypeface(textView.getTypeface(), 1);
            imageView.setColorFilter(COLOR_PRIMARY);
        }
        new AsyncTask<Void, Void, String>() {
            /* class com.samsung.accessory.hearablemgr.module.home.drawer.DrawerListAdapter.AnonymousClass1AsyncTaskGetView */

            /* access modifiers changed from: protected */
            public void onPreExecute() {
                Log.d(DrawerListAdapter.TAG, "AsyncTaskGetView::onPreExecute() : " + i);
                view.setTag(DrawerListAdapter.KEY_ASYNC_TASK_INSTANCE, this);
            }

            /* access modifiers changed from: protected */
            public String doInBackground(Void... voidArr) {
                String str;
                Log.d(DrawerListAdapter.TAG, "AsyncTaskGetView::doInBackground() : " + i);
                String deviceName = BluetoothUtil.getDeviceName(item.deviceId);
                String alias = BluetoothUtil.getAlias(item.deviceId);
                if (alias != null && !alias.equals(deviceName)) {
                    str = alias;
                } else if (DrawerListAdapter.this.isThereSameDevice(item.deviceFixedName)) {
                    str = item.deviceName;
                } else {
                    str = UhmFwUtil.getSimpleBTName(item.deviceFixedName);
                }
                Log.d(DrawerListAdapter.TAG, "AsyncTaskGetView::doInBackground() : " + i + " - result=" + str + "(originalName=" + deviceName + ", aliasName=" + alias + ", deviceFixedName=" + item.deviceFixedName + ", deviceName=" + item.deviceName + ")");
                return str;
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(String str) {
                if (view.getTag(DrawerListAdapter.KEY_ASYNC_TASK_INSTANCE) == this) {
                    textView.setText(str);
                    Log.d(DrawerListAdapter.TAG, "AsyncTaskGetView::onPostExecute() : " + i + " - done");
                    return;
                }
                Log.w(DrawerListAdapter.TAG, "AsyncTaskGetView::onPostExecute() : " + i + " - skipped");
            }
        }.executeOnExecutor(THREAD_POOL, new Void[0]);
        Log.d(TAG, "getView()_end : " + i);
        return view;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isThereSameDevice(String str) {
        Iterator<DeviceRegistryData> it = this.mData.iterator();
        boolean z = false;
        int i = 0;
        while (true) {
            if (it.hasNext()) {
                if (str.equals(it.next().deviceFixedName) && (i = i + 1) >= 2) {
                    z = true;
                    break;
                }
            } else {
                break;
            }
        }
        Log.d(TAG, "isThereSameDevice(" + str + ") : " + z);
        return z;
    }

    private static int getDeviceDrawerIcon(String str) {
        if (TextUtils.isEmpty(str)) {
            return R.drawable.drawer_ic_add;
        }
        if (str.contains("Fit")) {
            return R.drawable.drawer_ic_band;
        }
        if (str.contains("Buds Pro")) {
            return R.drawable.drawer_ic_attic;
        }
        if (str.contains("Buds Live")) {
            return R.drawable.drawer_ic_bean;
        }
        return (str.contains("IconX") || str.contains("Buds") || str.contains("Circle")) ? R.drawable.drawer_ic_buds : R.drawable.drawer_ic_watch;
    }
}
