package com.samsung.accessory.hearablemgr.core.gamemode;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.IBinder;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.ActionBuffer;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConstants;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.Msg;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.android.game.gos.IGosService;
import com.samsung.android.sdk.mobileservice.profile.Privacy;
import org.json.JSONObject;
import seccompat.android.bluetooth.BluetoothA2dp;
import seccompat.android.util.Log;
import seccompat.com.samsung.android.bluetooth.SemBluetoothAudioCast;
import seccompat.com.samsung.android.game.SemGameManager;

public class GameModeManager {
    private static final String ACTION_GOS_GAME_RESUME_PAUSE = "com.samsung.android.game.gos.ACTION_GAME_RESUME_PAUSE";
    private static final String EXTRA_GOS_PKG_NAME = "pkgName";
    private static final String EXTRA_GOS_TYPE = "type";
    private static final String GOS_AIDL_INTERFACE = "com.samsung.android.game.gos.IGosService";
    private static final int GOS_MIN_VERSION_CODE_POS = 210200000;
    private static final int GOS_MIN_VERSION_CODE_QOS = 300100000;
    private static final String GOS_PACKAGE_NAME = "com.samsung.android.game.gos";
    private static final IntentFilter RECEIVER_FILTER = new IntentFilter();
    private static final String TAG = "Attic_GameModeManager";
    private static final String VALUE_GOS_PAUSE = "GAME_PAUSED";
    private static final String VALUE_GOS_RESUME = "GAME_RESUMED";
    private final ServiceConnection CONNECTION_LISTENER = new ServiceConnection() {
        /* class com.samsung.accessory.hearablemgr.core.gamemode.GameModeManager.AnonymousClass2 */

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(GameModeManager.TAG, "onServiceConnected() : " + componentName);
            boolean z = true;
            GameModeManager.this.mBound = true;
            IGosService asInterface = IGosService.Stub.asInterface(iBinder);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(Privacy.KEY_EVENTS, "GAME_RESUMED,GAME_PAUSED");
                jSONObject.put("intent_action_name", GameModeManager.ACTION_GOS_GAME_RESUME_PAUSE);
                jSONObject.put("subscriber_name", Application.getContext().getPackageName());
                String requestWithJson = asInterface.requestWithJson("subscribe_events", jSONObject.toString());
                GameModeManager gameModeManager = GameModeManager.this;
                if (requestWithJson == null) {
                    z = false;
                }
                gameModeManager.mSucceedToSubscribe = z;
                Log.d(GameModeManager.TAG, "onServiceConnected() : result=" + requestWithJson);
            } catch (Throwable th) {
                th.printStackTrace();
                Log.e(GameModeManager.TAG, "onServiceConnected() : error=" + th);
            }
            GameModeManager.this.unbindGosService();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(GameModeManager.TAG, "onServiceDisconnected() : " + componentName);
            GameModeManager.this.mBound = false;
        }
    };
    private ActionBuffer mActionBuffer;
    private boolean mBound = false;
    private final BroadcastReceiver mGosReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.gamemode.GameModeManager.AnonymousClass3 */

        public void onReceive(Context context, Intent intent) {
            Log.d(GameModeManager.TAG, "onReceive() : " + intent.getAction());
            if (GameModeManager.ACTION_GOS_GAME_RESUME_PAUSE.equals(intent.getAction())) {
                String stringExtra = intent.getStringExtra("type");
                String stringExtra2 = intent.getStringExtra("pkgName");
                Log.d(GameModeManager.TAG, "onReceive() : type=" + stringExtra + ", pkgName=" + stringExtra2);
                if (stringExtra != null) {
                    char c = 65535;
                    int hashCode = stringExtra.hashCode();
                    if (hashCode != 403264027) {
                        if (hashCode == 1504027242 && stringExtra.equals(GameModeManager.VALUE_GOS_RESUME)) {
                            c = 0;
                        }
                    } else if (stringExtra.equals(GameModeManager.VALUE_GOS_PAUSE)) {
                        c = 1;
                    }
                    if (c == 0) {
                        GameModeManager.this.onGameModeChanged(stringExtra2, true);
                    } else if (c == 1) {
                        GameModeManager.this.onGameModeChanged(stringExtra2, false);
                    }
                }
            }
        }
    };
    private Byte mLastGameModeValue;
    private MaxBufferReceiver mMaxBufferReceiver;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.gamemode.GameModeManager.AnonymousClass4 */

        public void onReceive(Context context, Intent intent) {
            Log.d(GameModeManager.TAG, "onReceive() : " + intent.getAction());
            if (intent.getAction() != null) {
                String action = intent.getAction();
                char c = 65535;
                switch (action.hashCode()) {
                    case -2128145023:
                        if (action.equals("android.intent.action.SCREEN_OFF")) {
                            c = 3;
                            break;
                        }
                        break;
                    case -1553437513:
                        if (action.equals(CoreService.ACTION_DEVICE_EXTENDED_STATUS_READY)) {
                            c = 0;
                            break;
                        }
                        break;
                    case -1513304802:
                        if (action.equals(SemBluetoothAudioCast.ACTION_CAST_DEVICE_CONNECTION_STATE_CHANGED)) {
                            c = 6;
                            break;
                        }
                        break;
                    case -1423480515:
                        if (action.equals(SemBluetoothAudioCast.ACTION_AUDIO_SHARING_MODE_CHANGED)) {
                            c = 5;
                            break;
                        }
                        break;
                    case 823795052:
                        if (action.equals("android.intent.action.USER_PRESENT")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 1335721824:
                        if (action.equals(CoreService.ACTION_DEVICE_DISCONNECTED)) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1484068483:
                        if (action.equals(BluetoothA2dp.ACTION_DUAL_PLAY_MODE_ENABLED)) {
                            c = 4;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        GameModeManager.this.mLastGameModeValue = null;
                        GameModeManager.this.onExtendedStatusReady();
                        return;
                    case 1:
                        GameModeManager.this.mLastGameModeValue = null;
                        return;
                    case 2:
                        GameModeManager.this.onInUserUseChanged(true);
                        return;
                    case 3:
                        GameModeManager.this.onInUserUseChanged(false);
                        return;
                    case 4:
                    case 5:
                    case 6:
                        GameModeManager.this.onMaxBufferCondition();
                        return;
                    default:
                        return;
                }
            }
        }
    };
    private ScreenOnOffReceiver mScreenOnOffReceiver;
    private boolean mSucceedToSubscribe = false;
    private SupportService mSupportService;

    public interface SupportService {
        EarBudsInfo getEarBudsInfo();

        boolean isConnected();

        void sendSppMessage(Msg msg);
    }

    public GameModeManager(SupportService supportService) {
        this.mSupportService = supportService;
        registerReceiver();
        subscribeToGos();
        this.mActionBuffer = new ActionBuffer(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.gamemode.GameModeManager.AnonymousClass1 */

            public void run() {
                GameModeManager.this.sendCurrentState();
            }
        });
        this.mScreenOnOffReceiver = new ScreenOnOffReceiver(Application.getContext());
        this.mScreenOnOffReceiver.addReceiver(this.mReceiver);
        SemBluetoothAudioCast.init(Application.getContext());
        this.mMaxBufferReceiver = new MaxBufferReceiver(Application.getContext());
        if (SemBluetoothAudioCast.isSupported()) {
            this.mMaxBufferReceiver.addReceiver(this.mReceiver);
        }
    }

    public void destroy() {
        Log.d(TAG, "destroy()");
        this.mMaxBufferReceiver.destroy();
        this.mScreenOnOffReceiver.destroy();
        this.mActionBuffer.destroy();
        unbindGosService();
        unregisterReceiver();
    }

    private static Boolean isForegroundGame() {
        Boolean bool;
        if (Util.isSamsungDevice()) {
            try {
                bool = new SemGameManager().isForegroundGame();
            } catch (Error | Exception e) {
                Log.e(TAG, "isForegroundGame() : Exception : " + e);
            }
            return Boolean.valueOf(bool == null && bool.booleanValue() && Util.isInUserUse());
        }
        bool = null;
        return Boolean.valueOf(bool == null && bool.booleanValue() && Util.isInUserUse());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onExtendedStatusReady() {
        Log.d(TAG, "onExtendedStatusReady()");
        if (isEarBudsGameModeOn() || isAlwaysLcdOnOffEvent()) {
            this.mActionBuffer.action();
        }
        if (isSupportDevice() && !this.mSucceedToSubscribe) {
            subscribeToGos();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onGameModeChanged(String str, boolean z) {
        Log.d(TAG, "onGameModeChanged() : " + z + " / " + str);
        if (isEarBudsGameModeOn()) {
            this.mActionBuffer.action();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onInUserUseChanged(boolean z) {
        Log.d(TAG, "onInUserUseChanged() : " + z);
        if (isEarBudsGameModeOn() || isAlwaysLcdOnOffEvent()) {
            this.mActionBuffer.action();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onMaxBufferCondition() {
        boolean isSupported = SemBluetoothAudioCast.isSupported();
        Log.d(TAG, "onMaxBufferCondition() : " + isSupported);
        if (isSupported) {
            this.mActionBuffer.action();
        }
    }

    private void sendSppMessage(boolean z, boolean z2) {
        if (this.mSupportService.isConnected()) {
            byte b = (byte) ((z2 ? 1 : 0) | (z ? 16 : 0));
            Byte b2 = this.mLastGameModeValue;
            if (b2 == null || b2.byteValue() != b) {
                this.mSupportService.sendSppMessage(new MsgSimple(MsgID.GAME_MODE, b));
                this.mLastGameModeValue = Byte.valueOf(b);
                if (A2dpLatencyControl.isSupportDevice()) {
                    A2dpLatencyControl.setA2dpLatency(z);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendCurrentState() {
        boolean booleanValue = isForegroundGame().booleanValue();
        boolean isInUserUse = Util.isInUserUse();
        if (!isEarBudsGameModeOn()) {
            booleanValue = false;
        }
        if (SemBluetoothAudioCast.isSupported() && MaxBufferReceiver.isMaxBufferCondition()) {
            booleanValue = false;
            isInUserUse = false;
        }
        Log.d(TAG, "sendCurrentState() : onGame=" + booleanValue + ", inUserUse=" + isInUserUse);
        sendSppMessage(booleanValue, isInUserUse);
    }

    private static PackageInfo getGosPackageInfo() {
        try {
            return Application.getContext().getPackageManager().getPackageInfo(GOS_PACKAGE_NAME, 0);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getGosPackageInfo() : error=" + e.toString());
            return null;
        }
    }

    private static int getGosVersion() {
        PackageInfo gosPackageInfo = getGosPackageInfo();
        if (gosPackageInfo != null) {
            return gosPackageInfo.versionCode;
        }
        return -1;
    }

    public static boolean isSupportDevice() {
        boolean isSamsungDevice = Util.isSamsungDevice();
        int gosVersion = getGosVersion();
        boolean z = isSamsungDevice && ((Build.VERSION.SDK_INT == 28 && gosVersion >= GOS_MIN_VERSION_CODE_POS) || (Build.VERSION.SDK_INT >= 29 && gosVersion >= GOS_MIN_VERSION_CODE_QOS));
        StringBuilder sb = new StringBuilder();
        sb.append("isSupportDevice() : ");
        sb.append(z);
        sb.append(" (");
        sb.append(isSamsungDevice ? "S" : FmmConstants.NOT_SUPPORT);
        sb.append(" / SDK_INT: ");
        sb.append(Build.VERSION.SDK_INT);
        sb.append(" / gosVersion: ");
        sb.append(gosVersion);
        Log.i(TAG, sb.toString());
        return z;
    }

    public static boolean isAlwaysLcdOnOffEvent() {
        Log.d(TAG, "isForceLcdOnOffEvent() : true");
        return true;
    }

    public boolean isEarBudsGameModeOn() {
        boolean z = isSupportDevice() && this.mSupportService.getEarBudsInfo().adjustSoundSync;
        Log.d(TAG, "isEarBudsGameModeOn() : " + z);
        return z;
    }

    private void subscribeToGos() {
        if (isSupportDevice()) {
            Log.d(TAG, "subscribeToGos()");
            bindGosService();
        }
    }

    private void bindGosService() {
        Intent intent = new Intent(GOS_AIDL_INTERFACE);
        intent.setPackage(GOS_PACKAGE_NAME);
        boolean bindService = Application.getContext().bindService(intent, this.CONNECTION_LISTENER, 1);
        Log.d(TAG, "bindGosService() : " + bindService);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void unbindGosService() {
        Log.d(TAG, "unbindGosService() : " + this.mBound);
        if (this.mBound) {
            Application.getContext().unbindService(this.CONNECTION_LISTENER);
            this.mBound = false;
        }
    }

    private void registerReceiver() {
        Application.getContext().registerReceiver(this.mGosReceiver, getGosIntentFilter(), "android.permission.WRITE_SECURE_SETTINGS", null);
        Application.getContext().registerReceiver(this.mReceiver, getIntentFilter());
    }

    private void unregisterReceiver() {
        Application.getContext().unregisterReceiver(this.mGosReceiver);
        Application.getContext().unregisterReceiver(this.mReceiver);
    }

    static {
        RECEIVER_FILTER.addAction(ACTION_GOS_GAME_RESUME_PAUSE);
    }

    private IntentFilter getGosIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_GOS_GAME_RESUME_PAUSE);
        return intentFilter;
    }

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_DEVICE_CONNECTED);
        intentFilter.addAction(CoreService.ACTION_DEVICE_EXTENDED_STATUS_READY);
        intentFilter.addAction(CoreService.ACTION_DEVICE_DISCONNECTED);
        return intentFilter;
    }
}
