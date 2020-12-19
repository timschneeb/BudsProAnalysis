package com.google.firebase.iid;

import com.google.android.gms.tasks.Task;

final /* synthetic */ class zzn implements zzar {
    private final FirebaseInstanceId zzbb;
    private final String zzbc;
    private final String zzbd;
    private final String zzbe;
    private final String zzbf;

    zzn(FirebaseInstanceId firebaseInstanceId, String str, String str2, String str3, String str4) {
        this.zzbb = firebaseInstanceId;
        this.zzbc = str;
        this.zzbd = str2;
        this.zzbe = str3;
        this.zzbf = str4;
    }

    @Override // com.google.firebase.iid.zzar
    public final Task zzs() {
        return this.zzbb.zza(this.zzbc, this.zzbd, this.zzbe, this.zzbf);
    }
}
