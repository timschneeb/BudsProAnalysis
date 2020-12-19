package com.samsung.android.app.twatchmanager.smartswitch;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.f;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.watchmanager.R;

public class SmartSwitchBackupRestoreService extends Service {
    private static final String TAG = ("tUHM:SmartSwitch:" + SmartSwitchBackupRestoreService.class.getSimpleName());
    private Context mContext;
    private Intent mIntent;
    private SmartSwitchBackupRestoreManager mSmartSwitchBackupRestoreManager;

    private void startSmartSwitchProcess() {
        Intent intent = this.mIntent;
        if (intent != null) {
            String action = intent.getAction();
            String str = TAG;
            Log.d(str, "startSmartSwitchProcess()::action = " + action);
            if (action == null) {
                return;
            }
            if (SmartSwitchConstants.REQUEST_BACKUP_FROM_SMART_SWITCH.equals(action)) {
                new Thread(new Runnable() {
                    /* class com.samsung.android.app.twatchmanager.smartswitch.SmartSwitchBackupRestoreService.AnonymousClass1 */

                    public void run() {
                        SmartSwitchBackupRestoreService smartSwitchBackupRestoreService = SmartSwitchBackupRestoreService.this;
                        smartSwitchBackupRestoreService.mSmartSwitchBackupRestoreManager = new SmartSwitchBackupRestoreManager(smartSwitchBackupRestoreService.mContext, SmartSwitchBackupRestoreService.this.mIntent);
                        SmartSwitchBackupRestoreService.this.mSmartSwitchBackupRestoreManager.startBackup();
                        SmartSwitchBackupRestoreService.this.stopForegroundService();
                    }
                }).start();
            } else if (SmartSwitchConstants.REQUEST_RESTORE_FROM_SMART_SWITCH.equals(action)) {
                new Thread(new Runnable() {
                    /* class com.samsung.android.app.twatchmanager.smartswitch.SmartSwitchBackupRestoreService.AnonymousClass2 */

                    public void run() {
                        SmartSwitchBackupRestoreService smartSwitchBackupRestoreService = SmartSwitchBackupRestoreService.this;
                        smartSwitchBackupRestoreService.mSmartSwitchBackupRestoreManager = new SmartSwitchBackupRestoreManager(smartSwitchBackupRestoreService.mContext, SmartSwitchBackupRestoreService.this.mIntent);
                        SmartSwitchBackupRestoreService.this.mSmartSwitchBackupRestoreManager.startRestore();
                        SmartSwitchBackupRestoreService.this.stopForegroundService();
                    }
                }).start();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void stopForegroundService() {
        Log.d(TAG, "stopForegroundService");
        stopForeground(true);
        stopSelf();
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        Log.d(TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Log.d(TAG, "onStartCommand()");
        int onStartCommand = super.onStartCommand(intent, i, i2);
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel(getResources().getString(R.string.notification_channel_backup_and_restore), getResources().getString(R.string.notification_channel_backup_and_restore), 2));
        }
        f.b bVar = new f.b(this, getResources().getString(R.string.notification_channel_backup_and_restore));
        bVar.c(getResources().getString(R.string.app_name));
        bVar.b(getResources().getString(R.string.app_name));
        startForeground(Integer.MAX_VALUE, bVar.a());
        this.mIntent = intent;
        startSmartSwitchProcess();
        return onStartCommand;
    }
}
