package androidx.recyclerview.widget;

class K implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ StaggeredGridLayoutManager f950a;

    K(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        this.f950a = staggeredGridLayoutManager;
    }

    public void run() {
        this.f950a.c();
    }
}
