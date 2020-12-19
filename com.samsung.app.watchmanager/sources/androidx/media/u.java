package androidx.media;

import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import androidx.media.MediaBrowserServiceCompat;

class u implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ MediaBrowserServiceCompat.k f925a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f926b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ Bundle f927c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ ResultReceiver f928d;
    final /* synthetic */ MediaBrowserServiceCompat.j e;

    u(MediaBrowserServiceCompat.j jVar, MediaBrowserServiceCompat.k kVar, String str, Bundle bundle, ResultReceiver resultReceiver) {
        this.e = jVar;
        this.f925a = kVar;
        this.f926b = str;
        this.f927c = bundle;
        this.f928d = resultReceiver;
    }

    public void run() {
        MediaBrowserServiceCompat.b bVar = MediaBrowserServiceCompat.this.f868c.get(this.f925a.asBinder());
        if (bVar == null) {
            Log.w("MBServiceCompat", "sendCustomAction for callback that isn't registered action=" + this.f926b + ", extras=" + this.f927c);
            return;
        }
        MediaBrowserServiceCompat.this.a(this.f926b, this.f927c, bVar, this.f928d);
    }
}
