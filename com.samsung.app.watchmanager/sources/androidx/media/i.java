package androidx.media;

import androidx.media.MediaBrowserServiceCompat;

class i implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.b f894a;

    i(MediaBrowserServiceCompat.b bVar) {
        this.f894a = bVar;
    }

    public void run() {
        MediaBrowserServiceCompat.b bVar = this.f894a;
        MediaBrowserServiceCompat.this.f868c.remove(bVar.f.asBinder());
    }
}
