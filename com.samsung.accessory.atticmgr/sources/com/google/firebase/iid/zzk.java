package com.google.firebase.iid;

import java.util.concurrent.ThreadFactory;

/* access modifiers changed from: package-private */
public final /* synthetic */ class zzk implements ThreadFactory {
    static final ThreadFactory zzan = new zzk();

    private zzk() {
    }

    public final Thread newThread(Runnable runnable) {
        return zzh.zza(runnable);
    }
}
