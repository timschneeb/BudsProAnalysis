package com.accessorydm.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.accessorydm.XDMServiceManager;
import com.samsung.android.fotaprovider.log.Log;

public class XDMService extends Service {
    private LocalBinder localBinder;

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public XDMService getService() {
            return XDMService.this;
        }
    }

    public void onCreate() {
        super.onCreate();
        Log.I("");
        this.localBinder = new LocalBinder();
        XDMServiceManager.getInstance().xdmAddObserver();
        XDMServiceManager.getInstance().xdmInitializeService();
    }

    public void onDestroy() {
        Log.I("");
        super.onDestroy();
        XDMServiceManager.getInstance().xdmDeleteObserver();
    }

    public IBinder onBind(Intent intent) {
        return this.localBinder;
    }
}
