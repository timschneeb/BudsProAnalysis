package com.samsung.android.fotaagent;

import android.content.Intent;
import android.text.TextUtils;
import androidx.core.app.SafeJobIntentService;
import com.accessorydm.XDMSecReceiverApiCall;
import com.accessorydm.db.file.AccessoryInfoAdapter;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.filetransfer.XDMFileTransferManager;
import com.accessorydm.interfaces.XFOTAInterface;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.accessory.fotaprovider.AccessoryState;
import com.samsung.accessory.fotaprovider.controller.ConsumerInfo;
import com.samsung.accessory.fotaprovider.controller.RequestController;
import com.samsung.accessory.fotaprovider.controller.RequestError;
import com.samsung.android.fotaagent.network.NetConnect;
import com.samsung.android.fotaagent.network.action.NetworkResult;
import com.samsung.android.fotaagent.network.action.PollingAction;
import com.samsung.android.fotaagent.polling.Polling;
import com.samsung.android.fotaagent.update.UpdateInterface;
import com.samsung.android.fotaagent.update.UpdateState;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FotaUpdateJobIntentService extends SafeJobIntentService {
    private static volatile ScheduledExecutorService updateResultScheduler;
    private String mPushMsg = "";

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0055  */
    @Override // androidx.core.app.JobIntentService
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onHandleWork(android.content.Intent r7) {
        /*
            r6 = this;
            java.lang.String r0 = "msg"
            java.lang.String r1 = "no more status to check"
            r2 = 0
            java.lang.String r3 = "update_state"
            java.io.Serializable r3 = r7.getSerializableExtra(r3)     // Catch:{ Exception -> 0x0034, all -> 0x002f }
            com.samsung.android.fotaagent.update.UpdateState r3 = (com.samsung.android.fotaagent.update.UpdateState) r3     // Catch:{ Exception -> 0x0034, all -> 0x002f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x002d }
            r2.<init>()     // Catch:{ Exception -> 0x002d }
            java.lang.String r4 = "updateState: "
            r2.append(r4)     // Catch:{ Exception -> 0x002d }
            r2.append(r3)     // Catch:{ Exception -> 0x002d }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x002d }
            com.samsung.android.fotaprovider.log.Log.D(r2)     // Catch:{ Exception -> 0x002d }
            if (r3 == 0) goto L_0x003f
            java.lang.String r7 = r7.getStringExtra(r0)
            r6.mPushMsg = r7
            r6.handleState(r3)
            goto L_0x0047
        L_0x002d:
            r2 = move-exception
            goto L_0x0038
        L_0x002f:
            r3 = move-exception
            r5 = r3
            r3 = r2
            r2 = r5
            goto L_0x0049
        L_0x0034:
            r3 = move-exception
            r5 = r3
            r3 = r2
            r2 = r5
        L_0x0038:
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0048 }
            com.samsung.android.fotaprovider.log.Log.W(r2)     // Catch:{ all -> 0x0048 }
        L_0x003f:
            com.samsung.android.fotaprovider.log.Log.D(r1)
            com.samsung.android.fotaagent.update.UpdateState r7 = com.samsung.android.fotaagent.update.UpdateState.FOTA_REQUEST_COMPLETE
            r6.handleState(r7)
        L_0x0047:
            return
        L_0x0048:
            r2 = move-exception
        L_0x0049:
            if (r3 == 0) goto L_0x0055
            java.lang.String r7 = r7.getStringExtra(r0)
            r6.mPushMsg = r7
            r6.handleState(r3)
            goto L_0x005d
        L_0x0055:
            com.samsung.android.fotaprovider.log.Log.D(r1)
            com.samsung.android.fotaagent.update.UpdateState r7 = com.samsung.android.fotaagent.update.UpdateState.FOTA_REQUEST_COMPLETE
            r6.handleState(r7)
        L_0x005d:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaagent.FotaUpdateJobIntentService.onHandleWork(android.content.Intent):void");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendNextState(UpdateState updateState) {
        Intent intent = new Intent(FotaProviderInitializer.getContext(), FotaUpdateJobIntentService.class);
        intent.putExtra(UpdateInterface.UPDATE_STATE, updateState);
        intent.addFlags(32);
        enqueueWork(FotaProviderInitializer.getContext(), FotaUpdateJobIntentService.class, FotaServiceJobId.INSTANCE.UPDATE_JOB_ID, intent);
    }

    private void handleState(UpdateState updateState) {
        switch (updateState) {
            case INITIALIZE_PULL:
                Log.I("Update State: Initialize pull update");
                requestPull();
                sendNextState(UpdateState.FOTA_REQUEST_COMPLETE);
                return;
            case INITIALIZE_PUSH:
                Log.I("Update State: Initialize push update");
                if (isPushUpdate()) {
                    requestPush();
                }
                sendNextState(UpdateState.FOTA_REQUEST_COMPLETE);
                return;
            case INITIALIZE_POLLING:
                Log.I("Update State: Initialize polling update");
                Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                    /* class com.samsung.android.fotaagent.$$Lambda$FotaUpdateJobIntentService$oW1g60IYkzV4Vp_VsArilj46k */

                    public final void run() {
                        FotaUpdateJobIntentService.this.lambda$handleState$0$FotaUpdateJobIntentService();
                    }
                }, 1000, TimeUnit.MILLISECONDS);
                return;
            case INITIALIZE_BT_RECONNECT:
                Log.I("Update State: Initialize BT reconnect");
                Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                    /* class com.samsung.android.fotaagent.$$Lambda$FotaUpdateJobIntentService$QrMk84No66wbJGCJrmYs_hgtD40 */

                    public final void run() {
                        FotaUpdateJobIntentService.this.lambda$handleState$1$FotaUpdateJobIntentService();
                    }
                }, UpdateInterface.HOLDING_AFTER_BT_CONNECTED, TimeUnit.MILLISECONDS);
                return;
            case CHECK_CURRENT_STATUS:
                if (XDBFumoAdp.xdbGetFUMOStatus() == 60) {
                    Log.I("Update State: Waiting on until receive update result in update progress");
                    if (updateResultScheduler != null && !updateResultScheduler.isShutdown()) {
                        Log.D("Update State: shutdown first to avoid duplicated schedule execution");
                        updateResultScheduler.shutdownNow();
                    }
                    updateResultScheduler = Executors.newSingleThreadScheduledExecutor();
                    updateResultScheduler.schedule(new Runnable() {
                        /* class com.samsung.android.fotaagent.$$Lambda$FotaUpdateJobIntentService$8Y8HdNl_L2OqrLpTncd0ZHRNxA8 */

                        public final void run() {
                            FotaUpdateJobIntentService.this.lambda$handleState$2$FotaUpdateJobIntentService();
                        }
                    }, UpdateInterface.UPDATE_RESULT_WAITING_TIME, TimeUnit.MILLISECONDS);
                    return;
                }
                Log.I("Update State: Check current status");
                getDeviceInfo(UpdateState.CHECK_CURRENT_STATUS);
                return;
            case STOP_WAITING_ON_UPDATE_RESULT:
                if (updateResultScheduler == null || updateResultScheduler.isShutdown()) {
                    Log.I("Update State: Not need to stop waiting on update result");
                    return;
                }
                Log.I("Update State: Stop waiting on update result");
                updateResultScheduler.shutdownNow();
                updateResultScheduler = null;
                return;
            case CONSUMER_CONNECTION_FAILED:
                Log.I("Update State: Fail to connect consumer");
                sendNextState(UpdateState.FOTA_REQUEST_COMPLETE);
                return;
            case CHECK_POLLING_INFO:
                Log.I("Update State: Getting polling info");
                checkPolling();
                return;
            case POLLING_INFO_CHECK_FAILED:
                Log.I("Update State: Fail to get polling info");
                sendNextState(UpdateState.FOTA_REQUEST_COMPLETE);
                return;
            case NEED_TO_POLLING_UPDATE:
                Log.I("Update State: Need to polling update");
                requestPolling();
                sendNextState(UpdateState.FOTA_REQUEST_COMPLETE);
                return;
            case REPORT_UPDATE_SUCCESS:
                Log.I("Update State: Report update result to success by version comparing");
                XDMSecReceiverApiCall.getInstance().xdmUpdateResults(200, AccessoryState.UPDATE_TO_REPORTING.getValue());
                sendNextState(UpdateState.FOTA_REQUEST_COMPLETE);
                return;
            case REPORT_UPDATE_FAILURE:
                Log.I("Update State: Report update result to failure by version comparing");
                XDMSecReceiverApiCall.getInstance().xdmUpdateResults(-1, AccessoryState.UPDATE_TO_REPORTING.getValue());
                sendNextState(UpdateState.FOTA_REQUEST_COMPLETE);
                return;
            case REPORT_UPDATE_NO_RESPONSE:
                Log.I("Update State: Report update result to failure because no response");
                XDBFumoAdp.xdbSetFUMOStatus(XFOTAInterface.XDL_STATE_NO_RESPONSE_UPDATE_RESULT);
                XDMSecReceiverApiCall.getInstance().xdmUpdateResults(-1, AccessoryState.UPDATE_TO_REPORTING.getValue());
                sendNextState(UpdateState.FOTA_REQUEST_COMPLETE);
                return;
            case FOTA_REQUEST_COMPLETE:
                Log.I("Update State: Finish update init");
                stopSelf();
                return;
            default:
                return;
        }
    }

    public /* synthetic */ void lambda$handleState$0$FotaUpdateJobIntentService() {
        getDeviceInfo(UpdateState.INITIALIZE_POLLING);
    }

    public /* synthetic */ void lambda$handleState$1$FotaUpdateJobIntentService() {
        requestPull();
        sendNextState(UpdateState.FOTA_REQUEST_COMPLETE);
    }

    public /* synthetic */ void lambda$handleState$2$FotaUpdateJobIntentService() {
        Log.I("Update State: Expired Waiting time in update progress");
        if (XDBFumoAdp.xdbGetFUMOStatus() == 60) {
            getDeviceInfo(UpdateState.EXPIRED_UPDATE_RESULT_WAITING_TIME);
        }
    }

    private void requestPull() {
        Log.I("");
        XDMSecReceiverApiCall.getInstance().xdmPull();
    }

    private boolean isPushUpdate() {
        if (TextUtils.isEmpty(this.mPushMsg) || this.mPushMsg.length() <= 34) {
            return false;
        }
        try {
            if (UpdateInterface.PUSH_TYPE_DM.equals(this.mPushMsg.substring(31, 33))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.E(e.toString());
            return false;
        }
    }

    private String getPushMessage() {
        if (TextUtils.isEmpty(this.mPushMsg) || this.mPushMsg.length() <= 34) {
            return null;
        }
        try {
            return this.mPushMsg.substring(34, this.mPushMsg.length());
        } catch (Exception e) {
            Log.E(e.toString());
            return null;
        }
    }

    private void requestPush() {
        Log.I("");
        XDMSecReceiverApiCall.getInstance().xdmPush(getPushMessage());
    }

    private void getDeviceInfo(final UpdateState updateState) {
        Log.I("state: " + updateState);
        if (!AccessoryController.getInstance().getConnectionController().isConnected()) {
            if (updateState == UpdateState.EXPIRED_UPDATE_RESULT_WAITING_TIME) {
                Log.W("expired holding update result and device connection is not ready, so report no response");
                sendNextState(UpdateState.REPORT_UPDATE_NO_RESPONSE);
                return;
            }
            Log.W("Device connection is not ready");
            sendNextState(UpdateState.CONSUMER_CONNECTION_FAILED);
        } else if (AccessoryController.getInstance().getRequestController().isInProgress()) {
            Log.W("Accessory is in progress");
        } else {
            AccessoryController.getInstance().getRequestController().checkDeviceInfo(new RequestController.DeviceInfoRequestCallback.Result() {
                /* class com.samsung.android.fotaagent.FotaUpdateJobIntentService.AnonymousClass1 */

                @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.Result
                public void onSuccessAction(ConsumerInfo consumerInfo) {
                    Log.I("getDeviceInfo succeeded");
                    new AccessoryInfoAdapter().updateAccessoryDB(consumerInfo.getAccessoryInfo());
                    int i = AnonymousClass2.$SwitchMap$com$samsung$android$fotaagent$update$UpdateState[updateState.ordinal()];
                    if (i == 3) {
                        FotaUpdateJobIntentService.this.sendNextState(UpdateState.CHECK_POLLING_INFO);
                    } else if (i == 5) {
                        int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
                        FotaProviderState.setFotaBadgeState(xdbGetFUMOStatus);
                        if (FotaProviderState.isInProgress(xdbGetFUMOStatus)) {
                            Log.I("In progress, so show UI");
                            FotaUpdateJobIntentService.this.sendNextState(UpdateState.INITIALIZE_BT_RECONNECT);
                        } else if (consumerInfo.isRunBgUpdate()) {
                            Log.I("notified update from Gear");
                            FotaUpdateJobIntentService.this.sendNextState(UpdateState.INITIALIZE_BT_RECONNECT);
                        }
                    } else if (i != 15) {
                        Log.W("wrong state: " + updateState);
                    } else if (FotaProviderState.isInUpdateReporting(XDBFumoAdp.xdbGetFUMOStatus())) {
                        Log.I("expired holding update result, but received update result. do nothing");
                    } else if (consumerInfo.getFirmwareVersion().equalsIgnoreCase(XDBFumoAdp.xdbGetUpdateFWVer())) {
                        Log.I("expired holding update result, but update succeeded by version comparing");
                        FotaUpdateJobIntentService.this.sendNextState(UpdateState.REPORT_UPDATE_SUCCESS);
                    } else {
                        Log.I("expired holding update result, but update failed by version comparing");
                        FotaUpdateJobIntentService.this.sendNextState(UpdateState.REPORT_UPDATE_FAILURE);
                    }
                }

                @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.Result
                public void onFailure(RequestError requestError) {
                    Log.I("getDeviceInfo failed");
                    if (requestError != null) {
                        int i = AnonymousClass2.$SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError[requestError.ordinal()];
                        if (i != 1) {
                            if (i == 2) {
                                Log.I("detected different device");
                                FotaProviderState.blockActionDuringChangedDeviceProcess();
                                XDMFileTransferManager.changeDevice();
                            }
                        } else if (updateState == UpdateState.INITIALIZE_POLLING) {
                            Log.I("polling will be blocked by policy");
                            FotaUpdateJobIntentService.this.sendNextState(UpdateState.CHECK_POLLING_INFO);
                            return;
                        } else {
                            return;
                        }
                    }
                    if (updateState == UpdateState.INITIALIZE_POLLING) {
                        FotaUpdateJobIntentService.this.sendNextState(UpdateState.CONSUMER_CONNECTION_FAILED);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: com.samsung.android.fotaagent.FotaUpdateJobIntentService$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$accessory$fotaprovider$controller$RequestError = new int[RequestError.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(36:0|(2:1|2)|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|40) */
        /* JADX WARNING: Can't wrap try/catch for region: R(37:0|(2:1|2)|3|5|6|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|40) */
        /* JADX WARNING: Can't wrap try/catch for region: R(38:0|1|2|3|5|6|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|40) */
        /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0032 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x003c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0047 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0052 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x005d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0068 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0073 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x007f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x008b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0097 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x00a3 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00af */
        /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x00bb */
        /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x00c7 */
        static {
            /*
            // Method dump skipped, instructions count: 212
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaagent.FotaUpdateJobIntentService.AnonymousClass2.<clinit>():void");
        }
    }

    private void checkPolling() {
        NetworkResult execute = new NetConnect().execute(this, new PollingAction());
        Polling.calculateNextPollingTime();
        Polling.startPollingTimer(this);
        if (execute.isSuccess()) {
            Log.I("Receive result: success in PollingAction");
            if (execute.isUpdateAvailable()) {
                sendNextState(UpdateState.NEED_TO_POLLING_UPDATE);
            } else {
                sendNextState(UpdateState.FOTA_REQUEST_COMPLETE);
            }
        } else {
            Log.I("Receive result: fail in PollingAction");
            sendNextState(UpdateState.POLLING_INFO_CHECK_FAILED);
        }
    }

    private void requestPolling() {
        Log.I("");
        XDMSecReceiverApiCall.getInstance().xdmPolling();
    }
}
