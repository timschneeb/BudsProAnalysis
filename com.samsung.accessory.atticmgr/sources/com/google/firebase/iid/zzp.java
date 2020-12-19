package com.google.firebase.iid;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;

/* access modifiers changed from: package-private */
public final /* synthetic */ class zzp implements SuccessContinuation {
    private final FirebaseInstanceId zzbb;
    private final String zzbc;
    private final String zzbd;
    private final String zzbe;

    zzp(FirebaseInstanceId firebaseInstanceId, String str, String str2, String str3) {
        this.zzbb = firebaseInstanceId;
        this.zzbc = str;
        this.zzbd = str2;
        this.zzbe = str3;
    }

    @Override // com.google.android.gms.tasks.SuccessContinuation
    public final Task then(Object obj) {
        return this.zzbb.zzb(this.zzbc, this.zzbd, this.zzbe, (String) obj);
    }
}
