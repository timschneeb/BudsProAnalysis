package com.samsung.accessory.hearablemgr.module.touchcontrols;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.pm.PackageInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.BaseContentProvider;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.util.SecurityUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutineConstants;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSetTouchAndHoldNoiseControls;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSetTouchpadOption;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;
import com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldOptionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import seccompat.android.util.Log;

public class TouchAndHoldActivity extends ConnectionActivity implements TouchAndHoldOptionAdapter.OnListItemSelectedInterface {
    public static final int DEFAULT_TOUCHPAD_OPTION = 2;
    public static final int DEFAULT_TOUCHPAD_OPTION_ORDER = 0;
    public static final int LEFT_OPTION = 0;
    public static final int OPTION_BIXBY = 1;
    public static final int OPTION_NOISE_REDUCTION = 2;
    public static final int OPTION_OTHERS_LEFT = 5;
    public static final int OPTION_OTHERS_RIGHT = 6;
    public static final int OPTION_SPOTIFY = 4;
    public static final int OPTION_VOLUME = 3;
    public static final int RIGHT_OPTION = 1;
    private static final String TAG = (Application.TAG_ + TouchAndHoldActivity.class.getSimpleName());
    private static ArrayList<HashMap<String, String>> appListForA2A = new ArrayList<>();
    private Button button;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    private int countOfCheckedModes;
    private boolean isCheckedAmbientSound;
    private boolean isCheckedAnc;
    private boolean isCheckedOff;
    private final BroadcastReceiver mAppToAppPackageReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldActivity.AnonymousClass2 */

        public void onReceive(Context context, Intent intent) {
            if (Application.getCoreService().getConnectionState() == 2) {
                if (intent.getData() == null || !intent.getData().getSchemeSpecificPart().equals(Application.getContext().getPackageName())) {
                    TouchAndHoldActivity.checkApp2App(context);
                }
            }
        }
    };
    private int mClickedSide;
    protected Context mContext;
    private EarBudsInfo mEarBudsInfo;
    protected TouchAndHoldOptionAdapter mLeftOptionAdapter = null;
    protected ArrayList<TouchAndHoldOptionData> mLeftOptionList;
    protected RecyclerView mLeftOptionListView;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldActivity.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            String str = TouchAndHoldActivity.TAG;
            Log.d(str, "onReceive() : " + intent.getAction());
            String action = intent.getAction();
            if (((action.hashCode() == -1854841232 && action.equals(CoreService.ACTION_MSG_ID_STATUS_UPDATED)) ? (char) 0 : 65535) == 0) {
                TouchAndHoldActivity.this.updateUI();
                TouchAndHoldActivity touchAndHoldActivity = TouchAndHoldActivity.this;
                touchAndHoldActivity.updateConnectedDevice(touchAndHoldActivity.prevBatteryL, TouchAndHoldActivity.this.mEarBudsInfo.batteryL, TouchAndHoldActivity.this.mLeftOptionList);
                TouchAndHoldActivity touchAndHoldActivity2 = TouchAndHoldActivity.this;
                touchAndHoldActivity2.updateConnectedDevice(touchAndHoldActivity2.prevBatteryR, TouchAndHoldActivity.this.mEarBudsInfo.batteryR, TouchAndHoldActivity.this.mRightOptionList);
                TouchAndHoldActivity touchAndHoldActivity3 = TouchAndHoldActivity.this;
                touchAndHoldActivity3.prevBatteryL = touchAndHoldActivity3.mEarBudsInfo.batteryL;
                TouchAndHoldActivity touchAndHoldActivity4 = TouchAndHoldActivity.this;
                touchAndHoldActivity4.prevBatteryR = touchAndHoldActivity4.mEarBudsInfo.batteryR;
            }
        }
    };
    protected TouchAndHoldOptionAdapter mRightOptionAdapter = null;
    protected ArrayList<TouchAndHoldOptionData> mRightOptionList;
    protected RecyclerView mRightOptionListView;
    private int prevBatteryL;
    private int prevBatteryR;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate()");
        super.onCreate(bundle);
        this.mContext = this;
        setContentView(R.layout.activity_touch_and_hold);
        this.mEarBudsInfo = Application.getCoreService().getEarBudsInfo();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        checkApp2App(this.mContext);
        initView();
        registerReceiver();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onStart() {
        super.onStart();
        updateUI();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity, com.samsung.accessory.hearablemgr.module.base.ConnectionActivity
    public void onResume() {
        super.onResume();
        SamsungAnalyticsUtil.sendPage(SA.Screen.TOUCH_AND_HOLD);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        unregisterReceiver(this.mReceiver);
        unregisterReceiver(this.mAppToAppPackageReceiver);
        super.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        SamsungAnalyticsUtil.sendEvent(SA.Event.UP_BUTTON, SA.Screen.TOUCH_AND_HOLD);
        finish();
        return super.onSupportNavigateUp();
    }

    private void initView() {
        this.mLeftOptionListView = (RecyclerView) findViewById(R.id.left_menu_list);
        this.mLeftOptionListView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mLeftOptionListView.setItemAnimator(null);
        this.mRightOptionListView = (RecyclerView) findViewById(R.id.right_menu_list);
        this.mRightOptionListView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRightOptionListView.setItemAnimator(null);
        this.mLeftOptionList = initOptionList(0);
        this.mRightOptionList = initOptionList(1);
        this.mLeftOptionAdapter = new TouchAndHoldOptionAdapter(this.mLeftOptionList, this);
        this.mRightOptionAdapter = new TouchAndHoldOptionAdapter(this.mRightOptionList, this);
        this.mLeftOptionListView.setAdapter(this.mLeftOptionAdapter);
        this.mRightOptionListView.setAdapter(this.mRightOptionAdapter);
        this.mLeftOptionAdapter.notifyDataSetChanged();
        this.mRightOptionAdapter.notifyDataSetChanged();
        this.prevBatteryL = this.mEarBudsInfo.batteryL;
        this.prevBatteryR = this.mEarBudsInfo.batteryR;
        String str = TAG;
        Log.d(str, "initView() - battery L : " + this.mEarBudsInfo.batteryL + ", battery R : " + this.mEarBudsInfo.batteryR);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateUI() {
        String str = TAG;
        Log.d(str, "updateUI() - battery L : " + this.mEarBudsInfo.batteryL + ", battery R : " + this.mEarBudsInfo.batteryR);
        if (this.mEarBudsInfo.batteryL > 0) {
            UiUtil.setEnabledWithChildren(this.mLeftOptionListView, true);
        } else {
            UiUtil.setEnabledWithChildren(this.mLeftOptionListView, false);
        }
        if (this.mEarBudsInfo.batteryR > 0) {
            UiUtil.setEnabledWithChildren(this.mRightOptionListView, true);
        } else {
            UiUtil.setEnabledWithChildren(this.mRightOptionListView, false);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateConnectedDevice(int i, int i2, ArrayList<TouchAndHoldOptionData> arrayList) {
        if (i <= 0 && i2 > 0) {
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                arrayList.get(i3).setIsConnected(true);
            }
        } else if (i > 0 && i2 <= 0) {
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                arrayList.get(i4).setIsConnected(false);
            }
        }
    }

    private ArrayList<TouchAndHoldOptionData> initOptionList(int i) {
        this.mClickedSide = i;
        ArrayList<TouchAndHoldOptionData> arrayList = new ArrayList<>();
        int i2 = this.mClickedSide == 0 ? this.mEarBudsInfo.batteryL : this.mEarBudsInfo.batteryR;
        arrayList.add(new TouchAndHoldOptionData(this.mContext.getString(R.string.noise_controls), false, this.mClickedSide, i2 > 0));
        Context context = this.mContext;
        arrayList.add(new TouchAndHoldOptionData(context.getString(setVoiceRecognitionText(context)), false, this.mClickedSide, i2 > 0));
        arrayList.add(new TouchAndHoldOptionData(this.mContext.getString(this.mClickedSide == 0 ? R.string.settings_touchpad_popup_txt3_left : R.string.settings_touchpad_popup_txt3_right), false, this.mClickedSide, i2 > 0));
        if (isReadySpotify()) {
            arrayList.add(new TouchAndHoldOptionData(this.mContext.getString(R.string.settings_touchpad_popup_txt4), false, this.mClickedSide, i2 > 0));
        }
        if (appListForA2A.size() > 0) {
            for (int i3 = 0; i3 < appListForA2A.size(); i3++) {
                Log.i(TAG, "initOptionList()::" + appListForA2A.get(i3).get(RoutineConstants.APP_TO_APP_KEY_MENU_NAME));
                Log.i(TAG, "initOptionList()::" + appListForA2A.get(i3).get(BaseContentProvider.PACKAGE_NAME));
                arrayList.add(new TouchAndHoldOptionData(appListForA2A.get(i3).get(RoutineConstants.APP_TO_APP_KEY_MENU_NAME), false, this.mClickedSide, i2 > 0));
            }
        }
        arrayList.get(getSelectedItem(i, this.mClickedSide == 0 ? this.mEarBudsInfo.touchpadOptionLeft : this.mEarBudsInfo.touchpadOptionRight) - 1).setSelectedItem(true);
        return arrayList;
    }

    private int getSelectedItemByOthersOption(int i) {
        int i2 = isReadySpotify() ? 5 : 4;
        String string = Preferences.getString(i == 0 ? PreferenceKey.LEFT_OTHER_OPTION_PACKAGE_NAME : PreferenceKey.RIGHT_OTHER_OPTION_PACKAGE_NAME, "", UhmFwUtil.getLastLaunchDeviceId());
        if (appListForA2A.size() > 0) {
            for (int i3 = 0; i3 < appListForA2A.size(); i3++) {
                if (string.equals(appListForA2A.get(i3).get(BaseContentProvider.PACKAGE_NAME)) && appListForA2A.get(i3).get(RoutineConstants.APP_TO_APP_KEY_MENU_NAME) != null) {
                    return i3 + i2;
                }
            }
        }
        if (i == 0) {
            this.mEarBudsInfo.touchpadOptionLeft = 2;
        } else {
            this.mEarBudsInfo.touchpadOptionRight = 2;
        }
        Application.getCoreService().sendSppMessage(new MsgSetTouchpadOption((byte) this.mEarBudsInfo.touchpadOptionLeft, (byte) this.mEarBudsInfo.touchpadOptionRight));
        return 2;
    }

    private static int setVoiceRecognitionText(Context context) {
        String str = TAG;
        Log.i(str, "setVoiceRecognitionText()::" + getPreferredAppInfo(context));
        return getPreferredAppInfo(context) == 1 ? R.string.settings_touchpad_popup_txt1_bixby : R.string.settings_touchpad_popup_txt1_normal;
    }

    private static int getPreferredAppInfo(Context context) {
        Log.d(TAG, "getPreferredAppInfo()");
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(new Intent("android.intent.action.VOICE_COMMAND"), 0);
        if (resolveActivity == null) {
            return 0;
        }
        String str = TAG;
        Log.d(str, "ResolveInfo : : info.activityInfo.packageName : " + resolveActivity.activityInfo.packageName);
        if (resolveActivity.activityInfo.packageName.equalsIgnoreCase("com.samsung.android.bixby.agent")) {
            return 1;
        }
        return 0;
    }

    public static void checkApp2App(Context context) {
        List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(new Intent(Util.SEND_PUI_EVENT), XDMWbxml.WBXML_EXT_0);
        appListForA2A = new ArrayList<>();
        String str = TAG;
        Log.d(str, "receivers.size: " + queryBroadcastReceivers.size());
        for (ResolveInfo resolveInfo : queryBroadcastReceivers) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            Bundle bundle = activityInfo.metaData;
            if (!(activityInfo == null || bundle == null)) {
                String str2 = activityInfo.packageName;
                String string = bundle.getString(RoutineConstants.APP_TO_APP_KEY_MENU_NAME);
                String string2 = bundle.getString("description");
                String string3 = bundle.getString("autho_key");
                if (!(string == null || string2 == null || !checkAuthorization(str2, string3))) {
                    String str3 = TAG;
                    Log.d(str3, "menuName : " + string);
                    String str4 = TAG;
                    Log.d(str4, "description :" + string2);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(BaseContentProvider.PACKAGE_NAME, str2);
                    hashMap.put(RoutineConstants.APP_TO_APP_KEY_MENU_NAME, string);
                    hashMap.put("description", string2);
                    appListForA2A.add(hashMap);
                }
            }
        }
    }

    private static boolean checkAuthorization(String str, String str2) {
        boolean z;
        try {
            z = SecurityUtil.verify(str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            z = false;
        }
        String str3 = TAG;
        Log.d(str3, "packageName : " + str);
        String str4 = TAG;
        Log.d(str4, "decryptValue : " + z);
        return z;
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_MSG_ID_STATUS_UPDATED);
        registerReceiver(this.mReceiver, intentFilter);
        registerAppToAppPackageReceiver();
    }

    private void registerAppToAppPackageReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        registerReceiver(this.mAppToAppPackageReceiver, intentFilter);
    }

    @Override // com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldOptionAdapter.OnListItemSelectedInterface
    public void onItemSelected(View view, int i, int i2) {
        String str = TAG;
        Log.d(str, "onItemSelected, position = " + i + ", viewType : " + i2);
        TouchAndHoldOptionAdapter.TouchAndHoldViewHolder touchAndHoldViewHolder = (TouchAndHoldOptionAdapter.TouchAndHoldViewHolder) this.mLeftOptionListView.findViewHolderForAdapterPosition(i);
        if (i2 == 0) {
            onDefaultLeftOptionClickListener(i + 1);
        } else {
            onDefaultRightOptionClickListener(i + 1);
        }
    }

    private void onDefaultLeftOptionClickListener(int i) {
        String str = TAG;
        Log.d(str, "onDefaultLeftOptionClickListener():: " + i);
        if (i == 1) {
            if (isSetVolumeUpDown()) {
                SetVolumeControl(0);
            }
            this.mEarBudsInfo.touchpadOptionLeft = 2;
            noiseControlsDialog();
        } else if (i == 2) {
            if (isSetVolumeUpDown()) {
                SetVolumeControl(0);
            }
            this.mEarBudsInfo.touchpadOptionLeft = 1;
        } else if (i == 3) {
            if (!isSetVolumeUpDown()) {
                toastVolumeAutomatically(0);
            }
            this.mEarBudsInfo.touchpadOptionLeft = 3;
        } else if (i != 4) {
            onLeftOptionClickListener(i);
        } else if (isReadySpotify()) {
            if (isSetVolumeUpDown()) {
                SetVolumeControl(0);
            }
            this.mEarBudsInfo.touchpadOptionLeft = 4;
        } else {
            onLeftOptionClickListener(i);
        }
        Application.getCoreService().sendSppMessage(new MsgSetTouchpadOption((byte) this.mEarBudsInfo.touchpadOptionLeft, (byte) this.mEarBudsInfo.touchpadOptionRight));
    }

    private void onLeftOptionClickListener(int i) {
        String str = TAG;
        Log.d(str, "onLeftOptionClickListener():: " + i);
        if (isSetVolumeUpDown()) {
            SetVolumeControl(0);
        }
        this.mEarBudsInfo.touchpadOptionLeft = 5;
        setOtherOptionsValue(0, i);
    }

    private void onDefaultRightOptionClickListener(int i) {
        String str = TAG;
        Log.d(str, "onDefaultRightOptionClickListener():: " + i);
        if (i == 1) {
            if (isSetVolumeUpDown()) {
                SetVolumeControl(1);
            }
            this.mEarBudsInfo.touchpadOptionRight = 2;
            noiseControlsDialog();
        } else if (i == 2) {
            if (isSetVolumeUpDown()) {
                SetVolumeControl(1);
            }
            this.mEarBudsInfo.touchpadOptionRight = 1;
        } else if (i == 3) {
            if (!isSetVolumeUpDown()) {
                toastVolumeAutomatically(1);
            }
            this.mEarBudsInfo.touchpadOptionRight = 3;
        } else if (i != 4) {
            onRightOptionClickListener(i);
        } else if (isReadySpotify()) {
            if (isSetVolumeUpDown()) {
                SetVolumeControl(1);
            }
            this.mEarBudsInfo.touchpadOptionRight = 4;
        } else {
            onRightOptionClickListener(i);
        }
        Application.getCoreService().sendSppMessage(new MsgSetTouchpadOption((byte) this.mEarBudsInfo.touchpadOptionLeft, (byte) this.mEarBudsInfo.touchpadOptionRight));
    }

    private void onRightOptionClickListener(int i) {
        String str = TAG;
        Log.d(str, "onRightOptionClickListener():: " + i);
        if (isSetVolumeUpDown()) {
            SetVolumeControl(1);
        }
        this.mEarBudsInfo.touchpadOptionRight = 6;
        setOtherOptionsValue(1, i);
    }

    private boolean isSetVolumeUpDown() {
        return this.mEarBudsInfo.touchpadOptionLeft == 3 && this.mEarBudsInfo.touchpadOptionRight == 3;
    }

    private void SetVolumeControl(int i) {
        int i2 = i == 0 ? 1 : 0;
        EarBudsInfo earBudsInfo = this.mEarBudsInfo;
        int selectedItem = getSelectedItem(i2, i == 0 ? earBudsInfo.touchpadOptionRight : earBudsInfo.touchpadOptionLeft);
        if (i == 0) {
            updateOptionValue(this.mRightOptionList, this.mRightOptionAdapter, selectedItem, 2);
            this.mEarBudsInfo.touchpadOptionRight = 2;
            toastRightSideFromVolumetoDefault();
            return;
        }
        updateOptionValue(this.mLeftOptionList, this.mLeftOptionAdapter, selectedItem, 2);
        this.mEarBudsInfo.touchpadOptionLeft = 2;
        toastLeftSideFromVolumetoDefault();
    }

    private void setOtherOptionsValue(int i, int i2) {
        if (appListForA2A.size() > 0) {
            int i3 = i2 - (isReadySpotify() ? 5 : 4);
            if (appListForA2A.get(i3).get(RoutineConstants.APP_TO_APP_KEY_MENU_NAME) != null) {
                saveOtherOptionPackageName(i, appListForA2A.get(i3).get(BaseContentProvider.PACKAGE_NAME));
            }
        } else if (i == 0) {
            this.mEarBudsInfo.touchpadOptionLeft = 2;
        } else {
            this.mEarBudsInfo.touchpadOptionRight = 2;
        }
    }

    private void saveOtherOptionPackageName(int i, String str) {
        Preferences.putString(i == 0 ? PreferenceKey.LEFT_OTHER_OPTION_PACKAGE_NAME : PreferenceKey.RIGHT_OTHER_OPTION_PACKAGE_NAME, str, UhmFwUtil.getLastLaunchDeviceId());
    }

    private void toastRightSideFromVolumetoDefault() {
        Context context = this.mContext;
        Toast.makeText(context, context.getString(R.string.settings_touchpad_option_toast_set_right_noise_controls), 1).show();
    }

    private void toastLeftSideFromVolumetoDefault() {
        Context context = this.mContext;
        Toast.makeText(context, context.getString(R.string.settings_touchpad_option_toast_set_left_noise_controls), 1).show();
    }

    private void toastVolumeAutomatically(int i) {
        int i2 = i == 0 ? 1 : 0;
        EarBudsInfo earBudsInfo = this.mEarBudsInfo;
        int selectedItem = getSelectedItem(i2, i == 0 ? earBudsInfo.touchpadOptionRight : earBudsInfo.touchpadOptionLeft);
        if (i == 0) {
            updateOptionValue(this.mRightOptionList, this.mRightOptionAdapter, selectedItem, 3);
            this.mEarBudsInfo.touchpadOptionRight = 3;
            Context context = this.mContext;
            Toast.makeText(context, context.getString(R.string.settings_touchpad_option_toast_set_right_volume_up), 1).show();
            return;
        }
        updateOptionValue(this.mLeftOptionList, this.mLeftOptionAdapter, selectedItem, 3);
        this.mEarBudsInfo.touchpadOptionLeft = 3;
        Context context2 = this.mContext;
        Toast.makeText(context2, context2.getString(R.string.settings_touchpad_option_toast_set_left_volume_down), 1).show();
    }

    private int getSelectedItem(int i, int i2) {
        if (i2 > 4) {
            return getSelectedItemByOthersOption(i);
        }
        if (i2 == 2) {
            return 1;
        }
        if (i2 == 1) {
            return 2;
        }
        return i2;
    }

    private void updateOptionValue(ArrayList<TouchAndHoldOptionData> arrayList, TouchAndHoldOptionAdapter touchAndHoldOptionAdapter, int i, int i2) {
        arrayList.get(i - 1).setSelectedItem(false);
        arrayList.get(i2 == 2 ? 0 : i2 - 1).setSelectedItem(true);
        touchAndHoldOptionAdapter.notifyItemRangeChanged(0, arrayList.size());
    }

    public static String getOptionsText(Context context, int i, int i2) {
        switch (i2) {
            case 1:
                return context.getString(setVoiceRecognitionText(context));
            case 2:
                return context.getString(R.string.noise_controls);
            case 3:
                return context.getString(i == 0 ? R.string.settings_touchpad_popup_txt3_left : R.string.settings_touchpad_popup_txt3_right);
            case 4:
                if (isReadySpotify()) {
                    return context.getString(R.string.settings_touchpad_popup_txt4);
                }
                if (i == 0) {
                    return setLeftDefaultOption(context);
                }
                return setRightDefaultOption(context);
            case 5:
                return getOthersOptionText(context, 0, appListForA2A);
            case 6:
                return getOthersOptionText(context, 1, appListForA2A);
            default:
                return i == 0 ? setLeftDefaultOption(context) : setRightDefaultOption(context);
        }
    }

    public static boolean isReadySpotify() {
        boolean z = false;
        Long l = null;
        try {
            PackageInfo packageInfo = Application.getContext().getPackageManager().getPackageInfo(Util.SPOTIFY, 0);
            if (packageInfo != null) {
                l = Long.valueOf(PackageInfoCompat.getLongVersionCode(packageInfo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (l != null && l.longValue() >= 54789462) {
            z = true;
        }
        String str = TAG;
        Log.d(str, "isReadySpotify() : " + z + " (" + l + ")");
        return z;
    }

    private static String setLeftDefaultOption(Context context) {
        Application.getCoreService().getEarBudsInfo().touchpadOptionLeft = 2;
        Application.getCoreService().sendSppMessage(new MsgSetTouchpadOption((byte) Application.getCoreService().getEarBudsInfo().touchpadOptionLeft, (byte) Application.getCoreService().getEarBudsInfo().touchpadOptionRight));
        return context.getString(R.string.settings_noise_reduction_title);
    }

    private static String setRightDefaultOption(Context context) {
        Application.getCoreService().getEarBudsInfo().touchpadOptionRight = 2;
        Application.getCoreService().sendSppMessage(new MsgSetTouchpadOption((byte) Application.getCoreService().getEarBudsInfo().touchpadOptionLeft, (byte) Application.getCoreService().getEarBudsInfo().touchpadOptionRight));
        return context.getString(R.string.settings_noise_reduction_title);
    }

    private static String getOthersOptionText(Context context, int i, ArrayList<HashMap<String, String>> arrayList) {
        String string = Preferences.getString(i == 0 ? PreferenceKey.LEFT_OTHER_OPTION_PACKAGE_NAME : PreferenceKey.RIGHT_OTHER_OPTION_PACKAGE_NAME, "", UhmFwUtil.getLastLaunchDeviceId());
        if (arrayList.size() > 0) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (string.equals(arrayList.get(i2).get(BaseContentProvider.PACKAGE_NAME)) && arrayList.get(i2).get(RoutineConstants.APP_TO_APP_KEY_MENU_NAME) != null) {
                    return arrayList.get(i2).get(RoutineConstants.APP_TO_APP_KEY_MENU_NAME);
                }
            }
        }
        if (i == 0) {
            return setLeftDefaultOption(context);
        }
        return setRightDefaultOption(context);
    }

    private void noiseControlsDialog() {
        this.countOfCheckedModes = (this.mEarBudsInfo.noiseControlsAnc ? 1 : 0) + (this.mEarBudsInfo.noiseControlsAmbient ? 1 : 0) + (this.mEarBudsInfo.noiseControlsOff ? 1 : 0);
        ScrollView scrollView = (ScrollView) View.inflate(this, R.layout.check_box_noise_controls, null);
        String string = Application.getContext().getString(R.string.noise_controls_option_dialog_desc);
        ((TextView) scrollView.findViewById(R.id.check_box_noise_controls_desc)).setText(string + " " + Application.getContext().getString(R.string.noise_controls_option_dialog_desc_1));
        this.checkBox1 = (CheckBox) scrollView.findViewById(R.id.checkbox_anc);
        this.checkBox1.setText(Application.getContext().getString(R.string.settings_noise_reduction_title));
        this.checkBox1.setChecked(this.mEarBudsInfo.noiseControlsAnc);
        this.isCheckedAnc = this.mEarBudsInfo.noiseControlsAnc;
        this.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldActivity.AnonymousClass3 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                Log.d(TouchAndHoldActivity.TAG, "checkBox1 : " + z);
                TouchAndHoldActivity.this.isCheckedAnc = z;
                TouchAndHoldActivity.this.countOfCheckedModes += TouchAndHoldActivity.this.isCheckedAnc ? 1 : -1;
                if (TouchAndHoldActivity.this.countOfCheckedModes < 2) {
                    if (!TouchAndHoldActivity.this.isCheckedAmbientSound) {
                        TouchAndHoldActivity.this.checkBox2.setChecked(true);
                    } else if (!TouchAndHoldActivity.this.isCheckedOff) {
                        TouchAndHoldActivity.this.checkBox3.setChecked(true);
                    }
                }
                if (TouchAndHoldActivity.this.countOfCheckedModes >= 2) {
                    TouchAndHoldActivity.this.button.setEnabled(true);
                } else {
                    TouchAndHoldActivity.this.button.setEnabled(false);
                }
                SamsungAnalyticsUtil.sendEvent(SA.Event.SET_NOISE_CONTROLS, SA.Screen.TOUCH_AND_HOLD, "a");
            }
        });
        this.checkBox2 = (CheckBox) scrollView.findViewById(R.id.checkbox_ambient_sound);
        this.checkBox2.setText(Application.getContext().getString(R.string.settings_ambient_sound));
        this.checkBox2.setChecked(this.mEarBudsInfo.noiseControlsAmbient);
        this.isCheckedAmbientSound = this.mEarBudsInfo.noiseControlsAmbient;
        this.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldActivity.AnonymousClass4 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                Log.d(TouchAndHoldActivity.TAG, "checkBox2 : " + z);
                TouchAndHoldActivity.this.isCheckedAmbientSound = z;
                TouchAndHoldActivity.this.countOfCheckedModes += TouchAndHoldActivity.this.isCheckedAmbientSound ? 1 : -1;
                if (TouchAndHoldActivity.this.countOfCheckedModes < 2) {
                    if (!TouchAndHoldActivity.this.isCheckedAnc) {
                        TouchAndHoldActivity.this.checkBox1.setChecked(true);
                    } else if (!TouchAndHoldActivity.this.isCheckedOff) {
                        TouchAndHoldActivity.this.checkBox3.setChecked(true);
                    }
                }
                if (TouchAndHoldActivity.this.countOfCheckedModes >= 2) {
                    TouchAndHoldActivity.this.button.setEnabled(true);
                } else {
                    TouchAndHoldActivity.this.button.setEnabled(false);
                }
                SamsungAnalyticsUtil.sendEvent(SA.Event.SET_NOISE_CONTROLS, SA.Screen.TOUCH_AND_HOLD, "b");
            }
        });
        this.checkBox3 = (CheckBox) scrollView.findViewById(R.id.checkbox_off);
        this.checkBox3.setText(Application.getContext().getString(R.string.touch_and_hold_option_off));
        this.checkBox3.setChecked(this.mEarBudsInfo.noiseControlsOff);
        this.isCheckedOff = this.mEarBudsInfo.noiseControlsOff;
        this.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldActivity.AnonymousClass5 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                Log.d(TouchAndHoldActivity.TAG, "checkBox3 : " + z);
                TouchAndHoldActivity.this.isCheckedOff = z;
                TouchAndHoldActivity.this.countOfCheckedModes += TouchAndHoldActivity.this.isCheckedOff ? 1 : -1;
                if (TouchAndHoldActivity.this.countOfCheckedModes < 2) {
                    if (!TouchAndHoldActivity.this.isCheckedAnc) {
                        TouchAndHoldActivity.this.checkBox1.setChecked(true);
                    } else if (!TouchAndHoldActivity.this.isCheckedAmbientSound) {
                        TouchAndHoldActivity.this.checkBox2.setChecked(true);
                    }
                }
                if (TouchAndHoldActivity.this.countOfCheckedModes >= 2) {
                    TouchAndHoldActivity.this.button.setEnabled(true);
                } else {
                    TouchAndHoldActivity.this.button.setEnabled(false);
                }
                SamsungAnalyticsUtil.sendEvent(SA.Event.SET_NOISE_CONTROLS, SA.Screen.TOUCH_AND_HOLD, "c");
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Application.getContext().getString(R.string.noise_controls));
        builder.setCancelable(false);
        builder.setView(scrollView);
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            /* class com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldActivity.AnonymousClass6 */

            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4) {
                    return false;
                }
                dialogInterface.dismiss();
                return true;
            }
        });
        builder.setNegativeButton(Application.getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldActivity.AnonymousClass7 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton(Application.getContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldActivity.AnonymousClass8 */

            public void onClick(DialogInterface dialogInterface, int i) {
                TouchAndHoldActivity.this.mEarBudsInfo.noiseControlsAnc = TouchAndHoldActivity.this.isCheckedAnc;
                TouchAndHoldActivity.this.mEarBudsInfo.noiseControlsAmbient = TouchAndHoldActivity.this.isCheckedAmbientSound;
                TouchAndHoldActivity.this.mEarBudsInfo.noiseControlsOff = TouchAndHoldActivity.this.isCheckedOff;
                Application.getCoreService().sendSppMessage(new MsgSetTouchAndHoldNoiseControls(TouchAndHoldActivity.this.mEarBudsInfo.noiseControlsAnc, TouchAndHoldActivity.this.mEarBudsInfo.noiseControlsAmbient, TouchAndHoldActivity.this.mEarBudsInfo.noiseControlsOff));
                TouchAndHoldActivity.this.mLeftOptionAdapter.notifyItemChanged(0);
                TouchAndHoldActivity.this.mRightOptionAdapter.notifyItemChanged(0);
                dialogInterface.cancel();
                SamsungAnalyticsUtil.setStatusString(SA.Status.SET_NOISE_CONTROLS, SamsungAnalyticsUtil.makeSetNoiseControlsDetail(TouchAndHoldActivity.this.isCheckedAnc, TouchAndHoldActivity.this.isCheckedAmbientSound, TouchAndHoldActivity.this.isCheckedOff));
            }
        });
        final AlertDialog create = builder.create();
        create.setOnShowListener(new DialogInterface.OnShowListener() {
            /* class com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldActivity.AnonymousClass9 */

            public void onShow(DialogInterface dialogInterface) {
                TouchAndHoldActivity.this.button = create.getButton(-1);
                if (TouchAndHoldActivity.this.button != null) {
                    String str = TouchAndHoldActivity.TAG;
                    Log.d(str, "countOfCheckedModes : " + TouchAndHoldActivity.this.countOfCheckedModes);
                    if (TouchAndHoldActivity.this.countOfCheckedModes >= 2) {
                        TouchAndHoldActivity.this.button.setEnabled(true);
                    } else {
                        TouchAndHoldActivity.this.button.setEnabled(false);
                    }
                }
            }
        });
        create.show();
    }
}
