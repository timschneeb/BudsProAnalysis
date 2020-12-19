package com.samsung.android.app.watchmanager.setupwizard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.android.app.watchmanager.R;
import java.util.List;

public class PermissionGuideAdapter extends RecyclerView.a<MyViewHolder> {
    private List<PermissionGuideItem> permissionsList;

    public class MyViewHolder extends RecyclerView.v {
        private ImageView grid_image;
        private TextView mainText;
        private TextView subtext;

        public MyViewHolder(View view) {
            super(view);
            this.grid_image = (ImageView) view.findViewById(R.id.grid_image);
            this.mainText = (TextView) view.findViewById(R.id.mainText);
            this.subtext = (TextView) view.findViewById(R.id.subtext);
        }

        public void display(PermissionGuideItem permissionGuideItem) {
            this.grid_image.setImageDrawable(permissionGuideItem.image);
            this.mainText.setText(permissionGuideItem.title);
            this.subtext.setText(permissionGuideItem.description);
        }
    }

    public PermissionGuideAdapter(List<PermissionGuideItem> list) {
        this.permissionsList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.a
    public int getItemCount() {
        return this.permissionsList.size();
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.display(this.permissionsList.get(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.a
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.permission_guide_item_layout, viewGroup, false));
    }
}
