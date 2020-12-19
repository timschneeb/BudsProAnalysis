package com.google.firebase.iid;

import android.os.Bundle;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.io.IOException;

/* access modifiers changed from: package-private */
public final class zzt implements Continuation<Bundle, String> {
    private final /* synthetic */ zzs zzbs;

    zzt(zzs zzs) {
        this.zzbs = zzs;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [com.google.android.gms.tasks.Task] */
    @Override // com.google.android.gms.tasks.Continuation
    public final /* synthetic */ String then(Task<Bundle> task) throws Exception {
        zzs zzs = this.zzbs;
        return zzs.zza(task.getResult(IOException.class));
    }
}
