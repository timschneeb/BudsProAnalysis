package com.samsung.accessory.hearablemgr.module.notification;

import android.app.Activity;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.notification.NotificationAppData;
import com.samsung.accessory.hearablemgr.core.notification.NotificationConstants;
import com.samsung.accessory.hearablemgr.core.notification.NotificationManager;
import com.samsung.accessory.hearablemgr.core.notification.NotificationUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seccompat.android.util.Log;

public class NotificationListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements Filterable {
    private static final String TAG = "Attic_NotificationListAdapter";
    private boolean enable = true;
    private Activity mActivity;
    private ArrayList<NotificationAppData> mAppList;
    private Context mContext;
    private ICheckedNotificationApp mListener;
    private String mSearchText;
    private ArrayList<NotificationAppData> originalAppList;

    public interface ICheckedNotificationApp {
        void onChangeSearchList(int i);

        void onClickAppSettingDetail(NotificationAppData notificationAppData);

        void setCheckedApp(int i);
    }

    public NotificationListAdapter(Context context, ArrayList<NotificationAppData> arrayList, ICheckedNotificationApp iCheckedNotificationApp) {
        this.mActivity = (Activity) context;
        this.mAppList = arrayList;
        this.mListener = iCheckedNotificationApp;
        this.mContext = context;
    }

    public ArrayList<NotificationAppData> getList() {
        return this.mAppList;
    }

    public void setList(ArrayList<NotificationAppData> arrayList) {
        this.mAppList = arrayList;
        this.originalAppList = arrayList;
    }

    public void setEnable(boolean z) {
        this.enable = z;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification_list, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.appDescription.setVisibility(0);
        recyclerViewHolder.appDescription2.setVisibility(0);
        recyclerViewHolder.switccDivider.setVisibility(0);
        recyclerViewHolder.switchlayout.setVisibility(0);
        if (!(getCount() == 0 || this.mAppList.get(i) == null)) {
            recyclerViewHolder.appIcon.setImageDrawable(NotificationManager.getImageIcon(this.mContext, this.mAppList.get(i).getPackageName(), this.mAppList.get(i).isDual(), this.mAppList.get(i).getuId()));
            recyclerViewHolder.appName.setText(getSearchKeywordColorSpan(this.mAppList.get(i).getAppName(), this.mSearchText));
            recyclerViewHolder.selectApp.setContentDescription(this.mAppList.get(i).getAppName());
            setSubtext(i, recyclerViewHolder);
            recyclerViewHolder.appDescription.setVisibility(8);
            String packageName = this.mAppList.get(i).getPackageName();
            boolean isAppNotificationEnabled = NotificationUtil.isAppNotificationEnabled(packageName);
            recyclerViewHolder.selectApp.setChecked(isAppNotificationEnabled);
            this.mAppList.get(i).setEnable(isAppNotificationEnabled);
            if (Util.isTalkBackEnabled()) {
                recyclerViewHolder.selectApp.setFocusable(false);
                recyclerViewHolder.selectApp.setClickable(false);
            } else {
                recyclerViewHolder.selectApp.setFocusable(true);
                recyclerViewHolder.selectApp.setClickable(true);
            }
            Log.d(TAG, "getView() : mAppList :: position =" + i + " packageName = " + packageName + " isAppNotificationEnabled() = " + isAppNotificationEnabled + " Desc. Setting = " + recyclerViewHolder.appDescription.getText().toString() + "/" + recyclerViewHolder.appDescription2.getText().toString());
        }
        if (i == getCount() - 1) {
            recyclerViewHolder.divider.setVisibility(8);
        } else {
            recyclerViewHolder.divider.setVisibility(0);
        }
        setEnableView(recyclerViewHolder, this.enable, false);
        if (!NotificationUtil.semAreNotificationsEnabledForPackage(this.mAppList.get(i).getPackageName(), this.mAppList.get(i).isDual(), this.mAppList.get(i).getuId())) {
            if (this.enable) {
                setEnableView(recyclerViewHolder, false, true);
            }
            recyclerViewHolder.appDescription2.setText(R.string.blocked_on_phone);
        }
    }

    private void setEnableView(RecyclerViewHolder recyclerViewHolder, boolean z, boolean z2) {
        recyclerViewHolder.listlayout.setEnabled(z);
        if (z2) {
            recyclerViewHolder.switchlayout.setVisibility(z ? 0 : 8);
        } else {
            recyclerViewHolder.switchlayout.setEnabled(z);
        }
        recyclerViewHolder.selectApp.setEnabled(z);
        recyclerViewHolder.appName.setEnabled(z);
        recyclerViewHolder.appDescription.setEnabled(z);
        recyclerViewHolder.appDescription2.setEnabled(z);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(z ? 1.0f : 0.0f);
        recyclerViewHolder.appIcon.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return (long) this.mAppList.get(i).getAppName().hashCode();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<NotificationAppData> arrayList = this.mAppList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public int getCount() {
        ArrayList<NotificationAppData> arrayList = this.mAppList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int calcMaxWidthForAppName() {
        return this.mActivity.getApplicationContext().getResources().getDisplayMetrics().widthPixels - convertDipToPixels(114.0f);
    }

    private int convertDipToPixels(float f) {
        return (int) ((f * this.mActivity.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public int getCheckedCount() {
        Log.d(TAG, "getCheckedCount()");
        int i = 0;
        for (int i2 = 0; i2 < getCount(); i2++) {
            if (this.mAppList.get(i2).isEnable()) {
                i++;
            }
        }
        return i;
    }

    public Filter getFilter() {
        return new Filter() {
            /* class com.samsung.accessory.hearablemgr.module.notification.NotificationListAdapter.AnonymousClass1 */

            /* access modifiers changed from: protected */
            public Filter.FilterResults performFiltering(CharSequence charSequence) {
                ArrayList arrayList = new ArrayList();
                if (TextUtils.isEmpty(charSequence.toString())) {
                    arrayList = NotificationListAdapter.this.originalAppList;
                } else {
                    Iterator it = NotificationListAdapter.this.originalAppList.iterator();
                    while (it.hasNext()) {
                        NotificationAppData notificationAppData = (NotificationAppData) it.next();
                        if (notificationAppData.getAppName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            arrayList.add(notificationAppData);
                        }
                    }
                }
                Filter.FilterResults filterResults = new Filter.FilterResults();
                filterResults.values = arrayList;
                filterResults.count = arrayList.size();
                return filterResults;
            }

            /* access modifiers changed from: protected */
            public void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                NotificationListAdapter.this.mAppList = (ArrayList) filterResults.values;
                NotificationListAdapter.this.mSearchText = charSequence.toString();
                NotificationListAdapter.this.notifyDataSetChanged();
                NotificationListAdapter.this.mListener.onChangeSearchList(NotificationListAdapter.this.mAppList.size());
            }
        };
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView appDescription;
        public TextView appDescription2;
        public ImageView appIcon;
        public TextView appName;
        public View divider;
        public View listlayout;
        public SwitchCompat selectApp;
        public View switccDivider;
        public View switchlayout;
        public RecyclerViewHolder viewHolder = this;

        public RecyclerViewHolder(View view) {
            super(view);
            this.listlayout = (LinearLayout) view.findViewById(R.id.notifiation_list);
            this.switchlayout = (LinearLayout) view.findViewById(R.id.switch_layout);
            this.switccDivider = view.findViewById(R.id.switch_divider);
            this.selectApp = (SwitchCompat) view.findViewById(R.id.noti_listview_cb);
            this.appIcon = (ImageView) view.findViewById(R.id.listview_iconimg);
            this.appName = (TextView) view.findViewById(R.id.textview1);
            this.appName.setSelected(true);
            this.appDescription = (TextView) view.findViewById(R.id.textview2_second);
            this.appDescription.setSelected(true);
            this.appDescription2 = (TextView) view.findViewById(R.id.textview3);
            this.appDescription2.setSelected(true);
            this.appName.setMaxWidth(NotificationListAdapter.this.calcMaxWidthForAppName());
            this.divider = view.findViewById(R.id.divider);
            this.listlayout.setOnClickListener(new OnSingleClickListener(NotificationListAdapter.this) {
                /* class com.samsung.accessory.hearablemgr.module.notification.NotificationListAdapter.RecyclerViewHolder.AnonymousClass1 */

                @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
                public void onSingleClick(View view) {
                    NotificationListAdapter.this.mListener.onClickAppSettingDetail((NotificationAppData) NotificationListAdapter.this.mAppList.get(RecyclerViewHolder.this.getAdapterPosition()));
                }
            });
            this.switchlayout.setOnClickListener(new View.OnClickListener(NotificationListAdapter.this) {
                /* class com.samsung.accessory.hearablemgr.module.notification.NotificationListAdapter.RecyclerViewHolder.AnonymousClass2 */

                public void onClick(View view) {
                    RecyclerViewHolder.this.selectApp.setChecked(!RecyclerViewHolder.this.selectApp.isChecked());
                }
            });
            this.selectApp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(NotificationListAdapter.this) {
                /* class com.samsung.accessory.hearablemgr.module.notification.NotificationListAdapter.RecyclerViewHolder.AnonymousClass3 */

                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    int adapterPosition = RecyclerViewHolder.this.getAdapterPosition();
                    Log.d(NotificationListAdapter.TAG, "list clicked." + adapterPosition);
                    try {
                        NotificationAppData notificationAppData = (NotificationAppData) NotificationListAdapter.this.mAppList.get(adapterPosition);
                        notificationAppData.setEnable(z);
                        if (NotificationListAdapter.this.mListener != null) {
                            NotificationListAdapter.this.mListener.setCheckedApp(adapterPosition);
                        }
                        NotificationListAdapter.this.setSubtext(adapterPosition, RecyclerViewHolder.this.viewHolder);
                        StringBuilder sb = new StringBuilder();
                        sb.append(z ? "1" : "0");
                        sb.append(" ");
                        sb.append(notificationAppData.getPackageName());
                        SamsungAnalyticsUtil.setStatusString("6672", sb.toString());
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setSubtext(int i, RecyclerViewHolder recyclerViewHolder) {
        String str;
        String str2;
        boolean equals = this.mAppList.get(i).getPackageName().equals(NotificationConstants.INCOMING_CALL_PACKAGENAME);
        int i2 = R.string.voice_notification_details01;
        if (equals) {
            if (NotificationUtil.getAppNotificationDetails(this.mAppList.get(i).getPackageName()).equals(NotificationConstants.NOTIFICATION_TYPE_SUMMARY)) {
                str = "" + this.mContext.getString(R.string.voice_notification_details01) + ", ";
            } else {
                str = "" + this.mContext.getString(R.string.voice_notification_details02) + ", ";
            }
            if (Preferences.getBoolean(PreferenceKey.NOTIFICATION_CALL_REPEAT, true)) {
                str2 = str + this.mContext.getString(R.string.repeat_once);
            } else {
                str2 = str + this.mContext.getString(R.string.do_not_repeat);
            }
            if (this.mAppList.get(i).isEnable()) {
                recyclerViewHolder.appDescription2.setText(str2);
            } else {
                recyclerViewHolder.appDescription2.setText(R.string.menu_not_read_aloud);
            }
            if (NotificationUtil.isSupportSpeakCallerName()) {
                recyclerViewHolder.appDescription2.setVisibility(8);
                recyclerViewHolder.switccDivider.setVisibility(4);
            }
        } else if (this.mAppList.get(i).isEnable()) {
            TextView textView = recyclerViewHolder.appDescription2;
            if (!NotificationUtil.getAppNotificationDetails(this.mAppList.get(i).getPackageName()).equals(NotificationConstants.NOTIFICATION_TYPE_SUMMARY)) {
                i2 = R.string.voice_notification_details02;
            }
            textView.setText(i2);
        } else {
            recyclerViewHolder.appDescription2.setText(R.string.menu_not_read_aloud);
        }
    }

    private SpannableString getSearchKeywordColorSpan(String str, String str2) {
        SpannableString spannableString = new SpannableString(str);
        if (!TextUtils.isEmpty(str2)) {
            Matcher matcher = Pattern.compile(str2.toLowerCase()).matcher(str.toLowerCase());
            while (matcher.find()) {
                spannableString.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.colorPrimary)), matcher.start(), matcher.end(), 33);
            }
        }
        return spannableString;
    }
}
