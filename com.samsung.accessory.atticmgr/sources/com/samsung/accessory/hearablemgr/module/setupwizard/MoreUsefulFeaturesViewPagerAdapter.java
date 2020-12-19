package com.samsung.accessory.hearablemgr.module.setupwizard;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import java.util.ArrayList;

public class MoreUsefulFeaturesViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> mFragments = new ArrayList<>();

    public MoreUsefulFeaturesViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mFragments.add(new HowToWearFragment());
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return this.mFragments.get(UiUtil.rtlCompatIndex(i, getCount()));
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mFragments.size();
    }
}
