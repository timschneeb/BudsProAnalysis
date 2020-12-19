package com.samsung.accessory.hearablemgr.core.service;

import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.accessorydm.eng.core.XDMWbxml;
import com.accessorydm.interfaces.XDBInterface;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.BaseContentProvider;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import com.samsung.accessory.hearablemgr.common.util.BroadcastReceiverUtil;
import com.samsung.accessory.hearablemgr.common.util.ByteUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.common.util.WaitTimer;
import com.samsung.accessory.hearablemgr.common.util.WorkerHandler;
import com.samsung.accessory.hearablemgr.common.util.WorkerTask;
import com.samsung.accessory.hearablemgr.core.EarBudsFotaInfo;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutineConstants;
import com.samsung.accessory.hearablemgr.core.bluetooth.BluetoothManager;
import com.samsung.accessory.hearablemgr.core.fmm.receiver.FmmDeviceStatusReceiver;
import com.samsung.accessory.hearablemgr.core.fota.util.FotaUtil;
import com.samsung.accessory.hearablemgr.core.gamemode.GameModeManager;
import com.samsung.accessory.hearablemgr.core.notification.NotificationUtil;
import com.samsung.accessory.hearablemgr.core.service.SppConnectionManager;
import com.samsung.accessory.hearablemgr.core.service.message.Msg;
import com.samsung.accessory.hearablemgr.core.service.message.MsgCheckTheFitOfEarbudsResult;
import com.samsung.accessory.hearablemgr.core.service.message.MsgDebugData;
import com.samsung.accessory.hearablemgr.core.service.message.MsgDebugSKU;
import com.samsung.accessory.hearablemgr.core.service.message.MsgDebugSerialNumber;
import com.samsung.accessory.hearablemgr.core.service.message.MsgExtendedStatusUpdated;
import com.samsung.accessory.hearablemgr.core.service.message.MsgFotaDeviceInfoSwVersion;
import com.samsung.accessory.hearablemgr.core.service.message.MsgFotaEmergency;
import com.samsung.accessory.hearablemgr.core.service.message.MsgGetFmmConfig;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgManagerInfo;
import com.samsung.accessory.hearablemgr.core.service.message.MsgMeteringReport;
import com.samsung.accessory.hearablemgr.core.service.message.MsgMuteEarbudStatusUpdated;
import com.samsung.accessory.hearablemgr.core.service.message.MsgNoiseControlsUpdate;
import com.samsung.accessory.hearablemgr.core.service.message.MsgNoiseReductionModeUpdated;
import com.samsung.accessory.hearablemgr.core.service.message.MsgReset;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSetFmmConfig;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSetInBandRingtone;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSetTouchpadOption;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.accessory.hearablemgr.core.service.message.MsgStatusUpdated;
import com.samsung.accessory.hearablemgr.core.service.message.MsgTouchPadOther;
import com.samsung.accessory.hearablemgr.core.service.message.MsgTouchUpdated;
import com.samsung.accessory.hearablemgr.core.service.message.MsgUniversalAcknowledgement;
import com.samsung.accessory.hearablemgr.core.service.message.MsgUpdateTime;
import com.samsung.accessory.hearablemgr.core.service.message.MsgVersionInfo;
import com.samsung.accessory.hearablemgr.core.service.message.MsgVoiceWakeUpEvent;
import com.samsung.accessory.hearablemgr.core.service.message.MsgVoiceWakeUpListeningStatus;
import com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldActivity;
import com.samsung.android.fotaagent.update.UpdateInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import seccompat.android.util.Log;

public class CoreService implements GameModeManager.SupportService {
    public static final String ACTION_DEVICE_CONNECTED = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_DEVICE_CONNECTED";
    public static final String ACTION_DEVICE_CONNECTING = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_DEVICE_CONNECTING";
    public static final String ACTION_DEVICE_DISCONNECTED = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_DEVICE_DISCONNECTED";
    public static final String ACTION_DEVICE_DISCONNECTED_FROM_CONNECT = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_DEVICE_DISCONNECTED_FROM_CONNECT";
    public static final String ACTION_DEVICE_DISCONNECTING = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_DEVICE_DISCONNECTING";
    public static final String ACTION_DEVICE_EXTENDED_STATUS_READY = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_DEVICE_EXTENDED_STATUS_READY";
    public static final String ACTION_MSG_FOTA_CHECK_UPDATE = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_FOTA_CHECK_UPDATE";
    public static final String ACTION_MSG_ID_AMBIENT_SOUND_UPDATED = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_AMBIENT_SOUND_UPDATED";
    public static final String ACTION_MSG_ID_AMBIENT_VOLUME_UPDATED = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_AMBIENT_VOLUME_UPDATED";
    public static final String ACTION_MSG_ID_CALL_STATE = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_CALL_STATE";
    public static final String ACTION_MSG_ID_CHECK_THE_FIT_OF_EARBUDS_RESULT = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_CHECK_THE_FIT_OF_EARBUDS_RESULT";
    public static final String ACTION_MSG_ID_DEBUG_GET_ALL_DATA = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_DEBUG_GET_ALL_DATA";
    public static final String ACTION_MSG_ID_DEBUG_SERIAL_NUMBER = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_DEBUG_SERIAL_NUMBER";
    public static final String ACTION_MSG_ID_EQUALIZER_TYPE_UPDATED = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_EQUALIZER_TYPE_UPDATED";
    public static final String ACTION_MSG_ID_EXTENDED_STATUS_UPDATED = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_EXTENDED_STATUS_UPDATED";
    public static final String ACTION_MSG_ID_FIND_MY_EARBUDS_STATUS_UPDATED = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STATUS_UPDATED";
    public static final String ACTION_MSG_ID_FIND_MY_EARBUDS_STOP = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STOP";
    public static final String ACTION_MSG_ID_FOTA2_EMERGENCY = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_FOTA2_EMERGENCY";
    public static final String ACTION_MSG_ID_FOTA_EMERGENCY = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_FOTA_EMERGENCY";
    public static final String ACTION_MSG_ID_GET_FMM_CONFIG_RESP = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_GET_FMM_CONFIG_RESP";
    public static final String ACTION_MSG_ID_LOCK_TOUCHPAD_UPDATED = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_LOCK_TOUCHPAD_UPDATED";
    public static final String ACTION_MSG_ID_NOISE_CONTROLS_UPDATE = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_NOISE_CONTROLS_UPDATE";
    public static final String ACTION_MSG_ID_NOISE_REDUCTION_UPDATED = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_NOISE_REDUCTION_UPDATED";
    public static final String ACTION_MSG_ID_RESET = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_RESET";
    public static final String ACTION_MSG_ID_SCO_STATE_UPDATED = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_SCO_STATE_UPDATED";
    public static final String ACTION_MSG_ID_SET_FMM_CONFIG_RESULT = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_SET_FMM_CONFIG_RESULT";
    public static final String ACTION_MSG_ID_STATUS_UPDATED = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_STATUS_UPDATED";
    public static final String ACTION_MSG_ID_VOICE_WAKE_UP_EVENT = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_VOICE_WAKE_UP_EVENT";
    public static final String ACTION_MUTE_EARBUD_STATUS_UPDATED = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MUTE_EARBUD_STATUS_UPDATED";
    private static final int NO_RESPONSE_TIMEOUT = 3000;
    private static final String TAG = "Attic_CoreService";
    final String FOTA_BADGECOUNT = ("com.sec.android.fotaprovider.FOTA_BADGECOUNT_" + Application.getContext().getPackageName());
    final String FOTA_CHECKED_DATE_UPDATE = ("com.sec.android.fotaprovider.FOTA_CHECKED_DATE_UPDATE_" + Application.getContext().getPackageName());
    final String LAST_UPDATE_INFO = ("com.sec.android.fotaprovider.LAST_UPDATE_INFO_" + Application.getContext().getPackageName());
    final String UPDATE_IN_PROGRESS = ("com.sec.android.fotaprovider.UPDATE_IN_PROGRESS_" + Application.getContext().getPackageName());
    private BroadcastReceiverUtil.Receiver mBluetoothReceiver = new BroadcastReceiverUtil.Receiver() {
        /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass5 */

        @Override // com.samsung.accessory.hearablemgr.common.util.BroadcastReceiverUtil.Receiver
        public void setIntentFilter(IntentFilter intentFilter) {
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        }

        public void onReceive(Context context, final Intent intent) {
            Log.d(CoreService.TAG, "onReceive() : " + intent.getAction());
            CoreService.this.mWorker.post(new WorkerTask() {
                /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass5.AnonymousClass1TaskOnBluetoothReceive */

                /* JADX WARNING: Removed duplicated region for block: B:17:0x004f  */
                /* JADX WARNING: Removed duplicated region for block: B:32:0x010f  */
                @Override // com.samsung.accessory.hearablemgr.common.util.WorkerTask
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void execute() {
                    /*
                    // Method dump skipped, instructions count: 453
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass5.AnonymousClass1TaskOnBluetoothReceive.execute():void");
                }
            });
        }
    };
    private BluetoothDevice mConnectedDevice = null;
    private int mConnectionState = 0;
    private Context mContext;
    private boolean mCurrentCoupledStatus = false;
    private DeviceLogManager mDeviceLogManager = null;
    private EarBudsInfo mEarBudsInfo = new EarBudsInfo();
    private EarBudsUsageReporter mEarBudsUsageReporter = null;
    private boolean mEmulatingConnected = false;
    private boolean mExtendedStatusReady = false;
    private FotaTransferManager mFOTATransferManager;
    private FmmDeviceStatusReceiver mFmmDeviceStatusReceiver = null;
    private EarBudsFotaInfo mFotaInfo = new EarBudsFotaInfo();
    private BroadcastReceiverUtil.Receiver mFotaReceiver = new BroadcastReceiverUtil.Receiver() {
        /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass7 */

        @Override // com.samsung.accessory.hearablemgr.common.util.BroadcastReceiverUtil.Receiver
        public void setIntentFilter(IntentFilter intentFilter) {
            intentFilter.addAction(CoreService.this.FOTA_CHECKED_DATE_UPDATE);
            intentFilter.addAction(CoreService.this.UPDATE_IN_PROGRESS);
            intentFilter.addAction(CoreService.this.FOTA_BADGECOUNT);
            intentFilter.addAction(CoreService.this.LAST_UPDATE_INFO);
        }

        public void onReceive(Context context, final Intent intent) {
            Log.d(CoreService.TAG, "onReceive() : " + intent.getAction());
            CoreService.this.mWorker.post(new WorkerTask() {
                /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass7.AnonymousClass1TaskOnBroadcastReceive */

                @Override // com.samsung.accessory.hearablemgr.common.util.WorkerTask
                public void execute() {
                    if (CoreService.this.FOTA_CHECKED_DATE_UPDATE.equals(intent.getAction())) {
                        Log.d(this.TAG, "FOTA_CHECKED_DATE_UPDATE");
                        long longExtra = intent.getLongExtra(XDBInterface.XDM_SQL_DB_POLLING_TIME, -1);
                        String str = this.TAG;
                        Log.d(str, "FOTA_CHECKED_DATE_UPDATE : time :" + longExtra);
                        FotaUtil.setLastSWVersionCheckTime(longExtra);
                    } else if (CoreService.this.UPDATE_IN_PROGRESS.equals(intent.getAction())) {
                        Log.d(this.TAG, "UPDATE_IN_PROGRESS");
                    } else if (CoreService.this.FOTA_BADGECOUNT.equals(intent.getAction())) {
                        Log.d(this.TAG, "FOTA_BADGECOUNT");
                        int intExtra = intent.getIntExtra("badge_count", -1);
                        String str2 = this.TAG;
                        Log.d(str2, "FOTA_BADGECOUNT : badge_count : " + intExtra);
                        boolean z = true;
                        if (intExtra != 1) {
                            z = false;
                        }
                        FotaUtil.setCheckFotaUpdate(z);
                    } else if (CoreService.this.LAST_UPDATE_INFO.equals(intent.getAction())) {
                        Log.d(this.TAG, "LAST_UPDATE_INFO");
                    }
                }
            });
        }
    };
    private GameModeManager mGameModeManager = null;
    private boolean mOnDisconnectFromConnect = false;
    private BroadcastReceiverUtil.Receiver mReceiver = new BroadcastReceiverUtil.Receiver() {
        /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass4 */

        @Override // com.samsung.accessory.hearablemgr.common.util.BroadcastReceiverUtil.Receiver
        public void setIntentFilter(IntentFilter intentFilter) {
            intentFilter.addAction(BluetoothManager.ACTION_READY);
            intentFilter.addAction(BluetoothManager.ACTION_STOPPED);
        }

        public void onReceive(Context context, final Intent intent) {
            Log.d(CoreService.TAG, "onReceive() : " + intent.getAction());
            CoreService.this.mWorker.post(new WorkerTask() {
                /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass4.AnonymousClass1TaskOnBroadcastReceive */

                /* JADX WARNING: Removed duplicated region for block: B:12:0x002d  */
                /* JADX WARNING: Removed duplicated region for block: B:14:0x003f  */
                @Override // com.samsung.accessory.hearablemgr.common.util.WorkerTask
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void execute() {
                    /*
                        r4 = this;
                        android.content.Intent r0 = r3
                        java.lang.String r0 = r0.getAction()
                        int r1 = r0.hashCode()
                        r2 = 107150431(0x662fc5f, float:4.269129E-35)
                        r3 = 1
                        if (r1 == r2) goto L_0x0020
                        r2 = 1442301109(0x55f7c4b5, float:3.40530278E13)
                        if (r1 == r2) goto L_0x0016
                        goto L_0x002a
                    L_0x0016:
                        java.lang.String r1 = "com.samsung.accessory.hearablemgr.core.bluetooth.BluetoothManager.ACTION_READY"
                        boolean r0 = r0.equals(r1)
                        if (r0 == 0) goto L_0x002a
                        r0 = 0
                        goto L_0x002b
                    L_0x0020:
                        java.lang.String r1 = "com.samsung.accessory.hearablemgr.core.bluetooth.BluetoothManager.ACTION_STOPPED"
                        boolean r0 = r0.equals(r1)
                        if (r0 == 0) goto L_0x002a
                        r0 = r3
                        goto L_0x002b
                    L_0x002a:
                        r0 = -1
                    L_0x002b:
                        if (r0 == 0) goto L_0x003f
                        if (r0 == r3) goto L_0x0030
                        goto L_0x004d
                    L_0x0030:
                        java.lang.String r0 = r4.TAG
                        java.lang.String r1 = "BluetoothManager.ACTION_STOPPED"
                        seccompat.android.util.Log.d(r0, r1)
                        com.samsung.accessory.hearablemgr.core.service.CoreService$4 r0 = com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass4.this
                        com.samsung.accessory.hearablemgr.core.service.CoreService r0 = com.samsung.accessory.hearablemgr.core.service.CoreService.this
                        com.samsung.accessory.hearablemgr.core.service.CoreService.access$1000(r0)
                        goto L_0x004d
                    L_0x003f:
                        java.lang.String r0 = r4.TAG
                        java.lang.String r1 = "BluetoothManager.ACTION_READY"
                        seccompat.android.util.Log.d(r0, r1)
                        com.samsung.accessory.hearablemgr.core.service.CoreService$4 r0 = com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass4.this
                        com.samsung.accessory.hearablemgr.core.service.CoreService r0 = com.samsung.accessory.hearablemgr.core.service.CoreService.this
                        com.samsung.accessory.hearablemgr.core.service.CoreService.access$000(r0)
                    L_0x004d:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass4.AnonymousClass1TaskOnBroadcastReceive.execute():void");
                }
            });
        }
    };
    private WaitTimer mResponseTimer;
    private BroadcastReceiverUtil.Receiver mScoUpdateBroadcastReceiver = new BroadcastReceiverUtil.Receiver() {
        /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass6 */

        @Override // com.samsung.accessory.hearablemgr.common.util.BroadcastReceiverUtil.Receiver
        public void setIntentFilter(IntentFilter intentFilter) {
            intentFilter.addAction("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
        }

        public void onReceive(Context context, final Intent intent) {
            CoreService.this.mWorker.post(new WorkerTask() {
                /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass6.AnonymousClass1TaskOnScoBroadcastReceive */

                @Override // com.samsung.accessory.hearablemgr.common.util.WorkerTask
                public void execute() {
                    String action = intent.getAction();
                    if (((action.hashCode() == -1435586571 && action.equals("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) ? (char) 0 : 65535) == 0) {
                        int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                        String str = this.TAG;
                        Log.d(str, "ACTION_AUDIO_STATE_CHANGED  currentState : " + intExtra);
                        if (intExtra == 10 || intExtra == 12) {
                            Util.sendPermissionBroadcast(CoreService.this.mContext, new Intent(CoreService.ACTION_MSG_ID_SCO_STATE_UPDATED));
                        }
                    }
                }
            });
        }
    };
    private SpatialSensorManager mSpatialSensorManager = null;
    private final SppConnectionManager.Callback mSppConnectionCallback = new SppConnectionManager.Callback() {
        /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass3 */
        private final String TAG = "Attic_CoreService_SppCallback";

        @Override // com.samsung.accessory.hearablemgr.core.service.SppConnectionManager.Callback
        public void onMessage(BluetoothDevice bluetoothDevice, Msg msg) {
            CoreService.this.onSppMessage(msg);
        }

        @Override // com.samsung.accessory.hearablemgr.core.service.SppConnectionManager.Callback
        public void onConnectionStateChanged(final BluetoothDevice bluetoothDevice, final int i) {
            Log.d("Attic_CoreService_SppCallback", "onConnectionStateChanged() : " + BluetoothUtil.stateToString(i));
            CoreService.this.mWorker.post(new WorkerTask() {
                /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass3.AnonymousClass1TaskOnConnectionStateChanged */

                @Override // com.samsung.accessory.hearablemgr.common.util.WorkerTask
                public void execute() {
                    String str = this.TAG;
                    Log.d(str, "onConnectionStateChanged() : " + BluetoothUtil.stateToString(i));
                    if (i == 2) {
                        BluetoothManager bluetoothManager = Application.getBluetoothManager();
                        synchronized (bluetoothManager) {
                            if (bluetoothManager.isReady() && !bluetoothManager.isHeadsetConnecting(bluetoothDevice) && !bluetoothManager.isA2dpConnecting(bluetoothDevice)) {
                                Log.w(this.TAG, "onConnected() : force profile connect");
                                bluetoothManager.connectHeadset(bluetoothDevice);
                                bluetoothManager.connectA2dp(bluetoothDevice);
                            }
                        }
                    }
                    CoreService.this.updateConnectionState(bluetoothDevice, i);
                }
            });
            if (CoreService.this.mSpatialSensorManager != null) {
                CoreService.this.mSpatialSensorManager.onSppConnectionManagerStateChanged(bluetoothDevice, i);
            }
        }
    };
    private SppConnectionManager mSppConnectionManager = null;
    private Set<OnSppMessageListener> mSppMessageListener = new HashSet();
    private Handler mTimeOutHandler = new Handler() {
        /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass8 */

        public void handleMessage(Message message) {
            Log.d(CoreService.TAG, "TimeOut Message:" + Integer.toHexString(message.what & 255));
            int i = message.what;
            if (i == 34) {
                CoreService.this.sendSppMessage(new MsgDebugSKU());
            } else if (i == 38) {
                CoreService.this.sendSppMessage(new MsgDebugData());
            } else if (i == 41) {
                CoreService.this.sendSppMessage(new MsgDebugSerialNumber());
            }
        }
    };
    private WorkerHandler mWorker;

    public interface OnSppMessageListener {
        void onSppMessage(Msg msg);
    }

    public CoreService(Context context) {
        this.mContext = context;
        onCreate();
    }

    private void onCreate() {
        Log.d(TAG, "onCreate()");
        this.mWorker = WorkerHandler.createWorkerHandler("core_service_worker@" + this);
        this.mResponseTimer = new WaitTimer(this.mTimeOutHandler);
        this.mResponseTimer.reset();
        this.mSppConnectionManager = new SppConnectionManager(this.mSppConnectionCallback);
        this.mFOTATransferManager = new FotaTransferManager(this);
        this.mFmmDeviceStatusReceiver = new FmmDeviceStatusReceiver(this.mContext);
        BroadcastReceiverUtil.register(this.mContext, this.mReceiver);
        BroadcastReceiverUtil.register(this.mContext, this.mScoUpdateBroadcastReceiver);
        BroadcastReceiverUtil.register(this.mContext, this.mFotaReceiver);
        this.mDeviceLogManager = new DeviceLogManager(this);
        this.mGameModeManager = new GameModeManager(this);
        this.mEarBudsUsageReporter = new EarBudsUsageReporter(this);
        if (SpatialSensorManager.isSupported(this.mContext)) {
            this.mSpatialSensorManager = new SpatialSensorManager(this.mContext, this, this.mSppConnectionManager);
        }
        if (Application.getBluetoothManager().isReady()) {
            this.mWorker.post(new WorkerTask() {
                /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass1 */

                @Override // com.samsung.accessory.hearablemgr.common.util.WorkerTask
                public void execute() {
                    CoreService.this.onBluetoothManagerReady();
                }
            });
        }
        Log.d(TAG, "onCreate()_end");
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        this.mWorker.quit();
        BroadcastReceiverUtil.unregister(this.mContext, this.mBluetoothReceiver);
        BroadcastReceiverUtil.unregister(this.mContext, this.mReceiver);
        BroadcastReceiverUtil.unregister(this.mContext, this.mScoUpdateBroadcastReceiver);
        BroadcastReceiverUtil.unregister(this.mContext, this.mFotaReceiver);
        unregisterSppMessageListener(null);
        SpatialSensorManager spatialSensorManager = this.mSpatialSensorManager;
        if (spatialSensorManager != null) {
            spatialSensorManager.destroy();
        }
        this.mFOTATransferManager.destroy();
        this.mSppConnectionManager.destroy();
        this.mDeviceLogManager.destroy();
        this.mGameModeManager.destroy();
        this.mEarBudsUsageReporter.destroy();
        this.mFmmDeviceStatusReceiver.onDestroy();
        WaitTimer waitTimer = this.mResponseTimer;
        if (waitTimer != null) {
            waitTimer.reset();
        }
        Log.d(TAG, "onDestroy()_end");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onBluetoothManagerReady() {
        Log.d(TAG, "onBluetoothManagerReady()");
        BroadcastReceiverUtil.register(this.mContext, this.mBluetoothReceiver);
        BluetoothDevice bondedDevice = BluetoothUtil.getBondedDevice(getLastLaunchDeviceAddress());
        if (bondedDevice == null) {
            return;
        }
        if ((Application.getBluetoothManager().getHeadsetState(bondedDevice) == 2 || Application.getBluetoothManager().getA2dpState(bondedDevice) == 2) && this.mSppConnectionManager.getConnectionState() == 0) {
            connectSppByProfile(bondedDevice);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onBluetoothManagerStop() {
        Log.d(TAG, "onBluetoothManagerStop()");
        BroadcastReceiverUtil.unregister(this.mContext, this.mBluetoothReceiver);
        if (this.mSppConnectionManager.getConnectionState() != 0) {
            android.util.Log.d(TAG, "Disconnect SPP by onBluetoothManagerStop() : " + BluetoothUtil.privateAddress(this.mSppConnectionManager.getDevice()));
            disconnectSpp();
        }
    }

    private void onConnecting(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onConnecting()... : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
        Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_DEVICE_CONNECTING));
    }

    public void emulateConnected() {
        Log.d(TAG, "emulateConnected()");
        this.mEmulatingConnected = true;
        EarBudsInfo earBudsInfo = this.mEarBudsInfo;
        earBudsInfo.address = "00:00:00:00:00:00";
        earBudsInfo.coupled = true;
        earBudsInfo.batteryL = 90;
        earBudsInfo.batteryR = 90;
        earBudsInfo.wearingL = true;
        earBudsInfo.wearingR = true;
        Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_DEVICE_CONNECTED));
    }

    private void onConnected(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onConnected() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
        Preferences.putBoolean(PreferenceKey.HOME_DISCONNECTED_BY_USER, false);
        this.mCurrentCoupledStatus = false;
        synchronized (this) {
            this.mConnectedDevice = bluetoothDevice;
            this.mEarBudsInfo.address = bluetoothDevice.getAddress();
            EarBudsFotaInfo earBudsFotaInfo = this.mFotaInfo;
            earBudsFotaInfo.deviceId = "TWID:" + this.mEarBudsInfo.address.replace(":", "");
        }
        Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_DEVICE_CONNECTED));
        long currentTimeMillis = System.currentTimeMillis();
        sendSppMessage(new MsgUpdateTime(currentTimeMillis, TimeZone.getDefault().getOffset(currentTimeMillis)));
        sendRequestDebugSKU();
        sendRequestSerialNumber();
        sendRequestDebugGetAllData();
    }

    private void emulateDisconnected() {
        Log.d(TAG, "emulateDisconnected()");
        this.mEmulatingConnected = false;
        Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_DEVICE_DISCONNECTED));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void onSppMessage(Msg msg) {
        Log.d(TAG, "onSppMessage() : " + ByteUtil.toHexString(msg.id));
        boolean z = true;
        int i = 1;
        int i2 = 0;
        switch (msg.id) {
            case -118:
                Log.d(TAG, "MsgID.SET_IN_BAND_RINGTONE");
                MsgSetInBandRingtone msgSetInBandRingtone = (MsgSetInBandRingtone) msg;
                if (msgSetInBandRingtone.status != 1) {
                    if (msgSetInBandRingtone.status == 0) {
                        NotificationUtil.setInBandRingtone(false);
                        break;
                    }
                } else {
                    NotificationUtil.setInBandRingtone(true);
                    break;
                }
                break;
            case -111:
                Log.d(TAG, "MsgID.TOUCH_UPDATED");
                EarBudsInfo earBudsInfo = this.mEarBudsInfo;
                if (((MsgTouchUpdated) msg).status != 1) {
                    z = false;
                }
                earBudsInfo.touchpadLocked = z;
                SamsungAnalyticsUtil.setStatusString(SA.Status.LOCK_TOUCHPAD, this.mEarBudsInfo.touchpadLocked ? "1" : "0");
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_LOCK_TOUCHPAD_UPDATED));
                break;
            case -109:
                Log.d(TAG, "MsgID.TOUCHPAD_OTHER_OPTION");
                MsgTouchPadOther msgTouchPadOther = (MsgTouchPadOther) msg;
                if (msgTouchPadOther.touchpadOtherOptionValue != 4 || !TouchAndHoldActivity.isReadySpotify()) {
                    if (msgTouchPadOther.touchpadOtherOptionValue > 4) {
                        String str = "none";
                        String str2 = "none";
                        String string = Preferences.getString(msgTouchPadOther.touchpadOtherOptionValue == 5 ? PreferenceKey.LEFT_OTHER_OPTION_PACKAGE_NAME : PreferenceKey.RIGHT_OTHER_OPTION_PACKAGE_NAME, "", UhmFwUtil.getLastLaunchDeviceId());
                        if (string.equals("")) {
                            this.mEarBudsInfo.touchpadOptionLeft = 2;
                            this.mEarBudsInfo.touchpadOptionRight = 2;
                            Application.getCoreService().sendSppMessage(new MsgSetTouchpadOption((byte) this.mEarBudsInfo.touchpadOptionLeft, (byte) this.mEarBudsInfo.touchpadOptionRight));
                            break;
                        } else {
                            ArrayList<HashMap<String, String>> checkApp2App = checkApp2App();
                            if (checkApp2App.size() > 0) {
                                while (true) {
                                    if (i2 < checkApp2App.size()) {
                                        if (string.equals(checkApp2App.get(i2).get(BaseContentProvider.PACKAGE_NAME))) {
                                            str = checkApp2App.get(i2).get(RoutineConstants.APP_TO_APP_KEY_MENU_NAME);
                                            str2 = checkApp2App.get(i2).get("description");
                                        } else {
                                            i2++;
                                        }
                                    }
                                }
                                Intent intent = new Intent(Util.SEND_PUI_EVENT);
                                intent.setPackage(string);
                                intent.putExtra(RoutineConstants.APP_TO_APP_KEY_MENU_NAME, str);
                                intent.putExtra("description", str2);
                                this.mContext.sendBroadcast(intent);
                                SamsungAnalyticsUtil.sendEvent(SA.Event.TAP_AND_HOLD_OTHERS_APPS, (String) null, SamsungAnalyticsUtil.touchPadTapAndHoldOthersPkgNameToDetail(string));
                                break;
                            }
                        }
                    }
                } else {
                    Intent intent2 = new Intent("com.spotify.music.features.spoton.ACTION_PLAY_SPOTIFY");
                    intent2.setClassName(Util.SPOTIFY, "com.spotify.music.features.spoton.receiver.SpotOnReceiver");
                    intent2.putExtra("com.spotify.music.features.spoton.extras.CLIENT_ID", "1ba94f7ca71e428085112fd877ea8c14");
                    intent2.putExtra("com.spotify.music.features.spoton.extras.PENDING_INTENT", PendingIntent.getBroadcast(this.mContext, 0, intent2, 0));
                    intent2.putExtra("com.spotify.music.features.spoton.extras.BRAND", "Samsung");
                    intent2.putExtra("com.spotify.music.features.spoton.extras.MODEL", "Galaxy Buds Pro");
                    intent2.putExtra("com.spotify.music.features.spoton.extras.VERSION", this.mEarBudsInfo.deviceSWVer);
                    intent2.putExtra("com.spotify.music.features.spoton.extras.DEVICE_NAME", "SM-R190");
                    this.mContext.sendBroadcast(intent2);
                    SamsungAnalyticsUtil.sendEvent(SA.Event.TAP_AND_HOLD_OTHERS_APPS, (String) null, "a");
                    break;
                }
                break;
            case -102:
                Log.d(TAG, "MsgID.VOICE_WAKE_UP_EVENT");
                if (((MsgVoiceWakeUpEvent) msg).status) {
                    sendSppMessage(new MsgSimple(MsgID.VOICE_WAKE_UP_EVENT, true, (byte) 0));
                    Application.getAomManager().startBixby();
                    Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_VOICE_WAKE_UP_EVENT));
                    break;
                } else {
                    return;
                }
            case -101:
                Log.d(TAG, "MsgID.NOISE_REDUCTION_MODE_UPDATE");
                this.mEarBudsInfo.noiseReduction = ((MsgNoiseReductionModeUpdated) msg).noiseReduction;
                SamsungAnalyticsUtil.setStatusString(SA.Status.ANC, this.mEarBudsInfo.noiseReduction ? "1" : "0");
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_NOISE_REDUCTION_UPDATED));
                break;
            case -100:
                Log.d(TAG, "MsgID.VOICE_WAKE_UP_LISTENING_STATUS");
                Application.getAomManager().setBixbyMic(((MsgVoiceWakeUpListeningStatus) msg).voiceWakeUpListeningStatus);
                break;
            case -98:
                Log.d(TAG, "MsgID.CHECK_THE_FIT_OF_EARBUDS_REUSLT");
                MsgCheckTheFitOfEarbudsResult msgCheckTheFitOfEarbudsResult = (MsgCheckTheFitOfEarbudsResult) msg;
                this.mEarBudsInfo.leftCheckTheFitResult = msgCheckTheFitOfEarbudsResult.leftResult;
                this.mEarBudsInfo.rightCheckTheFitResult = msgCheckTheFitOfEarbudsResult.rightResult;
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_CHECK_THE_FIT_OF_EARBUDS_RESULT));
                break;
            case -95:
                Log.d(TAG, "MsgID.FIND_MY_EARBUDS_STOP");
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_FIND_MY_EARBUDS_STOP));
                break;
            case -93:
                Log.d(TAG, "MsgID.MUTE_EARBUD_STATUS_UPDATED");
                MsgMuteEarbudStatusUpdated msgMuteEarbudStatusUpdated = (MsgMuteEarbudStatusUpdated) msg;
                this.mEarBudsInfo.leftMuteStatus = msgMuteEarbudStatusUpdated.leftStatus;
                this.mEarBudsInfo.rightMuteStatus = msgMuteEarbudStatusUpdated.rightStatus;
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MUTE_EARBUD_STATUS_UPDATED));
                break;
            case -91:
                Log.d(TAG, "MsgID.VOICE_NOTI_STOP");
                Application.getNotificationCoreService().getNotificationTTSCore().stopTTS(false);
                break;
            case -84:
                Log.d(TAG, "MsgID.SET_FMM_CONFIG");
                this.mEarBudsInfo.resetOfSetFmmConfig = ((MsgSetFmmConfig) msg).result;
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_SET_FMM_CONFIG_RESULT));
                break;
            case -83:
                Log.d(TAG, "MsgID.GET_FMM_CONFIG");
                Intent intent3 = new Intent(ACTION_MSG_ID_GET_FMM_CONFIG_RESP);
                intent3.putExtra("getFmmConfig", ((MsgGetFmmConfig) msg).getFmmConfig);
                Util.sendPermissionBroadcast(this.mContext, intent3);
                break;
            case -76:
                Log.d(TAG, "MsgID.FOTA_DEVICE_INFO_SW_VERSION");
                BudsLogManager.sendLog(6, ((MsgFotaDeviceInfoSwVersion) msg).version);
                break;
            case -70:
                Log.d(TAG, "MsgID.FOTA_EMERGENCY");
                Log.d(TAG, "Reason : " + ((MsgFotaEmergency) msg).mReason);
                FotaUtil.setEmergencyFOTAIsRunning(true);
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_FOTA_EMERGENCY));
                sendSppMessage(new MsgFotaEmergency(MsgID.FOTA_EMERGENCY, true, (byte) 0));
                break;
            case 34:
                Log.d(TAG, "MsgID.DEBUG_SKU");
                this.mResponseTimer.remove(34);
                MsgDebugSKU msgDebugSKU = (MsgDebugSKU) msg;
                this.mEarBudsInfo.sku_left = msgDebugSKU.LeftSKU;
                this.mEarBudsInfo.sku_right = msgDebugSKU.RightSKU;
                if (this.mEarBudsInfo.sku_left == null && this.mEarBudsInfo.sku_right == null) {
                    this.mFotaInfo.salesCode = "";
                } else if (this.mEarBudsInfo.sku_left == null && this.mEarBudsInfo.sku_right != null) {
                    this.mFotaInfo.salesCode = this.mEarBudsInfo.sku_right.substring(this.mEarBudsInfo.sku_right.length() - 3, this.mEarBudsInfo.sku_right.length());
                } else if (this.mEarBudsInfo.sku_left == null || this.mEarBudsInfo.sku_right != null) {
                    this.mFotaInfo.salesCode = this.mEarBudsInfo.sku_right.substring(this.mEarBudsInfo.sku_right.length() - 3, this.mEarBudsInfo.sku_right.length());
                } else {
                    this.mFotaInfo.salesCode = this.mEarBudsInfo.sku_left.substring(this.mEarBudsInfo.sku_left.length() - 3, this.mEarBudsInfo.sku_left.length());
                }
                Preferences.putString(PreferenceKey.DEVICE_FOTA_SKU, this.mFotaInfo.salesCode, UhmFwUtil.getLastLaunchDeviceId());
                break;
            case 38:
                Log.d(TAG, "MsgID.DEBUG_ALL_DATA");
                this.mResponseTimer.remove(38);
                this.mEarBudsInfo.debugInfo = ((MsgDebugData) msg).debugdata;
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_DEBUG_GET_ALL_DATA));
                break;
            case 41:
                Log.d(TAG, "MsgID.DEBUG_SERIAL_NUMBER");
                this.mResponseTimer.remove(41);
                MsgDebugSerialNumber msgDebugSerialNumber = (MsgDebugSerialNumber) msg;
                this.mEarBudsInfo.serialNumber_left = msgDebugSerialNumber.SerialNumberLeft;
                this.mEarBudsInfo.serialNumber_right = msgDebugSerialNumber.SerialNumberRight;
                if (this.mEarBudsInfo.serialNumber_left == null && this.mEarBudsInfo.serialNumber_right == null) {
                    this.mFotaInfo.serialNumber = "";
                } else if (this.mEarBudsInfo.serialNumber_left == null && this.mEarBudsInfo.serialNumber_right != null) {
                    this.mFotaInfo.serialNumber = this.mEarBudsInfo.serialNumber_right;
                } else if (this.mEarBudsInfo.serialNumber_left == null || this.mEarBudsInfo.serialNumber_right != null) {
                    this.mFotaInfo.serialNumber = this.mEarBudsInfo.serialNumber_right;
                } else {
                    this.mFotaInfo.serialNumber = this.mEarBudsInfo.serialNumber_left;
                }
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_DEBUG_SERIAL_NUMBER));
                break;
            case 65:
                Log.d(TAG, "MsgID.METERING");
                MsgMeteringReport msgMeteringReport = (MsgMeteringReport) msg;
                Intent intent4 = new Intent();
                intent4.setAction(DeviceLogManager.ACTION_METERING_REPORT_ISSUETRACKER);
                intent4.setClassName(DeviceLogManager.PACKAGENAME_ISSUE_TRACKER, DeviceLogManager.CLASSNAME_ISSUE_TRACKER);
                intent4.putExtra("connectionSide", msgMeteringReport.connectedSide);
                intent4.putExtra("totalBatteryCapacity", msgMeteringReport.totalBatteryCapacity);
                intent4.putExtra("batlv", msgMeteringReport.battery);
                intent4.putExtra("a2dpUsage", msgMeteringReport.a2dpUsingTime);
                intent4.putExtra("escoOpen", msgMeteringReport.escoOpenTime);
                intent4.putExtra("ancOn", msgMeteringReport.ancOnTime);
                intent4.putExtra("ambientOn", msgMeteringReport.ambientOnTime);
                intent4.putExtra(XDBInterface.XDM_SQL_ACCESSORY_MODEL, "Galaxy Buds Pro");
                this.mContext.sendBroadcast(intent4);
                break;
            case 66:
                Log.d(TAG, "MsgID.UNIVERSAL_MSG_ID_ACKNOWLEDGEMENT");
                ((MsgUniversalAcknowledgement) msg).convertMessage(msg.bufRecvFrame);
                break;
            case 74:
                break;
            case 80:
                Log.d(TAG, "MsgID.RESET");
                MsgReset msgReset = (MsgReset) msg;
                this.mEarBudsInfo.resultOfReset = msgReset.result;
                if (msgReset.result) {
                    onResetManager();
                }
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_RESET));
                break;
            case 96:
                Log.d(TAG, "MsgID.MSG_ID_STATUS_UPDATED");
                sendSppMessage(new MsgSimple(MsgID.STATUS_UPDATED, true, (byte) 0));
                ((MsgStatusUpdated) msg).applyTo(this.mEarBudsInfo);
                if (this.mCurrentCoupledStatus != this.mEarBudsInfo.coupled) {
                    sendRequestSerialNumber();
                }
                this.mCurrentCoupledStatus = this.mEarBudsInfo.coupled;
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_STATUS_UPDATED));
                break;
            case 97:
                Log.d(TAG, "MsgID.MSG_ID_EXTENDED_STATUS_UPDATED");
                sendSppMessage(new MsgSimple(MsgID.EXTENDED_STATUS_UPDATED, true, (byte) 0));
                sendSppMessage(new MsgManagerInfo());
                ((MsgExtendedStatusUpdated) msg).applyTo(this.mEarBudsInfo);
                SpatialSensorManager.notifySpatialAudioSettingUpdated(this.mEarBudsInfo.spatialAudio);
                SamsungAnalyticsUtil.setStatusString(SA.Status.LOCK_TOUCHPAD, this.mEarBudsInfo.touchpadLocked ? "1" : "0");
                SamsungAnalyticsUtil.setStatusString(SA.Status.TOUCH_AND_HOLD_LEFT, SamsungAnalyticsUtil.touchPadOptionToDetail(this.mEarBudsInfo.touchpadOptionLeft));
                SamsungAnalyticsUtil.setStatusString(SA.Status.TOUCH_AND_HOLD_RIGHT, SamsungAnalyticsUtil.touchPadOptionToDetail(this.mEarBudsInfo.touchpadOptionRight));
                SamsungAnalyticsUtil.setStatusString(SA.Status.GAME_MODE, this.mEarBudsInfo.adjustSoundSync ? "1" : "0");
                SamsungAnalyticsUtil.setStatusString(SA.Status.EQUALIZER_STATUS, SamsungAnalyticsUtil.equalizerTypeToDetail(this.mEarBudsInfo.equalizerType));
                SamsungAnalyticsUtil.setStatusString(SA.Status.USE_AMBIENT_SOUND_DURING_CALLS, this.mEarBudsInfo.sideToneStatus ? "1" : "0");
                SamsungAnalyticsUtil.setStatusString(SA.Status.DOUBLE_TAP_SIDE, this.mEarBudsInfo.outsideDoubleTap ? "1" : "0");
                SamsungAnalyticsUtil.setStatusString(SA.Status.EXTRA_HIGH_VOLUME_AMBIENT, this.mEarBudsInfo.extraHighAmbient ? "1" : "0");
                SamsungAnalyticsUtil.setStatusString(SA.Status.VOICE_WAKE_UP, this.mEarBudsInfo.voiceWakeUp ? "1" : "0");
                SamsungAnalyticsUtil.setNoiseControlsStatus(this.mEarBudsInfo.noiseControls);
                SamsungAnalyticsUtil.setStatusInt(SA.Status.ACTIVE_NOISE_CANCELLING_LEVEL_STATUS, this.mEarBudsInfo.noiseReductionLevel);
                SamsungAnalyticsUtil.setStatusInt(SA.Status.AMBIENT_SOUND_VOLUME_STATUS, this.mEarBudsInfo.ambientSoundLevel);
                SamsungAnalyticsUtil.setStatusInt(SA.Status.VOICE_DETECT_STATUS, this.mEarBudsInfo.detectConversations ? 1 : 0);
                SamsungAnalyticsUtil.setStatusString(SA.Status.CONVERSATION_MODE_TIME_SETTING, SamsungAnalyticsUtil.conversationModeEndTimeIndexToDetail(this.mEarBudsInfo.detectConversationsDuration));
                SamsungAnalyticsUtil.setStatusString(SA.Status.SET_NOISE_CONTROLS, SamsungAnalyticsUtil.makeSetNoiseControlsDetail(this.mEarBudsInfo.noiseControlsAnc, this.mEarBudsInfo.noiseControlsAmbient, this.mEarBudsInfo.noiseControlsOff));
                SamsungAnalyticsUtil.setStatusInt(SA.Status.SEAMLESS_EARBUD_CONNECTION_STATUS, this.mEarBudsInfo.seamlessConnection ? 1 : 0);
                SamsungAnalyticsUtil.setStatusInt(SA.Status._3D_AUDIO_FOR_VIDEOS_STATUS, this.mEarBudsInfo.spatialAudio ? 1 : 0);
                if (!this.mEarBudsInfo.speakSeamlessly) {
                    i = 0;
                }
                SamsungAnalyticsUtil.setStatusInt(SA.Status.SPEAK_SEAMLESSLY, i);
                SamsungAnalyticsUtil.setStatusString(SA.Status.SOUND_BALANCE, SamsungAnalyticsUtil.makeSoundBalanceDetail(this.mEarBudsInfo.hearingEnhancements));
                Preferences.putInt(PreferenceKey.EQUALIZER_TYPE, Integer.valueOf(this.mEarBudsInfo.equalizerType));
                Preferences.putInt(PreferenceKey.TOUCHPAD_OPTION_LEFT, Integer.valueOf(this.mEarBudsInfo.touchpadOptionLeft));
                Preferences.putInt(PreferenceKey.TOUCHPAD_OPTION_RIGHT, Integer.valueOf(this.mEarBudsInfo.touchpadOptionRight));
                Preferences.putInt(PreferenceKey.NOISE_CONTROL_STATE, Integer.valueOf(this.mEarBudsInfo.noiseControls));
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_EXTENDED_STATUS_UPDATED));
                this.mWorker.post(new WorkerTask() {
                    /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass2 */

                    @Override // com.samsung.accessory.hearablemgr.common.util.WorkerTask
                    public void execute() {
                        if (!CoreService.this.mExtendedStatusReady) {
                            CoreService.this.mExtendedStatusReady = true;
                            CoreService.this.onExtendedStatusReady();
                        }
                    }
                });
                break;
            case 99:
                Log.d(TAG, "SppMessage.MSG_ID_VERSION_INFO");
                MsgVersionInfo msgVersionInfo = (MsgVersionInfo) msg;
                sendSppMessage(new MsgSimple(MsgID.VERSION_INFO, true, (byte) 0));
                if (msgVersionInfo.Left_SW_version.equals(msgVersionInfo.Right_SW_version) && FotaUtil.getEmergencyFOTAIsRunning()) {
                    FotaUtil.setEmergencyFOTAIsRunning(false);
                    break;
                }
            case 119:
                Log.d(TAG, "MsgID.MSG_ID_NOISE_CONTROLS_UPDATE");
                this.mEarBudsInfo.noiseControls = ((MsgNoiseControlsUpdate) msg).noiseControlsUpdate;
                Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_MSG_ID_NOISE_CONTROLS_UPDATE));
                Preferences.putInt(PreferenceKey.NOISE_CONTROL_STATE, Integer.valueOf(this.mEarBudsInfo.noiseControls));
                SamsungAnalyticsUtil.setNoiseControlsStatus(this.mEarBudsInfo.noiseControls);
                break;
        }
        for (OnSppMessageListener onSppMessageListener : this.mSppMessageListener) {
            onSppMessageListener.onSppMessage(msg);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onExtendedStatusReady() {
        Log.d(TAG, "onExtendedStatusReady()");
        Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_DEVICE_EXTENDED_STATUS_READY));
    }

    private void onDisconnecting(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onDisconnecting()... : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
        Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_DEVICE_DISCONNECTING));
    }

    private void onDisconnected(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onDisconnected() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
        this.mConnectedDevice = null;
        this.mExtendedStatusReady = false;
        if (this.mOnDisconnectFromConnect) {
            this.mOnDisconnectFromConnect = false;
            onDisconnectFromConnect(bluetoothDevice);
        }
        Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_DEVICE_DISCONNECTED));
    }

    private void onDisconnectFromConnect(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onDisconnectFromConnect() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
        Util.sendPermissionBroadcast(this.mContext, new Intent(ACTION_DEVICE_DISCONNECTED_FROM_CONNECT));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onHeadsetConnecting(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onHeadsetConnecting() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
        if (Util.equalsIgnoreCase(bluetoothDevice.getAddress(), getLastLaunchDeviceAddress()) && getConnectionState() == 0) {
            updateConnectionState(bluetoothDevice, 1);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onHeadsetConnected(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onHeadsetConnected() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
        if (Util.equalsIgnoreCase(bluetoothDevice.getAddress(), getLastLaunchDeviceAddress()) && this.mSppConnectionManager.getConnectionState() == 0) {
            connectSppByProfile(bluetoothDevice);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onA2dpConnecting(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onA2dpConnecting() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
        if (Util.equalsIgnoreCase(bluetoothDevice.getAddress(), getLastLaunchDeviceAddress()) && getConnectionState() == 0) {
            updateConnectionState(bluetoothDevice, 1);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onA2dpConnected(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onA2dpConnected() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
        if (Util.equalsIgnoreCase(bluetoothDevice.getAddress(), getLastLaunchDeviceAddress()) && this.mSppConnectionManager.getConnectionState() == 0) {
            connectSppByProfile(bluetoothDevice);
        }
    }

    private void onResetManager() {
        Log.d(TAG, "onResetManager");
        disconnectDevice();
    }

    public void connectToDevice() {
        Log.d(TAG, "connectToDevice() : " + BluetoothUtil.privateAddress(getLastLaunchDeviceAddress()));
        disconnectOtherDevice(getLastLaunchDeviceAddress());
        this.mWorker.post(new WorkerTask() {
            /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass1TaskConnectToDevice */

            @Override // com.samsung.accessory.hearablemgr.common.util.WorkerTask
            public void execute() {
                BluetoothDevice bondedDevice = BluetoothUtil.getBondedDevice(CoreService.this.getLastLaunchDeviceAddress());
                if (bondedDevice == null) {
                    Log.e(this.TAG, "TaskConnectToDevice() : device == null !!!");
                    return;
                }
                BluetoothManager bluetoothManager = Application.getBluetoothManager();
                synchronized (bluetoothManager) {
                    if (bluetoothManager.isReady()) {
                        bluetoothManager.connectHeadset(bondedDevice);
                        bluetoothManager.connectA2dp(bondedDevice);
                        if ((bluetoothManager.getHeadsetState(bondedDevice) == 2 || bluetoothManager.getA2dpState(bondedDevice) == 2) && !BluetoothUtil.isConnecting(CoreService.this.mSppConnectionManager.getConnectionState())) {
                            CoreService.this.mSppConnectionManager.connect(bondedDevice);
                        }
                    } else {
                        Log.e(this.TAG, "TaskConnectToDevice() : BluetoothManager.isReady() == false !!!");
                    }
                }
            }
        });
    }

    public void disconnectSpp() {
        Log.d(TAG, "disconnectSpp() : " + BluetoothUtil.privateAddress(this.mConnectedDevice));
        this.mSppConnectionManager.disconnect();
    }

    public void disconnectOtherDevice(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "disconnectOtherDevice() : curAddress is empty");
        } else if (isConnected() && !isConnected(str)) {
            Log.w(TAG, "disconnectOtherDevice() : " + BluetoothUtil.deviceToString(this.mConnectedDevice));
            disconnectSpp();
        }
    }

    public void disconnectDevice() {
        Log.d(TAG, "disconnectDevice() : " + BluetoothUtil.privateAddress(this.mConnectedDevice));
        if (Util.isEmulator()) {
            emulateDisconnected();
        } else {
            this.mWorker.post(new WorkerTask() {
                /* class com.samsung.accessory.hearablemgr.core.service.CoreService.AnonymousClass1TaskDisconnectToDevice */

                @Override // com.samsung.accessory.hearablemgr.common.util.WorkerTask
                public void execute() {
                    BluetoothManager bluetoothManager = Application.getBluetoothManager();
                    synchronized (bluetoothManager) {
                        if (bluetoothManager.isReady()) {
                            bluetoothManager.disconnectHeadset(CoreService.this.mConnectedDevice);
                            bluetoothManager.disconnectA2dp(CoreService.this.mConnectedDevice);
                            CoreService.this.disconnectSpp();
                        } else {
                            Log.e(this.TAG, "TaskDisconnectToDevice() : BluetoothManager.isReady() == false !!!");
                        }
                    }
                }
            });
        }
    }

    @Override // com.samsung.accessory.hearablemgr.core.gamemode.GameModeManager.SupportService
    public EarBudsInfo getEarBudsInfo() {
        return this.mEarBudsInfo;
    }

    public EarBudsFotaInfo getEarBudsFotaInfo() {
        return this.mFotaInfo;
    }

    @Override // com.samsung.accessory.hearablemgr.core.gamemode.GameModeManager.SupportService
    public boolean isConnected() {
        if (Util.isEmulator()) {
            return this.mEmulatingConnected;
        }
        return this.mConnectedDevice != null;
    }

    public boolean isConnected(String str) {
        BluetoothDevice bluetoothDevice = this.mConnectedDevice;
        return bluetoothDevice != null && Util.equalsIgnoreCase(bluetoothDevice.getAddress(), str);
    }

    public BluetoothDevice getConnectedDevice() {
        return this.mConnectedDevice;
    }

    public int getConnectionState() {
        if (!Util.isEmulator() || !this.mEmulatingConnected) {
            return this.mConnectionState;
        }
        return 2;
    }

    public boolean isExtendedStatusReady() {
        return this.mExtendedStatusReady;
    }

    @Override // com.samsung.accessory.hearablemgr.core.gamemode.GameModeManager.SupportService
    public void sendSppMessage(Msg msg) {
        this.mSppConnectionManager.sendMessage(msg);
    }

    public DeviceLogManager getDeviceLogInfo() {
        return this.mDeviceLogManager;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getLastLaunchDeviceAddress() {
        return UhmFwUtil.getLastLaunchDeviceId();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void checkHeadsetA2dpDisconnected(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "checkHeadsetA2dpDisconnected()");
        if (!isCurrentDevice(bluetoothDevice) || Application.getBluetoothManager().getHeadsetState(bluetoothDevice) != 0 || Application.getBluetoothManager().getA2dpState(bluetoothDevice) != 0) {
            return;
        }
        if (this.mSppConnectionManager.getConnectionState() != 0) {
            Log.d(TAG, "Disconnect SPP by profiles : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
            disconnectSpp();
        } else if (getConnectionState() != 0) {
            updateConnectionState(bluetoothDevice, 0);
        }
    }

    private void connectSppByProfile(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "connectSppByProfile() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
        this.mSppConnectionManager.connect(bluetoothDevice);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateConnectionState(BluetoothDevice bluetoothDevice, int i) {
        Log.d(TAG, "updateConnectionState()");
        BluetoothManager bluetoothManager = Application.getBluetoothManager();
        synchronized (bluetoothManager) {
            int headsetState = bluetoothManager.getHeadsetState(bluetoothDevice);
            int a2dpState = bluetoothManager.getA2dpState(bluetoothDevice);
            Log.d(TAG, "TaskUpdateConnectionState() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()) + " " + BluetoothUtil.stateToString(this.mConnectionState) + " (spp=" + BluetoothUtil.stateToString(i) + ", hfp=" + BluetoothUtil.stateToString(headsetState) + ", a2dp=" + BluetoothUtil.stateToString(a2dpState) + ")");
            if ((i != 3 || this.mConnectionState != 0) && (i != 1 || this.mConnectionState != 2)) {
                setConnectionState(bluetoothDevice, i);
            }
        }
    }

    private void setConnectionState(BluetoothDevice bluetoothDevice, int i) {
        int i2;
        if (i != this.mConnectionState) {
            Log.i(TAG, "setConnectionState() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()) + " " + BluetoothUtil.stateToString(i) + " (from " + BluetoothUtil.stateToString(this.mConnectionState) + ")");
            if (i == 0 && ((i2 = this.mConnectionState) == 3 || i2 == 2)) {
                this.mOnDisconnectFromConnect = true;
            }
            this.mConnectionState = i;
            if (i == 0) {
                onDisconnected(bluetoothDevice);
            } else if (i == 1) {
                onConnecting(bluetoothDevice);
            } else if (i == 2) {
                onConnected(bluetoothDevice);
            } else if (i == 3) {
                onDisconnecting(bluetoothDevice);
            }
        }
    }

    private boolean isCurrentDevice(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return false;
        }
        String address = bluetoothDevice.getAddress();
        BluetoothDevice bluetoothDevice2 = this.mConnectedDevice;
        return Util.equalsIgnoreCase(address, bluetoothDevice2 != null ? bluetoothDevice2.getAddress() : getLastLaunchDeviceAddress());
    }

    /* access modifiers changed from: private */
    public static boolean isPluginDevice(BluetoothDevice bluetoothDevice) {
        String name;
        if (bluetoothDevice == null || (name = bluetoothDevice.getName()) == null) {
            return false;
        }
        if (name.contains("Galaxy Buds Pro ") || name.contains("Galaxy Buds Pro ")) {
            return true;
        }
        return false;
    }

    public void registerSppMessageListener(OnSppMessageListener onSppMessageListener) {
        this.mSppMessageListener.add(onSppMessageListener);
    }

    public void unregisterSppMessageListener(OnSppMessageListener onSppMessageListener) {
        if (onSppMessageListener == null) {
            this.mSppMessageListener.clear();
        } else {
            this.mSppMessageListener.remove(onSppMessageListener);
        }
    }

    public void startFotaInstall(String str) {
        Log.d(TAG, "startFotaInstall : " + str);
        Log.d(TAG, "mEarBudsInfo.deviceSWVer : " + Application.getCoreService().getEarBudsInfo().deviceSWVer);
        Log.d(TAG, "FOTA start");
        FotaTransferManager fotaTransferManager = this.mFOTATransferManager;
        if (fotaTransferManager != null) {
            fotaTransferManager.startFota(str);
        }
    }

    public int getLatestFOTAProgress() {
        return this.mFOTATransferManager.getLatestFOTAProgress();
    }

    private void sendRequestSerialNumber() {
        sendSppMessage(new MsgDebugSerialNumber());
        WaitTimer waitTimer = this.mResponseTimer;
        if (waitTimer != null) {
            waitTimer.start(41, UpdateInterface.HOLDING_AFTER_BT_CONNECTED);
        }
    }

    private void sendRequestDebugGetAllData() {
        sendSppMessage(new MsgDebugData());
        WaitTimer waitTimer = this.mResponseTimer;
        if (waitTimer != null) {
            waitTimer.start(38, UpdateInterface.HOLDING_AFTER_BT_CONNECTED);
        }
    }

    private void sendRequestDebugSKU() {
        sendSppMessage(new MsgDebugSKU());
        WaitTimer waitTimer = this.mResponseTimer;
        if (waitTimer != null) {
            waitTimer.start(34, UpdateInterface.HOLDING_AFTER_BT_CONNECTED);
        }
    }

    public ArrayList<HashMap<String, String>> checkApp2App() {
        List<ResolveInfo> queryBroadcastReceivers = this.mContext.getPackageManager().queryBroadcastReceivers(new Intent(Util.SEND_PUI_EVENT), XDMWbxml.WBXML_EXT_0);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        Log.d(TAG, "receivers.size: " + queryBroadcastReceivers.size());
        for (ResolveInfo resolveInfo : queryBroadcastReceivers) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            Bundle bundle = activityInfo.metaData;
            if (!(activityInfo == null || bundle == null)) {
                String str = activityInfo.packageName;
                String string = bundle.getString(RoutineConstants.APP_TO_APP_KEY_MENU_NAME);
                String string2 = bundle.getString("description");
                if (!(string == null || string2 == null)) {
                    Log.d(TAG, "menuName : " + string);
                    Log.d(TAG, "description :" + string2);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(BaseContentProvider.PACKAGE_NAME, str);
                    hashMap.put(RoutineConstants.APP_TO_APP_KEY_MENU_NAME, string);
                    hashMap.put("description", string2);
                    arrayList.add(hashMap);
                }
            }
        }
        return arrayList;
    }
}
