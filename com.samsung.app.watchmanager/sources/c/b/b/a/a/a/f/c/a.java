package c.b.b.a.a.a.f.c;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import c.c.a.a.a.a;

/* access modifiers changed from: package-private */
public class a implements ServiceConnection {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ c.b.b.a.a.a.a f1768a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ b f1769b;

    a(b bVar, c.b.b.a.a.a.a aVar) {
        this.f1769b = bVar;
        this.f1768a = aVar;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        String str;
        try {
            this.f1769b.f1771b = a.AbstractBinderC0035a.a(iBinder);
            String b2 = this.f1769b.f1771b.b();
            if (b2 == null) {
                this.f1769b.e();
                this.f1769b.f1773d = true;
                str = "Token failed";
            } else {
                this.f1769b.f1773d = false;
                this.f1768a.a(b2);
                str = "DMA connected";
            }
            c.b.b.a.a.a.i.a.a("DMABinder", str);
        } catch (Exception e) {
            this.f1769b.e();
            this.f1769b.f1773d = true;
            c.b.b.a.a.a.i.a.a(e.getClass(), e);
            e.printStackTrace();
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.f1769b.f1771b = null;
    }
}
