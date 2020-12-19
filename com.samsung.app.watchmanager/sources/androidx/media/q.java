package androidx.media;

import android.support.v4.os.ResultReceiver;
import android.util.Log;
import androidx.media.MediaBrowserServiceCompat;

class q implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.k f909a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f910b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ ResultReceiver f911c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.j f912d;

    q(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, String str, ResultReceiver resultReceiver) {
        this.f912d = jVar;
        this.f909a = kVar;
        this.f910b = str;
        this.f911c = resultReceiver;
    }

    public void run() {
        MediaBrowserServiceCompat.b bVar = MediaBrowserServiceCompat.this.f868c.get(this.f909a.asBinder());
        if (bVar == null) {
            Log.w("MBServiceCompat", "getMediaItem for callback that isn't registered id=" + this.f910b);
            return;
        }
        MediaBrowserServiceCompat.this.a(this.f910b, bVar, this.f911c);
    }
}
