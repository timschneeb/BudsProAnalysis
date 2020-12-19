package d.a.a;

import d.a.a.a.b;
import d.a.a.b.q;
import java.io.Serializable;

public final class l extends b implements q, Serializable {

    /* renamed from: a  reason: collision with root package name */
    public static final l f2163a = new l(0);

    /* renamed from: b  reason: collision with root package name */
    private final long f2164b;

    public l(long j) {
        this.f2164b = j;
    }

    @Override // d.a.a.q
    public long a() {
        return this.f2164b;
    }

    @Override // d.a.a.q
    public a getChronology() {
        return q.O();
    }
}
