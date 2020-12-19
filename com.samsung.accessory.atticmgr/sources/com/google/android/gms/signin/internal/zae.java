package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.base.zab;
import com.google.android.gms.internal.base.zac;

public abstract class zae extends zab implements zad {
    public zae() {
        super("com.google.android.gms.signin.internal.ISignInCallbacks");
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.base.zab
    public boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 3) {
            zaa((ConnectionResult) zac.zaa(parcel, ConnectionResult.CREATOR), (zaa) zac.zaa(parcel, zaa.CREATOR));
        } else if (i == 4) {
            zag((Status) zac.zaa(parcel, Status.CREATOR));
        } else if (i == 6) {
            zah((Status) zac.zaa(parcel, Status.CREATOR));
        } else if (i == 7) {
            zaa((Status) zac.zaa(parcel, Status.CREATOR), (GoogleSignInAccount) zac.zaa(parcel, GoogleSignInAccount.CREATOR));
        } else if (i != 8) {
            return false;
        } else {
            zab((zaj) zac.zaa(parcel, zaj.CREATOR));
        }
        parcel2.writeNoException();
        return true;
    }
}
