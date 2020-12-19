package android.support.v4.media;

import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;

class b implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ MediaBrowserCompat.i f45a;

    b(MediaBrowserCompat.i iVar) {
        this.f45a = iVar;
    }

    public void run() {
        MediaBrowserCompat.i iVar = this.f45a;
        Messenger messenger = iVar.j;
        if (messenger != null) {
            try {
                iVar.i.a(messenger);
            } catch (RemoteException unused) {
                Log.w("MediaBrowserCompat", "RemoteException during connect for " + this.f45a.f17b);
            }
        }
        MediaBrowserCompat.i iVar2 = this.f45a;
        int i = iVar2.g;
        iVar2.b();
        if (i != 0) {
            this.f45a.g = i;
        }
        if (MediaBrowserCompat.f0a) {
            Log.d("MediaBrowserCompat", "disconnect...");
            this.f45a.a();
        }
    }
}
