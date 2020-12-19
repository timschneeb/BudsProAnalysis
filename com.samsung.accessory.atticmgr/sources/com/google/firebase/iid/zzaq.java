package com.google.firebase.iid;

import android.util.Log;
import android.util.Pair;
import androidx.collection.ArrayMap;
import com.google.android.gms.tasks.Task;
import java.util.Map;
import java.util.concurrent.Executor;

final class zzaq {
    private final Executor executor;
    private final Map<Pair<String, String>, Task<InstanceIdResult>> zzcs = new ArrayMap();

    zzaq(Executor executor2) {
        this.executor = executor2;
    }

    /* access modifiers changed from: package-private */
    public final synchronized Task<InstanceIdResult> zza(String str, String str2, zzar zzar) {
        Pair<String, String> pair = new Pair<>(str, str2);
        Task<InstanceIdResult> task = this.zzcs.get(pair);
        if (task != null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(pair);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 29);
                sb.append("Joining ongoing request for: ");
                sb.append(valueOf);
                Log.d("FirebaseInstanceId", sb.toString());
            }
            return task;
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String valueOf2 = String.valueOf(pair);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 24);
            sb2.append("Making new request for: ");
            sb2.append(valueOf2);
            Log.d("FirebaseInstanceId", sb2.toString());
        }
        Task<TContinuationResult> continueWithTask = zzar.zzs().continueWithTask(this.executor, new zzas(this, pair));
        this.zzcs.put(pair, continueWithTask);
        return continueWithTask;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Task zza(Pair pair, Task task) throws Exception {
        synchronized (this) {
            this.zzcs.remove(pair);
        }
        return task;
    }
}
