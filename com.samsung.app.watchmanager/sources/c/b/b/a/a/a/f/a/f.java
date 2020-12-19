package c.b.b.a.a.a.f.a;

import c.b.b.a.a.a.c.a;
import c.b.b.a.a.a.c.b;
import c.b.b.a.a.a.f.e;
import c.b.b.a.a.c;
import com.samsung.android.app.twatchmanager.update.UpdateCheckTask;

public class f implements b {

    /* renamed from: a  reason: collision with root package name */
    private c f1754a;

    /* renamed from: b  reason: collision with root package name */
    private c f1755b;

    /* renamed from: c  reason: collision with root package name */
    private e f1756c;

    /* renamed from: d  reason: collision with root package name */
    private a f1757d;
    private int e = -1;

    public f(c cVar, c cVar2, e eVar, a aVar) {
        this.f1754a = cVar;
        this.f1755b = cVar2;
        this.f1756c = eVar;
        this.f1757d = aVar;
    }

    @Override // c.b.b.a.a.a.c.b
    public int onFinish() {
        if (this.e == 0) {
            c.b.b.a.a.a.i.a.a("DLC Sender", "send result success : " + this.e);
            return 1;
        }
        c.b.b.a.a.a.i.a.a("DLC Sender", "send result fail : " + this.e);
        return -7;
    }

    @Override // c.b.b.a.a.a.c.b
    public void run() {
        try {
            this.e = this.f1754a.a().a(this.f1756c.d().b(), this.f1755b.e().substring(0, 3), this.f1756c.c(), this.f1756c.b(), UpdateCheckTask.RESULT_CANT_FIND_APP, "", c.b.b.a.a.b.f1812b, this.f1756c.a());
            c.b.b.a.a.a.i.a.c("send to DLC : " + this.f1756c.a());
        } catch (Exception e2) {
            c.b.b.a.a.a.i.a.a(f.class, e2);
        }
    }
}
