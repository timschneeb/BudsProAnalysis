package androidx.media;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import androidx.media.MediaBrowserServiceCompat;

class o implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.k f901a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f902b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ IBinder f903c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ Bundle f904d;
    final /* synthetic */ MediaBrowserServiceCompat.j e;

    o(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, String str, IBinder iBinder, Bundle bundle) {
        this.e = jVar;
        this.f901a = kVar;
        this.f902b = str;
        this.f903c = iBinder;
        this.f904d = bundle;
    }

    public void run() {
        MediaBrowserServiceCompat.b bVar = MediaBrowserServiceCompat.this.f868c.get(this.f901a.asBinder());
        if (bVar == null) {
            Log.w("MBServiceCompat", "addSubscription for callback that isn't registered id=" + this.f902b);
            return;
        }
        MediaBrowserServiceCompat.this.a(this.f902b, bVar, this.f903c, this.f904d);
    }
}
