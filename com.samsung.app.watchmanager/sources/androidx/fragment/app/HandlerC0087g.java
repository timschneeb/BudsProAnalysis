package androidx.fragment.app;

import android.os.Handler;
import android.os.Message;

/* access modifiers changed from: package-private */
/* renamed from: androidx.fragment.app.g  reason: case insensitive filesystem */
public class HandlerC0087g extends Handler {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ FragmentActivity f765a;

    HandlerC0087g(FragmentActivity fragmentActivity) {
        this.f765a = fragmentActivity;
    }

    public void handleMessage(Message message) {
        if (message.what != 2) {
            super.handleMessage(message);
            return;
        }
        this.f765a.e();
        this.f765a.f720d.i();
    }
}
