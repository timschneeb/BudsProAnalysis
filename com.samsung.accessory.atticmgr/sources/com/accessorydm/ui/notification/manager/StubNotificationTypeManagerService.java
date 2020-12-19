package com.accessorydm.ui.notification.manager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.samsung.android.fotaprovider.log.Log;

public class StubNotificationTypeManagerService extends Service implements NotificationTypeManagerService {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        Log.W("should not be called");
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Log.W("should not be called");
        stopSelf(i2);
        return 2;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.I("");
    }
}
