package com.samsung.accessory.hearablemgr.module.touchcontrols;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import java.util.ArrayList;
import seccompat.android.util.Log;

public class TouchAndHoldOptionAdapter extends RecyclerView.Adapter<TouchAndHoldViewHolder> {
    private static final String TAG = "Attic_TouchAndHoldOptionAdapter";
    private OnListItemSelectedInterface mListener;
    private ArrayList<TouchAndHoldOptionData> mOptionList;
    private int mSelectedItem = -1;
    private int mlastSelectedItem = -1;

    public interface OnListItemSelectedInterface {
        void onItemSelected(View view, int i, int i2);
    }

    public class TouchAndHoldViewHolder extends RecyclerView.ViewHolder {
        public View divider = this.itemView.findViewById(R.id.divider);
        public View listlayout = ((LinearLayout) this.itemView.findViewById(R.id.options_list));
        public TextView optionName = ((TextView) this.itemView.findViewById(R.id.option_textview));
        public RadioButton radioButton = ((RadioButton) this.itemView.findViewById(R.id.option_radio));
        public TextView subDesc = ((TextView) this.itemView.findViewById(R.id.option_sub_text));
        public TouchAndHoldViewHolder viewHolder = this;

        public TouchAndHoldViewHolder(View view) {
            super(view);
            this.listlayout.setOnClickListener(new View.OnClickListener(TouchAndHoldOptionAdapter.this) {
                /* class com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldOptionAdapter.TouchAndHoldViewHolder.AnonymousClass1 */

                public void onClick(View view) {
                    TouchAndHoldViewHolder.this.setRadio(view);
                    SamsungAnalyticsUtil.sendEvent(SA.Event.TAP_AND_HOLD_TOUCHPAD_LEFT);
                }
            });
            this.radioButton.setOnClickListener(new View.OnClickListener(TouchAndHoldOptionAdapter.this) {
                /* class com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldOptionAdapter.TouchAndHoldViewHolder.AnonymousClass2 */

                public void onClick(View view) {
                    TouchAndHoldViewHolder.this.setRadio(view);
                    SamsungAnalyticsUtil.sendEvent(SA.Event.TAP_AND_HOLD_TOUCHPAD_RIGHT);
                }
            });
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void setRadio(View view) {
            this.radioButton.setChecked(true);
            TouchAndHoldOptionAdapter.this.mSelectedItem = getAdapterPosition();
            ((TouchAndHoldOptionData) TouchAndHoldOptionAdapter.this.mOptionList.get(TouchAndHoldOptionAdapter.this.mlastSelectedItem)).setSelectedItem(false);
            ((TouchAndHoldOptionData) TouchAndHoldOptionAdapter.this.mOptionList.get(TouchAndHoldOptionAdapter.this.mSelectedItem)).setSelectedItem(true);
            TouchAndHoldOptionAdapter touchAndHoldOptionAdapter = TouchAndHoldOptionAdapter.this;
            touchAndHoldOptionAdapter.mlastSelectedItem = touchAndHoldOptionAdapter.mSelectedItem;
            TouchAndHoldOptionAdapter touchAndHoldOptionAdapter2 = TouchAndHoldOptionAdapter.this;
            touchAndHoldOptionAdapter2.notifyItemRangeChanged(0, touchAndHoldOptionAdapter2.getItemCount());
            TouchAndHoldOptionAdapter.this.mListener.onItemSelected(view, TouchAndHoldOptionAdapter.this.mSelectedItem, getItemViewType());
            Log.d(TouchAndHoldOptionAdapter.TAG, "setRadio - position = " + TouchAndHoldOptionAdapter.this.mSelectedItem);
        }
    }

    public TouchAndHoldOptionAdapter(ArrayList<TouchAndHoldOptionData> arrayList, OnListItemSelectedInterface onListItemSelectedInterface) {
        this.mOptionList = arrayList;
        this.mListener = onListItemSelectedInterface;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public TouchAndHoldViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new TouchAndHoldViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_touch_and_hold_list, viewGroup, false));
    }

    public void onBindViewHolder(TouchAndHoldViewHolder touchAndHoldViewHolder, int i) {
        String str;
        if (i == 0) {
            if (Application.getCoreService().getEarBudsInfo().noiseControlsAnc) {
                str = Application.getContext().getString(R.string.settings_noise_reduction_title);
                if (Application.getCoreService().getEarBudsInfo().noiseControlsAmbient) {
                    str = str + ", " + Application.getContext().getString(R.string.settings_ambient_sound);
                }
            } else {
                str = Application.getCoreService().getEarBudsInfo().noiseControlsAmbient ? Application.getContext().getString(R.string.settings_ambient_sound) : "";
            }
            if (Application.getCoreService().getEarBudsInfo().noiseControlsOff) {
                str = str + ", " + Application.getContext().getString(R.string.touch_and_hold_option_off);
            }
            touchAndHoldViewHolder.subDesc.setVisibility(0);
            touchAndHoldViewHolder.subDesc.setText(str);
        } else {
            touchAndHoldViewHolder.subDesc.setVisibility(8);
        }
        if (i == getCount() - 1) {
            touchAndHoldViewHolder.divider.setVisibility(4);
        }
        if (getCount() != 0 && this.mOptionList.get(i) != null) {
            touchAndHoldViewHolder.optionName.setText(this.mOptionList.get(i).getOptionName());
            String optionName = this.mOptionList.get(i).getOptionName();
            if (this.mOptionList.get(i).getSelectedItem()) {
                this.mlastSelectedItem = i;
                touchAndHoldViewHolder.radioButton.setChecked(true);
            } else {
                touchAndHoldViewHolder.radioButton.setChecked(false);
            }
            if (this.mOptionList.get(i).getIsConnected()) {
                UiUtil.setEnabledWithChildren(touchAndHoldViewHolder.listlayout, true);
            } else {
                UiUtil.setEnabledWithChildren(touchAndHoldViewHolder.listlayout, false);
            }
            Log.d(TAG, "getView() : mOptionList :: position =" + i + " optionName = " + optionName + "getSelectedItem : " + this.mOptionList.get(i).getSelectedItem());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<TouchAndHoldOptionData> arrayList = this.mOptionList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.mOptionList.get(i).getViewType();
    }

    public int getCount() {
        ArrayList<TouchAndHoldOptionData> arrayList = this.mOptionList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }
}
