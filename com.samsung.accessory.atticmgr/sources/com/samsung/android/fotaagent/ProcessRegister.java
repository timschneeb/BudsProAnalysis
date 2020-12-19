package com.samsung.android.fotaagent;

import android.content.Intent;
import com.samsung.android.fotaagent.register.RegisterInterface;
import com.samsung.android.fotaagent.register.RegisterType;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;

public class ProcessRegister {
    public void registerDeviceOnForeground() {
        registerDevice(RegisterType.FOREGROUND);
    }

    public void registerDeviceOnBackground() {
        registerDevice(RegisterType.BACKGROUND);
    }

    public void registerDeviceOnBackgroundWithDelay() {
        registerDevice(RegisterType.BACKGROUND_WITH_DELAY);
    }

    private void registerDevice(RegisterType registerType) {
        Log.I("register type: " + registerType);
        if (FotaProviderState.isDeviceRegisteredDB()) {
            Log.D("DB might be not cleared before register, so reset");
            FotaProviderState.resetDataAndStopAlarms(FotaProviderInitializer.getContext());
        }
        callRegisterJobIntentService(registerType);
    }

    public void registerPushIfNeeded() {
        if (!FotaProviderState.isPushRegisteredDB()) {
            Log.I("push is not registered - try to register push");
            callRegisterJobIntentService(RegisterType.PUSH);
            return;
        }
        Log.I("push is already registered");
    }

    public void updatePushId() {
        if (FotaProviderState.isPushRegisteredDB()) {
            Log.I("update push id if push is already registered");
            callRegisterJobIntentService(RegisterType.PUSH);
        }
    }

    private void callRegisterJobIntentService(RegisterType registerType) {
        Intent intent = new Intent(FotaProviderInitializer.getContext(), FotaRegisterJobIntentService.class);
        intent.addFlags(32);
        intent.putExtra(RegisterInterface.REGISTER_TYPE, registerType);
        FotaRegisterJobIntentService.enqueueWork(FotaProviderInitializer.getContext(), FotaRegisterJobIntentService.class, FotaServiceJobId.INSTANCE.REGISTER_JOB_ID, intent);
    }
}
