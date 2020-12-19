package b.j.a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* access modifiers changed from: package-private */
public class a extends Handler {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ b f1471a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    a(b bVar, Looper looper) {
        super(looper);
        this.f1471a = bVar;
    }

    public void handleMessage(Message message) {
        if (message.what != 1) {
            super.handleMessage(message);
        } else {
            this.f1471a.a();
        }
    }
}
