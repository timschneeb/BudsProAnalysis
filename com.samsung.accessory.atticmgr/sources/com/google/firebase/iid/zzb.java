package com.google.firebase.iid;

import android.content.Intent;

final class zzb implements Runnable {
    private final /* synthetic */ Intent zzq;
    private final /* synthetic */ Intent zzr;
    private final /* synthetic */ zzc zzs;

    zzb(zzc zzc, Intent intent, Intent intent2) {
        this.zzs = zzc;
        this.zzq = intent;
        this.zzr = intent2;
    }

    public final void run() {
        this.zzs.zzd(this.zzq);
        this.zzs.zza(this.zzr);
    }
}
