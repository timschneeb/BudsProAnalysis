package c.b.b.a.a.a.c;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class f implements c {

    /* renamed from: a  reason: collision with root package name */
    private static ExecutorService f1726a;

    /* renamed from: b  reason: collision with root package name */
    private static f f1727b;

    public f() {
        f1726a = Executors.newSingleThreadExecutor(new d(this));
    }

    public static c a() {
        if (f1727b == null) {
            f1727b = new f();
        }
        return f1727b;
    }

    @Override // c.b.b.a.a.a.c.c
    public void a(b bVar) {
        f1726a.submit(new e(this, bVar));
    }
}
