package com.google.firebase.iid;

import android.util.Pair;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* access modifiers changed from: package-private */
public final /* synthetic */ class zzas implements Continuation {
    private final zzaq zzcu;
    private final Pair zzcv;

    zzas(zzaq zzaq, Pair pair) {
        this.zzcu = zzaq;
        this.zzcv = pair;
    }

    @Override // com.google.android.gms.tasks.Continuation
    public final Object then(Task task) {
        return this.zzcu.zza(this.zzcv, task);
    }
}
