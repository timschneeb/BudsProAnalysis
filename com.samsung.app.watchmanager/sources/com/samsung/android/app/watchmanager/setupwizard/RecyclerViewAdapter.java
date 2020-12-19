package com.samsung.android.app.watchmanager.setupwizard;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.GroupInfo;
import com.samsung.android.app.twatchmanager.util.UIUtils;
import com.samsung.android.app.watchmanager.R;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.a<RecyclerView.v> {
    private static final int LEFT_IMAGE = 0;
    private static final int MINE_NOT_HERE_BUTTON_ITEM = 2;
    private static final int RIGHT_IMAGE = 1;
    private static final String TAG = "RecyclerViewAdapter";
    private Context context;
    private ArrayList<ImageItem> data;
    private boolean isMultiWindow = false;
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;

    public interface ItemClickListener {
        void onItemClick(View view, int i);
    }

    public class ViewHolder extends RecyclerView.v implements View.OnClickListener {
        private LinearLayout groupTextProgress;
        private ImageView image;
        private TextView imageTitle;
        private View marginView;
        private RelativeLayout outerRelativeParent;

        public ViewHolder(View view) {
            super(view);
            this.imageTitle = (TextView) view.findViewById(R.id.grid_text);
            this.image = (ImageView) view.findViewById(R.id.grid_image);
            this.outerRelativeParent = (RelativeLayout) view.findViewById(R.id.outer_relative_parent);
            this.groupTextProgress = (LinearLayout) view.findViewById(R.id.group_text_progress);
            if (UIUtils.isLandScapeMode(RecyclerViewAdapter.this.context)) {
                this.marginView = view.findViewById(R.id.tablet_space_view);
                setImageMargin();
            }
            view.setOnClickListener(this);
        }

        private void setImageMargin() {
            ViewGroup.LayoutParams layoutParams;
            double d2;
            double d3;
            View view = this.marginView;
            if (view != null && (layoutParams = view.getLayoutParams()) != null) {
                double convertDpToPx = (double) UIUtils.convertDpToPx(RecyclerViewAdapter.this.context, UIUtils.getWidth(RecyclerViewAdapter.this.context));
                Double.isNaN(convertDpToPx);
                int i = (int) (convertDpToPx * 0.75d);
                double displayRatio = (double) UIUtils.getDisplayRatio(RecyclerViewAdapter.this.context);
                if (displayRatio >= 1.6d) {
                    d2 = (double) i;
                    d3 = 0.16d;
                } else if (displayRatio > 1.3d) {
                    d2 = (double) i;
                    d3 = 0.1d;
                } else {
                    return;
                }
                Double.isNaN(d2);
                layoutParams.width = (int) (d2 * d3);
            }
        }

        public void onClick(View view) {
            if (RecyclerViewAdapter.this.mClickListener != null) {
                RecyclerViewAdapter.this.mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public class ViewHolderMineNotHere extends RecyclerView.v implements View.OnClickListener {
        private Button gearNotPresent;

        public ViewHolderMineNotHere(View view) {
            super(view);
            this.gearNotPresent = (Button) view.findViewById(R.id.gearNotHere);
            this.gearNotPresent.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (RecyclerViewAdapter.this.mClickListener != null) {
                RecyclerViewAdapter.this.mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public RecyclerViewAdapter(Context context2) {
        this.mInflater = LayoutInflater.from(context2);
        this.context = context2;
    }

    public ImageItem getItem(int i) {
        return this.data.get(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.a
    public int getItemCount() {
        ArrayList<ImageItem> arrayList = this.data;
        if (arrayList != null) {
            return arrayList.size() + 1;
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.a
    public int getItemViewType(int i) {
        if (i == this.data.size()) {
            return 2;
        }
        int i2 = i % 2;
        return (i2 == 0 || i2 != 1) ? 0 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.a
    public void onBindViewHolder(RecyclerView.v vVar, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType != 2) {
            ImageItem imageItem = this.data.get(i);
            String title = imageItem.getTitle();
            int lastIndexOf = title.lastIndexOf(System.getProperty("line.separator"));
            if (lastIndexOf != -1) {
                String str = TAG;
                Log.d(str, "index = " + lastIndexOf + " and group = " + title);
                SpannableString spannableString = new SpannableString(title);
                spannableString.setSpan(new TextAppearanceSpan(this.context, R.style.PickGearFragmentTitleStyle), lastIndexOf + 1, title.length(), 33);
                ((ViewHolder) vVar).imageTitle.setText(spannableString, TextView.BufferType.SPANNABLE);
            } else {
                ((ViewHolder) vVar).imageTitle.setText(title);
            }
            GroupInfo.ImageInfo imageInfo = imageItem.getImageInfo();
            if (imageInfo == null) {
                Log.d(TAG, "onBindViewHolder() image info is null.");
                return;
            }
            int drawableIdFromFileName = UIUtils.getDrawableIdFromFileName(this.context, imageInfo.name);
            if (drawableIdFromFileName > 0) {
                String str2 = TAG;
                Log.d(str2, "onBindViewHolder() resId:" + drawableIdFromFileName + " name : " + imageInfo.name);
                ((ViewHolder) vVar).image.setImageResource(drawableIdFromFileName);
            }
            if (!this.isMultiWindow) {
                return;
            }
            if (itemViewType == 0) {
                ViewHolder viewHolder = (ViewHolder) vVar;
                viewHolder.outerRelativeParent.setPadding(UIUtils.convertDpToPx(this.context, 0), UIUtils.convertDpToPx(this.context, 0), UIUtils.convertDpToPx(this.context, 0), UIUtils.convertDpToPx(this.context, 0));
                ((ViewGroup.MarginLayoutParams) viewHolder.groupTextProgress.getLayoutParams()).setMarginStart(UIUtils.convertDpToPx(this.context, -5));
            } else if (itemViewType == 1) {
                ViewHolder viewHolder2 = (ViewHolder) vVar;
                viewHolder2.outerRelativeParent.setPadding(UIUtils.convertDpToPx(this.context, 0), UIUtils.convertDpToPx(this.context, 0), UIUtils.convertDpToPx(this.context, 0), UIUtils.convertDpToPx(this.context, 0));
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewHolder2.groupTextProgress.getLayoutParams();
                marginLayoutParams.setMarginStart(UIUtils.convertDpToPx(this.context, 20));
                marginLayoutParams.setMarginEnd(UIUtils.convertDpToPx(this.context, -5));
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.a
    public RecyclerView.v onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.v viewHolderMineNotHere = i != 0 ? i != 1 ? i != 2 ? null : new ViewHolderMineNotHere(this.mInflater.inflate(R.layout.pick_gear_mines_not_here, viewGroup, false)) : new ViewHolder(this.mInflater.inflate(R.layout.right_image_item, viewGroup, false)) : new ViewHolder(this.mInflater.inflate(R.layout.left_image_item, viewGroup, false));
        if (viewHolderMineNotHere == null) {
            Log.d(TAG, "viewHolder is null");
        }
        return viewHolderMineNotHere;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void setData(ArrayList<ImageItem> arrayList) {
        this.data = arrayList;
    }

    /* access modifiers changed from: package-private */
    public void updateViewsForMultiWindowCase(boolean z) {
        this.isMultiWindow = z;
    }
}
