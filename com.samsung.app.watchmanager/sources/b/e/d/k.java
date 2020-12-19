package b.e.d;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class k {

    /* renamed from: a  reason: collision with root package name */
    private final Object f1375a = new Object();

    /* renamed from: b  reason: collision with root package name */
    private HandlerThread f1376b;

    /* renamed from: c  reason: collision with root package name */
    private Handler f1377c;

    /* renamed from: d  reason: collision with root package name */
    private int f1378d;
    private Handler.Callback e = new g(this);
    private final int f;
    private final int g;
    private final String h;

    public interface a<T> {
        void a(T t);
    }

    public k(String str, int i, int i2) {
        this.h = str;
        this.g = i;
        this.f = i2;
        this.f1378d = 0;
    }

    private void b(Runnable runnable) {
        synchronized (this.f1375a) {
            if (this.f1376b == null) {
                this.f1376b = new HandlerThread(this.h, this.g);
                this.f1376b.start();
                this.f1377c = new Handler(this.f1376b.getLooper(), this.e);
                this.f1378d++;
            }
            this.f1377c.removeMessages(0);
            this.f1377c.sendMessage(this.f1377c.obtainMessage(1, runnable));
        }
    }

    public <T> T a(Callable<T> callable, int i) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition newCondition = reentrantLock.newCondition();
        AtomicReference atomicReference = new AtomicReference();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        b(new j(this, atomicReference, callable, reentrantLock, atomicBoolean, newCondition));
        reentrantLock.lock();
        try {
            if (!atomicBoolean.get()) {
                return (T) atomicReference.get();
            }
            long nanos = TimeUnit.MILLISECONDS.toNanos((long) i);
            do {
                try {
                    nanos = newCondition.awaitNanos(nanos);
                } catch (InterruptedException unused) {
                }
                if (!atomicBoolean.get()) {
                    T t = (T) atomicReference.get();
                    reentrantLock.unlock();
                    return t;
                }
            } while (nanos > 0);
            throw new InterruptedException("timeout");
        } finally {
            reentrantLock.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public void a() {
        synchronized (this.f1375a) {
            if (!this.f1377c.hasMessages(1)) {
                this.f1376b.quit();
                this.f1376b = null;
                this.f1377c = null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Runnable runnable) {
        runnable.run();
        synchronized (this.f1375a) {
            this.f1377c.removeMessages(0);
            this.f1377c.sendMessageDelayed(this.f1377c.obtainMessage(0), (long) this.f);
        }
    }

    public <T> void a(Callable<T> callable, a<T> aVar) {
        b(new i(this, callable, new Handler(), aVar));
    }
}
