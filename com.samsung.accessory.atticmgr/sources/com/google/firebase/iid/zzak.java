package com.google.firebase.iid;

import android.os.Bundle;

final class zzak extends zzaj<Void> {
    zzak(int i, int i2, Bundle bundle) {
        super(i, 2, bundle);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.firebase.iid.zzaj
    public final boolean zzab() {
        return true;
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.firebase.iid.zzaj
    public final void zzb(Bundle bundle) {
        if (bundle.getBoolean("ack", false)) {
            finish(null);
        } else {
            zza(new zzam(4, "Invalid response to one way request"));
        }
    }
}
