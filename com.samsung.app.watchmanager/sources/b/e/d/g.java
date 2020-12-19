package b.e.d;

import android.os.Handler;
import android.os.Message;

/* access modifiers changed from: package-private */
public class g implements Handler.Callback {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ k f1364a;

    g(k kVar) {
        this.f1364a = kVar;
    }

    public boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            this.f1364a.a();
            return true;
        } else if (i != 1) {
            return true;
        } else {
            this.f1364a.a((Runnable) message.obj);
            return true;
        }
    }
}
