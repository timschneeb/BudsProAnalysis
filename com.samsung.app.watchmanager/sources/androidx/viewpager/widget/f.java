package androidx.viewpager.widget;

class f implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ViewPager f1202a;

    f(ViewPager viewPager) {
        this.f1202a = viewPager;
    }

    public void run() {
        this.f1202a.setScrollState(0);
        this.f1202a.e();
    }
}
