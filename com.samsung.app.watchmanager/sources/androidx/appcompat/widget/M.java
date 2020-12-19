package androidx.appcompat.widget;

import androidx.recyclerview.widget.LinearLayoutManager;

/* access modifiers changed from: package-private */
public class M {

    /* renamed from: a  reason: collision with root package name */
    private int f385a = 0;

    /* renamed from: b  reason: collision with root package name */
    private int f386b = 0;

    /* renamed from: c  reason: collision with root package name */
    private int f387c = LinearLayoutManager.INVALID_OFFSET;

    /* renamed from: d  reason: collision with root package name */
    private int f388d = LinearLayoutManager.INVALID_OFFSET;
    private int e = 0;
    private int f = 0;
    private boolean g = false;
    private boolean h = false;

    M() {
    }

    public int a() {
        return this.g ? this.f385a : this.f386b;
    }

    public void a(int i, int i2) {
        this.h = false;
        if (i != Integer.MIN_VALUE) {
            this.e = i;
            this.f385a = i;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.f = i2;
            this.f386b = i2;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001a, code lost:
        if (r2 != Integer.MIN_VALUE) goto L_0x0031;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0028, code lost:
        if (r2 != Integer.MIN_VALUE) goto L_0x0031;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(boolean r2) {
        /*
            r1 = this;
            boolean r0 = r1.g
            if (r2 != r0) goto L_0x0005
            return
        L_0x0005:
            r1.g = r2
            boolean r0 = r1.h
            if (r0 == 0) goto L_0x002b
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r2 == 0) goto L_0x001d
            int r2 = r1.f388d
            if (r2 == r0) goto L_0x0014
            goto L_0x0016
        L_0x0014:
            int r2 = r1.e
        L_0x0016:
            r1.f385a = r2
            int r2 = r1.f387c
            if (r2 == r0) goto L_0x002f
            goto L_0x0031
        L_0x001d:
            int r2 = r1.f387c
            if (r2 == r0) goto L_0x0022
            goto L_0x0024
        L_0x0022:
            int r2 = r1.e
        L_0x0024:
            r1.f385a = r2
            int r2 = r1.f388d
            if (r2 == r0) goto L_0x002f
            goto L_0x0031
        L_0x002b:
            int r2 = r1.e
            r1.f385a = r2
        L_0x002f:
            int r2 = r1.f
        L_0x0031:
            r1.f386b = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.M.a(boolean):void");
    }

    public int b() {
        return this.f385a;
    }

    public void b(int i, int i2) {
        this.f387c = i;
        this.f388d = i2;
        this.h = true;
        if (this.g) {
            if (i2 != Integer.MIN_VALUE) {
                this.f385a = i2;
            }
            if (i != Integer.MIN_VALUE) {
                this.f386b = i;
                return;
            }
            return;
        }
        if (i != Integer.MIN_VALUE) {
            this.f385a = i;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.f386b = i2;
        }
    }

    public int c() {
        return this.f386b;
    }

    public int d() {
        return this.g ? this.f386b : this.f385a;
    }
}
