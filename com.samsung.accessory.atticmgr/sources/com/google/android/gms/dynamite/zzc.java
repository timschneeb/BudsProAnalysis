package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

final class zzc implements DynamiteModule.VersionPolicy {
    zzc() {
    }

    @Override // com.google.android.gms.dynamite.DynamiteModule.VersionPolicy
    public final DynamiteModule.VersionPolicy.zzb zza(Context context, String str, DynamiteModule.VersionPolicy.zza zza) throws DynamiteModule.LoadingException {
        DynamiteModule.VersionPolicy.zzb zzb = new DynamiteModule.VersionPolicy.zzb();
        zzb.zzir = zza.getLocalVersion(context, str);
        if (zzb.zzir != 0) {
            zzb.zzit = -1;
        } else {
            zzb.zzis = zza.zza(context, str, true);
            if (zzb.zzis != 0) {
                zzb.zzit = 1;
            }
        }
        return zzb;
    }
}
