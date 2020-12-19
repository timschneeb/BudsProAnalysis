package com.google.firebase.iid;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

final /* synthetic */ class zzo implements Continuation {
    private final FirebaseInstanceId zzbb;
    private final String zzbc;
    private final String zzbd;

    zzo(FirebaseInstanceId firebaseInstanceId, String str, String str2) {
        this.zzbb = firebaseInstanceId;
        this.zzbc = str;
        this.zzbd = str2;
    }

    @Override // com.google.android.gms.tasks.Continuation
    public final Object then(Task task) {
        return this.zzbb.zza(this.zzbc, this.zzbd, task);
    }
}
