package com.samsung.accessory.hearablemgr.common.permission;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.samsung.accessory.atticmgr.R;
import java.util.ArrayList;

public class PermissionListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<PermissionItem> mList;

    public long getItemId(int i) {
        return (long) i;
    }

    public PermissionListAdapter(Context context, ArrayList<PermissionItem> arrayList) {
        this.mContext = context;
        this.mList = arrayList;
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int i) {
        ArrayList<PermissionItem> arrayList = this.mList;
        if (arrayList != null) {
            return arrayList.get(i);
        }
        return null;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater from = LayoutInflater.from(this.mContext);
            viewHolder = new ViewHolder();
            view = from.inflate(R.layout.item_permission_list, (ViewGroup) null);
            viewHolder.image = (ImageView) view.findViewById(R.id.permission_image);
            viewHolder.text = (TextView) view.findViewById(R.id.permission_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (this.mList.get(i).image != null) {
            viewHolder.image.setImageDrawable(this.mList.get(i).image);
        } else {
            viewHolder.image.setVisibility(4);
        }
        viewHolder.text.setText(this.mList.get(i).text);
        return view;
    }

    private static class ViewHolder {
        ImageView image;
        TextView text;

        private ViewHolder() {
        }
    }
}
