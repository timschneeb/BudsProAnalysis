package c.a.a.b.a;

import c.a.a.J;
import c.a.a.d.d;
import java.util.BitSet;

class Q extends J<BitSet> {
    Q() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0038, code lost:
        if (java.lang.Integer.parseInt(r1) != 0) goto L_0x0076;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0074, code lost:
        if (r8.j() != 0) goto L_0x0076;
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x007b A[SYNTHETIC] */
    @Override // c.a.a.J
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.BitSet a(c.a.a.d.b r8) {
        /*
        // Method dump skipped, instructions count: 134
        */
        throw new UnsupportedOperationException("Method not decompiled: c.a.a.b.a.Q.a(c.a.a.d.b):java.util.BitSet");
    }

    public void a(d dVar, BitSet bitSet) {
        if (bitSet == null) {
            dVar.h();
            return;
        }
        dVar.a();
        for (int i = 0; i < bitSet.length(); i++) {
            dVar.a(bitSet.get(i) ? 1 : 0);
        }
        dVar.c();
    }
}
