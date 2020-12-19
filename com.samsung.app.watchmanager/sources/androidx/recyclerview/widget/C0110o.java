package androidx.recyclerview.widget;

import androidx.recyclerview.widget.p;
import java.util.Comparator;

/* renamed from: androidx.recyclerview.widget.o  reason: case insensitive filesystem */
class C0110o implements Comparator<p.b> {
    C0110o() {
    }

    /* renamed from: a */
    public int compare(p.b bVar, p.b bVar2) {
        if ((bVar.f1116d == null) != (bVar2.f1116d == null)) {
            return bVar.f1116d == null ? 1 : -1;
        }
        boolean z = bVar.f1113a;
        if (z != bVar2.f1113a) {
            return z ? -1 : 1;
        }
        int i = bVar2.f1114b - bVar.f1114b;
        if (i != 0) {
            return i;
        }
        int i2 = bVar.f1115c - bVar2.f1115c;
        if (i2 != 0) {
            return i2;
        }
        return 0;
    }
}
