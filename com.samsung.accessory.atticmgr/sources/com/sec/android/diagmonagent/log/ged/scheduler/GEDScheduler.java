package com.sec.android.diagmonagent.log.ged.scheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import com.sec.android.diagmonagent.log.ged.util.DeviceUtils;
import com.sec.android.diagmonagent.log.ged.util.PreferenceUtils;
import java.util.concurrent.TimeUnit;

public class GEDScheduler {
    private static final int GED_JOB_SERVICE_ID = 75419411;
    private static final long GED_SCHEDULING_INTERVAL = TimeUnit.HOURS.toMillis(6);

    public static boolean registerJob(Context context) {
        try {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
            if (jobScheduler != null) {
                jobScheduler.schedule(new JobInfo.Builder(GED_JOB_SERVICE_ID, new ComponentName(context, GEDJobService.class)).setRequiredNetworkType(1).setPeriodic(GED_SCHEDULING_INTERVAL).setPersisted(true).build());
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean cancelJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (jobScheduler != null) {
            jobScheduler.cancel(GED_JOB_SERVICE_ID);
            return true;
        }
        Log.i(DeviceUtils.TAG, "Cancel job failed because job scheduler is null");
        return false;
    }

    public static boolean isGEDJobServiceRegistered(Context context) {
        try {
            for (JobInfo jobInfo : ((JobScheduler) context.getSystemService("jobscheduler")).getAllPendingJobs()) {
                if (GED_JOB_SERVICE_ID == jobInfo.getId()) {
                    Log.i(DeviceUtils.TAG, "Found matching GED job service id.");
                    return true;
                }
            }
            return false;
        } catch (NullPointerException unused) {
            Log.e(DeviceUtils.TAG, "NPE occurred while getting pending jobs");
            return false;
        }
    }

    public static boolean isPDIntervalPassed(Context context) {
        return System.currentTimeMillis() >= PreferenceUtils.getLastPDUpdatedTime(context) + TimeUnit.DAYS.toMillis((long) Integer.parseInt(PreferenceUtils.getPollingInterval(context)));
    }
}
