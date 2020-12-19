package c.b.b.a.a.a.f.c;

import c.b.b.a.a.a.c.b;
import c.b.b.a.a.c;
import c.c.a.a.a.a;

public class e implements b {

    /* renamed from: a  reason: collision with root package name */
    private c.b.b.a.a.a.f.e f1775a;

    /* renamed from: b  reason: collision with root package name */
    private a f1776b;

    /* renamed from: c  reason: collision with root package name */
    private c f1777c;

    e(a aVar, c cVar, c.b.b.a.a.a.f.e eVar) {
        this.f1775a = eVar;
        this.f1776b = aVar;
        this.f1777c = cVar;
    }

    @Override // c.b.b.a.a.a.c.b
    public int onFinish() {
        return 0;
    }

    @Override // c.b.b.a.a.a.c.b
    public void run() {
        try {
            this.f1776b.a(this.f1777c.k() ? 1 : 0, this.f1777c.e(), this.f1775a.d().b(), this.f1775a.c(), this.f1775a.a());
        } catch (Exception e) {
            c.b.b.a.a.a.i.a.a(e.getClass(), e);
        }
    }
}
