package com.samsung.android.fotaagent.register;

import android.content.Context;
import androidx.work.ExistingWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import java.util.concurrent.TimeUnit;

public class InitDeviceWork {
    private static InitDeviceWork instance = new InitDeviceWork();
    private String WORK_NAME = ("FotaInitDeviceWork_" + FotaProviderInitializer.getContext().getPackageName());
    private String WORK_TAG = ("FotaInitDeviceWorkTag_" + FotaProviderInitializer.getContext().getPackageName());
    private WorkResult workResult;

    public interface WorkResult {
        void onWorked();
    }

    private InitDeviceWork() {
    }

    public static InitDeviceWork getInstance() {
        return instance;
    }

    public void schedule(RegisterType registerType, WorkResult workResult2) {
        Log.I("schedule initializing device");
        this.workResult = workResult2;
        WorkManager.getInstance(FotaProviderInitializer.getContext()).enqueueUniqueWork(this.WORK_NAME, registerType == RegisterType.FOREGROUND ? ExistingWorkPolicy.REPLACE : ExistingWorkPolicy.KEEP, (OneTimeWorkRequest) ((OneTimeWorkRequest.Builder) ((OneTimeWorkRequest.Builder) new OneTimeWorkRequest.Builder(InitDeviceWorker.class).addTag(this.WORK_TAG)).setInitialDelay(registerType == RegisterType.BACKGROUND_WITH_DELAY ? RegisterInterface.DELAY_PERIOD_FOR_INITIALIZING_DEVICE : 0, TimeUnit.MILLISECONDS)).build());
    }

    public void cancel() {
        Log.I("cancel scheduled initializing device");
        WorkManager.getInstance(FotaProviderInitializer.getContext()).cancelAllWorkByTag(this.WORK_TAG);
    }

    public static class InitDeviceWorker extends Worker {
        public InitDeviceWorker(Context context, WorkerParameters workerParameters) {
            super(context, workerParameters);
        }

        @Override // androidx.work.Worker
        public ListenableWorker.Result doWork() {
            if (InitDeviceWork.getInstance().workResult != null) {
                Log.I("Scheduling is done. Do next operation");
                InitDeviceWork.getInstance().workResult.onWorked();
                return ListenableWorker.Result.success();
            }
            Log.W("Abnormal case. workResult is empty");
            return ListenableWorker.Result.failure();
        }
    }
}
