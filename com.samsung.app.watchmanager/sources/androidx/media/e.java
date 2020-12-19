package androidx.media;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import androidx.media.MediaBrowserServiceCompat;
import java.util.List;

class e extends MediaBrowserServiceCompat.i<List<MediaBrowserCompat.MediaItem>> {
    final /* synthetic */ MediaBrowserServiceCompat.b f;
    final /* synthetic */ String g;
    final /* synthetic */ Bundle h;
    final /* synthetic */ Bundle i;
    final /* synthetic */ MediaBrowserServiceCompat j;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    e(MediaBrowserServiceCompat mediaBrowserServiceCompat, Object obj, MediaBrowserServiceCompat.b bVar, String str, Bundle bundle, Bundle bundle2) {
        super(obj);
        this.j = mediaBrowserServiceCompat;
        this.f = bVar;
        this.g = str;
        this.h = bundle;
        this.i = bundle2;
    }

    /* access modifiers changed from: package-private */
    public void a(List<MediaBrowserCompat.MediaItem> list) {
        if (this.j.f868c.get(this.f.f.asBinder()) == this.f) {
            if ((a() & 1) != 0) {
                list = this.j.a(list, this.h);
            }
            try {
                this.f.f.a(this.g, list, this.h, this.i);
            } catch (RemoteException unused) {
                Log.w("MBServiceCompat", "Calling onLoadChildren() failed for id=" + this.g + " package=" + this.f.f870a);
            }
        } else if (MediaBrowserServiceCompat.f866a) {
            Log.d("MBServiceCompat", "Not sending onLoadChildren result for connection that has been disconnected. pkg=" + this.f.f870a + " id=" + this.g);
        }
    }
}
