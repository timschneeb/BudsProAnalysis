package androidx.media;

import android.os.IBinder;
import androidx.media.MediaBrowserServiceCompat;

class s implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.k f917a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.j f918b;

    s(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar) {
        this.f918b = jVar;
        this.f917a = kVar;
    }

    public void run() {
        IBinder asBinder = this.f917a.asBinder();
        MediaBrowserServiceCompat.b remove = MediaBrowserServiceCompat.this.f868c.remove(asBinder);
        if (remove != null) {
            asBinder.unlinkToDeath(remove, 0);
        }
    }
}
