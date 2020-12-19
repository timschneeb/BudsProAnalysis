package androidx.fragment.app;

import java.util.ArrayList;

/* access modifiers changed from: package-private */
public class x implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ArrayList f807a;

    x(ArrayList arrayList) {
        this.f807a = arrayList;
    }

    public void run() {
        B.a(this.f807a, 4);
    }
}
