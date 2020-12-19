package com.accessorydm.interfaces;

public interface XUIEventInterface {

    public enum DM_UIEVENT {
        XUI_DM_NONE,
        XUI_DM_NOT_INIT,
        XUI_DM_CONNECT_FAILED,
        XUI_DM_ABORT_BYUSER,
        XUI_DM_RECV_FAILED,
        XUI_DM_SEND_FAILED,
        XUI_DM_HTTP_INTERNAL_ERROR,
        XUI_DM_SYNC_ERROR,
        XUI_DM_NOTI_NOT_SPECIFIED,
        XUI_DM_NOTI_BACKGROUND,
        XUI_DM_NOTI_INFORMATIVE,
        XUI_DM_NOTI_INTERACTIVE,
        XUI_DM_NOTI_RESUME_MAX,
        XUI_DM_CHECKING_FOR_UPDATE,
        XUI_DM_NO_UPDATABLE_VERSION,
        XUI_DM_ROAMING_WIFI_DISCONNECTED,
        XUI_DM_FINISH;
        
        private static final DM_UIEVENT[] values = values();

        public static DM_UIEVENT valueOf(int i) {
            try {
                return values[i];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public enum DL_UIEVENT {
        XUI_DL_NONE,
        XUI_DL_DOWNLOAD_YES_NO,
        XUI_DL_DOWNLOAD_IN_PROGRESS,
        XUI_DL_DOWNLOAD_IN_COMPLETE,
        XUI_DL_DOWNLOAD_START_CONFIRM,
        XUI_DL_DOWNLOAD_RETRY_CONFIRM,
        XUI_DL_DOWNLOAD_FAILED,
        XUI_DL_DOWNLOAD_FAILED_WIFI_DISCONNECTED,
        XUI_DL_DOWNLOAD_FAILED_NETWORK_DISCONNECTED,
        XUI_DL_COPY_IN_PROGRESS,
        XUI_DL_UPDATE_START,
        XUI_DL_UPDATE_CONFIRM,
        XUI_DL_MEMORY_FULL,
        XUI_DL_CONNECT_FAILED;
        
        private static final DL_UIEVENT[] values = values();

        public static DL_UIEVENT valueOf(int i) {
            try {
                return values[i];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public enum ACCESSORY_UIEVENT {
        XUI_DM_ACCESSORY_NONE,
        XUI_DM_ACCESSORY_CONNECTION_FAILED,
        XUI_DM_ACCESSORY_COPY_RETRY_LATER,
        XUI_DM_ACCESSORY_COPY_RETRY_LATER_ACCESSORY_UNCOUPLED,
        XUI_DM_ACCESSORY_COPY_RETRY_CONFIRM,
        XUI_DM_ACCESSORY_COPY_FAILED,
        XUI_DM_ACCESSORY_INSTALL_FAILED,
        XUI_DM_ACCESSORY_LOW_MEMORY_DOWNLOAD_WATCH,
        XUI_DM_ACCESSORY_LOW_MEMORY_COPY_WATCH,
        XUI_DM_ACCESSORY_LOW_MEMORY_INSTALL_WATCH,
        XUI_DM_ACCESSORY_LOW_BATTERY_WATCH,
        XUI_DM_ACCESSORY_SYSSCOPE_SCANNING,
        XUI_DM_ACCESSORY_SYSSCOPE_MODIFIED,
        XUI_DM_ACCESSORY_BLOCKED_BY_POLICY_FAILED;
        
        private static final ACCESSORY_UIEVENT[] values = values();

        public static ACCESSORY_UIEVENT valueOf(int i) {
            try {
                return values[i];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
