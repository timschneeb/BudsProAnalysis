package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.google.android.material.badge.BadgeDrawable;
import com.samsung.accessory.atticmgr.R;
import seccompat.android.util.Log;

public class DropdownListPopup {
    private static final String TAG = "Attic_DropdownListPopup";
    private View mAnchor;
    private Context mContext;
    private boolean mIsShowing;
    private ListView mListView;
    private OnItemClickListener mOnItemClickListener;
    private PopupWindow mPopupWindow;
    private View mView;

    public interface OnItemClickListener {
        void onItemClick(DropdownListPopup dropdownListPopup, View view, int i, long j);
    }

    private DropdownListPopup(Context context, View view) {
        this.mContext = context;
        this.mAnchor = view;
    }

    public DropdownListPopup(Context context, View view, String[] strArr, Integer num) {
        this(context, view);
        init(new DropdownListPopupListAdapter(context, strArr, num));
    }

    public DropdownListPopup(Context context, View view, int[] iArr, Integer num) {
        this(context, view);
        init(new DropdownListPopupListAdapter(context, iArr, num));
    }

    private void init(DropdownListPopupListAdapter dropdownListPopupListAdapter) {
        this.mView = LayoutInflater.from(this.mContext).inflate(R.layout.popup_dropdown_list, (ViewGroup) null);
        this.mListView = (ListView) this.mView.findViewById(R.id.list_view);
        this.mListView.setAdapter((ListAdapter) dropdownListPopupListAdapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* class com.samsung.accessory.hearablemgr.common.ui.DropdownListPopup.AnonymousClass1 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (DropdownListPopup.this.mOnItemClickListener != null) {
                    DropdownListPopup.this.mOnItemClickListener.onItemClick(DropdownListPopup.this, view, i, j);
                }
            }
        });
        setListViewFixedSize(this.mListView);
        this.mPopupWindow = new PopupWindow(this.mView, -2, -2);
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.setFocusable(true);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    private int getPopupWindowHeight() {
        this.mPopupWindow.getContentView().measure(View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels, Integer.MIN_VALUE), 0);
        return this.mPopupWindow.getContentView().getMeasuredHeight();
    }

    public void show() {
        this.mIsShowing = true;
        int dimension = (int) this.mContext.getResources().getDimension(R.dimen.popupmenu_margin_start);
        int[] iArr = new int[2];
        int i = this.mContext.getResources().getDisplayMetrics().heightPixels;
        this.mAnchor.measure(0, 0);
        this.mAnchor.getLocationInWindow(iArr);
        int i2 = iArr[1];
        int measuredHeight = this.mAnchor.getMeasuredHeight();
        int popupWindowHeight = getPopupWindowHeight();
        int i3 = i - (i2 + popupWindowHeight);
        if (i3 < 0) {
            this.mPopupWindow.showAsDropDown(this.mAnchor, dimension, -popupWindowHeight, BadgeDrawable.BOTTOM_START);
        } else {
            this.mPopupWindow.showAsDropDown(this.mAnchor, dimension, -measuredHeight, BadgeDrawable.TOP_START);
        }
        Log.d(TAG, "show() : anchorHeight=" + measuredHeight + ", popupHeight=" + popupWindowHeight + ", heightSpace=" + i3 + ", anchorLocation[1]=" + iArr[1]);
    }

    public void dismiss() {
        this.mIsShowing = false;
        this.mPopupWindow.dismiss();
    }

    public PopupWindow getPopupWindow() {
        return this.mPopupWindow;
    }

    public ListView getListView() {
        return this.mListView;
    }

    public void setCheckedItem(int i) {
        ((DropdownListPopupListAdapter) this.mListView.getAdapter()).setCheckedItem(i);
    }

    private static void setListViewFixedSize(ListView listView) {
        if (listView.getAdapter() != null) {
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < listView.getAdapter().getCount(); i3++) {
                View view = listView.getAdapter().getView(i3, null, listView);
                view.measure(0, 0);
                if (view.getMeasuredWidth() > i) {
                    i = view.getMeasuredWidth();
                }
                i2 += view.getMeasuredHeight();
            }
            Log.d(TAG, "setListViewFixedSize() : width=" + i + ", height=" + i2);
            listView.getLayoutParams().width = i;
            listView.getLayoutParams().height = i2;
            listView.requestLayout();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public static class DropdownListPopupListAdapter extends BaseAdapter {
        int mCheckedPosition;
        Context mContext;
        String[] mItem;
        int[] mItemStringId;

        DropdownListPopupListAdapter(Context context, String[] strArr, Integer num) {
            this.mContext = context;
            this.mItem = strArr;
            this.mCheckedPosition = num != null ? num.intValue() : -1;
        }

        DropdownListPopupListAdapter(Context context, int[] iArr, Integer num) {
            this.mContext = context;
            this.mItemStringId = iArr;
            this.mCheckedPosition = num != null ? num.intValue() : -1;
        }

        public int getCount() {
            String[] strArr = this.mItem;
            if (strArr != null) {
                return strArr.length;
            }
            int[] iArr = this.mItemStringId;
            if (iArr != null) {
                return iArr.length;
            }
            return 0;
        }

        public String getItem(int i) {
            String[] strArr = this.mItem;
            if (strArr != null) {
                return strArr[i];
            }
            int[] iArr = this.mItemStringId;
            if (iArr != null) {
                return this.mContext.getString(iArr[i]);
            }
            return null;
        }

        public long getItemId(int i) {
            int[] iArr = this.mItemStringId;
            return iArr != null ? (long) iArr[i] : (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.popup_dropdown_list_item, (ViewGroup) null);
            }
            TextView textView = (TextView) view.findViewById(R.id.text_view);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
            String item = getItem(i);
            textView.setText(item);
            if (i == this.mCheckedPosition) {
                textView.setTextColor(viewGroup.getResources().getColor(R.color.colorPrimary));
                textView.setTypeface(textView.getTypeface(), 1);
                textView.setContentDescription(this.mContext.getResources().getString(R.string.selected) + " " + item);
                imageView.setVisibility(0);
            } else {
                textView.setTextColor(viewGroup.getResources().getColor(R.color.title_text_normal_color));
                textView.setTypeface(textView.getTypeface(), 0);
                textView.setContentDescription(this.mContext.getResources().getString(R.string.not_selected) + " " + item);
                imageView.setVisibility(4);
            }
            return view;
        }

        public void setCheckedItem(int i) {
            this.mCheckedPosition = i;
            notifyDataSetChanged();
        }
    }
}
