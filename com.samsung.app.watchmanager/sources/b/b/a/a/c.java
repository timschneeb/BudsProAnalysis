package b.b.a.a;

import java.util.concurrent.Executor;

public class c extends f {

    /* renamed from: a  reason: collision with root package name */
    private static volatile c f1263a;

    /* renamed from: b  reason: collision with root package name */
    private static final Executor f1264b = new a();

    /* renamed from: c  reason: collision with root package name */
    private static final Executor f1265c = new b();

    /* renamed from: d  reason: collision with root package name */
    private f f1266d = this.e;
    private f e = new e();

    private c() {
    }

    public static c b() {
        if (f1263a != null) {
            return f1263a;
        }
        synchronized (c.class) {
            if (f1263a == null) {
                f1263a = new c();
            }
        }
        return f1263a;
    }

    @Override // b.b.a.a.f
    public void a(Runnable runnable) {
        this.f1266d.a(runnable);
    }

    @Override // b.b.a.a.f
    public boolean a() {
        return this.f1266d.a();
    }

    @Override // b.b.a.a.f
    public void b(Runnable runnable) {
        this.f1266d.b(runnable);
    }
}
