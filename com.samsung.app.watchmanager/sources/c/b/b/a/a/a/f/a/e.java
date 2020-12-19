package c.b.b.a.a.a.f.a;

import android.content.Context;
import c.b.b.a.a.a.f.a;
import c.b.b.a.a.c;
import java.util.Map;
import java.util.Queue;

public class e extends a {
    private c g;

    public e(Context context, c cVar) {
        super(context, cVar);
        this.g = new c(context, new d(this));
        this.g.d();
    }

    /* access modifiers changed from: private */
    public void a() {
        Queue<c.b.b.a.a.a.f.e> b2 = this.e.b();
        while (!b2.isEmpty()) {
            this.f.a(new f(this.g, this.f1744b, b2.poll(), null));
        }
    }

    @Override // c.b.b.a.a.a.f.b
    public int a(Map<String, String> map) {
        c(map);
        if (!this.g.b()) {
            this.g.d();
            return 0;
        } else if (this.g.a() == null) {
            return 0;
        } else {
            a();
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    @Override // c.b.b.a.a.a.f.a
    public Map<String, String> e(Map<String, String> map) {
        super.e(map);
        map.remove("do");
        map.remove("dm");
        map.remove("v");
        return map;
    }
}
