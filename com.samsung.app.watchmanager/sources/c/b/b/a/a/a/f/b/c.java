package c.b.b.a.a.a.f.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import c.b.b.a.a.a.d.b;
import c.b.b.a.a.a.f.a;
import c.b.b.a.a.a.f.e;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class c extends a {
    public c(Context context, c.b.b.a.a.c cVar) {
        super(context, cVar);
    }

    private int a() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.f1743a.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return -4;
        }
        return activeNetworkInfo.getType();
    }

    private int a(int i) {
        if (i == -4) {
            c.b.b.a.a.a.i.a.a("DLS Sender", "Network unavailable.");
            return -4;
        } else if (!b.a(this.f1743a)) {
            return 0;
        } else {
            c.b.b.a.a.a.i.a.a("DLS Sender", "policy expired. request policy");
            return -6;
        }
    }

    private int a(int i, c.b.b.a.a.a.f.c cVar, Queue<e> queue, c.b.b.a.a.a.c.a aVar) {
        ArrayList arrayList = new ArrayList();
        Iterator<e> it = queue.iterator();
        while (true) {
            int i2 = 0;
            if (!it.hasNext()) {
                return 0;
            }
            LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
            int a2 = b.a(this.f1743a, i);
            if (51200 <= a2) {
                a2 = 51200;
            }
            while (it.hasNext()) {
                e next = it.next();
                if (next.d() == cVar) {
                    if (next.a().getBytes().length + i2 > a2) {
                        break;
                    }
                    i2 += next.a().getBytes().length;
                    linkedBlockingQueue.add(next);
                    it.remove();
                    arrayList.add(next.b());
                    if (queue.isEmpty()) {
                        this.e.a(arrayList);
                        queue = this.e.a(200);
                        it = queue.iterator();
                    }
                }
            }
            if (linkedBlockingQueue.isEmpty()) {
                return -1;
            }
            this.e.a(arrayList);
            a(i, cVar, linkedBlockingQueue, i2, aVar);
            c.b.b.a.a.a.i.a.a("DLSLogSender", "send packet : num(" + linkedBlockingQueue.size() + ") size(" + i2 + ")");
        }
    }

    private int a(int i, e eVar, c.b.b.a.a.a.c.a aVar, boolean z) {
        if (eVar == null) {
            return -100;
        }
        int length = eVar.a().getBytes().length;
        int a2 = b.a(this.f1743a, i, length);
        if (a2 != 0) {
            return a2;
        }
        b.b(this.f1743a, i, length);
        a aVar2 = new a(eVar, this.f1744b.e(), aVar);
        if (z) {
            c.b.b.a.a.a.i.a.c("sync send");
            aVar2.run();
            return aVar2.onFinish();
        }
        this.f.a(aVar2);
        return 0;
    }

    private void a(int i, c.b.b.a.a.a.f.c cVar, Queue<e> queue, int i2, c.b.b.a.a.a.c.a aVar) {
        b.b(this.f1743a, i, i2);
        this.f.a(new a(cVar, queue, this.f1744b.e(), aVar));
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x006a A[LOOP:0: B:12:0x006a->B:15:0x007a, LOOP_START, PHI: r2 
      PHI: (r2v3 int) = (r2v1 int), (r2v6 int) binds: [B:10:0x005d, B:15:0x007a] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // c.b.b.a.a.a.f.b
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int a(java.util.Map<java.lang.String, java.lang.String> r7) {
        /*
        // Method dump skipped, instructions count: 125
        */
        throw new UnsupportedOperationException("Method not decompiled: c.b.b.a.a.a.f.b.c.a(java.util.Map):int");
    }
}
