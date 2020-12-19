package com.samsung.android.app.twatchmanager.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.samsung.android.app.watchmanager.R;
import java.util.ArrayList;

public class PermissionListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<PermissionItem> mList;

    private static class ViewHolder {
        ImageView image;
        TextView text;

        private ViewHolder() {
        }
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

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater from = LayoutInflater.from(this.mContext);
            viewHolder = new ViewHolder();
            view = from.inflate(R.layout.template_item_list_permission, (ViewGroup) null);
            viewHolder.image = (ImageView) view.findViewById(R.id.permission_image);
            viewHolder.text = (TextView) view.findViewById(R.id.permission_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.image.setImageDrawable(this.mList.get(i).image);
        viewHolder.text.setText(this.mList.get(i).text);
        return view;
    }
}
