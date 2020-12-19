package com.samsung.accessory.hearablemgr.module.notification;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import java.util.Arrays;

public class NotificationSpinnerAdapter extends ArrayAdapter<AppListFilter> {
    private int currentSelectedPosition = 0;

    public enum AppListFilter {
        Allowed(R.string.menu_read_aloud),
        Blocked(R.string.menu_not_read_aloud),
        All(R.string.menu_all);
        
        private int titleResource;

        private AppListFilter(int i) {
            this.titleResource = i;
        }

        public String toString() {
            return Application.getContext().getString(this.titleResource);
        }
    }

    public NotificationSpinnerAdapter(Context context) {
        super(context, R.layout.item_notification_spinner_normal);
        setDropDownViewResource(R.layout.item_notification_spinner_check);
        addAll(Arrays.asList(AppListFilter.values()));
    }

    public void setSelectedPosition(int i) {
        this.currentSelectedPosition = i;
    }

    public int getSelectedPosition() {
        return this.currentSelectedPosition;
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        CheckedTextView checkedTextView = (CheckedTextView) super.getDropDownView(i, view, viewGroup);
        if (this.currentSelectedPosition == i) {
            checkedTextView.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
            if (isSupportDropDownItemStyle()) {
                checkedTextView.setCheckMarkDrawable(getContext().getResources().getDrawable(R.drawable.tw_common_list_ic_check));
                checkedTextView.setChecked(true);
            }
        } else {
            checkedTextView.setTextColor(getContext().getResources().getColor(R.color.title_text_normal_color));
            if (isSupportDropDownItemStyle()) {
                checkedTextView.setCheckMarkDrawable((Drawable) null);
                checkedTextView.setChecked(false);
            }
        }
        return checkedTextView;
    }

    private boolean isSupportDropDownItemStyle() {
        return Util.isSamsungDevice() && Build.VERSION.SDK_INT > 26;
    }
}
