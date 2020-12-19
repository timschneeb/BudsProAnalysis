package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.Intent;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* access modifiers changed from: package-private */
public final class zze {
    final Intent intent;
    private boolean zzaa = false;
    private final ScheduledFuture<?> zzab;
    private final BroadcastReceiver.PendingResult zzz;

    zze(Intent intent2, BroadcastReceiver.PendingResult pendingResult, ScheduledExecutorService scheduledExecutorService) {
        this.intent = intent2;
        this.zzz = pendingResult;
        this.zzab = scheduledExecutorService.schedule(new zzd(this, intent2), 9000, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: package-private */
    public final synchronized void finish() {
        if (!this.zzaa) {
            this.zzz.finish();
            this.zzab.cancel(false);
            this.zzaa = true;
        }
    }
}
