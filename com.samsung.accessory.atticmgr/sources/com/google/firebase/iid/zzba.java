package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import javax.annotation.Nullable;

final class zzba extends BroadcastReceiver {
    @Nullable
    private zzax zzdr;

    public zzba(zzax zzax) {
        this.zzdr = zzax;
    }

    public final void zzaq() {
        if (FirebaseInstanceId.zzm()) {
            Log.d("FirebaseInstanceId", "Connectivity change received registered");
        }
        this.zzdr.getContext().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public final void onReceive(Context context, Intent intent) {
        zzax zzax = this.zzdr;
        if (zzax != null && zzax.zzan()) {
            if (FirebaseInstanceId.zzm()) {
                Log.d("FirebaseInstanceId", "Connectivity changed. Starting background sync.");
            }
            FirebaseInstanceId.zza(this.zzdr, 0);
            this.zzdr.getContext().unregisterReceiver(this);
            this.zzdr = null;
        }
    }
}
