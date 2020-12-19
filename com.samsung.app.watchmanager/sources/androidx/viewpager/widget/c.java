package androidx.viewpager.widget;

import android.view.View;

class c implements View.OnClickListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ PagerTabStrip f1201a;

    c(PagerTabStrip pagerTabStrip) {
        this.f1201a = pagerTabStrip;
    }

    public void onClick(View view) {
        ViewPager viewPager = this.f1201a.f1179c;
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }
}
