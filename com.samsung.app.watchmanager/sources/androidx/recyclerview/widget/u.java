package androidx.recyclerview.widget;

import androidx.recyclerview.widget.C0096a;
import java.util.List;

/* access modifiers changed from: package-private */
public class u {

    /* renamed from: a  reason: collision with root package name */
    final a f1121a;

    /* access modifiers changed from: package-private */
    public interface a {
        C0096a.b a(int i, int i2, int i3, Object obj);

        void a(C0096a.b bVar);
    }

    u(a aVar) {
        this.f1121a = aVar;
    }

    private void a(List<C0096a.b> list, int i, int i2) {
        C0096a.b bVar = list.get(i);
        C0096a.b bVar2 = list.get(i2);
        int i3 = bVar2.f1053a;
        if (i3 == 1) {
            c(list, i, bVar, i2, bVar2);
        } else if (i3 == 2) {
            a(list, i, bVar, i2, bVar2);
        } else if (i3 == 4) {
            b(list, i, bVar, i2, bVar2);
        }
    }

    private int b(List<C0096a.b> list) {
        boolean z = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size).f1053a != 8) {
                z = true;
            } else if (z) {
                return size;
            }
        }
        return -1;
    }

    private void c(List<C0096a.b> list, int i, C0096a.b bVar, int i2, C0096a.b bVar2) {
        int i3 = bVar.f1056d < bVar2.f1054b ? -1 : 0;
        if (bVar.f1054b < bVar2.f1054b) {
            i3++;
        }
        int i4 = bVar2.f1054b;
        int i5 = bVar.f1054b;
        if (i4 <= i5) {
            bVar.f1054b = i5 + bVar2.f1056d;
        }
        int i6 = bVar2.f1054b;
        int i7 = bVar.f1056d;
        if (i6 <= i7) {
            bVar.f1056d = i7 + bVar2.f1056d;
        }
        bVar2.f1054b += i3;
        list.set(i, bVar2);
        list.set(i2, bVar);
    }

    /* access modifiers changed from: package-private */
    public void a(List<C0096a.b> list) {
        while (true) {
            int b2 = b(list);
            if (b2 != -1) {
                a(list, b2, b2 + 1);
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a0, code lost:
        if (r0 > r14.f1054b) goto L_0x00cc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ca, code lost:
        if (r0 >= r14.f1054b) goto L_0x00cc;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0077  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.util.List<androidx.recyclerview.widget.C0096a.b> r10, int r11, androidx.recyclerview.widget.C0096a.b r12, int r13, androidx.recyclerview.widget.C0096a.b r14) {
        /*
        // Method dump skipped, instructions count: 231
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.u.a(java.util.List, int, androidx.recyclerview.widget.a$b, int, androidx.recyclerview.widget.a$b):void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(java.util.List<androidx.recyclerview.widget.C0096a.b> r9, int r10, androidx.recyclerview.widget.C0096a.b r11, int r12, androidx.recyclerview.widget.C0096a.b r13) {
        /*
            r8 = this;
            int r0 = r11.f1056d
            int r1 = r13.f1054b
            r2 = 4
            r3 = 0
            r4 = 1
            if (r0 >= r1) goto L_0x000d
            int r1 = r1 - r4
            r13.f1054b = r1
            goto L_0x0020
        L_0x000d:
            int r5 = r13.f1056d
            int r1 = r1 + r5
            if (r0 >= r1) goto L_0x0020
            int r5 = r5 - r4
            r13.f1056d = r5
            androidx.recyclerview.widget.u$a r0 = r8.f1121a
            int r1 = r11.f1054b
            java.lang.Object r5 = r13.f1055c
            androidx.recyclerview.widget.a$b r0 = r0.a(r2, r1, r4, r5)
            goto L_0x0021
        L_0x0020:
            r0 = r3
        L_0x0021:
            int r1 = r11.f1054b
            int r5 = r13.f1054b
            if (r1 > r5) goto L_0x002b
            int r5 = r5 + r4
            r13.f1054b = r5
            goto L_0x0041
        L_0x002b:
            int r6 = r13.f1056d
            int r7 = r5 + r6
            if (r1 >= r7) goto L_0x0041
            int r5 = r5 + r6
            int r5 = r5 - r1
            androidx.recyclerview.widget.u$a r3 = r8.f1121a
            int r1 = r1 + r4
            java.lang.Object r4 = r13.f1055c
            androidx.recyclerview.widget.a$b r3 = r3.a(r2, r1, r5, r4)
            int r1 = r13.f1056d
            int r1 = r1 - r5
            r13.f1056d = r1
        L_0x0041:
            r9.set(r12, r11)
            int r11 = r13.f1056d
            if (r11 <= 0) goto L_0x004c
            r9.set(r10, r13)
            goto L_0x0054
        L_0x004c:
            r9.remove(r10)
            androidx.recyclerview.widget.u$a r11 = r8.f1121a
            r11.a(r13)
        L_0x0054:
            if (r0 == 0) goto L_0x0059
            r9.add(r10, r0)
        L_0x0059:
            if (r3 == 0) goto L_0x005e
            r9.add(r10, r3)
        L_0x005e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.u.b(java.util.List, int, androidx.recyclerview.widget.a$b, int, androidx.recyclerview.widget.a$b):void");
    }
}
