package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

final class zaat implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final /* synthetic */ zaak zagj;

    private zaat(zaak zaak) {
        this.zagj = zaak;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    public final void onConnectionSuspended(int i) {
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    public final void onConnected(Bundle bundle) {
        if (zaak.zai(this.zagj).isSignInClientDisconnectFixEnabled()) {
            zaak.zac(this.zagj).lock();
            try {
                if (zaak.zaf(this.zagj) != null) {
                    zaak.zaf(this.zagj).zaa(new zaar(this.zagj));
                    zaak.zac(this.zagj).unlock();
                }
            } finally {
                zaak.zac(this.zagj).unlock();
            }
        } else {
            zaak.zaf(this.zagj).zaa(new zaar(this.zagj));
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        zaak.zac(this.zagj).lock();
        try {
            if (zaak.zab(this.zagj, connectionResult)) {
                zaak.zaj(this.zagj);
                zaak.zak(this.zagj);
            } else {
                zaak.zaa(this.zagj, connectionResult);
            }
        } finally {
            zaak.zac(this.zagj).unlock();
        }
    }

    /* synthetic */ zaat(zaak zaak, zaal zaal) {
        this(zaak);
    }
}
