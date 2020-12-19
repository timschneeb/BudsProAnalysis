package com.samsung.android.fotaprovider.util.type;

import android.content.Context;
import androidx.work.WorkRequest;
import com.accessorydm.ui.downloadandinstallconfirm.DownloadAndInstallConfirmActivity;
import com.accessorydm.ui.downloadconfirm.XUIDownloadConfirmActivity;
import com.samsung.accessory.hearablemgr.BuildConstants;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.FotaProviderUtil;
import com.samsung.android.fotaprovider.util.galaxywearable.Settings;

public enum DeviceType {
    WATCH {
        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public long getWaitingTimeInMillisForInitializingDevice() {
            return 120000;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public long getWaitingTimeInMillisForUpdateResult() {
            return 90000;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public boolean isWifiOnlySettings(Context context) {
            return false;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public NotificationIconType getNotificationIconType() {
            return NotificationIconType.WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public boolean isNeedToInitializeFirebaseApp() {
            return FotaProviderUtil.isSingleFotaProvider();
        }
    },
    GEARFIT2 {
        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public long getWaitingTimeInMillisForInitializingDevice() {
            return WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public long getWaitingTimeInMillisForUpdateResult() {
            return 90000;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public boolean isWifiOnlySettings(Context context) {
            return false;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public NotificationIconType getNotificationIconType() {
            return NotificationIconType.FIT2;
        }
    },
    EARBUDS {
        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public boolean isSupportWifiOnlyFlagByServer() {
            return false;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public boolean isWifiAutoDownloadSettings(Context context) {
            return false;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public boolean shouldShowUpdateConfirmUI() {
            return false;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public TextType getTextType() {
            return TextType.EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public NotificationIconType getNotificationIconType() {
            if (DeviceType.isBeanEarbuds()) {
                return NotificationIconType.EARBUDS_BEAN;
            }
            if (DeviceType.isAtticEarbuds()) {
                return NotificationIconType.EARBUDS_ATTIC;
            }
            return NotificationIconType.EARBUDS_BEAN;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public Class getDownloadConfirmActivity() {
            return DownloadAndInstallConfirmActivity.class;
        }
    },
    BAND {
        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public boolean isSupportWifiOnlyFlagByServer() {
            return false;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public boolean isWifiAutoDownloadSettings(Context context) {
            return false;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public boolean shouldShowUpdateConfirmUI() {
            return false;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public TextType getTextType() {
            return TextType.BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public NotificationIconType getNotificationIconType() {
            return NotificationIconType.BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.DeviceType
        public Class getDownloadConfirmActivity() {
            return DownloadAndInstallConfirmActivity.class;
        }
    };
    
    public static final int INVALID_ID = -1;
    private static DeviceType deviceType = FotaProviderUtil.getDeviceType();
    private String FOTA_PROVIDER_NETWORK_SETTINGS_STATE;
    int FOTA_PROVIDER_STATE_DEFAULT;
    int FOTA_PROVIDER_STATE_OFF;
    int FOTA_PROVIDER_STATE_ON;
    private String FOTA_PROVIDER_WIFI_AUTO_DOWNLOAD_SETTINGS_STATE;

    public abstract NotificationIconType getNotificationIconType();

    public long getWaitingTimeInMillisForInitializingDevice() {
        return WorkRequest.MIN_BACKOFF_MILLIS;
    }

    public long getWaitingTimeInMillisForUpdateResult() {
        return 15000;
    }

    public boolean isNeedToInitializeFirebaseApp() {
        return true;
    }

    public boolean isPollingSupported() {
        return true;
    }

    public boolean isSupportWifiOnlyFlagByServer() {
        return true;
    }

    public boolean shouldShowUpdateConfirmUI() {
        return true;
    }

    private DeviceType() {
        this.FOTA_PROVIDER_WIFI_AUTO_DOWNLOAD_SETTINGS_STATE = "FOTAPROVIDER_UPDATE_AUTO_UPDATE";
        this.FOTA_PROVIDER_NETWORK_SETTINGS_STATE = "FOTAPROVIDER_UPDATE_WIFI_ONLY";
        this.FOTA_PROVIDER_STATE_DEFAULT = -1;
        this.FOTA_PROVIDER_STATE_OFF = 0;
        this.FOTA_PROVIDER_STATE_ON = 1;
    }

    public static DeviceType get() {
        return deviceType;
    }

    public static void reloadDeviceType() {
        deviceType = FotaProviderUtil.getDeviceType();
        Log.I("reload deviceType: " + deviceType);
    }

    public TextType getTextType() {
        return TextType.WATCH;
    }

    public boolean isWifiAutoDownloadSettings(Context context) {
        return context != null && Settings.System.getInt(context.getContentResolver(), this.FOTA_PROVIDER_WIFI_AUTO_DOWNLOAD_SETTINGS_STATE, this.FOTA_PROVIDER_STATE_DEFAULT) == this.FOTA_PROVIDER_STATE_ON;
    }

    public boolean isWifiOnlySettings(Context context) {
        return context != null && Settings.System.getInt(context.getContentResolver(), this.FOTA_PROVIDER_NETWORK_SETTINGS_STATE, this.FOTA_PROVIDER_STATE_DEFAULT) == this.FOTA_PROVIDER_STATE_ON;
    }

    public void setDefaultSettings(Context context) {
        if (context != null) {
            if (Settings.System.getInt(context.getContentResolver(), this.FOTA_PROVIDER_WIFI_AUTO_DOWNLOAD_SETTINGS_STATE, this.FOTA_PROVIDER_STATE_DEFAULT) == this.FOTA_PROVIDER_STATE_DEFAULT) {
                Settings.System.putInt(context.getContentResolver(), this.FOTA_PROVIDER_WIFI_AUTO_DOWNLOAD_SETTINGS_STATE, this.FOTA_PROVIDER_STATE_ON);
            }
            if (Settings.System.getInt(context.getContentResolver(), this.FOTA_PROVIDER_NETWORK_SETTINGS_STATE, this.FOTA_PROVIDER_STATE_DEFAULT) == this.FOTA_PROVIDER_STATE_DEFAULT) {
                Settings.System.putInt(context.getContentResolver(), this.FOTA_PROVIDER_NETWORK_SETTINGS_STATE, this.FOTA_PROVIDER_STATE_OFF);
            }
        }
    }

    public Class getDownloadConfirmActivity() {
        return XUIDownloadConfirmActivity.class;
    }

    /* access modifiers changed from: private */
    public static boolean isBeanEarbuds() {
        return getPackageName().contains(BuildConstants.FLAVOR_MODEL_NEOBEAN);
    }

    /* access modifiers changed from: private */
    public static boolean isAtticEarbuds() {
        return getPackageName().contains("attic");
    }

    private static String getPackageName() {
        return FotaProviderInitializer.getContext().getPackageName();
    }
}
