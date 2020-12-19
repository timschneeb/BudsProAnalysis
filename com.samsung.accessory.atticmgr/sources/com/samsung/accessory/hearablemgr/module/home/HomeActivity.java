package com.samsung.accessory.hearablemgr.module.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.BuildConfig;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.soagent.SOAgentServiceUtil;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import com.samsung.accessory.hearablemgr.common.util.ResponseCallback;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.bluetooth.BluetoothManagerEnabler;
import com.samsung.accessory.hearablemgr.core.bluetooth.ManufacturerDataUpdater;
import com.samsung.accessory.hearablemgr.core.fota.util.FotaUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase;
import com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity;
import com.samsung.accessory.hearablemgr.module.home.card.Card;
import com.samsung.accessory.hearablemgr.module.home.card.CardSmartThingsFind;
import com.samsung.accessory.hearablemgr.module.home.drawer.Drawer;
import com.samsung.android.fotaagent.update.UpdateInterface;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import seccompat.android.util.Log;

public class HomeActivity extends PermissionCheckActivity implements Card.CardOwnerActivity, Drawer.DrawerOwnerActivity {
    private static final int DAYS_30 = 30;
    private static final int DRAWER_GRAVITY = 8388611;
    public static final String EXTRA_AUTO_CONNECT = "HomeActivity.extra.AUTO_CONNECT";
    public static final String EXTRA_FROM_SETUPWIZARD = "HomeActivity.extra.FROM_SETUPWIZARD";
    private static final int MAX_CANCEL_COUNT = 3;
    private static final int MAX_SHOWING_COUNT = 5;
    private static final String TAG = "Attic_HomeActivity";
    private final int COLOR_BLACK = Application.getContext().getResources().getColor(R.color.color_black);
    protected ArrayList<Card> mCards;
    private Drawer mDrawer;
    private DrawerLayout mDrawerLayout;
    private Dialog mForegroundDialog;
    private View mGradationBg;
    private View mLayoutHome;
    private ProgressBar mProgressBar;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass5 */

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        public void onReceive(Context context, Intent intent) {
            char c;
            Log.d(HomeActivity.TAG, "onReceive() : " + intent.getAction());
            String action = intent.getAction();
            switch (action.hashCode()) {
                case -1854841232:
                    if (action.equals(CoreService.ACTION_MSG_ID_STATUS_UPDATED)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -1809423998:
                    if (action.equals(CoreService.ACTION_MSG_ID_LOCK_TOUCHPAD_UPDATED)) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -1658427642:
                    if (action.equals(CoreService.ACTION_MSG_ID_DEBUG_SERIAL_NUMBER)) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case -1577670709:
                    if (action.equals(CoreService.ACTION_MSG_ID_NOISE_CONTROLS_UPDATE)) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -1553437513:
                    if (action.equals(CoreService.ACTION_DEVICE_EXTENDED_STATUS_READY)) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1542292191:
                    if (action.equals(CoreService.ACTION_DEVICE_DISCONNECTING)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1129240014:
                    if (action.equals(CoreService.ACTION_MSG_ID_EXTENDED_STATUS_UPDATED)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -867028342:
                    if (action.equals(CoreService.ACTION_MSG_ID_FOTA_EMERGENCY)) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -654353038:
                    if (action.equals(CoreService.ACTION_MSG_ID_CALL_STATE)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -256433115:
                    if (action.equals(CoreService.ACTION_MSG_ID_EQUALIZER_TYPE_UPDATED)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -117388702:
                    if (action.equals(FotaUtil.ACTION_FOTA_PROGRESS_COPY_RESULT)) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 470334527:
                    if (action.equals(CoreService.ACTION_MSG_ID_AMBIENT_VOLUME_UPDATED)) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 545610013:
                    if (action.equals(CoreService.ACTION_DEVICE_CONNECTING)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 952856008:
                    if (action.equals(CoreService.ACTION_MSG_ID_NOISE_REDUCTION_UPDATED)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 1215575359:
                    if (action.equals(CoreService.ACTION_MSG_ID_DEBUG_GET_ALL_DATA)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1335721824:
                    if (action.equals(CoreService.ACTION_DEVICE_DISCONNECTED)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1403073508:
                    if (action.equals(CoreService.ACTION_DEVICE_CONNECTED)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1522137639:
                    if (action.equals(CoreService.ACTION_MSG_FOTA_CHECK_UPDATE)) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 1632152076:
                    if (action.equals(CoreService.ACTION_MSG_ID_FOTA2_EMERGENCY)) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 1795645538:
                    if (action.equals(CoreService.ACTION_MSG_ID_AMBIENT_SOUND_UPDATED)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1882440235:
                    if (action.equals(CoreService.ACTION_MSG_ID_SCO_STATE_UPDATED)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    HomeActivity.this.mProgressBar.setVisibility(0);
                    HomeActivity.this.updateUI();
                    return;
                case 1:
                    HomeActivity.this.mProgressBar.setVisibility(0);
                    HomeActivity.this.hideTipCard();
                    return;
                case 2:
                case 3:
                case 4:
                    HomeActivity.this.updateTipCard();
                    HomeActivity.this.mProgressBar.setVisibility(8);
                    HomeActivity.this.updateUI();
                    return;
                case 5:
                case 6:
                case 7:
                case '\b':
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                case 14:
                case 15:
                    HomeActivity.this.updateUI();
                    return;
                case 16:
                    HomeActivity.this.checkSellOutInfoUpdate();
                    return;
                case 17:
                    int intExtra = intent.getIntExtra(FotaUtil.FOTA_RESULT, 0);
                    Log.d(HomeActivity.TAG, "ACTION_FOTA_PROGRESS_COPY_RESULT : " + intExtra);
                    if (intExtra == 1) {
                        Log.d(HomeActivity.TAG, "ACTION_FOTA_UPDATE_DONE");
                        FotaUtil.setLastDoneTime(Calendar.getInstance().getTimeInMillis());
                        HomeActivity.this.dialogFOTADone();
                        return;
                    }
                    return;
                case 18:
                case 19:
                    Log.d(HomeActivity.TAG, "ACTION_MSG_ID_FOTA_EMERGENCY");
                    HomeActivity.this.emergencyFotaDialog();
                    return;
                case 20:
                    HomeActivity.this.updateTipCard();
                    return;
                default:
                    return;
            }
        }
    };
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private ScrollableLinearLayoutManager mRecyclerViewLayoutManager;
    private View mTextBadgeOnDrawerButton;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.d(TAG, "onCreate() : versionCode=2020121151");
        super.onCreate(bundle);
        setContentView(R.layout.activity_home);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.mTextBadgeOnDrawerButton = findViewById(R.id.text_badge_notification);
        findViewById(R.id.image_drawer_button).setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass1 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                HomeActivity.this.openDrawer();
            }
        });
        setTitle();
        this.mGradationBg = findViewById(R.id.view_gradation_bg);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        this.mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass2 */

            @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerSlide(View view, float f) {
                Log.v(HomeActivity.TAG, "onDrawerSlide() : " + f);
                float width = ((float) view.getWidth()) * f;
                View view2 = HomeActivity.this.mLayoutHome;
                if (UiUtil.isLayoutRtl(HomeActivity.this.mLayoutHome)) {
                    width = -width;
                }
                view2.setTranslationX(width);
            }

            @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerOpened(View view) {
                Log.d(HomeActivity.TAG, "onDrawerOpened()");
            }

            @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerClosed(View view) {
                Log.d(HomeActivity.TAG, "onDrawerClosed()");
            }

            @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerStateChanged(int i) {
                Log.v(HomeActivity.TAG, "onDrawerStateChanged() : " + i);
            }
        });
        this.mLayoutHome = findViewById(R.id.layout_home);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.mCards = HomeActivityModel.createCardList(this);
        this.mRecyclerViewAdapter = new RecyclerViewAdapter(this.mCards);
        this.mRecyclerViewLayoutManager = new ScrollableLinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(this.mRecyclerViewLayoutManager);
        this.mRecyclerView.setAdapter(this.mRecyclerViewAdapter);
        this.mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.card_between_space)));
        this.mDrawer = new Drawer(this);
        this.mProgressBar = (ProgressBar) findViewById(R.id.progress_connecting);
        registerReceiver();
        handleIntent(getIntent());
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(300);
        defaultItemAnimator.setRemoveDuration(300);
        this.mRecyclerView.setItemAnimator(defaultItemAnimator);
        checkSellOutInfoUpdate();
        updateTipCard();
        HomeActivityModel.checkFotaStatus(this);
        HomeActivityModel.initCardShowTime();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent == null) {
            Log.e(TAG, "handleIntent() : intent == null");
        } else if ((intent.getBooleanExtra(EXTRA_AUTO_CONNECT, false) || !Preferences.getBoolean(PreferenceKey.HOME_DISCONNECTED_BY_USER, false)) && !Application.getCoreService().isConnected(UhmFwUtil.getLastLaunchDeviceId())) {
            Log.d(TAG, "handleIntent() : AUTO_CONNECT");
            new Handler().post(new Runnable() {
                /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass3 */

                public void run() {
                    HomeActivity.this.requestConnectToDevice();
                }
            });
        }
    }

    private void setTitle() {
        String deviceName = BluetoothUtil.getDeviceName(UhmFwUtil.getLastLaunchDeviceId());
        String aliasName = BluetoothUtil.getAliasName(UhmFwUtil.getLastLaunchDeviceId());
        Log.i(TAG, "originalBTName=" + deviceName + ", aliasName=" + aliasName);
        if (aliasName == null || aliasName.equals(deviceName) || aliasName.equals("Galaxy Buds Pro")) {
            findViewById(R.id.image_app_name).setVisibility(0);
            findViewById(R.id.text_app_name).setVisibility(8);
            findViewById(R.id.focus_view_title).setContentDescription(getString(R.string.app_name));
            return;
        }
        findViewById(R.id.image_app_name).setVisibility(8);
        findViewById(R.id.text_app_name).setVisibility(0);
        ((AppCompatTextView) findViewById(R.id.text_app_name)).setText(aliasName);
        findViewById(R.id.focus_view_title).setContentDescription(aliasName);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateTipCard() {
        if (Application.getCoreService().isConnected()) {
            insertTipCard();
        } else {
            hideTipCard();
        }
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card.CardOwnerActivity
    public void removeTipCard() {
        Log.d(TAG, "removeCard()::" + this.mCards.get(1).getType());
        this.mRecyclerView.getRecycledViewPool().clear();
        this.mRecyclerViewAdapter.removeItem(1);
        updateTipCard();
    }

    private int getCurTipCard() {
        int type = this.mCards.get(1).getType();
        switch (type) {
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
                return type;
            default:
                return 100;
        }
    }

    public void insertTipCard() {
        Card newTipsCardInstance;
        int selectNextTipCard = HomeActivityModel.selectNextTipCard(this);
        Log.d(TAG, "insertTipCard():: nextCard=" + selectNextTipCard + ", curCard=" + getCurTipCard());
        if (isShowingTipCard() && selectNextTipCard != getCurTipCard()) {
            hideTipCard();
        }
        if (!isShowingTipCard() && (newTipsCardInstance = HomeActivityModel.newTipsCardInstance(this, selectNextTipCard)) != null) {
            this.mRecyclerViewAdapter.insertItem(1, newTipsCardInstance);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void hideTipCard() {
        Log.d(TAG, "hideTipCard() : " + getCurTipCard());
        if (isShowingTipCard()) {
            this.mRecyclerViewAdapter.removeItem(1);
        }
    }

    private boolean isShowingTipCard() {
        return getCurTipCard() != 100;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
        SamsungAnalyticsUtil.sendPage(SA.Screen.HOME);
        checkShowUpdateBadge();
        updateTipCard();
        updateUI();
        setTitle();
        this.mDrawer.updateProfileImage();
        Application.getUhmDatabase().postUpdateDeviceList();
        Application.getUhmDatabase().postCleanUpUnpairedDevices();
        Application.getUhmDatabase().postUpdatePluginDeviceName();
        Application.getUhmDatabase().postUpdateDeviceConnectionState();
        Application.getUhmDatabase().postUpdateLastLaunchDevice();
        ManufacturerDataUpdater.postUpdateManufacturerData();
        closeDrawerDirectly();
        HomeActivityModel.onResumeForFota(this);
        if (FotaUtil.getEmergencyFOTAIsRunning()) {
            emergencyFotaDialog();
        }
        if (CardSmartThingsFind.getShowAgain()) {
            SmartThingsUtil.getFmeServiceReady(true);
        }
        if (Application.getUhmDatabase().getDevice(UhmFwUtil.getLastLaunchDeviceId()) == null) {
            Log.e(TAG, "onResume() : device doesn't exist in tUHM DB");
            new Handler().post(new Runnable() {
                /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass4 */

                public void run() {
                    UhmFwUtil.startNewDeviceActivity(HomeActivity.this, false);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        unregisterReceiver(this.mReceiver);
        this.mDrawer.destroy();
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateUI() {
        Iterator<Card> it = this.mCards.iterator();
        while (it.hasNext()) {
            it.next().updateUI();
        }
        updateDrawerBadge();
        findViewById(R.id.layout_drawer).setImportantForAccessibility(2);
        findViewById(R.id.layout_coordinator).setImportantForAccessibility(2);
    }

    private void updateBadgeOnDrawerButton() {
        int i = 0;
        boolean z = Preferences.getBoolean(PreferenceKey.EXISTED_NEW_VERSION_PLUGIN, false, UhmFwUtil.getLastLaunchDeviceId());
        View view = this.mTextBadgeOnDrawerButton;
        if (!z) {
            i = 8;
        }
        view.setVisibility(i);
    }

    private void updateDrawerBadge() {
        Log.d(TAG, "updateDrawerBadge()");
        updateBadgeOnDrawerButton();
        this.mDrawer.updateGalaxyWearableBadge();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_MSG_ID_STATUS_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_EXTENDED_STATUS_UPDATED);
        intentFilter.addAction(CoreService.ACTION_DEVICE_EXTENDED_STATUS_READY);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_DEBUG_GET_ALL_DATA);
        intentFilter.addAction(CoreService.ACTION_DEVICE_CONNECTING);
        intentFilter.addAction(CoreService.ACTION_DEVICE_CONNECTED);
        intentFilter.addAction(CoreService.ACTION_DEVICE_DISCONNECTING);
        intentFilter.addAction(CoreService.ACTION_DEVICE_DISCONNECTED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_DEBUG_SERIAL_NUMBER);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_EQUALIZER_TYPE_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_AMBIENT_SOUND_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_CALL_STATE);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_SCO_STATE_UPDATED);
        intentFilter.addAction(FotaUtil.ACTION_FOTA_PROGRESS_COPY_RESULT);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_FOTA_EMERGENCY);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_FOTA2_EMERGENCY);
        intentFilter.addAction(CoreService.ACTION_MSG_FOTA_CHECK_UPDATE);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_NOISE_REDUCTION_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_LOCK_TOUCHPAD_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_AMBIENT_VOLUME_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_NOISE_CONTROLS_UPDATE);
        registerReceiver(this.mReceiver, intentFilter);
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.drawer.Drawer.DrawerOwnerActivity, com.samsung.accessory.hearablemgr.module.home.card.Card.CardOwnerActivity
    public void requestConnectToDevice() {
        Log.d(TAG, "requestConnectToDevice()");
        if (Util.isEmulator()) {
            Application.getCoreService().emulateConnected();
        } else if (BluetoothUtil.getAdapter() == null) {
            Log.e(TAG, "requestConnectToDevice() : BluetoothUtil.getAdapter() == null");
        } else if (Application.getBluetoothManager().isReady()) {
            Application.getCoreService().connectToDevice();
        } else if (!BluetoothUtil.isAdapterOn()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.turn_on_bluetooth_q);
            builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass6 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    new BluetoothManagerEnabler(new ResponseCallback() {
                        /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass6.AnonymousClass1 */

                        @Override // com.samsung.accessory.hearablemgr.common.util.ResponseCallback
                        public void onResponse(String str) {
                            if (str == null) {
                                Application.getCoreService().connectToDevice();
                                return;
                            }
                            HomeActivity homeActivity = HomeActivity.this;
                            Toast.makeText(homeActivity, "Error: " + str, 1).show();
                        }
                    }).execute();
                }
            });
            builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass7 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        } else {
            Log.e(TAG, "requestConnectToDevice() : BluetoothManager is NOT ready");
            Application.getBluetoothManager().rebindProfiles();
        }
    }

    @Override // androidx.activity.ComponentActivity
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        if (isDrawerOpen()) {
            closeDrawer(null);
        } else {
            super.onBackPressed();
        }
    }

    class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int mBottom;

        public VerticalSpaceItemDecoration(int i) {
            this.mBottom = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            if (recyclerView.getChildAdapterPosition(view) < recyclerView.getAdapter().getItemCount() - 1) {
                rect.bottom = this.mBottom;
            }
        }
    }

    public static void startSamsungMembers(Activity activity) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("voc://view/contactUs"));
        intent.putExtra("packageName", "com.samsung.accessory.fridaymgr");
        intent.putExtra("appId", "q5pb6l4o1v");
        intent.putExtra("appName", "Galaxy_Earbuds");
        String string = Preferences.getString(PreferenceKey.PREFERENCE_DEVICE_INFO_LAST_DEVICE_SW_VERSION, null);
        intent.putExtra("accessoryModelName", "SM-R190");
        if (!TextUtils.isEmpty(string)) {
            intent.putExtra("accessoryBuildNumber", string);
        }
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Log.e(TAG, "can not find activity, intent[" + intent + "]");
        }
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.drawer.Drawer.DrawerOwnerActivity
    public void startSamsungMembers() {
        startSamsungMembers(this);
    }

    public boolean isDrawerOpen() {
        return this.mDrawerLayout.isDrawerOpen(8388611);
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.drawer.Drawer.DrawerOwnerActivity
    public void openDrawer() {
        Log.d(TAG, "openDrawer()");
        if (!isDrawerOpen()) {
            this.mDrawerLayout.openDrawer(8388611);
        }
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.drawer.Drawer.DrawerOwnerActivity
    public void closeDrawer(final Runnable runnable) {
        Log.d(TAG, "closeDrawer()");
        if (isDrawerOpen()) {
            if (runnable != null) {
                final Handler handler = new Handler();
                final AnonymousClass8 r1 = new DrawerLayout.DrawerListener() {
                    /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass8 */

                    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
                    public void onDrawerOpened(View view) {
                    }

                    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
                    public void onDrawerSlide(View view, float f) {
                    }

                    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
                    public void onDrawerStateChanged(int i) {
                    }

                    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
                    public void onDrawerClosed(View view) {
                        Log.d(HomeActivity.TAG, "onDrawerClosed() : afterClose.run()");
                        handler.removeCallbacksAndMessages(null);
                        HomeActivity.this.mDrawerLayout.removeDrawerListener(this);
                        runnable.run();
                    }
                };
                this.mDrawerLayout.addDrawerListener(r1);
                handler.postDelayed(new Runnable() {
                    /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass9 */

                    public void run() {
                        Log.w(HomeActivity.TAG, "closeDrawer() : timeoverHandler.run()");
                        HomeActivity.this.mDrawerLayout.removeDrawerListener(r1);
                    }
                }, UpdateInterface.HOLDING_AFTER_BT_CONNECTED);
            }
            this.mDrawerLayout.closeDrawer(8388611);
        }
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.drawer.Drawer.DrawerOwnerActivity
    public void closeDrawerDirectly() {
        Log.d(TAG, "closeDrawerDirectly()");
        if (isDrawerOpen()) {
            this.mDrawerLayout.closeDrawer(8388611, false);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void checkSellOutInfoUpdate() {
        Log.d(TAG, "checkSellOutInfoUpdate()");
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        SOAgentServiceUtil.checkSellOutInfoUpdate(earBudsInfo.serialNumber_left, earBudsInfo.serialNumber_right, earBudsInfo.address);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void dialogFOTADone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.keep_the_case_open);
        builder.setMessage(R.string.install_now_notice_content);
        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass10 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void emergencyFotaDialog() {
        Log.d(TAG, "emergencyFotaDialog");
        Dialog dialog = this.mForegroundDialog;
        if (dialog != null && dialog.isShowing()) {
            this.mForegroundDialog.dismiss();
            this.mForegroundDialog = null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.fota_miss_match_version_title);
        builder.setMessage(R.string.fota_miss_match_version_content);
        builder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass11 */

            public void onClick(DialogInterface dialogInterface, int i) {
                HomeActivityModel.emergencyFota(HomeActivity.this);
            }
        });
        builder.setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.HomeActivity.AnonymousClass12 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        this.mForegroundDialog = builder.create();
        this.mForegroundDialog.show();
    }

    /* access modifiers changed from: protected */
    public void checkFotaStatus() {
        Log.d(TAG, "checkFota status");
    }

    private void checkShowUpdateBadge() {
        int queryAppUpdateCancelCount = UhmDatabase.queryAppUpdateCancelCount(BuildConfig.APPLICATION_ID);
        if (queryAppUpdateCancelCount < 32767) {
            int i = Preferences.getInt(PreferenceKey.PREV_UPDATE_CANCEL_COUNT, 0, UhmFwUtil.getLastLaunchDeviceId());
            if (queryAppUpdateCancelCount == 0) {
                Preferences.putBoolean(PreferenceKey.EXISTED_NEW_VERSION_PLUGIN, false, UhmFwUtil.getLastLaunchDeviceId());
            } else if (i < queryAppUpdateCancelCount) {
                Preferences.putBoolean(PreferenceKey.EXISTED_NEW_VERSION_PLUGIN, true, UhmFwUtil.getLastLaunchDeviceId());
            }
            Preferences.putInt(PreferenceKey.PREV_UPDATE_CANCEL_COUNT, Integer.valueOf(queryAppUpdateCancelCount), UhmFwUtil.getLastLaunchDeviceId());
        }
        updateDrawerBadge();
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card.CardOwnerActivity
    public void setBgGradationColor(int i) {
        Log.d(TAG, "setBgGradationColor() : " + i);
        Drawable background = this.mGradationBg.getBackground();
        if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColors(new int[]{i, this.COLOR_BLACK});
        }
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public void setScrollEnabled(boolean z) {
        if (this.mRecyclerViewLayoutManager != null) {
            Log.d(TAG, "setScrollEnabled() : " + z);
            this.mRecyclerViewLayoutManager.setScrollEnabled(z);
        }
    }

    class ScrollableLinearLayoutManager extends LinearLayoutManager {
        private boolean mScrollEnabled = true;

        public ScrollableLinearLayoutManager(Context context) {
            super(context);
        }

        public void setScrollEnabled(boolean z) {
            this.mScrollEnabled = z;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager, androidx.recyclerview.widget.LinearLayoutManager
        public boolean canScrollVertically() {
            return this.mScrollEnabled && super.canScrollVertically();
        }
    }
}
