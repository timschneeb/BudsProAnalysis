package androidx.media;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.media.MediaBrowserServiceCompat;

class m implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.k f895a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f896b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ int f897c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ int f898d;
    final /* synthetic */ Bundle e;
    final /* synthetic */ MediaBrowserServiceCompat.j f;

    m(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, String str, int i, int i2, Bundle bundle) {
        this.f = jVar;
        this.f895a = kVar;
        this.f896b = str;
        this.f897c = i;
        this.f898d = i2;
        this.e = bundle;
    }

    public void run() {
        IBinder asBinder = this.f895a.asBinder();
        MediaBrowserServiceCompat.this.f868c.remove(asBinder);
        MediaBrowserServiceCompat.b bVar = new MediaBrowserServiceCompat.b(this.f896b, this.f897c, this.f898d, this.e, this.f895a);
        MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
        mediaBrowserServiceCompat.f869d = bVar;
        bVar.h = mediaBrowserServiceCompat.a(this.f896b, this.f898d, this.e);
        MediaBrowserServiceCompat mediaBrowserServiceCompat2 = MediaBrowserServiceCompat.this;
        mediaBrowserServiceCompat2.f869d = null;
        if (bVar.h == null) {
            Log.i("MBServiceCompat", "No root for client " + this.f896b + " from service " + m.class.getName());
            try {
                this.f895a.a();
            } catch (RemoteException unused) {
                Log.w("MBServiceCompat", "Calling onConnectFailed() failed. Ignoring. pkg=" + this.f896b);
            }
        } else {
            try {
                mediaBrowserServiceCompat2.f868c.put(asBinder, bVar);
                asBinder.linkToDeath(bVar, 0);
                if (MediaBrowserServiceCompat.this.f != null) {
                    MediaBrowserServiceCompat.k kVar = this.f895a;
                    bVar.h.b();
                    throw null;
                }
            } catch (RemoteException unused2) {
                Log.w("MBServiceCompat", "Calling onConnect() failed. Dropping client. pkg=" + this.f896b);
                MediaBrowserServiceCompat.this.f868c.remove(asBinder);
            }
        }
    }
}
