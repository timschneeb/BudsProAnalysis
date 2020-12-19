package c.b.c.a.b;

import c.b.c.a.b.m;

/* access modifiers changed from: package-private */
public /* synthetic */ class d {

    /* renamed from: a  reason: collision with root package name */
    static final /* synthetic */ int[] f1841a = new int[m.a.values().length];

    /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
    /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
    static {
        /*
            c.b.c.a.b.m$a[] r0 = c.b.c.a.b.m.a.values()
            int r0 = r0.length
            int[] r0 = new int[r0]
            c.b.c.a.b.d.f1841a = r0
            int[] r0 = c.b.c.a.b.d.f1841a     // Catch:{ NoSuchFieldError -> 0x0014 }
            c.b.c.a.b.m$a r1 = c.b.c.a.b.m.a.OneWay     // Catch:{ NoSuchFieldError -> 0x0014 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            int[] r0 = c.b.c.a.b.d.f1841a     // Catch:{ NoSuchFieldError -> 0x001f }
            c.b.c.a.b.m$a r1 = c.b.c.a.b.m.a.GoAndBack     // Catch:{ NoSuchFieldError -> 0x001f }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: c.b.c.a.b.d.<clinit>():void");
    }
}
