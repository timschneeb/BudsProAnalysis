package b.e.d;

import android.os.Handler;
import androidx.core.content.a.h;
import b.e.d.f;
import b.e.d.k;

/* access modifiers changed from: package-private */
public class c implements k.a<f.c> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ h.a f1349a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ Handler f1350b;

    c(h.a aVar, Handler handler) {
        this.f1349a = aVar;
        this.f1350b = handler;
    }

    public void a(f.c cVar) {
        int i;
        h.a aVar;
        if (cVar == null) {
            aVar = this.f1349a;
            i = 1;
        } else {
            i = cVar.f1363b;
            if (i == 0) {
                this.f1349a.a(cVar.f1362a, this.f1350b);
                return;
            }
            aVar = this.f1349a;
        }
        aVar.a(i, this.f1350b);
    }
}
