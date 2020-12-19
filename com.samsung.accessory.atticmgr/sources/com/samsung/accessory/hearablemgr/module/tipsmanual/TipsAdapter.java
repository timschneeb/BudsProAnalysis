package com.samsung.accessory.hearablemgr.module.tipsmanual;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.ui.IntegratedTextFragment;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import java.util.ArrayList;

public class TipsAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mViewPagerFragments = new ArrayList<>();

    public TipsAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mViewPagerFragments.add(new TipsHowToWearFragment());
        this.mViewPagerFragments.add(new UseTouchpadFragment());
        if (Util.isFloatingFeatureForPowerSharing() || Util.isEmulator()) {
            this.mViewPagerFragments.add(new TipsChargingFragment());
        }
        this.mViewPagerFragments.add(new IntegratedTextFragment(R.layout.fragment_tips_case_battery));
        this.mViewPagerFragments.add(new TipsPairingFragment(R.layout.fragment_tips_pairing));
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return this.mViewPagerFragments.get(UiUtil.rtlCompatIndex(i, getCount()));
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mViewPagerFragments.size();
    }
}
