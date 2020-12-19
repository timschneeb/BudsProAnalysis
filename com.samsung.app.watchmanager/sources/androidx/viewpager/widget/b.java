package androidx.viewpager.widget;

import android.view.View;

class b implements View.OnClickListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ PagerTabStrip f1200a;

    b(PagerTabStrip pagerTabStrip) {
        this.f1200a = pagerTabStrip;
    }

    public void onClick(View view) {
        ViewPager viewPager = this.f1200a.f1179c;
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }
}
