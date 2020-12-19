package com.samsung.android.fotaagent.push;

import android.text.TextUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.samsung.android.fotaagent.ProcessFOTA;
import com.samsung.android.fotaagent.ProcessRegister;
import com.samsung.android.fotaprovider.log.Log;

public class FcmPushListenerService extends FirebaseMessagingService {
    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.I("FCM message received from: " + remoteMessage.getFrom());
        String str = remoteMessage.getData().get("msg");
        if (TextUtils.isEmpty(str)) {
            Log.W("Push message is null!!");
            return;
        }
        Log.H("Received: " + str);
        new ProcessFOTA().updateOnBackgroundByPush(str);
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onNewToken(String str) {
        Log.I("FCM registered");
        Log.H("new token: " + str);
        new ProcessRegister().updatePushId();
        super.onNewToken(str);
    }
}
