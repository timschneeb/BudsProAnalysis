package com.samsung.android.fotaagent;

import android.content.Intent;
import com.samsung.android.fotaagent.polling.Polling;
import com.samsung.android.fotaagent.update.UpdateInterface;
import com.samsung.android.fotaagent.update.UpdateState;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;

public class ProcessFOTA {
    public void updateOnForeground() {
        Log.I("");
        callFotaUpdateJobIntentService(UpdateState.INITIALIZE_PULL);
    }

    /* access modifiers changed from: package-private */
    public void updateOnBackgroundByPollingAlarm() {
        Log.I("");
        if (!Polling.isPassedPollingTime()) {
            Log.I("not passed polling time, do not process FOTA by Polling Alarm");
            return;
        }
        Log.I("passed polling time, process FOTA by Polling Alarm");
        callFotaUpdateJobIntentService(UpdateState.INITIALIZE_POLLING);
    }

    public void updateOnBackgroundByRequest() {
        Log.I("");
        if (!Polling.isPassedPollingTime()) {
            Log.I("not passed polling time, process FOTA by Gear Plugin updated");
            callFotaUpdateJobIntentService(UpdateState.INITIALIZE_POLLING);
            return;
        }
        Log.I("passed polling time, do not process FOTA by Gear Plugin updated");
    }

    public void updateOnBackgroundByPush(String str) {
        Log.I("");
        callFotaUpdateJobIntentService(UpdateState.INITIALIZE_PUSH, str);
    }

    public void updateByCurrentStatus() {
        if (Polling.isPassedPollingTime()) {
            Log.I("passed polling time, start polling");
            callFotaUpdateJobIntentService(UpdateState.INITIALIZE_POLLING);
            return;
        }
        Log.I("not passed polling time, continue FOTA if previous status is existed");
        callFotaUpdateJobIntentService(UpdateState.CHECK_CURRENT_STATUS);
    }

    public void stopHoldingUpdateResult() {
        callFotaUpdateJobIntentService(UpdateState.STOP_WAITING_ON_UPDATE_RESULT);
    }

    private void callFotaUpdateJobIntentService(UpdateState updateState) {
        callFotaUpdateJobIntentService(updateState, null);
    }

    private void callFotaUpdateJobIntentService(UpdateState updateState, String str) {
        if (FotaProviderState.isInChangedDeviceProcess()) {
            Log.W("in changed device progress");
            FotaProviderState.blockActionDuringChangedDeviceProcess();
            return;
        }
        Intent intent = new Intent(FotaProviderInitializer.getContext(), FotaUpdateJobIntentService.class);
        intent.putExtra(UpdateInterface.UPDATE_STATE, updateState);
        intent.putExtra("msg", str);
        intent.addFlags(32);
        startFotaUpdateJobIntentService(intent);
    }

    private void startFotaUpdateJobIntentService(Intent intent) {
        FotaUpdateJobIntentService.enqueueWork(FotaProviderInitializer.getContext(), FotaUpdateJobIntentService.class, FotaServiceJobId.INSTANCE.UPDATE_JOB_ID, intent);
    }
}
