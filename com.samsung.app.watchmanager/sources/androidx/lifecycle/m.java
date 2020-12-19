package androidx.lifecycle;

class m implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ LiveData f854a;

    m(LiveData liveData) {
        this.f854a = liveData;
    }

    public void run() {
        Object obj;
        synchronized (this.f854a.f821b) {
            obj = this.f854a.f;
            this.f854a.f = LiveData.f820a;
        }
        this.f854a.a(obj);
    }
}
