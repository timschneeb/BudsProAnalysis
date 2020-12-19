package com.google.firebase.iid;

import android.os.Bundle;
import com.google.android.gms.tasks.TaskCompletionSource;

/* access modifiers changed from: package-private */
public final /* synthetic */ class zzr implements Runnable {
    private final zzs zzbn;
    private final Bundle zzbo;
    private final TaskCompletionSource zzbp;

    zzr(zzs zzs, Bundle bundle, TaskCompletionSource taskCompletionSource) {
        this.zzbn = zzs;
        this.zzbo = bundle;
        this.zzbp = taskCompletionSource;
    }

    public final void run() {
        this.zzbn.zza(this.zzbo, this.zzbp);
    }
}
