package c.b.b.a.a.a.f.a;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import c.c.b.a.a.a.a;

/* access modifiers changed from: package-private */
public class a implements ServiceConnection {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ c f1747a;

    a(c cVar) {
        this.f1747a = cVar;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        c.b.b.a.a.a.i.a.a("DLC Sender", "DLC Client ServiceConnected");
        this.f1747a.i = a.AbstractBinderC0037a.a(iBinder);
        if (this.f1747a.f1752d != null) {
            this.f1747a.f1751c.unregisterReceiver(this.f1747a.f1752d);
            this.f1747a.f1752d = null;
        }
        if (this.f1747a.f != null) {
            this.f1747a.f.a(null);
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        c.b.b.a.a.a.i.a.a("DLC Sender", "Client ServiceDisconnected");
        this.f1747a.i = null;
        this.f1747a.g = false;
    }
}
