package b.a.b.a;

/* access modifiers changed from: package-private */
public class c implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ d f1217a;

    c(d dVar) {
        this.f1217a = dVar;
    }

    public void run() {
        this.f1217a.a(true);
        this.f1217a.invalidateSelf();
    }
}
