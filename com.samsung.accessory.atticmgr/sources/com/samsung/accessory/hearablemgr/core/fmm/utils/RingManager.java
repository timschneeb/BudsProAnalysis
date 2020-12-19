package com.samsung.accessory.hearablemgr.core.fmm.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.widget.Toast;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.Msg;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgMuteEarbud;
import seccompat.android.util.Log;

public class RingManager {
    public static final int ALREADY_RING = 4;
    public static final int DEVICE_BOTH_WEARING = 2;
    public static final int DEVICE_CALLING = 3;
    public static final int DEVICE_DISCONNECTED = 1;
    public static final String KEY_SENDER_ID = "senderId";
    public static final int SUCCESS = 0;
    private static final String TAG = (Application.TAG_ + RingManager.class.getSimpleName());
    private static BroadcastReceiver mRingReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.fmm.utils.RingManager.AnonymousClass2 */

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        public void onReceive(Context context, Intent intent) {
            char c;
            Log.d(RingManager.TAG, "onReceive : " + intent.getAction());
            String action = intent.getAction();
            switch (action.hashCode()) {
                case -2101458856:
                    if (action.equals(CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STOP)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -654353038:
                    if (action.equals(CoreService.ACTION_MSG_ID_CALL_STATE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1335721824:
                    if (action.equals(CoreService.ACTION_DEVICE_DISCONNECTED)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1882440235:
                    if (action.equals(CoreService.ACTION_MSG_ID_SCO_STATE_UPDATED)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                RingManager.ready();
            } else if (c == 1 || c == 2) {
                if (RingManager.isCalling()) {
                    RingManager.ready();
                }
            } else if (c == 3) {
                if (RingManager.sIsFinding) {
                    Toast.makeText(context, context.getResources().getString(Util.isTablet() ? R.string.settings_find_my_gear_disconnected_toast_tablet : R.string.settings_find_my_gear_disconnected_toast), 0).show();
                }
                RingManager.ready();
            }
        }
    };
    private static boolean sIsFinding = false;
    private static Handler sRingHandler = new Handler();

    public static void unregisterReceiver(Context context) {
        context.unregisterReceiver(mRingReceiver);
    }

    public static void ready() {
        ready(null);
    }

    public static void ready(String str) {
        Log.d(TAG, "ready()");
        if (sIsFinding) {
            stop(str);
        }
    }

    public static int find() {
        return find(null);
    }

    public static int find(String str) {
        Log.d(TAG, "find()");
        int check = check();
        if (check != 0) {
            String str2 = TAG;
            Log.d(str2, "don't use find my earbuds, result code : " + check);
            return check;
        }
        sIsFinding = true;
        Application.getCoreService().getEarBudsInfo().leftMuteStatus = false;
        Application.getCoreService().getEarBudsInfo().rightMuteStatus = false;
        sendStartSppMessage(str);
        sRingHandler.postDelayed(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.fmm.utils.RingManager.AnonymousClass1 */

            public void run() {
                RingManager.ready();
            }
        }, 180000);
        return 0;
    }

    public static void stop() {
        stop(null);
    }

    public static void stop(String str) {
        Log.d(TAG, "stop()");
        sIsFinding = false;
        Application.getCoreService().getEarBudsInfo().leftMuteStatus = true;
        Application.getCoreService().getEarBudsInfo().rightMuteStatus = true;
        sendStopSppMessage(str);
        sRingHandler.removeCallbacksAndMessages(null);
    }

    public static int check() {
        if (!isConnected()) {
            return 1;
        }
        if (isWearingBothEarbuds()) {
            return 2;
        }
        if (isCalling()) {
            return 3;
        }
        return isFinding() ? 4 : 0;
    }

    public static boolean isFinding() {
        return sIsFinding;
    }

    private static boolean isWearingBothEarbuds() {
        return isConnected() && Application.getCoreService().getEarBudsInfo().wearingL && Application.getCoreService().getEarBudsInfo().wearingR;
    }

    /* access modifiers changed from: private */
    public static boolean isCalling() {
        return Util.isCalling();
    }

    private static boolean isConnected() {
        return Application.getCoreService().isConnected();
    }

    private static boolean isLeftConnected() {
        return isConnected() && Application.getCoreService().getEarBudsInfo().batteryL > 0;
    }

    private static boolean isRightConnected() {
        return isConnected() && Application.getCoreService().getEarBudsInfo().batteryR > 0;
    }

    public static boolean isLeftMute() {
        return Application.getCoreService().getEarBudsInfo().leftMuteStatus;
    }

    public static boolean isRightMute() {
        return Application.getCoreService().getEarBudsInfo().rightMuteStatus;
    }

    public static void setLeftMute(boolean z) {
        setLeftMute(z, null);
    }

    public static void setLeftMute(boolean z, String str) {
        String str2 = TAG;
        Log.d(str2, "setLeftMute() : " + z);
        if (!isLeftConnected()) {
            Log.d(TAG, "left earbuds not connected");
        } else {
            sendMuteSppMessage(z, isRightMute(), str);
        }
    }

    public static void setRightMute(boolean z) {
        setRightMute(z, null);
    }

    public static void setRightMute(boolean z, String str) {
        String str2 = TAG;
        Log.d(str2, "setRightMute() : " + z);
        if (!isRightConnected()) {
            Log.d(TAG, "right earbuds not connected");
        } else {
            sendMuteSppMessage(isLeftMute(), z, str);
        }
    }

    private static void sendStartSppMessage(String str) {
        Application.getCoreService().sendSppMessage(new Msg((byte) MsgID.FIND_MY_EARBUDS_START));
        Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STATUS_UPDATED).putExtra(KEY_SENDER_ID, str));
    }

    private static void sendStopSppMessage(String str) {
        Application.getCoreService().sendSppMessage(new Msg((byte) MsgID.FIND_MY_EARBUDS_STOP));
        Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STATUS_UPDATED).putExtra(KEY_SENDER_ID, str));
    }

    private static void sendMuteSppMessage(boolean z, boolean z2, String str) {
        Application.getCoreService().getEarBudsInfo().leftMuteStatus = z;
        Application.getCoreService().getEarBudsInfo().rightMuteStatus = z2;
        Application.getCoreService().sendSppMessage(new MsgMuteEarbud(z, z2));
        Util.sendPermissionBroadcast(Application.getContext(), new Intent(CoreService.ACTION_MUTE_EARBUD_STATUS_UPDATED).putExtra(KEY_SENDER_ID, str));
    }

    public static void registerReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_MSG_ID_CALL_STATE);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_SCO_STATE_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STOP);
        intentFilter.addAction(CoreService.ACTION_DEVICE_DISCONNECTED);
        context.registerReceiver(mRingReceiver, intentFilter);
    }
}
