package c.a.a;

import c.a.a.c.a;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class q {

    /* renamed from: a  reason: collision with root package name */
    private c.a.a.b.q f1684a = c.a.a.b.q.f1632a;

    /* renamed from: b  reason: collision with root package name */
    private G f1685b = G.DEFAULT;

    /* renamed from: c  reason: collision with root package name */
    private AbstractC0155j f1686c = EnumC0154i.IDENTITY;

    /* renamed from: d  reason: collision with root package name */
    private final Map<Type, r<?>> f1687d = new HashMap();
    private final List<K> e = new ArrayList();
    private final List<K> f = new ArrayList();
    private boolean g;
    private String h;
    private int i = 2;
    private int j = 2;
    private boolean k;
    private boolean l;
    private boolean m = true;
    private boolean n;
    private boolean o;

    private void a(String str, int i2, int i3, List<K> list) {
        C0115a aVar;
        if (str != null && !"".equals(str.trim())) {
            aVar = new C0115a(str);
        } else if (i2 != 2 && i3 != 2) {
            aVar = new C0115a(i2, i3);
        } else {
            return;
        }
        list.add(I.a(a.a(Date.class), aVar));
        list.add(I.a(a.a(Timestamp.class), aVar));
        list.add(I.a(a.a(java.sql.Date.class), aVar));
    }

    public p a() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.e);
        Collections.reverse(arrayList);
        arrayList.addAll(this.f);
        a(this.h, this.i, this.j, arrayList);
        return new p(this.f1684a, this.f1686c, this.f1687d, this.g, this.k, this.o, this.m, this.n, this.l, this.f1685b, arrayList);
    }

    public q b() {
        this.m = false;
        return this;
    }
}
