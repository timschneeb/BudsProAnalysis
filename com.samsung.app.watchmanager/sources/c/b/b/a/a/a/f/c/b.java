package c.b.b.a.a.a.f.c;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import c.c.a.a.a.a;

public class b {

    /* renamed from: a  reason: collision with root package name */
    private Context f1770a;

    /* renamed from: b  reason: collision with root package name */
    private a f1771b;

    /* renamed from: c  reason: collision with root package name */
    private ServiceConnection f1772c;

    /* renamed from: d  reason: collision with root package name */
    private boolean f1773d = false;
    private boolean e = false;

    public b(Context context, c.b.b.a.a.a.a<Void, String> aVar) {
        this.f1770a = context;
        this.f1772c = new a(this, aVar);
    }

    public boolean a() {
        if (!this.e && !this.f1773d) {
            try {
                Intent intent = new Intent();
                intent.setClassName("com.sec.android.diagmonagent", "com.sec.android.diagmonagent.sa.receiver.SALogReceiverService");
                this.e = this.f1770a.bindService(intent, this.f1772c, 1);
                c.b.b.a.a.a.i.a.a("DMABinder", "bind " + this.e);
            } catch (Exception e2) {
                c.b.b.a.a.a.i.a.a(e2.getClass(), e2);
            }
        }
        return this.f1773d;
    }

    public a b() {
        return this.f1771b;
    }

    public boolean c() {
        return this.e;
    }

    public boolean d() {
        return this.f1773d;
    }

    public void e() {
        if (this.f1771b != null && this.e) {
            try {
                this.f1770a.unbindService(this.f1772c);
                this.e = false;
                c.b.b.a.a.a.i.a.a("DMABinder", "unbind");
            } catch (Exception e2) {
                c.b.b.a.a.a.i.a.a(e2.getClass(), e2);
            }
        }
    }
}
