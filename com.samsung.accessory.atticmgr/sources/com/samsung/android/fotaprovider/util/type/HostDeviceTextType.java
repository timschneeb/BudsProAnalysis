package com.samsung.android.fotaprovider.util.type;

import com.samsung.android.fotaprovider.deviceinfo.ProviderInfo;
import com.sec.android.fotaprovider.R;

public enum HostDeviceTextType {
    PHONE {
        @Override // com.samsung.android.fotaprovider.util.type.HostDeviceTextType
        public int getDownloadFailedNetworkDisconnectedMessageId() {
            return R.string.STR_ACCESSORY_DOWNLOAD_FAILED_NETWORK_DISCONNECTED_PHONE;
        }

        @Override // com.samsung.android.fotaprovider.util.type.HostDeviceTextType
        public int getDownloadRetryConfirmViaWifiMessageId() {
            return R.string.STR_ACCESSORY_DOWNLOAD_RETRY_CONFIRM_VIA_WIFI_PHONE;
        }

        @Override // com.samsung.android.fotaprovider.util.type.HostDeviceTextType
        public int getDownloadFailedLowMemoryMessageId() {
            return R.string.STR_ACCESSORY_LOW_MEMORY_DOWNLOAD_PHONE;
        }
    },
    TABLET {
        @Override // com.samsung.android.fotaprovider.util.type.HostDeviceTextType
        public int getDownloadFailedNetworkDisconnectedMessageId() {
            return R.string.STR_ACCESSORY_DOWNLOAD_FAILED_NETWORK_DISCONNECTED_TABLET;
        }

        @Override // com.samsung.android.fotaprovider.util.type.HostDeviceTextType
        public int getDownloadRetryConfirmViaWifiMessageId() {
            return R.string.STR_ACCESSORY_DOWNLOAD_RETRY_CONFIRM_VIA_WIFI_TABLET;
        }

        @Override // com.samsung.android.fotaprovider.util.type.HostDeviceTextType
        public int getDownloadFailedLowMemoryMessageId() {
            return R.string.STR_ACCESSORY_LOW_MEMORY_DOWNLOAD_TABLET;
        }
    };
    
    private static HostDeviceTextType hostDeviceTextType = getHostDeviceTextType();

    public abstract int getDownloadFailedLowMemoryMessageId();

    public abstract int getDownloadFailedNetworkDisconnectedMessageId();

    public abstract int getDownloadRetryConfirmViaWifiMessageId();

    public static HostDeviceTextType get() {
        return hostDeviceTextType;
    }

    private static HostDeviceTextType getHostDeviceTextType() {
        if (new ProviderInfo().isTablet()) {
            return TABLET;
        }
        return PHONE;
    }
}
