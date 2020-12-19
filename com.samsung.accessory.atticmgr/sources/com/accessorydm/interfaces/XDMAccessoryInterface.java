package com.accessorydm.interfaces;

public interface XDMAccessoryInterface {
    public static final int XDM_ACCESSORY_CHECKINFO_FAIL = 0;
    public static final int XDM_ACCESSORY_CHECKINFO_OK = 1;
    public static final int XDM_ACCESSORY_COPY_RETRY_COUNT_MAX = 3;
    public static final int XDM_ACCESSORY_DEVICE_NOT_REGISTERED = 0;
    public static final int XDM_ACCESSORY_DEVICE_REGISTERED = 1;
    public static final int XDM_ACCESSORY_DEVICE_REGISTERING = 2;
    public static final String XDM_ACCESSORY_INFO_COUNTRY_DEFAULT = "gb";
    public static final String XDM_ACCESSORY_INFO_MCC_DEFAULT = "234";
    public static final String XDM_ACCESSORY_INFO_PUSHTYPE_DEFAULT = "FCM";
    public static final int XDM_ACCESSORY_LOWBATTERY_RETRY_COUNT_MAX = 3;
    public static final int XDM_ACCESSORY_PUSH_FCM_REGISTERED = 2;
    public static final int XDM_ACCESSORY_PUSH_NOT_REGISTERED = 0;
    public static final int XDM_ACCESSORY_PUSH_SPP_REGISTERED = 1;

    public enum XDMAccessoryCheckState {
        XDM_ACCESSORY_CHECK_DONWLOAD,
        XDM_ACCESSORY_CHECK_COPY,
        XDM_ACCESSORY_CHECK_INSTALL
    }
}
