package com.samsung.android.app.twatchmanager.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import java.util.ArrayList;

public class BNRHelper {
    public static final String ACTION_DATA_BACKUP_COMPLETE = "com.samsung.android.app.watchmanager.ACTION_DATA_BACKUP_COMPLETE";
    public static final String ACTION_REQUEST_DATA_BACKUP = "com.samsung.android.app.watchmanager.ACTION_REQUEST_DATA_BACKUP";
    public static final String EXTRA_PACKAGE_LIST = "package_list";
    private static final String TAG = ("tUHM:" + BNRHelper.class.getSimpleName());
    final BroadcastReceiver receiver = new BroadcastReceiver() {
        /* class com.samsung.android.app.twatchmanager.manager.BNRHelper.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            String str = BNRHelper.TAG;
            Log.d(str, "onReceive() intent action:" + intent.getAction());
            if (intent.getAction().equalsIgnoreCase(BNRHelper.ACTION_DATA_BACKUP_COMPLETE)) {
                synchronized (BNRHelper.this) {
                    Log.d(BNRHelper.TAG, "onReceive:notifyAll()");
                    BNRHelper.this.notifyAll();
                }
            }
        }
    };
    boolean responseReceived = false;

    public void requestBackup(ArrayList<String> arrayList) {
        synchronized (this) {
            Log.d(TAG, "requestBackup()");
            this.responseReceived = false;
            Context appContext = TWatchManagerApplication.getAppContext();
            appContext.registerReceiver(this.receiver, new IntentFilter(ACTION_DATA_BACKUP_COMPLETE), "com.samsung.android.hostmanager.permission.ACCESS_UNIFIED_HOST_MANAGER", null);
            Intent intent = new Intent(ACTION_REQUEST_DATA_BACKUP);
            intent.putStringArrayListExtra(EXTRA_PACKAGE_LIST, arrayList);
            intent.addFlags(268435456);
            appContext.sendBroadcast(intent, "com.samsung.android.hostmanager.permission.ACCESS_UNIFIED_HOST_MANAGER");
            Log.d(TAG, "Wait for Backup complete");
            try {
                wait(20000);
                try {
                    TWatchManagerApplication.getAppContext().unregisterReceiver(this.receiver);
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, "Exception while unregistering receiver.");
                    e.printStackTrace();
                }
                Log.d(TAG, "Backup Complete");
            } catch (InterruptedException e2) {
                e2.printStackTrace();
                Log.d(TAG, "Backup Interrupted ");
                try {
                    TWatchManagerApplication.getAppContext().unregisterReceiver(this.receiver);
                } catch (IllegalArgumentException e3) {
                    Log.e(TAG, "Exception while unregistering receiver.");
                    e3.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    TWatchManagerApplication.getAppContext().unregisterReceiver(this.receiver);
                } catch (IllegalArgumentException e4) {
                    Log.e(TAG, "Exception while unregistering receiver.");
                    e4.printStackTrace();
                }
                throw th;
            }
        }
    }
}
