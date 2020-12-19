package androidx.media;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.media.MediaBrowserServiceCompat;

class r implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.k f913a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f914b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ int f915c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ int f916d;
    final /* synthetic */ Bundle e;
    final /* synthetic */ MediaBrowserServiceCompat.j f;

    r(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, String str, int i, int i2, Bundle bundle) {
        this.f = jVar;
        this.f913a = kVar;
        this.f914b = str;
        this.f915c = i;
        this.f916d = i2;
        this.e = bundle;
    }

    public void run() {
        IBinder asBinder = this.f913a.asBinder();
        MediaBrowserServiceCompat.this.f868c.remove(asBinder);
        MediaBrowserServiceCompat.b bVar = new MediaBrowserServiceCompat.b(this.f914b, this.f915c, this.f916d, this.e, this.f913a);
        MediaBrowserServiceCompat.this.f868c.put(asBinder, bVar);
        try {
            asBinder.linkToDeath(bVar, 0);
        } catch (RemoteException unused) {
            Log.w("MBServiceCompat", "IBinder is already dead.");
        }
    }
}
