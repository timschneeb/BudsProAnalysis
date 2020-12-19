package android.support.v4.media;

import android.content.ComponentName;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;

class c implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ComponentName f46a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ IBinder f47b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ MediaBrowserCompat.i.a f48c;

    c(MediaBrowserCompat.i.a aVar, ComponentName componentName, IBinder iBinder) {
        this.f48c = aVar;
        this.f46a = componentName;
        this.f47b = iBinder;
    }

    public void run() {
        if (MediaBrowserCompat.f0a) {
            Log.d("MediaBrowserCompat", "MediaServiceConnection.onServiceConnected name=" + this.f46a + " binder=" + this.f47b);
            MediaBrowserCompat.i.this.a();
        }
        if (this.f48c.a("onServiceConnected")) {
            MediaBrowserCompat.i iVar = MediaBrowserCompat.i.this;
            iVar.i = new MediaBrowserCompat.l(this.f47b, iVar.f19d);
            MediaBrowserCompat.i iVar2 = MediaBrowserCompat.i.this;
            iVar2.j = new Messenger(iVar2.e);
            MediaBrowserCompat.i iVar3 = MediaBrowserCompat.i.this;
            iVar3.e.a(iVar3.j);
            MediaBrowserCompat.i.this.g = 2;
            try {
                if (MediaBrowserCompat.f0a) {
                    Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                    MediaBrowserCompat.i.this.a();
                }
                MediaBrowserCompat.i.this.i.a(MediaBrowserCompat.i.this.f16a, MediaBrowserCompat.i.this.j);
            } catch (RemoteException unused) {
                Log.w("MediaBrowserCompat", "RemoteException during connect for " + MediaBrowserCompat.i.this.f17b);
                if (MediaBrowserCompat.f0a) {
                    Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                    MediaBrowserCompat.i.this.a();
                }
            }
        }
    }
}
