package com.google.firebase.iid;

import android.os.Binder;
import android.os.Process;
import android.util.Log;

public final class zzg extends Binder {
    private final zzc zzae;

    zzg(zzc zzc) {
        this.zzae = zzc;
    }

    public final void zza(zze zze) {
        if (Binder.getCallingUid() == Process.myUid()) {
            if (Log.isLoggable("EnhancedIntentService", 3)) {
                Log.d("EnhancedIntentService", "service received new intent via bind strategy");
            }
            if (this.zzae.zzc(zze.intent)) {
                zze.finish();
                return;
            }
            if (Log.isLoggable("EnhancedIntentService", 3)) {
                Log.d("EnhancedIntentService", "intent being queued for bg execution");
            }
            this.zzae.zzt.execute(new zzf(this, zze));
            return;
        }
        throw new SecurityException("Binding only allowed within app");
    }
}
