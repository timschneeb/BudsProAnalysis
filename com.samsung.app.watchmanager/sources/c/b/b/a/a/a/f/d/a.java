package c.b.b.a.a.a.f.d;

import android.content.Context;
import c.b.b.a.a.a.d.b;
import c.b.b.a.a.a.f.e;
import c.b.b.a.a.c;
import c.b.b.a.a.d;
import java.util.List;
import java.util.Queue;

public class a {

    /* renamed from: a  reason: collision with root package name */
    private static a f1783a;

    /* renamed from: b  reason: collision with root package name */
    private c.b.b.a.a.a.f.d.a.a f1784b;

    /* renamed from: c  reason: collision with root package name */
    private c.b.b.a.a.a.f.d.b.a f1785c;

    /* renamed from: d  reason: collision with root package name */
    private boolean f1786d;

    private a(Context context, boolean z) {
        if (z) {
            this.f1784b = new c.b.b.a.a.a.f.d.a.a(context);
        }
        this.f1785c = new c.b.b.a.a.a.f.d.b.a();
        this.f1786d = z;
    }

    private a(d dVar) {
        this.f1784b = new c.b.b.a.a.a.f.d.a.a(dVar);
        this.f1785c = new c.b.b.a.a.a.f.d.b.a();
        this.f1786d = true;
    }

    public static a a(Context context, c cVar) {
        a aVar;
        if (f1783a == null) {
            synchronized (a.class) {
                if (b.b() != 0) {
                    aVar = new a(context, false);
                } else if (c.b.b.a.a.a.i.c.a(context).getString("lgt", "").equals("rtb")) {
                    d c2 = cVar.c();
                    if (c2 != null) {
                        f1783a = new a(c2);
                    } else {
                        aVar = new a(context, true);
                    }
                } else {
                    aVar = new a(context, false);
                }
                f1783a = aVar;
            }
        }
        return f1783a;
    }

    private void d() {
        if (!this.f1785c.a().isEmpty()) {
            for (e eVar : this.f1785c.a()) {
                this.f1784b.a(eVar);
            }
            this.f1785c.a().clear();
        }
    }

    public Queue<e> a(int i) {
        Queue<e> queue;
        if (this.f1786d) {
            a();
            queue = i <= 0 ? this.f1784b.a() : this.f1784b.a(i);
        } else {
            queue = this.f1785c.a();
        }
        if (!queue.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("get log from ");
            sb.append(this.f1786d ? "Database " : "Queue ");
            sb.append("(");
            sb.append(queue.size());
            sb.append(")");
            c.b.b.a.a.a.i.a.c(sb.toString());
        }
        return queue;
    }

    public void a() {
        if (this.f1786d) {
            this.f1784b.a(c.b.b.a.a.a.i.e.a(5));
        }
    }

    public void a(long j, String str, c.b.b.a.a.a.f.c cVar) {
        a(new e(j, str, cVar));
    }

    public void a(Context context) {
        a(new c.b.b.a.a.a.f.d.a.a(context));
    }

    public void a(c.b.b.a.a.a.f.d.a.a aVar) {
        this.f1786d = true;
        this.f1784b = aVar;
        d();
    }

    public void a(e eVar) {
        if (this.f1786d) {
            this.f1784b.a(eVar);
        } else {
            this.f1785c.a(eVar);
        }
    }

    public void a(List<String> list) {
        if (!list.isEmpty() && this.f1786d) {
            this.f1784b.a(list);
        }
    }

    public Queue<e> b() {
        return a(0);
    }

    public boolean c() {
        return this.f1786d;
    }
}
