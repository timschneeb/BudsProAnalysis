package c.b.c.b.a;

import android.os.Handler;
import android.os.Message;

/* access modifiers changed from: package-private */
public class a implements Handler.Callback {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ b f1866a;

    a(b bVar) {
        this.f1866a = bVar;
    }

    public boolean handleMessage(Message message) {
        this.f1866a.m.start();
        return false;
    }
}
