package com.google.firebase.iid;

import android.os.Looper;
import android.os.Message;
import com.google.android.gms.internal.firebase_messaging.zze;

/* access modifiers changed from: package-private */
public final class zzat extends zze {
    private final /* synthetic */ zzau zzcw;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzat(zzau zzau, Looper looper) {
        super(looper);
        this.zzcw = zzau;
    }

    public final void handleMessage(Message message) {
        this.zzcw.zzb(message);
    }
}
