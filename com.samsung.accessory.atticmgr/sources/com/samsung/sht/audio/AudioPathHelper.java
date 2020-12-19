package com.samsung.sht.audio;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.samsung.sht.log.ShtLog;

public class AudioPathHelper {
    private static final String ACTION_ACTIVE_DEVICE_INTENT = "android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED";
    private static final String ACTION_DUAL_PLAY_MODE_ENABLED = "com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED";
    private static final long AUDIO_PATH_POLLING_INTERVAL = 1000;
    private static final int AUDIO_PATH_STATUS_SA_NOT_RUNNABLE = 2;
    private static final int AUDIO_PATH_STATUS_SA_RUNNABLE = 1;
    private static final int AUDIO_PATH_STATUS_UNKNOWN = 0;
    private static final int MSG_ACTIVE_A2DP_ADDRESS = 5;
    private static final int MSG_ACTIVE_SPP_ADDRESS = 4;
    private static final int MSG_AUDIO_PATH_TASK = 3;
    private static final int MSG_DUAL_PLAY_MODE_CHANGED = 6;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 2;
    private static final String TAG = AudioPathHelper.class.getSimpleName();
    private boolean isDualPlayMode = false;
    private boolean isRunning = false;
    private BroadcastReceiver mA2dpIntentReceiver = new BroadcastReceiver() {
        /* class com.samsung.sht.audio.AudioPathHelper.AnonymousClass2 */

        public void onReceive(Context context, Intent intent) {
            BluetoothDevice bluetoothDevice;
            String action = intent.getAction();
            String str = AudioPathHelper.TAG;
            ShtLog.i(str, "Intent received : " + action);
            if (action.equals(AudioPathHelper.ACTION_ACTIVE_DEVICE_INTENT)) {
                if (!AudioPathHelper.this.isRunning) {
                    ShtLog.e(AudioPathHelper.TAG, "Active A2dp Changed, but monitor is not running");
                    return;
                }
                Bundle extras = intent.getExtras();
                if (extras != null && (bluetoothDevice = (BluetoothDevice) extras.get("android.bluetooth.device.extra.DEVICE")) != null) {
                    String str2 = AudioPathHelper.TAG;
                    ShtLog.i(str2, "Intent Received: address " + bluetoothDevice.getAddress());
                    AudioPathHelper.this.mHandler.obtainMessage(5, bluetoothDevice.getAddress()).sendToTarget();
                }
            } else if (!action.equals("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED")) {
            } else {
                if (!AudioPathHelper.this.isRunning) {
                    ShtLog.e(AudioPathHelper.TAG, "Dual mode changed, but monitor is not running");
                } else {
                    AudioPathHelper.this.mHandler.obtainMessage(6, new Boolean(intent.getBooleanExtra("enable", true))).sendToTarget();
                }
            }
        }
    };
    private BluetoothA2dp mA2dpProfile = null;
    private BluetoothProfile.ServiceListener mA2dpProfileListener = new BluetoothProfile.ServiceListener() {
        /* class com.samsung.sht.audio.AudioPathHelper.AnonymousClass1 */

        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            if (i == 2) {
                AudioPathHelper.this.mA2dpProfile = (BluetoothA2dp) bluetoothProfile;
                BluetoothDevice semGetActiveStreamDevice = AudioPathHelper.this.mA2dpProfile.semGetActiveStreamDevice();
                if (AudioPathHelper.this.mHandler != null) {
                    AudioPathHelper.this.mHandler.obtainMessage(5, semGetActiveStreamDevice != null ? semGetActiveStreamDevice.getAddress() : null).sendToTarget();
                    AudioPathHelper.this.mHandler.obtainMessage(6, new Boolean(AudioPathHelper.this.mA2dpProfile.semIsDualPlayMode())).sendToTarget();
                }
            }
        }

        public void onServiceDisconnected(int i) {
            if (i == 2) {
                AudioPathHelper.this.mA2dpProfile = null;
                if (AudioPathHelper.this.mHandler != null) {
                    AudioPathHelper.this.mHandler.obtainMessage(5, null).sendToTarget();
                }
            }
        }
    };
    private String mActiveA2dpAddress = null;
    private int mActiveDeviceType = 0;
    private String mActiveSppAddress = null;
    private AudioManager mAudioManager = null;
    private int mAudioPathStatus = 0;
    private Callback mCallback = null;
    private Context mContext = null;
    private Handler mHandler = null;

    public interface Callback {
        void onImproperAudioPath();

        void onProperAudioPath();
    }

    public AudioPathHelper(Context context, AudioManager audioManager, Looper looper) {
        this.mContext = context;
        this.mAudioManager = audioManager;
        this.mHandler = new MyHandler(looper);
        ShtLog.e(TAG, "AudioPathHelper created");
    }

    public void setActiveSppAddress(String str) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.obtainMessage(4, str).sendToTarget();
        }
    }

    public void startMonitoring(Callback callback) {
        ShtLog.e(TAG, "startMonitoring");
        this.mCallback = callback;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.obtainMessage(1).sendToTarget();
        }
    }

    public void stopMonitoring() {
        ShtLog.e(TAG, "stopMonitoring");
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.obtainMessage(2).sendToTarget();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleStart() {
        if (this.isRunning) {
            ShtLog.e(TAG, "start requested, but is already running");
            return;
        }
        this.mAudioPathStatus = 0;
        this.mActiveDeviceType = 0;
        this.isDualPlayMode = true;
        this.mActiveA2dpAddress = null;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.mContext, this.mA2dpProfileListener, 2);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_ACTIVE_DEVICE_INTENT);
        intentFilter.addAction("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED");
        this.mContext.registerReceiver(this.mA2dpIntentReceiver, intentFilter);
        this.mHandler.obtainMessage(3).sendToTarget();
        this.isRunning = true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleStop() {
        if (!this.isRunning) {
            ShtLog.e(TAG, "stop requested, but is already stopped");
            return;
        }
        this.mContext.unregisterReceiver(this.mA2dpIntentReceiver);
        this.mActiveDeviceType = 0;
        this.isDualPlayMode = true;
        this.mActiveA2dpAddress = null;
        this.mHandler.removeMessages(3);
        this.isRunning = false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleAudioPathTask() {
        String str;
        String str2;
        if (!this.isRunning) {
            ShtLog.e(TAG, "audio path update requested, but is not running");
            return;
        }
        int semGetCurrentDeviceType = this.mAudioManager.semGetCurrentDeviceType();
        if (semGetCurrentDeviceType != this.mActiveDeviceType) {
            this.mActiveDeviceType = semGetCurrentDeviceType;
            String str3 = TAG;
            ShtLog.i(str3, "device type changed : " + this.mActiveDeviceType);
        }
        int i = (this.mActiveDeviceType != 8 || (str = this.mActiveSppAddress) == null || (str2 = this.mActiveA2dpAddress) == null || !str.equals(str2) || this.isDualPlayMode) ? 2 : 1;
        if (i != this.mAudioPathStatus) {
            this.mAudioPathStatus = i;
            String str4 = TAG;
            ShtLog.i(str4, "Audio path status updated : " + this.mAudioPathStatus);
            Callback callback = this.mCallback;
            if (callback != null) {
                if (this.mAudioPathStatus == 1) {
                    callback.onProperAudioPath();
                } else {
                    callback.onImproperAudioPath();
                }
            }
        }
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(3), 1000);
    }

    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1:
                    AudioPathHelper.this.handleStart();
                    return;
                case 2:
                    AudioPathHelper.this.handleStop();
                    return;
                case 3:
                    AudioPathHelper.this.handleAudioPathTask();
                    return;
                case 4:
                    if (message.obj == null) {
                        AudioPathHelper.this.mActiveSppAddress = null;
                    } else {
                        AudioPathHelper.this.mActiveSppAddress = (String) message.obj;
                    }
                    String str = AudioPathHelper.TAG;
                    ShtLog.i(str, "Active Spp Adress Updated(" + AudioPathHelper.this.mActiveSppAddress + ")");
                    return;
                case 5:
                    if (message.obj == null) {
                        AudioPathHelper.this.mActiveA2dpAddress = null;
                    } else {
                        AudioPathHelper.this.mActiveA2dpAddress = (String) message.obj;
                    }
                    String str2 = AudioPathHelper.TAG;
                    ShtLog.i(str2, "Active A2dp Adress Updated(" + AudioPathHelper.this.mActiveA2dpAddress + ")");
                    return;
                case 6:
                    AudioPathHelper.this.isDualPlayMode = ((Boolean) message.obj).booleanValue();
                    String str3 = AudioPathHelper.TAG;
                    ShtLog.i(str3, "Dual Play Mode Udpated(" + AudioPathHelper.this.isDualPlayMode + ")");
                    return;
                default:
                    ShtLog.e(AudioPathHelper.TAG, "Unknown msg");
                    return;
            }
        }
    }
}
