package c.b.b.a.a.a.c;

import c.b.b.a.a.a.i.a;
import java.util.concurrent.ThreadFactory;

/* access modifiers changed from: package-private */
public class d implements ThreadFactory {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ f f1723a;

    d(f fVar) {
        this.f1723a = fVar;
    }

    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setPriority(1);
        thread.setDaemon(true);
        a.a("newThread on Executor");
        return thread;
    }
}
