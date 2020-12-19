package com.samsung.accessory.hearablemgr.core.fmm.utils;

import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.gson.JsonArray;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import com.samsung.accessory.hearablemgr.core.bluetooth.BluetoothManager;
import com.samsung.accessory.hearablemgr.core.fmm.receiver.FmmRequestReceiver;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConstants;
import seccompat.android.util.Log;

public class FmmUtils {
    private static final String TAG = "Attic_FmmUtils";

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static String getActionByOperation(String str) {
        char c;
        switch (str.hashCode()) {
            case -2009453160:
                if (str.equals(FmmConstants.Operation.MUTE_L)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -2009453154:
                if (str.equals(FmmConstants.Operation.MUTE_R)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1881281404:
                if (str.equals(FmmConstants.Operation.REMOVE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1785516855:
                if (str.equals(FmmConstants.Operation.UPDATE)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -779858787:
                if (str.equals(FmmConstants.Operation.STATUS_CHANGE)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -290559266:
                if (str.equals(FmmConstants.Operation.CONNECTION)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2515504:
                if (str.equals("RING")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 279402862:
                if (str.equals(FmmConstants.Operation.GET_DEVICE_INFO)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1809891874:
                if (str.equals(FmmConstants.Operation.RING_STATUS)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1852415178:
                if (str.equals(FmmConstants.Operation.CONNECTION_CHECK)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        return (c == 0 || c == 1 || c == 2 || c == 3 || c == 4) ? FmmConstants.Action.ACTION_WD_CHANGED : FmmConstants.Action.ACTION_OPERATION_RESPONSE;
    }

    public static String getRequestReceiverClassName() {
        return FmmRequestReceiver.class.getName();
    }

    public static String getLeftSerialNumber() {
        if (Application.getCoreService().getEarBudsInfo().serialNumber_left == null) {
            return Preferences.getString(PreferenceKey.LEFT_INFO_SN, null);
        }
        return Application.getCoreService().getEarBudsInfo().serialNumber_left;
    }

    public static String getRightSerialNumber() {
        if (Application.getCoreService().getEarBudsInfo().serialNumber_right == null) {
            return Preferences.getString(PreferenceKey.RIGHT_INFO_SN, null);
        }
        return Application.getCoreService().getEarBudsInfo().serialNumber_right;
    }

    public static Boolean getIsConnected() {
        return Boolean.valueOf(Application.getCoreService().isConnected());
    }

    public static int getLeftBattery() {
        return Application.getCoreService().getEarBudsInfo().batteryL;
    }

    public static int getRightBattery() {
        return Application.getCoreService().getEarBudsInfo().batteryR;
    }

    public static int getLeftDeviceStatus() {
        if (getLeftIsConnected().booleanValue()) {
            return isFindingEarbuds() ? RingManager.isLeftMute() ? 5 : 4 : Application.getCoreService().getEarBudsInfo().wearingL ? 1 : 2;
        }
        return 0;
    }

    public static int getRightDeviceStatus() {
        if (getRightIsConnected().booleanValue()) {
            return isFindingEarbuds() ? RingManager.isRightMute() ? 5 : 4 : Application.getCoreService().getEarBudsInfo().wearingR ? 1 : 2;
        }
        return 0;
    }

    public static boolean isPlaying() {
        BluetoothManager bluetoothManager = Application.getBluetoothManager();
        return bluetoothManager.getA2dpProxy().isA2dpPlaying(BluetoothUtil.getBondedDevice(UhmFwUtil.getLastLaunchDeviceId()));
    }

    public static String getNickName(String str) {
        return Application.getUhmDatabase().getDeviceName(str);
    }

    public static Boolean getLeftIsConnected() {
        return Boolean.valueOf(getIsConnected().booleanValue() && Application.getCoreService().getEarBudsInfo().batteryL > 0);
    }

    public static Boolean getRightIsConnected() {
        return Boolean.valueOf(getIsConnected().booleanValue() && Application.getCoreService().getEarBudsInfo().batteryR > 0);
    }

    public static String getFwVersion() {
        if (Application.getCoreService().getEarBudsInfo().deviceSWVer == null) {
            return Preferences.getString(PreferenceKey.PREFERENCE_DEVICE_INFO_FW_VERSION, null);
        }
        return Application.getCoreService().getEarBudsInfo().deviceSWVer;
    }

    public static JsonArray getSupportOprtList() {
        JsonArray jsonArray = new JsonArray();
        if (isSupportedRing().booleanValue()) {
            jsonArray.add("RING");
        }
        if (isSupportedOfflineFinding().booleanValue()) {
            jsonArray.add("SET_DEVICE_INFO");
        }
        return jsonArray;
    }

    public static Boolean isSupportedRing() {
        return true;
    }

    public static Boolean isSupportedOfflineFinding() {
        if ("attic".hashCode() != -394473095) {
        }
        return true;
    }

    public static Boolean isFmmSupport() {
        boolean z = false;
        try {
            Bundle bundle = Application.getContext().getPackageManager().getApplicationInfo(FmmConstants.FMM_PACKAGE, 128).metaData;
            if (bundle != null) {
                z = Boolean.valueOf(bundle.getBoolean("com.samsung.android.fmm.supportFME"));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        }
        Log.d(TAG, "isFmmSupport() : " + z);
        return z;
    }

    public static int startFindMyEarbuds() {
        int find = RingManager.find(FmmRequestReceiver.class.getName());
        if (find == 1) {
            return 1;
        }
        if (find == 2) {
            return 9;
        }
        if (find != 3) {
            return find != 4 ? 0 : 7;
        }
        return 10;
    }

    public static boolean isFindingEarbuds() {
        return RingManager.isFinding();
    }

    public static void readyFindMyEarbuds() {
        RingManager.ready(FmmRequestReceiver.class.getName());
    }

    private static boolean isWearingLeft() {
        return Application.getCoreService().getEarBudsInfo().wearingL;
    }

    private static boolean isWearingRight() {
        return Application.getCoreService().getEarBudsInfo().wearingR;
    }

    public static int setLeftMute(boolean z) {
        if (!getLeftIsConnected().booleanValue()) {
            return 1;
        }
        if (!isFindingEarbuds()) {
            return 11;
        }
        if (z == RingManager.isLeftMute()) {
            return z ? 12 : 13;
        }
        if (!z && isWearingLeft()) {
            return 14;
        }
        RingManager.setLeftMute(z, FmmRequestReceiver.class.getName());
        return 0;
    }

    public static int setRightMute(boolean z) {
        if (!getRightIsConnected().booleanValue()) {
            return 1;
        }
        if (!isFindingEarbuds()) {
            return 11;
        }
        if (z == RingManager.isRightMute()) {
            return z ? 12 : 13;
        }
        if (!z && isWearingRight()) {
            return 14;
        }
        RingManager.setRightMute(z, FmmRequestReceiver.class.getName());
        return 0;
    }
}
