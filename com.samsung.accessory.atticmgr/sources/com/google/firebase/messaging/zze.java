package com.google.firebase.messaging;

import java.util.concurrent.Callable;

/* access modifiers changed from: package-private */
public final /* synthetic */ class zze implements Callable {
    private final zzd zzed;

    zze(zzd zzd) {
        this.zzed = zzd;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return this.zzed.zzat();
    }
}
