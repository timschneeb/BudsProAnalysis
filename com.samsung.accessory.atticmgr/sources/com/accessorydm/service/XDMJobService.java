package com.accessorydm.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import com.accessorydm.XDMServiceManager;
import com.samsung.android.fotaprovider.log.Log;

public class XDMJobService extends JobService {
    public void onCreate() {
        super.onCreate();
        Log.I("");
        XDMServiceManager.getInstance().xdmAddObserver();
        XDMServiceManager.getInstance().xdmInitializeService();
    }

    public void onDestroy() {
        Log.I("");
        super.onDestroy();
        XDMServiceManager.getInstance().xdmDeleteObserver();
    }

    public boolean onStartJob(JobParameters jobParameters) {
        Log.I("");
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        Log.I("");
        return false;
    }
}
