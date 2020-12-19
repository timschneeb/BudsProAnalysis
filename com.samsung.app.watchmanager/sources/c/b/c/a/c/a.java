package c.b.c.a.c;

import android.os.Handler;
import android.os.Message;
import c.b.c.a.c.b;

/* access modifiers changed from: package-private */
public class a implements Handler.Callback {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ b.a f1862a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ b f1863b;

    a(b bVar, b.a aVar) {
        this.f1863b = bVar;
        this.f1862a = aVar;
    }

    public boolean handleMessage(Message message) {
        boolean a2 = this.f1862a.a(message.what);
        Handler a3 = this.f1862a.a();
        a3.removeCallbacksAndMessages(null);
        this.f1863b.f1864a.remove(a3);
        return a2;
    }
}
