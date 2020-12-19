package c.b.b.a.a.a.f.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import c.b.b.a.a.a.a;

public class c {

    /* renamed from: a  reason: collision with root package name */
    private static String f1749a = "com.sec.spp.push";

    /* renamed from: b  reason: collision with root package name */
    private static String f1750b = "com.sec.spp.push.dlc.writer.WriterService";

    /* renamed from: c  reason: collision with root package name */
    private Context f1751c;

    /* renamed from: d  reason: collision with root package name */
    private BroadcastReceiver f1752d;
    private String e;
    private a f;
    private boolean g;
    private boolean h;
    private c.c.b.a.a.a.a i;
    private ServiceConnection j;

    public c(Context context) {
        this.g = false;
        this.h = false;
        this.j = new a(this);
        this.f1751c = context;
        this.e = context.getPackageName();
        this.e += ".REGISTER_FILTER";
    }

    public c(Context context, a aVar) {
        this(context);
        this.f = aVar;
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        if (this.g) {
            e();
        }
        try {
            Intent intent = new Intent(str);
            intent.setClassName(f1749a, f1750b);
            this.g = this.f1751c.bindService(intent, this.j, 1);
            c.b.b.a.a.a.i.a.a("DLCBinder", "bind");
        } catch (Exception e2) {
            c.b.b.a.a.a.i.a.a(c.class, e2);
        }
    }

    private void e() {
        if (this.g) {
            try {
                c.b.b.a.a.a.i.a.a("DLCBinder", "unbind");
                this.f1751c.unbindService(this.j);
                this.g = false;
            } catch (Exception e2) {
                c.b.b.a.a.a.i.a.a(c.class, e2);
            }
        }
    }

    public c.c.b.a.a.a.a a() {
        return this.i;
    }

    public boolean b() {
        return this.g;
    }

    public void c() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(this.e);
        if (this.f1752d == null) {
            this.f1752d = new b(this);
        }
        this.f1751c.registerReceiver(this.f1752d, intentFilter);
    }

    public void d() {
        if (this.f1752d == null) {
            c();
        }
        if (!this.h) {
            Intent intent = new Intent("com.sec.spp.push.REQUEST_REGISTER");
            intent.putExtra("EXTRA_PACKAGENAME", this.f1751c.getPackageName());
            intent.putExtra("EXTRA_INTENTFILTER", this.e);
            intent.setPackage("com.sec.spp.push");
            this.f1751c.sendBroadcast(intent);
            this.h = true;
            c.b.b.a.a.a.i.a.a("DLCBinder", "send register Request");
            c.b.b.a.a.a.i.a.c("send register Request:" + this.f1751c.getPackageName());
            return;
        }
        c.b.b.a.a.a.i.a.a("DLCBinder", "already send register request");
    }
}
