package com.samsung.accessory.hearablemgr.core.gamemode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.BroadcastReceiverUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import java.util.HashSet;
import java.util.Set;
import seccompat.android.bluetooth.BluetoothA2dp;
import seccompat.android.util.Log;
import seccompat.com.samsung.android.bluetooth.SemBluetoothAudioCast;

public class MaxBufferReceiver extends BroadcastReceiver {
    private static final String TAG = "Attic_MaxBufferReceiver";
    private Context mContext;
    private final BroadcastReceiverUtil.Receiver mDynamicReceiver = new BroadcastReceiverUtil.Receiver() {
        /* class com.samsung.accessory.hearablemgr.core.gamemode.MaxBufferReceiver.AnonymousClass1 */

        @Override // com.samsung.accessory.hearablemgr.common.util.BroadcastReceiverUtil.Receiver
        public void setIntentFilter(IntentFilter intentFilter) {
            intentFilter.addAction(BluetoothA2dp.ACTION_DUAL_PLAY_MODE_ENABLED);
            intentFilter.addAction(SemBluetoothAudioCast.ACTION_AUDIO_SHARING_MODE_CHANGED);
            intentFilter.addAction(SemBluetoothAudioCast.ACTION_CAST_DEVICE_CONNECTION_STATE_CHANGED);
        }

        public void onReceive(Context context, Intent intent) {
            Log.d(MaxBufferReceiver.TAG, "_onReceive() : " + Util.getAction(intent));
            for (BroadcastReceiver broadcastReceiver : MaxBufferReceiver.this.mListenReceivers) {
                broadcastReceiver.onReceive(context, intent);
            }
        }
    };
    private Set<BroadcastReceiver> mListenReceivers = new HashSet();

    public MaxBufferReceiver() {
    }

    public MaxBufferReceiver(Context context) {
        this.mContext = context;
        BroadcastReceiverUtil.register(this.mContext, this.mDynamicReceiver);
    }

    public void destroy() {
        Log.d(TAG, "destroy()");
        this.mListenReceivers.clear();
        BroadcastReceiverUtil.unregister(this.mContext, this.mDynamicReceiver);
    }

    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() : " + Util.getAction(intent));
    }

    public void addReceiver(BroadcastReceiver broadcastReceiver) {
        this.mListenReceivers.add(broadcastReceiver);
    }

    public void removeReceiver(BroadcastReceiver broadcastReceiver) {
        this.mListenReceivers.remove(broadcastReceiver);
    }

    public static boolean isMaxBufferCondition() {
        boolean isDualPlayMode = isDualPlayMode();
        boolean isAudioSharingEnabled = SemBluetoothAudioCast.isAudioSharingEnabled();
        boolean isMusicShareEnabled = SemBluetoothAudioCast.isMusicShareEnabled();
        Log.d(TAG, "isMaxBufferCondition : dualAudio=" + isDualPlayMode + ", budsTogether=" + isAudioSharingEnabled + ", musicShare=" + isMusicShareEnabled);
        return isDualPlayMode || isAudioSharingEnabled || isMusicShareEnabled;
    }

    private static boolean isDualPlayMode() {
        android.bluetooth.BluetoothA2dp a2dpProxy = Application.getBluetoothManager().getA2dpProxy();
        if (a2dpProxy != null) {
            return BluetoothA2dp.proxySemIsDualPlayMode(a2dpProxy);
        }
        return false;
    }
}
