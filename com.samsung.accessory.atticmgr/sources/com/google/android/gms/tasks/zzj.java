package com.google.android.gms.tasks;

final class zzj implements Runnable {
    private final /* synthetic */ Task zzg;
    private final /* synthetic */ zzi zzm;

    zzj(zzi zzi, Task task) {
        this.zzm = zzi;
        this.zzg = task;
    }

    public final void run() {
        synchronized (zzi.zza(this.zzm)) {
            if (zzi.zzb(this.zzm) != null) {
                zzi.zzb(this.zzm).onComplete(this.zzg);
            }
        }
    }
}
