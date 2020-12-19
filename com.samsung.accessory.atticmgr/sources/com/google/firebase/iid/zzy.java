package com.google.firebase.iid;

import android.util.Base64;
import com.google.android.gms.common.internal.Objects;
import java.security.KeyPair;

/* access modifiers changed from: package-private */
public final class zzy {
    private final KeyPair zzbw;
    private final long zzbx;

    zzy(KeyPair keyPair, long j) {
        this.zzbw = keyPair;
        this.zzbx = j;
    }

    /* access modifiers changed from: package-private */
    public final KeyPair getKeyPair() {
        return this.zzbw;
    }

    /* access modifiers changed from: package-private */
    public final long getCreationTime() {
        return this.zzbx;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzy)) {
            return false;
        }
        zzy zzy = (zzy) obj;
        if (this.zzbx != zzy.zzbx || !this.zzbw.getPublic().equals(zzy.zzbw.getPublic()) || !this.zzbw.getPrivate().equals(zzy.zzbw.getPrivate())) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzbw.getPublic(), this.zzbw.getPrivate(), Long.valueOf(this.zzbx));
    }

    /* access modifiers changed from: private */
    public final String zzv() {
        return Base64.encodeToString(this.zzbw.getPublic().getEncoded(), 11);
    }

    /* access modifiers changed from: private */
    public final String zzw() {
        return Base64.encodeToString(this.zzbw.getPrivate().getEncoded(), 11);
    }
}
