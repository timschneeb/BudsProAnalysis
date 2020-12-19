package com.samsung.android.fotaprovider.util.type;

import com.sec.android.fotaprovider.R;

public enum TextType {
    WATCH {
        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCautionAccessoryUncoupledTextId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyFailedTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryLaterTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getDownloadAndInstallConfirmTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getTitleId() {
            return R.string.STR_ACCESSORY_UPDATE_TITLE_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getConnectingMessageId() {
            return R.string.STR_ACCESSORY_CONNECTING_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getConnectionFailedMessageId() {
            return R.string.STR_ACCESSORY_CONNECTION_FAILED_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getInstallConfirmTitleId() {
            return R.string.STR_ACCESSORY_INSTALL_CONFIRM_TITLE_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getForceInstallConfirmTitleId() {
            return R.string.STR_ACCESSORY_FORCE_INSTALL_CONFIRM_TITLE_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getInstallConfirmCountdownTextId() {
            return R.string.STR_ACCESSORY_FORCE_INSTALL_CONFIRM_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getInstallConfirmNotificationPostponeScheduleInstallTextId() {
            return R.string.STR_NOTIFICATION_INSTALL_CONFIRM_POSTPONE_SCHEDULE_INSTALL_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyFailedMessageId() {
            return R.string.STR_ACCESSORY_COPY_FAILED_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryLaterMessageId() {
            return R.string.STR_ACCESSORY_COPY_RETRY_LATER_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryLaterAccessoryUncoupledMessageId() {
            return R.string.STR_ACCESSORY_COPY_RETRY_LATER_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryMessageId() {
            return R.string.STR_ACCESSORY_COPY_RETRY_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryPositiveButtonId() {
            return R.string.STR_BTN_OK;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getDownloadAccessoryLowMemoryMessageId() {
            return R.string.STR_ACCESSORY_LOW_MEMORY_DOWNLOAD_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyAccessoryLowMemoryMessageId() {
            return R.string.STR_ACCESSORY_LOW_MEMORY_COPY_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getInstallAccessoryLowMemoryMessageId() {
            return R.string.STR_ACCESSORY_LOW_MEMORY_INSTALL_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyAccessoryLowBatteryMessageId() {
            return R.string.STR_ACCESSORY_LOW_BATTERY_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getAccessoryModifiedTitleId() {
            return R.string.STR_ACCESSORY_MODIFIED_TITLE_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCautionMainDescriptionId(boolean z) {
            return R.string.STR_ACCESSORY_CAUTION_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCautionSettingsTextId() {
            return R.string.STR_ACCESSORY_CAUTION_SETTINGS_MAY_CHANGE_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCautionBackupTextId(boolean z) {
            if (z) {
                return -1;
            }
            return R.string.STR_ACCESSORY_CAUTION_BACKUP_WATCH;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getPolicyBlockedMessageId() {
            return R.string.STR_SYSTEMPOLICY_BLOCK_WATCH;
        }
    },
    EARBUDS {
        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCautionBackupTextId(boolean z) {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyFailedTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryLaterTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getForceInstallConfirmTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getInstallConfirmCountdownTextId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getInstallConfirmNotificationPostponeScheduleInstallTextId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getInstallConfirmTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getTitleId() {
            return R.string.STR_ACCESSORY_UPDATE_TITLE_EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getConnectingMessageId() {
            return R.string.STR_ACCESSORY_CONNECTING_EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getConnectionFailedMessageId() {
            return R.string.STR_ACCESSORY_CONNECTION_FAILED_EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getDownloadAndInstallConfirmTitleId() {
            return R.string.STR_ACCESSORY_DOWNLOAD_AND_UPDATE_CONFIRM_TITLE_EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyFailedMessageId() {
            return R.string.STR_ACCESSORY_COPY_FAILED_EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryLaterMessageId() {
            return R.string.STR_ACCESSORY_COPY_RETRY_LATER_EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryLaterAccessoryUncoupledMessageId() {
            if (HostDeviceTextType.get() == HostDeviceTextType.TABLET) {
                return R.string.STR_ACCESSORY_COPY_RETRY_LATER_ACCESSORY_UNCOUPLED_EARBUDS_WITH_TABLET;
            }
            return R.string.STR_ACCESSORY_COPY_RETRY_LATER_ACCESSORY_UNCOUPLED_EARBUDS_WITH_PHONE;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryTitleId() {
            return R.string.STR_ACCESSORY_COPY_RETRY_EARBUDS_TITLE;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryMessageId() {
            return R.string.STR_ACCESSORY_COPY_RETRY_EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryPositiveButtonId() {
            return R.string.STR_BTN_CONTINUE;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getDownloadAccessoryLowMemoryMessageId() {
            return R.string.STR_ACCESSORY_LOW_MEMORY_DOWNLOAD_PHONE;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyAccessoryLowMemoryMessageId() {
            return R.string.STR_ACCESSORY_LOW_MEMORY_DOWNLOAD_PHONE;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getInstallAccessoryLowMemoryMessageId() {
            return R.string.STR_ACCESSORY_LOW_MEMORY_DOWNLOAD_PHONE;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyAccessoryLowBatteryMessageId() {
            return R.string.STR_ACCESSORY_LOW_BATTERY_EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getAccessoryModifiedTitleId() {
            return R.string.STR_ACCESSORY_UPDATE_TITLE_EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCautionMainDescriptionId(boolean z) {
            return R.string.STR_ACCESSORY_CAUTION_EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCautionSettingsTextId() {
            return R.string.STR_ACCESSORY_CAUTION_SETTINGS_MAY_CHANGE_EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCautionAccessoryUncoupledTextId() {
            return R.string.STR_ACCESSORY_CAUTION_ACCESSORY_UNCOUPLED_EARBUDS;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getPolicyBlockedMessageId() {
            return R.string.STR_ACCESSORY_UPDATE_FAILED_TRY_LATER;
        }
    },
    BAND {
        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCautionAccessoryUncoupledTextId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyFailedTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryLaterTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getForceInstallConfirmTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getInstallConfirmCountdownTextId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getInstallConfirmNotificationPostponeScheduleInstallTextId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getInstallConfirmTitleId() {
            return -1;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getTitleId() {
            return R.string.STR_ACCESSORY_UPDATE_TITLE_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getConnectingMessageId() {
            return R.string.STR_ACCESSORY_CONNECTING_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getConnectionFailedMessageId() {
            return R.string.STR_ACCESSORY_CONNECTION_FAILED_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getDownloadAndInstallConfirmTitleId() {
            return R.string.STR_ACCESSORY_DOWNLOAD_AND_UPDATE_CONFIRM_TITLE_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyFailedMessageId() {
            return R.string.STR_ACCESSORY_COPY_FAILED_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryLaterMessageId() {
            return R.string.STR_ACCESSORY_COPY_RETRY_LATER_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryLaterAccessoryUncoupledMessageId() {
            return R.string.STR_ACCESSORY_COPY_RETRY_LATER_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryMessageId() {
            return R.string.STR_ACCESSORY_COPY_RETRY_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyRetryPositiveButtonId() {
            return R.string.STR_BTN_CONTINUE;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getDownloadAccessoryLowMemoryMessageId() {
            return R.string.STR_ACCESSORY_LOW_MEMORY_DOWNLOAD_PHONE;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyAccessoryLowMemoryMessageId() {
            return R.string.STR_ACCESSORY_LOW_MEMORY_DOWNLOAD_PHONE;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getInstallAccessoryLowMemoryMessageId() {
            return R.string.STR_ACCESSORY_LOW_MEMORY_DOWNLOAD_PHONE;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCopyAccessoryLowBatteryMessageId() {
            return R.string.STR_ACCESSORY_LOW_BATTERY_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getAccessoryModifiedTitleId() {
            return R.string.STR_ACCESSORY_MODIFIED_TITLE_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCautionMainDescriptionId(boolean z) {
            return R.string.STR_ACCESSORY_CAUTION_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCautionSettingsTextId() {
            return R.string.STR_ACCESSORY_CAUTION_SETTINGS_MAY_CHANGE_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getCautionBackupTextId(boolean z) {
            return R.string.STR_ACCESSORY_CAUTION_BACKUP_BAND;
        }

        @Override // com.samsung.android.fotaprovider.util.type.TextType
        public int getPolicyBlockedMessageId() {
            return R.string.STR_ACCESSORY_UPDATE_FAILED_TRY_LATER;
        }
    };
    
    public static final int INVALID_ID = -1;

    public abstract int getAccessoryModifiedTitleId();

    public abstract int getCautionAccessoryUncoupledTextId();

    public abstract int getCautionBackupTextId(boolean z);

    public abstract int getCautionMainDescriptionId(boolean z);

    public abstract int getCautionSettingsTextId();

    public abstract int getConnectingMessageId();

    public abstract int getConnectionFailedMessageId();

    public abstract int getCopyAccessoryLowBatteryMessageId();

    public abstract int getCopyAccessoryLowMemoryMessageId();

    public abstract int getCopyFailedMessageId();

    public abstract int getCopyFailedTitleId();

    public abstract int getCopyRetryLaterAccessoryUncoupledMessageId();

    public abstract int getCopyRetryLaterMessageId();

    public abstract int getCopyRetryLaterTitleId();

    public abstract int getCopyRetryMessageId();

    public abstract int getCopyRetryPositiveButtonId();

    public abstract int getCopyRetryTitleId();

    public abstract int getDownloadAccessoryLowMemoryMessageId();

    public abstract int getDownloadAndInstallConfirmTitleId();

    public abstract int getForceInstallConfirmTitleId();

    public abstract int getInstallAccessoryLowMemoryMessageId();

    public abstract int getInstallConfirmCountdownTextId();

    public abstract int getInstallConfirmNotificationPostponeScheduleInstallTextId();

    public abstract int getInstallConfirmTitleId();

    public abstract int getPolicyBlockedMessageId();

    public abstract int getTitleId();
}
