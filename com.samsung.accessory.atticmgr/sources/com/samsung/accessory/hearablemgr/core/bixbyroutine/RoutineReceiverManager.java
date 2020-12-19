package com.samsung.accessory.hearablemgr.core.bixbyroutine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import seccompat.android.util.Log;

public class RoutineReceiverManager {
    private static final String TAG = (Application.TAG_ + RoutineReceiverManager.class.getSimpleName());
    private Context context;
    private final BroadcastReceiver mDeviceConnectionReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutineReceiverManager.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            if (RoutineUtils.isSupportedRecommendCard(context)) {
                int i = Preferences.getInt(PreferenceKey.ROUTINE_CONNECTION_COUNT, 0, Preferences.MODE_MANAGER);
                if (i < 2) {
                    String str = RoutineReceiverManager.TAG;
                    Log.d(str, "ROUTINE_CONNECTION_COUNT : " + i);
                    Preferences.putInt(PreferenceKey.ROUTINE_CONNECTION_COUNT, Integer.valueOf(i + 1), Preferences.MODE_MANAGER);
                    return;
                }
                RoutineUtils.sendRecommendBroadcast(RoutineConstants.RECOMMEND_DRIVE_TAG_COMMUTE);
            }
        }
    };

    public RoutineReceiverManager(Context context2) {
        this.context = context2;
        onCreate();
    }

    public void onCreate() {
        Log.d(TAG, "onCreate()");
        RoutineUtils.initialize(this.context);
        registerReceiver();
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        unRegisterReceiver();
    }

    private void registerReceiver() {
        registerDeviceConnectionReceiver();
    }

    private void unRegisterReceiver() {
        this.context.unregisterReceiver(this.mDeviceConnectionReceiver);
    }

    private void registerDeviceConnectionReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_DEVICE_CONNECTED);
        this.context.registerReceiver(this.mDeviceConnectionReceiver, intentFilter);
    }
}
