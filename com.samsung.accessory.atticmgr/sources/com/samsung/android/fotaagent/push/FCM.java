package com.samsung.android.fotaagent.push;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;

public class FCM {
    public static final String FCM_UNKNOWN_ERROR = "FCM_UNKNOWN_ERROR";
    public static final String PLAY_SERVICE_ERROR = "PLAY_SERVICE_NOT_AVAILABLE";
    static final String SENDER_ID = "994410787046";
    public static FCM instance = new FCM();

    public FCMResult getRegistrationIDByBackground(Context context) {
        Log.D("");
        FCMResult fCMResult = new FCMResult();
        if (!checkPlayServices(context)) {
            Log.W("Play Services is not supported!");
            fCMResult.setResult(false);
            fCMResult.setErrorMsg(PLAY_SERVICE_ERROR);
            return fCMResult;
        }
        String registrationId = getRegistrationId(context);
        if (!TextUtils.isEmpty(registrationId)) {
            fCMResult.setResult(true);
            fCMResult.setPushID(registrationId);
            return fCMResult;
        }
        Log.I("Try to get Registration ID by InstanceID");
        try {
            String token = FirebaseInstanceId.getInstance().getToken(SENDER_ID, "FCM");
            if (!TextUtils.isEmpty(token)) {
                fCMResult.setResult(true);
                fCMResult.setPushID(token);
                storeRegistrationId(context, token);
            } else {
                fCMResult.setResult(false);
                fCMResult.setErrorMsg(FCM_UNKNOWN_ERROR);
            }
        } catch (Exception e) {
            fCMResult.setResult(false);
            fCMResult.setErrorMsg(e.getMessage());
            Log.printStackTrace(e);
        }
        return fCMResult;
    }

    private boolean checkPlayServices(Context context) {
        int isGooglePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        return isGooglePlayServicesAvailable == 0 || isGooglePlayServicesAvailable == 2;
    }

    private String getRegistrationId(Context context) {
        Log.D("");
        String fcmID = FotaProviderState.getFcmID(context);
        if (!fcmID.isEmpty()) {
            return fcmID;
        }
        Log.W("Registration ID is not found.");
        return "";
    }

    private void storeRegistrationId(Context context, String str) {
        FotaProviderState.storeFcmID(context, str);
    }
}
