package com.samsung.android.fotaagent;

import android.content.Intent;
import androidx.core.app.SafeJobIntentService;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.XDMSecReceiverApiCall;
import com.accessorydm.db.file.AccessoryInfoAdapter;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBPollingAdp;
import com.accessorydm.db.file.XDBRegistrationAdp;
import com.accessorydm.ui.handler.XDMToastHandler;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.accessory.fotaprovider.controller.ConsumerInfo;
import com.samsung.accessory.fotaprovider.controller.RequestController;
import com.samsung.accessory.fotaprovider.controller.RequestError;
import com.samsung.android.fotaagent.network.NetConnect;
import com.samsung.android.fotaagent.network.action.DeviceRegistrationAction;
import com.samsung.android.fotaagent.network.action.NetworkResult;
import com.samsung.android.fotaagent.network.action.PushRegistrationAction;
import com.samsung.android.fotaagent.polling.Polling;
import com.samsung.android.fotaagent.push.FCM;
import com.samsung.android.fotaagent.push.FCMResult;
import com.samsung.android.fotaagent.push.SPP;
import com.samsung.android.fotaagent.push.SPPResult;
import com.samsung.android.fotaagent.push.SPPResultReceiver;
import com.samsung.android.fotaagent.register.InitDeviceWork;
import com.samsung.android.fotaagent.register.RegisterInterface;
import com.samsung.android.fotaagent.register.RegisterState;
import com.samsung.android.fotaagent.register.RegisterType;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.GeneralUtil;
import com.samsung.android.fotaprovider.util.OperatorUtil;
import com.samsung.android.fotaprovider.util.type.DeviceType;
import com.sec.android.fotaprovider.R;

public class FotaRegisterJobIntentService extends SafeJobIntentService {
    /* access modifiers changed from: protected */
    @Override // androidx.core.app.JobIntentService
    public void onHandleWork(Intent intent) {
        Log.D("");
        RegisterType registerType = (RegisterType) intent.getSerializableExtra(RegisterInterface.REGISTER_TYPE);
        RegisterState registerState = (RegisterState) intent.getSerializableExtra(RegisterInterface.REGISTER_STATE);
        if (registerState == null) {
            sendNextState(registerType, RegisterState.CHECK_NEXT_STATE);
        } else {
            handleState(registerType, registerState, intent);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendNextState(RegisterType registerType, RegisterState registerState) {
        Log.D("");
        sendNextState(registerType, registerState, 0);
    }

    private void sendNextState(RegisterType registerType, RegisterState registerState, int i) {
        Log.D("");
        Intent intent = new Intent(FotaProviderInitializer.getContext(), FotaRegisterJobIntentService.class);
        intent.putExtra(RegisterInterface.REGISTER_TYPE, registerType);
        intent.putExtra(RegisterInterface.REGISTER_STATE, registerState);
        intent.putExtra(RegisterInterface.REGISTER_ERROR, i);
        intent.addFlags(32);
        enqueueWork(FotaProviderInitializer.getContext(), FotaRegisterJobIntentService.class, FotaServiceJobId.INSTANCE.REGISTER_JOB_ID, intent);
    }

    private void sendRegisterPushIdState(RegisterType registerType, String str, String str2) {
        Log.D("");
        Intent intent = new Intent(FotaProviderInitializer.getContext(), FotaRegisterJobIntentService.class);
        intent.putExtra(RegisterInterface.REGISTER_TYPE, registerType);
        intent.putExtra(RegisterInterface.REGISTER_STATE, RegisterState.REGISTERING_PUSH_ID);
        intent.putExtra(RegisterInterface.REGISTER_FCM_ID, str);
        intent.putExtra(RegisterInterface.REGISTER_SPP_ID, str2);
        intent.addFlags(32);
        enqueueWork(FotaProviderInitializer.getContext(), FotaRegisterJobIntentService.class, FotaServiceJobId.INSTANCE.REGISTER_JOB_ID, intent);
    }

    private void handleState(RegisterType registerType, RegisterState registerState, Intent intent) {
        switch (registerState) {
            case CHECK_NEXT_STATE:
                Log.I("Register State: Check condition to decide next state");
                checkNextState(registerType);
                return;
            case INITIALIZING_DEVICE:
                Log.I("Register State: Device initialization");
                initDevice(registerType);
                return;
            case DEVICE_INITIALIZATION_FAILED:
                Log.I("Register State: Fail to initialize device");
                sendNextState(registerType, RegisterState.REGISTRATION_COMPLETE);
                showToastIfAvailable(registerType, DeviceType.get().getTextType().getConnectionFailedMessageId());
                return;
            case REGISTERING_DEVICE:
                Log.I("Register State: Registering device");
                registerDevice(registerType);
                return;
            case DEVICE_REGISTRATION_FAILED:
                Log.I("Register State: Fail to register device");
                XDBRegistrationAdp.setDeviceRegistrationStatus(0);
                sendNextState(registerType, RegisterState.REGISTRATION_COMPLETE);
                int intExtra = intent.getIntExtra(RegisterInterface.REGISTER_ERROR, 0);
                if (intExtra == 400) {
                    showToastIfAvailable(registerType, R.string.STR_DM_UNABLE_NETWORK);
                    return;
                } else if (intExtra != 410) {
                    showToastIfAvailable(registerType, R.string.STR_REGISTRATION_FAILED);
                    return;
                } else {
                    showToastIfAvailable(registerType, R.string.STR_ROAMING_WIFI_DISCONNECTED);
                    return;
                }
            case DEVICE_REGISTRATION_SUCCESS:
                Log.I("Register State: Success to register device");
                registeredDevice(registerType);
                return;
            case REGISTERING_POLLING:
                Log.I("Register State: Registering polling");
                registerPolling(registerType);
                return;
            case REGISTERING_PUSH:
                Log.I("Register State: Registering push");
                registerPush(registerType);
                return;
            case REGISTERING_PUSH_ID:
                Log.I("Register State: Registering push id");
                registerPushID(registerType, intent.getStringExtra(RegisterInterface.REGISTER_FCM_ID), intent.getStringExtra(RegisterInterface.REGISTER_SPP_ID));
                return;
            case PUSH_REGISTRATION_FAILED:
                Log.I("Register State: Fail to register push");
                sendNextState(registerType, RegisterState.REGISTRATION_COMPLETE);
                return;
            case REGISTRATION_COMPLETE:
                Log.I("Register State: Finish registration");
                return;
            default:
                return;
        }
    }

    private void checkNextState(RegisterType registerType) {
        int deviceRegistrationStatus = XDBRegistrationAdp.getDeviceRegistrationStatus();
        if (deviceRegistrationStatus != 0) {
            if (deviceRegistrationStatus == 1) {
                Log.I("registered. go to next step");
                if (AnonymousClass3.$SwitchMap$com$samsung$android$fotaagent$register$RegisterType[registerType.ordinal()] != 1) {
                    sendNextState(registerType, RegisterState.REGISTERING_POLLING);
                } else {
                    sendNextState(registerType, RegisterState.REGISTERING_PUSH);
                }
            } else if (deviceRegistrationStatus != 2) {
                Log.W("no more status to check");
                sendNextState(registerType, RegisterState.REGISTRATION_COMPLETE);
            } else {
                Log.I("in registering...");
                sendNextState(registerType, RegisterState.REGISTERING_DEVICE);
            }
        } else if (XDBFumoAdp.xdbGetFUMOStatus() == 0) {
            Log.I("need to register, so initialize device first");
            sendNextState(registerType, RegisterState.INITIALIZING_DEVICE);
        } else {
            Log.I("do not need to register, abnormal case, reset all data");
            FotaProviderState.resetDataAndStopAlarms(FotaProviderInitializer.getContext());
            sendNextState(registerType, RegisterState.REGISTRATION_COMPLETE);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: com.samsung.android.fotaagent.FotaRegisterJobIntentService$3  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$fotaagent$register$RegisterType = new int[RegisterType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(24:0|(2:1|2)|3|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|(3:25|26|28)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(26:0|(2:1|2)|3|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|28) */
        /* JADX WARNING: Can't wrap try/catch for region: R(27:0|1|2|3|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|28) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0048 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0053 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x005e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0069 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0075 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0081 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x008d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0027 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0032 */
        static {
            /*
            // Method dump skipped, instructions count: 154
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaagent.FotaRegisterJobIntentService.AnonymousClass3.<clinit>():void");
        }
    }

    private void initDevice(final RegisterType registerType) {
        if (!AccessoryController.getInstance().getConnectionController().isConnected()) {
            Log.W("Device connection is not ready");
            sendNextState(registerType, RegisterState.DEVICE_INITIALIZATION_FAILED);
        } else if (AccessoryController.getInstance().getRequestController().isInProgress()) {
            Log.W("Accessory is in progress");
        } else if (XDBRegistrationAdp.getDeviceRegistrationStatus() == 0 || registerType == RegisterType.FOREGROUND) {
            Log.I("start to initialize device by " + registerType);
            InitDeviceWork.getInstance().schedule(registerType, new InitDeviceWork.WorkResult() {
                /* class com.samsung.android.fotaagent.FotaRegisterJobIntentService.AnonymousClass1 */
                RegisterType scheduledRegisterType = registerType;

                @Override // com.samsung.android.fotaagent.register.InitDeviceWork.WorkResult
                public void onWorked() {
                    Log.I("scheduled registerType: " + this.scheduledRegisterType);
                    FotaRegisterJobIntentService.this.startInitDevice(this.scheduledRegisterType);
                }
            });
        } else {
            Log.I("already initializing device");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startInitDevice(final RegisterType registerType) {
        if (XDBRegistrationAdp.getDeviceRegistrationStatus() == 0) {
            Log.I("initializing device to register");
            DeviceType.get().setDefaultSettings(this);
            AccessoryController.getInstance().getRequestController().initializeDeviceInfo(new RequestController.RequestCallback.Result() {
                /* class com.samsung.android.fotaagent.FotaRegisterJobIntentService.AnonymousClass2 */

                @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.Result
                public void onSuccessAction(ConsumerInfo consumerInfo) {
                    Log.I("initializeDeviceInfo : succeeded by " + registerType);
                    new AccessoryInfoAdapter().updateAccessoryDB(consumerInfo.getAccessoryInfo());
                    DeviceType.reloadDeviceType();
                    XDBRegistrationAdp.setDeviceRegistrationStatus(2);
                    FotaRegisterJobIntentService.this.sendNextState(registerType, RegisterState.CHECK_NEXT_STATE);
                }

                @Override // com.samsung.accessory.fotaprovider.controller.RequestController.RequestCallback.Result
                public void onFailure(RequestError requestError) {
                    Log.I("initializeDeviceInfo : failed by " + registerType);
                    XDBRegistrationAdp.setDeviceRegistrationStatus(0);
                    FotaRegisterJobIntentService.this.sendNextState(registerType, RegisterState.DEVICE_INITIALIZATION_FAILED);
                }
            });
            return;
        }
        Log.I("already registered device");
    }

    private void registerDevice(RegisterType registerType) {
        Log.I("registerType: " + registerType);
        showToastIfAvailable(registerType, R.string.STR_DM_CONNECTING_SERVER);
        if (FotaProviderState.isDeviceRegisteredDB()) {
            Log.W("duplicated request to register, so skip and keep going previous request");
            return;
        }
        XDMDmUtils.getInstance().xdmRegisterFactoryBootstrap();
        NetworkResult execute = new NetConnect().execute(this, new DeviceRegistrationAction());
        if (execute.isSuccess()) {
            Log.I("Receive result: success in DeviceRegistrationAction by " + registerType);
            XDBRegistrationAdp.setDeviceRegistrationStatus(1);
            sendNextState(registerType, RegisterState.DEVICE_REGISTRATION_SUCCESS);
            return;
        }
        Log.W("Receive result: fail in DeviceRegistrationAction by " + registerType);
        sendNextState(registerType, RegisterState.DEVICE_REGISTRATION_FAILED, execute.getErrorType());
    }

    private void registeredDevice(RegisterType registerType) {
        showToastIfAvailable(registerType, R.string.STR_REGISTRATION_SUCCESS);
        requestInit(registerType);
        sendNextState(registerType, RegisterState.REGISTERING_POLLING);
    }

    private void requestInit(RegisterType registerType) {
        Log.I("");
        XDMSecReceiverApiCall.getInstance().xdmDeviceRegistration(isForeground(registerType) ? 1 : 2);
    }

    private void registerPolling(RegisterType registerType) {
        if (DeviceType.get().isPollingSupported()) {
            if (!Polling.isPassedPollingTime()) {
                Log.I("Register polling time");
                Polling.calculateNextPollingTime();
                Polling.startPollingTimer(this);
            }
        } else if (XDBPollingAdp.xdbGetNextPollingTime() != 0) {
            Log.I("Unregister polling time, change polling time to zero");
            XDBPollingAdp.xdbSetNextPollingTime(0);
            Polling.stopPollingTimer(this);
        }
        sendNextState(registerType, RegisterState.REGISTERING_PUSH);
    }

    private void registerPush(RegisterType registerType) {
        if (OperatorUtil.isSPP()) {
            registerSPP(registerType);
        } else if (!GeneralUtil.isGSFPackagedInstalled()) {
            Log.W("GSF package is not installed. cannot support FCM");
        } else {
            registerFCM(registerType);
        }
    }

    private void registerSPP(RegisterType registerType) {
        SPP spp = SPP.getSPP();
        spp.setSPPReceiver(new SPPResultReceiver(registerType) {
            /* class com.samsung.android.fotaagent.$$Lambda$FotaRegisterJobIntentService$6cWt2M8lpgXHS40b5caeZCTfIc */
            private final /* synthetic */ RegisterType f$1;

            {
                this.f$1 = r2;
            }

            @Override // com.samsung.android.fotaagent.push.SPPResultReceiver
            public final void onSPPResponse(SPPResult sPPResult) {
                FotaRegisterJobIntentService.this.lambda$registerSPP$0$FotaRegisterJobIntentService(this.f$1, sPPResult);
            }
        });
        spp.requestID(this);
    }

    public /* synthetic */ void lambda$registerSPP$0$FotaRegisterJobIntentService(RegisterType registerType, SPPResult sPPResult) {
        if (sPPResult != null) {
            try {
                if (sPPResult.isSuccess()) {
                    Log.I("Receive result: success in SPP requestID");
                    Log.H("spp id" + sPPResult.getPushID());
                    sendRegisterPushIdState(registerType, "", sPPResult.getPushID());
                    return;
                }
                Log.W("Receive result: fail in SPP error: " + sPPResult.getError());
            } finally {
                SPP.getSPP().setSPPReceiver(null);
            }
        }
        sendNextState(registerType, RegisterState.PUSH_REGISTRATION_FAILED);
        SPP.getSPP().setSPPReceiver(null);
    }

    private void registerFCM(RegisterType registerType) {
        FCMResult registrationIDByBackground = FCM.instance.getRegistrationIDByBackground(FotaProviderInitializer.getContext());
        if (registrationIDByBackground != null) {
            if (registrationIDByBackground.isSuccess()) {
                Log.I("Receive result: success in FCM requestID");
                Log.H("fcm id:" + registrationIDByBackground.getPushID());
                sendRegisterPushIdState(registerType, registrationIDByBackground.getPushID(), "");
                return;
            }
            registrationIDByBackground.setNextRetry();
            Log.W("Receive result: fail in FCM error: " + registrationIDByBackground.getErrorMsg());
        }
        sendNextState(registerType, RegisterState.PUSH_REGISTRATION_FAILED);
    }

    private void registerPushID(RegisterType registerType, String str, String str2) {
        NetworkResult execute = new NetConnect().execute(this, new PushRegistrationAction(str, str2));
        if (execute.isSuccess()) {
            if (OperatorUtil.isSPP()) {
                Log.I("Receive result: success SPP in PushRegistrationAction by " + registerType);
                XDBRegistrationAdp.setPushRegistrationStatus(1);
            } else {
                Log.I("Receive result: success FCM in PushRegistrationAction by " + registerType);
                XDBRegistrationAdp.setPushRegistrationStatus(2);
            }
            sendNextState(registerType, RegisterState.REGISTRATION_COMPLETE);
            return;
        }
        if (execute.getErrorType() == 440) {
            FotaProviderState.resetDataAndStopAlarms(this);
        }
        Log.I("Receive result: fail in PushRegistrationAction by " + registerType);
        sendNextState(registerType, RegisterState.PUSH_REGISTRATION_FAILED);
    }

    private void showToastIfAvailable(RegisterType registerType, int i) {
        if (isForeground(registerType)) {
            XDMToastHandler.xdmShowToast(getApplicationContext().getString(i), 0);
        }
    }

    private boolean isForeground(RegisterType registerType) {
        return registerType == RegisterType.FOREGROUND;
    }
}
