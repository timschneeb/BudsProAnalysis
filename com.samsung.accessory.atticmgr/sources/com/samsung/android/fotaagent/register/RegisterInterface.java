package com.samsung.android.fotaagent.register;

import com.samsung.android.fotaprovider.util.type.DeviceType;

public interface RegisterInterface {
    public static final long DELAY_PERIOD_FOR_INITIALIZING_DEVICE = DeviceType.get().getWaitingTimeInMillisForInitializingDevice();
    public static final String REGISTER_ERROR = "register_error";
    public static final String REGISTER_FCM_ID = "register_fcm_id";
    public static final String REGISTER_SPP_ID = "register_spp_id";
    public static final String REGISTER_STATE = "register_state";
    public static final String REGISTER_TYPE = "registerType";
}
