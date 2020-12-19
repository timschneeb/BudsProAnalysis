package com.samsung.android.sdk.bixby2.action;

import android.app.PendingIntent;

public interface ResponseCallback {
    void onComplete(String str);

    void onComplete(String str, PendingIntent pendingIntent);
}
