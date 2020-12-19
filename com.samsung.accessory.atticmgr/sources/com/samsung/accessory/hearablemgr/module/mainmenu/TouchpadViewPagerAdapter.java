package com.samsung.accessory.hearablemgr.module.mainmenu;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.ui.LayoutResourceFragment;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;

public class TouchpadViewPagerAdapter extends FragmentStatePagerAdapter {
    private int MAX_PAGE = 4;
    Fragment[] mViewPagerFragments = new Fragment[this.MAX_PAGE];

    public TouchpadViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mViewPagerFragments[0] = new LayoutResourceFragment(R.layout.touchpad_viewpager_0);
        this.mViewPagerFragments[1] = new LayoutResourceFragment(R.layout.touchpad_viewpager_1);
        this.mViewPagerFragments[2] = new LayoutResourceFragment(R.layout.touchpad_viewpager_2);
        this.mViewPagerFragments[3] = new LayoutResourceFragment(R.layout.touchpad_viewpager_3);
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public Fragment getItem(int i) {
        return this.mViewPagerFragments[UiUtil.rtlCompatIndex(i, getCount())];
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mViewPagerFragments.length;
    }
}
