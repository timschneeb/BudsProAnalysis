package c.b.c.a.c;

import android.os.Handler;
import java.util.ArrayList;
import java.util.Iterator;

public class b {

    /* renamed from: a  reason: collision with root package name */
    ArrayList<Handler> f1864a = new ArrayList<>();

    public static abstract class a {

        /* renamed from: a  reason: collision with root package name */
        protected Handler f1865a = null;

        /* access modifiers changed from: protected */
        public Handler a() {
            return this.f1865a;
        }

        /* access modifiers changed from: protected */
        public void a(Handler handler) {
            this.f1865a = handler;
        }

        /* access modifiers changed from: protected */
        public abstract boolean a(int i);
    }

    public Handler a(a aVar) {
        Handler handler = new Handler(new a(this, aVar));
        aVar.a(handler);
        this.f1864a.add(handler);
        return handler;
    }

    public void a() {
        Iterator<Handler> it = this.f1864a.iterator();
        while (it.hasNext()) {
            it.next().removeCallbacksAndMessages(null);
        }
    }
}
