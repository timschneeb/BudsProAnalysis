package b.e.d;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* access modifiers changed from: package-private */
public class j implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AtomicReference f1371a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ Callable f1372b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ ReentrantLock f1373c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ AtomicBoolean f1374d;
    final /* synthetic */ Condition e;
    final /* synthetic */ k f;

    j(k kVar, AtomicReference atomicReference, Callable callable, ReentrantLock reentrantLock, AtomicBoolean atomicBoolean, Condition condition) {
        this.f = kVar;
        this.f1371a = atomicReference;
        this.f1372b = callable;
        this.f1373c = reentrantLock;
        this.f1374d = atomicBoolean;
        this.e = condition;
    }

    public void run() {
        try {
            this.f1371a.set(this.f1372b.call());
        } catch (Exception unused) {
        }
        this.f1373c.lock();
        try {
            this.f1374d.set(false);
            this.e.signal();
        } finally {
            this.f1373c.unlock();
        }
    }
}
