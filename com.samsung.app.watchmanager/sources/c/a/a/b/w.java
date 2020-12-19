package c.a.a.b;

import c.a.a.b.v;

class w extends v<K, V>.c {
    final /* synthetic */ v.b e;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    w(v.b bVar) {
        super(v.this, null);
        this.e = bVar;
    }

    @Override // java.util.Iterator
    public K next() {
        return a().f;
    }
}
