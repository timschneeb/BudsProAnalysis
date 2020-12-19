package com.samsung.accessory.hearablemgr.core.fota.util;

import android.content.Intent;
import com.samsung.accessory.fotaprovider.FotaProviderEventHandler;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import seccompat.android.util.Log;

public class FotaUtil {
    public static final String ACTION_FOTA_PROGRESS_COPY_RESULT = "com.samsung.accessory.neobean.service.action.FOTA_PROGRESS_COPY_RESULT";
    public static final String FOTA_RESULT = "fota_result";
    private static final String TAG = "NeoBean_FotaUtil";

    public interface FOTA_Copy_Process_Result_Id {
        public static final int COPY_DONE = 1;
        public static final int COPY_ERROR = 3;
        public static final int COPY_UNCOUPLED = 2;
    }

    public interface Fota_ErrorCode {
        public static final byte UnCoupled = -112;
    }

    public static boolean getCheckFotaUpdate() {
        boolean z = Preferences.getBoolean(PreferenceKey.CHECK_FOTA_UPDATE, false, UhmFwUtil.getLastLaunchDeviceId());
        Log.d(TAG, "getCheckFotaUpdate(): " + z);
        return z;
    }

    public static void setCheckFotaUpdate(boolean z) {
        Log.d(TAG, "setCheckFotaUpdate(): " + z);
        Preferences.putBoolean(PreferenceKey.CHECK_FOTA_UPDATE, Boolean.valueOf(z), UhmFwUtil.getLastLaunchDeviceId());
        Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_FOTA_CHECK_UPDATE));
    }

    public static long getLastSWVersionCheckTime() {
        long j = Preferences.getLong(PreferenceKey.LAST_VERSION_CHECK_TIME, 0, UhmFwUtil.getLastLaunchDeviceId());
        Log.d(TAG, "getLatestSWVersionDownloadUrl(): " + j);
        return j;
    }

    public static void setLastSWVersionCheckTime(long j) {
        Log.d(TAG, "setLastSWVersionCheckTime(): " + j);
        Preferences.putLong(PreferenceKey.LAST_VERSION_CHECK_TIME, Long.valueOf(j), UhmFwUtil.getLastLaunchDeviceId());
    }

    public static boolean getEmergencyFOTAIsRunning() {
        boolean z = Preferences.getBoolean(PreferenceKey.IS_EMERGENCY, false, UhmFwUtil.getLastLaunchDeviceId());
        Log.d(TAG, "getEmergencyFOTAIsRunning(): " + z);
        return z;
    }

    public static void setEmergencyFOTAIsRunning(Boolean bool) {
        Log.d(TAG, "setEmergencyFOTAIsRunning(): " + bool);
        Preferences.putBoolean(PreferenceKey.IS_EMERGENCY, bool, UhmFwUtil.getLastLaunchDeviceId());
    }

    public static long getLastDoneTime() {
        long j = Preferences.getLong(PreferenceKey.LATE_DONE_TIME, 0, UhmFwUtil.getLastLaunchDeviceId());
        Log.d(TAG, "getLastDoneTime(): " + j);
        return j;
    }

    public static void setLastDoneTime(long j) {
        Log.d(TAG, "setLastDoneTime(): " + j);
        Preferences.putLong(PreferenceKey.LATE_DONE_TIME, Long.valueOf(j), UhmFwUtil.getLastLaunchDeviceId());
    }

    public static void sendFotaBroadcast(boolean z) {
        Log.d(TAG, "sendFotaBroadcast : " + z);
        FotaProviderEventHandler.deviceConnectionChanged(Application.getContext(), z);
    }
}
