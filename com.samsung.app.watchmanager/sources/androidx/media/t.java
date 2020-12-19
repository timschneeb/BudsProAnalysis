package androidx.media;

import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import androidx.media.MediaBrowserServiceCompat;

class t implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.k f921a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f922b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ Bundle f923c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ ResultReceiver f924d;
    final /* synthetic */ MediaBrowserServiceCompat.j e;

    t(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, String str, Bundle bundle, ResultReceiver resultReceiver) {
        this.e = jVar;
        this.f921a = kVar;
        this.f922b = str;
        this.f923c = bundle;
        this.f924d = resultReceiver;
    }

    public void run() {
        MediaBrowserServiceCompat.b bVar = MediaBrowserServiceCompat.this.f868c.get(this.f921a.asBinder());
        if (bVar == null) {
            Log.w("MBServiceCompat", "search for callback that isn't registered query=" + this.f922b);
            return;
        }
        MediaBrowserServiceCompat.this.b(this.f922b, this.f923c, bVar, this.f924d);
    }
}
