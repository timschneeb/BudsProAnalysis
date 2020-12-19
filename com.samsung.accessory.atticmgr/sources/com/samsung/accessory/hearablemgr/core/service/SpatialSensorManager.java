package com.samsung.accessory.hearablemgr.core.service;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.BroadcastReceiverUtil;
import com.samsung.accessory.hearablemgr.common.util.ByteUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.Msg;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimpleArray;
import com.samsung.accessory.hearablemgr.library.SpatialSensorInterface;
import com.samsung.sht.ShtCore;
import seccompat.android.util.Log;

public class SpatialSensorManager implements CoreService.OnSppMessageListener {
    public static final String ACTION_SPATIAL_AUDIO_SETTING_UPDATED = "com.samsung.accessory.hearablemgr.core.service.SpatialSensorManager.ACTION_SPATIAL_AUDIO_SETTING_UPDATED";
    public static final String EXTRA_VALUE = "extra_value";
    private static final String TAG = "Attic_SpatialSensorManager";
    private final Context mContext;
    private final CoreService mCoreService;
    private final BroadcastReceiverUtil.Receiver mReceiver = new BroadcastReceiverUtil.Receiver() {
        /* class com.samsung.accessory.hearablemgr.core.service.SpatialSensorManager.AnonymousClass1 */

        @Override // com.samsung.accessory.hearablemgr.common.util.BroadcastReceiverUtil.Receiver
        public void setIntentFilter(IntentFilter intentFilter) {
            intentFilter.addAction(SpatialSensorManager.ACTION_SPATIAL_AUDIO_SETTING_UPDATED);
        }

        public void onReceive(Context context, Intent intent) {
            Log.d(SpatialSensorManager.TAG, "onReceive() : " + intent.getAction());
            String action = intent.getAction();
            if (((action.hashCode() == 56164497 && action.equals(SpatialSensorManager.ACTION_SPATIAL_AUDIO_SETTING_UPDATED)) ? (char) 0 : 65535) == 0) {
                if (intent.hasExtra(SpatialSensorManager.EXTRA_VALUE)) {
                    boolean booleanExtra = intent.getBooleanExtra(SpatialSensorManager.EXTRA_VALUE, false);
                    try {
                        Log.d(SpatialSensorManager.TAG, "onReceive() : onSettingUpdated() : " + booleanExtra);
                        SpatialSensorManager.this.mSpatialSensorLibrary.onSettingUpdated(booleanExtra);
                    } catch (Throwable th) {
                        th.printStackTrace();
                        Log.e(SpatialSensorManager.TAG, "onReceive() : Exception : " + th);
                    }
                } else {
                    Log.e(SpatialSensorManager.TAG, "onReceive() : intent.hasExtra(EXTRA_VALUE) == false");
                }
            }
        }
    };
    private final SpatialSensorInterface mSpatialSensorLibrary;
    private final SppConnectionManager mSppConnectionManager;

    SpatialSensorManager(Context context, CoreService coreService, SppConnectionManager sppConnectionManager) {
        Log.d(TAG, "SpatialSensorManager() : " + isSupported(context));
        this.mContext = context;
        this.mCoreService = coreService;
        this.mSppConnectionManager = sppConnectionManager;
        this.mSpatialSensorLibrary = new ShtCore();
        try {
            this.mSpatialSensorLibrary.onCreate(new SupportApi(), true);
        } catch (Throwable th) {
            th.printStackTrace();
            Log.e(TAG, "SpatialSensorManager() : Exception : " + th);
        }
        BroadcastReceiverUtil.register(this.mContext, this.mReceiver);
    }

    /* access modifiers changed from: package-private */
    public void onSppConnectionManagerStateChanged(BluetoothDevice bluetoothDevice, int i) {
        Log.d(TAG, "onSppConnectionManagerStateChanged() : " + i);
        String str = null;
        if (i == 0) {
            this.mCoreService.unregisterSppMessageListener(this);
            SpatialSensorInterface spatialSensorInterface = this.mSpatialSensorLibrary;
            if (bluetoothDevice != null) {
                str = bluetoothDevice.getAddress().toUpperCase();
            }
            spatialSensorInterface.onSppDisconnected(str);
        } else if (i == 2) {
            try {
                this.mCoreService.registerSppMessageListener(this);
                SpatialSensorInterface spatialSensorInterface2 = this.mSpatialSensorLibrary;
                if (bluetoothDevice != null) {
                    str = bluetoothDevice.getAddress().toUpperCase();
                }
                spatialSensorInterface2.onSppConnected(str);
            } catch (Throwable th) {
                th.printStackTrace();
                Log.e(TAG, "onSppConnectionManagerStateChanged() : Exception : " + th);
            }
        }
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.CoreService.OnSppMessageListener
    public void onSppMessage(Msg msg) {
        try {
            byte b = msg.id;
            if (b == -62 || b == -61) {
                Log.v(TAG, "onSppMessage() : " + ((int) msg.id));
                this.mSpatialSensorLibrary.onSppMessageReceived(msg.id, msg.getRecvData());
            }
        } catch (Throwable th) {
            th.printStackTrace();
            Log.e(TAG, "onSppMessage() : Exception : " + th);
        }
    }

    /* access modifiers changed from: package-private */
    public void destroy() {
        Log.d(TAG, "destroy()");
        BroadcastReceiverUtil.unregister(this.mContext, this.mReceiver);
        try {
            this.mCoreService.unregisterSppMessageListener(this);
            this.mSpatialSensorLibrary.onDestroy();
        } catch (Throwable th) {
            th.printStackTrace();
            Log.e(TAG, "destroy() : Exception : " + th);
        }
    }

    class SupportApi implements SpatialSensorInterface.SupportApi {
        SupportApi() {
        }

        @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface.SupportApi
        public Context getContext() {
            Log.d(SpatialSensorManager.TAG, "getContext()");
            return SpatialSensorManager.this.mContext;
        }

        @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface.SupportApi
        public boolean getSetting() {
            boolean z = SpatialSensorManager.this.mCoreService.getEarBudsInfo().spatialAudio;
            Log.d(SpatialSensorManager.TAG, "getSetting() : " + z);
            return z;
        }

        @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface.SupportApi
        public boolean isSppConnected() {
            boolean isConnected = SpatialSensorManager.this.mSppConnectionManager.isConnected();
            Log.d(SpatialSensorManager.TAG, "isSppConnected() : " + isConnected);
            return isConnected;
        }

        @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface.SupportApi
        public boolean sendSppMessage(byte b, byte[] bArr) {
            boolean z;
            if (SpatialSensorManager.this.mSppConnectionManager.isConnected()) {
                SpatialSensorManager.this.mSppConnectionManager.sendMessage(new MsgSimpleArray(b, bArr));
                z = true;
            } else {
                z = false;
            }
            Log.d(SpatialSensorManager.TAG, "sendSppMessage() : " + z + " / " + ByteUtil.toHexString(b) + ", " + ByteUtil.toLogString(bArr));
            return z;
        }
    }

    public static void notifySpatialAudioSettingUpdated(boolean z) {
        Log.d(TAG, "notifySpatialAudioSettingUpdated() : " + z);
        Intent intent = new Intent(ACTION_SPATIAL_AUDIO_SETTING_UPDATED);
        intent.putExtra(EXTRA_VALUE, z);
        Util.sendPermissionBroadcast(Application.getContext(), intent);
    }

    public static boolean isSupported(Context context) {
        boolean z = false;
        try {
            if (Util.isSamsungDevice() && ShtCore.isSupported(context)) {
                z = true;
            }
        } catch (Throwable th) {
            th.printStackTrace();
            Log.e(TAG, "isSupported() : Exception : " + th);
        }
        Log.d(TAG, "isSupported() : " + z);
        return z;
    }
}
