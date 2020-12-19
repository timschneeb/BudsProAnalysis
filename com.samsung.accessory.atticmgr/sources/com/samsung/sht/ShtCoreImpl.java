package com.samsung.sht;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import com.accessorydm.interfaces.XCommonInterface;
import com.samsung.accessory.hearablemgr.library.SpatialSensorInterface;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.fotaagent.update.UpdateInterface;
import com.samsung.sht.audio.AudioManagerHelper;
import com.samsung.sht.audio.AudioPathHelper;
import com.samsung.sht.audio.DolbyAtmosHelper;
import com.samsung.sht.audio.DolbyAtmosHelperImpl;
import com.samsung.sht.audio.SoundAliveHelper;
import com.samsung.sht.floating.CompassWindow;
import com.samsung.sht.floating.DebugModeHelper;
import com.samsung.sht.log.ShtLog;
import com.samsung.sht.multimedia.MultimediaHelper;
import com.samsung.sht.sensor.RelativeQuat;
import com.samsung.sht.sensor.WearAttitudeInfo;
import com.samsung.sht.spp.SppRecvHelper;
import com.samsung.sht.spp.SppSendHelper;
import java.util.Arrays;
import java.util.List;

public class ShtCoreImpl implements SpatialSensorInterface {
    private static final long ASK_WEAR_ON_OFF_INTERVAL_MS = 4000;
    private static final long ATTITUDE_LOG_INTERVAL_MS = 5000;
    private static final long BUD_GRV_LOG_INTERVAL = 5000;
    private static final String INTENT_MEDIA_SERVER_REBOOTED = "com.samsung.intent.action.MEDIA_SERVER_REBOOTED";
    private static final long KEEP_ALIVE_ACK_INVERVAL_MS = 2000;
    private static final int MSG_ON_AUDIO_PATH_UPDATED = 10;
    private static final int MSG_ON_DESTROY = 6;
    private static final int MSG_ON_MEDIA_STARTED = 4;
    private static final int MSG_ON_MEDIA_STOPPED = 5;
    private static final int MSG_ON_PRIMARY_UPDATED = 11;
    private static final int MSG_ON_REL_EULER_UPDATED = 9;
    private static final int MSG_ON_SCREEN_UPDATED = 8;
    private static final int MSG_ON_SETTING_UPDATED = 1;
    private static final int MSG_ON_SPP_CONNECTED = 2;
    private static final int MSG_ON_SPP_DISCONNECTED = 3;
    private static final int MSG_ON_WEAR_UPDATED = 7;
    private static final String TAG = "ShtCoreImpl";
    private String curBtAddress = null;
    private boolean isAudioPathProper = false;
    private boolean isDebugModeOn = false;
    private boolean isMediaRunning = false;
    private boolean isPrimaryRight = true;
    private boolean isScreenOn = false;
    private boolean isSettingOn = false;
    private boolean isShtRunning = false;
    private boolean isSppConnected = false;
    private boolean isWearing = false;
    private SpatialSensorInterface.SupportApi mApi = null;
    private AskWearOnOffTask mAskWearOnOffTask = null;
    private AudioManager mAudioManager = null;
    private AudioManagerHelper mAudioManagerHelper = null;
    private AudioPathHelper.Callback mAudioPathCallback = new AudioPathHelper.Callback() {
        /* class com.samsung.sht.ShtCoreImpl.AnonymousClass3 */

        @Override // com.samsung.sht.audio.AudioPathHelper.Callback
        public void onProperAudioPath() {
            ShtLog.d("onProperAudioPath");
            onAudioPathUpdated(true);
        }

        @Override // com.samsung.sht.audio.AudioPathHelper.Callback
        public void onImproperAudioPath() {
            ShtLog.i("onImproperAudioPath");
            onAudioPathUpdated(false);
        }

        private void onAudioPathUpdated(boolean z) {
            if (ShtCoreImpl.this.mHandler != null) {
                ShtCoreImpl.this.mHandler.obtainMessage(10, new Boolean(z)).sendToTarget();
            }
        }
    };
    private AudioPathHelper mAudioPathHelper = null;
    private CompassWindow mCompassWindow = null;
    private HandlerThread mCompassWindowThread = null;
    private Context mContext = null;
    private DolbyAtmosHelper mDolbyAtmosHelper = null;
    private ShtHandler mHandler = null;
    private KeepAliveTask mKeepAliveTask = null;
    private BroadcastReceiver mMediaServerRebootIntentReceiver = new BroadcastReceiver() {
        /* class com.samsung.sht.ShtCoreImpl.AnonymousClass2 */

        public void onReceive(Context context, Intent intent) {
            ShtLog.d("Intent Received:" + intent.getAction());
            if (intent.getAction().equals(ShtCoreImpl.INTENT_MEDIA_SERVER_REBOOTED)) {
                if (ShtCoreImpl.this.isSettingOn) {
                    if (ShtCoreImpl.this.mAudioManagerHelper != null) {
                        ShtCoreImpl.this.mAudioManagerHelper.setSpatialAudioSettingOn();
                    }
                } else if (ShtCoreImpl.this.mAudioManagerHelper != null) {
                    ShtCoreImpl.this.mAudioManagerHelper.setSpatialAudioSettingOff();
                }
                if (ShtCoreImpl.this.isShtRunning) {
                    if (ShtCoreImpl.this.mAudioManagerHelper != null) {
                        ShtCoreImpl.this.mAudioManagerHelper.setSpatialAudioOn();
                    }
                } else if (ShtCoreImpl.this.mAudioManagerHelper != null) {
                    ShtCoreImpl.this.mAudioManagerHelper.setSpatialAudioOff();
                }
                if (ShtCoreImpl.this.mDolbyAtmosHelper != null) {
                    ShtCoreImpl.this.mDolbyAtmosHelper.onMediaServerReboot(ShtCoreImpl.this.isShtRunning);
                }
            }
        }
    };
    private MultimediaHelper mMultimediaHelper = null;
    private MultimediaHelper.Callback mMultimediaHelperCallback = new MultimediaHelper.Callback() {
        /* class com.samsung.sht.ShtCoreImpl.AnonymousClass1 */

        @Override // com.samsung.sht.multimedia.MultimediaHelper.Callback
        public void onMediaStarted() {
            if (ShtCoreImpl.this.mHandler != null) {
                ShtCoreImpl.this.mHandler.obtainMessage(4).sendToTarget();
            }
        }

        @Override // com.samsung.sht.multimedia.MultimediaHelper.Callback
        public void onMediaStopped() {
            ShtCoreImpl.this.mHandler.obtainMessage(5).sendToTarget();
        }
    };
    private HandlerThread mMultimediaThread = null;
    private float[] mPrevAttitude = null;
    private long mPrevAttitudeLogTimestamp = 0;
    private long mPrevBudGrvLogTimestamp = 0;
    private long mReceivedGrvCnt = 0;
    private HandlerThread mRelQuatThread = null;
    private RelativeQuat mRelativeQuat = null;
    private RelativeQuat.Callback mRelativeQuatCallback = new RelativeQuat.Callback() {
        /* class com.samsung.sht.ShtCoreImpl.AnonymousClass6 */

        @Override // com.samsung.sht.sensor.RelativeQuat.Callback
        public void onHeadsetGrvNotUpdated() {
        }

        @Override // com.samsung.sht.sensor.RelativeQuat.Callback
        public void onRelativeEulerUpdated(float f, float f2, float f3) {
            ShtCoreImpl.this.mHandler.removeMessages(9);
            ShtCoreImpl.this.mHandler.obtainMessage(9, new float[]{f, f2, f3}).sendToTarget();
        }
    };
    private BroadcastReceiver mScreenIntentReceiver = new BroadcastReceiver() {
        /* class com.samsung.sht.ShtCoreImpl.AnonymousClass5 */

        public void onReceive(Context context, Intent intent) {
            ShtLog.i("Intent Received:" + intent.getAction());
            if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                if (ShtCoreImpl.this.mHandler != null) {
                    ShtCoreImpl.this.mHandler.obtainMessage(8, new Boolean(true)).sendToTarget();
                }
            } else if (intent.getAction().equals("android.intent.action.SCREEN_OFF") && ShtCoreImpl.this.mHandler != null) {
                ShtCoreImpl.this.mHandler.obtainMessage(8, new Boolean(false)).sendToTarget();
            }
        }
    };
    private SppRecvHelper mSppRecvHelper = null;
    private SppRecvHelper.Callback mSppRecvHelperCallback = new SppRecvHelper.Callback() {
        /* class com.samsung.sht.ShtCoreImpl.AnonymousClass4 */

        @Override // com.samsung.sht.spp.SppRecvHelper.Callback
        public void onBudGrvUpdated(List<float[]> list) {
            if (ShtCoreImpl.this.mRelativeQuat != null) {
                ShtCoreImpl.this.mRelativeQuat.updateHeadsetQuat(list.get(list.size() - 1));
            }
            if (ShtCoreImpl.this.mPrevBudGrvLogTimestamp != 0) {
                ShtCoreImpl.access$708(ShtCoreImpl.this);
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - ShtCoreImpl.this.mPrevBudGrvLogTimestamp >= XCommonInterface.WAKE_LOCK_TIMEOUT) {
                    ShtLog.i("For last interval(" + (currentTimeMillis - ShtCoreImpl.this.mPrevBudGrvLogTimestamp) + "ms), " + ShtCoreImpl.this.mReceivedGrvCnt + " grv received");
                    ShtCoreImpl.this.mPrevBudGrvLogTimestamp = currentTimeMillis;
                    ShtCoreImpl.this.mReceivedGrvCnt = 0;
                }
            }
            if (ShtCoreImpl.this.isDebugModeOn) {
                float[] fArr = list.get(list.size() - 1);
                ShtCoreImpl.this.mCompassWindow.setText(fArr[0] + "," + fArr[1] + "," + fArr[2] + "," + fArr[3]);
            }
        }

        @Override // com.samsung.sht.spp.SppRecvHelper.Callback
        public void onWearOnOffUpdated(boolean z, boolean z2) {
            if (ShtCoreImpl.this.mHandler != null) {
                ShtCoreImpl.this.mHandler.obtainMessage(11, new Boolean(z2)).sendToTarget();
                ShtCoreImpl.this.mHandler.removeMessages(7);
                Message obtainMessage = ShtCoreImpl.this.mHandler.obtainMessage(7, new Boolean(z));
                if (ShtCoreImpl.this.isWearing || !z) {
                    ShtCoreImpl.this.mHandler.sendMessage(obtainMessage);
                } else {
                    ShtCoreImpl.this.mHandler.sendMessageDelayed(obtainMessage, UpdateInterface.HOLDING_AFTER_BT_CONNECTED);
                }
            }
        }

        @Override // com.samsung.sht.spp.SppRecvHelper.Callback
        public void onAddSuccess() {
            ShtLog.i("Add successful ack");
        }

        @Override // com.samsung.sht.spp.SppRecvHelper.Callback
        public void onRemoveSuccess() {
            ShtLog.i("Remove successful ack");
        }
    };
    private SppSendHelper mSppSendHelper = null;
    private HandlerThread mThread = null;

    static /* synthetic */ long access$708(ShtCoreImpl shtCoreImpl) {
        long j = shtCoreImpl.mReceivedGrvCnt;
        shtCoreImpl.mReceivedGrvCnt = 1 + j;
        return j;
    }

    protected static boolean isFeatureSupported(Context context) {
        boolean z = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_HEADTRACKING_EFFECT");
        ShtLog.i("isFeatureSupported - Feature(" + z + ")");
        return z;
    }

    protected ShtCoreImpl() {
    }

    @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface
    public void onCreate(SpatialSensorInterface.SupportApi supportApi, boolean z) {
        ShtLog.i(TAG, "onCreate : " + z);
        this.mApi = supportApi;
        this.mContext = this.mApi.getContext();
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.mThread = new HandlerThread(":ShtCoreThread");
        this.mThread.start();
        this.mHandler = new ShtHandler(this.mThread.getLooper());
        this.mMultimediaThread = new HandlerThread(":MultimediaThread");
        this.mMultimediaThread.start();
        this.mMultimediaHelper = new MultimediaHelper(this.mMultimediaHelperCallback, this.mAudioManager, this.mMultimediaThread.getLooper());
        registerMediaMonitor();
        this.mRelQuatThread = new HandlerThread(":RelQuatThread");
        this.mRelQuatThread.start();
        this.mRelativeQuat = new RelativeQuat(this.mContext, this.mRelativeQuatCallback, this.mRelQuatThread.getLooper());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(INTENT_MEDIA_SERVER_REBOOTED);
        this.mContext.registerReceiver(this.mMediaServerRebootIntentReceiver, intentFilter);
        this.mDolbyAtmosHelper = new DolbyAtmosHelperImpl(this.mContext);
        this.mAudioPathHelper = new AudioPathHelper(this.mContext, this.mAudioManager, this.mHandler.getLooper());
        this.mAudioPathHelper.startMonitoring(this.mAudioPathCallback);
        this.mSppSendHelper = new SppSendHelper();
        this.mSppRecvHelper = new SppRecvHelper(this.mSppRecvHelperCallback);
        this.mKeepAliveTask = new KeepAliveTask();
        this.mAskWearOnOffTask = new AskWearOnOffTask();
        this.mHandler.post(this.mAskWearOnOffTask);
        this.mPrevAttitudeLogTimestamp = 0;
        this.mPrevAttitude = null;
        this.mPrevBudGrvLogTimestamp = 0;
        this.isScreenOn = ((PowerManager) this.mContext.getSystemService("power")).isInteractive();
        this.mAudioManagerHelper = new AudioManagerHelper(this.mAudioManager);
        this.isDebugModeOn = DebugModeHelper.checkDebugMode(this.mContext);
        if (this.isDebugModeOn) {
            this.mCompassWindowThread = new HandlerThread(":CompassWindowThread");
            this.mCompassWindowThread.start();
            this.mCompassWindow = new CompassWindow(this.mContext, this.mCompassWindowThread.getLooper());
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.SCREEN_ON");
        intentFilter2.addAction("android.intent.action.SCREEN_OFF");
        this.mContext.registerReceiver(this.mScreenIntentReceiver, intentFilter2);
        this.mHandler.obtainMessage(1, new Boolean(z)).sendToTarget();
    }

    @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface
    public void onSettingUpdated(boolean z) {
        ShtHandler shtHandler = this.mHandler;
        if (shtHandler != null) {
            shtHandler.obtainMessage(1, new Boolean(z)).sendToTarget();
        }
    }

    @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface
    public void onSppConnected(String str) {
        ShtHandler shtHandler = this.mHandler;
        if (shtHandler != null) {
            shtHandler.obtainMessage(2, str).sendToTarget();
        }
    }

    @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface
    public void onSppMessageReceived(byte b, byte[] bArr) {
        this.mSppRecvHelper.parse(b, bArr);
    }

    @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface
    public void onSppDisconnected(String str) {
        ShtHandler shtHandler = this.mHandler;
        if (shtHandler != null) {
            shtHandler.obtainMessage(3, str).sendToTarget();
        }
    }

    @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface
    public void onDestroy() {
        ShtLog.i(TAG, "onDestroy");
        this.mContext.unregisterReceiver(this.mScreenIntentReceiver);
        this.mAudioPathHelper.stopMonitoring();
        ShtHandler shtHandler = this.mHandler;
        if (shtHandler != null) {
            shtHandler.obtainMessage(6).sendToTarget();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleSettingUpdated(boolean z) {
        ShtLog.i(TAG, "handleSettingUpdated(" + z + ")/current internal val : " + this.isSettingOn);
        this.isSettingOn = z;
        if (this.isSettingOn) {
            AudioManagerHelper audioManagerHelper = this.mAudioManagerHelper;
            if (audioManagerHelper != null) {
                audioManagerHelper.setSpatialAudioSettingOn();
            }
        } else {
            AudioManagerHelper audioManagerHelper2 = this.mAudioManagerHelper;
            if (audioManagerHelper2 != null) {
                audioManagerHelper2.setSpatialAudioSettingOff();
            }
        }
        updateRunningCondition();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleConnectionUpdated(boolean z, String str) {
        ShtLog.i(TAG, "handleConnectionUpdated(" + z + "," + str + ")/current internal val : " + this.isSppConnected + "," + this.curBtAddress);
        this.isSppConnected = z;
        this.curBtAddress = str;
        AudioPathHelper audioPathHelper = this.mAudioPathHelper;
        if (!z) {
            str = null;
        }
        audioPathHelper.setActiveSppAddress(str);
        updateRunningCondition();
    }

    private void registerMediaMonitor() {
        MultimediaHelper multimediaHelper = this.mMultimediaHelper;
        if (multimediaHelper != null) {
            multimediaHelper.startMonitoring();
            ShtLog.i(TAG, "multimedia monitor registered");
        }
    }

    private void unregisterMediaMonitor() {
        MultimediaHelper multimediaHelper = this.mMultimediaHelper;
        if (multimediaHelper != null) {
            multimediaHelper.stopMonitoring();
            ShtLog.i(TAG, "multimedia monitor unregistered");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleMediaStateChanged(boolean z) {
        ShtLog.i(TAG, "handleMediaStateUpdated(" + z + ")/current internal val : " + this.isMediaRunning);
        this.isMediaRunning = z;
        updateRunningCondition();
    }

    private void updateRunningCondition() {
        ShtLog.i(TAG, "updateRunningCondition : setting(" + this.isSettingOn + "),conn(" + this.isSppConnected + "),media(" + this.isMediaRunning + "),wear(" + this.isWearing + "),screen(" + this.isScreenOn + "),audiopath(" + this.isAudioPathProper + ")");
        if (this.isSettingOn && this.isSppConnected && this.isMediaRunning && this.isWearing && this.isScreenOn && this.isAudioPathProper) {
            startSht();
        } else {
            stopSht();
        }
    }

    private void startSht() {
        ShtLog.i(TAG, "startSht/current state=" + this.isShtRunning);
        if (!this.isShtRunning) {
            this.mHandler.removeCallbacks(this.mKeepAliveTask);
            this.mPrevAttitudeLogTimestamp = 0;
            this.mPrevAttitude = null;
            this.mRelativeQuat.start();
            AudioManagerHelper audioManagerHelper = this.mAudioManagerHelper;
            if (audioManagerHelper != null) {
                audioManagerHelper.setSpatialAudioOn();
            }
            SoundAliveHelper.setSpatialAudioOn(this.mContext);
            this.mHandler.postDelayed(this.mKeepAliveTask, KEEP_ALIVE_ACK_INVERVAL_MS);
            if (this.isDebugModeOn) {
                this.mCompassWindow.show();
            }
            DolbyAtmosHelper dolbyAtmosHelper = this.mDolbyAtmosHelper;
            if (dolbyAtmosHelper != null) {
                dolbyAtmosHelper.setHeadTrackingEnabled(true);
            }
            ShtLog.i("Request Add to Bud");
            this.mSppSendHelper.sendAddMsg(this.mApi);
            this.mPrevBudGrvLogTimestamp = System.currentTimeMillis();
            this.mReceivedGrvCnt = 0;
            this.isShtRunning = true;
        }
    }

    private void stopSht() {
        ShtLog.i(TAG, "stopSht/current state=" + this.isShtRunning);
        if (this.isShtRunning) {
            this.mHandler.removeCallbacks(this.mKeepAliveTask);
            ShtLog.i("Request remove to bud");
            this.mSppSendHelper.sendRemoveMsg(this.mApi);
            this.mRelativeQuat.stop();
            AudioManagerHelper audioManagerHelper = this.mAudioManagerHelper;
            if (audioManagerHelper != null) {
                audioManagerHelper.setSpatialAudioOff();
            }
            SoundAliveHelper.setSpatialAudioff(this.mContext);
            if (this.isDebugModeOn) {
                this.mCompassWindow.dismiss();
            }
            DolbyAtmosHelper dolbyAtmosHelper = this.mDolbyAtmosHelper;
            if (dolbyAtmosHelper != null) {
                dolbyAtmosHelper.setHeadTrackingEnabled(false);
            }
            this.mPrevBudGrvLogTimestamp = 0;
            this.isShtRunning = false;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleDestroy() {
        ShtLog.i(TAG, "handleDestroy");
        this.mHandler.removeCallbacks(this.mKeepAliveTask);
        this.mHandler.removeCallbacks(this.mAskWearOnOffTask);
        this.mHandler = null;
        unregisterMediaMonitor();
        if (this.isShtRunning) {
            stopSht();
        }
        this.mCompassWindowThread.quitSafely();
        this.mThread.quitSafely();
        this.mRelQuatThread.quitSafely();
        this.mMultimediaThread.quitSafely();
    }

    private void handleGrv(List<float[]> list) {
        if (!this.isShtRunning) {
            ShtLog.e("handleGrv - error SHT not running");
        } else {
            this.mRelativeQuat.updateHeadsetQuat(list.get(list.size() - 1));
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleWearUpdated(boolean z) {
        ShtLog.i(TAG, "wear updated-" + z);
        this.isWearing = z;
        updateRunningCondition();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleScreenUpdated(boolean z) {
        ShtLog.i(TAG, "screen updated-" + z);
        this.isScreenOn = z;
        updateRunningCondition();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleRelEulerUpdated(float[] fArr) {
        long currentTimeMillis = System.currentTimeMillis();
        AudioManagerHelper audioManagerHelper = this.mAudioManagerHelper;
        if (audioManagerHelper != null) {
            audioManagerHelper.updateAttitude(fArr[0], fArr[1], fArr[2]);
        }
        if (this.isDebugModeOn) {
            this.mCompassWindow.setAzimuth(fArr[0]);
        }
        float[] fArr2 = this.mPrevAttitude;
        boolean z = fArr2 != null && (Math.abs(fArr2[0] - fArr[0]) > 10.0f || Math.abs(this.mPrevAttitude[1] - fArr[1]) > 5.0f);
        long j = this.mPrevAttitudeLogTimestamp;
        boolean z2 = j == 0 || currentTimeMillis - j >= XCommonInterface.WAKE_LOCK_TIMEOUT;
        if (z || z2) {
            ShtLog.i("Current attitude ts," + currentTimeMillis + ",y," + fArr[0] + ",p," + fArr[1] + ",r," + fArr[2]);
            this.mPrevAttitudeLogTimestamp = currentTimeMillis;
            this.mPrevAttitude = Arrays.copyOf(fArr, 3);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleAudioPathUpdated(boolean z) {
        ShtLog.i(TAG, "audio path updated-" + z);
        this.isAudioPathProper = z;
        updateRunningCondition();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handlePrimaryUpdated(boolean z) {
        ShtLog.i(TAG, "Primary updated : " + z + "/ current =" + this.isPrimaryRight);
        this.isPrimaryRight = z;
        this.mRelativeQuat.updateWearAttitude(WearAttitudeInfo.ATTITUDE[0][z ? 1 : 0]);
    }

    /* access modifiers changed from: private */
    public class ShtHandler extends Handler {
        public ShtHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1:
                    ShtCoreImpl.this.handleSettingUpdated(((Boolean) message.obj).booleanValue());
                    return;
                case 2:
                    ShtCoreImpl.this.handleConnectionUpdated(true, (String) message.obj);
                    return;
                case 3:
                    ShtCoreImpl.this.handleConnectionUpdated(false, (String) message.obj);
                    return;
                case 4:
                    ShtCoreImpl.this.handleMediaStateChanged(true);
                    return;
                case 5:
                    ShtCoreImpl.this.handleMediaStateChanged(false);
                    return;
                case 6:
                    ShtCoreImpl.this.handleDestroy();
                    return;
                case 7:
                    ShtCoreImpl.this.handleWearUpdated(((Boolean) message.obj).booleanValue());
                    return;
                case 8:
                    ShtCoreImpl.this.handleScreenUpdated(((Boolean) message.obj).booleanValue());
                    return;
                case 9:
                    ShtCoreImpl.this.handleRelEulerUpdated((float[]) message.obj);
                    return;
                case 10:
                    ShtCoreImpl.this.handleAudioPathUpdated(((Boolean) message.obj).booleanValue());
                    return;
                case 11:
                    ShtCoreImpl.this.handlePrimaryUpdated(((Boolean) message.obj).booleanValue());
                    return;
                default:
                    ShtLog.e(ShtCoreImpl.TAG, "Handler : wrong msg");
                    return;
            }
        }
    }

    /* access modifiers changed from: private */
    public class KeepAliveTask implements Runnable {
        private KeepAliveTask() {
        }

        public void run() {
            ShtCoreImpl.this.mSppSendHelper.sendAliveMsg(ShtCoreImpl.this.mApi);
            ShtCoreImpl.this.mHandler.postDelayed(this, ShtCoreImpl.KEEP_ALIVE_ACK_INVERVAL_MS);
        }
    }

    /* access modifiers changed from: private */
    public class AskWearOnOffTask implements Runnable {
        private AskWearOnOffTask() {
        }

        public void run() {
            if (!ShtCoreImpl.this.isWearing && ShtCoreImpl.this.isSettingOn && ShtCoreImpl.this.isSppConnected && ShtCoreImpl.this.isMediaRunning && ShtCoreImpl.this.isScreenOn && ShtCoreImpl.this.isAudioPathProper) {
                ShtLog.i("All condition fulfills except wear - ask wear");
                ShtCoreImpl.this.mSppSendHelper.sendRequestWearOnOffMsg(ShtCoreImpl.this.mApi);
            }
            ShtCoreImpl.this.mHandler.postDelayed(this, ShtCoreImpl.ASK_WEAR_ON_OFF_INTERVAL_MS);
        }
    }
}
