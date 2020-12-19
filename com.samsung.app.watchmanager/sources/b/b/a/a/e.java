package b.b.a.a;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class e extends f {

    /* renamed from: a  reason: collision with root package name */
    private final Object f1269a = new Object();

    /* renamed from: b  reason: collision with root package name */
    private final ExecutorService f1270b = Executors.newFixedThreadPool(2, new d(this));

    /* renamed from: c  reason: collision with root package name */
    private volatile Handler f1271c;

    @Override // b.b.a.a.f
    public void a(Runnable runnable) {
        this.f1270b.execute(runnable);
    }

    @Override // b.b.a.a.f
    public boolean a() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    @Override // b.b.a.a.f
    public void b(Runnable runnable) {
        if (this.f1271c == null) {
            synchronized (this.f1269a) {
                if (this.f1271c == null) {
                    this.f1271c = new Handler(Looper.getMainLooper());
                }
            }
        }
        this.f1271c.post(runnable);
    }
}
