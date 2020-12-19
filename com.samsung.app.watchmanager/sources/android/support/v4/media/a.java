package android.support.v4.media;

import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;

class a implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ MediaBrowserCompat.i f44a;

    a(MediaBrowserCompat.i iVar) {
        this.f44a = iVar;
    }

    public void run() {
        MediaBrowserCompat.i iVar = this.f44a;
        if (iVar.g != 0) {
            iVar.g = 2;
            if (!MediaBrowserCompat.f0a || iVar.h == null) {
                MediaBrowserCompat.i iVar2 = this.f44a;
                if (iVar2.i != null) {
                    throw new RuntimeException("mServiceBinderWrapper should be null. Instead it is " + this.f44a.i);
                } else if (iVar2.j == null) {
                    Intent intent = new Intent("android.media.browse.MediaBrowserService");
                    intent.setComponent(this.f44a.f17b);
                    MediaBrowserCompat.i iVar3 = this.f44a;
                    iVar3.h = new MediaBrowserCompat.i.a();
                    boolean z = false;
                    try {
                        z = this.f44a.f16a.bindService(intent, this.f44a.h, 1);
                    } catch (Exception unused) {
                        Log.e("MediaBrowserCompat", "Failed binding to service " + this.f44a.f17b);
                    }
                    if (!z) {
                        this.f44a.b();
                        this.f44a.f18c.b();
                    }
                    if (MediaBrowserCompat.f0a) {
                        Log.d("MediaBrowserCompat", "connect...");
                        this.f44a.a();
                    }
                } else {
                    throw new RuntimeException("mCallbacksMessenger should be null. Instead it is " + this.f44a.j);
                }
            } else {
                throw new RuntimeException("mServiceConnection should be null. Instead it is " + this.f44a.h);
            }
        }
    }
}
