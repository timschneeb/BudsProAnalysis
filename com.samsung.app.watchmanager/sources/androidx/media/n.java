package androidx.media;

import androidx.media.MediaBrowserServiceCompat;

class n implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.k f899a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.j f900b;

    n(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar) {
        this.f900b = jVar;
        this.f899a = kVar;
    }

    public void run() {
        MediaBrowserServiceCompat.b remove = MediaBrowserServiceCompat.this.f868c.remove(this.f899a.asBinder());
        if (remove != null) {
            remove.f.asBinder().unlinkToDeath(remove, 0);
        }
    }
}
