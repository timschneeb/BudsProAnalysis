package com.google.firebase.iid;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.google.android.gms.internal.firebase_messaging.zza;
import com.google.android.gms.internal.firebase_messaging.zzf;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.ScheduledExecutorService;

public final class zzac {
    private static zzac zzby;
    private final Context zzag;
    private final ScheduledExecutorService zzbz;
    private zzae zzca = new zzae(this);
    private int zzcb = 1;

    public static synchronized zzac zzc(Context context) {
        zzac zzac;
        synchronized (zzac.class) {
            if (zzby == null) {
                zzby = new zzac(context, zza.zza().zza(1, new NamedThreadFactory("MessengerIpcClient"), zzf.zze));
            }
            zzac = zzby;
        }
        return zzac;
    }

    private zzac(Context context, ScheduledExecutorService scheduledExecutorService) {
        this.zzbz = scheduledExecutorService;
        this.zzag = context.getApplicationContext();
    }

    public final Task<Void> zza(int i, Bundle bundle) {
        return zza(new zzak(zzx(), 2, bundle));
    }

    public final Task<Bundle> zzb(int i, Bundle bundle) {
        return zza(new zzal(zzx(), 1, bundle));
    }

    private final synchronized <T> Task<T> zza(zzaj<T> zzaj) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(zzaj);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 9);
            sb.append("Queueing ");
            sb.append(valueOf);
            Log.d("MessengerIpcClient", sb.toString());
        }
        if (!this.zzca.zzb(zzaj)) {
            this.zzca = new zzae(this);
            this.zzca.zzb(zzaj);
        }
        return zzaj.zzcl.getTask();
    }

    private final synchronized int zzx() {
        int i;
        i = this.zzcb;
        this.zzcb = i + 1;
        return i;
    }
}
