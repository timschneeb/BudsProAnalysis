package b.e.d;

import android.content.Context;
import android.graphics.Typeface;
import b.e.d.f;
import java.util.concurrent.Callable;

/* access modifiers changed from: package-private */
public class b implements Callable<f.c> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Context f1345a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ a f1346b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ int f1347c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ String f1348d;

    b(Context context, a aVar, int i, String str) {
        this.f1345a = context;
        this.f1346b = aVar;
        this.f1347c = i;
        this.f1348d = str;
    }

    @Override // java.util.concurrent.Callable
    public f.c call() {
        f.c a2 = f.a(this.f1345a, this.f1346b, this.f1347c);
        Typeface typeface = a2.f1362a;
        if (typeface != null) {
            f.f1352a.a(this.f1348d, typeface);
        }
        return a2;
    }
}
