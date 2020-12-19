package com.sec.android.diagmonagent.log.ged.scheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.db.GEDDatabase;
import com.sec.android.diagmonagent.log.ged.db.dao.EventDao;
import com.sec.android.diagmonagent.log.ged.db.dao.ResultDao;
import com.sec.android.diagmonagent.log.ged.db.dao.ServiceDao;
import com.sec.android.diagmonagent.log.ged.db.model.Event;
import com.sec.android.diagmonagent.log.ged.db.model.Result;
import com.sec.android.diagmonagent.log.ged.db.model.ServiceInfo;
import com.sec.android.diagmonagent.log.ged.servreinterface.controller.DiagmonApiManager;
import com.sec.android.diagmonagent.log.ged.util.DeviceUtils;
import com.sec.android.diagmonagent.log.ged.util.PreferenceUtils;
import java.util.List;

public class GEDJobService extends JobService {
    ServerTask serverTask = null;

    public boolean onStartJob(final JobParameters jobParameters) {
        int jobId = jobParameters.getJobId();
        String str = DeviceUtils.TAG;
        Log.i(str, "Job Started : " + jobId);
        this.serverTask = new ServerTask() {
            /* class com.sec.android.diagmonagent.log.ged.scheduler.GEDJobService.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // com.sec.android.diagmonagent.log.ged.scheduler.GEDJobService.ServerTask
            public Boolean doInBackground(JobParameters... jobParametersArr) {
                return super.doInBackground(jobParameters);
            }

            /* access modifiers changed from: protected */
            @Override // com.sec.android.diagmonagent.log.ged.scheduler.GEDJobService.ServerTask
            public void onPostExecute(Boolean bool) {
                super.onPostExecute(bool);
                GEDJobService.this.jobFinished(jobParameters, false);
            }
        };
        this.serverTask.execute(new JobParameters[0]);
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        ServerTask serverTask2 = this.serverTask;
        if (serverTask2 != null) {
            serverTask2.cancel(true);
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public class ServerTask extends AsyncTask<JobParameters, Void, Boolean> {
        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
        }

        ServerTask() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(JobParameters... jobParametersArr) {
            ServiceDao serviceDao = GEDDatabase.get(GEDJobService.this.getApplicationContext()).getServiceDao();
            ServiceInfo serviceInfo = serviceDao.getServiceInfo();
            if (serviceInfo == null) {
                Log.w(DeviceUtils.TAG, "Start jobService but serviceInfo is null");
                return true;
            }
            AppLog.initLogger(GEDJobService.this.getApplicationContext(), serviceInfo.getServiceId());
            if (TextUtils.isEmpty(PreferenceUtils.getJwtToken(GEDJobService.this.getApplicationContext()))) {
                DiagmonApiManager.getInstance().refreshToken(GEDJobService.this.getApplicationContext());
            }
            startPDService(serviceInfo.getServiceId());
            if (startSRService(serviceInfo)) {
                serviceDao.deleteServiceByTime(System.currentTimeMillis() - serviceDao.MAX_KEEP_TIME);
                serviceInfo = serviceDao.getServiceInfo();
                if (serviceInfo == null) {
                    Log.w(DeviceUtils.TAG, "ServiceInfo is deleted by time");
                    return true;
                }
            }
            startReportService(serviceInfo.getStatus());
            return true;
        }

        private void startPDService(String str) {
            if (GEDScheduler.isPDIntervalPassed(GEDJobService.this.getApplicationContext())) {
                if (DiagmonApiManager.getInstance().getPolicyVersionInfo(GEDJobService.this.getApplicationContext())) {
                    DiagmonApiManager.getInstance().refreshPolicy(GEDJobService.this.getApplicationContext(), str, 0);
                }
                PreferenceUtils.setLastPDUpdatedTime(GEDJobService.this.getApplicationContext(), System.currentTimeMillis());
                return;
            }
            Log.i(DeviceUtils.TAG, "Policy download interval is not yet passed");
        }

        private boolean startSRService(ServiceInfo serviceInfo) {
            if (serviceInfo.getStatus() != 0) {
                Log.i(DeviceUtils.TAG, "There isn't unregistered service");
                return false;
            }
            DiagmonApiManager.getInstance().serviceRegister(GEDJobService.this.getApplicationContext(), serviceInfo, 0);
            return true;
        }

        private void startReportService(int i) {
            if (i != 1) {
                Log.w(DeviceUtils.TAG, "Service is not registered, reports don't send");
                return;
            }
            EventDao eventDao = GEDDatabase.get(GEDJobService.this.getApplicationContext()).getEventDao();
            eventDao.deleteEventsByTime(System.currentTimeMillis() - eventDao.MAX_KEEP_TIME);
            eventDao.resetExpiredS3PathEvents();
            List<Event> unreportedAllEvents = isWiFiConnected() ? eventDao.getUnreportedAllEvents() : eventDao.getUnreportedCellularEvents();
            if (unreportedAllEvents.size() <= 0) {
                Log.i(DeviceUtils.TAG, "There isn't unreported event");
            } else {
                for (Event event : unreportedAllEvents) {
                    DiagmonApiManager.getInstance().eventReport(GEDJobService.this.getApplicationContext(), event, 0);
                }
            }
            ResultDao resultDao = GEDDatabase.get(GEDJobService.this.getApplicationContext()).getResultDao();
            resultDao.deleteResultsByTime(System.currentTimeMillis() - resultDao.MAX_KEEP_TIME);
            List<Result> unreportedResults = resultDao.getUnreportedResults();
            if (unreportedResults.size() <= 0) {
                Log.i(DeviceUtils.TAG, "There isn't unreported result");
                return;
            }
            for (Result result : unreportedResults) {
                DiagmonApiManager.getInstance().resultReport(GEDJobService.this.getApplicationContext(), result, 0);
            }
        }

        private boolean isWiFiConnected() {
            NetworkCapabilities networkCapabilities;
            ConnectivityManager connectivityManager = (ConnectivityManager) GEDJobService.this.getSystemService("connectivity");
            if (connectivityManager == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork())) == null || !networkCapabilities.hasTransport(1)) {
                return false;
            }
            Log.i(DeviceUtils.TAG, "Wi-Fi is connected");
            return true;
        }
    }
}
