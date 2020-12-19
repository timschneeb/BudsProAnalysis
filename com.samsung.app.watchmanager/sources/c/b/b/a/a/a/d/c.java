package c.b.b.a.a.a.d;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import c.b.b.a.a.a.i.a;
import c.b.b.a.a.h;

/* access modifiers changed from: package-private */
public class c extends BroadcastReceiver {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Application f1736a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ c.b.b.a.a.c f1737b;

    c(Application application, c.b.b.a.a.c cVar) {
        this.f1736a = application;
        this.f1737b = cVar;
    }

    public void onReceive(Context context, Intent intent) {
        a.a("receive " + intent.getAction());
        h.a(this.f1736a, this.f1737b);
    }
}
