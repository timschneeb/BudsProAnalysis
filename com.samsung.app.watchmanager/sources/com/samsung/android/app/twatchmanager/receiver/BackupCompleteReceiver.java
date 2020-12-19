package com.samsung.android.app.twatchmanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import com.samsung.android.app.twatchmanager.contentprovider.RegistryDbManagerWithProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.GearInfo;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.watchmanager.setupwizard.HMConnectFragment;
import java.util.HashMap;
import java.util.Set;

public class BackupCompleteReceiver extends BroadcastReceiver {
    public static final int MSG_HM_DISCONNECT_COMPLETE = 0;
    public static final int MSG_HM_TIMEOUT = 1;
    private static final String PACKAGE_NAME_GEAR2PLUGIN = "com.samsung.android.gear2plugin";
    private static final String PACKAGE_NAME_HOSTMANAGER = "com.samsung.android.hostmanager";
    private static final String TAG = ("tUHM:" + BackupCompleteReceiver.class.getSimpleName());
    private static Handler mHandler;
    private static BackupCompleteReceiver mInstance;
    private static int mSendCount = 0;

    public interface IDisconnectReason {
        public static final int DISCONNECT_FROM_DEVICELIST = 2;
        public static final int DISCONNECT_FROM_HMCONNECT = 1;
        public static final int DISCONNECT_FROM_UPDATE = 3;
    }

    public static void registerReceiver(Context context, Handler handler, String str, String str2, int i) {
        mSendCount = 1;
        mHandler = handler;
        String str3 = TAG;
        Log.d(str3, "registerReceiver() disconnectReason : " + i + " mSendCount : " + mSendCount);
        registerReceiverInternal(context);
        String str4 = TAG;
        Log.d(str4, "registerReceiver() sending Broadcast ** ACTION_HM_REQUEST_DISCONNECT ** to disconnect the device " + str2 + "(" + str + ")");
        sendDisconnectIntent(context, str, i);
        sendBackupFinishedMsg(1, 30000);
    }

    public static void registerReceiver(Context context, Handler handler, HashMap<String, GearInfo> hashMap, int i) {
        registerReceiverInternal(context);
        mHandler = handler;
        Set<String> keySet = hashMap.keySet();
        mSendCount = keySet.size();
        String str = TAG;
        Log.d(str, "registerReceiver() disconnectReason : " + i + " mSendCount : " + mSendCount);
        for (String str2 : keySet) {
            GearInfo gearInfo = hashMap.get(str2);
            String str3 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("registerReceiver() sending Broadcast ** ACTION_HM_REQUEST_DISCONNECT ** to disconnect the device ");
            sb.append(gearInfo != null ? gearInfo.deviceName : "null");
            sb.append("(");
            sb.append(str2);
            sb.append(")");
            Log.d(str3, sb.toString());
            sendDisconnectIntent(context, str2, i);
        }
        sendBackupFinishedMsg(1, 30000);
    }

    public static void registerReceiverInternal(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.watchmanager.ACTION_BROADCAST_HM_BACKUP_COMPLETE");
        intentFilter.addAction("com.samsung.android.hostmanager.action.GEAR_DEVICE_DISCONNECTED");
        intentFilter.addAction("android.intent.watchmanager.action.CONNECTION_DISCONNECTED");
        if (mInstance == null) {
            mInstance = new BackupCompleteReceiver();
        }
        BackupCompleteReceiver backupCompleteReceiver = mInstance;
        if (backupCompleteReceiver == null || context == null) {
            Log.d(TAG, "mInstance or context is null");
        } else {
            context.registerReceiver(backupCompleteReceiver, intentFilter);
        }
    }

    private static void sendBackupFinishedMsg(int i, int i2) {
        String str = TAG;
        Log.d(str, "HM has completed its data, mHandler [" + mHandler + "]");
        Handler handler = mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            Message obtain = Message.obtain(mHandler, i);
            if (i2 > 0) {
                mHandler.sendMessageDelayed(obtain, (long) i2);
            } else {
                obtain.sendToTarget();
            }
        }
    }

    public static void sendDisconnectIntent(Context context, String str, int i) {
        Intent intent = new Intent(GlobalConst.ACTION_HM_DISCONNECT_DEVICE);
        String str2 = new RegistryDbManagerWithProvider().queryDevicebyDeviceIdRegistryData(str, context).get(0).packagename;
        if ("com.samsung.android.gear2plugin".equals(str2)) {
            str2 = PACKAGE_NAME_HOSTMANAGER;
        }
        intent.setPackage(str2);
        intent.putExtra(HMConnectFragment.EXTRA_DEVICE_ADDRESS, str);
        intent.putExtra("reason", i);
        intent.putExtra("request_app_package_name", "com.samsung.android.app.watchmanager");
        context.sendBroadcast(intent, "com.samsung.android.hostmanager.permission.ACCESS_UNIFIED_HOST_MANAGER");
    }

    public static void unregisterReceiver(Context context) {
        Handler handler = mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        try {
            if (!(mInstance == null || context == null)) {
                context.unregisterReceiver(mInstance);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        mInstance = null;
    }

    public void onReceive(Context context, Intent intent) {
        mSendCount--;
        Log.d(TAG, "onReceive() action : " + intent.getAction() + " mSendCount : " + mSendCount);
        if (mSendCount <= 0) {
            sendBackupFinishedMsg(0, 0);
        }
    }
}
