package com.samsung.accessory.hearablemgr.module.notification;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.ui.DropdownListPopup;
import com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.notification.NotificationAppData;
import com.samsung.accessory.hearablemgr.core.notification.NotificationConstants;
import com.samsung.accessory.hearablemgr.core.notification.NotificationManager;
import com.samsung.accessory.hearablemgr.core.notification.NotificationUtil;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;
import java.util.ArrayList;
import seccompat.android.util.Log;

public class NotificationDetailActivity extends ConnectionActivity {
    private static final String TAG = "Attic_NotificationDetailActivity";
    private Drawable appIcon = null;
    private String appLabel = null;
    private int index = 0;
    private boolean isCheked = false;
    private boolean isDual = false;
    private boolean isPause = false;
    private SwitchCompat mAllowSwitch;
    private Context mContext;
    private DropdownListPopup mCurDropdownPopup;
    private TextView mIncomingcallSettingText;
    private ArrayList<NotificationAppData> mNotificationAppList;
    private String packageName = null;

    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_notifications_detail);
        this.mContext = this;
        this.packageName = getIntent().getStringExtra("appPackageName");
        Log.d(TAG, "pn = " + this.packageName);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(this.mContext.getString(R.string.app_notification_to_read_aloud));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onStart() {
        int i;
        Resources resources;
        super.onStart();
        Log.d(TAG, "onStart()");
        if (!this.isPause) {
            this.mNotificationAppList = NotificationManager.getInstance(this.mContext).getNotificationAppList();
            int i2 = 0;
            while (true) {
                if (i2 >= this.mNotificationAppList.size()) {
                    break;
                }
                NotificationAppData notificationAppData = this.mNotificationAppList.get(i2);
                if (notificationAppData.getPackageName().equals(this.packageName)) {
                    this.appLabel = notificationAppData.getAppName();
                    this.appIcon = NotificationManager.getImageIcon(this.mContext, notificationAppData.getPackageName(), notificationAppData.isDual(), notificationAppData.getuId());
                    this.isDual = notificationAppData.isDual();
                    this.index = i2;
                    break;
                }
                i2++;
            }
            if (this.isDual || this.packageName.equals(NotificationConstants.INCOMING_CALL_PACKAGENAME) || this.packageName.equals(NotificationConstants.MISSED_CALL_PACKAGENAME)) {
                findViewById(R.id.app_icon_layout).setBackground(null);
            }
            this.isCheked = NotificationUtil.isAppNotificationEnabled(this.packageName);
            if (!(this.appIcon == null || this.appLabel == null)) {
                ((ImageView) findViewById(R.id.app_icon_image)).setImageDrawable(this.appIcon);
                ((TextView) findViewById(R.id.app_icon_text)).setText(this.appLabel);
            }
            findViewById(R.id.app_icon_layout).setOnClickListener(new OnSingleClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NotificationDetailActivity.AnonymousClass1 */

                @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
                public void onSingleClick(View view) {
                    if (!NotificationDetailActivity.this.isDual && !NotificationDetailActivity.this.packageName.equals(NotificationConstants.INCOMING_CALL_PACKAGENAME) && !NotificationDetailActivity.this.packageName.equals(NotificationConstants.MISSED_CALL_PACKAGENAME)) {
                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.parse("package:" + NotificationDetailActivity.this.packageName));
                        NotificationDetailActivity.this.startActivityForResult(intent, 0);
                    }
                }
            });
            this.mAllowSwitch = (SwitchCompat) findViewById(R.id.allow_notifications_switch);
            this.mAllowSwitch.setChecked(this.isCheked);
            this.mAllowSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NotificationDetailActivity.AnonymousClass2 */

                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    NotificationUtil.setNotiEnabledApplication(NotificationDetailActivity.this.packageName, z ? NotificationConstants.NOTIFICATION_TYPE_ON : NotificationConstants.NOTIFICATION_TYPE_OFF);
                    if (z) {
                        NotificationDetailActivity.this.setSubHide(0);
                    } else {
                        NotificationDetailActivity.this.setSubHide(8);
                    }
                    NotificationManager.getInstance(NotificationDetailActivity.this.mContext).setAppAllowed(NotificationDetailActivity.this.index, NotificationDetailActivity.this.packageName, z);
                }
            });
            this.mAllowSwitch.setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NotificationDetailActivity.AnonymousClass3 */

                public void onClick(View view) {
                }
            });
            if (this.isCheked) {
                setSubHide(0);
            } else {
                setSubHide(8);
            }
            findViewById(R.id.detail_settings_sub_layout1).setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NotificationDetailActivity.AnonymousClass4 */

                public void onClick(View view) {
                    NotificationDetailActivity.this.setRadio(true);
                }
            });
            findViewById(R.id.detail_settings_sub_layout2).setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NotificationDetailActivity.AnonymousClass5 */

                public void onClick(View view) {
                    NotificationDetailActivity.this.setRadio(false);
                }
            });
            findViewById(R.id.read_out_notification_radio1).setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NotificationDetailActivity.AnonymousClass6 */

                public void onClick(View view) {
                    NotificationDetailActivity.this.setRadio(true);
                }
            });
            findViewById(R.id.read_out_notification_radio2).setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NotificationDetailActivity.AnonymousClass7 */

                public void onClick(View view) {
                    NotificationDetailActivity.this.setRadio(false);
                }
            });
            setRadio(NotificationUtil.getAppNotificationDetails(this.packageName).equals(NotificationConstants.NOTIFICATION_TYPE_SUMMARY));
            findViewById(R.id.allow_notification_layout).setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NotificationDetailActivity.AnonymousClass8 */

                public void onClick(View view) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(NotificationDetailActivity.this.packageName);
                    sb.append(";");
                    sb.append(NotificationDetailActivity.this.mAllowSwitch.isChecked() ? "a" : "b");
                    SamsungAnalyticsUtil.sendEvent("6655", SA.Screen.NOTIFICATION_MANAGE_DETAIL, sb.toString());
                    if (!NotificationDetailActivity.this.mAllowSwitch.isChecked()) {
                        NotificationDetailActivity.this.setSubHide(0);
                    } else {
                        NotificationDetailActivity.this.setSubHide(8);
                    }
                    NotificationDetailActivity.this.mAllowSwitch.setChecked(!NotificationDetailActivity.this.mAllowSwitch.isChecked());
                    NotificationUtil.setNotiEnabledApplication(NotificationDetailActivity.this.packageName, NotificationDetailActivity.this.mAllowSwitch.isChecked() ? NotificationConstants.NOTIFICATION_TYPE_ON : NotificationConstants.NOTIFICATION_TYPE_OFF);
                }
            });
            this.mIncomingcallSettingText = (TextView) findViewById(R.id.repeat_settings_subtext);
            TextView textView = this.mIncomingcallSettingText;
            if (Preferences.getBoolean(PreferenceKey.NOTIFICATION_CALL_REPEAT, true)) {
                resources = getResources();
                i = R.string.repeat_once;
            } else {
                resources = getResources();
                i = R.string.do_not_repeat;
            }
            textView.setText(resources.getString(i));
            findViewById(R.id.repeat_settings_layout).setOnClickListener(new View.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NotificationDetailActivity.AnonymousClass9 */

                public void onClick(View view) {
                    String[] strArr = {NotificationDetailActivity.this.getString(R.string.repeat_once), NotificationDetailActivity.this.getString(R.string.do_not_repeat)};
                    int i = !Preferences.getBoolean(PreferenceKey.NOTIFICATION_CALL_REPEAT, true) ? 1 : 0;
                    NotificationDetailActivity notificationDetailActivity = NotificationDetailActivity.this;
                    notificationDetailActivity.mCurDropdownPopup = new DropdownListPopup(notificationDetailActivity, view, strArr, Integer.valueOf(i));
                    NotificationDetailActivity.this.mCurDropdownPopup.getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
                        /* class com.samsung.accessory.hearablemgr.module.notification.NotificationDetailActivity.AnonymousClass9.AnonymousClass1 */

                        public void onDismiss() {
                            NotificationDetailActivity.this.mCurDropdownPopup = null;
                        }
                    });
                    NotificationDetailActivity.this.mCurDropdownPopup.setOnItemClickListener(new DropdownListPopup.OnItemClickListener() {
                        /* class com.samsung.accessory.hearablemgr.module.notification.NotificationDetailActivity.AnonymousClass9.AnonymousClass2 */

                        @Override // com.samsung.accessory.hearablemgr.common.ui.DropdownListPopup.OnItemClickListener
                        public void onItemClick(DropdownListPopup dropdownListPopup, View view, int i, long j) {
                            if (i == 0) {
                                SamsungAnalyticsUtil.sendEvent(SA.Event.REPEAT, SA.Screen.NOTIFICATION_MANAGE_DETAIL, NotificationDetailActivity.this.packageName + ";b");
                                SamsungAnalyticsUtil.setStatusString(SA.Status.REPEAT, "A");
                                NotificationDetailActivity.this.mIncomingcallSettingText.setText(R.string.repeat_once);
                                Preferences.putBoolean(PreferenceKey.NOTIFICATION_CALL_REPEAT, true);
                                dropdownListPopup.dismiss();
                            } else if (i == 1) {
                                SamsungAnalyticsUtil.sendEvent(SA.Event.REPEAT, SA.Screen.NOTIFICATION_MANAGE_DETAIL, NotificationDetailActivity.this.packageName + ";a");
                                SamsungAnalyticsUtil.setStatusString(SA.Status.REPEAT, "B");
                                NotificationDetailActivity.this.mIncomingcallSettingText.setText(R.string.do_not_repeat);
                                Preferences.putBoolean(PreferenceKey.NOTIFICATION_CALL_REPEAT, false);
                                dropdownListPopup.dismiss();
                            }
                        }
                    });
                    NotificationDetailActivity.this.mCurDropdownPopup.show();
                }
            });
            if (!this.isCheked || !this.packageName.equals(NotificationConstants.INCOMING_CALL_PACKAGENAME)) {
                findViewById(R.id.repeat_settings_layout).setVisibility(8);
            } else {
                findViewById(R.id.repeat_settings_layout).setVisibility(0);
            }
        }
    }

    public void setSubHide(int i) {
        findViewById(R.id.detail_settings_layout).setVisibility(i);
        findViewById(R.id.detali_framelayout).setVisibility(i);
        if (i != 0 || !this.packageName.equals(NotificationConstants.INCOMING_CALL_PACKAGENAME)) {
            findViewById(R.id.repeat_settings_layout).setVisibility(8);
        } else {
            findViewById(R.id.repeat_settings_layout).setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setRadio(boolean z) {
        ((RadioButton) findViewById(R.id.read_out_notification_radio1)).setChecked(z);
        ((RadioButton) findViewById(R.id.read_out_notification_radio2)).setChecked(!z);
        NotificationUtil.setAppNotificationDetails(this.packageName, z ? NotificationConstants.NOTIFICATION_TYPE_SUMMARY : NotificationConstants.NOTIFICATION_TYPE_DETAIL);
        StringBuilder sb = new StringBuilder();
        sb.append(this.packageName);
        sb.append(";");
        sb.append(z ? "b" : "a");
        SamsungAnalyticsUtil.sendEvent(SA.Event.READ_OUT_NOTIFICATIONS, SA.Screen.NOTIFICATION_MANAGE_DETAIL, sb.toString());
        SamsungAnalyticsUtil.setStatusString("6678", z ? "A" : "B");
    }

    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        this.isPause = false;
        SamsungAnalyticsUtil.sendPage(SA.Screen.NOTIFICATION_MANAGE_DETAIL);
        if (Util.isTalkBackEnabled()) {
            this.mAllowSwitch.setFocusable(false);
            this.mAllowSwitch.setClickable(false);
            return;
        }
        this.mAllowSwitch.setFocusable(true);
        this.mAllowSwitch.setClickable(true);
    }

    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        this.isPause = true;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        SamsungAnalyticsUtil.sendEvent(SA.Event.UP_BUTTON, SA.Screen.NOTIFICATION_MANAGE_DETAIL);
        finish();
        return super.onSupportNavigateUp();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        DropdownListPopup dropdownListPopup = this.mCurDropdownPopup;
        if (dropdownListPopup != null) {
            dropdownListPopup.dismiss();
            this.mCurDropdownPopup = null;
        }
    }
}
