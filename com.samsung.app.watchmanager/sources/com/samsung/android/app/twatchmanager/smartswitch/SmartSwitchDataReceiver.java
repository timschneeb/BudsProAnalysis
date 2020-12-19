package com.samsung.android.app.twatchmanager.smartswitch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.samsung.android.app.twatchmanager.log.Log;

public class SmartSwitchDataReceiver extends BroadcastReceiver {
    private static final String TAG = ("tUHM:SmartSwitch:" + SmartSwitchDataReceiver.class.getSimpleName());
    private SmartSwitchBackupRestoreManager mSmartSwitchBackupRestoreManager;

    public void onReceive(final Context context, final Intent intent) {
        Thread thread;
        String action = intent.getAction();
        String str = TAG;
        Log.d(str, "onReceive()::action = " + action);
        if (action != null) {
            if (SmartSwitchConstants.REQUEST_BACKUP_FROM_SMART_SWITCH.equals(action)) {
                if (Build.VERSION.SDK_INT >= 26) {
                    intent.setClass(context, SmartSwitchBackupRestoreService.class);
                    context.startForegroundService(intent);
                    return;
                }
                thread = new Thread(new Runnable() {
                    /* class com.samsung.android.app.twatchmanager.smartswitch.SmartSwitchDataReceiver.AnonymousClass1 */

                    public void run() {
                        SmartSwitchDataReceiver.this.mSmartSwitchBackupRestoreManager = new SmartSwitchBackupRestoreManager(context, intent);
                        SmartSwitchDataReceiver.this.mSmartSwitchBackupRestoreManager.startBackup();
                    }
                });
            } else if (SmartSwitchConstants.REQUEST_RESTORE_FROM_SMART_SWITCH.equals(action)) {
                thread = new Thread(new Runnable() {
                    /* class com.samsung.android.app.twatchmanager.smartswitch.SmartSwitchDataReceiver.AnonymousClass2 */

                    public void run() {
                        SmartSwitchDataReceiver.this.mSmartSwitchBackupRestoreManager = new SmartSwitchBackupRestoreManager(context, intent);
                        SmartSwitchDataReceiver.this.mSmartSwitchBackupRestoreManager.startRestore();
                    }
                });
            } else {
                return;
            }
            thread.start();
        }
    }
}
