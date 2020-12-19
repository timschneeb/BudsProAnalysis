package com.samsung.accessory.hearablemgr.core.service;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import com.samsung.accessory.fotaprovider.AccessoryEventHandler;
import com.samsung.accessory.fotaprovider.controller.ConsumerInfo;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.WaitTimer;
import com.samsung.accessory.hearablemgr.core.fota.manager.FOTAMainManager;
import com.samsung.accessory.hearablemgr.core.fota.manager.FotaRequestController;
import com.samsung.accessory.hearablemgr.core.fota.util.FotaBinaryFile;
import com.samsung.accessory.hearablemgr.core.fota.util.FotaUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.Msg;
import com.samsung.accessory.hearablemgr.core.service.message.MsgFotaControl;
import com.samsung.accessory.hearablemgr.core.service.message.MsgFotaDownloadData;
import com.samsung.accessory.hearablemgr.core.service.message.MsgFotaResult;
import com.samsung.accessory.hearablemgr.core.service.message.MsgFotaSession;
import com.samsung.accessory.hearablemgr.core.service.message.MsgFotaUpdated;
import java.io.File;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import seccompat.android.util.Log;

public class FotaTransferManager {
    private static final int FOTA_NO_RESPONSE_TIMEOUT = 20000;
    private static final int FOTA_TIMEOUT = 1000000;
    private static final String TAG = "Attic_FotaTransferManager";
    private int MTU_SIZE;
    private FotaBinaryFile mBinaryFile;
    private CoreService mCoreService;
    private int mCurEntryId;
    private int mCurFOTAProgress;
    private Timer mFOTAResponseWaitingTimer = new Timer();
    private WaitTimer mFotaTimer;
    private long mLastEntryOffset;
    private boolean mLastFragment;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.service.FotaTransferManager.AnonymousClass4 */

        /* JADX WARNING: Removed duplicated region for block: B:12:0x0046  */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x0078  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r5, android.content.Intent r6) {
            /*
            // Method dump skipped, instructions count: 124
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.service.FotaTransferManager.AnonymousClass4.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    CoreService.OnSppMessageListener mSppListener = new CoreService.OnSppMessageListener() {
        /* class com.samsung.accessory.hearablemgr.core.service.FotaTransferManager.AnonymousClass1 */

        @Override // com.samsung.accessory.hearablemgr.core.service.CoreService.OnSppMessageListener
        public void onSppMessage(Msg msg) {
            StringBuilder sb = new StringBuilder();
            sb.append("Msg : ");
            sb.append(String.format("%02X ", Byte.valueOf(msg.id)));
            Log.d(FotaTransferManager.TAG, sb.toString());
            switch (msg.id) {
                case -71:
                    Log.i(FotaTransferManager.TAG, "== MSG_ID_FOTA_RESULT ==");
                    Application.getCoreService().sendSppMessage(new MsgFotaResult());
                    if (((MsgFotaResult) msg).mResult == 0) {
                        Log.d(FotaTransferManager.TAG, "====FOTA UPDATE SUCCESS====");
                        Application.getCoreService().getEarBudsFotaInfo().printFota();
                        AccessoryEventHandler.getInstance().reportUpdateResult(new ConsumerInfo(Application.getCoreService().getEarBudsFotaInfo().deviceId, Application.getCoreService().getEarBudsFotaInfo().modelNumber, Application.getCoreService().getEarBudsFotaInfo().salesCode, Application.getCoreService().getEarBudsFotaInfo().firmwareVersion, Application.getCoreService().getEarBudsFotaInfo().uniqueNumber, Application.getCoreService().getEarBudsFotaInfo().serialNumber), true);
                        Log.d(FotaTransferManager.TAG, "send report update result : true");
                    }
                    FotaUtil.setLastDoneTime(Calendar.getInstance().getTimeInMillis());
                    return;
                case -70:
                default:
                    return;
                case -69:
                    Log.i(FotaTransferManager.TAG, "== RECV : MSG_ID_FOTA_OPEN ==");
                    FotaTransferManager.this.mFotaTimer.remove(-69);
                    MsgFotaSession msgFotaSession = (MsgFotaSession) msg;
                    Log.i(FotaTransferManager.TAG, "MSG_ID_FOTA_OPEN : mErrorCode=[success=0, fail=1] " + msgFotaSession.mErrorCode);
                    if (msgFotaSession.mErrorCode == 0) {
                        FotaTransferManager.this.mFotaTimer.start(-68, 20000);
                        return;
                    } else if (msgFotaSession.mErrorCode == -112) {
                        Log.d(FotaTransferManager.TAG, "Fota_ErrorCode : UnCoupled");
                        FOTAMainManager.getInstance().updateFOTACopyProcessResult(FotaUtil.ACTION_FOTA_PROGRESS_COPY_RESULT, 2);
                        return;
                    } else {
                        Log.d(FotaTransferManager.TAG, "Fota_ErrorCode : else error");
                        FOTAMainManager.getInstance().updateFOTACopyProcessResult(FotaUtil.ACTION_FOTA_PROGRESS_COPY_RESULT, 3);
                        return;
                    }
                case -68:
                    Log.i(FotaTransferManager.TAG, "== RECV : MSG_ID_FOTA_CONTROL ==");
                    MsgFotaControl msgFotaControl = (MsgFotaControl) msg;
                    Log.d(FotaTransferManager.TAG, "ControlID = " + msgFotaControl.mControlID);
                    int i = msgFotaControl.mControlID;
                    if (i == 0) {
                        FotaTransferManager.this.mFotaTimer.remove(-68);
                        FotaTransferManager.this.MTU_SIZE = msgFotaControl.mMtuSize;
                        Log.i(FotaTransferManager.TAG, "-- CONTROL_ID_SEND_MTU : " + FotaTransferManager.this.MTU_SIZE + " --");
                        Application.getCoreService().sendSppMessage(new MsgFotaControl(msgFotaControl.mControlID, msgFotaControl.mMtuSize));
                        return;
                    } else if (i == 1) {
                        Log.i(FotaTransferManager.TAG, "-- CONTROL_ID_READY_TO_DOWNLOAD --" + msgFotaControl.mId);
                        Application.getCoreService().sendSppMessage(new MsgFotaControl(msgFotaControl.mControlID, msgFotaControl.mId));
                        FotaTransferManager.this.mCurEntryId = msgFotaControl.mId;
                        return;
                    } else {
                        return;
                    }
                case -67:
                    Log.i(FotaTransferManager.TAG, "== RECV : MSG_ID_FOTA_DOWNLOAD_DATA ==");
                    MsgFotaDownloadData msgFotaDownloadData = (MsgFotaDownloadData) msg;
                    for (int i2 = 0; i2 < msgFotaDownloadData.mReqeustPacketNum; i2++) {
                        MsgFotaDownloadData msgFotaDownloadData2 = new MsgFotaDownloadData(FotaTransferManager.this.mBinaryFile, FotaTransferManager.this.mCurEntryId, msgFotaDownloadData.mReceivedOffset + ((long) (FotaTransferManager.this.MTU_SIZE * i2)), FotaTransferManager.this.MTU_SIZE, true);
                        FotaTransferManager.this.mLastFragment = msgFotaDownloadData2.isLastFragment();
                        FotaTransferManager.this.mLastEntryOffset = msgFotaDownloadData2.getOffset();
                        Log.d(FotaTransferManager.TAG, "mLastFragment : " + FotaTransferManager.this.mLastFragment);
                        Log.d(FotaTransferManager.TAG, "mLastEntryOffset : " + FotaTransferManager.this.mLastEntryOffset);
                        Log.d(FotaTransferManager.TAG, "msgFotaDownloadDataRecv.mReqeustPacketNum : " + msgFotaDownloadData.mReqeustPacketNum);
                        Application.getCoreService().sendSppMessage(msgFotaDownloadData2);
                        if (!FotaTransferManager.this.mLastFragment) {
                            try {
                                Thread.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            return;
                        }
                    }
                    return;
                case -66:
                    Log.i(FotaTransferManager.TAG, "== MSG_ID_FOTA_UPDATE ==");
                    MsgFotaUpdated msgFotaUpdated = (MsgFotaUpdated) msg;
                    int i3 = msgFotaUpdated.mUpdateId;
                    if (i3 == 0) {
                        Log.e(FotaTransferManager.TAG, " -- MsgFotaUpdated.UPDATE_ID_PERCENT : " + msgFotaUpdated.mPercent + "% --");
                        if (FotaRequestController.isInProgress) {
                            short s = (short) msgFotaUpdated.mPercent;
                            FotaTransferManager.this.mCurFOTAProgress = s;
                            if (FotaRequestController.mRequestFileTransferCallback != null) {
                                FotaRequestController.mRequestFileTransferCallback.onFileProgress(s);
                            }
                            Log.d(FotaTransferManager.TAG, "[ACTION_FOTA_PROGRESS_COPYING] percent : " + ((int) s) + "%");
                            return;
                        }
                        return;
                    } else if (i3 == 1) {
                        Log.i(FotaTransferManager.TAG, "-- MsgFotaUpdated.UPDATE_ID_STATE_CHANGED : " + msgFotaUpdated.mState + ", " + msgFotaUpdated.mErrorCode + " --");
                        Application.getCoreService().sendSppMessage(new MsgFotaUpdated());
                        if (msgFotaUpdated.mState == 0) {
                            new Handler().postDelayed(new Runnable() {
                                /* class com.samsung.accessory.hearablemgr.core.service.FotaTransferManager.AnonymousClass1.AnonymousClass1 */

                                public void run() {
                                    FOTAMainManager.getInstance().updateFOTACopyProcessResult(FotaUtil.ACTION_FOTA_PROGRESS_COPY_RESULT, 1);
                                }
                            }, 1000);
                            FotaTransferManager.this.initFOTAStatus();
                            Log.d(FotaTransferManager.TAG, "====FOTA SEND SUCCESS====");
                            Application.getCoreService().getEarBudsFotaInfo().printFota();
                            FotaRequestController.mRequestResultCallback.onSuccess(new ConsumerInfo(Application.getCoreService().getEarBudsFotaInfo().deviceId, Application.getCoreService().getEarBudsFotaInfo().modelNumber, Application.getCoreService().getEarBudsFotaInfo().salesCode, Application.getCoreService().getEarBudsFotaInfo().firmwareVersion, Application.getCoreService().getEarBudsFotaInfo().uniqueNumber, Application.getCoreService().getEarBudsFotaInfo().serialNumber));
                            Log.d(FotaTransferManager.TAG, "FotaRequestController.isInProgress false");
                            FotaRequestController.isInProgress = false;
                            return;
                        }
                        Log.d(FotaTransferManager.TAG, "fota_download_control>> close Download. download fail reason : " + msgFotaUpdated.mErrorCode);
                        FOTAMainManager.getInstance().updateFOTACopyProcessResult(FotaUtil.ACTION_FOTA_PROGRESS_COPY_RESULT, 3);
                        FotaTransferManager.this.initFOTAStatus();
                        return;
                    } else {
                        return;
                    }
            }
        }
    };
    private Handler mTimeOutHandler = new Handler() {
        /* class com.samsung.accessory.hearablemgr.core.service.FotaTransferManager.AnonymousClass3 */

        public void handleMessage(Message message) {
            Log.d(FotaTransferManager.TAG, "read Message :" + Integer.toHexString(message.what & 255));
            Log.d(FotaTransferManager.TAG, "RECEIVE offset : " + message.arg1);
            if (message.what == -69) {
                FOTAMainManager.getInstance().updateFOTACopyProcessResult(FotaUtil.ACTION_FOTA_PROGRESS_COPY_RESULT, 3);
                FotaTransferManager.this.initFOTAStatus();
            }
        }
    };
    private TimerTask mTimerTask;

    public FotaTransferManager(CoreService coreService) {
        this.mCoreService = coreService;
        this.mCoreService.registerSppMessageListener(this.mSppListener);
        this.mFotaTimer = new WaitTimer(this.mTimeOutHandler);
        this.mFotaTimer.reset();
        Application.getContext().registerReceiver(this.mReceiver, getIntentFilter());
    }

    public void startFota(String str) {
        Log.d(TAG, "startFota() : " + str);
        this.mBinaryFile = new FotaBinaryFile(new File(str));
        if (!this.mBinaryFile.open()) {
            FOTAMainManager.getInstance().updateFOTACopyProcessResult(FotaUtil.ACTION_FOTA_PROGRESS_COPY_RESULT, 3);
            return;
        }
        this.MTU_SIZE = 0;
        this.mCurEntryId = 0;
        this.mLastFragment = false;
        this.mLastEntryOffset = 0;
        Application.getCoreService().sendSppMessage(new MsgFotaSession(this.mBinaryFile));
        this.mFotaTimer.start(-69, 20000);
        runFOTAResponseWaitingTimer();
    }

    public int getLatestFOTAProgress() {
        Log.d(TAG, "getLatestFOTAProgress() - mCurFOTAProgress: " + this.mCurFOTAProgress + "%");
        return this.mCurFOTAProgress;
    }

    public void destroy() {
        Log.d(TAG, "destroy()");
        this.mCoreService.unregisterSppMessageListener(this.mSppListener);
        Application.getContext().unregisterReceiver(this.mReceiver);
    }

    public void killFOTAProcess() {
        Log.d(TAG, "killFOTAProcess()");
        FOTAMainManager.getInstance().updateFOTACopyProcessResult(FotaUtil.ACTION_FOTA_PROGRESS_COPY_RESULT, 3);
        initFOTAStatus();
    }

    public boolean isFOTAEnable() {
        boolean z = FotaRequestController.isInProgress;
        Log.d(TAG, "isFOTAEnable(): " + z);
        return z;
    }

    private void runFOTAResponseWaitingTimer() {
        TimerTask timerTask = this.mTimerTask;
        if (timerTask != null) {
            timerTask.cancel();
            this.mTimerTask = null;
            Log.d(TAG, "mFOTAResponseWaitingTimer is canceled!!");
        }
        this.mTimerTask = FOTAUpdateResponseTimeOut();
        this.mFOTAResponseWaitingTimer.schedule(this.mTimerTask, 1000000);
        Log.d(TAG, "runFOTAResponseWaitingTimer");
    }

    private TimerTask FOTAUpdateResponseTimeOut() {
        return new TimerTask() {
            /* class com.samsung.accessory.hearablemgr.core.service.FotaTransferManager.AnonymousClass2 */

            public void run() {
                Log.i(FotaTransferManager.TAG, "FOTAUpdateResponseTimeOut");
                FotaTransferManager.this.mTimerTask = null;
                FotaTransferManager.this.killFOTAProcess();
            }
        };
    }

    private void killFOTAResponseWaitingTimer() {
        TimerTask timerTask = this.mTimerTask;
        if (timerTask != null) {
            timerTask.cancel();
            this.mTimerTask = null;
            Log.i(TAG, "killFOTAResponseWaitingTimer");
        }
        WaitTimer waitTimer = this.mFotaTimer;
        if (waitTimer != null) {
            waitTimer.reset();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initFOTAStatus() {
        Log.d(TAG, "initFOTAStatus()");
        killFOTAResponseWaitingTimer();
        FotaUtil.setEmergencyFOTAIsRunning(false);
        this.mCurFOTAProgress = 0;
    }

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_MSG_ID_DEBUG_GET_ALL_DATA);
        intentFilter.addAction(CoreService.ACTION_DEVICE_DISCONNECTED);
        return intentFilter;
    }
}
