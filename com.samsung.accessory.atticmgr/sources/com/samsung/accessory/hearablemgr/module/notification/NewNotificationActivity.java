package com.samsung.accessory.hearablemgr.module.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.notification.NotificationAppData;
import com.samsung.accessory.hearablemgr.core.notification.NotificationConstants;
import com.samsung.accessory.hearablemgr.core.notification.NotificationManager;
import com.samsung.accessory.hearablemgr.core.notification.NotificationUtil;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;
import com.samsung.accessory.hearablemgr.module.notification.NotificationListAdapter;
import java.util.ArrayList;
import seccompat.android.util.Log;

public class NewNotificationActivity extends ConnectionActivity {
    private static final String TAG = "Attic_NotificationActivity";
    NotificationListAdapter mAllowAdapter;
    ArrayList<NotificationAppData> mAllowAppList;
    private RecyclerView mAllowRecyclerview;
    protected Context mContext;
    private LinearLayout mIgnoreEnableLayout;
    private SwitchCompat mIgnoreSwitch;
    private TextView mIgnoreText;
    private TextView mIgnoreTextDesc;
    private BroadcastReceiver mListUpdateReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.module.notification.NewNotificationActivity.AnonymousClass10 */

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            if (action.equals(NotificationConstants.ACTION_NOTIFICATION_LIST_UPDATE)) {
                Log.d(NewNotificationActivity.TAG, "ACTION_NOTIFICATION_LIST_UPDATE");
                if (NewNotificationActivity.this.retrieveHandler != null) {
                    NewNotificationActivity.this.retrieveHandler.sendMessage(2);
                }
            } else if (action.equals(NotificationConstants.ACTION_NOTIFICATION_SETTING_UPDATE)) {
                Log.d(NewNotificationActivity.TAG, "ACTION_NOTIFICATION_SETTING_UPDATE");
                NewNotificationActivity.this.mSwitchBar.setChecked(Preferences.getBoolean(PreferenceKey.NOTIFICATION_ENABLE, true));
            }
        }
    };
    private AlertDialog mNotificationAccessDialog = null;
    private boolean mPause = false;
    private NestedScrollView mScrollView;
    private SeslSwitchBar mSwitchBar;
    private AlertDialog mlimitNotiDialog = null;
    protected RetrieveProgressDialog retrieveDialog = null;
    protected RetrieveHandler retrieveHandler;
    private LinearLayout seeAllLayout;
    private TextView seeAllTextView;

    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "onCreate()::savedInstanceState =" + bundle);
        setContentView(R.layout.activity_new_notification);
        this.mContext = this;
        this.mPause = false;
        this.retrieveHandler = new RetrieveHandler();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(this.mContext.getString(R.string.read_notifications_aloud));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NotificationConstants.ACTION_NOTIFICATION_LIST_UPDATE);
        intentFilter.addAction(NotificationConstants.ACTION_NOTIFICATION_SETTING_UPDATE);
        registerReceiver(this.mListUpdateReceiver, intentFilter);
        this.seeAllTextView = (TextView) findViewById(R.id.see_all_text);
        this.seeAllLayout = (LinearLayout) findViewById(R.id.see_all_layout);
        this.mSwitchBar = (SeslSwitchBar) findViewById(R.id.switch_bar);
        this.mSwitchBar.addOnSwitchChangeListener(new SeslSwitchBar.OnSwitchChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.notification.$$Lambda$NewNotificationActivity$UrYAFDdU8bIEvR46WQsOHZP54 */

            @Override // androidx.appcompat.widget.SeslSwitchBar.OnSwitchChangeListener
            public final void onSwitchChanged(SwitchCompat switchCompat, boolean z) {
                NewNotificationActivity.this.lambda$onCreate$0$NewNotificationActivity(switchCompat, z);
            }
        });
        this.mIgnoreText = (TextView) findViewById(R.id.ignore_enable_text);
        this.mIgnoreSwitch = (SwitchCompat) findViewById(R.id.ignore_enable_switch);
        if (!NotificationUtil.isLockNone() || Preferences.getBoolean(PreferenceKey.NOTIFICATION_IGNORE_SETTING, true)) {
            this.mIgnoreSwitch.setChecked(!Preferences.getBoolean(PreferenceKey.NOTIFICATION_IGNORE_SETTING, true));
        } else {
            Log.d(TAG, "no lock screen");
            this.mIgnoreSwitch.setChecked(true);
            Preferences.putBoolean(PreferenceKey.NOTIFICATION_IGNORE_SETTING, false);
        }
        this.mIgnoreSwitch.setEnabled(Preferences.getBoolean(PreferenceKey.NOTIFICATION_ENABLE, true));
        this.mIgnoreTextDesc = (TextView) findViewById(R.id.ignore_enable_text_desc);
        this.mIgnoreEnableLayout = (LinearLayout) findViewById(R.id.ignore_notification_settingLayout);
        this.mIgnoreEnableLayout.setEnabled(Preferences.getBoolean(PreferenceKey.NOTIFICATION_ENABLE, true));
        this.mIgnoreEnableLayout.setFocusable(true);
        this.mIgnoreEnableLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.notification.NewNotificationActivity.AnonymousClass1 */

            public void onClick(View view) {
                Log.d(NewNotificationActivity.TAG, "mIgnoreEnablePanel.onClick");
                NewNotificationActivity.this.mIgnoreSwitch.setChecked(!NewNotificationActivity.this.mIgnoreSwitch.isChecked());
            }
        });
        this.mIgnoreSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.notification.NewNotificationActivity.AnonymousClass2 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (!NotificationUtil.isLockNone() || z) {
                    NewNotificationActivity.this.onClickIgnoreEnable(z);
                    return;
                }
                NewNotificationActivity.this.mIgnoreSwitch.setChecked(true);
                NewNotificationActivity.this.limitNotiDialog();
            }
        });
        this.seeAllLayout.setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.notification.NewNotificationActivity.AnonymousClass3 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                Intent intent = new Intent(NewNotificationActivity.this, NewManageNotificationActivity.class);
                if (NewNotificationActivity.this.mAllowAppList == null || NewNotificationActivity.this.mAllowAppList.size() <= 0) {
                    SamsungAnalyticsUtil.sendEvent(SA.Event.MANAGE_NOTIFICATIONS, SA.Screen.NOTIFICATION);
                    intent.putExtra("position", 2);
                } else {
                    SamsungAnalyticsUtil.sendEvent(SA.Event.NOTIFICATION_SEE_ALL, SA.Screen.NOTIFICATION);
                    intent.putExtra("position", 0);
                }
                NewNotificationActivity.this.startActivity(intent);
            }
        });
        this.mScrollView = (NestedScrollView) findViewById(R.id.notification_scrollview);
    }

    public /* synthetic */ void lambda$onCreate$0$NewNotificationActivity(SwitchCompat switchCompat, boolean z) {
        NotificationUtil.setPreIncomingCallStatus(z);
        SamsungAnalyticsUtil.sendEvent(SA.Event.MANAGE_NOTIFICATIONS_SWITCH, SA.Screen.NOTIFICATION, z ? "b" : "a");
        isVnSwitchOn(z);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initAppList() {
        Log.d(TAG, "initAppList()::");
        if (this.mPause) {
            Log.d(TAG, "after pause, do not init Applist");
        } else if (!NotificationManager.getInstance(this.mContext).isListCreated()) {
            Log.d(TAG, "initAppList not yet!");
            showRetrieveDialog();
        } else {
            findViewById(R.id.recently_recent_layout).setVisibility(0);
            initAllowList();
            int size = this.mAllowAppList.size();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            if (size == 0) {
                this.seeAllTextView.setText(getString(R.string.app_notification_to_read_aloud));
                layoutParams.setMarginStart(dpToPixel(24));
                this.seeAllTextView.setLayoutParams(layoutParams);
                this.seeAllLayout.setGravity(16);
                this.seeAllTextView.setTextSize(18.0f);
                if (Build.VERSION.SDK_INT >= 23) {
                    this.seeAllTextView.setTextColor(getResources().getColorStateList(R.color.selector_list_title_color, this.mContext.getTheme()));
                } else {
                    this.seeAllTextView.setTextColor(getResources().getColorStateList(R.color.selector_list_title_color));
                }
            } else {
                this.seeAllTextView.setText(getString(R.string.see_all));
                layoutParams.setMarginStart(0);
                this.seeAllTextView.setLayoutParams(layoutParams);
                this.seeAllLayout.setGravity(17);
                this.seeAllTextView.setTextSize(16.0f);
                if (Build.VERSION.SDK_INT >= 23) {
                    this.seeAllTextView.setTextColor(getResources().getColorStateList(R.color.selector_list_desc_color, this.mContext.getTheme()));
                } else {
                    this.seeAllTextView.setTextColor(getResources().getColorStateList(R.color.selector_list_desc_color));
                }
            }
            int i = 8;
            findViewById(R.id.divider).setVisibility(size == 0 ? 8 : 0);
            View findViewById = findViewById(R.id.see_all_title);
            if (size != 0) {
                i = 0;
            }
            findViewById.setVisibility(i);
            String string = Preferences.getString(PreferenceKey.NOTIFICATION_LOCALE, null);
            String locale = this.mContext.getResources().getConfiguration().locale.toString();
            Log.d(TAG, "Util.getConfigChange(getActivity()):" + string + ":");
            Log.d(TAG, "mCurLocale:" + locale + ":");
            if (!locale.equals(string) && this.retrieveHandler != null) {
                Log.d(TAG, "handle MSG_RETRIEVE_APPNAME_UPDATE");
                this.retrieveHandler.sendMessage(3);
            }
            if (Preferences.getBoolean(PreferenceKey.NOTIFICATION_FIRST_ENTRY, true)) {
                isVnSwitchOn(true);
                Preferences.putBoolean(PreferenceKey.NOTIFICATION_FIRST_ENTRY, false);
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onActivityResult(int i, int i2, Intent intent) {
        NotificationListAdapter notificationListAdapter;
        super.onActivityResult(i, i2, intent);
        Log.d(TAG, "onActivityResult " + i + " " + i2);
        if (i == 123 && (notificationListAdapter = this.mAllowAdapter) != null) {
            notificationListAdapter.notifyDataSetChanged();
        }
    }

    public void initAllowList() {
        this.mAllowRecyclerview = (RecyclerView) findViewById(R.id.allow_recyclerview);
        this.mAllowRecyclerview.setItemAnimator(null);
        this.mAllowRecyclerview.setNestedScrollingEnabled(false);
        this.mAllowRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        this.mAllowAppList = NotificationManager.getInstance(this.mContext).getAllowedNotificationList(true);
        if (this.mAllowAppList.size() > 5) {
            this.mAllowAppList = new ArrayList<>(this.mAllowAppList.subList(0, 5));
        }
        this.mAllowAdapter = new NotificationListAdapter(this, this.mAllowAppList, new NotificationListAdapter.ICheckedNotificationApp() {
            /* class com.samsung.accessory.hearablemgr.module.notification.NewNotificationActivity.AnonymousClass4 */

            @Override // com.samsung.accessory.hearablemgr.module.notification.NotificationListAdapter.ICheckedNotificationApp
            public void onChangeSearchList(int i) {
            }

            @Override // com.samsung.accessory.hearablemgr.module.notification.NotificationListAdapter.ICheckedNotificationApp
            public void setCheckedApp(int i) {
                Log.d(NewNotificationActivity.TAG, "updateTitleCount(mNotificationTitle)");
                NotificationAppData notificationAppData = NewNotificationActivity.this.mAllowAppList.get(i);
                NotificationUtil.setNotiEnabledApplication(notificationAppData.getPackageName(), notificationAppData.isEnable() ? NotificationConstants.NOTIFICATION_TYPE_ON : NotificationConstants.NOTIFICATION_TYPE_OFF);
                if (notificationAppData.getPackageName().equals(NotificationConstants.INCOMING_CALL_PACKAGENAME)) {
                    NotificationUtil.setSpeakCallerName(notificationAppData.isEnable() ? 1 : 0);
                }
            }

            @Override // com.samsung.accessory.hearablemgr.module.notification.NotificationListAdapter.ICheckedNotificationApp
            public void onClickAppSettingDetail(NotificationAppData notificationAppData) {
                Log.d(NewNotificationActivity.TAG, "onClickAppSettingDetail()");
                if (!NotificationUtil.isSupportSpeakCallerName() || !notificationAppData.getPackageName().equals(NotificationConstants.INCOMING_CALL_PACKAGENAME)) {
                    SamsungAnalyticsUtil.sendEvent(SA.Event.NOTIFICATION_APPS, SA.Screen.NOTIFICATION);
                    Intent intent = new Intent(NewNotificationActivity.this, NotificationDetailActivity.class);
                    intent.putExtra("appPackageName", notificationAppData.getPackageName());
                    NewNotificationActivity.this.startActivityForResult(intent, 123);
                    return;
                }
                NotificationUtil.setNotiEnabledApplication(notificationAppData.getPackageName(), !notificationAppData.isEnable() ? NotificationConstants.NOTIFICATION_TYPE_ON : NotificationConstants.NOTIFICATION_TYPE_OFF);
                NotificationUtil.setSpeakCallerName(!notificationAppData.isEnable());
                NewNotificationActivity.this.mAllowAdapter.notifyDataSetChanged();
            }
        });
        this.mAllowAdapter.setHasStableIds(true);
        this.mAllowAdapter.setEnable(this.mSwitchBar.isChecked());
        this.mAllowRecyclerview.setAdapter(this.mAllowAdapter);
    }

    private void initDisplay() {
        if (!NotificationUtil.isAccessibilityON() || !Preferences.getBoolean(PreferenceKey.NOTIFICATION_ENABLE, true)) {
            isVnSwitchOn(false);
        } else {
            isVnSwitchOn(true);
        }
        findViewById(R.id.recently_recent_layout).setVisibility(8);
    }

    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        if (this.mPause) {
            NotificationManager.getInstance(this.mContext).setCheckIncomingCallStatus(NotificationUtil.isAppNotificationEnabled(NotificationConstants.INCOMING_CALL_PACKAGENAME));
        }
        SamsungAnalyticsUtil.sendPage(SA.Screen.NOTIFICATION);
        if (!this.mPause) {
            this.mScrollView.scrollTo(0, 0);
        }
        this.mPause = false;
        if (this.mSwitchBar.isChecked() && !NotificationUtil.isAccessibilityON()) {
            isVnSwitchOn(false);
        }
        if (NotificationUtil.isLockNone() && !this.mIgnoreSwitch.isChecked()) {
            Log.d(TAG, "onResume - no lock screen");
            this.mIgnoreSwitch.setChecked(true);
        }
        if (Util.isTalkBackEnabled()) {
            this.mIgnoreSwitch.setFocusable(false);
            this.mIgnoreSwitch.setClickable(false);
            this.mSwitchBar.setFocusable(false);
            this.mSwitchBar.setClickable(false);
        } else {
            this.mIgnoreSwitch.setFocusable(true);
            this.mIgnoreSwitch.setClickable(true);
            this.mSwitchBar.setFocusable(true);
            this.mSwitchBar.setClickable(true);
        }
        initDisplay();
        initAppList();
    }

    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        this.mPause = true;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onStop() {
        super.onStop();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
        unregisterReceiver(this.mListUpdateReceiver);
        RetrieveHandler retrieveHandler2 = this.retrieveHandler;
        if (retrieveHandler2 != null) {
            retrieveHandler2.removeCallbacksAndMessages(null);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void isVnSwitchOn(boolean z) {
        Log.d(TAG, "isVnSwitchOn::" + z);
        this.mIgnoreText.setEnabled(z);
        this.mIgnoreTextDesc.setEnabled(z);
        this.mIgnoreEnableLayout.setEnabled(z);
        this.mIgnoreSwitch.setEnabled(z);
        NotificationListAdapter notificationListAdapter = this.mAllowAdapter;
        if (notificationListAdapter != null) {
            notificationListAdapter.setEnable(z);
        }
        this.seeAllLayout.setEnabled(z);
        this.seeAllTextView.setEnabled(z);
        Preferences.putBoolean(PreferenceKey.NOTIFICATION_ENABLE, Boolean.valueOf(z));
        SamsungAnalyticsUtil.setStatusInt(SA.Status.NOTIFICATION_ON, z ? 1 : 0);
        if (!z) {
            this.mSwitchBar.setChecked(false);
            return;
        }
        if (!NotificationUtil.isAccessibilityON()) {
            if (!Util.isSamsungDevice() || Util.getSDKVer() >= 27) {
                showEnableNotificationAccessDialog();
            } else {
                NotificationUtil.enableNotificationService(true);
            }
        }
        this.mSwitchBar.setChecked(true);
    }

    public void onClickIgnoreEnable(boolean z) {
        Log.d(TAG, "onClickIgnoreEnable" + z);
        Preferences.putBoolean(PreferenceKey.NOTIFICATION_IGNORE_SETTING, Boolean.valueOf(z ^ true));
        SamsungAnalyticsUtil.sendEvent(SA.Event.READ_OUT_WHILE_USING_PHONE, SA.Screen.NOTIFICATION, z ? "a" : "b");
    }

    private void showEnableNotificationAccessDialog() {
        Log.d(TAG, "showEnableNotificationAccessDialog()");
        if (this.mNotificationAccessDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.turn_on_notifications_dialog_title, new Object[]{getString(R.string.app_name)}));
            builder.setMessage(getString(R.string.turn_on_notifications_dialog_content, new Object[]{getString(R.string.app_name)}));
            builder.setPositiveButton(getResources().getString(R.string.turn_on_notifications_dialog_button), new DialogInterface.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NewNotificationActivity.AnonymousClass5 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (NewNotificationActivity.this.mNotificationAccessDialog != null && NewNotificationActivity.this.mNotificationAccessDialog.isShowing()) {
                        NewNotificationActivity.this.mNotificationAccessDialog.dismiss();
                        NewNotificationActivity.this.mNotificationAccessDialog = null;
                    }
                    Intent intent = new Intent();
                    if (Util.getSDKVer() == 17) {
                        intent.setAction("android.settings.ACCESSIBILITY_SETTINGS");
                    } else {
                        intent.setAction("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                    }
                    intent.addFlags(268435456);
                    try {
                        NewNotificationActivity.this.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NewNotificationActivity.AnonymousClass6 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (NewNotificationActivity.this.mSwitchBar.isChecked() && !NotificationUtil.isAccessibilityON()) {
                        NewNotificationActivity.this.isVnSwitchOn(false);
                    }
                    NewNotificationActivity.this.mNotificationAccessDialog.dismiss();
                    NewNotificationActivity.this.mNotificationAccessDialog = null;
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NewNotificationActivity.AnonymousClass7 */

                public void onCancel(DialogInterface dialogInterface) {
                    if (NewNotificationActivity.this.mSwitchBar.isChecked() && !NotificationUtil.isAccessibilityON()) {
                        NewNotificationActivity.this.isVnSwitchOn(false);
                    }
                    NewNotificationActivity.this.mNotificationAccessDialog = null;
                }
            });
            this.mNotificationAccessDialog = builder.create();
            this.mNotificationAccessDialog.show();
            this.mNotificationAccessDialog.getButton(-2).setAllCaps(false);
            this.mNotificationAccessDialog.getButton(-1).setAllCaps(true);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void limitNotiDialog() {
        AlertDialog alertDialog = this.mlimitNotiDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mlimitNotiDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.limit_notification_dialog));
        builder.setMessage(getResources().getString(R.string.limit_notification_dialog_desc));
        builder.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.notification.NewNotificationActivity.AnonymousClass8 */

            public void onClick(DialogInterface dialogInterface, int i) {
                NewNotificationActivity.this.mlimitNotiDialog.dismiss();
            }
        });
        this.mlimitNotiDialog = builder.show();
    }

    private void showRetrieveDialog() {
        RetrieveProgressDialog retrieveProgressDialog = this.retrieveDialog;
        if (retrieveProgressDialog == null) {
            this.retrieveDialog = new RetrieveProgressDialog(this.mContext, getResources().getString(R.string.retrieve_dialog_desc));
            this.retrieveDialog.show();
        } else if (!retrieveProgressDialog.isShowing()) {
            this.retrieveDialog.show();
        } else {
            return;
        }
        checkComplete();
    }

    private void checkComplete() {
        new Thread(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.module.notification.NewNotificationActivity.AnonymousClass9 */

            public void run() {
                do {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (!NotificationManager.getInstance(NewNotificationActivity.this.mContext).isListCreated());
                if (NewNotificationActivity.this.retrieveHandler != null) {
                    NewNotificationActivity.this.retrieveHandler.sendMessage(1);
                }
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public final class RetrieveHandler extends Handler {
        private RetrieveHandler() {
        }

        public void sendMessage(int i) {
            sendMessage(Message.obtain(this, i));
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Log.d(NewNotificationActivity.TAG, "MSG_RETRIEVE_LIST_COMPLETE");
                if (!NewNotificationActivity.this.isFinishing() && !NewNotificationActivity.this.isDestroyed()) {
                    NewNotificationActivity.this.mPause = false;
                    NewNotificationActivity.this.initAppList();
                    if (NewNotificationActivity.this.retrieveDialog != null && NewNotificationActivity.this.retrieveDialog.isShowing()) {
                        Log.d(NewNotificationActivity.TAG, "retrieveDialog = isShowing() && not null -> dismiss");
                        NewNotificationActivity.this.retrieveDialog.dismiss();
                    }
                }
            } else if (i == 2) {
                Log.d(NewNotificationActivity.TAG, "MSG_RETRIEVE_LIST_UPDATE");
                NewNotificationActivity.this.mPause = false;
                NewNotificationActivity.this.initAppList();
            } else if (i == 3 && !NewNotificationActivity.this.isFinishing() && !NewNotificationActivity.this.isDestroyed()) {
                Log.d(NewNotificationActivity.TAG, "MSG_RETRIEVE_APPNAME_UPDATE");
                Preferences.putString(PreferenceKey.NOTIFICATION_LOCALE, NewNotificationActivity.this.getResources().getConfiguration().locale.toString());
                NotificationManager.getInstance(NewNotificationActivity.this.mContext).updateAppNameApp(NewNotificationActivity.this.mContext);
                NewNotificationActivity.this.mPause = false;
                NewNotificationActivity.this.initAppList();
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        SamsungAnalyticsUtil.sendEvent(SA.Event.UP_BUTTON, SA.Screen.NOTIFICATION);
        finish();
        return super.onSupportNavigateUp();
    }

    private int dpToPixel(int i) {
        return (i * getResources().getConfiguration().densityDpi) / 160;
    }
}
