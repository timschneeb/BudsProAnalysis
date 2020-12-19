package c.b.b.a.a.a.f.d.b;

import c.b.b.a.a.a.f.e;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class a {

    /* renamed from: a  reason: collision with root package name */
    protected LinkedBlockingQueue<e> f1789a = new LinkedBlockingQueue<>(25);

    public Queue<e> a() {
        return this.f1789a;
    }

    public void a(e eVar) {
        if (!this.f1789a.offer(eVar)) {
            c.b.b.a.a.a.i.a.a("QueueManager", "queue size over. remove oldest log");
            this.f1789a.poll();
            this.f1789a.offer(eVar);
        }
    }
}
