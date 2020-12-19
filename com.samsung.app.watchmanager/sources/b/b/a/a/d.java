package b.b.a.a;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* access modifiers changed from: package-private */
public class d implements ThreadFactory {

    /* renamed from: a  reason: collision with root package name */
    private final AtomicInteger f1267a = new AtomicInteger(0);

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ e f1268b;

    d(e eVar) {
        this.f1268b = eVar;
    }

    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName(String.format("arch_disk_io_%d", Integer.valueOf(this.f1267a.getAndIncrement())));
        return thread;
    }
}
