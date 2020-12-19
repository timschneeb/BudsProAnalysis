package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import java.util.ArrayList;
import java.util.Map;

final class zaan extends zaau {
    final /* synthetic */ zaak zagj;
    private final Map<Api.Client, zaam> zagl;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zaan(zaak zaak, Map<Api.Client, zaam> map) {
        super(zaak, null);
        this.zagj = zaak;
        this.zagl = map;
    }

    @Override // com.google.android.gms.common.api.internal.zaau
    public final void zaan() {
        GoogleApiAvailabilityCache googleApiAvailabilityCache = new GoogleApiAvailabilityCache(zaak.zab(this.zagj));
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Api.Client client : this.zagl.keySet()) {
            if (!client.requiresGooglePlayServices() || (this.zagl.get(client).zaec)) {
                arrayList2.add(client);
            } else {
                arrayList.add(client);
            }
        }
        int i = -1;
        int i2 = 0;
        if (arrayList.isEmpty()) {
            ArrayList arrayList3 = arrayList2;
            int size = arrayList3.size();
            while (i2 < size) {
                Object obj = arrayList3.get(i2);
                i2++;
                i = googleApiAvailabilityCache.getClientAvailability(zaak.zaa(this.zagj), (Api.Client) obj);
                if (i == 0) {
                    break;
                }
            }
        } else {
            ArrayList arrayList4 = arrayList;
            int size2 = arrayList4.size();
            while (i2 < size2) {
                Object obj2 = arrayList4.get(i2);
                i2++;
                i = googleApiAvailabilityCache.getClientAvailability(zaak.zaa(this.zagj), (Api.Client) obj2);
                if (i != 0) {
                    break;
                }
            }
        }
        if (i != 0) {
            zaak.zad(this.zagj).zaa(new zaao(this, this.zagj, new ConnectionResult(i, null)));
            return;
        }
        if (zaak.zae(this.zagj) && zaak.zaf(this.zagj) != null) {
            zaak.zaf(this.zagj).connect();
        }
        for (Api.Client client2 : this.zagl.keySet()) {
            zaam zaam = this.zagl.get(client2);
            if (!client2.requiresGooglePlayServices() || googleApiAvailabilityCache.getClientAvailability(zaak.zaa(this.zagj), client2) == 0) {
                client2.connect(zaam);
            } else {
                zaak.zad(this.zagj).zaa(new zaap(this, this.zagj, zaam));
            }
        }
    }
}
