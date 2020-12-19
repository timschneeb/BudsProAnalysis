package b.e.d;

import android.os.Handler;
import b.e.d.k;
import java.util.concurrent.Callable;

/* access modifiers changed from: package-private */
public class i implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Callable f1367a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ Handler f1368b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ k.a f1369c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ k f1370d;

    i(k kVar, Callable callable, Handler handler, k.a aVar) {
        this.f1370d = kVar;
        this.f1367a = callable;
        this.f1368b = handler;
        this.f1369c = aVar;
    }

    public void run() {
        Object obj;
        try {
            obj = this.f1367a.call();
        } catch (Exception unused) {
            obj = null;
        }
        this.f1368b.post(new h(this, obj));
    }
}
