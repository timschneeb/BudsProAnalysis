package android.support.v4.media;

import android.content.ComponentName;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;

class d implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ComponentName f49a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ MediaBrowserCompat.i.a f50b;

    d(MediaBrowserCompat.i.a aVar, ComponentName componentName) {
        this.f50b = aVar;
        this.f49a = componentName;
    }

    public void run() {
        if (MediaBrowserCompat.f0a) {
            Log.d("MediaBrowserCompat", "MediaServiceConnection.onServiceDisconnected name=" + this.f49a + " this=" + this + " mServiceConnection=" + MediaBrowserCompat.i.this.h);
            MediaBrowserCompat.i.this.a();
        }
        if (this.f50b.a("onServiceDisconnected")) {
            MediaBrowserCompat.i iVar = MediaBrowserCompat.i.this;
            iVar.i = null;
            iVar.j = null;
            iVar.e.a(null);
            MediaBrowserCompat.i iVar2 = MediaBrowserCompat.i.this;
            iVar2.g = 4;
            iVar2.f18c.c();
        }
    }
}
