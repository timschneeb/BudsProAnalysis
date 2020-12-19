package com.samsung.accessory.hearablemgr.module.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
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

public class NewManageNotificationActivity extends ConnectionActivity implements SearchView.OnQueryTextListener {
    protected static String TAG = "Attic_ManageNotificationsActivity";
    NotificationSpinnerAdapter filterAdapter;
    protected Context mContext;
    protected boolean mIsRefresh = false;
    private BroadcastReceiver mListUpdateReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.module.notification.NewManageNotificationActivity.AnonymousClass8 */

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            if (action.equals(NotificationConstants.ACTION_NOTIFICATION_LIST_UPDATE)) {
                Log.d(NewManageNotificationActivity.TAG, "ACTION_NOTIFICATION_LIST_UPDATE");
                if (NewManageNotificationActivity.this.retrieveHandler != null) {
                    NewManageNotificationActivity.this.retrieveHandler.sendMessage(2);
                }
            } else if (action.equals(NotificationConstants.ACTION_NOTIFICATION_SETTING_UPDATE)) {
                Log.d(NewManageNotificationActivity.TAG, "ACTION_NOTIFICATION_SETTING_UPDATE");
                if (!Preferences.getBoolean(PreferenceKey.NOTIFICATION_ENABLE, true)) {
                    NewManageNotificationActivity.this.finish();
                }
            }
        }
    };
    private TextView mNoApplicationTextView;
    protected NotificationListAdapter mNotificationAppAdapter = null;
    protected ArrayList<NotificationAppData> mNotificationAppList;
    protected RecyclerView mNotificationAppListView;
    protected boolean mPause = false;
    protected SwitchCompat mSelectAllCB;
    private int mSpinnerType = 0;
    private String rememberedKeyword;
    protected RetrieveProgressDialog retrieveDialog = null;
    protected RetrieveHandler retrieveHandler;
    private SearchView searchView;

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextSubmit(String str) {
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String str = TAG;
        Log.d(str, "onCreate()::savedInstanceState =" + bundle);
        this.mContext = this;
        this.mPause = false;
        onCreateSpecific();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NotificationConstants.ACTION_NOTIFICATION_LIST_UPDATE);
        intentFilter.addAction(NotificationConstants.ACTION_NOTIFICATION_SETTING_UPDATE);
        registerReceiver(this.mListUpdateReceiver, intentFilter);
        this.retrieveHandler = new RetrieveHandler();
        this.mSelectAllCB = (SwitchCompat) findViewById(R.id.all_switch);
        this.mNotificationAppListView = (RecyclerView) findViewById(R.id.notifiation_menu_list);
        this.mNotificationAppListView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mNotificationAppListView.setItemAnimator(null);
        if (Util.isSamsungDevice()) {
            this.mNotificationAppListView.seslSetGoToTopEnabled(true);
        }
        this.mNoApplicationTextView = (TextView) findViewById(R.id.no_application);
        initSpinner(getIntent().getIntExtra("position", 0));
        initAppList();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_notification_searchview, menu);
        this.searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        this.searchView.setQueryHint(getResources().getString(R.string.search_apps));
        this.searchView.setOnQueryTextListener(this);
        try {
            this.searchView.findViewById(R.id.search_plate).setPadding(0, 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.searchView.setOnSearchClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.notification.NewManageNotificationActivity.AnonymousClass1 */

            public void onClick(View view) {
                Log.d(NewManageNotificationActivity.TAG, "OnSearchClickListener()");
                SamsungAnalyticsUtil.sendEvent(SA.Event.SEARCH_APP, SA.Screen.NOTIFICATION_MANAGE);
            }
        });
        setSearchIcon();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        String str = TAG;
        Log.d(str, "onOptionsItemSelected() : " + menuItem.getItemId());
        return super.onOptionsItemSelected(menuItem);
    }

    private void setSearchIcon() {
        try {
            ImageView imageView = (ImageView) this.searchView.findViewById(R.id.search_button);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.search_find));
            imageView.clearColorFilter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextChange(String str) {
        if (str == null) {
            str = "";
        }
        this.rememberedKeyword = str;
        this.mNotificationAppAdapter.getFilter().filter(this.rememberedKeyword);
        return false;
    }

    public void initSpinner(int i) {
        String str = TAG;
        Log.d(str, "initSpinner : " + i);
        Spinner spinner = (Spinner) findViewById(R.id.filter_spinner);
        this.filterAdapter = new NotificationSpinnerAdapter(this);
        this.filterAdapter.setSelectedPosition(i);
        spinner.setAdapter((SpinnerAdapter) this.filterAdapter);
        spinner.setSelection(i, false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /* class com.samsung.accessory.hearablemgr.module.notification.NewManageNotificationActivity.AnonymousClass2 */

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                String str = NewManageNotificationActivity.TAG;
                Log.d(str, "onItemSelected : " + i);
                NewManageNotificationActivity.this.mSpinnerType = i;
                NewManageNotificationActivity.this.loadAppList(i, false);
                NewManageNotificationActivity.this.filterAdapter.setSelectedPosition(i);
                SamsungAnalyticsUtil.sendEvent(SA.Event.APP_NOTIFICATION_LIST, SA.Screen.NOTIFICATION_MANAGE, null, SamsungAnalyticsUtil.listIndexToDetail(i));
            }
        });
    }

    public void loadAppList(int i, boolean z) {
        if (this.mNotificationAppAdapter != null) {
            int i2 = 8;
            findViewById(R.id.all_apps_layout).setVisibility(8);
            boolean z2 = true;
            if (i == 0 || i == 1) {
                NotificationManager instance = NotificationManager.getInstance(this);
                if (i != 0) {
                    z2 = false;
                }
                this.mNotificationAppList = instance.getAllowedNotificationList(z2);
            } else if (i == 2) {
                findViewById(R.id.all_apps_layout).setVisibility(0);
                this.mNotificationAppList = NotificationManager.getInstance(this.mContext).getNotificationAppList();
            }
            this.mNoApplicationTextView.setText(R.string.no_applications);
            TextView textView = this.mNoApplicationTextView;
            if (this.mNotificationAppList.size() == 0) {
                i2 = 0;
            }
            textView.setVisibility(i2);
            this.mNotificationAppAdapter.setList(this.mNotificationAppList);
            String str = this.rememberedKeyword;
            if (str == null || str.equals("")) {
                this.mNotificationAppAdapter.notifyDataSetChanged();
            } else {
                this.mNotificationAppAdapter.getFilter().filter(this.rememberedKeyword);
            }
            if (z) {
                this.mNotificationAppListView.scrollToPosition(0);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreateSpecific() {
        setContentView(R.layout.activity_new_manage_notifications);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(this.mContext.getString(R.string.app_notification_to_read_aloud));
        getWindow().addFlags(16777216);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
        if (!TextUtils.isEmpty(this.rememberedKeyword)) {
            onQueryTextChange(this.rememberedKeyword);
        }
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
            this.mNotificationAppAdapter = new NotificationListAdapter(this.mContext, this.mNotificationAppList, new NotificationListAdapter.ICheckedNotificationApp() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NewManageNotificationActivity.AnonymousClass3 */

                @Override // com.samsung.accessory.hearablemgr.module.notification.NotificationListAdapter.ICheckedNotificationApp
                public void setCheckedApp(int i) {
                    Log.d(NewManageNotificationActivity.TAG, "updateTitleCount(mNotificationTitle)");
                    NewManageNotificationActivity newManageNotificationActivity = NewManageNotificationActivity.this;
                    newManageNotificationActivity.refreshSelectAllCheckBox(newManageNotificationActivity.mNotificationAppList.get(i));
                }

                @Override // com.samsung.accessory.hearablemgr.module.notification.NotificationListAdapter.ICheckedNotificationApp
                public void onClickAppSettingDetail(NotificationAppData notificationAppData) {
                    Log.d(NewManageNotificationActivity.TAG, "onClickAppSettingDetail()");
                    if (!NotificationUtil.isSupportSpeakCallerName() || !notificationAppData.getPackageName().equals(NotificationConstants.INCOMING_CALL_PACKAGENAME)) {
                        SamsungAnalyticsUtil.sendEvent(SA.Event.NOTIFICATION_APPS, SA.Screen.NOTIFICATION_MANAGE);
                        Intent intent = new Intent(NewManageNotificationActivity.this, NotificationDetailActivity.class);
                        intent.putExtra("appPackageName", notificationAppData.getPackageName());
                        NewManageNotificationActivity.this.startActivityForResult(intent, 123);
                        return;
                    }
                    NotificationUtil.setNotiEnabledApplication(notificationAppData.getPackageName(), !notificationAppData.isEnable() ? NotificationConstants.NOTIFICATION_TYPE_ON : NotificationConstants.NOTIFICATION_TYPE_OFF);
                    NotificationUtil.setSpeakCallerName(!notificationAppData.isEnable());
                    NewManageNotificationActivity.this.mNotificationAppAdapter.notifyDataSetChanged();
                }

                @Override // com.samsung.accessory.hearablemgr.module.notification.NotificationListAdapter.ICheckedNotificationApp
                public void onChangeSearchList(int i) {
                    Log.d(NewManageNotificationActivity.TAG, "onChangeSearchList()");
                    if (i == 0) {
                        if (NewManageNotificationActivity.this.rememberedKeyword == null || !NewManageNotificationActivity.this.rememberedKeyword.equals("")) {
                            NewManageNotificationActivity.this.mNoApplicationTextView.setText(R.string.no_result_found);
                        } else {
                            NewManageNotificationActivity.this.mNoApplicationTextView.setText(R.string.no_applications);
                        }
                        NewManageNotificationActivity.this.mNoApplicationTextView.setVisibility(0);
                        return;
                    }
                    NewManageNotificationActivity.this.mNoApplicationTextView.setVisibility(8);
                }
            });
            this.mNotificationAppAdapter.setHasStableIds(true);
            this.mNotificationAppListView.setAdapter(this.mNotificationAppAdapter);
            loadAppList(this.filterAdapter.getSelectedPosition(), true);
            String string = Preferences.getString(PreferenceKey.NOTIFICATION_LOCALE, null);
            String locale = this.mContext.getResources().getConfiguration().locale.toString();
            String str = TAG;
            Log.d(str, "Util.getConfigChange(getActivity()):" + string + ":");
            String str2 = TAG;
            Log.d(str2, "mCurLocale:" + locale + ":");
            if (!locale.equals(string) && this.retrieveHandler != null) {
                Log.d(TAG, "handle MSG_RETRIEVE_APPNAME_UPDATE");
                this.retrieveHandler.sendMessage(3);
            }
            this.mNotificationAppAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                /* class com.samsung.accessory.hearablemgr.module.notification.NewManageNotificationActivity.AnonymousClass4 */

                @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public void onChanged() {
                    super.onChanged();
                    NewManageNotificationActivity newManageNotificationActivity = NewManageNotificationActivity.this;
                    newManageNotificationActivity.mNotificationAppList = newManageNotificationActivity.mNotificationAppAdapter.getList();
                    if (NewManageNotificationActivity.this.mSpinnerType == 2) {
                        NewManageNotificationActivity.this.refeshList();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void refeshList() {
        NotificationListAdapter notificationListAdapter = this.mNotificationAppAdapter;
        if (notificationListAdapter != null) {
            boolean z = true;
            this.mIsRefresh = true;
            if (notificationListAdapter.getCheckedCount() != this.mNotificationAppAdapter.getCount()) {
                z = false;
            }
            setSelectAllCBChecked(z);
            this.mIsRefresh = false;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void refreshSelectAllCheckBox(NotificationAppData notificationAppData) {
        Log.d(TAG, "refreshSelectAllCheckBox()  " + notificationAppData.getPackageName() + " " + notificationAppData.isEnable());
        notificationAppData.setEnable(notificationAppData.isEnable());
        boolean z = true;
        this.mIsRefresh = true;
        if (this.mNotificationAppAdapter.getCheckedCount() != this.mNotificationAppAdapter.getCount()) {
            z = false;
        }
        setSelectAllCBChecked(z);
        NotificationUtil.setNotiEnabledApplication(notificationAppData.getPackageName(), notificationAppData.isEnable() ? NotificationConstants.NOTIFICATION_TYPE_ON : NotificationConstants.NOTIFICATION_TYPE_OFF);
        if (notificationAppData.getPackageName().equals(NotificationConstants.INCOMING_CALL_PACKAGENAME)) {
            NotificationUtil.setSpeakCallerName(notificationAppData.isEnable() ? 1 : 0);
        }
        this.mIsRefresh = false;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onActivityResult(int i, int i2, Intent intent) {
        NotificationListAdapter notificationListAdapter;
        super.onActivityResult(i, i2, intent);
        String str = TAG;
        Log.d(str, "onActivityResult " + i + " " + i2);
        if (i == 123 && (notificationListAdapter = this.mNotificationAppAdapter) != null) {
            notificationListAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        SamsungAnalyticsUtil.sendPage(SA.Screen.NOTIFICATION_MANAGE);
        if (this.mPause && NotificationUtil.isSupportSpeakCallerName()) {
            NotificationUtil.setNotiEnabledApplication(NotificationConstants.INCOMING_CALL_PACKAGENAME, NotificationUtil.getSpeakCallerName() ? NotificationConstants.NOTIFICATION_TYPE_ON : NotificationConstants.NOTIFICATION_TYPE_OFF);
            NotificationManager.getInstance(this.mContext).setCheckIncomingCallStatus(NotificationUtil.isAppNotificationEnabled(NotificationConstants.INCOMING_CALL_PACKAGENAME));
            if (!(this.filterAdapter == null || this.mNotificationAppAdapter == null || !TextUtils.isEmpty(this.rememberedKeyword))) {
                loadAppList(this.filterAdapter.getSelectedPosition(), false);
            }
        }
        this.mSelectAllCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.notification.NewManageNotificationActivity.AnonymousClass5 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                String str = NewManageNotificationActivity.TAG;
                Log.d(str, "onResume()::select all click." + compoundButton.getTag());
                if (!NewManageNotificationActivity.this.mIsRefresh) {
                    for (int i = 0; i < NewManageNotificationActivity.this.mNotificationAppList.size(); i++) {
                        NewManageNotificationActivity.this.mNotificationAppList.get(i).setEnable(z);
                        String packageName = NewManageNotificationActivity.this.mNotificationAppList.get(i).getPackageName();
                        Preferences.putString(NotificationConstants.PREFERENCE_VN_APP_ENABLE + packageName, z ? NotificationConstants.NOTIFICATION_TYPE_ON : NotificationConstants.NOTIFICATION_TYPE_OFF, UhmFwUtil.getLastLaunchDeviceId());
                        if (packageName.equals(NotificationConstants.INCOMING_CALL_PACKAGENAME)) {
                            NotificationUtil.setSpeakCallerName(z ? 1 : 0);
                        }
                    }
                    NewManageNotificationActivity.this.mNotificationAppAdapter.notifyDataSetChanged();
                }
            }
        });
        findViewById(R.id.all_switch_layout).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.notification.NewManageNotificationActivity.AnonymousClass6 */

            public void onClick(View view) {
                NewManageNotificationActivity newManageNotificationActivity = NewManageNotificationActivity.this;
                newManageNotificationActivity.setSelectAllCBChecked(!newManageNotificationActivity.mSelectAllCB.isChecked());
            }
        });
        if (Util.isTalkBackEnabled()) {
            this.mSelectAllCB.setFocusable(false);
            this.mSelectAllCB.setClickable(false);
        } else {
            this.mSelectAllCB.setFocusable(true);
            this.mSelectAllCB.setClickable(true);
        }
        this.mPause = false;
    }

    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onPause() {
        CharSequence charSequence;
        super.onPause();
        Log.d(TAG, "onPause()");
        this.mPause = true;
        SearchView searchView2 = this.searchView;
        if (searchView2 != null) {
            charSequence = searchView2.getQuery();
        } else {
            charSequence = "";
        }
        this.rememberedKeyword = "" + ((Object) charSequence);
        NotificationManager.updateSAStatusNotificationAppToSelect();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onStop() {
        super.onStop();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        unregisterReceiver(this.mListUpdateReceiver);
        RetrieveHandler retrieveHandler2 = this.retrieveHandler;
        if (retrieveHandler2 != null) {
            retrieveHandler2.removeCallbacksAndMessages(null);
        }
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
            /* class com.samsung.accessory.hearablemgr.module.notification.NewManageNotificationActivity.AnonymousClass7 */

            public void run() {
                do {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (!NotificationManager.getInstance(NewManageNotificationActivity.this.mContext).isListCreated());
                if (NewManageNotificationActivity.this.retrieveHandler != null) {
                    NewManageNotificationActivity.this.retrieveHandler.sendMessage(1);
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
                Log.d(NewManageNotificationActivity.TAG, "MSG_RETRIEVE_LIST_COMPLETE");
                if (!NewManageNotificationActivity.this.isFinishing() && !NewManageNotificationActivity.this.isDestroyed()) {
                    NewManageNotificationActivity newManageNotificationActivity = NewManageNotificationActivity.this;
                    newManageNotificationActivity.mPause = false;
                    newManageNotificationActivity.initAppList();
                    if (NewManageNotificationActivity.this.retrieveDialog != null && NewManageNotificationActivity.this.retrieveDialog.isShowing()) {
                        Log.d(NewManageNotificationActivity.TAG, "retrieveDialog = isShowing() && not null -> dismiss");
                        NewManageNotificationActivity.this.retrieveDialog.dismiss();
                    }
                }
            } else if (i == 2) {
                Log.d(NewManageNotificationActivity.TAG, "MSG_RETRIEVE_LIST_UPDATE");
                NewManageNotificationActivity newManageNotificationActivity2 = NewManageNotificationActivity.this;
                newManageNotificationActivity2.mPause = false;
                newManageNotificationActivity2.initAppList();
            } else if (i == 3 && !NewManageNotificationActivity.this.isFinishing() && !NewManageNotificationActivity.this.isDestroyed()) {
                Log.d(NewManageNotificationActivity.TAG, "MSG_RETRIEVE_APPNAME_UPDATE");
                Preferences.putString(PreferenceKey.NOTIFICATION_LOCALE, NewManageNotificationActivity.this.getResources().getConfiguration().locale.toString());
                NotificationManager.getInstance(NewManageNotificationActivity.this.mContext).updateAppNameApp(NewManageNotificationActivity.this.mContext);
                NewManageNotificationActivity newManageNotificationActivity3 = NewManageNotificationActivity.this;
                newManageNotificationActivity3.mPause = false;
                newManageNotificationActivity3.initAppList();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setSelectAllCBChecked(boolean z) {
        if (this.mSpinnerType == 2 && this.mSelectAllCB != null) {
            NotificationListAdapter notificationListAdapter = this.mNotificationAppAdapter;
            if (notificationListAdapter == null || notificationListAdapter.getCount() != 0) {
                this.mSelectAllCB.setChecked(z);
                SamsungAnalyticsUtil.setStatusInt(SA.Status.ALL_APPS, z ? 1 : 0);
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        if (!this.searchView.isIconified()) {
            this.searchView.onActionViewCollapsed();
        } else {
            SamsungAnalyticsUtil.sendEvent(SA.Event.UP_BUTTON, SA.Screen.NOTIFICATION_MANAGE);
            finish();
        }
        return super.onSupportNavigateUp();
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        if (!this.searchView.isIconified()) {
            this.searchView.onActionViewCollapsed();
            return true;
        }
        finish();
        return true;
    }
}
