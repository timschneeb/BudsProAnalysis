package androidx.core.content.a;

import android.graphics.Typeface;
import androidx.core.content.a.h;

/* access modifiers changed from: package-private */
public class f implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Typeface f612a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ h.a f613b;

    f(h.a aVar, Typeface typeface) {
        this.f613b = aVar;
        this.f612a = typeface;
    }

    public void run() {
        this.f613b.a(this.f612a);
    }
}
