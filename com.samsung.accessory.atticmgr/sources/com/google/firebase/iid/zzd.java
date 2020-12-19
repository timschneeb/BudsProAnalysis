package com.google.firebase.iid;

import android.content.Intent;
import android.util.Log;

/* access modifiers changed from: package-private */
public final /* synthetic */ class zzd implements Runnable {
    private final zze zzx;
    private final Intent zzy;

    zzd(zze zze, Intent intent) {
        this.zzx = zze;
        this.zzy = intent;
    }

    public final void run() {
        zze zze = this.zzx;
        String action = this.zzy.getAction();
        StringBuilder sb = new StringBuilder(String.valueOf(action).length() + 61);
        sb.append("Service took too long to process intent: ");
        sb.append(action);
        sb.append(" App may get closed.");
        Log.w("EnhancedIntentService", sb.toString());
        zze.finish();
    }
}
