package com.google.firebase.iid;

import android.os.Handler;
import android.os.Message;

/* access modifiers changed from: package-private */
public final /* synthetic */ class zzad implements Handler.Callback {
    private final zzae zzcc;

    zzad(zzae zzae) {
        this.zzcc = zzae;
    }

    public final boolean handleMessage(Message message) {
        return this.zzcc.zza(message);
    }
}
