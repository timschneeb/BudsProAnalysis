package androidx.core.content.a;

import androidx.core.content.a.h;

/* access modifiers changed from: package-private */
public class g implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ int f614a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ h.a f615b;

    g(h.a aVar, int i) {
        this.f615b = aVar;
        this.f614a = i;
    }

    public void run() {
        this.f615b.a(this.f614a);
    }
}
