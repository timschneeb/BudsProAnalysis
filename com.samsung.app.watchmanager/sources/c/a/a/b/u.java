package c.a.a.b;

import c.a.a.b.v;
import java.util.Map;

class u extends v<K, V>.c {
    final /* synthetic */ v.a e;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    u(v.a aVar) {
        super(v.this, null);
        this.e = aVar;
    }

    @Override // java.util.Iterator
    public Map.Entry<K, V> next() {
        return a();
    }
}
