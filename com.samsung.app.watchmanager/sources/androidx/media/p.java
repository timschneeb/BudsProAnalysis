package androidx.media;

import android.os.IBinder;
import android.util.Log;
import androidx.media.MediaBrowserServiceCompat;

class p implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.k f905a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f906b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ IBinder f907c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.j f908d;

    p(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, String str, IBinder iBinder) {
        this.f908d = jVar;
        this.f905a = kVar;
        this.f906b = str;
        this.f907c = iBinder;
    }

    public void run() {
        MediaBrowserServiceCompat.b bVar = MediaBrowserServiceCompat.this.f868c.get(this.f905a.asBinder());
        if (bVar == null) {
            Log.w("MBServiceCompat", "removeSubscription for callback that isn't registered id=" + this.f906b);
        } else if (!MediaBrowserServiceCompat.this.a(this.f906b, bVar, this.f907c)) {
            Log.w("MBServiceCompat", "removeSubscription called for " + this.f906b + " which is not subscribed");
        }
    }
}
